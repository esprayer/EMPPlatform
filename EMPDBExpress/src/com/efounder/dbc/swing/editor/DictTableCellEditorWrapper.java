package com.efounder.dbc.swing.editor;

import java.util.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;

import com.borland.dbswing.*;
import com.borland.dx.dataset.*;
import com.efounder.bz.dbform.component.dc.table.DictTable;


public class DictTableCellEditorWrapper implements TableCellEditor {
	private TableCellEditor tableCellEditor;
	private Component editorComponent;
 	private int lastRow = -1;
 	private int row;
 	private int column;
 	private DictTable table;
 	public DictTableCellEditorWrapper(TableCellEditor tableCellEditor,DictTable table) {
 		this.tableCellEditor = tableCellEditor;
 		this.table=table;
 	}

 	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
 		if (tableCellEditor != null) {
 			editorComponent = tableCellEditor.getTableCellEditorComponent(table, value, isSelected, row, column);
 			this.row = row;
 			this.column = column;
 			return editorComponent;
 		}
 		return null;
 	}

 	public Object getCellEditorValue() {
 		if (tableCellEditor != null) {
 			return tableCellEditor.getCellEditorValue();
 		}
 		return null;
 	}

 	public boolean isCellEditable(EventObject anEvent) {
 		if (anEvent instanceof MouseEvent) {
 			Point point = ((MouseEvent) anEvent).getPoint();
 			if (((MouseEvent) anEvent).getClickCount() > 1) {
 				return tableCellEditor.isCellEditable(anEvent);
 			}
 		} else if (anEvent == null || anEvent instanceof KeyEvent) {
 			if (tableCellEditor.isCellEditable(anEvent)) {
 				SwingUtilities.invokeLater(new Runnable() {
 					public void run() {
 						editorComponent.requestFocus();
 					}
 				});
 				return true;
 			}
 		}
 		return false;
 	}

 	public boolean shouldSelectCell(EventObject anEvent) {
 		return tableCellEditor.shouldSelectCell(anEvent);
 	}
 	protected boolean isCellValid(TableCellEditor cellEditor) {
 		if (table.getDataSet() == null) {
 			return true;
 		}
// 		DBTableModel dbTableModel=(DBTableModel)table.getModel();
// 		if ((cellEditor instanceof TableMaskCellEditor && !((TableMaskCellEditor) cellEditor).isValidValue()) ||
// 				!dbTableModel.isValidValue(cellEditor.getCellEditorValue(), table.getEditingRow(), table.getEditingColumn())) {
// 			return false;
// 		}
 		return true;
 	}
 	
 	public boolean stopCellEditing() {
 		if (!isCellValid(tableCellEditor) || !tableCellEditor.stopCellEditing()) {
 			return false;
 		}
 		// moved to a new row, post DataSet row
 		if (lastRow != -1 && lastRow != row && table.getDataSet() != null) {
 			try {
 				table.getDataSet().goToRow(row);
 				lastRow = row;
 			} catch (DataSetException ex) {
 				ex.printStackTrace();
 				return false;
 			}
 		}
 		return true;
 	}

 	public void cancelCellEditing() {
 		tableCellEditor.cancelCellEditing();
 	}

 	public void addCellEditorListener(CellEditorListener l) {
 		tableCellEditor.addCellEditorListener(l);
 	}

 	public void removeCellEditorListener(CellEditorListener l) {
 		tableCellEditor.removeCellEditorListener(l);
 	}

 	public TableCellEditor getTableCellEditor() {
 		return tableCellEditor;
 	}
}

