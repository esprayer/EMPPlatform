package com.print.service;

import java.util.ResourceBundle;

import com.report.table.jxml.XmlTitle;
/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public interface CustomPrint {
  static ResourceBundle res = ResourceBundle.getBundle("com.print.service.Language");
  public static final String COUPLING_FRONT  = res.getString("String_16");
  public static final String COUPLING_AFTER  = res.getString("String_17");
  public static final String SPLIT_PAGE_TEXT = "";
  // 获取指定列的宽度
  public int getColWidth(ColumnDefine CD);
  // 获取第一行标题的字体名称
  public String getFirstTitleFontName(XmlTitle title);
  // 获取第一行标题的字体大小
  public int getFirstTitleFontSize(XmlTitle title);
  // 获取第一行标题的字体名称
  public String getTitleFontName(XmlTitle title);
  // 获取第一行标题的字体大小
  public int getTitleFontSize(XmlTitle title);
  // 获取表头的字体名称
  public String getHeadFontName();
  // 获取表头的字体大小
  public int getHeadFontSize();
  // 获取某一列的字体名称
  public String getColFontName(ColumnDefine CD);
  // 获取某一列的字体大小
  public int getColFontSize(ColumnDefine CD);
  // 获取某一列的格式
  public String getColFormat(ColumnDefine CD);
  // 获取某一列的类型
  public String getColType(ColumnDefine CD);
  // 获取某一列的对齐方式
  public String getColAlign(ColumnDefine CD);
  // 获取承前页的字符串
  public String getFrontPageTitle();
  // 获取承后页的字符串
  public String getAfterPageTitle();
  // 获取页分割的字符串
  public String getSplitPageText();
  // 获取软件名称
  public String getSoftName();
  // 获取单位名称
  public String getUnitName();
  // 获取打印时间
  public String getPrintDate();
  //
  public void setDefaultPageFormat(PrintFormatObject printFormatObject);
  //
  public double getRowHeigth();
  //
  public double getFirstRowHeigth();
  public void beginPrintProgress();
  public void watchPrintProgress(int printType,int VPageno,int HPageno);
  public void endPrintProgress();
  //获取字体是否加粗
  public boolean getTitleFontBold();
  //获取字体是否斜体
  public boolean getTitleFontItalic();
  //获取字体是否加粗
  public boolean getDefaultFontBold();
  //获取字体是否斜体
  public boolean getDefaultFontItalic();
}
