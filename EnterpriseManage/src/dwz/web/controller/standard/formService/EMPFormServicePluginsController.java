package dwz.web.controller.standard.formService;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.etsoft.server.EMPFormServiceUtil;

import dwz.business.standard.formService.EMPFormServiceMgr;
import dwz.persistence.formservice.bean.SYS_DBFORM;
import dwz.web.BaseController;
import esyt.framework.com.component.EMPComponentStub;
import esyt.framework.com.component.EMPComposeFormInfo;
import esyt.framework.com.core.xml.PackageStub;
import esyt.framework.com.core.xml.StubObject;


@Controller
@RequestMapping(value="/standard/formService/plugins")
public class EMPFormServicePluginsController extends BaseController{
	@Autowired
	private EMPFormServiceMgr formServiceMgr;
	
	@Autowired
	private EMPFormServiceUtil formServiceUtil;
	//------------------------------------------------------------------------------------------------
	//描述: 服务表单添加插入
	//设计: prayer
	//实现: prayer
	//修改:
	//------------------------------------------------------------------------------------------------
	@RequestMapping("/serviceList/{BBZD_BH}")
	public String formServicePluginsList(HttpServletRequest request, @PathVariable("BBZD_BH") String BBZD_BH, @RequestParam("serviceID") String serviceID, Model model) {
		SYS_DBFORM dbformObject = formServiceMgr.getSYS_DBFORM(BBZD_BH);
		Object object = formServiceUtil.loadFormService(request, BBZD_BH);
		if(object != null) {
			EMPComposeFormInfo formInfo = (EMPComposeFormInfo) object;
			HashMap       pluginsMap = (HashMap) formInfo.getDataContainerStub().getRefCompStubMap();
			model.addAttribute("prepare", pluginsMap.get("1"));
			model.addAttribute("execute", pluginsMap.get("2"));
			model.addAttribute("finish", pluginsMap.get("3"));
		}
		model.addAttribute("BBZD_BH", BBZD_BH);
		model.addAttribute("BBZD_MC", dbformObject.getBBZD_MC());
		model.addAttribute("serviceID", serviceID);
		return "/standard/formService/servicePlugins/pluginsList";
	}
	
	//------------------------------------------------------------------------------------------------
	//描述: 服务表单插件添加
	//设计: prayer
	//实现: prayer
	//修改:
	//------------------------------------------------------------------------------------------------
	@RequestMapping("/add/{BBZD_BH}")
	public String add(@PathVariable("BBZD_BH") String BBZD_BH, @RequestParam("serviceID") String serviceID, Model model) {
		model.addAttribute("BBZD_BH", BBZD_BH);
		model.addAttribute("serviceID", serviceID);
		return "/standard/formService/servicePlugins/add";
	}
	
	//------------------------------------------------------------------------------------------------
	//描述: 服务表单插件编辑
	//设计: prayer
	//实现: prayer
	//修改:
	//------------------------------------------------------------------------------------------------
	@RequestMapping("/edit/{compID}")
	public String edit(HttpServletRequest request, @PathVariable("compID") String compID, 
			           @RequestParam("compScope") String compScope, @RequestParam("BBZD_BH") String BBZD_BH, 
			           @RequestParam("serviceID") String serviceID, Model model) {
		EMPComponentStub stub = formServiceUtil.loadFormServicePlugins(request, BBZD_BH, compID, compScope);
		model.addAttribute("stub", stub);
		model.addAttribute("BBZD_BH", BBZD_BH);
		model.addAttribute("serviceID", serviceID);
		return "/standard/formService/servicePlugins/edit";
	}
	//------------------------------------------------------------------------------------------------
	//描述: 服务表单插件添加帮助
	//设计: prayer
	//实现: prayer
	//修改:
	//------------------------------------------------------------------------------------------------
	@RequestMapping("/servicePluginsListHelp/{serviceID}")
	public String servicePluginsListHelp(@PathVariable("serviceID") String serviceID, Model model) {
		Vector vector = (Vector) PackageStub.getContenetList().get(serviceID.toLowerCase());
		StubObject stub = null;
		EMPComponentStub po = null;
		List<EMPComponentStub> list = new ArrayList<EMPComponentStub>();
		
		for(int i = 0; i < vector.size(); i++) {
			stub = (StubObject) vector.get(i);
			po = new EMPComponentStub();
			po.setCompID(stub.getString("id", ""));
			po.setCompName(stub.getString("caption", ""));
			po.setCompClazz(stub.getString("modelClazz", ""));
			list.add(po);
		}
		model.addAttribute("serviceID", serviceID);
		model.addAttribute("pluginsList", list);
		return "/standard/formService/servicePlugins/pluginsHelp";
	}
	
	//------------------------------------------------------------------------------------------------
	//描述: 服务表单插件添加保存
	//设计: prayer
	//实现: prayer
	//修改:
	//------------------------------------------------------------------------------------------------
	@RequestMapping("/servicePlugins/insert/{BBZD_BH}")
	public ModelAndView servicePluginsInsert(HttpServletRequest request, @PathVariable("BBZD_BH") String BBZD_BH, @RequestParam("serviceID") String serviceID, EMPComponentStub po, Model model) {
		boolean bSuc = formServiceUtil.saveFormService(request, po, BBZD_BH);
		model.addAttribute("serviceID", serviceID);
		return ajaxDoneSuccess("添加成功！");
	}
	
	//------------------------------------------------------------------------------------------------
	//描述: 服务表单插件编辑保存
	//设计: prayer
	//实现: prayer
	//修改:
	//------------------------------------------------------------------------------------------------
	@RequestMapping("/servicePlugins/update/{BBZD_BH}")
	public ModelAndView servicePluginsUpdate(HttpServletRequest request, @PathVariable("BBZD_BH") String BBZD_BH, @RequestParam("serviceID") String serviceID, EMPComponentStub po, Model model) {
		boolean bSuc = formServiceUtil.saveFormService(request, po, BBZD_BH);
		model.addAttribute("serviceID", serviceID);
		return ajaxDoneSuccess("添加成功！");
	}
	
	//------------------------------------------------------------------------------------------------
	//描述: 服务表单插件删除
	//设计: prayer
	//实现: prayer
	//修改:
	//------------------------------------------------------------------------------------------------
	@RequestMapping("/servicePlugins/delete/{compID}")
	public ModelAndView servicePluginsDelete(HttpServletRequest request, @PathVariable("compID") String compID, 
								             @RequestParam("compScope") String compScope, @RequestParam("BBZD_BH") String BBZD_BH, 
								             @RequestParam("serviceID") String serviceID, Model model) {
		boolean bSuc = formServiceUtil.deleteFormServicePlugins(request, BBZD_BH, compID, compScope);
		model.addAttribute("serviceID", serviceID);
		return ajaxDoneSuccess("删除成功！");
	}
	
	//------------------------------------------------------------------------------------------------
	//描述: 服务表单插件移动
	//设计: prayer
	//实现: prayer
	//修改:
	//------------------------------------------------------------------------------------------------
	@RequestMapping("/servicePlugins/move/{compID}")
	public ModelAndView servicePluginsMove(HttpServletRequest request, @PathVariable("compID") String compID, 
								           @RequestParam("compScope") String compScope, @RequestParam("BBZD_BH") String BBZD_BH, 
								           @RequestParam("serviceID") String serviceID, @RequestParam("order") String order, Model model) {
		boolean bSuc = formServiceUtil.saveFormService(request, BBZD_BH, compID, compScope, order);
		model.addAttribute("serviceID", serviceID);
		return ajaxDoneSuccess("移动成功！");
	}
}
