package com.efounder.dbc.swing.render;

import java.awt.*;

import javax.swing.*;
import javax.swing.table.*;

import com.efounder.pub.util.JFNumber;

import java.math.BigDecimal;

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
public class ProgressCellRender extends JProgressBar implements TableCellRenderer {

    /**
     *
     */
    public ProgressCellRender() {
        setStringPainted(true);
        setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
        setMaximum(100);
        setMinimum(0);
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
    public Component getTableCellRendererComponent(JTable table, Object value,
        boolean isSelected, boolean hasFocus, int row, int column) {
        if (value != null && value instanceof BigDecimal) {
            double d = ((BigDecimal) value).doubleValue();
            if (d > 1) d = 1;
            setValue((int) (JFNumber.round(d, 2) * 100));
        }
        return this;
    }

}
