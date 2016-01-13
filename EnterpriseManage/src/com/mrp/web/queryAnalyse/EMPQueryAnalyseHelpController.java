package com.mrp.web.queryAnalyse;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mrp.biz.dicthelp.DictHelpServiceMgr;
import com.mrp.biz.sysconfigure.sysConfigureServiceMgr;
import com.mrp.persistence.sysConfigure.project.bean.HYXMZD;
import dwz.web.BaseController;
import esyt.framework.com.builder.base.data.EFDataSet;

@Controller
@RequestMapping(value="/queryAnalyse/help")
public class EMPQueryAnalyseHelpController extends BaseController{
	@Autowired
	private DictHelpServiceMgr dictHelpMgr;
	
	@Autowired
	private sysConfigureServiceMgr        sysConfigureMgr;
	
	@Autowired
	public HttpSession session;

	/**
	 * 项目查询
	 * @param F_CKBH
	 * @param model
	 * @return
	 */
	@RequestMapping("/project")
	public String projectHelp(@RequestParam String F_XMBH, @RequestParam String beginDate, 
			  				  @RequestParam String endDate, @RequestParam String F_XMZT, 
			  				  Model model) {
		List<HYXMZD> projectList = sysConfigureMgr.searchHYXM(beginDate.replaceAll("-", ""), endDate.replaceAll("-", ""), F_XMBH, F_XMZT,0, 100);
		model.addAttribute("projectList", projectList);
		model.addAttribute("beginDate", beginDate);
		model.addAttribute("endDate", endDate);
		model.addAttribute("F_XMZT", F_XMZT);
		model.addAttribute("F_XMBH", F_XMBH);
		return "/mrp/queryAnalyse/help/xmHelp";
	}
	
	/**
	 * 项目产品查询
	 * @param F_CKBH
	 * @param model
	 * @return
	 */
	@RequestMapping("/product/product")
	public String productHelp(@RequestParam String F_CPBH, @RequestParam String beginDate, 
			  				  @RequestParam String endDate, Model model) {
		EFDataSet helpDataSet = dictHelpMgr.searchHYXMCP(beginDate.replaceAll("-", ""), endDate.replaceAll("-", ""), F_CPBH, 0, 100);
		model.addAttribute("helpDataSet", helpDataSet);
		model.addAttribute("beginDate", beginDate);
		model.addAttribute("endDate", endDate);
		model.addAttribute("F_CPBH", F_CPBH);
		return "/mrp/queryAnalyse/help/xmcpHelp";
	}
	

	//--------------------------------------------------------------------------------------------------
	//描述:根据仓库帮助材料
	//设计: ES
	//实现: ES
	//修改:
	//--------------------------------------------------------------------------------------------------
	@RequestMapping("/material/helpMaterial")
	public String materialHelpByDeportList(@RequestParam("beginDate") String beginDate, @RequestParam("endDate") String endDate, 
			                               @RequestParam("F_XMBH") String F_XMBH, @RequestParam("F_CLBH") String F_CLBH, 
			                               Model model) {

		if(F_XMBH.equals("{F_XMBH}")) F_XMBH = "";
		
		EFDataSet hyclList = dictHelpMgr.searchHYCLZDByHYXM(beginDate.replaceAll("-", ""), endDate.replaceAll("-", ""), F_XMBH, F_CLBH, 0, 100);
		model.addAttribute("hyclList", hyclList);
		model.addAttribute("beginDate", beginDate);
		model.addAttribute("endDate", endDate);
		model.addAttribute("F_XMBH", F_XMBH);
		model.addAttribute("F_CLBH", F_CLBH);
		return "/mrp/queryAnalyse/help/xmclHelp";
	}
	
	//--------------------------------------------------------------------------------------------------
	//描述:根据项目申请日期帮助材料
	//设计: ES
	//实现: ES
	//修改:
	//--------------------------------------------------------------------------------------------------
	@RequestMapping("/material/helpByProjectDate")
	public String materialHelpByProjectDate(@RequestParam("beginDate") String beginDate, @RequestParam("endDate") String endDate, 
			                                @RequestParam("F_CLBH") String F_CLBH, Model model) {
		EFDataSet hyclList = dictHelpMgr.searchHYCLZD(beginDate.replaceAll("-", ""), endDate.replaceAll("-", ""), F_CLBH, 0, 100);
		model.addAttribute("hyclList", hyclList);
		model.addAttribute("beginDate", beginDate);
		model.addAttribute("endDate", endDate);
		model.addAttribute("F_CLBH", F_CLBH);
		return "/mrp/queryAnalyse/help/xmclHelp1";
	}
}
