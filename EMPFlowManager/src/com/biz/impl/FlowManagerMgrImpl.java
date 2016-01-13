package com.biz.impl;

import java.sql.ResultSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.biz.FlowManagerServiceMgr;
import com.common.util.EMPSQLUtil;
import com.efounder.builder.base.data.DataSetUtils;
import com.efounder.builder.base.data.EFDataSet;
import com.efounder.builder.base.data.EFRowSet;
import com.efounder.eai.data.JParamObject;
import com.efounder.sql.JConnection;
import com.efounder.sql.JStatement;
import com.framework.sys.business.AbstractBusinessObjectServiceMgr;
import com.persistence.FLOW_TASK_LIST;

@Transactional(rollbackFor = Exception.class)
@Service("FlowManagerServiceServiceMgr")
public class FlowManagerMgrImpl extends AbstractBusinessObjectServiceMgr implements FlowManagerServiceMgr {
	
	@Autowired
	private JdbcTemplate                    jdbcTemplate;
	
	public EFDataSet loadFlowNode(JParamObject PO) {
		JConnection      conn = null;
		JStatement       stmt = null;
		ResultSet          rs = null;
		String         strSql = " select ";
		String    strLeftJoin = "";
		String     BILL_ORDER = PO.GetValueByParamName("BILL_ORDER", null);
		EFDataSet dictDataSet = EFDataSet.getInstance(PO.GetValueByParamName("TABLE_NAME", ""));
		EFDataSet   objColsDS = null;
		EFRowSet     objColRS = null;
		EFRowSet      fkeyObj = null;
		
		try {
			conn = JConnection.getInstance(jdbcTemplate.getDataSource().getConnection());
			stmt = conn.createStatement();

			strSql = " select * from SYS_OBJCOLS" + " where OBJ_ID = '" + PO.GetValueByParamName("TABLE_NAME", "") + "' and F_STAU = '1'";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(strSql);
			objColsDS = DataSetUtils.resultSet2DataSet(rs, objColsDS);
			
			strSql = " select ";
			for(int i = 0; i < objColsDS.getRowCount(); i++) {
				objColRS = objColsDS.getRowSet(i);
				if(objColRS.getString("COL_ISFKEY", "0").equals("1")) {
					fkeyObj = getDictRow(stmt, objColRS.getString("COL_FOBJ", ""));
					if(fkeyObj == null) continue;
					strLeftJoin += " LEFT JOIN " + objColRS.getString("COL_FOBJ", "") + " ON " + objColRS.getString("COL_FOBJ", "") 
					             + "." + fkeyObj.getString("DCT_BMCOLID", "")  + " = " + PO.GetValueByParamName("TABLE_NAME", "") + "." + objColRS.getString("COL_ID", "");
					strSql += PO.GetValueByParamName("TABLE_NAME", "") + "." + objColRS.getString("COL_ID", "") + " as " + objColRS.getString("COL_ID", "") + ","
					        + objColRS.getString("COL_FOBJ", "") + "." + fkeyObj.getString("DCT_MCCOLID", "") + " as " 
					        + fkeyObj.getString("DCT_MCCOLID", "") + ",";
				} else {
					strSql += PO.GetValueByParamName("TABLE_NAME", "") + "." + objColRS.getString("COL_ID", "") + " as " + objColRS.getString("COL_ID", "") + ",";
				}
			}
			strSql = strSql.substring(0, strSql.lastIndexOf(","));
			
			strSql += " from " + PO.GetValueByParamName("TABLE_NAME", "") + " " + strLeftJoin + " where "
			        + " exists(SELECT 1 FROM FLOW_USER_LIST where FLOW_USER_LIST.FLOW_BH = FLOW_NODE_LIST.FLOW_ID and ((FLOW_USER_LIST.FLOW_BM = '"
			        + PO.GetValueByParamName("FLOW_BM", "")
			        + "' and FLOW_USER_LIST.F_TYPE = 'BM')or (FLOW_USER_LIST.FLOW_USER = '"
			        + PO.GetValueByParamName("FLOW_USER", "")
			        + "' and FLOW_USER_LIST.FLOW_BM = '" + PO.GetValueByParamName("FLOW_BM", "") + "' and FLOW_USER_LIST.F_TYPE = 'USER'))"
			        + " and FLOW_USER_LIST.NODE_BH = FLOW_NODE_LIST.NODE_ID)";
			if(PO.GetValueByParamName("NODE_TYPE", "").trim().length() > 0) {
				strSql += " and FLOW_NODE_LIST.NODE_TYPE = '" + PO.GetValueByParamName("NODE_TYPE", "") + "'";
			}
			if(PO.GetValueByParamName("NODE_ID", "").trim().length() > 0) {
				strSql += " and FLOW_NODE_LIST.NODE_ID = '" + PO.GetValueByParamName("NODE_ID", "") + "'";
			}
			if(PO.GetValueByParamName("NODE_NEXT", "").trim().length() > 0) {
				strSql += " and FLOW_NODE_LIST.NODE_NEXT = '" + PO.GetValueByParamName("NODE_ID", "") + "'";
			}
			strSql += " and FLOW_NODE_LIST.FLOW_ID = '" + PO.GetValueByParamName(FLOW_TASK_LIST._FLOW_ID, "") + "'";
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
	 * 获取字典元数据
	 * @param conn
	 * @param tableName
	 * @return
	 */
	public EFRowSet getDictRow(JStatement stmt, String tableName) {
		JConnection    conn = null;
		ResultSet        rs = null;
		EFDataSet    dictDS = EFDataSet.getInstance("SYS_DICTS");
		EFRowSet     dictRS = null;
		String       strSql = " select * from SYS_DICTS where DCT_ID = '" + tableName + "'";
		
		try {
			conn = JConnection.getInstance(jdbcTemplate.getDataSource().getConnection());
			stmt = conn.createStatement();
			rs = stmt.executeQuery(strSql);
			dictDS = DataSetUtils.resultSet2DataSet(rs, dictDS);
			if(dictDS != null && dictDS.getRowCount() > 0) {
				dictRS = dictDS.getRowSet(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dictRS;
	}

	public EFDataSet loadFlow(JParamObject PO) {
		JConnection      conn = null;
		JStatement       stmt = null;
		ResultSet          rs = null;
		String         strSql = " select ";
		String    strLeftJoin = "";
		String   TASK_TO_UNIT = "";
		String     BILL_ORDER = PO.GetValueByParamName("BILL_ORDER", null);
		String     BILL_WHERE = PO.GetValueByParamName("BILL_WHERE", null);
		EFDataSet flowDataSet = EFDataSet.getInstance(PO.GetValueByParamName("TABLE_NAME", ""));
		EFRowSet   flowRowSet = null;
		EFDataSet   objColsDS = null;
		EFRowSet     objColRS = null;
		EFRowSet      fkeyObj = null;
		
		try {
			conn = JConnection.getInstance(jdbcTemplate.getDataSource().getConnection());
			stmt = conn.createStatement();

			strSql = " select * from SYS_OBJCOLS" + " where OBJ_ID = '" + PO.GetValueByParamName("TABLE_NAME", "") + "' and F_STAU = '1'";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(strSql);
			objColsDS = DataSetUtils.resultSet2DataSet(rs, objColsDS);
			
			strSql = " select ";
			for(int i = 0; i < objColsDS.getRowCount(); i++) {
				objColRS = objColsDS.getRowSet(i);
				if(objColRS.getString("COL_ISFKEY", "0").equals("1")) {
					fkeyObj = getDictRow(stmt, objColRS.getString("COL_FOBJ", ""));
					if(fkeyObj == null) continue;
					strLeftJoin += " LEFT JOIN " + objColRS.getString("COL_FOBJ", "") + " ON " + objColRS.getString("COL_FOBJ", "") 
					             + "." + fkeyObj.getString("DCT_BMCOLID", "")  + " = " + PO.GetValueByParamName("TABLE_NAME", "") + "." + objColRS.getString("COL_ID", "");
					strSql += PO.GetValueByParamName("TABLE_NAME", "") + "." + objColRS.getString("COL_ID", "") + " as " + objColRS.getString("COL_ID", "") + ","
					        + objColRS.getString("COL_FOBJ", "") + "." + fkeyObj.getString("DCT_MCCOLID", "") + " as " 
					        + fkeyObj.getString("DCT_MCCOLID", "") + ",";
				} else {
					strSql += PO.GetValueByParamName("TABLE_NAME", "") + "." + objColRS.getString("COL_ID", "") + " as " + objColRS.getString("COL_ID", "") + ",";
				}
			}
			strSql = strSql.substring(0, strSql.lastIndexOf(","));
			
			strSql += " from " + PO.GetValueByParamName("TABLE_NAME", "") + " " + strLeftJoin + " where "
			        + " exists(SELECT 1 FROM FLOW_USER_LIST where FLOW_USER_LIST.FLOW_BH = " + PO.GetValueByParamName("TABLE_NAME", "") 
			        + ".FLOW_ID and ((FLOW_USER_LIST.FLOW_BM = '"
			        + PO.GetValueByParamName("FLOW_BM", "")
			        + "' and FLOW_USER_LIST.F_TYPE = 'BM')or (FLOW_USER_LIST.FLOW_USER = '"
			        + PO.GetValueByParamName("FLOW_USER", "")
			        + "' and FLOW_USER_LIST.FLOW_BM = '" + PO.GetValueByParamName("FLOW_BM", "") + "' and FLOW_USER_LIST.F_TYPE = 'USER'))"
			        + " and FLOW_USER_LIST.NODE_BH = " + PO.GetValueByParamName("TABLE_NAME", "") + ".NODE_TAG"
			        + " and (FLOW_USER_LIST.FLOW_BM = FLOW_TASK_LIST.TASK_TO_UNIT or FLOW_TASK_LIST.TASK_TO_UNIT = '' or FLOW_TASK_LIST.TASK_TO_UNIT = ' ' or FLOW_TASK_LIST.TASK_TO_UNIT is null)) "
			        + " and FLOW_TASK_LIST.FLOW_ID = '" + PO.GetValueByParamName("FLOW_ID", "") + "'";
			if(PO.GetValueByParamName("NODE_TYPE", "").trim().length() > 0) {
				strSql += " and " + PO.GetValueByParamName("TABLE_NAME", "") +".NODE_TYPE = '" + PO.GetValueByParamName("NODE_TYPE", "") + "'";
			}
			if(PO.GetValueByParamName("NODE_TAG", "").trim().length() > 0) {
				strSql += " and " + PO.GetValueByParamName("TABLE_NAME", "") +".NODE_TAG = '" + PO.GetValueByParamName("NODE_TAG", "") + "'";
			}
			
			if(PO.GetValueByParamName("RESR_STATUS", "").trim().length() > 0) {
				strSql += " and " + PO.GetValueByParamName("TABLE_NAME", "") +".RESR_STATUS = '" + PO.GetValueByParamName("RESR_STATUS", "") + "'";
			}
//			if(PO.GetValueByParamName("TASK_TO_UNIT", "").trim().length() > 0) {
//				if(PO.GetValueByParamName("TASK_TO_USER", "").trim().length() > 0) {
//					strSql += " and ((" + PO.GetValueByParamName("TABLE_NAME", "") +".TASK_TO_UNIT = '" + PO.GetValueByParamName("TASK_TO_UNIT", "") + "'"
//			                + " and " + PO.GetValueByParamName("TABLE_NAME", "") +".TASK_TO_USER = '" + PO.GetValueByParamName("TASK_TO_USER", "") + "')"
//			                + " or (" + PO.GetValueByParamName("TABLE_NAME", "") +".TASK_TO_UNIT = '" + PO.GetValueByParamName("TASK_TO_UNIT", "") + "'"
//			                + " and (" + PO.GetValueByParamName("TABLE_NAME", "") + ".TASK_TO_USER = '' or " + PO.GetValueByParamName("TABLE_NAME", "") 
//			                + ".TASK_TO_USER = ' ' or " + PO.GetValueByParamName("TABLE_NAME", "") + ".TASK_TO_USER is null)))";
//				} else {
//					strSql += " and (" + PO.GetValueByParamName("TABLE_NAME", "") +".TASK_TO_UNIT = '" + PO.GetValueByParamName("TASK_TO_UNIT", "") + "'"
//	                	    + " and (" + PO.GetValueByParamName("TABLE_NAME", "") + ".TASK_TO_USER = '' or " + PO.GetValueByParamName("TABLE_NAME", "") 
//	                	    + ".TASK_TO_USER = ' ' or " + PO.GetValueByParamName("TABLE_NAME", "") + ".TASK_TO_USER is null))";
//				}
//			}
			if(BILL_WHERE != null && BILL_WHERE.trim().length() > 0) {
				strSql += BILL_WHERE;
			}
			
			if(BILL_ORDER != null && BILL_ORDER.trim().length() > 0) {
				strSql += " order by " + BILL_ORDER;
			}
			
			rs = stmt.executeQuery(strSql);
			flowDataSet = DataSetUtils.resultSet2DataSet(rs, flowDataSet);
			
			for(int i = flowDataSet.getRowCount() - 1; i >= 0; i--) {
				flowRowSet = flowDataSet.getRowSet(i);
				TASK_TO_UNIT = flowRowSet.getString("TASK_TO_UNIT", "");
				if(!TASK_TO_UNIT.trim().equals("") && !PO.GetValueByParamName("FLOW_BM", "").equals(TASK_TO_UNIT)) {
					flowDataSet.removeRowSet(i);
				}
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
		return flowDataSet;	
	}

	public EFDataSet loadFlow1(JParamObject PO) {
		JConnection      conn = null;
		JStatement       stmt = null;
		ResultSet          rs = null;
		String         strSql = " select ";
		String    strLeftJoin = "";
		String   TASK_TO_UNIT = "";
		String     BILL_ORDER = PO.GetValueByParamName("BILL_ORDER", null);
		String     BILL_WHERE = PO.GetValueByParamName("BILL_WHERE", null);
		EFDataSet flowDataSet = EFDataSet.getInstance(PO.GetValueByParamName("TABLE_NAME", ""));
		EFRowSet   flowRowSet = null;
		EFDataSet   objColsDS = null;
		EFRowSet     objColRS = null;
		EFRowSet      fkeyObj = null;
		
		try {
			conn = JConnection.getInstance(jdbcTemplate.getDataSource().getConnection());
			stmt = conn.createStatement();

			strSql = " select * from SYS_OBJCOLS" + " where OBJ_ID = '" + PO.GetValueByParamName("TABLE_NAME", "") + "' and F_STAU = '1'";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(strSql);
			objColsDS = DataSetUtils.resultSet2DataSet(rs, objColsDS);
			
			strSql = " select ";
			for(int i = 0; i < objColsDS.getRowCount(); i++) {
				objColRS = objColsDS.getRowSet(i);
				if(objColRS.getString("COL_ISFKEY", "0").equals("1")) {
					fkeyObj = getDictRow(stmt, objColRS.getString("COL_FOBJ", ""));
					if(fkeyObj == null) continue;
					strLeftJoin += " LEFT JOIN " + objColRS.getString("COL_FOBJ", "") + " ON " + objColRS.getString("COL_FOBJ", "") 
					             + "." + fkeyObj.getString("DCT_BMCOLID", "")  + " = " + PO.GetValueByParamName("TABLE_NAME", "") + "." + objColRS.getString("COL_ID", "");
					strSql += PO.GetValueByParamName("TABLE_NAME", "") + "." + objColRS.getString("COL_ID", "") + " as " + objColRS.getString("COL_ID", "") + ","
					        + objColRS.getString("COL_FOBJ", "") + "." + fkeyObj.getString("DCT_MCCOLID", "") + " as " 
					        + fkeyObj.getString("DCT_MCCOLID", "") + ",";
				} else {
					strSql += PO.GetValueByParamName("TABLE_NAME", "") + "." + objColRS.getString("COL_ID", "") + " as " + objColRS.getString("COL_ID", "") + ",";
				}
			}
			strSql = strSql.substring(0, strSql.lastIndexOf(","));
			
			strSql += " from " + PO.GetValueByParamName("TABLE_NAME", "") + " " + strLeftJoin + " where "
			        + " exists(SELECT 1 FROM FLOW_USER_LIST where FLOW_USER_LIST.FLOW_BH = " + PO.GetValueByParamName("TABLE_NAME", "") 
			        + ".FLOW_ID and ((FLOW_USER_LIST.FLOW_BM = '"
			        + PO.GetValueByParamName("FLOW_BM", "")
			        + "' and FLOW_USER_LIST.F_TYPE = 'BM')or (FLOW_USER_LIST.FLOW_USER = '"
			        + PO.GetValueByParamName("FLOW_USER", "")
			        + "' and FLOW_USER_LIST.FLOW_BM = '" + PO.GetValueByParamName("FLOW_BM", "") + "' and FLOW_USER_LIST.F_TYPE = 'USER'))"
			        + " and (FLOW_USER_LIST.NODE_BH = " + PO.GetValueByParamName("TABLE_NAME", "") + ".NODE_TAG or FLOW_USER_LIST.NODE_BH = " + PO.GetValueByParamName("TABLE_NAME", "") + ".NODE_SRC)"
			        + " and (FLOW_USER_LIST.FLOW_BM = FLOW_TASK_LIST.TASK_TO_UNIT or FLOW_TASK_LIST.TASK_TO_UNIT = '' or FLOW_TASK_LIST.TASK_TO_UNIT = ' ' or FLOW_TASK_LIST.TASK_TO_UNIT is null)) "
			        + " and FLOW_TASK_LIST.FLOW_ID = '" + PO.GetValueByParamName("FLOW_ID", "") + "'";
			if(PO.GetValueByParamName("NODE_TYPE", "").trim().length() > 0) {
				strSql += " and " + PO.GetValueByParamName("TABLE_NAME", "") +".NODE_TYPE = '" + PO.GetValueByParamName("NODE_TYPE", "") + "'";
			}
			if(PO.GetValueByParamName("NODE_TAG", "").trim().length() > 0) {
				strSql += " and " + PO.GetValueByParamName("TABLE_NAME", "") +".NODE_TAG = '" + PO.GetValueByParamName("NODE_TAG", "") + "'";
			}
			
			if(PO.GetValueByParamName("RESR_STATUS", "").trim().length() > 0) {
				strSql += " and " + PO.GetValueByParamName("TABLE_NAME", "") +".RESR_STATUS = '" + PO.GetValueByParamName("RESR_STATUS", "") + "'";
			}
			if(BILL_WHERE != null && BILL_WHERE.trim().length() > 0) {
				strSql += BILL_WHERE;
			}
			
			if(BILL_ORDER != null && BILL_ORDER.trim().length() > 0) {
				strSql += " order by " + BILL_ORDER;
			}
			
			rs = stmt.executeQuery(strSql);
			flowDataSet = DataSetUtils.resultSet2DataSet(rs, flowDataSet);
			
			for(int i = flowDataSet.getRowCount() - 1; i >= 0; i--) {
				flowRowSet = flowDataSet.getRowSet(i);
				TASK_TO_UNIT = flowRowSet.getString("TASK_TO_UNIT", "");
				if(!TASK_TO_UNIT.trim().equals("") && !PO.GetValueByParamName("FLOW_BM", "").equals(TASK_TO_UNIT)) {
					flowDataSet.removeRowSet(i);
				}
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
		return flowDataSet;	
	}
	
	public EFDataSet loadFlowPurview(JParamObject PO) {
		JConnection             conn = null;
		JStatement              stmt = null;
		ResultSet                 rs = null;
		String                strSql = "";
		EFDataSet flowPurviewDataSet = EFDataSet.getInstance();
		try {
			conn = JConnection.getInstance(jdbcTemplate.getDataSource().getConnection());
			stmt = conn.createStatement();
			strSql = " select FLOW_USER_LIST.FLOW_BH as FLOW_BH,FLOW_STATUS_LIST.FLOW_MC as FLOW_MC,"
				   + " FLOW_USER_LIST.FLOW_BM as FLOW_BM,HYBMZD.F_BMMC as F_BMMC,FLOW_USER_LIST.FLOW_USER as FLOW_USER,BSUSER.USER_NAME as USER_NAME,"
				   + " FLOW_USER_LIST.F_TYPE as F_TYPE,FLOW_USER_LIST.NODE_BH as NODE_BH,FLOW_NODE_LIST.NODE_NAME as NODE_NAME"
				   + " from FLOW_USER_LIST "
				   + " LEFT JOIN FLOW_STATUS_LIST ON FLOW_STATUS_LIST.FLOW_BH = FLOW_USER_LIST.FLOW_BH"
				   + " LEFT JOIN HYBMZD ON HYBMZD.F_BMBH = FLOW_USER_LIST.FLOW_BM"
				   + " LEFT JOIN BSUSER ON BSUSER.USER_ID = FLOW_USER_LIST.FLOW_USER "
				   + " LEFT JOIN FLOW_NODE_LIST ON FLOW_NODE_LIST.NODE_ID = FLOW_USER_LIST.NODE_BH and FLOW_NODE_LIST.FLOW_ID = FLOW_USER_LIST.FLOW_BH"
				   + " where  FLOW_USER_LIST.FLOW_BH = '" + PO.GetValueByParamName("FLOW_ID", "") + "' and FLOW_USER_LIST.NODE_BH = '" 
				   + PO.GetValueByParamName("NODE_BH", "") + "' order by  FLOW_BM,FLOW_USER";
			rs = stmt.executeQuery(strSql);
			flowPurviewDataSet = DataSetUtils.resultSet2DataSet(rs, flowPurviewDataSet);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				EMPSQLUtil.closeAllResources(rs, stmt, conn);
			} catch(Exception ce) {
				ce.printStackTrace();
			}
		}
		return flowPurviewDataSet;	
	}

	@Override
	public EFDataSet loadAllFlowTask(JParamObject PO) {
		JConnection      conn = null;
		JStatement       stmt = null;
		ResultSet          rs = null;
		String         strSql = " select ";
		String    strLeftJoin = "";
		EFDataSet dictDataSet = EFDataSet.getInstance("FLOW_TASK_LIST");
		EFDataSet   objColsDS = null;
		EFRowSet     objColRS = null;
		EFRowSet      fkeyObj = null;
		String      tableName = "FLOW_TASK_LIST";
		String       BIZ_DATE = PO.GetValueByParamName(FLOW_TASK_LIST._BIZ_DATE, "");
		String       BIZ_DJBH = PO.GetValueByParamName(FLOW_TASK_LIST._BIZ_DJBH, "");
		String       BIZ_GUID = PO.GetValueByParamName(FLOW_TASK_LIST._BIZ_GUID, "");
		String        FLOW_ID = PO.GetValueByParamName("FLOW_ID", "");
		try {
			conn = JConnection.getInstance(jdbcTemplate.getDataSource().getConnection());
			stmt = conn.createStatement();

			strSql = " select * from FLOW_TASK_LIST where FLOW_ID = '" + FLOW_ID + "' and BIZ_DATE = '" + BIZ_DATE + "' and "
	               + " BIZ_DJBH = '" + BIZ_DJBH + "' and BIZ_GUID = '" + BIZ_GUID + "'";
			rs = stmt.executeQuery(strSql);
			if(rs != null && rs.next()) {
				tableName = "FLOW_TASK_LIST";
			} else {
				tableName = "FLOW_TASK_END_LIST";
			}
			strSql = " select * from SYS_OBJCOLS" + " where OBJ_ID = '" + tableName + "' and F_STAU = '1'";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(strSql);
			objColsDS = DataSetUtils.resultSet2DataSet(rs, objColsDS);
			
			strSql = " select ";
			for(int i = 0; i < objColsDS.getRowCount(); i++) {
				objColRS = objColsDS.getRowSet(i);
				if(objColRS.getString("COL_ISFKEY", "0").equals("1")) {
					fkeyObj = getDictRow(stmt, objColRS.getString("COL_FOBJ", ""));
					if(fkeyObj == null) continue;
					strLeftJoin += " LEFT JOIN " + objColRS.getString("COL_FOBJ", "") + " ON " + objColRS.getString("COL_FOBJ", "") 
					             + "." + fkeyObj.getString("DCT_BMCOLID", "")  + " = " + tableName + "." + objColRS.getString("COL_ID", "");
					strSql += PO.GetValueByParamName("TABLE_NAME", "") + "." + objColRS.getString("COL_ID", "") + " as " + objColRS.getString("COL_ID", "") + ","
					        + objColRS.getString("COL_FOBJ", "") + "." + fkeyObj.getString("DCT_MCCOLID", "") + " as " 
					        + fkeyObj.getString("DCT_MCCOLID", "") + ",";
				} else {
					strSql += tableName + "." + objColRS.getString("COL_ID", "") + " as " + objColRS.getString("COL_ID", "") + ",";
				}
			}			
			
			strSql += " date_format(from_unixtime(substring(OP_TIME, 1, 10)),'%Y-%m-%d %H:%i:%s') as F_CLRQ,HYBMZD.F_BMMC as OP_BMMC from " + tableName + strLeftJoin 
			        + " left join HYBMZD on HYBMZD.F_BMBH = " + tableName + ".OP_BMBH " + " where "
			        + "  FLOW_ID = '" + FLOW_ID + "' and BIZ_DATE = '" + BIZ_DATE + "' and "
			        + " BIZ_DJBH = '" + BIZ_DJBH + "' and BIZ_GUID = '" + BIZ_GUID
			        + "' and RESR_IN_CAUSE <> 'waiting' and (RESR_STATUS = 'processed' or RESR_STATUS = 'pending') order by OP_TIME";
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
	
}
