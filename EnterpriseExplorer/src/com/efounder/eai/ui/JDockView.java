package com.efounder.eai.ui;

import javax.swing.*;

import com.efounder.action.*;
import com.efounder.pfc.window.*;
import com.jidesoft.docking.DockableHolder;
import java.awt.Dimension;
import com.jidesoft.docking.DockableFrame;
import com.efounder.eai.ide.EnterpriseExplorer;
import com.jidesoft.docking.DockContext;
import java.awt.event.*;
import com.efounder.node.view.*;
import java.util.ResourceBundle;

/**
 * <p>Title: Enterprise Explorer</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2004</p>
 *
 * <p>Company: EFounder</p>
 *
 * @author not attributable
 * @version 1.0
 */
public class JDockView implements IView {
static ResourceBundle res = ResourceBundle.getBundle("com.efounder.eai.ui.Res");
//  frame.getContext().setInitMode(DockContext.STATE_FRAMEDOCKED);
//  frame.getContext().setInitSide(DockContext.DOCK_SIDE_SOUTH);
//  frame.getContext().setInitIndex(1);
  /**
   *
   */
  private int initMode;
  /**
   *
   */
  private int initSide;
  /**
   *
   */
  private int initIndex;
  /**
   *
   */
  private DockableHolder dockableHolder = null;
  /**
   *
   * @param initMode int
   * @param initSide int
   * @param initIndex int
   */
  public JDockView(DockableHolder dockableHolder,int initMode,
                   int initSide,int initIndex) {
    this.dockableHolder = dockableHolder;
    this.initMode  = initMode;
    this.initSide  = initSide;
    this.initIndex = initIndex;

  }

  /**
   * activeWindow
   *
   * @param cw IWindow
   * @todo Implement this com.efounder.pfc.window.IView method
   */
  public void activeWindow(IWindow cw) {
  }

  /**
   * closeChildWindow
   *
   * @param cw IWindow
   * @return IWindow
   * @todo Implement this com.efounder.pfc.window.IView method
   */
  public IWindow closeChildWindow(IWindow cw) {
    return closeChildWindow(cw,false);
  }
  protected void closeAllChildWindow() {
    if ( this.docFrameList.size() == 0 ) return;
    IWindow window = null;
    for(int i=0;i<docFrameList.size();i++) {
      window = (IWindow)docFrameList.get(i);
      closeChildWindow(window,true);
    }
    docFrameList.clear();
  }
  /**
   * closeChildWindow
   *
   * @param cw IWindow
   * @param mustClose boolean
   * @return IWindow
   * @todo Implement this com.efounder.pfc.window.IView method
   */
  public IWindow closeChildWindow(IWindow cw, boolean mustClose) {
    if ( cw == null ) {
      closeAllChildWindow();
      return null;
    }
    if ( mustClose || (cw.closingWindowEvent(this) && cw.canClose()) ) {
      setVisible(cw, false);
//      delActioin(cw);
//      unregistryWindow(cw);
      cw.closeWindowEvent(this);
      String frameKey = getFrameKey(cw);
      DockableFrame dockableFrame = dockableHolder.getDockingManager().getFrame(frameKey);
      if ( dockableFrame != null ) {
        dockableHolder.getDockingManager().removeFrame(frameKey,false);
      }
//      if (EnterpriseExplorer.ContentView.getActiveWindow() == null) {
//        EnterpriseExplorer.ExplorerView.setVisible(true);
//      }
      return cw;
    }
    return null;
  }

  /**
   * firePropertyChange
   *
   * @param propertyName String
   * @param oldValue Object
   * @param newValue Object
   * @todo Implement this com.efounder.pfc.window.IView method
   */
  public void firePropertyChange(String propertyName, Object oldValue,
                                 Object newValue) {
  }

  /**
   * getAction
   *
   * @return Action
   * @todo Implement this com.efounder.pfc.window.IView method
   */
  public Action getAction() {
    return viewAction;
  }
  private Action viewAction = null;
  /**
   * getActiveWindow
   *
   * @return IWindow
   * @todo Implement this com.efounder.pfc.window.IView method
   */
  public IWindow getActiveWindow() {
    return null;
  }

  /**
   * getContextAction
   *
   * @param ag ActionGroup
   * @return Action
   * @todo Implement this com.efounder.pfc.window.IView method
   */
  public Action getContextAction(ActionGroup ag) {
    return null;
  }

  /**
   * getID
   *
   * @return String
   * @todo Implement this com.efounder.pfc.window.IView method
   */
  public String getID() {
    return "";
  }

  /**
   * getIcon
   *
   * @return Icon
   * @todo Implement this com.efounder.pfc.window.IView method
   */
  public Icon getIcon() {
    return null;
  }

  /**
   * getLayer
   *
   * @return String
   * @todo Implement this com.efounder.pfc.window.IView method
   */
  public String getLayer() {
    return "";
  }

  /**
   * getObject
   *
   * @param Key Object
   * @param Def Object
   * @return Object
   * @todo Implement this com.efounder.pfc.window.IView method
   */
  public Object getObject(Object Key, Object Def) {
    return null;
  }

