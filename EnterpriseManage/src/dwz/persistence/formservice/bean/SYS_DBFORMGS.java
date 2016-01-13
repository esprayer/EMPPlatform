package dwz.persistence.formservice.bean;

import java.sql.Blob;

import dwz.dal.object.AbstractDO;

public class SYS_DBFORMGS extends AbstractDO{
	private String BBZD_BH;
	private Blob BBZD_GS;

	public SYS_DBFORMGS(){
	}

	public String getBBZD_BH() {
		return BBZD_BH;
	}

	public void setBBZD_BH(String bBZD_BH) {
		BBZD_BH = bBZD_BH;
	}

	public Blob getBBZD_GS() {
		return BBZD_GS;
	}

	public void setBBZD_GS(Blob bBZD_GS) {
		BBZD_GS = bBZD_GS;
	}

}

