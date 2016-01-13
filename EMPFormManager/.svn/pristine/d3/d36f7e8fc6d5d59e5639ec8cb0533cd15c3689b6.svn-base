package com.efounder.form.server.provider.plugins.util;

import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.efounder.component.EMPComposeFormInfo;
import com.efounder.eai.data.JParamObject;
import com.efounder.form.EFFormDataModel;
import com.efounder.form.io.FormDataProviderAdapter;
import com.efounder.form.io.FormDataResolverAdapter;
import com.efounder.form.server.util.EMPFormServiceUtil;
import com.efounder.sql.JConnection;
import com.efounder.sql.JStatement;
import com.metadata.bizmodel.SYS_MODEL;

@Repository
public class FormLoadUtil {
	
	@Autowired
	private JdbcTemplate                  jdbcTemplate;

	@Autowired
	private EMPFormServiceUtil         formServiceUtil;

	@Autowired   
	private HttpServletRequest                 request;

	private   HashMap                       pluginsMap;

	public void loadForm(JParamObject PO, EFFormDataModel formDataModel) {
		
		JConnection         conn = null;
		JStatement          stmt = null;
		Object            object = null;
		try {
			conn = JConnection.getInstance(jdbcTemplate.getDataSource().getConnection());

			stmt = conn.createStatement();
			
			object = formServiceUtil.loadFormService(request, PO.GetValueByParamName(SYS_MODEL.SERVICE_ID));

			EMPComposeFormInfo formInfo = (EMPComposeFormInfo) object;
			pluginsMap = (HashMap) formInfo.getDataContainerStub().getRefCompStubMap();

			prepareSaveForm(PO, stmt, formDataModel);
				
			saveBillData(PO, stmt, formDataModel);

			finishSaveForm(PO, stmt, formDataModel);
		} catch(Exception ce) {
			ce.printStackTrace();
			formDataModel.setFormSaveMessage(ce.getCause().getMessage());
		} finally {
			closeAllResources(null, stmt, conn);
		}
	}
	
	public void prepareSaveForm(JParamObject PO, JStatement stmt, EFFormDataModel formDataModel) throws Exception {
		Class   types[] = {JStatement.class, formDataModel.getClass(), PO.getClass()};
		Object object[] = {stmt, formDataModel, PO};
		List<String> prepareSaveFormList = (List<String>) pluginsMap.get("1");
		declareClassMethod(prepareSaveFormList, types, object, "prepareLoadForm");
	}
	
	public void saveBillData(JParamObject PO, JStatement stmt, EFFormDataModel formDataModel) throws Exception {
		Class   types[] = {JStatement.class, formDataModel.getClass(), PO.getClass()};
		Object object[] = {stmt, formDataModel, PO};
		List<String> saveBillDataList = (List<String>) pluginsMap.get("2");
		declareClassMethod(saveBillDataList, types, object, "loadBillData");
		declareClassMethod(saveBillDataList, types, object, "loadBillItemData");
		declareClassMethod(saveBillDataList, types, object, "loadFormInfo");
	}
	
	public void finishSaveForm(JParamObject PO, JStatement stmt, EFFormDataModel formDataModel) throws Exception {		 
		Class   types[] = {JStatement.class, formDataModel.getClass(), PO.getClass()};
		Object object[] = {stmt, formDataModel, PO};
		List<String> finishSaveFormList = (List<String>) pluginsMap.get("3");
		declareClassMethod(finishSaveFormList, types, object, "finishLoadForm");
	}
	
	public void declareClassMethod(List classList, Class types[], Object paramObject[], String methodName) throws Exception {
		Class      classObject = null;
		Method    methodObject = null;
		Object          object = null;
		String       strObject = "";
		for(int i = 0; classList != null && i < classList.size(); i++) {
			strObject = ((com.efounder.component.EMPComponentStub)classList.get(i)).getCompClazz();
			classObject = Class.forName(strObject); 
			object = (FormDataProviderAdapter) classObject.newInstance();            //创建类的对象
			methodObject = classObject.getMethod(methodName,types);                  //动态调用方法  
			methodObject.invoke(object, paramObject);                                //传给方法的的参数
		}
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
