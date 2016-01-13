package com.efounder.dbc.swing.render;

import javax.swing.table.*;
import java.awt.Component;
import javax.swing.UIManager;
import javax.swing.JTable;
import javax.swing.*;
import javax.swing.JTable.*;

public class PassWordCellRender
    extends JPasswordField implements TableCellRenderer {

    public PassWordCellRender() {
        super();
        setHorizontalAlignment(JLabel.LEFT);
    }

    public Component getTableCellRendererComponent(JTable table, Object value,
        boolean isSelected, boolean hasFocus, int row, int column) {
        setOpaque(true);
        if (isSelected) {
            setForeground(table.getSelectionForeground());
            setBackground(table.getSelectionBackground());
        } else {
            setForeground(table.getForeground());
            setBackground(table.getBackground());
        }
        String mess = "";
        if (value != null) {
          for (int i = 0, n = value.toString().length(); i < n; i++) {
            mess += "*";
          }
          setText(mess);
        }
        setBorder(null);
        return this;
    }
}
