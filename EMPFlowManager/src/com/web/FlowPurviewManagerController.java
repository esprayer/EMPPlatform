package com.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.biz.FlowManagerServiceMgr;
import com.efounder.builder.base.data.EFDataSet;
import com.efounder.builder.base.data.EFRowSet;
import com.efounder.builder.util.EMPRowSetUtil;
import com.efounder.dict.DMTServiceMgr;
import com.efounder.dict.FCTServiceMgr;
import com.efounder.eai.data.JParamObject;
import com.efounder.eai.data.JResponseObject;
import com.persistence.FLOW_NODE_LIST;
import com.persistence.FLOW_USER_LIST;
import com.web.BaseController;

@Controller
@RequestMapping(value="/standard/flowPurviewManager")
public class FlowPurviewManagerController extends BaseController{

	@Autowired
	private                            DMTServiceMgr                        DMTService;
	
	@Autowired
	private                            FCTServiceMgr                        FCTService;
	
	@Autowired
	private                    FlowManagerServiceMgr                       flowManager;
	
	@RequestMapping("/flowPurviewList/{FLOW_ID}")
	public String FlowList(@PathVariable String FLOW_ID, Model model) {
		JParamObject             PO = JParamObject.Create();

		PO.SetValueByParamName("tableName", "FLOW_USER_LIST");
		PO.SetValueByParamName("BILL_WHERE", " FLOW_USER_LIST.FLOW_BH = '" + FLOW_ID + "' and FLOW_USER_LIST.NODE_BH = ''");
		PO.SetValueByParamName("BILL_ORDER", " NODE_BH");
		EFDataSet       nodeDataset = DMTService.searchDictRow("FLOW_NODE_LIST", "FLOW_ID", FLOW_ID, null, 0, 100);
		EFDataSet       purviewDataset = FCTService.getFCTRows(PO);
		model.addAttribute("nodeDataset", nodeDataset);
		model.addAttribute("purviewDataset", purviewDataset);
		model.addAttribute("FLOW_ID", FLOW_ID);
		model.addAttribute("NODE_ID", "{slt_objId}");
		return "/standard/flow/purview/nodeList";
	}
	
	@RequestMapping("/flowNodePurviewList/{FLOW_ID}")
	public String FlowList(@PathVariable String FLOW_ID, @RequestParam String NODE_ID, Model model) {
		JParamObject             PO = JParamObject.Create();

		PO.SetValueByParamName("FLOW_ID", FLOW_ID);
		PO.SetValueByParamName("NODE_BH", NODE_ID);
		EFDataSet       purviewDataset = flowManager.loadFlowPurview(PO);
		model.addAttribute("purviewDataset", purviewDataset);
		model.addAttribute("NODE_ID", NODE_ID);
		model.addAttribute("FLOW_ID", FLOW_ID);
		return "/standard/flow/purview/purviewList";
	}
	
	@RequestMapping("/flowNodePurviewBMAdd/{NODE_ID}")
	public String bmAdd(@PathVariable String NODE_ID, @RequestParam String FLOW_ID, Model model) {
		EFRowSet    rowset = EFRowSet.getInstance();
		
		rowset.putString("FLOW_ID", FLOW_ID);
		rowset.putString("NODE_ID", NODE_ID);
		model.addAttribute("dictObject", rowset);
		return "/standard/flow/purview/purviewBMAdd";
	}
	
	@RequestMapping("/flowNodePurviewUserAdd/{NODE_ID}")
	public String userAdd(@PathVariable String NODE_ID, @RequestParam String FLOW_ID, Model model) {
		EFRowSet rowset = EFRowSet.getInstance();
		rowset.putString("FLOW_ID", FLOW_ID);
		rowset.putString("NODE_ID", NODE_ID);
		rowset.putString("F_TYPE", "USER");
		model.addAttribute("dictObject", rowset);
		return "/standard/flow/purview/purviewUserAdd";
	}
	
	@RequestMapping("/edit/{FLOW_ID}")
	public String edit(@PathVariable("FLOW_ID") String FLOW_ID, Model model) {
		EFRowSet rowset = DMTService.getRow("FLOW_NODE_LIST", "FLOW_ID", FLOW_ID);

		model.addAttribute("dictObject", rowset);
		
		return "/standard/flow/node/edit";
	}
	
