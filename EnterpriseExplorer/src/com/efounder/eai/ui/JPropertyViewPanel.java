package com.efounder.eai.ui;

import com.efounder.pfc.window.*;
import javax.swing.*;
import java.util.*;

/**
 * <p>Title: Enterprise Explorer</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: EFounder</p>
 * @author Skyline
 * @version 1.0
 */

public class JPropertyViewPanel extends JGenerViewPanel {
  public JPropertyViewPanel() {
    this.setViewPlacement(JViewPanel.VIEWPOS_BOTTOM);
  }
  /**
   *
   * @param cw IWindow
   * @param title String
   * @param tips String
   * @param icon Icon
   * @param Style int
   */
  public void openChildWindow(IWindow cw,String title,String tips,Icon icon,int Style){
    if ( tbContent.indexOfComponent(cw.getWindowComponent()) == -1 ) {
      // 设置Window的属性值
      cw.setTitle(title);cw.setIcon(icon);cw.setTips(tips);cw.setStyle(Style);
      cw.setView(this);
      tbContent.insertTab(cw.getTitle(),getWindowIcon(cw),cw.getWindowComponent(),cw.getTips(),tbContent.getTabCount());
      cw.openWindowEvent(this);// 打开事件
      tbContent.setSelectedComponent(cw.getWindowComponent());
    } else {
      tbContent.setSelectedComponent(cw.getWindowComponent());
      int index = tbContent.indexOfComponent(cw.getWindowComponent());
      if ( index != -1 ) {
//        cw.setTitle(title);
//        cw.setIcon(icon);
//        cw.setTips(tips);
      }
    }
    // 设置可视状态
//    setVisible(true);
  }
  public IWindow closeChildWindow(IWindow cw){
    if ( cw != null ) {
      setVisible(cw, false);
//      delActioin(cw);
//      unregistryWindow(cw);
      cw.closeWindowEvent(this);
      return cw;
    } else {
      int winCount = this.getWindowCount();
      java.util.List winList = new ArrayList();
      for(int i=0;i<winCount;i++) {
        cw = this.getWindow(i);
        winList.add(cw);
      }
      for(int i= 0;i<winCount;i++) {
        cw = (IWindow)winList.get(i);
        setVisible(cw,false);
        cw.closeWindowEvent(this);
      }
      this.setVisible(false);
    }
    return null;
  }

}
