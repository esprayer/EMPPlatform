package com.efounder.eai.ui;

import java.awt.*;
import com.efounder.pfc.window.*;
import javax.swing.*;
import java.util.*;
import com.efounder.action.*;
import com.efounder.eai.ide.*;
import com.efounder.eai.ui.action.*;
import java.awt.event.*;
import com.efounder.pub.comp.*;
import java.beans.*;
import javax.swing.event.*;

/**
 * <p>Title: Enterprise Explorer</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: EFounder</p>
 * @author Skyline
 * @version 1.0
 */

public class JGenerViewPanel  extends JViewPanel implements IView,MouseListener,PropertyChangeListener,ChangeListener {
  protected JTabbedPane getTabbedPane() {
    tbContent = new JTabbedPane(JTabbedPane.BOTTOM);
    return tbContent;
  }
  public JGenerViewPanel() {
    super();
    this.setBorder(null);
  }
  protected void initEvent() {
    this.tbContent.addMouseListener(this);
    this.tbContent.addChangeListener(this);
//    this.addMouseListener(this);
  }

  protected Icon getCompIcon(IWindow cw) {
    Icon closeIcon = null;
    if ( cw.isModified() )
      closeIcon = ExplorerIcons.ICON_CLOSE_MODIFIED;
    else if ( cw.isLock() )
      closeIcon = ExplorerIcons.ICON_READONLY_CLOSE;
    else
      closeIcon = ExplorerIcons.ICON_GENERICCLOSE;
    return closeIcon;
  }
  public void openChildWindow(IWindow cw,String title,String tips,Icon icon,int Style){
    if ( tbContent.indexOfComponent(cw.getWindowComponent()) == -1 ) {
      // 设置Window的属性值
      cw.setTitle(title);cw.setIcon(icon);cw.setTips(tips);cw.setStyle(Style);
      cw.setView(this);
      tbContent.insertTab(cw.getTitle(),getWindowIcon(cw),cw.getWindowComponent(),cw.getTips(),tbContent.getTabCount());
      // 将此窗口注册
      registryWindow(cw);
      // 注册Action
      addAction(cw);
      cw.openWindowEvent(this);// 打开事件
      tbContent.setSelectedComponent(cw.getWindowComponent());
    } else {
      tbContent.setSelectedComponent(cw.getWindowComponent());
      int index = tbContent.indexOfComponent(cw.getWindowComponent());
      if ( index != -1 ) {
        cw.setTitle(title);
        cw.setIcon(icon);
        cw.setTips(tips);
//        firePropertyChange("setTitle",cw.getWindowComponent(),title);
//        firePropertyChange("setToolTipText",cw.getWindowComponent(),tips);
//        firePropertyChange("setIcon",cw.getWindowComponent(),icon);
//        tbContent.setTitleAt(index,title);
//        tbContent.setToolTipTextAt(index,tips);
//        tbContent.setIconAt(index,icon);
      }
    }
    // 设置可视状态
    setVisible(true);
  }
  public IWindow closeChildWindow(IWindow cw,boolean mustClose){
    if ( mustClose || (cw.closingWindowEvent(this) && cw.canClose()) ) {
      setVisible(cw, false);
      delActioin(cw);
      unregistryWindow(cw);
      cw.closeWindowEvent(this);

      if (EnterpriseExplorer.ContentView.getActiveWindow() == null) {
        EnterpriseExplorer.ExplorerView.setVisible(true);
     }

      return cw;

    }

    return null;
  }
  /**
   * Invoked when the mouse button has been clicked (pressed
   * and released) on a component.
   */
  public void mouseClicked(MouseEvent e) {
    if ( e.getModifiers() == e.BUTTON3_MASK ) {
      showPopupmenu(e.getX(),e.getY());
    }
  }
  protected void showPopupmenu(int X,int Y) {
    ActionGroup popupMenu = new ActionGroup();
    getContextAction(popupMenu);
    if ( popupMenu.getActionCount() == 0 ) return;
//    popupMenu.add(popupAction);
    ActionPopupMenu actionPopupMenu = new ActionPopupMenu(this, popupMenu, true);
    actionPopupMenu.show(this, X, Y);
  }//String s, char c, String s1, Icon icon)ExplorerIcons.ICON_CLOSE_PROJECT
  /**
   * Invoked when a mouse button has been pressed on a component.
   */
  public void mousePressed(MouseEvent e) {
    if ( e.getModifiers() == e.BUTTON1_MASK ) {
      if ( e.getClickCount() == 2 ) {
        max_rstView();
        return;
      }

      if ( e.getSource() == this.tbContent ) {
        Point p = e.getPoint();
        MouseClickCloseWindow(p);
        return;
      }
      return;
    }
  }
  protected void showPopupmenu(MouseEvent e,int X,int Y) {
    ActionGroup popupMenu = new ActionGroup();
    getContextAction(popupMenu);
    if ( popupMenu.getActionCount() == 0 ) return;
//    popupMenu.add(popupAction);
    ActionPopupMenu actionPopupMenu = new ActionPopupMenu(this, popupMenu, true);
    actionPopupMenu.show((Component)e.getSource(), X, Y);
  }
  protected void MouseClickCloseWindow(Point p) {
    int Width = 0;
    int Index = this.tbContent.indexAtLocation(p.x,p.y);
    if ( Index == -1 ) return;
    IWindow comp = (IWindow)this.tbContent.getComponentAt(Index);
    if ( comp == null || !(comp instanceof IWindow) ) return;
    Icon icon = tbContent.getIconAt(Index);
    if ( icon != null ) {Width = icon.getIconWidth();}
    String Text = tbContent.getTitleAt(Index).trim();
    Width += tbContent.getGraphics().getFontMetrics().stringWidth(Text);
    int SX,SY,IW,IH;
    IW = getCompIcon(comp).getIconWidth();
    IH = getCompIcon(comp).getIconHeight();
    Rectangle rect = this.tbContent.getBoundsAt(Index);
    SX =  rect.x;
    if ( Width < rect.width ) {
      SX = rect.x + (rect.width - Width)/2;
    }
    SY = rect.y + (rect.height - IH)/2;
    if ( p.x >= SX && p.x <= (SX+IW) &&
         p.y >= SY && p.y <= (SY+IH) ) {
      this.closeChildWindow(comp);
    }
  }
  public WindowGroup[] getWindowGroups() {
    return null;
  }
}
