package com.mrp.persistence.sysConfigure.projectProduct.bean;

import dwz.dal.object.AbstractDO;

public class HYXMCP extends AbstractDO{
	private String         F_CPBH;
	private String         F_CPMC;
	private String         F_XMBH;
	private String         F_XMMC;
	private int            F_SYZT;	
	private java.util.Date F_CRDATE;
	private java.util.Date F_CHDATE;

	public HYXMCP(){
	}

	public String getF_CPBH() {
		return F_CPBH;
	}

	public void setF_CPBH(String f_cpbh) {
		F_CPBH = f_cpbh;
	}

	public String getF_CPMC() {
		return F_CPMC;
	}

	public void setF_CPMC(String f_cpmc) {
		F_CPMC = f_cpmc;
	}

	public String getF_XMBH() {
		return F_XMBH;
	}

	public void setF_XMBH(String f_xmbh) {
		F_XMBH = f_xmbh;
	}

	public String getF_XMMC() {
		return F_XMMC;
	}

	public void setF_XMMC(String f_xmmc) {
		F_XMMC = f_xmmc;
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

