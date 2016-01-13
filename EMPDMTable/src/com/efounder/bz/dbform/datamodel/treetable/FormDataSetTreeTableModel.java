package com.efounder.bz.dbform.datamodel.treetable;

import com.efounder.bz.dbform.bizmodel.MDModel;
import com.efounder.bz.dbform.datamodel.DCTreeTableModel;
import com.efounder.builder.base.data.EFDataSet;
import com.efounder.builder.meta.dctmodel.DCTMetaData;
import com.efounder.bz.dbform.datamodel.ColumnModel;
import com.efounder.bz.dbform.datamodel.column.Column;
import java.util.List;
import com.efounder.builder.base.data.EFRowSet;
import com.efounder.builder.base.data.ESPRowSet;
import com.efounder.bz.dbform.datamodel.DataSetComponentEvent;
import com.efounder.builder.base.data.DataSetEvent;
import com.efounder.bz.dbform.datamodel.DataSetComponent;

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
public class FormDataSetTreeTableModel extends DCTreeTableModel {

  /**
   *
   */
  public FormDataSetTreeTableModel() {
  }

  //根据主数据分级
  protected MDModel mdModel;
  //根据分录里的某一列分级
  protected String columnName;

  /**
   *
   * @param mdm MDModel
   */
  public void setMDModel(MDModel mdm){

    mdModel = mdm;

  }

  /**
   *
   * @return MDModel
   */
  public MDModel getMDModel(){
      if(mdModel == null){
          DataSetComponent dsc = this.getDataSetComponent();
          if(dsc instanceof MDModel){
              return (MDModel)dsc;
          }
      }
    return mdModel;
  }

  /**
   *
   * @param colName String
   */
  public void setColumnName(String colName) {
    if (colName.indexOf(".") != -1)
      columnName = colName.substring(colName.indexOf(".") + 1);
    else
      columnName = colName;
  }

  /**
   *
   * @return String
   */
  public String getColumnName(){
    return columnName;
  }

  /**
   *
   * @return EFDataSet
   */
  protected EFDataSet getMDMDataSet() throws Exception{
    if(mdModel == null) return null;
//    String dctId = mdModel.getDCT_ID();
    if(mdModel.getMDMDataModel() == null) mdModel.load();
    EFDataSet mdmDataSet = mdModel.getMDMDataModel().getDCTDataSet();
    return mdmDataSet;
  }

  /**
   *
   * @return DCTMetaData
   */
  protected DCTMetaData getMDMetaData() {
    DCTMetaData dctmetaData = null;
    MDModel mdm = getMDModel();
    if(mdm != null)
      dctmetaData = mdm.getDCTMetaData();

    return dctmetaData;
  }

  /**
   * 根据mdm构造分级dataset
   */
  protected void resetDataSet() throws Exception{
    EFDataSet mdmDS = getMDMDataSet();
    if( mdmDS == null || dataSet == null ) return;
    String itemCol = columnName;
    Column column = getColumn(columnName);
    if(column == null) return;
    if(column.isUserInternalDataSetID() && itemCol.indexOf(".") == -1)
      itemCol = column.getInternalDataSetID() + "." + itemCol;
    DCTMetaData mdmDctMetaData = getMDMetaData();
    if(mdmDctMetaData == null) return;
    dataSet = resetDataSet(mdmDS, dataSet, mdmDctMetaData, itemCol);
  }

  /**
   * 在主dataset中插入分级数据
   * @param mdmDS EFDataSet
   * @param mainDS EFDataSet
   * @param mdmDMD DCTMetaData
   * @param mainCol String
   * @return EFDataSet
   */
  protected EFDataSet resetDataSet(EFDataSet mdmDS, EFDataSet mainDS, DCTMetaData mdmDMD, String mainCol){
    if(mdmDS == null || mainDS == null || mdmDMD == null ) return null;
    String mdmBMCol = mdmDMD.getDCT_BMCOLID();
    String mdmJSCol = mdmDMD.getDCT_JSCOLID();
    String mdmMXCol = mdmDMD.getDCT_MXCOLID();
    if(mdmJSCol == null || mdmJSCol.equals("")) return mainDS;
    for(int i=0; i<mainDS.getRowCount(); i++){
      EFRowSet rs = mainDS.getRowSet(i);
      String keyValue = rs.getString(mainCol, "");
      if("".equals(keyValue)) continue;
      ESPRowSet mdmRS = mdmDS.getRowSet(keyValue);
      if(mdmRS != null ){
          rs.putString(mdmBMCol, mdmRS.getString(mdmBMCol, ""));

          int JS = mdmRS.getNumber(mdmJSCol, 1).intValue();
          rs.putNumber(mdmJSCol, JS);

          rs.putString(mdmMXCol, mdmRS.getString(mdmMXCol, ""));
      }else{
          rs.putString(mdmBMCol, keyValue);
          rs.putNumber(mdmJSCol, 1);
          rs.putString(mdmMXCol, "1");
      }
    }

    return mainDS;
  }

  /**
   *
   * @param columnName String
   * @return Column
   */
  protected Column getColumn(String columnName){
    Column column = null;
    ColumnModel columnModel = getColModel();
    if(columnModel == null) return null;
    for(int i = 0; i < columnModel.getColumnCount(); i++){
      Column cl = (Column)columnModel.getColumnByModelIndex(0);
      String cln = cl.getDataSetColID();
      if(cln.equals(columnName)) column = cl;
    }

    return column;
  }


  public void setDataSet(EFDataSet ds){
//    if (dataSet != ds) {
      // 清除掉事件监听器
      if (dataSet != null) {
        dataSet.removeDataSetListener(this);
      }
      dataSet = ds;
      // 增加事件监听器
      if (dataSet != null) {
        try{

            if(this.getMDModel() != null){
                resetDataSet();
            }
          resetDataSet();
        }catch(Exception e){
          e.printStackTrace();
        }
        dataSet.addDataSetListener(this);
        DCTMetaData mdmDctMetaData = getMDMetaData();

        if ( mdmDctMetaData != null )
          TreeTableUtils.createTreeTable(this,mdmDctMetaData,dataSet,this.getColModel());
      }

//    }

  }

  private boolean enableDataEdit = true;

  public boolean isEnableDataEdit() {
    return enableDataEdit;
  }

  public void setEnableDataEdit(boolean enableDataEdit) {
    this.enableDataEdit = enableDataEdit;
  }

  /**
   *
   * @param dataSetComponentEvent DataSetComponentEvent
   */
  public void dataSetComponentListener(DataSetComponentEvent
                                       dataSetComponentEvent) {
    if ( dataSetComponentEvent.getEventType() == DataSetComponentEvent.DSC_EVENT_START_EDIT ) {
      this.setEnableDataEdit(true);
      return;
    }
    if ( dataSetComponentEvent.getEventType() == DataSetComponentEvent.DSC_EVENT_STOP_EDIT ) {
      this.setEnableDataEdit(false);return;
    }
  }

  /**
   * 控制可否编辑
   * @param node Object
   * @param column int
   * @return boolean
   */
  public boolean isCellEditable(Object node, int column) {
      return this.isEnableDataEdit();
  }

}
