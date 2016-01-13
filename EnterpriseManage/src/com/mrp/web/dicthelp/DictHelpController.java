package com.mrp.web.dicthelp;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mrp.biz.dicthelp.DictHelpServiceMgr;

import com.mrp.persistence.sysConfigure.company.bean.HYCSZD;
import com.mrp.persistence.sysConfigure.department.bean.HYBMZD;
import com.mrp.persistence.sysConfigure.deport.bean.HYCKZD;
import com.mrp.persistence.sysConfigure.material.bean.HYCLZD;
import com.mrp.persistence.sysConfigure.product.bean.HYCPZD;
import com.mrp.persistence.sysConfigure.project.bean.HYXMZD;
import com.mrp.persistence.sysConfigure.projectProduct.bean.HYXMCP;
import com.mrp.persistence.sysConfigure.user.bean.HYZGZD;
import com.etsoft.pub.util.StringUtil;

import dwz.web.BaseController;
import esyt.framework.com.builder.base.data.EFDataSet;

@Controller
@RequestMapping(value="/dictHelp")
public class DictHelpController extends BaseController{
	@Autowired
	private DictHelpServiceMgr dictHelpMgr;

	//--------------------------------------------------------------------------------------------------
	//描述:部门列表
	//设计: ES
	//实现: ES
	//修改:
	//--------------------------------------------------------------------------------------------------
	@RequestMapping("/deportHelp")
	public String deportHelp(Model model) {
		List<HYBMZD> hybmList = dictHelpMgr.searchHYBMZD();
		model.addAttribute("hybmList", hybmList);	
		model.addAttribute("F_BMBH", "{F_BMBH}");	
		return "/help/dictHelp/deport/bmckHelp";
	}
	
	//--------------------------------------------------------------------------------------------------
	//描述:部门列表
	//设计: ES
	//实现: ES
	//修改:
	//--------------------------------------------------------------------------------------------------
	@RequestMapping("/companyHelp")
	public String companyHelp(@RequestParam String F_CSBH, @RequestParam String F_SYZT, Model model) {
		List<HYCSZD> hycsList = dictHelpMgr.searchHYCS(F_CSBH, F_SYZT, 0, 100);
		model.addAttribute("F_CSBH", F_CSBH);	
		model.addAttribute("hycsList", hycsList);
		return "/help/dictHelp/companyHelp";
	}
	
	//--------------------------------------------------------------------------------------------------
	//描述:部门对应仓库列表
	//设计: ES
	//实现: ES
	//修改:
	//--------------------------------------------------------------------------------------------------
	@RequestMapping("/deportHelp/{F_BMBH}")
	public String deportList(@PathVariable("F_BMBH") String F_BMBH, Model model) {
		List<HYCKZD> deportList = dictHelpMgr.searchHYCK(F_BMBH, 0, 100);
		model.addAttribute("deportList", deportList);
		model.addAttribute("F_BMBH", F_BMBH);
		model.addAttribute("F_ZDBH", "F_CKBH");
		model.addAttribute("F_ZDMC", "F_CKMC");
		return "/help/dictHelp/deport/deportList";
	}
	
	//--------------------------------------------------------------------------------------------------
	//描述:根据仓库编号，查找部门对应仓库列表
	//设计: ES
	//实现: ES
	//修改:
	//--------------------------------------------------------------------------------------------------
	@RequestMapping("/deport/search/{F_BMBH}")
	public String deportList(@PathVariable("F_BMBH") String F_BMBH, @RequestParam String keywords, Model model) {
		List<HYCKZD> deportList = dictHelpMgr.searchHYCK(F_BMBH, keywords, 0, 100);
		model.addAttribute("deportList", deportList);
		model.addAttribute("F_BMBH", F_BMBH);
		return "/help/dictHelp/deport/deportList";
	}
	
