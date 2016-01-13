package com.mrp.persistence.dailyBusiness.backgoods.mapper;

import java.util.List;

import dwz.dal.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.mrp.persistence.dailyBusiness.backgoods.bean.HYTHD;

@Repository
public interface HYTHDMapper extends BaseMapper<HYTHD,String>{
	// 查询
	List<HYTHD>  loadHYTHD(@Param("F_KJQJ") String F_KJQJ, @Param("F_DJBH") String F_DJBH,
					  	   @Param("F_GUID") String F_GUID, @Param("F_DJZT") String F_DJZT, 
					  	   @Param("F_CKBH") String F_CKBH, @Param("F_XMBH") String F_XMBH, 
					  	   @Param("F_THLX") String F_THLX);
	
	//根据出库单查找对应的退货单
	List<HYTHD>  searchHYTHDByHYCKD(@Param("F_KJQJ") String F_KJQJ, @Param("F_DJBH") String F_DJBH,
		  	                        @Param("F_THLX") String F_THLX, @Param("F_DJZT") String F_DJZT);
}
