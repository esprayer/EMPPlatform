package com.mrp.persistence.sysConfigure.department.mapper;

import java.util.List;

import com.mrp.persistence.sysConfigure.department.bean.HYBMZD;

import dwz.dal.BaseMapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface HYBMZDMapper extends BaseMapper<HYBMZD,String>{
	// 查询
	List<HYBMZD> findBySYZT(@Param("F_SYZT") String F_SYZT);
	
}
