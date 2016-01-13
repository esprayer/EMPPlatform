package com.efounder.eai.ui.action;

import java.awt.event.*;
import javax.swing.*;

import com.efounder.action.*;
import com.efounder.pfc.window.*;

/**
 * <p>Title: Enterprise Explorer</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: EFounder</p>
 * @author Skyline
 * @version 1.0
 */

public class JViewAction extends StateAction {
  IView   ActionView = null;
  IWindow ActionWindow = null;
  public void setActionView(IView ActionView) {
    this.ActionView = ActionView;
  }

  public IView getActionView() {
    return ActionView;
  }

  public void setActionWindow(IWindow ActionWindow) {
    this.ActionWindow = ActionWindow;
  }

  public IWindow getActionWindow() {
    return ActionWindow;
  }

  public JViewAction() {
  }
  public void actionPerformed(ActionEvent e,IView view,IWindow window) {

  }
  public boolean getState(Object obj) {
    if ( ActionWindow != null ) {
      return ActionView.isVisible(ActionWindow)&&ActionView.isVisible();
    } else {
      return ActionView.isVisible();
    }
  }

  public void setState(Object obj, boolean flag) {
    if ( ActionWindow != null ) {
      ActionView.setVisible(ActionWindow,flag);
    } else {
      ActionView.setVisible(flag);
    }
  }

//  public final void actionPerformed(ActionEvent e) {
//    actionPerformed(e,ActionView,ActionWindow);
//  }
  public JViewAction(IView ExplorerView,IWindow Window,String s, char c, String s1, Icon icon, Icon icon1)
  {
      super(s, c, s1, icon, icon1);
      this.ActionView = ExplorerView;
      ActionWindow = Window;
  }

//  public JViewAction(IView ExplorerView,IWindow Window,String s, char c, String s1, Icon icon, String s2)
//  {
//    super(s, c, s1, icon, s2);
//    this.ActionView = ExplorerView;
//    ActionWindow = Window;
//  }

  public JViewAction(IView ExplorerView,IWindow Window,String s, char c, String s1, Icon icon)
  {
    super(s, c, s1, icon);
    this.ActionView = ExplorerView;
    ActionWindow = Window;
  }

  public JViewAction(IView ExplorerView,IWindow Window,String s, char c, String s1)
  {
    super(s, c, s1);
    this.ActionView = ExplorerView;
    ActionWindow = Window;
  }

  public JViewAction(IView ExplorerView,IWindow Window,String s, char c)
  {
    super(s, c);
    this.ActionView = ExplorerView;
    ActionWindow = Window;
  }

  public JViewAction(IView ExplorerView,IWindow Window,String s)
  {
    super(s);
    this.ActionView = ExplorerView;
    ActionWindow = Window;
  }

  public JViewAction(IView ExplorerView,IWindow Window)
  {
    super(Window.getTitle());
    this.ActionView = ExplorerView;
  }

}
