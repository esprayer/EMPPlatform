package com.dms.persistence.document.bean;

import java.math.BigInteger;

import dwz.dal.object.AbstractDO;

public class DMS_DOC extends AbstractDO{
	private String         F_DOCID;
	private String         F_NAME;
	private String         F_FOLDERID;
	private String         F_TYPE;
	private String         F_PATH;
	private BigInteger     F_SIZE;
	private String         F_CONSIZE;
	private String         F_CREATOR;
	private String         F_CREATORNAME;
	private String         F_DELETE;
	private String         F_DEL_USERID;
	private String         F_DEL_USERNAME;
	private java.util.Date F_DELDATE;
	private String         F_LOCKED;
	private String         F_LOCKED_USERID;
	private String         F_LOCKED_USERNAME;
	private java.util.Date F_LOCKEDDATE;
	private java.util.Date F_CRDATE;
	private java.util.Date F_CHDATE;

	public DMS_DOC(){
	}

	public String getF_DOCID() {
		return F_DOCID;
	}

	public void setF_DOCID(String f_docid) {
		F_DOCID = f_docid;
	}

	public String getF_NAME() {
		return F_NAME;
	}

	public void setF_NAME(String f_name) {
		F_NAME = f_name;
	}

	public String getF_FOLDERID() {
		return F_FOLDERID;
	}

	public void setF_FOLDERID(String f_folderid) {
		F_FOLDERID = f_folderid;
	}

	public String getF_TYPE() {
		return F_TYPE;
	}

	public void setF_TYPE(String f_type) {
		F_TYPE = f_type;
	}

	public String getF_PATH() {
		return F_PATH;
	}

	public void setF_PATH(String f_path) {
		F_PATH = f_path;
	}

	public BigInteger getF_SIZE() {
		return F_SIZE;
	}

	public void setF_SIZE(BigInteger f_size) {
		F_SIZE = f_size;
	}

	public String getF_CONSIZE() {
		return F_CONSIZE;
	}

	public void setF_CONSIZE(String f_consize) {
		F_CONSIZE = f_consize;
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

	public String getF_DELETE() {
		return F_DELETE;
	}

	public void setF_DELETE(String f_delete) {
		F_DELETE = f_delete;
	}

	public String getF_DEL_USERID() {
		return F_DEL_USERID;
	}

	public void setF_DEL_USERID(String f_del_userid) {
		F_DEL_USERID = f_del_userid;
	}

	public String getF_DEL_USERNAME() {
		return F_DEL_USERNAME;
	}

	public void setF_DEL_USERNAME(String f_del_username) {
		F_DEL_USERNAME = f_del_username;
	}

	public java.util.Date getF_DELDATE() {
		return F_DELDATE;
	}

	public void setF_DELDATE(java.util.Date f_deldate) {
		F_DELDATE = f_deldate;
	}

	public String getF_LOCKED() {
		return F_LOCKED;
	}

	public void setF_LOCKED(String f_locked) {
		F_LOCKED = f_locked;
	}

	public String getF_LOCKED_USERID() {
		return F_LOCKED_USERID;
	}

	public void setF_LOCKED_USERID(String f_locked_userid) {
		F_LOCKED_USERID = f_locked_userid;
	}

	public String getF_LOCKED_USERNAME() {
		return F_LOCKED_USERNAME;
	}

	public void setF_LOCKED_USERNAME(String f_locked_username) {
		F_LOCKED_USERNAME = f_locked_username;
	}

	public java.util.Date getF_LOCKEDDATE() {
		return F_LOCKEDDATE;
	}

	public void setF_LOCKEDDATE(java.util.Date f_lockeddate) {
		F_LOCKEDDATE = f_lockeddate;
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

