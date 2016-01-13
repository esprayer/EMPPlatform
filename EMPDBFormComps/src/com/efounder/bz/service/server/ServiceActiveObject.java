package com.efounder.bz.service.server;

import java.sql.ResultSet;
import java.util.List;
import java.util.Vector;

import com.core.xml.PackageStub;
import com.core.xml.StubObject;
import com.efounder.builder.base.util.ESPServerContext;
import com.efounder.bz.service.ServiceComponentAdapter;
import com.efounder.bz.service.ServiceContextBuilder;
import com.efounder.component.EMPComponentStub;
import com.efounder.component.EMPComposeFormInfo;
import com.efounder.eai.EAI;
import com.efounder.eai.data.JParamObject;
import com.efounder.eai.data.JResponseObject;
import com.efounder.eai.framework.JActiveObject;
import com.efounder.form.pub.EMPServicePluginsUtil;
import com.efounder.sql.JConnection;
import com.efounder.sql.JStatement;

public class ServiceActiveObject extends JActiveObject {
	private static final String GUID = "00000000-0003-0001-0000000000000009";

	// ------------------------------------------------------------------------------------------------
	// 描述:
	// 设计: Skyline(2001.12.29)
	// 实现: Skyline
	// 修改:
	// ------------------------------------------------------------------------------------------------
	public ServiceActiveObject() {
		setObjectGUID(GUID);
	}

	/**
	 * 
	 * @param o1  Object
	 * @param o2  Object
	 * @param o3  Object
	 * @param o4  Object
	 * @throws Exception
	 * @return Object
	 */
	public Object syncRunService(Object o1, Object o2, Object o3, Object o4) throws Exception {
		JConnection                                conn = null;
		JStatement                                 stmt = null;
		Object                                   object = null;
		JParamObject                                 PO = null;
		JResponseObject                              RO = null;
		JResponseObject                  responseObject = new JResponseObject();
		EMPComposeFormInfo                     formInfo = null;
		ServiceContextBuilder     serviceContextBuilder = null;
		try {
			PO = (JParamObject) o1;
			
			conn = JConnection.getInstance(PO);
	
			conn.setAutoCommit(false);
			stmt = conn.createStatement();
						
			responseObject = new JResponseObject();
			
			RO = (JResponseObject) EAI.DAL.IOM("EMPFormServiceObject", "loadService", PO);
			
			if(RO != null && RO.getErrorCode() == 1) {
				object = RO.getResponseObject();
				formInfo = (EMPComposeFormInfo) object;
				
				serviceContextBuilder = initContextBuilder(formInfo);
				
				ESPServerContext espContext = serviceContextBuilder.createContext(PO, conn, o3, o4, responseObject, 1);
				espContext.setResponseObject(responseObject);
				
				Object response = serviceContextBuilder.createResponse(espContext);
					
				prepareSaveForm(espContext, formInfo, response);
				
				saveBillData(espContext, formInfo, response);

				finishSaveForm(espContext, formInfo, response);
				
				responseObject.setResponseObject(response);
			}
			conn.commit();
		} catch(Exception ce) {
			ce.printStackTrace();
		} finally {
			closeAllResources(null, stmt, conn);
		}
		return responseObject;
	}

	public ServiceContextBuilder initContextBuilder(EMPComposeFormInfo formInfo) throws Exception {
		StubObject                                 stub = null;
		EMPComponentStub             pluginsListService = null;
		ServiceContextBuilder     serviceContextBuilder = null;
		Vector                                   vector = null;
		
		pluginsListService = EMPServicePluginsUtil.loadPluginsListService(formInfo, EMPComponentStub.prepare);
		vector = (Vector) PackageStub.getContenetList().get((pluginsListService.getCompID() + "_ContextBuilder").toLowerCase());
		stub = (StubObject) vector.get(0);
		serviceContextBuilder = (ServiceContextBuilder) instanceClass(stub.getString("modelClazz", ""));
		return serviceContextBuilder;
	}
	
	public void prepareSaveForm(ESPServerContext espContex, EMPComposeFormInfo formInfo, Object responseObject) throws Exception {
		EMPComponentStub                     stub = null;
		ServiceComponentAdapter       compAdapter = null;
		List<EMPComponentStub>        pluginsList = null;

		stub = EMPServicePluginsUtil.loadPluginsListService(formInfo, EMPComponentStub.prepare);
		pluginsList = EMPServicePluginsUtil.loadPluginsList(formInfo, EMPComponentStub.prepare);
		compAdapter = (ServiceComponentAdapter) instanceClass(stub.getCompClazz());            //创建类的对象
		compAdapter.setServicePluginList(pluginsList);
		compAdapter.runService(espContex, responseObject);
	}
	
	public void saveBillData(ESPServerContext espContex, EMPComposeFormInfo formInfo, Object responseObject) throws Exception {
		EMPComponentStub                     stub = null;
		ServiceComponentAdapter       compAdapter = null;
		List<EMPComponentStub>        pluginsList = null;

		stub = EMPServicePluginsUtil.loadPluginsListService(formInfo, EMPComponentStub.execute);
		pluginsList = EMPServicePluginsUtil.loadPluginsList(formInfo, EMPComponentStub.execute);
		compAdapter = (ServiceComponentAdapter) instanceClass(stub.getCompClazz());            //创建类的对象
		compAdapter.setServicePluginList(pluginsList);
		compAdapter.runService(espContex, responseObject);
	}
	
	public void finishSaveForm(ESPServerContext espContex, EMPComposeFormInfo formInfo, Object responseObject) throws Exception {		 
		EMPComponentStub                     stub = null;
		ServiceComponentAdapter       compAdapter = null;
		List<EMPComponentStub>        pluginsList = null;

		stub = EMPServicePluginsUtil.loadPluginsListService(formInfo, EMPComponentStub.finish);
		pluginsList = EMPServicePluginsUtil.loadPluginsList(formInfo, EMPComponentStub.finish);
		compAdapter = (ServiceComponentAdapter) instanceClass(stub.getCompClazz());            //创建类的对象
		compAdapter.setServicePluginList(pluginsList);
		compAdapter.runService(espContex, responseObject);
	}

	public Object instanceClass(String className) throws Exception {
		Class                         classObject = null;
		
		classObject = Class.forName(className);
		return classObject.newInstance(); 
	}
	
	public static void closeAllResources(ResultSet rs, JStatement stmt, JConnection conn) {
		try {
			if(rs != null) {
				rs.close();
			}
		} catch(Exception ce) {
			ce.printStackTrace();
		}
		
		try {
			if(stmt != null) stmt.close();
		} catch(Exception ce) {
			ce.printStackTrace();
		}
		
		try {
			if(conn != null) conn.close();
		} catch(Exception ce) {
			ce.printStackTrace();
		}
	}
}