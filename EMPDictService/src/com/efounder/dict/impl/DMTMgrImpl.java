package com.efounder.dict.impl;

import java.sql.ResultSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.metadata.biz.MetaDataServiceMgr;
import com.metadata.persistence.SYS_DCT_CST;
import com.pub.util.StringFunction;

import com.common.util.EMPSQLUtil;
import com.efounder.builder.base.data.DataSetUtils;
import com.efounder.builder.base.data.EFDataSet;
import com.efounder.builder.base.data.EFRowSet;
import com.efounder.builder.meta.domodel.SYS_OBJCOLS;
import com.efounder.dctbuilder.rule.EMPCodeRuler;
import com.efounder.dict.DMTServiceMgr;
import com.efounder.eai.data.JParamObject;
import com.efounder.eai.data.JResponseObject;
import com.efounder.form.server.resolver.util.BizFormUtil;
import com.efounder.sql.JConnection;
import com.efounder.sql.JStatement;
import com.framework.sys.business.AbstractBusinessObjectServiceMgr;

@Transactional(rollbackFor = Exception.class)
@Service("DMTServiceMgr")
public class DMTMgrImpl extends AbstractBusinessObjectServiceMgr implements DMTServiceMgr {
	
	@Autowired
	private JdbcTemplate                    jdbcTemplate;
	
	@Autowired
	private MetaDataServiceMgr         metaDataServiceMgr;
	
	public EFDataSet searchDictRow(String tableName, String searchCol, String keywords, String F_SYZT, int startIndex, int count) {
		JParamObject     PO = JParamObject.Create();
		PO.SetValueByParamName("tableName", tableName);
		PO.SetValueByParamName("searchCol", searchCol);
		PO.SetValueByParamName("keywords", keywords);
		PO.SetValueByParamName("F_SYZT", F_SYZT);
		
		return searchDictRow(PO);
	}
	
	public EFDataSet searchDictRow(JParamObject PO) {
		JConnection    conn = null;
		JStatement     stmt = null;
		ResultSet        rs = null;
		EFDataSet   dataSet = EFDataSet.getInstance(PO.GetValueByParamName("tableName", ""));
		EFDataSet objcolsDS = EFDataSet.getInstance("SYS_OBJCOLS");
		String       strSql = " select COL_ID, COL_MC " + " from SYS_OBJCOLS"
			                + " where OBJ_ID = '" + PO.GetValueByParamName("tableName", "") + "' and F_STAU = '1'";
		String     sqlWhere = "";

		try {
			conn = JConnection.getInstance(jdbcTemplate.getDataSource().getConnection());
			stmt = conn.createStatement();
			rs = stmt.executeQuery(strSql);
			objcolsDS = DataSetUtils.resultSet2DataSet(rs, objcolsDS);
			PO.setValue("columns", objcolsDS);
			PO.SetValueByParamName("tableName", PO.GetValueByParamName("tableName", ""));
			
			if(!PO.GetValueByParamName("sqlWhere", sqlWhere).equals("")) {
				sqlWhere = PO.GetValueByParamName("sqlWhere", sqlWhere) + " and ";
			}
			
			if(PO.GetValueByParamName("searchCol", null) == null || PO.GetValueByParamName("searchCol", null).equals("")) {
				sqlWhere += " 1 = 1 ";
			} else {
				sqlWhere += PO.GetValueByParamName("searchCol", "") + " like '" + PO.GetValueByParamName("keywords", "") + "%'";
			}
			
			if(PO.GetValueByParamName("F_SYZT", "") != null && !PO.GetValueByParamName("F_SYZT", "").equals("")) {
				sqlWhere += " and F_SYZT = '" + PO.GetValueByParamName("F_SYZT", "") + "'";
			}
			PO.SetValueByParamName("sqlWhere", sqlWhere);
			strSql = EMPSQLUtil.getWholeSql(PO);
			rs = stmt.executeQuery(strSql);
			dataSet = DataSetUtils.resultSet2DataSet(rs, dataSet);
			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
			dataSet = EFDataSet.getInstance();
		} finally {
			try {
				EMPSQLUtil.closeAllResources(rs, stmt, conn);			
			} catch(Exception ce) {
				ce.printStackTrace();
			}
		}
		return dataSet;
	}
	
	public String getTempDictBM(String tableName) {
		JParamObject     PO = new JParamObject();
		String       tempBm = getTempDictBM(PO, tableName);
		return tempBm;
	}
	