	//--------------------------------------------------------------------------------------------------
	//描述:根据项目和产品编号，查找还有剩余的材料
	//设计: ES
	//实现: ES
	//修改:
	//--------------------------------------------------------------------------------------------------
	@RequestMapping("/material/bussinessHelp")
	public String materialList(@RequestParam("F_CPBH") String F_SSCPBH, 
							   @RequestParam("F_XMBH") String F_XMBH, 
							   @RequestParam("F_CLBH") String F_CLBH,
							   @RequestParam("F_CKBH") String F_CKBH, 
							   Model model) {
		
		if(F_SSCPBH.equals("{F_SSCPBH}")) F_SSCPBH = "";
		if(F_SSCPBH.equals("{F_YYCPBH}")) F_SSCPBH = "";
		if(F_XMBH.equals("{F_YYXMBH}") || F_XMBH.equals("{F_SSXMBH}")) F_XMBH = "";
		
		EFDataSet dataList = dictHelpMgr.xmclHelp(F_XMBH, F_SSCPBH, F_CLBH, F_CKBH, 0, 100);
		model.addAttribute("dataList", dataList);
		model.addAttribute("F_CPBH", F_SSCPBH);
		model.addAttribute("F_XMBH", F_XMBH);
		model.addAttribute("F_CKBH", F_CKBH);
		model.addAttribute("F_CLBH", F_CLBH);
		return getString("tarRel", "");
	}
	
	//--------------------------------------------------------------------------------------------------
	//描述:根据项目和产品编号，查找还有剩余的材料
	//设计: ES
	//实现: ES
	//修改:
	//--------------------------------------------------------------------------------------------------
	@RequestMapping("/material/help")
	public String materialList(@RequestParam("F_CLBH") String F_CLBH, Model model) {
		List<HYCLZD> dataList = dictHelpMgr.clHelp(F_CLBH, 0, 100);
		model.addAttribute("F_CLBH", F_CLBH);
		model.addAttribute("dataList", dataList);
		return getString("tarRel", "");
	}
	
	//--------------------------------------------------------------------------------------------------
	//描述:厂商帮助
	//设计: ES
	//实现: ES
	//修改:
	//--------------------------------------------------------------------------------------------------
	@RequestMapping("/company/help")
	public String compoanyList(@RequestParam("F_CSBH") String F_CSBH, @RequestParam("F_SYZT") String F_SYZT, Model model) {
		List<HYCSZD> companyList = dictHelpMgr.csHelp(F_CSBH, F_SYZT, 0, 100);
		model.addAttribute("F_CSBH", F_CSBH);
		model.addAttribute("companyList", companyList);
		return getString("tarRel", "");
	}
	
	@RequestMapping("/product/help")
	public String productHelpList(@RequestParam("F_CPBH") String F_CPBH, Model model) {
		List<HYCPZD> productList = dictHelpMgr.searchHYCPZD(F_CPBH, null, 0, 100);
		model.addAttribute("productList", productList);
		model.addAttribute("F_CPBH", F_CPBH);
		return "/help/dictHelp/cpzdHelp";
	}
	
	@RequestMapping("/product/help/{F_SSXMBH}")
	public String productHelpList(@PathVariable("F_SSXMBH") String F_XMBH, @RequestParam("HELPCOL") String HELPCOL, Model model) {
		String[] col = HELPCOL.split(",");
		List<HYXMCP> productList = dictHelpMgr.searchHYXMCP(F_XMBH, 0, 100);
		model.addAttribute("productList", productList);
		model.addAttribute("F_XMBH", F_XMBH);
		model.addAttribute("F_ZDBH", col[0]);
		model.addAttribute("F_ZDMC", col[1]);
		return "/help/dictHelp/xmcpzdHelp";
	}

	@RequestMapping("/project/product/help")
	public String productHelpByCL(@RequestParam("F_CKBH") String F_CKBH, 
								  @RequestParam("F_XMBH") String F_XMBH, 
								  @RequestParam("F_CPBH") String F_CPBH, 
								  @RequestParam("F_XMZT") String F_XMZT, 
								  @RequestParam("HELPCOL") String HELPCOL, 
								  Model model) {
		String[]             col = HELPCOL.split(",");
		int                index = 1;
		List<HYXMCP> productList = dictHelpMgr.searchHYXMCP(F_XMBH, F_CKBH, F_CPBH, F_XMZT, 0, 100);
		model.addAttribute("productList", productList);
		model.addAttribute("F_XMBH", F_XMBH);
		model.addAttribute("F_CKBH", F_CKBH);
		model.addAttribute("F_CPBH", F_CPBH);
		model.addAttribute("F_XMZT", F_XMZT);
		
		for(int i = 0; i < col.length; i++,index++) {
			model.addAttribute("F_ZDBH" + index, col[i]);
			model.addAttribute("F_ZDMC" + index, col[++i]);
		}
		return getString("tarRel", "");
	}
	
