package com.mrp.biz.server.plugins.resolver;

import com.mrp.biz.server.EMPCheckFormUtil;

import esyt.framework.com.builder.base.data.EFDataSet;
import esyt.framework.com.core.foundation.sql.JStatement;
import esyt.framework.com.dbform.bizmodel.EFFormDataModel;
import esyt.framework.com.dbform.io.FormDataResolverAdapter;

public class FormSubmitCheckValue extends FormDataResolverAdapter {

	public void prepareSaveForm(JStatement stmt, EFFormDataModel formDataModel) throws Exception {
		boolean         ifCheck = EMPCheckFormUtil.checkForm(stmt, formDataModel);
		String         errorMsg = "";
		EFDataSet   billDataSet = formDataModel.getBillDataSet();
		String           F_KJQJ = billDataSet.getRowSet(0).getString("F_KJQJ", "");
		String           F_GUID = billDataSet.getRowSet(0).getString("F_GUID", "");
		String         F_CHDATE = billDataSet.getRowSet(0).getString("F_CHDATE", "");
		
		if(F_CHDATE.trim().length() == 0) {
			F_CHDATE = String.valueOf(billDataSet.getRowSet(0).getNumber("F_CHDATE", 0));
		}
		
		if(ifCheck) {
			errorMsg += EMPCheckFormUtil.checkFormEdit(stmt, F_KJQJ, F_GUID, billDataSet.getTableName(), F_CHDATE);
			if(!errorMsg.equals("")) {
				throw new Exception(errorMsg);
			}
		}
	}
}
