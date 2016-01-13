package com.efounder.bz.dbform.component;

import javax.swing.*;

import com.efounder.comp.*;
import com.efounder.service.script.*;
import java.awt.BorderLayout;
import com.efounder.bz.dbform.component.popup.PopupMultiCheckBox;

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
public class FormPopupComboBox extends JComponentComboBox implements
    FormComponent, Scriptable {

  /**
   *
   */
  public FormPopupComboBox() {
  }

  /**
   * 已选择的是否可以编辑
   * @param enabled boolean
   */
  public void setSelectedEnabled(boolean enabled) {
  }

  public void initScript(ScriptManager scriptManager) {
  }

  public void finishScript(ScriptManager scriptManager) {
  }

  public ScriptObject getScriptObject() {
	    return null;
  }

  public Object getScriptKey() {
    return null;
  }

  public Object getScriptInstance() {
    return null;
  }

  public ScriptManager getScriptManager() {
	    return null;
  }

  public FormContainer getFormContainer() {
    return null;
  }

  public JComponent getJComponent() {
    return this;
  }

}
