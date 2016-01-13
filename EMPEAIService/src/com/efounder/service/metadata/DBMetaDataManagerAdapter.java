package com.efounder.service.metadata;

import com.efounder.sql.*;
import com.efounder.eai.data.*;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class DBMetaDataManagerAdapter extends DBMetaDataManager {
  /**
   *
   */
  public DBMetaDataManagerAdapter() {
  }

  /**
   *
   * @param OBJ_IDS Object[]
   * @return DBMetaData[]
   * @todo Implement this com.efounder.service.metadata.DBMetaDataManager method
   */
  public DBMetaData[] getDBMetaData(Object[] OBJ_IDS) {
    return null;
  }

  /**
   *
   * @param OBJ_IDS Object[]
   * @param CONS Object[]
   * @return DBResultSet[]
   * @todo Implement this com.efounder.service.metadata.DBMetaDataManager method
   */
  public DBResultSet[] getDBResultSet(Object[] OBJ_IDS, Object[] CONS) {
    return null;
  }
  /**
   *
   */
  protected JParamObject PO           = null;
  /**
   *
   */
  protected JConnection  dbConnection = null;
  /**
   *
   * @param contextObject Object
   * @todo Implement this com.efounder.service.metadata.DBMetaDataManager method
   */
  protected void initContextObject(Object contextObject) {
    // 获取当前的JParamObject对象
    PO = (JParamObject)contextObject;
    // 获取数据库链接
    dbConnection = (JConnection)PO.getLosableValue("_CONNECTION_");
  }
  /**
   * 获取当前的参数对象
   * @return JParamObject
   */
  protected JParamObject getParamObject() {
    return PO;
  }
  /**
   * 获取当前的数据链接对象
   * @return JConnection
   */
  protected JConnection  getConnection() {
    return dbConnection;
  }
}
