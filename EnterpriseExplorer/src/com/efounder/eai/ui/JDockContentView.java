package com.efounder.eai.ui;

import javax.swing.*;

import com.efounder.action.*;
import com.efounder.pfc.window.*;
import com.jidesoft.document.*;
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
public class JDockContentView implements IView {
static ResourceBundle res = ResourceBundle.getBundle("com.efounder.eai.ui.Res");
  /**
   *
   */
  private IDocumentPane documentPane = null;
  /**
   *
   * @param documentPane IDocumentPane
   */
  public JDockContentView(IDocumentPane documentPane) {
    this.documentPane = documentPane;
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
    return null;
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
    return null;
  }

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
    return false;
  }

  /**
   * openChildWindow
   *
   * @param cw IWindow
   * @todo Implement this com.efounder.pfc.window.IView method
   */
  public void openChildWindow(IWindow cw) {
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
    ContentDocumentComponent cdc = new ContentDocumentComponent(cw,title,tips,icon);
    documentPane.openDocument(cdc);
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
  }

  /**
   * setVisible
   *
   * @param v boolean
   * @todo Implement this com.efounder.pfc.window.IView method
   */
  public void setVisible(boolean v) {
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
