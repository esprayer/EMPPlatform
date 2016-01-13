package com.mrp.web.queryAnalyse;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.etsoft.pub.enums.EMPQueryParamEnum;
import com.etsoft.pub.util.StringUtil;
import com.mrp.biz.dailybusiness.BackGoodsServiceMgr;
import com.mrp.biz.dailybusiness.OutBoundServiceMgr;
import com.mrp.biz.dailybusiness.StorageServiceMgr;
import com.mrp.biz.dailybusiness.TransfersServiceMgr;
import com.mrp.biz.queryAnalyse.QueryAnalyseServiceMgr;
import com.mrp.biz.sysconfigure.sysConfigureServiceMgr;
import com.mrp.persistence.dailyBusiness.backgoods.bean.HYTHD;
import com.mrp.persistence.dailyBusiness.backgoods.bean.HYTHDMX;
import com.mrp.persistence.dailyBusiness.outBound.bean.HYCKD;
import com.mrp.persistence.dailyBusiness.outBound.bean.HYCKDMX;
import com.mrp.persistence.dailyBusiness.storage.bean.HYRKD;
import com.mrp.persistence.dailyBusiness.storage.bean.HYRKDMX;
import com.mrp.persistence.dailyBusiness.transfers.bean.HYDBD;
import com.mrp.persistence.dailyBusiness.transfers.bean.HYDBDMX;
import com.mrp.persistence.queryAnalyse.pageQuery.bean.EMPPageQuery;
import com.mrp.persistence.sysConfigure.company.bean.HYCSZD;
import com.mrp.persistence.sysConfigure.deport.bean.HYCKZD;
import com.mrp.persistence.sysConfigure.material.bean.HYCLZD;
import com.mrp.persistence.sysConfigure.product.bean.HYCPZD;
import com.mrp.persistence.sysConfigure.project.bean.HYXMZD;
import com.mrp.persistence.sysConfigure.suppliers.bean.HYDWZD;
import dwz.web.BaseController;
import esyt.framework.com.bridge.JResponseObject;
import esyt.framework.com.builder.base.data.EFDataSet;
import esyt.framework.com.builder.base.data.EFRowSet;
import esyt.framework.com.eai.data.JParamObject;

@Controller
@RequestMapping(value="/queryAnalyse/action")
public class EMPQueryAnalyseLCActionController extends BaseController{
	@Autowired
	private QueryAnalyseServiceMgr queryAnalyseServiceMgr;
	
	@Autowired
	private sysConfigureServiceMgr        sysConfigureMgr;
	
	@Autowired
	private      StorageServiceMgr             storageMgr;
	
	@Autowired
	private     OutBoundServiceMgr            outBoundMgr;
	
	@Autowired
	private    BackGoodsServiceMgr           backGoodsMgr;
	
	@Autowired
	private    TransfersServiceMgr           transfersMgr;
	
	@Autowired
	public HttpSession session;

	/**
	 * 库存查询材料联查项目结转
	 * @param F_CKBH
	 * @param model
	 * @return
	 */
	@RequestMapping("/projectCarryover")
	public String projectCarryover(@RequestParam String F_CKBH, @RequestParam String F_CLBH, 
			                       @RequestParam String F_DWBH, @RequestParam String F_CSBH, 
			                       @RequestParam String F_CLDJ, @RequestParam String F_XMZT, 
			                       @RequestParam String F_XMBH, EMPPageQuery page, Model model) {
		JParamObject           po = JParamObject.Create();
		HYCLZD               clzd = sysConfigureMgr.getHYCL(F_CLBH);
		HYCKZD               ckzd = sysConfigureMgr.getHYCKZD(F_CKBH);
		HYCSZD               cszd = sysConfigureMgr.getHYCS(F_CSBH);
		HYDWZD               dwzd = sysConfigureMgr.getHYDW(F_DWBH);
		HYXMZD               xmzd = sysConfigureMgr.getHYXM(F_XMBH);
		EFDataSet    queryDataSet = null;
		EFRowSet      queryRowSet = null;
		
		po.SetValueByParamName("F_CKBH", F_CKBH);
		po.SetValueByParamName("F_CLBH", F_CLBH);
		po.SetValueByParamName("F_DWBH", F_DWBH);
		po.SetValueByParamName("F_CSBH", F_CSBH);
		po.SetValueByParamName("F_XMBH", F_XMBH);
		po.SetValueByParamName("F_XMZT", F_XMZT);
		po.SetValueByParamName("F_CLDJ", F_CLDJ);
		po.setValue(EMPQueryParamEnum.PAGEQUERY, page);
		
		JResponseObject        RO = queryAnalyseServiceMgr.projectMaterialCarryover(po);
		queryRowSet = (EFRowSet) RO.getResponseObject();
		
		queryDataSet = queryRowSet.getDataSet(EMPQueryParamEnum.QUERYRESULT);
		page = (EMPPageQuery) queryRowSet.getExtProperty(EMPQueryParamEnum.PAGEQUERY, null);
		model.addAttribute("queryDataSet", queryDataSet);
		model.addAttribute("page", page);
		
		model.addAttribute("F_CKBH", F_CKBH);
		if(ckzd != null) model.addAttribute("F_CKMC", ckzd.getF_CKMC());
		
		model.addAttribute("F_CLBH", F_CLBH);
		if(clzd != null) model.addAttribute("F_CLMC", clzd.getF_CLMC());
		
		model.addAttribute("F_CSBH", F_CSBH);
		if(cszd != null) model.addAttribute("F_CSMC", cszd.getF_CSMC());
		
		model.addAttribute("F_DWBH", F_DWBH);
		if(dwzd != null) model.addAttribute("F_DWMC", dwzd.getF_DWMC());
		
		model.addAttribute("F_XMBH", F_XMBH);
		if(xmzd != null) model.addAttribute("F_XMMC", xmzd.getF_XMMC());
		
		model.addAttribute("F_CLDJ", F_CLDJ);
		model.addAttribute("F_XMZT", F_XMZT);
		
		return "/mrp/queryAnalyse/action/projectCarryoverList";
	}
	
