package com.dms.biz.docLogger.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dms.biz.docLogger.DOCLOGServiceMgr;
import com.dms.persistence.docLogger.bean.DMS_DOCLOG;
import com.dms.persistence.docLogger.mapper.DMS_DOCLOGMapper;

import dwz.framework.sys.business.AbstractBusinessObjectServiceMgr;

@Transactional(rollbackFor = Exception.class)
@Service("DOCLOGServiceMgr")
public class DOCLOGMgrImpl extends AbstractBusinessObjectServiceMgr implements DOCLOGServiceMgr {

	@Autowired
	private DMS_DOCLOGMapper docLogMapper ;

	public List<DMS_DOCLOG> DOCLogList(String F_DOCID, String F_OPERATOR) {
		return docLogMapper.DOCLogList(F_DOCID, F_OPERATOR);
	}

	public void insertDOCLog(DMS_DOCLOG docLog) {
		docLog.setF_CRDATE(new Date());
		docLogMapper.insert(docLog);
	}
}
