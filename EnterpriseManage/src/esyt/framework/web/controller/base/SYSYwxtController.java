package esyt.framework.web.controller.base;

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
import esyt.framework.persistence.qxgl.beans.SYSYwxt;
import esyt.framework.service.base.SYSYwxtServiceMgr;

@Controller
@RequestMapping(value="/management/base/ywxt")
public class SYSYwxtController extends BaseController{
	@Autowired
	private SYSYwxtServiceMgr ywxtMgr;
	
	@RequestMapping("")
	public String ywxtList(Model model) {
		List<SYSYwxt> ywxtList = ywxtMgr.searchYwxt(0, 0);
		model.addAttribute("ywxtList", ywxtList);
		return "/management/ywxt/list";
	}
	
	@RequestMapping("/search")
	public String ywxtList(@RequestParam String keywords, Model model) {
		List<SYSYwxt> ywxtList = ywxtMgr.searchYwxt(keywords, 0, 100);
		model.addAttribute("ywxtList", ywxtList);
		return "/management/ywxt/list";
	}
	
	@RequestMapping("/add")
	public String addYwxt(Model model) {
		return "/management/ywxt/add";
	}
	@RequestMapping("/edit/{ywxtId}")
	public String ywxtEdit(@PathVariable("ywxtId") String ywxtId, Model model) {
		SYSYwxt ywxtObject = ywxtMgr.getYwxt(ywxtId);
		model.addAttribute("ywxtObject", ywxtObject);
		return "/management/ywxt/edit";
	}
	@RequestMapping(value = "/insert", method = RequestMethod.POST)
	public ModelAndView insertYwxt(SYSYwxt sysYwxt){
		ywxtMgr.addYwxt(sysYwxt);
		return ajaxDoneSuccess(getMessage("msg.operation.success"));
	}
	
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public ModelAndView ywxtUpdate(SYSYwxt sysYwxt) {
		ywxtMgr.updateYwxt(sysYwxt);
		return ajaxDoneSuccess(getMessage("msg.operation.success"));
	}
	
	@RequestMapping("/delete/{ywxtId}")
	public ModelAndView ywxtDelete(@PathVariable("ywxtId") String ywxtId) {
		ywxtMgr.delYwxt(ywxtId);
		return ajaxDoneSuccess(getMessage("msg.operation.success"));
	}
}
