package com.persistence.qxgl.beans;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.dal.object.AbstractDO;

/**
 * 用户bean
 * 
 * @author frog
 * 
 */
public class UserGnFpCommand extends AbstractDO {
	private String USER_ID = "";
	private List<String> ids = new ArrayList();
	public String getUser_id() {
		return USER_ID;
	}
	public void setUser_id(String user_id) {
		this.USER_ID = user_id;
	}
	public List<String> getUsgn() {
		return ids;
	}
	public void setUsgn(List<String> usgn) {
		this.ids = usgn;
	}
}