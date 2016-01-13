package com.efounder.dbc.swing.editor;

import java.awt.*;

import javax.swing.*;

public class PassWordCellEditor extends DefaultCellEditor {

    private Object selValue;

    public PassWordCellEditor() {
        super(new JPasswordField());
    }

    public Object getCellEditorValue() {
        JPasswordField passField = (JPasswordField) getComponent();
        return passField.getText();
    }

    public Component getTableCellEditorComponent(JTable table, Object value,
                                                 boolean isSelected,
                                                 int row, int column) {
        JPasswordField password = (JPasswordField) getComponent();
        password.setBorder(null);
        if(value != null){
          password.setText(value.toString());
        }
        password.setOpaque(true);
        return password;
    }

}
