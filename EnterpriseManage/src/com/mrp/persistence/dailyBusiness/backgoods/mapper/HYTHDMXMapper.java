package com.mrp.persistence.dailyBusiness.backgoods.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.mrp.persistence.dailyBusiness.backgoods.bean.HYTHDMX;
import com.mrp.persistence.dailyBusiness.outBound.bean.HYCKDMX;

import dwz.dal.BaseMapper;

@Repository
public interface HYTHDMXMapper extends BaseMapper<HYTHDMX,String>{
	// 查询
	List<HYTHDMX>  loadHYTHDMX(@Param("F_KJQJ") String F_KJQJ, @Param("F_DJBH") String F_DJBH);
	
	// 查询
	List<HYCKDMX>  loadHYCLCKMXByKey(@Param("F_KJQJ") String F_KJQJ, @Param("F_DJBH") String F_DJBH, @Param("F_GUID") String F_GUID, @Param("F_FLBH") String F_FLBH);
	
	// 删除
	void deleteHYCLCKMX(@Param("F_KJQJ") String F_KJQJ, @Param("F_DJBH") String F_DJBH, @Param("F_FLBH") String F_FLBH);
	
	// 更新入库单明细退货数量
	void updateHYRKDMXTHCL(@Param("F_KJQJ") String F_KJQJ, @Param("F_DJBH") String F_DJBH);
	
	void insertbatch(@Param("list") List<HYTHDMX> list);
}
