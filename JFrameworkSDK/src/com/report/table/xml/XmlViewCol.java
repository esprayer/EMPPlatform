package com.report.table.xml;

import com.report.table.jxml.XmlElement;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author Lubo
 * @version 1.0
 */

public class XmlViewCol extends XmlElement{

  private String type;
  public XmlViewCol() {
  }
  public String getType() {
    return type;
  }
  public void setType(String type) {
    this.type = type;
  }
}
