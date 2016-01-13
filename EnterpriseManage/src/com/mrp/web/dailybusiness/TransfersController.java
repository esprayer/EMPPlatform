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

import com.mrp.biz.dailybusiness.TransfersServiceMgr;
import com.mrp.biz.dailybusiness.plugins.EMPFormDataCLJZDCK;
import com.mrp.biz.server.EMPCheckFormUtil;
import com.mrp.biz.server.plugins.util.FormModelDataResolverUtil;
import com.mrp.biz.server.plugins.util.FormSaveUtil;
import com.mrp.biz.server.plugins.util.FormSubmitUtil;
import com.mrp.biz.sysconfigure.sysConfigureServiceMgr;
import com.mrp.persistence.dailyBusiness.transfers.bean.HYDBD;
import com.mrp.persistence.dailyBusiness.transfers.bean.HYDBDMX;
import com.mrp.persistence.sysConfigure.deport.bean.HYCKZD;
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
public class TransfersController extends BaseController{
	@Autowired
	private TransfersServiceMgr transfersMgr;

	@Autowired
	private EMPFormUtil formUtil;

	@Autowired
	private EMPCheckFormUtil checkFormUtil;
	
	@Autowired
	private      sysConfigureServiceMgr  sysConfigureMgr;
	
	@Autowired
	private FormSaveUtil saveUtil;
	
	@Autowired
	private FormModelDataResolverUtil formModelUtil;
	
	@Autowired
	private FormSubmitUtil submitUtil;
	
	@Autowired
	public HttpSession session;
	
	/**
	 * 材料入库单期间范围选择
	 * @param model
	 * @return
	 */
	@RequestMapping("/transfers/hyqj")
	public String transfersHyqj(Model model) {	
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
		return "/mrp/dailyBusiness/transfers/HYQJForm";
	}	
	
	//--------------------------------------------------------------------------------------------------
	//描述:仓库调拨单列表
	//设计: ES
	//实现: ES
	//修改:
	//--------------------------------------------------------------------------------------------------
	@RequestMapping("/transfers/form/list")
	public String transfersFormList(@RequestParam("F_YEAR")  String F_YEAR, 
							        @RequestParam("F_MONTH") String F_MONTH,
							        @RequestParam("F_CKBH")  String F_CKBH,
							        Model model) {
		String           F_KJQJ = F_YEAR + F_MONTH;
		List<HYDBD>   hydbdList = transfersMgr.searchHYDBD(F_KJQJ, null, null, F_CKBH, "0", 0, 100);
		HYCKZD             ckzd = sysConfigureMgr.getHYCKZD(F_CKBH);
		model.addAttribute("F_YEAR", F_YEAR);
		model.addAttribute("F_MONTH", F_MONTH);
		model.addAttribute("F_KJQJ", F_KJQJ);	
		model.addAttribute("hydbdList", hydbdList);
		model.addAttribute("F_DJZT", "0");
		model.addAttribute("F_CKBH", F_CKBH);
		model.addAttribute("F_CKMC", ckzd.getF_CKMC());
		return "/mrp/dailyBusiness/transfers/cldbdFormList";
	}
	
	//--------------------------------------------------------------------------------------------------
	//描述:仓库调拨单列表
	//设计: ES
	//实现: ES
	//修改:
	//--------------------------------------------------------------------------------------------------
	@RequestMapping("/transfers/searchForm/list")
	public String transfersSearchFormList(@RequestParam("F_KJQJ")  String F_KJQJ, 
							              @RequestParam("F_CKBH")  String F_CKBH,
							              @RequestParam("F_DJZT")  String F_DJZT,
							              Model model) {
		
		if(F_DJZT.equals("-1")) F_DJZT = null;
		
		List<HYDBD>   hydbdList = transfersMgr.searchHYDBD(F_KJQJ, null, null, F_CKBH, F_DJZT, 0, 100);
		HYCKZD             ckzd = sysConfigureMgr.getHYCKZD(F_CKBH);
		
		model.addAttribute("F_KJQJ", F_KJQJ);	
		model.addAttribute("hydbdList", hydbdList);
		model.addAttribute("F_DJZT", F_DJZT);
		model.addAttribute("F_CKBH", F_CKBH);
		model.addAttribute("F_CKMC", ckzd.getF_CKMC());
		return "/mrp/dailyBusiness/transfers/cldbdFormList";
	}
	
