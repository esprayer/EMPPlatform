package com.efounder.buffer;

import java.util.*;

import com.efounder.eai.service.MemCachedManager;
import com.efounder.object.ChildLinkParentKey;

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
public class DataBufferManager {
  /**
   *
   */
  protected Object BufferManagerKey = null;
  /**
   *
   */
  protected java.util.Map dataBufferList = null;
  /**
   *
   */
  protected DataBufferManager(Object key) {
    BufferManagerKey = key;
  }
  /**
   *
   * @param v boolean
   */
  public static void setRememberDBM(boolean v) {
    rememberDBM = v;
  }
  /**
   *
   * @return boolean
   */
  public static boolean isRememberDBM() {
    return rememberDBM;
  }
  /**
   *
   */
  protected static boolean rememberDBM = true;
  /**
   *
   */
  protected static java.util.Map dataBufferManagerMap = null;
  /**
   *
   * @return Map
   */
  public static java.util.Map getDataBufferManagerMap() {
    return dataBufferManagerMap;
  }
  /**
   *
   * @param key Object
   * @return DataBufferManager
   */
  public static DataBufferManager createDataBufferManager(Object key) {
    return createDataBufferManager(key,true);
  }
  /**
   *
   * @param key Object
   * @return DataBufferManager
   */
  public static DataBufferManager createDataBufferManager(Object key,boolean memdbm) {
    Object uniKey = null;
    if ( key instanceof ChildLinkParentKey ) {
      uniKey = ((ChildLinkParentKey)key).getMasterKey();
    }
    if ( uniKey == null ) uniKey = key;
    DataBufferManager dbm = new DataBufferManager(uniKey);
    pubMemMap(key,dbm,memdbm);
    return dbm;
  }
  /**
   *
   * @param key Object
   * @return DataBufferManager
   */

  public synchronized static DataBufferManager getDataBufferManager(Object key) {
    if ( dataBufferManagerMap == null ) return null;
    Object uniKey = null;
    if ( key instanceof ChildLinkParentKey ) {
      uniKey = ((ChildLinkParentKey)key).getMasterKey();
    }
    if ( uniKey == null ) uniKey = key;
    return (DataBufferManager)dataBufferManagerMap.get(uniKey);
  }
  /**
   *
   * @param key Object
   * @return DataBufferManager
   */
  public synchronized static DataBufferManager removeDataBufferManager(Object key) {
    if ( dataBufferManagerMap == null ) return null;
    Object uniKey = null;
    if ( key instanceof ChildLinkParentKey ) {
      uniKey = ((ChildLinkParentKey)key).getMasterKey();
    }
    if ( uniKey == null ) uniKey = key;
    DataBufferManager dbm= (DataBufferManager)dataBufferManagerMap.remove(uniKey);
    dbm.removeAllDataBuffer();
    return dbm;
  }
  /**
   *
   * @param key Object
   * @param dbm DataBufferManager
   */
  protected synchronized static void pubMemMap(Object key,DataBufferManager dbm,boolean memdbm) {
    if ( !memdbm ) return;
    if ( !rememberDBM ) return;
    if ( dataBufferManagerMap == null ) dataBufferManagerMap = new java.util.HashMap();
    Object uniKey = null;
    if ( key instanceof ChildLinkParentKey ) {
      uniKey = ((ChildLinkParentKey)key).getMasterKey();
    }
    if ( uniKey == null ) uniKey = key;
    dataBufferManagerMap.put(uniKey,dbm);
  }
  /**
   *
   * @param key Object
   * @param dbm DataBufferManager
   */
  public synchronized static void putDataBufferManager(Object key,DataBufferManager dbm) {
    if ( dataBufferManagerMap == null ) dataBufferManagerMap = new java.util.HashMap();
    Object uniKey = null;
    if ( key instanceof ChildLinkParentKey ) {
      uniKey = ((ChildLinkParentKey)key).getMasterKey();
    }
    if ( uniKey == null ) uniKey = key;
    dataBufferManagerMap.put(uniKey,dbm);
  }
  /**
   *
   * @param key Object
   * @param <any> unknown
   * @return DataBuffer
   */
  public DataBuffer createDataBuffer(Object key) {
    // 创建一个新的DataBuffer
    DataBuffer dataBuffer = StubObjectBuffer.getInstance(key);
    // 增加到DataBuffer列表中
    addDataBufer(key,dataBuffer);
    // 返回当前创建的DataBuffer
    return dataBuffer;
  }
  /**
   *
   * @param key String
   * @return DataBuffer
   */
  public DataBuffer getMemCached(String key) {
	  MemCachedManager memCachedManager = MemCachedManager.getDefault();
//    Object value = memCachedManager.getMemCached().get(key);
//    if ( value != null ) {
//      DataBuffer dataBuffer = MemCachedBuffer.getInstance(key);
//      return dataBuffer;
//    }
	  DataBuffer db=getDataBuffer(key);
//    if(db==null&&!(db instanceof MemCachedBuffer)){
//      return null;
//    }
	  return db;
  }
  /**
   *
   * @param key String
   * @return DataBuffer
   */
  public DataBuffer createMemCached(String key) {
    DataBuffer dataBuffer = MemCachedBuffer.getInstance(key);
    // 增加到DataBuffer列表中
    addDataBufer(key,dataBuffer);
    MemCachedManager memCachedManager = MemCachedManager.getDefault();
//    memCachedManager.getMemCached().add(key,"1");
    // 返回当前创建的DataBuffer
    return dataBuffer;
  }
  /**
   * 初始化缓冲区列表
   */
  private void initDataBufferList() {
    if ( dataBufferList == null ) {
      dataBufferList = new java.util.HashMap();
    }
  }
  /**
   *
   */
  public void removeAllDataBuffer() {
//    if ( dataBufferList != null ){
//      Iterator it=dataBufferList.keySet().iterator();
//      while (it.hasNext()) {
//        Object o = it.next();
//        DataBuffer db=getDataBuffer(o);
//        if (db != null && db instanceof MemCachedBuffer)
//          ( (MemCachedBuffer) db).clearAll();
//      }
//      dataBufferList.clear();
//    }
//    dataBufferList = null;
  }
  /**
   *
   * @param key Object
   * @param dataBuffer DataBuffer
   */
  public void addDataBufer(Object key,DataBuffer dataBuffer) {
    initDataBufferList();
    dataBufferList.put(key,dataBuffer);
  }
  /**
   *
   * @param key Object
   */
  public void removeDataBuffer(Object key) {
//    if ( dataBufferList != null ) {
//      DataBuffer db=getDataBuffer(key);
//      if(db!=null&&db instanceof MemCachedBuffer)
//        ((MemCachedBuffer)db).clearAll();
//      dataBufferList.remove(key);
//    }
  }
  /**
   *
   * @param key Object
   * @return DataBuffer
   */
  public DataBuffer getDataBuffer(Object key) {
    if ( dataBufferList != null ) {
      return (DataBuffer)dataBufferList.get(key);
    }
    return null;
  }
  /**
   *
   * @return Map
   */
  public java.util.Map getDataBufferList() {
    return dataBufferList;
  }

}
