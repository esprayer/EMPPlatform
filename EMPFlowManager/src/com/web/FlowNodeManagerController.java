package com.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.efounder.builder.base.data.EFDataSet;
import com.efounder.builder.base.data.EFRowSet;
import com.efounder.builder.util.EMPRowSetUtil;
import com.efounder.dict.DMTServiceMgr;
import com.efounder.dict.FCTServiceMgr;
import com.efounder.eai.data.JParamObject;
import com.efounder.eai.data.JResponseObject;
import com.persistence.FLOW_NODE_LIST;
import com.web.BaseController;

@Controller
@RequestMapping(value="/standard/flowNodeManager")
public class FlowNodeManagerController extends BaseController{

	@Autowired
	private                            DMTServiceMgr                        DMTService;
	
	@Autowired
	private                            FCTServiceMgr                        FCTService;
	
	@RequestMapping("/flowNodeList/{FLOW_ID}")
	public String FlowList(@PathVariable String FLOW_ID, Model model) {
		EFRowSet         flowRowSet = DMTService.getRow("FLOW_STATUS_LIST", "FLOW_GUID", FLOW_ID);
		EFDataSet         ndDataset = DMTService.searchDictRow("FLOW_NODE_LIST", "FLOW_ID", flowRowSet.getString("FLOW_BH", ""), null, 0, 100);
		model.addAttribute("FLOW_ID", FLOW_ID);
		model.addAttribute("dataset", ndDataset);
		return "/standard/flow/node/list";
	}
	
	@RequestMapping("/flowNodeAdd/{FLOW_ID}")
	public String add(@PathVariable String FLOW_ID, Model model) {
		EFRowSet   flowRowSet = DMTService.getRow("FLOW_STATUS_LIST", "FLOW_GUID", FLOW_ID);
		EFRowSet       rowset = EFRowSet.getInstance();
		rowset.putString("FLOW_ID", FLOW_ID);
		model.addAttribute("dictObject", flowRowSet);
		return "/standard/flow/node/add";
	}
	
	@RequestMapping("/edit/{FLOW_ID}")
	public String edit(@PathVariable("FLOW_ID") String FLOW_ID, Model model) {
		EFRowSet rowset = DMTService.getRow("FLOW_NODE_LIST", "FLOW_ID", FLOW_ID);

		model.addAttribute("dictObject", rowset);
		
		return "/standard/flow/node/edit";
	}
	
	@RequestMapping(value = "/flowNodeInsert", method = RequestMethod.POST)
	public ModelAndView insert(FLOW_NODE_LIST flowNodeList) {
		EFRowSet rowset;
		
		try {
			rowset = EMPRowSetUtil.createRowSet(flowNodeList);
		} catch (Exception e) {
			e.printStackTrace();
			return ajaxDoneError(e.getMessage());
		}
		
		JResponseObject RO = FCTService.insertFCTRow("FLOW_NODE_LIST", rowset);
		if(RO.getErrorCode() == -1) {
			return ajaxDoneError(RO.getErrorString());
		} else {
			return ajaxDoneSuccess(RO.ResponseObject.toString());
		}
	}
	
	@RequestMapping(value = "/flowNodeUpdate/{FLOW_ID}", method = RequestMethod.POST)
	public ModelAndView update(@PathVariable("FLOW_ID") String FLOW_ID, FLOW_NODE_LIST flowNodeList, Model model) {
		EFRowSet rowset = DMTService.getRow("FLOW_NODE_LIST", "FLOW_ID", FLOW_ID);
		rowset.putString("NODE_NAME", flowNodeList.getNODE_NAME());
		rowset.putString("NODE_RETRIEVE", flowNodeList.getNODE_RETRIEVE());
		rowset.putString("NODE_TYPE", flowNodeList.getNODE_TYPE());
		rowset.putString("NODE_NEXT", flowNodeList.getNODE_NEXT());
		JResponseObject RO = DMTService.updateRow("FLOW_NODE_LIST", rowset);
		if(RO.getErrorCode() == -1) {
			return ajaxDoneError(RO.getErrorString());
		} else {
			return ajaxDoneSuccess(RO.ResponseObject.toString());
		}
	}
	
	@RequestMapping("/flowNodeDelete/{FLOW_ID}")
	public ModelAndView delete(@PathVariable("FLOW_ID") String FLOW_ID, @RequestParam String NODE_ID) {
		JParamObject PO = JParamObject.Create();
		
		PO.SetValueByParamName("BILL_WHERE", " FLOW_ID = '" + FLOW_ID + "' and NODE_ID = '" + NODE_ID + "'");
		JResponseObject RO = FCTService.deleteFCTRow("FLOW_STATUS_LIST", PO);
		if(RO.getErrorCode() == -1) {
			return ajaxDoneError(RO.getErrorString());
		} else {
			return ajaxDoneSuccess(RO.ResponseObject.toString());
		}
	}
}