	@RequestMapping(value = "/flowNodeInsert", method = RequestMethod.POST)
	public ModelAndView insert(FLOW_USER_LIST flowUserList) {
		EFRowSet    rowset;
		EFRowSet fctRowSet = null;
		String  BILL_WHERE = "";
		JParamObject    PO = JParamObject.Create();
		
		
		try {
			rowset = EMPRowSetUtil.createRowSet(flowUserList);
		} catch (Exception e) {
			e.printStackTrace();
			return ajaxDoneError(e.getMessage());
		}
		BILL_WHERE = " FLOW_USER_LIST.NODE_BH = '" + rowset.getString("NODE_BH", "") + "' and FLOW_USER_LIST.FLOW_BH = '" + rowset.getString("FLOW_BH", "") + "' and FLOW_USER_LIST.F_TYPE = '" + rowset.getString("F_TYPE", "") + "'"
                   + " and FLOW_BM = '" + rowset.getString("FLOW_BM", "") + "' and FLOW_USER = '" + rowset.getString("FLOW_USER", "") + "'";
		PO.SetValueByParamName("tableName", "FLOW_USER_LIST");
		PO.SetValueByParamName("BILL_WHERE", BILL_WHERE);
		fctRowSet = FCTService.getFCTRow(PO);
		
		if(fctRowSet == null) {
			JResponseObject RO = FCTService.insertFCTRow("FLOW_USER_LIST", rowset);
			if(RO.getErrorCode() == -1) {
				return ajaxDoneError(RO.getErrorString());
			} else {
				return ajaxDoneSuccess(RO.ResponseObject.toString());
			}
		} else {
			if(rowset.getString("FLOW_BM", "").equals("BM")) {
				return ajaxDoneError("部门【" + rowset.getString("FLOW_BM", "") + "】-【" + rowset.getString("F_BMMC", "") + "】审批节点人已经定义，请重新选择部门信息！");
			} else {
				return ajaxDoneError("用户【" + rowset.getString("FLOW_USER", "") + "】-【" + rowset.getString("USER_NAME", "") + "】审批节点人已经定义，请重新选择部门人员信息！");
			}			
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
	
	@RequestMapping("/flowPurviewDelete")
	public ModelAndView flowPurviewDelete(@RequestParam("FLOW_BH") String FLOW_BH, @RequestParam("NODE_BH") String NODE_BH, 
			                              @RequestParam("FLOW_BM") String FLOW_BM, @RequestParam("FLOW_USER") String FLOW_USER) {
		JParamObject PO = JParamObject.Create();
		
		PO.SetValueByParamName("BILL_WHERE", " FLOW_BH = '" + FLOW_BH + "' and NODE_BH = '" + NODE_BH + "' and FLOW_BM = '" + FLOW_BM + "' and FLOW_USER = '" + FLOW_USER + "'");
		JResponseObject RO = FCTService.deleteFCTRow("FLOW_USER_LIST", PO);
		if(RO.getErrorCode() == -1) {
			return ajaxDoneError(RO.getErrorString());
		} else {
			return ajaxDoneSuccess(RO.ResponseObject.toString());
		}
	}
	
	@RequestMapping("/flowUserHelp")
	public String flowUserHelp(Model model) {
		JParamObject    PO = JParamObject.Create();
		
		PO.SetValueByParamName("tableName", "HYBMZD");
		PO.SetValueByParamName("BILL_WHERE", " 1 = 1 ");
		PO.SetValueByParamName("BILL_ORDER", " F_BMBH");

		EFDataSet       bmDataset = FCTService.getFCTRows(PO);
		
		model.addAttribute("bmDataset", bmDataset);
		return "/standard/flow/purview/bmzgHelp";
	}
	
	@RequestMapping("/flowBMUserHelp")
	public String flowBMUserHelp(@RequestParam String F_BMBH, @RequestParam String USER_ID, Model model) {
		JParamObject    PO = JParamObject.Create();
		String  BILL_WHERE = "";
				
		BILL_WHERE = " USER_ORGID = '" + F_BMBH + "'";
		
		if(USER_ID.trim().length() > 0) {
			BILL_WHERE += " and USER_ID like '%" + USER_ID + "%'";
		}
		
		PO.SetValueByParamName("tableName", "BSUSER");
		PO.SetValueByParamName("BILL_WHERE", BILL_WHERE);
		PO.SetValueByParamName("BILL_ORDER", " USER_ID");
	
		EFDataSet     userDataset = FCTService.getFCTRows(PO);
		model.addAttribute("userDataset", userDataset);
		model.addAttribute("F_BMBH", F_BMBH);
		model.addAttribute("USER_ID", USER_ID);
		return "/standard/flow/purview/userList";
	}
}
