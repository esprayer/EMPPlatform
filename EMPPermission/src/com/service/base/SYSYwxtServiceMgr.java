package com.service.base;

import java.util.List;
import java.util.Map;

import com.efounder.builder.base.data.EFDataSet;
import com.efounder.eai.data.JParamObject;
import com.framework.sys.business.BusinessObjectServiceMgr;
import com.persistence.qxgl.beans.SYSYwxt;

public interface SYSYwxtServiceMgr extends BusinessObjectServiceMgr {
	//应用系统管理
	List<SYSYwxt> searchYwxt(int startIndex, int count);
	List<SYSYwxt> searchYwxt(String ywxtBhOrMc, int startIndex, int count);
	SYSYwxt getYwxt(String ywxtBh);
	void addYwxt(SYSYwxt ywxtObj);
	void updateYwxt(SYSYwxt ywxtObj);
	void delYwxt(String ywxtBh);
	EFDataSet queryXTZD();
	EFDataSet queryUSGN(JParamObject PO);
	
}
