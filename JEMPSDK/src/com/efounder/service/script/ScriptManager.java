package com.efounder.service.script;

import org.openide.util.*;
import com.efounder.eai.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public abstract class ScriptManager {
  protected static final String DEFAULT_SCRIPT = "BeanShell";
  /**
   *
   */
  protected ScriptManager() {
  }
  /**
   *
   * @return ScriptManager
   */
  public final static ScriptManager getInstance() {
    return getInstance(DEFAULT_SCRIPT);
  }
  /**
   *
   */
  protected java.util.Map scriptCallbackMap = null;
  /**
   *
   * @param scriptable Scriptable
   */
  public void addScriptable(Scriptable scriptable) {
    if ( scriptCallbackMap == null ) scriptCallbackMap = new java.util.HashMap();
    scriptCallbackMap.put(scriptable.getScriptKey(),scriptable);
  }
  /**
   *
   * @param scriptable Scriptable
   */
  public void removeScriptable(Scriptable scriptable) {
    if ( scriptCallbackMap == null ) return;
    scriptCallbackMap.remove(scriptable.getScriptKey());
  }
  /**
   *
   * @return ScriptManager
   */
  public final static ScriptManager getInstance(String scriptID) {
    ScriptManager scriptManager = null;
    scriptManager = (ScriptManager) Lookup.getDefault().lookups(ScriptManager.class,scriptID);
    scriptManager.initScriptManager();
    return scriptManager;
  }
  /**
   *
   * @param scriptable Scriptable
   * @param function String
   * @throws Exception
   */
  public final Object runFunction(Scriptable scriptable,String function) throws Exception {
    if ( scriptable == null ) return null;
    ScriptObject scriptObject = scriptable.getScriptObject();
    if ( scriptObject == null ) return null;
    Object script = scriptObject.getScript(ScriptObject.SCRIPT_TYPE_FUNC,function);
    if ( script != null ) {
      return runScriptObject(scriptable, scriptObject, ScriptObject.SCRIPT_TYPE_FUNC,function, script);
//      // ��ȡ����ֵ
//      Object value = getReturnObject();
//      // ����ֵ
//      clearReturnObject();
//      return value;
    }
    return null;
  }
  protected abstract Object getReturnObject();
  protected abstract void clearReturnObject();
  /**
   *
   * @param scriptable Scriptable
   * @param event String
   * @throws Exception
   */
  public final Object invokeEvent(Scriptable scriptable,String event) throws Exception {
    ScriptObject scriptObject = scriptable.getScriptObject();
    if ( scriptObject == null ) return null;
    Object script = scriptObject.getScript(ScriptObject.SCRIPT_TYPE_EVENT,event);
    if ( script != null )
      return runScriptObject(scriptable,scriptObject,ScriptObject.SCRIPT_TYPE_EVENT,event,script);
    return null;
  }
  /**
   *
   * @param scriptable Scriptable
   * @param script Object
   */
  protected final Object runScriptObject(Scriptable scriptable,ScriptObject scriptObject,
                                       String scriptType,String scriptName,Object script) throws Exception {
    initScript();
    scriptable.initScript(this);
    try {
      return runScript(scriptable,scriptObject,scriptType,scriptType,script);
    } finally {
      scriptable.finishScript(this);
      finishScript();
    }
  }
  /**
   *
   * @param scriptManager ScriptManager
   */
  protected final void initScript() {
    if ( scriptCallbackMap == null ) return;
    Object[] scripts = scriptCallbackMap.values().toArray();
    Scriptable scriptable = null;
    for(int i=0;i<scripts.length;i++) {
      scriptable = (Scriptable)scripts[i];
      scriptable.initScript(this);
    }
  }
  /**
   *
   * @param scriptManager ScriptManager
   */
  protected final void finishScript() {
    if ( scriptCallbackMap == null ) return;
    Object[] scripts = scriptCallbackMap.values().toArray();
    Scriptable scriptable = null;
    for(int i=0;i<scripts.length;i++) {
      scriptable = (Scriptable)scripts[i];
      scriptable.finishScript(this);
    }
  }
  /**
   *
   */
  protected abstract void initScriptManager();
  /**
   *
   * @param key String
   * @param value Object
   */
  public abstract void setObject(String key,Object value);
  /**
   *
   * @param key String
   */
  public abstract void removeObject(String key);
  /**
   *
   * @param key String
   * @param value Object
   * @return Object
   */
  public abstract Object getObject(String key,Object value);
  /**
   *
   * @param scriptable Scriptable
   * @param script Object
   */
  protected abstract Object runScript(Scriptable scriptable,ScriptObject scriptObject,
                                       String scriptType,String scriptName,Object script) throws Exception ;
  /**
   *
   * @param scriptText String
   * @throws Exception
   */
  public abstract Object runScriptText(String scriptText) throws Exception;
}
