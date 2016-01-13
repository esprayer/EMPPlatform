package com.dms.biz.docVersion.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dms.biz.docVersion.DocVersionServiceMgr;
import com.dms.persistence.version.bean.DMS_DOCVERSION;
import com.dms.persistence.version.mapper.DMS_DOCVERSIONMapper;

import dwz.framework.sys.business.AbstractBusinessObjectServiceMgr;

@Transactional(rollbackFor = Exception.class)
@Service("docVersionServiceMgr")
public class DocVersionMgrImpl extends AbstractBusinessObjectServiceMgr implements DocVersionServiceMgr {

	@Autowired
	private DMS_DOCVERSIONMapper versionMapper ;

	public void createVersion(DMS_DOCVERSION docObject) {
		docObject.setF_CRDATE(new Date());
		versionMapper.insert(docObject);
	}

	public List<DMS_DOCVERSION> loadDocVersion(String F_DOCID) {
		List<DMS_DOCVERSION> list = versionMapper.loadDocVersion(F_DOCID);
		return list;
	}

	public DMS_DOCVERSION loadMaxDocVersion(String F_DOCID) {		
		return versionMapper.loadMaxDocVersion(F_DOCID);
	}

	public void updateVersionNote(DMS_DOCVERSION docVersion) {
		versionMapper.update(docVersion);
	}

	public DMS_DOCVERSION loadDocVersionById(String F_DOCID, int F_VERSION_NUMBER) {
		return versionMapper.loadDocVersionById(F_DOCID, F_VERSION_NUMBER);
	}
}
