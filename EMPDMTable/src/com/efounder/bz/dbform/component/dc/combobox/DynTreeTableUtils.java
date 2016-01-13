package com.efounder.bz.dbform.component.dc.combobox;

import com.efounder.bz.dbform.datamodel.DMColComponent;
import com.efounder.bz.dbform.datamodel.ColumnModel;
import com.efounder.bz.dbform.datamodel.DCTableModel;
import com.efounder.bz.dbform.component.dc.table.DMTable;
import com.efounder.builder.meta.dctmodel.DCTMetaData;
import com.efounder.bz.dbform.bizmodel.MDModel;
import com.efounder.bz.dbform.datamodel.DCTreeTableModel;
import com.efounder.bz.dbform.component.dc.treetable.DMTreeTable;

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
public class DynTreeTableUtils {
  protected DynTreeTableUtils() {
  }
  /**
   *
   * @param dctMetaData DCTMetaData
   * @param mdModel MDModel
   * @param columnModel ColumnModel
   * @return DCTableModel
   * @throws Exception
   */
  public static DCTreeTableModel createDCTreeTableModel(DCTMetaData dctMetaData,MDModel mdModel,
                                                ColumnModel columnModel,DMColComponent dmColComponent) throws Exception {
    // 创建
    DCTreeTableModel tableModel = new DCTreeTableModel();
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
  public static DMTreeTable createDMTreeTable(DCTreeTableModel tableModel) {
    DMTreeTable dmTable = new DMTreeTable();
    dmTable.setColumnModel(tableModel.getColModel());
    dmTable.setTreeTableModel(tableModel);
    return dmTable;
  }
}
