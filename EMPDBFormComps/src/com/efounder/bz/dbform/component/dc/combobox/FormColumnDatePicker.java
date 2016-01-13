package com.efounder.bz.dbform.component.dc.combobox;

import java.util.*;

import javax.swing.*;

import com.sunking.swing.*;
import javax.swing.event.CellEditorListener;
import java.text.*;
import com.efounder.bz.dbform.datamodel.DMColComponent;
import com.efounder.bz.dbform.datamodel.DMComponent;
import com.efounder.builder.base.data.DataSetListener;
import com.efounder.builder.base.data.EFDataSet;
import com.efounder.bz.dbform.datamodel.DataSetComponent;
import com.efounder.builder.base.data.DataSetEvent;
import com.efounder.builder.base.data.EFRowSet;
import com.efounder.bz.dbform.datamodel.DataSetComponentEvent;
import com.efounder.builder.base.data.ESPRowSet;
import com.efounder.bz.dbform.component.FormComponent;
import com.efounder.bz.dbform.component.FormContainer;
import com.efounder.bz.dbform.component.RowSetValueUtils;
import java.awt.event.FocusListener;
import java.awt.event.FocusEvent;
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
public class FormColumnDatePicker extends JDatePickerEx implements FocusListener,FormComponent,CellEditor,
    DMComponent,DMColComponent,DataSetListener{
  /**
   *
   * @param date Date
   */
  public void setSelectedDate(Date date) {
      if(!isEditable())return;
    super.setSelectedDate(date);
    RowSetValueUtils.putObject(mainRowSet,this,date);
  }
  /**
   *
   * @param e FocusEvent
   */
  public void focusGained(FocusEvent e) {
    // 如果是嵌入Cell，则不处理
    if ( this.embedCellEditor ) return;
    // 获得焦点后，需要进行处理
    // 1.首先将值设置为当前Text，但是不能产生事件
//    rowSetValue2TextCompValue(this.mainRowSet);
  }
  /**
   *
   * @param e FocusEvent
   */
  public void focusLost(FocusEvent e) {
    // 如果是嵌入Cell，则不处理
    if ( this.embedCellEditor ) return;
    endEdit();
  }
  /**
   *
   * @param userText String
   */
  private void endEdit() {
    // 如果组件没有焦点
    // 如果disable或是不允许编缉，则不处理
    Date date = null;
    try {
      date = this.getSelectedDate();
      // 将用户输入值放入RowSet中
      RowSetValueUtils.putObject(mainRowSet,this,date);
    }
    catch (ParseException ex) {
      ErrorManager.getDefault().notify(ex);
    }
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
//    if ( e.getEventType() == DataSetEvent.CURSOR ) {
      EFDataSet dataSet = e.getDataSet();
      if ( dataSet == null ) return;
      mainRowSet = dataSet.getRowSet();
      Object value = null;
      if ( mainRowSet != null )
//        value = RowSetValueUtils.getValueCaption(rowSet,this);
        value = RowSetValueUtils.getObject(mainRowSet,this.internalDataSetID,this.dataSetColID);
      if (value instanceof String) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        try {
          value = sdf.parse( (String) value);
        }
        catch (ParseException ex) {
        }
      }
      this.setSelectedItem(value);
//    }
  }

  public void setSelectedItem(Object item) {
      boolean b=this.isEditable();
      if(!b)this.setEditable(true);
      super.setSelectedItem(item);
      if(!b)this.setEditable(false);
  }
  /**
   *
   * @param dataSetComponentEvent DataSetComponentEvent
   */
  public void dataSetComponentListener(DataSetComponentEvent
                                       dataSetComponentEvent) {
    if ( dataSetComponentEvent.getEventType() == DataSetComponentEvent.DSC_EVENT_START_EDIT ) {
      this.setEnabled(true);return;
    }
    if ( dataSetComponentEvent.getEventType() == DataSetComponentEvent.DSC_EVENT_STOP_EDIT ) {
      this.setEnabled(false);
      if ( this.isFocusOwner() )
        endEdit();
      return;
    }
    if ( dataSetComponentEvent.getEventType() == DataSetComponentEvent.DSC_EVENT_END_EDIT ) {
      if ( this.isFocusOwner() )
        endEdit();
      return;
    }
  }




























    //���ڸ�ʽ
    private int dateStyle;

    /**
     *
     */
    public FormColumnDatePicker() {
        super();
        this.addFocusListener(this);
        ( (JTextField)this.getEditor().getEditorComponent()).addFocusListener(this);
        this.setRequestFocusEnabled(true);
    }
    /**
     *
     * @param formatStyle int
     * @throws UnsupportedOperationException
     */
    public FormColumnDatePicker(int formatStyle) throws UnsupportedOperationException {
//        super(formatStyle);
    }
    /**
     *
     * @param formatStyle int
     * @param initialDatetime Date
     * @throws UnsupportedOperationException
     */
    public FormColumnDatePicker(int formatStyle, Date initialDatetime) throws
        UnsupportedOperationException {
//        super(formatStyle, initialDatetime);

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
     * @param dateStyle int
     */
    public void setDateStyle(int dateStyle) {
        this.dateStyle = dateStyle;
//        this.setStyle(this.getDateStyle());
    }

    /**
     *
     * @return int
     */
    public int getDateStyle() {
        return dateStyle;
    }




    /**
     *
     * @return Object
     */
    public Object getCellEditorValue() {
      try {
        return this.getSelectedDate();
      }
      catch (Exception ex) {
        return null;
      }
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
     * @return ESPRowSet
     */
    public ESPRowSet getMainRowSet() {
      return mainRowSet;
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
