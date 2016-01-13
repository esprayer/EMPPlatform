package com.mrp.persistence.dailyBusiness.warnmaterial.mapper;

import java.util.List;

import dwz.dal.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.mrp.persistence.dailyBusiness.warnmaterial.bean.HYCLYJ;

@Repository
public interface HYCLYJMapper extends BaseMapper<HYCLYJ,String>{
	// 查询
	List<HYCLYJ>  findByCLBH(@Param("F_CLBH") String F_CLBH);
	
	// 查询
	List<HYCLYJ>  clHelp(@Param("F_CLBH") String F_CLBH);
}
