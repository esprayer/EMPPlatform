package com.efounder.builder.meta.bizmodel;

public class SYS_MDL_CTN {
	
	// 每种类型的业务内容最大的事实表的数据，默认为16个，应该够用了
	public static final int _BIZ_CTN_TYPE_FCT_COUNT_ = 16;
	  
	/**
	 *
	 */
	public static final String CTN_ID  = "CTN_ID";
	
	/**
	 *
	 */
	public static final String CTN_FCT = "CTN_FCT";
	
	// 表单信息表
	public static final String _BIZ_CTN_TYPE_JBDS_  = "01"; // BILL_DATA_SET 表单数据集
	// 分录信息表
	public static final String _BIZ_CTN_TYPE_JIDS_  = "02"; // ITEM_DATA_SET 分录数据集
	
	/**
	 *
	 */
	public static final String CTN_TYPE = "CTN_TYPE";
	
	/**
	 *
	 */
	public static final String PCTN_ID  = "PCTN_ID";
}
