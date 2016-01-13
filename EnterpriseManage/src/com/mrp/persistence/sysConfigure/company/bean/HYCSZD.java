package com.mrp.persistence.sysConfigure.company.bean;

import dwz.dal.object.AbstractDO;

public class HYCSZD extends AbstractDO{
	private String         F_CSBH;
	private String         F_CSMC;
	private int            F_SYZT;	
	private java.util.Date F_CRDATE;
	private java.util.Date F_CHDATE;

	public HYCSZD(){
	}

	public String getF_CSBH() {
		return F_CSBH;
	}

	public void setF_CSBH(String f_csbh) {
		F_CSBH = f_csbh;
	}

	public String getF_CSMC() {
		return F_CSMC;
	}

	public void setF_CSMC(String f_csmc) {
		F_CSMC = f_csmc;
	}

	public int getF_SYZT() {
		return F_SYZT;
	}

	public void setF_SYZT(int f_syzt) {
		F_SYZT = f_syzt;
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

