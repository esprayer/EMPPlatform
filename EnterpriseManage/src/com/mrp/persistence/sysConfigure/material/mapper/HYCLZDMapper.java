package com.mrp.persistence.sysConfigure.material.mapper;

import java.util.List;

import com.mrp.persistence.sysConfigure.material.bean.HYCLZD;

import dwz.dal.BaseMapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface HYCLZDMapper extends BaseMapper<HYCLZD,String>{
	List<HYCLZD> findByKey(@Param("F_XMBH") String F_XMBH);
}
