package com.mrp.web.queryAnalyse;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.etsoft.pub.enums.EMPQueryParamEnum;
import com.mrp.biz.queryAnalyse.QueryAnalyseServiceMgr;
import com.mrp.biz.sysconfigure.sysConfigureServiceMgr;
import com.mrp.persistence.queryAnalyse.pageQuery.bean.EMPPageQuery;
import com.mrp.persistence.sysConfigure.deport.bean.HYCKZD;

import dwz.web.BaseController;
import esyt.framework.com.bridge.JResponseObject;
import esyt.framework.com.builder.base.data.EFDataSet;
import esyt.framework.com.builder.base.data.EFRowSet;
import esyt.framework.com.eai.data.JParamObject;

@Controller
@RequestMapping(value="/queryAnalyse")
public class EMPQueryAnalyseController extends BaseController{
	@Autowired
	private QueryAnalyseServiceMgr queryAnalyseServiceMgr;
	
	@Autowired
	private sysConfigureServiceMgr        sysConfigureMgr;
	
	@Autowired
	public HttpSession session;

	/**
	 * 库存查询
	 * @param F_CKBH
	 * @param model
	 * @return
	 */
	@RequestMapping("/warehouseMaterial")
	public String searchWarehouseMaterial(@RequestParam String F_CKBH, @RequestParam String F_CLBH, EMPPageQuery page, Model model) {
		JParamObject           po = JParamObject.Create();
		EFDataSet    queryDataSet = null;
		EFRowSet      queryRowSet = null;
		HYCKZD               ckzd = sysConfigureMgr.getHYCKZD(F_CKBH);
		
		po.SetValueByParamName("F_CKBH", F_CKBH);
		po.SetValueByParamName("F_CLBH", F_CLBH);
		po.setValue(EMPQueryParamEnum.PAGEQUERY, page);
		
		JResponseObject        RO = queryAnalyseServiceMgr.searchWarehouseMaterial(po);
		queryRowSet = (EFRowSet) RO.getResponseObject();
		
		queryDataSet = queryRowSet.getDataSet(EMPQueryParamEnum.QUERYRESULT);
		page = (EMPPageQuery) queryRowSet.getExtProperty(EMPQueryParamEnum.PAGEQUERY, null);
		model.addAttribute("queryDataSet", queryDataSet);
		model.addAttribute("page", page);
		model.addAttribute("F_CKBH", F_CKBH);
		if(ckzd != null) model.addAttribute("F_CKMC", ckzd.getF_CKMC());
		return "/mrp/queryAnalyse/warehouseMaterialList";
	}
	
	/**
	 * 材料明细查询参数选择界面
	 * @param F_CKBH
	 * @param model
	 * @return
	 */
	@RequestMapping("/preQueryForm/materialQueryPage")
	public String materialQueryPage(Model model) {
		String               F_YEAR = "";
		Calendar                cal = Calendar.getInstance();
		java.util.Date     currTime = new java.util.Date();
		SimpleDateFormat  formatter = new SimpleDateFormat("yyyy-MM-dd");
		F_YEAR = String.valueOf(cal.get(Calendar.YEAR));

		model.addAttribute("beginDate", F_YEAR + "-01-01");
		model.addAttribute("endDate", formatter.format(currTime));		
		return "/mrp/queryAnalyse/preQueryForm/materialQueryPage";
	}
	
	/**
	 * 材料明细查询
	 * @param F_CKBH
	 * @param model
	 * @return
	 */
	@RequestMapping("/materialDetails")
	public String materialDetails(@RequestParam String beginDate, @RequestParam String endDate, 
								  @RequestParam String F_CKBH, @RequestParam String F_XMBH, @RequestParam String F_CLBH, 
								  EMPPageQuery page, Model model) {
		JParamObject           po = JParamObject.Create();
		EFDataSet    queryDataSet = null;
		EFRowSet      queryRowSet = null;
		
		po.SetValueByParamName("beginDate", beginDate);
		po.SetValueByParamName("endDate", endDate);
		po.SetValueByParamName("F_CKBH", F_CKBH);
		po.SetValueByParamName("F_XMBH", F_XMBH);
		po.SetValueByParamName("F_CLBH", F_CLBH);
		po.setValue(EMPQueryParamEnum.PAGEQUERY, page);
		
		JResponseObject        RO = queryAnalyseServiceMgr.projectMaterialQuery(po);
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
		model.addAttribute("F_XMBH", F_XMBH);
		model.addAttribute("F_CLBH", F_CLBH);
		return "/mrp/queryAnalyse/materialDetailsList";
	}
	