  /**
   * getResult
   *
   * @return int
   * @todo Implement this com.efounder.pfc.window.IView method
   */
  public int getResult() {
    return 0;
  }

  /**
   * getStyle
   *
   * @return int
   * @todo Implement this com.efounder.pfc.window.IView method
   */
  public int getStyle() {
    return 0;
  }
  private String title = null;
  /**
   * getTitle
   *
   * @return String
   * @todo Implement this com.efounder.pfc.window.IView method
   */
  public String getTitle() {
    return title;
  }

  /**
   * getViewComponent
   *
   * @return JComponent
   * @todo Implement this com.efounder.pfc.window.IView method
   */
  public JComponent getViewComponent() {
    return null;
  }

  /**
   * getViewDevice
   *
   * @return IViewDevice
   * @todo Implement this com.efounder.pfc.window.IView method
   */
  public IViewDevice getViewDevice() {
    return null;
  }

  /**
   * getViewPlacement
   *
   * @return int
   * @todo Implement this com.efounder.pfc.window.IView method
   */
  public int getViewPlacement() {
    return 0;
  }

  /**
   * getViewStatus
   *
   * @return int
   * @todo Implement this com.efounder.pfc.window.IView method
   */
  public int getViewStatus() {
    return 0;
  }

  /**
   * getWindow
   *
   * @param index int
   * @return IWindow
   * @todo Implement this com.efounder.pfc.window.IView method
   */
  public IWindow getWindow(int index) {
    return null;
  }

  /**
   * getWindowCount
   *
   * @return int
   * @todo Implement this com.efounder.pfc.window.IView method
   */
  public int getWindowCount() {
    return 0;
  }

  /**
   * getWindowGroups
   *
   * @return WindowGroup[]
   * @todo Implement this com.efounder.pfc.window.IView method
   */
  public WindowGroup[] getWindowGroups() {
    return null;
  }

  /**
   * hideView
   *
   * @todo Implement this com.efounder.pfc.window.IView method
   */
  public void hideView() {
  }

  /**
   * initView
   *
   * @param node Object
   * @todo Implement this com.efounder.pfc.window.IView method
   */
  public void initView(Object node) {
  }

  /**
   * insertWindowAction
   *
   * @param win IWindow
   * @todo Implement this com.efounder.pfc.window.IView method
   */
  public void insertWindowAction(IWindow win) {
  }

  /**
   * isOpen
   *
   * @param cw IWindow
   * @return boolean
   * @todo Implement this com.efounder.pfc.window.IView method
   */
  public boolean isOpen(IWindow cw) {
    return false;
  }

  /**
   * isVisible
   *
   * @param cw IWindow
   * @return boolean
   * @todo Implement this com.efounder.pfc.window.IView method
   */
  public boolean isVisible(IWindow cw) {
    return false;
  }

  /**
   * isVisible
   *
   * @return boolean
   * @todo Implement this com.efounder.pfc.window.IView method
   */
  public boolean isVisible() {
    return visible;
  }

  /**
   * openChildWindow
   *
   * @param cw IWindow
   * @todo Implement this com.efounder.pfc.window.IView method
   */
  public void openChildWindow(IWindow cw) {
    openChildWindow(cw,cw.getTitle(),cw.getTips(),cw.getIcon());
  }

  /**
   * openChildWindow
   *
   * @param cw IWindow
   * @param title String
   * @param tips String
   * @param icon Icon
   * @todo Implement this com.efounder.pfc.window.IView method
   */
  public void openChildWindow(IWindow cw, String title, String tips, Icon icon) {
    openChildWindow(cw,title,tips,icon,0);
  }
  /**
   *
   * @param cw IWindow
   * @param title String
   * @param icon Icon
   * @return DockableFrame
   */
  protected DockableFrame createDockableFrame(IWindow cw,String title, Icon icon) {
    String frameKey = getFrameKey(cw);
    DockableFrame frame = new DockableFrame(frameKey, icon);
    frame.setTitle(title);
    frame.setTabTitle(title);
    frame.setPreferredSize(new Dimension(200, 200));
    cw.setTitle(title);cw.setIcon(icon);
    docFrameList.add(cw);
    FrameCloseAction fca = new FrameCloseAction(frame,cw);
    frame.setCloseAction(  fca  );

    return frame;
  }

  class FrameCloseAction extends AbstractAction {
    private DockableFrame dockFrame = null;
    private IWindow window = null;
    public FrameCloseAction(DockableFrame frame,IWindow cw) {
      dockFrame = frame;
      window = cw;
    }
    public void actionPerformed(ActionEvent e) {
      String frameKey = JDockView.this.getFrameKey(window);

      if ( closeChildWindow(window) != null ) {
        Object winOwner = window.getWinOwner();
        if ( winOwner != null && winOwner instanceof NodeViewer ) {
          ((NodeViewer)winOwner).setAttribViewVisible(window,false);
        }
      }

//      dockableHolder.getDockingManager().removeFrame(frameKey);
//      Object winOwner = window.getWinOwner();
//      if ( winOwner != null && winOwner instanceof NodeViewer ) {
//        ((NodeViewer)winOwner).setAttribViewVisible(window,false);
//      }
    }
  }

