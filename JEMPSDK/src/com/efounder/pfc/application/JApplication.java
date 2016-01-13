package com.efounder.pfc.application;

import jfoundation.gui.window.classes.JMainWindow;

import com.efounder.pfc.window.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class JApplication {
  protected String Name;
  protected String Language;
  protected JMainWindow MainWindow = null;
  public JApplication() {
  }
  public int Execute(Object Param,Object[] Array) {
    return 0;
  }
  public int Execute() {
    return Execute(null,null);
  }
  public void setName(String n) {
    Name = n;
  }
  public String getName() {
    return Name;
  }
  public void setLanguage(String n) {
    Language = n;
  }
  public String getLanguage() {
    return Language;
  }
  public void setMainWindow(JMainWindow mw) {
    MainWindow = mw;
  }
  public JMainWindow getMainWindow() {
    return MainWindow;
  }
}
