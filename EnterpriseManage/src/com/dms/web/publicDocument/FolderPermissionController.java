package com.dms.web.publicDocument;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJacksonJsonView;

import com.dms.biz.permission.FolderPermissionServiceMgr;
import com.dms.persistence.permission.bean.DMS_FOLDER_PERMISSION;
import com.mrp.biz.sysconfigure.sysConfigureServiceMgr;
import com.mrp.persistence.sysConfigure.department.bean.HYBMZD;

import dwz.web.BaseController;
import esyt.framework.persistence.qxgl.beans.SYSUser;
import esyt.framework.service.qxgl.SYSUserServiceMgr;

@Controller
@RequestMapping(value="/dms/folderPermission")
public class FolderPermissionController extends BaseController{
	@Autowired
	private FolderPermissionServiceMgr permissionMgr;
	
	@Autowired
	private sysConfigureServiceMgr sysConfigureMgr;

	@Autowired
	private SYSUserServiceMgr userMgr;
	
	@Autowired
	public HttpSession session;

	/**
	 * 加载权限
	 * -
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("/load")
	public String permissionList(@RequestParam String SELECT_ID, 
							     @RequestParam String FOLDER_ID, 
							     Model model) {
		DMS_FOLDER_PERMISSION  permission = permissionMgr.loadFolderPermission(FOLDER_ID, SELECT_ID);
		model.addAttribute("permission", permission);
		model.addAttribute("FOLDER_ID", FOLDER_ID);
		model.addAttribute("SELECT_ID", SELECT_ID);
		return "/dms/permission/permissionEdit";
	}

	/**
	 * 加载权限
	 * @param model
	 * @return
	 */
	@RequestMapping("/createFolderPermission")
	public String createFolderPermission(@RequestParam String FOLDER_ID, Model model) {
		List<HYBMZD> hybmList = sysConfigureMgr.searchHYBMZD(0, 100);
		model.addAttribute("bmList", hybmList);
		model.addAttribute("zgList", new ArrayList());
		model.addAttribute("FOLDER_ID", FOLDER_ID);
		return "/dms/permission/permissionAdd";
	}
	
	/**
	 * 删除权限
	 * @param model
	 * @return
	 */
	@RequestMapping("/delete")
	public ModelAndView deletePermission(@RequestParam String SELECT_ID, @RequestParam String FOLDER_ID, Model model) {
		int deleteFlag = permissionMgr.deleteFolderPermission(SELECT_ID, FOLDER_ID);
		ModelAndView modelView = null;
		if(deleteFlag == -1) {
			modelView = ajaxDoneError("该权限是从上级目录继承下来的权限，不能删除！");
		} else {
			modelView = ajaxDoneSuccess("权限删除成功");
		}		
		return modelView;
	}
	
	/**
	 * 保存权限
	 * @param model
	 * @return
	 */
	@RequestMapping("/save")
	public ModelAndView savePermission(@RequestParam String SELECT_ID, 
									   @RequestParam String FOLDER_ID, 
								 	   @RequestParam String G1, 
								 	   @RequestParam String G2,
								 	   @RequestParam String G3,
								 	   @RequestParam String G4,
								 	   @RequestParam String G5,
								 	   @RequestParam String G6,
								 	   @RequestParam String G7,
								 	   @RequestParam String G8,
								 	  HttpServletRequest request,
								 	   Model model) {
		String temp = "";
		String content = "";
		SYSUser user = (SYSUser) session.getAttribute("contextUser");
		
		temp = dealWithPermission(G1, G2, G3, G4, G5, G6, G7, G8);
		
		DMS_FOLDER_PERMISSION  permission = permissionMgr.loadFolderPermission(SELECT_ID);
		
		if(!temp.equals("")) {
			if(!permission.getF_FOLDERID().equals(FOLDER_ID)) {
				if(permission.getF_USERID().equals("")) {
					content = "0;" + permission.getF_BMBH();
				} else {
					content = "1;" + permission.getF_BMBH() + ";" + permission.getF_USERID();
				}
				permission = new DMS_FOLDER_PERMISSION();	
				permission.setF_FOLDERID(FOLDER_ID);
				permission.setF_CREATOR(user.getUSER_ID());
				permission.setF_PERMISSION(temp);
				permissionMgr.insertfolderPermission(permission, content, request);			
			} else {
				permission.setF_PERMISSION(temp);
				permissionMgr.updateFolderPermission(permission);
			}
		}
		
		ModelAndView modelView = ajaxDoneSuccess("保存成功");
		MappingJacksonJsonView view = (MappingJacksonJsonView)modelView.getView();
		Map attributes = view.getAttributesMap();
		String forwardUrl = attributes.get("forwardUrl").toString();
		forwardUrl += "?FOLDER_ID=" + FOLDER_ID;
		attributes.put("forwardUrl", forwardUrl);
		return modelView;
	}
	
