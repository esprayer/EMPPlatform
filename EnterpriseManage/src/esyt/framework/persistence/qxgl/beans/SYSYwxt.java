package esyt.framework.persistence.qxgl.beans;

import java.util.Date;

import dwz.dal.object.AbstractDO;
/**
 * 用户bean
 * @author frog
 *
 */
public class SYSYwxt extends AbstractDO{
	private String APP_ID   = "";
	private String APP_NAME = "";
	private String APP_DES  = "";
	private String APP_ICON = "";
	private String LAN_ID   = "";
	private String APP_URL  = "";
	public String getAPP_URL() {
		return APP_URL;
	}
	public void setAPP_URL(String aPP_URL) {
		APP_URL = aPP_URL;
	}
	private String F_SYZT   = "1";
	private Date F_CRDATE = new Date();
	private Date F_CHDATE = new Date();
	
	public String getAPP_ID() {
		return APP_ID;
	}
	public void setAPP_ID(String aPP_ID) {
		APP_ID = aPP_ID;
	}
	public String getAPP_NAME() {
		return APP_NAME;
	}
	public void setAPP_NAME(String aPP_NAME) {
		APP_NAME = aPP_NAME;
	}
	public String getAPP_DES() {
		return APP_DES;
	}
	public void setAPP_DES(String aPP_DES) {
		APP_DES = aPP_DES;
	}
	public String getAPP_ICON() {
		return APP_ICON;
	}
	public void setAPP_ICON(String aPP_ICON) {
		APP_ICON = aPP_ICON;
	}
	public String getLAN_ID() {
		return LAN_ID;
	}
	public void setLAN_ID(String lAN_ID) {
		LAN_ID = lAN_ID;
	}
	public String getF_SYZT() {
		return F_SYZT;
	}
	public void setF_SYZT(String f_SYZT) {
		F_SYZT = f_SYZT;
	}
	public Date getF_CRDATE() {
		return F_CRDATE;
	}
	public void setF_CRDATE(Date f_CRDATE) {
		F_CRDATE = new Date();
	}
	public Date getF_CHDATE() {
		return F_CHDATE;
	}
	public void setF_CHDATE(Date f_CHDATE) {
		F_CHDATE  = new Date();
	}
	
}