	@RequestMapping("/product/project/help")
	public String projectHelpByCL(@RequestParam("F_CLBH") String F_CLBH, 
								  @RequestParam("F_XMBH") String F_XMBH, 
								  @RequestParam("F_CPBH") String F_CPBH, 
								  Model model) {
		String                 F_YEAR = "";
		Calendar                  cal = Calendar.getInstance();
		java.util.Date       currTime = new java.util.Date();
		SimpleDateFormat    formatter = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat   formatter1 = new SimpleDateFormat("yyyyMMdd");
		
		F_YEAR = String.valueOf(cal.get(Calendar.YEAR));
		
		if(F_CPBH.matches("\\{F_CPBH\\[[0-9]\\]\\}")){
			F_CPBH = "";
		}
		EFDataSet ds = dictHelpMgr.xmclHelp("", F_CPBH, F_CLBH, F_YEAR + "0101", formatter1.format(currTime), 0, 100);
		model.addAttribute("projectList", ds);
		model.addAttribute("F_XMBH", F_XMBH);
		model.addAttribute("F_CPBH", F_CPBH);
		model.addAttribute("F_CLBH", F_CLBH);
		model.addAttribute("projectList", ds);
		model.addAttribute("beginDate", F_YEAR + "-01-01");
		model.addAttribute("endDate", formatter.format(currTime));		
		return "/help/dictHelp/clxmHelp";
	}
	
	@RequestMapping("/product/project/searchHelp")
	public String productSearchHelpByCL(@RequestParam("F_CLBH") String F_CLBH, 
								        @RequestParam("F_XMBH") String F_XMBH, 
								        @RequestParam("F_CPBH") String F_CPBH, 
								        @RequestParam("beginDate") String beginDate, 
								        @RequestParam("endDate") String endDate, 
								        Model model) {
		String  beginDateTemp = beginDate.replaceAll("-", "");
		String    endDateTemp = endDate.replaceAll("-", "");

		EFDataSet ds = dictHelpMgr.xmclHelp(F_XMBH, F_CPBH, F_CLBH, beginDateTemp, endDateTemp, 0, 100);
		model.addAttribute("projectList", ds);
		model.addAttribute("F_XMBH", F_XMBH);
		model.addAttribute("F_CPBH", F_CPBH);
		model.addAttribute("F_CLBH", F_CLBH);
		model.addAttribute("projectList", ds);
		model.addAttribute("beginDate", beginDate);
		model.addAttribute("endDate", endDate);		
		return "/help/dictHelp/clxmHelp";
	}
	
	@RequestMapping("/product/help/search/{F_XMBH}")
	public String productHelpList(@PathVariable("F_XMBH") String F_XMBH, 
			  					  @RequestParam("F_ZDBH") String F_ZDBH, 
			  					  @RequestParam("F_ZDMC") String F_ZDMC, 
			  					  @RequestParam String keywords, Model model) {
		List<HYXMCP> productList = dictHelpMgr.searchHYXMCP(F_XMBH, keywords, 0, 100);
		model.addAttribute("productList", productList);
		model.addAttribute("F_XMBH", F_XMBH);
		model.addAttribute("F_ZDBH", F_ZDBH);
		model.addAttribute("F_ZDMC", F_ZDMC);
		return "/help/dictHelp/xmcpzdHelp";
	}
	
	@RequestMapping("/deportHelp/user")
	public String userList(@RequestParam("TYPE") String TYPE, @RequestParam("MUL") String MUL, Model model) {
		List<HYBMZD> hybmList = dictHelpMgr.searchHYBMZD();	
		model.addAttribute("hybmList", hybmList);
		model.addAttribute("TYPE", TYPE);
		model.addAttribute("MUL", MUL);
		return "/help/dictHelp/bmzgHelp";
	}
	
