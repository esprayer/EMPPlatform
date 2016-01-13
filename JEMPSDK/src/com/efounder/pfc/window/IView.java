package com.efounder.pfc.window;

import javax.swing.*;
import com.efounder.action.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public interface IView {
  public final int VIEW_TITLE     = 0x0001; // 有标题
  public final int VIEW_NO_TITLE  = 0x0000; // 没有标题
  public final int VIEW_MAX       = 0x0002; // 可最大化
  public final int VIEW_MIN       = 0x0004; // 可最小化
  public final int VIEW_CLOSE     = 0x0010; // 可关闭
  public final int VIEWPOS_TOP    = JTabbedPane.TOP;
  public final int VIEWPOS_BOTTOM = JTabbedPane.BOTTOM;
  public final int VIEWPOS_LEFT   = JTabbedPane.LEFT;
  public final int VIEWPOS_RIGHT  = JTabbedPane.RIGHT;
  public JComponent getViewComponent();
  public void setStyle(int style);
  public int  getStyle();
  public void setVisible(IWindow cw,boolean v);
  public boolean isVisible(IWindow cw);
  public boolean isOpen(IWindow cw);
  public void openChildWindow(IWindow cw);
  public void openChildWindow(IWindow cw,String title,String tips,Icon icon);
  public void openChildWindow(IWindow cw,String title,String tips,Icon icon,int Style);
  public IWindow closeChildWindow(IWindow cw);
  public IWindow closeChildWindow(IWindow cw,boolean mustClose);
  public void setTitle(String title);
  public void setIcon(Icon icon);
  public void setViewPlacement(int viewPlacement);
  public int  getViewPlacement();
  public Icon getIcon();
  public String getTitle();
  public String getID();
  public void   setID(String ID);
  public void setVisible(boolean v);
  public boolean isVisible();
  public void setViewDevice(IViewDevice vd);
  public IViewDevice getViewDevice();
  public void setLocation(int l);
  public void setLocation(double w);
  public void   setLayer(String layer);
  public String getLayer();
  public void putObject(Object Key,Object Value);
  public Object getObject(Object Key,Object Def);
  public Action getAction();
  public void setAction(Action a);
  public void firePropertyChange(String propertyName, Object oldValue, Object newValue);
  public void setExtendedState(int stat);
  public void activeWindow(IWindow cw);
  public IWindow getActiveWindow();
  public IWindow getWindow(int index);
  public Action getContextAction(ActionGroup ag);
  public int getWindowCount();
  public WindowGroup[] getWindowGroups();
  public void showView(Object context);
  public void hideView();
  public void initView(Object node);
  public int getResult();
  public void removeWindowAction();
  public void insertWindowAction(IWindow win);
  public int getViewStatus();
  public void setViewStatus(int status);
}
