package com.mrp.web.dailybusiness;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJacksonJsonView;

import com.etsoft.pub.util.EMPJsonUtil;
import com.etsoft.pub.util.EMPReflectUtil;
import com.etsoft.pub.util.StringFunction;
import com.etsoft.pub.util.StringUtil;
import com.etsoft.server.EMPFormUtil;
import com.mrp.biz.dailybusiness.BackGoodsServiceMgr;
import com.mrp.biz.dailybusiness.OutBoundServiceMgr;
import com.mrp.biz.dailybusiness.plugins.EMPFormDataCLJZTCK;
import com.mrp.biz.dicthelp.DictHelpServiceMgr;
import com.mrp.biz.server.plugins.util.FormModelDataResolverUtil;
import com.mrp.biz.sysconfigure.sysConfigureServiceMgr;
import com.mrp.persistence.dailyBusiness.backgoods.bean.HYTHD;
import com.mrp.persistence.dailyBusiness.backgoods.bean.HYTHDMX;
import com.mrp.persistence.dailyBusiness.outBound.bean.HYCKD;
import com.mrp.persistence.dailyBusiness.outBound.bean.HYCKDMX;
import com.mrp.persistence.sysConfigure.company.bean.HYCSZD;
import com.mrp.persistence.sysConfigure.department.bean.HYBMZD;
import com.mrp.persistence.sysConfigure.deport.bean.HYCKZD;
import com.mrp.persistence.sysConfigure.project.bean.HYXMZD;
import com.mrp.persistence.sysConfigure.user.bean.HYZGZD;

import dwz.web.BaseController;
import esyt.framework.com.builder.base.data.DataSetUtils;
import esyt.framework.com.builder.base.data.EFDataSet;
import esyt.framework.com.builder.base.data.EFRowSet;
import esyt.framework.com.dbform.bizmodel.EFFormDataModel;
import esyt.framework.com.dbform.bizmodel.SYS_MDL_VAL;
import esyt.framework.persistence.qxgl.beans.SYSUser;

@Controller
@RequestMapping(value="/dailyBusiness")
public class BackGoodsController extends BaseController{
	@Autowired
	private         BackGoodsServiceMgr       backGoodsMgr;
	
	@Autowired
	private          OutBoundServiceMgr        outBoundMgr;
	
	@Autowired
	private      sysConfigureServiceMgr    sysConfigureMgr;
	
	@Autowired
	private          DictHelpServiceMgr        dictHelpMgr;
	
	@Autowired
	private   FormModelDataResolverUtil      formModelUtil;
	
	@Autowired
	private                 EMPFormUtil           formUtil;
	
	@Autowired
	public                  HttpSession            session;

	/**
	 * 材料退货单项目选择管理
	 * @param model
	 * @return
	 */
	@RequestMapping("/backGoods/hyqj")
	public String backGoodsXm(Model model) {	
		Calendar               cal = Calendar.getInstance();
		int                   year = cal.get(Calendar.YEAR);
		int                  month = cal.get(Calendar.MONTH) + 1;
		String[]              kjqj = new String[2];		
		List<String>      kjndList = new ArrayList<String>();
		List<String[]>    kjqjList = new ArrayList<String[]>();
		
		for(int i = year -3; i <= year; i++) {
			kjndList.add(String.valueOf(i));
		}
		
		for(int i = 1; i <= 12; i++) {
			kjqj = new String[2];
			if(i < 10) kjqj[0] = "0" + i;
			else kjqj[0] = String.valueOf(i);
			if(i == month) kjqj[1] = "1";
			else kjqj[1] = "0";
			kjqjList.add(kjqj);
		}

		model.addAttribute("kjndList", kjndList);	
		model.addAttribute("kjqjList", kjqjList);	
		return "/mrp/dailyBusiness/backgoods/HYQJForm";
	}	
	
