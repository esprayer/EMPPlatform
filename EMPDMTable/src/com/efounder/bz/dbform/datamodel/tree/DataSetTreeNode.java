package com.efounder.bz.dbform.datamodel.tree;

import javax.swing.tree.*;

import com.efounder.builder.base.data.*;
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
public class DataSetTreeNode extends DefaultMutableTreeNode {
  /**
   *
   * @return DataSetTreeNode
   */
  public static DataSetTreeNode getInstance(DOMetaData doMetaData) {
    DataSetTreeNode node = new DataSetTreeNode();
    node.doMetaData = doMetaData;
    return node;
  }
  /**
   *
   */
  protected DOMetaData doMetaData = null;
  /**
   *
   * @param rowSet ESPRowSet
   * @return DataSetTreeNode
   */
  public static DataSetTreeNode getInstance(ESPRowSet rowSet,
                                            StringBuffer DCT_BMCOLID,
                                            StringBuffer DCT_MCCOLID,
                                            StringBuffer DCT_JSCOLID) {
    DataSetTreeNode node = new DataSetTreeNode();
    node.rowSet = rowSet;
    node.DCT_BMCOLID = DCT_BMCOLID;
    node.DCT_MCCOLID = DCT_MCCOLID;
    node.DCT_JSCOLID = DCT_JSCOLID;
    return node;
  }
  public String toString() {
    if ( doMetaData != null ) {
      return doMetaData.getObjID()+"-"+doMetaData.getString("OBJ_MC","");
    }
    if ( rowSet == null ) return null;
    String BM = rowSet.getString(DCT_BMCOLID.toString(),null);
    String MC = rowSet.getString(DCT_MCCOLID.toString(),null);
    return BM+"-"+MC;
  }
  /**
   *
   */
  protected StringBuffer DCT_BMCOLID = null;
  /**
   *
   */
  protected StringBuffer DCT_MCCOLID = null;
  /**
   *
   */
  protected StringBuffer DCT_JSCOLID = null;
  /**
   *
   */
  private ESPRowSet rowSet = null;
  /**
   *
   * @param rowSet ESPRowSet
   */
  public void setRowSet(ESPRowSet rowSet) {
    this.rowSet = rowSet;
  }
  /**
   *
   * @return ESPRowSet
   */
  public ESPRowSet getRowSet() {
    return rowSet;
  }
}
