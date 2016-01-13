package com.efounder.dbc.swing.render;

import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.JCheckBox;
import java.awt.Component;
import javax.swing.table.TableCellRenderer;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;
import java.util.Hashtable;
import java.math.BigDecimal;
import java.awt.Color;
import java.text.NumberFormat;
import javax.swing.JSpinner;

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

public class IntegerCellRender extends DefaultTableCellRenderer {

    public IntegerCellRender() {
        super();
        this.setHorizontalAlignment(JLabel.CENTER);
    }

    protected void setValue(Object value) {
        String text = "";
        if (value == null) {
            text = "";
        }
        if (value instanceof Integer) {
            Integer bigDeciValue = (Integer) value;
            setText(bigDeciValue.toString());
        } else if (value != null) {
            text = value.toString();
            setText(text);
        } else {
            setText(text);
        }
    }

}
