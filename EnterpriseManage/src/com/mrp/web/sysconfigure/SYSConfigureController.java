package com.mrp.web.sysconfigure;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.mrp.biz.sysconfigure.sysConfigureServiceMgr;
import com.mrp.persistence.sysConfigure.company.bean.HYCSZD;
import com.mrp.persistence.sysConfigure.department.bean.HYBMZD;
import com.mrp.persistence.sysConfigure.deport.bean.HYCKZD;
import com.mrp.persistence.sysConfigure.material.bean.HYCLZD;
import com.mrp.persistence.sysConfigure.product.bean.HYCPZD;
import com.mrp.persistence.sysConfigure.project.bean.HYXMZD;
import com.mrp.persistence.sysConfigure.projectProduct.bean.HYXMCP;
import com.mrp.persistence.sysConfigure.suppliers.bean.HYDWZD;
import com.mrp.persistence.sysConfigure.user.bean.HYZGZD;
import dwz.web.BaseController;
import esyt.framework.persistence.qxgl.beans.SYSUser;
import esyt.framework.service.qxgl.SYSUserServiceMgr;

@Controller
@RequestMapping(value="/sysConfigure")
public class SYSConfigureController extends BaseController{
	@Autowired
	private sysConfigureServiceMgr sysConfigureMgr;
	
	@Autowired
	private SYSUserServiceMgr userMgr;
	
	@Autowired
	public HttpSession session;

	/**
	 * 部门
	 * @param model
	 * @return
	 */
	@RequestMapping("/department")
	public String departmentList(Model model) {
		List<HYBMZD> hybmList = sysConfigureMgr.searchHYBMZD(0, 100);
		model.addAttribute("hybmList", hybmList);
		return "/mrp/sysConfigure/department/list";
	}

	@RequestMapping("/department/search")
	public String departmentList(@RequestParam String keywords, Model model) {
		List<HYBMZD> hybmList = sysConfigureMgr.searchHYBMZD(keywords, 0, 100);
		model.addAttribute("hybmList", hybmList);
		return "/mrp/sysConfigure/department/list";
	}
	
	@RequestMapping("/department/add")
	public String departmentAdd(Model model) {
		return "/mrp/sysConfigure/department/add";
	}

	@RequestMapping("/department/edit/{F_BMBH}")
	public String departmentEdit(@PathVariable("F_BMBH") String F_BMBH, Model model) {
		HYBMZD hybmObject = sysConfigureMgr.getHYBM(F_BMBH);
		model.addAttribute("hybmObject", hybmObject);
		return "/mrp/sysConfigure/department/edit";
	}
	
	@RequestMapping(value = "/department/insert", method = RequestMethod.POST)
	public ModelAndView departmentInsert(HYBMZD hybmObject) {
		HYBMZD po = sysConfigureMgr.getHYBM(hybmObject.getF_BMBH());
		if(po != null) {
			return ajaxDoneError("部门编号【" + hybmObject.getF_BMBH() + "]已经存在，请重新维护一个部门编号！");
		}
		sysConfigureMgr.addHYBM(hybmObject);
		return ajaxDoneSuccess("部门" + hybmObject.getF_BMMC() + "【" + hybmObject.getF_BMBH() + "]新建成功！");
	}
	
	@RequestMapping(value = "/department/update/{F_BMBH}", method = RequestMethod.POST)
	public ModelAndView departmentUpdate(@PathVariable("F_BMBH") String F_BMBH, HYBMZD hybmObject, Model model) {
		List<SYSUser> userList = null;
		
		if(hybmObject.getF_SYZT() == 0) {
			userList = userMgr.checkUserByBm(F_BMBH, "1");
			if(userList.size() > 0) {
				return ajaxDoneError("部门" + hybmObject.getF_BMMC() + "【" + F_BMBH + "】包含用户，所以该部门不允许停用！");
			}
		}

		hybmObject.setF_BMBH(F_BMBH);
		sysConfigureMgr.updateHYBM(hybmObject);
		hybmObject = sysConfigureMgr.getHYBM(F_BMBH);
		model.addAttribute("hybmObject", hybmObject);
		return ajaxDoneSuccess("部门" + hybmObject.getF_BMMC() + "【" + hybmObject.getF_BMBH() + "】保存成功！");
	}
	
	@RequestMapping("/department/delete/{F_BMBH}")
	public ModelAndView departmentDelete(@PathVariable("F_BMBH") String F_BMBH) {
		sysConfigureMgr.delHYBM(F_BMBH);
		return ajaxDoneSuccess("部门" + F_BMBH + "删除成功！");
	}
	
	
	
	
	
	
	
	
	/**
	 * 厂商
	 * @param model
	 * @return
	 */
	@RequestMapping("/companys")
	public String companysList(Model model) {
		List<HYCSZD> hycsList = sysConfigureMgr.searchHYCSZD("", "", 0, 100);
		model.addAttribute("hycsList", hycsList);
		return "/mrp/sysConfigure/companys/list";
	}