	/**
	 * 库存查询材料联查项目结转
	 * @param F_CKBH
	 * @param model
	 * @return
	 */
	@RequestMapping("/projectProductCarryover")
	public String projectProductCarryover(@RequestParam String F_CKBH, @RequestParam String F_CLBH, 
			                              @RequestParam String F_DWBH, @RequestParam String F_CSBH, 
			                              @RequestParam String F_CLDJ, @RequestParam String F_XMZT, 
			                              @RequestParam String F_XMBH, @RequestParam String F_CPBH, 
			                              EMPPageQuery page, Model model) {
		JParamObject           po = JParamObject.Create();
		HYCLZD               clzd = sysConfigureMgr.getHYCL(F_CLBH);
		HYCKZD               ckzd = sysConfigureMgr.getHYCKZD(F_CKBH);
		HYCSZD               cszd = sysConfigureMgr.getHYCS(F_CSBH);
		HYDWZD               dwzd = sysConfigureMgr.getHYDW(F_DWBH);
		HYXMZD               xmzd = sysConfigureMgr.getHYXM(F_XMBH);
		HYCPZD               cpzd = sysConfigureMgr.getHYCP(F_CPBH);
		EFDataSet    queryDataSet = null;
		EFRowSet      queryRowSet = null;
		
		po.SetValueByParamName("F_CKBH", F_CKBH);
		po.SetValueByParamName("F_CLBH", F_CLBH);
		po.SetValueByParamName("F_DWBH", F_DWBH);
		po.SetValueByParamName("F_CSBH", F_CSBH);
		po.SetValueByParamName("F_CPBH", F_CPBH);
		po.SetValueByParamName("F_XMBH", F_XMBH);
		po.SetValueByParamName("F_XMZT", F_XMZT);
		po.SetValueByParamName("F_CLDJ", F_CLDJ);
		po.setValue(EMPQueryParamEnum.PAGEQUERY, page);
		
		JResponseObject        RO = queryAnalyseServiceMgr.projectProductCarryoverDetails(po);
		queryRowSet = (EFRowSet) RO.getResponseObject();
		
		queryDataSet = queryRowSet.getDataSet(EMPQueryParamEnum.QUERYRESULT);
		page = (EMPPageQuery) queryRowSet.getExtProperty(EMPQueryParamEnum.PAGEQUERY, null);
		model.addAttribute("queryDataSet", queryDataSet);
		model.addAttribute("page", page);
		
		model.addAttribute("F_CKBH", F_CKBH);
		if(ckzd != null) model.addAttribute("F_CKMC", ckzd.getF_CKMC());
		
		model.addAttribute("F_CLBH", F_CLBH);
		if(clzd != null) model.addAttribute("F_CLMC", clzd.getF_CLMC());
		
		model.addAttribute("F_CSBH", F_CSBH);
		if(cszd != null) model.addAttribute("F_CSMC", cszd.getF_CSMC());
		
		model.addAttribute("F_DWBH", F_DWBH);
		if(dwzd != null) model.addAttribute("F_DWMC", dwzd.getF_DWMC());
		
		model.addAttribute("F_XMBH", F_XMBH);
		if(xmzd != null) model.addAttribute("F_XMMC", xmzd.getF_XMMC());
		
		model.addAttribute("F_CPBH", F_CPBH);
		if(cpzd != null) model.addAttribute("F_CPMC", cpzd.getF_CPMC());
		
		model.addAttribute("F_CLDJ", F_CLDJ);
		model.addAttribute("F_XMZT", F_XMZT);
		
		return "/mrp/queryAnalyse/action/projectProductCarryoverList";
	}

