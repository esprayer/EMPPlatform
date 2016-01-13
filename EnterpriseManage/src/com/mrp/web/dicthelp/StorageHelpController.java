package com.mrp.web.dicthelp;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mrp.biz.dicthelp.StorageDictHelpServiceMgr;

import dwz.web.BaseController;

import esyt.framework.com.builder.base.data.EFDataSet;

@Controller
@RequestMapping(value="/storageDailyBusiness")
public class StorageHelpController extends BaseController{

	@Autowired
	private StorageDictHelpServiceMgr storageDictHelpServiceMgr;
	
	/**
	 * 入库单批量添加项目编号帮助
	 * @param F_XMBH
	 * @param model
	 * @return
	 */
	@RequestMapping("/projectHelp")
	public String porjectHelp(@RequestParam String F_XMBH, @RequestParam String F_CLBH, Model model) {
		String F_YEAR = "";
		Calendar cal = Calendar.getInstance();
		java.util.Date currTime = new java.util.Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat formatter1 = new SimpleDateFormat("yyyyMMdd");
		F_YEAR = String.valueOf(cal.get(Calendar.YEAR));
		
		EFDataSet projectList = storageDictHelpServiceMgr.searchHYXMZD(F_YEAR + "0101", formatter1.format(currTime), F_XMBH, F_CLBH, 0, 100);
		model.addAttribute("projectList", projectList);
		model.addAttribute("beginDate", F_YEAR + "-01-01");
		model.addAttribute("endDate", formatter.format(currTime));	
		model.addAttribute("F_XMBH", F_XMBH);
		model.addAttribute("F_CLBH", F_CLBH);
		return "/mrp/dailyBusiness/storage/help/projectHelp";
	}
}