	//--------------------------------------------------------------------------------------------------
	//描述:材料出库、退货
	//设计: ES
	//实现: ES
	//修改:
	//--------------------------------------------------------------------------------------------------
	@RequestMapping("/backGoods/form")
	public String backGoodsList(@RequestParam("F_YEAR")  String  F_YEAR, 
								@RequestParam("F_MONTH") String F_MONTH,  
								@RequestParam("F_XMBH")  String  F_XMBH, 
								@RequestParam("F_CKBH")  String  F_CKBH, 
								Model model) {
		String                F_KJQJ = F_YEAR + F_MONTH;
		List<HYCKD>        hyckdList = backGoodsMgr.searchHYCKD(F_KJQJ, "", "", "1", "", F_CKBH, F_XMBH, 0, 100);
		List<HYTHD>        hythdList = backGoodsMgr.searchHYTHD(F_KJQJ, "", "", F_CKBH, F_XMBH, "0", "0", 0, 100);
		HYXMZD                  xmzd = sysConfigureMgr.getHYXM(F_XMBH);
		HYCKZD                  ckzd = sysConfigureMgr.getHYCKZD(F_CKBH);
		Calendar                 cal = Calendar.getInstance();
		int                     year = cal.get(Calendar.YEAR);
		String[]                kjqj = new String[2];		
		String[]                kjnd = new String[2];		
		List<String[]>      kjndList = new ArrayList<String[]>();
		List<String[]>      kjqjList = new ArrayList<String[]>();
		
		for(int i = year -3; i <= year; i++) {
			kjnd = new String[2];
			kjnd[0] = String.valueOf(i);
			if(i == Integer.parseInt(F_YEAR)) kjnd[1] = "1";
			else kjnd[1] = "0";
			kjndList.add(kjnd);
		}
		
		for(int i = 1; i <= 12; i++) {
			kjqj = new String[2];
			if(i < 10) kjqj[0] = "0" + i;
			else kjqj[0] = String.valueOf(i);
			if(i == Integer.parseInt(F_MONTH)) kjqj[1] = "1";
			else kjqj[1] = "0";
			kjqjList.add(kjqj);
		}	
		model.addAttribute("F_DJZT", "0");
		model.addAttribute("F_THLX", "0");
		model.addAttribute("F_KJQJ", F_KJQJ);
		model.addAttribute("F_XMBH", F_XMBH);
		model.addAttribute("F_XMMC", xmzd.getF_XMMC());
		model.addAttribute("F_CKBH", F_CKBH);
		model.addAttribute("F_CKMC", ckzd.getF_CKMC());
		model.addAttribute("hyckdList", hyckdList);
		model.addAttribute("hythdList", hythdList);
		model.addAttribute("kjndList", kjndList);	
		model.addAttribute("kjqjList", kjqjList);	
		return "/mrp/dailyBusiness/backgoods/clckthdFormList";
	}
	
	//--------------------------------------------------------------------------------------------------
	//描述:联查出库单
	//设计: ES
	//实现: ES
	//修改:
	//--------------------------------------------------------------------------------------------------
	@RequestMapping("/backGoods/lcDVForm/{F_DJBH}")
	public String lcDVForm(@RequestParam("F_GUID")       String EXT_BIZ_GUID, 
						   @PathVariable("F_DJBH")       String EXT_BIZ_DJBH, 
			               @RequestParam("EXT_BIZ_KJQJ") String EXT_BIZ_KJQJ,
			               @RequestParam("F_KJQJ")       String F_KJQJ,
						   Model model) {
		List<HYCKDMX> hyckdmxList = outBoundMgr.searchHYCKDMXByDjbh(EXT_BIZ_KJQJ, EXT_BIZ_DJBH);	
		List<HYCKD>     hyckdList = outBoundMgr.searchHYCKD(EXT_BIZ_KJQJ, EXT_BIZ_DJBH, "", null, "", "", "", 0, 100);
		hyckdList.get(0).setF_DATE(StringUtil.formatDate(hyckdList.get(0).getF_DATE()));
		model.addAttribute("hyckdmxList", hyckdmxList);
		model.addAttribute("hyckd", hyckdList.get(0));
		model.addAttribute("F_KJQJ", EXT_BIZ_KJQJ);		
		model.addAttribute("F_DJBH", EXT_BIZ_DJBH);
		model.addAttribute("F_CHDATE", hyckdList.get(0).getF_CHDATE().getTime());
		return "/mrp/dailyBusiness/outBound/clckdHead";
	}
	
