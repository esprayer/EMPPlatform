package com.mrp.biz.dailybusiness;

import java.util.List;

import com.mrp.persistence.dailyBusiness.transfers.bean.HYDBD;
import com.mrp.persistence.dailyBusiness.transfers.bean.HYDBDMX;

import dwz.framework.sys.business.BusinessObjectServiceMgr;

public interface TransfersServiceMgr extends BusinessObjectServiceMgr {
	//入库单列表
	List<HYDBD> searchHYDBD(String F_KJQJ, String F_DJBH, String F_GUID, String F_CKBH, String F_DJZT, int startIndex, int count);
	List<HYDBDMX> searchHYDBDMX(String F_KJQJ, String F_DJBH, String F_GUID, String F_FLBH, String F_FLGUID, int startIndex, int count);

}
