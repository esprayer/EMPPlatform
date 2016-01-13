package com.mrp.web.queryAnalyse;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.etsoft.pub.enums.EMPQueryParamEnum;
import com.etsoft.pub.util.EMPExportExcelMultiTable;
import com.etsoft.pub.util.EMPExportExcelStatement;
import com.etsoft.pub.util.EMPExportExcelTable;
import com.mrp.biz.queryAnalyse.QueryAnalyseServiceMgr;
import com.mrp.biz.sysconfigure.sysConfigureServiceMgr;
import com.mrp.persistence.sysConfigure.deport.bean.HYCKZD;
import com.mrp.persistence.sysConfigure.material.bean.HYCLZD;
import com.mrp.persistence.sysConfigure.product.bean.HYCPZD;
import com.mrp.persistence.sysConfigure.project.bean.HYXMZD;
import dwz.web.BaseController;
import esyt.framework.com.bridge.JResponseObject;
import esyt.framework.com.builder.base.data.EFDataSet;
import esyt.framework.com.builder.base.data.EFRowSet;
import esyt.framework.com.eai.data.JParamObject;

@Controller
@RequestMapping(value="/queryAnalyse/action/export")
public class EMPQueryAnalyseExportLCActionController extends BaseController{
	@Autowired
	private QueryAnalyseServiceMgr queryAnalyseServiceMgr;
	
	@Autowired
	private sysConfigureServiceMgr        sysConfigureMgr;
	
	@Autowired
	public HttpSession session;

	/**
	 * 库存查询材料联查项目结转
	 * @param F_CKBH
	 * @param model
	 * @return
	 */
	@RequestMapping("/projectCarryover")
	public ModelAndView projectCarryover(@RequestParam String F_CKBH, @RequestParam String F_CLBH, 
			                             @RequestParam String F_DWBH, @RequestParam String F_CSBH, 
			                             @RequestParam String F_CLDJ, @RequestParam String F_XMZT, 
			                             HttpServletRequest request, HttpServletResponse response, 
			                             @RequestParam String F_XMBH, Model model) {
		JParamObject           po = JParamObject.Create();
		HYCLZD               clzd = sysConfigureMgr.getHYCL(F_CLBH);
		HYCKZD               ckzd = sysConfigureMgr.getHYCKZD(F_CKBH);
		List<String>  subHeadList = new ArrayList<String>();
		EFDataSet        columnDS = EFDataSet.getInstance();
		EFRowSet         columnRS = null;
		EFRowSet          queryRS = null;
		EFDataSet         queryDS = null;
		
		po.SetValueByParamName("F_CKBH", F_CKBH);
		po.SetValueByParamName("F_CLBH", F_CLBH);
		po.SetValueByParamName("F_DWBH", F_DWBH);
		po.SetValueByParamName("F_CSBH", F_CSBH);
		po.SetValueByParamName("F_XMBH", F_XMBH);
		po.SetValueByParamName("F_XMZT", F_XMZT);
		po.SetValueByParamName("F_CLDJ", F_CLDJ);
		
		JResponseObject        RO = queryAnalyseServiceMgr.projectMaterialCarryover(po);
		queryRS = (EFRowSet) RO.getResponseObject();
		queryDS = queryRS.getDataSet(EMPQueryParamEnum.QUERYRESULT);
		
		try {
			//副标题
			subHeadList.add("仓库名称：" + F_CKBH + "-" + ckzd.getF_CKMC());
			subHeadList.add("材料名称：" + F_CLBH + "-" + clzd.getF_CLMC());

			columnRS = EFRowSet.getInstance();
			columnRS.putString("COLUMN_ID", "F_XMBH");
			columnRS.putString("COLUMN_ALIGN", "LEFT");
			columnRS.putString("COLUMN_NAME", "项目编号");
			columnRS.putString("COLUMN_WIDTH", "150");
			columnRS.putString("COLUMN_TYPE", "C");
			columnDS.insertRowSet(columnRS);
			
			columnRS = EFRowSet.getInstance();
			columnRS.putString("COLUMN_ID", "F_XMMC");
			columnRS.putString("COLUMN_ALIGN", "LEFT");
			columnRS.putString("COLUMN_NAME", "项目名称");
			columnRS.putString("COLUMN_WIDTH", "200");
			columnRS.putString("COLUMN_TYPE", "C");
			columnDS.insertRowSet(columnRS);
			
			columnRS = EFRowSet.getInstance();
			columnRS.putString("COLUMN_ID", "F_RKSL");
			columnRS.putString("COLUMN_ALIGN", "RIGHT");
			columnRS.putString("COLUMN_NAME", "入库数量");
			columnRS.putString("COLUMN_WIDTH", "100");
			columnRS.putString("COLUMN_TYPE", "N");
			columnRS.putString("COLUMN_DECN", "2");
			columnDS.insertRowSet(columnRS);
			
			columnRS = EFRowSet.getInstance();
			columnRS.putString("COLUMN_ID", "F_RKSL");
			columnRS.putString("COLUMN_ALIGN", "RIGHT");
			columnRS.putString("COLUMN_NAME", "出库数量");
			columnRS.putString("COLUMN_WIDTH", "100");
			columnRS.putString("COLUMN_TYPE", "N");
			columnRS.putString("COLUMN_DECN", "2");
			columnDS.insertRowSet(columnRS);

			columnRS = EFRowSet.getInstance();
			columnRS.putString("COLUMN_ID", "F_CLTHSL");
			columnRS.putString("COLUMN_ALIGN", "RIGHT");
			columnRS.putString("COLUMN_NAME", "材料退货数量");
			columnRS.putString("COLUMN_WIDTH", "100");
			columnRS.putString("COLUMN_TYPE", "N");
			columnRS.putString("COLUMN_DECN", "2");
			columnDS.insertRowSet(columnRS);
			
			columnRS = EFRowSet.getInstance();
			columnRS.putString("COLUMN_ID", "F_CSTHSL");
			columnRS.putString("COLUMN_ALIGN", "RIGHT");
			columnRS.putString("COLUMN_NAME", "厂商退货数量");
			columnRS.putString("COLUMN_WIDTH", "100");
			columnRS.putString("COLUMN_TYPE", "N");
			columnRS.putString("COLUMN_DECN", "2");
			columnDS.insertRowSet(columnRS);
			
			columnRS = EFRowSet.getInstance();
			columnRS.putString("COLUMN_ID", "F_DBSL");
			columnRS.putString("COLUMN_ALIGN", "RIGHT");
			columnRS.putString("COLUMN_NAME", "调拨数量");
			columnRS.putString("COLUMN_WIDTH", "100");
			columnRS.putString("COLUMN_TYPE", "N");
			columnRS.putString("COLUMN_DECN", "2");
			columnDS.insertRowSet(columnRS);
			
			columnRS = EFRowSet.getInstance();
			columnRS.putString("COLUMN_ID", "F_JZSL");
			columnRS.putString("COLUMN_ALIGN", "RIGHT");
			columnRS.putString("COLUMN_NAME", "结转数量");
			columnRS.putString("COLUMN_WIDTH", "100");
			columnRS.putString("COLUMN_TYPE", "N");
			columnRS.putString("COLUMN_DECN", "2");
			columnDS.insertRowSet(columnRS);

			//下载
			EMPExportExcelTable exportExcelTable = new EMPExportExcelTable();
			exportExcelTable.exportExcelTable(request, response, queryDS, columnDS, subHeadList, "库 存 材 料 联 查 项 目 查 询 分 析");			
		} catch (Exception e) {

			e.printStackTrace();
		}
		return ajaxDoneError("导出成功！");
	}
	
