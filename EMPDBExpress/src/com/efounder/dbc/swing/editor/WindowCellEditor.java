package com.efounder.dbc.swing.editor;

import java.awt.*;

import javax.swing.*;

import com.efounder.buffer.*;
import com.efounder.builder.base.data.*;
import com.efounder.dctbuilder.data.*;
import com.efounder.eai.data.*;

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
public class WindowCellEditor extends DefaultCellEditor {

    protected ColumnMetaData cmd;
    protected PopupEditComponent editcomp;

    /**
     *
     * @param cmd ColumnMetaData
     */
    public WindowCellEditor(ColumnMetaData cmd) {
        super(new JTextField());
        this.cmd = cmd;
        editcomp = new PopupEditComponent((JTextField) editorComponent, cmd);
    }

    /**
     *
     * @return Object
     */
    public Object getCellEditorValue() {
        return super.getCellEditorValue();
    }

    /**
     *
     * @return Component
     */
    public Component getComponent() {
        return editcomp;
    }

    /**
     *
     * @param table JTable
     * @param value Object
     * @param isSelected boolean
     * @param row int
     * @param column int
     * @return Component
     */
    public Component getTableCellEditorComponent(JTable table, Object value,
                                                 boolean isSelected,
                                                 int row, int column) {
        super.getTableCellEditorComponent(table, value, isSelected, row, column);
        return editcomp;
    }

}
