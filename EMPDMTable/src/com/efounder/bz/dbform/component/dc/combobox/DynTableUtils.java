package com.efounder.bz.dbform.component.dc.combobox;

import com.efounder.bz.dbform.bizmodel.MDModel;
import com.efounder.bz.dbform.datamodel.ColumnModel;
import com.efounder.builder.meta.dctmodel.DCTMetaData;
import com.efounder.bz.dbform.datamodel.column.Column;
import com.efounder.bz.dbform.datamodel.DMColComponent;
import com.efounder.builder.meta.domodel.DOMetaData;

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
public class DynTableUtils {
  /**
   *
   */
  protected DynTableUtils() {
  }
  /**
   *
   * @param dctMetaData DCTMetaData
   * @return ColumnModel
   * @throws Exception
   */
  public static ColumnModel createColumnModel(DCTMetaData dctMetaData) throws Exception {
    ColumnModel columnModel = new ColumnModel();
    // 生成编码列
    Column bmCol = new Column();
    bmCol.setDataSetColID(dctMetaData.getDCT_BMCOLID());
    bmCol.setInternalDataSetID(dctMetaData.getObjID());
    bmCol.setModelIndex(0);
    columnModel.insertDataComponent(bmCol);
    // 生成名称列
    Column mcCol = new Column();
    mcCol.setDataSetColID(dctMetaData.getDCT_MCCOLID());
    mcCol.setInternalDataSetID(dctMetaData.getObjID());
    mcCol.setModelIndex(1);
    columnModel.insertDataComponent(mcCol);
    return columnModel;
  }
  /**
   *
   * @param mdModel MDModel
   * @param dmColComponent DMColComponent
   * @throws Exception
   */
  public static void processMDModel(MDModel mdModel,DMColComponent dmColComponent) throws Exception {
    String DCT_ID  = dmColComponent.getViewDataSetID();
    mdModel.setBizDCTID(DCT_ID);
    String FKEY_ID = dmColComponent.getFkeyID();
    mdModel.setFKEY_ID(FKEY_ID);
    String RLGL_ID = dmColComponent.getRlglID();
    mdModel.setRLGL_ID(RLGL_ID);
  }
  /**
   *
   * @param mdModel MDModel
   * @param dmColComponent DMColComponent
   * @return DCTMetaData
   * @throws Exception
   */
  public static DCTMetaData getDCTMetaData(MDModel mdModel,DMColComponent dmColComponent) throws Exception {
    String DCT_ID  = dmColComponent.getViewDataSetID();
    DOMetaData[] doMetaDatas = mdModel.getDOMetaData(DCT_ID);
    if ( doMetaDatas == null || doMetaDatas.length == 0 ) return null;
    return doMetaDatas[0].getDCTMetaData();
  }
  /**
   *
   * @param dctMetaData DCTMetaData
   * @param mdModel MDModel
   * @param columnModel ColumnModel
   * @return DCTableModel
   * @throws Exception
   */
  public static DCTableModel createDCTableModel(DCTMetaData dctMetaData,MDModel mdModel,
                                                ColumnModel columnModel,DMColComponent dmColComponent) throws Exception {
    // 创建
    DCTableModel tableModel = new DCTableModel();
    tableModel.setDataSetID(dmColComponent.getViewDataSetID());
    tableModel.setDataSetComponent(mdModel);
    tableModel.setColModel(columnModel);
    return tableModel;
  }
  /**
   *
   * @param tableModel DCTableModel
   * @return DMTable
   */
  public static DMTable createDMTable(DCTableModel tableModel) {
    DMTable dmTable = new DMTable();
    dmTable.setColModel(tableModel.getColModel());
    dmTable.setDCTableModel(tableModel);
    return dmTable;
  }
}
