package com.help.xml;

import com.report.table.jxml.XmlElement;

/**
 * <p>Title: XML列管理.非公用类</p>
 * <p>Description: 特定类</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Pansoft</p>
 * @author chaor
 * @version 1.0
 */

public class XmlColumn extends XmlElement {

  private String no;
  private String caption;
  private String width;
  private String fontname;
  private String fontsize;
  private String viewctrl;
  private String editctrl;
  private String lock;
  private String change;
  private String datatype;
  private String fontstyle;
  private String format;
  private String align;
  private String showmask;
  public XmlColumn() {
  }

  public String getCaption() {
    return caption;
  }

  public String getChange() {
    return change;
  }

  public String getDatatype() {
    return datatype;
  }
  public String getShowMask(){
    return showmask;
  }
  public String getEditctrl() {
    return editctrl;
  }

  public String getFontname() {
    return fontname;
  }

  public String getFontsize() {
    return fontsize;
  }

  public String getLock() {
    return lock;
  }

  public String getNo() {
    return no;
  }

  public String getViewctrl() {
    return viewctrl;
  }

  public String getWidth() {
    return width;
  }

  public static void main(String[] args) {
    XmlColumn column1 = new XmlColumn();
  }

  public void setCaption(String caption) {
    this.caption = caption;
  }

  public void setChange(String change) {
    this.change = change;
  }

  public void setDatatype(String datatype) {
    this.datatype = datatype;
  }

  public void setShowMask(String showmask) {
    this.showmask = showmask;
  }
  public void setEditctrl(String editctrl) {
    this.editctrl = editctrl;
  }

  public void setFontname(String fontname) {
    this.fontname = fontname;
  }

  public void setFontsize(String fontsize) {
    this.fontsize = fontsize;
  }

  public void setLock(String lock) {
    this.lock = lock;
  }

  public void setNo(String no) {
    this.no = no;
  }

  public void setViewctrl(String viewctrl) {
    this.viewctrl = viewctrl;
  }

  public void setWidth(String width) {
    this.width = width;
  }

  public void setFontstyle(String fontstyle) {
    this.fontstyle = fontstyle;
  }

  public String getFontstyle() {
    return fontstyle;
  }

  public void setFormat(String format) {
    this.format = format;
  }

  public String getFormat() {
    return format;
  }

  public void setAlign(String align) {
    this.align = align;
  }

  public String getAlign() {
    return align;
  }
}
