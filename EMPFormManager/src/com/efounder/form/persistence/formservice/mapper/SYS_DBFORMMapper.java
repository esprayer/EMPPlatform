package com.efounder.form.persistence.formservice.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.dal.BaseMapper;
import com.efounder.form.persistence.formservice.bean.SYS_DBFORM;

@Repository
public interface SYS_DBFORMMapper extends BaseMapper<SYS_DBFORM,String>{
	// 查询
	List<SYS_DBFORM> loadSYS_DBFORM(@Param("BBZD_BH") String BBZD_BH);
}
