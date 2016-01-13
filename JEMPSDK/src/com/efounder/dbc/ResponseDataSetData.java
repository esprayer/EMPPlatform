package com.efounder.dbc;

import java.io.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class ResponseDataSetData implements Serializable {
  protected int       errorCode      = 0;
  protected Object    agentObject    = null;
  protected Exception agentException = null;
  public int getErrorCode() {
    return errorCode;
  }

  public Exception getAgentException() {
    return agentException;
  }

  public Object getAgentObject() {
    return agentObject;
  }

  public ResponseDataSetData(Object ao,Exception ex,int ec) {
    agentObject = ao;
    agentException = ex;
    errorCode = ec;
  }

}
