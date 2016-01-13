package com.pub.util;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFDataFormat;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.efounder.builder.base.data.EFDataSet;
import com.efounder.builder.base.data.EFRowSet;

public class EMPExportExcelStatement extends EMPExportExcelTable {
	
	/**
	 * 将数据导出生成excel文件
	 * @param queryDS             查询数据集
	 * @param columnDS            列数据集
	 * @param subHeadList         副标题信息
	 * @param title               标题
	 * @return
	 * @throws Exception
	 */
	public void exportExcelTable(HttpServletRequest request, HttpServletResponse response, EFRowSet applyForm, String title) throws Exception {
		//创建excel对象
		XSSFWorkbook workBook = getExportFileSheet();
		//创建标题
		setHeadCellFont(workBook, title);
		//创建副标题
		createSubHeads(workBook, applyForm);
		//创建列信息
		exportColumnData(workBook);
		//创建数据信息
		exportData(workBook, applyForm);
		//生成文件
		File exportFile = createExportExcel(request, response, workBook, title);
		//下载文件
		downloadFile(response, exportFile, title);
	}
	
	/**
	 * 创建excel对象
	 * @return
	 * @throws Exception
	 */
	private XSSFWorkbook getExportFileSheet() throws Exception {
        XSSFWorkbook workBook = new XSSFWorkbook();
        workBook.createSheet("导出结果");
        return workBook;
	}
	
