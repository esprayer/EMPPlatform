package com.mrp.biz.server.plugins.util;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.jdbc.support.JdbcUtils;
import org.springframework.stereotype.Repository;

import com.etsoft.server.EMPFormServiceUtil;

import esyt.framework.com.builder.base.data.EFRowSet;
import esyt.framework.com.component.EMPComposeFormInfo;
import esyt.framework.com.core.foundation.sql.JConnection;
import esyt.framework.com.core.foundation.sql.JStatement;
import esyt.framework.com.dbform.bizmodel.EFFormDataModel;
import esyt.framework.com.dbform.bizmodel.SYS_MDL_VAL;

@Repository
public class FormSubmitUtil {
	
	@Autowired
	private JdbcTemplate                      jdbcTemplate;

	@Autowired
	private EMPFormServiceUtil             formServiceUtil;
	
	@Autowired   
	private HttpServletRequest                     request;
	
	private List<String>             prepareSubmitFormList;
	
	private List<String>                  saveBillDataList;
	
	private List<String>              finishSubmitFormList;
	
	private     HashMap                         pluginsMap;
	
	public void submitForm(EFFormDataModel formDataModel, EFRowSet headRowSet) {
		submitForm(formDataModel, headRowSet, "");
	}
	
	public void submitForm(EFFormDataModel formDataModel, EFRowSet headRowSet, String serviceKey) {
		JConnection         conn = null;
		JStatement          stmt = null;
		Object            object = null;
		
		try {
			conn = JConnection.getInstance(jdbcTemplate.getDataSource().getConnection());
			
			stmt = conn.createStatement();

			object = formServiceUtil.loadFormService(request, serviceKey);

			if(object != null) {
				EMPComposeFormInfo formInfo = (EMPComposeFormInfo) object;
				pluginsMap = (HashMap) formInfo.getDataContainerStub().getRefCompStubMap();
				
				prepareSubmitFormList = (List<String>) pluginsMap.get("1");
				saveBillDataList = (List<String>) pluginsMap.get("2");
				finishSubmitFormList = (List<String>) pluginsMap.get("3");
				
				prepareSaveForm(stmt, formDataModel);
				
				saveBillData(stmt, formDataModel);

				finishSaveForm(stmt, formDataModel);
				
				conn.commit();
				
				formDataModel.setFormSaveStatus(EFFormDataModel._FORM_SAVE_SUCCESS_);
			} else {
				prepareSaveForm(stmt, formDataModel);
				
				saveBillData(stmt, formDataModel);

				finishSaveForm(stmt, formDataModel);
				
				conn.commit();
				
				formDataModel.setFormSaveStatus(EFFormDataModel._FORM_SAVE_SUCCESS_);
			}
		} catch(Exception ce) {
			ce.printStackTrace();
			
			try {
				conn.rollback();
			} catch (Exception e) {
				e.printStackTrace();
			}
			formDataModel.setFormSaveStatus(EFFormDataModel._FORM_SAVE_FAIL_);
			formDataModel.setFormSaveMessage(ce.getCause().getMessage());
		} finally {
			try {
				JdbcUtils.closeStatement(stmt);
				stmt = null;
				DataSourceUtils.releaseConnection(conn, jdbcTemplate.getDataSource());
			} catch(Exception ce) {
				ce.printStackTrace();
			}
		}
		
	}
	
	public void prepareSaveForm(JStatement stmt, EFFormDataModel formDataModel) throws Exception {
		Class   types[] = {JStatement.class, formDataModel.getClass()};
		Object object[] = {stmt, formDataModel};
		declareClassListMethod(prepareSubmitFormList, types, object, "prepareSaveForm");
	}
	
	public void saveBillData(JStatement stmt, EFFormDataModel formDataModel) throws Exception {
		Class   types[] = {JStatement.class, formDataModel.getClass()};
		Object object[] = {stmt, formDataModel};
		declareClassListMethod(saveBillDataList, types, object, "saveBillData");
	}
	
	public void finishSaveForm(JStatement stmt, EFFormDataModel formDataModel) throws Exception {	
		Class   types[] = {JStatement.class, formDataModel.getClass()};
		Object object[] = {stmt, formDataModel};
		declareClassListMethod(finishSubmitFormList, types, object, "finishSaveForm");
	}

	public void declareClassListMethod(List classList, Class types[], Object paramObject[], String methodName) throws Exception {
		String       strObject = "";
		for(int i = 0; i < classList.size(); i++) {
			if(classList.get(i) instanceof String) {
				strObject = classList.get(i).toString();
			} else {
				strObject = ((esyt.framework.com.component.EMPComponentStub)classList.get(i)).getCompClazz();
			}
			declareClassMethod(strObject, types, paramObject, methodName);
		}
	}
	
	public void declareClassMethod(String className, Class types[], Object paramObject[], String methodName) throws Exception {
		Class      classObject = null;
		Method    methodObject = null;
		Object          object = null;
		
		classObject = Class.forName(className); 
		object = classObject.newInstance();                                      //创建类的对象
		methodObject = classObject.getMethod(methodName,types);                  //动态调用方法  
		methodObject.invoke(object, paramObject);                                //传给方法的的参数
	}
	
	public void initServerKeyList() {
		initPrepareSubmitFormList();
		initSaveBillDataList();
		initFinishSaveFormList();
	}
	
	public void initPrepareSubmitFormList() {
		prepareSubmitFormList = new ArrayList<String>();
		prepareSubmitFormList.add("com.mrp.biz.server.plugins.resolver.FormDataProvider");
		prepareSubmitFormList.add("com.mrp.biz.server.plugins.resolver.FormSubmitCheckValue");
		prepareSubmitFormList.add("com.mrp.biz.server.plugins.resolver.FormDefValueSet");
		prepareSubmitFormList.add("com.mrp.biz.server.plugins.resolver.FormDataDeleter");
	}
	
	public void initSaveBillDataList() {
		saveBillDataList = new ArrayList<String>();
		saveBillDataList.add("com.mrp.biz.server.plugins.resolver.FormHeadDataResolver");
		saveBillDataList.add("com.mrp.biz.server.plugins.resolver.FormItemDataResolver");	
	}

	public void initFinishSaveFormList() {
		finishSubmitFormList = new ArrayList<String>();
	}
	
	public void addFinishSubmitFormPlugins(String serverKey) {
		if(finishSubmitFormList == null) finishSubmitFormList = new ArrayList<String>();
		finishSubmitFormList.add(serverKey);
	}
}
