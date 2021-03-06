package com.web.controller.qxgl;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJacksonJsonView;

import com.efounder.builder.base.data.EFDataSet;
import com.efounder.eai.EAI;
import com.efounder.eai.data.JParamObject;
import com.efounder.eai.data.JResponseObject;
import com.persistence.qxgl.beans.RoleFpCommand;
import com.persistence.qxgl.beans.SYSUser;
import com.persistence.qxgl.beans.SYSYwxt;
import com.service.base.SYSYwxtServiceMgr;
import com.service.qxgl.SYSRoleServiceMgr;
import com.service.qxgl.SYSUserServiceMgr;
import com.service.qxgl.SYSYhjsqxServiceMgr;
import com.web.BaseController;

/**
 * 用户权限管理
 * @author frog
 *
 */
@Controller
@RequestMapping(value="/management/qxgl")
public class SYSQxglController extends BaseController{
	@Autowired
	private SYSYhjsqxServiceMgr qxglMgr;
	@Autowired
	private SYSUserServiceMgr userMgr;
	@Autowired
	private SYSRoleServiceMgr roleMgr;
	@Autowired
	private SYSYwxtServiceMgr ywxtMgr;
	@Autowired
	public                               HttpSession                           session;
	
	@RequestMapping("/assignRole/{USER_ID}")
	public String assignUserRole(@PathVariable("USER_ID") String USER_ID,Model model) {
		model.addAttribute("user",userMgr.loadUserById(USER_ID));
		model.addAttribute("wfpRoles",qxglMgr.queryWfpRole(USER_ID));
		model.addAttribute("userRoles",qxglMgr.queryUserRole(USER_ID));
		return "/management/user/rolelist";
	}
	@RequestMapping("/saveUserRoleGx")
	public ModelAndView saveUserRoleGx(RoleFpCommand command, Model model) {
		qxglMgr.saveUsRole(command);
		ModelAndView mav = ajaxDoneSuccess(getMessage("msg.operation.success"));
		return mav;
	}
	@RequestMapping("/assignGnqx/{keyid}")
	public String assignGnqx(@PathVariable("keyid") String keyId,Model model) {
		List<SYSYwxt> ywxtList = null;
		List<Map> menuList = null;
		SYSYwxt ywxt = null;
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		
		String type = request.getParameter("Type");
		
		ywxtList = ywxtMgr.searchYwxt(1, 100);
		if(null!=ywxtList&&!ywxtList.isEmpty()){
			ywxt = ywxtList.get(0);
			menuList = qxglMgr.queryAllMenu(ywxt.getAPP_ID());
		}
		model.addAttribute("ywxtList",ywxtList);
		model.addAttribute("menuList",menuList);
		model.addAttribute("ywxtObject",ywxt);
		setGnqxObj(type,keyId,model);//
		return getGnqxUrl(type);
	}
	
	@RequestMapping("/allocationFunction/{keyid}")
	public String allocationFunction(@PathVariable("keyid") String keyId,Model model) {
		List<SYSYwxt> ywxtList = null;
		List<Map> menuList = null;
		EFDataSet      xtDataSet = null;
		SYSYwxt ywxt = null;
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
	
		String type = request.getParameter("Type");

		xtDataSet = ywxtMgr.queryXTZD();
		
		ywxtList = ywxtMgr.searchYwxt(1, 100);
		if(null!=ywxtList&&!ywxtList.isEmpty()){
			ywxt = ywxtList.get(0);
			menuList = qxglMgr.queryAllMenu(ywxt.getAPP_ID());
		}
		model.addAttribute("xtDataSet",xtDataSet);
		model.addAttribute("menuList",xtDataSet);
		model.addAttribute("ywxtObject",ywxt);
		setGnqxObj(type,keyId,model);//
		return "/management/user/menugnqx";
	}
	
	//处理用户功能权限
	private void setGnqxObj(String type,String key,Model model){
		if(null!=type && type.equals("role")){
			model.addAttribute("roleObj",roleMgr.getRole(key));
		}else{
			model.addAttribute("user",userMgr.loadUserById(key));
		}
	}
	//处理角色功能权限
	private String getGnqxUrl(String type){
		if(null!=type && type.equals("role")){
			return "/management/role/menuqx";
		}
		return "/management/user/menuqx";
	}
	
	
		
	
	@RequestMapping("/menu/{MENU_BH}")
	public String queryMenu(@PathVariable("MENU_BH") String menu_bh,@RequestParam String type,@RequestParam int js,@RequestParam String obj_id,Model model) {
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
        String ywxtId = request.getParameter("ywxtId");
        
		model.addAttribute("parentMenu",menu_bh);
		model.addAttribute("js",js);
		model.addAttribute("ywxtId",ywxtId);

		model.addAttribute("SubMenu",qxglMgr.querySubMenu(ywxtId,type,obj_id, js, menu_bh));
		this.setObjAttribute(type, obj_id, model);
		return getRedirectPath(type);
	}
	
