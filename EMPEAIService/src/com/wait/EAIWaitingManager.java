package com.wait;

import org.openide.*;
import java.awt.*;
import javax.swing.*;
import com.efounder.eai.*;
import org.openide.util.RequestProcessor.*;
import org.openide.util.*;
import com.efounder.pfc.window.*;
import com.efounder.eai.ide.*;
import java.lang.reflect.*;


/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class EAIWaitingManager extends WaitingManager {
  protected static Window busyWindow = null;
  protected static BusyBar2 busyBar = null;
  protected static String message = null;
  public EAIWaitingManager() {
  }
  /**
   *
   * @param waitCompObject Component
   * @param message String
   */
  public void beginWait(Component waitCompObject, String message) {
    if ( EnterpriseExplorer.getExplorer() != null )
      EnterpriseExplorer.getExplorer().setDisableEvent(true);
  }
  /**
   *
   * @param waitCompObject Component
   */
  public void endWait(Component waitCompObject) {
    if ( EnterpriseExplorer.getExplorer() != null )
      EnterpriseExplorer.getExplorer().setDisableEvent(false);
  }
  /**
   *
   */
//  protected static int waitCount = 0;
  /**
   *
   * @param waitObject Component
   * @param title String
   * @param message String
   * @param icon Icon
   * @param run Runnable
   */
//  public void waitInvoke(Component waitObject,String title, String message, Icon icon,Runnable run,Component waitComp) {
//    waitInvoke(waitObject,title,message,icon,run,waitComp);
//  }
  /**
   *
   * @param waitObject Component
   * @param title String
   * @param message String
   * @param icon Icon
   * @param run Runnable
   */
  public void waitInvoke(Component waitObject,String title, String message, Icon icon,Runnable run,Component waitComp) {
    RequestProcessor.Task openNodeObjectTask = null;
    if ( waitObject == null ) {
      waitObject = EAI.EA.getMainWindow();
    } else {
      waitObject = SwingUtilities.getWindowAncestor(waitObject);
    }
    Runnable runObject = new WaitThread(waitObject,title,message,icon,run,waitComp);
    Thread t = new Thread(runObject);
    t.start();
//    runObject.run();
//    SwingUtilities.invokeLater(runObject);
//    openNodeObjectTask = RequestProcessor.getDefault().create(runObject);
//    openNodeObjectTask.schedule(0);
//    Thread.currentThread().
  }
  /**
   *
   * @param waitObject Component
   * @param message String
   * @param icon Icon
   */
  public void beginWaitingFor(Component waitObject,String title,String message,Icon icon,Runnable run,Component waitComp) {
    if ( EAI.EA == null || EAI.EA.getMainWindow() == null ) return;
    Window pw = null;
    if ( waitObject == null ) {
      pw = EAI.EA.getMainWindow();
    } else {
      if ( waitObject instanceof Window )
        pw = (Window)waitObject;
      else
        pw = SwingUtilities.getWindowAncestor(waitObject);
    }
    EnterpriseExplorer.getExplorer().setDisableEvent(true);
    createBusyWindow(pw,message,icon,waitComp);
    showBusyWindow(message,icon);
    setWaitMessage(title,message,icon);
//    waitCount++;
    // 执行线程
    executeThread(run);
  }
  /**
   * 任务执行线程的过程
   * @param run Runnable
   */
  protected void executeThread(Runnable run) {
    if ( run == null ) return;
    RequestProcessor.Task openNodeObjectTask = null;
    openNodeObjectTask = RequestProcessor.getDefault().create(run);
    openNodeObjectTask.schedule(0);

  }
  /**
   * setWaitMessage
   *
   * @param title String
   * @param content String
   * @param contentIcon Icon
   */
  public void setWaitMessage(String title, String content, Icon contentIcon) {
    if ( busyBar == null ) return;
    busyBar.setWaitIcon(contentIcon);
    busyBar.setWaitText(content);
    IWindowStatus Status = null;
    Status = (IWindowStatus)System.getProperties().get("WindowStatus");
    if ( Status != null ) {
      Status.setIcon(contentIcon);
      Status.setText(title);
    }
  }

  /**
   *
   * @param waitObject Component
   */
  public void endWaitngFor(Component waitObject) {
//    waitCount--;
//    if ( waitCount == 0 ) {
      hideBusyWindow();
      if(EnterpriseExplorer.getExplorer()!=null)
      EnterpriseExplorer.getExplorer().setDisableEvent(false);
//    }
  }
  /**
   *
   */
  protected void createBusyWindow(java.awt.Window pw,String message,Icon icon,Component waitComp) {
    if ( busyWindow != null ) return;

    busyBar = new BusyBar2(null);
    busyBar.addUserWaitComp(waitComp);
    JWindow dlg = null;
    if ( pw instanceof java.awt.Frame ) {
      Frame frame = (Frame)pw;
      dlg = new JWindow(frame);
    }
    if ( pw instanceof java.awt.Window ) {
      Window dialog = (Window )pw;
      dlg = new JWindow(dialog);
    }
    busyWindow = dlg;
//    dlg.setUndecorated(true);
    dlg.getRootPane().setLayout(new BorderLayout());
    dlg.getRootPane().add(busyBar,BorderLayout.CENTER);
    this.busyBar.setWaitIcon(icon);
    this.busyBar.setWaitText(message);
    busyWindow.pack();
//    int width  = 360;
//    int height = 70;
//    busyWindow.setSize(width,height);
    com.efounder.util.WindowUtils.centerWindow(busyWindow);
  }

  /**
   *
   */
  public void hideBusyWindow() {
    if ( busyWindow != null ) {
      busyWindow.setVisible(false);
      busyWindow.dispose();
    }
    busyWindow = null;busyBar = null;
  }
  /**
   *
   */
  protected void showBusyWindow(String message,Icon icon) {
    if ( busyWindow != null && !busyWindow.isVisible() ) {
      busyWindow.setVisible(true);
    }
  }

  public void waitingFor(String title, String content, Icon contentIcon, Object waitObject) {

  }
  /**
   *
   * @param title String
   * @param contentIcon Icon
   * @param spmodel SpinnerModel
   * @param waitObject Object
   */
  public void waitingFor(String title, Icon contentIcon, SpinnerModel spmodel, Object waitObject) {

  }
  public WaitingManager getInstance(String name) {
    return this;
  }
}