	//--------------------------------------------------------------------------------------------------
	//描述:项目申请单导入 供应中心、单位领导、分管领导、主管领导帮助,根绝部门编号查询人员
	//设计: ES
	//实现: ES
	//修改:
	//--------------------------------------------------------------------------------------------------
	@RequestMapping("/project/user/{F_SXBM}")
	public String ldUserList(@RequestParam("TYPE") String TYPE, @RequestParam("MUL") String MUL, @PathVariable("F_SXBM") String F_SXBM, Model model) {
		List<HYZGZD> userList = dictHelpMgr.searchHYZGZD(F_SXBM, 0, 100);
		String[] helpCol = TYPE.split(",");
		model.addAttribute("userList", userList);
		model.addAttribute("F_SXBM", F_SXBM);
		model.addAttribute("TYPE", TYPE);
		model.addAttribute("MUL", MUL);
		model.addAttribute("F_ZDBH", helpCol[0]);
		model.addAttribute("F_ZDMC", helpCol[1]);
		return "/help/dictHelp/userList";
	}
	
	//--------------------------------------------------------------------------------------------------
	//描述:项目申请单导入 供应中心、单位领导、分管领导、主管领导帮助,根绝部门编号模糊查询人员
	//设计: ES
	//实现: ES
	//修改:
	//--------------------------------------------------------------------------------------------------
	@RequestMapping("/project/user/search/{F_SXBM}")
	public String userList(@RequestParam("TYPE") String TYPE, 
						   @PathVariable("F_SXBM") String F_SXBM, 
						   @RequestParam("MUL") String MUL, 
						   @RequestParam String keywords,
						   Model model) {
		List<HYZGZD> userList = dictHelpMgr.searchHYZGZD(F_SXBM, keywords, 0, 100);
		String[] helpCol = TYPE.split(",");
		model.addAttribute("userList", userList);
		model.addAttribute("F_SXBM", F_SXBM);
		model.addAttribute("TYPE", TYPE);
		model.addAttribute("MUL", MUL);
		model.addAttribute("F_ZDBH", helpCol[0]);
		model.addAttribute("F_ZDMC", helpCol[1]);
		return "/help/dictHelp/userList";
	}
	
	@RequestMapping("/project/help/{F_CKBH}")
	public String projectHelpList1(@RequestParam("F_XMZT") String F_XMZT, 
								   @PathVariable("F_CKBH") String F_CKBH, 
   								   @RequestParam("F_XMBH") String F_XMBH,
 								   @RequestParam("F_YEAR") String F_YEAR,
 								   @RequestParam("F_MONTH") String F_MONTH,
 								   @RequestParam("F_CRFX") String F_CRFX,
 								   Model model) {
		String begindate = StringUtil.getMonthBegin(F_YEAR + "01");
		String enddate = StringUtil.getMonthEnd(F_YEAR + F_MONTH);

		List<HYXMZD> projectList = dictHelpMgr.searchXMCK(begindate, enddate, F_CKBH, F_XMBH, "", F_CRFX, F_XMZT,0, 100);
		model.addAttribute("projectList", projectList);
		model.addAttribute("beginDate", StringUtil.formatDate(begindate));
		model.addAttribute("endDate", StringUtil.formatDate(enddate));		
		model.addAttribute("F_CKBH", F_CKBH);	
		model.addAttribute("F_XMZT", F_XMZT);
		return "/help/dictHelp/ckxmHelp";
	}

	//--------------------------------------------------------------------------------------------------
	//描述:材料出库单，所属项目帮助
	//设计: ES
	//实现: ES
	//修改:
	//--------------------------------------------------------------------------------------------------
	@RequestMapping("/project/list/{F_SSCKBH}")
	public String projectHelpList2(@RequestParam("F_XMZT") String F_XMZT, 
								   @PathVariable("F_SSCKBH") String F_CKBH, 
   								   @RequestParam("F_XMBH") String F_XMBH,
   								   @RequestParam("F_CLBH") String F_CLBH, 
   								   @RequestParam("F_CPBH") String F_CPBH, 
 								   @RequestParam("F_KJQJ") String F_KJQJ,
 								   Model model) {
		List<String[]> dataList = dictHelpMgr.xmHelp(F_XMBH, F_CPBH, F_CLBH, F_CKBH, F_XMZT, 0, 100);
		model.addAttribute("dataList", dataList);
		model.addAttribute("F_CPBH", F_XMZT);
		model.addAttribute("F_XMBH", F_CKBH);
		model.addAttribute("F_CKBH", F_XMBH);
		model.addAttribute("F_CPBH", F_CLBH);
		model.addAttribute("F_XMBH", F_CPBH);
		model.addAttribute("F_CKBH", F_KJQJ);
		return getString("tarRel", "");
	}

