package com.efounder.eai.ui;

import javax.swing.*;

import com.efounder.pfc.window.*;
import com.jidesoft.docking.DockableHolder;

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
public class JDockViewDevice implements IViewDevice {
  /**
   *
   */
  private DockableHolder dockableHolder = null;
  /**
   *
   * @param dockableHolder DockableHolder
   */
  public JDockViewDevice(DockableHolder dockableHolder) {
    this.dockableHolder = dockableHolder;
  }

  /**
   * getAction
   *
   * @return Action
   * @todo Implement this com.efounder.pfc.window.IViewDevice method
   */
  public Action getAction() {
    return GROUP_View;
  }

  /**
   * getContentView
   *
   * @return IView
   * @todo Implement this com.efounder.pfc.window.IViewDevice method
   */
  public IView getContentView() {
    return null;
  }

  /**
   * getDeviceComponent
   *
   * @return JComponent
   * @todo Implement this com.efounder.pfc.window.IViewDevice method
   */
  public JComponent getDeviceComponent() {
    return null;
  }

  /**
   * getExplorerView
   *
   * @return IView
   * @todo Implement this com.efounder.pfc.window.IViewDevice method
   */
  public IView getExplorerView() {
    return null;
  }

  /**
   * getMessageView
   *
   * @return IView
   * @todo Implement this com.efounder.pfc.window.IViewDevice method
   */
  public IView getMessageView() {
    return null;
  }

  /**
   * getPropertyView
   *
   * @return IView
   * @todo Implement this com.efounder.pfc.window.IViewDevice method
   */
  public IView getPropertyView() {
    return null;
  }

  /**
   * getStructView
   *
   * @return IView
   * @todo Implement this com.efounder.pfc.window.IViewDevice method
   */
  public IView getStructView() {
    return null;
  }

  /**
   * getTopView
   *
   * @return IView
   * @todo Implement this com.efounder.pfc.window.IViewDevice method
   */
  public IView getTopView() {
    return null;
  }

  /**
   * isVisible
   *
   * @param comp IView
   * @return boolean
   * @todo Implement this com.efounder.pfc.window.IViewDevice method
   */
  public boolean isVisible(IView comp) {
    return false;
  }

  /**
   * openView
   *
   * @param comp IView
   * @param layer String
   * @todo Implement this com.efounder.pfc.window.IViewDevice method
   */
  public void openView(IView comp, String layer) {
  }

  /**
   * openView
   *
   * @param comp IView
   * @param layer int
   * @todo Implement this com.efounder.pfc.window.IViewDevice method
   */
  public void openView(IView comp, int layer) {
  }

  /**
   * openView
   *
   * @param comp IView
   * @param layer int
   * @param Style int
   * @todo Implement this com.efounder.pfc.window.IViewDevice method
   */
  public void openView(IView comp, int layer, int Style) {
  }

  /**
   * openView
   *
   * @param comp IView
   * @param layer String
   * @param Style int
   * @todo Implement this com.efounder.pfc.window.IViewDevice method
   */
  public void openView(IView comp, String layer, int Style) {
  }
  /**
   *
   */
  private Action GROUP_View = null;
  /**
   * setAction
   *
   * @param a Action
   * @todo Implement this com.efounder.pfc.window.IViewDevice method
   */
  public void setAction(Action a) {
    GROUP_View = a;
  }

  /**
   * setExtendedState
   *
   * @param comp IView
   * @param stat int
   * @todo Implement this com.efounder.pfc.window.IViewDevice method
   */
  public void setExtendedState(IView comp, int stat) {
      comp.setExtendedState(stat);
  }

  /**
   * setLocation
   *
   * @param comp IView
   * @param l int
   * @todo Implement this com.efounder.pfc.window.IViewDevice method
   */
  public void setLocation(IView comp, int l) {
  }

  /**
   * setLocation
   *
   * @param comp IView
   * @param w double
   * @todo Implement this com.efounder.pfc.window.IViewDevice method
   */
  public void setLocation(IView comp, double w) {
  }

  /**
   * setViewState
   *
   * @param comp IView
   * @param stat int
   * @todo Implement this com.efounder.pfc.window.IViewDevice method
   */
  public void setViewState(IView comp, int stat) {
  }

  /**
   * setVisible
   *
   * @param comp IView
   * @param v boolean
   * @todo Implement this com.efounder.pfc.window.IViewDevice method
   */
  public void setVisible(IView comp, boolean v) {
  }
}
