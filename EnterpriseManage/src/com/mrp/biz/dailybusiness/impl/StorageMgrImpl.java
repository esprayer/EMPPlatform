package com.mrp.biz.dailybusiness.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mrp.biz.dailybusiness.StorageServiceMgr;
import com.mrp.persistence.dailyBusiness.storage.bean.HYRKD;
import com.mrp.persistence.dailyBusiness.storage.bean.HYRKDMX;
import com.mrp.persistence.dailyBusiness.storage.mapper.HYRKDMXMapper;
import com.mrp.persistence.dailyBusiness.storage.mapper.HYRKDMapper;

import dwz.framework.sys.business.AbstractBusinessObjectServiceMgr;
import esyt.framework.com.eai.data.JParamObject;

@Transactional(rollbackFor = Exception.class)
@Service("storageServiceMgr")
public class StorageMgrImpl extends AbstractBusinessObjectServiceMgr implements StorageServiceMgr {

	@Autowired
	private HYRKDMapper hyrkdMapper;

	@Autowired
	private HYRKDMXMapper hyrkdmxMapper;
	
	public List<HYRKD> searchHYRKD(String F_KJQJ, String F_DJZT, String F_DATE, String F_CKBH, String F_DJBH, String F_GUID, String F_RKLX, int startIndex, int count) {
		List<HYRKD> bos = new ArrayList<HYRKD>();		
		if(F_DJZT == null || F_DJZT.equals("-1")){
			F_DJZT = null;
		}
		
		if(F_DATE == null || F_DATE.equals("")){
			F_DATE = null;
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
		
		List<HYRKD> pos = hyrkdMapper.loadHYRKD(F_KJQJ, F_DJZT, F_DATE, F_CKBH, F_DJBH, F_GUID, F_RKLX);
		for (HYRKD po : pos) {
			bos.add(po);
		}
		return bos;
	}
	
	public List<HYRKDMX> searchHYRKDMX(String F_KJQJ, String F_DJBH, String F_GUID, String F_FLBH, String F_FLGUID, int startIndex, int count) {
		List<HYRKDMX> bos = new ArrayList<HYRKDMX>();	
		
		if(F_DJBH.equals("")){
			F_DJBH = null;
		}
		
		if(F_GUID.equals("")){
			F_GUID = null;
		}
		
		if(F_FLBH.equals("")){
			F_FLBH = null;
		}
		
		if(F_FLGUID.equals("")){
			F_FLGUID = null;
		}
		
		List<HYRKDMX> pos = hyrkdmxMapper.loadHYCLRKMXByKey(F_KJQJ, F_DJBH, F_GUID, F_FLBH, F_FLGUID);
		for (HYRKDMX po : pos) {
			bos.add(po);
		}
		return bos;
	}
	
	public HYRKD loadHYRKD(JParamObject PO) {
		String F_KJQJ = PO.GetValueByParamName("F_KJQJ", null);
		String F_DJBH = PO.GetValueByParamName("F_DJBH", null);
		String F_GUID = PO.GetValueByParamName("F_GUID", null);
		List<HYRKD> pos = hyrkdMapper.loadHYRKD(F_KJQJ, null, null, null, F_DJBH, F_GUID, null);
		return pos.get(0);
	}
}
