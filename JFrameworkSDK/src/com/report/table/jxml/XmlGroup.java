package com.report.table.jxml;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author Lubo
 * @version 1.0
 */
public class XmlGroup extends XmlElement {
  private boolean grouped;
  private String  no="";
  private String  caption ="";
  private String  fontname="";
  private String  fontsize="";
  private java.util.Vector items;
  private String fontstyle="";
  public XmlGroup() {
    setGrouped(false);
  }

  public void setGrouped(boolean id){
    grouped = id;
  }

  public boolean getGrouped(){
    return grouped;
  }
  public String getCaption() {
    return caption;
  }
  public String getFontname() {
    return fontname;
  }
  public String getFontsize() {
    return fontsize;
  }
  public java.util.Vector getItems() {
    return items;
  }
  public String getNo() {
    return no;
  }
  public void setCaption(String caption) {
    this.caption = caption;
  }
  public void setFontname(String fontname) {
    this.fontname = fontname;
  }
  public void setFontsize(String fontsize) {
    this.fontsize = fontsize;
  }
public void setItems(java.util.Vector items) {
    this.items = items;
  }
  public void setNo(String no) {
    this.no = no;
  }
  public void setFontstyle(String fontstyle) {
    this.fontstyle = fontstyle;
  }
  public String getFontstyle() {
    return fontstyle;
  }
  public java.awt.Font getFont(){
    return null;
  }
  public XmlItem getItemById(String id){
    return (XmlItem)XmlManager.getElementById(this.getItems(),id);
  }

}