	//--------------------------------------------------------------------------------------------------
	//描述:新增仓库调拨单
	//设计: ES
	//实现: ES
	//修改:
	//--------------------------------------------------------------------------------------------------
	@RequestMapping("/transfers/form/addForm")
	public String transfersAddDj(@RequestParam("F_KJQJ") String F_KJQJ, @RequestParam("F_CKBH") String F_CKBH, Model model) {
		java.util.Date       currTime = new java.util.Date();
		SimpleDateFormat    formatter = new SimpleDateFormat("yyyy-MM-dd");
		HYDBD                   hydbd = new HYDBD();
		SYSUser                  user = (SYSUser) session.getAttribute("contextUser");
		HYCKZD                   ckzd = sysConfigureMgr.getHYCKZD(F_CKBH);
		
		hydbd.setF_KJQJ(F_KJQJ);
		hydbd.setF_GUID(UUID.randomUUID().toString());
		hydbd.setF_DJZT("0");
		hydbd.setF_CKBH(F_CKBH);
		hydbd.setF_CKMC(ckzd.getF_CKMC());
		hydbd.setF_BZRID(user.getUSER_ID());
		hydbd.setF_BZRMC(user.getUSER_NAME());	
		hydbd.setF_DATE(formatter.format(currTime));	
		model.addAttribute("hydbd", hydbd);
		return "/mrp/dailyBusiness/transfers/cldbdHead";
	}
	
	//--------------------------------------------------------------------------------------------------
	//描述:编辑仓库调拨单
	//设计: ES
	//实现: ES
	//修改:
	//--------------------------------------------------------------------------------------------------
	@RequestMapping("/transfers/form/editForm/{F_DJBH}")
	public String transfersEdit(@PathVariable("F_DJBH") String F_DJBH, 
							    @RequestParam("F_KJQJ") String F_KJQJ, 
							    @RequestParam("F_CKBH") String F_CKBH,
							    Model model) {
		List<HYDBDMX>   hydbdmxList = transfersMgr.searchHYDBDMX(F_KJQJ, F_DJBH, null, null, null, 0, 100);	
		List<HYDBD>       hydbdList = transfersMgr.searchHYDBD(F_KJQJ, F_DJBH, null, F_CKBH, null, 0, 100);
		HYDBD                    po = hydbdList.get(0);
		po.setF_DATE(StringUtil.formatDate(po.getF_DATE()));
		model.addAttribute("hydbdmxList", hydbdmxList);
		model.addAttribute("hydbd", po);
		model.addAttribute("F_KJQJ", F_KJQJ);		
		model.addAttribute("F_DJBH", F_DJBH);
		model.addAttribute("F_CKBH", F_CKBH);
		model.addAttribute("F_CHDATE", po.getF_CHDATE().getTime());
		return "/mrp/dailyBusiness/transfers/cldbdHead";
	}
	
	//--------------------------------------------------------------------------------------------------
	//描述:添加材料入库单明细,当第一次添加的时候，没有单据编号，第一次添加后，形成单据编号
	//设计: ES
	//实现: ES
	//修改:
	//--------------------------------------------------------------------------------------------------
	@RequestMapping("/transfers/form/addfl")
	public String transfersAdd(@RequestParam String F_DJBH,
							   @RequestParam("F_GUID") String F_GUID, 
							   @RequestParam("F_CKBH") String F_CKBH, 
							   @RequestParam("F_CHDATE") String F_CHDATE, 
							   @RequestParam String F_KJQJ,
							   Model model) {
		java.util.Date     currTime = new java.util.Date();
		SimpleDateFormat  formatter = new SimpleDateFormat("yyyy-MM-dd");
		String             F_FLGUID = String.valueOf(currTime.getTime());
		
		model.addAttribute("F_DATE", formatter.format(currTime));		
		model.addAttribute("F_KJQJ", F_KJQJ);
		model.addAttribute("F_DJBH", F_DJBH);
		model.addAttribute("F_GUID", F_GUID);
		model.addAttribute("F_CKBH", F_CKBH);
		model.addAttribute("F_CHDATE", F_CHDATE);
		model.addAttribute("F_FLGUID", F_FLGUID);
		return "/mrp/dailyBusiness/transfers/cldbdmxAdd";
	}
	
