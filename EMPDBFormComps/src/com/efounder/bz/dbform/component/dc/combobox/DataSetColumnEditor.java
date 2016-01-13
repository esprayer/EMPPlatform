package com.efounder.bz.dbform.component.dc.combobox;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;

import com.efounder.action.*;
import com.efounder.builder.base.data.*;
import com.efounder.bz.dbform.component.*;
import com.efounder.bz.dbform.component.dc.*;
import com.efounder.bz.dbform.datamodel.*;
import com.efounder.eai.ide.*;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2009</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class DataSetColumnEditor extends JPanel implements DataSetListener,FormComponent,DMComponent,DMColComponent {
  /**
   *
   * @return FormContainer
   */
  public FormContainer getFormContainer() {
    return null;
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
   */
  protected JTextComponentColumn textComponentColumn = null;
  private int labelWidth = 20;
  public int getLabelWidth() {
    return labelWidth;
  }
  public void setLabelWidth(int labelWidth) {
    this.labelWidth = labelWidth;
    Dimension dim = this.lbIcon.getPreferredSize();
    dim.width = labelWidth;
    lbIcon.setPreferredSize(dim);
  }
  /**
   *
   */
  private String fieldIcon;
  /**
   *
   */
  private String fieldLabel;
  /**
   *
   * @return String
   */
  public String getFieldLabel() {
    return fieldLabel;
  }
  /**
   *
   * @param fieldLabel String
   */
  public void setFieldLabel(String fieldLabel) {
    this.fieldLabel = fieldLabel;
    if ( this.fieldLabel != null ) this.fieldLabel = this.fieldLabel.trim();
    this.lbIcon.setText(fieldLabel);
  }
  /**
   *
   * @return String
   */
  public String getFieldIcon() {
    return fieldIcon;
  }
  /**
   *
   * @param strIcon String
   */
  public void setFieldIcon(String strIcon) {
    this.fieldIcon = strIcon;
    this.lbIcon .setIcon(ExplorerIcons.getExplorerIcon(fieldIcon));
  }
  /**
   *
   */
  protected void initTextComponent() {
    textComponentColumn = JTextComponentColumn.getJTextComponentColumn(this.valueField);
    valueField.addFocusListener(textComponentColumn);
  }



  /**
   *
   */
  public DataSetColumnEditor() {
    try {
      jbInit();
      initTextComponent();
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
  }
  /**
   *
   * @throws Exception
   */
  private void jbInit() throws Exception {
    this.setLayout(borderLayout1);
    pnEdit.setLayout(borderLayout2);
    pnIcon.setLayout(borderLayout3);
    pnTools.setLayout(flowLayout2);
    flowLayout2.setAlignment(FlowLayout.RIGHT);
    flowLayout2.setHgap(0);
    flowLayout2.setVgap(0);
    pnIcon.setOpaque(false);
    pnTools.setOpaque(false);
    super.setOpaque(false);
//    pnEdit.setOpaque(false);
    this.add(pnIcon, java.awt.BorderLayout.WEST);
    this.add(pnTools, java.awt.BorderLayout.EAST); //    this.setBackground();
    this.add(pnEdit, java.awt.BorderLayout.CENTER);
    pnEdit.add(valueField, java.awt.BorderLayout.CENTER);
    pnIcon.add(lbIcon, java.awt.BorderLayout.CENTER);
    pnEdit.setBorder(valueField.getBorder());
    valueField.setBorder(null);
  }
  /**
   *
   * @param v boolean
   */
  public void setEditOpaque(boolean v) {
    pnEdit.setOpaque(v);
    this.valueField.setOpaque(v);
  }
  /**
   *
   * @return boolean
   */
  public boolean isEditOpaque() {
    return pnEdit.isOpaque();
  }
  /**
   *
   * @param b Border
   */
  public void setEditBorder(Border b) {
    pnEdit.setBorder(b);
  }
  /**
   *
   * @return Border
   */
  public Border getEditBorder() {
    return pnEdit.getBorder();
  }
  /**
   *
   */
  protected DataSetComponent dataSetComponent = null;
  /**
   *
   * @return DataSetComponent
   */
  public DataSetComponent getDataSetComponent() {
    return textComponentColumn.getDataSetComponent();
  }
  /**
   *
   * @param dsc DataSetComponent
   */
  public void setDataSetComponent(DataSetComponent dsc) {
    if ( dataSetComponent != dsc ) {
      if ( dataSetComponent != null ) dataSetComponent.removeDMComponent(this);
      dataSetComponent =  dsc;
      if ( dataSetComponent != null ) dataSetComponent.insertDMComponent(this);
    }
    textComponentColumn.setDataSetComponent(dsc);
  }
  /**
   *
   */
  protected String dataSetID = null;
  /**
   *
   * @param dataSetID String
   */
  public void setDataSetID(String dataSetID) {
    textComponentColumn.setDataSetID(dataSetID);
  }
  /**
   *
   * @return String
   */
  public String getDataSetID() {
    return textComponentColumn.getDataSetID();
  }
  /**
   *
   * @return String
   */
  public String getDataSetColID() {
    return textComponentColumn.getDataSetColID();
  }
  /**
   *
   * @param dataSetColID String
   */
  public void setDataSetColID(String dataSetColID) {
    textComponentColumn.setDataSetColID(dataSetColID);
  }
  /**
   *
   * @return String
   */
  public String getInternalDataSetID() {
    return textComponentColumn.getInternalDataSetID();
  }
  /**
   *
   * @param dataSetID String
   */
  public void setInternalDataSetID(String dataSetID) {
    textComponentColumn.setInternalDataSetID(dataSetID);
  }
  /**
   *
   * @return EFDataSet
   */
  public EFDataSet getDataSet() {
    return textComponentColumn.getDataSet();
  }
  /**
   *
   * @param dataSet EFDataSet
   */
  public void setDataSet(EFDataSet ds) {
    textComponentColumn.setDataSet(ds);
  }
  /**
   *
   * @return boolean
   */
  public boolean getMetaData() {
    return textComponentColumn.getMetaData();
  }
  /**
   *
   * @param v boolean
   */
  public void setMetaData(boolean v) {
    textComponentColumn.setMetaData(v);
  }
  /**
   *
   * @param viewDataSetID String
   */
  public void   setViewDataSetID(String viewDataSetID) {
    textComponentColumn.setViewDataSetID(viewDataSetID);
  }
  /**
   *
   * @return String
   */
  public String getViewDataSetID() {
    return textComponentColumn.getViewDataSetID();
  }
  /**
   *
   * @param viewDataSetColID String
   */
  public void   setViewDataSetColID(String viewDataSetColID) {
    this.textComponentColumn.setViewDataSetColID(viewDataSetColID);
  }
  /**
   *
   * @return String
   */
  public String getViewDataSetColID() {
    return textComponentColumn.getViewDataSetColID();
  }
  /**
   *
   * @param e DataSetEvent
   */
  public void dataSetChanged(DataSetEvent e) {
//    textComponentColumn.dataSetChanged(e);
  }
  /**
   *
   * @param valueDataSetColID String
   */
  public void setValueDataSetColID(String valueDataSetColID) {
    textComponentColumn.setValueDataSetColID(valueDataSetColID);
  }
  /**
   *
   * @return String
   */
  public String getValueDataSetColID() {
    return textComponentColumn.getValueDataSetColID();
  }
  /**
   *
   * @param dataSetComponentEvent DataSetComponentEvent
   */
  public void dataSetComponentListener(DataSetComponentEvent
                                       dataSetComponentEvent) {
    if ( dataSetComponentEvent.getEventType() == DataSetComponentEvent.DSC_EVENT_START_EDIT ) {
      if ( this.helpButton != null ) helpButton.setEnabled(true);
      if ( this.editButton != null ) editButton.setEnabled(true);
      if ( this.popupButton != null ) popupButton.setEnabled(true);
    }
    if ( dataSetComponentEvent.getEventType() == DataSetComponentEvent.DSC_EVENT_STOP_EDIT ||
         dataSetComponentEvent.getEventType() == DataSetComponentEvent.DSC_EVENT_END_EDIT ) {
      if ( this.helpButton != null ) helpButton.setEnabled(false);
      if ( this.editButton != null ) editButton.setEnabled(false);
      if ( this.popupButton != null ) popupButton.setEnabled(false);
      return;
    }
  }
  /**
   *
   * @return ESPRowSet
   */
  public ESPRowSet getMainRowSet() {
    return textComponentColumn.getMainRowSet();
  }
  /**
   *
   * @return boolean
   */
  public boolean isUserInternalDataSetID() {
    return textComponentColumn.isUserInternalDataSetID();
  }
  /**
   *
   * @param v boolean
   */
  public void setUserInternalDataSetID(boolean v) {
    textComponentColumn.setUserInternalDataSetID(v);
  }
  /**
   *
   * @return String
   */
  public String getFkeyID() {
    return textComponentColumn.getFkeyID();
  }
  /**
   *
   * @param fkey String
   */
  public void setFkeyID(String fkey) {
    textComponentColumn.setFkeyID(fkey);
  }
  /**
   *
   * @return String
   */
  public String getRlglID() {
    return textComponentColumn.getRlglID();
  }
  /**
   *
   * @param rlglID String
   */
  public void setRlglID(String rlglID) {
    textComponentColumn.setRlglID(rlglID);
  }
  /**
   *
   * @return boolean
   */
  public boolean isEmbedCellEditor() {
    return textComponentColumn.isEmbedCellEditor();
  }
  /**
   *
   * @param v boolean
   */
  public void setEmbedCellEditor(boolean v) {
    textComponentColumn.setEmbedCellEditor(v);
  }












  /**
   *
   */
  protected boolean allowHelp  = false;
  /**
   *
   * @return boolean
   */
  public boolean isAllowHelp() {
    return allowHelp;
  }
  /**
   *
   * @param allowHelp boolean
   */
  public void setAllowHelp(boolean allowHelp) {
    this.allowHelp = allowHelp;
    buildButton();

  }
  /**
   *
   */
  protected void createHelpButton() {
    if ( helpButton == null ) {
      Action helpAction = new ActiveObjectAction(this, this, "helpDICT", "",
                                                 '0', "",
                                                 ExplorerIcons.
                                                 getExplorerIcon("oicons/help.png"));
      helpButton = ActionButton.getActionButton(this, helpAction);
    }
    pnTools.add(helpButton);
  }
  /**
   *
   */
  protected void removeHelpButton() {
    if ( helpButton != null ) {
      pnTools.remove(helpButton);
    }
  }
  /**
   *
   */
  protected JButton helpButton = null;












  /**
   *
   */
  protected boolean allowEdit  = false;
  /**
   *
   * @return boolean
   */
  public boolean isAllowEdit() {
    return allowEdit;
  }
  /**
   *
   * @param allowEdit boolean
   */
  public void setAllowEdit(boolean allowEdit) {
    this.allowEdit = allowEdit;
    buildButton();
  }
  /**
   *
   */
  protected void createEditButton() {
    if ( editButton == null ) {
      Action editAction = new ActiveObjectAction(this, this, "editDICT", "",
                                                 '0', "",
                                                 ExplorerIcons.
                                                 getExplorerIcon("fmisres/ZW_PZ_EDIT.gif"));
      editButton = ActionButton.getActionButton(this, editAction);
    }
    pnTools.add(editButton);
  }
  /**
   *
   */
  protected void removeEditButton() {
    if ( editButton != null ) {
      pnTools.remove(editButton);
    }
  }
  /**
   *
   */
  protected JButton editButton = null;











  protected void buildButton() {
    if ( this.allowHelp ) {
      this.createHelpButton();
    } else {
      this.removeHelpButton();
    }
    if ( this.allowEdit ) {
      this.createEditButton();
    } else {
      this.removeEditButton();
    }
    if ( this.allowPopup ) {
      this.createPopupButton();
    } else {
      this.removePopupButton();
    }
  }


  /**
   *
   */
  protected boolean allowPopup = false;
  /**
   *
   * @return boolean
   */
  public boolean isAllowPopup() {
    return allowPopup;
  }
  /**
   *
   * @param allowPopup boolean
   */
  public void setAllowPopup(boolean allowPopup) {
    this.allowPopup = allowPopup;
    buildButton();
  }

  /**
   *
   */
  protected void createPopupButton() {
    if ( popupButton == null ) {
      Action popupAction = new ActiveObjectAction(this, this, "popupDICT", "",
                                                 '0', "",
                                                 ExplorerIcons.
                                                 getExplorerIcon("oicons/extras/down.gif"));
      popupButton = ActionButton.getActionButton(this, popupAction);
    }
    this.pnEdit.add(popupButton,BorderLayout.EAST);
  }
  /**
   *
   */
  protected void removePopupButton() {
    if ( popupButton != null ) {
      pnEdit.remove(popupButton);
    }
  }
  /**
   *
   */
  protected JButton popupButton = null;
  BorderLayout borderLayout1 = new BorderLayout();
  JPanel pnIcon = new JPanel();
  JLabel lbIcon = new JLabel();
  JPanel pnTools = new JPanel();
  JPanel pnEdit = new JPanel();
  BorderLayout borderLayout2 = new BorderLayout();
  JTextField valueField = new JTextField();
  FlowLayout flowLayout2 = new FlowLayout();
  BorderLayout borderLayout3 = new BorderLayout();
  /**
   *
   */
  protected int horizontalAlignment = -1;
  /**
   *
   * @return int
   */
  public int getHorizontalAlignment() {
    return horizontalAlignment;
  }
  /**
   *
   * @param horizontalAlignment int
   */
  public void setHorizontalAlignment(int horizontalAlignment) {
    this.horizontalAlignment = horizontalAlignment;
  }
  /**
   *
   */
  protected String numberFormat = null;
  /**
   *
   * @return String
   */
  public String getNumberFormat() {
    return numberFormat;
  }
  /**
   *
   * @param numberFormat String
   */
  public void setNumberFormat(String numberFormat) {
    this.numberFormat = numberFormat;
  }
  /**
   *
   */
  protected String dateFormat = null;
  /**
   *
   * @return String
   */
  public String getDateFormat() {
    return this.dateFormat;
  }
  /**
   *
   * @param dateFormat String
   */
  public void setDateFormat(String dateFormat) {
    this.dateFormat = dateFormat;
  }
  /**
   *
   */
  protected String formulaOne = null;
  /**
   *
   * @return String
   */
  public String getFormulaOne() {
    return this.formulaOne;
  }
  /**
   *
   * @param formulaOne String
   */
  public void setFormulaOne(String formulaOne) {
    this.formulaOne = formulaOne;
  }
  //编辑掩码
  String editMask;
  public String getEditMask() {
	  // TODO Auto-generated method stub
	  return editMask;
  }
  public void setEditMask(String editMask) {
	  // TODO Auto-generated method stub
	  this.editMask = editMask;
  }
}
