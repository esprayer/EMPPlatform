package com.help.xml;

import com.report.table.jxml.XmlElement;

/**
 * <p>Title: XML标题管理.非公用类</p>
 * <p>Description: 特定类</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Pansoft</p>
 * @author chaor
 * @version 1.0
 */


public class XmlTitle extends XmlElement{

  public XmlTitle() {
  }
  private String align;
  private String fontname;
  private String fontsize;
  private java.util.Vector labels;
  private String fontstyle;
  public String getAlign() {
    return align;
  }
  public void setAlign(String align) {
    this.align = align;
  }
  public void setFontname(String fontname) {
    this.fontname = fontname;
  }
  public String getFontname() {
    return fontname;
  }
  public void setFontsize(String fontsize) {
    this.fontsize = fontsize;
  }
  public String getFontsize() {
    return fontsize;
  }
  public void setLabels(java.util.Vector labels) {
    this.labels = labels;
  }
  public java.util.Vector getLabels() {
    return labels;
  }
  public void setFontstyle(String fontstyle) {
    this.fontstyle = fontstyle;
  }
  public String getFontstyle() {
    return fontstyle;
  }
}