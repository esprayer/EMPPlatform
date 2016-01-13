package com.efounder.dbc.swing.editor;

import javax.swing.AbstractCellEditor;
import javax.swing.table.TableCellEditor;
import javax.swing.JSpinner;
import javax.swing.SpinnerListModel;
import java.awt.Component;
import javax.swing.JTable;
import java.util.EventObject;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;

public class IntegerCellEditor extends AbstractCellEditor
            implements TableCellEditor {
        final JSpinner spinner = new JSpinner();

        // Initializes the spinner.
        public IntegerCellEditor(String[] items) {
            spinner.setModel(new SpinnerListModel(java.util.Arrays.asList(items)));
        }

        // Prepares the spinner component and returns it.
        public Component getTableCellEditorComponent(JTable table, Object value,
                boolean isSelected, int row, int column) {
            int intVal = 0;
            if (value instanceof BigDecimal) {
                intVal = ( (BigDecimal) value).intValue();
            }
            spinner.setValue(new Integer(intVal));
            return spinner;
        }

        // Enables the editor only for double-clicks.
        public boolean isCellEditable(EventObject evt) {
            if (evt instanceof MouseEvent) {
                return ((MouseEvent)evt).getClickCount() >= 2;
            }
            return true;
        }

        // Returns the spinners current value.
        public Object getCellEditorValue() {
            return spinner.getValue();
        }
    }
