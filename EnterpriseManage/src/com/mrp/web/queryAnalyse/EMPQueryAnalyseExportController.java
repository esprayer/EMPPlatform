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
import com.etsoft.pub.util.EMPExportExcelTable;
import com.mrp.biz.queryAnalyse.QueryAnalyseServiceMgr;
import com.mrp.biz.sysconfigure.sysConfigureServiceMgr;
import com.mrp.persistence.sysConfigure.deport.bean.HYCKZD;
import com.mrp.persistence.sysConfigure.project.bean.HYXMZD;
import dwz.web.BaseController;
import esyt.framework.com.bridge.JResponseObject;
import esyt.framework.com.builder.base.data.EFDataSet;
import esyt.framework.com.builder.base.data.EFRowSet;
import esyt.framework.com.eai.data.JParamObject;

@Controller
@RequestMapping(value="/queryAnalyse/export")
public class EMPQueryAnalyseExportController extends BaseController{
	@Autowired
	private QueryAnalyseServiceMgr queryAnalyseServiceMgr;
	
	@Autowired
	private sysConfigureServiceMgr        sysConfigureMgr;
	
	@Autowired
	public HttpSession session;

	/**
	 * 导出库存查询
	 * @param F_CKBH
	 * @param model
	 * @return
	 */
	@RequestMapping("/warehouseMaterial/exportExcel")
	public ModelAndView exportExcelWarehouseMaterial(@RequestParam String F_CKBH, @RequestParam String F_CLBH, 
													 HttpServletRequest request, HttpServletResponse response, 
													 Model model) {
		JParamObject           po = JParamObject.Create();
		po.SetValueByParamName("F_CKBH", F_CKBH);
		po.SetValueByParamName("F_CLBH", F_CLBH);
		JResponseObject        RO = queryAnalyseServiceMgr.searchWarehouseMaterial(po);
		List<String>  subHeadList = new ArrayList<String>();
		EFDataSet        columnDS = EFDataSet.getInstance();
		EFRowSet         columnRS = null;
		EFRowSet          queryRS = null;
		EFDataSet         queryDS = null;
		
		try {
			
			queryRS = (EFRowSet) RO.getResponseObject();
			queryDS = queryRS.getDataSet(EMPQueryParamEnum.QUERYRESULT);
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
	 * 导出材料明细查询
	 * @param F_CKBH
	 * @param model
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/materialDetails")
	public ModelAndView materialDetails(@RequestParam String beginDate, @RequestParam String endDate, 
								        @RequestParam String F_CKBH, @RequestParam String F_XMBH, @RequestParam String F_CLBH, 
								        HttpServletRequest request, HttpServletResponse response, 
								        Model model) throws Exception {
		JParamObject           po = JParamObject.Create();
		List<String>  subHeadList = new ArrayList<String>();
		EFDataSet        columnDS = EFDataSet.getInstance();
		EFRowSet         columnRS = null;
		EFRowSet          queryRS = null;
		EFDataSet         queryDS = null;
		
		po.SetValueByParamName("beginDate", beginDate);
		po.SetValueByParamName("endDate", endDate);
		po.SetValueByParamName("F_CKBH", F_CKBH);
		po.SetValueByParamName("F_XMBH", F_XMBH);
		po.SetValueByParamName("F_CLBH", F_CLBH);
		
		JResponseObject        RO = queryAnalyseServiceMgr.projectMaterialQuery(po);
		queryRS = (EFRowSet) RO.getResponseObject();
		queryDS = queryRS.getDataSet(EMPQueryParamEnum.QUERYRESULT);
		
		//副标题
		subHeadList.add("查询日期：" + beginDate.replaceAll("-", ".") + "-" + endDate.replaceAll("-", "."));
		
		columnRS = EFRowSet.getInstance();
		columnRS.putString("COLUMN_ID", "F_CKBH");
		columnRS.putString("COLUMN_ALIGN", "LEFT");
		columnRS.putString("COLUMN_NAME", "仓库编号");
		columnRS.putString("COLUMN_WIDTH", "100");
		columnRS.putString("COLUMN_TYPE", "C");
		columnDS.insertRowSet(columnRS);
		
		columnRS = EFRowSet.getInstance();
		columnRS.putString("COLUMN_ID", "F_CKMC");
		columnRS.putString("COLUMN_ALIGN", "LEFT");
		columnRS.putString("COLUMN_NAME", "仓库名称");
		columnRS.putString("COLUMN_WIDTH", "100");
		columnRS.putString("COLUMN_TYPE", "C");
		columnDS.insertRowSet(columnRS);
		
		columnRS = EFRowSet.getInstance();
		columnRS.putString("COLUMN_ID", "F_XMBH");
		columnRS.putString("COLUMN_ALIGN", "LEFT");
		columnRS.putString("COLUMN_NAME", "项目编号");
		columnRS.putString("COLUMN_WIDTH", "100");
		columnRS.putString("COLUMN_TYPE", "C");
		columnDS.insertRowSet(columnRS);
		
		columnRS = EFRowSet.getInstance();
		columnRS.putString("COLUMN_ID", "F_XMMC");
		columnRS.putString("COLUMN_ALIGN", "LEFT");
		columnRS.putString("COLUMN_NAME", "项目名称");
		columnRS.putString("COLUMN_WIDTH", "100");
		columnRS.putString("COLUMN_TYPE", "C");
		columnDS.insertRowSet(columnRS);
		
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
		
		//下载
		EMPExportExcelTable exportExcelTable = new EMPExportExcelTable();
		exportExcelTable.exportExcelTable(request, response, queryDS, columnDS, subHeadList, "材 料 明 细 查 询 分 析");		
		return ajaxDoneError("导出成功！");
	}
	
	/**
	 * 导出项目查询
	 * @param F_CKBH
	 * @param model
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/projectDetails")
	public ModelAndView projectDetails(@RequestParam String beginDate, @RequestParam String endDate, 
								       @RequestParam String F_XMBH, Model model, 
								       HttpServletRequest request, HttpServletResponse response) throws Exception {
		JParamObject           po = JParamObject.Create();
		List<String>  subHeadList = new ArrayList<String>();
		EFDataSet        columnDS = EFDataSet.getInstance();
		EFRowSet         columnRS = null;
		EFRowSet          queryRS = null;
		EFDataSet         queryDS = null;
		HYXMZD               xmzd = sysConfigureMgr.getHYXM(F_XMBH);
		
		po.SetValueByParamName("beginDate", beginDate.replaceAll("-", ""));
		po.SetValueByParamName("endDate", endDate.replaceAll("-", ""));
		po.SetValueByParamName("F_XMBH", F_XMBH);
		po.SetValueByParamName("ORDERBY", " F_YYXMBH");
		po.SetValueByParamName("GROUPBY", " F_YYXMBH");
		
		JResponseObject        RO = queryAnalyseServiceMgr.projectDetailsQuery(po);
		queryRS = (EFRowSet) RO.getResponseObject();
		queryDS = queryRS.getDataSet(EMPQueryParamEnum.QUERYRESULT);
		//副标题
		subHeadList.add("查询日期：" + beginDate.replaceAll("-", ".") + "-" + endDate.replaceAll("-", "."));
		if(xmzd == null) {
			subHeadList.add("项目名称：" + F_XMBH);
		} else {
			subHeadList.add("项目名称：" + F_XMBH + "-" + xmzd.getF_XMMC());
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
		exportExcelTable.exportExcelTable(request, response, queryDS, columnDS, subHeadList, "项 目 查 询 分 析");		
		return ajaxDoneError("导出成功！");
	}
	
	/**
	 * 导出项目产品明细查询
	 * @param F_CKBH
	 * @param model
	 * @return
	 */
	@RequestMapping("/productDetails")
	public ModelAndView productDetails(@RequestParam String beginDate, @RequestParam String endDate, 
								       @RequestParam String F_CPBH, Model model, 
								       HttpServletRequest request, HttpServletResponse response) throws Exception {
		JParamObject           po = JParamObject.Create();
		List<String>  subHeadList = new ArrayList<String>();
		EFDataSet        columnDS = EFDataSet.getInstance();
		EFRowSet         columnRS = null;
		EFRowSet          queryRS = null;
		EFDataSet         queryDS = null;
		
		po.SetValueByParamName("beginDate", beginDate.replaceAll("-", ""));
		po.SetValueByParamName("endDate", endDate.replaceAll("-", ""));
		po.SetValueByParamName("F_CPBH", F_CPBH);
		po.SetValueByParamName("ORDERBY", " F_YYCPBH, F_YYXMBH");
		po.SetValueByParamName("GROUPBY", " F_YYCPBH, F_YYXMBH");
		po.SetValueByParamName("CXFLAG", "XMCP");
		
		JResponseObject        RO = queryAnalyseServiceMgr.projectProductQuery(po);
		queryRS = (EFRowSet) RO.getResponseObject();
		queryDS = queryRS.getDataSet(EMPQueryParamEnum.QUERYRESULT);
		
		//副标题
		subHeadList.add("查询日期：" + beginDate.replaceAll("-", ".") + "-" + endDate.replaceAll("-", "."));

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
		columnRS.putString("COLUMN_ID", "F_XMZT");
		columnRS.putString("COLUMN_ALIGN", "LEFT");
		columnRS.putString("COLUMN_NAME", "项目状态");
		columnRS.putString("COLUMN_MASK", "0:未完成;1:已完成");
		columnRS.putString("COLUMN_WIDTH", "100");
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
		exportExcelTable.exportExcelTable(request, response, queryDS, columnDS, subHeadList, "项 目 产 品 查 询 分 析");		
		return ajaxDoneError("导出成功！");
	}
}
