package com.core.xml;

import javax.swing.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class JStubObject {
  public String ID = null;
  public String Name = null;
  public String Caption = null;
  public String ClassName = null;
  public Object CalssObject = null;
  public Icon   ObjectIcon = null;
  public Object AddObject  = null;
  public JStubObject() {
  }
  public String toString() {
    return  Caption;
  }
  public Icon getIcon() {
    return ObjectIcon;
  }
}
