package com.service.base;

import java.util.List;

import com.framework.sys.business.BusinessObjectServiceMgr;
import com.persistence.qxgl.beans.SYSMenu;
import com.persistence.qxgl.beans.SYSYwxt;

public interface SYSMenuServiceMgr extends BusinessObjectServiceMgr {
	//功能菜单管理
	List<SYSMenu> searchMenu(String appId,int startIndex, int count);
	List<SYSMenu> searchMenu(String menuBh,String appId,int js, int startIndex, int count);
	SYSMenu getMenu(String menuBh,String appId);
	void addMenu(SYSMenu menuObj);
	void updateMenu(SYSMenu menuObj);
	void delMenu(String menuBh,String appId);
	List<SYSYwxt> queryYwxt();
}
