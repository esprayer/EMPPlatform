package com.efounder.bz.dbform.component;

import java.math.*;
import java.text.*;
import java.util.*;

import java.awt.event.*;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.text.*;

import com.efounder.action.ActionButton;
import com.efounder.action.ActiveObjectAction;
import com.efounder.builder.base.data.*;
import com.efounder.bz.dbform.component.document.*;
import com.efounder.bz.dbform.component.popup.*;
import com.efounder.bz.dbform.datamodel.*;
import com.efounder.comp.*;
import com.efounder.eai.ide.ExplorerIcons;
import com.efounder.pub.util.*;

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
//
public class FormNumberField
    extends JComponentComboBox implements FocusListener,
    DataSetListener, FormComponent, DMComponent, DMColComponent, CellEditor {
  /**
   * �Ƿ�Ƕ��CellEditor\uFFFD\uFFFD
   */
  protected boolean embedCellEditor = false;
  /**
   *
   * @return boolean
   */
  public boolean isEmbedCellEditor() {
    return embedCellEditor;
  }

  /**
   *
   * @param v boolean
   */
  public void setEmbedCellEditor(boolean v) {
    embedCellEditor = v;
  }
  public void setArrowButton(boolean v) {
      super.setArrowButton(v);
      if(!v&&getPopupComponent() instanceof ComponentPopup){
          ComponentPopup cp=(ComponentPopup)getPopupComponent();
          this.removeMouseListener(cp.getMouseListener());
      }
    }
  //数字软键盘
  private FormPopupNumber popupnum = new FormPopupNumber(this);
  //数字输入控制
  private NumberDocument document = new NumberDocument();
  //显示软键盘(PopupMenu)
  private boolean showSoftNumber = true;
  //是否可以编辑
  private boolean editable = true;
  //是否启用输入控制，如果控制则只能输入合法的数值数据
  private boolean inputControl = true;
  //百分比显示
  private boolean showPercentage = false;
  //是否启用最大最小控制
  private boolean maxinControl = false;
  //允许输入的最大数字
  private double maxNumber = Double.MAX_VALUE;
  //允许输入的最小数字
  private double minNumber = Double.MIN_VALUE;

  /**
   *
   * @param e FocusEvent
   */
  public void focusGained(FocusEvent e) {
    if (this.embedCellEditor) {
      return;
    }

  }

  /**
   *
   * @param e FocusEvent
   */
  public void focusLost(FocusEvent e) {
    // 如果是嵌入Cell，则不处理
    if (this.embedCellEditor) {
      return;
    }
    endEdit();
  }

  /**
   *
   * @param show boolean
   */
  public void setShowSoftNumber(boolean show) {
    showSoftNumber = show;
    this.setPopHeight(200);
    this.setPopWidth(220);
    initPopupNumber();
    initPopupListener();
    if(!show&&getMouseListeners().length>0){
        this.removeMouseListener(this.getMouseListeners()[0]);
    }
  }

  /**
   *
   * @return boolean
   */
  public boolean isShowSoftNumber() {
    return this.showSoftNumber;
  }

  /**
   *
   * @param showSoftNumber boolean
   */
  public FormNumberField() {
    this(true);
  }

  /**
   *
   * @param showsoft boolean
   */
  public FormNumberField(boolean showsoft) {
    this.setShowSoftNumber(showsoft);
    this.setNumberDocument();
    ( (JTextComponent)this.getEditor().getEditorComponent()).addFocusListener(this);
}

  /**
   * 根据文本框里的输入实时更新POPUPMENU
   */
  private void initPopupListener() {
    if (!isShowSoftNumber()) {
      return;
    }
    getEditor().getEditorComponent().addKeyListener(new KeyAdapter() {
      public void keyTyped(KeyEvent e) {
        updatePopup();
      }
    });
  }

  /**
   *
   */
  public void updatePopup() {
    super.updatePopupMenu();
  }

  /**
   * setNumberDocument
   */
  private void setNumberDocument() {
    if (!isInputControl()) {
      return;
    }
    JTextComponent edr = (JTextComponent) getEditor().getEditorComponent();
    edr.setDocument(document);
  }

  /**
   * initPopupNumber
   */
  private void initPopupNumber() {
    if (isShowSoftNumber()) {
      setPopupComponent(popupnum);
    }
  }
  public PopupComponent getPopupComponent(){
      if(!isShowSoftNumber())return null;
      return super.getPopupComponent();
  }
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
  protected DataSetComponent dataSetComponent = null;
  /**
   *
   * @return DataSetComponent
   */
  public DataSetComponent getDataSetComponent() {
    return dataSetComponent;
  }

  /**
   *
   * @param dsc DataSetComponent
   */
  public void setDataSetComponent(DataSetComponent dsc) {
    if (dataSetComponent != dsc) {
      if (dataSetComponent != null) {
        dataSetComponent.removeDMComponent(this);
      }
      dataSetComponent = dsc;
      if (dataSetComponent != null) {
        dataSetComponent.insertDMComponent(this);
      }
    }
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
    this.dataSetID = dataSetID;
    if (dataSetComponent != null) {
      setDataSet(dataSetComponent.getDataSet(dataSetID));
    }
  }

  /**
   *
   * @return String
   */
  public String getDataSetID() {
    return dataSetID;
  }

  /**
   *
   */
  protected String dataSetColID = null;
  /**
   *
   * @return String
   */
  public String getDataSetColID() {
    return this.dataSetColID;
  }

  /**
   *
   * @param dataSetColID String
   */
  public void setDataSetColID(String dataSetColID) {
    this.dataSetColID = dataSetColID;
  }

  protected String internalDataSetID = null;
  /**
   *
   * @return String
   */
  public String getInternalDataSetID() {
    return internalDataSetID;
  }

  /**
   *
   * @param dataSetID String
   */
  public void setInternalDataSetID(String dataSetID) {
    this.internalDataSetID = dataSetID;
  }

  /**
   *
   */
  protected EFDataSet dataSet = null;
  /**
   *
   * @return EFDataSet
   */
  public EFDataSet getDataSet() {
    return dataSet;
  }

  /**
   *
   * @param dataSet EFDataSet
   */
  public void setDataSet(EFDataSet ds) {
    if (dataSet != ds) {
      // 清除掉事件监听器
      if (dataSet != null) {
        dataSet.removeDataSetListener(this);
      }
      dataSet = ds;
      // 增加事件监听器
      if (dataSet != null) {
        dataSet.addDataSetListener(this);
      }
      // 数据发生改变
//      this.setDataVector(null,null);
//      this.fireTableDataChanged();
    }
  }

  protected boolean metaData = false;
  /**
   *
   * @return boolean
   */
  public boolean getMetaData() {
    return metaData;
  }

  /**
   *
   * @param v boolean
   */
  public void setMetaData(boolean v) {
    metaData = v;
  }

  /**
   *
   */
  protected String viewDataSetID = null;
  /**
   *
   * @param viewDataSetID String
   */
  public void setViewDataSetID(String viewDataSetID) {
    this.viewDataSetID = viewDataSetID;
  }

  /**
   *
   * @return String
   */
  public String getViewDataSetID() {
    return viewDataSetID;
  }

  /**
   *
   */
  protected String viewDataSetColID = null;
  /**
   *
   * @param viewDataSetColID String
   */
  public void setViewDataSetColID(String viewDataSetColID) {
    this.viewDataSetColID = viewDataSetColID;
  }

  /**
   *
   * @param editable boolean
   */
  public void setEditable(boolean editable) {

    this.editable = editable;
    super.setEditable(editable);
    if (editable) {
      ( (JTextComponent)this.getEditor().getEditorComponent()).
          removeFocusListener(this);
      ( (JTextComponent)this.getEditor().getEditorComponent()).addFocusListener(this);
//          this.addFocusListener(this);
    }
    else {
      this.addFocusListener(this);
      this.removeFocusListener(this);
    }
    ( (JTextComponent)this.getEditor().getEditorComponent()).setEditable(editable);
  }

  /**
   *
   * @param inputControl boolean
   */
  public void setInputControl(boolean inputControl) {
    this.inputControl = inputControl;
    this.setNumberDocument();
  }

  /**
   *
   * @param showPercentage boolean
   */
  public void setShowPercentage(boolean showPercentage) {
    this.showPercentage = showPercentage;
  }

  /**
   *
   * @param max double
   */
  public void setMaxNumber(double max) {
    this.maxNumber = max;
    document.setMaxNumber(this.getMaxNumber());
  }

  /**
   *
   * @param minNumber double
   */
  public void setMinNumber(double minNumber) {
    this.minNumber = minNumber;
    document.setMinNumber(this.getMinNumber());
  }

  /**
   *
   * @param maxinControl boolean
   */
  public void setMaxinControl(boolean maxinControl) {
    this.maxinControl = maxinControl;
    document.setMaxinControl(isMaxinControl());
  }

  /**
   *
   * @return String
   */
  public String getViewDataSetColID() {
    return viewDataSetColID;
  }

  /**
   *
   * @return boolean
   */
  public boolean isEditable() {
    return editable;
  }

  /**
   *
   * @return boolean
   */
  public boolean isInputControl() {
    return inputControl;
  }

  /**
   *
   * @return boolean
   */
  public boolean isShowPercentage() {
    return showPercentage;
  }

  /**
   *
   * @return double
   */
  public double getMaxNumber() {
    return this.maxNumber;
  }

  /**
   *
   * @return double
   */
  public double getMinNumber() {
    return this.minNumber;
  }

  /**
   *
   * @return boolean
   */
  public boolean isMaxinControl() {
    return maxinControl;
  }

  protected EFRowSet mainRowSet = null;
  /**
   *
   * @param e DataSetEvent
   */
  public void dataSetChanged(DataSetEvent e) {
    EFDataSet dataSet = e.getDataSet();
    mainRowSet = dataSet.getRowSet();
    Object value = null;
    if (mainRowSet != null) {
      value = RowSetValueUtils.getObject(mainRowSet, this.internalDataSetID,this.dataSetColID);
    }
    this.setSelectedItem(value);
  }

  protected boolean userInternalDataSetID = false;
  public boolean isUserInternalDataSetID() {
    return userInternalDataSetID;
  }

  public void setUserInternalDataSetID(boolean v) {
    userInternalDataSetID = v;
  }

  protected String fkeyID = null;
  //
  public String getFkeyID() {
    return fkeyID;
  }
  /**
   *
   * @param fkey String
   */
  public void setFkeyID(String fkey) {
    this.fkeyID = fkey;
  }
  /**
   *
   */
  protected String rlglID = null;
  /**
   *
   * @return String
   */
  public String getRlglID() {
    return rlglID;
  }
  /**
   *
   * @param rlglID String
   */
  public void setRlglID(String rlglID) {
    this.rlglID = rlglID;
  }

  /**
   *
   * @return Object
   */
  public Object getCellEditorValue() {
//    Object value = this.getSelectedItem();
//    if ( value == null ) {
    JTextComponent textComp = (JTextComponent)this.getEditor().
        getEditorComponent();
    String value = textComp.getText();
    if (intValue) {
        return value;
    }
    BigDecimal bd = null;
    try {
      bd = new BigDecimal(value.replace(",", ""));
      bd=bd.setScale(decn,RoundingMode.HALF_UP);
    }
    catch (Exception ex) {

    }
    return bd;
//    }
//    return value;
  }

  /**
   *
   * @param anEvent EventObject
   * @return boolean
   */
  public boolean isCellEditable(EventObject anEvent) {
    return false;
  }

  /**
   *
   * @param anEvent EventObject
   * @return boolean
   */
  public boolean shouldSelectCell(EventObject anEvent) {
    return false;
  }

  /**
   *
   * @return boolean
   */
  public boolean stopCellEditing() {
    return false;
  }

  /**
   *
   */
  public void cancelCellEditing() {
  }
  /**
   *
   * @param l CellEditorListener
   */
  public void addCellEditorListener(CellEditorListener l) {
  }
  /**
   *
   * @param l CellEditorListener
   */
  public void removeCellEditorListener(CellEditorListener l) {
  }
  /**
   *
   */
  protected String valueDataSetColID = null;
  /**
   *
   * @param valueDataSetColID String
   */
  public void setValueDataSetColID(String valueDataSetColID) {
    this.valueDataSetColID = valueDataSetColID;
  }
  /**
   *
   * @return String
   */
  public String getValueDataSetColID() {
    return valueDataSetColID;
  }

  /**
   *
   * @param userText String
   */
  private void endEdit() {
    String text = ( (JTextComponent)this.getEditor().getEditorComponent()).getText();
    Object number = getCellEditorValue();
    RowSetValueUtils.putObject(this.getMainRowSet(), this, number);
    if(intValue)
      ( (JTextComponent)this.getEditor().getEditorComponent()).setText((String)number);
  }

  /**
   *
   * @param dataSetComponentEvent DataSetComponentEvent
   */
  public void dataSetComponentListener(DataSetComponentEvent
                                       dataSetComponentEvent) {
    if (dataSetComponentEvent.getEventType() ==
        DataSetComponentEvent.DSC_EVENT_START_EDIT) {
      this.setEnabled(true);
      return;
    }
    if (dataSetComponentEvent.getEventType() ==
        DataSetComponentEvent.DSC_EVENT_STOP_EDIT) {
      this.setEnabled(false);
//      if (this.isFocusOwner()) {
      if(((JTextComponent)this.getEditor().getEditorComponent()).isFocusOwner()){
        endEdit();
      }
      return;
    }
    if (dataSetComponentEvent.getEventType() ==
        DataSetComponentEvent.DSC_EVENT_END_EDIT) {
//      if (this.isFocusOwner()) {
      if(((JTextComponent)this.getEditor().getEditorComponent()).isFocusOwner()){
        endEdit();
      }
      return;
    }
  }

  public ESPRowSet getMainRowSet() {
    return mainRowSet;
  }

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
    ( (JTextField)this.getEditor().getEditorComponent()).setHorizontalAlignment(horizontalAlignment);
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
  private boolean intValue=false;//是否只允许整数
  private int decn=2;//decn
  /**
   *
   * @return String
   */
  public String getFormulaOne() {
    return this.formulaOne;
  }

  public boolean isIntValue() {
    return intValue;
  }

  /**
   *
   * @param formulaOne String
   */
  public void setFormulaOne(String formulaOne) {
    this.formulaOne = formulaOne;
  }

  public void setIntValue(boolean intValue) {
    this.intValue = intValue;
    //只允许整数，不允许录入小数点
    document.setOnlyInt(intValue);

  }

  public void setDecn(int decn) {
    this.decn = decn;
  }

  public int getDecn() {
    return this.decn;
  }

  /**
     *
     * @param o Object
     * @todo Implement this com.efounder.comp.JComponentComboBox method
     */
    public void setSelectedItem(Object o) {
        super.setSelectedItem(o);
      if(numberFormat==null||numberFormat.trim().length()==0)
        numberFormat = "#,##0.00;#,##0.00";
      NumberFormat nf = new DecimalFormat(numberFormat);
      if(o!=null&&!o.equals("")){
        try {
          if (o instanceof String)
            o = new BigDecimal( (String) o);
//          o = nf.format(o);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
      }
      if(o==null||"0".equals(o)||"0.00".equals(o))o="";
      ( (JTextComponent)this.getEditor().getEditorComponent()).setText(o.toString());
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
