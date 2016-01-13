package com.mrp.biz.sysconfigure;

import java.util.List;

import dwz.framework.sys.business.BusinessObjectServiceMgr;

import com.mrp.persistence.sysConfigure.company.bean.HYCSZD;
import com.mrp.persistence.sysConfigure.department.bean.HYBMZD;
import com.mrp.persistence.sysConfigure.deport.bean.HYCKZD;
import com.mrp.persistence.sysConfigure.material.bean.HYCLZD;
import com.mrp.persistence.sysConfigure.product.bean.HYCPZD;
import com.mrp.persistence.sysConfigure.project.bean.HYXMZD;
import com.mrp.persistence.sysConfigure.projectProduct.bean.HYXMCP;
import com.mrp.persistence.sysConfigure.suppliers.bean.HYDWZD;
import com.mrp.persistence.sysConfigure.user.bean.HYZGZD;

public interface sysConfigureServiceMgr extends BusinessObjectServiceMgr {
	//部门
	List<HYBMZD> searchHYBMZD(int startIndex, int count);
	List<HYBMZD> searchBMZD(String F_SYZT, int startIndex, int count);
	List<HYBMZD> searchHYBMZD(String F_BMBH, int startIndex, int count);
	HYBMZD getHYBM(String F_BMBH);
	void addHYBM(HYBMZD hybmObject);
	void updateHYBM(HYBMZD hybmObject);
	void delHYBM(String F_BMBH);
	
	//厂商
	List<HYCSZD> searchHYCSZD(String F_CSBH, String F_SYZT, int startIndex, int count);
	HYCSZD getHYCS(String F_CSBH);
	void addHYCS(HYCSZD hycsObject);
	void updateHYCS(HYCSZD hycsObject);
	void delHYCS(String F_CSBH);
	
	//用户
	List<HYZGZD> searchHYZG(String F_BMBH, int startIndex, int count);	
	List<HYZGZD> searchHYZG(String F_BMBH, String F_ZGBH, int startIndex, int count);
	HYZGZD getHYZG(String F_BMBH, String F_ZGBH);
	List<HYZGZD> loadHYZGByBh(String F_ZGBH);
	void addHYZG(HYZGZD hybmObject);
	void updateHYZG(HYZGZD hyzgObject);
	void delHYZG(String F_BMBH, String F_ZGBH);
	
	//仓库
	List<HYCKZD> searchHYCK(String F_BMBH, int startIndex, int count);
	List<HYCKZD> searchHYCK(String F_BMBH, String F_CKBH, int startIndex, int count);
	HYCKZD getHYCK(String F_BMBH, String F_CKBH);
	HYCKZD getHYCKZD(String F_CKBH);
	void addHYCK(HYCKZD hyckObject);
	void updateHYCK(HYCKZD hyckObject);
	void delHYCK(String F_BMBH, String F_CKBH);
	
	//供应商
	HYDWZD getHYDW(String F_DWBH);
	void addHYDW(HYDWZD hydwObject);
	void updateHYDW(HYDWZD hydwObject);
	void delHYDW(String F_DWBH);
	List<HYDWZD> searchHYDW(int startIndex, int count);
	List<HYDWZD> searchHYDW(String F_DWBH, int startIndex, int count);
	
	//电气材料
	List<HYCLZD> searchHYCLZD(int startIndex, int count);
	List<HYCLZD> searchHYCLZD(String F_CLBH, int startIndex, int count);
	HYCLZD getHYCL(String F_CLBH);
	void addHYCL(HYCLZD hyclObject);
	void updateHYCL(HYCLZD hyclObject);
	void delHYCL(String F_CLBH);
	
	//电气材料
	List<HYXMZD> searchHYXM(String beginDate, String endDate, String F_XMZT,int startIndex, int count);
	List<HYXMZD> searchHYXM(String beginDate, String endDate, String F_XMBH, String F_XMZT,int startIndex, int count);
	HYXMZD getHYXM(String F_XMBH);
	void doCompleteProject(String F_XMBH, String F_WCR);
	
	//产品
	HYCPZD getHYCP(String F_CPBH);
	void addHYCP(HYCPZD hycpObject);
	void updateHYCP(HYCPZD hycpObject);
	void delHYCP(String F_CPBH);
	List<HYCPZD> searchHYCP(int startIndex, int count);
	List<HYCPZD> searchHYCP(String F_CPBH, int startIndex, int count);
	//项目产品
	List<HYXMCP> searchHYXMCP(String F_XMBH, int startIndex, int count);
	//项目产品
	List<HYXMCP> searchHYXMCP(String F_XMBH, String F_SYZT);
	List<HYXMCP> searchHYXMCP(String F_XMBH, String F_CPBH, int startIndex, int count);
	List<HYXMCP> searchHYXMCP(String F_XMBH, String F_CPBH, String F_SYZT, int startIndex, int count);
	HYXMCP getHYXMCP(String F_XMBH, String F_CPBH);
	void addHYXMCP(HYXMCP hycpObject);
	void updateHYXMCP(HYXMCP hycpObject);
	void delHYXMCP(String F_XMBH, String F_CPBH);
}
