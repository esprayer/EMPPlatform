package com.etsoft.pub.util;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import esyt.framework.com.builder.base.data.EFDataSet;
import esyt.framework.com.builder.base.data.EFRowSet;

public class EMPExportExcelTable {
	
	/**
	 * 将数据导出生成excel文件
	 * @param queryDS             查询数据集
	 * @param columnDS            列数据集
	 * @param subHeadList         副标题信息
	 * @param title               标题
	 * @return
	 * @throws Exception
	 */
	public void exportExcelTable(HttpServletRequest request, HttpServletResponse response, EFDataSet queryDS, EFDataSet columnDS, List<String> subHeadList, String title) throws Exception {
		//创建excel对象
		XSSFWorkbook workBook = getExportFileSheet();
		//创建标题
		setHeadCellFont(workBook, columnDS, title);
		//创建副标题
		setSubHeadCellFont(workBook, columnDS, subHeadList);
		//创建列信息
		exportColumnData(workBook, columnDS, subHeadList);
		//创建数据信息
		exportData(workBook, queryDS, columnDS, subHeadList);
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
	private void setHeadCellFont(XSSFWorkbook workbook, EFDataSet columnDS, String title) {
		
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
        workbook.getSheetAt(0).addMergedRegion(new CellRangeAddress(0,0,0,columnDS.getRowCount() - 1));   // 四个参数分别是：起始行，结束行，起始列，结束列
        cell.setCellValue(title);                                                                     // 写入头标题
	}
	
	/**
	 * 创建副标题
	 * @param workbook
	 */
	private void setSubHeadCellFont(XSSFWorkbook workbook, EFDataSet columnDS, List subHeadList) {
		
		XSSFCellStyle   style = null;
		int          rowCount = subHeadList.size() / 2 + subHeadList.size() % 2;                                                                        // 副标题行数	
		int          fristCol = 0;
		int       columnCount = columnDS.getRowCount();
		String          title = "";
		XSSFRow           row = null;   
		XSSFCell         cell = null;
		XSSFFont         font = workbook.createFont();                                                                             // 创建字体对象
		/**
		 * 副标题每行放两个，一个居左，一个居右，所以除以2，多出来的一个，另起一行，
		 */
		for(int i = 0; i < rowCount; i++) {
			row = workbook.getSheetAt(0).createRow(i + 1);                                                                         // 创建一个行对象 ,加1是因为有一行头标题
			for(int j = 0; j < 2; j++) {
				if(subHeadList.size() <= (i * 2 + j)) break;
				style = workbook.createCellStyle();
				title = subHeadList.get(i * 2 + j).toString();                      
				font.setFontHeightInPoints((short) 9);                                                                             // 字号 
		        style.setFont(font);
				if(j == 0) {
					 /**
			         * 因为每行两个副标题，所以第一个标题占一半
			         */
			        fristCol = columnCount / 2;        
					cell = row.createCell(j);                                                                                      // 创建单元格
					cell.setCellValue(title);  
					style.setAlignment(HSSFCellStyle.ALIGN_LEFT);                                                                  // 水平居左
			        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);                                                     // 垂直居中
			        cell.setCellStyle(style);                                                                                      // 应用样式对象                                                
			        workbook.getSheetAt(0).addMergedRegion(new CellRangeAddress(i + 1, i + 1, 0, fristCol - 1));                   // 合并单元格
				} else {
					/**
			         * 因为每行两个副标题，第一个标题占一半，第二个占剩下的所有单元格
			         */
			        fristCol = columnCount - (columnCount / 2) - (columnCount % 2);
					cell = row.createCell(columnCount / 2);                                                                                          // 创建单元格
					cell.setCellValue(title);  
					                                                                                                               // 字号 
			        style.setFont(font);
					style.setAlignment(HSSFCellStyle.ALIGN_RIGHT);                                                                 // 水平居右
			        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);                                                     // 垂直居中
			        cell.setCellStyle(style);           // 应用样式对象
			        
			        workbook.getSheetAt(0).addMergedRegion(new CellRangeAddress(i + 1, i + 1, fristCol, columnCount - 1));         // 合并单元格
				}
			}
		}
	}
	
	/**
	 * 创建列
	 * @param workbook
	 */
	private void exportColumnData(XSSFWorkbook workbook, EFDataSet columnDS, List subHeadList) {	
		XSSFCellStyle            style = workbook.createCellStyle();	
		XSSFRow                    row = null;   
		XSSFCell                  cell = null;
		EFRowSet             colRowset = null;
		XSSFFont                  font = workbook.createFont();                                      // 创建字体对象
		int                   startRow = subHeadList.size() / 2 + subHeadList.size() % 2 + 1;        // 获取数据开始行，副标题信息除以2加1为副标题占有所有行，再加1为标题的行数，所以最后加2
		int                columnWidth = 0;
		String              columnMask = "";
		String[]            masksArray = null;
		String[]             maskArray = null;
		Map<String, String>    maskMap = new HashMap<String, String>();
		row = workbook.getSheetAt(0).createRow(startRow);                                            // 创建一个行对象 列标题
		for(int i = 0; i < columnDS.getRowCount(); i++) {
			colRowset = columnDS.getRowSet(i);                                                       // 获取列对象
			columnMask = colRowset.getString("COLUMN_MASK", null);                                   // 获取列显示掩码
			if(columnMask != null) {
				masksArray = columnMask.split(";");
				for(int j = 0; masksArray != null && masksArray.length > 0 && j < masksArray.length; j++) {
					maskArray = masksArray[j].split(":");
					if(maskArray != null && maskArray.length > 0) {
						maskMap.put(maskArray[0], maskArray[1]);
					}
				}
				colRowset.setExtProperty("MASKMAP", maskMap);
			}
			cell = row.createCell(i);                                                                // 创建单元格
			cell.setCellValue(colRowset.getString("COLUMN_NAME", ""));                               // 设置列名称
			style.setAlignment(HSSFCellStyle.ALIGN_CENTER_SELECTION);                                // 水平居中
	        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);                               // 垂直居中
	        font.setFontHeightInPoints((short) 10);                                                  // 字号 
	        style.setFont(font);	                                                                 // 设置字体
	        style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);                                    // 设置前景填充样式 
	        style.setFillForegroundColor(new XSSFColor(new java.awt.Color(204, 204, 255)));          // 设置单元格背景色
	        setColumnBorder(style, 0);                                                               // 设置列边框
	        columnWidth = Integer.parseInt(colRowset.getString("COLUMN_WIDTH", "100"));
	        workbook.getSheetAt(0).setColumnWidth(i, 32 * columnWidth);                              // 对列设置宽度
	        cell.setCellStyle(style); 
		}
	}
	
	/**
	 * 创建列
	 * @param workbook
	 */
	private void exportData(XSSFWorkbook workbook, EFDataSet queryDS, EFDataSet columnDS, List subHeadList) {	
		EFRowSet             queryRS = null;
		EFRowSet            columnRS = null;
		XSSFRow                  row = null;   
		XSSFCell                cell = null;
		XSSFFont                font = workbook.createFont();                                               // 创建字体对象
		XSSFCellStyle          style = workbook.createCellStyle();
        String             COLUMN_ID = "";
        String               maskKey = "";
        String             maskValue = "";
        Map<String, String>  maskMap = null;
        
		// 获取数据开始行，副标题信息除以2为副标题占有所有行，再加1为标题的行数，最后加一行列信息，所以最后加3
		int          startRow = subHeadList.size() / 2 + subHeadList.size() % 2 + 1;
		//循环数据集中每行数据
		for(int i = 0; i < queryDS.getRowCount(); i++) {
			startRow += 1;                                                                           // 获取行号
			row = workbook.getSheetAt(0).createRow(startRow);                                        // 创建一个行对象
			queryRS = queryDS.getRowSet(i);
			//循环列数据集中每个列信息
			for(int j = 0; j < columnDS.getRowCount(); j++) {
				style = workbook.createCellStyle();
				cell = row.createCell(j);                                                            // 创建单元格
				cell.setCellStyle(style);                                                            // 应用样式对象
				columnRS = columnDS.getRowSet(j);
				maskMap = (Map<String, String>) columnRS.getExtProperty("MASKMAP", null);
				
				setColumnAlign(style, columnRS.getString("COLUMN_ALIGN", ""));                       // 设置列对齐方式
				setColumnBorder(style, 1);                                                           // 设置列边框		        
		        font.setFontHeightInPoints((short) 10);                                              // 字号 
		        style.setFont(font);	                                                             // 设置字体
		        COLUMN_ID = columnRS.getString("COLUMN_ID", "");
		        if (columnRS.getString("COLUMN_TYPE", "").equals("C")) {
		        	maskKey = queryRS.getString(COLUMN_ID, "");
		        	if(maskMap != null) {
		        		if(maskMap.get(maskKey) == null) maskValue = maskKey;
		        		else maskValue = maskMap.get(maskKey).toString();
		        	} else {
		        		maskValue = maskKey;
		        	}
		        	cell.setCellValue(maskValue);                                                    // 设置列名称
		        } else if (columnRS.getString("COLUMN_TYPE", "").equals("N")) {
		        	maskKey = (queryRS.getNumber(COLUMN_ID, null) == null ? "" : queryRS.getNumber(COLUMN_ID, 0).toString());
		        	if(maskMap != null) {
		        		if(maskMap.get(maskKey) != null) {
		        			maskValue = maskMap.get(maskKey).toString();
			        		cell.setCellValue(maskValue);    
		        		}
		        	} else {
		        		XSSFDataFormat df = workbook.createDataFormat();
			        	cell.getCellStyle().setDataFormat(df.getFormat("#,##0.00"));
			        	cell.setCellValue(queryRS.getNumber(COLUMN_ID, 0.0).doubleValue());              // 设置列名称
		        	}
		        }
			}
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
		com.etsoft.pub.util.UploadFileUtil upFile = new com.etsoft.pub.util.UploadFileUtil();
		upFile.download(exportFile,title.replace(" ", "") + ".xls", "", response);
	}
}
