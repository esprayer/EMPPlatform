package com.efounder.bz.dbform.component.dc.table.render;

import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;
import javax.swing.BorderFactory;
import javax.swing.border.Border;
import javax.swing.table.JTableHeader;
import javax.swing.JLabel;
import java.awt.Component;
import java.awt.Color;

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

//public class FooterTextRender extends DefaultTableCellHeaderRenderer implements TableCellRenderer {
  public class FooterTextRender extends  JLabel implements TableCellRenderer {
   Color INNER_BORDER_COLOR = new Color(135,177,225);
    public FooterTextRender(JTable table) {
      JTableHeader header = table.getTableHeader();
      setOpaque(true);
      setBackground(header.getBackground());
      setFont(header.getFont());
      Border LINE_BORDER_BLACK = BorderFactory.createLineBorder(INNER_BORDER_COLOR);
      setBorder(LINE_BORDER_BLACK);
      setHorizontalAlignment(CENTER);
      setForeground(header.getForeground());;

    }

    public Component getTableCellRendererComponent(JTable table, Object value,
                          boolean isSelected, boolean hasFocus, int row, int column) {

      setText((value == null) ? "" : value.toString());
      return this;
    }
  }
