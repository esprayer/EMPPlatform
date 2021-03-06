package com.report.table;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;

/**
 * 此处插入类型描述。
 * 创建日期：(2002-1-17 15:56:59)
 * @author：Lubo
 */
public class FrozenColumnHeader extends javax.swing.JScrollPane {
    protected JTable mainTable;
    protected JTable headerTable;
    protected int columnCount;

    /**
     * FrozenColumnHeader 构造子注解。
     */
    public FrozenColumnHeader(JTable table, int columns) {
        super();
        mainTable = table;
        headerTable = new JTable(mainTable.getModel());
        getViewport().setView(headerTable);
        columnCount = columns;
    }
    public void addNotify() {
        TableColumn column;
        super.addNotify();
        TableColumnModel mainModel = mainTable.getColumnModel();
        TableColumnModel headerModel = new DefaultTableColumnModel();
        int frozenWidth = 0;
        for (int i = 0; i < columnCount; i++) {
            column = mainModel.getColumn(0);
            mainModel.removeColumn(column);
            headerModel.addColumn(column);
            frozenWidth += column.getPreferredWidth();

        }
        headerTable.setColumnModel(headerModel);
        Component columnHeader = getColumnHeader().getView();
        getColumnHeader().setView(null);
        JScrollPane mainScrollPane =
            (JScrollPane) SwingUtilities.getAncestorOfClass(JScrollPane.class, mainTable);
        mainScrollPane.setCorner(JScrollPane.UPPER_LEFT_CORNER, columnHeader);
        headerTable.setPreferredScrollableViewportSize(new Dimension(frozenWidth, 0));
    }
}