	/**
	 * 添加权限
	 * @param model
	 * @return
	 */
	@RequestMapping("/addFolderPermission")
	public ModelAndView addFolderPermission(@RequestParam String FOLDER_ID, 
								 	   		@RequestParam String G1, 
								 	   		@RequestParam String G2,
								 	   		@RequestParam String G3,
								 	   		@RequestParam String G4,
								 	   		@RequestParam String G5,
								 	   		@RequestParam String G6,
								 	   		@RequestParam String G7,
								 	   		@RequestParam String G8,
								 	   		@RequestParam String content,
								 	   		HttpServletRequest request,
								 	   		Model model) {
		SYSUser user = (SYSUser) session.getAttribute("contextUser");

		String temp = dealWithPermission(G1, G2, G3, G4, G5, G6, G7, G8);
		
		DMS_FOLDER_PERMISSION permission = new DMS_FOLDER_PERMISSION();	
		permission.setF_FOLDERID(FOLDER_ID);
		permission.setF_CREATOR(user.getUSER_ID());
		permission.setF_PERMISSION(temp);
		String errorMsg = permissionMgr.insertfolderPermission(permission, content, request);		
		ModelAndView modelView = null;
		if(errorMsg.trim().equals("")) {
			modelView = ajaxDoneSuccess("保存成功");
		} else {
			modelView = ajaxDoneError("保存失败：" + errorMsg);
		}
		MappingJacksonJsonView view = (MappingJacksonJsonView)modelView.getView();
		Map attributes = view.getAttributesMap();
		String forwardUrl = attributes.get("forwardUrl").toString();
		forwardUrl += "&FOLDER_ID=" + FOLDER_ID;
		attributes.put("forwardUrl", forwardUrl);
		
		if(!errorMsg.trim().equals("")) {
			attributes.put("callbackType", "");
		}
		
		return modelView;
	}
	
	/**
	 * 处理权限
	 * @param G1
	 * @param G2
	 * @param G3
	 * @param G4
	 * @param G5
	 * @param G6
	 * @param G7
	 * @param G8
	 * @return
	 */
	private String dealWithPermission(String G1, String G2, String G3, String G4,
 	   								  String G5, String G6, String G7, String G8) {
		String temp = "";
		if(G1.equals(",1")) G1 = "M";
		else G1 = "";
		
		if(G2.equals(",1")) G2 = "L";
		else G2 = "";
		
		if(G3.equals(",1")) G3 = "C";
		else G3 = "";
		
		if(G4.equals(",1")) G4 = "R";
		else G4 = "";
		
		if(G5.equals(",1")) G5 = "D";
		else G5 = "";
		
		if(G6.equals(",1")) G6 = "P";
		else G6 = "";
		
		if(G7.equals(",1")) G7 = "U";
		else G7 = "";
		
		if(G8.equals(",1")) G8 = "E";
		else G8 = "";
		
		// 如果具有删除打印权限，则默认具有浏览权限
		temp = G3 + G4 + G5 + G6 + G7 + G8;
		if (!temp.equals("") && G2.equals(""))
			G2 = "L";

		// 如果有创建权限，则默认具有浏览、阅读和编辑权限
		if (G3.equals(",1")) {
			G2 = "L";
			G4 = "R";
			G8 = "E";
		}
		
		//如果具有编辑权限，则默认具有阅读权限
		if (G8.equals(",1")) {
			G4 = "R";
		}
		
		//如果具有管理权限，则默认具有所有权限
		if(!G1.equals("")) {
			G2 = "L";
			G3 = "C";
			G4 = "R";
			G5 = "D";
			G6 = "P";
			G7 = "U";
			G8 = "E";
		}
		return G1 + G2 + G3 + G4 + G5 + G6 + G7 + G8;
	}
	
	/**
	 * 加载文档权限
	 * @param model
	 * @return
	 */
	@RequestMapping("/folderPermissionList")
	public String folderPermissionList(@RequestParam String FOLDER_ID, Model model) {
		List<DMS_FOLDER_PERMISSION>  permission = permissionMgr.folderPermissionList(FOLDER_ID, "", "");
		model.addAttribute("permission", permission);
		model.addAttribute("FOLDER_ID", FOLDER_ID);
		return "/dms/permission/folderPermissionList";
	}
	
	@RequestMapping("/bmzgList")
	public String bmzgList(@RequestParam String F_BMBH, Model model) {
		List<SYSUser> userList = null;
		if(F_BMBH.equals("all")) {
			userList = new ArrayList();
		} else {
			userList = userMgr.searchUser(F_BMBH, 0, 100);
		}
		HYBMZD bmzd = sysConfigureMgr.getHYBM(F_BMBH);
		model.addAttribute("userList", userList);
		model.addAttribute("F_BMBH", F_BMBH);
		model.addAttribute("F_BMMC", bmzd.getF_BMMC());
		return "/dms/permission/zgList";
	}
	
	@RequestMapping("/bmList")
	public String bmzgList(Model model) {
		List<HYBMZD> hybmList = sysConfigureMgr.searchHYBMZD(0, 100);
		model.addAttribute("bmList", hybmList);
		return "/dms/permission/bmList";
	}
}