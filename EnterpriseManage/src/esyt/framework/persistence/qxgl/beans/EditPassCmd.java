package esyt.framework.persistence.qxgl.beans;

import dwz.dal.object.AbstractDO;

public class EditPassCmd extends AbstractDO {
	private String userId = "";
	private String oldPassword = "";
	private String newPassword = "";
	private String rnewPassword = "";
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getOldPassword() {
		return oldPassword;
	}
	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}
	public String getNewPassword() {
		return newPassword;
	}
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	public String getRnewPassword() {
		return rnewPassword;
	}
	public void setRnewPassword(String rnewPassword) {
		this.rnewPassword = rnewPassword;
	}
	
	
}

