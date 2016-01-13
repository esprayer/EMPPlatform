package com.efounder.plugin;

import com.core.xml.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class PluginManager {
  /**
   *
   */
  public PluginManager() {
  }
  /**
   *
   * @param key String
   * @return List
   */
  public static java.util.List loadPlugins(String key) {
    return loadPlugins(key,true);
  }
  /**
   *
   * @param key String
   * @return List
   */
  public static java.util.List loadSingleInstance(String key) {
    java.util.List pluginList = new java.util.ArrayList();
    String Clazz = null;
    StubObject stubObject = null;Class processClass = null;
    // 从装备清单中获取paintProcessList
    java.util.List paintProcessList = PackageStub.getContentVector(key);
    if ( paintProcessList == null ) return pluginList;
    for(int i=0;i<paintProcessList.size();i++) {
      // 获取StubObject对象
      stubObject = (StubObject)paintProcessList.get(i);
      // 获取Clazz
      Clazz = stubObject.getString("clazz",null);
      if ( Clazz == null || "".equals(Clazz) ) continue;
      try {
        //
        processClass = Class.forName(Clazz);     // 获取报表数据处理类
        // 设置插件类
        stubObject.setObject("pluginClass",processClass);
//        Object pluginObject = processClass.newInstance();
        // 调用相应的方法，获取唯一实列
        Object[] args = {stubObject,processClass};
        Object pluginObject = JBOFClass.CallClassMethod(processClass,"getInstance",args);
        if ( pluginObject != null ) {
          Object[] arg = {stubObject};
          JBOFClass.CallObjectMethod(pluginObject,"setStubObject",arg);
        }
        // 设置插件对象
        stubObject.setObject("pluginObject", pluginObject);
        // 增加到列表中
        pluginList.add(stubObject);
      } catch ( Exception e ) {
        e.printStackTrace();
      }
    }
    return pluginList;
  }
  /**
   *
   */
  public static java.util.List loadPlugins(String key,boolean initObject) {
    java.util.List pluginList = new java.util.ArrayList();
    String Clazz = null;
    StubObject stubObject = null;Class processClass = null;
    // 从装备清单中获取paintProcessList
    java.util.List paintProcessList = PackageStub.getContentVector(key);
    if ( paintProcessList == null ) return pluginList;
    for(int i=0;i<paintProcessList.size();i++) {
      // 获取StubObject对象
      stubObject = (StubObject)paintProcessList.get(i);
      // 获取Clazz
      Clazz = stubObject.getString("clazz",null);
      if ( Clazz == null || "".equals(Clazz) ) continue;
      try {
        //
        processClass = Class.forName(Clazz);     // 获取报表数据处理类
        // 设置插件类
        stubObject.setObject("pluginClass",processClass);
        if ( initObject ) {
          Object pluginObject = processClass.newInstance();
          // 设置插件对象
          stubObject.setObject("pluginObject", pluginObject);
        }
        // 增加到列表中
        pluginList.add(stubObject);
      } catch ( Exception e ) {
        e.printStackTrace();
      }
    }
    return pluginList;
  }
  /**
   *
   * @param stubList List
   * @param pluginKey String
   * @return StubObject
   */
  public static StubObject getPluginStubObject(java.util.List stubList,String pluginKey) {
    StubObject stub = null;
    if ( stubList == null ) return null;
    for(int i=0;i<stubList.size();i++) {
      stub = (StubObject)stubList.get(i);
      if ( pluginKey.equals(stub.getID()) ) return stub;
    }
    return null;
  }
  /**
   *
   * @param stubObject StubObject
   * @return Object
   */
  public static Object getPluginObject(StubObject stubObject) {
    return stubObject.getObject("pluginObject", null);
  }
  /**
   *
   * @param stubObject StubObject
   * @return Object
   */
  public static Object createPluginObject(StubObject stubObject) {
    Class processClass = null;
    processClass = (Class)stubObject.getObject("pluginClass",null);
    if ( processClass != null ) {
      try {
        return processClass.newInstance();
      } catch ( Exception e ) {
        return null;
      }
    }
    return null;
  }
}