	//--------------------------------------------------------------------------------------------------
	//描述:编辑仓库调拨单分录信息
	//设计: ES
	//实现: ES
	//修改:
	//--------------------------------------------------------------------------------------------------
	@RequestMapping("/transfers/form/editfl")
	public String transfersFormFlEdit(@RequestParam("F_KJQJ") String F_KJQJ,
			                          @RequestParam("F_DJBH") String F_DJBH,
			                          @RequestParam("F_FLBH") String F_FLBH,
			                          @RequestParam("F_FLGUID") String F_FLGUID,
			                          @RequestParam("F_CKBH") String F_CKBH,
			                          Model model) {
		List<HYDBDMX>   hydbdmxList = transfersMgr.searchHYDBDMX(F_KJQJ, F_DJBH, null, F_FLBH, F_FLGUID, 0, 100);		
		List<HYDBD>       hydbdList = transfersMgr.searchHYDBD(F_KJQJ, F_DJBH, null, F_CKBH, null, 0, 100);
		HYDBDMX                 po = hydbdmxList.get(0);
		model.addAttribute("hydbdmx", po);
		model.addAttribute("F_CHDATE", hydbdList.get(0).getF_CHDATE().getTime());
		model.addAttribute("F_CKBH", F_CKBH);
		model.addAttribute("F_DJZT", hydbdList.get(0).getF_DJZT());
		return "/mrp/dailyBusiness/transfers/cldbdmxEdit";
	}
	
	//--------------------------------------------------------------------------------------------------
	//描述:删除仓库调拨单明细
	//设计: ES
	//实现: ES
	//修改:
	//--------------------------------------------------------------------------------------------------
	@RequestMapping("/transfers/form/deletefl")
	public ModelAndView transfersFormFlDelete(@RequestParam("F_KJQJ") String F_KJQJ, 
											  @RequestParam("F_DJBH") String F_DJBH,
											  @RequestParam("F_CKBH") String F_CKBH,
											  @RequestParam("F_FLBH") String F_FLBH,
											  @RequestParam("F_FLGUID") String F_FLGUID,
											  @RequestParam("F_CHDATE") String F_CHDATE, 
											  Model model) throws Exception {
		SYSUser             user = (SYSUser) session.getAttribute("contextUser");
		EFDataSet    itemDataSet = EFDataSet.getInstance("HYDBDMX");
		EFRowSet          rowset = EFRowSet.getInstance();
		EFRowSet       itemRwSet = EFRowSet.getInstance();
		
		rowset.putString("F_DJBH", F_DJBH);
		rowset.putString("F_KJQJ", F_KJQJ);
		rowset.putString("F_BZRID", user.getUSER_ID());
		rowset.putString("F_CKBH", F_CKBH);
		rowset.putString("F_CHDATE", F_CHDATE);
		rowset.putNumber("formEditStatus", 0x0003);
		
		itemRwSet.putString("F_DJBH", F_DJBH);
		itemRwSet.putString("F_KJQJ", F_KJQJ);
		itemRwSet.putString("F_FLGUID", F_FLGUID);
		itemRwSet.putString("F_FLBH", F_FLBH);
		
		itemDataSet.insertRowSet(itemRwSet);	
		rowset.setDataSet("HYDBDMX", itemDataSet);
		
		EFFormDataModel formDataModel = formModelUtil.saveForm("HYDBD", rowset, SYS_MDL_VAL.CLDB, "SaveDBDResolver");
		
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
		
		ModelAndView modelView = ajaxDoneSuccess("单据分录保存成功！单据编号【" + rowset.getObject("F_DJBH", "") + "】");
		MappingJacksonJsonView view = (MappingJacksonJsonView)modelView.getView();
		Map attributes = view.getAttributesMap();
		String forwardUrl = attributes.get("callBackUrl").toString();
		forwardUrl += "/" + rowset.getObject("F_DJBH", "") + "?F_KJQJ=" + rowset.getString("F_KJQJ", "") 
		            + "&F_DJZT=" + rowset.getString("F_DJZT", "") + "&F_RKLX=" + rowset.getString("F_RKLX", "")
		            + "&F_CHDATE=" + F_CHDATE ;
		attributes.put("F_CHDATE", F_CHDATE);
		attributes.put("navTabId", "transfersNav");
		attributes.put("callBackUrl", forwardUrl);
		attributes.put("title", "材料入库单据");
		
		return modelView;
	}
	