	//--------------------------------------------------------------------------------------------------
	//描述:项目申请单明细,增加材料帮助查询
	//设计: ES
	//实现: ES
	//修改:
	//--------------------------------------------------------------------------------------------------
	@RequestMapping("/outBound/material/search")
	public String materiaSearchlList(@RequestParam("F_CPBH") String F_CPBH, 
									 @RequestParam("F_XMBH") String F_XMBH, 
									 @RequestParam("F_CKBH") String F_CKBH, 
									 @RequestParam("F_CLBH") String F_CLBH, 
									 @RequestParam("F_XMZT") String F_XMZT, 
									 @RequestParam String keywords, Model model) {
		List<String[]> dataList = dictHelpMgr.xmHelp(F_XMBH, F_CPBH, F_CLBH, F_CKBH, F_XMZT, 0, 100);
		model.addAttribute("dataList", dataList);
		model.addAttribute("F_CPBH", F_CPBH);
		model.addAttribute("F_XMBH", F_XMBH);
		model.addAttribute("F_CKBH", F_CKBH);
		model.addAttribute("F_CLBH", F_CLBH);
		model.addAttribute("F_XMZT", F_XMZT);
		model.addAttribute("keywords", keywords);
		return getString("tarRel", "");
	}
	
	//--------------------------------------------------------------------------------------------------
	//描述:材料入库单，材料编号帮助,如果项目编号为{F_XMBH}，则代表项目编号为空，则先弹出一个界面，选择项目和产品编号
	//设计: ES
	//实现: ES
	//修改:
	//--------------------------------------------------------------------------------------------------
	@RequestMapping("/materialHelp/{F_XMBH}")
	public String scanningMaterial(@PathVariable("F_XMBH") String F_XMBH,
								   @RequestParam("F_DWBH") String F_DWBH,
								   @RequestParam("F_CSBH") String F_CSBH, 
								   @RequestParam("F_CPBH") String F_CPBH, 
								   @RequestParam("WHERE") String WHERE, 
								   @RequestParam String F_CLBH,
								   Model model) {
		if(F_XMBH.equals("{F_XMBH}")) F_XMBH = "";
		if(F_DWBH.equals("{F_DWBH}")) F_DWBH = "";
		if(F_CSBH.equals("{F_CSBH}")) F_CSBH = "";
		if(F_CPBH.equals("{F_CPBH}")) F_CPBH = "";
		if(F_CLBH.equals("{F_CLBH}")) F_CLBH = "";
		
		EFDataSet dataset = dictHelpMgr.scanningHYCLZD(F_XMBH, F_CLBH, F_DWBH, F_CPBH, F_CSBH, WHERE, 0, 100);
		model.addAttribute("dataset", dataset.getRowSetList());
		if(F_XMBH.equals("")) F_XMBH = "{F_XMBH}";
		model.addAttribute("F_XMBH", F_XMBH);
		model.addAttribute("F_DWBH", F_DWBH);
		model.addAttribute("F_CSBH", F_CSBH);
		model.addAttribute("F_CPBH", F_CPBH);
		model.addAttribute("F_CLBH", F_CLBH);
		model.addAttribute("WHERE", "");
		return "/help/dictHelp/xmclHelp";
	}

