package com.efounder.comp.table;

import javax.swing.Icon;
import javax.swing.table.*;
import com.efounder.ui.Icons;
import com.efounder.pub.comp.CompoundIcon;
import javax.swing.JTable;
import com.core.xml.StubObject;
import java.awt.Component;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2006</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class StatusIconCellRenderer extends StatusCellRenderer {
  /**
   *
   * @param icon Icon
   * @return TableCellRenderer
   */
  public static TableCellRenderer getInstance(Icon icon) {
    return new StatusIconCellRenderer(icon);
  }
  /**
   *
   */
  protected Icon icon = null;
  /**
   *
   * @param icon Icon
   */
  protected StatusIconCellRenderer(Icon icon) {
    super();
    this.icon = icon;
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
    StatusCellRenderer df = (StatusCellRenderer)super.getTableCellRendererComponent(table,value,isSelected,hasFocus,row,column);
    if ( !(value instanceof StubObject) ) return df;
    StubObject stub = (StubObject)value;
    Icon ii = stub.getIcon();if ( ii == null ) ii = icon;
    if ( ii != null ) {
      int level = stub.getLevel();
      Icon blankIcon = Icons.getBlankIcon(16*(level),16);
      ii = new CompoundIcon(blankIcon,ii);
      df.setIcon(ii);
    }
    ii = stub.getCompIcon();
    df.setStatusIcon(ii);
    return df;
  }
}
