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

import com.mrp.biz.dailybusiness.StorageServiceMgr;
import com.mrp.biz.dailybusiness.plugins.EMPFormDataCLJZICK;
import com.mrp.biz.server.EMPCheckFormUtil;
import com.mrp.biz.server.plugins.util.FormModelDataResolverUtil;
import com.mrp.persistence.dailyBusiness.storage.bean.HYRKD;
import com.mrp.persistence.dailyBusiness.storage.bean.HYRKDMX;
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
public class StorageController extends BaseController{
	@Autowired
	private StorageServiceMgr storageMgr;

	@Autowired
	private EMPFormUtil formUtil;

	@Autowired
	private EMPCheckFormUtil checkFormUtil;
	
	@Autowired
	private FormModelDataResolverUtil formModelUtil;
	
	@Autowired
	public HttpSession session;
	
	/**
	 * 材料入库单期间范围选择
	 * @param model
	 * @return
	 */
	@RequestMapping("/storage/hyqj")
	public String storageHyqj(Model model) {	
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
		return "/mrp/dailyBusiness/storage/HYQJForm";
	}	
	
	//--------------------------------------------------------------------------------------------------
	//描述:材料入库单
	//设计: ES
	//实现: ES
	//修改:
	//--------------------------------------------------------------------------------------------------
	@RequestMapping("/storage/form/list")
	public String storageList(@RequestParam("F_KJQJ") String F_KJQJ,
						      @RequestParam("F_DJZT") String F_DJZT, 
						      @RequestParam("F_CKBH") String F_CKBH,
						      @RequestParam("F_CKMC") String F_CKMC,
						      @RequestParam("F_RKLX") String F_RKLX,
						      Model model) {
		List<HYRKD> hyrkdList = storageMgr.searchHYRKD(F_KJQJ, F_DJZT, "", F_CKBH, "", "", F_RKLX, 0, 100);
		model.addAttribute("F_KJQJ", F_KJQJ);		
		model.addAttribute("hyrkdList", hyrkdList);
		model.addAttribute("F_DJZT", F_DJZT);
		model.addAttribute("F_CKBH", F_CKBH);	
		model.addAttribute("F_CKMC", F_CKMC);		
		model.addAttribute("F_RKLX", F_RKLX);		
		return "/mrp/dailyBusiness/storage/clrkdFormList";
	}
	
	//--------------------------------------------------------------------------------------------------
	//描述:材料入库单
	//设计: ES
	//实现: ES
	//修改:
	//--------------------------------------------------------------------------------------------------
	@RequestMapping("/storage/form/rkdList")
	public String storageList(@RequestParam("F_KJQJ") String F_KJQJ,
						      @RequestParam("F_GUID") String F_GUID,
						      @RequestParam("F_RKLX") String F_RKLX,
						      Model model) {
		List<HYRKDMX>  hyrkdmxList = storageMgr.searchHYRKDMX(F_KJQJ, "", F_GUID, "", "", 0, 100);
		List<HYRKD>          hyrkd = storageMgr.searchHYRKD(F_KJQJ, "-1", "", "", "", F_GUID, F_RKLX, 0, 100);	
		hyrkd.get(0).setF_DATE(StringUtil.formatDate(hyrkd.get(0).getF_DATE()));
		model.addAttribute("hyrkd", (HYRKD)hyrkd.get(0));
		model.addAttribute("hyrkdmxList", hyrkdmxList);	
		model.addAttribute("F_CHDATE", ((HYRKD)hyrkd.get(0)).getF_CHDATE().getTime());
		return "/mrp/dailyBusiness/storage/clrkdForm";
	}
	
