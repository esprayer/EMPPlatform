package com.dms.biz.permission.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.etsoft.pub.util.JCompareObject;
import com.etsoft.pub.util.StringFunction;
import com.dms.biz.permission.FolderPermissionServiceMgr;
import com.dms.persistence.folder.bean.DMS_FOLDER;
import com.dms.persistence.folder.mapper.DMS_FOLDERMapper;
import com.dms.persistence.permission.bean.DMS_FOLDER_PERMISSION;
import com.dms.persistence.permission.mapper.DMS_FOLDER_PERMISSIONMapper;
import com.mrp.persistence.sysConfigure.department.bean.HYBMZD;
import com.mrp.persistence.sysConfigure.department.mapper.HYBMZDMapper;

import dwz.framework.sys.business.AbstractBusinessObjectServiceMgr;
import esyt.framework.persistence.qxgl.beans.SYSUser;
import esyt.framework.persistence.qxgl.mapper.SYSUserMapper;

@Transactional(rollbackFor = Exception.class)
@Service("FolderPermissionServiceMgr")
public class FolderPermissionServiceMgrImpl extends AbstractBusinessObjectServiceMgr implements FolderPermissionServiceMgr {

	@Autowired
	private DMS_FOLDER_PERMISSIONMapper permissionMapper ;
	
	@Autowired
	private DMS_FOLDERMapper folderMapper ;

	@Autowired
	private HYBMZDMapper bmzdMapper ;
	
	@Autowired
	private SYSUserMapper userMapper ;
	
	/**
	 * 加载全部权限,本级目录继承父目录的权限，一直到根目录
	 */
	public List<DMS_FOLDER_PERMISSION> folderPermissionList(String F_FOLDERID, String F_BMBH, String F_USERID) {
		if(F_BMBH == null || F_BMBH.equals("")) F_BMBH = null;
		if(F_USERID == null || F_USERID.equals("")) F_USERID = null;

		//从当前目录向上查找，一直找到根目录，将路径放到队列中，用于从根目录开始循环，将目录和下级目录的权限进行合并
		Queue<String> folderQueue = allFolderList(F_FOLDERID);
		
		//取出最上一级目录
		String folderID = folderQueue.poll();

		//当前循环目录权限
		List<DMS_FOLDER_PERMISSION> childrenList = new ArrayList<DMS_FOLDER_PERMISSION>();
		//当前目录的上级目录权限
		List<DMS_FOLDER_PERMISSION> parentList = new ArrayList<DMS_FOLDER_PERMISSION>();
		
		//如果根目录编号不为空，则查找根目录的权限
		if(folderID != null) {
			parentList = permissionMapper.searchPermit(folderID, null, null);
		}
		
		//如果队列不为空，循环取出每个目录的全部权限，和上级权限作比较，并合并
		if(folderQueue.size() > 1) {
			//循环队列
	        while((folderID = folderQueue.poll()) != null){ 	        	
	        	//加载当前循环目录权限
	        	childrenList = permissionMapper.searchPermit(folderID, null, null);
	        	
	        	//将当前循环目录权限与该目录的父目录权限合并
	            if(childrenList != null) {
	            	parentList = mergePermission(parentList, childrenList);
	            }
	            if(folderQueue.size() <= 1) break;
	        }  
		}
		folderID = folderQueue.poll();
		
		//取出当前选择目录权限，如果当前目录为根目录childrenList为空根目录权限为parentList
		if(folderID != null) {
			//加载当前目录权限
	    	childrenList = permissionMapper.searchPermit(folderID, null, null);
		}
		
		//当前选择目录集成所有上级目录权限
		//因为参数调用时地址引用，所以不需要返回值，也不知道这样好不好，反正是可以的，看着办
		extendsParentFolderPermission(parentList,childrenList);
		return childrenList;
	}
	
	/**
	 * 插入权限
	 */
	public void insertfolderPermission(DMS_FOLDER_PERMISSION permission, HttpServletRequest request) {
		Date now = new Date();
		permission.setF_CHDATE(now);
		permission.setF_CRDATE(now);
		permissionMapper.insert(permission);
	}

