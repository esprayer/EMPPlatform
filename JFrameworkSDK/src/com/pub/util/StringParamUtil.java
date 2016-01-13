package com.pub.util;

import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;
import com.core.xml.StubObject;
import java.util.StringTokenizer;
import java.util.LinkedHashMap;
import java.util.Hashtable;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

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
public class StringParamUtil {
  public StringParamUtil() {
  }


  /**
   *
   * @param strSjlx String
   * @return List
   */
  public static List getListFromStr(String strSjlx) {
    return getListFromStr(strSjlx, ";");
  }

  /**
   *
   * @param strSjlx String
   * @param sign String
   * @return List
   */
  public static List getListFromStr(String strSjlx, String sign) {
    List retList = new ArrayList();
    StringTokenizer st = new StringTokenizer(strSjlx, sign);
    StubObject so = null;
    while (st.hasMoreTokens()) {
      String item = st.nextToken();
      retList.add(item);
    }

    return retList;
  }

  /**
   *
   * @param str String
   * @return Map
   */
  public static Map getMapFromStr(String str) {
    return getMapFromStr(str, ";");
  }

  /**
   *
   * @param str String
   * @param sign String
   * @return Map
   */
  public static Map getMapFromStr(String str, String sign) {
    return getMapFromStr(str,sign,":");
  }

  /**
   * ��ʽ:   ID:YS_BKZD,MC:����ֵ�,JS:2
   * ���ø�ʽ:getMapFromStr(str,",",":")
   * @param str String
   * @param sign1 String
   * @param sign2 String
   * @return Map
   */
  public static Map getMapFromStr(String str, String sign1,String sign2) {
//  Map retMap = new HashMap();
    Map retMap = new LinkedHashMap();
    List l = getListFromStr(str, sign1);
    for (int i = 0; i < l.size(); i++) {
      String s = (String) l.get(i);
      if("".equals(s.trim())) continue;
      if(s.indexOf(sign2) ==-1) continue;//add by luody
      String key = s.substring(0, s.indexOf(sign2));
      String value = s.substring(s.indexOf(sign2) + 1);

      retMap.put(key, value);
    }

    return retMap;
}

/**
 * ��ǰ׺���ַ�
 *  * ��ʽ:   SRC=ID:YS_BKZD;MC:����ֵ�;JS:2
   * ���ø�ʽ:getMapFromPrefixStr(str,";",":")
 * @param srcStr String
 * @param sign1 String
 * @param sign2 String
 * @return Map
 */
public static Map getMapFromPrefixStr(String srcStr,String sign1,String sign2) {
//  Map map = new HashMap();
  Map map = new LinkedHashMap();
  StringTokenizer st = new StringTokenizer(srcStr, ",");
  while(st.hasMoreTokens()){
    String item = st.nextToken();
    if("".equals(item.trim())) continue;
    int pos = item.indexOf("=");
    if(pos > 0) {//ֻҪ�Ⱥź��������
      srcStr = item.substring(pos+1);
    }
    Map tmpMap = getMapFromStr(srcStr, sign1,sign2);
    if(tmpMap != null){
      map.putAll(tmpMap);
    }
  }

  return map;
 }

 public static Map getMapFromPrefixStr(String srcStr,String sign1) {
   return getMapFromPrefixStr(srcStr,sign1, ":");
}

 public static String convertStrFromKVMap(Map kvMap){
//  if(kvMap == null) return null;
//  String strTxt = "";
//  Object[] keys = kvMap.keySet().toArray();
//  for(int i = 0; i < keys.length; i++){
//    String value = (String)kvMap.get(keys[i]);
//    strTxt += (String)keys[i]+":"+value+",";
//  }
//  strTxt = strTxt.substring(0,strTxt.lastIndexOf(","));
//  return strTxt;
     return convertStrFromKVMap(kvMap,":");
}

public static String convertStrFromKVMap(Map kvMap,String sign){
   if(kvMap == null) return null;
   String strTxt = "";
   Object[] keys = kvMap.keySet().toArray();
   for(int i = 0; i < keys.length; i++){
     String value = (String)kvMap.get(keys[i]);
     strTxt += (String)keys[i]+sign+value+",";
   }
   strTxt = strTxt.substring(0,strTxt.lastIndexOf(","));
   return strTxt;
}

/**
 *
 * @param keyValueStr String  �ַ�
 * @param distance String �ָ�����|��
 * @param keyValueDistance String key & value �ָ���
 * @return Map
 */
public  static Map keyValueToMap(String keyValueStr,String distance,String keyValueDistance){
     if(distance == null || "".equals(distance.trim())) distance = ",";//Ĭ��Ϊ���ż��
     StringTokenizer st = new StringTokenizer(keyValueStr,distance);
     String keyValue = "";
     String key = "";
     String value = "";
     Map map = new HashMap();
     while(st.hasMoreTokens()){
       keyValue = st.nextToken();
       if(keyValue != null){
         if(keyValueDistance == null || "".equals(keyValueDistance.trim()))
          keyValueDistance = "=";
          int start = keyValue.indexOf(keyValueDistance);
          if(start >0){
            key = keyValue.substring(0,start);
            value = keyValue.substring(start+keyValueDistance.length(),keyValue.length());
            map.put(key.trim(),value);
          }
          else{
             map.put(keyValue.trim(),"");
          }
       }
     }
     return map;
 }
 /**
 *
 * @param keyValueStr String  �ַ�
 * @param distance String �ָ�����|��
 * @param keyValueDistance String key & value �ָ���
 * @return Hashtable
 */
public  static Hashtable keyValueToHashtable(String keyValueStr,String distance,String keyValueDistance){
     if(distance == null || "".equals(distance.trim())) distance = ",";//Ĭ��Ϊ���ż��
     StringTokenizer st = new StringTokenizer(keyValueStr,distance);
     String keyValue = "";
     String key = "";
     String value = "";
     Hashtable map = new Hashtable();
     while(st.hasMoreTokens()){
       keyValue = st.nextToken();
       if(keyValue != null){
         if(keyValueDistance == null || "".equals(keyValueDistance.trim()))
          keyValueDistance = "=";
          int start = keyValue.indexOf(keyValueDistance);
          if(start >0){
            key = keyValue.substring(0,start);
            value = keyValue.substring(start+keyValueDistance.length(),keyValue.length());
            map.put(key.trim(),value);
          }
          else{
             map.put(keyValue.trim(),"");
          }
       }
     }
     return map;
 }
 /**
  * �����ʼ�������־����ַ��б�
  * @param sourceStr String
  * @param startSign String
  * @param endSign String
  * @param includeSign boolean  ���ش����Ƿ�����ʼ��ʶ��
  * @return List
  */
 public static List getSignStr2List(String sourceStr,String startSign,String endSign,boolean includeSign){
    if(sourceStr == null || "".equals(sourceStr.trim())) return null;
    List list = new ArrayList();
    String expStr = "\b"+startSign+"[*]"+endSign+"\b";
    Pattern p = Pattern.compile(expStr);
    Matcher m = p.matcher(sourceStr);
      while(m.find()) {
        String nstr = "";
        if(includeSign) nstr = sourceStr.substring(m.start(),m.end());
        else nstr = sourceStr.substring(m.start()+1,m.end()-1);
        System.out.println(nstr);
        list.add(nstr);
      }
   return list;
 }

 public static List convertArrayToList(Object[] obj){
   if(obj == null) return null;
   List retList = new ArrayList();
   for(int i = 0; i < obj.length; i++){
     retList.add(obj[i]);
   }
   return retList;
 }


}
