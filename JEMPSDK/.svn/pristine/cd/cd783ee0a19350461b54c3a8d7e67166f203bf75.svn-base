package com.efounder.eai.data;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class EAServerException extends Exception implements java.io.Serializable {
  protected String ExceptionClassName = null;
  protected String ErrorMessage  = null;
  protected java.util.Hashtable  ErrorMessageList = new java.util.Hashtable();
  protected StackTraceElement[]  stackArray = null;
  public java.util.Map getErrorMessageList() {
    return ErrorMessageList;
  }
  public String getExceptionClassName() {
    return ExceptionClassName;
  }

  public EAServerException(Throwable e) {
    Throwable ee = getThrowable(e);
    // 获取堆zhan
    stackArray = ee.getStackTrace();
    // 获取类名
    ExceptionClassName = ee.getClass().getName();
    ErrorMessage = ee.getMessage();
    getErrorMessageList(e);
  }
  void getErrorMessageList(Throwable e) {
    if ( e == null ) return;
    String m = e.getMessage();
    if ( m != null ) {
      ErrorMessageList.put(e.getClass().getName(),m);
    }
    getErrorMessageList(e.getCause());
  }

  String getErrorMessage(Throwable e) {
    if ( e == null ) return null;
    String m = e.getMessage();
    if ( m != null ) return m;else return getErrorMessage(e.getCause());
  }
  Throwable getThrowable(Throwable e) {
    String m = e.getMessage();
    if ( m != null )
      return e;
    else {
      if ( e.getCause() == null )
        return e;
      else
        return getThrowable(e.getCause());
    }
  }

  public String getMessage() {
      return ErrorMessage;
  }
  public String toString() {
      String s = ExceptionClassName;
      String message = getLocalizedMessage();
      return (message != null) ? (s + ": " + message) : s;
  }
  public StackTraceElement[] getStackTrace() {
      return stackArray;
  }

}
