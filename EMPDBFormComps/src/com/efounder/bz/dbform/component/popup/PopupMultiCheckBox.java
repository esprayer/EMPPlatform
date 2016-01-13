package com.efounder.bz.dbform.component.popup;

import java.util.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.plaf.basic.*;

import com.borland.jbcl.layout.*;
import com.core.xml.*;
import com.efounder.comp.*;
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
public class PopupMultiCheckBox extends JPanel implements PopupComponent,
     Scriptable, ActionListener {

  /**
   *
   */
  protected JCheckBox[] checkboxs;
  protected StubObject[] sobjects;
  protected ComboPopup comboPopup = null;
  protected JComboBox comboBox = null;
  protected BorderLayout borderLayout1 = new BorderLayout();
  protected JPanel jPanel2 = new JPanel();
  protected VerticalFlowLayout verticalFlowLayout1 = new VerticalFlowLayout();
  protected JButton btnok = new JButton();
  protected JPanel jPanel4 = new JPanel(new GridLayout());
  protected JPanel jPanel1 = new JPanel(new FlowLayout(0));
  protected JPanel jPanel3 = new JPanel(new FlowLayout(2));
  protected JCheckBox jCheckBox1 = new JCheckBox();

  /**
   *
   */
  public PopupMultiCheckBox() {
    this(null);
  }

  /**
   *
   * @param object Object[]
   */
  public PopupMultiCheckBox(StubObject[] object) {
    try {
      jbInit();
      setMultiCheckBox(object);
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  /**
   *
   * @param object StubObject[]
   */
  public void setMultiCheckBox(StubObject[] object) {
    if (object == null || object.length == 0) return;
    sobjects = object;
    checkboxs = new JCheckBox[object.length];
    for (int i = 0; i < object.length; i++) {
      JCheckBox checkbox = new JCheckBox(object[i].getCaption());
      checkbox.setIcon(object[i].getIcon());
      checkbox.addActionListener(this);
      jPanel2.add(checkbox);
      checkboxs[i] = checkbox;
    }
  }

  /**
   *
   */
  protected void jbInit() {
    this.setLayout(borderLayout1);
    jPanel2.setLayout(verticalFlowLayout1);
    btnok.setPreferredSize(new Dimension(50, 23));
    btnok.setText("确定");
    btnok.addActionListener(this);
    verticalFlowLayout1.setVgap(0);
//    jCheckBox1.setPreferredSize(new Dimension(60, 23));
    jCheckBox1.setText("全部");
    jCheckBox1.addActionListener(this);
    this.add(jPanel4, java.awt.BorderLayout.SOUTH);
    jPanel4.add(jPanel1);
    jPanel4.add(jPanel3);
    jPanel1.add(jCheckBox1);
    jPanel3.add(btnok);
    this.add(jPanel2, java.awt.BorderLayout.CENTER);
  }

  /**
   *
   * @return JComponent
   */
  public JComponent getJComponent() {
    return this;
  }

  /**
   *
   * @param item Object
   */
  public void setSelectedItem(Object item) {
  }


  /**
   *
   * @param enabled boolean
   */
  public void setSelectedEnabled(boolean enabled) {
    for (int i = 0; checkboxs != null && i < checkboxs.length; i++) {
      if (checkboxs[i].isSelected())
        checkboxs[i].setEnabled(enabled);
    }
    jCheckBox1.setEnabled(enabled);
  }

  /**
   *
   * @param enabled boolean
   */
  public void setItemSelectEnabled(boolean enabled) {
    for (int i = 0; checkboxs != null && i < checkboxs.length; i++) {
      checkboxs[i].setEnabled(enabled);
    }
    jCheckBox1.setEnabled(enabled);
  }

  /**
   *
   * @return Object
   */
  public Object getSelectedItem() {
    StringBuffer sb = new StringBuffer();
    StubObject[] sos = getSelectedItems();
    for (int i = 0; sos != null && i < sos.length; i++) {
      sb.append(sos[i].getCaption());
      if (i < sos.length - 1)
        sb.append(";");
    }
    return sb.toString();
  }

  /**
   *
   * @return Object
   */
  public StubObject[] getSelectedItems() {
    java.util.List result = new ArrayList();
    for (int i = 0; checkboxs != null && i < checkboxs.length; i++) {
      if (checkboxs[i].isSelected())
        result.add(sobjects[i]);
    }
    StubObject[] sos = new StubObject[result.size()];
    System.arraycopy(result.toArray(), 0, sos, 0, sos.length);
    return sos;
  }

  /**
   *
   * @param items StubObject[]
   */
  public void setSelectedItems(StubObject[] items) {
    for (int i = 0; sobjects != null && i < sobjects.length; i++) {
      if (Arrays.asList(items).contains(sobjects[i]))
        checkboxs[i].setSelected(true);
    }
    this.ok();
  }

  /**
   *
   * @param ids String[]
   */
  public void setSelectedItems(String[] ids) {
    for (int i = 0; sobjects != null && i < sobjects.length; i++) {
      if (Arrays.asList(ids).contains((String)sobjects[i].getID()))
        checkboxs[i].setSelected(true);
    }
    this.ok();
  }

  /**
   *
   * @return ListCellRenderer
   */
  public ListCellRenderer getListCellRenderer() {
    return null;
  }

  /**
   *
   * @param box JComboBox
   */
  public void setJComboBox(JComboBox box) {
    comboBox = box;
  }

  public void setComboPopup(ComboPopup comboPopup) {
    this.comboPopup = comboPopup;
  }

  /**
   *
   * @return boolean
   */
  public boolean isNotUseScrollPane() {
    return false;
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

  /**
   *
   * @param e ActionEvent
   */
  public void actionPerformed(ActionEvent e) {
    if (e.getSource() == this.jCheckBox1) {
      select();
    } else if (e.getSource() == this.btnok) {
      ok();
    } else if (e.getSource() instanceof JCheckBox) {
      this.setSelectedItemss();
    }
  }

  /**
   *
   */
  private void select() {
    boolean select = jCheckBox1.isSelected();
    for (int i = 0; i < checkboxs.length; i++) {
      checkboxs[i].setSelected(select);
    }
  }

  /**
   *
   */
  private void ok() {
    if (comboPopup != null) {
//      comboBox.setSelectedItem(this.getSelectedItem());
//      ( (JComponentComboBox) comboBox).setSelectedItems(getSelectedItems());
      this.setSelectedItemss();
      comboPopup.hide();
    }
  }

  /**
   *
   */
  protected void setSelectedItemss() {
    if (comboPopup != null) {
      comboBox.setSelectedItem(this.getSelectedItem());
      ( (JComponentComboBox) comboBox).setSelectedItems(getSelectedItems());
    }
  }

}
