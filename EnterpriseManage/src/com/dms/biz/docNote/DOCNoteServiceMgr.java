package com.dms.biz.docNote;

import java.util.List;

import com.dms.persistence.docNote.bean.DMS_DOCNOTE;

import dwz.framework.sys.business.BusinessObjectServiceMgr;

public interface DOCNoteServiceMgr extends BusinessObjectServiceMgr {
	void insertDocNote(DMS_DOCNOTE docNote);
	void updateDocNote(DMS_DOCNOTE docNote);
	void delteDocNote(String F_ID);
	List<DMS_DOCNOTE>  docNoteList(String DOCID, String uerId);
	DMS_DOCNOTE  loadDocNote(String F_ID);
}
