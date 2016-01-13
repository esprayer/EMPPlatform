package com.dms.persistence.permission.bean;

import dwz.dal.object.AbstractDO;

public class DMS_FOLDER_PERMISSION extends AbstractDO{
	private String         F_ID;
	private String         F_FOLDERID;
	private String         F_PERMISSION;
	private String         F_BMBH;
	private String         F_BMMC;
	private String         F_USERID;
	private String         F_USERNAME;
	private String         F_CREATOR;
	private java.util.Date F_CRDATE;
	private java.util.Date F_CHDATE;
	
	public DMS_FOLDER_PERMISSION(){
	}

	public String getF_ID() {
		return F_ID;
	}

	public void setF_ID(String f_id) {
		F_ID = f_id;
	}

	public String getF_FOLDERID() {
		return F_FOLDERID;
	}

	public void setF_FOLDERID(String f_folderid) {
		F_FOLDERID = f_folderid;
	}

	public String getF_PERMISSION() {
		return F_PERMISSION;
	}

	public void setF_PERMISSION(String f_permission) {
		F_PERMISSION = f_permission;
	}

	public String getF_BMBH() {
		return F_BMBH;
	}

	public void setF_BMBH(String f_bmbh) {
		F_BMBH = f_bmbh;
	}

	public String getF_BMMC() {
		return F_BMMC;
	}

	public void setF_BMMC(String f_bmmc) {
		F_BMMC = f_bmmc;
	}

	public String getF_USERID() {
		return F_USERID;
	}

	public void setF_USERID(String f_userid) {
		F_USERID = f_userid;
	}

	public String getF_USERNAME() {
		return F_USERNAME;
	}

	public void setF_USERNAME(String f_username) {
		F_USERNAME = f_username;
	}

	public String getF_CREATOR() {
		return F_CREATOR;
	}

	public void setF_CREATOR(String f_creator) {
		F_CREATOR = f_creator;
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