	//--------------------------------------------------------------------------------------------------
	//描述:材料出库单生成退货单
	//设计: ES
	//实现: ES
	//修改:
	//--------------------------------------------------------------------------------------------------
	@RequestMapping("/backGoods/backStorageForm/{F_DJBH}")
	public String backStorageForm(@RequestParam("F_GUID")       String EXT_BIZ_GUID, 
								  @PathVariable("F_DJBH")       String EXT_BIZ_DJBH, 
			                      @RequestParam("EXT_BIZ_KJQJ") String EXT_BIZ_KJQJ,
			                      @RequestParam("F_KJQJ")       String F_KJQJ,
			                      Model model) throws Exception {
		String                 F_GUID = StringFunction.generateKey();
		String               F_FLGUID = StringFunction.generateKey();
		List<HYTHDMX>       thdmxList = new ArrayList<HYTHDMX>();
		EFDataSet           hythdmxDS = formUtil.createProductReturnFormByDVItem(F_KJQJ, F_GUID, F_FLGUID, EXT_BIZ_KJQJ, EXT_BIZ_DJBH);	
		HYTHD                   hythd = formUtil.createProductReturnFormByDVHead(F_KJQJ, F_GUID, EXT_BIZ_KJQJ, EXT_BIZ_DJBH);
		SYSUser                  user = (SYSUser) session.getAttribute("contextUser");
		java.util.Date       currTime = new java.util.Date();
		SimpleDateFormat    formatter = new SimpleDateFormat("yyyy-MM-dd");
		thdmxList = EMPReflectUtil.datasetReflectClass(HYTHDMX.class.getName(), hythdmxDS);
		hythd.setF_BZ("");
		hythd.setF_THRID("");
		hythd.setF_THRMC("");
		hythd.setF_BZRID(user.getUSER_ID());
		hythd.setF_BZRMC(user.getUSER_NAME());
		hythd.setF_DATE(formatter.format(currTime));	
		model.addAttribute("hythdmxList", thdmxList);
		model.addAttribute("hythd", hythd);
		return "/mrp/dailyBusiness/backgoods/clthdHead";
	}
	
	//--------------------------------------------------------------------------------------------------
	//描述:生成退货单并保存
	//设计: ES
	//实现: ES
	//修改:
	//--------------------------------------------------------------------------------------------------
	@RequestMapping(value="/backGoods/createSaveBackGoodsForm", method=RequestMethod.POST)
	public ModelAndView createSaveBackGoodsForm(@RequestBody Map dataMap, 
												@RequestParam("formType") String formType, 									  
										  		Model model) throws Exception {
		SimpleDateFormat   formatter = new SimpleDateFormat("yyyyMMdd");
		java.util.Date      currTime = new java.util.Date();
		EFRowSet          jsonRowSet = EMPJsonUtil.convertMapToRowSet(dataMap);
		EFDataSet        itemDataSet = jsonRowSet.getDataSet(HYTHDMX.class.getName());
		EFRowSet              rowset = EFRowSet.getInstance();
		List<HYCKDMX>    hyckdmxList = outBoundMgr.searchHYCKDMXByDjbh(jsonRowSet.getString("EXT_BIZ_KJQJ", ""), jsonRowSet.getString("EXT_BIZ_DJBH", ""));
		EFDataSet       ckdmxDataSet = DataSetUtils.javaBean2DataSet(hyckdmxList);
		EFDataSet       thdmxDataSet = convertHYCKDMX2HYTHDMX(ckdmxDataSet, itemDataSet);
		itemDataSet.setTableName("HYTHDMX");
		
		rowset.setDataMap(jsonRowSet.getDataMap());
		rowset.putString("F_DJZT", "0");
//		rowset.putString("F_BZ", StringFunction.ISO2UTF(rowset.getString("F_BZ", "")));
		rowset.putString("F_BZ", rowset.getString("F_BZ", ""));
		rowset.putString("F_DATE", formatter.format(currTime));
		rowset.remove(HYTHDMX.class.getName());
		rowset.setDataSet("HYTHDMX", thdmxDataSet);
		rowset.putString(EFFormDataModel._FORMEDITSAVETYPE_, formType);
		
		if(jsonRowSet.getString("F_THRID", "").equals("")) rowset.putString("F_THLX", "1");
		else rowset.putString("F_THLX", "0");
		
		EFFormDataModel formDataModel = formModelUtil.saveForm("HYTHD", rowset, SYS_MDL_VAL.CLTH, "SaveTHDResolver");
		if(formDataModel.getFormSaveStatus() == EFFormDataModel._FORM_SAVE_FAIL_) {
			return ajaxDoneError("保存失败！原因：<br>" + formDataModel.getFormSaveMessage());
		}
		rowset = formDataModel.getBillDataSet().getRowSet(0);
		ModelAndView modelView = ajaxDoneSuccess("单据分录保存成功！单据编号【" + rowset.getObject("F_DJBH", "") + "】");
		MappingJacksonJsonView view = (MappingJacksonJsonView)modelView.getView();
		Map attributes = view.getAttributesMap();
		String forwardUrl = attributes.get("forwardUrl").toString();
		forwardUrl += "?F_DJBH=" + rowset.getObject("F_DJBH", "") + "&F_KJQJ=" + rowset.getString("F_KJQJ", "") + "&F_GUID=" + rowset.getString("F_GUID", "");
		attributes.put("forwardUrl", forwardUrl);
		
		return modelView; 
	}
	