	/**
	 * 库存查询材料联查项目结转
	 * @param F_CKBH
	 * @param model
	 * @return
	 */
	@RequestMapping("/projectProductCarryover")
	public ModelAndView projectProductCarryover(@RequestParam String F_CKBH, @RequestParam String F_CLBH, 
			                                    @RequestParam String F_DWBH, @RequestParam String F_CSBH, 
			                                    @RequestParam String F_CLDJ, @RequestParam String F_XMZT, 
			                                    @RequestParam String F_XMBH, @RequestParam String F_CPBH, 
			                                    HttpServletRequest request, HttpServletResponse response, 
			                                    Model model) {
		JParamObject           po = JParamObject.Create();
		HYCLZD               clzd = sysConfigureMgr.getHYCL(F_CLBH);
		HYCKZD               ckzd = sysConfigureMgr.getHYCKZD(F_CKBH);
		HYXMZD               xmzd = sysConfigureMgr.getHYXM(F_XMBH);
		List<String>  subHeadList = new ArrayList<String>();
		EFDataSet        columnDS = EFDataSet.getInstance();
		EFRowSet         columnRS = null;
		EFRowSet          queryRS = null;
		EFDataSet         queryDS = null;
		
		po.SetValueByParamName("F_CKBH", F_CKBH);
		po.SetValueByParamName("F_CLBH", F_CLBH);
		po.SetValueByParamName("F_DWBH", F_DWBH);
		po.SetValueByParamName("F_CSBH", F_CSBH);
		po.SetValueByParamName("F_CPBH", F_CPBH);
		po.SetValueByParamName("F_XMBH", F_XMBH);
		po.SetValueByParamName("F_XMZT", F_XMZT);
		po.SetValueByParamName("F_CLDJ", F_CLDJ);
		
		JResponseObject        RO = queryAnalyseServiceMgr.projectProductCarryoverDetails(po);
		queryRS = (EFRowSet) RO.getResponseObject();
		queryDS = queryRS.getDataSet(EMPQueryParamEnum.QUERYRESULT);
		
		try {
			
			//副标题
			subHeadList.add("仓库名称：" + F_CKBH + "-" + ckzd.getF_CKMC());
			subHeadList.add("项目名称：" + F_XMBH + "-" + xmzd.getF_XMMC());
			subHeadList.add("材料名称：" + F_CLBH + "-" + clzd.getF_CLMC());

			columnRS = EFRowSet.getInstance();
			columnRS.putString("COLUMN_ID", "F_CPBH");
			columnRS.putString("COLUMN_ALIGN", "LEFT");
			columnRS.putString("COLUMN_NAME", "产品编号");
			columnRS.putString("COLUMN_WIDTH", "100");
			columnRS.putString("COLUMN_TYPE", "C");
			columnDS.insertRowSet(columnRS);
			
			columnRS = EFRowSet.getInstance();
			columnRS.putString("COLUMN_ID", "F_CPMC");
			columnRS.putString("COLUMN_ALIGN", "LEFT");
			columnRS.putString("COLUMN_NAME", "产品名称");
			columnRS.putString("COLUMN_WIDTH", "100");
			columnRS.putString("COLUMN_TYPE", "C");
			columnDS.insertRowSet(columnRS);
			
			columnRS = EFRowSet.getInstance();
			columnRS.putString("COLUMN_ID", "F_CLDJ");
			columnRS.putString("COLUMN_ALIGN", "RIGHT");
			columnRS.putString("COLUMN_NAME", "材料单价");
			columnRS.putString("COLUMN_WIDTH", "100");
			columnRS.putString("COLUMN_TYPE", "N");
			columnRS.putString("COLUMN_DECN", "2");
			columnDS.insertRowSet(columnRS);
			
			columnRS = EFRowSet.getInstance();
			columnRS.putString("COLUMN_ID", "F_RKSL");
			columnRS.putString("COLUMN_ALIGN", "RIGHT");
			columnRS.putString("COLUMN_NAME", "入库数量");
			columnRS.putString("COLUMN_WIDTH", "100");
			columnRS.putString("COLUMN_TYPE", "N");
			columnRS.putString("COLUMN_DECN", "2");
			columnDS.insertRowSet(columnRS);
			
			columnRS = EFRowSet.getInstance();
			columnRS.putString("COLUMN_ID", "F_CKSL");
			columnRS.putString("COLUMN_ALIGN", "RIGHT");
			columnRS.putString("COLUMN_NAME", "出库数量");
			columnRS.putString("COLUMN_WIDTH", "100");
			columnRS.putString("COLUMN_TYPE", "N");
			columnRS.putString("COLUMN_DECN", "2");
			columnDS.insertRowSet(columnRS);

			columnRS = EFRowSet.getInstance();
			columnRS.putString("COLUMN_ID", "F_CLTHSL");
			columnRS.putString("COLUMN_ALIGN", "RIGHT");
			columnRS.putString("COLUMN_NAME", "材料退货数量");
			columnRS.putString("COLUMN_WIDTH", "100");
			columnRS.putString("COLUMN_TYPE", "N");
			columnRS.putString("COLUMN_DECN", "2");
			columnDS.insertRowSet(columnRS);
			
			columnRS = EFRowSet.getInstance();
			columnRS.putString("COLUMN_ID", "F_CSTHSL");
			columnRS.putString("COLUMN_ALIGN", "RIGHT");
			columnRS.putString("COLUMN_NAME", "厂商退货数量");
			columnRS.putString("COLUMN_WIDTH", "100");
			columnRS.putString("COLUMN_TYPE", "N");
			columnRS.putString("COLUMN_DECN", "2");
			columnDS.insertRowSet(columnRS);
			
			columnRS = EFRowSet.getInstance();
			columnRS.putString("COLUMN_ID", "F_DBSL");
			columnRS.putString("COLUMN_ALIGN", "RIGHT");
			columnRS.putString("COLUMN_NAME", "调拨数量");
			columnRS.putString("COLUMN_WIDTH", "100");
			columnRS.putString("COLUMN_TYPE", "N");
			columnRS.putString("COLUMN_DECN", "2");
			columnDS.insertRowSet(columnRS);

			columnRS = EFRowSet.getInstance();
			columnRS.putString("COLUMN_ID", "F_JZSL");
			columnRS.putString("COLUMN_ALIGN", "RIGHT");
			columnRS.putString("COLUMN_NAME", "结转数量");
			columnRS.putString("COLUMN_WIDTH", "100");
			columnRS.putString("COLUMN_TYPE", "N");
			columnRS.putString("COLUMN_DECN", "2");
			columnDS.insertRowSet(columnRS);
			
			//下载
			EMPExportExcelTable exportExcelTable = new EMPExportExcelTable();
			exportExcelTable.exportExcelTable(request, response, queryDS, columnDS, subHeadList, "库 存 材 料 联 查 项 目 产 品 查 询 分 析");			
		} catch (Exception e) {

			e.printStackTrace();
		}
		return ajaxDoneError("导出成功！");
	}
	
