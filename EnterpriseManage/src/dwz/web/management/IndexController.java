package dwz.web.management;

//import javax.inject.Inject;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJacksonJsonView;

import com.dms.biz.sysLogger.SYSLOGServiceMgr;
import com.mrp.biz.queryAnalyse.QueryAnalyseServiceMgr;

import esyt.framework.com.bridge.JResponseObject;
import esyt.framework.com.builder.base.data.EFRowSet;
import esyt.framework.com.eai.data.JParamObject;
import esyt.framework.persistence.qxgl.beans.EditPassCmd;
import esyt.framework.persistence.qxgl.beans.LoginCommand;
import esyt.framework.persistence.qxgl.beans.SYSUser;
import esyt.framework.persistence.qxgl.beans.SYSYwxt;
import esyt.framework.pub.SYSConstant;
import esyt.framework.service.base.SYSMenuServiceMgr;
import esyt.framework.service.base.SYSYwxtServiceMgr;
import esyt.framework.service.qxgl.SYSSecurityMgr;
import esyt.framework.service.qxgl.SYSUserServiceMgr;

@Controller("management.indexController")
@RequestMapping("/management")
public class IndexController {

	@Autowired
	private      SYSUserServiceMgr                  userMgr;
	
	@Autowired
	private      SYSMenuServiceMgr                  menuMgr;
	
	@Autowired
	private       SYSLOGServiceMgr                sysLogMgr;
	
	@Autowired
	private QueryAnalyseServiceMgr   queryAnalyseServiceMgr;
	
	@Autowired
	public             HttpSession                  session;
	
	@Autowired
	public             SYSSecurityMgr            securityMgr;
	
	@Autowired
	public          SYSYwxtServiceMgr               ywxtMgr;
	@RequestMapping("")
	public String index() {
		return "/index";
	}
	
	@RequestMapping("/welcome")
	public String index(@RequestParam String TYPE, Model model) {
		SYSUser       user = (SYSUser) session.getAttribute("contextUser");
		JParamObject    PO = JParamObject.Create();
		JResponseObject RO = null;
		EFRowSet    rowset = null;
		SYSYwxt selectYwxt = ywxtMgr.getYwxt(TYPE);
		
		if(user == null && !TYPE.equals("Standard")) return "index";
		
		PO.SetValueByEnvName("USER_ID", user.getUSER_ID());
		PO.SetValueByEnvName("EMP", TYPE);
	
		if(TYPE.equals(SYSConstant.SYS_TYPE_MRP)) {
			PO.SetValueByParamName("CXFLAG", "ALL");
			RO = queryAnalyseServiceMgr.SystemsHomeInfo(PO);
			rowset = (EFRowSet) RO.getResponseObject();
			model.addAttribute("SYSTEMHOME", rowset);	
		}
		List menuList = securityMgr.queryUserMenu(user.getUSER_ID(), TYPE + "MENU");
		model.addAttribute("menuList", menuList);
		if(null!=selectYwxt){
			model.addAttribute("TYPE", TYPE);
			return selectYwxt.getAPP_URL();
		}
		return "/standard/index";
	}
	
	@RequestMapping(value = "/login")
	public ModelAndView loginCheck(HttpServletRequest request,LoginCommand loginCommand, Model model) throws Exception{
		int loginResult = userMgr.hasMatchUser(loginCommand.getUserId(), loginCommand.getPassword());
		String sysType = ""; 		String viewjsp = "welcome";
		String status  = "error";
		String msg     = "";
		List<SYSYwxt> ywxtList = null;
		switch(loginResult){
		case SYSConstant.LOGIN_USER_NOTEXISTS:
			viewjsp = "index";
			msg = "用户名不存。";
			ywxtList = new ArrayList();
			break;
		case SYSConstant.LOGIN_PWD_WRONG:
			viewjsp = "index";
			msg = "密码错误。";
			ywxtList = new ArrayList();
			break;
		case SYSConstant.LOGIN_VALIDATE_TRUE:
			afterLogingSuccess(request,loginCommand,"");
			ywxtList = securityMgr.queryUserYwxt(loginCommand.getUserId());
			break;
			default :break;
		}
		
//		//如果只上一个系统，则根据TYPE进入向对应的系统
//		List<String> list = determineSystemPrivileges(loginCommand);


		if(ywxtList.size() == 1) {
			sysType = ywxtList.get(0).getAPP_ID();
			List menuList = securityMgr.queryUserMenu(loginCommand.getUserId(), sysType + "MENU");
			model.addAttribute("menuList", menuList);
			if(sysType.equals(SYSConstant.SYS_TYPE_MRP)) {
				model.addAttribute("TYPE", SYSConstant.SYS_TYPE_MRP);
				viewjsp = "/mrp/index";
			} else if(sysType.equals(SYSConstant.SYS_TYPE_DMS)) {
				model.addAttribute("TYPE", SYSConstant.SYS_TYPE_DMS);	
				viewjsp = "/dms/index";
			} else if(sysType.equals(SYSConstant.SYS_TYPE_UPM)) {
				model.addAttribute("TYPE", SYSConstant.SYS_TYPE_UPM);	
				viewjsp = "/upm/index";
			} else if(sysType.equals(SYSConstant.SYS_TYPE_CMS)) {
				model.addAttribute("TYPE", SYSConstant.SYS_TYPE_CMS);	
				viewjsp = "/cms/index";
			}
		} else if(ywxtList.size() > 1) {
			model.addAttribute("ywxtList", ywxtList);	
		} else {
			viewjsp = "index";
			msg = "用户没有权限访问系统。";
		}

		ModelAndView modelView =  new ModelAndView(viewjsp, status, msg);
		modelView.getModel().put("time", "102010");
		modelView.getModel().put("username", loginCommand.getUserId());
		modelView.getModel().put("password", loginCommand.getPassword());
		return modelView;
	}
	
