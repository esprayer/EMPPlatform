package com.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.business.MetadataColumnObject;
import com.business.MetadataServiceMgr;

@Controller
@RequestMapping(value="/standard/metadata")
public class MetadataColumnController extends BaseController{
	@Autowired
	private MetadataServiceMgr metadataMgr;

	@RequestMapping("/objcols/{OBJ_ID}")
	public String metadataList(@PathVariable("OBJ_ID") String objID, Model model) {
		List<MetadataColumnObject> metaList = metadataMgr.searchSYS_OBJCOLS(null, objID, 0, 100);
		model.addAttribute("OBJ_ID", objID);
		model.addAttribute("metaColList", metaList);
		return "/standard/metadata/objcol/list";
	}
	
	@RequestMapping("/objcols/add/{OBJ_ID}")
	public String add(@PathVariable("OBJ_ID") String objID, Model model) {
		MetadataColumnObject objCol = new MetadataColumnObject();
		objCol.setOBJ_ID(objID);
		objCol.setCOL_ISFKEY("1");
		objCol.setCOL_ISKEY("1");
		objCol.setCOL_ISNULL("1");	
		objCol.setCOL_VISIBLE("1");
		objCol.setCOL_EDITABLE("1");
		objCol.setCOL_LEN(30);
		objCol.setCOL_PREC(22);
		objCol.setCOL_SCALE(6);
		model.addAttribute("OBJ_ID", objID);
		model.addAttribute("metadataObjectCol", objCol);
		return "/standard/metadata/objcol/add";
	}
	
	@RequestMapping("/objcols/edit/{OBJ_ID}")
	public String edit(@PathVariable("OBJ_ID") String OBJ_ID, 
					   @RequestParam("COL_ID") String COL_ID,
					   Model model) {
		MetadataColumnObject metadataObjectCol = metadataMgr.getSYS_OBJCOL(OBJ_ID, COL_ID);
		model.addAttribute("OBJ_ID", OBJ_ID);
		model.addAttribute("metadataObjectCol", metadataObjectCol);
		
		return "/standard/metadata/objcol/edit";
	}
	
	@RequestMapping(value = "/objcols/insert", method = RequestMethod.POST)
	public ModelAndView insert(MetadataColumnObject metadataColObject) {
		metadataColObject.setCOL_ISKEY(checkStringNull(metadataColObject.getCOL_ISKEY(), "0"));
		metadataColObject.setCOL_ISFKEY(checkStringNull(metadataColObject.getCOL_ISFKEY(), "0"));
		metadataColObject.setCOL_ISNULL(checkStringNull(metadataColObject.getCOL_ISNULL(), "0"));
		metadataColObject.setCOL_VISIBLE(checkStringNull(metadataColObject.getCOL_VISIBLE(), "0"));
		metadataColObject.setCOL_EDITABLE(checkStringNull(metadataColObject.getCOL_EDITABLE(), "0"));	
		metadataColObject.setF_STAU(1);
		metadataMgr.addSYS_OBJCOL(metadataColObject);
		return ajaxDoneSuccess(getMessage("msg.operation.success"));
	}
	
	@RequestMapping(value = "/objcols/update", method = RequestMethod.POST)
	public ModelAndView update(MetadataColumnObject metadataColObject) {
		metadataColObject.setCOL_ISKEY(checkStringNull(metadataColObject.getCOL_ISKEY(), "0"));
		metadataColObject.setCOL_ISFKEY(checkStringNull(metadataColObject.getCOL_ISFKEY(), "0"));
		metadataColObject.setCOL_ISNULL(checkStringNull(metadataColObject.getCOL_ISNULL(), "0"));
		metadataColObject.setCOL_VISIBLE(checkStringNull(metadataColObject.getCOL_VISIBLE(), "0"));
		metadataColObject.setCOL_EDITABLE(checkStringNull(metadataColObject.getCOL_EDITABLE(), "0"));	
		metadataMgr.updSYS_OBJCOL(metadataColObject);
		return ajaxDoneSuccess(getMessage("msg.operation.success"));
	}
	
	@RequestMapping("/objcols/enableCol/{OBJ_ID}")
	public ModelAndView delete(@PathVariable("OBJ_ID") String OBJ_ID, 
			   				   @RequestParam("COL_ID") String COL_ID,
			   				   Model model) {
		metadataMgr.updateSYS_OBJCOL_STAU(OBJ_ID, COL_ID, 1);
		return ajaxDoneSuccess(getMessage("msg.operation.success"));
	}
	
	@RequestMapping("/objcols/disableCol/{OBJ_ID}")
	public ModelAndView disableCol(@PathVariable("OBJ_ID") String OBJ_ID, 
			   				       @RequestParam("COL_ID") String COL_ID,
			   				   Model model) {
		metadataMgr.updateSYS_OBJCOL_STAU(OBJ_ID, COL_ID, 0);
		return ajaxDoneSuccess(getMessage("msg.operation.success"));
	}
	
	@RequestMapping("/search/{OBJ_ID}")
	public String list(@PathVariable("OBJ_ID") String OBJ_ID, 
					   @RequestParam String keywords, 
					   @RequestParam String typeKeywords, 
					   Model model) {
		List<MetadataColumnObject> metaColList = metadataMgr.findObjectColByObjId(OBJ_ID, keywords, typeKeywords);
		model.addAttribute("metaColList", metaColList);
		model.addAttribute("OBJ_ID", OBJ_ID);
		model.addAttribute("typeKeywords", typeKeywords);
		return "/standard/metadata/objcol/list";
	}
	
	private String checkStringNull(String val, String defVal) {
		if(val == null) return defVal;
		else return val;
	}
}
