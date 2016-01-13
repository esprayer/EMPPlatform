package com.mrp.persistence.sysConfigure.product.mapper;

import java.util.List;

import com.mrp.persistence.sysConfigure.product.bean.HYCPZD;

import dwz.dal.BaseMapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface HYCPZDMapper extends BaseMapper<HYCPZD,String>{
	// 查询
	List<HYCPZD> findByObjId(@Param("F_CPBH") String F_CPBH, @Param("F_SYZT") String F_SYZT);
	
	// 查询
	HYCPZD loadHYCP(@Param("F_CPBH") String F_CPBH, @Param("F_SYZT") String F_SYZT);
	
	// 删除
	void deleteHYCP(@Param("F_CPBH") String F_CPBH);
}
