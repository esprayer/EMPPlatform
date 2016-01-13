package esyt.framework.persistence.qxgl.beans;

import dwz.dal.object.AbstractDO;
/**
 * 角色bean
 * @author frog
 *
 */
public class BaseUSRole extends AbstractDO {
	private String ROLE_ID = "";
	private String USER_ID = "";
	private String F_SH;
	public String getROLE_ID() {
		return ROLE_ID;
	}
	public void setROLE_ID(String ROLE_ID) {
		this.ROLE_ID = ROLE_ID;
	}
	public String getUSER_ID() {
		return USER_ID;
	}
	public void setUSER_ID(String USER_ID) {
		this.USER_ID = USER_ID;
	}
	public String getF_SH() {
		return F_SH;
	}
	public void setF_SH(String USER_ID) {
		this.F_SH = USER_ID;
	}
	
}
