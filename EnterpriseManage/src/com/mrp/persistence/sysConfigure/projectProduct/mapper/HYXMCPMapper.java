package com.mrp.persistence.sysConfigure.projectProduct.mapper;

import java.util.List;

import com.mrp.persistence.sysConfigure.product.bean.HYCPZD;
import com.mrp.persistence.sysConfigure.projectProduct.bean.HYXMCP;

import dwz.dal.BaseMapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface HYXMCPMapper extends BaseMapper<HYXMCP,String>{
	// 查询
	List<HYXMCP> findCpByXm(@Param("F_XMBH") String F_XMBH, @Param("F_SYZT") String F_SYZT);
	
	// 查询
	List<HYXMCP> findCpByXms(@Param("F_XMBH") String F_XMBH, @Param("F_CPBH") String F_CPBH, @Param("F_SYZT") String F_SYZT);
	
	// 查询
	HYXMCP loadHYXMCP(@Param("F_XMBH") String F_XMBH, @Param("F_CPBH") String F_CPBH);
	
	// 查询
	List<HYXMCP> searchHYCPZDByXMCL(@Param("beginDate") String beginDate, @Param("endDate") String endDate, 
								    @Param("F_XMBH") String F_XMBH, @Param("F_CPBH") String F_CPBH, 
								    @Param("F_CLBH") String F_CLBH);
	
	
	// 查询
	List<HYXMCP> findCpByXmCk(@Param("F_XMBH") String F_XMBH, @Param("F_CKBH") String F_CKBH, @Param("F_CPBH") String F_CPBH, @Param("F_XMZT") String F_XMZT);
	// 删除
	void deleteHYXMCP(@Param("F_XMBH") String F_XMBH, @Param("F_CPBH") String F_CPBH);
}
