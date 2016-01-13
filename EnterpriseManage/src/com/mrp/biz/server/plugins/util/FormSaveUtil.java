package com.mrp.biz.server.plugins.util;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.jdbc.support.JdbcUtils;
import org.springframework.stereotype.Repository;

import com.etsoft.server.EMPFormServiceUtil;

import esyt.framework.com.component.EMPComposeFormInfo;
import esyt.framework.com.core.foundation.sql.JConnection;
import esyt.framework.com.core.foundation.sql.JStatement;
import esyt.framework.com.dbform.bizmodel.EFFormDataModel;
import esyt.framework.com.dbform.bizmodel.SYS_MDL_VAL;
import esyt.framework.com.dbform.io.FormDataResolverAdapter;

@Repository
public class FormSaveUtil {
	
	@Autowired
	private JdbcTemplate                  jdbcTemplate;

	@Autowired
	private EMPFormServiceUtil         formServiceUtil;

	@Autowired   
	private HttpServletRequest                 request;

	private   HashMap                       pluginsMap;

	public void saveForm(EFFormDataModel formDataModel, String serviceKey) {
		
		boolean          bCommit = false;
		JConnection         conn = null;
		JStatement          stmt = null;
		Object            object = null;
		try {
			conn = JConnection.getInstance(jdbcTemplate.getDataSource().getConnection());
	
			bCommit = conn.getAutoCommit();
			conn.setAutoCommit(false);
			stmt = conn.createStatement();
			
			object = formServiceUtil.loadFormService(request, serviceKey);

			EMPComposeFormInfo formInfo = (EMPComposeFormInfo) object;
			pluginsMap = (HashMap) formInfo.getDataContainerStub().getRefCompStubMap();

			prepareSaveForm(stmt, formDataModel);
				
			saveBillData(stmt, formDataModel);

			finishSaveForm(stmt, formDataModel);
			formDataModel.setFormSaveStatus(EFFormDataModel._FORM_SAVE_SUCCESS_);
			conn.commit();
		} catch(Exception ce) {
			try {
				conn.rollback();
			} catch (Exception e) {
				e.printStackTrace();
			}
			ce.printStackTrace();
			formDataModel.setFormSaveStatus(EFFormDataModel._FORM_SAVE_FAIL_);
			formDataModel.setFormSaveMessage(ce.getCause().getMessage());
		} finally {
			
			try {
				conn.setAutoCommit(bCommit);
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
		List<String> prepareSaveFormList = (List<String>) pluginsMap.get("1");
		declareClassMethod(prepareSaveFormList, types, object, "prepareSaveForm");
	}
	
	public void saveBillData(JStatement stmt, EFFormDataModel formDataModel) throws Exception {
		Class   types[] = {JStatement.class, formDataModel.getClass()};
		Object object[] = {stmt, formDataModel};
		List<String> saveBillDataList = (List<String>) pluginsMap.get("2");
		declareClassMethod(saveBillDataList, types, object, "saveBillData");
	}
	
	public void finishSaveForm(JStatement stmt, EFFormDataModel formDataModel) throws Exception {		 
		Class   types[] = {JStatement.class, formDataModel.getClass()};
		Object object[] = {stmt, formDataModel};
		List<String> finishSaveFormList = (List<String>) pluginsMap.get("3");
		declareClassMethod(finishSaveFormList, types, object, "finishSaveForm");
	}
	
	public void declareClassMethod(List classList, Class types[], Object paramObject[], String methodName) throws Exception {
		Class      classObject = null;
		Method    methodObject = null;
		Object          object = null;
		String       strObject = "";
		for(int i = 0; i < classList.size(); i++) {
			strObject = ((esyt.framework.com.component.EMPComponentStub)classList.get(i)).getCompClazz();
			classObject = Class.forName(strObject); 
			object = (FormDataResolverAdapter) classObject.newInstance();            //创建类的对象
			methodObject = classObject.getMethod(methodName,types);                  //动态调用方法  
			methodObject.invoke(object, paramObject);                                //传给方法的的参数
		}
	}
}
