package com.wait;

import java.awt.*;
import javax.swing.*;
import org.openide.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class WaitThread implements Runnable {
  Component waitObject;
  String title;
  String message;
  Icon icon;
  Runnable runThread;
  Component waitComp = null;
  /**
   *
   * @param waitObject Component
   * @param title String
   * @param message String
   * @param icon Icon
   * @param runThread Runnable
   */
  public WaitThread(Component waitObject,String title, String message, Icon icon,Runnable runThread) {
    this(waitObject,title,message,icon,runThread,null);
  }
  /**
   *
   * @param waitObject Component
   * @param title String
   * @param message String
   * @param icon Icon
   * @param runThread Runnable
   * @param waitComp Component
   */
  public WaitThread(Component waitObject,String title, String message, Icon icon,Runnable runThread,Component waitComp) {
    this.waitObject = waitObject;
    this.title      = title;
    this.message    = message;
    this.icon       = icon;
    this.runThread  = runThread;
    this.waitComp   = waitComp;
  }
  /**
   *
   */
  public void run() {
    try {
      Thread.currentThread().setContextClassLoader(WaitThread.class.getClassLoader());
      WaitingManager.getDefault().beginWaitingFor(waitObject,title,message,icon,null,waitComp);
      runThread.run();
    } catch ( Exception e ) {
      e.printStackTrace();
    } finally {
      WaitingManager.getDefault().endWaitngFor(waitObject);
    }
  }

}
