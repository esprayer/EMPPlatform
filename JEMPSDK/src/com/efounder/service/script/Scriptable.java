package com.efounder.service.script;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2003</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public interface Scriptable {
  /**
   *
   * @param scriptManager ScriptManager
   */
  public void initScript(ScriptManager scriptManager);
  /**
   *
   * @param scriptManager ScriptManager
   */
  public void finishScript(ScriptManager scriptManager);
  /**
   *
   * @return ScriptObject
   */
  public ScriptObject getScriptObject();
  /**
   *
   * @return Object
   */
  public Object getScriptKey();
  /**
   *
   * @return Object
   */
  public Object getScriptInstance();
  /**
   *
   * @return ScriptManager
   */
  public ScriptManager getScriptManager();
}
