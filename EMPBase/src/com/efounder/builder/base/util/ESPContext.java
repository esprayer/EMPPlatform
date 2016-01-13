package com.efounder.builder.base.util;

import com.efounder.builder.base.data.*;
import com.efounder.eai.data.*;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2009</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public abstract class ESPContext extends EFRowSet {
  /**
   *
   * @return String
   */
  public abstract String getDBUniqueID();
  /**
   *
   */
  protected ESPContext() {
  }
  /**
   *
   */
  protected JParamObject paramObject = null;
  /**
   *
   * @return JParamObject
   */
  public JParamObject getParamObject() {
    return paramObject;
  }
  /**
   *
   */
  protected Exception exception  = null;
  /**
   *
   * @return Exception
   */
  public Exception getException() {
    return exception;
  }
  /**
   *
   * @param exception Exception
   */
  public void setException(Exception exception) {
    this.exception = exception;
  }
  /**
   *
   */
  protected Object contextObject = null;
  private String returnMessage = null;
  /**
   *
   * @return Object
   */
  public Object getContextObject() {
    return contextObject;
  }

  private Object returnObject = null;

  public String getReturnMessage() {
    return returnMessage;
  }

  public int getReturnCode() {
    return returnCode;
  }

  public Object getReturnObject() {
    return returnObject;
  }

  private int returnCode = 0;

  /**
   *
   * @param contextObject Object
   */
  public void setContextObject(Object contextObject) {
    this.contextObject = contextObject;
  }

  public void setReturnMessage(String returnMessage) {
    this.returnMessage = returnMessage;
  }

  public void setReturnCode(int returnCode) {
    this.returnCode = returnCode;
  }

  public void setReturnObject(Object returnObject) {
    this.returnObject = returnObject;
  }

}