	/**
	 * 项目查询参数选择界面
	 * @param F_CKBH
	 * @param model
	 * @return
	 */
	@RequestMapping("/preQueryForm/projectQueryPage")
	public String projectQueryPage(Model model) {
		String               F_YEAR = "";
		Calendar                cal = Calendar.getInstance();
		java.util.Date     currTime = new java.util.Date();
		SimpleDateFormat  formatter = new SimpleDateFormat("yyyy-MM-dd");
		F_YEAR = String.valueOf(cal.get(Calendar.YEAR));

		model.addAttribute("beginDate", F_YEAR + "-01-01");
		model.addAttribute("endDate", formatter.format(currTime));		
		return "/mrp/queryAnalyse/preQueryForm/projectQueryPage";
	}
	
	/**
	 * 项目查询
	 * @param F_CKBH
	 * @param model
	 * @return
	 */
	@RequestMapping("/projectDetails")
	public String projectDetails(@RequestParam String beginDate, @RequestParam String endDate, 
								 @RequestParam String F_XMBH, EMPPageQuery page, Model model) {
		JParamObject           po = JParamObject.Create();
		EFDataSet    queryDataSet = null;
		EFRowSet      queryRowSet = null;
		
		po.SetValueByParamName("beginDate", beginDate.replaceAll("-", ""));
		po.SetValueByParamName("endDate", endDate.replaceAll("-", ""));
		po.SetValueByParamName("F_XMBH", F_XMBH);
		po.SetValueByParamName("ORDERBY", " F_YYXMBH");
		po.SetValueByParamName("GROUPBY", " F_YYXMBH");
		po.setValue(EMPQueryParamEnum.PAGEQUERY, page);
		
		JResponseObject        RO = queryAnalyseServiceMgr.projectDetailsQuery(po);
		queryRowSet = (EFRowSet) RO.getResponseObject();
		
		queryDataSet = queryRowSet.getDataSet(EMPQueryParamEnum.QUERYRESULT);
		page = (EMPPageQuery) queryRowSet.getExtProperty(EMPQueryParamEnum.PAGEQUERY, null);
		model.addAttribute("queryDataSet", queryDataSet);
		model.addAttribute("page", page);
		model.addAttribute("beginDate", beginDate);
		model.addAttribute("endDate", endDate);
		model.addAttribute("formatBeginDate", beginDate.replaceAll("-", "."));
		model.addAttribute("formatEndDate", endDate.replaceAll("-", "."));
		model.addAttribute("F_XMBH", F_XMBH);
		
		return "/mrp/queryAnalyse/projectDetailsList";
	}
	
	/**
	 * 项目产品明细查询参数选择界面
	 * @param F_CKBH
	 * @param model
	 * @return
	 */
	@RequestMapping("/preQueryForm/productQueryPage")
	public String productQueryPage(Model model) {
		String               F_YEAR = "";
		Calendar                cal = Calendar.getInstance();
		java.util.Date     currTime = new java.util.Date();
		SimpleDateFormat  formatter = new SimpleDateFormat("yyyy-MM-dd");
		F_YEAR = String.valueOf(cal.get(Calendar.YEAR));

		model.addAttribute("beginDate", F_YEAR + "-01-01");
		model.addAttribute("endDate", formatter.format(currTime));		
		return "/mrp/queryAnalyse/preQueryForm/productQueryPage";
	}
	
	/**
	 * 项目产品明细查询
	 * @param F_CKBH
	 * @param model
	 * @return
	 */
	@RequestMapping("/productDetails")
	public String productDetails(@RequestParam String beginDate, @RequestParam String endDate, 
								 @RequestParam String F_CPBH, EMPPageQuery page, Model model) {
		JParamObject           po = JParamObject.Create();
		EFDataSet    queryDataSet = null;
		EFRowSet      queryRowSet = null;
		
		po.SetValueByParamName("beginDate", beginDate.replaceAll("-", ""));
		po.SetValueByParamName("endDate", endDate.replaceAll("-", ""));
		po.SetValueByParamName("F_CPBH", F_CPBH);
		po.SetValueByParamName("ORDERBY", " F_YYCPBH, F_YYXMBH");
		po.SetValueByParamName("GROUPBY", " F_YYCPBH, F_YYXMBH");
		po.SetValueByParamName("CXFLAG", "XMCP");	
		po.setValue(EMPQueryParamEnum.PAGEQUERY, page);
		
		JResponseObject        RO = queryAnalyseServiceMgr.projectProductQuery(po);
		queryRowSet = (EFRowSet) RO.getResponseObject();
		
		queryDataSet = queryRowSet.getDataSet(EMPQueryParamEnum.QUERYRESULT);
		page = (EMPPageQuery) queryRowSet.getExtProperty(EMPQueryParamEnum.PAGEQUERY, null);
		model.addAttribute("queryDataSet", queryDataSet);
		model.addAttribute("beginDate", beginDate);
		model.addAttribute("endDate", endDate);
		model.addAttribute("formatBeginDate", beginDate.replaceAll("-", "."));
		model.addAttribute("formatEndDate", endDate.replaceAll("-", "."));
		model.addAttribute("F_CPBH", F_CPBH);
		
		return "/mrp/queryAnalyse/productDetailsList";
	}
	
