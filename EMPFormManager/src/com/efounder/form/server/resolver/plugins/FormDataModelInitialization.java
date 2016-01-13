package com.efounder.form.server.resolver.plugins;

import com.efounder.builder.base.data.EFDataSet;
import com.efounder.builder.base.data.EFRowSet;
import com.efounder.eai.data.JParamObject;
import com.efounder.form.EFFormDataModel;
import com.efounder.form.io.FormDataResolverAdapter;
import com.efounder.form.server.provider.plugins.util.FormDataUtil;
import com.efounder.sql.JStatement;
import com.metadata.bizmodel.SYS_MODEL;

public class FormDataModelInitialization extends FormDataResolverAdapter {

	//初始化FormDataModel
	public void prepareSaveForm(JStatement stmt, EFFormDataModel model, JParamObject PO) throws Exception {
		EFDataSet dataset = FormDataUtil.getSYS_MDL_VAL(stmt, PO.GetValueByParamName(SYS_MODEL.MODEL_ID, ""));
		EFRowSet   rowset = null;
		
		for(int i = 0; i < dataset.getRowCount(); i++) {
			rowset = dataset.getRowSet(i);
			PO.SetValueByParamName(rowset.getString("MDL_KEY", ""), rowset.getString("MDL_VAL", ""));
		}
		
	}
}
