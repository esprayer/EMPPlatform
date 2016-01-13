package com.dms.biz.permission;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.annotations.Param;

import com.dms.persistence.permission.bean.DMS_FOLDER_PERMISSION;

import dwz.framework.sys.business.BusinessObjectServiceMgr;

public interface FolderPermissionServiceMgr extends BusinessObjectServiceMgr {
	void insertfolderPermission(DMS_FOLDER_PERMISSION permission,HttpServletRequest request);
	String insertfolderPermission(DMS_FOLDER_PERMISSION permission,String content,HttpServletRequest request);
	List<DMS_FOLDER_PERMISSION>  folderPermissionList(String F_FOLDERID, String F_BMBH, String F_USERID);
	DMS_FOLDER_PERMISSION  loadFolderPermission(String F_ID);
	DMS_FOLDER_PERMISSION  loadFolderPermission(String F_FOLDERID, String F_ID);
	void updateFolderPermission(DMS_FOLDER_PERMISSION po);
	int deleteFolderPermission(String F_ID, String FOLDER_ID);
	List<DMS_FOLDER_PERMISSION> loadFolderPermission(@Param("F_FOLDERID") String F_FOLDERID, @Param("F_BMBH") String F_BMBH, @Param("F_USERID") String F_USERID);
}
