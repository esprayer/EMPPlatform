package com.efounder.bz.dbform.action;

import javax.swing.*;

import com.efounder.bz.dbform.container.*;
import com.efounder.bz.dbform.datamodel.*;
import com.efounder.bz.dbform.component.ActionProvider;
import com.efounder.bz.dbform.component.FormComponent;
import com.efounder.service.script.Scriptable;

/**
 *
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
public class ActionAdapter extends ActionComponentAdapter implements ActionProvider,Comparable {
  /**
   *
   */
  public ActionAdapter() {
  }

  /**
   *
   */
  protected FormComponent formComponent = null;
  /**
   *
   * @return FormComponent
   */
  public FormComponent getContextComponent() {
    return formComponent;
  }
  /**
   *
   * @param formComp FormComponent
   */
  public void setContextComponent(FormComponent formComp) {
    if ( formComponent != formComp ) {
      if ( formComponent != null ) removeContextAction(formComponent);
      formComponent = formComp;
      if ( formComponent != null ) addContextAction(formComponent);
    }
  }
  /**
   *
   * @param formComp FormComponent
   */
  protected void removeContextAction(FormComponent formComp) {
    java.util.List actionList = (java.util.List)formComp.getJComponent().getClientProperty("contextActionList");
    if ( actionList == null ) return;
    actionList.remove(this);
  }
  /**
   *
   * @param formComp FormComponent
   */
  protected void addContextAction(FormComponent formComp) {
    java.util.List actionList = (java.util.List)formComp.getJComponent().getClientProperty("contextActionList");
    if ( actionList == null ) {
      actionList = new java.util.ArrayList();
      formComp.getJComponent().putClientProperty("contextActionList",actionList);
    }
    if ( actionList.indexOf(this) == -1 ) // ���û�����
      actionList.add(this);
  }
//  /**
//   *
//   */
//  protected FormCanvas formCanvas = null;
//  /**
//   *
//   * @return FormCanvas
//   */
//  public FormCanvas getFormCanvas() {
//    return formCanvas;
//  }
//  /**
//   *
//   * @param formCanvas FormCanvas
//   */
//  public void setFormCanvas(FormCanvas formCanvas) {
//    if ( this.formCanvas != null ) this.formCanvas.removeAction(this);
//    this.formCanvas = formCanvas;
//    if ( this.formCanvas != null ) this.formCanvas.insertAction(this);
//  }
  /**
   *
   * @param frame Object
   * @param nodeArray Object[]
   * @return ActionGroup
   */
  public Action getAction(Object frame, Object[] nodeArray) {
    return null;
  }
  /**
   *
   * @param o Object
   * @return int
   */
  public int compareTo(Object co) {
    if ( co == null) return 1;
    String thisText = this.getActionText();
    if ( thisText == null ) thisText = "0";
    ActionAdapter aa = (ActionAdapter)co;
    String otherText = aa.getActionText();
    if ( otherText == null ) otherText = "0";
    return thisText.compareTo(otherText);
  }

}
