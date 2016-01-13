package jenterprise.bof.classes.AppExplorerObject;

import java.awt.*;

import javax.swing.*;

import jenterprise.bof.classes.*;
import jbof.application.classes.*;
import jbof.gui.window.classes.*;
import jbof.gui.window.classes.style.mdi.*;
import jframework.foundation.classes.*;
import jfoundation.gui.window.classes.JMainWindow;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */
//--------------------------------------------------------------------------------------------------
//描述:
//设计: Skyline(2001.12.29)
//实现: Skylin
//修改:
//--------------------------------------------------------------------------------------------------
public class JAppExplorerChildWindow extends JBOFMDIChildWindow {
  BorderLayout borderLayout1 = new BorderLayout();
  JTabbedPane tpExplorer = new JTabbedPane();
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  public JAppExplorerChildWindow() {
    try {
      jbInit();
    }
    catch(Exception ex) {
      ex.printStackTrace();
    }
  }
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  public JAppExplorerChildWindow(JBOFApplication App,JActiveObject AO) {
    super(App,AO);
    try {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  void jbInit() throws Exception {
    this.setLayout(borderLayout1);
    this.add(tpExplorer,  BorderLayout.CENTER);
  }
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  public Object InitChildWindow(JBOFApplication App,JMainWindow MainWindow,Object AddObject,Object AddObject1) {
    Object Key;String ClassName;JBOFChildWindow cw;JPanelItemStub pis;
    try {
      for(int i=0;i<JBOFAppExplorerObject.PanelList.size();i++) {
        pis = (JPanelItemStub)JBOFAppExplorerObject.PanelList.get(i);
        ClassName = pis.classname;
        cw = (JBOFChildWindow)Class.forName(ClassName).newInstance();
        cw.LoadWindowIcon();
        cw.setApplication(App);
        cw.setActiveObject((JActiveObject)AddObject);
        cw.InitChildWindow(App,App.MainWindow,AddObject,AddObject1);
        tpExplorer.insertTab(cw.getChildWindowName(),cw.WindowIcon,cw,cw.getChildWindowName(),i);
      }
    } catch ( Exception e ) {
      e.printStackTrace();
    }
    return null;
  }
  //----------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //----------------------------------------------------------------------------------------------
  public boolean Close() {
      return false;
  }
}
