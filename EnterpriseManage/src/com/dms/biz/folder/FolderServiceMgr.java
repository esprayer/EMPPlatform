package com.dms.biz.folder;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.annotations.Param;

import com.dms.persistence.folder.bean.DMS_FOLDER;

import dwz.framework.sys.business.BusinessObjectServiceMgr;
import esyt.framework.persistence.qxgl.beans.SYSUser;

public interface FolderServiceMgr extends BusinessObjectServiceMgr {
	int createDirectory(DMS_FOLDER folderObject);
	List<DMS_FOLDER> folderList(String PATH, SYSUser user);
	List<DMS_FOLDER> folderList(String PATH, SYSUser user, String F_DELETE);
	List<DMS_FOLDER> loadPermissionFolderList(String NODETYPE, SYSUser user);
	List<DMS_FOLDER> loadPermissionFolderList(String NODETYPE, SYSUser user, String F_DELETE);
	DMS_FOLDER loadFolder(String F_FOLDERID);
	DMS_FOLDER loadFolder(String F_FOLDERID, String F_DELETE);
	int updateFolder(DMS_FOLDER folder);
	DMS_FOLDER searchFolder(String F_PARENTID, String F_NAME);
	//列举该用户删除的目录
	List<DMS_FOLDER> loadDeleteFolder(@Param("F_DEL_USERID") String F_DEL_USERID);
	void deleteFolder(List<String> folderList,SYSUser user);
	void reduceFolder(List<String> folderList,SYSUser user);
	void completelyDelete(List<String> folderList,SYSUser user);
}