	/**
	 * 删除权限
	 */
	public int deleteFolderPermission(String F_ID, String FOLDER_ID) {
		DMS_FOLDER_PERMISSION po = permissionMapper.load(F_ID);
		//目录相同，说明删除的是本目录的权限
		if(po.getF_FOLDERID().equals(FOLDER_ID)) {
			permissionMapper.delete(F_ID);
			return 1;
		} 
		//否则为上级继承下来的权限，不能删除
		else {
			return -1;
		}		
	}

	/**
	 * 编辑某个权限，加载该权限，如果某个权限是继承的上级，则不允许编辑
	 */
	public DMS_FOLDER_PERMISSION loadFolderPermission(String F_FOLDERID, String F_ID) {
		String permission = "";
		DMS_FOLDER_PERMISSION  po = permissionMapper.loadPermit(F_FOLDERID, F_ID);
		DMS_FOLDER_PERMISSION  mergePO = new DMS_FOLDER_PERMISSION();
		DMS_FOLDER_PERMISSION departmentPO = new DMS_FOLDER_PERMISSION();
		DMS_FOLDER_PERMISSION userPO = new DMS_FOLDER_PERMISSION();

		//如果当前显示权限是继承的上级权限，则逐级查找上级，一直到权限编号存在的目录
		DMS_FOLDER_PERMISSION  po1 = loadSpecificFolderPermission(F_FOLDERID, F_ID);
		
		//部门目录权限
		List<DMS_FOLDER_PERMISSION> departmentList = loadDepartmentFolderPermission(F_FOLDERID, F_ID, po1);
		//用户目录权限
		List<DMS_FOLDER_PERMISSION> userList = new ArrayList<DMS_FOLDER_PERMISSION>();
		 
		departmentPO = mergeDepartmentOnePermission(departmentList);
		
		if(po == null) {
			po = new DMS_FOLDER_PERMISSION();
			po.setF_FOLDERID(F_FOLDERID);
			po.setF_ID(F_ID);
		}
		
		if(po1.getF_USERID().trim().equals("")) {	
			permission += extendsSuperiorsPermission(departmentPO, po);
			po.setF_PERMISSION(permission);
			return po;
		} else {
			userList = loadUserFolderPermission(F_FOLDERID, F_ID, po1);
			userPO = mergeUserOnePermission(userList);
			mergePO = mergeDepartmentUserOnePermission(departmentPO, userPO);
			permission = extendsSuperiorsPermission(mergePO, po);
			po.setF_PERMISSION(permission);
		}
		return po;
	}
	
	/**
	 * 加载某个权限
	 */
	public DMS_FOLDER_PERMISSION loadFolderPermission(String F_ID) {
		DMS_FOLDER_PERMISSION  po = permissionMapper.load(F_ID);
		return po;
	}
	
	/**
	 * 更新权限
	 */
	public void updateFolderPermission(DMS_FOLDER_PERMISSION po) {
		permissionMapper.update(po);
	}

	/**
	 * 批量加入权限
	 */
	public String insertfolderPermission(DMS_FOLDER_PERMISSION permission, String content, HttpServletRequest request) {
		List<DMS_FOLDER_PERMISSION> list = new ArrayList<DMS_FOLDER_PERMISSION>();
		DMS_FOLDER_PERMISSION po = null;
		String[] permissionList = content.split(",");
		String[] value;
		HYBMZD bmzd = null;
		SYSUser user = null;
		String errorMsg = "";

		boolean bCheck = false;
		
		for(int i = 0; i < permissionList.length; i++) { 
			Date now = new Date();
			value = permissionList[i].split(";");
			if(value.length < 2) continue;
			//检查权限是否存在
			bCheck = checkPermission(permission.getF_FOLDERID(), value);
			if(!bCheck) {
				if(value[0].equals("0")) {
					bmzd = bmzdMapper.load(value[1]);
					errorMsg += "<br>[部门]" + bmzd.getF_BMMC() + "(" + value[1] + ")权限已经存在，不允许重复添加";
				} else {
					user = userMapper.loadUser(value[1], value[2]);
					errorMsg += "<br>[用户]" + user.getUSER_NAME() + "(" + value[2] + ")权限已经存在，不允许重复添加";
				}	
				continue;
			}
			
			po = new DMS_FOLDER_PERMISSION();
			po.setF_FOLDERID(permission.getF_FOLDERID());
			po.setF_ID(StringFunction.generateKey());
			po.setF_CREATOR(permission.getF_CREATOR());
			po.setF_PERMISSION(permission.getF_PERMISSION());
			po.setF_CRDATE(now);
			po.setF_CHDATE(now);
			if(value[0].equals("0")) {
				po.setF_BMBH(value[1]);
				po.setF_USERID("");
			} else {
				po.setF_BMBH(value[1]);
				po.setF_USERID(value[2]);
			}
			list.add(po);
		}
		if(list.size() > 0)	permissionMapper.insertbatch(list);
		return errorMsg;
	}
	
