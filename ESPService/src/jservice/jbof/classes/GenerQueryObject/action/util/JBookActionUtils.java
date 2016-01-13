package jservice.jbof.classes.GenerQueryObject.action.util;

import java.awt.Color;
import java.io.*;
import java.lang.reflect.*;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFShape;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFSimpleShape;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.f1j.ss.*;
import com.f1j.swing.*;

import jframework.foundation.classes.*;
import jservice.jbof.classes.GenerQueryObject.action.*;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: Pansoft</p>
 * @author Stephen Zhao
 * @version 1.0
 */

public final class JBookActionUtils {
  static ResourceBundle res = ResourceBundle.getBundle("jservice.jbof.classes.GenerQueryObject.action.util.Language");
  static Map fontMap = new HashMap();
  static Map styleMap = new HashMap();
  private static int MAX_ROW =20000;
  /**
     *
     * @param book
     * @param Type
     */
    public static void setTextMargin(JBook book, long Type) {
        String Text;
        try {
            int SRow = 0, SCol = 0, ERow = 0, ECol = 0;
            SRow = book.getSelStartRow();
            SCol = book.getSelStartCol();
            ERow = book.getSelEndRow();
            ECol = book.getSelEndCol();
            for (int R = SRow; R <= ERow; R++) {
                for (int C = SCol; C <= ECol; C++) {
                    Text = book.getText(R, C);
                    if (Text.trim().length() == 0) {
                        continue;
                    }
                    try {
                        Double.valueOf(Text);
                    }
                    catch (Exception e) {
                        if (Type == 0) {
                            if (Text.substring(0, 1).equals(" ")) {
                                Text = Text.substring(1, Text.length());
                            }
                        }
                        else {
                            Text = " " + Text;
                        }
                        book.setText(R, C, Text);
                    }
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param book
     * @param startRow
     * @param startCol
     * @param endRow
     * @param endCol
     * @return
     */
    public static String coor2Formula(JBook book, int startRow, int startCol, int endRow, int endCol) {
        String T1 = null, T2 = null, Text = null;
        try {
            T1 = book.formatRCNr(startRow, startCol, false);
            if (startRow == endRow &&
                startCol == endCol) {
                Text = T1;
            }
            else {
                T2 = book.formatRCNr(endRow, endCol, false);
                Text = T1 + ":" + T2;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return Text;
    }

    /**
     * 转出到Excel97
     * @param book
     */
    public static void export2Excel(JBook book, String defaultName) {
        String path = null;
        //选择路径
        JFileChooser mFileChooser = new JFileChooser();
        mFileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        mFileChooser.setMultiSelectionEnabled(false);
        mFileChooser.setRequestFocusEnabled(true);
        mFileChooser.setFileFilter(new javax.swing.filechooser.FileFilter() {
            public boolean accept(File file) {
                if (file.getName().toLowerCase().endsWith(".xls")) {
                    return true;
                }
                return false;
            }

            public String getDescription() {
                return "*.xls";
            }
        });
        if (defaultName != null && defaultName.length() > 0) {
            try {
//        System.getProperty("user.home") + File.separator +
                String defaultPath = defaultName;
                File file = new File(defaultPath);
                mFileChooser.setSelectedFile(file);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }

        java.awt.Component mainWin = JActiveDComDM.MainApplication.MainWindow;

        int option = mFileChooser.showSaveDialog(mainWin);
        if (option == JFileChooser.OPEN_DIALOG) {
            if (mFileChooser.getSelectedFile() != null) {
                File file = mFileChooser.getSelectedFile();
                path = file.getAbsolutePath();
            }
        }
        else {
            return;
        }

        //报表转出
        String outputPath = "";
        try {
            outputPath = path + ".xls";
            book.write(outputPath, new WriteParams(JBook.eFileExcel97));
        }
        catch (IOException ioe) {
            JOptionPane.showMessageDialog(mainWin, res.getString("String_8") + ioe.getLocalizedMessage(), res.getString("String_9"), JOptionPane.ERROR_MESSAGE);
            ioe.printStackTrace();
            return;
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(mainWin, res.getString("String_10") + e.getLocalizedMessage(), res.getString("String_11"), JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            return;
        }
        JOptionPane.showMessageDialog(mainWin, res.getString("String_12") + outputPath, res.getString("String_13"), JOptionPane.INFORMATION_MESSAGE);
    }
    /**
     * 把查询结果转出到2007Excle
     * @param book
     * @param defaultName
     * @throws Exception
     */
    public static void exportToExcel(JBook book, String defaultName) throws Exception {
		// TODO Auto-generated method stub
    	 FileOutputStream fileOutputStream = null;
    	 String path = null;
    	 String tempFilePath = null;
    	 String tempOutputSystemPath =System.getenv("TMP");
    	 //String tempPath = System.getProperty("java.io.tmpdir");
         //选择路径
         JFileChooser mFileChooser = new JFileChooser();
         mFileChooser.setAcceptAllFileFilterUsed(false);
         //添加XML文件（大数据量）(*.xml)文件的过滤器
         mFileChooser.addChoosableFileFilter(new ExcelFileFilter("XML文件(大数据量Excle打开)(*.xml)"));
         //添加Excle 2007 工作薄(*.xlsx)文件的过滤器
         mFileChooser.addChoosableFileFilter(new ExcelFileFilter("Excle 2007 工作薄(*.xlsx)"));
         //添加Excle 97-2003 工作薄(*.xls)文件的过滤器
         mFileChooser.addChoosableFileFilter(new ExcelFileFilter("Excle 97-2003 工作薄(*.xls)"));
         //设置成只读模式
         mFileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
         //设置成单选模式
         mFileChooser.setMultiSelectionEnabled(false);
         mFileChooser.setRequestFocusEnabled(true);
         if (defaultName != null && defaultName.length() > 0) {
             try {
                 String defaultPath = defaultName;
                 File file = new File(defaultPath);
                 mFileChooser.setSelectedFile(file);
             }
             catch (Exception e) {
                 e.printStackTrace();
             }
         }

         java.awt.Component mainWin = JActiveDComDM.MainApplication.MainWindow;
         int option = mFileChooser.showSaveDialog(mainWin);
         if (option == JFileChooser.OPEN_DIALOG) {
             if (mFileChooser.getSelectedFile() != null) {
                 File file = mFileChooser.getSelectedFile();
                 path = file.getAbsolutePath();
             }
         }
         else {
             return;
         }
         String outputPath = "";
         String fileExtension1 = ".xls";
         int index = 0;
         int index1 = 0;
         String fileExtension = mFileChooser.getFileFilter().getDescription();
         if(fileExtension.indexOf(".") > -1){
          index = fileExtension.indexOf(".");
          index1 = fileExtension.length();
          fileExtension1 = fileExtension.substring(index,index1-1);
         }
   	  outputPath = path + fileExtension1;
   	  File fileExsits = new  File(outputPath);
   	  String overwriteName =defaultName;
   	  String strSeparator = System.getProperty("file.separator");
 	  int index2 = path.lastIndexOf(strSeparator);
          overwriteName = path.substring(index2);
  	  path = path.substring(0,index2);
         if (fileExsits!=null&&fileExsits.exists()&&fileExsits.getName().equals(defaultName+fileExtension1))
           {
             int overwriteSelect = JOptionPane.showConfirmDialog(mainWin,"文件[" + defaultName+fileExtension1+"]已存在,是否覆盖?",
                "提示",JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                if (overwriteSelect != JOptionPane.YES_OPTION){
               	 overwriteName = checkFileName(path,defaultName,fileExtension1);
                }
         }
         outputPath = path +strSeparator+overwriteName+ fileExtension1;
         //报表转出
         try {
          if(fileExtension1.equals(".xls")){
        	  book.write(outputPath, new WriteParams(JBook.eFileExcel97));
           }else if(fileExtension1.equals(".xml")){
            	 new ExportXmlFile(book,outputPath,defaultName);
           }else{
               int beginRow = 0;
               int beginCol = 0;
               int rowTotal = book.getLastRow()+1;
               int colTotal = book.getLastCol()+1;
               //创建新的 Excel 工作簿
               XSSFWorkbook workbook = new XSSFWorkbook();
//               tempFilePath = tempOutputSystemPath+strSeparator+defaultName+fileExtension1;
//               File file = new File(tempFilePath);
//               if(file!=null&&file.exists()){//先删除临时文件
//             		 file.delete();
//                }
//               fileOutputStream = new FileOutputStream(tempFilePath);
               fileOutputStream = new FileOutputStream(outputPath);
                 // 生成excel
               for (int t = 0, n = rowTotal/ MAX_ROW + 1; t < n; t++) {
                     //在 Excel 工作簿中建创一个工作表,其名为缺省值 sheet1,在一个excle中创建多个sheet
                     XSSFSheet sheet = workbook.createSheet(defaultName+String.valueOf(t));
                     for(int w = 0 ; w < colTotal ; w++){
                    	 int width = book.getColWidth(w);
                         sheet.setColumnWidth(w,width);
                     }
                     //合并标题单元格
                     if(t==0){
                       sheet.addMergedRegion(new CellRangeAddress(0,0,0,colTotal-1));
                     }
                     //创建字体
                     XSSFFont font = null;
                     //创建格式
                     XSSFCellStyle  cellStyle= null;
                     beginRow = 0;
                     for (int i = 1, min = (rowTotal - t * MAX_ROW + 1) > (MAX_ROW + 1) ? (MAX_ROW + 1): (rowTotal - t * MAX_ROW + 1); i < min; i++) {
                    	     XSSFRow row = sheet.createRow(beginRow++);
                    	     //sheet.groupRow(6, 24);
                    	     beginCol = 0;
                    	     int ii = MAX_ROW * (t) + i - 1;
                    	     for(int j = 0 ; j < colTotal; j++){//循环列
//                    	     sheet.autoSizeColumn(j); //sheet页自适应宽度
                             XSSFCell cell = row.createCell((int)beginCol++);
                             if(ii == 0){
                              	 //创建标题字体
                            	 XSSFFont fontTitle = workbook.createFont();
                            	 //创建标题格式
                                 XSSFCellStyle  cellStyleTitle= workbook.createCellStyle();
                            	 fontTitle.setFontName(book.getActiveCellEx().getCellFormat(ii,j).getFontName());
                            	 fontTitle.setColor((short)book.getActiveCellEx().getCellFormat(ii,j).getFontColor());
                            	 fontTitle.setFontHeightInPoints((short)book.getActiveCellEx().getCellFormat(ii,j).getFontSizeInPoints());
                            	 cellStyleTitle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
                                 // 把创建的字体付加于格式
                            	 cellStyleTitle.setFont(fontTitle);
        						 // 把上面的格式付加于一个单元格
        						 cell.setCellStyle(cellStyleTitle);
        						 // 设置此单元格中存入的是字符串
        						 cell.setCellType(XSSFCell.CELL_TYPE_STRING);
        						 cell.setCellValue(book.getText(ii, j));
                               }else{
                                   short lineColor = IndexedColors.CORNFLOWER_BLUE.getIndex();
                                   short HorizontalAlignment = book.getActiveCellEx().getCellFormat(ii,j).getHorizontalAlignment();
                                   short VerticalAlignment = book.getActiveCellEx().getCellFormat(ii,j).getVerticalAlignment();
                                   short BorderBottom = book.getActiveCellEx().getCellFormat(ii,j).getBottomBorder();
                                   short BorderLeft = book.getActiveCellEx().getCellFormat(ii,j).getLeftBorder();
                                   short BorderRight = book.getActiveCellEx().getCellFormat(ii,j).getRightBorder();
                                   short BorderTop = book.getActiveCellEx().getCellFormat(ii,j).getTopBorder();
                                   cellStyle = (XSSFCellStyle) getCellStyle(workbook,lineColor,HorizontalAlignment,VerticalAlignment,BorderBottom,BorderLeft,BorderRight,BorderTop);
                                   String valueFormat =  book.getActiveCellEx().getCellFormat(ii,j).getValueFormat();
                                   //设置此单元格中存入的是字符串
                                   if(valueFormat.indexOf("#")>-1){//数字
                                	   cell.setCellType(XSSFCell.CELL_TYPE_NUMERIC);
                                   }else{//字符串
                                	   cell.setCellType(XSSFCell.CELL_TYPE_STRING);
                                   }
                                   //优化代码，把字体格式存入map缓存，以提高转出速度
                                   String fontName = book.getActiveCellEx().getCellFormat(ii,j).getFontName();
                                   short fontColor = (short)book.getActiveCellEx().getCellFormat(ii,j).getFontColor();
                                   short fonHeightInpoints = (short) book.getActiveCellEx().getCellFormat(ii,j).getFontSizeInPoints();
                                   int fontSize =  book.getActiveCellEx().getCellFormat(ii,j).getFontSize();
                                   font = (XSSFFont) getFont(workbook,fontName,fontColor,fonHeightInpoints,fontSize);
                                   //把创建的字体付加于格式
                                   cellStyle.setFont(font);
                                   //把上面的格式付加于一个单元格
                                   cell.setCellStyle(cellStyle);
                                   cell.setCellValue(book.getText(ii,j));
                                 }
//                             cell.setCellValue(book.getText(ii,j));
                    	     }
                    	     System.gc();//回收垃圾
                               }
                         }
                 workbook.write(fileOutputStream);
                 fileOutputStream.flush();
               }
         }catch (IOException ioe) {
             JOptionPane.showMessageDialog(mainWin, res.getString("String_8") + ioe.getLocalizedMessage(), res.getString("String_9"), JOptionPane.ERROR_MESSAGE);
             ioe.printStackTrace();
             return;
         }
         catch (Exception e) {
             JOptionPane.showMessageDialog(mainWin, res.getString("String_10") + e.getLocalizedMessage(), res.getString("String_11"), JOptionPane.ERROR_MESSAGE);
             e.printStackTrace();
             return;
         }finally{
        	 // 操作结束，关闭文件
        	 try{
        	 if(fileExtension1.equals(".xlsx")){
        	 if(fileOutputStream!=null){fileOutputStream.close();fileOutputStream = null;}
        	 if(fontMap != null){fontMap.clear();}
        	 if(styleMap != null){styleMap.clear();}
//        	 File file1 = new File(outputPath);
//        	 if(file1!=null&&file1.exists()){
//        		 file1.delete();
//        	 }
//        	 new MergerSheet(tempFilePath, outputPath,defaultName);
//        	 File file2 = new File(tempFilePath);
//        	 if(file2!=null&&file2.exists()){
//        		 System.gc();//回收垃圾
//        		 file2.delete();//强制删除
//        	 }
         }
        	 }catch(Exception e){

        	 }
         }
         JOptionPane.showMessageDialog(mainWin, res.getString("String_12") + outputPath, res.getString("String_13"), JOptionPane.INFORMATION_MESSAGE);
	}
    /**
     * 字体 fontMap为全局的Map，用来保存相同字体要求的Font对象
	 * 当要取得字体对象时，先判断是否已经缓存了，如果是，则不需要再创建
     * @param workbook
     * @param fontName
     * @param fontColor
     * @param fonHeightInpoints
     * @param fontSize
     * @return
     */
    private static Font getFont(Workbook workbook, String fontName, short fontColor, short fonHeightInpoints, int fontSize) {
		Font font = (Font) fontMap.get(fontName + fontColor+fonHeightInpoints+fontSize);
		if (font == null) {
			font = workbook.createFont();
			font.setFontName(fontName);
			font.setColor(fontColor);
			font.setFontHeightInPoints((short) fonHeightInpoints);
			fontMap.put(fontName + fontColor+fonHeightInpoints+fontSize, font);
		}
		return font;
	}
    /**
     * 设定样式，然后将样式缓存在全局的HashMap对象styleMap 中
     * @param borderTop
     * @param borderRight
     * @param borderLeft
     * @param borderBottom
     * @param verticalAlignment
     * @param horizontalAlignment
     * @param lineColor
     * @return
     */
    private static CellStyle getCellStyle(Workbook workbook,short lineColor, short horizontalAlignment, short verticalAlignment, short borderBottom, short borderLeft, short borderRight, short borderTop){
      String key = String.valueOf(lineColor+horizontalAlignment+verticalAlignment+borderBottom+borderLeft+borderRight+borderTop);
      CellStyle cellStyle = (CellStyle) styleMap.get(key);
      if (cellStyle == null) {
    	  cellStyle  = workbook.createCellStyle();
          cellStyle.setRightBorderColor(lineColor);
          cellStyle.setLeftBorderColor(lineColor);
          cellStyle.setBottomBorderColor(lineColor);
          cellStyle.setTopBorderColor(lineColor);
          cellStyle.setAlignment( horizontalAlignment);
          cellStyle.setVerticalAlignment(verticalAlignment);
          cellStyle.setBorderBottom(borderBottom);
          cellStyle.setBorderLeft(borderLeft);
          cellStyle.setBorderRight(borderRight);
          cellStyle.setBorderTop(borderTop);
          styleMap.put(key,cellStyle);
    }
    return cellStyle;
    }

    /**
    * 检查此文件夹下有无同名，若有返回新文件名“文件名_重名”
    * @param strPath        "D:\\excelTo\\"
    * @param checkFilename   文件名
    * @return   文件名
    */
    private static String checkFileName(String strPath ,String checkFilename,String fileExtension){
       File file = new File (strPath);
       if(file!=null&&file.exists()){
         File[] f = file.listFiles();
         for(int i=0;i < f.length;i++){
           if(f[i].getName().equals(checkFilename+fileExtension)){
             checkFilename+=checkFilename+"_重名";
             checkFileName(strPath,checkFilename,fileExtension);
             break;
           }
         }
       }
       return checkFilename;
    }

    /**
     * 查找实现方法
     * @param actionName
     * @return
     */
    public static Method lookupActionImplMethod(String actionName) {
        try {
            if (actionName != null) {
                String methodName = JBookActionConstants.ACTION_METHOD_PREFIX + actionName;
                Method method = JBookActionImpl.class.getMethod(methodName, new Class[] {JBookAction.class}); ;
                return method;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     *
     * @param clazz
     * @param book
     * @param isModal
     */
    public static void invakeJBookDialog(Class clazz, JBook book, boolean isModal) throws Exception {
        SwingUtilities.invokeLater(new JBookDialogProxy(
            clazz,
            book,
            true
            ));
    }

//    public static void main(String []arg){
//    	XSSFWorkbook wb = new XSSFWorkbook();  // or new XSSFWorkbook();
//    	for(int i = 0 ;i < 2 ; i++){
//        XSSFSheet sheet1 = wb.createSheet("new sheet"+i);
//    	}
//        FileOutputStream fileOut;
//		try {
//			fileOut = new FileOutputStream("c:\\workbook.xlsx");
//			wb.write(fileOut);
//	        fileOut.close();
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//    }
}
