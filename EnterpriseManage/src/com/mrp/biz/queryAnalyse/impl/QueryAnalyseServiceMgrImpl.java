package com.mrp.biz.queryAnalyse.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dwz.framework.sys.business.AbstractBusinessObjectServiceMgr;
import esyt.framework.com.bridge.JResponseObject;
import esyt.framework.com.eai.data.JParamObject;

import com.mrp.biz.queryAnalyse.QueryAnalyseServiceMgr;
import com.mrp.biz.queryAnalyse.query.action.EMPCarryoverDetailsQuery;
import com.mrp.biz.queryAnalyse.query.action.EMPMaterialLCDetails;
import com.mrp.biz.queryAnalyse.query.action.EMPProjectCarryoverQuery;
import com.mrp.biz.queryAnalyse.query.action.EMPProjectDetailsLCSatement;
import com.mrp.biz.queryAnalyse.query.action.EMPProjectProductDetailsLCDeploy;
import com.mrp.biz.queryAnalyse.query.action.EMPXMCPDetailsLCMaterialQuery;
import com.mrp.biz.queryAnalyse.query.EMPProjectDetailsQuery;
import com.mrp.biz.queryAnalyse.query.EMPProjectMaterialQuery;
import com.mrp.biz.queryAnalyse.query.EMPProjectProductQuery;
import com.mrp.biz.queryAnalyse.query.EMPSystemHomeQuery;
import com.mrp.biz.queryAnalyse.query.action.EMPProjectProductCarryoverQuery;
import com.mrp.biz.queryAnalyse.query.EMPWarehouseMaterialQuery;
import com.mrp.biz.queryAnalyse.query.action.EMPMaterialDetailsLCDetails;
import com.mrp.biz.queryAnalyse.query.action.EMPMaterialDetailsLCXMCP;

@Transactional(rollbackFor = Exception.class)
@Service("queryAnalyseServiceMgr")
public class QueryAnalyseServiceMgrImpl extends AbstractBusinessObjectServiceMgr implements QueryAnalyseServiceMgr {

	@Autowired
	private                 EMPWarehouseMaterialQuery             warehouseMaterial;
	
	@Autowired
	private                  EMPProjectCarryoverQuery              projectCarryover;
	
	@Autowired
	private           EMPProjectProductCarryoverQuery       projectProductCarryover;
	
	@Autowired
	private                  EMPCarryoverDetailsQuery              carryoverDetails;
	
	@Autowired
	private                   EMPProjectMaterialQuery               projectMaterial;
	
	@Autowired
	private                  EMPMaterialDetailsLCXMCP         materialDetailsLCXMCP;

	@Autowired
	private               EMPMaterialDetailsLCDetails      materialDetailsLCDetails;
	
	@Autowired
	private                    EMPProjectDetailsQuery                projectDetails;
	
	@Autowired
	private             EMPXMCPDetailsLCMaterialQuery         xmcpDetailsLCMaterial;
	
	@Autowired
	private          EMPProjectProductDetailsLCDeploy                XMCPCLLCDeploy;
	
	@Autowired
	private               EMPProjectDetailsLCSatement      projectDetailsLCSatement;
	
	@Autowired
	private                        EMPSystemHomeQuery                    systemHome;
	
	@Autowired
	private                      EMPMaterialLCDetails             materialLCDetails;
	
	@Autowired
	private                    EMPProjectProductQuery                projectProduct;
	
	//--------------------------------------------------------------------------------------------------
	// 描述:库存查询
	// 设计: ES(2013.09.12)
	// 实现: ES
	// 修改:
	// --------------------------------------------------------------------------------------------------
	public JResponseObject searchWarehouseMaterial(JParamObject PO) {
		return warehouseMaterial.QueryObject(PO);
	}
	
	//--------------------------------------------------------------------------------------------------
	// 描述:库存材料联查项目结转
	// 设计: ES(2013.09.12)
	// 实现: ES
	// 修改:
	// --------------------------------------------------------------------------------------------------
	public JResponseObject projectMaterialCarryover(JParamObject PO) {
		return projectCarryover.QueryObject(PO);
	}
	
	//--------------------------------------------------------------------------------------------------
	// 描述:库存材料联查项目产品结转
	// 设计: ES(2013.09.12)
	// 实现: ES
	// 修改:
	// --------------------------------------------------------------------------------------------------
	public JResponseObject projectProductCarryoverDetails(JParamObject PO) {
		return projectProductCarryover.QueryObject(PO);
	}
	
