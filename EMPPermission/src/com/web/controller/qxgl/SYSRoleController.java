package com.web.controller.qxgl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.persistence.qxgl.beans.SYSRole;
import com.persistence.qxgl.beans.SYSUser;
import com.service.qxgl.SYSRoleServiceMgr;
import com.service.qxgl.SYSYhjsqxServiceMgr;
import com.web.BaseController;

@Controller
@RequestMapping(value="/management/base/role")
public class SYSRoleController extends BaseController{
	@Autowired
	private SYSRoleServiceMgr roleMgr;
	
	@Autowired
	private SYSYhjsqxServiceMgr qxMgr;
	
	@RequestMapping("")
	public String roleList(Model model) {
		List<SYSRole> roleList = roleMgr.searchRoles(0, 0);
		model.addAttribute("roleList", roleList);
		return "/management/role/list";
	}
	
	@RequestMapping("/search")
	public String roleList(@RequestParam String keywords, Model model) {
		List<SYSRole> roleList = roleMgr.searchRoles(keywords, 0, 100);
		model.addAttribute("roleList", roleList);
		return "/management/role/list";
	}
	
	@RequestMapping("/add")
	public String addRole(Model model) {
		return "/management/role/add";
	}
	@RequestMapping("/edit/{roleId}")
	public String roleEdit(@PathVariable("roleId") String roleId, Model model) {
		SYSRole roleObject = roleMgr.getRole(roleId);
		model.addAttribute("roleObject", roleObject);
		return "/management/role/edit";
	}
	@RequestMapping(value = "/insert", method = RequestMethod.POST)
	public ModelAndView insertRole(SYSRole sysRole) {
		roleMgr.addRole(sysRole);
		return ajaxDoneSuccess(getMessage("msg.operation.success"));
	}
	
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public ModelAndView roleUpdate(SYSRole sysRole) {
		roleMgr.updateRole(sysRole);
		return ajaxDoneSuccess(getMessage("msg.operation.success"));
	}
	
	@RequestMapping("/delete/{roleId}")
	public ModelAndView roleDelete(@PathVariable("roleId") String roleId) {
		roleMgr.delRole(roleId);
		return ajaxDoneSuccess(getMessage("msg.operation.success"));
	}
	@RequestMapping("/assignUser/{roleId}")
	public String assignUser(@PathVariable("roleId") String roleId,Model model) {
		List<SYSUser> list = qxMgr.queryUserByRoleId(roleId);
		SYSRole roleObj = roleMgr.getRole(roleId);
		model.addAttribute("yfpUsers",list);
		model.addAttribute("roleObj", roleObj);
		return "/management/role/roleUser";
	}
	@RequestMapping("/userHelp/{roleId}")
	public String helpUser(@PathVariable("roleId") String roleId,Model model) {
		List<SYSUser> list = qxMgr.queryWfpUser(null, roleId);
		SYSRole roleObj = roleMgr.getRole(roleId);
		model.addAttribute("wfpUsers",list);
		model.addAttribute("roleObj", roleObj);
		model.addAttribute("roleid", roleId);
		return "/management/role/userHelp";
	}
}
