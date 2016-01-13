package com.persistence;

import com.dal.object.AbstractDO;

public class FLOW_USER_LIST extends AbstractDO{
	private String         FLOW_BH;
	private String         NODE_BH;
	private String         FLOW_BM;	
	private String          F_BMMC;
	private String       FLOW_USER;
	private String       USER_NAME;
	private String          F_TYPE;	

	public FLOW_USER_LIST(){
	}

	public String getFLOW_BH() {
		return FLOW_BH;
	}

	public void setFLOW_BH(String fLOW_BH) {
		FLOW_BH = fLOW_BH;
	}

	public String getNODE_BH() {
		return NODE_BH;
	}

	public void setNODE_BH(String nODE_BH) {
		NODE_BH = nODE_BH;
	}

	public String getFLOW_BM() {
		return FLOW_BM;
	}

	public void setFLOW_BM(String fLOW_BM) {
		FLOW_BM = fLOW_BM;
	}

	public String getF_BMMC() {
		return F_BMMC;
	}

	public void setF_BMMC(String f_BMMC) {
		F_BMMC = f_BMMC;
	}

	public String getFLOW_USER() {
		return FLOW_USER;
	}

	public void setFLOW_USER(String fLOW_USER) {
		FLOW_USER = fLOW_USER;
	}

	public String getUSER_NAME() {
		return USER_NAME;
	}

	public void setUSER_NAME(String uSER_NAME) {
		USER_NAME = uSER_NAME;
	}

	public String getF_TYPE() {
		return F_TYPE;
	}

	public void setF_TYPE(String f_TYPE) {
		F_TYPE = f_TYPE;
	}
}

