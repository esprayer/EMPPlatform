package com.dms.biz.docLogger;

import java.util.List;

import com.dms.persistence.docLogger.bean.DMS_DOCLOG;

import dwz.framework.sys.business.BusinessObjectServiceMgr;

public interface DOCLOGServiceMgr extends BusinessObjectServiceMgr {
	void insertDOCLog(DMS_DOCLOG docLog);
	List<DMS_DOCLOG>  DOCLogList(String DOCID, String F_CREATOR);
}
