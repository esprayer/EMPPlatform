package jbof.gui.window.classes.style.mdi;

import javax.swing.JProgressBar;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */
//--------------------------------------------------------------------------------------------------
//描述:
//设计: Skyline(2001.04.22)
//实现: Skyline
//修改:
//--------------------------------------------------------------------------------------------------
public class JWaitThread extends Thread {
  JProgressBar pbProgressBar;
  boolean isWait = false;
  //----------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //----------------------------------------------------------------------------------------------
  public JWaitThread(JProgressBar pb) {
    pbProgressBar = pb;
  }
  //----------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //----------------------------------------------------------------------------------------------
  public void run() {
    int Count = 0 ;
    try {
      while ( true ) {
        synchronized ( this ) {
          if ( isWait ) {
            Count = 0;
            ProcessBar(0);
            wait();
          }
          Count += 1;
          ProcessBar(Count%100);
        }
        yield();
        sleep(100);
      }
    } catch ( Exception e ) {
      //WaitThread = null;
//      e.printStackTrace();
    }
    return;
  }
  //----------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //----------------------------------------------------------------------------------------------
  synchronized void ProcessBar(int Value) {
    this.pbProgressBar.setValue(Value);
  }
  //----------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //----------------------------------------------------------------------------------------------
  synchronized public void BeginWait() {
    try {
      synchronized(this) {
        isWait = false;
        this.notify();
      }
    } catch ( Exception e ) {
      e.printStackTrace();
    }
  }
  //----------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //----------------------------------------------------------------------------------------------
  synchronized public void EndWait() {
    try {
      synchronized(this) {
        isWait = true;
      }
    } catch ( Exception e ) {
      e.printStackTrace();
    }
  }
}