package com.efounder.dbc.swing.editor;

import javax.swing.JTable;
import javax.swing.DefaultCellEditor;
import java.awt.Component;
import javax.swing.JComboBox;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2008</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */

public class MonthCellEditor extends DefaultCellEditor {

    public MonthCellEditor() {
        super(new JComboBox());
        JComboBox box = (JComboBox) editorComponent;
        createBox(box, getData());
    }

    protected void createBox(JComboBox box, Object[] objs) {
        if (objs != null) {
            for (int i = 0; i < objs.length; i++) {
                box.addItem(objs[i]);
            }
        }
    }

    protected String[] getData() {
        String[] data = new String[12];
        for (int i = 1; i <= data.length; i++) {
            if (i < 10) {
                data[i - 1] = "0" + i;
            } else {
                data[i - 1] = "" + i;
            }
        }
        return data;
    }

    public Object getCellEditorValue() {
        JComboBox box = (JComboBox) editorComponent;
        return box.getSelectedItem();
    }

    public Component getTableCellEditorComponent(JTable table, Object value,
                                                 boolean isSelected,
                                                 int row, int column) {
        return super.getTableCellEditorComponent(table, value, isSelected, row,
                                                 column);
    }
}
