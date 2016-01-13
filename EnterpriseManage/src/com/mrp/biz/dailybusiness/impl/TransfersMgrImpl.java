package com.mrp.biz.dailybusiness.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mrp.biz.dailybusiness.TransfersServiceMgr;
import com.mrp.persistence.dailyBusiness.transfers.bean.HYDBD;
import com.mrp.persistence.dailyBusiness.transfers.bean.HYDBDMX;
import com.mrp.persistence.dailyBusiness.transfers.mapper.HYDBDMXMapper;
import com.mrp.persistence.dailyBusiness.transfers.mapper.HYDBDMapper;

import dwz.framework.sys.business.AbstractBusinessObjectServiceMgr;

@Transactional(rollbackFor = Exception.class)
@Service("transfersServiceMgr")
public class TransfersMgrImpl extends AbstractBusinessObjectServiceMgr implements TransfersServiceMgr {

	@Autowired
	private HYDBDMapper hydbdMapper;

	@Autowired
	private HYDBDMXMapper hydbdmxMapper;
	
	public List<HYDBD> searchHYDBD(String F_KJQJ, String F_DJBH, String F_GUID, String F_CKBH, String F_DJZT, int startIndex, int count) {
		List<HYDBD> bos = new ArrayList<HYDBD>();		
		if(F_DJZT == null || F_DJZT.equals("-1")){
			F_DJZT = null;
		}

		if(F_CKBH == null || F_CKBH.equals("")){
			F_CKBH = null;
		}
		
		if(F_DJBH == null || F_DJBH.equals("")){
			F_DJBH = null;
		}
		
		if(F_GUID == null || F_GUID.equals("")){
			F_GUID = null;
		}
		
		List<HYDBD> pos = hydbdMapper.loadHYDBD(F_KJQJ, F_DJZT, F_CKBH, F_DJBH, F_GUID);
		for (HYDBD po : pos) {
			bos.add(po);
		}
		return bos;
	}
	
	public List<HYDBDMX> searchHYDBDMX(String F_KJQJ, String F_DJBH, String F_GUID, String F_FLBH, String F_FLGUID, int startIndex, int count) {
		List<HYDBDMX> bos = new ArrayList<HYDBDMX>();	
		List<HYDBDMX> pos = hydbdmxMapper.loadHYDBDMX(F_KJQJ, F_DJBH, F_GUID, F_FLBH, F_FLGUID);
		for (HYDBDMX po : pos) {
			bos.add(po);
		}
		return bos;
	}
}
