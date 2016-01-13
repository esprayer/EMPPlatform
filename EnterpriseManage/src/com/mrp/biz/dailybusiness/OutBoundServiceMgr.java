package com.mrp.biz.dailybusiness;

import java.util.List;

import com.mrp.persistence.dailyBusiness.outBound.bean.HYCKD;
import com.mrp.persistence.dailyBusiness.outBound.bean.HYCKDMX;

import dwz.framework.sys.business.BusinessObjectServiceMgr;

public interface OutBoundServiceMgr extends BusinessObjectServiceMgr {
	List<HYCKDMX> searchHYCKDMX(String F_KJQJ, String F_DJBH, String F_GUID, String F_FLBH, String F_FLGUID, int startIndex, int count);
	
	List<HYCKDMX> searchHYCKDMXByDjbh(String F_KJQJ, String F_DJBH);
	
	List<HYCKDMX> searchHYCKDMXByGuid(String F_KJQJ, String F_GUID);

	//出库单列表
	List<HYCKD> searchHYCKD(String F_KJQJ, String F_DJBH, String F_GUID, String F_DJZT, String F_DATE, String F_CKBH, String F_XMBH, int startIndex, int count);
}
