package com.efounder.dict;

import com.framework.sys.business.BusinessObjectServiceMgr;
import com.efounder.builder.base.data.EFDataSet;
import com.efounder.builder.base.data.EFRowSet;
import com.efounder.eai.data.JParamObject;
import com.efounder.eai.data.JResponseObject;

public interface FCTServiceMgr extends BusinessObjectServiceMgr {

	//客户属性字典
	JResponseObject insertFCTRow(String tableName, EFRowSet rowset);
	
	JResponseObject insertFCTDataSet(String tableName, EFDataSet dataset);

	JResponseObject deleteFCTRow(String tableName, JParamObject PO);	

	//客户属性字典
	EFRowSet getFCTRow(JParamObject PO);	
	
	EFDataSet getFCTRows(JParamObject PO);	
}
