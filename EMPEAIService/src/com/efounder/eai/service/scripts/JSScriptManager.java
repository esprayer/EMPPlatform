package com.efounder.eai.service.scripts;

import com.efounder.service.script.*;
//import javax.script.*;

public class JSScriptManager extends ScriptManager {
  /**
   *
   */
  public JSScriptManager() {
  }
  /**
   *
   * @param key String
   * @param value Object
   * @return Object
   * @todo Implement this com.efounder.service.script.ScriptManager method
   */
  public Object getObject(String key, Object def) {
    Object value = def;
//    if ( scriptEngine == null ) return value;
//    try {
//      value = scriptEngine.get(key);
//    } catch ( Exception e ) {
//      e.printStackTrace();
//    }
    return value;
  }
  /**
   *
   */
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
    setObject(RETURN_VALUE,null);
  }
  /**
   *
   */
//  private ScriptEngine scriptEngine = null;
  /**
   *
   */
  protected void initScriptManager() {
//    ScriptEngineManager scriptEngineManager = new ScriptEngineManager();
//    scriptEngine = scriptEngineManager.getEngineByName("js");
  }

  /**
   *
   * @param scriptable Scriptable
   * @param scriptObject ScriptObject
   * @param scriptType String
   * @param scriptName String
   * @param script Object
   * @throws Exception
   * @todo Implement this com.efounder.service.script.ScriptManager method
   */
  protected Object runScript(Scriptable scriptable, ScriptObject scriptObject,
                           String scriptType, String scriptName, Object script) throws
      Exception {
//    if ( scriptEngine == null ) return null;
//    scriptEngine.put("scriptable",scriptable);
//    scriptEngine.put("scriptObject",scriptObject);
//    scriptEngine.put("scriptType",scriptType);
//    scriptEngine.put("scriptName",scriptName);
//    scriptEngine.eval(script.toString());
    return null;
  }

  /**
   *
   * @param key String
   * @param value Object
   */
  public void setObject(String key, Object value) {
//    if ( scriptEngine != null ) {
//      try {
//        scriptEngine.put(key, value);
//      } catch ( Exception e ) {
//        e.printStackTrace();
//      }
//    }
  }

  public void removeObject(String key) {
  }

  public Object runScriptText(String scriptText) throws Exception {
    return null;
  }
}
