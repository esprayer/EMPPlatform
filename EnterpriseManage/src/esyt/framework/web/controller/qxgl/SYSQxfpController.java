package esyt.framework.web.controller.qxgl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import dwz.web.BaseController;
import esyt.framework.persistence.qxgl.beans.SYSRole;
import esyt.framework.service.qxgl.SYSRoleServiceMgr;

@Controller
@RequestMapping(value="/management/qxgl/usqx")
public class SYSQxfpController extends BaseController{
	@Autowired
	private SYSRoleServiceMgr roleMgr;
	
	@RequestMapping("")
	public String roleList(Model model) {
		List<SYSRole> roleList = roleMgr.searchRoles(0, 0);
		model.addAttribute("roleList", roleList);
		return "/mrp/sysConfigure/usqx/list";
	}
	
	@RequestMapping("/search")
	public String departmentList(@RequestParam String keywords, Model model) {
		List<SYSRole> roleList = roleMgr.searchRoles(keywords, 0, 100);
		model.addAttribute("roleList", roleList);
		return "/mrp/sysConfigure/usqx/list";
	}
	
	@RequestMapping("/add")
	public String addRole(Model model) {
		return "/mrp/sysConfigure/usqx/add";
	}
	@RequestMapping("/edit/{roleId}")
	public String roleEdit(@PathVariable("roleId") String roleId, Model model) {
		SYSRole roleObject = roleMgr.getRole(roleId);
		model.addAttribute("roleObject", roleObject);
		return "/mrp/sysConfigure/usqx/edit";
	}
	@RequestMapping(value = "/insert", method = RequestMethod.POST)
	public ModelAndView insertRole(SYSRole sysRole) {
		roleMgr.addRole(sysRole);
		return ajaxDoneSuccess(getMessage("msg.operation.success"));
	}
	
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public ModelAndView roleUpdate(SYSRole sysRole) {
		roleMgr.updateRole(sysRole);
		return ajaxDoneError(getMessage("msg.operation.success"));
	}
	
	@RequestMapping("/delete/{roleId}")
	public ModelAndView roleDelete(@PathVariable("roleId") String roleId) {
		roleMgr.delRole(roleId);
		return ajaxDoneError(getMessage("msg.operation.success"));
	}
}
