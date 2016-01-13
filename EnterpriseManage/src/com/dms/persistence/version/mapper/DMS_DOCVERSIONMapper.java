package com.dms.persistence.version.mapper;

import java.util.List;

import dwz.dal.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.dms.persistence.version.bean.DMS_DOCVERSION;

@Repository
public interface DMS_DOCVERSIONMapper extends BaseMapper<DMS_DOCVERSION,String>{
	// 查找目录下文件
	List<DMS_DOCVERSION>  loadDocVersion(@Param("F_DOCID") String F_DOCID);
	
	// 查找某个文件的特定版本
	DMS_DOCVERSION loadDocVersionById(@Param("F_DOCID") String F_DOCID, @Param("F_VERSION_NUMBER") int F_VERSION_NUMBER);
	
	//获取最大版本号
	DMS_DOCVERSION  loadMaxDocVersion(@Param("F_DOCID") String F_DOCID);
	
	// 删除为文档的版本
	void  deleteDocVersion(@Param("F_DOCID") String F_DOCID);
}