	/**
	 * 加载目录权限
	 */
	public List<DMS_FOLDER_PERMISSION> loadFolderPermission(String F_FOLDERID, String F_BMBH, String F_USERID) {
		return folderPermissionList(F_FOLDERID, null, null);
	}
	
	/**
	 * 当前选择目录集成父目录权限
	 */
	private void extendsParentFolderPermission(List<DMS_FOLDER_PERMISSION> parentList, List<DMS_FOLDER_PERMISSION> currentList) {
		Map<String, DMS_FOLDER_PERMISSION> departmentpermissionMap = departmentPermission(parentList);	
		Map<String, DMS_FOLDER_PERMISSION> userpermissionMap = userPermission(parentList);
		Map<String, String> newpermissionMap = new HashMap<String, String>();
		DMS_FOLDER_PERMISSION po = null;
		DMS_FOLDER_PERMISSION parentPO = null;
		String permission = "";
		Iterator         iterator = null;
		
		//先将本机部门权限和上级部门权限进行合并，再处理上级的用户权限
		for(int i = 0; i < currentList.size(); i++) {
			po = currentList.get(i);
			if(!po.getF_USERID().equals("")) continue;
			
			if(departmentpermissionMap.get(po.getF_BMBH()) != null) {
				parentPO = departmentpermissionMap.get(po.getF_BMBH());		
				permission += extendsSuperiorsPermission(parentPO, po);
				parentPO.setF_PERMISSION(permission);
			} else {
				departmentpermissionMap.put(po.getF_BMBH(), po);
			}			
		}
		
		//上级权限中，用户权限继承部门权限
		userExtendsDepartmentPermission(departmentpermissionMap, userpermissionMap);
		
		//如果当前选择目录没有权限，则全部集成父目录权限
		if(currentList.size() == 0) {
			//循环上级目录每个权限节点
			for(int i = 0; i < parentList.size(); i++) {
				parentPO = parentList.get(i);
				po = copyPermission(parentPO);
				permission = extendsSuperiorsPermission(parentPO, null);
				
				if(!po.getF_USERID().equals("") && departmentpermissionMap.get(po.getF_BMBH()) != null) {
					parentPO = departmentpermissionMap.get(po.getF_BMBH());		
					permission += extendsSuperiorsPermission(parentPO, po);
				}
				po.setF_PERMISSION(permission);
				currentList.add(po);
				
				//该权限是部门权限
				if(po.getF_USERID().equals("")) {
					newpermissionMap.put(po.getF_BMBH(), po.getF_BMBH());
				}
				//该权限是用户权限
				else {
					newpermissionMap.put(po.getF_BMBH() + "@" + po.getF_USERID(), po.getF_BMBH() + "@" + po.getF_USERID());
				}
			}
		} else {
			//循环当前选择目录，继承相对应的上级目录权限
			for(int i = 0; i < currentList.size(); i++) {
				po = currentList.get(i);
				permission = "";
				//该权限是部门权限
				if(po.getF_USERID().equals("")) {
					if(departmentpermissionMap.get(po.getF_BMBH()) != null) {
						parentPO = departmentpermissionMap.get(po.getF_BMBH());					
					} else {
						parentPO = new DMS_FOLDER_PERMISSION();
					}
					permission += extendsSuperiorsPermission(parentPO, po);
					po.setF_PERMISSION(permission);
					parentPO.setF_PERMISSION(permission);
					departmentpermissionMap.put(po.getF_BMBH(), parentPO);
					newpermissionMap.put(po.getF_BMBH(), po.getF_BMBH());
				}
				//该权限是用户权限
				else {
					if(userpermissionMap.get(po.getF_BMBH() + "@" + po.getF_USERID()) != null) {
						parentPO = userpermissionMap.get(po.getF_BMBH() + "@" + po.getF_USERID());					
					} else {
						parentPO = new DMS_FOLDER_PERMISSION();
					}
					permission += extendsSuperiorsPermission(parentPO, po);
					po.setF_PERMISSION(permission);

					if(departmentpermissionMap.get(po.getF_BMBH()) != null) {
						parentPO = departmentpermissionMap.get(po.getF_BMBH());		
						permission += extendsSuperiorsPermission(parentPO, po);
						po.setF_PERMISSION(permission);	
					}
					
					newpermissionMap.put(po.getF_BMBH() + "@" + po.getF_USERID(), po.getF_BMBH() + "@" + po.getF_USERID());
				}
			}
		}
		
		
		//如果上级目录权限在当前选择目录中不存在，则添加
		//判断部门
		iterator = departmentpermissionMap.entrySet().iterator();
		while(iterator.hasNext()){
     		Map.Entry m = (Map.Entry) iterator.next();
     		if(newpermissionMap.get(m.getKey().toString()) == null) {
     			currentList.add((DMS_FOLDER_PERMISSION)m.getValue());
     		}
		}
		
		//判断用户
		iterator = userpermissionMap.entrySet().iterator();
		while(iterator.hasNext()){
     		Map.Entry m = (Map.Entry) iterator.next();
     		if(newpermissionMap.get(m.getKey().toString()) == null) {
     			currentList.add((DMS_FOLDER_PERMISSION)m.getValue());
     		}
		}
		
		Comparator ct = new JCompareObject();
        Collections.sort(currentList, ct);
	}
	
