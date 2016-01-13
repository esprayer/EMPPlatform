package jservice.jbof.classes.GenerQueryObject;

import java.util.Map;
import java.util.HashMap;

/**
 *
 * <p>Title:静态数据存储区,用于在某些特殊情况下，在类之间传输数据。 </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
public class GlobalDataObject {
  public static String __MULTI_PAGE_QUERY = "__MULTI_PAGE";
  public static String __PARAM_KEY = "__PARAM_KEY";
  public static String __ERROR_INFO = "__ERROR_INFO";
  private static Map dataMap = new HashMap();

  /**
   * 缓存数据。
   * @param key String
   * @param value Object
   */
  public static void putValue(String key, Object value) {
    dataMap.put(key, value);
  }

  /**
   * 删除并返回该值对应的数据.
   * @param key String
   * @return Object
   */
  public static Object getValue(String key) {
    return dataMap.remove(key);
  }

  /**
   * 测试是否有该值对应的缓存数据。
   * @param key String
   * @return boolean
   */
  public static boolean existValue(String key) {
     return dataMap.containsKey(key);
  }

  public static void main(String[] args) {
    putValue("AAA", "AAA_AAA");
    putValue("BBB", "BBB_BBB");
    Object obj = getValue("AAA");
    obj = getValue("AAA");
    obj = getValue("BBB");
  }
}
