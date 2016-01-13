package com.efounder.pfc.window;

import javax.swing.*;
import com.efounder.action.ActionStub;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public interface IWindow {
  public final int WINDOW_TITLE     = 0x0001; // �б���
  public final int WINDOW_NO_TITLE  = 0x0000; // û�б���
  public final int WINDOW_MAX       = 0x0002; // �����
  public final int WINDOW_MIN       = 0x0004; // ����С��
  public final int WINDOW_CLOSE     = 0x0010; // �ɹر�

  public void addWindowListener(ChildWindowListener cwl);
  public void removeWindowListener(ChildWindowListener cwl);

  public JComponent getWindowComponent();
  public void   setName(String name);
  public String getName();
  public void setStyle(int style);
  public int  getStyle();
  public void setTitle(String title);
  public void setIcon(Icon icon);
  public void setViewPlacement(int viewPlacement);
  public void setModified(boolean m);
  public boolean isModified();
  public void setLock(boolean l);
  public boolean isLock();
  public boolean canClose();
  public boolean canOpen();
  public boolean canDeleteAction();
  public void Close();
  public void Open();
  public int  getViewPlacement();
  public Icon getIcon();
  public String getTitle();
  public void Show();
  public void Hide();
  public IView getView();
  public void  setView(IView view);
  public void putObject(Object Key,Object Value);
  public Object getObject(Object Key,Object Def);
  public void initObject(Object o);
  public String getID();
  public void   setID(String id);
  public void setTips(String t);
  public String getTips();
  public void firePropertyChange(String propertyName, Object oldValue, Object newValue);
  public void activeWindowEvent(IView view);
  public void deactiveWindowEvent(IView view);
  public void openWindowEvent(IView view);
  public void closeWindowEvent(IView view);
  public boolean closingWindowEvent(IView view);
  public void iconWindowEvent(IView view);
  public void deiconWindowEvent(IView view);
  public Action getTopFrameAction();
  public Action getContextAction();
  public Action getFrameAction();
  public Action getPopupAction();
  public ActionStub[] getFloatAction();
  public void setGroupID(String id);
  public void setGroupCaption(String caption);
  public String getGroupID();
  public String getGroupCaption();
  public Icon getGroupIcon();
  public void setGroupIcon(Icon icon);
  public void setNodeKey(Object nodeKey);
  public Object getNodeKey();
  public IWindow getParentWindow();
  public void setParentWindow(IWindow win);
  public int getViewStatus();
  public void setViewStatus(int status);
  // 增加对布局管理的属性
  public byte[] getMaxLayoutData();    // 最大化之前的布局数据
  public void setMaxLayoutData(byte[] maxLayoutData);
  public byte[] getSwitchLayoutData(); // 窗口切换的布局数据
  public void setSwitchLayoutData(byte[] switchLayoutData);
  // 设置最大化状态
  public void setMaxWindow(boolean isMaxWindow);
  // 是否最大化状态
  public boolean isMaxWindow();
  //
  public Object getWinOwner();
  public void setWinOwner(Object winOwner);
  // 地址或是服务器
  public String getPageBaseUrl();
  public void setPageBaseUrl(String url);
}
