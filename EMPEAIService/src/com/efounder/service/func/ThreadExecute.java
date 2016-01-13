package com.efounder.service.func;

import org.openide.*;
import com.efounder.model.biz.BIZFunction;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2004</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class ThreadExecute implements Runnable {
  protected BIZExecuteFunction exec = null;
  protected BIZFunction bizFunction = null;
  /**
   *
   */
  protected ThreadExecute() {
  }
  /**
   *
   * @param exec BIZExecuteFunction
   * @return ThreadExecute
   */
  public static ThreadExecute getInstance(BIZExecuteFunction exec,BIZFunction bizFunction) {
    ThreadExecute threadExecute = new ThreadExecute();
    threadExecute.exec = exec;threadExecute.bizFunction = bizFunction;
    return threadExecute;
  }
  protected Object responseObject = null;
  protected Object prepareObject = null;
  /**
   *
   * @return Object
   */
  public Object getPrepareObject() {
    return prepareObject;
  }
  /**
   *
   * @return Object
   */
  public Object getResponseObject() {
    return responseObject;
  }
  /**
   * When an object implementing interface <code>Runnable</code> is used to create a thread,
   * starting the thread causes the object's <code>run</code> method to be called in that
   * separately executing thread.
   *
   * @todo Implement this java.lang.Runnable method
   */
  public void run() {
    try {
      execute(this);
    } catch ( Exception e ) {
      e.printStackTrace();
      ErrorManager.getDefault().notify(e);
    } finally {
    }
  }
  public static Object execute(ThreadExecute thread) throws Exception {
    thread.prepareObject  = thread.exec.prepare();
    thread.responseObject = thread.exec.execute(thread.prepareObject);
    thread.exec.finish();
    BIZExecuteManager.processResponse(thread.exec,thread.bizFunction,thread);
    return thread.responseObject;
  }
}
