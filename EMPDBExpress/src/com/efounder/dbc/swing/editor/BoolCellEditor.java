package com.efounder.dbc.swing.editor;

import java.awt.*;

import javax.swing.*;

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
public class BoolCellEditor
    extends DefaultCellEditor {

    private Object selValue;
    private Object unselValue;

    public BoolCellEditor() {
        this("1", "0");
    }

    public BoolCellEditor(Object selValue, Object unselValue) {
        super(new JCheckBox());
        JCheckBox box = (JCheckBox) getComponent();
        box.setHorizontalAlignment(JCheckBox.CENTER);
        this.selValue = selValue;
        this.unselValue = unselValue;
    }

    public Object getCellEditorValue() {
        JCheckBox box = (JCheckBox) getComponent();
        if (box.isSelected())
            return selValue;
        else
            return unselValue;
    }

    public Component getTableCellEditorComponent(JTable table, Object value,
                                                 boolean isSelected,
                                                 int row, int column) {
        JCheckBox box = (JCheckBox) getComponent();
        if (value == null) value = "";
        //改进以支持数值型
        //modified by gengeng 2009-10-14
        String strVal = "";
        if (value instanceof Number) {
            strVal = String.valueOf( ( (Number) value).intValue());
        } else {
            strVal = value.toString();
        }
        box.setSelected(selValue.equals(strVal));
        box.setBackground(table.getSelectionBackground());
        box.setForeground(table.getSelectionForeground());
        return editorComponent;
    }

}
