package com.web;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.business.MetadataObject;
import com.business.MetadataServiceMgr;
import com.web.BaseController;

@Controller
@RequestMapping(value="/standard/form")
public class FormController extends BaseController{
	@RequestMapping("/formOpen")
	public String formOpen(@RequestParam String navTabId, @RequestParam String action, @RequestParam String title, Model model) {
		Calendar              cal = Calendar.getInstance();
		int                  year = cal.get(Calendar.YEAR);
		int                 month = cal.get(Calendar.MONTH) + 1;
		String[]             kjqj = new String[2];		
		List<String>     kjndList = new ArrayList<String>();
		List<String[]>   kjqjList = new ArrayList<String[]>();
		
		for(int i = year -3; i <= year; i++) {
			kjndList.add(String.valueOf(i));
		}
		
		for(int i = 1; i <= 12; i++) {
			kjqj = new String[2];
			if(i < 10) kjqj[0] = "0" + i;
			else kjqj[0] = String.valueOf(i);
			if(i == month) kjqj[1] = "1";
			else kjqj[1] = "0";
			kjqjList.add(kjqj);
		}

		model.addAttribute("kjndList", kjndList);	
		model.addAttribute("kjqjList", kjqjList);
		model.addAttribute("navTabId", navTabId);
		model.addAttribute("action", action);
		model.addAttribute("title", title);
		return "/HYQJForm";
	}
}