	/**
	 * 联查项目申请单
	 * @param F_CKBH
	 * @param model
	 * @return
	 */
	@RequestMapping("/projectApplicationForm")
	public ModelAndView projectApplicationForm(@RequestParam String F_XMBH, Model model) {
		return ajaxDoneError("导出成功！");
	}
	
	/**
	 * 结转明细
	 * @param F_CKBH
	 * @param model
	 * @return
	 */
	@RequestMapping("/carryoverDetails")
	public ModelAndView carryoverDetails(@RequestParam String F_CKBH, @RequestParam String F_CLBH, 
					               @RequestParam String F_DWBH, @RequestParam String F_CSBH, 
					               @RequestParam String F_CLDJ, @RequestParam String F_XMZT, 
					               @RequestParam String F_XMBH, @RequestParam String F_CPBH, 
					               HttpServletRequest request, HttpServletResponse response, 
					               Model model) {
		
		JParamObject           po = JParamObject.Create();
		List<String>  subHeadList = new ArrayList<String>();
		HYCLZD               clzd = sysConfigureMgr.getHYCL(F_CLBH);
		HYCKZD               ckzd = sysConfigureMgr.getHYCKZD(F_CKBH);
		HYXMZD               xmzd = sysConfigureMgr.getHYXM(F_XMBH);
		HYCPZD               cpzd = sysConfigureMgr.getHYCP(F_CPBH);
		EFRowSet          queryRS = null;
		EFDataSet         queryDS = null;
		
		po.SetValueByParamName("F_CKBH", F_CKBH);
		po.SetValueByParamName("F_CLBH", F_CLBH);
		po.SetValueByParamName("F_DWBH", F_DWBH);
		po.SetValueByParamName("F_CSBH", F_CSBH);
		po.SetValueByParamName("F_CPBH", F_CPBH);
		po.SetValueByParamName("F_XMBH", F_XMBH);
		po.SetValueByParamName("F_XMZT", F_XMZT);
		po.SetValueByParamName("F_CLDJ", F_CLDJ);
		
		JResponseObject        RO = queryAnalyseServiceMgr.carryoverDetails(po);
		queryRS = (EFRowSet) RO.getResponseObject();
		queryDS = queryRS.getDataSet(EMPQueryParamEnum.QUERYRESULT);
		
		try {
			//副标题
			if(ckzd != null) subHeadList.add("仓库名称：" + F_CKBH + "-" + ckzd.getF_CKMC());
			if(xmzd != null) subHeadList.add("项目名称：" + F_XMBH + "-" + xmzd.getF_XMMC());
			if(clzd != null) subHeadList.add("材料名称：" + F_CLBH + "-" + clzd.getF_CLMC());
			if(cpzd != null) subHeadList.add("产品名称：" + F_CPBH + "-" + cpzd.getF_CPMC());

			//下载
			EMPExportExcelMultiTable exportExcelMultiTable = new EMPExportExcelMultiTable();
			exportExcelMultiTable.exportExcelTable(request, response, queryDS, subHeadList, "结 转 明 细");			
		} catch (Exception e) {

			e.printStackTrace();
		}
		return ajaxDoneError("导出成功！");
	}
	
