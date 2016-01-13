package com.mrp.biz.dicthelp;

import java.util.List;

import dwz.framework.sys.business.BusinessObjectServiceMgr;
import esyt.framework.com.builder.base.data.EFDataSet;

import com.mrp.persistence.sysConfigure.company.bean.HYCSZD;
import com.mrp.persistence.sysConfigure.department.bean.HYBMZD;
import com.mrp.persistence.sysConfigure.deport.bean.HYCKZD;
import com.mrp.persistence.sysConfigure.material.bean.HYCLZD;
import com.mrp.persistence.sysConfigure.product.bean.HYCPZD;
import com.mrp.persistence.sysConfigure.project.bean.HYXMZD;
import com.mrp.persistence.sysConfigure.projectProduct.bean.HYXMCP;
import com.mrp.persistence.sysConfigure.user.bean.HYZGZD;

public interface DictHelpServiceMgr extends BusinessObjectServiceMgr {
	List<HYBMZD> searchHYBMZD();
	List<HYCKZD> searchHYCK(String F_BMBH, int startIndex, int count);
	List<HYCKZD> searchHYCK(String F_BMBH, String F_CKBH, int startIndex, int count);
	EFDataSet xmclHelp(String F_XMBH, String F_CPBH, String F_CLBH, String F_CKBH, int startIndex, int coun);	
	EFDataSet xmclHelp(String F_XMBH, String F_CPBH, String F_CLBH, String beginDate, String endDate, int startIndex, int coun);	
	List<String[]> xmHelp(String F_XMBH, String F_CPBH, String F_CLBH, String F_CKBH, String F_XMZT, int startIndex, int coun);
	List<HYCLZD> clHelp(String F_CLBH, int startIndex, int coun);
	List<HYCSZD> csHelp(String F_CSBH, String F_SYZT, int startIndex, int coun);
	
	//生产厂商
	List<HYCSZD> searchHYCS(String F_CSBH, String F_SYZT, int startIndex, int count);
	
	//产品
	List<HYCPZD> searchHYCPZD(String F_CPBH, String F_SYZT, int startIndex, int count);
	
	//项目产品
	List<HYXMCP> searchHYXMCP(String F_XMBH, int startIndex, int count);
	List<HYXMCP> searchHYXMCP(String F_XMBH, String F_CPBH, int startIndex, int count);
	List<HYXMCP> searchHYXMCP(String F_XMBH, String F_CKBH, String F_CPBH, String F_XMZT, int startIndex, int count);
	EFDataSet searchHYXMCP(String beginDate, String endDate, String F_CPBH, int startIndex, int count);
	List<HYXMCP> searchHYXMCP(String beginDate, String endDate, String F_XMBH, String F_CPBH, String F_CLBH, int startIndex, int count);
	
	List<HYZGZD> searchHYZGZD(String F_SXBM, String keywords, int startIndex, int count);
	List<HYZGZD> searchHYZGZD(String F_SXBM, int startIndex, int count);
	List<HYZGZD> searchUseHYZGZD(String F_SXBM, String keywords, String F_SYZT, int startIndex, int count);
	List<HYZGZD> searchUseHYZGZD(String F_SXBM, String F_SYZT, int startIndex, int count);
	List<HYCLZD> searchHYCLZD(int startIndex, int count);
	List<HYCLZD> searchHYCLZD(String keywords, int startIndex, int count);
	List<HYCLZD> searchHYCLZDByKey(String F_XMBH, int startIndex, int count);
	EFDataSet scanningHYCLZD(String F_XMBH, String F_CLBH, String F_DWBH, String F_CPBH, String F_CSBH, String WHERE, int startIndex, int count);
	EFDataSet deportMaterialHelp(String F_CKBH, String F_XMBH, String F_CLBH, String F_DWBH, String F_CSBH, String WHERE, int startIndex, int count);
	List<String[]> searchHYCLZDByXm(String F_XMBH, String F_CLBH, int startIndex, int count);
	//项目
	List<HYXMZD> searchHYXMZD(String beginDate, String endDate, String F_CLBH, String F_XMZT, String F_XMBH, int startIndex, int count);
	List<HYXMZD> searchHYXMZD(String beginDate, String endDate, String F_XMZT,int startIndex, int count);
	List<HYXMZD> searchHYXMZD(String beginDate, String endDate, String F_XMBH, String F_XMZT,int startIndex, int count);
	List<HYXMZD> searchXMCK(String beginDate, String endDate, String F_CKBH, String F_XMBH, String F_CLBH, String F_CRFX, String F_XMZT,int startIndex, int count);
	
	//材料帮助
	EFDataSet searchHYCLZD(String beginDate, String endDate, String F_CKBH, String F_CLBH, int startIndex, int count);
	EFDataSet searchHYCLZD(String beginDate, String endDate, String F_CLBH,int startIndex, int count);
	EFDataSet searchHYCLZDByHYXM(String beginDate, String endDate, String F_XMBH, String F_CLBH,int startIndex, int count);
}