	//--------------------------------------------------------------------------------------------------
	//描述:保存仓库调拨单明细
	//设计: ES
	//实现: ES
	//修改:
	//--------------------------------------------------------------------------------------------------
	@RequestMapping(value = "/transfers/formmx/insert/{F_KJQJ}", method = RequestMethod.POST)
	public ModelAndView transfersFormFlInsert(@PathVariable("F_KJQJ") String F_KJQJ,@RequestParam("F_DJBH") String F_DJBH,
			                                  @RequestParam("F_CKBH") String F_CKBH,@RequestParam("F_CHDATE") String F_CHDATE, 
			                                  HYDBDMX hydbdmxObject, @RequestParam("formType") String formType,
			                                  Model model) throws Exception {	
		SYSUser                 user = (SYSUser) session.getAttribute("contextUser");
		
		SimpleDateFormat   formatter = new SimpleDateFormat("yyyyMMdd");
		java.util.Date      currTime = new java.util.Date();

		EFDataSet        itemDataSet = EFDataSet.getInstance("HYDBDMX");
		EFRowSet              rowset = EFRowSet.getInstance();
		EFRowSet           itemRwSet = EFRowSet.getInstance();

		rowset.putString("F_DJBH", F_DJBH);
		rowset.putString("F_GUID", hydbdmxObject.getF_GUID());
		rowset.putString("F_KJQJ", F_KJQJ);
		rowset.putString("F_BZRID", user.getUSER_ID());
		rowset.putString("F_CKBH", F_CKBH);
		rowset.putString("F_DJZT", "0");
		rowset.putString("F_DATE", formatter.format(currTime));
		rowset.putString(EFFormDataModel._FORMEDITSAVETYPE_, formType);
		rowset.putString("F_CHDATE", F_CHDATE);
		
		itemRwSet = (EFRowSet) DataSetUtils.javaBean2RowSet(hydbdmxObject);
		itemDataSet.insertRowSet(itemRwSet);
		itemRwSet.putString("F_KJQJ", F_KJQJ);		
		rowset.setDataSet("HYDBDMX", itemDataSet);

		EFFormDataModel formDataModel = formModelUtil.saveForm("HYDBD", rowset, SYS_MDL_VAL.CLDB, "SaveDBDResolver");
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
		
		ModelAndView modelView = ajaxDoneSuccess("单据分录保存成功！单据编号【" + rowset.getObject("F_DJBH", "") + "】");
		MappingJacksonJsonView view = (MappingJacksonJsonView)modelView.getView();
		Map attributes = view.getAttributesMap();
		String forwardUrl = attributes.get("forwardUrl").toString();
		forwardUrl += "/" + rowset.getObject("F_DJBH", "") + "?F_KJQJ=" + rowset.getString("F_KJQJ", "") 
		            + "&F_CKBH=" + F_CKBH;
		attributes.put("F_CHDATE", F_CHDATE);
		attributes.put("navTabId", "transfersNav");
		attributes.put("forwardUrl", forwardUrl);
		attributes.put("title", "仓库调拨单据");
		
		return modelView;
	}
	
