package com.efounder.form.server.resolver.util;

import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.efounder.builder.base.data.EFRowSet;
import com.efounder.component.EMPComposeFormInfo;
import com.efounder.eai.data.JParamObject;
import com.efounder.form.EFFormDataModel;
import com.efounder.form.server.util.EMPFormServiceUtil;
import com.efounder.sql.JConnection;
import com.efounder.sql.JStatement;
import com.metadata.bizmodel.SYS_MODEL;

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
		JParamObject PO = JParamObject.Create();
		PO.SetValueByParamName(SYS_MODEL.SERVICE_ID, serviceKey);
		submitForm(formDataModel, headRowSet, PO);
	}

	public void submitForm(EFFormDataModel formDataModel, EFRowSet headRowSet, JParamObject PO) {
		JConnection         conn = null;
		JStatement          stmt = null;
		Object            object = null;
		
		try {
			conn = JConnection.getInstance(jdbcTemplate.getDataSource().getConnection());
			
			stmt = conn.createStatement();

			object = formServiceUtil.loadFormService(request, PO.GetValueByParamName(SYS_MODEL.SERVICE_ID, ""));

			if(object != null) {
				EMPComposeFormInfo formInfo = (EMPComposeFormInfo) object;
				pluginsMap = (HashMap) formInfo.getDataContainerStub().getRefCompStubMap();
				
				prepareSubmitFormList = (List<String>) pluginsMap.get("1");
				saveBillDataList = (List<String>) pluginsMap.get("2");
				finishSubmitFormList = (List<String>) pluginsMap.get("3");
				
				prepareSaveForm(stmt, formDataModel, PO);
				
				saveBillData(stmt, formDataModel, PO);

				finishSaveForm(stmt, formDataModel, PO);
				
				conn.commit();
				
				formDataModel.setFormSaveStatus(EFFormDataModel._FORM_SAVE_SUCCESS_);
			} else {
				prepareSaveForm(stmt, formDataModel, PO);
				
				saveBillData(stmt, formDataModel, PO);

				finishSaveForm(stmt, formDataModel, PO);
				
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
			closeAllResources(null, stmt, conn);
		}
		
	}
	
	public void prepareSaveForm(JStatement stmt, EFFormDataModel formDataModel, JParamObject PO) throws Exception {
		Class   types[] = {JStatement.class, formDataModel.getClass(), PO.getClass()};
		Object object[] = {stmt, formDataModel, PO};
		declareClassListMethod(prepareSubmitFormList, types, object, "prepareSaveForm");
	}
	
	public void saveBillData(JStatement stmt, EFFormDataModel formDataModel, JParamObject PO) throws Exception {
		Class   types[] = {JStatement.class, formDataModel.getClass(), PO.getClass()};
		Object object[] = {stmt, formDataModel, PO};
		declareClassListMethod(saveBillDataList, types, object, "saveBillData");
	}
	
	public void finishSaveForm(JStatement stmt, EFFormDataModel formDataModel, JParamObject PO) throws Exception {	
		Class   types[] = {JStatement.class, formDataModel.getClass(), PO.getClass()};
		Object object[] = {stmt, formDataModel, PO};
		declareClassListMethod(finishSubmitFormList, types, object, "finishSaveForm");
	}

	public void declareClassListMethod(List classList, Class types[], Object paramObject[], String methodName) throws Exception {
		String       strObject = "";
		for(int i = 0; classList != null && i < classList.size(); i++) {
			if(classList.get(i) instanceof String) {
				strObject = classList.get(i).toString();
			} else {
				strObject = ((com.efounder.component.EMPComponentStub)classList.get(i)).getCompClazz();
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
