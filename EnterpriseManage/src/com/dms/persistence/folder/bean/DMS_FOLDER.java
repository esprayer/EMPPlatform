package com.dms.persistence.folder.bean;

import dwz.dal.object.AbstractDO;

public class DMS_FOLDER extends AbstractDO{
	private String         F_FOLDERID;
	private String         F_CREATOR;
	private String         F_ZGMC;
	private String         F_DESC;
	private String         F_TYPE;
	private String         F_NAME;
	private String         F_PARENTID;
	private String         F_FOLDERPATH;
	private int            F_DOCNumber;
	private String         F_DEL_USERID;
	private String         F_DEL_USERNAME;
	private java.util.Date F_DELDATE;
	private java.util.Date F_CRDATE;
	private java.util.Date F_CHDATE;

	public DMS_FOLDER(){
	}

	public String getF_FOLDERID() {
		return F_FOLDERID;
	}

	public void setF_FOLDERID(String f_folderid) {
		F_FOLDERID = f_folderid;
	}

	public String getF_CREATOR() {
		return F_CREATOR;
	}

	public void setF_CREATOR(String f_creator) {
		F_CREATOR = f_creator;
	}

	public String getF_ZGMC() {
		return F_ZGMC;
	}

	public void setF_ZGMC(String f_zgmc) {
		F_ZGMC = f_zgmc;
	}

	public String getF_DESC() {
		return F_DESC;
	}

	public void setF_DESC(String f_desc) {
		F_DESC = f_desc;
	}

	public String getF_TYPE() {
		return F_TYPE;
	}

	public void setF_TYPE(String f_type) {
		F_TYPE = f_type;
	}

	public String getF_NAME() {
		return F_NAME;
	}

	public void setF_NAME(String f_name) {
		F_NAME = f_name;
	}

	public String getF_PARENTID() {
		return F_PARENTID;
	}

	public void setF_PARENTID(String f_parentid) {
		F_PARENTID = f_parentid;
	}

	public String getF_FOLDERPATH() {
		return F_FOLDERPATH;
	}

	public void setF_FOLDERPATH(String f_folderpath) {
		F_FOLDERPATH = f_folderpath;
	}

	public int getF_DOCNumber() {
		return F_DOCNumber;
	}

	public void setF_DOCNumber(int number) {
		F_DOCNumber = number;
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

