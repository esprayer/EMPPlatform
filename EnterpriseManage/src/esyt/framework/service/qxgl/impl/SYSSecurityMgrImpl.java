package esyt.framework.service.qxgl.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dwz.framework.sys.business.AbstractBusinessObjectServiceMgr;
import esyt.framework.persistence.qxgl.beans.SYSMenu;
import esyt.framework.persistence.qxgl.beans.SYSRole;
import esyt.framework.persistence.qxgl.beans.SYSUser;
import esyt.framework.persistence.qxgl.beans.SYSYwxt;
import esyt.framework.persistence.qxgl.mapper.SYSSecurityMapper;
import esyt.framework.persistence.qxgl.mapper.SYSYhjsqxMapper;
import esyt.framework.service.base.SYSMenuServiceMgr;
import esyt.framework.service.qxgl.SYSSecurityMgr;

@Transactional(rollbackFor = Exception.class)
@Service("SYSSecurityMgr")
public class SYSSecurityMgrImpl extends AbstractBusinessObjectServiceMgr implements SYSSecurityMgr {
	@Autowired
	private SYSSecurityMapper securityMapper;
	@Autowired
	private SYSYhjsqxMapper yhjsqxMapper;
	@Autowired
	private SYSMenuServiceMgr menuService;
	
	@Override
	public List<SYSYwxt> queryUserYwxt(SYSUser user) {
		
		return null;
	}

	@Override
	public List<SYSYwxt> queryUserYwxt(String userId) {
		return securityMapper.queryUserYwxt(userId);
	}

	@Override
	public List queryUserMenu(String user, String ywxt) {
		
		return securityMapper.queryUserMenuOfApp(user, ywxt);
	}
	
	@Override
	public List queryUsersGnqx(String user, String ywxt) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isHaveYwxtQx() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isHaveMenuQx() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isHaveGnqx() {
		// TODO Auto-generated method stub
		return false;
	}
	
}