	/**
	 * 结转明细
	 * @param F_CKBH
	 * @param model
	 * @return
	 */
	@RequestMapping("/carryoverDetails")
	public String carryoverDetails(@RequestParam String F_CKBH, @RequestParam String F_CLBH, 
					               @RequestParam String F_DWBH, @RequestParam String F_CSBH, 
					               @RequestParam String F_CLDJ, @RequestParam String F_XMZT, 
					               @RequestParam String F_XMBH, @RequestParam String F_CPBH, 
					               EMPPageQuery page, Model model) {
		
		JParamObject           po = JParamObject.Create();
		HYCLZD               clzd = sysConfigureMgr.getHYCL(F_CLBH);
		HYCKZD               ckzd = sysConfigureMgr.getHYCKZD(F_CKBH);
		HYCSZD               cszd = sysConfigureMgr.getHYCS(F_CSBH);
		HYDWZD               dwzd = sysConfigureMgr.getHYDW(F_DWBH);
		HYXMZD               xmzd = sysConfigureMgr.getHYXM(F_XMBH);
		HYCPZD               cpzd = sysConfigureMgr.getHYCP(F_CPBH);
		EFDataSet    queryDataSet = null;
		EFRowSet      queryRowSet = null;
		
		po.SetValueByParamName("F_CKBH", F_CKBH);
		po.SetValueByParamName("F_CLBH", F_CLBH);
		po.SetValueByParamName("F_DWBH", F_DWBH);
		po.SetValueByParamName("F_CSBH", F_CSBH);
		po.SetValueByParamName("F_CPBH", F_CPBH);
		po.SetValueByParamName("F_XMBH", F_XMBH);
		po.SetValueByParamName("F_XMZT", F_XMZT);
		po.SetValueByParamName("F_CLDJ", F_CLDJ);	
		po.setValue(EMPQueryParamEnum.PAGEQUERY, page);
		
		JResponseObject        RO = queryAnalyseServiceMgr.carryoverDetails(po);
		queryRowSet = (EFRowSet) RO.getResponseObject();
		
		queryDataSet = queryRowSet.getDataSet(EMPQueryParamEnum.QUERYRESULT);
		page = (EMPPageQuery) queryRowSet.getExtProperty(EMPQueryParamEnum.PAGEQUERY, null);
		model.addAttribute("queryDataSet", queryDataSet);
		model.addAttribute("page", page);
		
		model.addAttribute("F_CKBH", F_CKBH);
		if(ckzd != null) model.addAttribute("F_CKMC", ckzd.getF_CKMC());
		
		model.addAttribute("F_CLBH", F_CLBH);
		if(clzd != null) model.addAttribute("F_CLMC", clzd.getF_CLMC());
		
		model.addAttribute("F_CSBH", F_CSBH);
		if(cszd != null) model.addAttribute("F_CSMC", cszd.getF_CSMC());
		
		model.addAttribute("F_DWBH", F_DWBH);
		if(dwzd != null) model.addAttribute("F_DWMC", dwzd.getF_DWMC());
		
		model.addAttribute("F_XMBH", F_XMBH);
		if(xmzd != null) model.addAttribute("F_XMMC", xmzd.getF_XMMC());
		
		model.addAttribute("F_CPBH", F_CPBH);
		if(cpzd != null) model.addAttribute("F_CPMC", cpzd.getF_CPMC());
		
		model.addAttribute("F_CLDJ", F_CLDJ);
		model.addAttribute("F_XMZT", F_XMZT);
		
		return "/mrp/queryAnalyse/action/carryoverDetailsList";
	}
	
	/**
	 * 联查单据
	 * @param F_CKBH
	 * @param model
	 * @return
	 */
	@RequestMapping("/formDetails")
	public String queryFormDetails(@RequestParam String F_KJQJ, @RequestParam String F_DJBH, 
					               @RequestParam String F_CRFX, Model model) {
		if(F_CRFX.equals("R")) {
			return loadStorageForm(F_DJBH, F_KJQJ, model);
		} else if(F_CRFX.equals("C")) {
			return loadOutBoundForm(F_DJBH, F_KJQJ, model);
		} else if(F_CRFX.equals("D")) {
			return loadTransfersForm(F_DJBH, F_KJQJ, model);
		} else {
			return loadBackGoodsForm(F_DJBH, F_KJQJ, model);
		}
	}
	
	//--------------------------------------------------------------------------------------------------
	//描述:加载入库单
	//设计: ES
	//实现: ES
	//修改:
	//--------------------------------------------------------------------------------------------------
	private String loadStorageForm(String F_DJBH, String F_KJQJ, Model model) {
		JParamObject PO = JParamObject.Create();
		PO.SetValueByParamName("F_KJQJ", F_KJQJ);
		PO.SetValueByParamName("F_DJBH", F_DJBH);
		
		List<HYRKDMX>   hyrkdmxList = storageMgr.searchHYRKDMX(F_KJQJ, F_DJBH, "", "", "", 0, 100);	
		HYRKD             hyrkdList = storageMgr.loadHYRKD(PO);
		
		hyrkdList.setF_DATE(StringUtil.formatDate(hyrkdList.getF_DATE()));
		model.addAttribute("hyrkdmxList", hyrkdmxList);
		model.addAttribute("hyrkd", hyrkdList);
		model.addAttribute("F_KJQJ", F_KJQJ);		
		model.addAttribute("F_DJBH", F_DJBH);
		model.addAttribute("F_RKLX", hyrkdList.getF_RKLX());
		model.addAttribute("F_CHDATE", hyrkdList.getF_CHDATE().getTime());
		return "/mrp/dailyBusiness/storage/clrkdHead";
    }
	 