	//判断用户具有使用哪个系统的权限
	private List<String> determineSystemPrivileges(LoginCommand loginCommand) {
		List<String> list = new ArrayList<String>();
		list.add(SYSConstant.SYS_TYPE_DMS);
		if(loginCommand.getUserId().equals("admin")) list.add(SYSConstant.SYS_TYPE_MRP);
		return list;
	}
	
	
	
	/**
	 *登陆成功之后的一些操作
	 * @param request
	 * @param loginCommand
	 */
	private void afterLogingSuccess(HttpServletRequest request,LoginCommand loginCommand,String sysType){
		SYSUser user = userMgr.loadUserById(loginCommand.getUserId());
		request.getSession().setAttribute("contextUser", user);
		request.getSession().setAttribute("sysType", sysType);
	}
	

	@RequestMapping(value = "/logout")
	public String logout(){		
		session.invalidate();
		return "/index";
	}
	
	@RequestMapping("/timeoutLoginin")
	public String timeoutLoginin(@RequestParam String TYPE, Model model) {
		LoginCommand loginCommand = new LoginCommand();
		model.addAttribute("loginCommand", loginCommand);	
		model.addAttribute("TYPE", TYPE);
		return "timeoutLogin";
	}
	
	@RequestMapping(value = "/timeoutLogininCheck")
	public ModelAndView timeoutLogininCheck(@RequestParam("TYPE") String TYPE, HttpServletRequest request,LoginCommand loginCommand, Model model) throws Exception{
		int loginResult = userMgr.hasMatchUser(loginCommand.getUserId(), loginCommand.getPassword());
		
		switch(loginResult){
			case SYSConstant.LOGIN_USER_NOTEXISTS:
				return ajaxDone(300, "用户名不存。");
			case SYSConstant.LOGIN_PWD_WRONG:
				return ajaxDone(300, "密码错误。");
			case SYSConstant.LOGIN_VALIDATE_TRUE:
				afterLogingSuccess(request,loginCommand,TYPE);
					
				if(TYPE.equals(SYSConstant.SYS_TYPE_DMS)) {
					sysLogMgr.insertLoginSYSLog(request);
				}
				break;
		}

		return ajaxDone(200, "重新登陆成功。");
	}
	
	@RequestMapping(value = "/checkSession")
	public ModelAndView checkSession(Model model){
		SYSUser user = (SYSUser) session.getAttribute("contextUser");
		if(user == null) return ajaxDone(301, "");
		else return ajaxDone(200, "");
	}
	
	@RequestMapping(value = "/updatePwd", method = RequestMethod.POST)
	public ModelAndView updatePwd(EditPassCmd editPassCmd,Model model) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		SYSUser user = (SYSUser)session.getAttribute("contextUser");
		if(null!=user){
			editPassCmd.setUserId(user.getUSER_ID());
			userMgr.updatePass(editPassCmd);
		}else{
			return ajaxDone(301, "");
		}
		return ajaxDone(200, "修改密码成功！");

	}
	@RequestMapping(value = "/changePwd")
	public String changePwd(Model model) {
		model.addAttribute("oldPass","");
		return "/management/editPwd";
	}


	protected ModelAndView ajaxDone(int statusCode, String message) {
		ModelAndView mav = new ModelAndView("ajaxDone");

		mav.addObject("statusCode", statusCode);
		mav.addObject("message", message);
		
		  //开始返回
        MappingJacksonJsonView view = new MappingJacksonJsonView();
        Map attributes = new HashMap();
        attributes.put("success", Boolean.TRUE);
        attributes.put("statusCode", statusCode);
        attributes.put("message", message);
        if(statusCode == 200) attributes.put("callbackType", "closeCurrent");
        view.setAttributesMap(attributes);
        mav.setView(view);
		return mav;
	}
}