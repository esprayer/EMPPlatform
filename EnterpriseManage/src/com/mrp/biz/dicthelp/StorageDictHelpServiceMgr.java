package com.mrp.biz.dicthelp;

import dwz.framework.sys.business.BusinessObjectServiceMgr;
import esyt.framework.com.builder.base.data.EFDataSet;

public interface StorageDictHelpServiceMgr extends BusinessObjectServiceMgr {
	//项目
	EFDataSet searchHYXMZD(String beginDate, String endDate, String F_XMBH, String F_CLBH,int startIndex, int count);
}
