package com.dms.web.publicDocument;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.dms.biz.docVersion.DocVersionServiceMgr;
import com.dms.biz.document.DocServiceMgr;
import com.dms.persistence.version.bean.DMS_DOCVERSION;

import dwz.web.BaseController;

@Controller
@RequestMapping(value="/dms/docVersion")
public class DocVersionController extends BaseController{

	@Autowired
	private DocVersionServiceMgr versionMgr;
	
	@Autowired
	private DocServiceMgr docMgr;

	@Autowired
	public HttpSession session;

	//--------------------------------------------------------------------------------------------------
	//描述:列举所有版本信息
	//设计: ES
	//实现: ES
	//修改:
	//--------------------------------------------------------------------------------------------------
	@RequestMapping("/loadAllVersion")
	public String loadAllVersion(@RequestParam String FOLDER_ID, 
								 @RequestParam String NODETYPE,  
								 @RequestParam String F_DOCID,  
								 @RequestParam String realPath, 
			 					 Model model) {
		
		List<DMS_DOCVERSION> versionList = versionMgr.loadDocVersion(F_DOCID);
		model.addAttribute("FOLDER_ID", FOLDER_ID);
		model.addAttribute("F_DOCID", F_DOCID);
		model.addAttribute("NODETYPE", NODETYPE);
		model.addAttribute("realPath", realPath);
		model.addAttribute("versionList", versionList);
		return "/dms/document/docVersionManagement";
	}
	
	//--------------------------------------------------------------------------------------------------
	//描述:导入文件选择
	//设计: ES
	//实现: ES
	//修改:
	//--------------------------------------------------------------------------------------------------
	@RequestMapping("/versionAddNote")
	public String versionAddNote(@RequestParam String F_DOCID, 
			 					 @RequestParam String curPath, 
			 					 @RequestParam String F_PARENT,  
			 					 @RequestParam String F_TYPE, 
			 					 Model model) {
		model.addAttribute("F_DOCID", F_DOCID);
		model.addAttribute("curPath", curPath);
		model.addAttribute("F_PARENT", F_PARENT);
		model.addAttribute("F_TYPE", F_TYPE);
		return "/dms/document/docCommentsAdd";
	}
	
	//--------------------------------------------------------------------------------------------------
	//描述:退回至某个版本，修改说明维护
	//设计: ES
	//实现: ES
	//修改:
	//--------------------------------------------------------------------------------------------------
	@RequestMapping("/backVersion")
	public String backVersion(@RequestParam String FOLDER_ID, 
						      @RequestParam String NODETYPE,  
						      @RequestParam String F_DOCID,  
						      @RequestParam String realPath, 
						      @RequestParam String inputPath, 
						      @RequestParam String backVersion,
						      Model model) {		
		model.addAttribute("FOLDER_ID", FOLDER_ID);
		model.addAttribute("F_DOCID", F_DOCID);
		model.addAttribute("backVersion", backVersion);
		model.addAttribute("NODETYPE", NODETYPE);
		model.addAttribute("realPath", realPath);
		model.addAttribute("inputPath", inputPath);
		return "/dms/document/docVersionNoteAdd";
	}
}