	//--------------------------------------------------------------------------------------------------
	//描述:加载出库单
	//设计: ES
	//实现: ES
	//修改:
	//--------------------------------------------------------------------------------------------------
	public String loadOutBoundForm(String F_DJBH, String F_KJQJ, Model model) {
		List<HYCKDMX> hyckdmxList = outBoundMgr.searchHYCKDMXByDjbh(F_KJQJ, F_DJBH);	
		List<HYCKD>     hyckdList = outBoundMgr.searchHYCKD(F_KJQJ, F_DJBH, "", null, "", "", "", 0, 100);
		hyckdList.get(0).setF_DATE(StringUtil.formatDate(hyckdList.get(0).getF_DATE()));
		model.addAttribute("hyckdmxList", hyckdmxList);
		model.addAttribute("hyckd", hyckdList.get(0));
		model.addAttribute("F_KJQJ", F_KJQJ);		
		model.addAttribute("F_DJBH", F_DJBH);
		model.addAttribute("F_CHDATE", hyckdList.get(0).getF_CHDATE().getTime());
		return "/mrp/dailyBusiness/outBound/clckdHead";
	} 
	
	//--------------------------------------------------------------------------------------------------
	//描述:加载退货单
	//设计: ES
	//实现: ES
	//修改:
	//--------------------------------------------------------------------------------------------------
	public String loadBackGoodsForm(String F_DJBH, String F_KJQJ, Model model) {
		List<HYTHDMX> hythdmxList = backGoodsMgr.searchHYTHDMX(F_KJQJ, F_DJBH, 0, 100);	
		List<HYTHD> hythd = backGoodsMgr.searchHYTHD(F_KJQJ, F_DJBH, null, null, null, null, null, 0, 100);	
		hythd.get(0).setF_DATE(StringUtil.formatDate(hythd.get(0).getF_DATE()));
		model.addAttribute("hythdmxList", hythdmxList);
		model.addAttribute("hythd", hythd.get(0));
		model.addAttribute("F_KJQJ", F_KJQJ);		
		model.addAttribute("F_DJBH", F_DJBH);
		model.addAttribute("F_CHDATE", hythd.get(0).getF_CHDATE().getTime());
		return "/mrp/dailyBusiness/backgoods/clthdHead";
	}
	
	//--------------------------------------------------------------------------------------------------
	//描述:加载仓库调拨单
	//设计: ES
	//实现: ES
	//修改:
	//--------------------------------------------------------------------------------------------------
	public String loadTransfersForm(String F_DJBH,  String F_KJQJ, Model model) {
		List<HYDBDMX>   hydbdmxList = transfersMgr.searchHYDBDMX(F_KJQJ, F_DJBH, null, null, null, 0, 100);	
		List<HYDBD>       hydbdList = transfersMgr.searchHYDBD(F_KJQJ, F_DJBH, null, null, null, 0, 100);
		HYDBD                    po = hydbdList.get(0);
		po.setF_DATE(StringUtil.formatDate(po.getF_DATE()));
		model.addAttribute("hydbdmxList", hydbdmxList);
		model.addAttribute("hydbd", po);
		model.addAttribute("F_KJQJ", F_KJQJ);		
		model.addAttribute("F_DJBH", F_DJBH);
		model.addAttribute("F_CKBH", po.getF_CKBH());
		model.addAttribute("F_CHDATE", po.getF_CHDATE().getTime());
		return "/mrp/dailyBusiness/transfers/cldbdHead";
	}
	
