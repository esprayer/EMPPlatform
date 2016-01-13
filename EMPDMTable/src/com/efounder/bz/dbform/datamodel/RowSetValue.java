package com.efounder.bz.dbform.datamodel;

import com.efounder.builder.base.data.ESPRowSet;
import com.efounder.bz.dbform.datamodel.column.Column;
import com.efounder.builder.meta.domodel.DOMetaData;
import com.efounder.builder.meta.dctmodel.DCTMetaData;
import org.jdesktop.swingx.treetable.DefaultMutableTreeTableNode;

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
public class RowSetValue {
  /**
   *
   */
  protected RowSetValue() {
  }
  /**
   *
   */
  protected ESPRowSet mainRowSet = null;
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
  protected Column column = null;
  /**
   *
   * @return Column
   */
  public Column getColumn() {
    return column;
  }
  /**
   *
   * @param column Column
   */
  public void setColumn(Column column) {
    this.column = column;
  }
  /**
   *
   */
  protected Object cellValue = null;
  /**
   *
   * @return Object
   */
  public Object getCellValue() {
    return cellValue;
  }
  public void setCellValue(Object o){
      cellValue=o;
  }
  /**
   *
   * @param mainRowSet ESPRowSet
   * @param cellValue Object
   * @return RowSetValue
   */
  public static RowSetValue getInstance(ESPRowSet mainRowSet,Object cellValue,Column column) {
    RowSetValue rowSetValue = new RowSetValue();
    rowSetValue.mainRowSet = mainRowSet;
    rowSetValue.cellValue  = cellValue;
    rowSetValue.column     = column;
    return rowSetValue;
  }
  /**
   *
   * @return String
   */
  public String toString() {
    if ( cellValue == null ) return null;
    return cellValue.toString();
  }
  /**
   *
   */
  protected ESPRowSet colMetaData = null;
  /**
   *
   * @return ESPRowSet
   */
  public ESPRowSet getColMetaData() {
    return colMetaData;
  }
  /**
   *
   * @param colMetaData ESPRowSet
   */
  public void setColMetaData(ESPRowSet colMetaData) {
    this.colMetaData = colMetaData;
  }
  /**
   *
   */
  protected DOMetaData doMetaData = null;
  /**
   *
   * @return DOMetaData
   */
  public DOMetaData getDOMetaData() {
    return doMetaData;
  }
  /**
   *
   * @param doMetaData DOMetaData
   */
  public void setDOMetaData(DOMetaData doMetaData) {
    this.doMetaData = doMetaData;
  }
  /**
   *
   */
  protected ColumnModel columnModel = null;
  /**
   *
   * @param columnModel ColumnModel
   */
  public void setColumnModel(ColumnModel columnModel) {
    this.columnModel = columnModel;
  }
  /**
   *
   * @return ColumnModel
   */
  public ColumnModel getColumnModel() {
    return columnModel;
  }
  /**
   *
   */
  protected DCTMetaData viewDCTMetaData = null;
  /**
   *
   * @return DCTMetaData
   */
  public DCTMetaData getViewDCTMetaData() {
    return viewDCTMetaData;
  }
  /**
   *
   * @param md DCTMetaData
   */
  public void setViewDCTMetaData(DCTMetaData md) {
    viewDCTMetaData = md;
  }
  /**
   *
   */
  protected DefaultMutableTreeTableNode treeTableNode = null;
  /**
   *
   * @return DefaultMutableTreeTableNode
   */
  public DefaultMutableTreeTableNode getTreeTableNode() {
    return treeTableNode;
  }
  /**
   *
   * @param treeTableNode DefaultMutableTreeTableNode
   */
  public void setTreeTableNode(DefaultMutableTreeTableNode treeTableNode) {
    this.treeTableNode = treeTableNode;
  }
}
