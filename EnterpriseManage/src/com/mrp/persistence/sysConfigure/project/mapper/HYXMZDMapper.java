package com.mrp.persistence.sysConfigure.project.mapper;

import java.util.List;

import com.mrp.persistence.sysConfigure.project.bean.HYXMZD;

import dwz.dal.BaseMapper;

import org.apache.ibatis.annotations.Param;
import org.apache.poi.hssf.record.formula.functions.T;
import org.springframework.stereotype.Repository;

@Repository
public interface HYXMZDMapper extends BaseMapper<HYXMZD,String>{
	// 查询
	List<HYXMZD> loadXM(@Param("beginDate") String beginDate, @Param("endDate") String endDate, @Param("F_XMZT") String F_XMZT);
	List<HYXMZD> loadRKXM(@Param("beginDate") String beginDate, @Param("endDate") String endDate, 
			              @Param("F_CKBH") String F_CKBH, @Param("F_XMBH") String F_XMBH, 
			              @Param("F_CLBH") String F_CLBH, @Param("F_XMZT") String F_XMZT);
	
	List<HYXMZD> loadCKXM(@Param("beginDate") String beginDate, @Param("endDate") String endDate, 
            			  @Param("F_CKBH") String F_CKBH, @Param("F_XMBH") String F_XMBH, 
            			  @Param("F_CLBH") String F_CLBH, @Param("F_XMZT") String F_XMZT);
	
	// 查询
	List<HYXMZD> loadXMByKey(@Param("beginDate") String beginDate, @Param("endDate") String endDate, @Param("F_XMZT") String F_XMZT, @Param("keywords") String keywords);
	
	// 更新项目编号
	void updateXMBH(@Param("UUID") String UUID, @Param("F_XMBH") String F_XMBH);
	List<HYXMZD> findByCL(@Param("beginDate") String beginDate, @Param("endDate") String endDate,@Param("F_XMBH") String F_XMBH, @Param("F_XMZT") String F_XMZT, @Param("F_CLBH") String F_CLBH);
	void updateXMZT(HYXMZD xmzd);
}
