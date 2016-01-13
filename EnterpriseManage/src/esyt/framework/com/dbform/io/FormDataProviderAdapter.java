package esyt.framework.com.dbform.io;

import java.sql.Statement;

import esyt.framework.com.dbform.bizmodel.EFFormDataModel;

public class FormDataProviderAdapter implements FormDataProvider {

	public Object prepareLoadForm(Statement stmt, EFFormDataModel formModel) throws Exception {

		return null;
	}

	public Object finishLoadForm(Statement stmt, EFFormDataModel formModel) throws Exception {

		return null;
	}

	public Object loadFormInfo(Statement stmt, EFFormDataModel formModel) throws Exception {
		
		return null;
	}

	public Object loadBillData(Statement stmt, EFFormDataModel formModel) throws Exception {

		return null;
	}

	public Object loadBillItemData(Statement stmt, EFFormDataModel formModel, String billContentID) throws Exception {

		return null;
	}

}
