package com.mrp.biz.queryAnalyse;

import dwz.framework.sys.business.BusinessObjectServiceMgr;
import esyt.framework.com.bridge.JResponseObject;
import esyt.framework.com.eai.data.JParamObject;

public interface QueryAnalyseServiceMgr extends BusinessObjectServiceMgr {
	//库存查询
	JResponseObject searchWarehouseMaterial(JParamObject PO);
	
	//库存材料联查项目结转
	JResponseObject projectMaterialCarryover(JParamObject PO);

	//库存材料联查项目产品结转
	JResponseObject projectProductCarryoverDetails(JParamObject PO);
	
	//结转明细
	JResponseObject carryoverDetails(JParamObject PO);
	
	//材料明细查询
	JResponseObject projectMaterialQuery(JParamObject PO);
	
	//材料明细联查项目查询
	JResponseObject materialDetailsLCXMCP(JParamObject PO);

	//材料明细联查明细查询
	JResponseObject materialDetailsLCDetails(JParamObject PO);
	
	//项目明细查询
	JResponseObject projectDetailsQuery(JParamObject PO);
	
	//项目明细查询
	JResponseObject projectProductQuery(JParamObject PO);
	
	//项目明细查询
	JResponseObject projectDetailsLCCPQuery(JParamObject PO);
	
	//项目产品明细查询
	JResponseObject productDetailsQuery(JParamObject PO);
	
	//项目产品联查材料
	JResponseObject projectProductDetailsLCMaterial(JParamObject PO);
	
	//项目、产品、材料联查用料调配
	JResponseObject xmcpclDetailsLCDeploy(JParamObject PO);
	
	//项目联查申请单
	JResponseObject projectDetailsLCSatement(JParamObject PO);
	
	//系统首页显示信息
	JResponseObject SystemsHomeInfo(JParamObject PO);
}