	/**
	 * 创建标题，并设置字体、字号、加粗、颜色
	 * @param workbook
	 */
	private void setHeadCellFont(XSSFWorkbook workbook, String title) {
		XSSFCellStyle   style = workbook.createCellStyle();
		XSSFRow           row = (XSSFRow) workbook.getSheetAt(0).createRow(0);                        // 创建一个行对象
		XSSFCell         cell = row.createCell(0);                                                    // 创建单元格
		XSSFFont         font = workbook.createFont();                                                // 创建字体对象
        font.setFontName(HSSFFont.FONT_ARIAL);                                                        // 字体
        font.setFontHeightInPoints((short) 16);                                                       // 字号 
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);                                                 // 加粗
        style.setFont(font);
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER_SELECTION);                                     // 水平居中
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);                                    // 垂直居中
        row.setHeightInPoints(23);                                                                    // 设置行高23像素
        cell.setCellStyle(style);                                                                     // 应用样式对象
        workbook.getSheetAt(0).addMergedRegion(new CellRangeAddress(0,0,0,16));   // 四个参数分别是：起始行，结束行，起始列，结束列
        cell.setCellValue(title);                                                                     // 写入头标题
	}
	
	/**
	 * 创建副标题
	 * @param workbook
	 */
	private void createSubHeads(XSSFWorkbook workbook, EFRowSet applyForm) {
		
		XSSFCellStyle           style = null;
		XSSFRow                   row = null;   
		XSSFFont                 font = workbook.createFont();                                                                  // 创建字体对象
		SimpleDateFormat    formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date                     date = null;
		
		style = workbook.createCellStyle();                  
		font.setFontHeightInPoints((short) 9);                                                                          // 字号 
        style.setFont(font);		
		style.setAlignment(HSSFCellStyle.ALIGN_LEFT);                                                                  // 水平居左
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);                                                     // 垂直居中

        row = workbook.getSheetAt(0).createRow(1);
        createSubHeadCell(workbook, row, style, "项目编号：", 1, 0);
        createSubHeadCell(workbook, row, style, applyForm.getString("F_XMBH", ""), 1, 2);
        createSubHeadCell(workbook, row, style, "项目名称：", 1, 4);
        createSubHeadCell(workbook, row, style, applyForm.getString("F_XMMC", ""), 1, 6);
        createSubHeadCell(workbook, row, style, "申请日期：", 1, 8);
        createSubHeadCell(workbook, row, style, applyForm.getString("F_SQSJ", ""), 1, 10);
        createSubHeadCell(workbook, row, style, "项目单位：", 1, 12);
        createSubHeadCell(workbook, row, style, applyForm.getString("F_SQDW", ""), 1, 14);
        
        row = workbook.getSheetAt(0).createRow(2);
        createSubHeadCell(workbook, row, style, "申请人：", 2, 0);
        createSubHeadCell(workbook, row, style, applyForm.getString("F_SQRMC", ""), 2, 2);
        createSubHeadCell(workbook, row, style, "供应中心：", 2, 4);
        createSubHeadCell(workbook, row, style, applyForm.getString("F_GYZXMC", ""), 2, 6);
        createSubHeadCell(workbook, row, style, "单位领导名称：", 2, 8);
        createSubHeadCell(workbook, row, style, applyForm.getString("F_DWLDMC", ""), 2, 10);
        createSubHeadCell(workbook, row, style, "分管领导名称：", 2, 12);
        createSubHeadCell(workbook, row, style, applyForm.getString("F_FGLDMC", ""), 2, 14);
        
        row = workbook.getSheetAt(0).createRow(3);
        createSubHeadCell(workbook, row, style, "主管领导名称：", 3, 0);
        createSubHeadCell(workbook, row, style, applyForm.getString("F_ZGLDMC", ""), 3, 2);
        createSubHeadCell(workbook, row, style, "材料需求时间：", 3, 4);
        date = (Date) applyForm.getObject("F_CLXQSJ", "");
        createSubHeadCell(workbook, row, style, formatter.format(date), 3, 6);
        createSubHeadCell(workbook, row, style, "项目状态：", 3, 8);
        
        if(applyForm.getString("F_XMZT", "0").equals("0")) {
        	createSubHeadCell(workbook, row, style, "未完工", 3, 10);
        } else {
        	createSubHeadCell(workbook, row, style, "已完工", 3, 10);
        	createSubHeadCell(workbook, row, style, "完工时间：", 3, 12);
        	date = (Date) applyForm.getObject("F_WGSJ", "");
            createSubHeadCell(workbook, row, style, formatter.format(date), 3, 14);
        }
	}
	
	/**
	 * 创建一个副标题
	 */
	private void createSubHeadCell(XSSFWorkbook workbook, XSSFRow row, XSSFCellStyle style, String title, int rowIndex, int colIndex) {
		XSSFCell         cell = row.createCell(colIndex);                                                                                      // 创建单元格
		cell.setCellValue(title);     
		workbook.getSheetAt(0).addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, colIndex, colIndex + 1));                   // 合并单元格
		cell.setCellStyle(style);   
	}
	
	/**
	 * 创建列
	 * @param workbook
	 */
	private void exportColumnData(XSSFWorkbook workbook) {	
		XSSFCellStyle          style = null;	
		XSSFRow                  row = null;   
		XSSFCell                cell = null;
		XSSFFont                font = workbook.createFont();                                           // 创建字体对象
		XSSFCellStyle       topStyle = setColumnTopBorder(workbook);
		XSSFCellStyle    buttomStyle = setColumnButtomBorder(workbook);
		row = workbook.getSheetAt(0).createRow(5);                                               // 创建一个行对象 列标题
		style = workbook.createCellStyle();	
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER_SELECTION);                                // 水平居中
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);                               // 垂直居中
        font.setFontHeightInPoints((short) 10);                                                  // 字号 
        style.setFont(font);	                                                                 // 设置字体
        style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);                                    // 设置前景填充样式 
        style.setFillForegroundColor(new XSSFColor(new java.awt.Color(204, 204, 255)));          // 设置单元格背景色
        setColumnBorder(style, 0);                                                               // 设置列边框
        workbook.getSheetAt(0).setColumnWidth(0, 32 * 100);                                      // 对列设置宽度
        
        
        /**
         * 创建单元格、设置列名称、合并单元格
         */
		cell = row.createCell(0);
        cell.setCellValue("材料编号");
        workbook.getSheetAt(0).addMergedRegion(new CellRangeAddress(5, 6, 0, 0));
        cell.setCellStyle(style); 
        
		cell = row.createCell(1);
        cell.setCellValue("材料名称");
        workbook.getSheetAt(0).addMergedRegion(new CellRangeAddress(5, 6, 1, 1));
        cell.setCellStyle(style); 

		cell = row.createCell(2);
        cell.setCellValue("型号规格");
        workbook.getSheetAt(0).addMergedRegion(new CellRangeAddress(5, 6, 2, 2));
        cell.setCellStyle(style); 

		cell = row.createCell(3);
        cell.setCellValue("单位");
        workbook.getSheetAt(0).addMergedRegion(new CellRangeAddress(5, 6, 3, 3));
        cell.setCellStyle(style); 

		cell = row.createCell(4);
        cell.setCellValue("申请数量");
        workbook.getSheetAt(0).addMergedRegion(new CellRangeAddress(5, 6, 4, 4));
        cell.setCellStyle(style); 

		cell = row.createCell(5);
        cell.setCellValue("入库数量");
        workbook.getSheetAt(0).addMergedRegion(new CellRangeAddress(5, 6, 5, 5));
        cell.setCellStyle(style); 

		cell = row.createCell(6);
        cell.setCellValue("出库数量");
        workbook.getSheetAt(0).addMergedRegion(new CellRangeAddress(5, 5, 6, 8));
        cell.setCellStyle(style); 

        cell = row.createCell(7);
        cell.setCellStyle(topStyle); 
        
        cell = row.createCell(8);
        cell.setCellStyle(topStyle); 
        
		cell = row.createCell(9);
        cell.setCellValue("退货数量");
        workbook.getSheetAt(0).addMergedRegion(new CellRangeAddress(5, 5, 9, 10));
        cell.setCellStyle(style); 
        
        cell = row.createCell(10);
        cell.setCellStyle(topStyle); 
        
        cell = row.createCell(11);
        cell.setCellValue("调拨数量");
        workbook.getSheetAt(0).addMergedRegion(new CellRangeAddress(5, 6, 11, 11));
        cell.setCellStyle(style); 
        
        cell = row.createCell(12);
        cell.setCellValue("库存数量");
        workbook.getSheetAt(0).addMergedRegion(new CellRangeAddress(5, 6, 12, 12));
        cell.setCellStyle(style); 
        
        cell = row.createCell(13);
        cell.setCellValue("领用数量");
        workbook.getSheetAt(0).addMergedRegion(new CellRangeAddress(5, 6, 13, 13));
        cell.setCellStyle(style); 
        
        cell = row.createCell(14);
        cell.setCellValue("领用总价");
        workbook.getSheetAt(0).addMergedRegion(new CellRangeAddress(5, 6, 14, 14));
        cell.setCellStyle(style); 
        
        cell = row.createCell(15);
        cell.setCellValue("库存情况");
        workbook.getSheetAt(0).addMergedRegion(new CellRangeAddress(5, 6, 15, 15));
        cell.setCellStyle(style); 
        
        cell = row.createCell(16);
        cell.setCellValue("备注");
        workbook.getSheetAt(0).addMergedRegion(new CellRangeAddress(5, 6, 16, 16));
        cell.setCellStyle(style); 
        
        row = workbook.getSheetAt(0).createRow(6);                                               // 创建一个行对象 列标题
        /**
         * 创建单元格、设置列名称、合并单元格
         */
		cell = row.createCell(6);
        cell.setCellValue("正常领用");
        cell.setCellStyle(style); 
        
		cell = row.createCell(7);
        cell.setCellValue("借调");
        cell.setCellStyle(style); 

		cell = row.createCell(8);
        cell.setCellValue("被借调");
        cell.setCellStyle(style); 

		cell = row.createCell(9);
        cell.setCellValue("材料退货");
        cell.setCellStyle(style); 

		cell = row.createCell(10);
        cell.setCellValue("厂商退货");
        cell.setCellStyle(style); 
        
        for(int i = 0; i < 5; i++) {
        	cell = row.createCell(i);
            cell.setCellStyle(buttomStyle); 
        }
        
        for(int i = 11; i < 16; i++) {
        	cell = row.createCell(i);
            cell.setCellStyle(buttomStyle); 
        }
	}
	
	/**
	 * 创建列
	 * @param workbook
	 */
	private void exportData(XSSFWorkbook workbook, EFRowSet applyForm) {	
		EFDataSet            queryDS = applyForm.getDataSet("HYXMMX");
		EFRowSet             queryRS = null;
		XSSFRow                  row = null;   
		XSSFFont                font = workbook.createFont();                                               // 创建字体对象
		XSSFCellStyle          style = workbook.createCellStyle();
		int                 startRow = 6;
		List<String>         colList = new ArrayList<String>();
		//循环数据集中每行数据
		for(int i = 0; i < queryDS.getRowCount(); i++) {
			startRow += 1;                                                                           // 获取行号
			row = workbook.getSheetAt(0).createRow(startRow);                                        // 创建一个行对象
			queryRS = queryDS.getRowSet(i);
			
			style = workbook.createCellStyle();
			setColumnAlign(style, "LEFT");                                                       // 设置列对齐方式
			setColumnBorder(style, 1);                                                           // 设置列边框		        
	        font.setFontHeightInPoints((short) 10);                                              // 字号 
	        style.setFont(font);	                                                             // 设置字体
	        
	        colList = new ArrayList<String>();
	        colList.add("F_CLBH");
	        colList.add("F_CLMC");
	        colList.add("F_GGXH");
	        colList.add("F_JLDW");

	        createColumn(workbook, row, style, colList, 0, queryRS, "");
	        
	        style = workbook.createCellStyle();
			setColumnAlign(style, "RIGHT");                                                       // 设置列对齐方式
			setColumnBorder(style, 1);                                                           // 设置列边框		        
	        font.setFontHeightInPoints((short) 10);                                              // 字号 
	        style.setFont(font);	                                                             // 设置字体
	        
	        colList = new ArrayList<String>();
	        colList.add("F_SQSL");
	        colList.add("F_RKSL");
	        colList.add("F_ZCSL");
	        colList.add("F_JDSL");
	        colList.add("F_BJDSL");
	        colList.add("F_CLTHSL");
	        colList.add("F_CSTHSL");
	        colList.add("F_DBSL");
	        colList.add("F_KCSL");
	        colList.add("F_LYSL");
	        colList.add("F_CLZJ");

	        createColumn(workbook, row, style, colList, 4, queryRS, "N");
	        
	        style = workbook.createCellStyle();
			setColumnAlign(style, "LEFT");                                                       // 设置列对齐方式
			setColumnBorder(style, 1);                                                           // 设置列边框		        
	        font.setFontHeightInPoints((short) 10);                                              // 字号 
	        style.setFont(font);	                                                             // 设置字体
	        
	        colList = new ArrayList<String>();
	        colList.add("F_KCQK");
	        colList.add("F_BZ");

	        createColumn(workbook, row, style, colList, 15, queryRS, "");
		}
	}
	
	/**
	 * 创建单元格
	 * @param row
	 * @param style
	 * @param colList
	 * @param startCol
	 * @param rowset
	 */
	private void createColumn(XSSFWorkbook workbook, XSSFRow row, XSSFCellStyle style, List<String> colList, int startCol, EFRowSet rowset, String type) {
		XSSFCell         cell = null;
		
		for(int i = 0; i < colList.size(); i++) {
			/**
	         * 创建单元格、设置列名称
	         */
	        cell = row.createCell(startCol);
			cell.setCellStyle(style);
			if(type.equals("N")) {
				XSSFDataFormat df = workbook.createDataFormat();
	        	cell.getCellStyle().setDataFormat(df.getFormat("#,##0.00"));
	        	cell.setCellValue(rowset.getNumber(colList.get(i), 0.0).doubleValue());              // 设置列名称
			} else {
				cell.setCellValue(rowset.getString(colList.get(i), ""));
			}
	        startCol++;
		}
	}
	
	/**
	 * 设置列对齐方式
	 * @param rowset
	 * @param style
	 * @param align
	 * @param columnId
	 */
	private void setColumnAlign(XSSFCellStyle style, String align) { 
		if (align.equals("LEFT")) {
			style.setAlignment(HSSFCellStyle.ALIGN_LEFT);                   // 水平居左
	        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);      // 垂直居中
		} else if (align.equals("RIGHT")) {
			style.setAlignment(HSSFCellStyle.ALIGN_RIGHT);                  // 水平居右
	        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);      // 垂直居中
		} else if (align.equals("RIGHT")) {
			style.setAlignment(HSSFCellStyle.ALIGN_CENTER_SELECTION);       // 水平居中
	        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);      // 垂直居中
		} 
		
	}
	
	/**
	 * 设置列边框
	 * @param style
	 */
	private void setColumnBorder(XSSFCellStyle style, int tableType) {
		//============================  
        // 设置单元格边框样式  
        // CellStyle.BORDER_DOUBLE      双边线  
        // CellStyle.BORDER_THIN        细边线  
        // CellStyle.BORDER_MEDIUM      中等边线  
        // CellStyle.BORDER_DASHED      虚线边线  
        // CellStyle.BORDER_HAIR        小圆点虚线边线  
        // CellStyle.BORDER_THICK       粗边线  
        //============================  
        
		//如果tableType为0，则为Head
		//如果为1，则为body
		if (tableType == 0) {
			style.setBorderTop(HSSFCellStyle.BORDER_MEDIUM );                                    // 顶部边框粗线
	        style.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM );                                 // 底部边框粗线
		} else {
			style.setBorderTop(HSSFCellStyle.BORDER_THIN );                                      // 顶部边框细线
	        style.setBorderBottom(HSSFCellStyle.BORDER_THIN );                                   // 底部边框细线
		}
        
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);                                          // 左边边框
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);                                         // 右边边框	        
        style.setBottomBorderColor(new XSSFColor(new java.awt.Color(153, 153, 255)));            // 设置底部边框颜色
        style.setTopBorderColor(new XSSFColor(new java.awt.Color(153, 153, 255)));               // 设置顶部边框颜色
        style.setLeftBorderColor(new XSSFColor(new java.awt.Color(153, 153, 255)));              // 设置左边边框颜色
        style.setRightBorderColor(new XSSFColor(new java.awt.Color(153, 153, 255)));             // 设置右边边框颜色
