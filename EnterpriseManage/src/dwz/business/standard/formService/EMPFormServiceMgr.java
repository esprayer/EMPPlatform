package dwz.business.standard.formService;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import dwz.framework.sys.business.BusinessObjectServiceMgr;
import dwz.persistence.formservice.bean.SYS_DBFORM;
import esyt.framework.com.component.EMPComposeFormInfo;

public interface EMPFormServiceMgr extends BusinessObjectServiceMgr {
	SYS_DBFORM getSYS_DBFORM(String BBZD_BH);
	void addSYS_DBFORM(SYS_DBFORM dbformObject);
	void updSYS_DBFORM(SYS_DBFORM dbformObject);
	void delSYS_DBFORM(String BBZD_BH);
	
	List<SYS_DBFORM> searchSYS_DBFORM(String BBZD_BH);
}
