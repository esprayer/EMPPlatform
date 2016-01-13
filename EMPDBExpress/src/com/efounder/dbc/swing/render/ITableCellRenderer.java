package com.efounder.dbc.swing.render;

import javax.swing.*;
import javax.swing.table.*;

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
public interface ITableCellRenderer {
    public boolean canProcess(JTable table, TableCellRenderer renderer, Object value, int row, int column);
    public boolean setCellRenderer(JTable table, TableCellRenderer renderer, Object value, int row, int column);
}
