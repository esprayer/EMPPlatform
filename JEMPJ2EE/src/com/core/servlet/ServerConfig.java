package com.core.servlet;

import java.util.*;
import java.io.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class ServerConfig {
  private static Properties mainConfigList = null;
  private static String ConfigBase = null;
  private static final String CONFIG_FILE_NAME = "/Start/server.properties";
  public static void initMainConfig(Hashtable hashTable) {
//    if ( mainConfigList != null ) return;
//    ConfigBase = (String)hashTable.get("root.home");
    mainConfigList = new Properties();
    loadConfig();
    if ( hashTable == null ) return;
    try{
      Enumeration eu = hashTable.keys();
      String key;
      while (eu.hasMoreElements()) {
        key = (String) eu.nextElement();
        System.setProperty(key, (String) hashTable.get(key));
      }
    }catch(Exception e){
      e.printStackTrace();
    }
  }
  public static void put(Object k,Object v) {
    mainConfigList.put(k,v);
//    saveConfig();
  }
  public static Object get(Object k,Object def) {
    Object res = null;
    res = mainConfigList.get(k);
    if ( res == null ) res = def;
    return res;
  }
//  public static void saveConfig() {
//    File file = null;
//    try {
//      file = new File(ConfigBase+CONFIG_FILE_NAME);
//      FileOutputStream fos = new FileOutputStream(file);
//      mainConfigList.store(fos,"ServerConfig Ver 1.0.0 by xSkyline");
//      fos.flush();
//      fos.close();
//    } catch ( Exception e ) {
//      e.printStackTrace();
//    }
//  }
  public static void loadConfig() {
    File file = null;
    try {
      java.net.URL url = ServerConfig.class.getResource(CONFIG_FILE_NAME);
      if ( url != null ) {
        mainConfigList.load(url.openStream());
      }
    } catch ( Exception e ) {
      e.printStackTrace();
    }
  }
}
