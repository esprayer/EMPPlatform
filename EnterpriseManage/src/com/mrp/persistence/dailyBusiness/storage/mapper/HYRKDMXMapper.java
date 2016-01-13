package com.mrp.persistence.dailyBusiness.storage.mapper;

import java.util.List;

import dwz.dal.BaseMapper;
import com.mrp.persistence.sysConfigure.deport.bean.HYCKZD;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.mrp.persistence.dailyBusiness.storage.bean.HYRKDMX;

@Repository
public interface HYRKDMXMapper extends BaseMapper<HYRKDMX,String>{
	// 查询
	List<HYCKZD> findCkByBm(@Param("F_BMBH") String F_BMBH);
	
	// 查询
	HYRKDMX  loadHYCLRKMX(@Param("F_KJQJ") String F_KJQJ, @Param("F_GUID") String F_GUID, @Param("F_DJBH") String F_DJBH, @Param("F_FLBH") String F_FLBH);
	
	// 查询
	List<HYRKDMX>  loadHYCLRKMXByKey(@Param("F_KJQJ") String F_KJQJ, @Param("F_DJBH") String F_DJBH, @Param("F_GUID") String F_GUID, @Param("F_FLBH") String F_FLBH, @Param("F_FLGUID") String F_FLGUID);
	
	// 删除
	void deleteHYCLRKMX(@Param("F_KJQJ") String F_KJQJ, @Param("F_DJBH") String F_DJBH, @Param("F_FLGUID") String F_FLGUID, @Param("F_FLBH") String F_FLBH);
}