	/**
	 * 材料明细联查项目查询
	 * @param F_CKBH
	 * @param model
	 * @return
	 */
	@RequestMapping("/materialDetailsLCProject")
	public String materialDetailsLCProject(@RequestParam String beginDate, @RequestParam String endDate, 
									       @RequestParam String F_CKBH, @RequestParam String F_CLBH, 
									       @RequestParam String F_DWBH, @RequestParam String F_CSBH, 
									       @RequestParam String F_XMBH, @RequestParam String F_CLDJ, 
									       EMPPageQuery page, Model model) {
		JParamObject           po = JParamObject.Create();
		HYCLZD               clzd = sysConfigureMgr.getHYCL(F_CLBH);
		HYCKZD               ckzd = sysConfigureMgr.getHYCKZD(F_CKBH);
		HYCSZD               cszd = sysConfigureMgr.getHYCS(F_CSBH);
		HYDWZD               dwzd = sysConfigureMgr.getHYDW(F_DWBH);
		EFDataSet    queryDataSet = null;
		EFRowSet      queryRowSet = null;
		
		po.SetValueByParamName("beginDate", beginDate);
		po.SetValueByParamName("endDate", endDate);
		po.SetValueByParamName("F_CKBH", F_CKBH);
		po.SetValueByParamName("F_CLBH", F_CLBH);
		po.SetValueByParamName("F_DWBH", F_DWBH);
		po.SetValueByParamName("F_CSBH", F_CSBH);
		po.SetValueByParamName("F_CLDJ", F_CLDJ);
		po.SetValueByParamName("F_XMBH", F_XMBH);
		po.setValue(EMPQueryParamEnum.PAGEQUERY, page);
		
		JResponseObject        RO = queryAnalyseServiceMgr.materialDetailsLCXMCP(po);
		queryRowSet = (EFRowSet) RO.getResponseObject();
		
		queryDataSet = queryRowSet.getDataSet(EMPQueryParamEnum.QUERYRESULT);
		page = (EMPPageQuery) queryRowSet.getExtProperty(EMPQueryParamEnum.PAGEQUERY, null);
		model.addAttribute("queryDataSet", queryDataSet);
		model.addAttribute("page", page);
		model.addAttribute("beginDate", beginDate);
		model.addAttribute("endDate", endDate);
		model.addAttribute("formatBeginDate", beginDate.replaceAll("-", "."));
		model.addAttribute("formatEndDate", endDate.replaceAll("-", "."));
		model.addAttribute("F_CKBH", F_CKBH);
		model.addAttribute("F_CLBH", F_CLBH);
		model.addAttribute("F_DWBH", F_DWBH);
		model.addAttribute("F_CSBH", F_CSBH);
		model.addAttribute("F_XMBH", F_XMBH);
		model.addAttribute("F_CLDJ", F_CLDJ);
		
		if(ckzd != null) model.addAttribute("F_CKMC", ckzd.getF_CKMC());
		if(clzd != null) model.addAttribute("F_CLMC", clzd.getF_CLMC());
		if(cszd != null) model.addAttribute("F_CSMC", cszd.getF_CSMC());
		if(dwzd != null) model.addAttribute("F_DWMC", dwzd.getF_DWMC());

		return "/mrp/queryAnalyse/action/materialDetailsLCProjectList";
	}
	
	/**
	 * 材料明细联查产品查询
	 * @param F_CKBH
	 * @param model
	 * @return
	 */
	@RequestMapping("/materialDetailsLCProduct")
	public String materialDetailsLCProduct(@RequestParam String beginDate, @RequestParam String endDate, 
									       @RequestParam String F_CKBH, @RequestParam String F_CLBH, 
									       @RequestParam String F_DWBH, @RequestParam String F_CSBH, 
									       @RequestParam String F_XMBH, @RequestParam String F_CLDJ, 
									       @RequestParam String F_CPBH, EMPPageQuery page, Model model) {
		JParamObject           po = JParamObject.Create();
		HYCLZD               clzd = sysConfigureMgr.getHYCL(F_CLBH);
		HYCKZD               ckzd = sysConfigureMgr.getHYCKZD(F_CKBH);
		HYCSZD               cszd = sysConfigureMgr.getHYCS(F_CSBH);
		HYDWZD               dwzd = sysConfigureMgr.getHYDW(F_DWBH);
		HYXMZD               xmzd = sysConfigureMgr.getHYXM(F_XMBH);
		HYCPZD               cpzd = sysConfigureMgr.getHYCP(F_CPBH);
		EFDataSet    queryDataSet = null;
		EFRowSet      queryRowSet = null;
		
		po.SetValueByParamName("beginDate", beginDate);
		po.SetValueByParamName("endDate", endDate);
		po.SetValueByParamName("F_CKBH", F_CKBH);
		po.SetValueByParamName("F_CLBH", F_CLBH);
		po.SetValueByParamName("F_DWBH", F_DWBH);
		po.SetValueByParamName("F_CSBH", F_CSBH);
		po.SetValueByParamName("F_CLDJ", F_CLDJ);
		po.SetValueByParamName("F_XMBH", F_XMBH);
		po.SetValueByParamName("F_CPBH", F_CPBH);
		po.SetValueByParamName("LCFLAG", "LCCP");
		po.setValue(EMPQueryParamEnum.PAGEQUERY, page);
		
		JResponseObject        RO = queryAnalyseServiceMgr.materialDetailsLCXMCP(po);
		queryRowSet = (EFRowSet) RO.getResponseObject();
		
		queryDataSet = queryRowSet.getDataSet(EMPQueryParamEnum.QUERYRESULT);
		page = (EMPPageQuery) queryRowSet.getExtProperty(EMPQueryParamEnum.PAGEQUERY, null);
		model.addAttribute("queryDataSet", queryDataSet);
		model.addAttribute("page", page);
		model.addAttribute("beginDate", beginDate);
		model.addAttribute("formatBeginDate", beginDate.replaceAll("-", "."));
		model.addAttribute("formatEndDate", endDate.replaceAll("-", "."));
		model.addAttribute("endDate", endDate);
		model.addAttribute("F_CKBH", F_CKBH);
		model.addAttribute("F_CLBH", F_CLBH);
		model.addAttribute("F_DWBH", F_DWBH);
		model.addAttribute("F_CSBH", F_CSBH);
		model.addAttribute("F_XMBH", F_XMBH);
		model.addAttribute("F_CPBH", F_CPBH);
		model.addAttribute("F_CLDJ", F_CLDJ);
		
		if(ckzd != null) model.addAttribute("F_CKMC", ckzd.getF_CKMC());
		if(clzd != null) model.addAttribute("F_CLMC", clzd.getF_CLMC());
		if(cszd != null) model.addAttribute("F_CSMC", cszd.getF_CSMC());
		if(dwzd != null) model.addAttribute("F_DWMC", dwzd.getF_DWMC());
		if(xmzd != null) model.addAttribute("F_XMMC", xmzd.getF_XMMC());
		if(cpzd != null) model.addAttribute("F_CPMC", cpzd.getF_CPMC());
		return "/mrp/queryAnalyse/action/materialDetailsLCProductList";
	}
	
