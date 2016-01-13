package com.efounder.form.server.provider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.efounder.eai.data.JParamObject;
import com.efounder.form.EFFormDataModel;
import com.efounder.form.server.provider.plugins.util.FormLoadUtil;

@Transactional(rollbackFor = Exception.class)
@Service("formModelDataProvider")
public class FormModelDataProvider {
	
	@Autowired
	private   FormLoadUtil               loadUtil;

	public EFFormDataModel loadForm(JParamObject PO) throws Exception {
		EFFormDataModel  formDataModel = EFFormDataModel.getInstance();
		loadUtil.loadForm(PO, formDataModel);
		return formDataModel;
	}
}
