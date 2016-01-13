package com.mrp.web.dailybusiness;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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

import com.mrp.biz.dailybusiness.OutBoundServiceMgr;
import com.mrp.biz.dailybusiness.plugins.EMPFormDataCLJZOCK;
import com.mrp.biz.dicthelp.DictHelpServiceMgr;
import com.mrp.biz.server.EMPCheckFormUtil;
import com.mrp.biz.server.plugins.util.FormModelDataResolverUtil;
import com.mrp.biz.sysconfigure.sysConfigureServiceMgr;
import com.mrp.persistence.dailyBusiness.outBound.bean.HYCKD;
import com.mrp.persistence.dailyBusiness.outBound.bean.HYCKDMX;
import com.mrp.persistence.sysConfigure.department.bean.HYBMZD;
import com.mrp.persistence.sysConfigure.deport.bean.HYCKZD;
import com.mrp.persistence.sysConfigure.project.bean.HYXMZD;
import com.mrp.persistence.sysConfigure.projectProduct.bean.HYXMCP;
import com.etsoft.pub.util.EMPJsonUtil;
import com.etsoft.pub.util.EMPReflectUtil;
import com.etsoft.pub.util.StringFunction;
import com.etsoft.pub.util.StringUtil;
import com.etsoft.server.EMPFormUtil;

import dwz.web.BaseController;
import esyt.framework.com.builder.base.data.DataSetUtils;
import esyt.framework.com.builder.base.data.EFDataSet;
import esyt.framework.com.builder.base.data.EFRowSet;
import esyt.framework.com.dbform.bizmodel.EFFormDataModel;
import esyt.framework.com.dbform.bizmodel.SYS_MDL_VAL;
import esyt.framework.persistence.qxgl.beans.SYSUser;

@Controller
@RequestMapping(value="/dailyBusiness")
public class OutBoundController extends BaseController{
	@Autowired
	private          OutBoundServiceMgr      outBoundMgr;

	@Autowired
	private      sysConfigureServiceMgr  sysConfigureMgr;
	
	@Autowired
	private   FormModelDataResolverUtil    formModelUtil;

	@Autowired
	private                 EMPFormUtil         formUtil;
	
	@Autowired
	public                  HttpSession          session;
	
	@Autowired
	private            EMPCheckFormUtil    checkFormUtil;
	
	@Autowired
	private          DictHelpServiceMgr      dictHelpMgr;
	
	/**
	 * 材料出库单期间范围选择
	 * @param model
	 * @return
	 */
	@RequestMapping("/outBound/hyqj")
	public String outBoundHyqj(Model model) {	
		Calendar              cal = Calendar.getInstance();
		int                  year = cal.get(Calendar.YEAR);
		int                 month = cal.get(Calendar.MONTH) + 1;
		String[]             kjqj = new String[2];		
		List<String>     kjndList = new ArrayList<String>();
		List<String[]>   kjqjList = new ArrayList<String[]>();
		
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
		return "/mrp/dailyBusiness/outBound/HYQJForm";
	}	
	
	//--------------------------------------------------------------------------------------------------
	//描述:材料出库单
	//设计: ES
	//实现: ES
	//修改:
	//--------------------------------------------------------------------------------------------------
	@RequestMapping("/outBound")
	public String outBoundList(@RequestParam("F_YEAR") String F_YEAR, 
		    				   @RequestParam("F_MONTH") String F_MONTH, 
		    				   @RequestParam("F_XMBH") String F_XMBH, 
		    				   @RequestParam("F_CKBH") String F_CKBH, 
		    				   Model model) {		
		String           F_KJQJ = F_YEAR + F_MONTH;
		//F_KJQJ, F_DJBH, F_GUID, F_DJZT, F_DATE, F_CKBH, F_XMBH, startIndex, count
		List<HYCKD>   hyckdList = outBoundMgr.searchHYCKD(F_KJQJ, "", "", "0", "", F_CKBH, F_XMBH, 0, 100);
		HYXMZD             xmzd = sysConfigureMgr.getHYXM(F_XMBH);
		HYCKZD             ckzd = sysConfigureMgr.getHYCKZD(F_CKBH);
		model.addAttribute("F_KJQJ", F_KJQJ);	
		model.addAttribute("hyckdList", hyckdList);
		model.addAttribute("F_DJZT", "0");
		model.addAttribute("F_XMBH", F_XMBH);
		model.addAttribute("F_XMMC", xmzd.getF_XMMC());
		model.addAttribute("F_CKBH", F_CKBH);
		model.addAttribute("F_CKMC", ckzd.getF_CKMC());
		model.addAttribute("F_YEAR", F_YEAR);
		model.addAttribute("F_MONTH", F_MONTH);
		return "/mrp/dailyBusiness/outBound/clckdFormList";
	}
	
