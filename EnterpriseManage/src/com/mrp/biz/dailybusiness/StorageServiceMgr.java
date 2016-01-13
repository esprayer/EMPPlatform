package com.mrp.biz.dailybusiness;

import java.util.List;

import com.mrp.persistence.dailyBusiness.storage.bean.HYRKD;
import com.mrp.persistence.dailyBusiness.storage.bean.HYRKDMX;

import dwz.framework.sys.business.BusinessObjectServiceMgr;
import esyt.framework.com.eai.data.JParamObject;

public interface StorageServiceMgr extends BusinessObjectServiceMgr {
	//入库单列表
	List<HYRKD> searchHYRKD(String F_KJQJ, String F_DJZT, String F_DATE, String F_CKBH, String F_DJBH, String F_GUID, String F_RKLX, int startIndex, int count);
	List<HYRKDMX> searchHYRKDMX(String F_KJQJ, String F_DJBH, String F_GUID, String F_FLBH, String F_FLGUID, int startIndex, int count);
	//加载入库单
	HYRKD loadHYRKD(JParamObject PO);

}
