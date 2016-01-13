package com.report.table;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;
import java.util.ResourceBundle;

public class GroupableHeaderTableCellRenderer extends DefaultTableCellRenderer {
  static ResourceBundle res = ResourceBundle.getBundle("com.report.table.Language");
                public Component getTableCellRendererComponent(
                    JTable table,
                    Object value,
                    boolean isSelected,
                    boolean hasFocus,
                    int row,
                    int column) {
                    JTableHeader header = table.getTableHeader();
                    if (header != null) {
                        setForeground(header.getForeground());
                        setBackground(header.getBackground());
                        setFont(header.getFont());
                    }
                    setHorizontalAlignment(JLabel.CENTER);
                    //setVerticalAlignment(JLabel.BOTTOM);
                    setText((value == null) ? "" : value.toString());
                    setBorder(UIManager.getBorder("TableHeader.cellBorder"));
                    return this;
                }

}
