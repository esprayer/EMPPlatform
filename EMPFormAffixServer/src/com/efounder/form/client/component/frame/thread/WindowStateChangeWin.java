package com.efounder.form.client.component.frame.thread;

import com.efounder.form.client.component.frame.JPICFrame;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2010</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */

public class WindowStateChangeWin
    implements Runnable {
  public WindowStateChangeWin() {
    try {
      jbInit();
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  JPICFrame picFrame ;
  public WindowStateChangeWin(JPICFrame picFrame) {
    this.picFrame = picFrame;
  }


  /**
   * When an object implementing interface <code>Runnable</code> is used to
   * create a thread, starting the thread causes the object's <code>run</code>
   * method to be called in that separately executing thread.
   *
   * @todo Implement this java.lang.Runnable method
   */
  public void run() {
      picFrame.exeWindowStatChange();
  }

  private void jbInit() throws Exception {
  }

}
