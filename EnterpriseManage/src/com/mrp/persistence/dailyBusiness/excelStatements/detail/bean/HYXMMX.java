package com.mrp.persistence.dailyBusiness.excelStatements.detail.bean;

import dwz.dal.object.AbstractDO;

public class HYXMMX extends AbstractDO{
	private String         F_XMBH;
	private String         F_CLBH;
	private String         F_CLMC;
	private double         F_SL;
	private String         F_BZ;
	private String         F_KCQK;	
	private java.util.Date F_CRDATE;
	private java.util.Date F_CHDATE;
	
	public HYXMMX(){
	}

	public String getF_XMBH() {
		return F_XMBH;
	}

	public void setF_XMBH(String f_xmbh) {
		F_XMBH = f_xmbh;
	}

	public String getF_CLBH() {
		return F_CLBH;
	}

	public void setF_CLBH(String f_clbh) {
		F_CLBH = f_clbh;
	}

	public String getF_CLMC() {
		return F_CLMC;
	}

	public void setF_CLMC(String f_clmc) {
		F_CLMC = f_clmc;
	}

	public double getF_SL() {
		return F_SL;
	}

	public void setF_SL(double f_sl) {
		F_SL = f_sl;
	}

	public String getF_BZ() {
		return F_BZ;
	}

	public void setF_BZ(String f_bz) {
		F_BZ = f_bz;
	}

	public String getF_KCQK() {
		return F_KCQK;
	}

	public void setF_KCQK(String f_kcqk) {
		F_KCQK = f_kcqk;
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