	/**
	 * 调整权限，将上级权限都设置为只读
	 * @param list
	 */
	private void adjustPermissions(List<DMS_FOLDER_PERMISSION> list) {
		DMS_FOLDER_PERMISSION po = null;
		String permission = "";
		String newPermission = "";
		
		for(int i = 0; i < list.size(); i++) {
			po = list.get(i);
			permission = po.getF_PERMISSION();
			if(permission.indexOf("M") > -1) {
				newPermission += "M0";
			}
			if(permission.indexOf("L") > -1) {
				newPermission += "L0";
			}
			if(permission.indexOf("C") > -1) {
				newPermission += "C0";
			}
			if(permission.indexOf("R") > -1) {
				newPermission += "R0";
			}
			if(permission.indexOf("D") > -1) {
				newPermission += "D0";
			}
			if(permission.indexOf("P") > -1) {
				newPermission += "P0";
			}
			if(permission.indexOf("U") > -1) {
				newPermission += "U0";
			}
			if(permission.indexOf("E") > -1) {
				newPermission += "E0";
			}
			po.setF_PERMISSION(newPermission);
		}
	}
	
	/**
	 * 上级目录所有权限中，用户继承部门权限
	 * @param superiorsFolderPermission
	 * @param selectFolderPermission
	 * @return
	 */
	private void userExtendsDepartmentPermission(Map<String, DMS_FOLDER_PERMISSION> departmentpermissionMap,
												 Map<String, DMS_FOLDER_PERMISSION> userpermissionMap) {
		String permission = "";
		Iterator         iterator = null;
		DMS_FOLDER_PERMISSION userPermission = null;
		DMS_FOLDER_PERMISSION departmentPermission = null;
		
		//循环所有用户权限，集成部门权限
		iterator = userpermissionMap.entrySet().iterator();
		while(iterator.hasNext()){
     		Map.Entry m = (Map.Entry) iterator.next();
     		userPermission = (DMS_FOLDER_PERMISSION) m.getValue();
     		if(departmentpermissionMap.get(userPermission.getF_BMBH()) != null) {
     			departmentPermission = departmentpermissionMap.get(userPermission.getF_BMBH());
     			permission = extendsSuperiorsPermission(departmentPermission, userPermission);
     			userPermission.setF_PERMISSION(permission);
     		}
		}
	}
	
