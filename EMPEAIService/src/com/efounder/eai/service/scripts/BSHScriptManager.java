package com.efounder.eai.service.scripts;

import bsh.Interpreter;

import com.efounder.service.script.*;

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
public class BSHScriptManager extends ScriptManager {
  /**
   *
   */
  public BSHScriptManager() {

  }
  protected Interpreter scriptInterpreter = null;
  /**
   *
   */
  protected void initScriptManager() {
    scriptInterpreter = new Interpreter();
  }
  /**
   *
   * @param key String
   * @param value Object
   */
  public void setObject(String key, Object value) {
    if ( scriptInterpreter != null ) {
      try {
        scriptInterpreter.set(key, value);
      } catch ( Exception e ) {
        e.printStackTrace();
      }
    }
  }
  public void removeObject(String key) {
    try {
      scriptInterpreter.unset(key);
    } catch ( Exception e ) {
      e.printStackTrace();
    }
  }
  /**
   *
   * @param key String
   * @param value Object
   * @return Object
   */
  public Object getObject(String key, Object def) {
    Object value = def;
    if ( scriptInterpreter == null ) return value;
    try {
      value = scriptInterpreter.get(key);
    } catch ( Exception e ) {
      e.printStackTrace();
    }
    return value;
  }
  protected static final String RETURN_VALUE = "returnObject";
  /**
   *
   * @return Object
   */
  protected Object getReturnObject() {
    return getObject(RETURN_VALUE,null);
  }
  /**
   *
   */
  protected void clearReturnObject() {
    removeObject(RETURN_VALUE);
//    setObject(RETURN_VALUE,null);
  }
  /**
   *
   * @param scriptable Scriptable
   * @param script Object
   */
  protected Object runScript(Scriptable scriptable,ScriptObject scriptObject,
                                       String scriptType,String scriptName,Object script) throws Exception {
    if ( scriptInterpreter == null ) return null;
    scriptInterpreter.set("scriptable",scriptable);
    scriptInterpreter.set("scriptObject",scriptObject);
    scriptInterpreter.set("scriptType",scriptType);
    scriptInterpreter.set("scriptName",scriptName);
    scriptInterpreter.eval(script.toString());
    Object value = getReturnObject();
    clearReturnObject();
    return value;
  }
  /**
   *
   * @param scriptText String
   * @throws Exception
   */
  public Object runScriptText(String scriptText) throws Exception {
    if ( scriptInterpreter == null ) return null;
    scriptInterpreter.eval(scriptText);
    Object value = getReturnObject();
    clearReturnObject();
    return value;
  }
}
