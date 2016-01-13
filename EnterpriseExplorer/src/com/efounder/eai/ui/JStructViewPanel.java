package com.efounder.eai.ui;

import com.efounder.pfc.window.*;
import javax.swing.*;
import java.awt.*;

/**
 * <p>Title: Enterprise Explorer</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: EFounder</p>
 * @author Skyline
 * @version 1.0
 */

public class JStructViewPanel extends JViewPanel {
  public JStructViewPanel() {
    super();
    removetbContent();
  }
  /**
   *
   */
  private void removetbContent() {
    this.remove(tbContent);
  }
  /**
   *
   */
  private IWindow currentWindow = null;
  /**
   *
   * @param cw IWindow
   * @param title String
   * @param tips String
   * @param icon Icon
   * @param Style int
   */
  public void openChildWindow(IWindow cw,String title,String tips,Icon icon,int Style){
    // 设置Window的属性值
    cw.setTitle(title);
    cw.setIcon(icon);
    cw.setTips(tips);
    cw.setStyle(Style);
    cw.setView(this);
    this.add(cw.getWindowComponent(), BorderLayout.CENTER);
    cw.openWindowEvent(this); // 打开事件
    cw.activeWindowEvent(this); // 激活事件
    this.setIcon(icon);
    this.setTitle(title);
    currentWindow = cw;
//    setVisible(cw,true);
  }
  /**
   *
   * @param cw IWindow
   * @return IWindow
   */
  public IWindow closeChildWindow(IWindow cw){
    if ( currentWindow != null ) {
      this.remove(currentWindow.getWindowComponent());
      setVisible(currentWindow, false);
      currentWindow.deactiveWindowEvent(this);
      currentWindow.closeWindowEvent(this);
      currentWindow = null;
      return currentWindow;
    }
    this.setVisible(currentWindow,false);
    return null;
  }
  /**
   *
   * @param cw IWindow
   * @param v boolean
   */
  public void setVisible(IWindow cw,boolean v) {
    this.getViewDevice().setVisible(this,v);
  }
  /**
   *
   * @param cw IWindow
   * @return boolean
   */
  public boolean isVisible(IWindow cw) {
    return this.isVisible();
  }
  public void setViewPlacement(int viewPlacement) {

  }
  public int  getViewPlacement() {
    return -1;
  }
  public int getWindowCount() {
    if ( this.currentWindow != null ) return 1;
    return 0;
  }
  /**
   *
   * @return IWindow
   */
  public IWindow getActiveWindow() {
    return currentWindow;
  }
  /**
   *
   * @param index int
   * @return IWindow
   */
  public IWindow getWindow(int index) {
    return currentWindow;
  }

}
