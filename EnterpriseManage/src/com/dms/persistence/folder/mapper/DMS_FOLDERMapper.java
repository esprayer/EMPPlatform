package com.dms.persistence.folder.mapper;

import java.util.List;

import dwz.dal.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.dms.persistence.folder.bean.DMS_FOLDER;

@Repository
public interface DMS_FOLDERMapper extends BaseMapper<DMS_FOLDER,String>{
	// 新建目录
	void  createDirectory(@Param("folderObject") DMS_FOLDER folderObject);

	//查询目录
	DMS_FOLDER loadFolder(@Param("F_PARENTID") String F_PARENTID, @Param("F_NAME") String F_NAME, @Param("F_DELETE") String F_DELETE);
	
	// 查找指定目录
	DMS_FOLDER  searchDirectory(@Param("F_FOLDERID") String F_FOLDERID, @Param("F_DELETE") String F_DELETE);
	
	// 列举目录下所有自己创建的文档
	List<DMS_FOLDER>  loadFolderList(@Param("F_PARENTID") String F_PARENTID, @Param("F_DELETE") String F_DELETE);	
	
	// 根据用户列举目录下所有有权限的文档
	List<DMS_FOLDER>  loadOtherFolderByUser(@Param("F_CREATOR") String F_CREATOR, @Param("F_USERID") String F_USERID, @Param("F_FOLDERID") String F_FOLDERID);
	
	// 根据部门列举目录下所有有权限的文档
	List<DMS_FOLDER>  loadOtherFolderByBm(@Param("F_CREATOR") String F_CREATOR, @Param("F_BMBH") String F_BMBH, @Param("F_FOLDERID") String F_FOLDERID);
	
	//列举有预览权限的目录
	List<DMS_FOLDER> searchFolderByPermission(@Param("F_TYPE") String F_TYPE, @Param("F_DELETE") String F_DELETE);
	
	//列举该用户删除的目录
	List<DMS_FOLDER> loadDeleteFolder(@Param("F_DEL_USERID") String F_DEL_USERID);
	
	void deleteFolder(@Param("F_FOLDERID") String F_FOLDERID, @Param("F_DELETE") String F_DELETE, @Param("F_DEL_USERID") String F_DEL_USERID, @Param("F_DELDATE") java.util.Date F_DELDATE);
	
	void completelyDelete(@Param("F_FOLDERID") String F_FOLDERID);
}
