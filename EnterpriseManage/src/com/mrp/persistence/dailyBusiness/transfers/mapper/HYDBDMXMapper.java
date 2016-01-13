package com.mrp.persistence.dailyBusiness.transfers.mapper;

import java.util.List;

import dwz.dal.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.mrp.persistence.dailyBusiness.transfers.bean.HYDBDMX;

@Repository
public interface HYDBDMXMapper extends BaseMapper<HYDBDMX,String>{
	// 查询
	List<HYDBDMX>  loadHYDBDMX(@Param("F_KJQJ") String F_KJQJ, @Param("F_DJBH") String F_DJBH, @Param("F_GUID") String F_GUID, @Param("F_FLBH") String F_FLBH, @Param("F_FLGUID") String F_FLGUID);
}
