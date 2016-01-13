package com.efounder.service.metadata;

import org.openide.util.*;
import com.efounder.eai.data.*;
import com.efounder.sql.*;

/**
 * <p>Title: EFounder 元数管理</p>
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
public abstract class DBMetaDataManager {
  /**
   *
   */
  protected static java.util.Map dbmetaDataManagerList = new java.util.HashMap();
  /**
   *
   */
  protected DBMetaDataManager() {
  }
  /**
   *
   * @return MetaDataManager
   */
  public static DBMetaDataManager getInstance(Object key,Object contextObject) {
    /**
     * 从Hashtable中获取已存在的元数据管理器
     */
    DBMetaDataManager ddm = (DBMetaDataManager)dbmetaDataManagerList.get(key);
    if ( ddm == null ) {
      /**
       * 查找出指定的DBMetaDataManager
       */
      ddm = (DBMetaDataManager)Lookup.getDefault().lookup(com.efounder.service.metadata.DBMetaDataManager.class,key);
      /**
       * 放入Hashtable中
       */
      dbmetaDataManagerList.put(key,ddm);
    }
    // 如果不空，则初始化ContextObject
    if ( ddm != null ) ddm.initContextObject(contextObject);
    return ddm;
  }
  /**
   *
   */
  protected final static String FIX_DB_META_MANAGER_ID = "EFOUNDER";
  protected final static String CLIENT_DB_META_MANAGER_ID = "CLIENT";
  /**
   *
   * @param contextObject Object
   * @return MetaDataManager
   */
  public static DBMetaDataManager getInstance(Object contextObject) {
    return getInstance(FIX_DB_META_MANAGER_ID,contextObject);
  }
  /**
   *
   * @param contextObject Object
   * @return DBMetaDataManager
   */
  public static DBMetaDataManager getClientInstance(Object contextObject) {
    return getInstance(CLIENT_DB_META_MANAGER_ID,contextObject);
  }
  /**
   *
   * @param contextObject Object
   */
  protected abstract void initContextObject(Object contextObject);
  /**
   *
   * @param OBJ_IDS Object[]
   * @return DBMetaData[]
   */
  public abstract DBMetaData[] getDBMetaData(Object[] OBJ_IDS);
  /**
   *
   * @param OBJ_ID Object
   * @return DBMetaData
   */
  public final DBMetaData getDBMetaData(Object OBJ_ID) {
    Object[] OBJ_IDS = {OBJ_ID};
    DBMetaData[] dd = getDBMetaData(OBJ_IDS);
    return dd[0];
  }
  /**
   *
   * @param OBJ_IDS Object[]
   * @param con Object[]
   * @return DBResultSet[]
   */
  public abstract DBResultSet[] getDBResultSet(Object[] OBJ_IDS,Object[] CONS);
  /**
   *
   * @param OBJ_ID Object
   * @param CON Object
   * @return DBResultSet
   */
  public final DBResultSet getDBResultSet(Object OBJ_ID,Object CON) {
    Object[] OBJ_IDS = {OBJ_ID};Object[] CONS = {CON};
    DBResultSet[] ds = getDBResultSet(OBJ_IDS,CONS);
    return ds[0];
  }
}
