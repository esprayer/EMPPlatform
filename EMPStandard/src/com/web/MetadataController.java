package com.web;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.metadata.biz.MetaDataServiceMgr;
import com.persistence.qxgl.beans.SYSUser;
import com.pub.util.EMPExportExcelTable;
import com.web.BaseController;

import com.business.MetadataObject;
import com.business.MetadataServiceMgr;
import com.cms.biz.business.CMSGeneralContractServiceMgr;
import com.cms.biz.queryAnalyse.CMSQueryAnalyseServiceMgr;
import com.efounder.builder.base.data.EFDataSet;
import com.efounder.builder.base.data.EFRowSet;
import com.efounder.eai.data.JParamObject;
import com.efounder.eai.data.JResponseObject;
import com.enums.EMPQueryParamEnum;

@Controller
@RequestMapping(value="/standard/metadata/object")
public class MetadataController extends BaseController{
	@Autowired
	private MetadataServiceMgr metadataMgr;

	@RequestMapping("")
	public String metadataList(Model model) {
		List<MetadataObject> metaList = metadataMgr.searchSYS_OBJECT(null, 0, 100);
		model.addAttribute("metaList", metaList);
		return "/standard/metadata/object/list";
	}

	@RequestMapping("/search")
	public String list(@RequestParam String keywords, Model model) {
		List<MetadataObject> metaList = metadataMgr.findByObjId(keywords);
		model.addAttribute("metaList", metaList);
		return "/standard/metadata/object/list";
	}
	
	@RequestMapping("/add")
	public String add(Model model) {
		return "/standard/metadata/object/add";
	}
	
	@RequestMapping("/edit/{OBJ_ID}")
	public String edit(@PathVariable("OBJ_ID") String objID, Model model) {
		MetadataObject metadataObject = metadataMgr.getSYS_OBJECT(objID);

		model.addAttribute("metadataObject", metadataObject);
		
		return "/standard/metadata/object/edit";
	}
	
	@RequestMapping(value = "/insert", method = RequestMethod.POST)
	public ModelAndView insert(MetadataObject metadataObject) {
		metadataMgr.addSYS_OBJECT(metadataObject);

		return ajaxDoneSuccess(getMessage("msg.operation.success"));
	}
	
	@RequestMapping(value = "/update/{OBJ_ID}", method = RequestMethod.POST)
	public ModelAndView update(@PathVariable("OBJ_ID") String objID, MetadataObject metadataObject, Model model) {
		metadataObject.setOBJ_ID(objID);
		metadataMgr.updSYS_OBJECT(metadataObject);
		metadataObject = metadataMgr.getSYS_OBJECT(objID);
		model.addAttribute("metadataObject", metadataObject);
		return ajaxDoneSuccess(getMessage("msg.operation.success"));
	}
	
	@RequestMapping("/delete/{OBJ_ID}")
	public ModelAndView delete(@PathVariable("OBJ_ID") String objID) {

		metadataMgr.delSYS_OBJECT(objID);

		return ajaxDoneSuccess(getMessage("msg.operation.success"));
	}
	
	@RequestMapping("/createObject/{OBJ_ID}")
	public ModelAndView createObject(@PathVariable("OBJ_ID") String objID) {
		Object[] message = metadataMgr.createObject(objID);
		return ajaxDoneSuccess(getMessage("msg.operation.success"));
	}
	
	@RequestMapping("/deleteObject/{OBJ_ID}")
	public ModelAndView deleteObject(@PathVariable("OBJ_ID") String objID) {
		Object[] message = metadataMgr.deleteObject(objID);
		return ajaxDoneSuccess(getMessage("msg.operation.success"));
	}
}