	@RequestMapping("/gnqx")
	public String queryGnqx(@RequestParam String type,@RequestParam String USER_ID,Model model) {
		JParamObject PO = JParamObject.Create();
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		EFDataSet usgnDataSet = null;
        String F_XTBH = request.getParameter("F_XTBH");
        PO.SetValueByParamName("F_ZGBH", USER_ID);
        PO.SetValueByParamName("F_XTBH", F_XTBH);
        usgnDataSet = ywxtMgr.queryUSGN(PO);
		model.addAttribute("usgn",usgnDataSet);
		model.addAttribute("F_XTBH",F_XTBH);
		setGnqxObj(type,USER_ID,model);//
		return "/management/user/gnqxlist";
	}
	
	/**
	 * 设置请求数据
	 * @param type
	 * @param obj_id
	 * @param model
	 */
	private void setObjAttribute(String type,String obj_id,Model model){
		if(null!=type&&type.equals("role")){
			model.addAttribute("roleObj", roleMgr.getRole(obj_id));
		}else{
			model.addAttribute("user",userMgr.loadUserById(obj_id));
		}
	}
	/**
	 * 获取跳转路径
	 * @param type
	 * @return
	 */
	private String getRedirectPath(String type){
		if(null!=type&&type.equals("role")){
			return "/management/role/menulist";
		}
		return "/management/user/menulist";
	}
	@RequestMapping("/saveUserGn/{obj_id}")
	public ModelAndView saveUserGn(@PathVariable("obj_id") String obj_id,@RequestParam String ids, Model model) {
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
        String type = request.getParameter("type");
        String ywxtId = request.getParameter("ywxtId");
        if(null!=type&&type.equals("role")){
    		qxglMgr.saveRoleGnqx(ywxtId,obj_id, ids);
        }else{
    		qxglMgr.saveUserGnqx(ywxtId,obj_id,ids);
        }
		ModelAndView modelView = ajaxDoneSuccess("批量授权成功！");
		MappingJacksonJsonView view = (MappingJacksonJsonView)modelView.getView();
		Map attributes = view.getAttributesMap();
		attributes.put("ywxtId", ywxtId);
		String forwardUrl = attributes.get("forwardUrl").toString();
		forwardUrl += "&obj_id="+obj_id;
		forwardUrl +="&type="+type;
        forwardUrl +="&ywxtId="+ywxtId;
		attributes.put("forwardUrl", forwardUrl);
		return modelView;
	}
	
	@RequestMapping("/saveUSGN")
	public ModelAndView saveUSGN(@RequestParam("USER_ID") String USER_ID, @RequestParam String ids, Model model) {
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
        String type = request.getParameter("type");
        String F_XTBH = request.getParameter("F_XTBH");
        JParamObject PO = JParamObject.Create();
        JResponseObject RO = null;
        ModelAndView modelView = null;
        	
        PO.SetValueByParamName("F_GNBHS", ids);
        PO.SetValueByParamName("USER_ID", USER_ID);
        PO.SetValueByParamName("F_SH", "1");
        
        if(null!=type&&type.equals("role")){
    		qxglMgr.saveRoleGnqx(F_XTBH,USER_ID, ids);
        }else{
        	RO = qxglMgr.saveUSGN(PO);
        	if(RO != null && RO.getErrorCode() == 1) {
        		modelView = ajaxDoneSuccess("批量授权成功！");
        	} else {
        		modelView = ajaxDoneSuccess("批量授权失败！");
        	}
        }
		MappingJacksonJsonView view = (MappingJacksonJsonView)modelView.getView();
		Map attributes = view.getAttributesMap();
		attributes.put("F_XTBH", F_XTBH);
		String forwardUrl = attributes.get("forwardUrl").toString();
		forwardUrl += "?USER_ID="+USER_ID;
		forwardUrl +="&type="+type;
        forwardUrl +="&F_XTBH="+F_XTBH;
		attributes.put("forwardUrl", forwardUrl);
		return modelView;
	}
	