	/**
	 * 材料明细联查项目查询
	 * @param F_CKBH
	 * @param model
	 * @return
	 */
	@RequestMapping("/materialDetailsLCProject")
	public ModelAndView materialDetailsLCProject(@RequestParam String beginDate, @RequestParam String endDate, 
									       @RequestParam String F_CKBH, @RequestParam String F_CLBH, 
									       @RequestParam String F_DWBH, @RequestParam String F_CSBH, 
									       @RequestParam String F_XMBH, @RequestParam String F_CLDJ, 
									       HttpServletRequest request, HttpServletResponse response, 
									       Model model) {
		JParamObject           po = JParamObject.Create();
		HYCKZD               ckzd = sysConfigureMgr.getHYCKZD(F_CKBH);
		List<String>  subHeadList = new ArrayList<String>();
		EFDataSet        columnDS = EFDataSet.getInstance();
		EFRowSet         columnRS = null;
		EFRowSet          queryRS = null;
		EFDataSet         queryDS = null;
		
		po.SetValueByParamName("beginDate", beginDate);
		po.SetValueByParamName("endDate", endDate);
		po.SetValueByParamName("F_CKBH", F_CKBH);
		po.SetValueByParamName("F_CLBH", F_CLBH);
		po.SetValueByParamName("F_DWBH", F_DWBH);
		po.SetValueByParamName("F_CSBH", F_CSBH);
		po.SetValueByParamName("F_CLDJ", F_CLDJ);
		po.SetValueByParamName("F_XMBH", F_XMBH);
		
		JResponseObject        RO = queryAnalyseServiceMgr.materialDetailsLCXMCP(po);
		queryRS = (EFRowSet) RO.getResponseObject();
		queryDS = queryRS.getDataSet(EMPQueryParamEnum.QUERYRESULT);
		
		try {
			
			//副标题
			subHeadList.add("查询日期：" + beginDate.replaceAll("-", ".") + "-" + endDate.replaceAll("-", "."));
			if(ckzd == null) {
				subHeadList.add("仓库名称：" + F_CKBH);
			} else {
				subHeadList.add("仓库名称：" + F_CKBH + "-" + ckzd.getF_CKMC());
			}
			
			columnRS = EFRowSet.getInstance();
			columnRS.putString("COLUMN_ID", "F_XMBH");
			columnRS.putString("COLUMN_ALIGN", "LEFT");
			columnRS.putString("COLUMN_NAME", "项目编号");
			columnRS.putString("COLUMN_WIDTH", "150");
			columnRS.putString("COLUMN_TYPE", "C");
			columnDS.insertRowSet(columnRS);
			
			columnRS = EFRowSet.getInstance();
			columnRS.putString("COLUMN_ID", "F_XMMC");
			columnRS.putString("COLUMN_ALIGN", "LEFT");
			columnRS.putString("COLUMN_NAME", "项目名称");
			columnRS.putString("COLUMN_WIDTH", "200");
			columnRS.putString("COLUMN_TYPE", "C");
			columnDS.insertRowSet(columnRS);
			
			columnRS = EFRowSet.getInstance();
			columnRS.putString("COLUMN_ID", "F_XMZT");
			columnRS.putString("COLUMN_ALIGN", "LEFT");
			columnRS.putString("COLUMN_NAME", "项目状态");
			columnRS.putString("COLUMN_MASK", "0:未完成;1:已完成");
			columnRS.putString("COLUMN_WIDTH", "100");
			columnRS.putString("COLUMN_TYPE", "C");
			columnDS.insertRowSet(columnRS);

			columnRS = EFRowSet.getInstance();
			columnRS.putString("COLUMN_ID", "F_RKSL");
			columnRS.putString("COLUMN_ALIGN", "RIGHT");
			columnRS.putString("COLUMN_NAME", "入库数量");
			columnRS.putString("COLUMN_WIDTH", "100");
			columnRS.putString("COLUMN_TYPE", "N");
			columnRS.putString("COLUMN_DECN", "2");
			columnDS.insertRowSet(columnRS);
			
			columnRS = EFRowSet.getInstance();
			columnRS.putString("COLUMN_ID", "F_CKSL");
			columnRS.putString("COLUMN_ALIGN", "RIGHT");
			columnRS.putString("COLUMN_NAME", "出库数量");
			columnRS.putString("COLUMN_WIDTH", "100");
			columnRS.putString("COLUMN_TYPE", "N");
			columnRS.putString("COLUMN_DECN", "2");
			columnDS.insertRowSet(columnRS);

			columnRS = EFRowSet.getInstance();
			columnRS.putString("COLUMN_ID", "F_CLTHSL");
			columnRS.putString("COLUMN_ALIGN", "RIGHT");
			columnRS.putString("COLUMN_NAME", "材料退货数量");
			columnRS.putString("COLUMN_WIDTH", "100");
			columnRS.putString("COLUMN_TYPE", "N");
			columnRS.putString("COLUMN_DECN", "2");
			columnDS.insertRowSet(columnRS);
			
			columnRS = EFRowSet.getInstance();
			columnRS.putString("COLUMN_ID", "F_CSTHSL");
			columnRS.putString("COLUMN_ALIGN", "RIGHT");
			columnRS.putString("COLUMN_NAME", "厂商退货数量");
			columnRS.putString("COLUMN_WIDTH", "100");
			columnRS.putString("COLUMN_TYPE", "N");
			columnRS.putString("COLUMN_DECN", "2");
			columnDS.insertRowSet(columnRS);
			
			columnRS = EFRowSet.getInstance();
			columnRS.putString("COLUMN_ID", "F_DBSL");
			columnRS.putString("COLUMN_ALIGN", "RIGHT");
			columnRS.putString("COLUMN_NAME", "调拨数量");
			columnRS.putString("COLUMN_WIDTH", "100");
			columnRS.putString("COLUMN_TYPE", "N");
			columnRS.putString("COLUMN_DECN", "2");
			columnDS.insertRowSet(columnRS);

			columnRS = EFRowSet.getInstance();
			columnRS.putString("COLUMN_ID", "F_JZSL");
			columnRS.putString("COLUMN_ALIGN", "RIGHT");
			columnRS.putString("COLUMN_NAME", "结转数量");
			columnRS.putString("COLUMN_WIDTH", "100");
			columnRS.putString("COLUMN_TYPE", "N");
			columnRS.putString("COLUMN_DECN", "2");
			columnDS.insertRowSet(columnRS);
			
			//下载
			EMPExportExcelTable exportExcelTable = new EMPExportExcelTable();
			exportExcelTable.exportExcelTable(request, response, queryDS, columnDS, subHeadList, "材 料 联 查 项 目 查 询 分 析");			
		} catch (Exception e) {

			e.printStackTrace();
		}
		return ajaxDoneError("导出成功！");
	}
	
	/**
	 * 材料明细联查产品查询
	 * @param F_CKBH
	 * @param model
	 * @return
	 */
	@RequestMapping("/materialDetailsLCProduct")
	public ModelAndView materialDetailsLCProduct(@RequestParam String beginDate, @RequestParam String endDate, 
									             @RequestParam String F_CKBH, @RequestParam String F_CLBH, 
									             @RequestParam String F_DWBH, @RequestParam String F_CSBH, 
									             @RequestParam String F_XMBH, @RequestParam String F_CLDJ, 
									             HttpServletRequest request, HttpServletResponse response, 
									             @RequestParam String F_CPBH, Model model) {
		JParamObject           po = JParamObject.Create();
		HYCLZD               clzd = sysConfigureMgr.getHYCL(F_CLBH);
		HYCKZD               ckzd = sysConfigureMgr.getHYCKZD(F_CKBH);
		HYXMZD               xmzd = sysConfigureMgr.getHYXM(F_XMBH);
		List<String>  subHeadList = new ArrayList<String>();
		EFDataSet        columnDS = EFDataSet.getInstance();
		EFRowSet         columnRS = null;
		EFRowSet          queryRS = null;
		EFDataSet         queryDS = null;
		
		po.SetValueByParamName("beginDate", beginDate);
		po.SetValueByParamName("endDate", endDate);
		po.SetValueByParamName("F_CKBH", F_CKBH);
		po.SetValueByParamName("F_CLBH", F_CLBH);
		po.SetValueByParamName("F_DWBH", F_DWBH);
		po.SetValueByParamName("F_CSBH", F_CSBH);
		po.SetValueByParamName("F_CLDJ", F_CLDJ);
		po.SetValueByParamName("F_XMBH", F_XMBH);
		po.SetValueByParamName("F_CPBH", F_CPBH);
		po.SetValueByParamName("LCFLAG", "LCCP");
		
		JResponseObject        RO = queryAnalyseServiceMgr.materialDetailsLCXMCP(po);
		queryRS = (EFRowSet) RO.getResponseObject();
		queryDS = queryRS.getDataSet(EMPQueryParamEnum.QUERYRESULT);
		
		try {
			
			//副标题
			subHeadList.add("查询日期：" + beginDate.replaceAll("-", ".") + "-" + endDate.replaceAll("-", "."));
			subHeadList.add("仓库名称：" + F_CKBH + "-" + ckzd.getF_CKMC());
			subHeadList.add("项目名称：" + F_XMBH + "-" + xmzd.getF_XMMC());
			subHeadList.add("材料名称：" + F_CLBH + "-" + clzd.getF_CLMC());
			
			columnRS = EFRowSet.getInstance();
			columnRS.putString("COLUMN_ID", "F_CPBH");
			columnRS.putString("COLUMN_ALIGN", "LEFT");
			columnRS.putString("COLUMN_NAME", "产品编号");
			columnRS.putString("COLUMN_WIDTH", "150");
			columnRS.putString("COLUMN_TYPE", "C");
			columnDS.insertRowSet(columnRS);
			
			columnRS = EFRowSet.getInstance();
			columnRS.putString("COLUMN_ID", "F_CPMC");
			columnRS.putString("COLUMN_ALIGN", "LEFT");
			columnRS.putString("COLUMN_NAME", "产品名称");
			columnRS.putString("COLUMN_WIDTH", "200");
			columnRS.putString("COLUMN_TYPE", "C");
			columnDS.insertRowSet(columnRS);

			columnRS = EFRowSet.getInstance();
			columnRS.putString("COLUMN_ID", "F_RKSL");
			columnRS.putString("COLUMN_ALIGN", "RIGHT");
			columnRS.putString("COLUMN_NAME", "入库数量");
			columnRS.putString("COLUMN_WIDTH", "100");
			columnRS.putString("COLUMN_TYPE", "N");
			columnRS.putString("COLUMN_DECN", "2");
			columnDS.insertRowSet(columnRS);
			
			columnRS = EFRowSet.getInstance();
			columnRS.putString("COLUMN_ID", "F_CKSL");
			columnRS.putString("COLUMN_ALIGN", "RIGHT");
			columnRS.putString("COLUMN_NAME", "出库数量");
			columnRS.putString("COLUMN_WIDTH", "100");
			columnRS.putString("COLUMN_TYPE", "N");
			columnRS.putString("COLUMN_DECN", "2");
			columnDS.insertRowSet(columnRS);

			columnRS = EFRowSet.getInstance();
			columnRS.putString("COLUMN_ID", "F_CLTHSL");
			columnRS.putString("COLUMN_ALIGN", "RIGHT");
			columnRS.putString("COLUMN_NAME", "材料退货数量");
			columnRS.putString("COLUMN_WIDTH", "100");
			columnRS.putString("COLUMN_TYPE", "N");
			columnRS.putString("COLUMN_DECN", "2");
			columnDS.insertRowSet(columnRS);
			
			columnRS = EFRowSet.getInstance();
			columnRS.putString("COLUMN_ID", "F_CSTHSL");
			columnRS.putString("COLUMN_ALIGN", "RIGHT");
			columnRS.putString("COLUMN_NAME", "厂商退货数量");
			columnRS.putString("COLUMN_WIDTH", "100");
			columnRS.putString("COLUMN_TYPE", "N");
			columnRS.putString("COLUMN_DECN", "2");
			columnDS.insertRowSet(columnRS);
			
			columnRS = EFRowSet.getInstance();
			columnRS.putString("COLUMN_ID", "F_DBSL");
			columnRS.putString("COLUMN_ALIGN", "RIGHT");
			columnRS.putString("COLUMN_NAME", "调拨数量");
			columnRS.putString("COLUMN_WIDTH", "100");
			columnRS.putString("COLUMN_TYPE", "N");
			columnRS.putString("COLUMN_DECN", "2");
			columnDS.insertRowSet(columnRS);

			columnRS = EFRowSet.getInstance();
			columnRS.putString("COLUMN_ID", "F_JZSL");
			columnRS.putString("COLUMN_ALIGN", "RIGHT");
			columnRS.putString("COLUMN_NAME", "结转数量");
			columnRS.putString("COLUMN_WIDTH", "100");
			columnRS.putString("COLUMN_TYPE", "N");
			columnRS.putString("COLUMN_DECN", "2");
			columnDS.insertRowSet(columnRS);
			
			//下载
			EMPExportExcelTable exportExcelTable = new EMPExportExcelTable();
			exportExcelTable.exportExcelTable(request, response, queryDS, columnDS, subHeadList, "材 料 联 查 项 目 产 品 查 询 分 析");			
		} catch (Exception e) {

			e.printStackTrace();
		}
		return ajaxDoneError("导出成功！");
	}
	