	/**
	 * 材料联查明细查询
	 * @param F_CKBH
	 * @param model
	 * @return
	 */
	@RequestMapping("/materialDetailsLCDetails")
	public String materialDetailsLCDetails(@RequestParam String beginDate, @RequestParam String endDate, 
									       @RequestParam String F_CKBH, @RequestParam String F_CLBH, 
									       @RequestParam String F_DWBH, @RequestParam String F_CSBH, 
									       @RequestParam String F_XMBH, @RequestParam String F_CLDJ, 
									       EMPPageQuery page, Model model) {
		JParamObject           po = JParamObject.Create();
		HYCLZD               clzd = sysConfigureMgr.getHYCL(F_CLBH);
		HYCKZD               ckzd = sysConfigureMgr.getHYCKZD(F_CKBH);
		HYCSZD               cszd = sysConfigureMgr.getHYCS(F_CSBH);
		HYDWZD               dwzd = sysConfigureMgr.getHYDW(F_DWBH);
		HYXMZD               xmzd = sysConfigureMgr.getHYXM(F_XMBH);
		EFDataSet    queryDataSet = null;
		EFRowSet      queryRowSet = null;
		
		po.SetValueByParamName("beginDate", beginDate.replaceAll("-", ""));
		po.SetValueByParamName("endDate", endDate.replaceAll("-", ""));
		po.SetValueByParamName("F_CKBH", F_CKBH);
		po.SetValueByParamName("F_CLBH", F_CLBH);
		po.SetValueByParamName("F_DWBH", F_DWBH);
		po.SetValueByParamName("F_CSBH", F_CSBH);
		po.SetValueByParamName("F_CLDJ", F_CLDJ);
		po.SetValueByParamName("F_XMBH", F_XMBH);
		po.setValue(EMPQueryParamEnum.PAGEQUERY, page);
		
		JResponseObject        RO = queryAnalyseServiceMgr.materialDetailsLCDetails(po);
		queryRowSet = (EFRowSet) RO.getResponseObject();
		
		queryDataSet = queryRowSet.getDataSet(EMPQueryParamEnum.QUERYRESULT);
		page = (EMPPageQuery) queryRowSet.getExtProperty(EMPQueryParamEnum.PAGEQUERY, null);
		model.addAttribute("queryDataSet", queryDataSet);
		model.addAttribute("page", page);
		model.addAttribute("beginDate", beginDate);
		model.addAttribute("endDate", endDate);
		model.addAttribute("F_XMBH", F_XMBH);
		model.addAttribute("F_CKBH", F_CKBH);
		model.addAttribute("F_CLBH", F_CLBH);
		model.addAttribute("F_DWBH", F_DWBH);
		model.addAttribute("F_CSBH", F_CSBH);
		model.addAttribute("F_CLDJ", F_CLDJ);

		if(ckzd != null) model.addAttribute("F_CKMC", ckzd.getF_CKMC());
		if(clzd != null) model.addAttribute("F_CLMC", clzd.getF_CLMC());
		if(cszd != null) model.addAttribute("F_CSMC", cszd.getF_CSMC());
		if(dwzd != null) model.addAttribute("F_DWMC", dwzd.getF_DWMC());
		if(xmzd != null) model.addAttribute("F_XMMC", xmzd.getF_XMMC());
		return "/mrp/queryAnalyse/action/materialCarryoverDetailsList";
	}
	