	@RequestMapping("/companys/search")
	public String companysList(@RequestParam String F_CSBH, Model model) {
		List<HYCSZD> hycsList = sysConfigureMgr.searchHYCSZD(F_CSBH, "", 0, 100);
		model.addAttribute("F_CSBH", F_CSBH);
		model.addAttribute("hycsList", hycsList);
		return "/mrp/sysConfigure/companys/list";
	}
	
	@RequestMapping("/companys/add")
	public String companyAdd(Model model) {
		return "/mrp/sysConfigure/companys/add";
	}

	@RequestMapping("/companys/edit/{F_CSBH}")
	public String companyEdit(@PathVariable("F_CSBH") String F_CSBH, Model model) {
		HYCSZD hycsObject = sysConfigureMgr.getHYCS(F_CSBH);
		model.addAttribute("hycsObject", hycsObject);
		return "/mrp/sysConfigure/companys/edit";
	}
	
	@RequestMapping(value = "/companys/insert", method = RequestMethod.POST)
	public ModelAndView companyInsert(HYCSZD hycsObject) {
		HYCSZD po = sysConfigureMgr.getHYCS(hycsObject.getF_CSBH());
		if(po != null) return ajaxDoneError(hycsObject.getF_CSBH() + "【 " + hycsObject.getF_CSMC() + "】已经存在，不允许重复添加！");
		sysConfigureMgr.addHYCS(hycsObject);
		return ajaxDoneSuccess(hycsObject.getF_CSBH() + "【 " + hycsObject.getF_CSMC() + "】厂商添加成功！");
	}
	
	@RequestMapping(value = "/companys/update/{F_CSBH}", method = RequestMethod.POST)
	public ModelAndView companyUpdate(@PathVariable("F_CSBH") String F_CSBH, HYCSZD hycsObject, Model model) {
		hycsObject.setF_CSBH(F_CSBH);
		sysConfigureMgr.updateHYCS(hycsObject);
		hycsObject = sysConfigureMgr.getHYCS(F_CSBH);
		model.addAttribute("hycsObject", hycsObject);
		return ajaxDoneSuccess(hycsObject.getF_CSBH() + "【 " + hycsObject.getF_CSMC() + "】厂商修改成功！");
	}
	
	@RequestMapping("/companys/delete/{F_CSBH}")
	public ModelAndView companyDelete(@PathVariable("F_CSBH") String F_CSBH) {
		sysConfigureMgr.delHYCS(F_CSBH);
		return ajaxDoneSuccess("【 " + F_CSBH + "】厂商删除成功！");
	}
	
	
	
	/**
	 * 供应商
	 * @param model
	 * @return
	 */
	@RequestMapping("/suppliers")
	public String suppliersList(Model model) {
		List<HYDWZD> hydwList = sysConfigureMgr.searchHYDW(0, 100);
		model.addAttribute("hydwList", hydwList);
		return "/mrp/sysConfigure/suppliers/list";
	}

	@RequestMapping("/suppliers/search")
	public String suppliersList(@RequestParam String keywords, Model model) {
		List<HYDWZD> hydwList = sysConfigureMgr.searchHYDW(keywords, 0, 100);
		model.addAttribute("hydwList", hydwList);
		return "/mrp/sysConfigure/suppliers/list";
	}
	
	@RequestMapping("/suppliers/add")
	public String suppliersAdd(Model model) {
		return "/mrp/sysConfigure/suppliers/add";
	}

	@RequestMapping("/suppliers/edit/{F_DWBH}")
	public String suppliersEdit(@PathVariable("F_DWBH") String F_BMBH, Model model) {
		HYDWZD hydwObject = sysConfigureMgr.getHYDW(F_BMBH);
		model.addAttribute("hydwObject", hydwObject);
		return "/mrp/sysConfigure/suppliers/edit";
	}
	
	@RequestMapping(value = "/suppliers/insert", method = RequestMethod.POST)
	public ModelAndView suppliersInsert(HYDWZD hydwObject) {
		HYDWZD po = sysConfigureMgr.getHYDW(hydwObject.getF_DWBH());
		if(po != null) return ajaxDoneError("供应商" + po.getF_DWBH() + "【" + po.getF_DWMC() + "】已经存在，不允许重复添加！");
		sysConfigureMgr.addHYDW(hydwObject);
		return ajaxDoneSuccess("供应商【 " + hydwObject.getF_DWBH() + "】添加成功！");
	}
	
	@RequestMapping(value = "/suppliers/update/{F_DWBH}", method = RequestMethod.POST)
	public ModelAndView suppliersUpdate(@PathVariable("F_DWBH") String F_DWBH, HYDWZD hydwObject, Model model) {
		hydwObject.setF_DWBH(F_DWBH);
		sysConfigureMgr.updateHYDW(hydwObject);
		hydwObject = sysConfigureMgr.getHYDW(F_DWBH);
		model.addAttribute("hybmObject", hydwObject);
		return ajaxDoneSuccess("供应商【 " +  hydwObject.getF_DWMC() + "】修改成功！");
	}
	
