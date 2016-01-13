package com.efounder.bz.dbform.component;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import org.jdesktop.swingx.*;
import org.openide.util.*;
import com.efounder.service.script.*;

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
public class FormTimer extends JXPanel implements FormComponent, Scriptable,
    ActionListener, Runnable {

  /**
   *
   */
  public FormTimer() {
    try {
      jbInit();
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  /**
   *
   */
  private RequestProcessor.Task timer = RequestProcessor.getDefault().create(this);
  private Runnable runnable = null;

  public FormContainer getFormContainer() {
    return null;
  }

  public JComponent getJComponent() {
    return this;
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

  private void jbInit() throws Exception {
    this.setLayout(borderLayout1);
    chbTimer.setText(this.getShowText());
    chbTimer.addActionListener(this);
    spTimer.getModel().setValue(Integer.valueOf(5));
    this.add(chbTimer, java.awt.BorderLayout.WEST);
    this.add(label, java.awt.BorderLayout.EAST);
    this.add(spTimer, java.awt.BorderLayout.CENTER);
    spTimer.setMinimumSize(new Dimension(50, 23));
  }

  BorderLayout borderLayout1 = new BorderLayout();
  JCheckBox chbTimer = new JCheckBox();
  JLabel label = new JLabel();
  JSpinner spTimer = new JSpinner();

  public void actionPerformed(ActionEvent e) {
    if (e.getSource() == this.chbTimer) {
      manageTimer();
    }
  }

  /**
   *
   * @return long
   */
  protected int getDelay(){
    int l = 1;
    Object value = spTimer.getValue();
    if (value instanceof Integer)
      l = ((Integer)value).intValue();
    if (l < 1) l = 1;
    return l*1000;
  }

  /**
   *
   */
  protected void manageTimer() {
    int delay = this.getDelay();
    if (chbTimer.isSelected())
      timer.schedule(delay);
    else
      timer.cancel();
  }

  /**
   *
   */
  public void run() {
    try {
      if (!getJComponent().isShowing() || !chbTimer.isSelected()) {
          timer.cancel();
          return;
      }
      runScript();
      if (runnable != null)
        runnable.run();
    } finally {
      timer.schedule(getDelay());
    }
  }

  /**
   *
   * @param runnable Runnable
   */
  public void setRunnable(Runnable runnable) {
    this.runnable = runnable;
  }

  /**
   *
   * @return Runnable
   */
  public Runnable getRunable() {
    return runnable;
  }

  /**
   * runScript
   */
  protected void runScript() {
//    try {
//      Object returnObject = ScriptUtil.runComponentFunction(getScriptManager(), this,
//          "runTask",
//          new String[] {},
//          new Object[] {});
//    } catch (Exception ex) {
//    }
  }

  /**
   *
   * @param showText String
   */
  public void setShowText(String showText) {
    this.showText = showText;
    chbTimer.setText(this.getShowText());
  }

  /**
   *
   * @param selected boolean
   */
  public void setSelected(boolean selected) {
    chbTimer.setSelected(selected);
  }

  /**
   *
   * @param val int
   */
  public void setValue(int val){
    spTimer.getModel().setValue(Integer.valueOf(val));
  }

  /**
   *
   * @return String
   */
  public String getShowText() {
    return showText;
  }

  /**
   *
   */
  private String showText = "AutoRefresh(s)";

}
