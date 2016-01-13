package com.efounder.builder.base.util;

import java.sql.*;

import com.efounder.eai.data.*;
import com.efounder.sql.*;

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
public class ESPServerContext extends ESPContext {
  // 同步运行
  public static final int SYNC_RUN  = 0x0001;
  // 异步运行
  public static final int ASYNC_RUN = 0x0002;
  // 异步方式运行时所在的任务
  protected org.openide.util.RequestProcessor.Task serviceTask = null;
  /**
   *
   * @param task Task
   */
  public void setServiceTask(org.openide.util.RequestProcessor.Task task) {
    this.serviceTask = task;
  }
  /**
   *
   * @return Task
   */
  public org.openide.util.RequestProcessor.Task getServiceTask() {
    return serviceTask;
  }
  //
  protected ESPServerContext() {
  }
  /**
   *
   * @return ESPServerContext
   */
  public static ESPServerContext getInstance(JParamObject paramObject,JConnection conn) {
    ESPServerContext ctx = new ESPServerContext();
    ctx.paramObject = paramObject;
    ctx.connection  = conn;
    return ctx;
  }
  /**
   *
   */
  protected Object dataObject = null;
  /**
   *
   * @return Object
   */
  public Object getDataObject() {
    return dataObject;
  }
  /**
   *
   * @param dataObject Object
   */
  public void setDataObject(Object dataObject) {
    this.dataObject = dataObject;
  }
  /**
   *
   */
  protected Object customObject = null;
  /**
   *
   * @return Object
   */
  public Object getCustomObject() {
    return customObject;
  }
  /**
   *
   * @param customObject Object
   */
  public void setCustomObject(Object customObject) {
    this.customObject = customObject;
  }
  /**
   *
   */
  protected Object addinObject = null;
  /**
   *
   * @param addinObject Object
   */
  public void setAddinObject(Object addinObject) {
    this.addinObject = addinObject;
  }
  /**
   *
   * @return Object
   */
  public Object getAddinObject() {
    return addinObject;
  }
  /**
   *
   */
  protected int runType = SYNC_RUN;
  /**
   *
   * @return int
   */
  public int getRunType() {
    return runType;
  }
  /**
   *
   * @param runType int
   */
  public void setRunType(int runType) {
    this.runType = runType;
  }
  /**
   *
   * @return String
   */
  public String getDBUniqueID() {
    return "";
  }
  /**
   *
   * @param paramObject JParamObject
   */
  public void setParamObject(JParamObject paramObject) {
    this.paramObject = paramObject;
  }

  /**
   *
   */
  protected JConnection connection   = null;
  /**
   *
   * @return JConnection
   */
  public JConnection getConnection() {
    return connection;
  }
  /**
   *
   * @param connection JConnection
   */
  public void setConnection(JConnection connection) {
    this.connection = connection;
  }
  /**
   *
   */
  protected Statement statement      = null;
  /**
   *
   * @return Statement
   */
  public Statement getStatement() {
    return statement;
  }
  /**
   *
   * @param statement Statement
   */
  public void setStatement(Statement statement) {
    this.statement = statement;
  }
  /**
   *
   */
  protected JResponseObject responseObject = null;
  /**
   *
   * @return JResponseObject
   */
  public JResponseObject getResponseObject() {
    return responseObject;
  }
  /**
   *
   * @param responseObject JResponseObject
   */
  public void setResponseObject(JResponseObject responseObject) {
    this.responseObject = responseObject;
  }
}
