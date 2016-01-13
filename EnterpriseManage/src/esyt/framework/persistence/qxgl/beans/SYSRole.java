package esyt.framework.persistence.qxgl.beans;

import dwz.dal.object.AbstractDO;
/**
 * 角色bean
 * @author frog
 *
 */
public class SYSRole extends AbstractDO {
	private String roleId      = "";			//角色ID
	private String roleName    = "";			//角色名称
	private int    js          = 0 ;    		//级数
	private String mx          = "";            //明细否
	private String parent      = "#root";	    //父节点	
	private String note        = "";	        // 备注

	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	
	public int getJs() {
		return js;
	}
	public void setJs(int js) {
		this.js = js;
	}
	public String getMx() {
		return mx;
	}
	public void setMx(String mx) {
		this.mx = mx;
	}
	public String getParent() {
		return parent;
	}
	public void setParent(String parent) {
		this.parent = parent;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	
}
