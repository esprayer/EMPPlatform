package com.tree.util;

import com.core.xml.StubObject;
import java.util.StringTokenizer;
import java.util.Map;
import java.util.HashMap;
import com.efounder.service.tree.TreeNodeStubObject;
import java.util.Iterator;

/**
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
public class TreeTools {
  public TreeTools() {
  }
  //把key=value字符串转换成stubObject对象
   public  static StubObject keyValueToStub(String keyValueStr,String distance,String keyValueDistance){
     if(distance == null || "".equals(distance.trim())) distance = ",";//默认为逗号间隔
     StringTokenizer st = new StringTokenizer(keyValueStr,distance);
     String keyValue = "";
     String key = "";
     String value = "";
     StubObject stub = new StubObject();
     while(st.hasMoreTokens()){
       keyValue = st.nextToken();
       if(keyValue != null){
         if(keyValueDistance == null || "".equals(keyValueDistance.trim()))
          keyValueDistance = "=";
          int start = keyValue.indexOf(keyValueDistance);
          if(start >0){
            key = keyValue.substring(0,start);
            value = keyValue.substring(start+keyValueDistance.length(),keyValue.length());
            stub.setString(key.trim(),value);
          }
       }
     }
     return stub;
 }
 public  static Map keyValueToMap(String keyValueStr,String distance,String keyValueDistance){
     if(distance == null || "".equals(distance.trim())) distance = ",";//默认为逗号间隔
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
 public  static void mapToNodeValue(Map map,TreeNodeStubObject treeNodeStub,String nodeKeyName,String distance,String keyValueDistance){
     if(distance == null || "".equals(distance.trim())) distance = ",";//默认为逗号间隔
     if(keyValueDistance == null || "".equals(keyValueDistance.trim()))
          keyValueDistance = "=";
     StringBuffer sb = new StringBuffer();
     String key = "";
     String value = "";
     Object obj = null;
     Iterator it = map.keySet().iterator();
     while(it.hasNext()){
       key = it.next().toString();
       obj = map.get(key);
       if(obj == null) value = "";
       else value = obj.toString();
       if(!"".equals(value))
         sb.append(key).append(keyValueDistance).append(value).append(distance);
      }
      treeNodeStub.setString(nodeKeyName,sb.toString());

 }



}