	/**
	 * 继承上级权限
	 * @param superiorsFolderPermission
	 * @param selectFolderPermission
	 * @return
	 */
	private String extendsSuperiorsPermission(DMS_FOLDER_PERMISSION parentPermission,DMS_FOLDER_PERMISSION childPermission) {
		String permission = "";
		permission += existsParentPermission(parentPermission, childPermission, "M");
		permission += existsParentPermission(parentPermission, childPermission, "L");
		permission += existsParentPermission(parentPermission, childPermission, "C");
		permission += existsParentPermission(parentPermission, childPermission, "R");
		permission += existsParentPermission(parentPermission, childPermission, "D");
		permission += existsParentPermission(parentPermission, childPermission, "P");
		permission += existsParentPermission(parentPermission, childPermission, "U");
		permission += existsParentPermission(parentPermission, childPermission, "E");
		return permission;
	}
	
	/**
	 * 复制权限
	 * @param sourcePermission 来源权限
	 * @param targetPermission 目标权限
	 */
	private DMS_FOLDER_PERMISSION copyPermission(DMS_FOLDER_PERMISSION sourcePermission) {
		DMS_FOLDER_PERMISSION targetPermission = new DMS_FOLDER_PERMISSION();
		targetPermission.setF_BMBH(sourcePermission.getF_BMBH());
		targetPermission.setF_BMMC(sourcePermission.getF_BMMC());
		targetPermission.setF_CREATOR(sourcePermission.getF_CREATOR());
		targetPermission.setF_FOLDERID(sourcePermission.getF_FOLDERID());
		targetPermission.setF_ID(sourcePermission.getF_ID());
		targetPermission.setF_PERMISSION(sourcePermission.getF_PERMISSION());
		targetPermission.setF_USERID(sourcePermission.getF_USERID());
		targetPermission.setF_USERNAME(sourcePermission.getF_USERNAME());
		return targetPermission;
		
	}
	
	/**
	 * 判断当前目录是否具有父目录对应的权限
	 */
	private String existsParentPermission(DMS_FOLDER_PERMISSION parentPermission, DMS_FOLDER_PERMISSION currentPermission, String permission) {
		boolean parentExistsPermission = false;
		boolean currentExistsPermission = false;
		
		if(parentPermission == null) {
			parentExistsPermission = false;
		} else {
			parentExistsPermission = existsPermission(parentPermission, permission);
		}
		if(currentPermission == null) {
			currentExistsPermission = false;
		} else {
			currentExistsPermission = existsPermission(currentPermission, permission);
		}
		
		if(currentExistsPermission) return permission + "1";
		if(parentExistsPermission && !currentExistsPermission) return permission + "0";		
		return "";
	}
	
	/**
	 * 判断当前目录是否具有父目录对应的权限
	 */
	private boolean existsPermission(DMS_FOLDER_PERMISSION folderPermission, String permission) {
		String F_PERMISSION = folderPermission.getF_PERMISSION();
		if(F_PERMISSION == null) return false; 
		if(F_PERMISSION.indexOf(permission) > -1) return true;
		else return false;
	}
	
	/**
	 * 递归调用该方法，返回从根目录到本级的路径集合
	 */
	private Queue<String> allFolderList(String FOLDER_ID) {
		Queue<String> folderQueue = new LinkedList<String>(); 
		DMS_FOLDER folder = folderMapper.searchDirectory(FOLDER_ID, "0");
		
		//如果folder为空，则表示该节点为根目录
		if(folder == null) return folderQueue;
		else {
			folderQueue = allFolderList(folder.getF_PARENTID());
			folderQueue.offer(folder.getF_FOLDERID());
		}
		return folderQueue;
	}
	
