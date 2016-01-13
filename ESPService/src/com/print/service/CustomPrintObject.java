package com.print.service;

import java.util.*;

import com.efounder.eai.data.JParamObject;
import com.f1j.swing.*;
import com.report.table.jxml.XmlTitle;

import jservice.jbof.classes.GenerQueryObject.*;
import jservice.jbof.classes.*;
import java.awt.print.*;
import javax.print.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class CustomPrintObject implements CustomPrint {
  static ResourceBundle res = ResourceBundle.getBundle("com.print.service.Language");
  private String  CompanyName = null;
  private String  SoftName    = null;
  private String  PrintDate   = null;
  public JQueryStubObject QO = null;
  private JBook book = null;
  private JBook printBook = null;
  private double  FirstTitleRowHeight = 0.36;//0.40;//0.50 // 主标题的行高
  private double  RowHeight = 0.18;//0.20//0.25; // 固定行高 默认是0.25英寸
  public String DEFAULT_FONT_NAME = "宋体";
  public String DEFAULT_NUMBER_FONT_NAME = "Dialog";
  public int DEFAULT_FONT_SIZE = 12;//8;//12;
  public int FIRST_ROW_FONT_SIZE = 16;//16;//24
  public String FIRST_ROW_FONT_NAME = DEFAULT_FONT_NAME;
//  public int DEFAULT_ORIENTATION = 1;//打印方向
  public boolean DEFAULT_ORIENTATION = true;//打印方向
  public double TOPMARGIN=0.5;    //页边距
  public double BOTTOMMARGIN=0.5;
  public double LEFTMARGIN=1;
  public double RIGHTMARGIN=1;
  public String DESIGNATEDPAGE = "0"; //是否打印指定页
  public int SaterPageNO = 0; //开始页
  public short PaperSize = 0;//纸张
  public boolean AccountBook  = false;//是否是正式账页
  public boolean TITLE_FONT_BOLD = false;
  public boolean TITLE_FONT_ITALIC = false;
  public boolean DEFAULT_FONT_BOLD = false;
  public boolean DEFAULT_FONT_ITALIC = false;
  public int desColIndex = 3;//摘要列
  public CustomPrintObject(JParamObject PO,JQueryStubObject QO,JBook book,AccountPrintFormatParam zszyFormat) {
    this.book = book;
    this.QO = QO;
    initContext(PO);
    setPrintOption(zszyFormat);
  }
  
  public CustomPrintObject(JParamObject PO,JQueryStubObject QO,JBook book,JBook printBook,AccountPrintFormatParam zszyFormat) {
	this.book = book;
	this.printBook = printBook;
	this.QO = QO;
	initContext(PO);
	setPrintOption(zszyFormat);
  }
  
  private void initContext(JParamObject PO) {
    this.CompanyName = PO.GetValueByEnvName("CW_SYDWMC", "");
    this.SoftName    = PO.GetValueByEnvName("CW_SOFTMC", "");
    Calendar Cale = Calendar.getInstance();
    int Year,Month,Day;
    Year  = Cale.get(Calendar.YEAR);
    Month = Cale.get(Calendar.MONTH)+1;// 月份是从0开始的需要加1
    Day   = Cale.get(Calendar.DAY_OF_MONTH);
    PrintDate   = Year+res.getString("String_25")+Month+res.getString("String_26")+Day+res.getString("String_27");
  }

  // 获取指定列的宽度
  public int getColWidth(ColumnDefine CD) {
    String swidth = CD.getColWidth();
    return Integer.valueOf(swidth).intValue();
  }
  // 获取第一行标题的字体名称
  public String getFirstTitleFontName(XmlTitle title) {
    return FIRST_ROW_FONT_NAME;
  }
  // 获取第一行标题的字体大小
  public int getFirstTitleFontSize(XmlTitle title) {
    return FIRST_ROW_FONT_SIZE;
  }
  // 获取标题的字体名称
  public String getTitleFontName(XmlTitle title) {
    return DEFAULT_FONT_NAME;//title.getFontname();
  }
  // 获取标题的字体大小
  public int getTitleFontSize(XmlTitle title) {
    return DEFAULT_FONT_SIZE;//Integer.valueOf(title.getFontsize()).intValue();
  }
  // 获取表头的字体名称
  public String getHeadFontName() {
    return DEFAULT_FONT_NAME;
  }
  // 获取表头的字体大小
  public int getHeadFontSize() {
    return DEFAULT_FONT_SIZE;
  }
  // 获取某一列的字体名称
  public String getColFontName(ColumnDefine CD) {
    String type = CD.getColType();
    if ( "C".equals(type.toUpperCase()) ) {
      return DEFAULT_FONT_NAME;
    }
//    if ( "N".equals(type.toUpperCase()) ) {
    return DEFAULT_NUMBER_FONT_NAME;
//    }
//    String font = CD.getFontName();
//    if ( font == null || "".equals(font.trim()) )
//      font = DEFAULT_FONT_NAME;
//      return font;
  }
  // 获取某一列的字体大小
  public int getColFontSize(ColumnDefine CD) {
    return DEFAULT_FONT_SIZE;
//    String swidth = CD.getFontSize();int width;
//    if ( swidth == null || "".equals(swidth.trim()) ) {
//      width = DEFAULT_FONT_SIZE;
//    } else width = Integer.valueOf(swidth).intValue();
//    return width;
  }
  // 获取某一列的格式
  public String getColFormat(ColumnDefine CD) {
    return CD.getColFormat();
  }
  //获取表头字体是加粗
  public boolean getTitleFontBold(){
    return TITLE_FONT_BOLD;
  }
  //获取表头字体是斜体
  public boolean getTitleFontItalic(){
    return TITLE_FONT_ITALIC;
  }
  //获取字体是加粗
  public boolean getDefaultFontBold(){
    return DEFAULT_FONT_BOLD;
  }
  //获取字体是斜体
  public boolean getDefaultFontItalic(){
    return DEFAULT_FONT_ITALIC;
  }
  // 获取某一列的类型
  public String getColType(ColumnDefine CD) {
    return CD.getColType();
  }
  public String getColAlign(ColumnDefine CD) {
    return CD.getColAlign();
  }
  // 获取承前页的字符串
  public String getFrontPageTitle() {
    return CustomPrint.COUPLING_FRONT;
  }
  // 获取承后页的字符串
  public String getAfterPageTitle() {
    return CustomPrint.COUPLING_AFTER;
  }
  // 获取页分割的字符串
  public String getSplitPageText() {
    return CustomPrint.SPLIT_PAGE_TEXT;
  }
  // 获取软件名称
  public String getSoftName() {
    return SoftName;
  }
  // 获取单位名称
  public String getUnitName() {
    return CompanyName;
  }
  // 获取打印时间
  public String getPrintDate() {
    return PrintDate;
  }
  //
  public double getRowHeigth() {
    return RowHeight;
  }
  public String getDesignatedPage(){
	  return DESIGNATEDPAGE;
  }
  
  public int getSaterPageNO(){
	  return SaterPageNO;
  }
  
  //
  public double getFirstRowHeigth() {
    return FirstTitleRowHeight;
  }
  public void setDefaultPageFormat(PrintFormatObject printFormatObject) {
    setDefaultPrintPageAttributes(book);
    if(printBook != null) {
    	setDefaultPrintPageAttributes(printBook);
    }
    printFormatObject.desColIndex = desColIndex;
  }
  public void setPrintOption(AccountPrintFormatParam AccountFormat){
    if (AccountFormat!=null){
      if (AccountFormat.Type.equals("2")){//如果是日记账
        desColIndex = 5;
      }

      DEFAULT_ORIENTATION = AccountFormat.Default_Orientation;

      TOPMARGIN = AccountFormat.TopMargin / 100;
      BOTTOMMARGIN = AccountFormat.BottomMargin / 100;
      LEFTMARGIN = AccountFormat.LeftMargin / 100;
      RIGHTMARGIN = AccountFormat.RightMargin / 100;

      FIRST_ROW_FONT_NAME = AccountFormat.TitleFontName;
      FIRST_ROW_FONT_SIZE = AccountFormat.TitleFontSize;

      DEFAULT_FONT_NAME = AccountFormat.DefaultFontName;
      DEFAULT_FONT_SIZE = AccountFormat.DefaultFontSize;

//      FirstTitleRowHeight  = 0.5;
//      RowHeight = 0.6;

      TITLE_FONT_BOLD = AccountFormat.TitleFontBold;
      TITLE_FONT_ITALIC = AccountFormat.TitleFontItalic;
      DEFAULT_FONT_BOLD = AccountFormat.DefaultFontBold;
      DEFAULT_FONT_ITALIC = AccountFormat.DefaultFontItalic;

      PaperSize = AccountFormat.PaperSize;
      AccountBook = AccountFormat.AccountBook;
      DESIGNATEDPAGE = AccountFormat.DesignatedPage;
      SaterPageNO = AccountFormat.SaterPageNO;
    }
  }
  private void setDefaultPrintPageAttributes(JBook book) {
	if (book == null) {
		return;
	}
	try {
		if (PaperSize==0){//使用系统纸张
			PageFormat PF = PrinterJob.getPrinterJob().defaultPage(); //
	        if (PF == null) {
	          return;
	        }
	        //返回的单位是 1/72nds of an inch
	        //纸张宽度
	        double width = PF.getWidth();
	        Double DwidthTwips = new Double( (width / 72) * 1440); //得到twips单位
	        int widthTwips = DwidthTwips.intValue();
	        //纸张高度
	        double height = PF.getHeight();
	        Double DheightTwips = new Double( (height / 72) * 1440);
	        int heightTwips = DheightTwips.intValue();
	        book.setPrintPaperSize(widthTwips, heightTwips); //设置纸张大小

	        int ORIENTATION = PF.getOrientation();
	        if (ORIENTATION == PageFormat.LANDSCAPE) { //纵向
	          book.setPrintLandscape(true);
	        } else {
	          book.setPrintLandscape(false);
	        }
	      }else if (PaperSize > 0){
	        book.setPrintPaperSize(PaperSize);
	        //如果是正式账页,则设置方向
	        if (AccountBook){
	          // 打印方向 纵向
	          book.setPrintLandscape(DEFAULT_ORIENTATION);
	        }
	      }

	      book.setPrintLeftToRight(true);

//	      //页宽
//	      if(AccountBook){
//	        book.setPrintScaleFitToPage(true);
//	        book.setPrintScaleFitHPages(1);
//	        book.setPrintScaleFitVPages(9999);
//	      }
//	      else{
//	        book.setPrintScaleFitToPage(false);
//	      }

	      //颜色
	      book.setPrintNoColor(true);
	      //网格线
	      book.setPrintGridLines(false);
	      //页眉为空
	      book.setPrintHeader("");
	      //
	      book.setPrintFooter("");
	      //
	      book.setPrintTopMargin(TOPMARGIN);
	      book.setPrintBottomMargin(BOTTOMMARGIN);
	      book.setPrintLeftMargin(LEFTMARGIN);
	      book.setPrintRightMargin(RIGHTMARGIN);
	      book.setPrintFooterMargin(0.0);
	      book.setPrintHeaderMargin(0.0);
	}
	catch (Exception e) {
		e.printStackTrace();
	}
  }
  
  /**
   *
   * @param printType int 0：格式 1:数据
   * @param HPageno int
   * @param VPageno int
   */
  public void watchPrintProgress(int printType,int VPageno,int HPageno) {
//    String text = "正在生成第" +String.valueOf(VPageno)+"-"+String.valueOf(HPageno)+ "页的打印";
//    if ( printType == 0 ) {
//      text = text+"格式，请稍候...";
//    }
//    if ( printType == 1 ) {
//      text = text+"数据，请稍候...";
//    }
//    JGenerQueryObject.getQueryWait(QO).setTitle1(text);
  }
  public void beginPrintProgress() {
//    JGenerQueryObject.processWait(QO);
//    JGenerQueryObject.showWaiting(QO);
  }
  public void endPrintProgress() {
//    JGenerQueryObject.hideWaiting(QO);
  }

}
