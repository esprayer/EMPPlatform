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
import com.efounder.dict.FCTServiceMgr;
import com.efounder.eai.data.JParamObject;
import com.efounder.eai.data.JResponseObject;
import com.efounder.form.server.resolver.util.BizFormUtil;
import com.efounder.sql.JConnection;
import com.efounder.sql.JStatement;
import com.framework.sys.business.AbstractBusinessObjectServiceMgr;

@Transactional(rollbackFor = Exception.class)
@Service("FCTServiceMgr")
public class FCTMgrImpl extends AbstractBusinessObjectServiceMgr implements FCTServiceMgr {
	
	@Autowired
	private JdbcTemplate                    jdbcTemplate;
	
	@Autowired
	private MetaDataServiceMgr         metaDataServiceMgr;
	
	/**
	 * 插入新的记录
	 */
	public JResponseObject insertFCTRow(String tableName, EFRowSet rowset) {
		JConnection        conn = null;
		JStatement         stmt = null;
		EFRowSet    bizMetaData = null;
		JResponseObject      RO = new JResponseObject();

		try {
			conn = JConnection.getInstance(jdbcTemplate.getDataSource().getConnection());
			conn.setAutoCommit(false);
			stmt = conn.createStatement();

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
	public JResponseObject insertFCTDataSet(String tableName, EFDataSet dataset) {
		JConnection        conn = null;
		JStatement         stmt = null;
		EFRowSet    bizMetaData = null;
		JResponseObject      RO = new JResponseObject();

		try {
			conn = JConnection.getInstance(jdbcTemplate.getDataSource().getConnection());
			conn.setAutoCommit(false);
			stmt = conn.createStatement();

			bizMetaData = BizFormUtil.getBIZMetaData(stmt, tableName);
			
			for(int i = 0; i < dataset.getRowCount(); i++) {
				try {
					BizFormUtil.saveRowSetData(stmt, tableName, dataset.getRowSet(i), bizMetaData.getDataSet(SYS_OBJCOLS._SYS_OBJCOLS_));
				} catch(Exception ce) {
					ce.printStackTrace();
					conn.rollback();
					RO.setErrorCode(-1);
					RO.setErrorString(ce.getMessage());
					return RO;
				}
			}
			conn.commit();
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
	
	public EFRowSet getFCTRow(JParamObject PO) {
		JConnection      conn = null;
		JStatement       stmt = null;
		ResultSet          rs = null;
		String         strSql = " select ";
		String    strLeftJoin = "";
		String     BILL_ORDER = PO.GetValueByParamName("BILL_ORDER", null);
		EFDataSet dictDataSet = EFDataSet.getInstance(PO.GetValueByParamName("tableName", ""));
		EFRowSet   dictRowSet = EFRowSet.getInstance();
		EFDataSet   objColsDS = metaDataServiceMgr.getSYS_OBJCOLS(PO.GetValueByParamName("tableName", ""));
		EFRowSet     objColRS = null;
		EFRowSet      fkeyObj = null;
		
		try {
			conn = JConnection.getInstance(jdbcTemplate.getDataSource().getConnection());
			stmt = conn.createStatement();
			dictRowSet = metaDataServiceMgr.getDictRow(PO.GetValueByParamName("tableName", ""));
			
			for(int i = 0; i < objColsDS.getRowCount(); i++) {
				objColRS = objColsDS.getRowSet(i);
				if(objColRS.getString("COL_ISFKEY", "1").equals("1")) {
					fkeyObj = metaDataServiceMgr.getDictRow(objColRS.getString("COL_FOBJ", ""));
					if(fkeyObj == null) continue;
					strLeftJoin += " LEFT JOIN " + objColRS.getString("COL_FOBJ", "") + " ON " + objColRS.getString("COL_FOBJ", "") 
					             + "." + fkeyObj.getString("DCT_BMCOLID", "")  + " = " + PO.GetValueByParamName("tableName", "") + "." + objColRS.getString("COL_ID", "");
					strSql += PO.GetValueByParamName("tableName", "") + "." + objColRS.getString("COL_ID", "") + " as " + objColRS.getString("COL_ID", "") + ","
					        + objColRS.getString("COL_FOBJ", "") + "." + fkeyObj.getString("DCT_MCCOLID", "") + ",";
				} else {
					strSql += PO.GetValueByParamName("tableName", "") + "." + objColRS.getString("COL_ID", "") + " as " + objColRS.getString("COL_ID", "") + ",";
				}
			}
			strSql = strSql.substring(0, strSql.lastIndexOf(","));
			
			strSql += " from " + PO.GetValueByParamName("tableName", "") + " " + strLeftJoin + " where " + PO.GetValueByParamName("BILL_WHERE", "");
			if(BILL_ORDER != null && BILL_ORDER.trim().length() > 0) {
				strSql += " order by " + BILL_ORDER;
			}
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

	public EFDataSet getFCTRows(JParamObject PO) {
		String loadMeataData = PO.GetValueByParamName("loadMeataData", "1");
		
		return getFCTRows(PO, loadMeataData.equals("1"));		
	}
	
	private EFDataSet getFCTRows(JParamObject PO, boolean loadMeataData) {
		JConnection      conn = null;
		JStatement       stmt = null;
		ResultSet          rs = null;
		String         strSql = " select ";
		String    strLeftJoin = "";
		String     BILL_ORDER = PO.GetValueByParamName("BILL_ORDER", null);
		EFDataSet dictDataSet = EFDataSet.getInstance(PO.GetValueByParamName("tableName", ""));
		EFDataSet   objColsDS = metaDataServiceMgr.getSYS_OBJCOLS(PO.GetValueByParamName("tableName", ""));
		EFRowSet     objColRS = null;
		EFRowSet      fkeyObj = null;
		
		try {
			conn = JConnection.getInstance(jdbcTemplate.getDataSource().getConnection());
			stmt = conn.createStatement();
			
			if(loadMeataData) {
				for(int i = 0; i < objColsDS.getRowCount(); i++) {
					objColRS = objColsDS.getRowSet(i);
					if(objColRS.getString("COL_ISFKEY", "1").equals("1")) {
						fkeyObj = metaDataServiceMgr.getDictRow(objColRS.getString("COL_FOBJ", ""));
						if(fkeyObj == null) continue;
						strLeftJoin += " LEFT JOIN " + objColRS.getString("COL_FOBJ", "") + " ON " + objColRS.getString("COL_FOBJ", "") 
						             + "." + fkeyObj.getString("DCT_BMCOLID", "")  + " = " + PO.GetValueByParamName("tableName", "") + "." + objColRS.getString("COL_ID", "");
						strSql += PO.GetValueByParamName("tableName", "") + "." + objColRS.getString("COL_ID", "") + " as " + objColRS.getString("COL_ID", "") + ","
						        + objColRS.getString("COL_FOBJ", "") + "." + fkeyObj.getString("DCT_MCCOLID", "") + " as " 
						        + fkeyObj.getString("DCT_MCCOLID", "") + ",";
					} else {
						strSql += PO.GetValueByParamName("tableName", "") + "." + objColRS.getString("COL_ID", "") + " as " + objColRS.getString("COL_ID", "") + ",";
					}
				}
				strSql = strSql.substring(0, strSql.lastIndexOf(","));
				
				strSql += " from " + PO.GetValueByParamName("tableName", "") + " " + strLeftJoin + " where " + PO.GetValueByParamName("BILL_WHERE", "");
			} else {
				strSql += " * from " + PO.GetValueByParamName("tableName", "") + " where " + PO.GetValueByParamName("BILL_WHERE", "");
			}
			if(BILL_ORDER != null && BILL_ORDER.trim().length() > 0) {
				strSql += " order by " + BILL_ORDER;
			}
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
	
	/**
	 * 删除字典数据
	 */
	public JResponseObject deleteFCTRow(String tableName, JParamObject PO) {
		JConnection      conn = null;
		JStatement       stmt = null;
		JResponseObject    RO = new JResponseObject();
		String         strSql = "";
		
		try {
			conn = JConnection.getInstance(jdbcTemplate.getDataSource().getConnection());
			conn.setAutoCommit(false);
			stmt = conn.createStatement();

			try {
				strSql = "delete from " + tableName + " where " + PO.GetValueByParamName("BILL_WHERE", " 1 = 2 ");
				stmt.execute(strSql);
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
