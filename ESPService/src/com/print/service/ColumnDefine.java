package com.print.service;

import java.util.ResourceBundle;



/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
/**
 F_SZBH       varchar(20)   not null,
 F_BH       varchar(4)    not null,
 F_MC       varchar(20)   null,
 *F_COL      varchar(20)   not null,
 *F_CAPT     varchar(20)   null,
 *F_ZT       varchar(20)   null,
 *F_ZH       varchar(5)    null,
 *F_ZY       char(1)       null,1：粗体,2：斜体,3：下划线
 *F_WIDTH    varchar(20)   null,
 *F_FORMAT   varchar(20)   null,
 *F_ALIGN    char(10)      null left:左,center：中,right：右,x_axis:水平自动
 */
public class ColumnDefine implements Cloneable {
  static ResourceBundle res = ResourceBundle.getBundle("com.pansoft.print.service.Language");
  protected String ColType   = null;// 数据类型
  protected String ColDec    = null;// 小数位数
  protected String ColID     = null;// 列ID
  protected String ColName   = null;// 列英文名称
  protected String ColText   = null;// 列中文名称
  protected String ColCaption= null;// 列标识
  protected String ColWidth  = null;// 列宽度
  protected String FontName  = "宋体"; // 字体名字
  protected String FontSize  = null;    // 字体大小
  protected String FontStyle = null;     // 字体样式
  protected String ColAlign  = null;     // 对齐方式
  protected String ColFormat = null;  // 列的显示格式
  //add by fengbo 2007-10-30
  protected String Showmask = null;  // 列的showmask属性

  // 只能有一个父节点
  protected ColumnDefine parentCol = null;

  public void setParentCol(ColumnDefine parentCol) {
    this.parentCol = parentCol;
  }

  public void setShowmask(String Showmask) {
    this.Showmask = Showmask;
  }

  public ColumnDefine getParentCol() {
    return parentCol;
  }

  public String getShowmask() {
    return Showmask;
  }

  public void setColType(String ColType) {
    this.ColType = ColType;
  }

  public String getColType() {
    return ColType;
  }

  public void setColDec(String ColDec) {
    this.ColDec = ColDec;
  }

  public String getColDec() {
    return ColDec;
  }

  public void setColID(String ColID) {
    this.ColID = ColID;
  }

  public String getColID() {
    return ColID;
  }

  public void setColCaption(String ColCaption) {
    this.ColCaption = ColCaption;
  }

  public String getColCaption() {
    return ColCaption;
  } /**
   * 检查是否有子列
   * @return boolean
   */


  public void setFontSize(String FontSize) {
    this.FontSize = FontSize;
  }

  public String getFontSize() {
    return FontSize;
  }

  public void setFontName(String FontName) {
    this.FontName = FontName;
  }

  public String getFontName() {
    return FontName;
  }

  public void setColName(String ColName) {
    this.ColName = ColName;
  }

  public String getColName() {
    return ColName;
  }

  public void setColText(String ColText) {
    this.ColText = ColText;
  }

  public String getColText() {
    return ColText;
  }

  public void setColAlign(String ColAlign) {
    this.ColAlign = ColAlign;
  }

  public String getColAlign() {
    return ColAlign;
  }

  public void setColWidth(String ColWidth) {
    this.ColWidth = ColWidth;
  }

  public String getColWidth() {
    return ColWidth;
  }

  public void setFontStyle(String FontStyle) {
    this.FontStyle = FontStyle;
  }

  public String getFontStyle() {
    return FontStyle;
  }

  public void setColFormat(String ColFormat) {
    this.ColFormat = ColFormat;
  }

  public String getColFormat() {
    return ColFormat;
  }

  public ColumnDefine() {
  }
  public Object clone() {
    ColumnDefine CD = null;
    try {// parentCol
       CD = (ColumnDefine)super.clone();
       CD.parentCol = null;
    } catch ( Exception e ) {
    }
    return CD;
  }
}