	/**
	 * 材料联查明细查询
	 * @param F_CKBH
	 * @param model
	 * @return
	 */
	@RequestMapping("/materialDetailsLCDetails")
	public ModelAndView materialDetailsLCDetails(@RequestParam String beginDate, @RequestParam String endDate, 
									       @RequestParam String F_CKBH, @RequestParam String F_CLBH, 
									       @RequestParam String F_DWBH, @RequestParam String F_CSBH, 
									       @RequestParam String F_XMBH, @RequestParam String F_CLDJ, 
									       HttpServletRequest request, HttpServletResponse response, 
									       Model model) {
		JParamObject           po = JParamObject.Create();
		List<String>  subHeadList = new ArrayList<String>();
		EFDataSet        columnDS = EFDataSet.getInstance();
		EFRowSet         columnRS = null;
		EFRowSet          queryRS = null;
		EFDataSet         queryDS = null;
		
		po.SetValueByParamName("beginDate", beginDate.replaceAll("-", ""));
		po.SetValueByParamName("endDate", endDate.replaceAll("-", ""));
		po.SetValueByParamName("F_CKBH", F_CKBH);
		po.SetValueByParamName("F_CLBH", F_CLBH);
		po.SetValueByParamName("F_DWBH", F_DWBH);
		po.SetValueByParamName("F_CSBH", F_CSBH);
		po.SetValueByParamName("F_CLDJ", F_CLDJ);
		po.SetValueByParamName("F_XMBH", F_XMBH);

		JResponseObject        RO = queryAnalyseServiceMgr.materialDetailsLCDetails(po);
		queryRS = (EFRowSet) RO.getResponseObject();
		queryDS = queryRS.getDataSet(EMPQueryParamEnum.QUERYRESULT);
		
		try {
			
			//副标题
			subHeadList.add("查询日期：" + beginDate.replaceAll("-", ".") + "-" + endDate.replaceAll("-", "."));
			
			columnRS = EFRowSet.getInstance();
			columnRS.putString("COLUMN_ID", "F_CLBH");
			columnRS.putString("COLUMN_ALIGN", "LEFT");
			columnRS.putString("COLUMN_NAME", "材料编号");
			columnRS.putString("COLUMN_WIDTH", "100");
			columnRS.putString("COLUMN_TYPE", "C");
			columnDS.insertRowSet(columnRS);
			
			columnRS = EFRowSet.getInstance();
			columnRS.putString("COLUMN_ID", "F_CLMC");
			columnRS.putString("COLUMN_ALIGN", "LEFT");
			columnRS.putString("COLUMN_NAME", "材料名称");
			columnRS.putString("COLUMN_WIDTH", "100");
			columnRS.putString("COLUMN_TYPE", "C");
			columnDS.insertRowSet(columnRS);
			
			columnRS = EFRowSet.getInstance();
			columnRS.putString("COLUMN_ID", "F_GGXH");
			columnRS.putString("COLUMN_ALIGN", "LEFT");
			columnRS.putString("COLUMN_NAME", "规格型号");
			columnRS.putString("COLUMN_WIDTH", "100");
			columnRS.putString("COLUMN_TYPE", "C");
			columnDS.insertRowSet(columnRS);
			
			columnRS = EFRowSet.getInstance();
			columnRS.putString("COLUMN_ID", "F_JLDW");
			columnRS.putString("COLUMN_ALIGN", "LEFT");
			columnRS.putString("COLUMN_NAME", "计量单位");
			columnRS.putString("COLUMN_WIDTH", "100");
			columnRS.putString("COLUMN_TYPE", "C");
			columnDS.insertRowSet(columnRS);
			
			columnRS = EFRowSet.getInstance();
			columnRS.putString("COLUMN_ID", "F_DWBH");
			columnRS.putString("COLUMN_ALIGN", "LEFT");
			columnRS.putString("COLUMN_NAME", "供应商编号");
			columnRS.putString("COLUMN_WIDTH", "100");
			columnRS.putString("COLUMN_TYPE", "C");
			columnDS.insertRowSet(columnRS);
			
			columnRS = EFRowSet.getInstance();
			columnRS.putString("COLUMN_ID", "F_DWMC");
			columnRS.putString("COLUMN_ALIGN", "LEFT");
			columnRS.putString("COLUMN_NAME", "供应商名称");
			columnRS.putString("COLUMN_WIDTH", "100");
			columnRS.putString("COLUMN_TYPE", "C");
			columnDS.insertRowSet(columnRS);
			
			columnRS = EFRowSet.getInstance();
			columnRS.putString("COLUMN_ID", "F_CSBH");
			columnRS.putString("COLUMN_ALIGN", "LEFT");
			columnRS.putString("COLUMN_NAME", "厂商编号");
			columnRS.putString("COLUMN_WIDTH", "100");
			columnRS.putString("COLUMN_TYPE", "C");
			columnDS.insertRowSet(columnRS);
			
			columnRS = EFRowSet.getInstance();
			columnRS.putString("COLUMN_ID", "F_CSMC");
			columnRS.putString("COLUMN_ALIGN", "LEFT");
			columnRS.putString("COLUMN_NAME", "厂商名称");
			columnRS.putString("COLUMN_WIDTH", "100");
			columnRS.putString("COLUMN_TYPE", "C");
			columnDS.insertRowSet(columnRS);
			
			columnRS = EFRowSet.getInstance();
			columnRS.putString("COLUMN_ID", "F_CLSL");
			columnRS.putString("COLUMN_ALIGN", "RIGHT");
			columnRS.putString("COLUMN_NAME", "库存数量");
			columnRS.putString("COLUMN_WIDTH", "100");
			columnRS.putString("COLUMN_TYPE", "N");
			columnRS.putString("COLUMN_DECN", "2");
			columnDS.insertRowSet(columnRS);
			
			columnRS = EFRowSet.getInstance();
			columnRS.putString("COLUMN_ID", "F_CLDJ");
			columnRS.putString("COLUMN_ALIGN", "RIGHT");
			columnRS.putString("COLUMN_NAME", "材料单价");
			columnRS.putString("COLUMN_WIDTH", "100");
			columnRS.putString("COLUMN_TYPE", "N");
			columnRS.putString("COLUMN_DECN", "2");
			columnDS.insertRowSet(columnRS);
			
			columnRS = EFRowSet.getInstance();
			columnRS.putString("COLUMN_ID", "F_CLZJ");
			columnRS.putString("COLUMN_ALIGN", "RIGHT");
			columnRS.putString("COLUMN_NAME", "材料总价");
			columnRS.putString("COLUMN_WIDTH", "100");
			columnRS.putString("COLUMN_TYPE", "N");
			columnRS.putString("COLUMN_DECN", "2");
			columnDS.insertRowSet(columnRS);

			//下载
			EMPExportExcelTable exportExcelTable = new EMPExportExcelTable();
			exportExcelTable.exportExcelTable(request, response, queryDS, columnDS, subHeadList, "库 存 查 询 分 析");			
		} catch (Exception e) {

			e.printStackTrace();
		}
		return ajaxDoneError("导出成功！");
	}
	
