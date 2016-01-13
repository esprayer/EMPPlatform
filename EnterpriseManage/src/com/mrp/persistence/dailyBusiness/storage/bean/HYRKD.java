package com.mrp.persistence.dailyBusiness.storage.bean;

import java.util.Date;

import dwz.dal.object.AbstractDO;

public class HYRKD extends AbstractDO{
	private String         F_DATE;
	private String         F_KJQJ;
	private String         F_DJBH;
	private String         F_GUID;
	private String         F_DJZT;
	private String         F_BZ;
	private String         F_BZRID;	
	private String         F_BZRMC;	
	private String         F_SBRID;	
	private String         F_SBRMC;
	private String         F_CKBH;
	private String         F_CKMC;
	private String         F_RKLX;
	private long           F_CRDATE;
	private long           F_CHDATE;
	private long           F_SBDATE;
	
	public HYRKD(){
	}

	public String getF_DATE() {
		return F_DATE;
	}

	public void setF_DATE(String f_DATE) {
		F_DATE = f_DATE;
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

	public String getF_DJZT() {
		return F_DJZT;
	}

	public void setF_DJZT(String f_DJZT) {
		F_DJZT = f_DJZT;
	}

	public String getF_BZ() {
		return F_BZ;
	}

	public void setF_BZ(String f_BZ) {
		F_BZ = f_BZ;
	}

	public String getF_BZRID() {
		return F_BZRID;
	}

	public void setF_BZRID(String f_BZRID) {
		F_BZRID = f_BZRID;
	}

	public String getF_BZRMC() {
		return F_BZRMC;
	}

	public void setF_BZRMC(String f_BZRMC) {
		F_BZRMC = f_BZRMC;
	}

	public String getF_SBRID() {
		return F_SBRID;
	}

	public void setF_SBRID(String f_SBRID) {
		F_SBRID = f_SBRID;
	}

	public String getF_SBRMC() {
		return F_SBRMC;
	}

	public void setF_SBRMC(String f_SBRMC) {
		F_SBRMC = f_SBRMC;
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

	public String getF_RKLX() {
		return F_RKLX;
	}

	public void setF_RKLX(String f_RKLX) {
		F_RKLX = f_RKLX;
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

	public Date getF_SBDATE() {
		Date dd = new Date(F_SBDATE);
		return dd;
	}

	public void setF_SBDATE(long f_SBDATE) {
		F_SBDATE = f_SBDATE;
	}

}

