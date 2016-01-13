package com.mrp.persistence.dailyBusiness.transfers.mapper;

import java.util.List;

import dwz.dal.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.mrp.persistence.dailyBusiness.storage.bean.HYRKD;
import com.mrp.persistence.dailyBusiness.transfers.bean.HYDBD;

@Repository
public interface HYDBDMapper extends BaseMapper<HYDBD,String>{
	// 查询
	List<HYDBD>  loadHYDBD(@Param("F_KJQJ") String F_KJQJ, @Param("F_DJZT") String F_DJZT, 
			               @Param("F_CKBH") String F_CKBH, @Param("F_DJBH") String F_DJBH, 
			               @Param("F_GUID") String F_GUID);
}
