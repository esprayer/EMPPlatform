package com.efounder.dbc.swing.render;

import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.DefaultTableCellRenderer;

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
public class TextCellRenderer extends DefaultTableCellRenderer implements TableCellRenderer {

    /**
     *
     */
    public TextCellRenderer() {
    }

    /**
     *
     */
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        this.setText(value != null ? value.toString() : "");
        this.setToolTipText(value != null ? value.toString() : "");
        return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
    }

}
