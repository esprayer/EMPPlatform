package com.report.table.jxml;

import java.awt.Font;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */


public class XMLAttr extends XmlElement{

  public XMLAttr() {
  }
  private String value;

  public void setValue(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }
}
