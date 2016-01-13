package com.dms.biz.sysLogger;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.dms.persistence.sysLogger.bean.DMS_SYSLOG;

import dwz.framework.sys.business.BusinessObjectServiceMgr;

public interface SYSLOGServiceMgr extends BusinessObjectServiceMgr {
	void insertLoginSYSLog(HttpServletRequest request);
	List<DMS_SYSLOG>  SYSLogList(String F_CREATOR);
}
