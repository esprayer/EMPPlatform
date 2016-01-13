package esyt.framework.com.dbform.io;

import java.sql.Statement;
import esyt.framework.com.dbform.bizmodel.EFFormDataModel;

public interface FormDataProvider {
	/**
	   *
	   * @param formModel EFFormDataModel
	   * @throws Exception
	   */
	  public Object prepareLoadForm(Statement stmt,EFFormDataModel formModel) throws Exception;
	  /**
	   *
	   * @param formModel EFFormDataModel
	   * @throws Exception
	   */
	  public Object finishLoadForm(Statement stmt,EFFormDataModel formModel) throws Exception;
	  /**
	   * @param formModel
	   * @throws Exception
	   */
	  public Object loadFormInfo(Statement stmt,EFFormDataModel formModel) throws Exception;

	  /**
	   * @param formModel
	   * @throws Exception
	   */
	  public Object loadBillData(Statement stmt,EFFormDataModel formModel) throws Exception;

	  /**
	   * @param formModel
	   * @param billInfo
	   * @throws Exception
	   */
	  public Object loadBillItemData(Statement stmt,EFFormDataModel formModel, String billContentID) throws Exception;
}
