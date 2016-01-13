package jservice.jbof.classes.BOFOfflineObject;

import jbof.application.classes.*;
import jbof.gui.window.classes.*;
import jframework.foundation.classes.*;
import jfoundation.object.classes.JBOFClass;
//--------------------------------------------------------------------------------------------------
//描述:
//设计: Skyline(2001.04.22)
//实现: Skyline
//修改:
//--------------------------------------------------------------------------------------------------
public class JOfflineDataInfoObject implements Runnable {
  static JBOFChildWindow DCWindow = null;
  static JBOFChildWindow DBWindow = null;
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  public JOfflineDataInfoObject() {
  }
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  public static void OpenDataChildWindow(JBOFApplication Application,JActiveObject AO) {
      try {
        Application.MainWindow.BeginWaitCursor();
        if ( DCWindow == null ) {
          // 获取子窗口的类
          DCWindow = (JBOFChildWindow)Class.forName("jservice.jbof.classes.BOFOfflineObject.JOfflineDataChildWindow").newInstance();
          DCWindow.LoadWindowIcon();
          DCWindow.setApplication(Application);
          DCWindow.setActiveObject(AO);
          // 进行子窗口的初始化
          DCWindow.InitChildWindow(Application,Application.MainWindow,null,null);
          DCWindow.LoadGUIResource();
        }
        if ( DCWindow != null ) {
          Application.OpenObjectWindow("离线数据管理",DCWindow.WindowIcon,"离线数据管理",DCWindow);
        }
      } catch(Exception e) {
        e.printStackTrace();
      } finally {
        Application.MainWindow.EndWaitCursor();
      }
  }
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  public static void OpenDataSetChildWindow(JBOFApplication Application,JActiveObject AO) {
      try {
        Application.MainWindow.BeginWaitCursor();
        if ( DBWindow == null ) {
          // 获取子窗口的类
          DBWindow = (JBOFChildWindow)Class.forName("jservice.jbof.classes.BOFOfflineObject.JAgentDataSetDemoWindow").newInstance();
          DBWindow.LoadWindowIcon();
          DBWindow.setApplication(Application);
          DBWindow.setActiveObject(AO);
          // 进行子窗口的初始化
          DBWindow.InitChildWindow(Application,Application.MainWindow,null,null);
          DBWindow.LoadGUIResource();
        }
        if ( DBWindow != null ) {
          Application.OpenObjectWindow("DataSet演示",DBWindow.WindowIcon,"DataSet演示",DBWindow);
        }
      } catch(Exception e) {
        e.printStackTrace();
      } finally {
        Application.MainWindow.EndWaitCursor();
      }
  }
  public void run() {
    JBOFClass.CallObjectMethod(DCWindow,"StartRun");
//    DCWindow.StartRun();
  }
}
