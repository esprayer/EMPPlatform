package com.efounder.form.server.resolver.plugins;

import com.efounder.builder.base.data.EFDataSet;
import com.efounder.eai.data.JParamObject;
import com.efounder.form.EFFormDataModel;
import com.efounder.form.io.FormDataResolverAdapter;
import com.efounder.form.server.resolver.util.SYS_MDL_VAL;
import com.efounder.sql.JStatement;
import com.metadata.bizmodel.SYS_MODEL;
import com.mrp.biz.server.EMPCheckFormUtil;

public class FormCheckValue extends FormDataResolverAdapter {

public void prepareSaveForm(JStatement stmt, EFFormDataModel formDataModel, JParamObject PO) throws Exception {
		
		boolean         ifCheck = EMPCheckFormUtil.checkForm(stmt, formDataModel, PO);
		String         errorMsg = "";
		EFDataSet   billDataSet = formDataModel.getBillDataSet();
		String           F_KJQJ = billDataSet.getRowSet(0).getString(PO.GetValueByParamName(SYS_MODEL._BLFL_KJQJ_COL_, SYS_MDL_VAL.BILL_KJQJ), "");
		String           F_GUID = billDataSet.getRowSet(0).getString(PO.GetValueByParamName(SYS_MODEL._BILL_GUID_COL_, SYS_MDL_VAL.BILL_GUID), "");
		String         F_CHDATE = billDataSet.getRowSet(0).getString(PO.GetValueByParamName(SYS_MODEL._BILL_CHDATE_COL_, SYS_MDL_VAL.BILL_CHDATE), "");
		
		if(F_CHDATE.trim().length() == 0) {
			F_CHDATE = String.valueOf(billDataSet.getRowSet(0).getNumber("F_CHDATE", 0));
		}
		
		if(ifCheck) {
			errorMsg += EMPCheckFormUtil.checkFormEdit(stmt, PO, F_KJQJ, F_GUID, billDataSet.getTableName(), F_CHDATE);
			if(!errorMsg.equals("")) {
				throw new Exception(errorMsg);
			}
		}	
	}
}