	//--------------------------------------------------------------------------------------------------
	//描述:生成退货单并提交
	//设计: ES
	//实现: ES
	//修改:
	//--------------------------------------------------------------------------------------------------
	@RequestMapping("/backGoods/createSubmitBackGoodsForm")
	public ModelAndView createSubmitBackGoodsForm(@RequestBody Map dataMap, 
									   			  @RequestParam("formType") String formType, 									  
										  		  Model model) throws Exception {
		SYSUser                user = (SYSUser) session.getAttribute("contextUser");
		EFDataSet       itemDataSet = EFDataSet.getInstance("HYTHDMX");
		EFRowSet             rowset = EFRowSet.getInstance();
		EFRowSet          itemRwSet = EFRowSet.getInstance();
		EFRowSet         jsonRowSet = EMPJsonUtil.convertMapToRowSet(dataMap);
		ModelAndView    modeAndView = checkForm(jsonRowSet);
		
		if(modeAndView != null) return modeAndView;

		rowset.putString("F_DJBH", jsonRowSet.getString("F_DJBH", ""));
		rowset.putString("F_KJQJ", jsonRowSet.getString("F_KJQJ", ""));
		rowset.putString("F_GUID", jsonRowSet.getString("F_GUID", ""));
		rowset.putString("F_SBRID", user.getUSER_ID());
		rowset.putString("F_DJZT", "1");
		rowset.putNumber("F_SBDATE", (new java.util.Date()).getTime());
		itemDataSet.insertRowSet(itemRwSet);
		itemRwSet.putString("F_KJQJ", jsonRowSet.getString("F_DJBH", ""));	
		rowset.putString("F_DJBH", jsonRowSet.getString("F_DJBH", ""));
		rowset.setDataSet("HYTHDMX", jsonRowSet.getDataSet(HYTHDMX.class.getName()));

		if(jsonRowSet.getString("F_THRID", "").equals("")) rowset.putString("F_THLX", "1");
		else rowset.putString("F_THLX", "0");
		
		EFFormDataModel formDataModel = formModelUtil.submitForm("HYTHD", rowset, "SubmitTHDResolver");
		rowset = formDataModel.getBillDataSet().getRowSet(0);
		itemDataSet = rowset.getDataSet("HYTHDMX");
		
		if(formDataModel.getFormSaveStatus() == EFFormDataModel._FORM_SAVE_FAIL_) {
			return ajaxDoneError("保存失败！原因：<br>" + formDataModel.getFormSaveMessage());
		}
		
		rowset = formDataModel.getBillDataSet().getRowSet(0);
		ModelAndView modelView = ajaxDoneSuccess("单据提交成功！单据编号【" + rowset.getObject("F_DJBH", "") + "】");
		MappingJacksonJsonView view = (MappingJacksonJsonView)modelView.getView();
		Map attributes = view.getAttributesMap();
		String forwardUrl = attributes.get("forwardUrl").toString();
		forwardUrl += "?F_DJBH=" + rowset.getObject("F_DJBH", "") + "&F_KJQJ=" + rowset.getString("F_KJQJ", "") + "&F_GUID=" + rowset.getString("F_GUID", "");
		attributes.put("forwardUrl", forwardUrl);
		
		return modelView; 
	}
	
	
	//--------------------------------------------------------------------------------------------------
	//描述:检查退货单在保存或者提交前是否有退货，否则出库数量容易出现负数
	//设计: ES
	//实现: ES
	//修改:
	//--------------------------------------------------------------------------------------------------
	private ModelAndView checkForm(EFRowSet rowset) {
		Map<String, EFRowSet>   flMap = new HashMap<String, EFRowSet>();
		Double                 bcthsl = 0.0;
		double                   cksl = 0.0;
		double                   thsl = 0.0;
		String                 F_KJQJ = rowset.getString("F_KJQJ", "");
		String                 F_GUID = rowset.getString("F_GUID", "");
		String           EXT_BIZ_KJQJ = rowset.getString("EXT_BIZ_KJQJ", "");
		String           EXT_BIZ_DJBH = rowset.getString("EXT_BIZ_DJBH", "");
		EFRowSet           itemRowset = null;
		EFRowSet        hythdmxRowset = null;
		EFDataSet         itemDataSet = rowset.getDataSet(HYTHDMX.class.getName());
		EFDataSet           hythdmxDS = formUtil.createProductReturnFormByDVItem(F_KJQJ, F_GUID, "", EXT_BIZ_KJQJ, EXT_BIZ_DJBH);	
		List<HYTHD>         hythdList = backGoodsMgr.searchHYTHD(rowset.getString("F_KJQJ", ""), rowset.getString("F_DJBH", ""), null, null, null, null, null, 0, 100);
		
		if(hythdList.size() > 0 && hythdList.get(0).getF_DJZT().equals("1")) {
			String tjDate =  StringUtil.getDateString("yyyy-MM-dd  HH:mm:ss", hythdList.get(0).getF_CHDATE());
			String msg = "保存提交失败,原因：<br>退货单据在本次提交前,已由【" + hythdList.get(0).getF_SBRID() 
			           + "-" + hythdList.get(0).getF_SBRMC() + "】在" + tjDate 
			           + "提交了单据。不允许修改单据！";
			return ajaxDoneError(msg);
		}
		
		rowset.putNumber("F_CRDATE", hythdList.get(0).getF_CRDATE().getTime());
		
		for(int i = 0; i < hythdmxDS.getRowCount(); i++) {
			hythdmxRowset = hythdmxDS.getRowSet(i);
			flMap.put(hythdmxRowset.getString("F_FLBH", ""), hythdmxRowset);
		}
		
		for(int i = 0; i < itemDataSet.getRowCount(); i++) {
			itemRowset = itemDataSet.getRowSet(i);
			hythdmxRowset = flMap.get(itemRowset.getString("F_FLBH", ""));
			if(hythdmxRowset == null) continue;
			try {
				bcthsl = itemRowset.getNumber("F_THSL", 0.0).doubleValue();
				cksl = hythdmxRowset.getNumber("F_CKSL", 0.0).doubleValue();
				thsl = hythdmxRowset.getNumber("F_THSL", 0.0).doubleValue();
				if(bcthsl > (cksl - thsl)) {
					return ajaxDoneError("保存失败,原因：<br>材料在退货单据提交前,材料【" + hythdmxRowset.getString("F_CLMC", "") + "】进行了退货,单据并已提交。<br>导致库存数量小于本次维护的退货数量，请重新维护本次退货数量并提交单据！<li>当前可退货数量为：" + (cksl - thsl) + "</li>");
				}
			} catch(Exception ce) {
				ce.printStackTrace();
			}
		}
		
		return null;
	}
	
