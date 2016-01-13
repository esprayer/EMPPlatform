package com.mrp.persistence.sysConfigure.user.mapper;

import java.util.List;

import com.mrp.persistence.sysConfigure.user.bean.HYZGZD;

import dwz.dal.BaseMapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface HYZGZDMapper extends BaseMapper<HYZGZD,String>{
	// 查询
	List<HYZGZD> findZgByBm(@Param("F_SXBM") String F_SXBM, @Param("F_SYZT") String F_SYZT);
	
	// 查询
	List<HYZGZD> findZgByBms(@Param("F_SXBM") String F_SXBM, @Param("F_ZGBH") String F_ZGBH, @Param("F_SYZT") String F_SYZT);
	
	// 查询
	HYZGZD loadHYZG(@Param("F_SXBM") String F_SXBM, @Param("F_ZGBH") String F_ZGBH);
	
	// 查询
	List<HYZGZD> loadHYZGByBh(@Param("F_ZGBH") String F_ZGBH);
	
	// 删除
	void deleteHYZG(@Param("F_SXBM") String F_SXBM, @Param("F_ZGBH") String F_ZGBH);
}
