package com.dms.web.publicDocument;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.dms.biz.docNote.DOCNoteServiceMgr;
import com.dms.persistence.docNote.bean.DMS_DOCNOTE;

import dwz.web.BaseController;
import esyt.framework.persistence.qxgl.beans.SYSUser;

@Controller
@RequestMapping(value="/dms/docNote")
public class DocNoteController extends BaseController{
	@Autowired
	private DOCNoteServiceMgr docNoteMgr;
	
	@Autowired
	public HttpSession session;

	//--------------------------------------------------------------------------------------------------
	//描述:添加评论
	//设计: ES
	//实现: ES
	//修改:
	//--------------------------------------------------------------------------------------------------
	@RequestMapping("/add")
	public String docNoteAdd(@RequestParam("F_DOCID") String F_DOCID, Model model) {
		model.addAttribute("F_DOCID", F_DOCID);
		return "/dms/document/docCommentsAdd";
	}
	
	//--------------------------------------------------------------------------------------------------
	//描述:编辑评论
	//设计: ES
	//实现: ES
	//修改:
	//--------------------------------------------------------------------------------------------------
	@RequestMapping("/edit")
	public String docNoteEdit(@RequestParam("F_ID") String F_ID, @RequestParam("F_DOCID") String F_DOCID, Model model) {
		DMS_DOCNOTE	note = docNoteMgr.loadDocNote(F_ID);
		model.addAttribute("note", note);
		model.addAttribute("F_ID", F_ID);
		model.addAttribute("F_DOCID", F_DOCID);
		return "/dms/document/docCommentsEdit";
	}
	
	//--------------------------------------------------------------------------------------------------
	//描述:删除评论
	//设计: ES
	//实现: ES
	//修改:
	//--------------------------------------------------------------------------------------------------
	@RequestMapping("/delete")
	public ModelAndView deleteNote(@RequestParam String F_ID, Model model) {
		docNoteMgr.delteDocNote(F_ID);
		return ajaxDoneSuccess("删除成功");
	}
	
	//--------------------------------------------------------------------------------------------------
	//描述:加载评论
	//设计: ES
	//实现: ES
	//修改:
	//--------------------------------------------------------------------------------------------------
	@RequestMapping("/load")
	public String loadNote(@RequestParam String F_DOCID, Model model) {
		SYSUser user = (SYSUser) session.getAttribute("contextUser");
		List<DMS_DOCNOTE> noteList = docNoteMgr.docNoteList(F_DOCID,user.getUSER_ID());
		model.addAttribute("noteList", noteList);
		model.addAttribute("F_DOCID", F_DOCID);
		model.addAttribute("USER_ID", user.getUSER_ID());
		return "/dms/document/docComments";
	}
	
	//--------------------------------------------------------------------------------------------------
	//描述:保存评论
	//设计: ES
	//实现: ES
	//修改:
	//--------------------------------------------------------------------------------------------------
	@RequestMapping("/save")
	public ModelAndView saveNote(DMS_DOCNOTE docNote, Model model) {
		SYSUser user = (SYSUser) session.getAttribute("contextUser");
		docNote.setF_WRITER(user.getUSER_ID());		
		docNoteMgr.updateDocNote(docNote);
		return ajaxDoneSuccess("保存成功");
	}
	
	//--------------------------------------------------------------------------------------------------
	//描述:保存评论
	//设计: ES
	//实现: ES
	//修改:
	//--------------------------------------------------------------------------------------------------
	@RequestMapping("/insert")
	public ModelAndView insertNote(@RequestParam String F_DOCID, @RequestParam String F_MS, DMS_DOCNOTE docNote, Model model) {
		SYSUser user = (SYSUser) session.getAttribute("contextUser");
		docNote.setF_WRITER(user.getUSER_ID());		
		docNoteMgr.insertDocNote(docNote);
		return ajaxDoneSuccess("保存成功");
	}
}
