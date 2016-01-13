package com.service.qxgl.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.efounder.eai.data.JParamObject;
import com.efounder.eai.data.JResponseObject;
import com.framework.sys.business.AbstractBusinessObjectServiceMgr;
import com.persistence.qxgl.beans.BaseUSRole;
import com.persistence.qxgl.beans.RoleFpCommand;
import com.persistence.qxgl.beans.SYSRole;
import com.persistence.qxgl.beans.SYSUser;
import com.persistence.qxgl.mapper.SYSYhjsqxMapper;
import com.server.EMPDALPermissionManager;
import com.server.EMPPrivilegeUtil;
import com.service.qxgl.SYSYhjsqxServiceMgr;

@Transactional(rollbackFor = Exception.class)
@Service("yhjsqxServiceMgr")
public class SYSYhjsqxServiceMgrImpl extends AbstractBusinessObjectServiceMgr implements SYSYhjsqxServiceMgr {
	@Autowired
	private SYSYhjsqxMapper yhjsqxMapper;

	@Autowired
	private EMPPrivilegeUtil privilegeUtil;
	
	@Autowired
	private EMPDALPermissionManager permissionManager;
	
	@Override
	public void saveUsRole(RoleFpCommand command) {
		yhjsqxMapper.deleteUserRoles(command.getUSER_ID());
		List<String> listRole = command.getRoles();
		for(String roleid : listRole){
			BaseUSRole obj = new BaseUSRole();
			obj.setUSER_ID(command.getUSER_ID());
			obj.setROLE_ID(roleid);
			yhjsqxMapper.saveUserRoles(obj);
		}
		
	}

	/**
	 * 查询用户已分配的角色
	 */
	@Override
	public List<SYSRole> queryUserRole(String user_id) {
		List<SYSRole> bos = yhjsqxMapper.queryUserRole(user_id);
		return bos;
	}

	/**
	 * 查询用户未分派的角色
	 */
	@Override
	public List<SYSRole> queryWfpRole(String user_id) {
		List<SYSRole> bos = yhjsqxMapper.queryWfpRole(user_id);
		return bos;
	}

