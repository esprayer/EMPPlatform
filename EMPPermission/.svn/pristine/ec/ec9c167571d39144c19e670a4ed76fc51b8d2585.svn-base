package com.service.base.impl;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.framework.sys.business.AbstractBusinessObjectServiceMgr;
import com.persistence.qxgl.beans.SYSMenu;
import com.persistence.qxgl.beans.SYSYwxt;
import com.persistence.qxgl.mapper.SYSMenuMapper;
import com.persistence.qxgl.mapper.SYSYwxtMapper;
import com.service.base.SYSMenuServiceMgr;

@Transactional(rollbackFor = Exception.class)
@Service("sysMenuServiceMgr")
public class SYSMenuServiceMgrImpl extends AbstractBusinessObjectServiceMgr implements SYSMenuServiceMgr {
	@Autowired
	private SYSYwxtMapper ywxtMapper;
	@Autowired
	private SYSMenuMapper menuMapper;
	
	@Override
	public void addMenu(SYSMenu menuObj) {		
		menuMapper.insert(menuObj);
		menuMapper.updateParent(menuObj.getF_PARENT(), menuObj.getAPP_ID(),"0");
	}

	@Override
	public void updateMenu(SYSMenu menuObj) {	
		menuMapper.update(menuObj);
	}
	@Override
	public List<SYSYwxt> queryYwxt() {
		return ywxtMapper.findAll();
	}

	@Override
	public List<SYSMenu> searchMenu(String appId, int startIndex, int count) {
		return menuMapper.findAllMenu(appId);
	}

	@Override
	public List<SYSMenu> searchMenu(String menuBh, String appId,int js,
			int startIndex, int count) {
		return menuMapper.findSubMenuByBh(menuBh, appId,js+1);
	}

	@Override
	public SYSMenu getMenu(String menuBh, String appId) {
		// TODO Auto-generated method stub
		return menuMapper.findMenuByBh(menuBh, appId);
	}

	@Override
	public void delMenu(String menuBh, String appId) {
		SYSMenu sysMenu = menuMapper.findMenuByBh(menuBh, appId);
		menuMapper.deleteMenu(menuBh,appId);
		int count = menuMapper.getSubMenuCount(sysMenu.getF_PARENT(), appId);
		if(count==0){
			menuMapper.updateParent(sysMenu.getF_PARENT(), appId,"1");
		}
	}

	
}
