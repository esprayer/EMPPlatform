package com.dms.biz.docNote.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.etsoft.pub.util.StringFunction;
import com.dms.biz.docNote.DOCNoteServiceMgr;
import com.dms.persistence.docNote.bean.DMS_DOCNOTE;
import com.dms.persistence.docNote.mapper.DMS_DOCNOTEMapper;

import dwz.framework.sys.business.AbstractBusinessObjectServiceMgr;

@Transactional(rollbackFor = Exception.class)
@Service("DOCNoteServiceMgr")
public class DOCNoteMgrImpl extends AbstractBusinessObjectServiceMgr implements DOCNoteServiceMgr {

	@Autowired
	private DMS_DOCNOTEMapper docNoteMapper ;

	public void delteDocNote(String F_ID) {
		docNoteMapper.delete(F_ID);
	}

	public void insertDocNote(DMS_DOCNOTE docNote) {
		docNote.setF_CRDATE(new Date());
		docNote.setF_CHDATE(new Date());
		docNote.setF_ID(StringFunction.generateKey());
		docNoteMapper.insert(docNote);
	}

	public void updateDocNote(DMS_DOCNOTE docNote) {
		DMS_DOCNOTE po = loadDocNote(docNote.getF_ID());
		po.setF_MS(docNote.getF_MS());
		po.setF_CHDATE(new Date());
		docNoteMapper.update(po);
	}
	
	public DMS_DOCNOTE loadDocNote(String F_ID) {
		return docNoteMapper.load(F_ID);
	}

	@Override
	public List<DMS_DOCNOTE> docNoteList(String F_DOCID, String userId) {
		List<DMS_DOCNOTE> list = docNoteMapper.DocNoteList(F_DOCID);
		DMS_DOCNOTE note;
		for(int i = 0; i < list.size(); i++) {
			note = list.get(i);
			if(note.getF_WRITER().equals(userId)) note.setF_FLAG("1");
			else note.setF_FLAG("0");
		}
		return list;
	}
}
