package dwz.persistence.beans;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import dwz.dal.object.AbstractDO;

public class SYS_OBJCOL extends AbstractDO{
	private String         OBJ_ID;
	private String         COL_ID;
	private String         COL_MC;
	private String         COL_DES;
	private String         COL_TYPE;
	private int            COL_LEN;
	private int            COL_PREC;
	private int            COL_SCALE;
	private String         COL_ISKEY;
	private String         COL_ISNULL;	
	private String         COL_VISIBLE;
	private String         COL_EDITABLE;
	private String         COL_EDIT;
	private String         COL_VIEW;
	private String         COL_DEFAULT;
	private int            COL_DISP;
	private String         COL_ISFKEY;
	private String         COL_FOBJ;
	private int            F_STAU;
	private java.util.Date F_CRDATE;
	private java.util.Date F_CHDATE;

	public SYS_OBJCOL(){
	}

	public SYS_OBJCOL(String OBJ_ID){
		this.OBJ_ID = OBJ_ID;
	}

	public String getOBJ_ID() {
		return OBJ_ID;
	}

	public void setOBJ_ID(String obj_id) {
		OBJ_ID = obj_id;
	}

	public String getCOL_ID() {
		return COL_ID;
	}

	public void setCOL_ID(String col_id) {
		COL_ID = col_id;
	}

	public String getCOL_MC() {
		return COL_MC;
	}

	public void setCOL_MC(String col_mc) {
		COL_MC = col_mc;
	}

	public String getCOL_DES() {
		return COL_DES;
	}

	public void setCOL_DES(String col_des) {
		COL_DES = col_des;
	}

	public String getCOL_TYPE() {
		return COL_TYPE;
	}

	public void setCOL_TYPE(String col_type) {
		COL_TYPE = col_type;
	}

	public int getCOL_LEN() {
		return COL_LEN;
	}

	public void setCOL_LEN(int col_len) {
		COL_LEN = col_len;
	}

	public int getCOL_PREC() {
		return COL_PREC;
	}

	public void setCOL_PREC(int col_prec) {
		COL_PREC = col_prec;
	}

	public int getCOL_SCALE() {
		return COL_SCALE;
	}

	public void setCOL_SCALE(int col_scale) {
		COL_SCALE = col_scale;
	}

	public String getCOL_ISKEY() {
		return COL_ISKEY;
	}

	public void setCOL_ISKEY(String col_iskey) {
		COL_ISKEY = col_iskey;
	}

	public String getCOL_ISNULL() {
		return COL_ISNULL;
	}

	public void setCOL_ISNULL(String col_isnull) {
		COL_ISNULL = col_isnull;
	}

	public String getCOL_VISIBLE() {
		return COL_VISIBLE;
	}

	public void setCOL_VISIBLE(String col_visible) {
		COL_VISIBLE = col_visible;
	}

	public String getCOL_EDITABLE() {
		return COL_EDITABLE;
	}

	public void setCOL_EDITABLE(String col_editable) {
		COL_EDITABLE = col_editable;
	}

	public String getCOL_EDIT() {
		return COL_EDIT;
	}

	public void setCOL_EDIT(String col_edit) {
		COL_EDIT = col_edit;
	}

	public String getCOL_VIEW() {
		return COL_VIEW;
	}

	public void setCOL_VIEW(String col_view) {
		COL_VIEW = col_view;
	}

	public String getCOL_DEFAULT() {
		return COL_DEFAULT;
	}

	public void setCOL_DEFAULT(String col_default) {
		COL_DEFAULT = col_default;
	}

	public int getCOL_DISP() {
		return COL_DISP;
	}

	public void setCOL_DISP(int col_disp) {
		COL_DISP = col_disp;
	}

	public String getCOL_ISFKEY() {
		return COL_ISFKEY;
	}

	public void setCOL_ISFKEY(String col_isfkey) {
		COL_ISFKEY = col_isfkey;
	}

	public String getCOL_FOBJ() {
		return COL_FOBJ;
	}

	public void setCOL_FOBJ(String col_fobj) {
		COL_FOBJ = col_fobj;
	}

	public int getF_STAU() {
		return F_STAU;
	}

	public void setF_STAU(int f_stau) {
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

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("OBJ_ID",this.getOBJ_ID())
			.append("COL_ID",this.getCOL_ID())
			.append("COL_MC",this.getCOL_MC())
			.append("COL_DES",this.getCOL_DES())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(this.getOBJ_ID())
			.append(this.getCOL_ID())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof SYS_OBJCOL == false) return false;
		if(this == obj) return true;
		SYS_OBJCOL other = (SYS_OBJCOL)obj;
		return new EqualsBuilder()
			.append(this.getOBJ_ID(),other.getOBJ_ID())
			.append(this.getCOL_ID(),other.getCOL_ID())
			.isEquals();
	}
}