	@Override
	public List<SYSRole> queryRoleByUserId(String user_id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<SYSUser> queryUserByRoleId(String roleId) {
		List<SYSUser> bos = yhjsqxMapper.queryRoleUser(null,roleId);
		return bos;
	}


	@Override
	public void saveUserGnqx(String ywxtId,String user_id,String menuIds) {
		String[] arryMenu = menuIds.split(",");
		Map map = null;
		String access = ywxtId+"MENU";
		processYwxtQx(ywxtId,user_id,"user");
		for(int i=0;i<arryMenu.length;i++){
			String id = "USER-"+user_id+"-"+access+"-"+arryMenu[i];
			yhjsqxMapper.delGnqxById(id);
			
			map = new HashMap();
			map.put("F_ID", id);
			map.put("F_MASTER", "USER");
			map.put("MASTER_VALUE", user_id);
			map.put("F_ACCESS", access);
			map.put("ACCESS_VALUE", arryMenu[i]);
			map.put("F_OP", "1");
			yhjsqxMapper.saveGnqx(map);
		}
	}

	public void processYwxtQx(String ywxtId,String obj_id,String type) {
		Map map = null;
		String master = "USER";
		if(type.equals("role"))
			master = "ROLE";
		
		String master_value = obj_id;
		String access = "APP";
		String access_value = ywxtId;
		String qxid = master+"-"+master_value+"-"+access+"-"+access_value;
		int count = yhjsqxMapper.isExistsYwxtQx(qxid);
		if(count<=0){
			map  = new HashMap();
			map.put("F_ID", qxid);
			map.put("F_MASTER", master);
			map.put("MASTER_VALUE", master_value);
			map.put("F_ACCESS", access);
			map.put("ACCESS_VALUE", access_value);
			map.put("F_OP", "1");
			yhjsqxMapper.saveGnqx(map);
		}
	}
	
	@Override
	public List<Map> queryGnqx(Map paramMap) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Map> queryAllMenu(String app_id) {
		List<Map> bos = yhjsqxMapper.queryMenuTree(app_id);
		return bos;
	}


	@Override
	public List<Map> queryRoleYfpGn(String user_id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Map> queryRoleWfpGn(String user_id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Map> querySubMenuByUser(String ywxtId,String user_id, int js, String menu_bh) {
		List<Map> bos = yhjsqxMapper.querySubMenu(ywxtId,user_id, js, menu_bh);
		return bos;
	}

	@Override
	public void delUserGnqx(String ywxtId,String user_id, String menuIds) {
		String[] arryMenu = menuIds.split(",");
		Map map = null;
		boolean bExit = false;
		String access = ywxtId+"MENU";
		String id = "";
		
		for(int i=0;i<arryMenu.length;i++){
			id = "USER-"+user_id+"-"+access+"-"+arryMenu[i];
			yhjsqxMapper.delGnqxById(id);
		}
		bExit = privilegeUtil.checkUserPrivilege("USER-"+user_id+"-"+access+"-");
		if(!bExit) {
			id = "USER-"+user_id+"-APP-"+ywxtId;
			yhjsqxMapper.delGnqxById(id);
		}
	}

	@Override
	public List<SYSUser> queryRoleUser(String org_id, String roleId) {
		return yhjsqxMapper.queryRoleUser(org_id, roleId);
	}

	@Override
	public List<SYSUser> queryWfpUser(String org_id, String roleId) {
		return yhjsqxMapper.queryWfpUser(org_id, roleId);
	}

	@Override
	public void saveRoleUs(String roleId, String userIds) {
		if(null==userIds) return;
		String[] users = userIds.split(",");
		for(String userId : users){
			BaseUSRole obj = new BaseUSRole();
			obj.setUSER_ID(userId);
			obj.setROLE_ID(roleId);
			yhjsqxMapper.saveUserRoles(obj);
		}
	}

	@Override
	public void deleteRoleUs(String roleId, String userIds) {
		if(null==userIds) return;
		String[] users = userIds.split(",");
		for(String userId : users){
			BaseUSRole obj = new BaseUSRole();
			obj.setUSER_ID(userId);
			obj.setROLE_ID(roleId);
			yhjsqxMapper.deleteUserVsRole(obj);
		}
	}

	@Override
	public List<Map> querySubMenuByRole(String ywxtId,String role_id, int js, String menu_bh) {
		List<Map> bos = yhjsqxMapper.querySubMenuByRole(ywxtId,role_id, js, menu_bh);
		return bos;
	}

	@Override
	public List<Map> querySubMenu(String ywxtId,String type, String obj_id, int js,
			String menu_bh) {
		List<Map> bos  = null;
		if(null!=type&&type.equals("role")){
			bos = querySubMenuByRole(ywxtId,obj_id, js, menu_bh);
		}else{
			bos = querySubMenuByUser(ywxtId,obj_id, js, menu_bh);
		}
		return bos;
	}

	@Override
	public void saveRoleGnqx(String ywxtId, String role_id, String menuIds) {
		String[] arryMenu = menuIds.split(",");
		Map map = null;
		String access = ywxtId+"MENU";
		this.processYwxtQx(ywxtId, role_id, "role");
		for(int i=0;i<arryMenu.length;i++){
			String id = "ROLE-"+role_id+"-"+access+"-"+arryMenu[i];
			yhjsqxMapper.delGnqxById(id);
			
			map = new HashMap();
			map.put("F_ID", id);
			map.put("F_MASTER", "ROLE");
			map.put("MASTER_VALUE", role_id);
			map.put("F_ACCESS", access);
			map.put("ACCESS_VALUE", arryMenu[i]);
			map.put("F_OP", "1");
			yhjsqxMapper.saveGnqx(map);
		}
	}

	@Override
	public void delRoleGnqx(String ywxtId,String role_id, String menuIds) {
		String[] arryMenu = menuIds.split(",");
		Map map = null;
		
		boolean bExit = false;
		String access = ywxtId+"MENU";
		String id = "";
		for(int i=0;i<arryMenu.length;i++){
			id = "ROLE-"+role_id+"-"+access+"-"+arryMenu[i];
			yhjsqxMapper.delGnqxById(id);
		}
		bExit = privilegeUtil.checkUserPrivilege("ROLE-"+role_id+"-"+access+"-");
		if(!bExit) {
			id = "ROLE-"+role_id+"-APP-"+ywxtId;
			yhjsqxMapper.delGnqxById(id);
		}
	}

	public JResponseObject saveUSGN(JParamObject PO) {
		return permissionManager.batchSaveUSGN(PO);
	}
}
