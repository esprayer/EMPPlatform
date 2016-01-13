package com.mrp.persistence.sysConfigure.company.mapper;

import java.util.List;

import com.mrp.persistence.sysConfigure.company.bean.HYCSZD;

import dwz.dal.BaseMapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface HYCSZDMapper extends BaseMapper<HYCSZD,String>{
	// 查询
	List<HYCSZD> findById(@Param("F_CSBH") String F_CSBH, @Param("F_SYZT") String F_SYZT);
	
	// 查询
	List<HYCSZD> findBySYZT(@Param("F_SYZT") String F_SYZT);
	
	// 查询
	List<HYCSZD> findCkByBms(@Param("F_BMBH") String F_BMBH, @Param("F_CKBH") String F_CKBH);
	
	// 查询
	HYCSZD loadHYCK(@Param("F_BMBH") String F_BMBH, @Param("F_CKBH") String F_CKBH);
	
	// 删除
	void deleteHYCK(@Param("F_BMBH") String F_BMBH, @Param("F_CKBH") String F_CKBH);
}
