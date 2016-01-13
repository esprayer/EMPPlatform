package com.efounder.eai.ui.action;

import com.efounder.action.UpdateAction;
import java.awt.event.*;
import com.efounder.pfc.window.*;
import javax.swing.*;
import com.efounder.eai.ui.*;
import org.openide.*;

/**
 * <p>Title: Enterprise Explorer</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: EFounder</p>
 * @author Skyline
 * @version 1.0
 */

public class JWindowAction extends UpdateAction {
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

  public JWindowAction() {
  }
  public void actionPerformed(ActionEvent e,IView view,IWindow window) {
    view.activeWindow(window);
  }
  public final void doPerformed(ActionEvent e) {
//    Object comp = e.getSource();
//    try {
//      WaitingManager.getDefault().beginWait(findWindow(comp));
      actionPerformed(e, ActionView, ActionWindow);
//    } catch ( Exception ex ) {
//      ex.printStackTrace();
//    } finally {
//      WaitingManager.getDefault().endWait(findWindow(comp));
//    }
  }
  public JWindowAction(IView ExplorerView,IWindow Window,String s, char c, String s1, Icon icon)
  {
    super(s, c, s1, icon);
    this.ActionView = ExplorerView;
    ActionWindow = Window;
  }
  public IWindow getWindow() {
    return ActionWindow;
  }
  protected void unregistryWindow(IWindow cw) {
    // 将此窗口取消注册
    WindowManager.unregistryWindow(cw);
  }
}
