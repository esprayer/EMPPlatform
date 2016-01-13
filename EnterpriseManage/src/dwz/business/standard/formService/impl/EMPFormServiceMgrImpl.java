package dwz.business.standard.formService.impl;

import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dwz.business.standard.formService.EMPFormServiceMgr;
import dwz.persistence.formservice.bean.SYS_DBFORM;
import dwz.framework.sys.business.AbstractBusinessObjectServiceMgr;
import dwz.persistence.formservice.mapper.SYS_DBFORMMapper;
import esyt.framework.com.component.EMPComposeFormInfo;
import esyt.framework.com.core.xml.PackageStub;
import esyt.framework.com.core.xml.StubObject;

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
