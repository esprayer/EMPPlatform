package com.efounder.service.func;

import com.efounder.model.biz.*;

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
public abstract class BIZExecuteFunctionAdapter implements BIZExecuteFunction {
  /**
   *
   */
  public BIZExecuteFunctionAdapter() {
  }
  /**
   *
   */
  protected Object userObject  = null;
  /**
   *
   */
  protected Object addinObject = null;
  private BIZFunction bizFunction;

  public void setUserObject(Object userObject) {
    this.userObject = userObject;
  }

  public void setAddinObject(Object addinObject) {
    this.addinObject = addinObject;
  }

  public void setBizFunction(BIZFunction bizFunction) {
    this.bizFunction = bizFunction;
  }

  public Object getUserObject() {
    return userObject;
  }

  public Object getAddinObject() {
    return addinObject;
  }

  public BIZFunction getBizFunction() {
    return bizFunction;
  }
  public Object proxyPrepare(BIZFunction bizFunction,Object userObject,Object addinObject) throws Exception {
    return prepare(bizFunction,userObject,addinObject);
  }
  /**
   *
   * @param bizFunction BIZFunction
   * @param userObject Object
   * @param addinObject Object
   * @return Object
   * @throws Exception
   */
  protected abstract Object prepare(BIZFunction bizFunction,Object userObject,Object addinObject) throws Exception;
  public Object proxyExecute(BIZFunction bizFunction,Object prepareObject,Object userObject,Object addinObject) throws Exception {
    return execute(bizFunction,prepareObject,userObject,addinObject);
  }

  /**
   *
   * @param bizFunction BIZFunction
   * @param prepareObject Object
   * @param userObject Object
   * @param addinObject Object
   * @return Object
   * @throws Exception
   */
  protected abstract Object execute(BIZFunction bizFunction,Object prepareObject,Object userObject,Object addinObject) throws Exception;
  /**
   *
   * @return Object
   * @throws Exception
   */
  public abstract Object finish() throws Exception;
  /**
   *
   * @return Object
   * @throws Exception
   */
  public Object execute(Object prepareObject) throws Exception {
    return execute(bizFunction,prepareObject,userObject,addinObject);
  }
  /**
   *
   * @return Object
   * @throws Exception
   */
  public Object prepare() throws Exception {
    return prepare(bizFunction,userObject,addinObject);
  }
  /**
   *
   * @return boolean
   */
  public boolean isThread(BIZFunction bizFunction) {
    return isThread();
  }
  /**
   *
   * @return boolean
   */
  public boolean isThread() {
    return true;
  }
}
