package com.mrp.persistence.dailyBusiness.transfers.bean;

import java.util.Date;

import dwz.dal.object.AbstractDO;

public class HYDBDMX extends AbstractDO{
	private String         F_KJQJ;
	private String         F_DJBH;
	private String         F_GUID;
	private String       F_FLGUID;
	private String         F_FLBH;
	private String         F_XMBH;
	private String         F_XMMC;
	private String         F_CPBH;
	private String         F_CPMC;
	private String         F_CLBH;
	private String         F_CLMC;
	private String         F_CKBH;
	private String         F_CKMC;
	private String         F_DWBH;
	private String         F_DWMC;
	private String         F_CSBH;
	private String         F_CSMC;
	private String         F_CLDJ;
	private double         F_CLSL;		
	private   long       F_CRDATE;
	private   long       F_CHDATE;

	public HYDBDMX(){
	}

	public String getF_KJQJ() {
		return F_KJQJ;
	}

	public void setF_KJQJ(String f_KJQJ) {
		F_KJQJ = f_KJQJ;
	}

	public String getF_DJBH() {
		return F_DJBH;
	}

	public void setF_DJBH(String f_DJBH) {
		F_DJBH = f_DJBH;
	}

	public String getF_GUID() {
		return F_GUID;
	}

	public void setF_GUID(String f_GUID) {
		F_GUID = f_GUID;
	}

	public String getF_FLGUID() {
		return F_FLGUID;
	}

	public void setF_FLGUID(String f_FLGUID) {
		F_FLGUID = f_FLGUID;
	}

	public String getF_FLBH() {
		return F_FLBH;
	}

	public void setF_FLBH(String f_FLBH) {
		F_FLBH = f_FLBH;
	}

	public String getF_XMBH() {
		return F_XMBH;
	}

	public void setF_XMBH(String f_XMBH) {
		F_XMBH = f_XMBH;
	}

	public String getF_XMMC() {
		return F_XMMC;
	}

	public void setF_XMMC(String f_XMMC) {
		F_XMMC = f_XMMC;
	}

	public String getF_CPBH() {
		return F_CPBH;
	}

	public void setF_CPBH(String f_CPBH) {
		F_CPBH = f_CPBH;
	}

	public String getF_CPMC() {
		return F_CPMC;
	}

	public void setF_CPMC(String f_CPMC) {
		F_CPMC = f_CPMC;
	}

	public String getF_CLBH() {
		return F_CLBH;
	}

	public void setF_CLBH(String f_CLBH) {
		F_CLBH = f_CLBH;
	}

	public String getF_CLMC() {
		return F_CLMC;
	}

	public void setF_CLMC(String f_CLMC) {
		F_CLMC = f_CLMC;
	}

	public String getF_CKBH() {
		return F_CKBH;
	}

	public void setF_CKBH(String f_CKBH) {
		F_CKBH = f_CKBH;
	}

	public String getF_CKMC() {
		return F_CKMC;
	}

	public void setF_CKMC(String f_CKMC) {
		F_CKMC = f_CKMC;
	}

	public String getF_DWBH() {
		return F_DWBH;
	}

	public void setF_DWBH(String f_DWBH) {
		F_DWBH = f_DWBH;
	}

	public String getF_DWMC() {
		return F_DWMC;
	}

	public void setF_DWMC(String f_DWMC) {
		F_DWMC = f_DWMC;
	}

	public String getF_CSBH() {
		return F_CSBH;
	}

	public void setF_CSBH(String f_CSBH) {
		F_CSBH = f_CSBH;
	}

	public String getF_CSMC() {
		return F_CSMC;
	}

	public void setF_CSMC(String f_CSMC) {
		F_CSMC = f_CSMC;
	}

	public String getF_CLDJ() {
		return F_CLDJ;
	}

	public void setF_CLDJ(String f_CLDJ) {
		F_CLDJ = f_CLDJ;
	}

	public double getF_CLSL() {
		return F_CLSL;
	}

	public void setF_CLSL(double f_CLSL) {
		F_CLSL = f_CLSL;
	}

	public Date getF_CRDATE() {
		Date dd = new Date(F_CRDATE);
		return dd;
	}

	public void setF_CRDATE(long f_CRDATE) {
		F_CRDATE = f_CRDATE;
	}

	public Date getF_CHDATE() {
		Date dd = new Date(F_CHDATE);
		return dd;
	}

	public void setF_CHDATE(long f_CHDATE) {
		F_CHDATE = f_CHDATE;
	}

}

