package esyt.framework.service.qxgl.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dwz.framework.sys.business.AbstractBusinessObjectServiceMgr;
import esyt.framework.persistence.qxgl.beans.SYSRole;
import esyt.framework.persistence.qxgl.mapper.SYSRoleMapper;
import esyt.framework.service.qxgl.SYSRoleServiceMgr;

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
