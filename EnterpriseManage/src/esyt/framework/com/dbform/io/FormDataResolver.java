package esyt.framework.com.dbform.io;

import java.sql.Statement;

import esyt.framework.com.builder.base.data.EFDataSet;
import esyt.framework.com.dbform.bizmodel.EFFormDataModel;

public interface FormDataResolver {
	/**
	 * 
	 * @param formModel
	 *            EFFormDataModel
	 * @throws Exception
	 */
	public void prepareSaveForm(Statement stmt, EFFormDataModel model) throws Exception;

	/**
	 * 
	 * @param formModel
	 *            EFFormDataModel
	 * @throws Exception
	 */
	public void finishSaveForm(Statement stmt, EFFormDataModel model) throws Exception;
	
	/**
	   * 装入单据数据，一般情况下只有一行数据
	   * @param formModel
	   * @throws Exception
	   */
	  public void saveBillData(Statement stmt, EFFormDataModel model) throws Exception;
}