	//--------------------------------------------------------------------------------------------------
	//描述:材料入库单
	//设计: ES
	//实现: ES
	//修改:
	//--------------------------------------------------------------------------------------------------
	@RequestMapping("/storage/form")
	public String storageFormList(@RequestParam("F_YEAR") String F_YEAR, 
							      @RequestParam("F_MONTH") String F_MONTH,
							      @RequestParam("F_RKLX") String F_RKLX,
							      Model model) {
		String           F_KJQJ = F_YEAR + F_MONTH;
		List<HYRKD>   hyrkdList = storageMgr.searchHYRKD(F_KJQJ, "0", "", "", "", "", F_RKLX, 0, 100);
		
		model.addAttribute("F_KJQJ", F_KJQJ);	
		model.addAttribute("hyrkdList", hyrkdList);
		model.addAttribute("F_DJZT", "0");
		model.addAttribute("F_YEAR", F_YEAR);
		model.addAttribute("F_MONTH", F_MONTH);
		model.addAttribute("F_RKLX", F_RKLX);
		return "/mrp/dailyBusiness/storage/clrkdFormList";
	}
	
	//--------------------------------------------------------------------------------------------------
	//描述:新增材料入库单
	//设计: ES
	//实现: ES
	//修改:
	//--------------------------------------------------------------------------------------------------
	@RequestMapping("/storage/form/addRkd")
	public String storageAddDj(@RequestParam("F_KJQJ") String F_KJQJ, @RequestParam("F_RKLX") String F_RKLX, Model model) {
		java.util.Date       currTime = new java.util.Date();
		SimpleDateFormat    formatter = new SimpleDateFormat("yyyy-MM-dd");
		HYRKD                   hyrkd = new HYRKD();
		SYSUser                  user = (SYSUser) session.getAttribute("contextUser");
		
		
		hyrkd.setF_KJQJ(F_KJQJ);
		hyrkd.setF_GUID(UUID.randomUUID().toString());
		hyrkd.setF_DJZT("0");
		hyrkd.setF_RKLX(F_RKLX);
		hyrkd.setF_BZRID(user.getUSER_ID());
		hyrkd.setF_BZRMC(user.getUSER_NAME());	
		hyrkd.setF_DATE(formatter.format(currTime));	
		model.addAttribute("hyrkd", hyrkd);
		return "/mrp/dailyBusiness/storage/clrkdHead";
	}

	//--------------------------------------------------------------------------------------------------
	//描述:添加材料入库单明细,当第一次添加的时候，没有单据编号，第一次添加后，形成单据编号
	//设计: ES
	//实现: ES
	//修改:
	//--------------------------------------------------------------------------------------------------
	@RequestMapping("/storage/form/addfl")
	public String storageAdd(@RequestParam String F_DJBH,
							 @RequestParam("F_GUID") String F_GUID, 
							 @RequestParam("F_RKLX") String F_RKLX, 
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
		model.addAttribute("F_RKLX", F_RKLX);
		model.addAttribute("F_CKBH", F_CKBH);
		model.addAttribute("F_CHDATE", F_CHDATE);
		model.addAttribute("F_FLGUID", F_FLGUID);
		return "/mrp/dailyBusiness/storage/clrkdmxAdd";
	}
	
	//--------------------------------------------------------------------------------------------------
	//描述:第一步,通过扫描枪录入材料
	//设计: ES
	//实现: ES
	//修改:
	//--------------------------------------------------------------------------------------------------
	@RequestMapping("/form/storage/batchAdd/scanningMaterial")
	public String batchAddfl(@RequestParam String F_DJBH,
							 @RequestParam("F_GUID") String F_GUID, 
							 @RequestParam("F_RKLX") String F_RKLX, 
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
		model.addAttribute("F_RKLX", F_RKLX);
		model.addAttribute("F_CKBH", F_CKBH);
		model.addAttribute("F_CHDATE", F_CHDATE);
		return "/mrp/dailyBusiness/storage/batchAdd/storageMaterial";
	}
	
