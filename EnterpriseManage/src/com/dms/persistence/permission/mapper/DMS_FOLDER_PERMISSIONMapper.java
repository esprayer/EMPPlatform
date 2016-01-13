package com.dms.persistence.permission.mapper;

import java.util.List;

import dwz.dal.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.dms.persistence.permission.bean.DMS_FOLDER_PERMISSION;

@Repository
public interface DMS_FOLDER_PERMISSIONMapper extends BaseMapper<DMS_FOLDER_PERMISSION,String>{
	// 查询权限
	List<DMS_FOLDER_PERMISSION> searchPermit(@Param("F_FOLDERID") String F_FOLDERID, @Param("F_BMBH") String F_BMBH, @Param("F_USERID") String F_USERID);
	DMS_FOLDER_PERMISSION loadPermit(@Param("F_FOLDERID") String F_FOLDERID, @Param("F_ID") String F_ID);
	void insertbatch(@Param("list") List<DMS_FOLDER_PERMISSION> list);
	List<DMS_FOLDER_PERMISSION> searchDepartmentPermit(@Param("F_FOLDERID") String F_FOLDERID, @Param("F_BMBH") String F_BMBH);
	List<DMS_FOLDER_PERMISSION> searchUserPermit(@Param("F_FOLDERID") String F_FOLDERID, @Param("F_BMBH") String F_BMBH, @Param("F_USERID") String F_USERID);	
}
