package com.mrp.persistence.dailyBusiness.storage.mapper;

import java.util.List;

import dwz.dal.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.mrp.persistence.dailyBusiness.storage.bean.HYRKD;

@Repository
public interface HYRKDMapper extends BaseMapper<HYRKD,String>{
	// 查询
	List<HYRKD>  loadHYRKD(@Param("F_KJQJ") String F_KJQJ, @Param("F_DJZT") String F_DJZT, 
			               @Param("F_DATE") String F_DATE, @Param("F_CKBH") String F_CKBH, 
			               @Param("F_DJBH") String F_DJBH, @Param("F_GUID") String F_GUID,
			               @Param("F_RKLX") String F_RKLX);
}