	/**
	 * 将上级目录权限和本级目录部门和用户权限进行合并
	 */
	private List<DMS_FOLDER_PERMISSION> mergePermission (List<DMS_FOLDER_PERMISSION> parentList, List<DMS_FOLDER_PERMISSION> childrenList){
		List<DMS_FOLDER_PERMISSION> permissionList = new ArrayList<DMS_FOLDER_PERMISSION>();
		
		//将上级和本级部门权限进行合并，并将合并后的权限形成索引
		Map<String, DMS_FOLDER_PERMISSION> departmentpermissionMap = mergeDepartmentPermission(parentList, childrenList);
		
		//将上级和本级用户权限进行合并，并将合并后的权限形成索引
		Map<String, DMS_FOLDER_PERMISSION> userpermissionMap = mergeUserPermission(parentList, childrenList);
		
		
		DMS_FOLDER_PERMISSION po = null;
		DMS_FOLDER_PERMISSION bmpo = null;
		Iterator         iterator = null;
		
		//将合并后的部门权限放到List中
		iterator = departmentpermissionMap.entrySet().iterator();
		while(iterator.hasNext()){
     		Map.Entry m = (Map.Entry) iterator.next();
     		permissionList.add((DMS_FOLDER_PERMISSION) m.getValue());
		}
		
		//将合并后的用户权限放到List中
		iterator = userpermissionMap.entrySet().iterator();
		while(iterator.hasNext()){
     		Map.Entry m = (Map.Entry) iterator.next();
     		permissionList.add((DMS_FOLDER_PERMISSION) m.getValue());
		}
		return permissionList;
	}
	
	/**
	 * 将上级目录权限和下级目录部门权限进行合并
	 */
	private Map<String, DMS_FOLDER_PERMISSION>  mergeDepartmentPermission(List<DMS_FOLDER_PERMISSION> parentList, List<DMS_FOLDER_PERMISSION> childrenList) {
		
		//上级部门权限建立索引，方便下级权限继承上级部门权限
		Map<String, DMS_FOLDER_PERMISSION> departmentpermissionMap = departmentPermission(parentList);	
		
		DMS_FOLDER_PERMISSION po = null;
		DMS_FOLDER_PERMISSION po1 = null;
		
		String key = "";
		
		//循环本级目录权限，如果当前节点权限存在上级，则将上级和本级权限合并
		//以上级所有权限位标准，下级存在则合并，不存在则插入，这样形成全集
		for(int i = 0; i < childrenList.size(); i++) {
			po = childrenList.get(i);
			
			//当前循环节点设置的是部门权限
			if(po.getF_USERID().equals("")) {
				key = po.getF_BMBH();
				
				//如果当前目录节点部门在上级也设置了权限，则将这两个权限进行合并
				if(departmentpermissionMap.get(key) != null) {
					po1 = departmentpermissionMap.get(key);
					po1.setF_PERMISSION(po1.getF_PERMISSION() + po.getF_PERMISSION());
				}
				//如果上级没有设置某个该部门权限，则插入，形成新的记录
				else {
					departmentpermissionMap.put(key, po);
				}
			}
		}
		
		return departmentpermissionMap;
	}
	
	/**
	 * 将上级目录权限和下级目录用户权限进行合并
	 */
	private Map<String, DMS_FOLDER_PERMISSION>  mergeUserPermission(List<DMS_FOLDER_PERMISSION> parentList, List<DMS_FOLDER_PERMISSION> childrenList) {
		Map<String, DMS_FOLDER_PERMISSION> userpermissionMap = userPermission(parentList);
		
		DMS_FOLDER_PERMISSION po = null;
		DMS_FOLDER_PERMISSION po1 = null;
		
		String key = "";

		for(int i = 0; i < childrenList.size(); i++) {
			po = childrenList.get(i);
			//当前循环节点设置的是用户权限
			if(!po.getF_USERID().trim().equals("")) {
				key = po.getF_BMBH() + "@" + po.getF_USERID();
				//如果当前目录节点部门在上级也设置了权限，则将这两个权限进行合并
				if(userpermissionMap.get(key) != null) {
					po1 = userpermissionMap.get(key);
					po1.setF_PERMISSION(po1.getF_PERMISSION() + po.getF_PERMISSION());
				}
				//如果上级没有设置某个该部门权限，则插入，形成新的记录
				else {
					userpermissionMap.put(key, po);
				}
			}
		}
		return userpermissionMap;
	}
	
