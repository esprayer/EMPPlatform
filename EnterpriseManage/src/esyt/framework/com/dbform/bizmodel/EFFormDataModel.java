package esyt.framework.com.dbform.bizmodel;

import java.io.Serializable;

import esyt.framework.com.builder.base.data.EFDataSet;
import esyt.framework.com.builder.base.data.EFRowSet;

public class EFFormDataModel extends EFRowSet implements Serializable {
	static final long serialVersionUID = 1L;
	private EFDataSet billDataSet = null;

	public static EFFormDataModel getInstance() {
		EFFormDataModel formModel = new EFFormDataModel();
		return formModel;
	}

	public static final int _FORM_EDIT_STATUS_ = 0x0000;
	// 新建状态
	public static final int _FORM_EDIT_CREATE_ = 0x0001;
	// 编缉状态
	public static final int _FORM_EDIT_CHANGE_ = 0x0002;
	// 删除状态
	public static final int _FORM_EDIT_DELETE_ = 0x0003;
	// 锁定状态
	public static final int   _FORM_EDIT_LOCK_ = 0x0004;
	// 提交状态
	public static final int _FORM_EDIT_SUBMIT_ = 0x0005;

	/**
	   *
	   */
	protected int formEditStatus = _FORM_EDIT_STATUS_;
	
	// 保存成功
	public static final int _FORM_SAVE_SUCCESS_ = 0x0000;
	// 保存失败
	public static final int _FORM_SAVE_FAIL_ = 0x0001;
	
	/**
	   *
	   */
	protected int formSaveStatus = _FORM_SAVE_SUCCESS_;
	
	// 编辑头信息
	public static final int _FORM_SAVE_HEAD_ = 0x0000;
	// 编辑一条分录信息
	public static final int _FORM_SAVE_ITEM_ = 0x0001;
	// 编辑多条分录信息
	public static final int _FORM_SAVE_ITEMS_ = 0x0002;
	// 编辑所有分录信息
	public static final int _FORM_SAVE_ITEMS_ALL_ = 0x0003;
	
	/**
	   *
	   */
	protected int formSaveType = _FORM_SAVE_HEAD_;
	
	public static final String _FORMEDITSAVETYPE_  = "formEditSaveType";
	/**
	   *
	   */
	protected String formSaveMessage = "";

	/**
	 * 
	 * @return int
	 */
	public int getFormEditStatus() {
		return formEditStatus;
	}
	
	/**
	 * 
	 * @param status int
	 */
	public void setFormEditStatus(int status) {
		this.formEditStatus = status;
	}
	  
	/**
	 * 
	 * @return int
	 */
	public int getFormSaveStatus() {
		return formSaveStatus;
	}
	
	/**
	 * 
	 * @param status int
	 */
	public void setFormSaveStatus(int status) {
		this.formSaveStatus = status;
	}

	/**
	 * 
	 * @return int
	 */
	public String getFormSaveMessage() {
		return formSaveMessage;
	}
	
	/**
	 * 
	 * @param status int
	 */
	public void setFormSaveMessage(String saveMsg) {
		this.formSaveMessage = saveMsg;
	}
	
	/**
	 * 
	 * @return int
	 */
	public int getFormSaveType() {
		return formSaveType;
	}
	
	/**
	 * 
	 * @param status int
	 */
	public void setFormSaveType(int type) {
		this.formSaveType = type;
	}
	
	/**
	 * 
	 * @param status int
	 */
	public void setFormSaveType(String type) {
		this.formSaveType = Integer.parseInt(type);
	}
	
	public EFDataSet getBillDataSet() {
		return this.billDataSet;
	}

	public void setBillDataSet(EFDataSet billDataSet) {
		setDataSet(billDataSet.getTableName(), billDataSet);
		this.billDataSet = billDataSet;
	}

	public EFRowSet getBillData(int row) {
		if (this.billDataSet == null)
			return null;
		return this.billDataSet.getRowSet(row);
	}

	public String getF_DJBH() {
		return getString("F_DJBH", "");
	}

	public String getF_FLGUID() {
		return getString("F_FLGUID", "");
	}
	
	public String getF_DATE() {
		return getString("F_DATE", "");
	}

	public String getF_GUID() {
		return getString("F_GUID", "");
	}

	public void setF_DJBH(String F_DJBH) {
		putString("F_DJBH", F_DJBH);
	}

	public void setF_GUID(String F_GUID) {
		putString("F_GUID", F_GUID);
	}

	public void setF_DATE(String F_DATE) {
		putString("F_DATE", F_DATE);
	}

	public void setF_FLGUID(String F_FLGUID) {
		putString("F_FLGUID", F_FLGUID);
	}
	
	public String getDataObjectGUID() {
		return getF_GUID();
	}

	public String getDataObjectF_DATE() {
		return getF_DATE();
	}

	public String getDataObjectF_DJBH() {
		return getF_DJBH();
	}
}