	//--------------------------------------------------------------------------------------------------
	//描述:材料出库单
	//设计: ES
	//实现: ES
	//修改:
	//--------------------------------------------------------------------------------------------------
	@RequestMapping("/outBound/form/list")
	public String outBoundSearchList(@RequestParam("F_KJQJ") String F_KJQJ,
						      	     @RequestParam("F_DJZT") String F_DJZT, 
						      	     @RequestParam("F_CKBH") String F_CKBH,
						      	     @RequestParam("F_XMBH") String F_XMBH,
						      	     Model model) {
		//F_KJQJ, F_DJBH, F_GUID, F_DJZT, F_DATE, F_CKBH, F_XMBH, startIndex, count
		List<HYCKD>  hyckdList = outBoundMgr.searchHYCKD(F_KJQJ, "", "", F_DJZT, "", F_CKBH, F_XMBH, 0, 100);
		HYXMZD            xmzd = sysConfigureMgr.getHYXM(F_XMBH);
		HYCKZD            ckzd = sysConfigureMgr.getHYCKZD(F_CKBH);
		model.addAttribute("F_KJQJ", F_KJQJ);		
		model.addAttribute("hyckdList", hyckdList);
		model.addAttribute("F_DJZT", F_DJZT);
		model.addAttribute("F_CKBH", F_CKBH);	
		model.addAttribute("F_CKMC", ckzd.getF_CKMC());		
		model.addAttribute("F_XMBH", F_XMBH);
		model.addAttribute("F_XMMC", xmzd.getF_XMMC());
		model.addAttribute("F_DJZT", F_DJZT);	
		model.addAttribute("F_YEAR", F_KJQJ.subSequence(0, 4));
		model.addAttribute("F_MONTH", F_KJQJ.subSequence(4, 6));
		return "/mrp/dailyBusiness/outBound/clckdFormList";
	}
	
	//--------------------------------------------------------------------------------------------------
	//描述:新增材料出库单
	//设计: ES
	//实现: ES
	//修改:
	//--------------------------------------------------------------------------------------------------
	@RequestMapping("/outBound/form/addCkd")
	public String outBoundAddDj(@RequestParam("F_KJQJ") String F_KJQJ, 
								@RequestParam("F_CKBH") String F_CKBH,
								@RequestParam("F_XMBH") String F_XMBH,
								Model model) {
		java.util.Date       currTime = new java.util.Date();
		SimpleDateFormat    formatter = new SimpleDateFormat("yyyy-MM-dd");
		HYCKD                   hyckd = new HYCKD();
		SYSUser                  user = (SYSUser) session.getAttribute("contextUser");
		
		HYXMZD                   xmzd = sysConfigureMgr.getHYXM(F_XMBH);
		HYCKZD                   ckzd = sysConfigureMgr.getHYCKZD(F_CKBH);
		
		hyckd.setF_KJQJ(F_KJQJ);
		hyckd.setF_GUID(UUID.randomUUID().toString());
		hyckd.setF_DJZT("0");
		hyckd.setF_BZRID(user.getUSER_ID());
		hyckd.setF_BZRMC(user.getUSER_NAME());	
		hyckd.setF_CKBH(F_CKBH);
		hyckd.setF_CKMC(ckzd.getF_CKMC());
		hyckd.setF_XMBH(F_XMBH);
		hyckd.setF_XMMC(xmzd.getF_XMMC());
		hyckd.setF_DATE(formatter.format(currTime));	
		model.addAttribute("F_YEAR", F_KJQJ.subSequence(0, 4));
		model.addAttribute("F_MONTH", F_KJQJ.subSequence(4, 6));
		model.addAttribute("hyckd", hyckd);
		return "/mrp/dailyBusiness/outBound/clckdHead";
	}
	
	//--------------------------------------------------------------------------------------------------
	//描述:添加材料出库单明细,当第一次添加的时候，没有单据编号，第一次添加后，形成单据编号
	//设计: ES
	//实现: ES
	//修改:
	//--------------------------------------------------------------------------------------------------
	@RequestMapping("/outBound/form/addfl")
	public String outBoundAdd(@RequestParam String F_DJBH,
							  @RequestParam("F_GUID") String F_GUID, 
							  @RequestParam("F_XMBH") String F_XMBH, 
							  @RequestParam("F_CKBH") String F_CKBH, 
							  @RequestParam("F_CHDATE") String F_CHDATE,
						      @RequestParam String F_KJQJ,
						      Model model) {
		java.util.Date     currTime = new java.util.Date();
		SimpleDateFormat  formatter = new SimpleDateFormat("yyyy-MM-dd");
		String             F_FLGUID = String.valueOf(currTime.getTime());
		HYXMZD                 xmzd = sysConfigureMgr.getHYXM(F_XMBH);
		HYCKZD                 ckzd = sysConfigureMgr.getHYCKZD(F_CKBH);
		
		model.addAttribute("F_DATE", formatter.format(currTime));		
		model.addAttribute("F_KJQJ", F_KJQJ);
		model.addAttribute("F_DJBH", F_DJBH);
		model.addAttribute("F_GUID", F_GUID);
		model.addAttribute("F_XMBH", F_XMBH);
		model.addAttribute("F_XMMC", xmzd.getF_XMMC());
		model.addAttribute("F_CKBH", F_CKBH);
		model.addAttribute("F_CKMC", ckzd.getF_CKMC());	
		model.addAttribute("F_CHDATE", F_CHDATE);
		model.addAttribute("F_FLGUID", F_FLGUID);
		return "/mrp/dailyBusiness/outBound/clckdmxAdd";
	}
	
