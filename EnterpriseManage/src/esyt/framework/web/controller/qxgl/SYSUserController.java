package esyt.framework.web.controller.qxgl;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.mrp.biz.sysconfigure.sysConfigureServiceMgr;
import com.mrp.persistence.sysConfigure.department.bean.HYBMZD;

import dwz.web.BaseController;
import esyt.framework.persistence.qxgl.beans.SYSUser;
import esyt.framework.service.qxgl.SYSUserServiceMgr;

@Controller
@RequestMapping(value="/management/base/user")
public class SYSUserController extends BaseController{
	@Autowired
	private SYSUserServiceMgr userMgr;
	@Autowired
	private sysConfigureServiceMgr sysConfigureMgr;

	@RequestMapping("")
	public String userList(Model model) {
		List<HYBMZD> hybmList = sysConfigureMgr.searchBMZD("1", 0, 100);
		model.addAttribute("hybmList", hybmList);
		return "/management/user/list";
	}
	/**
	 * 用户
	 * @param model
	 * @return
	 */
	@RequestMapping("/{USER_ORGID}")
	public String userList(@PathVariable("USER_ORGID") String USER_ORGID, Model model) {
		List<SYSUser> userList = userMgr.searchUser(USER_ORGID, 0, 100);
		model.addAttribute("userList", userList);
		model.addAttribute("USER_ORGID", USER_ORGID);
		return "/management/user/userList";
	}
	
	
	@RequestMapping("/search/{USER_ORGID}")
	public String userList(@PathVariable("USER_ORGID") String USER_ORGID, @RequestParam String keywords, Model model) {
		List<SYSUser> userList = userMgr.searchUser(USER_ORGID, keywords, 0, 100);
		model.addAttribute("userList", userList);
		model.addAttribute("USER_ORGID", USER_ORGID);
		return "/management/user/userList";
	}
	
	@RequestMapping("/add/{USER_ORGID}")
	public String userAdd(@PathVariable("USER_ORGID") String USER_ORGID, Model model) {
		SYSUser userObject = new SYSUser();
		userObject.setUSER_ORGID(USER_ORGID);
		model.addAttribute("userobj", userObject);
		return "/management/user/add";
	}
	
	@RequestMapping("/insert/{USER_ORGID}")
	public ModelAndView userInsert(@PathVariable("USER_ORGID") String USER_ORGID, SYSUser userObject, Model model) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		List<SYSUser> userList = userMgr.checkUserById(userObject.getUSER_ID());
		
		if(userList != null && userList.size() > 0) {
			ModelAndView mav = ajaxDoneError("用户编号已经存在，请重新输入一个编号！");
			return mav;
		}
		
		userObject.setUSER_ORGID(USER_ORGID);
		userMgr.addUser(userObject);
		ModelAndView mav = ajaxDoneSuccess(getMessage("msg.operation.success"));
		model.addAttribute("USER_ORGID",USER_ORGID);
		return mav;
	}
	
	@RequestMapping("/edit/{USER_ID}")
	public String userEdit(@PathVariable("USER_ID") String USER_ID, 
			   			   @RequestParam("USER_ORGID") String USER_ORGID, 
			   			   Model model) {
		SYSUser userobj = userMgr.getUser(USER_ORGID, USER_ID);
		model.addAttribute("userobj", userobj);
		model.addAttribute("USER_ID", USER_ID);
		model.addAttribute("USER_ORGID", USER_ORGID);
		return "/management/user/edit";
	}
	
	@RequestMapping(value = "/update/{USER_ID}", method = RequestMethod.POST)
	public ModelAndView userUpdate(@PathVariable("USER_ID") String USER_ID, 
			   					   @RequestParam("USER_ORGID") String USER_ORGID, 
			   					   SYSUser userObject, Model model) {
		userObject.setUSER_ID(USER_ID);
		userObject.setUSER_ORGID(USER_ORGID);
		userMgr.updateUser(userObject);
		return ajaxDoneSuccess(getMessage("msg.operation.success"));
	}
	
	@RequestMapping("/delete/{USER_ID}")
	public ModelAndView userDelete(@PathVariable("USER_ID") String USER_ID, 
								   @RequestParam("USER_ORGID") String USER_ORGID) {
		userMgr.delUser(USER_ORGID, USER_ID);
		return ajaxDoneSuccess(getMessage("msg.operation.success"));
	}
	
	@RequestMapping("/deport/department/{F_BMBH}")
	public String departmentUserList(@PathVariable("F_BMBH") String F_BMBH, Model model) {
		List<SYSUser> userList = userMgr.searchUser(F_BMBH, 0, 100);
		model.addAttribute("userList", userList);
		model.addAttribute("F_BMBH", F_BMBH);
		return "/management/zdHelp/zgHelp";
	}
	
	@RequestMapping("/deport/department/user/{F_BMBH}")
	public String departmentUserLists(@PathVariable("F_BMBH") String F_BMBH, @RequestParam String keywords, Model model) {
		List<SYSUser> userList = userMgr.searchUser(F_BMBH, keywords, 0, 100);
		model.addAttribute("userList", userList);
		model.addAttribute("F_BMBH", F_BMBH);
		return "/management/zdHelp/zgHelp";
	}
	
}
