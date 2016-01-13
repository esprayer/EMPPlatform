package com.efounder.bz.dbform.component.dc;

import javax.swing.text.*;

import com.efounder.builder.base.data.*;
import com.efounder.bz.dbform.component.*;
import com.efounder.bz.dbform.datamodel.*;
import java.awt.event.FocusListener;
import java.awt.event.FocusEvent;

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
public class JTextComponentColumn implements DataSetListener,DMComponent,DMColComponent,FocusListener {
  /**
   *
   * @param e FocusEvent
   */
  public void focusGained(FocusEvent e) {
    // 如果是嵌入Cell，则不处理
    if ( this.embedCellEditor ) return;
    if ( !textComponent.isEnabled() || !textComponent.isEditable() ) return;
    // 获得焦点后，需要进行处理
    // 1.首先将值设置为当前Text，但是不能产生事件
    rowSetValue2TextCompValue(this.mainRowSet);
    // 获取焦点后，需要将文档改变事件开启
    enableDocumentEvent();
  }
  /**
   *
   * @param e FocusEvent
   */
  public void focusLost(FocusEvent e) {
    // 如果是嵌入Cell，则不处理
    if ( this.embedCellEditor ) return;
    disableDocumentEvent();
    // 失去焦点时，需要将用户输入的值检查确认后，设置到当前RowSet中，并且
    // 设置相应显示值
    // 如果用户录入的值不能通过检查，则让用户重新输和
    String userText = textComponent.getText();
    endEdit(userText);
  }
  /**
   *
   * @param userText String
   */
  private void endEdit(String userText) {
    // 如果组件没有焦点
//    if ( textComponent.isFocusOwner() ) return;
    // 如果disable或是不允许编缉，则不处理
    if ( !textComponent.isEnabled() || !textComponent.isEditable() ) return;
    // 检查用户输入的值是否合法
    if ( checkUserInputValue(userText) ) {
//      disableDocumentEvent();
      // 将用户输入值放入RowSet中
      textCompValue2RowSetValue(userText,this.mainRowSet);
      // 将转换生成的显示值放入TextComponent中
      // 不需要，因为会产生DataSetChangeEvent，如果没有改变，则需要设置，这里就不检查是否一直了
      // 都设置一次吧
      rowSetValue2TextCompCaption(this.mainRowSet);
    }
  }
  /**
   *
   * @param userText String
   * @return boolean
   */
  protected boolean checkUserInputValue(String userText) {
    return true;
  }
  /**
   *
   */
  protected JTextDocumentEvent tdEvent = null;
  /**
   *
   */
  public void disableDocumentEvent() {
    if ( embedCellEditor ) return;
    if ( tdEvent == null || textComponent == null ) return;
    textComponent.getDocument().removeDocumentListener(tdEvent);
  }
  /**
   *
   */
  public void enableDocumentEvent() {
    if ( embedCellEditor ) return;
    if ( textComponent == null ) return;
    if ( tdEvent == null ) tdEvent = JTextDocumentEvent.getJTextDocumentEvent(this);
    // 先删
    this.textComponent.getDocument().removeDocumentListener(tdEvent);
    // 再加
    this.textComponent.getDocument().addDocumentListener(tdEvent);
  }
  /**
   * 由RowSet中的值设置到当前文本组件中显示，可能需要进行ID2Name的转换
   * 这种转换中，需要提供格式转换功能
   */
  protected void rowSetValue2TextCompCaption(EFRowSet rowSet) {
    if ( mainRowSet != null ) {
      value   = RowSetValueUtils.getObject(mainRowSet,this);
      caption = RowSetValueUtils.getValueCaption(mainRowSet, this);
    }
    textComponent.setText(caption);
  }
  /**
   *
   */
  protected Object value = null;
  /**
   *
   */
  protected String caption = null;
  /**
   * 在TextComponent组件是当前编缉组件是，他应该显示当前RowSet中真实的值，
   * 不应该是ID2Name的转换结果
   * @param rowSet EFRowSet
   */
  protected void rowSetValue2TextCompValue(EFRowSet rowSet) {
    Object value = "";
    if ( rowSet != null )
      value = RowSetValueUtils.getObject(rowSet,this);
    textComponent.setText(value!=null?value.toString():null);
  }
  /**
   *
   * @param userText String
   * @param rowSet EFRowSet
   */
  protected void textCompValue2RowSetValue(String userText,EFRowSet rowSet) {
    RowSetValueUtils.putObject(rowSet,this,userText);
  }
  /**
   *
   * @return JTextComponent
   */
  public JTextComponent getJTextComponent() {
    return textComponent;
  }
  /**
   *
   */
  protected JTextComponent textComponent = null;
  /**
   *
   * @param textComponent JTextComponent
   */
  protected JTextComponentColumn(JTextComponent textComponent) {
    this.textComponent = textComponent;
  }
  /**
   *
   * @param textComponent JTextComponent
   * @return JTextComponentColumn
   */
  public static JTextComponentColumn getJTextComponentColumn(JTextComponent textComponent) {
    JTextComponentColumn tcc = new JTextComponentColumn(textComponent);
    return tcc;
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
    if ( dataSetComponent != dsc ) {
      if ( dataSetComponent != null ) dataSetComponent.removeDMComponent(this);
      dataSetComponent =  dsc;
      if ( dataSetComponent != null ) dataSetComponent.insertDMComponent(this);
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
    if ( dataSetComponent != null )
      setDataSet(dataSetComponent.getDataSet(dataSetID));
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
  /**
   *
   */
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
    if ( dataSet != ds ) {
      //
      if ( dataSet != null ) dataSet.removeDataSetListener(this);
      dataSet = ds;
      //
      if ( dataSet != null ) dataSet.addDataSetListener(this);
      //
    }
  }
  /**
   *
   */
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
  public void   setViewDataSetID(String viewDataSetID) {
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
  public void   setViewDataSetColID(String viewDataSetColID) {
    this.viewDataSetColID = viewDataSetColID;
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
   */
  protected EFRowSet mainRowSet = null;
  /**
   *
   * @param e DataSetEvent
   */
  public void dataSetChanged(DataSetEvent e) {
    EFDataSet dataSet = e.getDataSet();
    if ( dataSet == null ) return;
    mainRowSet = dataSet.getRowSet();
    rowSetValue2TextCompCaption(mainRowSet);
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
   * @param dataSetComponentEvent DataSetComponentEvent
   */
  public void dataSetComponentListener(DataSetComponentEvent
                                       dataSetComponentEvent) {
    if ( dataSetComponentEvent.getEventType() == DataSetComponentEvent.DSC_EVENT_START_EDIT ) {
      textComponent.setEnabled(true);return;
    }
    if ( dataSetComponentEvent.getEventType() == DataSetComponentEvent.DSC_EVENT_STOP_EDIT ) {
      String userText = textComponent.getText();
      if ( textComponent.isFocusOwner() )
        endEdit(userText);
      textComponent.setEnabled(false);
      return;
    }
    if ( dataSetComponentEvent.getEventType() == DataSetComponentEvent.DSC_EVENT_END_EDIT ) {
      String userText = textComponent.getText();
      if ( textComponent.isFocusOwner() )
        endEdit(userText);
      return;
    }

  }
  /**
   *
   * @return ESPRowSet
   */
  public ESPRowSet getMainRowSet() {
    return mainRowSet;
  }
  /**
   *
   */
  protected boolean userInternalDataSetID = false;
  /**
   *
   * @return boolean
   */
  public boolean isUserInternalDataSetID() {
    return userInternalDataSetID;
  }
  /**
   *
   * @param v boolean
   */
  public void setUserInternalDataSetID(boolean v) {
    userInternalDataSetID = v;
  }
  /**
   *
   */
  protected String fkeyID = null;
  /**
   *
   * @return String
   */
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
   * 是否嵌入CellEditor中
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
