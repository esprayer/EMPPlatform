package com.put.util;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.util.CellRangeAddress;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFDataFormat;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.efounder.builder.base.data.EFDataSet;
import com.efounder.builder.base.data.EFRowSet;

public class EMPExportExcelMultiTable {
	
	/**
	 * 将数据导出生成excel文件
	 * @param queryDS             查询数据集
	 * @param columnDS            列数据集
	 * @param subHeadList         副标题信息
	 * @param title               标题
	 * @return
	 * @throws Exception
	 */
	public void exportExcelTable(HttpServletRequest request, HttpServletResponse response, EFDataSet queryDataSet, List<String> subHeadList, String title) throws Exception {
		//创建excel对象
		XSSFWorkbook workBook = getExportFileSheet();
		//创建标题
		setHeadCellFont(workBook, title);
		//创建副标题
		createSubHeads(workBook, subHeadList);
		//创建列信息
		exportColumnData(workBook, subHeadList);
		//创建数据信息
		exportData(workBook, queryDataSet, subHeadList);
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
        workbook.getSheetAt(0).addMergedRegion(new CellRangeAddress(0,0,0,16));                       // 四个参数分别是：起始行，结束行，起始列，结束列
        cell.setCellValue(title);                                                                     // 写入头标题
	}
	
	/**
	 * 创建副标题
	 * @param workbook
	 */
	private void createSubHeads(XSSFWorkbook workbook, List subHeadList) {
		
		XSSFCellStyle   style = null;
		int          rowCount = subHeadList.size() / 2 + subHeadList.size() % 2;                                                                        // 副标题行数	
		int          fristCol = 0;
		int       columnCount = 26;
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
	private void exportColumnData(XSSFWorkbook workbook, List subHeadList) {	
		XSSFCellStyle          style = null;	
		XSSFRow                  row = null;   
		XSSFCell                cell = null;
		XSSFFont                font = workbook.createFont();                                           // 创建字体对象
		XSSFCellStyle       topStyle = setColumnTopBorder(workbook);
		XSSFCellStyle    buttomStyle = setColumnButtomBorder(workbook);
		int                 rowCount = subHeadList.size() / 2 + subHeadList.size() % 2 + 1;         
		
		row = workbook.getSheetAt(0).createRow(rowCount);                                               // 创建一个行对象 列标题
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
        cell.setCellValue("会计期间");
        workbook.getSheetAt(0).addMergedRegion(new CellRangeAddress(rowCount, rowCount + 1, 0, 0));
        cell.setCellStyle(style); 
        
		cell = row.createCell(1);
        cell.setCellValue("单据编号");
        workbook.getSheetAt(0).addMergedRegion(new CellRangeAddress(rowCount, rowCount + 1, 1, 1));
        cell.setCellStyle(style); 

		cell = row.createCell(2);
        cell.setCellValue("分录编号");
        workbook.getSheetAt(0).addMergedRegion(new CellRangeAddress(rowCount, rowCount + 1, 2, 2));
        cell.setCellStyle(style); 

		cell = row.createCell(3);
        cell.setCellValue("材料入库信息");
        workbook.getSheetAt(0).addMergedRegion(new CellRangeAddress(rowCount, rowCount, 3, 8));
        cell.setCellStyle(style); 

        cell = row.createCell(4);
        cell.setCellStyle(topStyle); 
        
        cell = row.createCell(5);
        cell.setCellStyle(topStyle); 
        
        cell = row.createCell(6);
        cell.setCellStyle(topStyle); 
        
        cell = row.createCell(7);
        cell.setCellStyle(topStyle); 
        
        cell = row.createCell(8);
        cell.setCellStyle(topStyle); 

		cell = row.createCell(9);
        cell.setCellValue("材料出库信息");
        workbook.getSheetAt(0).addMergedRegion(new CellRangeAddress(rowCount, rowCount, 9, 14));
        cell.setCellStyle(style); 

        cell = row.createCell(10);
        cell.setCellStyle(topStyle); 
        
        cell = row.createCell(11);
        cell.setCellStyle(topStyle); 
        
        cell = row.createCell(12);
        cell.setCellStyle(topStyle); 
        
        cell = row.createCell(13);
        cell.setCellStyle(topStyle); 
        
        cell = row.createCell(14);
        cell.setCellStyle(topStyle); 

		cell = row.createCell(15);
        cell.setCellValue("材料编号");
        workbook.getSheetAt(0).addMergedRegion(new CellRangeAddress(rowCount, rowCount + 1, 15, 15));
        cell.setCellStyle(style); 

        cell = row.createCell(16);
        cell.setCellValue("材料名称");
        workbook.getSheetAt(0).addMergedRegion(new CellRangeAddress(rowCount, rowCount + 1, 16, 16));
        cell.setCellStyle(style); 
        
        cell = row.createCell(17);
        cell.setCellValue("规格型号");
        workbook.getSheetAt(0).addMergedRegion(new CellRangeAddress(rowCount, rowCount + 1, 17, 17));
        cell.setCellStyle(style); 
        
        cell = row.createCell(18);
        cell.setCellValue("计量单位");
        workbook.getSheetAt(0).addMergedRegion(new CellRangeAddress(rowCount, rowCount + 1, 18, 18));
        cell.setCellStyle(style); 
        
        cell = row.createCell(19);
        cell.setCellValue("供应商编号");
        workbook.getSheetAt(0).addMergedRegion(new CellRangeAddress(rowCount, rowCount + 1, 19, 19));
        cell.setCellStyle(style); 
        
        cell = row.createCell(20);
        cell.setCellValue("供应商名称");
        workbook.getSheetAt(0).addMergedRegion(new CellRangeAddress(rowCount, rowCount + 1, 20, 20));
        cell.setCellStyle(style); 
        
        cell = row.createCell(21);
        cell.setCellValue("出入方向");
        workbook.getSheetAt(0).addMergedRegion(new CellRangeAddress(rowCount, rowCount + 1, 21, 21));
        cell.setCellStyle(style); 
        
        cell = row.createCell(22);
        cell.setCellValue("单据类型");
        workbook.getSheetAt(0).addMergedRegion(new CellRangeAddress(rowCount, rowCount + 1, 22, 22));
        cell.setCellStyle(style); 
        
        cell = row.createCell(23);
        cell.setCellValue("材料单价");
        workbook.getSheetAt(0).addMergedRegion(new CellRangeAddress(rowCount, rowCount + 1, 25, 25));
        cell.setCellStyle(style); 
        
        cell = row.createCell(24);
        cell.setCellValue("材料数量");
        workbook.getSheetAt(0).addMergedRegion(new CellRangeAddress(rowCount, rowCount + 1, 23, 23));
        cell.setCellStyle(style); 
        
        cell = row.createCell(25);
        cell.setCellValue("材料总价");
        workbook.getSheetAt(0).addMergedRegion(new CellRangeAddress(rowCount, rowCount + 1, 24, 24));
        cell.setCellStyle(style); 
        
        row = workbook.getSheetAt(0).createRow(rowCount + 1);                                               // 创建一个行对象 列标题
        /**
         * 创建单元格、设置列名称、合并单元格
         */
        
        //材料入库信息
        cell = row.createCell(3);
        cell.setCellValue("仓库编号");
        cell.setCellStyle(style); 
        
		cell = row.createCell(4);
        cell.setCellValue("仓库编号");
        cell.setCellStyle(style); 
        
		cell = row.createCell(5);
        cell.setCellValue("仓库名称");
        cell.setCellStyle(style); 

		cell = row.createCell(6);
        cell.setCellValue("项目编号");
        cell.setCellStyle(style); 

		cell = row.createCell(7);
        cell.setCellValue("项目名称");
        cell.setCellStyle(style); 

		cell = row.createCell(8);
        cell.setCellValue("产品编号");
        cell.setCellStyle(style); 
        
        
        
        //材料出库信息
        cell = row.createCell(9);
        cell.setCellValue("产品名称");
        cell.setCellStyle(style); 
        
        cell = row.createCell(10);
        cell.setCellValue("仓库编号");
        cell.setCellStyle(style); 
        
		cell = row.createCell(11);
        cell.setCellValue("仓库名称");
        cell.setCellStyle(style); 

		cell = row.createCell(12);
        cell.setCellValue("项目编号");
        cell.setCellStyle(style); 

		cell = row.createCell(13);
        cell.setCellValue("项目名称");
        cell.setCellStyle(style); 

		cell = row.createCell(14);
        cell.setCellValue("产品编号");
        cell.setCellStyle(style); 
 
        
        for(int i = 0; i < 3; i++) {
        	cell = row.createCell(i);
            cell.setCellStyle(buttomStyle); 
        }
        
        for(int i = 15; i < 26; i++) {
        	cell = row.createCell(i);
            cell.setCellStyle(buttomStyle); 
        }
	}
	
	/**
	 * 创建列
	 * @param workbook
	 */
	private void exportData(XSSFWorkbook workbook, EFDataSet queryDataSet, List subHeadList) {	
		EFRowSet      queryRS = null;
		XSSFRow           row = null;   
		XSSFFont         font = workbook.createFont();                                               // 创建字体对象
		XSSFCellStyle   style = workbook.createCellStyle();
		List<String>  colList = new ArrayList<String>();
		// 获取数据开始行，副标题信息除以2为副标题占有所有行，再加1为标题的行数，最后加一行列信息，所以最后加3
		int          startRow = subHeadList.size() / 2 + subHeadList.size() % 2 + 2;
		//循环数据集中每行数据
		for(int i = 0; i < queryDataSet.getRowCount(); i++) {
			startRow += 1;                                                                           // 获取行号
			row = workbook.getSheetAt(0).createRow(startRow);                                        // 创建一个行对象
			queryRS = queryDataSet.getRowSet(i);
			//循环列数据集中每个列信息
			style = workbook.createCellStyle();
			setColumnAlign(style, "LEFT");                                                       // 设置列对齐方式
			setColumnBorder(style, 1);                                                           // 设置列边框		        
	        font.setFontHeightInPoints((short) 10);                                              // 字号 
	        style.setFont(font);	                                                             // 设置字体
	        
	        colList = new ArrayList<String>();
	        colList.add("F_KJQJ");
	        colList.add("F_DJBH");
	        colList.add("F_FLBH");
	        colList.add("F_CKBH");
	        colList.add("F_CKMC");
	        colList.add("F_XMBH");
	        colList.add("F_XMMC");
	        colList.add("F_CPBH");
	        colList.add("F_CPMC");
	        colList.add("F_YYCKBH");
	        colList.add("F_YYCKMC");
	        colList.add("F_YYXMBH");
	        colList.add("F_YYXMMC");
	        colList.add("F_YYCPBH");
	        colList.add("F_YYCPMC");
	        colList.add("F_CLBH");
	        colList.add("F_CLMC");
	        colList.add("F_GGXH");
	        colList.add("F_JLDW");
	        colList.add("F_DWBH");
	        colList.add("F_DWMC");
	        colList.add("F_CSBH");
	        colList.add("F_CSMC");
	        colList.add("F_CRFX");
	        colList.add("F_DJLX");

	        createColumn(workbook, row, style, colList, 0, queryRS, "");
			
	        style = workbook.createCellStyle();
			setColumnAlign(style, "RIGHT");                                                      // 设置列对齐方式
			setColumnBorder(style, 1);                                                           // 设置列边框		        
	        font.setFontHeightInPoints((short) 10);                                              // 字号 
	        style.setFont(font);	                                                             // 设置字体
	        
	        colList = new ArrayList<String>();
	        colList.add("F_CLDJ");
	        colList.add("F_CLSL");
	        colList.add("F_CLZJ");

	        createColumn(workbook, row, style, colList, 23, queryRS, "N");
	        
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
				if(colList.get(i).equals("F_CRFX")) {
					if(rowset.getString(colList.get(i), "").equals("R")) {
						cell.setCellValue("入库单");
					} else if(rowset.getString(colList.get(i), "").equals("C")) {
						cell.setCellValue("出库单");
					} else if(rowset.getString(colList.get(i), "").equals("T")) {
						cell.setCellValue("退库单");
					} else if(rowset.getString(colList.get(i), "").equals("D")) {
						cell.setCellValue("调拨单");
					}
				} else if(colList.get(i).equals("F_DJLX")) {
					if(rowset.getString(colList.get(i), "").equals("R0")) {
						cell.setCellValue("采购入库");
					} else if(rowset.getString(colList.get(i), "").equals("R1")) {
						cell.setCellValue("更换入库");
					} else if(rowset.getString(colList.get(i), "").equals("T0")) {
						cell.setCellValue("退库单");
					} else if(rowset.getString(colList.get(i), "").equals("T1")) {
						cell.setCellValue("材料退货");
					} else if(rowset.getString(colList.get(i), "").equals("C0")) {
						cell.setCellValue("正常出库");
					} else if(rowset.getString(colList.get(i), "").equals("C1")) {
						cell.setCellValue("借调出库");
					} else if(rowset.getString(colList.get(i), "").equals("C2")) {
						cell.setCellValue("被借调出库");
					} else if(rowset.getString(colList.get(i), "").equals("D")) {
						cell.setCellValue("仓库调拨");
					}
				} else {
					cell.setCellValue(rowset.getString(colList.get(i), ""));
				}				
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
