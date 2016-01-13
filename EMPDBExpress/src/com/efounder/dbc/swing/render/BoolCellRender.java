package com.efounder.dbc.swing.render;

import java.awt.*;

import javax.swing.*;
import javax.swing.table.*;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2003</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class BoolCellRender
    extends JCheckBox implements TableCellRenderer {

    private Object selValue;
    private Object unselValue;

    public BoolCellRender(Object selValue, Object unselValue) {
        super();
        this.selValue = selValue;
        this.unselValue = unselValue;
        setHorizontalAlignment(JLabel.CENTER);
    }

    public BoolCellRender() {
        this("1", "0");
    }

    public Component getTableCellRendererComponent(JTable table, Object value,
        boolean isSelected, boolean hasFocus, int row, int column) {
//        setOpaque(true);
        if (isSelected) {
            setForeground(table.getSelectionForeground());
            setBackground(table.getSelectionBackground());
        } else {
            setForeground(table.getForeground());
            setBackground(table.getBackground());
        }
        //改进以支持数值型
        value = value == null ? "" : value;
        String strVal = "";
        if (value instanceof Number) {
            strVal = String.valueOf( ( (Number) value).intValue());
        } else {
            strVal = value.toString();
        }
        if (selValue.equals(strVal))
            setSelected(true);
        else if (unselValue.equals(strVal))
            setSelected(false);
        else
            setSelected(false);
        return this;
    }
}
