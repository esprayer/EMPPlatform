package com.efounder.form.web.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.core.xml.PackageStub;
import com.core.xml.StubObject;
import com.efounder.component.EMPComposeFormInfo;
import com.efounder.form.biz.EMPFormServiceMgr;
import com.efounder.form.persistence.formservice.bean.SYS_DBFORM;
import com.efounder.form.server.util.EMPFormServiceUtil;
import com.web.BaseController;

@Controller
@RequestMapping(value="/standard/formService")
public class EMPFormServiceController extends BaseController{
	@Autowired
	private EMPFormServiceMgr formServiceMgr;

	@Autowired
	private EMPFormServiceUtil formServiceUtil;
	
	//------------------------------------------------------------------------------------------------
	//描述: 服务表单列表
	//设计: prayer
	//实现: prayer
	//修改:
	//------------------------------------------------------------------------------------------------
	@RequestMapping("/list")
	public String metadataList(Model model) {
		List<SYS_DBFORM> dbformList = formServiceMgr.searchSYS_DBFORM(null);
		model.addAttribute("dbformList", dbformList);
		return "/standard/formService/service/list";
	}

	//------------------------------------------------------------------------------------------------
	//描述: 服务表单查询列表
	//设计: prayer
	//实现: prayer
	//修改:
	//------------------------------------------------------------------------------------------------
	@RequestMapping("/search")
	public String list(@RequestParam String BBZD_BH, Model model) {
		List<SYS_DBFORM> dbformList = formServiceMgr.searchSYS_DBFORM(BBZD_BH);
		model.addAttribute("dbformList", dbformList);
		model.addAttribute("BBZD_BH", BBZD_BH);
		return "/standard/formService/service/list";
	}
	
	//------------------------------------------------------------------------------------------------
	//描述: 服务表单添加
	//设计: prayer
	//实现: prayer
	//修改:
	//------------------------------------------------------------------------------------------------
	@RequestMapping("/add")
	public String add(Model model) {
		return "/standard/formService/service/add";
	}
	
	//------------------------------------------------------------------------------------------------
	//描述: 服务表单编辑
	//设计: prayer
	//实现: prayer
	//修改:
	//------------------------------------------------------------------------------------------------
	@RequestMapping("/edit/{BBZD_BH}")
	public String edit(@PathVariable("BBZD_BH") String BBZD_BH, Model model) {
		SYS_DBFORM dbformObject = formServiceMgr.getSYS_DBFORM(BBZD_BH);

		model.addAttribute("dbformObject", dbformObject);
		
		return "/standard/formService/service/edit";
	}
	
	//------------------------------------------------------------------------------------------------
	//描述: 服务表单添加插入
	//设计: prayer
	//实现: prayer
	//修改:
	//------------------------------------------------------------------------------------------------
	@RequestMapping(value = "/insert", method = RequestMethod.POST)
	public ModelAndView insert(SYS_DBFORM dbformObject) {
		formServiceMgr.addSYS_DBFORM(dbformObject);

		return ajaxDoneSuccess("添加成功！");
	}
	
	//------------------------------------------------------------------------------------------------
	//描述: 服务表单编辑修改
	//设计: prayer
	//实现: prayer
	//修改:
	//------------------------------------------------------------------------------------------------
	@RequestMapping(value = "/update/{BBZD_BH}", method = RequestMethod.POST)
	public ModelAndView update(@PathVariable("BBZD_BH") String BBZD_BH, SYS_DBFORM dbformObject, Model model) {
		dbformObject.setBBZD_BH(BBZD_BH);
		formServiceMgr.updSYS_DBFORM(dbformObject);
		dbformObject = formServiceMgr.getSYS_DBFORM(BBZD_BH);
		model.addAttribute("dbformObject", dbformObject);
		return ajaxDoneSuccess("修改成功！");
	}
	
	//------------------------------------------------------------------------------------------------
	//描述: 服务表单删除
	//设计: prayer
	//实现: prayer
	//修改:
	//------------------------------------------------------------------------------------------------
	@RequestMapping("/delete/{BBZD_BH}")
	public ModelAndView delete(@PathVariable("BBZD_BH") String BBZD_BH) {
		formServiceMgr.delSYS_DBFORM(BBZD_BH);

		return ajaxDoneSuccess("删除成功！");
	}
	
	//------------------------------------------------------------------------------------------------
	//描述: 服务表单 服务插件
	//设计: prayer
	//实现: prayer
	//修改:
	//------------------------------------------------------------------------------------------------
	@RequestMapping("/serviceList/{BBZD_BH}")
	public String formServiceList(HttpServletRequest request, @PathVariable("BBZD_BH") String BBZD_BH, Model model) {
		List<EMPComposeFormInfo> list = new ArrayList<EMPComposeFormInfo>();
		EMPComposeFormInfo po = null;
		StubObject stub = null;
		SYS_DBFORM dbformObject = formServiceMgr.getSYS_DBFORM(BBZD_BH);
		
		Vector vector = (Vector) PackageStub.getContenetList().get("bizservicecomponentpalettes");
		for(int i = 0; i < vector.size(); i++) {
			stub = (StubObject) vector.get(i);
			po = new EMPComposeFormInfo();
			po.setFormID(stub.getString("id", ""));
			po.setFormClass(stub.getString("compClazz", ""));
			po.setFormName(stub.getString("caption", ""));
			list.add(po);
		}
		Object object = formServiceUtil.loadFormService(request, BBZD_BH);
		if(object != null) {
			EMPComposeFormInfo formInfo = (EMPComposeFormInfo) object;
			HashMap       pluginsMap = (HashMap) formInfo.getDataContainerStub().getRefCompStubMap();
			model.addAttribute("prepare", pluginsMap.get("1"));
			model.addAttribute("execute", pluginsMap.get("2"));
			model.addAttribute("finish", pluginsMap.get("3"));
		}
		model.addAttribute("comList", list);
		model.addAttribute("BBZD_BH", dbformObject.getBBZD_BH());
		model.addAttribute("BBZD_MC", dbformObject.getBBZD_MC());
		model.addAttribute("serviceID", "{serviceID}");
		return "/standard/formService/servicePlugins/list";
	}
}
