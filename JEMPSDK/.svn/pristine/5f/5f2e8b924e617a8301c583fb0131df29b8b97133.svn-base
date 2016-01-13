package com.efounder.eai.application;

import java.awt.Component;
import java.util.Hashtable;

import javax.swing.Icon;

import com.efounder.eai.EAI;
import com.efounder.eai.application.classes.JBOFApplication;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class JManagerApplication implements IManagerApplication {
  public Hashtable ApplicationList = new Hashtable();
  public JManagerApplication() {
  }
  //------------------------------------------------------------------------------------------------
  //����:
  //���: Skyline(2001.12.29)
  //ʵ��: Skyline
  //�޸�:
  //------------------------------------------------------------------------------------------------
  public void InitManager() throws Exception {
	  JBOFApplication desktopPane = EAI.getMainWindow();
	  if(desktopPane == null) {
		  EAI.createMainWindow();
	  }	  
  }
  
  public void OpenObjectWindow(String title,Icon icon, String tip,Component comp) {
	  EAI.getMainWindow().openWindow(title, icon, tip, comp);
  }
  
  @Override
  public int ExecuteApplication(String AppName, Object[] Array) {
	
	return 0;
  }
  @Override
  public int ExecuteApplication(String AppName) {
	  return ExecuteApplication(AppName,null);
  }
}
