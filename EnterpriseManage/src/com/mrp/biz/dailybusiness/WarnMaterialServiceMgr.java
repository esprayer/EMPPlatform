package com.mrp.biz.dailybusiness;

import java.util.List;

import com.mrp.persistence.dailyBusiness.warnmaterial.bean.HYCLYJ;

import dwz.framework.sys.business.BusinessObjectServiceMgr;

public interface WarnMaterialServiceMgr extends BusinessObjectServiceMgr {
	List<HYCLYJ> searchHYCLYJ(int startIndex, int count);
	List<HYCLYJ> searchHYCLYJ(String F_CLBH, int startIndex, int count);
	HYCLYJ getHYCLYJ(String F_CLBH);
	void addHYCLYJ(HYCLYJ hyclObject);
	void updateHYCLYJ(HYCLYJ hyclObject);
	void delHYCLYJ(String F_CLBH);
}