	//--------------------------------------------------------------------------------------------------
	//描述:提交仓库调拨单
	//设计: ES
	//实现: ES
	//修改:
	//--------------------------------------------------------------------------------------------------
	@RequestMapping(value = "/transfers/form/submit")
	public ModelAndView transfersFormSubmit(@RequestParam("F_KJQJ") String F_KJQJ,
										    @RequestParam("F_DJBH") String F_DJBH, 
										    @RequestParam("F_GUID") String F_GUID, 
										    @RequestParam("F_CHDATE") String F_CHDATE,  
										    HYDBD hydbdObject, Model model) {	
		SYSUser                user = (SYSUser) session.getAttribute("contextUser");
		EFDataSet       itemDataSet = EFDataSet.getInstance("HYDBDMX");
		EFRowSet             rowset = EFRowSet.getInstance();
		EFRowSet          itemRwSet = EFRowSet.getInstance();
		List            hyrkdmxList = new ArrayList();
		
		rowset.putString("F_DJBH", F_DJBH);
		rowset.putString("F_KJQJ", F_KJQJ);
		rowset.putString("F_GUID", F_GUID);
		rowset.putString("F_SBRID", user.getUSER_ID());
		rowset.putString("F_DJZT", "1");
		rowset.putNumber("F_SBDATE", (new java.util.Date()).getTime());
		rowset.putNumber("F_CHDATE", Long.parseLong(F_CHDATE));
		itemDataSet.insertRowSet(itemRwSet);
		itemRwSet.putString("F_KJQJ", F_KJQJ);	
		rowset.putString("F_DJBH", F_DJBH);
		rowset.setDataSet("HYDBDMX", itemDataSet);
		EFFormDataModel formDataModel = formModelUtil.submitForm("HYDBD", rowset, "SubmitDBDResolver");
		rowset = formDataModel.getBillDataSet().getRowSet(0);
		itemDataSet = rowset.getDataSet("HYDBDMX");
		
		if(formDataModel.getFormSaveStatus() == EFFormDataModel._FORM_SAVE_FAIL_) {
			return ajaxDoneError("保存失败！原因：<br>" + formDataModel.getFormSaveMessage());
		}
		
		try {
			HYDBD rkd = (HYDBD)EMPReflectUtil.rowtsetReflectClass(HYDBD.class.getName(), rowset);
			hyrkdmxList = EMPReflectUtil.datasetReflectClass(HYDBDMX.class.getName(), itemDataSet);
			rkd.setF_DATE(StringUtil.formatDate(((HYDBD)rkd).getF_DATE()));
			model.addAttribute("hyrkdmxList", hyrkdmxList);
			model.addAttribute("hyrkd", rkd);
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
		model.addAttribute("F_KJQJ", F_KJQJ);		
		model.addAttribute("F_DJBH", F_DJBH);
		ModelAndView modelView = ajaxDoneSuccess("提交成功！");
		MappingJacksonJsonView view = (MappingJacksonJsonView)modelView.getView();
		Map attributes = view.getAttributesMap();
		String forwardUrl = attributes.get("forwardUrl").toString();
		forwardUrl += "/" + rowset.getObject("F_DJBH", "") + "?F_KJQJ=" + rowset.getString("F_KJQJ", "") + "&F_CKBH=" + hydbdObject.getF_CKBH();
		attributes.put("F_CHDATE", F_CHDATE);
		attributes.put("forwardUrl", forwardUrl);
		attributes.put("title", "仓库调拨单据");
		
		return modelView;
	}

	//--------------------------------------------------------------------------------------------------
	//描述:保存仓库调拨单
	//设计: ES
	//实现: ES
	//修改:
	//--------------------------------------------------------------------------------------------------
	@RequestMapping(value = "/transfers/form/update", method = RequestMethod.POST)
	public ModelAndView transfersFormUpdate(HYDBD hydbd,
			 							    @RequestParam("F_CHDATE") String F_CHDATE, 
			 							    Model model) throws Exception {
		SYSUser    user = (SYSUser) session.getAttribute("contextUser");

		EFRowSet rowset = EFRowSet.getInstance();
		
		rowset = DataSetUtils.javaBean2RowSet(hydbd);
		rowset.putString("F_DJZT", "0");
		if(!F_CHDATE.equals("")) rowset.putNumber("F_CHDATE", Long.parseLong(F_CHDATE));

		rowset.putString("F_BZRID", user.getUSER_ID());
		rowset.putString("F_DATE", StringUtil.getDate(hydbd.getF_DATE()));
		
		EFFormDataModel formDataModel = formModelUtil.saveForm("HYDBD", rowset, SYS_MDL_VAL.CLDB, "SaveDBDResolver");
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
		model.addAttribute("F_DJBH", rowset.getString("F_DJBH", ""));
		model.addAttribute("F_CHDATE", F_CHDATE);
		
		ModelAndView modelView = ajaxDoneSuccess("单据保存成功！单据编号【" + rowset.getString("F_DJBH", "") + "】");
		MappingJacksonJsonView view = (MappingJacksonJsonView)modelView.getView();
		Map attributes = view.getAttributesMap();
		String forwardUrl = attributes.get("forwardUrl").toString();
		forwardUrl += "/" + rowset.getObject("F_DJBH", "") + "?F_KJQJ=" + rowset.getString("F_KJQJ", "") + "&F_CKBH=" + rowset.getString("F_CKBH", "");
		attributes.put("F_CHDATE", F_CHDATE);
		attributes.put("forwardUrl", forwardUrl);
		attributes.put("title", "仓库调拨单据");
		return modelView;
	}
	
	//--------------------------------------------------------------------------------------------------
	//描述:第一步,通过扫描枪录入材料
	//设计: ES
	//实现: ES
	//修改:
	//--------------------------------------------------------------------------------------------------
	@RequestMapping("/form/transfers/batchAdd/scanningMaterial")
	public String batchAddfl(@RequestParam String F_DJBH,
							 @RequestParam("F_GUID") String F_GUID,  
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
		model.addAttribute("F_CKBH", F_CKBH);
		model.addAttribute("F_CHDATE", F_CHDATE);
		return "/mrp/dailyBusiness/transfers/batchAdd/transfersMaterial";
	}
	
	//--------------------------------------------------------------------------------------------------
	//描述:第二部，确定材料存在
	//设计: ES
	//实现: ES
	//修改:
	//--------------------------------------------------------------------------------------------------
	@RequestMapping("/form/batchAdd/transfers/checkInputMaterial")
	public ModelAndView checkInputMaterial(@RequestParam String F_DJBH,
									       @RequestParam("F_GUID") String F_GUID, 
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
		
		errorMsg += checkFormUtil.checkFormSubmit(F_KJQJ, F_GUID, "HYDBD", F_CHDATE);
		errorMsg += checkFormUtil.checkFormEdit(F_KJQJ, F_GUID, "HYDBD", F_CHDATE);
		
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
		dialogHref += "F_DJBH="+F_DJBH+"&F_GUID="+F_GUID+"&F_KJQJ="+F_KJQJ+"&F_CKBH="+F_CKBH+"&F_CHDATE="+F_CHDATE;
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
	@RequestMapping("/form/batchAdd/transfers/selectMaterial")
	public String batchAddMaterial(@RequestParam String F_DJBH,
							       @RequestParam("F_GUID") String F_GUID,  
							       @RequestParam("F_CKBH") String F_CKBH, 
							       @RequestParam String F_KJQJ,
							       @RequestParam("F_CLBH") String F_CLBH, 
							       @RequestParam("F_CHDATE") String F_CHDATE,
							       Model model) {
		SYSUser              user = (SYSUser) session.getAttribute("contextUser");
		String           tempClbh = F_CLBH.replaceAll("\r\n", "@");
		EFDataSet              ds = formUtil.deportMaterialInfo(tempClbh, F_CKBH);
		java.util.Date   currTime = new java.util.Date();
		String           F_FLGUID = String.valueOf(currTime.getTime());
		
		model.addAttribute("clList", ds.getRowSetList());
		model.addAttribute("F_LRRID", user.getUSER_ID());
		model.addAttribute("F_KJQJ", F_KJQJ);
		model.addAttribute("F_DJBH", F_DJBH);
		model.addAttribute("F_GUID", F_GUID);
		model.addAttribute("F_CKBH", F_CKBH);
		model.addAttribute("F_CHDATE", F_CHDATE);
		model.addAttribute("F_FLGUID", F_FLGUID);
		return "/mrp/dailyBusiness/transfers/batchAdd/selectAddMaterial";
	}
	
	//--------------------------------------------------------------------------------------------------
	//描述:通过扫描枪批量添加材料入库单明细,当第一次添加的时候，没有单据编号，第一次添加后，形成单据编号
	//设计: ES
	//实现: ES
	//修改:
	//--------------------------------------------------------------------------------------------------
	@RequestMapping(value="/form/batchAdd/transfers/saveSelectMaterial", method=RequestMethod.POST)
	public  ModelAndView batchAddSacnningMaterial(@RequestBody Map dataMap, @RequestParam("formType") String formType, Model model) throws Exception {  
			SimpleDateFormat   formatter = new SimpleDateFormat("yyyyMMdd");
			java.util.Date      currTime = new java.util.Date();
			EFRowSet              rowSet = EMPJsonUtil.convertMapToRowSet(dataMap);
			EFDataSet        itemDataSet = rowSet.getDataSet(HYDBDMX.class.getName());
			EFRowSet              rowset = EFRowSet.getInstance();
			String              F_CHDATE = "";
			
			itemDataSet.setTableName("HYDBDMX");
			rowset.setDataMap(rowSet.getDataMap());
			rowset.putString("F_DJZT", "0");
			rowset.putString("F_DATE", formatter.format(currTime));
			rowset.remove(HYDBDMX.class.getName());
			rowset.setDataSet("HYDBDMX", itemDataSet);
			rowset.putString(EFFormDataModel._FORMEDITSAVETYPE_, formType);
			EFFormDataModel formDataModel = formModelUtil.saveForm("HYDBD", rowset, SYS_MDL_VAL.CLDB, "SaveDBDResolver");
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
			forwardUrl += "/" + rowset.getObject("F_DJBH", "") + "?F_KJQJ=" + rowset.getString("F_KJQJ", "")
			            + "&F_CKBH=" + rowSet.getString("F_CKBH", "");
			attributes.put("F_CHDATE", F_CHDATE);
			attributes.put("navTabId", "transfersNav");
			attributes.put("forwardUrl", forwardUrl);
			attributes.put("title", "仓库调拨单据");
			
			return modelView; 
	 } 
}