	/**
	 * 将部门权限形式Map，建立唯一索引，方便下级目录继承上级目录
	 */
	private Map<String, DMS_FOLDER_PERMISSION> departmentPermission(List<DMS_FOLDER_PERMISSION> list) {
		Map<String, DMS_FOLDER_PERMISSION> permissionMap = new HashMap<String, DMS_FOLDER_PERMISSION>();
		DMS_FOLDER_PERMISSION po = null;
		String key = "";
		for(int i = 0; i <list.size(); i++) {
			po = list.get(i);
			if(!po.getF_USERID().trim().equals("")) continue;
			key = po.getF_BMBH();
			permissionMap.put(key, po);
		}
		return permissionMap;
	}
	
	/**
	 * 将用户权限形式Map，建立唯一索引，方便下级目录继承上级目录
	 */
	private Map<String, DMS_FOLDER_PERMISSION> userPermission(List<DMS_FOLDER_PERMISSION> list) {
		Map<String, DMS_FOLDER_PERMISSION> permissionMap = new HashMap<String, DMS_FOLDER_PERMISSION>();
		DMS_FOLDER_PERMISSION po = null;
		String key = "";
		for(int i = 0; i <list.size(); i++) {
			po = list.get(i);
			if(po.getF_USERID().equals("")) continue;
			key = po.getF_BMBH() + "@" + po.getF_USERID();
			permissionMap.put(key, po);
		}
		return permissionMap;
	}
	
	/**
	 * 加载部门权限
	 */
	private List<DMS_FOLDER_PERMISSION> loadDepartmentFolderPermission(String F_FOLDERID, String F_ID, DMS_FOLDER_PERMISSION  po) {
		//从当前目录向上查找，一直找到根目录，将路径放到队列中，用于从根目录开始循环，将目录和下级目录的权限进行合并
		Queue<String> folderQueue = allFolderList(F_FOLDERID);
		//当前循环目录权限
		List<DMS_FOLDER_PERMISSION> childrenList = new ArrayList<DMS_FOLDER_PERMISSION>();
		//当前目录的上级目录权限
		List<DMS_FOLDER_PERMISSION> parentList = new ArrayList<DMS_FOLDER_PERMISSION>();
		//当前目录
		String folderID = folderQueue.poll();

		//如果目录编号不为空，则查找当前目录当前部门的选择权限
		if(folderID != null) {
			parentList = permissionMapper.searchPermit(folderID, po.getF_BMBH(), "");
		}
		
		if(folderQueue.size() > 1) {
			//循环队列
	        while((folderID = folderQueue.poll()) != null){ 	        	
	        	//加载当前目录权限
	        	childrenList = permissionMapper.searchDepartmentPermit(folderID, po.getF_BMBH());
	        	
	        	//将当前目录权限与父目录权限合并
	            if(childrenList != null) {
	            	parentList = mergePermission(parentList, childrenList);
	            }
	            if(folderQueue.size() <= 1) break;
	        }  
		}
		folderID = folderQueue.poll();
		
		if(folderID != null) {
			//加载当前目录权限
	    	childrenList = permissionMapper.searchDepartmentPermit(folderID, po.getF_BMBH());
		}
		
		//因为参数调用时地址引用，所以不需要返回值，也不知道这样好不好，反正是可以的，看着办
		extendsParentFolderPermission(parentList,childrenList);
		return childrenList;
	}
	
	/**
	 * 查找该权限
	 * @param F_FOLDERID
	 * @param F_ID
	 * @return
	 */
	private DMS_FOLDER_PERMISSION loadSpecificFolderPermission(String F_FOLDERID, String F_ID) {
		DMS_FOLDER_PERMISSION po = null;
		DMS_FOLDER folder = folderMapper.searchDirectory(F_FOLDERID, "0");
		while(folder != null) {
			po = permissionMapper.loadPermit(folder.getF_FOLDERID(), F_ID);
			if(po != null) return po;
			folder = folderMapper.searchDirectory(folder.getF_PARENTID(), "0");
		}
		return null;
	}
	
