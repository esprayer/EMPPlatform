package com.efounder.pfc.window;

import java.awt.*;
import javax.swing.JPanel;
import javax.swing.*;
import java.util.*;
import java.awt.event.*;
import com.efounder.actions.*;
import com.efounder.action.ActionStub;
import com.efounder.action.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class JChildWindow extends JPanel implements IWindow{
  protected IView  parentView  = null;
  protected String windowTitle = null;
  protected String windowID    = null;
  protected String windowTips  = null;
  protected Icon   windowIcon  = null;
  protected boolean windowModified = false;
  protected boolean windowLock     = false;
  protected int    viewPlacement = IView.VIEWPOS_TOP;
  protected Hashtable windowAttr = null;
  protected java.util.List windowListenerList = null;
  public JChildWindow() {

  }
  public void registerContextActionProvider(Object Key,ContextActionProvider cap) {
    ActionManager.registerContextActionProvider(Key,cap);
  }
  public void initObject(Object o) {

  }
  public void putObject(Object Key,Object Value) {
    if ( windowAttr == null ) windowAttr = new Hashtable();
    windowAttr.put(Key,Value);
  }
  public Object getObject(Object Key,Object Def) {
    Object res = Def;
    if ( windowAttr != null ) {
      res = windowAttr.get(Key);
      if ( res == null ) res = Def;
    }
    return res;
  }
  public void setID(String name) {
    windowID = name;
  }
  public String getID() {
    return windowID;
  }
  public JComponent getWindowComponent() {
    return this;
  }
  public void setStyle(int style) {

  }
  public int  getStyle() {
    return 0;
  }
  public void setTitle(String title) {
    if ( parentView != null )
      parentView.firePropertyChange("setTitle",this,title);
    windowTitle = title;
  }
  public void setIcon(Icon icon) {
    windowIcon  = icon;
    if ( parentView != null )
      parentView.firePropertyChange("setIcon",this,icon);
  }
  public void setViewPlacement(int viewPlacement) {

  }
  public void setLock(boolean l) {
    if ( windowLock != l ) {
      if ( parentView != null )
        windowLock = l;
        parentView.firePropertyChange("setLock",this,null);
    }
  }
  public boolean isLock() {
    return windowLock;
  }
  public void setModified(boolean m) {
    windowModified = m;
    if ( parentView != null )
      parentView.firePropertyChange("setModified",this,null);
  }
  public boolean isModified() {
    return windowModified;
  }
  public boolean canClose() {
    return true;
  }
  public boolean canOpen() {
    return true;
  }
  public boolean canDeleteAction() {
    return true;
  }
  public void Close() {
    if ( parentView != null && canClose() )
      parentView.closeChildWindow(this);
  }
  public void Open() {

  }
  public int  getViewPlacement() {
    return IView.VIEWPOS_TOP;
  }
  public Icon getIcon() {
    return windowIcon;
  }
  public String getTitle() {
    return windowTitle;
  }
  public void Show() {

  }
  public void Hide() {

  }
  public IView getView() {
    return parentView;
  }
  public void  setView(IView view) {
    parentView = view;
  }
  public void setTips(String t) {
    if ( parentView != null )
      parentView.firePropertyChange("setTips",this,t);
    windowTips = t;
  }
  public String getTips() {
    return windowTips;
  }
  protected void processMouseEvent(MouseEvent e ) {
    super.processMouseEvent(e);
  }
  public void firePropertyChange(String propertyName, Object oldValue, Object newValue) {
    super.firePropertyChange(propertyName,oldValue,newValue);
  }

  public void activeWindowEvent(IView view) {
    if ( windowListenerList == null ) return;
    ChildWindowListener cwl = null;
    ChildWindowEvent event = new ChildWindowEvent(this,view);
    for(int i=0;i<windowListenerList.size();i++) {
      cwl = (ChildWindowListener)windowListenerList.get(i);
      cwl.windowActivated(event);
    }
  }
  public void deactiveWindowEvent(IView view) {
    if ( windowListenerList == null ) return;
    ChildWindowListener cwl = null;
    ChildWindowEvent event = new ChildWindowEvent(this,view);
    for(int i=0;i<windowListenerList.size();i++) {
      cwl = (ChildWindowListener)windowListenerList.get(i);
      cwl.windowDeactivated(event);
    }
  }
  public void openWindowEvent(IView view) {
    if ( windowListenerList == null ) return;
    ChildWindowListener cwl = null;
    ChildWindowEvent event = new ChildWindowEvent(this,view);
    for(int i=0;i<windowListenerList.size();i++) {
      cwl = (ChildWindowListener)windowListenerList.get(i);
      cwl.windowOpened(event);
    }
  }
  public void closeWindowEvent(IView view) {
    if ( windowListenerList == null ) return;
    ChildWindowListener cwl = null;
    ChildWindowEvent event = new ChildWindowEvent(this,view);
    for(int i=0;i<windowListenerList.size();i++) {
      cwl = (ChildWindowListener)windowListenerList.get(i);
      cwl.windowClosed(event);
    }
    // 清掉自动状态管理的组件
    ActionToolBar.removeAutoUpdateComponent(this);
  }
  public boolean closingWindowEvent(IView view) {
    if ( windowListenerList == null ) return true;
    ChildWindowListener cwl = null;boolean res = true;
    ChildWindowEvent event = new ChildWindowEvent(this,view);
    for(int i=0;i<windowListenerList.size();i++) {
      cwl = (ChildWindowListener)windowListenerList.get(i);
      if ( !cwl.windowClosing(event) ) {
        res = false;break;
      }
    }
    return res;
  }
  public void iconWindowEvent(IView view) {
    if ( windowListenerList == null ) return;
    ChildWindowListener cwl = null;
    ChildWindowEvent event = new ChildWindowEvent(this,view);
    for(int i=0;i<windowListenerList.size();i++) {
      cwl = (ChildWindowListener)windowListenerList.get(i);
      cwl.windowIconified(event);
    }
  }
  public void deiconWindowEvent(IView view) {
    if ( windowListenerList == null ) return;
    ChildWindowListener cwl = null;
    ChildWindowEvent event = new ChildWindowEvent(this,view);
    for(int i=0;i<windowListenerList.size();i++) {
      cwl = (ChildWindowListener)windowListenerList.get(i);
      cwl.windowDeiconified(event);
    }
  }

  public synchronized void addWindowListener(ChildWindowListener cwl) {
    if ( windowListenerList == null ) windowListenerList = new Vector();
    if ( windowListenerList.indexOf(cwl) == -1 ) {
      windowListenerList.add(cwl);
    }
  }
  public synchronized void removeWindowListener(ChildWindowListener cwl) {
    if ( windowListenerList == null ) return;
    windowListenerList.remove(cwl);
  }
  /**
   *
   * @return Action
   */
  public Action getFrameAction() {
    return null;
  }
  /**
   * ��ȡ�����ĵ�Action
   * @return Action
   */
  public Action getContextAction() {
    return null;
  }
  /**
   * ��ȡ�����е�Action
   * @return Action
   */
  public Action getTopFrameAction() {
    return null;
  }
  /**
   * ��ȡ�Ҽ���Action
   * @return Action
   */
  public Action getPopupAction() {
    return null;
  }
  public ActionStub[] getFloatAction() {
    return null;
  }
  protected String groupID    = null;
  protected String groupCaption = null;
  /**
   *
   * @param id String
   */
  public void setGroupID(String id) {
    groupID = id;
  }
  /**
   *
   * @param caption String
   */
  public void setGroupCaption(String caption) {
    groupCaption = caption;
  }
  /**
   * ��ȡ��ID
   * @return String
   */
  public String getGroupID() {
    return groupID;
  }
  /**
   * ��ȡ�����
   * @return String
   */
  public String getGroupCaption() {
    return groupCaption;
  }
  protected Icon   groupIcon    = null;
  public Icon getGroupIcon() {
    return groupIcon;
  }
  public void setGroupIcon(Icon icon) {
    groupIcon = icon;
  }
  protected Object nodeKey = null;
  public void setNodeKey(Object nodeKey) {
    this.nodeKey = nodeKey;
  }
  public Object getNodeKey() {
    return nodeKey;
  }
  protected IWindow parentWindow = null;
  public IWindow getParentWindow() {
    return parentWindow;
  }
  public void setParentWindow(IWindow win) {
    parentWindow = win;
  }
  /**
   *
   */
  protected int viewStatus = -1;
  /**
   *
   * @return int
   */
  public int getViewStatus() {
    return viewStatus;
  }
  /**
   *
   * @param status int
   */
  public void setViewStatus(int status) {
    viewStatus = status;
  }
  // 增加对布局管理的属性
  /**
   *
   */
  private byte[] maxLayoutData = null;
  /**
   *
   * @return byte[]
   */
  public byte[] getMaxLayoutData() {    // 最大化之前的布局数据
    return maxLayoutData;
  }
  /**
   *
   * @param maxLayoutData byte[]
   */
  public void setMaxLayoutData(byte[] maxLayoutData) {
    this.maxLayoutData = maxLayoutData;
  }
  /**
   *
   */
  private byte[] switchLayoutData = null;
  /**
   *
   * @return byte[]
   */
  public byte[] getSwitchLayoutData() { // 窗口切换的布局数据
    return switchLayoutData;
  }
  /**
   *
   * @param switchLayoutData byte[]
   */
  public void setSwitchLayoutData(byte[] switchLayoutData) {
    this.switchLayoutData = switchLayoutData;
  }
  /**
   *
   */
  private boolean maxWindow = false;
  // 设置最大化状态
  /**
   *
   * @param isMaxWindow boolean
   */
  public void setMaxWindow(boolean mx) {
    maxWindow = mx;
  }
  // 是否最大化状态
  /**
   *
   * @return boolean
   */
  public boolean isMaxWindow() {
    return maxWindow;
  }
  /**
   *
   */
  private Object winOwner = null;
  /**
   *
   * @return Object
   */
  public Object getWinOwner() {
    return winOwner;
  }
  /**
   *
   * @param winOwner Object
   */
  public void setWinOwner(Object winOwner) {
    this.winOwner = winOwner;
  }
  //
  protected String pageBaseUrl = null;
  // 地址或是服务器
  public String getPageBaseUrl() {
    return pageBaseUrl;
  }
  //
  public void setPageBaseUrl(String url) {
    pageBaseUrl = url;
  }
}
