package com.efounder.service.script;

import java.util.*;



/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public interface ScriptObject {
  public static final String SCRIPT_TYPE_FUNC  = "METHOD";
  public static final String SCRIPT_TYPE_EVENT = "EVENT";
  /**
   *
   * @param key Object
   * @param def Object
   * @return Object
   */
  public Object getObject(Object key,Object def);
  /**
   *
   * @param key Object
   * @param value Object
   */
  public void setObject(Object key,Object value);
  /**
   *
   * @param key Object
   * @param def Object
   * @return Object
   */
  public Object getValue(Object key,Object def);
  /**
   *
   * @param key Object
   * @param value Object
   */
  public void setValue(Object key,Object value);
  /**
   *
   * @return String
   */
  public String getScriptKey();
  public void setScriptKey(String value);
  /**
   *
   * @return String
   */
  public String getScriptInstance();
  public void   setScriptInstance(String value);
  /**
   * 获取脚本对象的
   * @return List
   */
  public List getFunctionList();
  public void setFunctionList(List list);
  /**
   *
   * @return List
   */
  public List getEventList();
  public void setEventList(List list);
  /**
   *
   * @param scriptType String
   * @return List
   */
  public List getScriptNameList(String scriptType);
  /**
   *
   * @param scriptType String
   * @param scriptNameList List
   */
  public void setScriptNameList(String scriptType,List scriptNameList);
  /**
   *
   * @param scriptType String
   * @param scriptName String
   * @return Object
   */
  public String getScript(String scriptType,String scriptName);
  /**
   *
   * @param scriptName String
   * @return String
   */
  public String getFunction(String scriptName);
  /**
   *
   * @param scriptName String
   * @return String
   */
  public String getEvent(String scriptName);
  /**
   *
   * @param scriptType String
   * @param scriptName String
   * @param script Object
   * @return Object
   */
  public void setScript(String scriptType,String scriptName,String script);
}