	/**
	 * 项目联查产品查询
	 * @param F_CKBH
	 * @param model
	 * @return
	 */
	@RequestMapping("/projectDetailsLCProduct")
	public String projectLCProduct(@RequestParam String beginDate, @RequestParam String endDate, 
								   @RequestParam String F_XMBH, @RequestParam String F_CPBH, 
								   EMPPageQuery page, Model model) {
		JParamObject           po = JParamObject.Create();
		HYXMZD               xmzd = sysConfigureMgr.getHYXM(F_XMBH);
		EFDataSet    queryDataSet = null;
		EFRowSet      queryRowSet = null;
		
		po.SetValueByParamName("beginDate", beginDate.replaceAll("-", ""));
		po.SetValueByParamName("endDate", endDate.replaceAll("-", ""));
		po.SetValueByParamName("F_XMBH", F_XMBH);
		po.SetValueByParamName("F_CPBH", F_CPBH);
		po.SetValueByParamName("CXFLAG", "XMCP");
		po.SetValueByParamName("ORDERBY", " F_YYCPBH, F_YYXMBH");
		po.SetValueByParamName("GROUPBY", " F_YYCPBH, F_YYXMBH");
		po.setValue(EMPQueryParamEnum.PAGEQUERY, page);
		
		JResponseObject        RO = queryAnalyseServiceMgr.projectProductQuery(po);
		queryRowSet = (EFRowSet) RO.getResponseObject();
		
		queryDataSet = queryRowSet.getDataSet(EMPQueryParamEnum.QUERYRESULT);
		page = (EMPPageQuery) queryRowSet.getExtProperty(EMPQueryParamEnum.PAGEQUERY, null);
		model.addAttribute("queryDataSet", queryDataSet);
		model.addAttribute("page", page);
		model.addAttribute("beginDate", beginDate);
		model.addAttribute("formatBeginDate", beginDate.replaceAll("-", "."));
		model.addAttribute("formatEndDate", endDate.replaceAll("-", "."));
		model.addAttribute("endDate", endDate);
		model.addAttribute("F_XMBH", F_XMBH);
		model.addAttribute("F_CPBH", F_CPBH);

		if(xmzd != null) model.addAttribute("F_XMMC", xmzd.getF_XMMC());
		
		return "/mrp/queryAnalyse/action/projectDetailsLCProductList";
	}
	
	/**
	 * 项目产品材料联查用料调配查询(几个功能通用)
	 * @param F_CKBH
	 * @param model
	 * @return
	 */
	@RequestMapping("/xmcpclDetailsLCDeploy")
	public String xmcpclDetailsLCDeploy(@RequestParam String beginDate, @RequestParam String endDate, 
								        @RequestParam String F_XMBH,    @RequestParam String F_CPBH, 
								        @RequestParam String F_CLBH,    @RequestParam String CX,
								        EMPPageQuery page, Model model) {
		JParamObject           po = JParamObject.Create();
		HYCLZD               clzd = sysConfigureMgr.getHYCL(F_CLBH);
		HYXMZD               xmzd = sysConfigureMgr.getHYXM(F_XMBH);
		HYCPZD               cpzd = sysConfigureMgr.getHYCP(F_CPBH);
		EFDataSet    queryDataSet = null;
		EFRowSet      queryRowSet = null;
		
		po.SetValueByParamName("beginDate", beginDate.replaceAll("-", ""));
		po.SetValueByParamName("endDate", endDate.replaceAll("-", ""));
		po.SetValueByParamName("F_XMBH", F_XMBH);
		po.SetValueByParamName("F_CPBH", F_CPBH);
		po.SetValueByParamName("F_CLBH", F_CLBH);
		po.SetValueByParamName("CX", "CX");
		po.setValue(EMPQueryParamEnum.PAGEQUERY, page);
		
		JResponseObject        RO = queryAnalyseServiceMgr.xmcpclDetailsLCDeploy(po);
		queryRowSet = (EFRowSet) RO.getResponseObject();
		
		queryDataSet = queryRowSet.getDataSet(EMPQueryParamEnum.QUERYRESULT);
		page = (EMPPageQuery) queryRowSet.getExtProperty(EMPQueryParamEnum.PAGEQUERY, null);
		model.addAttribute("queryDataSet", queryDataSet);
		model.addAttribute("page", page);
		model.addAttribute("beginDate", beginDate);		
		model.addAttribute("endDate", endDate);
		model.addAttribute("F_XMBH", F_XMBH);
		model.addAttribute("F_CPBH", F_CPBH);
		model.addAttribute("F_CLBH", F_CLBH);
		model.addAttribute("CX", CX);
		model.addAttribute("formatBeginDate", beginDate.replaceAll("-", "."));
		model.addAttribute("formatEndDate", endDate.replaceAll("-", "."));
		
		if(clzd != null) {
			model.addAttribute("CL", "1");
			model.addAttribute("F_CLMC", clzd.getF_CLMC());
		}
		if(xmzd != null) {
			model.addAttribute("XM", "1");
			model.addAttribute("F_XMMC", xmzd.getF_XMMC());
		}
		if(cpzd != null) {
			model.addAttribute("CP", "1");
			model.addAttribute("F_CPMC", cpzd.getF_CPMC());
		}
		
		return "/mrp/queryAnalyse/action/projectProductLCDeployList";
	}
	
