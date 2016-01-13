package jbof.gui.window.classes.style.mdi.imp;

import java.awt.event.*;
import java.util.*;

import javax.swing.*;

import com.efounder.eai.application.classes.JBOFApplication;

import jbof.application.classes.*;
/**
 * <p>Title: Java Framework</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author Skyline
 * @version 1.0
 */

public class JMenuItemStub {
  public static Properties ObjectList = new Properties();
  public String       Name=null;
  public String       Caption=null;
  public String       OperateItem=null;
  public String       Icon=null;
  public int          Type = 0;
  public int          Toolbar=0;
  public String       IsPublic="F";
  public com.jidesoft.swing.JideMenu        Menu=null;
  public JMenuItem    MenuItem=null;
  public AbstractButton      Button=null;
  public ImageIcon    Image=null;
  public KeyStroke    KS=null;
  public String OperateNo = null;
//  public Class        OperateClass  = null;
//  public Object       OperateObject = null;
  ActionListener      FrameWindow=null;
  JBOFApplication     Application=null;
  //----------------------------------------------------------------------------------------------
  //描述: 构�\uFFFD�函�\uFFFD
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //----------------------------------------------------------------------------------------------
  public JMenuItemStub(ActionListener fw,JBOFApplication app) {
      FrameWindow = fw;
      Application = app;
  }
  public void setEnabel(boolean b) {
    if ( MenuItem != null ) MenuItem.setEnabled(b);
    if ( Button   != null ) Button.setEnabled(b);
  }
  public boolean getEnable() {
    if ( MenuItem != null ) return MenuItem.isEnabled();
    return false;
  }
  public void setText(String b) {
    Caption = b;
    if ( MenuItem != null ) MenuItem.setText(b);
    if ( Button   != null ) Button.setToolTipText(b);
  }

}
