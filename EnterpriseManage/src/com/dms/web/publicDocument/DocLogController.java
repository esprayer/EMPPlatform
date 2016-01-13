package com.dms.web.publicDocument;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.dms.biz.docLogger.DOCLOGServiceMgr;
import com.dms.persistence.docLogger.bean.DMS_DOCLOG;

import dwz.web.BaseController;

@Controller
@RequestMapping(value="/dms/docLog")
public class DocLogController extends BaseController{

	@Autowired
	private DOCLOGServiceMgr docLogMgr;

	@Autowired
	public HttpSession session;

	//--------------------------------------------------------------------------------------------------
	//描述:查看文件入职
	//设计: ES
	//实现: ES
	//修改:
	//--------------------------------------------------------------------------------------------------
	@RequestMapping("/docLogList")
	public String docLogList(@RequestParam String F_DOCID, Model model) {
		List<DMS_DOCLOG> docLogList = docLogMgr.DOCLogList(F_DOCID, null);		
		model.addAttribute("docLogList", docLogList);
		return "/dms/document/docAudit";
	}
}