	/**
	 * 项目-产品联查材料
	 * @param F_CKBH
	 * @param model
	 * @return
	 */
	@RequestMapping("/projectProductDetailsLCMaterial")
	public String projectProductDetailsLCMaterial(@RequestParam String beginDate, @RequestParam String endDate, 
												  @RequestParam String F_CPBH, @RequestParam String F_CLBH, 
								                  @RequestParam String F_XMBH, EMPPageQuery page, Model model) {
		JParamObject           po = JParamObject.Create();
		HYXMZD               xmzd = sysConfigureMgr.getHYXM(F_XMBH);
		HYCPZD               cpzd = sysConfigureMgr.getHYCP(F_CPBH);
		EFDataSet    queryDataSet = null;
		EFRowSet      queryRowSet = null;
		
		po.SetValueByParamName("F_XMBH", F_XMBH);
		po.SetValueByParamName("F_CPBH", F_CPBH);
		po.SetValueByParamName("F_CLBH", F_CLBH);
		po.setValue(EMPQueryParamEnum.PAGEQUERY, page);
		
		JResponseObject        RO = queryAnalyseServiceMgr.projectProductDetailsLCMaterial(po);
		queryRowSet = (EFRowSet) RO.getResponseObject();
		
		queryDataSet = queryRowSet.getDataSet(EMPQueryParamEnum.QUERYRESULT);
		page = (EMPPageQuery) queryRowSet.getExtProperty(EMPQueryParamEnum.PAGEQUERY, null);
		model.addAttribute("queryDataSet", queryDataSet);
		model.addAttribute("page", page);
		model.addAttribute("beginDate", beginDate);		
		model.addAttribute("endDate", endDate);
		model.addAttribute("F_XMBH", F_XMBH);
		model.addAttribute("F_CPBH", F_CPBH);
		model.addAttribute("F_CLBH", F_CLBH);
		model.addAttribute("formatBeginDate", beginDate.replaceAll("-", "."));
		model.addAttribute("formatEndDate", endDate.replaceAll("-", "."));
		
		if(xmzd != null) model.addAttribute("F_XMMC", xmzd.getF_XMMC());
		if(cpzd != null) model.addAttribute("F_CPMC", cpzd.getF_CPMC());
		
		return "/mrp/queryAnalyse/action/projectProductDetailsLCMaterialList";
	}
	
	/**
	 * 产品联查材料
	 * @param F_CKBH
	 * @param model
	 * @return
	 */
	@RequestMapping("/productDetailsLCMaterial")
	public String productDetailsLCMaterial(@RequestParam String beginDate, @RequestParam String endDate, 
										   @RequestParam String F_CPBH, @RequestParam String F_CLBH, 
								           @RequestParam String F_XMBH, EMPPageQuery page, Model model) {
		JParamObject           po = JParamObject.Create();
		HYXMZD               xmzd = sysConfigureMgr.getHYXM(F_XMBH);
		HYCPZD               cpzd = sysConfigureMgr.getHYCP(F_CPBH);
		EFDataSet    queryDataSet = null;
		EFRowSet      queryRowSet = null;
		
		po.SetValueByParamName("F_XMBH", F_XMBH);
		po.SetValueByParamName("F_CPBH", F_CPBH);
		po.SetValueByParamName("F_CLBH", F_CLBH);
		po.setValue(EMPQueryParamEnum.PAGEQUERY, page);
		
		JResponseObject        RO = queryAnalyseServiceMgr.projectProductDetailsLCMaterial(po);
		queryRowSet = (EFRowSet) RO.getResponseObject();
		
		queryDataSet = queryRowSet.getDataSet(EMPQueryParamEnum.QUERYRESULT);
		page = (EMPPageQuery) queryRowSet.getExtProperty(EMPQueryParamEnum.PAGEQUERY, null);
		model.addAttribute("queryDataSet", queryDataSet);
		model.addAttribute("page", page);
		model.addAttribute("endDate", endDate);
		model.addAttribute("beginDate", beginDate);		
		model.addAttribute("F_XMBH", F_XMBH);
		model.addAttribute("F_CPBH", F_CPBH);
		model.addAttribute("F_CLBH", F_CLBH);
		model.addAttribute("formatBeginDate", beginDate.replaceAll("-", "."));
		model.addAttribute("formatEndDate", endDate.replaceAll("-", "."));
		
		if(xmzd != null) model.addAttribute("F_XMMC", xmzd.getF_XMMC());
		if(cpzd != null) model.addAttribute("F_CPMC", cpzd.getF_CPMC());
		
		return "/mrp/queryAnalyse/action/productDetailsLCMaterialList";
	}
	
	/**
	 * 项目联查申请单
	 * @param F_CKBH
	 * @param model
	 * @return
	 */
	@RequestMapping("/productDetailsLCSatement")
	public String productDetailsLCSatement(@RequestParam String beginDate, @RequestParam String endDate,  
								           @RequestParam String F_XMBH, EMPPageQuery page, Model model) {
		JParamObject           po = JParamObject.Create();

		po.SetValueByParamName("F_XMBH", F_XMBH);
		
		JResponseObject        RO = queryAnalyseServiceMgr.projectDetailsLCSatement(po);
		EFRowSet        applyForm = (EFRowSet) RO.getResponseObject();

		model.addAttribute("applyForm", applyForm);
		model.addAttribute("F_XMBH", F_XMBH);
		model.addAttribute("beginDate", beginDate);
		model.addAttribute("endDate", endDate);

		return "/mrp/queryAnalyse/action/applyForm";
	}
}
