package com.biz.help;

import com.efounder.builder.base.data.EFDataSet;
import com.efounder.eai.data.JParamObject;
import com.framework.sys.business.BusinessObjectServiceMgr;

public interface HelpServiceMgr extends BusinessObjectServiceMgr {
	//字典帮助
	EFDataSet helpDict(String tableName, String sqlColumn, String sqlWhere);	
	
	//字典帮助
	EFDataSet helpDict(JParamObject PO);	
}
