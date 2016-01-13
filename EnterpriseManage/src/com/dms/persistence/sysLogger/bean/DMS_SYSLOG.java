package com.dms.persistence.sysLogger.bean;

import dwz.dal.object.AbstractDO;

public class DMS_SYSLOG extends AbstractDO{
	private String         F_ID;
	private String         F_CREATOR;
	private String         F_IP;
	private String            F_MSG;
	private java.util.Date F_CRDATE;

	public DMS_SYSLOG(){
	}

	public String getF_ID() {
		return F_ID;
	}

	public void setF_ID(String f_id) {
		F_ID = f_id;
	}

	public String getF_CREATOR() {
		return F_CREATOR;
	}

	public void setF_CREATOR(String f_creator) {
		F_CREATOR = f_creator;
	}

	public String getF_IP() {
		return F_IP;
	}

	public void setF_IP(String f_ip) {
		F_IP = f_ip;
	}

	public String getF_MSG() {
		return F_MSG;
	}

	public void setF_MSG(String f_msg) {
		F_MSG = f_msg;
	}

	public java.util.Date getF_CRDATE() {
		return F_CRDATE;
	}

	public void setF_CRDATE(java.util.Date f_crdate) {
		F_CRDATE = f_crdate;
	}

}

