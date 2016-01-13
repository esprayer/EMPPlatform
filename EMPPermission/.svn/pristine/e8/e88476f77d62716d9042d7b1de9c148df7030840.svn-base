package com.service.qxgl.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.framework.sys.business.AbstractBusinessObjectServiceMgr;
import com.persistence.qxgl.beans.SYSRole;
import com.persistence.qxgl.mapper.SYSRoleMapper;
import com.service.qxgl.SYSRoleServiceMgr;

@Transactional(rollbackFor = Exception.class)
@Service("sysRoleServiceMgr")
public class SYSRoleServiceMgrImpl extends AbstractBusinessObjectServiceMgr implements SYSRoleServiceMgr {
	@Autowired
	private SYSRoleMapper roleMapper;
	
	public List<SYSRole> searchRoles(int startIndex, int count) {
		List<SYSRole> bos= roleMapper.findAll();
		return bos;
	}

	
	public List<SYSRole> searchRoles(String roleinfo, int startIndex, int count) {
		List<SYSRole> bos= roleMapper.findByObjId(roleinfo);
		return bos;
	}

	
	public SYSRole getRole(String rolebh) {
		SYSRole roleObj = roleMapper.load(rolebh);
		return roleObj;
	}

	
	public void addRole(SYSRole roleObj) {
		roleMapper.insert(roleObj);
	}

	
	public void updateRole(SYSRole roleObj) {
		roleMapper.update(roleObj);
	}

	
	public void delRole(String rolebh) {
		roleMapper.delete(rolebh);
	}

	
}
