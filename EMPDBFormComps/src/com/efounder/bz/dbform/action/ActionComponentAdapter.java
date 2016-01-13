package com.efounder.bz.dbform.action;

import com.efounder.bz.dbform.datamodel.*;
import javax.swing.Action;
import java.awt.event.ActionEvent;
import com.efounder.service.script.Scriptable;
import com.efounder.service.script.ScriptManager;
import com.efounder.service.script.ScriptObject;
import org.openide.ErrorManager;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2008</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public abstract class ActionComponentAdapter extends DataComponentAdapter implements Scriptable,ActionComponent {
  /**
   *
   */
  public ActionComponentAdapter() {
  }
  /**
   *
   */
  protected String actionIcon = null;
  /**
   *
   * @return String
   */
  public String getActionIcon() {
    return actionIcon;
  }
  /**
   *
   * @param ai String
   */
  public void setActionIcon(String ai) {
    this.actionIcon = ai;
  }
  /**
   *
   */
  protected String actionText = null;
  /**
   *
   * @return String
   */
  public String getActionText() {
    return actionText;
  }
  /**
   *
   * @param at String
   */
  public void setActionText(String at) {
    this.actionText = at;
  }
  /**
   *
   * @param frame Object
   * @param nodeArray Object[]
   * @return ActionComponent
   */
  public Action getAction(ActionComponent actionProvider,Object frame, Object[] nodeArray) {
    return null;
  }
  /**
   *
   * @param actionObject Object
   * @param nodeArray Object[]
   * @param action Action
   * @return boolean
   */
  public boolean canAccept(ActionComponent actionProvider,Object actionObject, Object[] nodeArray) {
    String[] paramNames  = {"actionComponent","actionObject","nodeArray"};
    Object[] paramValues = {this,actionObject,nodeArray};
    Boolean res = null;
//    try {
//       res = (Boolean)ScriptUtil.runComponentFunction(this, "canAccept",
//                                       paramNames,
//                                       paramValues);
//       if ( res == null ) res = true;
//     } catch ( Exception ex ) {
////       ErrorManager.getDefault().notify(ex);
//    	 ex.printStackTrace();
//     }
     return res;
  }
  /**
   *
   * @param actionObject Object
   * @param nodeArray Object[]
   * @param action Action
   * @return Boolean
   */
  public Boolean updateexecuteAction(Object actionObject, Object[] nodeArray, Action action) {
    String[] paramNames  = {"actionComponent","actionObject","nodeArray","action"};
    Object[] paramValues = {this,actionObject,nodeArray,action};
    Boolean res = null;
//    try {
//       res = (Boolean)ScriptUtil.runComponentFunction(this, "updateAction",
//                                       paramNames,
//                                       paramValues);
//       if ( res == null ) res = true;
//     } catch ( Exception ex ) {
////       ErrorManager.getDefault().notify(ex);
//    	 ex.printStackTrace();
//     }
     return res;
  }
  /**
   *
   * @param actionObject Object
   * @param nodeArray Object[]
   * @param action Action
   * @param actionevent ActionEvent
   */
  public void executeAction(Object actionObject, Object[] nodeArray, Action action,ActionEvent actionevent) {
    String[] paramNames  = {"actionComponent","actionObject","nodeArray","action","actionEvent"};
    Object[] paramValues = {this,actionObject,nodeArray,action,actionevent};
//    try {
//       ScriptUtil.runComponentFunction(this, "executeAction",
//                                       paramNames,
//                                       paramValues);
//     } catch ( Exception ex ) {
//       ErrorManager.getDefault().notify(ex);
//     }
  }
  /**
   *
   * @param scriptManager ScriptManager
   */
  public void initScript(ScriptManager scriptManager) {
  }
  /**
   *
   * @param scriptManager ScriptManager
   */
  public void finishScript(ScriptManager scriptManager) {
  }
  /**
   *
   * @return ScriptObject
   */
  public ScriptObject getScriptObject() {
//    return ScriptUtil.getFormFunctionObject(this);
	  return null;
  }
  /**
   *
   * @return Object
   */
  public Object getScriptKey() {
    return null;
  }
  /**
   *
   * @return Object
   */
  public Object getScriptInstance() {
    return null;
  }
  /**
   *
   * @return ScriptManager
   */
  public ScriptManager getScriptManager() {
//    return ScriptUtil.getScriptManager(this);
	  return null;
  }
}
