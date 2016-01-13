package dwz.persistence.beans;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import dwz.dal.object.AbstractDO;

public class SYS_OBJECT extends AbstractDO{
	private String         OBJ_ID;
	private String         OBJ_MC;
	private String         OBJ_DES;
	private String         OBJ_TYPE;
	private String         OBJ_APPTYPE;
	private String         SYS_ID;
	private Integer        F_STAU;
	private java.util.Date F_CRDATE;
	private java.util.Date F_CHDATE;

	public SYS_OBJECT(){
	}

	public SYS_OBJECT(String OBJ_ID){
		this.OBJ_ID = OBJ_ID;
	}

	public String getOBJ_ID() {
		return OBJ_ID;
	}

	public void setOBJ_ID(String obj_id) {
		OBJ_ID = obj_id;
	}

	public String getOBJ_MC() {
		return OBJ_MC;
	}

	public void setOBJ_MC(String obj_mc) {
		OBJ_MC = obj_mc;
	}

	public String getOBJ_DES() {
		return OBJ_DES;
	}

	public void setOBJ_DES(String obj_des) {
		OBJ_DES = obj_des;
	}

	public String getOBJ_TYPE() {
		return OBJ_TYPE;
	}

	public void setOBJ_TYPE(String obj_type) {
		OBJ_TYPE = obj_type;
	}

	public String getOBJ_APPTYPE() {
		return OBJ_APPTYPE;
	}

	public void setOBJ_APPTYPE(String obj_apptype) {
		OBJ_APPTYPE = obj_apptype;
	}

	public String getSYS_ID() {
		return SYS_ID;
	}

	public void setSYS_ID(String sys_id) {
		SYS_ID = sys_id;
	}

	public Integer getF_STAU() {
		return F_STAU;
	}

	public void setF_STAU(Integer f_stau) {
		F_STAU = f_stau;
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

