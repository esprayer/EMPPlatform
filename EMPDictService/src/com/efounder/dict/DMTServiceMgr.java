package com.efounder.dict;

import com.framework.sys.business.BusinessObjectServiceMgr;
import com.efounder.builder.base.data.EFDataSet;
import com.efounder.builder.base.data.EFRowSet;
import com.efounder.eai.data.JParamObject;
import com.efounder.eai.data.JResponseObject;

public interface DMTServiceMgr extends BusinessObjectServiceMgr {
	//客户属性字典
	EFDataSet searchDictRow(String tableName, String searchCol, String keywords, String F_SYZT, int startIndex, int count);	
	
	//客户属性字典
	EFDataSet searchDictRow(JParamObject PO);	
	
	//客户属性字典
	String getTempDictBM(String tableName);	
	
	//客户属性字典
	String getTempDictBM(JParamObject PO, String tableName);	
	
	//客户属性字典
	JResponseObject insertDCTRow(String tableName, EFRowSet rowset);	
	
	JResponseObject insertMDLRow(JParamObject PO, String tableName, EFRowSet rowset);
	
	//客户属性字典
	JResponseObject updateRow(String tableName, EFRowSet rowset);	
	
	//客户属性字典
	JResponseObject deleteRow(String tableName, String keyVal);	

	//客户属性字典
	EFRowSet getRow(String tableName, String F_ZDBH);	
	
	//客户属性字典
	EFRowSet getRow(String tableName, String F_ZDBM, String F_ZDBH);	
	
	EFDataSet getRows(String tableName, String F_ZDBM, String F_ZDBH);	
}
