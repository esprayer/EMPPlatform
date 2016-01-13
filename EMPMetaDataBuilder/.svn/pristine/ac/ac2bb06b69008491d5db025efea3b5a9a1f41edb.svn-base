package com.metadata.biz;

import com.efounder.builder.base.data.EFDataSet;
import com.efounder.builder.base.data.EFRowSet;
import com.framework.sys.business.BusinessObjectServiceMgr;

public interface MetaDataServiceMgr extends BusinessObjectServiceMgr {
	//获取字典元数据
	EFRowSet getDictRow(String tableName);	
	
	//获取字典扩展属性
	EFRowSet getExtendProperties(String tableName);	
	
	//获取字典元数据
	EFRowSet getSYS_OBJECTS(String tableName);	
	
	//获取字典列元数据
	EFDataSet getSYS_OBJCOLS(String tableName);	
	
	EFDataSet getSYS_MDL_CTN(String MDL_ID, String CTN_TYPE);
	
	EFDataSet getSYS_MDL_VAL(String MDL_ID);
}
