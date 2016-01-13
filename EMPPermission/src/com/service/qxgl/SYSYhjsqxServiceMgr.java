package com.service.qxgl;

import java.util.List;
import java.util.Map;

import com.efounder.builder.base.data.EFDataSet;
import com.efounder.eai.data.JParamObject;
import com.efounder.eai.data.JResponseObject;
import com.framework.sys.business.BusinessObjectServiceMgr;
import com.persistence.qxgl.beans.RoleFpCommand;
import com.persistence.qxgl.beans.SYSRole;
import com.persistence.qxgl.beans.SYSUser;

public interface SYSYhjsqxServiceMgr extends BusinessObjectServiceMgr {
	
	void saveUsRole(RoleFpCommand command);         //保存用户角色对应关系
	
	void saveRoleUs(String roleId,String userIds);         //保存角色对应的用户
	
	void deleteRoleUs(String roleId,String userIds);       ///删除角色对应的用户
	
	List<SYSRole> queryRoleByUserId(String user_id); //查询所有角色
	List<SYSRole> queryUserRole(String user_id); //查询用户已分配角色
	List<SYSRole> queryWfpRole(String user_id);  //查询用户未分配角色

	List<SYSUser> queryUserByRoleId(String roleId); //查询角色已对应的用户
	List<SYSUser> queryRoleUser(String org_id, String roleId); //查询角色已对应的用户
	List<SYSUser> queryWfpUser(String org_id, String roleId);  //查询未对应角色的用户

	
	void saveUserGnqx(String ywxtId,String user_id,String menuIds);       //保存用户功能权限
	void delUserGnqx(String ywxtId,String user_id,String menuIds);       //保存用户功能权限

	void saveRoleGnqx(String ywxtId,String user_id, String menuIds);       //保存角色功能权限
	void delRoleGnqx(String ywxtId,String user_id,String menuIds);       //删除用户功能权限

	JResponseObject saveUSGN(JParamObject PO);       //保存角色功能权限

	List<Map> queryGnqx(Map paramMap);   //查询已分配功能
	List<Map> queryAllMenu(String app_id);   //查询已分配功能
	List<Map> queryRoleYfpGn(String user_id);   //查询已分配功能
	List<Map> queryRoleWfpGn(String user_id);   //查询已分配功能
	List<Map> querySubMenu(String ywxtId,String type,String obj_id,int js, String menu_bh);
	List<Map> querySubMenuByRole(String ywxtId,String role_id,int js, String menu_bh);
	List<Map> querySubMenuByUser(String ywxtId,String user_id,int js, String menu_bh);
}
