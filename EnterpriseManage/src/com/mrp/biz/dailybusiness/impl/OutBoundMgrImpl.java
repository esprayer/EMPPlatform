package com.mrp.biz.dailybusiness.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mrp.biz.dailybusiness.OutBoundServiceMgr;
import com.mrp.persistence.dailyBusiness.outBound.bean.HYCKD;
import com.mrp.persistence.dailyBusiness.outBound.bean.HYCKDMX;
import com.mrp.persistence.dailyBusiness.outBound.mapper.HYCKDMXMapper;
import com.mrp.persistence.dailyBusiness.outBound.mapper.HYCKDMapper;

import dwz.framework.sys.business.AbstractBusinessObjectServiceMgr;

@Transactional(rollbackFor = Exception.class)
@Service("outBoundServiceMgr")
public class OutBoundMgrImpl extends AbstractBusinessObjectServiceMgr implements OutBoundServiceMgr {	
	@Autowired
	private HYCKDMXMapper hyckdmxMapper;
	
	@Autowired
	private HYCKDMapper hyckdMapper;
	
	public List<HYCKDMX> searchHYCKDMXByDjbh(String F_KJQJ, String F_DJBH) {
		return searchHYCKDMX(F_KJQJ, F_DJBH, null, null, null, 0, 100);
	}
	
	public List<HYCKDMX> searchHYCKDMXByGuid(String F_KJQJ, String F_GUID) {
		return searchHYCKDMX(F_KJQJ, null, F_GUID, null, null, 0, 100);
	}
	
	public List<HYCKDMX> searchHYCKDMX(String F_KJQJ, String F_DJBH,
									   String F_GUID, String F_FLBH, 
									   String F_FLGUID, 
									   int startIndex, int count) {
		List<HYCKDMX> bos = new ArrayList<HYCKDMX>();	
		
		if(F_DJBH == null || F_DJBH.equals("")){
			F_DJBH = null;
		}
		
		if(F_GUID == null || F_GUID.equals("")){
			F_GUID = null;
		}
		
		if(F_FLBH == null || F_FLBH.equals("")){
			F_FLBH = null;
		}
		
		if(F_FLGUID == null || F_FLGUID.equals("")){
			F_FLGUID = null;
		}
		
		List<HYCKDMX> pos = hyckdmxMapper.loadHYCLCKMXByKey(F_KJQJ, F_DJBH, F_GUID, F_FLBH, F_FLGUID);
		for (HYCKDMX po : pos) {
			bos.add(po);
		}
		return bos;
	}

	public List<HYCKD> searchHYCKD(String F_KJQJ, String F_DJBH, String F_GUID,
			                       String F_DJZT, String F_DATE, String F_CKBH, String F_XMBH,
			                       int startIndex, int count) {
		List<HYCKD> bos = new ArrayList<HYCKD>();		
		if(F_DJZT == null || F_DJZT.equals("-1")){
			F_DJZT = null;
		}
		
		if(F_DATE == null || F_DATE.equals("")){
			F_DATE = null;
		}
		
		if(F_CKBH == null || F_CKBH.equals("")){
			F_CKBH = null;
		}
		
		if(F_XMBH == null || F_XMBH.equals("")){
			F_XMBH = null;
		}
		
		if(F_DJBH == null || F_DJBH.equals("")){
			F_DJBH = null;
		}
		
		if(F_GUID == null || F_GUID.equals("")){
			F_GUID = null;
		}
		
		List<HYCKD> pos = hyckdMapper.loadHYCKD(F_KJQJ, F_DATE, F_DJBH, F_GUID, F_DJZT, F_CKBH, F_XMBH);
		for (HYCKD po : pos) {
			bos.add(po);
		}
		return bos;
	}
}
