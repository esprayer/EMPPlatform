package com.efounder.bz.dbform.component;

import javax.swing.*;
import com.efounder.builder.base.data.EFDataSet;
import com.efounder.bz.dbform.datamodel.DataSetComponent;
import com.efounder.builder.base.data.DataSetEvent;
import com.efounder.builder.base.data.DataSetListener;
import com.efounder.bz.dbform.datamodel.DMColComponent;
import com.efounder.bz.dbform.datamodel.DMComponent;
import com.efounder.bz.dbform.datamodel.DataSetComponentEvent;
import com.efounder.builder.base.data.ESPRowSet;
import com.efounder.builder.base.data.EFRowSet;

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
public class FormLabel extends JLabel implements DataSetListener,FormComponent,DMComponent,DMColComponent {
  /**
   *
   * @param dataSetComponentEvent DataSetComponentEvent
   */
  public void dataSetComponentListener(DataSetComponentEvent
                                       dataSetComponentEvent) {
//    if ( dataSetComponentEvent.getEventType() == DataSetComponentEvent.DSC_EVENT_START_EDIT ) {
//      this.setEnabled(true);return;
//    }
//    if ( dataSetComponentEvent.getEventType() == DataSetComponentEvent.DSC_EVENT_STOP_EDIT ) {
//      this.setEnabled(false);return;
//    }
  }
  /**
   *
   */
  public FormLabel() {
    this.setText("formLable");
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

  protected String internalDataSetID = null;
  public String getInternalDataSetID() {
    return internalDataSetID;
  }
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
      // �����¼�������
      if ( dataSet != null ) dataSet.removeDataSetListener(this);
      dataSet = ds;
      // ����¼�������
      if ( dataSet != null ) dataSet.addDataSetListener(this);
      // ��ݷ���ı�
//      this.setDataVector(null,null);
//      this.fireTableDataChanged();
    }
  }
  protected boolean metaData = false;
  public boolean getMetaData() {
    return metaData;
  }
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
  protected EFRowSet mainRowSet = null;
  public void dataSetChanged(DataSetEvent e) {
//    if ( e.getEventType() == DataSetEvent.CURSOR ) {
      EFDataSet dataSet = e.getDataSet();
      if ( dataSet == null ) return;
      mainRowSet = dataSet.getRowSet();
      String value = null;
      if ( mainRowSet != null )
        value = RowSetValueUtils.getValueCaption(mainRowSet,this);
//        value = (String)RowSetValueUtils.getObject(rowSet,this.internalDataSetID,this.dataSetColID);
      this.setText(value);
//    }
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
  //
  public void setFkeyID(String fkey) {
    this.fkeyID = fkey;
  }
  protected String rlglID = null;
  //
  public String getRlglID() {
    return rlglID;
  }
  //
  public void setRlglID(String rlglID) {
    this.rlglID = rlglID;
  }
  protected String valueDataSetColID = null;
  // 取值字段
  public void setValueDataSetColID(String valueDataSetColID) {
    this.valueDataSetColID = valueDataSetColID;
  }
  //
  public String getValueDataSetColID() {
    return valueDataSetColID;
  }


  public ESPRowSet getMainRowSet() {
    return mainRowSet;
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
