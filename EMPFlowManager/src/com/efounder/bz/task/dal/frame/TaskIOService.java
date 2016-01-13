package com.efounder.bz.task.dal.frame;

import java.sql.*;

import com.efounder.builder.base.data.*;
import com.efounder.bz.flow.*;
import com.efounder.bz.flow.drive.*;
import com.efounder.db.*;
import com.efounder.eai.data.*;
import com.efounder.eai.framework.*;
import com.efounder.sql.*;
import com.efounder.eai.service.ParameterManager;
import com.efounder.eai.EAI;
import com.core.xml.PackageStub;
import com.core.xml.StubObject;

import java.util.*;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2006</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class TaskIOService extends JActiveObject {
	/**
	 *
	 */
	public TaskIOService() {
	}

	public Object loadFlowTaskInfo(JParamObject PO,Object o1,Object o3,Object o4) throws Exception {
		JResponseObject RO = null;
		JConnection   conn = JConnection.getInstance(PO);
		JStatement    stmt = conn.createStatement();
		EFDataSet      eds = null;
		
		try {
//			EFDataSet activeFlow = FlowTaskUtil.getRunFlow(PO, stmt,PO.GetValueByParamName("FLOW_BH"));
			eds = FlowTaskUtil.loadTaskInfo(PO,conn,stmt,null,null,null);
			RO = new JResponseObject(eds);
		} catch ( Exception e ) {
			RO = new JResponseObject(null,-1,e);
			e.printStackTrace();
		} finally {
			stmt.close();
			conn.close();
		}
		return RO;
	}
	
	public Object loadAllFlowTask(JParamObject PO,Object o1,Object o3,Object o4) throws Exception {
		JResponseObject          RO = null;
		JConnection            conn = JConnection.getInstance(PO);
		JStatement             stmt = conn.createStatement();
		EFDataSet   flowTaskDataSet = null;
		
		try {
			flowTaskDataSet = FlowTaskUtil.loadAllFlowTask(PO,conn,stmt);
			RO = new JResponseObject(flowTaskDataSet);
		} catch ( Exception e ) {
			RO = new JResponseObject(null,-1,e);
			e.printStackTrace();
		} finally {
			stmt.close();
			conn.close();
		}
		return RO;
	}
}
