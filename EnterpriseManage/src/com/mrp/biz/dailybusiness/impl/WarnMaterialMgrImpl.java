package com.mrp.biz.dailybusiness.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mrp.biz.dailybusiness.WarnMaterialServiceMgr;
import com.mrp.persistence.dailyBusiness.warnmaterial.bean.HYCLYJ;
import com.mrp.persistence.dailyBusiness.warnmaterial.mapper.HYCLYJMapper;

import dwz.framework.sys.business.AbstractBusinessObjectServiceMgr;

@Transactional(rollbackFor = Exception.class)
@Service("warnMaterialServiceMgr")
public class WarnMaterialMgrImpl extends AbstractBusinessObjectServiceMgr implements WarnMaterialServiceMgr {

	@Autowired
	private HYCLYJMapper hyclyjMapper;

	public void addHYCLYJ(HYCLYJ hyclObject) {
		Date now = new Date();
		HYCLYJ po = hyclyjMapper.load(hyclObject.getF_CLBH());
		
		if(po == null) {
			hyclObject.setF_CHDATE(now);
			hyclObject.setF_CRDATE(now);
			hyclyjMapper.insert(hyclObject);
		} else {
			po.setF_KCSX(hyclObject.getF_KCSX() + po.getF_KCSX());
			po.setF_KCXX(hyclObject.getF_KCXX() + po.getF_KCXX());
			po.setF_CHDATE(now);
			hyclyjMapper.update(po);
		}
	}

	public void delHYCLYJ(String F_CLBH) {
		hyclyjMapper.delete(F_CLBH);
	}

	public HYCLYJ getHYCLYJ(String F_CLBH) {
		HYCLYJ po = hyclyjMapper.load(F_CLBH);
		return po;
	}

	public List<HYCLYJ> searchHYCLYJ(int startIndex, int count) {
		List<HYCLYJ> bos = new ArrayList<HYCLYJ>();
		List<HYCLYJ> pos = hyclyjMapper.findByCLBH("");
		for (HYCLYJ po : pos) {
			bos.add(po);
		}
		return bos;
	}

	@Override
	public List<HYCLYJ> searchHYCLYJ(String F_CLBH, int startIndex, int count) {
		List<HYCLYJ> bos = new ArrayList<HYCLYJ>();
		List<HYCLYJ> pos = hyclyjMapper.findByCLBH(F_CLBH);
		for (HYCLYJ po : pos) {
			bos.add(po);
		}
		return bos;
	}

	public void updateHYCLYJ(HYCLYJ hyclObject) {
		Date now = new Date();
		HYCLYJ po = hyclyjMapper.load(hyclObject.getF_CLBH());
		po.setF_KCSX(hyclObject.getF_KCSX());
		po.setF_KCXX(hyclObject.getF_KCXX());		
		po.setF_CHDATE(now);
		hyclyjMapper.update(po);
	}
}
