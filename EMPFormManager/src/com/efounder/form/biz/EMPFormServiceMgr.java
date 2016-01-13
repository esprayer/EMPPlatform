package com.efounder.form.biz;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.efounder.form.persistence.formservice.bean.SYS_DBFORM;
import com.framework.sys.business.BusinessObjectServiceMgr;

public interface EMPFormServiceMgr extends BusinessObjectServiceMgr {
	SYS_DBFORM getSYS_DBFORM(String BBZD_BH);
	void addSYS_DBFORM(SYS_DBFORM dbformObject);
	void updSYS_DBFORM(SYS_DBFORM dbformObject);
	void delSYS_DBFORM(String BBZD_BH);
	
	List<SYS_DBFORM> searchSYS_DBFORM(String BBZD_BH);
}
