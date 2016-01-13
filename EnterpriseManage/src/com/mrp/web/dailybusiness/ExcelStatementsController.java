package com.mrp.web.dailybusiness;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.mrp.biz.dailybusiness.ExcelStatementsServiceMgr;
import com.mrp.biz.sysconfigure.sysConfigureServiceMgr;
import com.mrp.persistence.dailyBusiness.excelStatements.detail.bean.HYXMMX;
import com.mrp.persistence.sysConfigure.project.bean.HYXMZD;
import com.mrp.persistence.sysConfigure.projectProduct.bean.HYXMCP;
import com.etsoft.pub.util.FileUtil;
import com.etsoft.pub.util.StringUtil;

import dwz.web.BaseController;

@Controller
@RequestMapping(value="/dailyBusiness")
public class ExcelStatementsController extends BaseController{
	@Autowired
	private ExcelStatementsServiceMgr excelStatements;

	@Autowired
	private sysConfigureServiceMgr sysConfigureMgr;
	
	@Autowired
	public HttpSession session;
	
	//--------------------------------------------------------------------------------------------------
	//描述:项目申请单导入
	//设计: ES
	//实现: ES
	//修改:
	//--------------------------------------------------------------------------------------------------
	@RequestMapping("/excelStatements")
	public String statementsList(Model model) {
		String F_YEAR = "";
		Calendar cal = Calendar.getInstance();
		java.util.Date currTime = new java.util.Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat formatter1 = new SimpleDateFormat("yyyyMMdd");
		F_YEAR = String.valueOf(cal.get(Calendar.YEAR));
		
		List<HYXMZD> hyxmList = excelStatements.searchHYXM(F_YEAR + "0101", formatter1.format(currTime), null, 0, 100);
		model.addAttribute("hyxmList", hyxmList);
		model.addAttribute("beginDate", F_YEAR + "-01-01");
		model.addAttribute("endDate", formatter.format(currTime));		
		return "/mrp/dailyBusiness/excelStatements/project/list";
	}
	
