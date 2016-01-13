package com.common;

import java.awt.*;
import javax.swing.*;
import jframework.foundation.classes.*;
import jfoundation.gui.window.classes.*;
import javax.swing.border.*;

import com.core.xml.JBOFClass;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class JWaitDialog extends JFrameDialog {
  JPanel WaitPanel = new JPanel();
  BorderLayout borderLayout1 = new BorderLayout();
  JPanel UserObject = new JPanel();
  TitledBorder titledBorder1;
  Border border1;
//
//  public JWaitDialog(Frame frame, String title, boolean modal) {
//    super(frame, title, modal);
//
//  }

  public JWaitDialog(JPanel UserObject) {
    super(JActiveDComDM.MainApplication.MainWindow, "", false);
//    super.setMenuShow(false);
    this.UserObject = UserObject;
    JBOFClass.CallObjectMethod(UserObject,"setParentObject",this);
    try {
      jbInit();
//      pack();
    }
    catch(Exception ex) {
      ex.printStackTrace();
    }
  }

  private void jbInit() throws Exception {
    titledBorder1 = new TitledBorder("");
    border1 = BorderFactory.createBevelBorder(BevelBorder.RAISED,new Color(0, 0, 128),new Color(0, 0, 128),new Color(0, 0, 128),new Color(0, 0, 128));
    setUndecorated(true);
    WaitPanel.setLayout(borderLayout1);
    WaitPanel.setBorder(border1);
    getContentPane().add(WaitPanel);
    WaitPanel.add(UserObject, BorderLayout.CENTER);
    this.setSize(350,80);
  }
  public Object getUserObject() {
    return UserObject;
  }
}
