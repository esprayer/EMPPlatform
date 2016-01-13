package com.efounder.bz.task.dal.frame;

import java.beans.*;
import java.io.*;
import java.sql.*;
import java.sql.Statement;
import java.util.*;

import com.common.util.EMPSQLUtil;
import com.efounder.builder.base.data.*;
import com.efounder.builder.base.util.*;
import com.efounder.builder.meta.*;
import com.efounder.builder.meta.domodel.*;
import com.efounder.bz.flow.*;
import com.efounder.bz.flow.drive.*;
import com.efounder.bz.task.dal.*;
import com.efounder.bz.task.model.*;
import com.efounder.db.*;
import com.efounder.eai.EAI;
import com.efounder.eai.data.*;
import com.efounder.eai.service.*;
import com.efounder.sql.*;
import com.persistence.FLOW_TASK_LIST;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2009</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class FlowTaskUtil {
	
	/**
	 *
	 * @param PO JParamObject
	 * @param conn JConnection
	 * @param stmt Statement
	 * @return EFDataSet
	 * @throws Exception
	 */
	public static EFDataSet loadTaskInfo(JParamObject PO, JConnection conn, JStatement stmt, EFDataSet floweds,String flowid,String nodeid) throws Exception {
		getUserInfo(PO, conn, stmt);
		EFDataSet alltaskeds = getFlowDataSet(stmt, PO);
		return alltaskeds;
	}
	
	/**
	 * 获取用户信息
	 * @throws SQLException 
	 */
	public static void getUserInfo(JParamObject PO, JConnection conn, JStatement stmt) throws SQLException {
		String strSql = "select * from bsuser where USER_ID = '" + PO.GetValueByEnvName("UserName", "") + "'";
		ResultSet                rs = null;
		
		rs = stmt.executeQuery(strSql);
		
		if(rs != null && rs.next()) {
			PO.SetValueByParamName("USER_ID", rs.getString("USER_ID"));
			PO.SetValueByParamName("USER_ORGID", rs.getString("USER_ORGID"));
		}
	}
	
	/**
	 * 获取流程数据
	 * @throws Exception
	 */
	public static EFDataSet getFlowDataSet(JStatement stmt, JParamObject PO) throws Exception {
		String              strSql = "";
		String              USER_ID = PO.GetValueByParamName("USER_ID", "");
		String           USER_ORGID = PO.GetValueByParamName("USER_ORGID", "");
		EFDataSet       flowDataSet = EFDataSet.getInstance("FLOWDataSet");
		ResultSet                rs = null;
		int           flowTaskCount = 0;
		
		//查询有权限的流程
		strSql = "select FLOW_BH as FLOW_ID, FLOW_MC, '' as OP_ID, '' as NODE_ID, '' as BIZ_GUID, '' as BIZ_DJBH, '' as BIZ_KJQJ, '' as OP_NOTE, " 
			   + "'' as OP_USER_NAME, '' as F_TJRQ from FLOW_STATUS_LIST where FLOW_STATUS = '1' and exists (select 1 from FLOW_USER_LIST where " 
			   + " FLOW_STATUS_LIST.FLOW_BH = FLOW_USER_LIST.FLOW_BH and ((FLOW_USER_LIST.FLOW_BM = '" + USER_ORGID 
			   + "' and FLOW_USER_LIST.F_TYPE = 'BM') or (FLOW_USER_LIST.FLOW_USER = '" + USER_ID 
			   + "' and FLOW_USER_LIST.FLOW_BM = '" + USER_ORGID + "' and FLOW_USER_LIST.F_TYPE = 'USER')))"
			   + " order by FLOW_GUID";
		rs = stmt.executeQuery(strSql);
		flowDataSet = DataSetUtils.resultSet2DataSet(rs, flowDataSet);
		getFlowNodeDataSet(stmt, PO, flowDataSet);
		for(int i = flowDataSet.getRowCount() - 1; i >= 0; i--) {
			flowTaskCount = flowDataSet.getRowSet(i).getNumber("FLOW_TASK_COUNT", 0.0).intValue();
			if(flowTaskCount == 0) flowDataSet.removeRowSet(i);
		}
		return flowDataSet;
	}
	
	/**
	 * 根据流程ID获取流程节点数据
	 * @throws Exception
	 */
	public static void getFlowNodeDataSet(JStatement stmt, JParamObject PO, EFDataSet flowDataSet) throws Exception {
		String               strSql = "";
		String              USER_ID = PO.GetValueByParamName("USER_ID", "");
		String           USER_ORGID = PO.GetValueByParamName("USER_ORGID", "");
		int                   count = 0;
		EFDataSet   flowNodeDataSet = EFDataSet.getInstance();
		ResultSet                rs = null;
		String               FmtStr = "00";
		java.text.DecimalFormat  df = new java.text.DecimalFormat(FmtStr);
		
		for(int i = 0; i < flowDataSet.getRowCount(); i++) {
			flowNodeDataSet = EFDataSet.getInstance();
			//取出流程下有权限的节点
			strSql = "select FLOW_ID, NODE_NAME as FLOW_MC, NODE_ID, '' as BIZ_GUID, " 
			       + " '' as OP_ID, '' as BIZ_DJBH, '' as BIZ_KJQJ, '' as OP_NOTE, '' as OP_USER_NAME, '' as F_TJRQ" 
			       + " from FLOW_NODE_LIST where FLOW_ID = '" + flowDataSet.getRowSet(i).getString("FLOW_ID", "") + "' and " 
				   + " exists(SELECT 1 FROM FLOW_USER_LIST where FLOW_USER_LIST.FLOW_BH = FLOW_NODE_LIST.FLOW_ID and ((FLOW_USER_LIST.FLOW_BM = '"
				   + USER_ORGID
				   + "' and FLOW_USER_LIST.F_TYPE = 'BM')or (FLOW_USER_LIST.FLOW_USER = '"
				   + USER_ID
				   + "' and FLOW_USER_LIST.FLOW_BM = '" + USER_ORGID + "' and FLOW_USER_LIST.F_TYPE = 'USER'))"
		       	   + " and FLOW_USER_LIST.NODE_BH = FLOW_NODE_LIST.NODE_ID)"
				   + " order by NODE_ID";
			rs = stmt.executeQuery(strSql);
			flowNodeDataSet = DataSetUtils.resultSet2DataSet(rs, flowNodeDataSet);
			flowDataSet.getRowSet(i).putString("id", df.format(i + 1));
			flowDataSet.getRowSet(i).putString("pid", "");
			
			//计算节点下共有多少任务
			count = getFlowTaskDataSet(stmt, PO, flowNodeDataSet, df.format(i + 1), flowDataSet.getRowSet(i).getString("FLOW_ID", ""), flowDataSet.getRowSet(i).getString("FLOW_MC", ""));
			
			if(count > 0) {
				for(int j = flowNodeDataSet.getRowCount() - 1; j >= 0; j--) {
					if(flowNodeDataSet.getRowSet(j).getDataSet("TASKPendingDataSet") == null || flowNodeDataSet.getRowSet(j).getDataSet("TASKPendingDataSet").getRowCount() == 0) {
						flowNodeDataSet.removeRowSet(j);
					}
				}
				flowDataSet.getRowSet(i).putString("FLOW_MC", flowDataSet.getRowSet(i).getString("FLOW_MC", "") + "[共有任务" + count + "个]");
				flowDataSet.getRowSet(i).setDataSet("NODEDataSet", flowNodeDataSet);
				flowDataSet.getRowSet(i).putNumber("FLOW_TASK_COUNT", count);
			}
		}
	}
	
	/**
	 * 根据流程节点ID获取流程数据
	 * @throws Exception
	 */
	public static int getFlowTaskDataSet(JStatement stmt, JParamObject PO, EFDataSet flowNodeDataSet, String PID, String FLOW_BH, String FLOW_MC) throws Exception {
		String                     strSql = "";
		String               strSqlColumn = "";
		String                    USER_ID = PO.GetValueByParamName("USER_ID", "");
		String                 USER_ORGID = PO.GetValueByParamName("USER_ORGID", "");
		EFDataSet         flowTaskDataSet = EFDataSet.getInstance();
		ResultSet                      rs = null;
		int                         count = 0;
		String                     FmtStr = "00";
		java.text.DecimalFormat        df = new java.text.DecimalFormat(FmtStr);
		
		for(int i = 0; i < flowNodeDataSet.getRowCount(); i++) {
			strSqlColumn = " select BIZ_DATE as BIZ_KJQJ, BIZ_DJBH,FLOW_ID, BIZ_GUID, '" + flowNodeDataSet.getRowSet(i).getString("FLOW_MC", "") + "' as FLOW_MC, OP_ID, from_unixtime(substring(OP_TIME, 1, 10)) as F_TJRQ, "
       		             + " OP_NOTE, OP_USER_NAME";
			
			flowTaskDataSet = EFDataSet.getInstance();
			//取出流程下有权限的节点
			strSql = strSqlColumn + ", '" + flowNodeDataSet.getRowSet(i).getString("NODE_ID", "") + "' as NODE_ID from FLOW_TASK_LIST where FLOW_ID = '" + flowNodeDataSet.getRowSet(i).getString("FLOW_ID", "") + "' and " 
				   + " TASK_STATUS = 'processing' and RESR_STATUS = 'pending' and RESR_IN_CAUSE <> 'waiting' and NODE_TAG = '" + flowNodeDataSet.getRowSet(i).getString("NODE_ID", "") + "' and " 
				   + "(TASK_TO_UNIT IS NULL or TASK_TO_UNIT = '' or TASK_TO_UNIT = ' ' or TASK_TO_UNIT = '" + USER_ORGID + "' or (FLOW_ID = 'PSHT_EDIT_FLOW' and NODE_TAG = 'N1')) and "
				   + "(TASK_TO_USER IS NULL or TASK_TO_USER = '' or TASK_TO_USER = ' ' or TASK_TO_USER = '" + USER_ID + "' or (FLOW_ID = 'PSHT_EDIT_FLOW' and NODE_TAG = 'N1')) "
				   + " order by BIZ_KJQJ, BIZ_DJBH";
			rs = stmt.executeQuery(strSql);

			DataSetUtils.resultSet2DataSet(rs, flowTaskDataSet);

			if(flowTaskDataSet.getRowCount() == 0) continue;
			
			convertFlowTaskDataSet(PID + df.format(i + 1), FLOW_BH, FLOW_MC, flowNodeDataSet.getRowSet(i).getString("NODE_BH", ""), flowTaskDataSet);
			
			flowNodeDataSet.getRowSet(i).putString("id", PID + df.format(i + 1));
			flowNodeDataSet.getRowSet(i).putString("pid", PID);
			flowNodeDataSet.getRowSet(i).putString("FLOW_ID", FLOW_BH);
			flowNodeDataSet.getRowSet(i).putString("FLOW_MC", flowNodeDataSet.getRowSet(i).getString("FLOW_MC", "") + "[共有任务" + flowTaskDataSet.getRowCount() + "个]");
			flowNodeDataSet.getRowSet(i).putString("JS", "2");
			flowNodeDataSet.getRowSet(i).setDataSet("TASKPendingDataSet", flowTaskDataSet);
			//计算节点下共有多少任务
			count += flowTaskDataSet.getRowCount();
		}
		return count;
	}

	public static void convertFlowTaskDataSet(String PID, String FLOW_BH, String FLOW_MC, String NODE_ID, EFDataSet dataset) {
		String                    FmtStr = "000";
		java.text.DecimalFormat       df = new java.text.DecimalFormat(FmtStr);
		
		for(int i = 0; i < dataset.getRowCount(); i++) {
			dataset.getRowSet(i).putString("id", PID + df.format(i + 1));
			dataset.getRowSet(i).putString("pid", PID);
		}
	}
	
	
	public static EFDataSet loadAllFlowTask(JParamObject PO, JConnection conn, JStatement stmt) {
		ResultSet               rs = null;
		String              strSql = " select ";
		EFRowSet        flowRowset = EFRowSet.getInstance();
		String            BIZ_GUID = PO.GetValueByParamName(FLOW_TASK_LIST._BIZ_GUID, "");
		String             FLOW_ID = PO.GetValueByParamName("FLOW_ID", "");
		EFDataSet  flowTaskDataSet = null;
		
		try {
			strSql = " select * from FLOW_TASK_LIST where FLOW_ID = '" + FLOW_ID + "' and BIZ_GUID = '" + BIZ_GUID + "'";
			rs = stmt.executeQuery(strSql);
			if(rs != null && rs.next()) {
				flowRowset.putString("TABLE_NAME", "FLOW_TASK_LIST");
			} else {
				flowRowset.putString("TABLE_NAME", "FLOW_TASK_END_LIST");
			}
			
			flowTaskDataSet = EFDataSet.getInstance(flowRowset.getString("TABLE_NAME", ""));
			
			strSql = " select OP_ID, FLOW_ID, BIZ_DJBH, BIZ_DATE, BIZ_GUID, OP_USER, OP_USER_NAME, date_format(from_unixtime(substring(OP_TIME, 1, 10)),'%Y-%m-%d %H:%i:%s') as F_CLRQ, "
		     	   + " OP_BMBH, HYBMZD.F_BMMC as OP_BMMC, OP_NOTE, TASK_STATUS, OP_NODE, NODE_SRC, NODE_SRC_NAME, NODE_TAG, NODE_TAG_NAME, POP_ID, RESR_STATUS, RESR_IN_CAUSE, RESR_OUT_CAUSE, "
		     	   + " F_CHAR01,F_CHAR02,F_CHAR03,F_CHAR04,F_CHAR05,F_CHAR06,F_CHAR07,F_CHAR08,F_CHAR09,F_CHAR10, "
		     	   + " F_CHAR11,F_CHAR12,F_CHAR13,F_CHAR14,F_CHAR15,F_CHAR16,F_CHAR17,F_CHAR18,F_CHAR19,F_CHAR20,"
		     	   + " F_DATE01,F_DATE02,F_DATE03,F_DATE04,F_DATE05,F_DATE06,F_DATE07,F_DATE08,F_DATE09,F_DATE10," 
		     	   + " F_DATE11,F_DATE12,F_DATE13,F_DATE14,F_DATE15,F_DATE16,F_DATE17,F_DATE18,F_DATE19,F_DATE20,"
		     	   + " F_NUM01,F_NUM02,F_NUM03,F_NUM04,F_NUM05,F_NUM06,F_NUM07,F_NUM08,F_NUM09,F_NUM10," 
		     	   + " F_NUM11,F_NUM12,F_NUM13,F_NUM14,F_NUM15,F_NUM16,F_NUM17,F_NUM18,F_NUM19,F_NUM20,"
		     	   + " TASK_TO_UNIT, TASK_TO_UNIT_NAME, TASK_TO_USER, TASK_TO_USER_NAME"
		     	   + " from " + flowRowset.getString("TABLE_NAME", "")
		     	   + " left join HYBMZD on OP_BMBH = HYBMZD.F_BMBH"
		     	   + " where FLOW_ID = '" + FLOW_ID + "' and BIZ_GUID = '" + BIZ_GUID 
		     	   + "' and RESR_IN_CAUSE <> 'waiting' and (RESR_STATUS = 'processed' or RESR_STATUS = 'pending') order by OP_TIME";	
			flowRowset.putString("querySql", strSql);
			rs = stmt.executeQuery(strSql);
			DataSetUtils.resultSet2DataSet(rs, flowTaskDataSet);
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				EMPSQLUtil.closeAllResources(rs, stmt, conn);
			} catch(Exception ce) {
				ce.printStackTrace();
			}
		}
		return flowTaskDataSet;		
	}
}
