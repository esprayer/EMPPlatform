package com.mrp.biz.dailybusiness;

import java.util.List;

import com.mrp.persistence.dailyBusiness.backgoods.bean.HYTHD;
import com.mrp.persistence.dailyBusiness.backgoods.bean.HYTHDMX;
import com.mrp.persistence.dailyBusiness.outBound.bean.HYCKD;

import dwz.framework.sys.business.BusinessObjectServiceMgr;

public interface BackGoodsServiceMgr extends BusinessObjectServiceMgr {
	//材料退货单明细
	List<HYTHDMX> searchHYTHDMX(String F_KJQJ, String F_DJBH, int startIndex, int count);
	List<HYTHD> searchHYTHDByHYCKD(String F_KJQJ, String F_DJBH, String F_THLX, String F_DJZT, int startIndex, int count);
	List<HYTHD> searchHYTHD(String F_KJQJ, String F_DJBH, String F_GUID, String F_CKBH, String F_XMBH, String F_THLX, String F_DJZT, int startIndex, int count);
	List<HYCKD> searchHYCKD(String F_KJQJ, String F_DJBH, String F_GUID, String F_DJZT, String F_DATE, String F_CKBH, String F_XMBH, int startIndex, int count);	
}
