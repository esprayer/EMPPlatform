package com.efounder.builder.util;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Map;

/**
 * @author ES
 *
 */
public final class MapUtil {
  /**
   *
   * @param map
   * @param keyName
   * @param number
   */
  public static void putNumber(Map map, String keyName, Number value) {
    putObject(map,keyName,value);
  }

  /**
   *
   * @param map
   * @param keyName
   * @param defNum
   * @return
   */
  public static Number getNumber(Map map, String keyName, Number defValue) {
    Object value = getObject(map,keyName,defValue);
    if (value instanceof Number && value != null) {
      return (Number) value;
    }
    else {
      return defValue;
    }
  }

  /**
   *
   * @param map
   * @param keyName
   * @param value
   */
  public static void putString(Map map, String keyName, String value) {
    putObject(map,keyName,value);
  }

  /**
   *
   * @param map
   * @param keyName
   * @param defValue
   * @return
   */
  public static String getString(Map map, String keyName, String defValue) {
    Object value = getObject(map,keyName,defValue);
    if (value instanceof String && value != null) {
      return (String) value;
    }
    else {
      return defValue;
    }
  }

  public static void putBoolean(Map map, String keyName, Boolean value) {
    putObject(map,keyName,value);
  }

  /**
   *
   * @param map
   * @param keyName
   * @param defValue
   * @return
   */
  public static Boolean getBoolean(Map map, String keyName, Boolean defValue) {
    Object value = getObject(map,keyName,defValue);
    if (value instanceof Boolean && value != null) {
      return (Boolean) value;
    }
    else {
      return defValue;
    }
  }

  /**
   *
   * @param map
   * @param keyName
   * @param value
   */
  public static void putDate(Map map, String keyName, Date value) {
    putObject(map,keyName,value);
  }

  /**
   *
   * @param map
   * @param keyName
   * @param defValue
   * @return
   */
  public static Date getDate(Map map, String keyName, Date defValue) {
    Object value = getObject(map,keyName,defValue);
    if (value instanceof Date && value != null) {
      return (Date) value;
    }
    else {
      return defValue;
    }
  }

  /**
  *
  * @param map
  * @param keyName
  * @param defValue
  * @return
  */
 public static Timestamp getTimestamp(Map map, String keyName, Timestamp defValue) {
   Object value = getObject(map,keyName,defValue);
   if (value instanceof Timestamp && value != null) {
     return (Timestamp) value;
   }
   else {
     return defValue;
   }
 }
 
  /**
   *
   * @param map
   * @param keyName
   * @param value
   */
  public static void putObject(Map map, String keyName, Object value) {
    if (map == null) {
      return;
    }
    if(value==null)
      map.remove(keyName);
    else
      map.put(keyName, value);
  }

  /**
   *
   * @param map
   * @param keyName
   * @param defValue
   * @return
   */
  public static Object getObject(Map map, String keyName, Object defValue) {
    if (map == null) {
      return defValue;
    }
    Object value = map.get(keyName);
    if (value instanceof Object && value != null) {
      return (Object) value;
    }
    else {
      return defValue;
    }
  }
}
