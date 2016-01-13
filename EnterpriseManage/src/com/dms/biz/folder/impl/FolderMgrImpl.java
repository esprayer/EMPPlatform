package com.dms.biz.folder.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dms.biz.folder.FolderServiceMgr;
import com.dms.persistence.folder.bean.DMS_FOLDER;
import com.dms.persistence.folder.mapper.DMS_FOLDERMapper;

import dwz.framework.sys.business.AbstractBusinessObjectServiceMgr;
import esyt.framework.persistence.qxgl.beans.SYSUser;

@Transactional(rollbackFor = Exception.class)
@Service("folderServiceMgr")
public class FolderMgrImpl extends AbstractBusinessObjectServiceMgr implements FolderServiceMgr {

	@Autowired
	private DMS_FOLDERMapper folderMapper ;

	//--------------------------------------------------------------------------------------------------
	//描述:创建目录，返回值：-1：文档已经存在；0：创建失败；1：创建成功
	//设计: ES
	//实现: ES
	//修改:
	//--------------------------------------------------------------------------------------------------
	public int createDirectory(DMS_FOLDER folderObject) {
		return createDirectory(folderObject, "0");
	}

	public int createDirectory(DMS_FOLDER folderObject, String F_DELETE) {
		DMS_FOLDER folder = folderMapper.loadFolder(folderObject.getF_PARENTID(), folderObject.getF_NAME(), F_DELETE);
		if(folder != null) return -1;
		Date now = new Date();
		folderObject.setF_CRDATE(now);
		folderObject.setF_CHDATE(now);
		try {
			folderMapper.insert(folderObject);
			return 1;
		} catch(Exception ce) {
			ce.printStackTrace();
			return 0;
		}
	}
	
	//--------------------------------------------------------------------------------------------------
	//描述:获取包含目录
	//设计: ES
	//实现: ES
	//修改:
	//--------------------------------------------------------------------------------------------------
	public List<DMS_FOLDER> folderList(String FOLDER_ID, SYSUser user) {		
		return folderList(FOLDER_ID, user, "0");
	}

	public List<DMS_FOLDER> folderList(String FOLDER_ID, SYSUser user, String F_DELETE) {
		List<DMS_FOLDER> folderList = folderMapper.loadFolderList(FOLDER_ID, F_DELETE);
		return folderList;
	}
	
	public DMS_FOLDER loadFolder(String F_FOLDERID) {		
		return loadFolder(F_FOLDERID, "0");
	}

	public DMS_FOLDER loadFolder(String F_FOLDERID, String F_DELETE) {		
		return folderMapper.searchDirectory(F_FOLDERID, F_DELETE);
	}
	
	public int updateFolder(DMS_FOLDER folder) {
		Date now = new Date();
		DMS_FOLDER po = folderMapper.load(folder.getF_FOLDERID());
		
		if(po != null) {
			po.setF_NAME(folder.getF_NAME());
			po.setF_CHDATE(now);
			po.setF_DESC(folder.getF_DESC());
			folderMapper.update(po);
		}
		return 0;
	}

	//--------------------------------------------------------------------------------------------------
	//描述:查询目录
	//设计: ES
	//实现: ES
	//修改:
	//--------------------------------------------------------------------------------------------------
	public DMS_FOLDER searchFolder(String F_PARENTID, String F_NAME) {
		return folderMapper.loadFolder(F_PARENTID, F_NAME, "0");
	}

	public List<DMS_FOLDER> loadPermissionFolderList(String NODETYPE, SYSUser user) {		
		return loadPermissionFolderList(NODETYPE, user, "0");
	}
	
	//--------------------------------------------------------------------------------------------------
	//描述:查询根目录下所有有权限的目录
	//设计: ES
	//实现: ES
	//修改:
	//--------------------------------------------------------------------------------------------------
	public List<DMS_FOLDER> loadPermissionFolderList(String NODETYPE, SYSUser user, String F_DELETE) {
		List<DMS_FOLDER> folderList = folderMapper.searchFolderByPermission(NODETYPE, F_DELETE);
		List<DMS_FOLDER> list = new ArrayList<DMS_FOLDER>();
		DMS_FOLDER po = null;
		DMS_FOLDER po1 = null;
		Map<String, String> folderMap = new HashMap<String, String>();

		//循环节点，建立索引
		for(int i = 0; i < folderList.size(); i++) {
			po = folderList.get(i);
			folderMap.put(po.getF_FOLDERID(), po.getF_FOLDERID());
		}
		
		//循环所有有权限的节点，如果当前循环的节点上级，存在结果集中，则删除该循环节点
		for(int i = 0; i < folderList.size(); i++) {
			po = folderList.get(i);
			//查找当前父目录
			po1 = folderMapper.searchDirectory(po.getF_PARENTID(), F_DELETE);
			//如果不存在，已经查找到根节点
			if(po1 == null) list.add(po);
			//如果存在，则继续向上查找，直到找到
			else {
				while(po1 != null) {
					if(folderMap.get(po1.getF_FOLDERID()) == null) {
						po1 = folderMapper.searchDirectory(po1.getF_PARENTID(), F_DELETE);
					} else {
						folderMap.remove(po.getF_FOLDERID());
						break;
					}
				}
			}
		}
		
		//因为查询出来的节点是已经排序的，所以重新循环节点，将排除的节点删掉
		for(int i = folderList.size() - 1; i >= 0 ; i--) {
			if(folderMap.get(folderList.get(i).getF_FOLDERID()) == null) folderList.remove(i);
		}
		return folderList;
	}

	/**
	 * 删除文件夹
	 */
	public void deleteFolder(List<String> folderList, SYSUser user) {
		List<DMS_FOLDER> folder = null;
		for(int i = 0; i < folderList.size(); i++) {
			folder = folderMapper.loadFolderList(folderList.get(i).toString(), "0");
			if(folder.size() > 0) continue;
			folderMapper.deleteFolder(folderList.get(i).toString(), "1", user.getUSER_ID(), new Date());
		}
	}

	//--------------------------------------------------------------------------------------------------
	//描述:获取该用户删除目录
	//设计: ES
	//实现: ES
	//修改:
	//--------------------------------------------------------------------------------------------------
	public List<DMS_FOLDER> loadDeleteFolder(String F_DEL_USERID) {		
		return folderMapper.loadDeleteFolder(F_DEL_USERID);
	}

	/**
	 * 彻底删除文件夹
	 */
	public void completelyDelete(List<String> folderList, SYSUser user) {
		for(int i = 0; i < folderList.size(); i++) {
			folderMapper.completelyDelete(folderList.get(i).toString());
		}
	}

	/**
	 * 还原文件夹
	 */
	public void reduceFolder(List<String> folderList, SYSUser user) {
		for(int i = 0; i < folderList.size(); i++) {
			folderMapper.deleteFolder(folderList.get(i).toString(), "0", null, null);
		}
	}
}