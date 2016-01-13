package com.mrp.biz.dailybusiness.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mrp.biz.dailybusiness.BackGoodsServiceMgr;
import com.mrp.persistence.dailyBusiness.backgoods.bean.HYTHD;
import com.mrp.persistence.dailyBusiness.backgoods.bean.HYTHDMX;
import com.mrp.persistence.dailyBusiness.backgoods.mapper.HYTHDMXMapper;
import com.mrp.persistence.dailyBusiness.backgoods.mapper.HYTHDMapper;
import com.mrp.persistence.dailyBusiness.outBound.bean.HYCKD;
import com.mrp.persistence.dailyBusiness.outBound.mapper.HYCKDMapper;

import dwz.framework.sys.business.AbstractBusinessObjectServiceMgr;

@Transactional(rollbackFor = Exception.class)
@Service("backGoodsServiceMgr")
public class BackGoodsMgrImpl extends AbstractBusinessObjectServiceMgr implements BackGoodsServiceMgr {

	@Autowired
	private HYTHDMapper hythdMapper;
	
	@Autowired
	private HYTHDMXMapper hythdmxMapper;

	@Autowired
	private HYCKDMapper hyckdMapper;
	
	public List<HYTHDMX> searchHYTHDMX(String F_KJQJ, String F_DJBH, int startIndex, int count) {
		List<HYTHDMX> bos = new ArrayList<HYTHDMX>();		
		List<HYTHDMX> pos = hythdmxMapper.loadHYTHDMX(F_KJQJ, F_DJBH);
		for (HYTHDMX po : pos) {
			bos.add(po);
		}
		return bos;
	}
	
	public List<HYTHD> searchHYTHD(String F_KJQJ, String F_DJBH, String F_GUID, String F_CKBH, String F_XMBH, String F_THLX, String F_DJZT, int startIndex, int count) {	
		if(F_DJBH == null || F_DJBH.equals("")){
			F_DJBH = null;
		}
		
		if(F_GUID == null || F_GUID.equals("")){
			F_GUID = null;
		}
		
		if(F_CKBH == null || F_CKBH.equals("")){
			F_CKBH = null;
		}
		
		if(F_XMBH == null || F_XMBH.equals("")){
			F_XMBH = null;
		}
		
		if(F_DJZT == null || F_DJZT.equals("-1")){
			F_DJZT = null;
		}
		List<HYTHD> hythd = hythdMapper.loadHYTHD(F_KJQJ, F_DJBH, F_GUID, F_DJZT, F_CKBH, F_XMBH, F_THLX);
		return hythd;
	}
	
	public List<HYTHD> searchHYTHDByHYCKD(String F_KJQJ, String F_DJBH, String F_THLX, String F_DJZT, int startIndex, int count) {	
		List<HYTHD> hythdList = hythdMapper.searchHYTHDByHYCKD(F_KJQJ, F_DJBH, F_THLX, F_DJZT);
		return hythdList;
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