	public String getTempDictBM(JParamObject PO, String tableName) {
		JConnection    conn = null;
		String     codeRule = "";
		String   DCT_BMSTRU = "";
		String       tempBm = "";
		int           bmLen = 0;
		EMPCodeRuler   rule = new EMPCodeRuler();
		
		try {
			conn = JConnection.getInstance(jdbcTemplate.getDataSource().getConnection());
			//获取编码方式
			codeRule = getCodeRule(tableName);
			//获取字典元数据
			DCT_BMSTRU = getDctBMSTRU(tableName);
			if(DCT_BMSTRU == null || DCT_BMSTRU.trim().equals("")) {
				DCT_BMSTRU = "6";
			}
			bmLen = StringFunction.getStrutLength(DCT_BMSTRU);
			PO.SetValueByParamName("DCT_BMSTRU_LENGTH", "" + bmLen);
			PO.SetValueByParamName("CODERULE", codeRule);
			
			tempBm = rule.getCodeByRule(PO);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				EMPSQLUtil.closeAllResources(null, null, conn);
			} catch(Exception ce) {
				ce.printStackTrace();
			}
		}
		return tempBm;
	}
	
	/**
	 * 获取编号方式
	 * @param conn
	 * @param tableName
	 * @return
	 */
	private String getCodeRule(String tableName) {
		EFRowSet   codeRuleRS = null;
		String       codeRule = "";

		try {
			codeRuleRS = metaDataServiceMgr.getExtendProperties(tableName);
			codeRule = codeRuleRS.getString(SYS_DCT_CST._CodeRule_, "");
			if(codeRule.equals("")) {
				codeRule = "LSH";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return codeRule;
	}
	
	/**
	 * 获取字典元数据编码结构
	 * @param conn
	 * @param tableName
	 * @return
	 */
	private String getDctBMSTRU(String tableName) {
		EFRowSet     dictRS = null;
		String   DCT_BMSTRU = "";

		try {
			dictRS = metaDataServiceMgr.getDictRow(tableName);
			if(dictRS == null) {
				return null;
			} else {
				DCT_BMSTRU = dictRS.getString("DCT_BMSTRU", "");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return DCT_BMSTRU;
	}
	
	
	public JResponseObject insertDCTRow(String tableName, EFRowSet rowset) {
		JConnection        conn = null;
		JStatement         stmt = null;
		String         codeRule = "";
		String       DCT_BMSTRU = "";
		String            newBH = "";
		String           F_ZDBM = "";
		int               bmLen = 0;
		EFRowSet     dictRowSet = null;
		EFRowSet    bizMetaData = null;
		JParamObject         PO = JParamObject.Create();
		JResponseObject      RO = new JResponseObject();
		EMPCodeRuler  codeRuler = new EMPCodeRuler();
		try {
			conn = JConnection.getInstance(jdbcTemplate.getDataSource().getConnection());
			conn.setAutoCommit(false);
			stmt = conn.createStatement();
			
			//获取编码方式
			codeRule = getCodeRule(tableName);
			//获取字典元数据
			DCT_BMSTRU = getDctBMSTRU(tableName);
			if(DCT_BMSTRU == null || DCT_BMSTRU.trim().equals("")) {
				DCT_BMSTRU = "6";
			}
			dictRowSet = metaDataServiceMgr.getDictRow(tableName);
			F_ZDBM = dictRowSet.getString("DCT_BMCOLID", "");
			bmLen = StringFunction.getStrutLength(DCT_BMSTRU);
			PO.SetValueByParamName("DCT_BMSTRU_LENGTH", "" + bmLen);
			PO.SetValueByParamName("CODERULE", codeRule);
			PO.SetValueByParamName("OBJ_ID", tableName);
			newBH = codeRuler.getNextDCTBHFromDB(conn, PO);
			rowset.put(F_ZDBM, newBH);
			
			bizMetaData = BizFormUtil.getBIZMetaData(stmt, tableName);
			
			try {
				BizFormUtil.saveRowSetData(stmt, tableName, rowset, bizMetaData.getDataSet(SYS_OBJCOLS._SYS_OBJCOLS_));
				conn.commit();
			} catch(Exception ce) {
				RO.setErrorCode(-1);
				RO.setErrorString(ce.getMessage());
				return RO;
			}
			RO.setErrorCode(1);
			RO.setResponseObject("数据添加成功！");
			return RO;
		} catch (Exception e) {
			e.printStackTrace();
			conn.rollback();
		} finally {
			try {
				EMPSQLUtil.closeAllResources(null, stmt, conn);
			} catch(Exception ce) {
				ce.printStackTrace();
			}
		}
		return RO;
	}
	
	/**
	 * 插入新的记录
	 */
	public JResponseObject insertMDLRow(JParamObject PO, String tableName, EFRowSet rowset) {
		JConnection        conn = null;
		JStatement         stmt = null;
		String         codeRule = "";
		String       DCT_BMSTRU = "";
		String            newBH = "";
		String           F_ZDBM = "";
		int               bmLen = 0;
		EFRowSet     dictRowSet = null;
		EFRowSet    bizMetaData = null;
		JResponseObject      RO = new JResponseObject();
		EMPCodeRuler  codeRuler = new EMPCodeRuler();
		try {
			conn = JConnection.getInstance(jdbcTemplate.getDataSource().getConnection());
			conn.setAutoCommit(false);
			stmt = conn.createStatement();
			
			//获取编码方式
			codeRule = getCodeRule(tableName);
			//获取字典元数据
			DCT_BMSTRU = getDctBMSTRU(tableName);
			if(DCT_BMSTRU == null || DCT_BMSTRU.trim().equals("")) {
				DCT_BMSTRU = "6";
			}
			dictRowSet = metaDataServiceMgr.getDictRow(tableName);
			F_ZDBM = dictRowSet.getString("DCT_BMCOLID", "");
			bmLen = StringFunction.getStrutLength(DCT_BMSTRU);
			PO.SetValueByParamName("DCT_BMSTRU_LENGTH", "" + bmLen);
			PO.SetValueByParamName("CODERULE", codeRule);
			PO.SetValueByParamName("OBJ_ID", tableName);
			newBH = codeRuler.getNextModelBHFromDB(conn, PO);
			rowset.put(F_ZDBM, newBH);
			
			bizMetaData = BizFormUtil.getBIZMetaData(stmt, tableName);
			
			try {
				BizFormUtil.saveRowSetData(stmt, tableName, rowset, bizMetaData.getDataSet(SYS_OBJCOLS._SYS_OBJCOLS_));
				conn.commit();
			} catch(Exception ce) {
				RO.setErrorCode(-1);
				RO.setErrorString(ce.getMessage());
				return RO;
			}
			RO.setErrorCode(1);
			RO.setResponseObject("数据添加成功！");
			return RO;
		} catch (Exception e) {
			e.printStackTrace();
			conn.rollback();
		} finally {
			try {
				EMPSQLUtil.closeAllResources(null, stmt, conn);
			} catch(Exception ce) {
				ce.printStackTrace();
			}
		}
		return RO;
	}

	
	public EFRowSet getRow(String tableName, String F_ZDBH) {
		JConnection      conn = null;
		JStatement       stmt = null;
		ResultSet          rs = null;
		String         strSql = " select ";
		String    strLeftJoin = "";
		String         F_ZDBM = "";
		EFDataSet dictDataSet = EFDataSet.getInstance(tableName);
		EFRowSet   dictRowSet = EFRowSet.getInstance();
		EFDataSet   objColsDS = metaDataServiceMgr.getSYS_OBJCOLS(tableName);
		EFRowSet     objColRS = null;
		EFRowSet      fkeyObj = null;
		
		try {
			conn = JConnection.getInstance(jdbcTemplate.getDataSource().getConnection());
			stmt = conn.createStatement();
			dictRowSet = metaDataServiceMgr.getDictRow(tableName);
			F_ZDBM = dictRowSet.getString("DCT_BMCOLID", "");
			
			for(int i = 0; i < objColsDS.getRowCount(); i++) {
				objColRS = objColsDS.getRowSet(i);
				if(objColRS.getString("COL_ISFKEY", "1").equals("1")) {
					fkeyObj = metaDataServiceMgr.getDictRow(objColRS.getString("COL_FOBJ", ""));
					if(fkeyObj == null) continue;
					strLeftJoin += " LEFT JOIN " + objColRS.getString("COL_FOBJ", "") + " ON " + objColRS.getString("COL_FOBJ", "") 
					             + "." + fkeyObj.getString("DCT_BMCOLID", "")  + " = " + tableName + "." + objColRS.getString("COL_ID", "");
					strSql += tableName + "." + objColRS.getString("COL_ID", "") + " as " + objColRS.getString("COL_ID", "") + ","
					        + objColRS.getString("COL_FOBJ", "") + "." + fkeyObj.getString("DCT_MCCOLID", "") + ",";
				} else {
					strSql += tableName + "." + objColRS.getString("COL_ID", "") + " as " + objColRS.getString("COL_ID", "") + ",";
				}
			}
			strSql = strSql.substring(0, strSql.lastIndexOf(","));
			
			strSql += " from " + tableName + " " + strLeftJoin + " where " + F_ZDBM + " = '" + F_ZDBH + "'";
			rs = stmt.executeQuery(strSql);
			dictDataSet = DataSetUtils.resultSet2DataSet(rs, dictDataSet);
			if(dictDataSet != null && dictDataSet.getRowCount() > 0) {
				dictRowSet = dictDataSet.getRowSet(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				EMPSQLUtil.closeAllResources(rs, stmt, conn);		
			} catch(Exception ce) {
				ce.printStackTrace();
			}
		}
		return dictRowSet;
	}

	public EFDataSet getRows(String tableName, String F_ZDBM, String F_ZDBH) {
		JConnection      conn = null;
		JStatement       stmt = null;
		ResultSet          rs = null;
		String         strSql = " select ";
		String    strLeftJoin = "";
		EFDataSet dictDataSet = EFDataSet.getInstance(tableName);
		EFDataSet   objColsDS = metaDataServiceMgr.getSYS_OBJCOLS(tableName);
		EFRowSet     objColRS = null;
		EFRowSet      fkeyObj = null;
		
		try {
			conn = JConnection.getInstance(jdbcTemplate.getDataSource().getConnection());
			stmt = conn.createStatement();

			for(int i = 0; i < objColsDS.getRowCount(); i++) {
				objColRS = objColsDS.getRowSet(i);
				if(objColRS.getString("COL_ISFKEY", "1").equals("1")) {
					fkeyObj = metaDataServiceMgr.getDictRow(objColRS.getString("COL_FOBJ", ""));
					if(fkeyObj == null) continue;
					strLeftJoin += " LEFT JOIN " + objColRS.getString("COL_FOBJ", "") + " ON " + objColRS.getString("COL_FOBJ", "") 
					             + "." + fkeyObj.getString("DCT_BMCOLID", "")  + " = " + tableName + "." + objColRS.getString("COL_ID", "");
					strSql += tableName + "." + objColRS.getString("COL_ID", "") + " as " + objColRS.getString("COL_ID", "") + ","
					        + objColRS.getString("COL_FOBJ", "") + "." + fkeyObj.getString("DCT_MCCOLID", "") + " as " 
					        + fkeyObj.getString("DCT_MCCOLID", "") + ",";
				} else {
					strSql += tableName + "." + objColRS.getString("COL_ID", "") + " as " + objColRS.getString("COL_ID", "") + ",";
				}
			}
			strSql = strSql.substring(0, strSql.lastIndexOf(","));
			
			strSql += " from " + tableName + " " + strLeftJoin + " where " + F_ZDBM + " = '" + F_ZDBH + "'";
			rs = stmt.executeQuery(strSql);
			dictDataSet = DataSetUtils.resultSet2DataSet(rs, dictDataSet);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				EMPSQLUtil.closeAllResources(rs, stmt, conn);
			} catch(Exception ce) {
				ce.printStackTrace();
			}
		}
		return dictDataSet;		
	}
	
	public EFRowSet getRow(String tableName, String F_ZDBM, String F_ZDBH) {
		JConnection      conn = null;
		JStatement       stmt = null;
		ResultSet          rs = null;
		String         strSql = " select ";
		String    strLeftJoin = "";
		EFDataSet dictDataSet = EFDataSet.getInstance(tableName);
		EFRowSet   dictRowSet = EFRowSet.getInstance();
		EFDataSet   objColsDS = metaDataServiceMgr.getSYS_OBJCOLS(tableName);
		EFRowSet     objColRS = null;
		EFRowSet      fkeyObj = null;
		
		try {
			conn = JConnection.getInstance(jdbcTemplate.getDataSource().getConnection());
			stmt = conn.createStatement();
			dictRowSet = metaDataServiceMgr.getDictRow(tableName);

			for(int i = 0; i < objColsDS.getRowCount(); i++) {
				objColRS = objColsDS.getRowSet(i);
				if(objColRS.getString("COL_ISFKEY", "1").equals("1")) {
					fkeyObj = metaDataServiceMgr.getDictRow(objColRS.getString("COL_FOBJ", ""));
					if(fkeyObj == null) continue;
					strLeftJoin += " LEFT JOIN " + objColRS.getString("COL_FOBJ", "") + " ON " + objColRS.getString("COL_FOBJ", "") 
					             + "." + fkeyObj.getString("DCT_BMCOLID", "")  + " = " + tableName + "." + objColRS.getString("COL_ID", "");
					strSql += tableName + "." + objColRS.getString("COL_ID", "") + " as " + objColRS.getString("COL_ID", "") + ","
					        + objColRS.getString("COL_FOBJ", "") + "." + fkeyObj.getString("DCT_MCCOLID", "") + " as " 
					        + fkeyObj.getString("DCT_MCCOLID", "") + ",";
				} else {
					strSql += tableName + "." + objColRS.getString("COL_ID", "") + " as " + objColRS.getString("COL_ID", "") + ",";
				}
			}
			strSql = strSql.substring(0, strSql.lastIndexOf(","));
			
			strSql += " from " + tableName + " " + strLeftJoin + " where " + F_ZDBM + " = '" + F_ZDBH + "'";
			rs = stmt.executeQuery(strSql);
			dictDataSet = DataSetUtils.resultSet2DataSet(rs, dictDataSet);
			if(dictDataSet != null && dictDataSet.getRowCount() > 0) {
				dictRowSet = dictDataSet.getRowSet(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				EMPSQLUtil.closeAllResources(rs, stmt, conn);
			} catch(Exception ce) {
				ce.printStackTrace();
			}
		}
		return dictRowSet;
	}
	
	/**
	 * 更新数据
	 */
	public JResponseObject updateRow(String tableName, EFRowSet rowset) {
		JConnection      conn = null;
		JStatement       stmt = null;
		EFRowSet  bizMetaData = null;
		JResponseObject    RO = new JResponseObject();
		
		try {
			conn = JConnection.getInstance(jdbcTemplate.getDataSource().getConnection());
			conn.setAutoCommit(false);
			stmt = conn.createStatement();

			bizMetaData = BizFormUtil.getBIZMetaData(stmt, tableName);
			
			try {
				BizFormUtil.updateRowSetData(stmt, tableName, rowset, bizMetaData.getDataSet(SYS_OBJCOLS._SYS_OBJCOLS_));
				conn.commit();
			} catch(Exception ce) {
				RO.setErrorCode(-1);
				RO.setErrorString(ce.getMessage());
				return RO;
			}
			RO.setErrorCode(1);
			RO.setResponseObject("数据修改成功！");
			return RO;
		} catch (Exception e) {
			e.printStackTrace();
			conn.rollback();
		} finally {
			try {
				EMPSQLUtil.closeAllResources(null, stmt, conn);
			} catch(Exception ce) {
				ce.printStackTrace();
			}
		}
		return RO;	
	}

	/**
	 * 删除字典数据
	 */
	public JResponseObject deleteRow(String tableName, String keyVal) {
		JConnection      conn = null;
		JStatement       stmt = null;
		EFRowSet  bizMetaData = null;
		JResponseObject    RO = new JResponseObject();
		
		try {
			conn = JConnection.getInstance(jdbcTemplate.getDataSource().getConnection());
			conn.setAutoCommit(false);
			stmt = conn.createStatement();

			bizMetaData = BizFormUtil.getBIZMetaData(stmt, tableName);
			
			try {
				BizFormUtil.deleteRowSetData(stmt, tableName, keyVal, bizMetaData.getDataSet(SYS_OBJCOLS._SYS_OBJCOLS_));
				conn.commit();
			} catch(Exception ce) {
				RO.setErrorCode(-1);
				RO.setErrorString(ce.getMessage());
				return RO;
			}
			RO.setErrorCode(1);
			RO.setResponseObject("数据删除成功！");
			return RO;
		} catch (Exception e) {
			e.printStackTrace();
			conn.rollback();
		} finally {
			try {
				EMPSQLUtil.closeAllResources(null, stmt, conn);
			} catch(Exception ce) {
				ce.printStackTrace();
			}
		}
		return RO;	
	}
}
