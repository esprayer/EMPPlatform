package com.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.biz.FlowManagerServiceMgr;
import com.efounder.builder.base.data.EFDataSet;
import com.efounder.dict.DMTServiceMgr;
import com.efounder.dict.FCTServiceMgr;
import com.efounder.eai.data.JParamObject;
import com.persistence.FLOW_TASK_LIST;
import com.web.BaseController;

@Controller
@RequestMapping(value="/standard/flowTaskManager")
public class FlowTaskManagerController extends BaseController{
	@Autowired
	private                            DMTServiceMgr                        DMTService;

	@Autowired
	private                            FCTServiceMgr                        FCTService;
	
	@Autowired
	private                    FlowManagerServiceMgr                flowManagerService;
	
	@RequestMapping("/queryList")
	public String querFlowTaskList(@RequestParam String FLOW_ID, @RequestParam String BIZ_DATE, @RequestParam String BIZ_DJBH, @RequestParam String BIZ_GUID, Model model) {
		JParamObject PO = JParamObject.Create();
		PO.SetValueByParamName(FLOW_TASK_LIST._FLOW_ID, FLOW_ID);
		PO.SetValueByParamName(FLOW_TASK_LIST._BIZ_DATE, BIZ_DATE);
		PO.SetValueByParamName(FLOW_TASK_LIST._BIZ_DJBH, BIZ_DJBH);
		PO.SetValueByParamName(FLOW_TASK_LIST._BIZ_GUID, BIZ_GUID);
		
		EFDataSet       ndDataset = flowManagerService.loadAllFlowTask(PO);
		model.addAttribute("dataset", ndDataset);
		return "/standard/flow/flowTask/list";
	}
}
