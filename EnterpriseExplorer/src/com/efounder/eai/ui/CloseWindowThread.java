package com.efounder.eai.ui;

import com.efounder.pfc.window.IWindow;
import com.efounder.pfc.window.IView;
import com.efounder.eai.ide.EnterpriseExplorer;

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
public class CloseWindowThread implements Runnable {
  protected IWindow window = null;
  protected boolean mustClose = false;
  protected JViewPanel view = null;
  /**
   *
   * @param cw IWindow
   * @param mustClose boolean
   * @param view IView
   */
  public CloseWindowThread(IWindow cw,boolean mustClose,JViewPanel view) {
    window = cw;
    this.mustClose = mustClose;
    this.view = view;
  }

  public void run() {
    if ( mustClose || (window.closingWindowEvent(view) && window.canClose()) ) {
      view.setVisible(window, false);
      view.delActioin(window);
      view.unregistryWindow(window);
      window.closeWindowEvent(view);
      view.setFirstTab();
      view.setNullParentWindow(window); // 清除以cw为parentwindow的window

      if (EnterpriseExplorer.ContentView.getActiveWindow() == null) {
        EnterpriseExplorer.ExplorerView.setVisible(true);
      }
    }
  }
}
