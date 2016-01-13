package com.mrp.persistence.sysConfigure.deport.mapper;

import java.util.List;

import com.mrp.persistence.sysConfigure.deport.bean.HYCKZD;

import dwz.dal.BaseMapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface HYCKZDMapper extends BaseMapper<HYCKZD,String>{
	// 查询
	List<HYCKZD> findCkByBm(@Param("F_BMBH") String F_BMBH);
	
	// 查询
	List<HYCKZD> findCkByBms(@Param("F_BMBH") String F_BMBH, @Param("F_CKBH") String F_CKBH);
	
	// 查询
	HYCKZD loadHYCK(@Param("F_BMBH") String F_BMBH, @Param("F_CKBH") String F_CKBH);
	
	// 查询
	HYCKZD loadHYCKZD(@Param("F_CKBH") String F_CKBH);
	
	// 删除
	void deleteHYCK(@Param("F_BMBH") String F_BMBH, @Param("F_CKBH") String F_CKBH);
}
