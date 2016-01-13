package com.efounder.bz.dbform.datamodel.proxy;

import com.efounder.bz.dbform.datamodel.*;
import com.efounder.builder.base.data.EFDataSet;
import com.efounder.builder.base.data.ESPRowSet;
import com.efounder.builder.base.data.DataSetListener;

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
public  class DMColComponentProxy
    implements DMColComponent,DMComponent {
  Object Warper=null;
  public DMColComponentProxy(Object dm) {
    Warper=dm;
  }
  public String getWholeColID(){
    String col=getDataSetColID();
    if(col==null)return null;
    if (isUserInternalDataSetID())
      col = getInternalDataSetID() + "." + col;
    return col;
  }
  protected String dataSetColID = null;
  public String getDataSetColID() {
    return dataSetColID;
  }
  public void setDataSetColID(String dataSetColID) {
     this.dataSetColID = dataSetColID;
  }
  protected DataSetComponent dataSetComponent = null;
  public DataSetComponent getDataSetComponent() {
    return dataSetComponent;
  }
  public void setDataSetComponent(DataSetComponent dsc) {
    if (dataSetComponent != dsc) {
      if (dataSetComponent != null)
        dataSetComponent.removeDMComponent((DMComponent)Warper);
      dataSetComponent = dsc;
      if (dataSetComponent != null)
        dataSetComponent.insertDMComponent((DMComponent)Warper);
    }
  }
  protected String dataSetID = null;
  public void setDataSetID(String dataSetID) {
    this.dataSetID = dataSetID;
    if (dataSetComponent != null)
      ((DMComponent)Warper).setDataSet(dataSetComponent.getDataSet(dataSetID));
  }
  public String getDataSetID() {
    return dataSetID;
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
    internalDataSetID = dataSetID;
 }

 protected boolean isMetaData = false;
 /**
  *
  * @return boolean
  */
 public boolean getMetaData() {
   return isMetaData;
 }
 public void setMetaData(boolean v) {
   isMetaData = v;
 }
 protected String viewDataSetID = null;
 public void setViewDataSetID(String viewDataSetID) {
   this.viewDataSetID = viewDataSetID;
 }
 public String getViewDataSetID() {
   return viewDataSetID;
 }
 protected boolean userInternalDataSetID = false;
  public boolean isUserInternalDataSetID() {
    return userInternalDataSetID;
  }

  public void setUserInternalDataSetID(boolean v) {
    userInternalDataSetID = v;
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

  String fkey = null;
  public String getFkeyID() {
    return fkey;
  }

  public void setFkeyID(String fkey) {
    this.fkey = fkey;
  }
   public String getRlglID() {
     return rlglID;
   }
   String rlglID=null;
   public void setRlglID(String rlglID) {
     this.rlglID=rlglID;
   }
   int alignment=0;
   public int getHorizontalAlignment() {
     return alignment;
   }

   public void setHorizontalAlignment(int horizontalAlignment) {
     alignment=horizontalAlignment;
   }
   String numberFormat=null;
   public String getNumberFormat() {
     return numberFormat;
   }

   public void setNumberFormat(String numberFormat) {
     this.numberFormat=numberFormat;
   }
   String dateFormat="";
   public String getDateFormat() {
     return dateFormat;
   }

   public void setDateFormat(String dateFormat) {
   this.dateFormat=dateFormat;
   }
   String formula=null;
   public String getFormulaOne() {
     return formula;
   }
   protected String viewDataSetColID = null;
  public void setViewDataSetColID(String viewDataSetColID) {
    this.viewDataSetColID=viewDataSetColID;
  }

  public String getViewDataSetColID() {
    return viewDataSetColID;
  }

  public void setFormulaOne(String formulaOne) {
    formula=formulaOne;
  }
  EFDataSet dataSet=null;
  public void setDataSet(EFDataSet ds) {
    if ( dataSet != ds ) {
      // 清除掉事件监听器
      if ( dataSet != null ) dataSet.removeDataSetListener((DataSetListener)Warper);
      dataSet = ds;
      // 增加事件监听器
      if ( dataSet != null ) dataSet.addDataSetListener((DataSetListener)Warper);
      }
  }

  public EFDataSet getDataSet() {
    return dataSet;
  }
  protected ESPRowSet mainRowSet = null;
  public ESPRowSet getMainRowSet() {
    return mainRowSet;
  }
  public void setMainRowSet(ESPRowSet ers) {
    mainRowSet=ers;
  }
  public void dataSetComponentListener(DataSetComponentEvent
                                       dataSetComponentEvent) {
    ((DMComponent)Warper).dataSetComponentListener(dataSetComponentEvent);
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