	/**
	 * 加载用户权限
	 */
	private List<DMS_FOLDER_PERMISSION> loadUserFolderPermission(String F_FOLDERID, String F_ID, DMS_FOLDER_PERMISSION  po) {
		F_FOLDERID = po.getF_FOLDERID();
		
		//从当前目录向上查找，一直找到根目录，将路径放到队列中，用于从根目录开始循环，将目录和下级目录的权限进行合并
		Queue<String> folderQueue = allFolderList(F_FOLDERID);
		//当前循环目录权限
		List<DMS_FOLDER_PERMISSION> childrenList = new ArrayList<DMS_FOLDER_PERMISSION>();
		//当前目录的上级目录权限
		List<DMS_FOLDER_PERMISSION> parentList = new ArrayList<DMS_FOLDER_PERMISSION>();
		//当前目录
		String folderID = folderQueue.poll();
		//如果目录编号不为空，则查找当前目录当前部门的选择权限
		if(folderID != null) {
			parentList = permissionMapper.searchPermit(folderID, po.getF_BMBH(), po.getF_USERID());
		}
		
		if(folderQueue.size() > 1) {
			//循环队列
	        while((folderID = folderQueue.poll()) != null){ 	        	
	        	//加载当前目录权限
	        	childrenList = permissionMapper.searchUserPermit(folderID, po.getF_BMBH(), po.getF_USERID());
	        	
	        	//将当前目录权限与父目录权限合并
	            if(childrenList != null) {
	            	parentList = mergePermission(parentList, childrenList);
	            }
	            if(folderQueue.size() <= 1) break;
	        }  
		}
		folderID = folderQueue.poll();
		
		if(folderID != null) {
			//加载当前目录权限
	    	childrenList = permissionMapper.searchPermit(folderID, po.getF_BMBH(), po.getF_USERID());
		}
		
		//因为参数调用时地址引用，所以不需要返回值，也不知道这样好不好，反正是可以的，看着办
		extendsParentFolderPermission(parentList,childrenList);
		return childrenList;
	}
	
	/**
	 * 因为根据部门编号查找权限，所以部门编号一致，将部门权限合并一条
	 */
	private DMS_FOLDER_PERMISSION mergeDepartmentOnePermission(List<DMS_FOLDER_PERMISSION> list) {
		DMS_FOLDER_PERMISSION po = new DMS_FOLDER_PERMISSION();
		DMS_FOLDER_PERMISSION po1;
		String permission = "";
		if(list.size() > 0) {
			po = list.get(0);
			for(int i = 1; i < list.size(); i++) {
				po1 = list.get(i);
				permission += extendsSuperiorsPermission(po, po1);
				po.setF_PERMISSION(permission);
			}
		}
		return po;
	}
	
	/**
	 * 因为根据部门和用户编号查找权限，所以部门和用户编号一致，将部门和用户权限合并一条
	 */
	private DMS_FOLDER_PERMISSION mergeUserOnePermission(List<DMS_FOLDER_PERMISSION> list) {
		DMS_FOLDER_PERMISSION po = new DMS_FOLDER_PERMISSION();
		DMS_FOLDER_PERMISSION po1;
		String permission = "";
		if(list.size() > 0) {
			po = list.get(0);
			for(int i = 1; i < list.size(); i++) {
				po1 = list.get(i);
				permission += extendsSuperiorsPermission(po, po1);
				po.setF_PERMISSION(permission);
			}
		}
		return po;
	}
	
	/**
	 * 因为根据部门和用户编号查找权限，所以部门和用户编号一致，将用户权限继承部门权限，并合并一条
	 */
	private DMS_FOLDER_PERMISSION mergeDepartmentUserOnePermission(DMS_FOLDER_PERMISSION department, DMS_FOLDER_PERMISSION user) {
		String permission = "";
		permission += extendsSuperiorsPermission(department, user);
		user.setF_PERMISSION(permission);
		return user;
	}
	
	private boolean checkPermission(String F_FOLDERID, String[] value) {
		List<DMS_FOLDER_PERMISSION> po = null;
		if(value[0].equals("0")) {
			po = permissionMapper.searchDepartmentPermit(F_FOLDERID, value[1]);
			if(po == null || po.size() == 0) return true;
			else return false;
		} else {
			po = permissionMapper.searchUserPermit(F_FOLDERID, value[1], value[2]);
			if(po == null || po.size() == 0) return true;
			else return false;
		}
	}
}
