package jenterprise.bof.classes.AppExplorerObject;

import java.awt.*;


import jbof.application.classes.*;
import jbof.gui.window.classes.style.mdi.*;
import jframework.foundation.classes.*;
import javax.swing.*;
import java.util.ResourceBundle;

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
public class JFlowCtrlPanel extends JBOFMDIChildWindow {
  static ResourceBundle res = ResourceBundle.getBundle("jenterprise.bof.classes.AppExplorerObject.Language");
  BorderLayout borderLayout1 = new BorderLayout();
  private JLabel jLabel1 = new JLabel();
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  public JFlowCtrlPanel() {
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
  public JFlowCtrlPanel(JBOFApplication App,JActiveObject AO) {
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
    jLabel1.setHorizontalAlignment(SwingConstants.CENTER);
    jLabel1.setHorizontalTextPosition(SwingConstants.CENTER);
    jLabel1.setText(res.getString("String_25"));
    this.setLayout(borderLayout1);
    this.add(jLabel1, BorderLayout.CENTER);
  }
  //----------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //----------------------------------------------------------------------------------------------
  public String getChildWindowName() {
    return res.getString("String_26");
  }
}
