package com.dms.persistence.sysLogger.mapper;

import java.util.List;

import dwz.dal.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.dms.persistence.sysLogger.bean.DMS_SYSLOG;

@Repository
public interface DMS_SYSLOGMapper extends BaseMapper<DMS_SYSLOG,String>{

	void  insertSYSLog(@Param("sysLog") DMS_SYSLOG sysLog);

	// 列举目录下所有文档
	List<DMS_SYSLOG>  SYSLogList(@Param("F_CREATOR") String F_CREATOR);
}
