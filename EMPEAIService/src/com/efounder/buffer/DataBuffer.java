package com.efounder.buffer;

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
public abstract class DataBuffer {
  protected Object dataBufferKey = null;
  /**
   * 数据缓冲区列表
   */
  protected java.util.Map dataList = null;
  /**
   *
   */
  protected DataBuffer() {
  }
  /**
   * 初始Hash的大小
   */
  protected static final int INIT_SIZE = 200;
  /**
   *
   */
  protected void initDataList() {
    if ( dataList == null ) {
      dataList = new java.util.concurrent.ConcurrentHashMap(INIT_SIZE);
    }
  }
  public java.util.Map getBufferMap(){
    return dataList;
  }
  /**
   *
   * @param key Object
   * @param data Object
   */
  public void addData(Object key,Object data) {
    initDataList();
    if(data!=null)
      dataList.put(key,data);
    else
      dataList.remove(key);
  }
  /**
   *
   * @param key Object
   */
  public void removeData(Object key) {
    if ( dataList != null ) {
      dataList.remove(key);
    }
  }
  /**
   *
   * @param key Object
   * @return Object
   */
  public Object getData(Object key) {
    if ( dataList != null &&key!=null) {
      return dataList.get(key);
    }
    return null;
  }

  /**
   *
   * @return String[]
   */
  public String[] getDataKey() {
      if (dataList == null)
          return null;
      Object[] arr = dataList.keySet().toArray();
      if (arr == null)
          return null;
      String[] str = new String[arr.length];
      System.arraycopy(arr, 0, str, 0, str.length);
      return str;
  }

  /**
   *
   * @param key Object
   * @return boolean
   */
  public boolean exists(Object key) {
    if ( dataList != null ) {
      return dataList.containsKey(key);
    }
    return false;
  }

}
