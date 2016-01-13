package com.dms.biz.docVersion;

import java.util.List;

import com.dms.persistence.version.bean.DMS_DOCVERSION;


import dwz.framework.sys.business.BusinessObjectServiceMgr;

public interface DocVersionServiceMgr extends BusinessObjectServiceMgr {
	void createVersion(DMS_DOCVERSION docVersion);
	void updateVersionNote(DMS_DOCVERSION docVersion);
	List<DMS_DOCVERSION> loadDocVersion(String F_DOCID);
	DMS_DOCVERSION loadMaxDocVersion(String F_DOCID);
	DMS_DOCVERSION loadDocVersionById(String F_DOCID, int F_VERSION_NUMBER);
}