	//--------------------------------------------------------------------------------------------------
	//描述:重新加载退货单
	//设计: ES
	//实现: ES
	//修改:
	//--------------------------------------------------------------------------------------------------
	@RequestMapping("/backGoods/reloadBackGoodsForm")
	public String reloadBackGoodsForm(@RequestParam("F_GUID") String F_GUID, 
									  @RequestParam("F_DJBH") String F_DJBH, 
			                          @RequestParam("F_KJQJ") String F_KJQJ,
			                          Model model) {
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
	//描述:查看出库单对应的所有材料退货单
	//设计: ES
	//实现: ES
	//修改:
	//--------------------------------------------------------------------------------------------------
	@RequestMapping("/backGoods/searchBackStorageForm/{F_DJBH}")
	public String backGoodsFormList(@RequestParam("F_GUID")       String EXT_BIZ_GUID, 
								    @PathVariable("F_DJBH")       String EXT_BIZ_DJBH, 
					                @RequestParam("EXT_BIZ_KJQJ") String EXT_BIZ_KJQJ,
					                @RequestParam("F_KJQJ")       String F_KJQJ,
					                @RequestParam("F_DJZT")       String F_DJZT,
					                @RequestParam("F_THLX")       String F_THLX,
									Model model) {
		if(F_THLX.equals("-1")) F_THLX = null;
		if(F_DJZT.equals("-1")) F_DJZT = null;
		
		List<HYTHD> hythdList = backGoodsMgr.searchHYTHDByHYCKD(F_KJQJ, EXT_BIZ_DJBH, F_THLX, F_DJZT, 0, 100);	
		model.addAttribute("F_THLX", F_THLX);
		model.addAttribute("F_DJZT", F_DJZT);
		model.addAttribute("F_KJQJ", F_KJQJ);
		model.addAttribute("EXT_BIZ_DJBH", EXT_BIZ_DJBH);
		model.addAttribute("EXT_BIZ_GUID", EXT_BIZ_GUID);
		model.addAttribute("EXT_BIZ_KJQJ", EXT_BIZ_KJQJ);
		model.addAttribute("hythdList", hythdList);
		return "/mrp/dailyBusiness/backgoods/lc/clthdFormList";
	}

	//--------------------------------------------------------------------------------------------------
	//描述:搜索材料退货单
	//设计: ES
	//实现: ES
	//修改:
	//--------------------------------------------------------------------------------------------------
	@RequestMapping("/backGoods/form/outBoundList")
	public String backGoodsFormOutBoundHeadList(@RequestParam("F_KJQJ") String F_KJQJ,
									    		@RequestParam("F_XMBH") String F_XMBH, 
									    		@RequestParam("F_CKBH") String F_CKBH, 
									    		@RequestParam("F_YEAR") String F_YEAR,
									    		@RequestParam("F_MONTH") String F_MONTH,
									    		Model model) {
		List<HYCKD>   hyckdList = backGoodsMgr.searchHYCKD(F_YEAR + F_MONTH, "", "", "1", "", F_CKBH, F_XMBH, 0, 100);
		Calendar            cal = Calendar.getInstance();
		int                year = cal.get(Calendar.YEAR);
		String[]           kjqj = new String[2];		
		String[]           kjnd = new String[2];		
		List<String[]> kjndList = new ArrayList<String[]>();
		List<String[]> kjqjList = new ArrayList<String[]>();
		HYXMZD             xmzd = sysConfigureMgr.getHYXM(F_XMBH);
		HYCKZD             ckzd = sysConfigureMgr.getHYCKZD(F_CKBH);
		
		for(int i = year -3; i <= year; i++) {
			kjnd = new String[2];
			kjnd[0] = String.valueOf(i);
			if(i == Integer.parseInt(F_YEAR)) kjnd[1] = "1";
			else kjnd[1] = "0";
			kjndList.add(kjnd);
		}
		
		for(int i = 1; i <= 12; i++) {
			kjqj = new String[2];
			if(i < 10) kjqj[0] = "0" + i;
			else kjqj[0] = String.valueOf(i);
			if(i == Integer.parseInt(F_MONTH)) kjqj[1] = "1";
			else kjqj[1] = "0";
			kjqjList.add(kjqj);
		}

		model.addAttribute("F_KJQJ", F_KJQJ);
		model.addAttribute("F_XMBH", F_XMBH);
		model.addAttribute("F_XMMC", xmzd.getF_XMMC());
		model.addAttribute("F_CKBH", F_CKBH);
		model.addAttribute("F_CKMC", ckzd.getF_CKMC());
		model.addAttribute("hyckdList", hyckdList);
		model.addAttribute("kjndList", kjndList);	
		model.addAttribute("kjqjList", kjqjList);	
		return "/mrp/dailyBusiness/backgoods/clckdFormList";
	}
	
	//--------------------------------------------------------------------------------------------------
	//描述:搜索材料退货单
	//设计: ES
	//实现: ES
	//修改:
	//--------------------------------------------------------------------------------------------------
	@RequestMapping("/backGoods/form/list")
	public String backGoodsFormHeadList(@RequestParam("F_KJQJ") String F_KJQJ,
									    @RequestParam("F_XMBH") String F_XMBH, 
									    @RequestParam("F_CKBH") String F_CKBH, 
									    @RequestParam("F_DJZT") String F_DJZT,
									    @RequestParam("F_THLX") String F_THLX,
									    Model model) {
		
		if(F_THLX.equals("-1")) F_THLX = null;
		if(F_DJZT.equals("-1")) F_DJZT = null;
		
		List<HYTHD>        hythdList = backGoodsMgr.searchHYTHD(F_KJQJ, "", "", F_CKBH, F_XMBH, F_THLX, F_DJZT, 0, 100);
		HYXMZD                  xmzd = sysConfigureMgr.getHYXM(F_XMBH);
		HYCKZD                  ckzd = sysConfigureMgr.getHYCKZD(F_CKBH);

		model.addAttribute("F_DJZT", F_DJZT);
		model.addAttribute("F_THLX", F_THLX);
		model.addAttribute("F_KJQJ", F_KJQJ);
		model.addAttribute("F_XMBH", F_XMBH);
		model.addAttribute("F_XMMC", xmzd.getF_XMMC());
		model.addAttribute("F_CKBH", F_CKBH);
		model.addAttribute("F_CKMC", ckzd.getF_CKMC());
		model.addAttribute("hythdList", hythdList);	
		return "/mrp/dailyBusiness/backgoods/clthdFormList";
	}
	
	@RequestMapping("/backGoods/help/departmentUser/department")
	public String userList(Model model) {
		List<HYBMZD> hybmList = dictHelpMgr.searchHYBMZD();	
		model.addAttribute("hybmList", hybmList);
		return "/mrp/dailyBusiness/backgoods/help/userHelp/bmzgHelp";
	}
	
	//--------------------------------------------------------------------------------------------------
	//描述:
	//设计: ES
	//实现: ES
	//修改:
	//--------------------------------------------------------------------------------------------------
	@RequestMapping("/backGoods/help/departmentUser/user/{F_SXBM}")
	public String departmentUserList(@PathVariable("F_SXBM") String F_SXBM, Model model) {
		List<HYZGZD> userList = dictHelpMgr.searchUseHYZGZD(F_SXBM, "1", 0, 100);
		model.addAttribute("userList", userList);
		model.addAttribute("F_SXBM", F_SXBM);
		return "/mrp/dailyBusiness/backgoods/help/userHelp/userList";
	}
	
	//--------------------------------------------------------------------------------------------------
	//描述:
	//设计: ES
	//实现: ES
	//修改:
	//--------------------------------------------------------------------------------------------------
	@RequestMapping("/backGoods/help/departmentUser/user/search/{F_SXBM}")
	public String userList(@PathVariable("F_SXBM") String F_SXBM, 
						   @RequestParam String keywords,
						   Model model) {
		List<HYZGZD> userList = dictHelpMgr.searchUseHYZGZD(F_SXBM, keywords, "1", 0, 100);
		model.addAttribute("userList", userList);
		model.addAttribute("F_SXBM", F_SXBM);
		return "/mrp/dailyBusiness/backgoods/help/userHelp/userList";
	}
	
	//--------------------------------------------------------------------------------------------------
	//描述:
	//设计: ES
	//实现: ES
	//修改:
	//--------------------------------------------------------------------------------------------------
	@RequestMapping("/backGoods/help/companyHelp")
	public String companyList(@RequestParam String F_CSBH, Model model) {
		List<HYCSZD> hycsList = dictHelpMgr.searchHYCS(F_CSBH, "1", 0, 100);
		model.addAttribute("F_CSBH", F_CSBH);	
		model.addAttribute("hycsList", hycsList);
		return "/mrp/dailyBusiness/backgoods/help/companyHelp";
	}
	
	private EFDataSet convertHYCKDMX2HYTHDMX(EFDataSet ckdmxDataSet, EFDataSet thdmxDataset) {
		EFDataSet          dataset = EFDataSet.getInstance("HYTHDMX");
		EFRowSet       ckdmxRowset = null;
		EFRowSet       thdmxRowset = null;
		String                 key = "";
		Map<String, EFRowSet>  map = new HashMap<String, EFRowSet>();
		for(int i = 0; i < ckdmxDataSet.getRowCount(); i++) {
			ckdmxRowset = ckdmxDataSet.getRowSet(i);
			key = ckdmxRowset.getString("F_KJQJ", "") + "@";
			key += ckdmxRowset.getString("F_DJBH", "") + "@";
			key += ckdmxRowset.getString("F_FLBH", "");
			
			ckdmxRowset.putString("EXT_BIZ_KJQJ", ckdmxRowset.getString("F_KJQJ", ""));
			ckdmxRowset.putString("EXT_BIZ_DJBH", ckdmxRowset.getString("F_DJBH", ""));
			ckdmxRowset.putString("EXT_BIZ_FLGUID", ckdmxRowset.getString("F_FLGUID", ""));
			ckdmxRowset.putString("EXT_BIZ_FLBH", ckdmxRowset.getString("F_FLBH", ""));
			
			ckdmxRowset.getDataMap().remove("F_KJQJ");
			ckdmxRowset.getDataMap().remove("F_DJBH");
			ckdmxRowset.getDataMap().remove("F_GUID");
			ckdmxRowset.getDataMap().remove("F_FLGUID");
			ckdmxRowset.getDataMap().remove("F_FLBH");
			ckdmxRowset.getDataMap().remove("F_CRDATE");
			ckdmxRowset.getDataMap().remove("F_CHDATE");
			map.put(key, ckdmxRowset);
		}
		
		for(int i = 0; i < thdmxDataset.getRowCount(); i++) {
			thdmxRowset = thdmxDataset.getRowSet(i);
			key = thdmxRowset.getString("EXT_BIZ_KJQJ", "") + "@";
			key += thdmxRowset.getString("EXT_BIZ_DJBH", "") + "@";
			key += thdmxRowset.getString("EXT_BIZ_FLBH", "");
			ckdmxRowset = map.get(key);
			
			ckdmxRowset.putString("F_KJQJ", thdmxRowset.getString("F_KJQJ", ""));
			ckdmxRowset.putString("F_DJBH", thdmxRowset.getString("F_DJBH", ""));
			ckdmxRowset.putString("F_GUID", thdmxRowset.getString("F_GUID", ""));
			ckdmxRowset.putString("F_FLGUID", thdmxRowset.getString("F_FLGUID", ""));
			ckdmxRowset.putString("F_FLBH", thdmxRowset.getString("F_FLBH", ""));
			ckdmxRowset.putObject("F_THSL", thdmxRowset.getObject("F_THSL", 0.0));
			dataset.insertRowSet(ckdmxRowset);
		}
		return dataset;
	}
}
