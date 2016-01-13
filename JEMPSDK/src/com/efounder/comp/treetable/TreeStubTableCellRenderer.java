package com.efounder.comp.treetable;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;

import com.core.xml.*;
import com.efounder.pub.comp.*;
import com.efounder.ui.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class TreeStubTableCellRenderer extends DefaultTableCellRenderer {
  protected Icon icon = null;
  /**
   *
   */
  public TreeStubTableCellRenderer(Icon icon) {
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
    DefaultTableCellRenderer df = (DefaultTableCellRenderer)super.getTableCellRendererComponent(table,value,isSelected,hasFocus,row,column);
    if ( !(value instanceof StubObject) ) return df;
    StubObject stub = (StubObject)value;
    Icon ii = stub.getIcon();if ( ii == null ) ii = icon;
    if ( ii != null ) {
      int level = stub.getLevel();
      Icon blankIcon = Icons.getBlankIcon(16*(level),16);
      ii = new CompoundIcon(blankIcon,ii);
      df.setIcon(ii);
    }
    return df;
  }
}
