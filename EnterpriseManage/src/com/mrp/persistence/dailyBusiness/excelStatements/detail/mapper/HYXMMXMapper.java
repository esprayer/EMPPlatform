package com.mrp.persistence.dailyBusiness.excelStatements.detail.mapper;

import java.util.List;

import dwz.dal.BaseMapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.mrp.persistence.dailyBusiness.backgoods.bean.HYTHDMX;
import com.mrp.persistence.dailyBusiness.excelStatements.detail.bean.HYXMMX;

@Repository
public interface HYXMMXMapper extends BaseMapper<HYXMMX,String>{
	// 查询
	List<HYXMMX> loadHYXMMX(@Param("F_XMBH") String F_XMBH);
	
	// 查询
	List<HYXMMX> findClByXms(@Param("F_XMBH") String F_XMBH, @Param("F_CLBH") String F_CLBH);
	
	// 查询
	HYXMMX findClByXm(@Param("F_XMBH") String F_XMBH, @Param("F_CLBH") String F_CLBH);
	
	// 删除
	void deleteHYXMMX(@Param("F_XMBH") String F_XMBH, @Param("F_CLBH") String F_CLBH);
	
	// 删除
	void deleteHYXMMXS(@Param("F_XMBH") String F_XMBH);
	
	void insertbatch(@Param("list") List<HYXMMX> list);
	
	// 更新项目编号
	void updateXMBH(@Param("UUID") String UUID, @Param("F_XMBH") String F_XMBH);
}
