package com.mrp.persistence.dailyBusiness.depositary.mapper;

import dwz.dal.BaseMapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.mrp.persistence.dailyBusiness.depositary.bean.HYCK;

@Repository
public interface HYCKMapper extends BaseMapper<HYCK,String>{
	// 查询
	HYCK loadHYCK(@Param("F_CLBH") String F_CLBH, @Param("F_DWBH") String F_DWBH, @Param("F_CKBH") String F_CKBH, @Param("F_CLDJ") Double F_CLDJ);
}
