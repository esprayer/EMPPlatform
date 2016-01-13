/*
 * Powered By [dwz4j-framework]
 * Web Site: http://j-ui.com
 * Google Code: http://code.google.com/p/dwz4j/
 * Generated 2012-07-29 17:54:23 by code generator
 */
package com.business;

import dwz.framework.sys.business.AbstractBusinessObject;
import dwz.persistence.beans.SYS_OBJCOL;
import dwz.persistence.beans.SYS_OBJECT;

public class MetadataColumnObject extends AbstractBusinessObject {
	private static final long serialVersionUID = 1L;
	private SYS_OBJCOL sysObjcol;

	/* generateConstructor */
	public MetadataColumnObject() {
		this.sysObjcol = new SYS_OBJCOL();
	}

	public SYS_OBJCOL getSYS_OBJCOL() {
		return this.sysObjcol;
	}
	
	public MetadataColumnObject(SYS_OBJCOL sysObjcol) {
		this.sysObjcol = sysObjcol;
	}

	public String getOBJ_ID() {
		return sysObjcol.getOBJ_ID();
	}

	public void setOBJ_ID(String obj_id) {
		sysObjcol.setOBJ_ID(obj_id);
	}

	public String getCOL_ID() {
		return sysObjcol.getCOL_ID();
	}

	public void setCOL_ID(String col_id) {
		sysObjcol.setCOL_ID(col_id);
	}

	public String getCOL_MC() {
		return sysObjcol.getCOL_MC();
	}

	public void setCOL_MC(String col_mc) {
		sysObjcol.setCOL_MC(col_mc);
	}

	public String getCOL_DES() {
		return sysObjcol.getCOL_DES();
	}

	public void setCOL_DES(String col_des) {
		sysObjcol.setCOL_DES(col_des);
	}

	public String getCOL_TYPE() {
		return sysObjcol.getCOL_TYPE();
	}

	public void setCOL_TYPE(String col_type) {
		sysObjcol.setCOL_TYPE(col_type);
	}

	public int getCOL_LEN() {
		return sysObjcol.getCOL_LEN();
	}

	public void setCOL_LEN(int col_len) {
		sysObjcol.setCOL_LEN(col_len);
	}

	public int getCOL_PREC() {
		return sysObjcol.getCOL_PREC();
	}

	public void setCOL_PREC(int col_prec) {
		sysObjcol.setCOL_PREC(col_prec);
	}

	public int getCOL_SCALE() {
		return sysObjcol.getCOL_SCALE();
	}

	public void setCOL_SCALE(int col_scale) {
		sysObjcol.setCOL_SCALE(col_scale);
	}

	public String getCOL_ISKEY() {
		return sysObjcol.getCOL_ISKEY();
	}

	public void setCOL_ISKEY(String col_iskey) {
		sysObjcol.setCOL_ISKEY(col_iskey);
	}

	public String getCOL_ISNULL() {
		return sysObjcol.getCOL_ISNULL();
	}

	public void setCOL_ISNULL(String col_isnull) {
		sysObjcol.setCOL_ISNULL(col_isnull);
	}

	public String getCOL_VISIBLE() {
		return sysObjcol.getCOL_VISIBLE();
	}

	public void setCOL_VISIBLE(String col_visible) {
		sysObjcol.setCOL_VISIBLE(col_visible);
	}

	public String getCOL_EDITABLE() {
		return sysObjcol.getCOL_EDITABLE();
	}

	public void setCOL_EDITABLE(String col_editable) {
		sysObjcol.setCOL_EDITABLE(col_editable);
	}

	public String getCOL_EDIT() {
		return sysObjcol.getCOL_EDIT();
	}

	public void setCOL_EDIT(String col_edit) {
		sysObjcol.setCOL_EDIT(col_edit);
	}

	public String getCOL_VIEW() {
		return sysObjcol.getCOL_VIEW();
	}

	public void setCOL_VIEW(String col_view) {
		sysObjcol.setCOL_VIEW(col_view);
	}

	public String getCOL_DEFAULT() {
		return sysObjcol.getCOL_DEFAULT();
	}

	public void setCOL_DEFAULT(String col_default) {
		sysObjcol.setCOL_DEFAULT(col_default);
	}

	public int getCOL_DISP() {
		return sysObjcol.getCOL_DISP();
	}

	public void setCOL_DISP(int col_disp) {
		sysObjcol.setCOL_DISP(col_disp);
	}

	public String getCOL_ISFKEY() {
		return sysObjcol.getCOL_ISFKEY();
	}

	public void setCOL_ISFKEY(String col_isfkey) {
		sysObjcol.setCOL_ISFKEY(col_isfkey);
	}

	public String getCOL_FOBJ() {
		return sysObjcol.getCOL_FOBJ();
	}

	public void setCOL_FOBJ(String col_fobj) {
		sysObjcol.setCOL_FOBJ(col_fobj);
	}

	public int getF_STAU() {
		return sysObjcol.getF_STAU();
	}

	public void setF_STAU(int f_stau) {
		sysObjcol.setF_STAU(f_stau);
	}

	public java.util.Date getF_CRDATE() {
		return sysObjcol.getF_CRDATE();
	}

	public void setF_CRDATE(java.util.Date f_crdate) {
		sysObjcol.setF_CRDATE(f_crdate);
	}

	public java.util.Date getF_CHDATE() {
		return sysObjcol.getF_CHDATE();
	}

	public void setF_CHDATE(java.util.Date f_chdate) {
		sysObjcol.setF_CHDATE(f_chdate);
	}

	public Integer getId() {
		return 0;
	}
}
