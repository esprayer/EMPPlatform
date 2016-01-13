package esyt.framework.persistence.qxgl.beans;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import dwz.dal.object.AbstractDO;

/**
 * 用户bean
 * 
 * @author frog
 * 
 */
public class RoleFpCommand extends AbstractDO {
	private String USER_ID = "";
	private List<String> roles = new ArrayList();
	public String getUSER_ID() {
		return USER_ID;
	}
	public void setUSER_ID(String uSER_ID) {
		USER_ID = uSER_ID;
	}
	public List<String> getRoles() {
		return roles;
	}
	public void setRoles(List<String> roles) {
		this.roles = roles;
	}
}