	@RequestMapping("/delUserGn/{obj_id}")
	public ModelAndView delUserGn(@PathVariable("obj_id") String obj_id,@RequestParam String ids, Model model) {
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
        String type = request.getParameter("type");
        String ywxtId = request.getParameter("ywxtId");
        if(null!=type&&type.equals("role")){
        	qxglMgr.delRoleGnqx(ywxtId,obj_id, ids);
        }else{
    		qxglMgr.delUserGnqx(ywxtId,obj_id,ids);
        }
		ModelAndView modelView = ajaxDoneSuccess("取消批量授权成功！");
		MappingJacksonJsonView view = (MappingJacksonJsonView)modelView.getView();
		Map attributes = view.getAttributesMap();
		if(attributes.get("forwardUrl") != null) {
			String forwardUrl = attributes.get("forwardUrl").toString();
			forwardUrl += "&obj_id="+obj_id;
	        forwardUrl +="&type="+type;
	        forwardUrl +="&ywxtId="+ywxtId;
			attributes.put("forwardUrl", forwardUrl);
		}else{
			attributes.put("ywxtId", ywxtId);
		}
		return modelView;
	}
	
	@RequestMapping("/deleteUSGN")
	public ModelAndView deleteUSGN(@RequestParam("USER_ID") String USER_ID, @RequestParam String ids, Model model) {
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
        String type = request.getParameter("type");
        String F_XTBH = request.getParameter("F_XTBH");
        JParamObject PO = JParamObject.Create();
        JResponseObject RO = null;
        ModelAndView modelView = null;
        	
        PO.SetValueByParamName("F_GNBHS", ids);
        PO.SetValueByParamName("USER_ID", USER_ID);
        PO.SetValueByParamName("F_SH", "0");
        
        if(null!=type&&type.equals("role")){
    		qxglMgr.saveRoleGnqx(F_XTBH,USER_ID, ids);
        }else{
        	RO = qxglMgr.saveUSGN(PO);
        	if(RO != null && RO.getErrorCode() == 1) {
        		modelView = ajaxDoneSuccess("取消批量授权成功！");
        	} else {
        		modelView = ajaxDoneSuccess("取消批量授权失败！");
        	}
        }
		MappingJacksonJsonView view = (MappingJacksonJsonView)modelView.getView();
		Map attributes = view.getAttributesMap();
		attributes.put("F_XTBH", F_XTBH);
		String forwardUrl = attributes.get("forwardUrl").toString();
		forwardUrl += "?USER_ID="+USER_ID;
		forwardUrl +="&type="+type;
        forwardUrl +="&F_XTBH="+F_XTBH;
		attributes.put("forwardUrl", forwardUrl);
		return modelView;
	}
	
	@RequestMapping("/search")
	public ModelAndView searchMenu(@PathVariable("USER_ID") String USER_ID,@RequestParam String ids, Model model) {
//		qxglMgr.delUserGnqx(USER_ID,ids);
		ModelAndView mav = ajaxDoneSuccess(getMessage("msg.operation.success"));
		return mav;
	}
	@RequestMapping("/change")
	public String ywxtChange(@RequestParam String ywxtId,@RequestParam String obj_id, Model model) {
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		String qxType = request.getParameter("type");
		
		List<SYSYwxt> ywxtList = null;
		List<Map> menuList = null;
		ywxtList = ywxtMgr.searchYwxt(1, 100);
		SYSYwxt ywxt = ywxtMgr.getYwxt(ywxtId);
		if(null!=ywxt){
			menuList = qxglMgr.queryAllMenu(ywxt.getAPP_ID());
		}
		model.addAttribute("ywxtList",ywxtList);
		setYwxtChangeAttr(qxType,obj_id,model);
		model.addAttribute("menuList",menuList);
		model.addAttribute("ywxtObject",ywxt);
		return getYwxtChangeRedirect(qxType);
	}
	private void setYwxtChangeAttr(String type,String obj_id,Model model){
		if(null!=type&&type.equals("role")){
			model.addAttribute("roleObj",roleMgr.getRole(obj_id));
		}else{
			model.addAttribute("user",userMgr.loadUserById(obj_id));
		}
	}
	private String getYwxtChangeRedirect(String type){
		if(null!=type&&type.equals("role")){
			return "/management/role/menuqx";
		}else{
			return "/management/user/menuqx";
		}
	}
	@RequestMapping("/saveRoleUserGx")
	public ModelAndView saveRoleUserGx(HttpServletRequest request, Model model) {
		String userIds = request.getParameter("selectedIds");
		String roleId = request.getParameter("roleid");

		if(null!=userIds){
			qxglMgr.saveRoleUs(roleId, userIds);
		}
		ModelAndView mav = ajaxDoneSuccess(getMessage("msg.operation.success"));
		return mav;
	}
	@RequestMapping("/delRoleUserGx")
	public ModelAndView delRoleUserGx(HttpServletRequest request, Model model) {
		String userIds = request.getParameter("userid");
		String roleId = request.getParameter("roleid");
		if(null!=userIds){
			qxglMgr.deleteRoleUs(roleId, userIds);
		}
		ModelAndView mav = ajaxDoneSuccess(getMessage("msg.operation.success"));
		return mav;
	}
	
	
}