	/**
	 * 项目联查产品查询
	 * @param F_CKBH
	 * @param model
	 * @return
	 */
	@RequestMapping("/projectDetailsLCProduct")
	public ModelAndView projectLCProduct(@RequestParam String beginDate, @RequestParam String endDate, 
								   @RequestParam String F_XMBH, @RequestParam String F_CPBH, 
								   HttpServletRequest request, HttpServletResponse response, Model model) {
		JParamObject           po = JParamObject.Create();
		HYXMZD               xmzd = sysConfigureMgr.getHYXM(F_XMBH);
		List<String>  subHeadList = new ArrayList<String>();
		EFDataSet        columnDS = EFDataSet.getInstance();
		EFRowSet         columnRS = null;
		EFRowSet          queryRS = null;
		EFDataSet         queryDS = null;
		
		po.SetValueByParamName("beginDate", beginDate.replaceAll("-", ""));
		po.SetValueByParamName("endDate", endDate.replaceAll("-", ""));
		po.SetValueByParamName("F_XMBH", F_XMBH);
		po.SetValueByParamName("F_CPBH", F_CPBH);
		po.SetValueByParamName("CXFLAG", "XMCP");
		po.SetValueByParamName("ORDERBY", " F_YYCPBH, F_YYXMBH");
		po.SetValueByParamName("GROUPBY", " F_YYCPBH, F_YYXMBH");
		
		JResponseObject        RO = queryAnalyseServiceMgr.projectDetailsLCCPQuery(po);
		queryRS = (EFRowSet) RO.getResponseObject();
		queryDS = queryRS.getDataSet(EMPQueryParamEnum.QUERYRESULT);
		
		try {
			
			//副标题
			subHeadList.add("查询日期：" + beginDate.replaceAll("-", ".") + "-" + endDate.replaceAll("-", "."));
			subHeadList.add("项目名称：" + F_XMBH + "-" + xmzd.getF_XMMC());
			
			columnRS = EFRowSet.getInstance();
			columnRS.putString("COLUMN_ID", "F_CPBH");
			columnRS.putString("COLUMN_ALIGN", "LEFT");
			columnRS.putString("COLUMN_NAME", "产品编号");
			columnRS.putString("COLUMN_WIDTH", "150");
			columnRS.putString("COLUMN_TYPE", "C");
			columnDS.insertRowSet(columnRS);
			
			columnRS = EFRowSet.getInstance();
			columnRS.putString("COLUMN_ID", "F_CPMC");
			columnRS.putString("COLUMN_ALIGN", "LEFT");
			columnRS.putString("COLUMN_NAME", "产品名称");
			columnRS.putString("COLUMN_WIDTH", "200");
			columnRS.putString("COLUMN_TYPE", "C");
			columnDS.insertRowSet(columnRS);
			
			columnRS = EFRowSet.getInstance();
			columnRS.putString("COLUMN_ID", "F_KCCB");
			columnRS.putString("COLUMN_ALIGN", "RIGHT");
			columnRS.putString("COLUMN_NAME", "项目库存成本");
			columnRS.putString("COLUMN_WIDTH", "100");
			columnRS.putString("COLUMN_TYPE", "N");
			columnRS.putString("COLUMN_DECN", "2");
			columnDS.insertRowSet(columnRS);
			
			columnRS = EFRowSet.getInstance();
			columnRS.putString("COLUMN_ID", "F_XMCB");
			columnRS.putString("COLUMN_ALIGN", "RIGHT");
			columnRS.putString("COLUMN_NAME", "项目领用成本");
			columnRS.putString("COLUMN_WIDTH", "100");
			columnRS.putString("COLUMN_TYPE", "N");
			columnRS.putString("COLUMN_DECN", "2");
			columnDS.insertRowSet(columnRS);
			
			columnRS = EFRowSet.getInstance();
			columnRS.putString("COLUMN_ID", "F_ZCB");
			columnRS.putString("COLUMN_ALIGN", "RIGHT");
			columnRS.putString("COLUMN_NAME", "项目预计总成本");
			columnRS.putString("COLUMN_WIDTH", "100");
			columnRS.putString("COLUMN_TYPE", "N");
			columnRS.putString("COLUMN_DECN", "2");
			columnDS.insertRowSet(columnRS);

			//下载
			EMPExportExcelTable exportExcelTable = new EMPExportExcelTable();
			exportExcelTable.exportExcelTable(request, response, queryDS, columnDS, subHeadList, "项 目 联 查 产 品 查 询 分 析");			
		} catch (Exception e) {

			e.printStackTrace();
		}
		
		return ajaxDoneError("导出成功！");
	}
	
