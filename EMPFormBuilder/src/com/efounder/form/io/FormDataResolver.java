package com.efounder.form.io;

import java.sql.Statement;

import com.efounder.eai.data.JParamObject;
import com.efounder.form.EFFormDataModel;

public interface FormDataResolver {
	/**
	 * 
	 * @param formModel
	 *            EFFormDataModel
	 * @throws Exception
	 */
	public void prepareSaveForm(Statement stmt, EFFormDataModel model, JParamObject PO) throws Exception;

	/**
	 * 
	 * @param formModel
	 *            EFFormDataModel
	 * @throws Exception
	 */
	public void finishSaveForm(Statement stmt, EFFormDataModel model, JParamObject PO) throws Exception;
	
	/**
	   * 装入单据数据，一般情况下只有一行数据
	   * @param formModel
	   * @throws Exception
	   */
	  public void saveBillData(Statement stmt, EFFormDataModel model, JParamObject PO) throws Exception;
}
