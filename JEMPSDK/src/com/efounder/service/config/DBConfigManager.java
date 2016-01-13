package com.efounder.service.config;

import com.efounder.eai.data.*;
import java.util.*;

public class DBConfigManager {
  /**
   *
   */
  protected DBConfigManager() {
  }
  /**
   *
   */
  protected static Map dbValueMap = null;
  /**
   *
   * @param key String
   * @param def String
   * @return String
   */
  public static String getValue(JParamObject param,String key,String def) {
    Map valueMap = getValueMap(param);
    if ( valueMap == null ) return def;
    Object res = valueMap.get(key);
    return res==null?def:res.toString();
  }
  public static Map getValueMap(JParamObject param) {
    if ( dbValueMap == null ) return null;
    String appID = param.getAppUniqueID();
    if ( appID == null ) return null;
    return (Map)dbValueMap.get(appID);
  }
  /**
   *
   * @param map Map
   */
  public static void setValueMap(JParamObject param,Map map) {
    String appID = param.getAppUniqueID();
    if ( dbValueMap == null ) dbValueMap = new HashMap();
    dbValueMap.put(appID,map);
  }
}
