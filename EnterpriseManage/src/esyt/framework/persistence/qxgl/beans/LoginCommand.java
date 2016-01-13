package esyt.framework.persistence.qxgl.beans;

import dwz.dal.object.AbstractDO;

public class LoginCommand extends AbstractDO {
	private String userId = "";
	private String password = "";
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
}