	/**
	 * 系统首页材料预警查询
	 * @param F_CKBH
	 * @param model
	 * @return
	 */
	@RequestMapping("/homePage/clyj")
	public String homePageClyj(Model model) {
		JParamObject           po = JParamObject.Create();
		po.SetValueByParamName("CXFLAG", "CLYJ");
		JResponseObject        RO = queryAnalyseServiceMgr.SystemsHomeInfo(po);
		EFRowSet           rowset = (EFRowSet) RO.getResponseObject();
		
		rowset = (EFRowSet) RO.getResponseObject();
		model.addAttribute("SYSTEMHOME", rowset);	
		
		return "/mrp/home/clyj";
	}

	/**
	 * 系统首页材料入库单查询
	 * @param F_CKBH
	 * @param model
	 * @return
	 */
	@RequestMapping("/homePage/hyrkd")
	public String homePageHyrkd(@RequestParam String USER_ID, Model model) {
		JParamObject           po = JParamObject.Create();
		po.SetValueByEnvName("USER_ID", USER_ID);
		po.SetValueByParamName("CXFLAG", "HYRKD");
		JResponseObject        RO = queryAnalyseServiceMgr.SystemsHomeInfo(po);
		EFRowSet           rowset = (EFRowSet) RO.getResponseObject();
		
		rowset = (EFRowSet) RO.getResponseObject();
		model.addAttribute("USER_ID", USER_ID);	
		model.addAttribute("SYSTEMHOME", rowset);	
		
		return "/mrp/home/hyrkd";
	}
	
	/**
	 * 系统首页材料出库单查询
	 * @param F_CKBH
	 * @param model
	 * @return
	 */
	@RequestMapping("/homePage/hyckd")
	public String homePageHyckd(@RequestParam String USER_ID, Model model) {
		JParamObject           po = JParamObject.Create();
		po.SetValueByEnvName("USER_ID", USER_ID);
		po.SetValueByParamName("CXFLAG", "HYCKD");
		JResponseObject        RO = queryAnalyseServiceMgr.SystemsHomeInfo(po);
		EFRowSet           rowset = (EFRowSet) RO.getResponseObject();
		
		rowset = (EFRowSet) RO.getResponseObject();
		model.addAttribute("USER_ID", USER_ID);	
		model.addAttribute("SYSTEMHOME", rowset);	
		
		return "/mrp/home/hyckd";
	}
	
	/**
	 * 系统首页材料退货单查询
	 * @param F_CKBH
	 * @param model
	 * @return
	 */
	@RequestMapping("/homePage/hythd")
	public String homePageHythd(@RequestParam String USER_ID, Model model) {
		JParamObject           po = JParamObject.Create();
		po.SetValueByEnvName("USER_ID", USER_ID);
		po.SetValueByParamName("CXFLAG", "HYTHD");
		JResponseObject        RO = queryAnalyseServiceMgr.SystemsHomeInfo(po);
		EFRowSet           rowset = (EFRowSet) RO.getResponseObject();
		
		rowset = (EFRowSet) RO.getResponseObject();
		model.addAttribute("USER_ID", USER_ID);	
		model.addAttribute("SYSTEMHOME", rowset);	
		
		return "/mrp/home/hythd";
	}
	
	/**
	 * 系统首页材料调拨单查询
	 * @param F_CKBH
	 * @param model
	 * @return
	 */
	@RequestMapping("/homePage/hydbd")
	public String homePageHydbd(@RequestParam String USER_ID, Model model) {
		JParamObject           po = JParamObject.Create();
		po.SetValueByEnvName("USER_ID", USER_ID);
		po.SetValueByParamName("CXFLAG", "HYDBD");
		JResponseObject        RO = queryAnalyseServiceMgr.SystemsHomeInfo(po);
		EFRowSet           rowset = (EFRowSet) RO.getResponseObject();
		
		rowset = (EFRowSet) RO.getResponseObject();
		model.addAttribute("USER_ID", USER_ID);	
		model.addAttribute("SYSTEMHOME", rowset);	
		
		return "/mrp/home/hydbd";
	}
	
	/**
	 * 系统首页材料厂商推过查询
	 * @param F_CKBH
	 * @param model
	 * @return
	 */
	@RequestMapping("/homePage/clcsth")
	public String homePageClcsth(Model model) {
		JParamObject           po = JParamObject.Create();
		po.SetValueByParamName("CXFLAG", "CLCSTH");
		JResponseObject        RO = queryAnalyseServiceMgr.SystemsHomeInfo(po);
		EFRowSet           rowset = (EFRowSet) RO.getResponseObject();
		
		rowset = (EFRowSet) RO.getResponseObject();
		model.addAttribute("SYSTEMHOME", rowset);	
		
		return "/mrp/home/clcsth";
	}
}