	//--------------------------------------------------------------------------------------------------
	//描述:材料入库单，材料编号帮助,如果项目编号为{F_XMBH}，则代表项目编号为空，则先弹出一个界面，选择项目和产品编号
	//设计: ES
	//实现: ES
	//修改:
	//--------------------------------------------------------------------------------------------------
	@RequestMapping("/deportMaterialHelp/{F_CKBH}")
	public String deportMaterialHelp(@PathVariable("F_CKBH") String F_CKBH,
									 @RequestParam("F_XMBH") String F_XMBH,
								     @RequestParam("F_DWBH") String F_DWBH,
								     @RequestParam("F_CSBH") String F_CSBH, 
								     @RequestParam("F_CLBH") String F_CLBH, 
								     @RequestParam("WHERE") String WHERE, 
								     Model model) {
		if(F_DWBH.equals("{F_DWBH}")) F_DWBH = "";
		if(F_CSBH.equals("{F_CSBH}")) F_CSBH = "";
		
		EFDataSet dataset = dictHelpMgr.deportMaterialHelp(F_CKBH, F_XMBH, F_CLBH, F_DWBH, F_CSBH, WHERE, 0, 100);
		model.addAttribute("dataset", dataset);
		model.addAttribute("F_CKBH", F_CKBH);
		model.addAttribute("F_XMBH", F_XMBH);
		model.addAttribute("F_DWBH", F_DWBH);
		model.addAttribute("F_CSBH", F_CSBH);
		model.addAttribute("F_CLBH", F_CLBH);
		model.addAttribute("WHERE", "");
		return "/help/dictHelp/ckclHelp";
	}
	
	@RequestMapping("/project/help/search/{F_CLBH}")
	public String projectHelpList(@RequestParam String beginDate, 
							      @RequestParam String endDate, 
							      @RequestParam String F_XMBH, 
							      @PathVariable String F_CLBH,
								  @RequestParam String F_XMZT,
							      Model model) {
		String beginDateTemp = beginDate.replaceAll("-", "");
		String endDateTemp = endDate.replaceAll("-", "");
		List<HYXMZD> projectList = dictHelpMgr.searchHYXMZD(beginDateTemp, endDateTemp,F_CLBH,F_XMZT,F_XMBH,0, 100);
		model.addAttribute("projectList", projectList);
		model.addAttribute("beginDate", beginDate);
		model.addAttribute("endDate", endDate);
		model.addAttribute("F_CLBH", F_CLBH);
		model.addAttribute("F_XMBH", F_XMBH);
		model.addAttribute("F_XMZT", F_XMZT);
		return getString("tarRel", "");
	}	
	
	//--------------------------------------------------------------------------------------------------
	//描述:出库单和退货单项目帮助
	//设计: ES
	//实现: ES
	//修改:
	//--------------------------------------------------------------------------------------------------
	@RequestMapping("/outBound/project/help/search/{F_CKBH}")
	public String projectHelpList3(@RequestParam String beginDate, 
							       @RequestParam String endDate, 
							       @RequestParam String F_XMBH, 
							       @RequestParam String F_CRFX, 
							       @PathVariable("F_CKBH") String F_CKBH, 
								   @RequestParam String F_XMZT,
							       Model model) {
		String beginDateTemp = beginDate.replaceAll("-", "");
		String endDateTemp = endDate.replaceAll("-", "");
		List<HYXMZD> projectList = dictHelpMgr.searchXMCK(beginDateTemp, endDateTemp, F_CKBH, F_XMBH, "", F_CRFX, F_XMZT,0, 100);
		model.addAttribute("projectList", projectList);
		model.addAttribute("beginDate", beginDate);
		model.addAttribute("endDate", endDate);		
		model.addAttribute("F_CKBH", F_CKBH);		
		model.addAttribute("F_XMBH", F_XMBH);
		model.addAttribute("F_XMZT", F_XMZT);
		return getString("tarRel", "");
	}	
	