	//--------------------------------------------------------------------------------------------------
	//描述:第二部，确定材料存在
	//设计: ES
	//实现: ES
	//修改:
	//--------------------------------------------------------------------------------------------------
	@RequestMapping("/form/batchAdd/storage/checkInputMaterial")
	public ModelAndView checkInputMaterial(@RequestParam String F_DJBH,
									       @RequestParam("F_GUID") String F_GUID, 
									       @RequestParam("F_RKLX") String F_RKLX, 
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
		
		errorMsg += checkFormUtil.checkFormSubmit(F_KJQJ, F_GUID, "HYRKD", F_CHDATE);
		errorMsg += checkFormUtil.checkFormEdit(F_KJQJ, F_GUID, "HYRKD", F_CHDATE);
		
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
		dialogHref += "F_DJBH="+F_DJBH+"&F_GUID="+F_GUID+"&F_RKLX="+F_RKLX+"&F_KJQJ="+F_KJQJ+"&F_CKBH="+F_CKBH+"&F_CHDATE="+F_CHDATE;
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
	@RequestMapping("/form/batchAdd/storage/selectMaterial")
	public String batchAddMaterial(@RequestParam String F_DJBH,
							       @RequestParam("F_GUID") String F_GUID, 
							       @RequestParam("F_RKLX") String F_RKLX, 
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
		
		model.addAttribute("clList", ds.getRowSetList());
		model.addAttribute("F_LRR", user.getUSER_ID());
		model.addAttribute("F_KJQJ", F_KJQJ);
		model.addAttribute("F_DJBH", F_DJBH);
		model.addAttribute("F_GUID", F_GUID);
		model.addAttribute("F_RKLX", F_RKLX);
		model.addAttribute("F_CKBH", F_CKBH);
		model.addAttribute("F_CHDATE", F_CHDATE);
		model.addAttribute("F_FLGUID", F_FLGUID);
		return "/mrp/dailyBusiness/storage/batchAdd/selectAddMaterial";
	}
	
	//--------------------------------------------------------------------------------------------------
	//描述:通过扫描枪批量添加材料入库单明细,当第一次添加的时候，没有单据编号，第一次添加后，形成单据编号
	//设计: ES
	//实现: ES
	//修改:
	//--------------------------------------------------------------------------------------------------
	@RequestMapping(value="/form/batchAdd/storage/saveSelectMaterial", method=RequestMethod.POST)
	public  ModelAndView batchAddSacnningMaterial(@RequestBody Map dataMap, @RequestParam("formType") String formType, Model model) throws Exception {  
			SimpleDateFormat   formatter = new SimpleDateFormat("yyyyMMdd");
			java.util.Date      currTime = new java.util.Date();
			EFRowSet              rowSet = EMPJsonUtil.convertMapToRowSet(dataMap);
			EFDataSet        itemDataSet = rowSet.getDataSet(HYRKDMX.class.getName());
			EFRowSet              rowset = EFRowSet.getInstance();
			String              F_CHDATE = "";

			itemDataSet.setTableName("HYRKDMX");
			rowset.setDataMap(rowSet.getDataMap());
			rowset.putString("F_DJZT", "0");
			rowset.putString("F_DATE", formatter.format(currTime));
			rowset.remove(HYRKDMX.class.getName());
			rowset.setDataSet("HYRKDMX", itemDataSet);
			rowset.putString(EFFormDataModel._FORMEDITSAVETYPE_, formType);

			EFFormDataModel formDataModel = formModelUtil.saveForm("HYRKD", rowset, SYS_MDL_VAL.CLRK, "SaveRKDResolver");
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
			            + "&F_DJZT=" + rowset.getString("F_DJZT", "") + "&F_RKLX=" + rowset.getString("F_RKLX", "")
			            + "&F_CHDATE=" + F_CHDATE;
			attributes.put("F_CHDATE", F_CHDATE);
			attributes.put("navTabId", "storageNav");
			attributes.put("forwardUrl", forwardUrl);
			attributes.put("title", "材料入库单据");
			
			return modelView; 
	 } 
	
	//--------------------------------------------------------------------------------------------------
	//描述:保存新增项目申请单明细
	//设计: ES
	//实现: ES
	//修改:
	//--------------------------------------------------------------------------------------------------
	@RequestMapping(value = "/storage/formmx/insert/{F_KJQJ}", method = RequestMethod.POST)
	public ModelAndView storageFormFlInsert(@PathVariable("F_KJQJ") String F_KJQJ,@RequestParam("F_DJBH") String F_DJBH,
			                                @RequestParam("F_CKBH") String F_CKBH,@RequestParam("F_RKLX") String F_RKLX, 
			                                @RequestParam("F_CHDATE") String F_CHDATE, HYRKDMX hyrkdmxObject, 
			                                @RequestParam("formType") String formType,
			                                Model model) throws Exception {	
		SYSUser                 user = (SYSUser) session.getAttribute("contextUser");
		
		SimpleDateFormat   formatter = new SimpleDateFormat("yyyyMMdd");
		java.util.Date      currTime = new java.util.Date();

		EFDataSet        itemDataSet = EFDataSet.getInstance("HYRKDMX");
		EFRowSet              rowset = EFRowSet.getInstance();
		EFRowSet           itemRwSet = EFRowSet.getInstance();
		String                errMsg = "";
		
		rowset.putString("F_DJBH", F_DJBH);
		rowset.putString("F_GUID", hyrkdmxObject.getF_GUID());
		rowset.putString("F_KJQJ", F_KJQJ);
		rowset.putString("F_RKLX", F_RKLX);
		rowset.putString("F_BZRID", user.getUSER_ID());
		rowset.putString("F_CKBH", F_CKBH);
		rowset.putString("F_DJZT", "0");
		rowset.putString("F_DATE", formatter.format(currTime));
		rowset.putString(EFFormDataModel._FORMEDITSAVETYPE_, formType);
		rowset.putString("F_CHDATE", F_CHDATE);
		
		itemRwSet = (EFRowSet) DataSetUtils.javaBean2RowSet(hyrkdmxObject);
		itemDataSet.insertRowSet(itemRwSet);
		itemRwSet.putString("F_KJQJ", F_KJQJ);		
		rowset.setDataSet("HYRKDMX", itemDataSet);
		EFFormDataModel formDataModel = formModelUtil.saveForm("HYRKD", rowset, SYS_MDL_VAL.CLRK, "SaveRKDResolver");
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
		model.addAttribute("F_RKLX", F_RKLX);
		model.addAttribute("F_CHDATE", F_CHDATE);
		
		ModelAndView modelView = ajaxDoneSuccess("单据分录保存成功！单据编号【" + rowset.getObject("F_DJBH", "") + "】");
		MappingJacksonJsonView view = (MappingJacksonJsonView)modelView.getView();
		Map attributes = view.getAttributesMap();
		String forwardUrl = attributes.get("forwardUrl").toString();
		forwardUrl += "/" + rowset.getObject("F_DJBH", "") + "?F_KJQJ=" + rowset.getString("F_KJQJ", "") 
		            + "&F_DJZT=" + rowset.getString("F_DJZT", "") + "&F_RKLX=" + rowset.getString("F_RKLX", "")
		            + "&F_CHDATE=" + F_CHDATE;
		attributes.put("F_CHDATE", F_CHDATE);
		attributes.put("navTabId", "storageNav");
		attributes.put("forwardUrl", forwardUrl);
		attributes.put("title", "材料入库单据");
		
		return modelView;
	}
	
	//--------------------------------------------------------------------------------------------------
	//描述:保存新增项目申请单明细
	//设计: ES
	//实现: ES
	//修改:
	//--------------------------------------------------------------------------------------------------
	@RequestMapping(value = "/storage/form/submit")
	public ModelAndView storageFormSubmit(@RequestParam("F_KJQJ") String F_KJQJ,
										  @RequestParam("F_DJBH") String F_DJBH, 
										  @RequestParam("F_GUID") String F_GUID, 
										  @RequestParam("F_CHDATE") String F_CHDATE,  
			 							  HYRKD hyrkdObject, Model model) {	
		SYSUser                user = (SYSUser) session.getAttribute("contextUser");
		EFDataSet       itemDataSet = EFDataSet.getInstance("HYRKDMX");
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
		rowset.setDataSet("HYRKDMX", itemDataSet);
		EFFormDataModel formDataModel = formModelUtil.submitForm("HYRKD", rowset, "SubmitRDKResolver");
		rowset = formDataModel.getBillDataSet().getRowSet(0);
		itemDataSet = rowset.getDataSet("HYRKDMX");
		
		if(formDataModel.getFormSaveStatus() == EFFormDataModel._FORM_SAVE_FAIL_) {
			return ajaxDoneError("保存失败！原因：<br>" + formDataModel.getFormSaveMessage());
		}
		
		try {
			HYRKD rkd = (HYRKD)EMPReflectUtil.rowtsetReflectClass(HYRKD.class.getName(), rowset);
			hyrkdmxList = EMPReflectUtil.datasetReflectClass(HYRKDMX.class.getName(), itemDataSet);
			rkd.setF_DATE(StringUtil.formatDate(((HYRKD)rkd).getF_DATE()));
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
		forwardUrl += "/" + rowset.getObject("F_DJBH", "") + "?F_KJQJ=" + rowset.getString("F_KJQJ", "")
                    + "&F_DJZT=&F_RKLX=" + rowset.getObject("F_RKLX", "");
		attributes.put("F_CHDATE", F_CHDATE);
		attributes.put("forwardUrl", forwardUrl);
		attributes.put("title", "材料入库单据");
		
		return modelView;
	}

	//--------------------------------------------------------------------------------------------------
	//描述:保存项目申请单
	//设计: ES
	//实现: ES
	//修改:
	//--------------------------------------------------------------------------------------------------
	@RequestMapping(value = "/storage/form/update", method = RequestMethod.POST)
	public ModelAndView storageFormUpdate(HYRKD hyrkd,
			 							  @RequestParam("F_CHDATE") String F_CHDATE, 
			                              Model model) throws Exception {
		SYSUser    user = (SYSUser) session.getAttribute("contextUser");

		EFRowSet rowset = EFRowSet.getInstance();

		rowset = DataSetUtils.javaBean2RowSet(hyrkd);
		rowset.putString("F_DJZT", "0");
		if(!F_CHDATE.equals("")) rowset.putNumber("F_CHDATE", Long.parseLong(F_CHDATE));

		rowset.putString("F_BZRID", user.getUSER_ID());
		rowset.putString("F_DATE", StringUtil.getDate(hyrkd.getF_DATE()));

		EFFormDataModel formDataModel = formModelUtil.saveForm("HYRKD", rowset, SYS_MDL_VAL.CLRK, "SaveRKDResolver");
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
		forwardUrl += "/" + rowset.getObject("F_DJBH", "") + "?F_KJQJ=" + rowset.getString("F_KJQJ", "")
		            + "&F_DJZT=&F_RKLX=" + rowset.getObject("F_RKLX", "");
		attributes.put("F_CHDATE", F_CHDATE);
		attributes.put("forwardUrl", forwardUrl);
		attributes.put("title", "材料入库单据");
		return modelView;
	}
	
	//--------------------------------------------------------------------------------------------------
	//描述:删除新增项目申请单明细
	//设计: ES
	//实现: ES
	//修改:
	//--------------------------------------------------------------------------------------------------
	@RequestMapping("/storage/form/deletefl")
	public ModelAndView storageFormFlDelete(@RequestParam("F_FLBH") String F_FLBH,
            								@RequestParam("F_FLGUID") String F_FLGUID,
            								@RequestParam("F_KJQJ") String F_KJQJ, 
											@RequestParam("F_DJBH") String F_DJBH,
											@RequestParam("F_CKBH") String F_CKBH,
											@RequestParam("F_RKLX") String F_RKLX,
											@RequestParam("F_CHDATE") String F_CHDATE, 
											Model model) throws Exception {
		SYSUser             user = (SYSUser) session.getAttribute("contextUser");
		EFDataSet    itemDataSet = EFDataSet.getInstance("HYRKDMX");
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
		rowset.setDataSet("HYRKDMX", itemDataSet);
		
		EFFormDataModel formDataModel = formModelUtil.saveForm("HYRKD", rowset, SYS_MDL_VAL.CLRK, "SaveRKDResolver");
		
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
		model.addAttribute("F_RKLX", F_RKLX);
		model.addAttribute("F_CHDATE", F_CHDATE);
		
		ModelAndView modelView = ajaxDoneSuccess("单据分录保存成功！单据编号【" + rowset.getObject("F_DJBH", "") + "】");
		MappingJacksonJsonView view = (MappingJacksonJsonView)modelView.getView();
		Map attributes = view.getAttributesMap();
		String forwardUrl = attributes.get("callBackUrl").toString();
		forwardUrl += "/" + rowset.getObject("F_DJBH", "") + "?F_KJQJ=" + rowset.getString("F_KJQJ", "") 
		            + "&F_DJZT=" + rowset.getString("F_DJZT", "") + "&F_RKLX=" + rowset.getString("F_RKLX", "")
		            + "&F_CHDATE=" + F_CHDATE ;
		attributes.put("F_CHDATE", F_CHDATE);
		attributes.put("navTabId", "storageNav");
		attributes.put("callBackUrl", forwardUrl);
		attributes.put("title", "材料入库单据");
		
		return modelView;
	}
	
	//--------------------------------------------------------------------------------------------------
	//描述:编辑项目申请单
	//设计: ES
	//实现: ES
	//修改:
	//--------------------------------------------------------------------------------------------------
	@RequestMapping("/storage/form/edit/{F_DJBH}")
	public String storageEdit(@PathVariable("F_DJBH") String F_DJBH, 
			                  @RequestParam("F_KJQJ") String F_KJQJ,
			                  @RequestParam("F_DJZT") String F_DJZT,
			                  @RequestParam("F_RKLX") String F_RKLX,
			                  Model model) {
		List<HYRKDMX>   hyrkdmxList = storageMgr.searchHYRKDMX(F_KJQJ, F_DJBH, "", "", "", 0, 100);	
		List<HYRKD>       hyrkdList = storageMgr.searchHYRKD(F_KJQJ, "-1", "", "", F_DJBH, "", F_RKLX, 0, 100);
		
		hyrkdList.get(0).setF_DATE(StringUtil.formatDate(hyrkdList.get(0).getF_DATE()));
		model.addAttribute("hyrkdmxList", hyrkdmxList);
		model.addAttribute("hyrkd", (HYRKD)hyrkdList.get(0));
		model.addAttribute("F_KJQJ", F_KJQJ);		
		model.addAttribute("F_DJBH", F_DJBH);
		model.addAttribute("F_RKLX", F_RKLX);
		model.addAttribute("F_CHDATE", hyrkdList.get(0).getF_CHDATE().getTime());
		return "/mrp/dailyBusiness/storage/clrkdHead";
	}
	
	//--------------------------------------------------------------------------------------------------
	//描述:编辑项目申请单明细
	//设计: ES
	//实现: ES
	//修改:
	//--------------------------------------------------------------------------------------------------
	@RequestMapping("/storage/form/editfl")
	public String storageFormFlEdit(@RequestParam("F_FLBH") String F_FLBH,
                                    @RequestParam("F_FLGUID") String F_FLGUID,
                                    @RequestParam("F_KJQJ") String F_KJQJ,
			                        @RequestParam("F_DJBH") String F_DJBH,
			                        @RequestParam("F_DJZT") String F_DJZT,
			                        @RequestParam("F_RKLX") String F_RKLX,
			                        @RequestParam("F_CKBH") String F_CKBH,
			                        Model model) {
		List<HYRKDMX>   hyrkdmxList = storageMgr.searchHYRKDMX(F_KJQJ, F_DJBH, "", F_FLBH, F_FLGUID, 0, 100);	
		List<HYRKD>       hyrkdList = storageMgr.searchHYRKD(F_KJQJ, "-1", "", "", F_DJBH, "", F_RKLX, 0, 100);
		
		model.addAttribute("hyrkdmxList", (HYRKDMX)hyrkdmxList.get(0));
		model.addAttribute("F_CHDATE", hyrkdList.get(0).getF_CHDATE().getTime());
		model.addAttribute("F_RKLX", F_RKLX);
		model.addAttribute("F_CKBH", F_CKBH);
		model.addAttribute("F_DJZT", hyrkdList.get(0).getF_DJZT());
		return "/mrp/dailyBusiness/storage/clrkdmxEdit";
	}
}
