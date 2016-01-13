package com.efounder.eai.application;

import java.awt.Component;

import javax.swing.Icon;

import com.core.xml.StubObject;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public interface IManagerApplication {
  public int ExecuteApplication(String AppName,Object[] Array);
  public int ExecuteApplication(String AppName);
}
