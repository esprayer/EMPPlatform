package com.mrp.biz.dailybusiness;

import java.util.List;

import com.mrp.persistence.dailyBusiness.excelStatements.detail.bean.HYXMMX;
import com.mrp.persistence.sysConfigure.project.bean.HYXMZD;

import dwz.framework.sys.business.BusinessObjectServiceMgr;

public interface ExcelStatementsServiceMgr extends BusinessObjectServiceMgr {
	//电气材料
	List<HYXMZD> searchHYXM(String beginDate, String endDate, String F_XMZT, int startIndex, int count);
	List<HYXMZD> searchHYXM(String beginDate, String endDate, String F_XMZT, String keywords, int startIndex, int count);
	String[] statementsReslove(String fileName);
	HYXMZD getHYXM(String F_XMBH);
	void updateHYXM(HYXMZD hydwObject);
	void delHYXM(String F_XMBH);

	//项目申请的明细
	List<HYXMMX> loadHYXMMX(String F_XMBH, int startIndex, int count);	
	HYXMMX getHYXMMX(String F_XMBH, String F_CLBH);
	List<HYXMMX> searchHYXMMX(String F_XMBH, String keywords, int startIndex, int count);
	void addHYXMMX(HYXMMX hyxmmxObject);
	void updateHYXMMX(HYXMMX hyxmmxObject);
	void delHYXMMX(String F_XMBH, String F_CLBH);
}