	//--------------------------------------------------------------------------------------------------
	//描述:保存新增出库单明细
	//设计: ES
	//实现: ES
	//修改:
	//--------------------------------------------------------------------------------------------------
	@RequestMapping(value = "/outBound/formmx/insert/{F_KJQJ}", method = RequestMethod.POST)
	public ModelAndView outBoundFormFlInsert(@PathVariable("F_KJQJ") String F_KJQJ,
			                                 @RequestParam("F_CKBH") String F_CKBH,
			                                 @RequestParam("F_XMBH") String F_XMBH,
			                                 @RequestParam("F_DJBH") String F_DJBH, 
			                                 @RequestParam("F_CHDATE") String F_CHDATE,
			                                 @RequestParam("formType") String formType,
			                                 HYCKDMX hyckdmxObject, Model model) throws Exception {
		SYSUser                user = (SYSUser) session.getAttribute("contextUser");
		java.util.Date     currTime = new java.util.Date();
		SimpleDateFormat  formatter = new SimpleDateFormat("yyyyMMdd");
		EFDataSet       itemDataSet = EFDataSet.getInstance("HYCKDMX");
		EFRowSet             rowset = EFRowSet.getInstance();
		EFRowSet          itemRwSet = EFRowSet.getInstance();

		rowset = EFRowSet.getInstance();
		rowset.putString("F_DJBH", F_DJBH);
		rowset.putString("F_GUID", hyckdmxObject.getF_GUID());
		rowset.putString("F_KJQJ", F_KJQJ);
		rowset.putString("F_BZRID", user.getUSER_ID());
		rowset.putString("F_CKBH", F_CKBH);
		rowset.putString("F_XMBH", F_XMBH);
		rowset.putString("F_DJZT", "0");
		rowset.putString("F_DATE", formatter.format(currTime));
		rowset.putString(EFFormDataModel._FORMEDITSAVETYPE_, formType);
		rowset.putString("F_CHDATE", F_CHDATE);
		
		itemRwSet = (EFRowSet) DataSetUtils.javaBean2RowSet(hyckdmxObject);
		itemDataSet.insertRowSet(itemRwSet);
		itemRwSet.putString("F_KJQJ", F_KJQJ);		
		rowset.setDataSet("HYCKDMX", itemDataSet);

		EFFormDataModel formDataModel = formModelUtil.saveForm("HYCKD", rowset, SYS_MDL_VAL.CLCK, "SaveCKDResolver");
		if(formDataModel.getFormSaveStatus() == EFFormDataModel._FORM_SAVE_FAIL_) {
			ModelAndView modelView = ajaxDoneError("保存失败！原因：<br>" + formDataModel.getFormSaveMessage());
			MappingJacksonJsonView view = (MappingJacksonJsonView)modelView.getView();
			Map attributes = view.getAttributesMap();
			attributes.remove("callbackType");
			return modelView;
		}
		rowset = formDataModel.getBillDataSet().getRowSet(0);
		F_CHDATE = rowset.getObject("F_CHDATE", "").toString();
		model.addAttribute("F_CHDATE", F_CHDATE);
		model.addAttribute("F_DJBH", rowset.getObject("F_DJBH", ""));
		model.addAttribute("F_YEAR", F_KJQJ.subSequence(0, 4));
		model.addAttribute("F_MONTH", F_KJQJ.subSequence(4, 6));
		model.addAttribute("F_CHDATE", F_CHDATE);
		model.addAttribute("navTabId", "outBoundNav");
		model.addAttribute("title", "材料出库单据");
		
		ModelAndView modelView = ajaxDoneSuccess("单据分录保存成功！单据编号【" + rowset.getObject("F_DJBH", "") + "】");
		MappingJacksonJsonView view = (MappingJacksonJsonView)modelView.getView();
		Map attributes = view.getAttributesMap();
		String forwardUrl = attributes.get("forwardUrl").toString();
		forwardUrl += "/" + rowset.getObject("F_DJBH", "") + "?F_KJQJ=" + rowset.getString("F_KJQJ", "");
		
		attributes.put("forwardUrl", forwardUrl);
		return modelView;
	}
	
