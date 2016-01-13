package com.efounder.form.io;

import java.sql.Statement;

import com.efounder.eai.data.JParamObject;
import com.efounder.form.EFFormDataModel;

public interface FormDataProvider {
	/**
	   *
	   * @param formModel EFFormDataModel
	   * @throws Exception
	   */
	  public Object prepareLoadForm(JParamObject PO, Statement stmt,EFFormDataModel formModel) throws Exception;
	  /**
	   *
	   * @param formModel EFFormDataModel
	   * @throws Exception
	   */
	  public Object finishLoadForm(JParamObject PO, Statement stmt,EFFormDataModel formModel) throws Exception;
	  /**
	   * @param formModel
	   * @throws Exception
	   */
	  public Object loadFormInfo(JParamObject PO, Statement stmt,EFFormDataModel formModel) throws Exception;

	  /**
	   * @param formModel
	   * @throws Exception
	   */
	  public Object loadBillData(JParamObject PO, Statement stmt,EFFormDataModel formModel) throws Exception;

	  /**
	   * @param formModel
	   * @param billInfo
	   * @throws Exception
	   */
	  public Object loadBillItemData(JParamObject PO, Statement stmt,EFFormDataModel formModel, String billContentID) throws Exception;
}
