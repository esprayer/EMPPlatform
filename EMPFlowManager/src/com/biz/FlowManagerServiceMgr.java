package com.biz;

import com.efounder.builder.base.data.EFDataSet;
import com.efounder.eai.data.JParamObject;
import com.framework.sys.business.BusinessObjectServiceMgr;

public interface FlowManagerServiceMgr extends BusinessObjectServiceMgr {
	//
	EFDataSet loadFlowNode(JParamObject PO);

	//
	EFDataSet loadFlow(JParamObject PO);
	
	EFDataSet loadFlow1(JParamObject PO);
	
	//
	EFDataSet loadFlowPurview(JParamObject PO);
	
	//
	EFDataSet loadAllFlowTask(JParamObject PO);
}
