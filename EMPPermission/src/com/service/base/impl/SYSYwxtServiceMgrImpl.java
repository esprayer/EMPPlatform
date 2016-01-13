package com.service.base.impl;

import java.sql.ResultSet;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.efounder.builder.base.data.EFDataSet;
import com.efounder.eai.data.JParamObject;
import com.efounder.eai.data.JResponseObject;
import com.efounder.sql.JConnection;
import com.efounder.sql.JStatement;
import com.framework.sys.business.AbstractBusinessObjectServiceMgr;
import com.persistence.qxgl.beans.SYSYwxt;
import com.persistence.qxgl.mapper.SYSYwxtMapper;
import com.server.EMPDALPermissionManager;
import com.service.base.SYSYwxtServiceMgr;

@Transactional(rollbackFor = Exception.class)
@Service("sysYwxtServiceMgr")
public class SYSYwxtServiceMgrImpl extends AbstractBusinessObjectServiceMgr implements SYSYwxtServiceMgr {
	@Autowired
	private SYSYwxtMapper ywxtMapper;

	@Autowired
	private EMPDALPermissionManager permissionManager;
	
	@Override
	public List<SYSYwxt> searchYwxt(int startIndex, int count) {
		return ywxtMapper.findAll();
	}

	@Override
	public List<SYSYwxt> searchYwxt(String ywxtBhOrMc, int startIndex, int count) {
		return ywxtMapper.findByObjId(ywxtBhOrMc);
	}

	@Override
	public SYSYwxt getYwxt(String ywxtBh) {
		return ywxtMapper.load(ywxtBh);
	}

	@Override
	public void addYwxt(SYSYwxt ywxtObj) {
		ywxtMapper.insert(ywxtObj);
	}

	@Override
	public void updateYwxt(SYSYwxt ywxtObj) {
		ywxtMapper.update(ywxtObj);
	}

	@Override
	public void delYwxt(String ywxtBh) {
		ywxtMapper.delete(ywxtBh);
	}

	public EFDataSet queryXTZD() {
		return permissionManager.queryBSXTZD();
	}
	
	public EFDataSet queryUSGN(JParamObject PO) {
		return permissionManager.queryBSUSGN(PO);
	}
}