	//--------------------------------------------------------------------------------------------------
	//描述:项目申请单导入
	//设计: ES
	//实现: ES
	//修改:
	//--------------------------------------------------------------------------------------------------
	@RequestMapping("/excelStatements/upload")
	public void statementsUpload(HttpServletRequest request, HttpServletResponse response, Model model) {
		String fileName;
		String newFileName = null;
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;    
		String servletPath = request.getRealPath("") + "\\upload";
		String strFolder = "";
		Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();  
		java.util.Date currTime = new java.util.Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
		strFolder = formatter.format(currTime);		
		
		for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {    
             // 上传文件名    
             System.out.println("key: " + entity.getKey());    
             MultipartFile mf = entity.getValue();    
             fileName = mf.getOriginalFilename();           
             String uuid = StringUtil.nextId();// 返回一个随机UUID。
             String suffix = fileName.indexOf(".") != -1 ? fileName.substring(fileName.lastIndexOf("."), fileName.length()) : null;

             newFileName = servletPath + "\\" + strFolder + "\\"  + uuid + (suffix!=null?suffix:"");// 构成新文件名。
             
             //判断目录是否存在，每天上传的申请单都放到对应的一个目录下
             FileUtil.createDirectory(servletPath + "\\" + strFolder);
             File uploadFile = new File(newFileName);    
             try {  
                 FileCopyUtils.copy(mf.getBytes(), uploadFile); 
	         } catch (IOException e) {  
	             e.printStackTrace();  
	         }    
		}   
		try {
			response.getWriter().write(newFileName);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//--------------------------------------------------------------------------------------------------
	//描述:项目申请单Excel文件解析
	//设计: ES
	//实现: ES
	//修改:
	//--------------------------------------------------------------------------------------------------
	@RequestMapping("/excelStatements/statementsReslove")
	public ModelAndView statementsReslove(@RequestParam String FILE_NAME, Model model) {
		File file = new File(FILE_NAME);
		if(!file.exists()) ajaxDoneError("解析失败，上传文件不存在！");     
		String[] array = excelStatements.statementsReslove(FILE_NAME);
		String F_YEAR = "";
		Calendar cal = Calendar.getInstance();
		java.util.Date currTime = new java.util.Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat formatter1 = new SimpleDateFormat("yyyyMMdd");
		F_YEAR = String.valueOf(cal.get(Calendar.YEAR));
		
		List<HYXMZD> hyxmList = excelStatements.searchHYXM(F_YEAR + "0101", formatter1.format(currTime), null, 0, 100);
		model.addAttribute("hyxmList", hyxmList);
		model.addAttribute("beginDate", F_YEAR + "-01-01");
		model.addAttribute("endDate", formatter.format(currTime));	
		if(array[0].equals("0")) ajaxDoneError(array[1]);
		return ajaxDoneSuccess("文件解析成功！");
	}
	
	//--------------------------------------------------------------------------------------------------
	//描述:项目申请单导入基本信息查询
	//设计: ES
	//实现: ES
	//修改:
	//--------------------------------------------------------------------------------------------------
	@RequestMapping("/excelStatements/project/search")
	public String projectList(@RequestParam String beginDate, 
							  @RequestParam String endDate, 
							  @RequestParam String F_XMZT, 
							  @RequestParam String keywords, 
							  Model model) {
		String beginDateTemp = beginDate.replaceAll("-", "");
		String endDateTemp = endDate.replaceAll("-", "");
		List<HYXMZD> hyxmList = excelStatements.searchHYXM(beginDateTemp, endDateTemp, F_XMZT, keywords,0, 100);
		model.addAttribute("hyxmList", hyxmList);
		model.addAttribute("beginDate", beginDate);
		model.addAttribute("endDate", endDate);
		model.addAttribute("F_XMZT", F_XMZT);
		return "/mrp/dailyBusiness/excelStatements/project/list";
	}
	
	//--------------------------------------------------------------------------------------------------
	//描述:项目申请单导入
	//设计: ES
	//实现: ES
	//修改:
	//--------------------------------------------------------------------------------------------------
	@RequestMapping("/excelStatements/project/add")
	public String excelStatementsProjectAdd(Model model) {
		return "/mrp/dailyBusiness/excelStatements/project/importFilel";
	}

	//--------------------------------------------------------------------------------------------------
	//描述:编辑项目申请单
	//设计: ES
	//实现: ES
	//修改:
	//--------------------------------------------------------------------------------------------------
	@RequestMapping("/excelStatements/project/edit/{F_XMBH}")
	public String excelStatementsProjectEdit(@PathVariable("F_XMBH") String F_XMBH, Model model) {
		HYXMZD hyxmObject = excelStatements.getHYXM(F_XMBH);
		model.addAttribute("hyxmObject", hyxmObject);
		return "/mrp/dailyBusiness/excelStatements/project/edit";
	}

	//--------------------------------------------------------------------------------------------------
	//描述:项目申请单导入 ,保存项目申请单
	//设计: ES
	//实现: ES
	//修改:
	//--------------------------------------------------------------------------------------------------
	@RequestMapping(value = "/excelStatements/project/update/{F_XMBH}", method = RequestMethod.POST)
	public ModelAndView excelStatementsProjectUpdate(@PathVariable("F_XMBH") String F_XMBH, HYXMZD hyxmObject, Model model) {
		HYXMZD po = excelStatements.getHYXM(F_XMBH);
		if(po.getF_XMZT().equals("1")) {
			String msg = "材料申请单在保存之前，已由【" + po.getF_WCRBH() + "-" 
	                   + po.getF_WCRMC() + "】人员在【" + StringUtil.getDateString("yyyy-MM-dd hh:mm:ss", po.getF_WGSJ()) 
	                   + "】时对项目进行了完工操作，不能修改项目申请单！";
			return ajaxDoneError(msg);
		}
		hyxmObject.setF_XMBH(F_XMBH);
		excelStatements.updateHYXM(hyxmObject);
		hyxmObject = excelStatements.getHYXM(F_XMBH);
		model.addAttribute("hyxmObject", hyxmObject);
		return ajaxDoneSuccess("项目：" + po.getF_XMMC() + "保存成功！");
	}
	
	//--------------------------------------------------------------------------------------------------
	//描述:如果项目未完成，项目没有进行，则可以删除项目申请单
	//设计: ES
	//实现: ES
	//修改:
	//--------------------------------------------------------------------------------------------------
	@RequestMapping("/excelStatements/project/delete/{F_XMBH}")
	public ModelAndView excelStatementsProjectDelete(@PathVariable("F_XMBH") String F_XMBH) {
		HYXMZD po = excelStatements.getHYXM(F_XMBH);
		if(po.getF_XMZT().equals("1")) {
			String msg = "材料申请单明细在保存之前，已由【" + po.getF_WCRBH() + "-" 
	                   + po.getF_WCRMC() + "】人员在【" + StringUtil.getDateString("yyyy-MM-dd hh:mm:ss", po.getF_WGSJ()) 
	                   + "】时对项目进行了完工操作，不能删除项目申请单！";
			return ajaxDoneError(msg);
		}
		excelStatements.delHYXM(F_XMBH);
		return ajaxDoneSuccess("项目：" + po.getF_XMMC() + "删除成功！");
	}
	
	
	//--------------------------------------------------------------------------------------------------
	//描述:根据项目编号查看项目申请单明细
	//设计: ES
	//实现: ES
	//修改:
	//--------------------------------------------------------------------------------------------------
	@RequestMapping("/excelStatements/material/{F_XMBH}")
	public String statementsMaterialList(@PathVariable("F_XMBH") String F_XMBH, Model model) {
		HYXMZD hyxmObject = excelStatements.getHYXM(F_XMBH);
		List<HYXMMX> hyxmmxList = excelStatements.loadHYXMMX(F_XMBH, 0, 100);
		model.addAttribute("hyxmmxList", hyxmmxList);	
		model.addAttribute("F_XMBH", F_XMBH);	
		model.addAttribute("F_XMZT", hyxmObject.getF_XMZT());
		return "/mrp/dailyBusiness/excelStatements/material/list";
	}
	
	//--------------------------------------------------------------------------------------------------
	//描述:添加项目申请单明细
	//设计: ES
	//实现: ES
	//修改:
	//--------------------------------------------------------------------------------------------------
	@RequestMapping("/excelStatements/material/add/{F_XMBH}")
	public String statementsMaterialAdd(@PathVariable("F_XMBH") String F_XMBH, Model model) {		
		HYXMMX hyxmmxObject = new HYXMMX();
		hyxmmxObject.setF_XMBH(F_XMBH);
		model.addAttribute("hyxmmxObject", hyxmmxObject);		
		return "/mrp/dailyBusiness/excelStatements/material/add";
	}
	
	//--------------------------------------------------------------------------------------------------
	//描述:编辑项目申请单明细
	//设计: ES
	//实现: ES
	//修改:
	//--------------------------------------------------------------------------------------------------
	@RequestMapping("/excelStatements/material/edit/{F_CLBH}")
	public String statementsMaterialEdit(@RequestParam("F_XMBH") String F_XMBH, @PathVariable("F_CLBH") String F_CLBH, Model model) {
		HYXMMX hyxmmxObject = excelStatements.getHYXMMX(F_XMBH, F_CLBH);
		model.addAttribute("hyxmmxObject", hyxmmxObject);
		return "/mrp/dailyBusiness/excelStatements/material/edit";
	}
	
	//--------------------------------------------------------------------------------------------------
	//描述:新增项目申请单明细
	//设计: ES
	//实现: ES
	//修改:
	//--------------------------------------------------------------------------------------------------
	@RequestMapping(value = "/excelStatements/material/insert/{F_XMBH}", method = RequestMethod.POST)
	public ModelAndView statementsMaterialInsert(@PathVariable("F_XMBH") String F_XMBH,HYXMMX hyxmmxObject) {
		HYXMZD po = excelStatements.getHYXM(F_XMBH);
		if(po.getF_XMZT().equals("1")) {
			String msg = "材料申请单明细在保存之前，已由【" + po.getF_WCRBH() + "-" 
			           + po.getF_WCRMC() + "】人员在【" + StringUtil.getDateString("yyyy-MM-dd hh:mm:ss", po.getF_WGSJ()) 
			           + "】时对项目进行了完工操作，不能修改项目申请单明细！";
			return ajaxDoneError(msg);
		}
		hyxmmxObject.setF_XMBH(F_XMBH);
		HYXMMX xmmx = excelStatements.getHYXMMX(F_XMBH, hyxmmxObject.getF_CLBH());
		if(xmmx != null) {
			return ajaxDoneError("项目编号：" + F_XMBH + "<br>材料编号：" + hyxmmxObject.getF_CLBH() + "<br>已经存在，不允许添加！");
		}
		excelStatements.addHYXMMX(hyxmmxObject);
		return ajaxDoneSuccess("项目编号：" + F_XMBH + "<br>材料编号：" + hyxmmxObject.getF_CLBH() + "<br>添加成功！");
	}
	
	//--------------------------------------------------------------------------------------------------
	//描述:更新项目申请单明细
	//设计: ES
	//实现: ES
	//修改:
	//--------------------------------------------------------------------------------------------------
	@RequestMapping(value = "/excelStatements/material/update/{F_XMBH}", method = RequestMethod.POST)
	public ModelAndView statementsMaterialUpdate(@PathVariable("F_XMBH") String F_XMBH,HYXMMX hyxmmxObject, Model model) {
		HYXMZD po = excelStatements.getHYXM(F_XMBH);
		if(po.getF_XMZT().equals("1")) {
			String msg = "材料申请单明细在保存之前，已由【" + po.getF_WCRBH() + "-" 
	                   + po.getF_WCRMC() + "】人员在【" + StringUtil.getDateString("yyyy-MM-dd hh:mm:ss", po.getF_WGSJ()) 
	                   + "】时对项目进行了完工操作，不能修改项目申请单明细！";
			return ajaxDoneError(msg);
		}
		hyxmmxObject.setF_XMBH(F_XMBH);
		excelStatements.updateHYXMMX(hyxmmxObject);
		hyxmmxObject = excelStatements.getHYXMMX(F_XMBH, hyxmmxObject.getF_CLBH());
		model.addAttribute("hyxmmxObject", hyxmmxObject);
		return ajaxDoneSuccess("项目编号：" + F_XMBH + "<br>材料编号：" + hyxmmxObject.getF_CLBH() + "<br>修改成功！");
	}
	
	//--------------------------------------------------------------------------------------------------
	//描述:删除项目申请单明细
	//设计: ES
	//实现: ES
	//修改:
	//--------------------------------------------------------------------------------------------------
	@RequestMapping("/excelStatements/material/delete/{F_XMBH}")
	public ModelAndView statementsMaterialDelete(@PathVariable("F_XMBH") String F_XMBH, @RequestParam("F_CLBH") String F_CLBH) {
		HYXMZD po = excelStatements.getHYXM(F_XMBH);
		if(po.getF_XMZT().equals("1")) {
			String msg = "材料申请单明细在保存之前，已由【" + po.getF_WCRBH() + "-" 
	                   + po.getF_WCRMC() + "】人员在【" + StringUtil.getDateString("yyyy-MM-dd hh:mm:ss", po.getF_WGSJ()) 
	                   + "】时对项目进行了完工操作，不能删除项目申请单明细！";
			return ajaxDoneError(msg);
		}
		excelStatements.delHYXMMX(F_XMBH, F_CLBH);
		return ajaxDoneSuccess("项目编号：" + F_XMBH + "<br>材料编号：" + F_CLBH + "<br>删除成功！");
	}
	
	//--------------------------------------------------------------------------------------------------
	//描述:项目申请单明细基本信息模糊查询
	//设计: ES
	//实现: ES
	//修改:
	//--------------------------------------------------------------------------------------------------
	@RequestMapping("/excelStatements/material/search/{F_XMBH}")
	public String statementsMaterialList(@PathVariable("F_XMBH") String F_XMBH, 
							  		     @RequestParam String keywords, 
							  		     Model model) {
		HYXMZD hyxmObject = excelStatements.getHYXM(F_XMBH);
		List<HYXMMX> hyxmmxList = excelStatements.searchHYXMMX(F_XMBH, keywords,0, 100);
		model.addAttribute("hyxmmxList", hyxmmxList);
		model.addAttribute("F_XMBH", F_XMBH);
		model.addAttribute("F_XMZT", hyxmObject.getF_XMZT());
		return "/mrp/dailyBusiness/excelStatements/material/list";
	}
	
	//////////////////////////////
	//--------------------------------------------------------------------------------------------------
	//描述:根据项目编号查看项目申请单明细
	//设计: ES
	//实现: ES
	//修改:
	//--------------------------------------------------------------------------------------------------
	@RequestMapping("/excelStatements/product/{F_XMBH}")
	public String statementsProductList(@PathVariable("F_XMBH") String F_XMBH, Model model) {
		HYXMZD hyxmObject = excelStatements.getHYXM(F_XMBH);
		List<HYXMCP> productList = sysConfigureMgr.searchHYXMCP(F_XMBH, 0, 100);
		model.addAttribute("productList", productList);		
		model.addAttribute("F_XMBH", F_XMBH);	
		model.addAttribute("F_XMZT", hyxmObject.getF_XMZT());
		return "/mrp/dailyBusiness/excelStatements/product/list";
	}
	
	//--------------------------------------------------------------------------------------------------
	//描述:添加项目申请单明细
	//设计: ES
	//实现: ES
	//修改:
	//--------------------------------------------------------------------------------------------------
	@RequestMapping("/excelStatements/product/add/{F_XMBH}")
	public String statementsProductAdd(@PathVariable("F_XMBH") String F_XMBH, Model model) {		
		HYXMZD hyxmObject = sysConfigureMgr.getHYXM(F_XMBH);
		HYXMCP hyxmcpObject = new HYXMCP();
		hyxmcpObject.setF_XMBH(F_XMBH);
		hyxmcpObject.setF_XMMC(hyxmObject.getF_XMMC());
		model.addAttribute("hyxmcpObject", hyxmcpObject);
		return "/mrp/dailyBusiness/excelStatements/product/add";
	}
	
	//--------------------------------------------------------------------------------------------------
	//描述:新增项目申请单明细
	//设计: ES
	//实现: ES
	//修改:
	//--------------------------------------------------------------------------------------------------
	@RequestMapping(value = "/excelStatements/product/insert/{F_XMBH}", method = RequestMethod.POST)
	public ModelAndView statementsProductInsert(@PathVariable("F_XMBH") String F_XMBH,HYXMCP hyxmcpObject) {
		HYXMZD po = excelStatements.getHYXM(F_XMBH);
		if(po.getF_XMZT().equals("1")) {
			String msg = "材料申请单明细在保存之前，已由【" + po.getF_WCRBH() + "-" 
			           + po.getF_WCRMC() + "】人员在【" + StringUtil.getDateString("yyyy-MM-dd hh:mm:ss", po.getF_WGSJ()) 
			           + "】时对项目进行了完工操作，不能修改项目申请单明细！";
			return ajaxDoneError(msg);
		}
		hyxmcpObject.setF_XMBH(F_XMBH);
		HYXMCP xmcp = sysConfigureMgr.getHYXMCP(hyxmcpObject.getF_XMBH(), hyxmcpObject.getF_CPBH());
		if(xmcp != null) {
			return ajaxDoneError("项目编号：" + F_XMBH + "<br>产品编号：" + hyxmcpObject.getF_CPBH() + "<br>已经存在，不允许添加！");
		}
		sysConfigureMgr.addHYXMCP(hyxmcpObject);
		return ajaxDoneSuccess("项目编号：" + F_XMBH + "<br>产品编号：" + hyxmcpObject.getF_CPBH() + "<br>添加成功！");
	}
	
	//--------------------------------------------------------------------------------------------------
	//描述:删除项目申请单明细
	//设计: ES
	//实现: ES
	//修改:
	//--------------------------------------------------------------------------------------------------
	@RequestMapping("/excelStatements/product/delete/{F_CPBH}")
	public ModelAndView statementsProductDelete(@PathVariable("F_CPBH") String F_CPBH, @RequestParam("F_XMBH") String F_XMBH) {
		sysConfigureMgr.delHYXMCP(F_XMBH, F_CPBH);
		return ajaxDoneSuccess("项目编号：" + F_XMBH + "<br>产品编号：" + F_CPBH + "<br>删除成功！");
	}
	
	//--------------------------------------------------------------------------------------------------
	//描述:项目申请单明细基本信息模糊查询
	//设计: ES
	//实现: ES
	//修改:
	//--------------------------------------------------------------------------------------------------
	@RequestMapping("/excelStatements/product/search/{F_XMBH}")
	public String statementsProductList(@PathVariable("F_XMBH") String F_XMBH, 
							  		    @RequestParam String F_CPBH, 
							  		    Model model) {
		HYXMZD hyxmObject = excelStatements.getHYXM(F_XMBH);
		List<HYXMCP> productList = sysConfigureMgr.searchHYXMCP(F_XMBH, F_CPBH, 0, 100);
		model.addAttribute("productList", productList);
		model.addAttribute("F_XMBH", F_XMBH);
		model.addAttribute("F_CPBH", F_CPBH);
		model.addAttribute("F_XMZT", hyxmObject.getF_XMZT());
		return "/mrp/dailyBusiness/excelStatements/product/list";
	}
}
