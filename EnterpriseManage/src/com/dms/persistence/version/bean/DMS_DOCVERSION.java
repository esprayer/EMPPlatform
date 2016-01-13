package com.dms.persistence.version.bean;

import java.math.BigInteger;

import dwz.dal.object.AbstractDO;

public class DMS_DOCVERSION extends AbstractDO{
	private String         F_ID;
	private String         F_DOCID;
	private int            F_VERSION_NUMBER;
	private String         F_CREATOR;
	private String         F_CREATORNAME;
	private String         F_COMMENT;	
	private java.util.Date F_CRDATE;

	public DMS_DOCVERSION(){
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

	public int getF_VERSION_NUMBER() {
		return F_VERSION_NUMBER;
	}

	public void setF_VERSION_NUMBER(int f_version_number) {
		F_VERSION_NUMBER = f_version_number;
	}

	public String getF_CREATOR() {
		return F_CREATOR;
	}

	public void setF_CREATOR(String f_creator) {
		F_CREATOR = f_creator;
	}

	public String getF_CREATORNAME() {
		return F_CREATORNAME;
	}

	public void setF_CREATORNAME(String f_creatorname) {
		F_CREATORNAME = f_creatorname;
	}

	public String getF_COMMENT() {
		return F_COMMENT;
	}

	public void setF_COMMENT(String f_comment) {
		F_COMMENT = f_comment;
	}

	public java.util.Date getF_CRDATE() {
		return F_CRDATE;
	}

	public void setF_CRDATE(java.util.Date f_crdate) {
		F_CRDATE = f_crdate;
	}
}

