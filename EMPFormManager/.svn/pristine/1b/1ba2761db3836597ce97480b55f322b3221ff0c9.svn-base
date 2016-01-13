package com.efounder.form.biz.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.efounder.form.biz.EMPFormServiceMgr;
import com.efounder.form.persistence.formservice.bean.SYS_DBFORM;
import com.efounder.form.persistence.formservice.mapper.SYS_DBFORMMapper;
import com.framework.sys.business.AbstractBusinessObjectServiceMgr;

@Transactional(rollbackFor = Exception.class)
@Service("EMPFormServiceMgr")
public class EMPFormServiceMgrImpl extends AbstractBusinessObjectServiceMgr implements EMPFormServiceMgr {
	@Autowired
	private SYS_DBFORMMapper dbFormMapper;

	public SYS_DBFORM getSYS_DBFORM(String BBZD_BH) {
		SYS_DBFORM po = dbFormMapper.load(BBZD_BH);
		return po;
	}

	public void addSYS_DBFORM(SYS_DBFORM dbformObject) {
		dbformObject.setBBZD_SBLX("D");
		dbformObject.setF_STAU("0");
		dbFormMapper.insert(dbformObject);
	}

	public void updSYS_DBFORM(SYS_DBFORM dbformObject) {
		dbformObject.setBBZD_SBLX("D");
		dbformObject.setF_STAU("0");
		dbFormMapper.update(dbformObject);
	}

	public void delSYS_DBFORM(String BBZD_BH) {
		dbFormMapper.delete(BBZD_BH);
	}

	public List<SYS_DBFORM> searchSYS_DBFORM(String BBZD_BH) {
		List<SYS_DBFORM> list = dbFormMapper.loadSYS_DBFORM(BBZD_BH);
		return list;
	}
}
