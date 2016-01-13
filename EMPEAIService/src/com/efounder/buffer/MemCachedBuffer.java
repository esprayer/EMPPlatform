package com.efounder.buffer;

import com.efounder.eai.service.*;
import java.util.*;
/**
 *
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
public class MemCachedBuffer extends DataBuffer {
  /**
   *
   */
  public MemCachedBuffer(String bkey) {
    dataBufferKey = bkey;
  }
  /**
   *
   */
  protected MemCachedManager memCachedManager = null;
  /**
   *
   */
  protected void initDataList() {
	  if(memCachedManager==null)
		  memCachedManager = MemCachedManager.getDefault();
	  if ( memCachedManager == null || memCachedManager.getMemCached() == null ) {
		  if(memCachedManager.getMemCached() == null) {
			  memCachedManager.initMemCachedClient();
		  }
		  super.initDataList();
	  }
  }
  public java.util.Map getBufferMap(){
      if (memCachedManager == null)
          return super.getBufferMap();
      String uKey = dataBufferKey.toString()+"_ALLKEY";
      Map map=(Map) memCachedManager.getMemCached().get(uKey);
      if(map==null){
          map=new HashMap();
          memCachedManager.getMemCached().add(uKey,map);
      }
      return map;
 }

  public void clearAll(){
    if(memCachedManager==null)return;
    Map map=getBufferMap();
    if(map==null)return;
    Iterator it=map.keySet().iterator();
    while(it.hasNext()){
      Object o=it.next();
      String uKey = dataBufferKey.toString()+"_"+o.toString();
      memCachedManager.getMemCached().delete(uKey);
    }
    map.clear();
    String uKey = dataBufferKey.toString()+"_ALLKEY";
    memCachedManager.getMemCached().delete(uKey);
  }
  /**
   *
   * @param key Object
   * @param data Object
   */
  public void addData(Object key,Object data) {
//      initDataList();
//    if ( memCachedManager != null ) {
//      String uKey = dataBufferKey.toString()+"_"+key.toString();
//      memCachedManager.getMemCached().add(uKey,data);
//      Map map=getBufferMap();
//      map.put(key,"MEMCACHE");
//       uKey = dataBufferKey.toString()+"_ALLKEY";
//      memCachedManager.getMemCached().set(uKey,map);
//    } else {
//      super.addData(key,data);
//    }
  }
  /**
   *
   * @param key Object
   */
  public void removeData(Object key) {
    if ( memCachedManager != null ) {
      String uKey = dataBufferKey.toString()+"_"+key.toString();
      memCachedManager.getMemCached().delete(uKey);;
      Map map=getBufferMap();
      map.remove(key);
      uKey = dataBufferKey.toString()+"_ALLKEY";
      memCachedManager.getMemCached().set(uKey,map);
    }
    super.removeData(key);
  }
  /**
   *
   * @param key Object
   * @return Object
   */
  public Object getData(Object key) {
       initDataList();
      if ( memCachedManager != null ) {
      String uKey = dataBufferKey.toString()+"_"+key.toString();
      return null;
//      return memCachedManager.getMemCached().get(uKey);
    } else {
      return super.getData(key);
    }
  }
  public boolean exists(Object key) {
	  return false;
//    if ( memCachedManager != null ) {
//      String uKey = dataBufferKey.toString()+"_"+key.toString();
//      return memCachedManager.getMemCached().keyExists(uKey);
//    } else {
//      return super.exists(key);
//    }
  }
  /**
   *
   * @param key Object
   * @return DataBuffer
   */
  protected static DataBuffer getInstance(String key) {
    return new MemCachedBuffer(key);
  }
}