	//--------------------------------------------------------------------------------------------------
	// 描述:结转明细
	// 设计: ES(2013.09.12)
	// 实现: ES
	// 修改:
	// --------------------------------------------------------------------------------------------------
	public JResponseObject carryoverDetails(JParamObject PO) {
		return carryoverDetails.QueryObject(PO);
	}
	
	//--------------------------------------------------------------------------------------------------
	// 描述:材料明细查询
	// 设计: ES(2013.09.12)
	// 实现: ES
	// 修改:
	// --------------------------------------------------------------------------------------------------
	public JResponseObject projectMaterialQuery(JParamObject PO) {
		return projectMaterial.QueryObject(PO);
	}
	
	//--------------------------------------------------------------------------------------------------
	// 描述:材料明细联查项目查询
	// 设计: ES(2013.09.12)
	// 实现: ES
	// 修改:
	// --------------------------------------------------------------------------------------------------
	public JResponseObject materialDetailsLCXMCP(JParamObject PO) {
		return materialLCDetails.QueryObject(PO);
	}
	
	//--------------------------------------------------------------------------------------------------
	// 描述:材料明细联查明细查询
	// 设计: ES(2013.09.12)
	// 实现: ES
	// 修改:
	// --------------------------------------------------------------------------------------------------
	public JResponseObject materialDetailsLCDetails(JParamObject PO) {
		return materialDetailsLCDetails.QueryObject(PO);
	}
	
	//--------------------------------------------------------------------------------------------------
	// 描述:项目明细查询
	// 设计: ES(2013.09.12)
	// 实现: ES
	// 修改:
	// --------------------------------------------------------------------------------------------------
	public JResponseObject projectDetailsQuery(JParamObject PO) {
		return projectDetails.QueryObject(PO);
	}
	
	//--------------------------------------------------------------------------------------------------
	// 描述:项目产品查询
	// 设计: ES(2013.09.12)
	// 实现: ES
	// 修改:
	// --------------------------------------------------------------------------------------------------
	public JResponseObject projectProductQuery(JParamObject PO) {
		return projectProduct.QueryObject(PO);
	}
	
	//--------------------------------------------------------------------------------------------------
	// 描述:项目查询联查产品
	// 设计: ES(2013.09.12)
	// 实现: ES
	// 修改:
	// --------------------------------------------------------------------------------------------------
	public JResponseObject projectDetailsLCCPQuery(JParamObject PO) {
		return projectProduct.QueryObject(PO);
	}
	
	//--------------------------------------------------------------------------------------------------
	// 描述:项目产品联查材料
	// 设计: ES(2013.09.12)
	// 实现: ES
	// 修改:
	// --------------------------------------------------------------------------------------------------
	public JResponseObject projectProductDetailsLCMaterial(JParamObject PO) {
		return xmcpDetailsLCMaterial.QueryObject(PO);
	}
	
	//--------------------------------------------------------------------------------------------------
	// 描述:项目产品明细查询
	// 设计: ES(2013.09.12)
	// 实现: ES
	// 修改:
	// --------------------------------------------------------------------------------------------------
	public JResponseObject productDetailsQuery(JParamObject PO) {
		return projectDetails.QueryObject(PO);
	}
	
	//--------------------------------------------------------------------------------------------------
	//项目、产品、材料联查用料调配
	// 设计: ES(2013.09.12)
	// 实现: ES
	// 修改:
	// --------------------------------------------------------------------------------------------------
	public JResponseObject xmcpclDetailsLCDeploy(JParamObject PO) {
		return XMCPCLLCDeploy.QueryObject(PO);
	}
	
	//--------------------------------------------------------------------------------------------------
	// 描述:项目联查申请单
	// 设计: ES(2013.09.12)
	// 实现: ES
	// 修改:
	// --------------------------------------------------------------------------------------------------
	public JResponseObject projectDetailsLCSatement(JParamObject PO) {
		return projectDetailsLCSatement.QueryObject(PO);
	}

	//--------------------------------------------------------------------------------------------------
	// 描述:系统首页信息查询
	// 设计: ES(2013.09.12)
	// 实现: ES
	// 修改:
	// --------------------------------------------------------------------------------------------------
	public JResponseObject SystemsHomeInfo(JParamObject PO) {		
		return systemHome.QueryObject(PO);
	}
}
