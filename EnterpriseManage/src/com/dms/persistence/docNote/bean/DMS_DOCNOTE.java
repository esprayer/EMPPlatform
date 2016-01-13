package com.dms.persistence.docNote.bean;

import dwz.dal.object.AbstractDO;

public class DMS_DOCNOTE extends AbstractDO{
	private String         F_ID;
	private String         F_DOCID;
	private String         F_WRITER;
	private String         F_WRITERNAME;
	private String         F_MS;
	private String         F_FLAG;
	private java.util.Date F_CRDATE;
	private java.util.Date F_CHDATE;

	public DMS_DOCNOTE(){
	}

	public String getF_ID() {
		return F_ID;
	}

	public void setF_ID(String f_id) {
		F_ID = f_id;
	}

	public String getF_DOCID() {
		return F_DOCID;
	}

	public void setF_DOCID(String f_docid) {
		F_DOCID = f_docid;
	}

	public String getF_WRITER() {
		return F_WRITER;
	}

	public void setF_WRITER(String f_writer) {
		F_WRITER = f_writer;
	}

	public String getF_WRITERNAME() {
		return F_WRITERNAME;
	}

	public void setF_WRITERNAME(String f_writername) {
		F_WRITERNAME = f_writername;
	}

	public String getF_MS() {
		return F_MS;
	}

	public void setF_MS(String f_ms) {
		F_MS = f_ms;
	}

	public String getF_FLAG() {
		return F_FLAG;
	}

	public void setF_FLAG(String f_flag) {
		F_FLAG = f_flag;
	}

	public java.util.Date getF_CRDATE() {
		return F_CRDATE;
	}

	public void setF_CRDATE(java.util.Date f_crdate) {
		F_CRDATE = f_crdate;
	}

	public java.util.Date getF_CHDATE() {
		return F_CHDATE;
	}

	public void setF_CHDATE(java.util.Date f_chdate) {
		F_CHDATE = f_chdate;
	}
}

