package com.help.xml;

import com.report.table.jxml.XmlElement;

/**
 * <p>Title: XML的标签管理.非公用类</p>
 * <p>Description: 特定类</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Pansoft</p>
 * @author chaor
 * @version 1.0
 */


public class XmlLabel extends XmlElement {

  public XmlLabel() {
  }
  private String caption;
  public String getCaption() {
    return caption;
  }
  public void setCaption(String caption) {
    this.caption = caption;
  }
}