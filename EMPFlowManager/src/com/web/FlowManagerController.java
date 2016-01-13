package com.web;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.efounder.builder.base.data.EFDataSet;
import com.efounder.builder.base.data.EFRowSet;
import com.efounder.builder.util.EMPRowSetUtil;
import com.efounder.dict.DMTServiceMgr;
import com.efounder.dict.FCTServiceMgr;
import com.efounder.eai.data.JResponseObject;
import com.persistence.FLOW_STATUS_LIST;
import com.web.BaseController;

@Controller
@RequestMapping(value="/standard/flowManager")
public class FlowManagerController extends BaseController{
	@Autowired
	private                            DMTServiceMgr                        DMTService;

	@Autowired
	private                            FCTServiceMgr                        FCTService;
	
	@RequestMapping("/list")
	public String FlowList(Model model) {
		EFDataSet       ndDataset = DMTService.searchDictRow("FLOW_STATUS_LIST", "FLOW_BH", "", null, 0, 100);
		model.addAttribute("dataset", ndDataset);
		return "/standard/flowService/list";
	}
	
	@RequestMapping("/add")
	public String add(Model model) {
		return "/standard/flowService/add";
	}
	
	@RequestMapping("/edit/{FLOW_GUID}")
	public String edit(@PathVariable("FLOW_GUID") String FLOW_GUID, Model model) {
		EFRowSet rowset = DMTService.getRow("FLOW_STATUS_LIST", "FLOW_GUID", FLOW_GUID);

		model.addAttribute("dictObject", rowset);
		
		return "/standard/flowService/edit";
	}
	
	@RequestMapping(value = "/insert", method = RequestMethod.POST)
	public ModelAndView insert(FLOW_STATUS_LIST flowTaskList) {
		EFRowSet rowset;
		
		try {
			rowset = EMPRowSetUtil.createRowSet(flowTaskList);
		} catch (Exception e) {
			e.printStackTrace();
			return ajaxDoneError(e.getMessage());
		}
		rowset.putString("FLOW_GUID", String.valueOf(new Date().getTime()));
		
		JResponseObject RO = FCTService.insertFCTRow("FLOW_STATUS_LIST", rowset);
		if(RO.getErrorCode() == -1) {
			return ajaxDoneError(RO.getErrorString());
		} else {
			return ajaxDoneSuccess(RO.ResponseObject.toString());
		}
	}
	
	@RequestMapping(value = "/update/{FLOW_GUID}", method = RequestMethod.POST)
	public ModelAndView update(@PathVariable("FLOW_GUID") String FLOW_GUID, FLOW_STATUS_LIST flowTaskList, Model model) {
		EFRowSet rowset = DMTService.getRow("FLOW_STATUS_LIST", "FLOW_GUID", FLOW_GUID);
		rowset.putString("FLOW_ID", flowTaskList.getFLOW_BH());
		rowset.putString("FLOW_MC", flowTaskList.getFLOW_MC());
		rowset.putString("FLOW_STATUS", flowTaskList.getFLOW_STATUS());
		JResponseObject RO = DMTService.updateRow("FLOW_STATUS_LIST", rowset);
		if(RO.getErrorCode() == -1) {
			return ajaxDoneError(RO.getErrorString());
		} else {
			return ajaxDoneSuccess(RO.ResponseObject.toString());
		}
	}
	
	@RequestMapping("/delete/{FLOW_GUID}")
	public ModelAndView delete(@PathVariable("FLOW_GUID") String FLOW_GUID) {
		JResponseObject RO = DMTService.deleteRow("FLOW_STATUS_LIST", FLOW_GUID);
		if(RO.getErrorCode() == -1) {
			return ajaxDoneError(RO.getErrorString());
		} else {
			return ajaxDoneSuccess(RO.ResponseObject.toString());
		}
	}
	
	@RequestMapping("/flowList")
	public String FlowAllList(Model model) {
		EFDataSet       ndDataset = DMTService.searchDictRow("FLOW_STATUS_LIST", "FLOW_BH", "", null, 0, 100);
		model.addAttribute("dataset", ndDataset);
		return "/standard/flow/flowList";
	}
}
