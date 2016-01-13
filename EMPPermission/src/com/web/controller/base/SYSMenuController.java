package com.web.controller.base;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.persistence.qxgl.beans.SYSMenu;
import com.persistence.qxgl.beans.SYSYwxt;
import com.service.base.SYSMenuServiceMgr;
import com.service.base.SYSYwxtServiceMgr;
import com.web.BaseController;

@Controller
@RequestMapping(value="/management/base/menu")
public class SYSMenuController extends BaseController{
	@Autowired
	private SYSMenuServiceMgr menuMgr;
	@Autowired
	private SYSYwxtServiceMgr ywxtMgr;
	@RequestMapping("")
	public String menuList(Model model) {
		List<SYSYwxt> ywxtList = menuMgr.queryYwxt();
		List<SYSMenu> menuList = null;
		List<SYSMenu> menuTree = null;
		SYSYwxt ywxt = null;
		if(null!=ywxtList&&!ywxtList.isEmpty()){
			ywxt = ywxtList.get(0);
			menuList = menuMgr.searchMenu("ROOT", ywxt.getAPP_ID(), 0, 0, 100);
			menuTree = menuMgr.searchMenu(ywxt.getAPP_ID(), 0, 100);
		}
		
		model.addAttribute("menuList", menuList);
		model.addAttribute("menuTree", menuTree);
		model.addAttribute("ywxtList", ywxtList);
		model.addAttribute("ywxtObject", ywxt);
		return "/management/menu/list";
	}
	
	@RequestMapping("/search/{menuId}")
	public String menuList(@PathVariable("menuId") String menuId,@RequestParam int js,@RequestParam String APP_ID, Model model) {
		List<SYSMenu> menuList = menuMgr.searchMenu(menuId,APP_ID,js, 0, 100);
		List<SYSMenu> menuTree = menuMgr.searchMenu(APP_ID, 0, 100);

		model.addAttribute("menuList", menuList);
		model.addAttribute("menuTree", menuTree);
		model.addAttribute("APP_ID", APP_ID);
		model.addAttribute("parent", menuId);

		return "/management/menu/menuTable";
	}
	
	@RequestMapping("/add/{menuId}")
	public String addYwxt(@PathVariable("menuId") String menuId,@RequestParam String APP_ID,Model model) {
		SYSMenu parent = menuMgr.getMenu(menuId,APP_ID);
		SYSMenu menuObject = new SYSMenu();
		if(null!=parent){
			menuObject.setMENU_BH(parent.getMENU_BH());
			menuObject.setF_PARENT(parent.getMENU_BH());
			menuObject.setF_JS(parent.getF_JS()+1);
		}else{
			menuObject.setF_JS(1);
			menuObject.setF_PARENT("#ROOT");
		}
		menuObject.setAPP_ID(APP_ID);
		menuObject.setF_MX("1");
		
		model.addAttribute("menuObject", menuObject);
		return "/management/menu/add";
	}
	@RequestMapping("/edit/{menuId}")
	public String menuEdit(@PathVariable("menuId") String menuId,@RequestParam String APP_ID, Model model) {
		SYSMenu menuObject = menuMgr.getMenu(menuId,APP_ID);
		model.addAttribute("menuObject", menuObject);
		model.addAttribute("APP_ID", APP_ID);
		return "/management/menu/edit";
	}
	@RequestMapping(value = "/insert", method = RequestMethod.POST)
	public ModelAndView insertMenu(SYSMenu sysMenu){
		sysMenu.setF_CRDATE(new Date());
		sysMenu.setF_CHDATE(new Date());
		menuMgr.addMenu(sysMenu);
		return ajaxDoneSuccess(getMessage("msg.operation.success"));
	}
	
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public ModelAndView menuUpdate(SYSMenu SYSMenu) {
		menuMgr.updateMenu(SYSMenu);
		return ajaxDoneSuccess(getMessage("msg.operation.success"));
	}
	
	@RequestMapping("/delete/{menuId}")
	public ModelAndView menuDelete(@PathVariable("menuId") String menuId,@RequestParam String APP_ID) {
		menuMgr.delMenu(menuId,APP_ID);
		return ajaxDoneSuccess(getMessage("msg.operation.success"));
	}
	@RequestMapping("/change")
	public String ywxtChange(@RequestParam String ywxtId, Model model) {
		List<SYSYwxt> ywxtList = menuMgr.queryYwxt();
		List<SYSMenu> menuList = null;
		List<SYSMenu> menuTree = null;
		SYSYwxt ywxt = ywxtMgr.getYwxt(ywxtId);
		if(null!=ywxt){
			menuList = menuMgr.searchMenu("ROOT", ywxt.getAPP_ID(), 0, 0, 100);
			menuTree = menuMgr.searchMenu(ywxt.getAPP_ID(), 0, 100);
		}
		
		model.addAttribute("menuList", menuList);
		model.addAttribute("menuTree", menuTree);
		model.addAttribute("ywxtList", ywxtList);
		model.addAttribute("ywxtObject", ywxt);
		return "/management/menu/list";
	}
}