	//--------------------------------------------------------------------------------------------------
	//描述:编辑出库单
	//设计: ES
	//实现: ES
	//修改:
	//--------------------------------------------------------------------------------------------------
	@RequestMapping("/outBound/form/edit/{F_DJBH}")
	public String outBoundEdit(@PathVariable("F_DJBH") String F_DJBH, 
			                   @RequestParam("F_KJQJ") String F_KJQJ,
			                   Model model) {
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
	//描述:编辑出库单明细
	//设计: ES
	//实现: ES
	//修改:
	//--------------------------------------------------------------------------------------------------
	@RequestMapping("/outBound/form/editfl")
	public String outBoundFormFlEdit(@RequestParam("F_FLBH") String F_FLBH,
									 @RequestParam("F_FLGUID") String F_FLGUID,
									 @RequestParam("F_KJQJ") String F_KJQJ,
			                         @RequestParam("F_DJBH") String F_DJBH,
			                         @RequestParam("F_DJZT") String F_DJZT,
			                         Model model) {
		List<HYCKDMX>  hyckdmxList = outBoundMgr.searchHYCKDMX(F_KJQJ, F_DJBH, "", F_FLBH, F_FLGUID, 0, 100);	
		List<HYCKD>      hyckdList = outBoundMgr.searchHYCKD(F_KJQJ, F_DJBH, "", F_DJZT, "", "", "", 0, 100);
		HYCKDMX                 po = hyckdmxList.get(0);
		double                kcsl = formUtil.getClKcSl(po.getF_SSXMBH(), po.getF_SSCPBH(), po.getF_CLBH(), po.getF_SSCKBH(), po.getF_CLDJ());		
		EFDataSet           lyslDS = formUtil.getClLySl(po.getF_YYXMBH(), po.getF_YYCPBH(), po.getF_CLBH(), po.getF_YYCKBH(), po.getF_CLDJ());
		EFRowSet            rowset = null;
		
		po.setF_KCSL(kcsl);
		
		if(lyslDS.getRowCount() > 0) {
			rowset = lyslDS.getRowSet(0);
			po.setF_LYSL(rowset.getNumber("F_LYSL", 0).doubleValue());
			po.setF_SQSL(rowset.getNumber("F_SQSL", 0).doubleValue());
		} else {
			double sqsl = formUtil.getClSqSl(po.getF_SSXMBH(), po.getF_CLBH());
			po.setF_LYSL(0);
			po.setF_SQSL(sqsl);
		}
		model.addAttribute("hyckdmxList", hyckdmxList.get(0));
		model.addAttribute("hyckd", hyckdList.get(0));
		model.addAttribute("F_CHDATE", hyckdList.get(0).getF_CHDATE().getTime());
		model.addAttribute("F_DJZT", hyckdList.get(0).getF_DJZT());
		
		model.addAttribute("F_KJQJ", F_KJQJ);
		model.addAttribute("F_DJBH", F_DJBH);
		model.addAttribute("F_GUID", po.getF_GUID());
		model.addAttribute("F_XMBH", po.getF_SSXMBH());
		model.addAttribute("F_XMMC", po.getF_SSXMMC());
		model.addAttribute("F_CKBH", po.getF_SSCKBH());
		model.addAttribute("F_CKMC", po.getF_SSCKMC());	
		model.addAttribute("F_FLGUID", F_FLGUID);
		
		
		return "/mrp/dailyBusiness/outBound/clckdmxEdit";
	}
	
	//--------------------------------------------------------------------------------------------------
	//描述:删除出库单明细
	//设计: ES
	//实现: ES
	//修改:
	//--------------------------------------------------------------------------------------------------
	@RequestMapping("/outBound/form/deletefl")
	public ModelAndView outBoundFlDelete(@RequestParam("F_FLBH") String F_FLBH,
										 @RequestParam("F_FLGUID") String F_FLGUID,
										 @RequestParam("F_CHDATE") String F_CHDATE, 
										 @RequestParam("F_KJQJ") String F_KJQJ, 
										 @RequestParam("F_DJBH") String F_DJBH, 
										 Model model) throws Exception {
		SYSUser           user = (SYSUser) session.getAttribute("contextUser");
		EFDataSet  itemDataSet = EFDataSet.getInstance("HYCKDMX");
		EFRowSet        rowset = EFRowSet.getInstance();
		EFRowSet     itemRwSet = EFRowSet.getInstance();
		
		rowset.putString("F_DJBH", F_DJBH);
		rowset.putString("F_KJQJ", F_KJQJ);
		rowset.putString("F_BZRID", user.getUSER_ID());
		rowset.putString("F_CHDATE", F_CHDATE);
		rowset.putNumber("formEditStatus", 0x0003);
		
		itemRwSet.putString("F_DJBH", F_DJBH);
		itemRwSet.putString("F_KJQJ", F_KJQJ);
		itemRwSet.putString("F_FLGUID", F_FLGUID);
		itemRwSet.putString("F_FLBH", F_FLBH);
		
		itemDataSet.insertRowSet(itemRwSet);	
		rowset.setDataSet("HYCKDMX", itemDataSet);
		
		EFFormDataModel formDataModel = formModelUtil.saveForm("HYCKD", rowset, SYS_MDL_VAL.CLCK, "SaveCKDResolver");
		
		if(formDataModel.getFormSaveStatus() == EFFormDataModel._FORM_SAVE_FAIL_) {
			ModelAndView modelView = ajaxDoneError("保存失败！原因：<br>" + formDataModel.getFormSaveMessage());
			MappingJacksonJsonView view = (MappingJacksonJsonView)modelView.getView();
			Map attributes = view.getAttributesMap();
			attributes.remove("callbackType");
			return modelView;
		}
		rowset = formDataModel.getBillDataSet().getRowSet(0);
		F_CHDATE = rowset.getObject("F_CHDATE", "").toString();
		model.addAttribute("F_CHDATE", F_CHDATE);
		model.addAttribute("F_DJBH", rowset.getObject("F_DJBH", ""));
		model.addAttribute("F_CHDATE", F_CHDATE);
		
		ModelAndView modelView = ajaxDoneSuccess("单据分录删除成功！单据编号【" + rowset.getObject("F_DJBH", "") + "】");
		MappingJacksonJsonView view = (MappingJacksonJsonView)modelView.getView();
		Map attributes = view.getAttributesMap();
		String forwardUrl = attributes.get("callBackUrl").toString();
		forwardUrl += "/" + rowset.getObject("F_DJBH", "") + "?F_KJQJ=" + rowset.getString("F_KJQJ", "");
		attributes.put("F_CHDATE", F_CHDATE);
//		model.addAttribute("navTabId", "outBoundNav");
		attributes.put("forwardUrl", forwardUrl);
		attributes.put("title", "材料出库单据");
		
		return modelView;
	}
	
	//--------------------------------------------------------------------------------------------------
	//描述:材料出库单
	//设计: ES
	//实现: ES
	//修改:
	//--------------------------------------------------------------------------------------------------
	@RequestMapping("/outBound/form/ckdList")
	public String outBoundCkdList(@RequestParam("F_KJQJ") String F_KJQJ,
								  @RequestParam("F_GUID") String F_GUID,
								  Model model) {
		List<HYCKDMX> hyckdmxList = outBoundMgr.searchHYCKDMXByGuid(F_KJQJ, F_GUID);		
		List<HYCKD>     hyckdList = outBoundMgr.searchHYCKD(F_KJQJ, "", F_GUID, "-1", "", "", "", 0, 100);	
		hyckdList.get(0).setF_DATE(StringUtil.formatDate(hyckdList.get(0).getF_DATE()));
		model.addAttribute("hyckd", (HYCKD)hyckdList.get(0));
		model.addAttribute("hyckdmxList", hyckdmxList);	
		model.addAttribute("F_KJQJ", F_KJQJ);		
		model.addAttribute("F_DJZT", ((HYCKD)hyckdList.get(0)).getF_DJZT());
		model.addAttribute("F_CKBH", ((HYCKD)hyckdList.get(0)).getF_CKBH());	
		model.addAttribute("F_CKMC", ((HYCKD)hyckdList.get(0)).getF_CKMC());
		model.addAttribute("F_XMBH", ((HYCKD)hyckdList.get(0)).getF_XMBH());	
		model.addAttribute("F_XMMC", ((HYCKD)hyckdList.get(0)).getF_XMMC());			
		return "/mrp/dailyBusiness/outBound/clckdForm";
	}
	
	//--------------------------------------------------------------------------------------------------
	//描述:保存出库单
	//设计: ES
	//实现: ES
	//修改:
	//--------------------------------------------------------------------------------------------------
	@RequestMapping(value = "/outBound/form/update", method = RequestMethod.POST)
	public ModelAndView outBoundFormUpdate(HYCKD hyckd,
										   @RequestParam("F_CHDATE") String F_CHDATE, 
										   Model model) throws Exception {	
		SYSUser    user = (SYSUser) session.getAttribute("contextUser");
		EFRowSet rowset = EFRowSet.getInstance();
		rowset = DataSetUtils.javaBean2RowSet(hyckd);
		rowset.putString("F_DJZT", "0");
		if(!F_CHDATE.equals("")) rowset.putNumber("F_CHDATE", Long.parseLong(F_CHDATE));

		rowset.putString("F_BZRID", user.getUSER_ID());
		rowset.putString("F_DATE", StringUtil.getDate(hyckd.getF_DATE()));
		
		EFFormDataModel formDataModel = formModelUtil.saveForm("HYCKD", rowset, SYS_MDL_VAL.CLCK, "SaveCKDResolver");
		if(formDataModel.getFormSaveStatus() == EFFormDataModel._FORM_SAVE_FAIL_) {
			ModelAndView modelView = ajaxDoneError("保存失败！原因：<br>" + formDataModel.getFormSaveMessage());
			MappingJacksonJsonView view = (MappingJacksonJsonView)modelView.getView();
			Map attributes = view.getAttributesMap();
			attributes.remove("callbackType");
			return modelView;
		}
		rowset = formDataModel.getBillDataSet().getRowSet(0);
		F_CHDATE = rowset.getObject("F_CHDATE", "").toString();
		model.addAttribute("F_CKBH", rowset.getString("F_CKBH", ""));
		model.addAttribute("F_CKMC", rowset.getString("F_CKMC", ""));
		model.addAttribute("F_XMBH", rowset.getString("F_XMBH", ""));
		model.addAttribute("F_XMMC", rowset.getString("F_XMBH", ""));
		model.addAttribute("F_DJBH", rowset.getString("F_DJBH", ""));
		model.addAttribute("F_CHDATE", F_CHDATE);
		model.addAttribute("navTabId", "outBoundNav");
		model.addAttribute("tabid", "outBoundLiNav");
		
		ModelAndView modelView = ajaxDoneSuccess("单据保存成功！单据编号【" + rowset.getString("F_DJBH", "") + "】");
		MappingJacksonJsonView view = (MappingJacksonJsonView)modelView.getView();
		Map attributes = view.getAttributesMap();
		String forwardUrl = attributes.get("forwardUrl").toString();
		forwardUrl += "/" + rowset.getObject("F_DJBH", "") + "?F_KJQJ=" + rowset.getString("F_KJQJ", "") ;
		attributes.put("forwardUrl", forwardUrl);
		attributes.put("title", "材料出库单据");
		return modelView;
	}
	
	//--------------------------------------------------------------------------------------------------
	//描述:提交材料出库单
	//设计: ES
	//实现: ES
	//修改:
	//--------------------------------------------------------------------------------------------------
	@RequestMapping(value = "/outBound/form/submit")
	public ModelAndView outBoundFormSubmit(@RequestParam("F_CHDATE") String F_CHDATE,  
							  			   HYCKD hyckd, Model model) throws Exception {		
		SYSUser               user = (SYSUser) session.getAttribute("contextUser");
		EFDataSet      itemDataSet = EFDataSet.getInstance("HYCKDMX");
		EFRowSet            rowset = EFRowSet.getInstance();
		EFRowSet         itemRwSet = EFRowSet.getInstance();
		List<HYCKDMX>  hyckdmxList = null;

		rowset = DataSetUtils.javaBean2RowSet(hyckd);
		rowset.putString("F_DJBH", hyckd.getF_DJBH());
		rowset.putString("F_KJQJ", hyckd.getF_KJQJ());
		rowset.putString("F_GUID", hyckd.getF_GUID());
		rowset.putString("F_SBRID", user.getUSER_ID());
		rowset.putString("F_DJZT", "1");
		rowset.putNumber("F_SBDATE", (new java.util.Date()).getTime());
		rowset.putNumber("F_CHDATE", Long.parseLong(F_CHDATE));
		rowset.putString("F_DATE", StringUtil.getDate(hyckd.getF_DATE()));
		itemDataSet.insertRowSet(itemRwSet);
		itemRwSet.putString("F_KJQJ", hyckd.getF_KJQJ());	
		rowset.putString("F_DJBH", hyckd.getF_DJBH());
		rowset.setDataSet("HYCKDMX", itemDataSet);
	
		EFFormDataModel formDataModel = formModelUtil.submitForm("HYCKD", rowset, "SubmitCKDResolver");
		rowset = formDataModel.getBillDataSet().getRowSet(0);
		itemDataSet = rowset.getDataSet("HYCKDMX");
		
		if(formDataModel.getFormSaveStatus() == EFFormDataModel._FORM_SAVE_FAIL_) {
			return ajaxDoneError("保存失败！原因：<br>" + formDataModel.getFormSaveMessage());
		}
		
		try {
			HYCKD ckd = (HYCKD)EMPReflectUtil.rowtsetReflectClass(HYCKD.class.getName(), rowset);
			hyckdmxList = EMPReflectUtil.datasetReflectClass(HYCKDMX.class.getName(), itemDataSet);
			ckd.setF_DATE(StringUtil.formatDate(((HYCKD)ckd).getF_DATE()));
			model.addAttribute("hyckdmxList", hyckdmxList);
			model.addAttribute("hyrkd", ckd);
		} catch(Exception ce) {
			return ajaxDoneError("保存失败！原因：<br>" + ce.getMessage());
		}
		if(formDataModel.getFormSaveStatus() == EFFormDataModel._FORM_SAVE_FAIL_) {
			ModelAndView modelView = ajaxDoneError("保存失败！原因：<br>" + formDataModel.getFormSaveMessage());
			MappingJacksonJsonView view = (MappingJacksonJsonView)modelView.getView();
			Map attributes = view.getAttributesMap();
			attributes.remove("callbackType");
			return modelView;
		}
		model.addAttribute("F_KJQJ", hyckd.getF_KJQJ());		
		model.addAttribute("F_DJBH", hyckd.getF_DJBH());
		ModelAndView modelView = ajaxDoneSuccess("提交成功！");
		MappingJacksonJsonView view = (MappingJacksonJsonView)modelView.getView();
		Map attributes = view.getAttributesMap();
		String forwardUrl = attributes.get("forwardUrl").toString();
		forwardUrl += "/" + rowset.getObject("F_DJBH", "") + "?F_KJQJ=" + rowset.getString("F_KJQJ", "") ;
		attributes.put("F_CHDATE", F_CHDATE);
		attributes.put("forwardUrl", forwardUrl);
		attributes.put("title", "材料出库单据");

		return modelView;
	}
	
	//--------------------------------------------------------------------------------------------------
	//描述:部门列表
	//设计: ES
	//实现: ES
	//修改:
	//--------------------------------------------------------------------------------------------------
	@RequestMapping("outBound/deportHelp")
	public String deportHelp(Model model) {
		List<HYBMZD> hybmList = sysConfigureMgr.searchHYBMZD(0, 100);
		model.addAttribute("hybmList", hybmList);
		model.addAttribute("F_ZDBH", "F_YYCKBH");
		model.addAttribute("F_ZDMC", "F_YYCKMC");
		return "/mrp/dailyBusiness/outBound/deport/bmckHelp";
	}
	
	//--------------------------------------------------------------------------------------------------
	//描述:应用项目帮助
	//设计: ES
	//实现: ES
	//修改:
	//--------------------------------------------------------------------------------------------------
	@RequestMapping("outBound/DVProjectHelp")
	public String DVProjectHelp(@RequestParam("beginDate") String beginDate, @RequestParam("endDate") String endDate, 
							    @RequestParam("F_CLBH") String F_CLBH, @RequestParam("F_XMBH") String F_XMBH, Model model) {
		List<HYXMZD> projectList = dictHelpMgr.searchHYXMZD(beginDate.replaceAll("-", ""), endDate.replaceAll("-", ""), F_CLBH, "0", F_XMBH, 0, 100);
		if(beginDate.indexOf("-") == -1) {
			beginDate = StringUtil.formatDate(beginDate);
			endDate = StringUtil.formatDate(endDate);
		}
		model.addAttribute("projectList", projectList);
		model.addAttribute("beginDate", beginDate);
		model.addAttribute("endDate", endDate);
		model.addAttribute("F_XMBH", F_XMBH);
		return "/mrp/dailyBusiness/outBound/help/yyclxmHelp";
	}
	
	//--------------------------------------------------------------------------------------------------
	//描述:应用项目帮助
	//设计: ES
	//实现: ES
	//修改:
	//--------------------------------------------------------------------------------------------------
	@RequestMapping("outBound/DVProjectProductHelp")
	public String DVProjectProductHelp(@RequestParam("beginDate") String beginDate, @RequestParam("endDate") String endDate, 
							           @RequestParam("F_CLBH") String F_CLBH, @RequestParam("F_XMBH") String F_XMBH, 
							           @RequestParam("F_CPBH") String F_CPBH, Model model) {
		if(beginDate.indexOf("-") == -1) {
			beginDate = beginDate.substring(0, 4) + "0101";
		}
		List<HYXMCP> productList = dictHelpMgr.searchHYXMCP(beginDate.replaceAll("-", ""), endDate.replaceAll("-", ""), F_XMBH, F_CPBH, F_CLBH, 0, 100);
		if(beginDate.indexOf("-") == -1) {
			beginDate = StringUtil.formatDate(beginDate.substring(0, 4) + "0101");
			endDate = StringUtil.formatDate(endDate);
		}
		model.addAttribute("productList", productList);
		model.addAttribute("beginDate", beginDate);
		model.addAttribute("endDate", endDate);
		model.addAttribute("F_XMBH", F_XMBH);
		model.addAttribute("F_CPBH", F_CPBH);
		model.addAttribute("F_CLBH", F_CLBH);
		return "/mrp/dailyBusiness/outBound/help/yyclxmcpHelp";
	}
	
	//--------------------------------------------------------------------------------------------------
	//描述:部门对应仓库列表
	//设计: ES
	//实现: ES
	//修改:
	//--------------------------------------------------------------------------------------------------
	@RequestMapping("outBound/deportHelp/{F_BMBH}")
	public String deportList(@PathVariable("F_BMBH") String F_BMBH, Model model) {
		List<HYCKZD> deportList = sysConfigureMgr.searchHYCK(F_BMBH, 0, 100);
		model.addAttribute("deportList", deportList);
		model.addAttribute("F_BMBH", F_BMBH);
		model.addAttribute("F_ZDBH", "F_YYCKBH");
		model.addAttribute("F_ZDMC", "F_YYCKMC");
		return "/mrp/dailyBusiness/outBound/deport/deportList";
	}
	
	//--------------------------------------------------------------------------------------------------
	//描述:根据仓库编号，查找部门对应仓库列表
	//设计: ES
	//实现: ES
	//修改:
	//--------------------------------------------------------------------------------------------------
	@RequestMapping("outBound/deport/search/{F_BMBH}")
	public String deportList(@PathVariable("F_BMBH") String F_BMBH, @RequestParam String keywords, Model model) {
		List<HYCKZD> deportList = sysConfigureMgr.searchHYCK(F_BMBH, keywords, 0, 100);
		model.addAttribute("deportList", deportList);
		model.addAttribute("F_BMBH", F_BMBH);
		return "/mrp/dailyBusiness/outBound/deport/deportList";
	}
	
	//--------------------------------------------------------------------------------------------------
	//描述:第一步,通过扫描枪录入材料
	//设计: ES
	//实现: ES
	//修改:
	//--------------------------------------------------------------------------------------------------
	@RequestMapping("/form/outBound/batchAdd/scanningMaterial")
	public String batchAddfl(@RequestParam String F_DJBH,
							 @RequestParam("F_GUID") String F_GUID, 
							 @RequestParam("F_XMBH") String F_XMBH, 
							 @RequestParam("F_CKBH") String F_CKBH, 
							 @RequestParam("F_CHDATE") String F_CHDATE,
						     @RequestParam String F_KJQJ,
						     Model model) {
		java.util.Date     currTime = new java.util.Date();
		SimpleDateFormat  formatter = new SimpleDateFormat("yyyy-MM-dd");
		
		model.addAttribute("F_DATE", formatter.format(currTime));		
		model.addAttribute("F_KJQJ", F_KJQJ);
		model.addAttribute("F_DJBH", F_DJBH);
		model.addAttribute("F_GUID", F_GUID);
		model.addAttribute("F_XMBH", F_XMBH);
		model.addAttribute("F_CKBH", F_CKBH);
		model.addAttribute("F_CHDATE", F_CHDATE);
		return "/mrp/dailyBusiness/outBound/batchAdd/outBoundMaterial";
	}
	
	//--------------------------------------------------------------------------------------------------
	//描述:第二部，确定材料存在
	//设计: ES
	//实现: ES
	//修改:
	//--------------------------------------------------------------------------------------------------
	@RequestMapping("/form/batchAdd/outBound/checkInputMaterial")
	public ModelAndView checkInputMaterial(@RequestParam String F_DJBH,
									       @RequestParam("F_GUID") String F_GUID, 
									       @RequestParam("F_XMBH") String F_XMBH, 
									       @RequestParam("F_CKBH") String F_CKBH, 
									       @RequestParam String F_KJQJ,
									       @RequestParam String F_CLBH,
									       @RequestParam("F_CHDATE") String F_CHDATE,
							       		   Model model) throws Exception {
		String             errorMsg = "";
		String             tempClbh = F_CLBH.replaceAll("\r\n", "@");
		EFRowSet             rowset = null;
		Iterator                 it = null;
		Map<String, String>   clMap = new HashMap<String, String>();
		EFDataSet                ds = formUtil.checkMaterial(tempClbh);
		String[]              array = tempClbh.split("@");
		
		errorMsg += checkFormUtil.checkFormSubmit(F_KJQJ, F_GUID, "HYCKD", F_CHDATE);
		errorMsg += checkFormUtil.checkFormEdit(F_KJQJ, F_GUID, "HYCKD", F_CHDATE);
		
		if(!errorMsg.equals("")) return ajaxDoneError(errorMsg);
		
		for(int i = 0; i < array.length; i++) {
			clMap.put(array[i], array[i]);
		}
		for(int i = 0; i < ds.getRowCount(); i++) {
			rowset = ds.getRowSet(i);
			if(clMap.get(rowset.getString("F_CLBH", "")) != null) clMap.remove(rowset.getString("F_CLBH", ""));
		}
		
		if(!clMap.isEmpty()) {
			errorMsg = "检查错误，原因：<br>";
			it =  clMap.entrySet().iterator();
			while(it != null && it.hasNext()) {
				Map.Entry m = (Map.Entry) it.next();
	     		errorMsg += m.getKey().toString() + "<br>";
			}
			errorMsg += "以上材料编号在材料字典中没有维护信息，找不到对应的条形码信息，请首先维护材料条形码信息！";
			return ajaxDoneError(errorMsg);
		}
		ModelAndView modelView = ajaxDoneSuccess("");
		MappingJacksonJsonView view = (MappingJacksonJsonView)modelView.getView();
		Map attributes = view.getAttributesMap();
		String dialogHref = attributes.get("dialogHref").toString();
		dialogHref += "F_DJBH="+F_DJBH+"&F_GUID="+F_GUID+"&F_XMBH="+F_XMBH+"&F_KJQJ="+F_KJQJ+"&F_CKBH="+F_CKBH+"&F_CHDATE="+F_CHDATE;
		attributes.put("dialogHref", dialogHref);
		StringFunction.setSysClipboardText(F_CLBH);
		return modelView;
	}
	
	//--------------------------------------------------------------------------------------------------
	//描述:第三部，选择材料
	//设计: ES
	//实现: ES
	//修改:
	//--------------------------------------------------------------------------------------------------
	@RequestMapping("/form/batchAdd/outBound/selectMaterial")
	public String batchAddMaterial(@RequestParam String F_DJBH,
							       @RequestParam("F_GUID") String F_GUID, 
							       @RequestParam("F_XMBH") String F_XMBH, 
							       @RequestParam("F_CKBH") String F_CKBH, 
							       @RequestParam String F_KJQJ,
							       @RequestParam("F_CLBH") String F_CLBH, 
							       @RequestParam("F_CHDATE") String F_CHDATE,
							       Model model) {
		SYSUser              user = (SYSUser) session.getAttribute("contextUser");
		String           tempClbh = F_CLBH.replaceAll("\r\n", "@");
		EFDataSet              ds = formUtil.materialInfo(tempClbh);
		java.util.Date   currTime = new java.util.Date();
		String           F_FLGUID = String.valueOf(currTime.getTime());
		HYXMZD               xmzd = sysConfigureMgr.getHYXM(F_XMBH);
		HYCKZD               ckzd = sysConfigureMgr.getHYCKZD(F_CKBH);

		model.addAttribute("clList", ds.getRowSetList());
		model.addAttribute("F_LRR", user.getUSER_ID());
		model.addAttribute("F_KJQJ", F_KJQJ);
		model.addAttribute("F_DJBH", F_DJBH);
		model.addAttribute("F_GUID", F_GUID);
		model.addAttribute("F_XMBH", F_XMBH);
		model.addAttribute("F_XMMC", xmzd.getF_XMMC());
		model.addAttribute("F_CKBH", F_CKBH);
		model.addAttribute("F_CKMC", ckzd.getF_CKMC());	
		model.addAttribute("F_CHDATE", F_CHDATE);
		model.addAttribute("F_FLGUID", F_FLGUID);
		return "/mrp/dailyBusiness/outBound/batchAdd/selectAddMaterial";
	}
	
	//--------------------------------------------------------------------------------------------------
	//描述:通过扫描枪批量添加材料入库单明细,当第一次添加的时候，没有单据编号，第一次添加后，形成单据编号
	//设计: ES
	//实现: ES
	//修改:
	//--------------------------------------------------------------------------------------------------
	@RequestMapping(value="/form/batchAdd/outBound/saveSelectMaterial", method=RequestMethod.POST)
	public  ModelAndView batchAddSacnningMaterial(@RequestBody Map dataMap, @RequestParam("formType") String formType, Model model) throws Exception {  
			SimpleDateFormat   formatter = new SimpleDateFormat("yyyyMMdd");
			java.util.Date      currTime = new java.util.Date();
			EFRowSet              rowSet = EMPJsonUtil.convertMapToRowSet(dataMap);
			EFDataSet        itemDataSet = rowSet.getDataSet(HYCKDMX.class.getName());
			EFRowSet              rowset = EFRowSet.getInstance();
			String              F_CHDATE = "";
			
			itemDataSet.setTableName("HYCKDMX");
			rowset.setDataMap(rowSet.getDataMap());
			rowset.putString("F_DJZT", "0");
			rowset.putString("F_DATE", formatter.format(currTime));
			rowset.remove(HYCKDMX.class.getName());
			rowset.setDataSet("HYCKDMX", itemDataSet);
			rowset.putString(EFFormDataModel._FORMEDITSAVETYPE_, formType);
			EFFormDataModel formDataModel = formModelUtil.saveForm("HYCKD", rowset, SYS_MDL_VAL.CLCK, "SaveCKDResolver");
			if(formDataModel.getFormSaveStatus() == EFFormDataModel._FORM_SAVE_FAIL_) {
				return ajaxDoneError("保存失败！原因：<br>" + formDataModel.getFormSaveMessage());
			}
			rowset = formDataModel.getBillDataSet().getRowSet(0);
			F_CHDATE = rowset.getObject("F_CHDATE", "").toString();
			model.addAttribute("F_CHDATE", F_CHDATE);
			ModelAndView modelView = ajaxDoneSuccess("单据分录保存成功！单据编号【" + rowset.getObject("F_DJBH", "") + "】");
			MappingJacksonJsonView view = (MappingJacksonJsonView)modelView.getView();
			Map attributes = view.getAttributesMap();
			String forwardUrl = attributes.get("forwardUrl").toString();
			forwardUrl += "/" + rowset.getObject("F_DJBH", "") + "?F_KJQJ=" + rowset.getString("F_KJQJ", "") ;
			attributes.put("F_CHDATE", F_CHDATE);
			attributes.put("forwardUrl", forwardUrl);
			attributes.put("navTabId", "outBoundNav");
			attributes.put("title", "材料出库单据");
			
			return modelView; 
	 } 
}