	@RequestMapping("/suppliers/delete/{F_DWBH}")
	public ModelAndView suppliersDelete(@PathVariable("F_DWBH") String F_DWBH) {
		sysConfigureMgr.delHYDW(F_DWBH);
		return ajaxDoneSuccess("供应商【 " +  F_DWBH + "】删除成功！");
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * 用户
	 * @param model
	 * @return
	 */
	@RequestMapping("/user/{F_SXBM}")
	public String userList(@PathVariable("F_SXBM") String F_SXBM, Model model) {
		List<HYZGZD> userList = sysConfigureMgr.searchHYZG(F_SXBM, 0, 100);
		model.addAttribute("userList", userList);
		model.addAttribute("F_SXBM", F_SXBM);
		return "/mrp/sysConfigure/user/userList";
	}
	
	@RequestMapping("/user")
	public String userList(Model model) {
		List<HYBMZD> hybmList = sysConfigureMgr.searchHYBMZD(0, 100);
		model.addAttribute("hybmList", hybmList);
		model.addAttribute("F_SXBM", "{F_BMBH}");
		return "/mrp/sysConfigure/user/list";
	}
	
	@RequestMapping("/user/search/{F_SXBM}")
	public String userList(@PathVariable("F_SXBM") String F_SXBM, @RequestParam String keywords, Model model) {
		List<HYZGZD> userList = sysConfigureMgr.searchHYZG(F_SXBM, keywords, 0, 100);
		model.addAttribute("userList", userList);
		model.addAttribute("F_SXBM", F_SXBM);
		return "/mrp/sysConfigure/user/userList";
	}
	
	@RequestMapping("/user/add/{F_SXBM}")
	public String userAdd(@PathVariable("F_SXBM") String F_SXBM, Model model) {
		HYZGZD hyzgObject = new HYZGZD();
		hyzgObject.setF_SXBM(F_SXBM);
		model.addAttribute("hyzgObject", hyzgObject);
		return "/mrp/sysConfigure/user/add";
	}
	
	@RequestMapping("/user/insert/{F_SXBM}")
	public ModelAndView userInsert(@PathVariable("F_SXBM") String F_SXBM, HYZGZD hyzgObject, Model model) {
		HYZGZD po = sysConfigureMgr.getHYZG(null, hyzgObject.getF_ZGBH());
		
		if(po != null) {
			return ajaxDoneError("部门编号：" + po.getF_SXBM() + "<br>用户编号：" + hyzgObject.getF_ZGBH() + "<br>已经存在，不允许添加！");
		}
		
		hyzgObject.setF_SXBM(F_SXBM);
		sysConfigureMgr.addHYZG(hyzgObject);
		ModelAndView mav = ajaxDoneSuccess("部门编号：" + F_SXBM + "<br>用户编号：" + hyzgObject.getF_ZGBH() + "<br>添加成功！");
		model.addAttribute("F_SXBM",F_SXBM);
		return mav;
	}
	
	@RequestMapping("/user/edit/{F_ZGBH}")
	public String userEdit(@PathVariable("F_ZGBH") String F_ZGBH, 
			   			   @RequestParam("F_SXBM") String F_SXBM, 
			   			   Model model) {
		HYZGZD hyzgObject = sysConfigureMgr.getHYZG(F_SXBM, F_ZGBH);
		model.addAttribute("hyzgObject", hyzgObject);
		model.addAttribute("F_ZGBH", F_ZGBH);
		model.addAttribute("F_SXBM", F_SXBM);
		return "/mrp/sysConfigure/user/edit";
	}
	
	@RequestMapping(value = "/user/update/{F_ZGBH}", method = RequestMethod.POST)
	public ModelAndView userUpdate(@PathVariable("F_ZGBH") String F_ZGBH, 
			   					   @RequestParam("F_SXBM") String F_SXBM, 
			   					   HYZGZD hyzgObject, Model model) {
		hyzgObject.setF_ZGBH(F_ZGBH);
		hyzgObject.setF_SXBM(F_SXBM);
		sysConfigureMgr.updateHYZG(hyzgObject);
		return ajaxDoneSuccess("部门编号：" + F_SXBM + "<br>用户编号：" + F_ZGBH + "<br>修改成功！");
	}
	
	@RequestMapping("/user/delete/{F_ZGBH}")
	public ModelAndView userDelete(@PathVariable("F_ZGBH") String F_ZGBH, 
								   @RequestParam("F_SXBM") String F_SXBM) {
		sysConfigureMgr.delHYZG(F_SXBM, F_ZGBH);
		return ajaxDoneSuccess("部门编号：" + F_SXBM + "<br>用户编号：" + F_ZGBH + "<br>删除成功！");
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * 仓库
	 * @param model
	 * @return
	 */
	@RequestMapping("/deport/{F_BMBH}")
	public String deportList(@PathVariable("F_BMBH") String F_BMBH, Model model) {
		List<HYCKZD> deportList = sysConfigureMgr.searchHYCK(F_BMBH, 0, 100);
		model.addAttribute("deportList", deportList);
		model.addAttribute("F_BMBH", F_BMBH);
		return "/mrp/sysConfigure/deport/deportList";
	}
	
	@RequestMapping("/deport")
	public String deportList(Model model) {
		List<HYBMZD> hybmList = sysConfigureMgr.searchHYBMZD(0, 100);
		model.addAttribute("F_BMBH", "{F_BMBH}");
		model.addAttribute("hybmList", hybmList);
		return "/mrp/sysConfigure/deport/list";
	}
	
	@RequestMapping("/deport/search/{F_BMBH}")
	public String deportList(@PathVariable("F_BMBH") String F_BMBH, @RequestParam String keywords, Model model) {
		List<HYCKZD> deportList = sysConfigureMgr.searchHYCK(F_BMBH, keywords, 0, 100);
		model.addAttribute("deportList", deportList);
		model.addAttribute("F_BMBH", F_BMBH);
		return "/mrp/sysConfigure/deport/deportList";
	}
	
	@RequestMapping("/deport/add/{F_BMBH}")
	public String deportAdd(@PathVariable("F_BMBH") String F_BMBH, Model model) {
		HYBMZD hybmObject = sysConfigureMgr.getHYBM(F_BMBH);
		HYCKZD hyckObject = new HYCKZD();
		hyckObject.setF_BMBH(F_BMBH);
		hyckObject.setF_BMMC(hybmObject.getF_BMMC());
		hyckObject.setF_ZGBH("");
		hyckObject.setF_ZGMC("");
		model.addAttribute("hyckObject", hyckObject);
		return "/mrp/sysConfigure/deport/add";
	}
	
	@RequestMapping("/deport/insert/{F_BMBH}")
	public ModelAndView deportInsert(@PathVariable("F_BMBH") String F_BMBH, HYCKZD hyckObject, Model model) {
		HYCKZD po = sysConfigureMgr.getHYCKZD(hyckObject.getF_CKBH());
		
		if(po != null) {
			return ajaxDoneError("部门编号：" + po.getF_BMBH() + "<br>仓库编号：" + po.getF_CKBH() + "<br>已经存在，不允许添加！");
		}

		hyckObject.setF_BMBH(F_BMBH);
		sysConfigureMgr.addHYCK(hyckObject);
		ModelAndView mav =  ajaxDoneSuccess("部门编号：" + F_BMBH + "<br>仓库编号：" + po.getF_CKBH() + "<br>添加成功！");
		model.addAttribute("F_BMBH",F_BMBH);
		return mav;
	}
	
	@RequestMapping("/deport/edit/{F_CKBH}")
	public String deportEdit(@PathVariable("F_CKBH") String F_CKBH, 
			   			     @RequestParam("F_BMBH") String F_BMBH, 
			   			     Model model) {
		HYCKZD hyckObject = sysConfigureMgr.getHYCK(F_BMBH, F_CKBH);
		model.addAttribute("hyckObject", hyckObject);
		model.addAttribute("F_CKBH", F_CKBH);
		model.addAttribute("F_BMBH", F_BMBH);
		return "/mrp/sysConfigure/deport/edit";
	}
	
	@RequestMapping(value = "/deport/update/{F_CKBH}", method = RequestMethod.POST)
	public ModelAndView deportUpdate(@PathVariable("F_CKBH") String F_CKBH, 
			   					   @RequestParam("F_BMBH") String F_BMBH, 
			   					   HYCKZD hyckObject, Model model) {
		hyckObject.setF_CKBH(F_CKBH);
		hyckObject.setF_BMBH(F_BMBH);
		sysConfigureMgr.updateHYCK(hyckObject);
		return ajaxDoneSuccess("部门编号：" + F_BMBH + "<br>仓库编号：" + F_CKBH + "<br>修改成功！");
	}
	
	@RequestMapping("/deport/delete/{F_CKBH}")
	public ModelAndView deportDelete(@PathVariable("F_CKBH") String F_CKBH, 
								   @RequestParam("F_BMBH") String F_BMBH) {
		sysConfigureMgr.delHYCK(F_BMBH, F_CKBH);
		return ajaxDoneSuccess("部门编号：" + F_BMBH + "<br>仓库编号：" + F_CKBH + "<br>删除成功！");
	}
	
	@RequestMapping("/deport/department/{F_BMBH}")
	public String departmentUserList(@PathVariable("F_BMBH") String F_BMBH, Model model) {
		List<HYZGZD> userList = sysConfigureMgr.searchHYZG(F_BMBH, 0, 100);
		model.addAttribute("userList", userList);
		model.addAttribute("F_BMBH", F_BMBH);
		return "/help/dictHelp/zgHelp";
	}
	
	@RequestMapping("/deport/department/user/{F_BMBH}")
	public String departmentUserLists(@PathVariable("F_BMBH") String F_BMBH, @RequestParam String keywords, Model model) {
		List<HYZGZD> userList = sysConfigureMgr.searchHYZG(F_BMBH, keywords, 0, 100);
		model.addAttribute("userList", userList);
		model.addAttribute("F_BMBH", F_BMBH);
		return "/help/dictHelp/zgHelp";
	}
	
	
	
	
	
	
	
	
	
	
	/**
	 * 电气材料
	 * @param model
	 * @return
	 */
	@RequestMapping("/material")
	public String materialList(Model model) {
		List<HYCLZD> hyclList = sysConfigureMgr.searchHYCLZD(0, 100);
		model.addAttribute("hyclList", hyclList);
		return "/mrp/sysConfigure/material/list";
	}

	@RequestMapping("/material/search")
	public String materialList(@RequestParam String keywords, Model model) {
		List<HYCLZD> hyclList = sysConfigureMgr.searchHYCLZD(keywords, 0, 100);
		model.addAttribute("hyclList", hyclList);
		return "/mrp/sysConfigure/material/list";
	}
	
	@RequestMapping("/material/add")
	public String materialAdd(Model model) {
		SYSUser user = (SYSUser) session.getAttribute("contextUser");
		String F_ZGBH = user.getUSER_ID();
		HYCLZD hyclObject = new HYCLZD();		
		hyclObject.setF_LRR(F_ZGBH);
		hyclObject.setF_LRRXM(user.getUSER_NAME());
		model.addAttribute("hyclObject", hyclObject);
		return "/mrp/sysConfigure/material/add";
	}

	@RequestMapping("/material/edit/{F_CLBH}")
	public String materialEdit(@PathVariable("F_CLBH") String F_CLBH, Model model) {
		SYSUser user = (SYSUser) session.getAttribute("contextUser");
		String F_ZGBH = user.getUSER_ID();
		HYCLZD hyclObject = sysConfigureMgr.getHYCL(F_CLBH);
		hyclObject.setF_LRR(F_ZGBH);
		hyclObject.setF_LRRXM(user.getUSER_NAME());
		model.addAttribute("hyclObject", hyclObject);
		return "/mrp/sysConfigure/material/edit";
	}
	
	@RequestMapping(value = "/material/insert", method = RequestMethod.POST)
	public ModelAndView materialInsert(HYCLZD hyclObject) {
		HYCLZD po = sysConfigureMgr.getHYCL(hyclObject.getF_CLBH());
		
		if(po != null) {
			return ajaxDoneError(hyclObject.getF_CLBH() + "【" + hyclObject.getF_CLMC() + "】已经存在，不允许添加！");
		}
		
		sysConfigureMgr.addHYCL(hyclObject);
		return ajaxDoneSuccess(hyclObject.getF_CLBH() + "【" + hyclObject.getF_CLMC() + "】材料添加成功！");
	}
	
	@RequestMapping(value = "/material/update/{F_CLBH}", method = RequestMethod.POST)
	public ModelAndView materialUpdate(@PathVariable("F_CLBH") String F_CLBH, HYCLZD hyclObject, Model model) {
		hyclObject.setF_CLBH(F_CLBH);
		sysConfigureMgr.updateHYCL(hyclObject);
		hyclObject = sysConfigureMgr.getHYCL(F_CLBH);
		model.addAttribute("hyclObject", hyclObject);
		return ajaxDoneSuccess(hyclObject.getF_CLBH() + "【" + hyclObject.getF_CLMC() + "】材料修改成功！");
	}
	
	@RequestMapping("/material/delete/{F_CLBH}")
	public ModelAndView materialDelete(@PathVariable("F_CLBH") String F_CLBH) {
		sysConfigureMgr.delHYCL(F_CLBH);
		return ajaxDoneSuccess("【 " + F_CLBH + "】材料删除成功！");
	}
	@RequestMapping("/material/suppliers/search")
	public String departmentUserLists(@RequestParam String keywords, Model model) {
		List<HYDWZD> hydwList = sysConfigureMgr.searchHYDW(keywords, 0, 100);
		model.addAttribute("hydwList", hydwList);
		return "/help/dictHelp/dwHelp";
	}

	@RequestMapping("/material/suppliers")
	public String materialSuppliersList(Model model) {
		List<HYDWZD> hydwList = sysConfigureMgr.searchHYDW(0, 100);
		model.addAttribute("hydwList", hydwList);
		return "/help/dictHelp/dwHelp";
	}
	
	
	
	
	
	/**
	 * 项目
	 * @param model
	 * @return
	 */
	@RequestMapping("/project")
	public String projectList(Model model) {
		String F_YEAR = "";
		Calendar cal = Calendar.getInstance();
		java.util.Date currTime = new java.util.Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat formatter1 = new SimpleDateFormat("yyyyMMdd");
		F_YEAR = String.valueOf(cal.get(Calendar.YEAR));
		
		List<HYXMZD> projectList = sysConfigureMgr.searchHYXM(F_YEAR + "0101", formatter1.format(currTime), null, 0, 100);
		model.addAttribute("projectList", projectList);
		model.addAttribute("beginDate", F_YEAR + "-01-01");
		model.addAttribute("endDate", formatter.format(currTime));		
		return "/mrp/sysConfigure/project/list";
	}	
	
	@RequestMapping("/project/search")
	public String projectList(@RequestParam String beginDate, 
							  @RequestParam String endDate, 
							  @RequestParam String F_XMZT, 
							  Model model) {
		String beginDateTemp = beginDate.replaceAll("-", "");
		String endDateTemp = endDate.replaceAll("-", "");
		List<HYXMZD> projectList = sysConfigureMgr.searchHYXM(beginDateTemp, endDateTemp, F_XMZT,0, 100);
		model.addAttribute("projectList", projectList);
		model.addAttribute("beginDate", beginDate);
		model.addAttribute("endDate", endDate);
		model.addAttribute("F_XMZT", F_XMZT);
		return "/mrp/sysConfigure/project/list";
	}	
	
	@RequestMapping("/project/help")
	public String projectHelpList(@RequestParam("F_XMZT") String F_XMZT, Model model) {
		String F_YEAR = "";
		Calendar cal = Calendar.getInstance();
		java.util.Date currTime = new java.util.Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat formatter1 = new SimpleDateFormat("yyyyMMdd");
		F_YEAR = String.valueOf(cal.get(Calendar.YEAR));
		
		List<HYXMZD> projectList = sysConfigureMgr.searchHYXM(F_YEAR + "0101", formatter1.format(currTime), F_XMZT,0, 100);
		model.addAttribute("projectList", projectList);
		model.addAttribute("beginDate", F_YEAR + "-01-01");
		model.addAttribute("endDate", formatter.format(currTime));		
		return "/help/dictHelp/xmHelp";
	}
	
	@RequestMapping("/project/help/search")
	public String projectHelpList(@RequestParam String beginDate, 
							      @RequestParam String endDate, 
							      @RequestParam String F_XMBH, 
							      @RequestParam("F_XMZT") String F_XMZT,
							      Model model) {
		String beginDateTemp = beginDate.replaceAll("-", "");
		String endDateTemp = endDate.replaceAll("-", "");
		List<HYXMZD> projectList = sysConfigureMgr.searchHYXM(beginDateTemp, endDateTemp, F_XMBH, F_XMZT,0, 100);
		model.addAttribute("projectList", projectList);
		model.addAttribute("beginDate", beginDate);
		model.addAttribute("endDate", endDate);
		model.addAttribute("F_XMBH", F_XMBH);
		return "/help/dictHelp/xmHelp";
	}	
	
	@RequestMapping("/project/xmwg/{F_XMBH}")
	public ModelAndView xmwg(@PathVariable String F_XMBH, 
							 @RequestParam String beginDate, 
							 @RequestParam String endDate, 
							 @RequestParam String F_XMZT, 
							 Model model) {
		SYSUser          user = (SYSUser) session.getAttribute("contextUser");
		String  tempBeginDate = beginDate.replaceAll("-", "");
		String    tempEndDate = endDate.replaceAll("-", "");
		sysConfigureMgr.doCompleteProject(F_XMBH, user.getUSER_ID());
		List<HYXMZD> projectList = sysConfigureMgr.searchHYXM(tempBeginDate, tempEndDate, F_XMZT,0, 100);
		model.addAttribute("projectList", projectList);
		model.addAttribute("beginDate", beginDate);
		model.addAttribute("endDate", endDate);
		model.addAttribute("F_XMZT", F_XMZT);
		return ajaxDoneSuccess("操作成功！");
	}	
	
	@RequestMapping("/project/cancelxmwg/{F_XMBH}")
	public String cancelxmwg(@RequestParam String beginDate, 
							 @RequestParam String endDate, 
							 @RequestParam String F_XMZT, 
							 Model model) {
		List<HYXMZD> projectList = sysConfigureMgr.searchHYXM(beginDate, endDate, F_XMZT,0, 100);
		model.addAttribute("projectList", projectList);
		model.addAttribute("beginDate", beginDate);
		model.addAttribute("endDate", endDate);
		model.addAttribute("F_XMZT", F_XMZT);
		return "/mrp/sysConfigure/deport/deportList";
	}	
	
	
	/**
	 * 产品管理
	 * @param model
	 * @return
	 */
	@RequestMapping("/product")
	public String productsList(Model model) {
		List<HYCPZD> hycpList = sysConfigureMgr.searchHYCP(0, 100);
		model.addAttribute("hycpList", hycpList);
		return "/mrp/sysConfigure/product/list";
	}

	@RequestMapping("/product/search")
	public String productsList(@RequestParam String F_CPBH, Model model) {
		List<HYCPZD> hycpList = sysConfigureMgr.searchHYCP(F_CPBH, 0, 100);
		model.addAttribute("hycpList", hycpList);
		model.addAttribute("F_CPBH", F_CPBH);
		return "/mrp/sysConfigure/product/list";
	}
	
	@RequestMapping("/product/add")
	public String productAdd(Model model) {
		return "/mrp/sysConfigure/product/add";
	}

	@RequestMapping("/product/edit/{F_CPBH}")
	public String productEdit(@PathVariable("F_CPBH") String F_BMBH, Model model) {
		HYCPZD hycpObject = sysConfigureMgr.getHYCP(F_BMBH);
		model.addAttribute("hycpObject", hycpObject);
		return "/mrp/sysConfigure/product/edit";
	}
	
	@RequestMapping(value = "/product/insert", method = RequestMethod.POST)
	public ModelAndView productInsert(HYCPZD hycpObject) {
		HYCPZD po = sysConfigureMgr.getHYCP(hycpObject.getF_CPBH());
		if(po != null) return ajaxDoneError("产品" + po.getF_CPBH() + "【" + po.getF_CPMC() + "】已经存在，不允许重复添加！");
		sysConfigureMgr.addHYCP(hycpObject);
		return ajaxDoneSuccess("产品【 " + hycpObject.getF_CPBH() + "】添加成功！");
	}
	
	@RequestMapping(value = "/product/update/{F_CPBH}", method = RequestMethod.POST)
	public ModelAndView productUpdate(@PathVariable("F_CPBH") String F_CPBH, HYCPZD hycpObject, Model model) {
		hycpObject.setF_CPBH(F_CPBH);
		sysConfigureMgr.updateHYCP(hycpObject);
		hycpObject = sysConfigureMgr.getHYCP(F_CPBH);
		model.addAttribute("hybmObject", hycpObject);
		return ajaxDoneSuccess("产品【 " +  hycpObject.getF_CPMC() + "】修改成功！");
	}
	
	@RequestMapping("/product/delete/{F_CPBH}")
	public ModelAndView productDelete(@PathVariable("F_CPBH") String F_CPBH) {
		sysConfigureMgr.delHYCP(F_CPBH);
		return ajaxDoneSuccess("产品【 " +  F_CPBH + "】删除成功！");
	}
	
	/**
	 * 项目明细管理
	 * @param model
	 * @return
	 */
	@RequestMapping("/project/product/rqfw")
	public String productList(Model model) {
		String                F_YEAR = "";
		Calendar                 cal = Calendar.getInstance();
		java.util.Date      currTime = new java.util.Date();
		SimpleDateFormat   formatter = new SimpleDateFormat("yyyy-MM-dd");
		F_YEAR = String.valueOf(cal.get(Calendar.YEAR));
				
		model.addAttribute("beginDate", F_YEAR + "-01-01");
		model.addAttribute("endDate", formatter.format(currTime));		
		return "/mrp/sysConfigure/projectProduct/rqfw";
	}	
	
	@RequestMapping("/project/product/search/{F_XMBH}")
	public String productLists(@PathVariable("F_XMBH") String F_XMBH, 
			                   @RequestParam String F_CPBH, Model model) {
		List<HYXMCP> productList = sysConfigureMgr.searchHYXMCP(F_XMBH, F_CPBH, 0, 100);
		model.addAttribute("productList", productList);
		model.addAttribute("F_XMBH", F_XMBH);
		model.addAttribute("F_CPBH", F_CPBH);
		return "/mrp/sysConfigure/projectProduct/productList";
	}

	@RequestMapping("/project/product/list")
	public String productList1(@RequestParam String beginDate, 
			  				   @RequestParam String endDate, 
			  				   Model model) {
		String       beginDateTemp = beginDate.replaceAll("-", "");
		String         endDateTemp = endDate.replaceAll("-", "");
		
		List<HYXMZD>   projectList = sysConfigureMgr.searchHYXM(beginDateTemp, endDateTemp, "0", 0, 100);
		
		model.addAttribute("F_XMBH", "{F_XMBH}1");		
		model.addAttribute("hyxmList", projectList);
		model.addAttribute("beginDate", beginDate);
		model.addAttribute("endDate", endDate);		
		return "/mrp/sysConfigure/projectProduct/list";
	}	
	
	@RequestMapping("/project/product/{F_XMBH}")
	public String productList(@PathVariable("F_XMBH") String F_XMBH, Model model) {
		List<HYXMCP> productList = sysConfigureMgr.searchHYXMCP(F_XMBH, 0, 100);
		model.addAttribute("productList", productList);
		model.addAttribute("F_XMBH", F_XMBH);
		return "/mrp/sysConfigure/projectProduct/productList";
	}
	
	@RequestMapping("/project/product/help/{F_XMBH}")
	public String productHelpList(@PathVariable("F_XMBH") String F_XMBH, Model model) {
		List<HYXMCP> productList = sysConfigureMgr.searchHYXMCP(F_XMBH, "1");
		model.addAttribute("productList", productList);
		model.addAttribute("F_XMBH", F_XMBH);
		return "/help/dictHelp/xmcpHelp";
	}
	
	@RequestMapping("/project/product/help")
	public String productHelpList1(@RequestParam("F_XMBH") String F_XMBH, Model model) {
		List<HYXMCP> productList = sysConfigureMgr.searchHYXMCP(F_XMBH, "1");
		model.addAttribute("productList", productList);
		model.addAttribute("F_XMBH", F_XMBH);
		return "/help/dictHelp/xmcpHelp";
	}
		
	@RequestMapping("/project/product/help/search/{F_XMBH}")
	public String productHelpList(@PathVariable("F_XMBH") String F_XMBH, @RequestParam String keywords, Model model) {
		List<HYXMCP> productList = sysConfigureMgr.searchHYXMCP(F_XMBH, keywords, "1", 0, 100);
		model.addAttribute("productList", productList);
		model.addAttribute("F_XMBH", F_XMBH);
		return "/help/dictHelp/xmcpHelp";
	}
	
	@RequestMapping("/project/product/add/{F_XMBH}")
	public String productAdd(@PathVariable("F_XMBH") String F_XMBH, Model model) {
		HYXMZD hyxmObject = sysConfigureMgr.getHYXM(F_XMBH);
		HYXMCP hyxmcpObject = new HYXMCP();
		hyxmcpObject.setF_XMBH(F_XMBH);
		hyxmcpObject.setF_XMMC(hyxmObject.getF_XMMC());
		model.addAttribute("hyxmcpObject", hyxmcpObject);
		return "/mrp/sysConfigure/projectProduct/add";
	}
	
	@RequestMapping("/project/product/insert/{F_XMBH}")
	public ModelAndView deportInsert(@PathVariable("F_XMBH") String F_XMBH, HYXMCP hycpObject, Model model) {
		hycpObject.setF_XMBH(F_XMBH);
		HYXMCP po = sysConfigureMgr.getHYXMCP(null, hycpObject.getF_CPBH());
		
		if(po != null) return ajaxDoneError("项目编号：" + po.getF_XMBH() + "<br>材料编号：" + hycpObject.getF_CPBH() + "<br>已经存在，不允许添加！");
		
		sysConfigureMgr.addHYXMCP(hycpObject);
		ModelAndView mav = ajaxDoneSuccess("项目编号：" + F_XMBH + "<br>产品编号：" + hycpObject.getF_CPBH() + "<br>添加成功！");
		model.addAttribute("F_XMBH",F_XMBH);
		return mav;
	}
	
	@RequestMapping("/project/product/edit/{F_CPBH}")
	public String productEdit(@PathVariable("F_CPBH") String F_CPBH, 
			   			      @RequestParam("F_XMBH") String F_XMBH, 
			   			      Model model) {
		HYXMCP hycpObject = sysConfigureMgr.getHYXMCP(F_XMBH, F_CPBH);
		model.addAttribute("hycpObject", hycpObject);
		model.addAttribute("F_CKBH", F_CPBH);
		model.addAttribute("F_BMBH", F_XMBH);
		return "/mrp/sysConfigure/projectProduct/edit";
	}
	
	@RequestMapping(value = "/project/product/update/{F_CPBH}", method = RequestMethod.POST)
	public ModelAndView productUpdate(@PathVariable("F_CPBH") String F_CPBH, 
			   					      @RequestParam("F_XMBH") String F_XMBH, 
			   					      HYXMCP hycpObject, Model model) {
		hycpObject.setF_CPBH(F_CPBH);
		hycpObject.setF_XMBH(F_XMBH);
		sysConfigureMgr.updateHYXMCP(hycpObject);
		return ajaxDoneSuccess("项目编号：" + F_XMBH + "<br>产品编号：" + F_CPBH + "<br>保存成功！");
	}
	
	@RequestMapping("/project/product/delete/{F_CPBH}")
	public ModelAndView productDelete(@PathVariable("F_CPBH") String F_CPBH, 
								      @RequestParam("F_XMBH") String F_XMBH) {
		sysConfigureMgr.delHYXMCP(F_XMBH, F_CPBH);
		return ajaxDoneSuccess("项目编号：" + F_XMBH + "<br>产品编号：" + F_CPBH + "<br>删除成功！");
	}
}
