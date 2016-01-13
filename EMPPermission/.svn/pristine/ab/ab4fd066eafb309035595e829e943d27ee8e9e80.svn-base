package com.service.qxgl;

import java.util.List;

import com.framework.sys.business.BusinessObjectServiceMgr;
import com.persistence.qxgl.beans.SYSRole;

public interface SYSRoleServiceMgr extends BusinessObjectServiceMgr {
	//部门
	List<SYSRole> searchRoles(int startIndex, int count);
	List<SYSRole> searchRoles(String roleinfo, int startIndex, int count);
	SYSRole getRole(String rolebh);
	void addRole(SYSRole roleObj);
	void updateRole(SYSRole roleObje);
	void delRole(String rolebh);
}