  /**
   *
   * @param window IWindow
   * @return String
   */
  protected static String getFrameKey(IWindow window) {
    String frameKey = window.getID();
    if ( frameKey == null ) frameKey = window.getClass().getName()+window.hashCode();
    return frameKey;
  }
  protected java.util.List docFrameList = new java.util.ArrayList();
  /**
   * openChildWindow
   *
   * @param cw IWindow
   * @param title String
   * @param tips String
   * @param icon Icon
   * @param Style int
   * @todo Implement this com.efounder.pfc.window.IView method
   */
  public void openChildWindow(IWindow cw, String title, String tips, Icon icon,
                              int Style) {
    if ( !this.visible ) return;
    String frameKey = getFrameKey(cw);

    DockableFrame dockableFrame = dockableHolder.getDockingManager().getFrame(frameKey);
    if ( dockableFrame == null ) {
      dockableFrame = createDockableFrame(cw, title, icon);

      dockableFrame.setInitMode(initMode); // 用户设置的状态
      dockableFrame.setInitSide(initSide);
      dockableFrame.setInitIndex(initIndex);
      dockableFrame.add(cw.getWindowComponent());
      dockableFrame.setInitMode(initMode); // 用户设置的状态
      this.dockableHolder.getDockingManager().addFrame(dockableFrame);
    } else {
//      DockContext dockContext = dockableHolder.getDockingManager().getContextOf(frameKey);
//      dockContext.setCurrentMode(DockContext.STATE_FRAMEDOCKED);
//      dockableFrame.setVisible(true);
//      dockableFrame.setHidable(false);
      dockableHolder.getDockingManager().showFrame(frameKey);
    }
    dockableHolder.getDockingManager().activateFrame(frameKey);
  }

  /**
   * putObject
   *
   * @param Key Object
   * @param Value Object
   * @todo Implement this com.efounder.pfc.window.IView method
   */
  public void putObject(Object Key, Object Value) {
  }

  /**
   * removeWindowAction
   *
   * @todo Implement this com.efounder.pfc.window.IView method
   */
  public void removeWindowAction() {
  }

  /**
   * setAction
   *
   * @param a Action
   * @todo Implement this com.efounder.pfc.window.IView method
   */
  public void setAction(Action a) {
  }

  /**
   * setExtendedState
   *
   * @param stat int
   * @todo Implement this com.efounder.pfc.window.IView method
   */
  public void setExtendedState(int stat) {
  }

  /**
   * setID
   *
   * @param ID String
   * @todo Implement this com.efounder.pfc.window.IView method
   */
  public void setID(String ID) {
  }

  /**
   * setIcon
   *
   * @param icon Icon
   * @todo Implement this com.efounder.pfc.window.IView method
   */
  public void setIcon(Icon icon) {
  }

  /**
   * setLayer
   *
   * @param layer String
   * @todo Implement this com.efounder.pfc.window.IView method
   */
  public void setLayer(String layer) {
  }

  /**
   * setLocation
   *
   * @param w double
   * @todo Implement this com.efounder.pfc.window.IView method
   */
  public void setLocation(double w) {
  }

  /**
   * setLocation
   *
   * @param l int
   * @todo Implement this com.efounder.pfc.window.IView method
   */
  public void setLocation(int l) {
  }

  /**
   * setStyle
   *
   * @param style int
   * @todo Implement this com.efounder.pfc.window.IView method
   */
  public void setStyle(int style) {
  }

  /**
   * setTitle
   *
   * @param title String
   * @todo Implement this com.efounder.pfc.window.IView method
   */
  public void setTitle(String title) {

  }

  /**
   * setViewDevice
   *
   * @param vd IViewDevice
   * @todo Implement this com.efounder.pfc.window.IView method
   */
  public void setViewDevice(IViewDevice vd) {
  }

  /**
   * setViewPlacement
   *
   * @param viewPlacement int
   * @todo Implement this com.efounder.pfc.window.IView method
   */
  public void setViewPlacement(int viewPlacement) {
  }

  /**
   * setViewStatus
   *
   * @param status int
   * @todo Implement this com.efounder.pfc.window.IView method
   */
  public void setViewStatus(int status) {
  }

  /**
   * setVisible
   *
   * @param cw IWindow
   * @param v boolean
   * @todo Implement this com.efounder.pfc.window.IView method
   */
  public void setVisible(IWindow cw, boolean v) {
    if ( v )
      this.openChildWindow(cw);
  }
  private boolean visible = true;
  /**
   * setVisible
   *
   * @param v boolean
   * @todo Implement this com.efounder.pfc.window.IView method
   */
  public void setVisible(boolean v) {
    visible = v;
  }

  /**
   * showView
   *
   * @param context Object
   * @todo Implement this com.efounder.pfc.window.IView method
   */
  public void showView(Object context) {
  }
}
