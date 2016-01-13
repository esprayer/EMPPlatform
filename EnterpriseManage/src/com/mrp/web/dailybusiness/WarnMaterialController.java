package com.mrp.web.dailybusiness;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.mrp.biz.dailybusiness.WarnMaterialServiceMgr;

import com.mrp.persistence.dailyBusiness.warnmaterial.bean.HYCLYJ;

import dwz.web.BaseController;

@Controller
@RequestMapping(value="/dailyBusiness")
public class WarnMaterialController extends BaseController{
	@Autowired
	private WarnMaterialServiceMgr warnMaterialMgr;
	
	/**
	 * 材料预警设置
	 * @param model
	 * @return
	 */
	@RequestMapping("/warnmaterial")
	public String materialList(Model model) {
		List<HYCLYJ> clyjList = warnMaterialMgr.searchHYCLYJ(0, 100);
		model.addAttribute("clyjList", clyjList);
		return "/mrp/dailyBusiness/warnmaterial/list";
	}

	@RequestMapping("/warnmaterial/search")
	public String materialList(@RequestParam String F_CLBH, Model model) {
		List<HYCLYJ> clyjList = warnMaterialMgr.searchHYCLYJ(F_CLBH, 0, 100);
		model.addAttribute("F_CLBH", F_CLBH);
		model.addAttribute("clyjList", clyjList);
		return "/mrp/dailyBusiness/warnmaterial/list";
	}
	
	@RequestMapping("/warnmaterial/add")
	public String materialAdd(Model model) {
		HYCLYJ clyjObject = new HYCLYJ();
		model.addAttribute("clyjObject", clyjObject);
		return "/mrp/dailyBusiness/warnmaterial/add";
	}

	@RequestMapping("/warnmaterial/edit/{F_CLBH}")
	public String materialEdit(@PathVariable("F_CLBH") String F_CLBH, Model model) {
		HYCLYJ clyjObject = warnMaterialMgr.getHYCLYJ(F_CLBH);		
		model.addAttribute("clyjObject", clyjObject);
		return "/mrp/dailyBusiness/warnmaterial/edit";
	}
	
	@RequestMapping(value = "/warnmaterial/insert", method = RequestMethod.POST)
	public ModelAndView materialInsert(HYCLYJ clyjObject) {
		warnMaterialMgr.addHYCLYJ(clyjObject);
		return ajaxDoneSuccess("添加成功！");
	}
	
	@RequestMapping(value = "/warnmaterial/update", method = RequestMethod.POST)
	public ModelAndView materialUpdate(HYCLYJ clyjObject, Model model) {
		warnMaterialMgr.updateHYCLYJ(clyjObject);
		clyjObject = warnMaterialMgr.getHYCLYJ(clyjObject.getF_CLBH());
		model.addAttribute("hyclObject", clyjObject);
		model.addAttribute("F_CLBH", clyjObject.getF_CLBH());
		return ajaxDoneSuccess("修改成功！");
	}
	
	@RequestMapping("/warnmaterial/delete/{F_CLBH}")
	public ModelAndView materialDelete(@PathVariable("F_CLBH") String F_CLBH) {
		warnMaterialMgr.delHYCLYJ(F_CLBH);
		return ajaxDoneSuccess("删除成功！");
	}
}
