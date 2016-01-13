package com.efounder.bz.dbform.datamodel.treetable;

import org.jdesktop.swingx.treetable.*;
import com.efounder.builder.base.data.*;
import com.efounder.bz.dbform.datamodel.*;
import com.efounder.bz.dbform.datamodel.column.Column;
import javax.swing.table.TableColumn;
import java.util.HashMap;
import com.efounder.bz.dbform.component.RowSetValueUtils;

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
public class DataSetTreeTableNode extends DefaultMutableTreeTableNode {
  /**
   *
   * @param rowSet EFRowSet
   * @return DataSetTreeTableNode
   */
  public static DataSetTreeTableNode getInstance(ESPRowSet rowSet) {
    DataSetTreeTableNode node = new DataSetTreeTableNode();
    node.setRowSet(rowSet);
    return node;
  }
  /**
   *
   * @return DataSetTreeTableNode
   */
  public static DataSetTreeTableNode getInstance() {
    DataSetTreeTableNode node = new DataSetTreeTableNode();
    return node;
  }
  /**
   *
   */
  protected java.util.Map tableColumnMapByModelIndex = new HashMap();
  /**
   *
   * @param modelIndex int
   * @return TableColumn
   */
  protected TableColumn getTableColumnByModelIndex(int modelIndex) {
    if (columnModel == null) {
      return null;
    }
    String index = String.valueOf(modelIndex);
    Column tableColumn = (Column) tableColumnMapByModelIndex.get(index);
    if (tableColumn == null) {
      tableColumn = columnModel.getColumnByModelIndex(modelIndex);
      if (tableColumn != null) {
        tableColumnMapByModelIndex.put(index, tableColumn);
      }
    }
    return tableColumn;
  }
  /**
   * {@inheritDoc}
   */
  public Object getValueAt(int column) {
    // 先获取表列
    TableColumn tableColumn = this.getTableColumnByModelIndex(column);
    // 如果为空，则直接返回为空
    if (tableColumn == null || !(tableColumn instanceof DMColComponent) ) {
      return null;
    }
    ESPRowSet rowSet = this.getRowSet();
    if (rowSet == null) {
      return null;
    }
    return RowSetValueUtils.getObject(rowSet,(DMColComponent)tableColumn);
//    Object colID = tableColumn.getIdentifier();
//    if (colID == null) {
//      return null;
//    }
//
//    return rowSet.getObject(colID.toString(), null);
  }

  /**
   * {@inheritDoc}
   */
  public int getColumnCount() {
    if ( columnModel == null ) return 1;
    return columnModel.getColumnCount();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isEditable(int column) {
    return true;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setValueAt(Object aValue, int column) {
    // 先获取表列
    TableColumn tableColumn = this.getTableColumnByModelIndex(column);
    // 如果为空，则直接返回为空
    if (tableColumn == null) {
      return;
    }
    Object colID = tableColumn.getIdentifier();
    if (colID == null) {
      return;
    }
    ESPRowSet rowSet = this.getRowSet();
    if (rowSet == null) {
      return;
    }
    rowSet.putObject(colID.toString(), aValue);
  }



























  /**
   *
   */
  public DataSetTreeTableNode() {
  }
  /**
   *
   */
  protected ColumnModel columnModel = null;
  /**
   *
   * @return ColumnModel
   */
  public ColumnModel getColModel() {
    return columnModel;
  }

  /**
   *
   * @param colModel ColumnModel
   */
  public void setColModel(ColumnModel colModel) {
    this.columnModel = colModel;
  }
  /**
   *
   */
//  private EFRowSet rowSet = null;
  /**
   *
   * @param rowSet EFRowSet
   */
  public void setRowSet(ESPRowSet rowSet) {
    this.setUserObject(rowSet);
  }
  /**
   *
   * @return EFRowSet
   */
  public ESPRowSet getRowSet() {
    return (ESPRowSet)this.getUserObject();
  }
}
