package com.dms.persistence.docLogger.bean;

import dwz.dal.object.AbstractDO;

public class DMS_DOCLOG extends AbstractDO{
	private String         F_OPID;
	private String         F_DOCID;
	private String         F_DOCNAME;
	private int            F_OPTYPE;
	private String         F_OPERATOR;
	private String         F_OPERATORNAME;
	private String         F_IP;
	private String         F_MSG;
	private java.util.Date F_CRDATE;

	public DMS_DOCLOG(){
	}

	public String getF_OPID() {
		return F_OPID;
	}

	public void setF_OPID(String f_opid) {
		F_OPID = f_opid;
	}

	public String getF_DOCID() {
		return F_DOCID;
	}

	public void setF_DOCID(String f_docid) {
		F_DOCID = f_docid;
	}

	public String getF_DOCNAME() {
		return F_DOCNAME;
	}

	public void setF_DOCNAME(String f_docname) {
		F_DOCNAME = f_docname;
	}

	public int getF_OPTYPE() {
		return F_OPTYPE;
	}

	public void setF_OPTYPE(int f_optype) {
		F_OPTYPE = f_optype;
	}

	public String getF_OPERATOR() {
		return F_OPERATOR;
	}

	public void setF_OPERATOR(String f_operator) {
		F_OPERATOR = f_operator;
	}

	public String getF_OPERATORNAME() {
		return F_OPERATORNAME;
	}

	public void setF_OPERATORNAME(String f_operatorname) {
		F_OPERATORNAME = f_operatorname;
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

