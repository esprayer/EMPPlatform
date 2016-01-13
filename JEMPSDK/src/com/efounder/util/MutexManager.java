package com.efounder.util;

import java.io.*;
import com.efounder.eai.*;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2003</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class MutexManager {
  /**
   *
   */
  protected MutexManager() {
  }
  /**
   *
   */
  protected static java.util.Map mutexLockList = new java.util.Hashtable();
  /**
   *
   * @param mutexObject Object
   * @return boolean
   */
  public static boolean isRunMutexObject(Object mutexObject) {
    return ( "1".equals(mutexLockList.get(mutexObject)) );
  }
  /**
   *
   * @param mutexObject Object
   */
  public static void setRunMutexObject(Object mutexObject) {
    mutexLockList.put(mutexObject,"1");
  }
  /**
   *
   * @param mutexObject Object
   */
  public static void clearRunMutexObject(Object mutexObject) {
    mutexLockList.remove(mutexObject);
  }
  /**
   *
   * @param key String
   * @return Object
   */
  public static Object getMutexObject(String key) {
    String localIP = netutil.getLocalAddress();
    String Application = EAI.Application;
    String Product     = EAI.Product;
    String timeStamp   = String.valueOf(System.currentTimeMillis());
    return localIP+"-"+Application+"-"+Product+"-"+timeStamp+"-"+key;
  }


}