	/**
	 * 项目产品材料联查用料调配查询(几个功能通用)
	 * @param F_CKBH
	 * @param model
	 * @return
	 */
	@RequestMapping("/xmcpclDetailsLCDeploy")
	public ModelAndView xmcpclDetailsLCDeploy(@RequestParam String beginDate, @RequestParam String endDate, 
								              @RequestParam String F_XMBH,    @RequestParam String F_CPBH, 
								              @RequestParam String F_CLBH,    @RequestParam String CX,
								              HttpServletRequest request, HttpServletResponse response, 
								              Model model) {
		JParamObject           po = JParamObject.Create();

		List<String>  subHeadList = new ArrayList<String>();
		HYCLZD               clzd = sysConfigureMgr.getHYCL(F_CLBH);
		HYCPZD               cpzd = sysConfigureMgr.getHYCP(F_CPBH);
		HYXMZD               xmzd = sysConfigureMgr.getHYXM(F_XMBH);
		EFRowSet          queryRS = null;
		EFDataSet         queryDS = null;
		
		po.SetValueByParamName("beginDate", beginDate.replaceAll("-", ""));
		po.SetValueByParamName("endDate", endDate.replaceAll("-", ""));
		po.SetValueByParamName("F_XMBH", F_XMBH);
		po.SetValueByParamName("F_CPBH", F_CPBH);
		po.SetValueByParamName("F_CLBH", F_CLBH);
		po.SetValueByParamName("CX", "CX");

		JResponseObject        RO = queryAnalyseServiceMgr.xmcpclDetailsLCDeploy(po);
		queryRS = (EFRowSet) RO.getResponseObject();
		queryDS = queryRS.getDataSet(EMPQueryParamEnum.QUERYRESULT);
		
		try {
			
			//副标题
			subHeadList.add("查询日期：" + beginDate.replaceAll("-", ".") + "-" + endDate.replaceAll("-", "."));
			if(xmzd != null) subHeadList.add("项目名称：" + F_XMBH + "-" + xmzd.getF_XMMC());
			if(clzd != null) subHeadList.add("材料名称：" + F_CLBH + "-" + clzd.getF_CLMC());
			if(cpzd != null) subHeadList.add("产品名称：" + F_CPBH + "-" + cpzd.getF_CPMC());

			//下载
			EMPExportExcelMultiTable exportExcelMultiTable = new EMPExportExcelMultiTable();
			exportExcelMultiTable.exportExcelTable(request, response, queryDS, subHeadList, "联 查 用 料 调 配");				
		} catch (Exception e) {

			e.printStackTrace();
		}
		
		return ajaxDoneError("导出成功！");
	}
	
	/**
	 * 项目-产品联查材料
	 * @param F_CKBH
	 * @param model
	 * @return
	 */
	@RequestMapping("/projectProductDetailsLCMaterial")
	public ModelAndView projectProductDetailsLCMaterial(@RequestParam String beginDate, @RequestParam String endDate, 
												  @RequestParam String F_CPBH, @RequestParam String F_CLBH, 
												  HttpServletRequest request, HttpServletResponse response, 
								                  @RequestParam String F_XMBH, Model model) {
		JParamObject           po = JParamObject.Create();
		HYXMZD               xmzd = sysConfigureMgr.getHYXM(F_XMBH);
		HYCPZD               cpzd = sysConfigureMgr.getHYCP(F_CPBH);
		List<String>  subHeadList = new ArrayList<String>();
		EFDataSet        columnDS = EFDataSet.getInstance();
		EFRowSet         columnRS = null;
		EFRowSet          queryRS = null;
		EFDataSet         queryDS = null;
		
		po.SetValueByParamName("F_XMBH", F_XMBH);
		po.SetValueByParamName("F_CPBH", F_CPBH);
		po.SetValueByParamName("F_CLBH", F_CLBH);
		
		JResponseObject        RO = queryAnalyseServiceMgr.projectProductDetailsLCMaterial(po);
		queryRS = (EFRowSet) RO.getResponseObject();
		queryDS = queryRS.getDataSet(EMPQueryParamEnum.QUERYRESULT);
		
		try {
			
			//副标题
			subHeadList.add("查询日期：" + beginDate.replaceAll("-", ".") + "-" + endDate.replaceAll("-", "."));
			subHeadList.add("项目名称：" + F_XMBH + "-" + xmzd.getF_XMMC());
			subHeadList.add("产品名称：" + F_CPBH + "-" + cpzd.getF_CPMC());
			
			columnRS = EFRowSet.getInstance();
			columnRS.putString("COLUMN_ID", "F_CLBH");
			columnRS.putString("COLUMN_ALIGN", "LEFT");
			columnRS.putString("COLUMN_NAME", "材料编号");
			columnRS.putString("COLUMN_WIDTH", "150");
			columnRS.putString("COLUMN_TYPE", "C");
			columnDS.insertRowSet(columnRS);
			
			columnRS = EFRowSet.getInstance();
			columnRS.putString("COLUMN_ID", "F_CLMC");
			columnRS.putString("COLUMN_ALIGN", "LEFT");
			columnRS.putString("COLUMN_NAME", "材料名称");
			columnRS.putString("COLUMN_WIDTH", "200");
			columnRS.putString("COLUMN_TYPE", "C");
			columnDS.insertRowSet(columnRS);

			columnRS = EFRowSet.getInstance();
			columnRS.putString("COLUMN_ID", "F_KCSL");
			columnRS.putString("COLUMN_ALIGN", "RIGHT");
			columnRS.putString("COLUMN_NAME", "材料库存数量");
			columnRS.putString("COLUMN_WIDTH", "200");
			columnRS.putString("COLUMN_TYPE", "N");
			columnRS.putString("COLUMN_DECN", "2");
			columnDS.insertRowSet(columnRS);
			
			columnRS = EFRowSet.getInstance();
			columnRS.putString("COLUMN_ID", "F_KCCB");
			columnRS.putString("COLUMN_ALIGN", "RIGHT");
			columnRS.putString("COLUMN_NAME", "材料库存成本");
			columnRS.putString("COLUMN_WIDTH", "200");
			columnRS.putString("COLUMN_TYPE", "N");
			columnRS.putString("COLUMN_DECN", "2");
			columnDS.insertRowSet(columnRS);
			
			columnRS = EFRowSet.getInstance();
			columnRS.putString("COLUMN_ID", "F_LYSL");
			columnRS.putString("COLUMN_ALIGN", "RIGHT");
			columnRS.putString("COLUMN_NAME", "材料领用数量");
			columnRS.putString("COLUMN_WIDTH", "200");
			columnRS.putString("COLUMN_TYPE", "N");
			columnRS.putString("COLUMN_DECN", "2");
			columnDS.insertRowSet(columnRS);

			columnRS = EFRowSet.getInstance();
			columnRS.putString("COLUMN_ID", "F_LYCB");
			columnRS.putString("COLUMN_ALIGN", "RIGHT");
			columnRS.putString("COLUMN_NAME", "材料领用成本");
			columnRS.putString("COLUMN_WIDTH", "200");
			columnRS.putString("COLUMN_TYPE", "N");
			columnRS.putString("COLUMN_DECN", "2");
			columnDS.insertRowSet(columnRS);
			
			columnRS = EFRowSet.getInstance();
			columnRS.putString("COLUMN_ID", "F_ZCB");
			columnRS.putString("COLUMN_ALIGN", "RIGHT");
			columnRS.putString("COLUMN_NAME", "材料总成本");
			columnRS.putString("COLUMN_WIDTH", "200");
			columnRS.putString("COLUMN_TYPE", "N");
			columnRS.putString("COLUMN_DECN", "2");
			columnDS.insertRowSet(columnRS);
			
			//下载
			EMPExportExcelTable exportExcelTable = new EMPExportExcelTable();
			exportExcelTable.exportExcelTable(request, response, queryDS, columnDS, subHeadList, "项 目 产 品 联 查 材 料 查 询 分 析");			
		} catch (Exception e) {

			e.printStackTrace();
		}
		return ajaxDoneError("导出成功！");
	}
	
