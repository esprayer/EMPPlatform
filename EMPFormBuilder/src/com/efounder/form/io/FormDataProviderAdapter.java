package com.efounder.form.io;

import java.sql.Statement;

import com.efounder.eai.data.JParamObject;
import com.efounder.form.EFFormDataModel;

public class FormDataProviderAdapter implements FormDataProvider {

	public Object prepareLoadForm(JParamObject PO, Statement stmt, EFFormDataModel formModel) throws Exception {

		return null;
	}

	public Object finishLoadForm(JParamObject PO, Statement stmt, EFFormDataModel formModel) throws Exception {

		return null;
	}

	public Object loadFormInfo(JParamObject PO, Statement stmt, EFFormDataModel formModel) throws Exception {
		
		return null;
	}

	public Object loadBillData(JParamObject PO, Statement stmt, EFFormDataModel formModel) throws Exception {

		return null;
	}

	public Object loadBillItemData(JParamObject PO, Statement stmt, EFFormDataModel formModel, String billContentID) throws Exception {

		return null;
	}

}
