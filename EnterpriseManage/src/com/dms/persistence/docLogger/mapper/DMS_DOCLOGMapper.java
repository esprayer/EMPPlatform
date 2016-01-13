package com.dms.persistence.docLogger.mapper;

import java.util.List;

import dwz.dal.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.dms.persistence.docLogger.bean.DMS_DOCLOG;

@Repository
public interface DMS_DOCLOGMapper extends BaseMapper<DMS_DOCLOG,String>{
	// 列举目录下所有文档
	List<DMS_DOCLOG>  DOCLogList(@Param("F_DOCID") String F_DOCID, @Param("F_OPERATOR") String F_OPERATOR);
	
	// 删除为文档的日志
	void  deleteDocLog(@Param("F_DOCID") String F_DOCID);
}