	@RequestMapping("/outBound/project/help/searchByKjqj/{F_CKBH}")
	public String projectHelpByKjqjList2(@RequestParam("F_XMZT") String F_XMZT, 
								   @PathVariable("F_CKBH") String F_CKBH, 
   								   @RequestParam("F_XMBH") String F_XMBH,
 								   @RequestParam("F_YEAR") String F_YEAR,
 								   @RequestParam("F_MONTH") String F_MONTH,
 								   Model model) {
		String begindate = StringUtil.getMonthBegin(F_YEAR + "01");
		String enddate = StringUtil.getMonthEnd(F_YEAR + F_MONTH);

		List<HYXMZD> projectList = dictHelpMgr.searchHYXMZD(begindate, enddate, F_XMBH, F_XMZT,0, 100);
		model.addAttribute("projectList", projectList);
		model.addAttribute("beginDate", StringUtil.formatDate(begindate));
		model.addAttribute("endDate", StringUtil.formatDate(enddate));		
		model.addAttribute("F_CKBH", F_CKBH);	
		model.addAttribute("F_XMZT", F_XMZT);
		model.addAttribute("F_XMBH", F_XMBH);
		return "/mrp/dailyBusiness/outBound/help/ckxmHelp";
	}
	
	@RequestMapping("/outBound/project/help/searchByRq/{F_CKBH}")
	public String projectHelpByRqList(@RequestParam("F_XMZT") String F_XMZT, 
								      @PathVariable("F_CKBH") String F_CKBH, 
   								   	  @RequestParam("F_XMBH") String F_XMBH,
 								      @RequestParam("beginDate") String beginDate,
 								      @RequestParam("endDate") String endDate,
 								      Model model) {
		List<HYXMZD> projectList = dictHelpMgr.searchHYXMZD(beginDate.replaceAll("-", ""), endDate.replaceAll("-", ""), F_XMBH, F_XMZT,0, 100);
		model.addAttribute("projectList", projectList);
		model.addAttribute("beginDate", beginDate);
		model.addAttribute("endDate", endDate);		
		model.addAttribute("F_CKBH", F_CKBH);	
		model.addAttribute("F_XMZT", F_XMZT);
		model.addAttribute("F_XMBH", F_XMBH);
		return "/mrp/dailyBusiness/outBound/help/ckxmHelp";
	}
	
	//--------------------------------------------------------------------------------------------------
	//描述:根据材料编号，找到对应的项目
	//设计: ES
	//实现: ES
	//修改:
	//--------------------------------------------------------------------------------------------------
	@RequestMapping("/material")
	public String materialList(Model model) {
		List<HYCLZD> hyclList = dictHelpMgr.searchHYCLZD(0, 100);
		model.addAttribute("hyclList", hyclList);
		return "/help/dictHelp/clHelp";
	}
	
	//--------------------------------------------------------------------------------------------------
	//描述:项目申请单明细,增加材料帮助查询
	//设计: ES
	//实现: ES
	//修改:
	//--------------------------------------------------------------------------------------------------
	@RequestMapping("/excelStatements/material/search")
	public String materiaSearchlList(@RequestParam("F_CPBH") String F_CPBH, @RequestParam("F_XMBH") String F_XMBH, @RequestParam("F_CKBH") String F_CKBH, @RequestParam String keywords, Model model) {
		List<String[]> dataList = dictHelpMgr.xmclHelp(F_XMBH, F_CPBH, keywords, F_CKBH, 0, 100);
		model.addAttribute("dataList", dataList);
		model.addAttribute("F_CPBH", F_CPBH);
		model.addAttribute("F_XMBH", F_XMBH);
		model.addAttribute("F_CKBH", F_CKBH);
		model.addAttribute("keywords", keywords);
		return getString("tarRel", "");
	}
	
	@RequestMapping("/project/help")
	public String projectHelpList(@RequestParam("F_XMZT") String F_XMZT, Model model) {
		String F_YEAR = "";
		Calendar cal = Calendar.getInstance();
		java.util.Date currTime = new java.util.Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat formatter1 = new SimpleDateFormat("yyyyMMdd");
		F_YEAR = String.valueOf(cal.get(Calendar.YEAR));
		
		List<HYXMZD> projectList = dictHelpMgr.searchHYXMZD(F_YEAR + "0101", formatter1.format(currTime), F_XMZT,0, 100);
		model.addAttribute("projectList", projectList);
		model.addAttribute("F_XMZT", F_XMZT);
		model.addAttribute("beginDate", F_YEAR + "-01-01");
		model.addAttribute("endDate", formatter.format(currTime));		
		return getString("tarRel", "");
	}	
}