	/**
	 * 产品联查材料
	 * @param F_CKBH
	 * @param model
	 * @return
	 */
	@RequestMapping("/productDetailsLCMaterial")
	public ModelAndView productDetailsLCMaterial(@RequestParam String beginDate, @RequestParam String endDate, 
										   @RequestParam String F_CPBH, @RequestParam String F_CLBH, 
										   HttpServletRequest request, HttpServletResponse response, 
								           @RequestParam String F_XMBH, Model model) {
		JParamObject           po = JParamObject.Create();
		HYXMZD               xmzd = sysConfigureMgr.getHYXM(F_XMBH);
		HYCPZD               cpzd = sysConfigureMgr.getHYCP(F_CPBH);
		List<String>  subHeadList = new ArrayList<String>();
		EFDataSet        columnDS = EFDataSet.getInstance();
		EFRowSet         columnRS = null;
		EFRowSet          queryRS = null;
		EFDataSet         queryDS = null;
		
		po.SetValueByParamName("F_XMBH", F_XMBH);
		po.SetValueByParamName("F_CPBH", F_CPBH);
		po.SetValueByParamName("F_CLBH", F_CLBH);
		
		JResponseObject        RO = queryAnalyseServiceMgr.projectProductDetailsLCMaterial(po);
		queryRS = (EFRowSet) RO.getResponseObject();
		queryDS = queryRS.getDataSet(EMPQueryParamEnum.QUERYRESULT);

		try {
			
			//副标题
			subHeadList.add("查询日期：" + beginDate.replaceAll("-", ".") + "-" + endDate.replaceAll("-", "."));
			subHeadList.add("项目名称：" + F_XMBH + "-" + xmzd.getF_XMMC());
			subHeadList.add("产品名称：" + F_CPBH + "-" + cpzd.getF_CPMC());
			
			columnRS = EFRowSet.getInstance();
			columnRS.putString("COLUMN_ID", "F_CLBH");
			columnRS.putString("COLUMN_ALIGN", "LEFT");
			columnRS.putString("COLUMN_NAME", "材料编号");
			columnRS.putString("COLUMN_WIDTH", "150");
			columnRS.putString("COLUMN_TYPE", "C");
			columnDS.insertRowSet(columnRS);
			
			columnRS = EFRowSet.getInstance();
			columnRS.putString("COLUMN_ID", "F_CLMC");
			columnRS.putString("COLUMN_ALIGN", "LEFT");
			columnRS.putString("COLUMN_NAME", "材料名称");
			columnRS.putString("COLUMN_WIDTH", "200");
			columnRS.putString("COLUMN_TYPE", "C");
			columnDS.insertRowSet(columnRS);
			
			columnRS = EFRowSet.getInstance();
			columnRS.putString("COLUMN_ID", "F_CLSL");
			columnRS.putString("COLUMN_ALIGN", "RIGHT");
			columnRS.putString("COLUMN_NAME", "材料用料数量");
			columnRS.putString("COLUMN_WIDTH", "200");
			columnRS.putString("COLUMN_TYPE", "N");
			columnRS.putString("COLUMN_DECN", "2");
			columnDS.insertRowSet(columnRS);
			
			columnRS = EFRowSet.getInstance();
			columnRS.putString("COLUMN_ID", "F_CLZJ");
			columnRS.putString("COLUMN_ALIGN", "RIGHT");
			columnRS.putString("COLUMN_NAME", "材料总价");
			columnRS.putString("COLUMN_WIDTH", "200");
			columnRS.putString("COLUMN_TYPE", "N");
			columnRS.putString("COLUMN_DECN", "2");
			columnDS.insertRowSet(columnRS);

			//下载
			EMPExportExcelTable exportExcelTable = new EMPExportExcelTable();
			exportExcelTable.exportExcelTable(request, response, queryDS, columnDS, subHeadList, "项 目 产 品联 查 材 料 查 询 分 析");			
		} catch (Exception e) {

			e.printStackTrace();
		}
		
		return ajaxDoneError("导出成功！");
	}
	
	/**
	 * 项目联查申请单
	 * @param F_CKBH
	 * @param model
	 * @return
	 */
	@RequestMapping("/productDetailsLCSatement")
	public ModelAndView productDetailsLCSatement(HttpServletRequest request, HttpServletResponse response, 
										         @RequestParam String F_XMBH, Model model) {
		JParamObject           po = JParamObject.Create();
		po.SetValueByParamName("F_XMBH", F_XMBH);
		
		JResponseObject        RO = queryAnalyseServiceMgr.projectDetailsLCSatement(po);
		EFRowSet        applyForm = (EFRowSet) RO.getResponseObject();

		try {
			//下载
			EMPExportExcelStatement exportExcelTable = new EMPExportExcelStatement();
			exportExcelTable.exportExcelTable(request, response, applyForm, "材 料 申 请 单");			
		} catch (Exception e) {

			e.printStackTrace();
		}
		return ajaxDoneError("导出成功！");
	}
}
