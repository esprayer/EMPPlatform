package com.mrp.persistence.sysConfigure.department.bean;

import dwz.dal.object.AbstractDO;

public class HYBMZD extends AbstractDO{
	private String         F_BMBH;
	private String         F_BMMC;
	private int            F_JS;
	private int            F_MX;
	private int            F_SYZT;	
	private java.util.Date F_CRDATE;
	private java.util.Date F_CHDATE;

	public HYBMZD(){
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

	public int getF_JS() {
		return F_JS;
	}

	public void setF_JS(int f_js) {
		F_JS = f_js;
	}

	public int getF_MX() {
		return F_MX;
	}

	public void setF_MX(int f_mx) {
		F_MX = f_mx;
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