//        style.setWrapText(true);                                                                 // 设置单元格内容是否自动换行
	}

	/**
	 * 设置列上边框粗边线 
	 * @param style
	 */
	private XSSFCellStyle setColumnTopBorder(XSSFWorkbook workbook) {
		//============================  
        // 设置单元格边框样式  
        // CellStyle.BORDER_DOUBLE      双边线  
        // CellStyle.BORDER_THIN        细边线  
        // CellStyle.BORDER_MEDIUM      中等边线  
        // CellStyle.BORDER_DASHED      虚线边线  
        // CellStyle.BORDER_HAIR        小圆点虚线边线  
        // CellStyle.BORDER_THICK       粗边线  
        //============================  
		XSSFCellStyle   style = workbook.createCellStyle();
		style.setBorderTop(HSSFCellStyle.BORDER_MEDIUM );                                    // 顶部边框粗线
        
        style.setTopBorderColor(new XSSFColor(new java.awt.Color(153, 153, 255)));               // 设置顶部边框颜色
        return style;
	}
	
	/**
	 * 设置列下边框粗边线 
	 * @param style
	 */
	private XSSFCellStyle setColumnButtomBorder(XSSFWorkbook workbook) {
		//============================  
        // 设置单元格边框样式  
        // CellStyle.BORDER_DOUBLE      双边线  
        // CellStyle.BORDER_THIN        细边线  
        // CellStyle.BORDER_MEDIUM      中等边线  
        // CellStyle.BORDER_DASHED      虚线边线  
        // CellStyle.BORDER_HAIR        小圆点虚线边线  
        // CellStyle.BORDER_THICK       粗边线  
        //============================  
		XSSFCellStyle   style = workbook.createCellStyle();
		style.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM );                                     // 底部边框粗线
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);                                         // 右边边框	        
		style.setBottomBorderColor(new XSSFColor(new java.awt.Color(153, 153, 255)));            // 设置底部边框颜色
        style.setRightBorderColor(new XSSFColor(new java.awt.Color(153, 153, 255)));             // 设置右边边框颜色
        return style;
	}
	
	/**
	 * 生成Excel文件
	 * @param request
	 * @param response
	 * @param workbook
	 * @param title
	 * @throws Exception
	 */
	private File createExportExcel(HttpServletRequest request, HttpServletResponse response, XSSFWorkbook workbook, String title) throws Exception {
		String                       servletPath = request.getRealPath("") + "\\upload\\exp";
		String                              uuid = StringUtil.nextId();                                    // 生成一个随机UUID。
		java.util.Date                  currTime = new java.util.Date();
		SimpleDateFormat               formatter = new SimpleDateFormat("yyyyMMdd");
		String                         strFolder = formatter.format(currTime);		
		String                       newFileName = servletPath + "\\" + strFolder + "\\"  + uuid + ".xls"; // 构成新文件名。
		
        FileUtil.createDirectorys(servletPath + "\\" + strFolder);                                         //判断目录是否存在，每天导出的文件都放到对应的一个目录下
        File exportFile = new File(newFileName);   
        
    	FileOutputStream os = new FileOutputStream(exportFile);                                            // 文件输出流 
    	workbook.write(os);                                                                                // 将文档对象写入文件输出流
    	os.close();                                                                                        // 关闭文件输出流 
    	System.out.println("创建成功 office 2007 excel");
    	return exportFile;
	}
	
	/**
	 * 下载文件
	 * @param response
	 * @param exportFile
	 * @param title
	 */
	private void downloadFile(HttpServletResponse response, File exportFile, String title) {
		UploadFileUtil upFile = new UploadFileUtil();
		upFile.download(exportFile,title.replace(" ", "") + ".xls", "", response);
	}
}
