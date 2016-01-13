package com.efounder.dataobject.view;

import javax.swing.table.TableCellRenderer;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.*;
import com.borland.dx.dataset.*;
import javax.swing.table.*;
import com.efounder.dataobject.node.*;
import com.efounder.ui.*;
import com.efounder.pub.comp.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class DCTLevelCellRenderer extends DefaultTableCellRenderer {
  protected Icon icon = null;
  protected DCTNodeDataRow dctNodeDataRow = null;
  protected DataSet dataSet = null;
  protected String LevelColID = null;
  protected String DCT_TYPE = null; //
  /**
   *
   * @param dataSet DataSet
   * @param LevelColID String
   * @param icon Icon
   * @param dctNodeDataRow DCTNodeDataRow
   */
  public DCTLevelCellRenderer(String DCT_TYPE,DataSet dataSet,String LevelColID,Icon icon,DCTNodeDataRow dctNodeDataRow) {
    this.icon           = icon;
    this.dctNodeDataRow = dctNodeDataRow;
    this.dataSet = dataSet;
    this.LevelColID = LevelColID;
    this.DCT_TYPE   = DCT_TYPE;
  }
  /**
   *
   * @param table JTable
   * @param value Object
   * @param isSelected boolean
   * @param hasFocus boolean
   * @param row int
   * @param column int
   * @return Component
   */
  public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
    DefaultTableCellRenderer df = (DefaultTableCellRenderer)super.getTableCellRendererComponent(table,value,isSelected,hasFocus,row,column);
    Icon ii = getDataIcon(value,row,column);
    if ( ii != null ) {
      int level = getLevel(value,row,column);
      Icon blankIcon = Icons.getBlankIcon(16*(level-1),16);
      ii = new CompoundIcon(blankIcon,ii);
      df.setIcon(ii);
    }

    return df;
  }
  protected int getLevel(Object value,int row,int col) {
    if ( dctNodeDataRow != null ) {
      return dctNodeDataRow.getLevel(DCT_TYPE,value,dataSet,row,col);
    } else if ( LevelColID != null && !"".equals(LevelColID.trim()) ) {
      DataRow dataRow = new DataRow(dataSet);
      dataSet.getDataRow(dataRow);
      return dataRow.getBigDecimal(LevelColID).intValue();
    }
    return 1;
  }
  protected Icon getDataIcon(Object value,int row,int col) {
    if ( dctNodeDataRow != null ) {
      return dctNodeDataRow.getIcon(DCT_TYPE,value,dataSet,row,col);
    }
    return icon;
  }
}
