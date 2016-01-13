/*
 * Powered By [dwz4j-framework]
 * Web Site: http://j-ui.com
 * Google Code: http://code.google.com/p/dwz4j/
 * Generated 2012-07-29 17:54:23 by code generator
 */
package dwz.business.standard;

import java.util.HashSet;
import java.util.Set;

import dwz.framework.sys.business.AbstractBusinessObject;
import dwz.persistence.beans.SYS_OBJECT;

public class MetadataObject extends AbstractBusinessObject {
	private static final long serialVersionUID = 1L;
	private SYS_OBJECT sysObject;

	/* generateConstructor */
	public MetadataObject() {
		this.sysObject = new SYS_OBJECT();
	}

	public MetadataObject(SYS_OBJECT sysObject) {
		this.sysObject = sysObject;
	}

	public SYS_OBJECT getSYS_OBJECT() {
		return this.sysObject;
	}

	public String getOBJ_ID() {
		return sysObject.getOBJ_ID();
	}

	public void setOBJ_ID(String obj_id) {
		sysObject.setOBJ_ID(obj_id);
	}

	public String getOBJ_MC() {
		return sysObject.getOBJ_MC();
	}

	public void setOBJ_MC(String obj_mc) {
		sysObject.setOBJ_MC(obj_mc);
	}

	public String getOBJ_DES() {
		return sysObject.getOBJ_DES();
	}

	public void setOBJ_DES(String obj_des) {
		sysObject.setOBJ_DES(obj_des);
	}

	public String getOBJ_TYPE() {	
		return sysObject.getOBJ_TYPE();
	}

	public void setOBJ_TYPE(String obj_type) {
		sysObject.setOBJ_TYPE(obj_type);
	}

	public String getOBJ_APPTYPE() {
		return sysObject.getOBJ_APPTYPE();
	}

	public void setOBJ_APPTYPE(String obj_apptype) {
		sysObject.setOBJ_APPTYPE(obj_apptype);
	}

	public String getSYS_ID() {
		return sysObject.getSYS_ID();
	}

	public void setSYS_ID(String sys_id) {
		sysObject.setSYS_ID(sys_id);
	}

	public Integer getF_STAU() {
		return sysObject.getF_STAU();
	}

	public void setF_STAU(Integer f_stau) {
		sysObject.setF_STAU(f_stau);
	}

	public java.util.Date getF_CRDATE() {
		return sysObject.getF_CRDATE();
	}

	public void setF_CRDATE(java.util.Date f_crdate) {
		sysObject.setF_CRDATE(f_crdate);
	}

	public java.util.Date getF_CHDATE() {
		return sysObject.getF_CHDATE();
	}

	public void setF_CHDATE(java.util.Date f_chdate) {
		sysObject.setF_CHDATE(f_chdate);
	}

	public Integer getId() {
		return 0;
	}
}
