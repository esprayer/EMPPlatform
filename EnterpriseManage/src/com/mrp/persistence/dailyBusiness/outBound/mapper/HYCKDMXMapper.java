package com.mrp.persistence.dailyBusiness.outBound.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.mrp.persistence.dailyBusiness.outBound.bean.HYCKDMX;
import com.mrp.persistence.dailyBusiness.storage.bean.HYRKDMX;

import dwz.dal.BaseMapper;

@Repository
public interface HYCKDMXMapper extends BaseMapper<HYCKDMX,String>{
	// 查询
	HYRKDMX  loadHYCLRKMX(@Param("F_KJQJ") String F_KJQJ, @Param("F_GUID") String F_GUID, @Param("F_DJBH") String F_DJBH, @Param("F_FLBH") String F_FLBH);
	
	// 查询
	List<HYCKDMX>  loadHYCLCKMXByKey(@Param("F_KJQJ") String F_KJQJ, @Param("F_DJBH") String F_DJBH, @Param("F_GUID") String F_GUID, @Param("F_FLBH") String F_FLBH, @Param("F_FLGUID") String F_FLGUID);
	
	// 删除
	void deleteHYCLCKMX(@Param("F_KJQJ") String F_KJQJ, @Param("F_DJBH") String F_DJBH, @Param("F_FLBH") String F_FLBH);
	
	// 更新入库单明细退货数量
	void updateHYRKDMXTHCL(@Param("F_KJQJ") String F_KJQJ, @Param("F_DJBH") String F_DJBH);
}
