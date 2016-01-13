package com.efounder.bz.dbform.component.dc.table;

import java.beans.*;
import java.io.IOException;
import java.nio.CharBuffer;
import java.util.*;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.*;
import javax.swing.plaf.UIResource;
import javax.swing.table.*;

import org.jdesktop.swingx.*;
import org.jdesktop.swingx.decorator.*;
import org.openide.WaitingManager;

import com.efounder.builder.base.data.*;
import com.efounder.bz.dbform.component.dc.table.corner.TableCorner;
import com.efounder.bz.dbform.component.dc.table.header.TableRowHeader;
import com.efounder.bz.dbform.datamodel.*;
import com.efounder.bz.dbform.datamodel.column.*;
import com.jidesoft.plaf.LookAndFeelFactory;
import com.jidesoft.plaf.office2003.BasicOffice2003Theme;
import com.jidesoft.plaf.office2003.Office2003Painter;

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
public class DictTable extends JXTable implements DataSetListener, Runnable {
//	/**
//	 * 
//	 * @return TableModel
//	 */
//	protected static TableModel getDemoTableModel() {
//		DictModel td = new DictModel();
//		return td;
//	}

	private DictRowSorter dictRowSorter;
	/**
	 *
	 */
	public DictTable(TableModel model) {
		super(model);
		RowSorter rowSorter = new TableRowSorter(model);
		dictRowSorter = new DictRowSorter(model);
        this.setColModel(new ColumnModel());
        setColumnSelectionAllowed(false);
		setRowHeight(22);
		setAutoCreateColumnsFromModel(false);
		setAutoCreateRowSorter(true);
		setRowSorter(null);
		initializeLocalVars();
		setHighlighters(HighlighterFactory.createSimpleStriping());
		this.updateUI();
	}

	private boolean autoSel = false;

	public boolean isAutoSel() {
		return autoSel;
	}

	public void setAutoSel(boolean autoSel) {
		this.autoSel = autoSel;
	}

	// 多选时，将同本table关联的dataset里的数据设个标志，这样可以在formmodel里批量删除
	public void valueChanged(ListSelectionEvent e) {
		super.valueChanged(e);
//		if (!autoSel)
//			return;
//		if (e.getValueIsAdjusting())
//			return;
		if (getDCTableModel() == null || getDCTableModel().getDataSet() == null)
			return;
		EFDataSet eds = getDCTableModel().getDataSet();
		for (int i = 0; eds != null && i < eds.getRowCount(); i++) {
			eds.getRowSet(i).put("_ROW_SELECT_", null);// 不触发事件,否则数据多了慢
		}
		int rows[] = this.getSelectedRows();
		for (int i = 0; i < rows.length; i++) {
			if (getDCTableModel().getDataSet().getRowSet(rows[i]) != null) {
				getDCTableModel().getDataSet().getRowSet(rows[i]).put("_ROW_SELECT_", Boolean.TRUE);
				//设置选中行
				eds.goToRow(rows[i]);
			}
		}
	}

	/**
	 * 
	 * @return DCTableListSelectionModel
	 */
//	public DCTableListSelectionModel getDCTSelectionModel() {
//		ListSelectionModel lm = this.getSelectionModel();
//		if (lm != null && lm instanceof DCTableListSelectionModel)
//			return (DCTableListSelectionModel) lm;
//		else
//			return null;
//	}

	/**
	 * 
	 * @param lm  DCTableListSelectionModel
	 */
//	public void setDCTSelectionModel(DCTableListSelectionModel lm) {
//		this.setSelectionModel(lm);
//	}

	/**
	 * 
	 * @return DCTableModel
	 */
	public DictModel getDCTableModel() {
		TableModel tableModel = this.getModel();
		if (tableModel != null && tableModel instanceof DictModel)
			return (DictModel) tableModel;
		else
			return null;
	}

	/**
	 * 
	 * @param tableModel  TableModel
	 */
	public void setDCTableModel(DictModel tableModel) {
//		TableModel oldTM = this.getModel();
//		if (oldTM != null && oldTM instanceof DictModel) {
//			((DictModel) oldTM).setListSelectionModel(null);
//		}
//		setModel(tableModel);
//		if (tableModel != null)
//			tableModel.setListSelectionModel(this.getSelectionModel());
	}

	/**
	 * 
	 * @param newModel
	 *            ListSelectionModel
	 */
	public void setSelectionModel(ListSelectionModel newModel) {
		DictModel tableModel = this.getDCTableModel();
//		if (tableModel != null) {
//			tableModel.setListSelectionModel(newModel);
//		}
		super.setSelectionModel(newModel);
	}

	/**
	 * 
	 * @return ColumnModel
	 */
	public ColumnModel getColModel() {
		TableColumnModel columnModel = this.getColumnModel();
		if (columnModel != null && columnModel instanceof ColumnModel)
			return (ColumnModel) columnModel;
		else
			return null;
	}

	/**
	 * modify
	 * 
	 * @param columnModel
	 *            TableColumnModel
	 */
	public void setColModel(ColumnModel columnModel) {
		if (columnModel == null) {
			TableColumnModel cm = this.createDefaultColumnModel();
			super.setColumnModel(cm);
			return;
		}
//		// 如果有�\uFFFD�择列或是有图标�\uFFFD(系统�\uFFFD)
//		if (columnModel.isCheckBoxColumn() || columnModel.isIconColumn()) {
//			// this.setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);
//		}
		this.getDCTableModel().setColModel(columnModel);
		super.setColumnModel(columnModel);
		// 增加对列脚设置表列模�\uFFFD
//		if (getColumnFooter() != null) {
//			getColumnFooter().setColumnModel(columnModel);
//		}
	}

	/**
   *
   */
//	protected ColumnHeaderGroupModel groupModel = null;

	/**
	 * 
	 * @return ColumnHeaderGroupModel
	 */
//	public ColumnHeaderGroupModel getGroupModel() {
//		return groupModel;
//	}

	/**
	 * 
	 * @param groupModel
	 *            ColumnHeaderGroupModel
	 */
//	public void setGroupModel(ColumnHeaderGroupModel groupModel) {
//		ColumnHeaderGroupModel old = this.groupModel;
//		if (groupModel != old) {
//			if (old != null) {
//				old.removeColumnModelListener(this);
//			}
//			this.groupModel = groupModel;
//			if (groupModel != null)
//				groupModel.addColumnModelListener(this);
//
//			// Set the column model of the header as well.
//			if (tableHeader != null
//					&& tableHeader instanceof TableGroupColumnHeader) {
//				((TableGroupColumnHeader) tableHeader)
//						.setGroupModel(groupModel);
//			}
//			firePropertyChange("columnModel", this.getColModel(),
//					this.getColModel());
//			resizeAndRepaint();
//		}
//	}

//	/**
//	 * 列脚分组模型，包含多个分�\uFFFD
//	 */
//	protected ColumnFooterGroupModel groupFooterModel;
//
//	/**
//	 * 
//	 * @return ColumnHeaderGroupModel
//	 */
//	public ColumnFooterGroupModel getGroupFooterModel() {
//		return groupFooterModel;
//	}
//
//	/**
//	 * 
//	 * @param groupModel
//	 *            ColumnHeaderGroupModel
//	 */
//	public void setGroupFooterModel(ColumnFooterGroupModel groupModel) {
//		ColumnFooterGroupModel old = this.groupFooterModel;
//		if (groupModel != old) {
//			// if (old != null) old.removeColumnModelListener(this);
//			this.groupFooterModel = groupModel;
//			// if ( groupModel != null )groupModel.addColumnModelListener(this);
//			// Set the column model of the header as well.
//			if (this.getColumnFooter() != null) {
//				if (groupModel.getFooterModel() instanceof FootCalcTableModel) {
//					((FootCalcTableModel) groupModel.getFooterModel())
//							.setDataSet(this.getDataSet());
//				}
//				getColumnFooter().setGroupModel(groupModel);
//			}
//			// firePropertyChange("columnModel", this.getColModel(),
//			// this.getColModel());
//			resizeAndRepaint();
//		}
//	}
//
//	/**
//	 * 
//	 * @return FormContainer
//	 */
//	public FormContainer getFormContainer() {
//		return null;
//	}
//
//	/**
//	 * 
//	 * @return JComponent
//	 */
//	public JComponent getJComponent() {
//		return this;
//	}
//
//	/**
//	 * 
//	 * @return JTableHeader
//	 */
//	protected JTableHeader createDefaultTableHeader() {
//		return new TableGroupColumnHeader(columnModel);
//	}
//
//	/**
//   *
//   */
//	protected void configureEnclosingScrollPane() {
//		Container viewport = getParent();
//		if (viewport instanceof JViewport) {
//			Container parent = viewport.getParent();
//			if (parent instanceof JScrollPane) {
//				JScrollPane scrollPane = (JScrollPane) parent;
//				// If we've haven't been added as the scrollpane's view, then do
//				// nothing
//				if (viewport != null
//						&& ((JViewport) viewport).getView() == this) {
//					//
//					configureColumnHeader(scrollPane);
//					//
//					configureScrollPane(scrollPane);
//					//
//					configureRowHeader(scrollPane);
//					//
//					if (scrollPane instanceof JideScrollPane) {
//						configureColumnFooter((JideScrollPane) scrollPane);
//					}
//					//
//					if (this.columnHeaderVisible && this.rowHeaderVisible) {
//						configureCorner(scrollPane,
//								JScrollPane.UPPER_LEFT_CORNER);
//						// configureCorner(scrollPane,
//						// JScrollPane.UPPER_RIGHT_CORNER);
//					}
//					if (this.columnFooterVisible && this.rowHeaderVisible) {
//						configureCorner(scrollPane,
//								JScrollPane.LOWER_LEFT_CORNER);
//						// configureCorner(scrollPane,
//						// JScrollPane.LOWER_RIGHT_CORNER);
//					}
//					scrollPane.revalidate();
//					scrollPane.repaint();
//				}
//			}
//		}
//	}
//
//	/**
//	 * 
//	 * @param scrollPane
//	 *            JScrollPane
//	 * @param cornerKey
//	 *            String
//	 */
//	private void configureCorner(JScrollPane scrollPane, String cornerKey) {
//		JButton upperLeftCorner = new TableCorner(this);
//		upperLeftCorner
//				.setBorder(UIManager.getBorder("TableHeader.cellBorder"));
//		scrollPane.setCorner(cornerKey, upperLeftCorner);
//	}
//
//	/**
//	 * 
//	 * @param scrollPane
//	 *            JScrollPane
//	 */
//	private void configureScrollPane(JScrollPane scrollPane) {
//		Border border = scrollPane.getBorder();
//		if (border == null || border instanceof UIResource) {
//			scrollPane.setBorder(UIManager.getBorder("Table.scrollPaneBorder"));
//		}
//	}
//
//	/**
//	 * 
//	 * @param scrollPane
//	 *            JScrollPane
//	 */
//	private void configureRowHeader(JScrollPane scrollPane) {
//		// Add row header if visible
//		if (rowHeaderVisible) {
//			if (rowHeader == null) {
//				rowHeader = new TableRowHeader(this);
//			}
//			scrollPane.setRowHeaderView(rowHeader);
//			rowHeader.revalidate();
//			rowHeader.repaint();
//		} else {
//			scrollPane.setRowHeaderView(null);
//		}
//	}
//
//	/**
//	 * 
//	 * @param scrollPane
//	 *            JScrollPane
//	 */
//	private void configureColumnHeader(JScrollPane scrollPane) {
//		// If we're in the main viewport, then add columns and column header
//		if (columnHeaderVisible) {
//			scrollPane.setColumnHeaderView(getTableHeader());
//			getTableHeader().revalidate();
//			getTableHeader().repaint();
//		} else {
//			scrollPane.setColumnHeaderView(null);
//		}
//	}
//
//	/**
//	 * modify
//	 * 
//	 * @param scrollPane
//	 *            JideScrollPane
//	 */
//	private void configureColumnFooter(JideScrollPane scrollPane) {
//		// If we're in the main viewport, then add columns and column header
//		scrollPane.setColumnFooterView(null);
//		if (columnFooterVisible) {
//			JViewport viewport = new JViewport();
//			viewport.setView(getColumnFooter());
//			viewport.setPreferredSize(getColumnFooter().getPreferredSize());
//			scrollPane.setColumnFooter(viewport);
//			// scrollPane.getColumnFooter().setPreferredSize(new
//			// java.awt.Dimension(300,25));
//			// scrollPane.setColumnFooter(viewport);
//			getColumnFooter().revalidate();
//			getColumnFooter().repaint();
//		}
//	}
//
//	/**
//   *
//   */
//	protected TableRowHeader rowHeader = null;
//
//	/**
//	 * 
//	 * @return TableRowHeader
//	 */
//	public TableRowHeader getRowHeader() {
//		if (rowHeader == null) {
//			rowHeader = new TableRowHeader(this);
//		}
//		return rowHeader;
//	}
//
//	/**
//	 * 
//	 * @param rowHeader
//	 *            TableRowHeader
//	 */
//	public void setRowHeader(TableRowHeader rowHeader) {
//		if (this.rowHeader != null) {
//			this.rowHeader.setModel(null);
//			this.rowHeader.setCellRenderer(null);
//		}
//		this.rowHeader = rowHeader;
//		setRowHeaderVisible(rowHeader != null);
//	}
//
//	/**
//	 * modify
//	 */
//	protected TableGroupColumnFooter columnFooter = null;
//
//	/**
//	 * modify
//	 * 
//	 * @return TableColumnFooter
//	 */
//	public TableGroupColumnFooter getColumnFooter() {
//		if (columnFooter == null) {
//			columnFooter = new TableGroupColumnFooter(this.columnModel);
//			columnFooter.setDefaultRenderer(Object.class, new FooterTextRender(
//					columnFooter));
//			columnFooter.setAutoCreateColumnsFromModel(false);
//		}
//		return columnFooter;
//	}
//
//	/**
//	 * 
//	 * @param columnFooter
//	 *            TableColumnFooter
//	 */
//	public void setColumnFooter(TableGroupColumnFooter columnFooter) {
////		if (this.columnFooter != columnFooter) {
////			TableGroupColumnFooter old = this.columnFooter;
////			// Release the old footer
////			if (old != null) {
////				old.setTable(null);
////			}
////			this.columnFooter = columnFooter;
////			if (columnFooter != null) {
////				columnFooter.setTable(this);
////			}
////			firePropertyChange("columnFooter", old, columnFooter);
////		}
//	}

	/**
	 * 
	 * @return JideScrollPane
	 */
	protected JSplitPane getJideScrollPane() {
		Container viewport = getParent();
		if (viewport instanceof JViewport) {
			Container parent = viewport.getParent();
			if (parent instanceof JSplitPane) {
				return (JSplitPane) parent;
			}
		}
		return null;
	}

	/**
   *
   */
	protected boolean columnHeaderVisible = true;

	/**
	 * 
	 * @param columnHeaderVisible
	 *            boolean
	 */
	public void setColumnHeaderVisible(boolean columnHeaderVisible) {
//		this.columnHeaderVisible = columnHeaderVisible;
//		if (isDisplayable()) {
//			Container viewport = getParent();
//			if (viewport instanceof JViewport) {
//				Container parent = viewport.getParent();
//				if (parent instanceof JScrollPane) {
//					JScrollPane scrollPane = (JScrollPane) parent;
//					if (viewport != null
//							&& ((JViewport) viewport).getView() == this) {
//						if (columnHeaderVisible) {
//							scrollPane.setColumnHeaderView(getTableHeader());
//							if (this.rowHeaderVisible)
//								this.configureCorner(scrollPane,
//										JScrollPane.UPPER_LEFT_CORNER);
//						} else {
//							scrollPane.setColumnHeaderView(null);
//							scrollPane.setCorner(JScrollPane.UPPER_LEFT_CORNER,
//									null);
//						}
//					}
//				}
//			}
//		}
//		firePropertyChange("columnHeaderVisible", !columnHeaderVisible,
//				columnHeaderVisible);
	}

	/**
	 * <p>
	 * Returns <code>true</code> if the column header is visible.
	 * </p>
	 * 
	 * @return If <code>true</code>, the column header is visible.
	 * @see #setColumnHeaderVisible
	 */
	public boolean isColumnHeaderVisible() {
		return columnHeaderVisible;
	}

	/**
   *
   */
	protected boolean rowHeaderVisible = true;

	/**
	 * 
	 * @return boolean
	 */
	public boolean isRowHeaderVisible() {
		return rowHeaderVisible;
	}

	/**
	 * 
	 * @param rowHeaderVisible
	 *            boolean
	 */
	public void setRowHeaderVisible(boolean rowHeaderVisible) {
//		this.rowHeaderVisible = rowHeaderVisible;
//		if (isDisplayable()) {
//			Container viewport = getParent();
//			if (viewport instanceof JViewport) {
//				Container parent = viewport.getParent();
//				if (parent instanceof JScrollPane) {
//					JScrollPane scrollPane = (JScrollPane) parent;
//					if (viewport != null
//							&& ((JViewport) viewport).getView() == this) {
//						// Add row header if visible
//						if (rowHeaderVisible) {
//							scrollPane.setRowHeaderView(this.getRowHeader());
//							if (this.columnHeaderVisible)
//								this.configureCorner(scrollPane,
//										JScrollPane.UPPER_LEFT_CORNER);
//							if (this.columnFooterVisible)
//								this.configureCorner(scrollPane,
//										JScrollPane.LOWER_LEFT_CORNER);
//						} else {
//							scrollPane.setRowHeaderView(null);
//							scrollPane.setCorner(JScrollPane.UPPER_LEFT_CORNER,
//									null);
//							scrollPane.setCorner(JScrollPane.LOWER_LEFT_CORNER,
//									null);
//						}
//					}
//				}
//			}
//		}
//		firePropertyChange("rowHeaderVisible", !rowHeaderVisible,
//				rowHeaderVisible);
	}

	/**
   *
   */
	protected boolean columnFooterVisible = true;

//	/**
//	 * 
//	 * @param columnHeaderVisible
//	 *            boolean
//	 */
//	public void setColumnFooterVisible(boolean columnFooterVisible) {
//		this.columnFooterVisible = columnFooterVisible;
//		if (isDisplayable()) {
//			Container viewport = getParent();
//			if (viewport instanceof JViewport) {
//				Container parent = viewport.getParent();
//				if (parent instanceof JideScrollPane) {
//					JideScrollPane scrollPane = (JideScrollPane) parent;
//					if (viewport != null
//							&& ((JViewport) viewport).getView() == this) {
//						if (columnFooterVisible) {
//							this.configureColumnFooter(scrollPane);
//							// scrollPane.setColumnFooterView(this.getColumnFooter());
//							if (this.rowHeaderVisible)
//								this.configureCorner(scrollPane,
//										JScrollPane.LOWER_LEFT_CORNER);
//						} else {
//							scrollPane.setColumnFooterView(null);
//							scrollPane.setCorner(JScrollPane.LOWER_LEFT_CORNER,
//									null);
//						}
//					}
//				}
//			}
//		}
//		firePropertyChange("columnFooterVisible", !columnFooterVisible,
//				columnFooterVisible);
//	}
//
//	/**
//	 * modify
//	 * 
//	 * @return TableGroupColumnFooter
//	 */
//	protected TableGroupColumnFooter createDefaultColumnFooter() {
//		return this.getColumnFooter();
//	}

	/**
	 * <p>
	 * Returns <code>true</code> if the column header is visible.
	 * </p>
	 * 
	 * @return If <code>true</code>, the column header is visible.
	 * @see #setColumnHeaderVisible
	 */
	public boolean isColumnFooterVisible() {
		return columnFooterVisible;
	}

	/**
   *
   */
	protected void initializeLocalVars() {
		setOpaque(true);
		createDefaultRenderers();
		createDefaultEditors();

		setTableHeader(createDefaultTableHeader());
//		setColumnFooter(createDefaultColumnFooter());
		setShowGrid(true);
		// setAutoResizeMode(AUTO_RESIZE_OFF);
		setRowHeight(21); // will actually get overwritten by more accurate
							// value, if possible, in addNotify()
		setRowMargin(1);
		// modified by lchong
		setRowSelectionAllowed(true);
		// this.setRolloverEnabled(false);

		// modified end
		setColumnSelectionAllowed(false);

		// setCellSelectionEnabled(true);
		setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		setCellEditor(null);
		setEditingColumn(-1);
		setEditingRow(-1);
		setPreferredScrollableViewportSize(new Dimension(450, 400));

		// I'm registered to do tool tips so we can draw tips for the renderers
		ToolTipManager toolTipManager = ToolTipManager.sharedInstance();
		toolTipManager.registerComponent(this);

		setAutoscrolls(true);

//		addPropertyChangeListener(this);
	}

	/**
	 * 
	 * @param e
	 *            PropertyChangeEvent
	 */
	public void propertyChange(PropertyChangeEvent e) {
		if (e.getPropertyName().equals("columnModel")) {
//			Object columnModel;
//			if ((columnModel = e.getOldValue()) != null) {
//				((TableColumnModel) columnModel).getSelectionModel()
//						.removeListSelectionListener(this);
//			}
//			if ((columnModel = e.getNewValue()) != null) {
//				((TableColumnModel) columnModel).getSelectionModel()
//						.addListSelectionListener(this);
//			}
//		}
//		if (this.rowHeader != null) {
//			this.rowHeader.validate();
//			this.rowHeader.repaint();
//		}
//		if (this.tableHeader != null) {
//			this.tableHeader.validate();
//			this.tableHeader.repaint();
//		}
//		if (this.columnFooter != null) {
//			this.columnFooter.validate();
//			this.columnFooter.repaint();
		}
	}

	protected void configureEnclosingScrollPane() {
		Container viewport = getParent();
	    if (viewport instanceof JViewport) {
	    	Container parent = viewport.getParent();
	    	if (parent instanceof JScrollPane) {
	    		JScrollPane scrollPane = (JScrollPane) parent;
	    		// If we've haven't been added as the scrollpane's view, then do nothing
	    		if (viewport != null && ((JViewport) viewport).getView() == this) {
	    			//
	    			configureColumnHeader(scrollPane);
	    			//
	    			configureScrollPane(scrollPane);
	    			//
	    			configureRowHeader(scrollPane);
	    			//
	    			if ( this.columnHeaderVisible && this.rowHeaderVisible ) {
	    				configureCorner(scrollPane, JScrollPane.UPPER_LEFT_CORNER);
	    			}
	    			if ( this.columnFooterVisible && this.rowHeaderVisible ) {
	    				configureCorner(scrollPane, JScrollPane.LOWER_LEFT_CORNER);
	    			}
	    			scrollPane.revalidate();
	    			scrollPane.repaint();
	    			scrollPane.setVisible(false);
	    			scrollPane.setVisible(true);
	    			this.setVisible(false);
	    			this.setVisible(true);
	    		}
	    	}
	    }
	}
	 
	/**
	 *
	 * @param scrollPane JScrollPane
	 */
	private void configureColumnHeader(JScrollPane scrollPane) {
		// If we're in the main viewport, then add columns and column header
	    if (columnHeaderVisible) {
	    	scrollPane.setColumnHeaderView(getTableHeader());
	    	getTableHeader().revalidate();
	    	getTableHeader().repaint();
	    } else {
	    	scrollPane.setColumnHeaderView(null);
	    }
	}
 
	/**
	 *
	 * @param scrollPane JScrollPane
	 */
	private void configureScrollPane(JScrollPane scrollPane) {
		Border border = scrollPane.getBorder();
	    if (border == null || border instanceof UIResource) {
	    	scrollPane.setBorder(UIManager.getBorder("Table.scrollPaneBorder"));
	    }
	}
	  
	/**
	 *
	 */
	protected TableRowHeader rowHeader = null;
	  
	/**
	 *
	 * @return TableRowHeader
	 */
	public TableRowHeader getRowHeader() {
		if ( rowHeader == null ) {
	      rowHeader = new TableRowHeader(this);
	    }
	    return rowHeader;
	}
	  
	/**
	 *
	 * @param scrollPane JScrollPane
	 */
	private void configureRowHeader(JScrollPane scrollPane) {
		// Add row header if visible
	    if (rowHeaderVisible) {
	    	if (rowHeader == null) {
	    		rowHeader = new TableRowHeader(this);
	    	}
	    	scrollPane.setRowHeaderView(rowHeader);
	    	rowHeader.revalidate();
	    	rowHeader.repaint();
	    } else {
	    	scrollPane.setRowHeaderView(null);
	    }
	}
	  
	/**
	 *
	 * @param scrollPane JScrollPane
	 * @param cornerKey String
	 */
	private void configureCorner(JScrollPane scrollPane,String cornerKey) {
	    JButton upperLeftCorner = new TableCorner(this);
	    upperLeftCorner.setBorder(UIManager.getBorder("TableHeader.cellBorder"));
	    scrollPane.setCorner(cornerKey, upperLeftCorner);
	}
	  
	public void tableChanged(TableModelEvent e) {
		super.tableChanged(e);
		if (e.getType() == DataSetEvent.DELETE) {
			if (e.getFirstRow() > 0)
				setRowSelectionInterval(e.getFirstRow() - 1, e.getFirstRow() - 1);
		}
		if (this.rowHeader != null) {
			this.rowHeader.validate();
			this.rowHeader.repaint();
		}
		if (this.tableHeader != null) {
			this.tableHeader.validate();
			this.tableHeader.repaint();
		}
//		if (this.columnFooter != null) {
//			this.columnFooter.validate();
//			this.columnFooter.repaint();
//		}
		Container viewport = getParent();
		if (viewport instanceof JViewport) {
			Container parent = viewport.getParent();
			if (parent instanceof JScrollPane) {
				JScrollPane scrollPane = (JScrollPane) parent;
				if (scrollPane.getCorner(JScrollPane.UPPER_LEFT_CORNER) != null) {
					scrollPane.getCorner(JScrollPane.UPPER_LEFT_CORNER).setPreferredSize(new Dimension(this.getRowHeader().getWidth(), this.getTableHeader().getHeight()));
					scrollPane.getCorner(JScrollPane.UPPER_LEFT_CORNER).validate();
					scrollPane.getCorner(JScrollPane.UPPER_LEFT_CORNER).repaint();
				}
			}
		}
	}

//	/**
//	 * 
//	 * @return ESPRowSet
//	 */
//	public ESPRowSet getSelectRowSet() {
//		return null;
//	}
//
//	/**
//   *
//   */
//	protected DCTMetaData dctMetaData = null;
//
//	/**
//	 * 
//	 * @return DCTMetaData
//	 */
//	public DCTMetaData getDCTMetaData() {
//		return dctMetaData;
//	}
//
//	/**
//	 * 
//	 * @param dctMetaData
//	 *            DCTMetaData
//	 */
//	public void setDCTMetaData(DCTMetaData dctMetaData) {
//		this.dctMetaData = dctMetaData;
//	}

	/**
	 * 
	 * @param selectionMode   int
	 */
	public void setSelectionMode(int selectionMode) {
		super.setSelectionMode(selectionMode);
	}

	/**
   *
   */
//	protected DataSetComponent dataSetComponent = null;
//
//	/**
//	 * 
//	 * @return DataSetComponent
//	 */
//	public DataSetComponent getDataSetComponent() {
//		return dataSetComponent;
//	}

	/**
	 * 
	 * @param dsc
	 *            DataSetComponent
	 */
//	public void setDataSetComponent(DataSetComponent dsc) {
////		if (dataSetComponent != dsc) {
////			if (dataSetComponent != null) {
////				dataSetComponent.removeDMComponent(this);
////			}
////			dataSetComponent = dsc;
////			if (dataSetComponent != null) {
////				dataSetComponent.insertDMComponent(this);
////			}
////		}
//	}

	/**
   *
   */
	protected String dataSetID = null;

	/**
	 * 
	 * @param dataSetID
	 *            String
	 */
	public void setDataSetID(String dataSetID) {
//		this.dataSetID = dataSetID;
//		if (dataSetComponent != null) {
//			setDataSet(dataSetComponent.getDataSet(dataSetID));
//		}
	}

	/**
	 * 
	 * @return String
	 */
	public String getDataSetID() {
		return dataSetID;
	}

	/**
   *
   */
	protected EFDataSet dataSet = null;

	/**
	 * 
	 * @return EFDataSet
	 */
	public EFDataSet getDataSet() {
		return dataSet;
	}

	/**
	 * 
	 * @param dataSet
	 *            EFDataSet
	 */
	public void setDataSet(EFDataSet ds) {
		if (dataSet != ds) {
			// 清除掉事件监听器
			if (dataSet != null) {
				dataSet.removeDataSetListener(this);
			}
			dataSet = ds;
			// 增加事件监听�\uFFFD
			if (dataSet != null) {
				dataSet.addDataSetListener(this);
			}
//			if (getGroupFooterModel() != null
//					&& getGroupFooterModel().getFooterModel() instanceof FootCalcTableModel) {
//				((FootCalcTableModel) getGroupFooterModel().getFooterModel())
//						.setDataSet(dataSet);
//			}
		}
	}

	/**
	 * 
	 * @return ESPRowSet
	 */
	public ESPRowSet getMainRowSet() {
		return null;
	}

	/**
	 * 
	 * @param dataSetComponentEvent
	 *            DataSetComponentEvent
	 */
//	public void dataSetComponentListener(DataSetComponentEvent dataSetComponentEvent) {
//		if (dataSetComponentEvent.getEventType() == DataSetComponentEvent.DSC_EVENT_START_EDIT) {
//
//			return;
//		}
//		if (dataSetComponentEvent.getEventType() == DataSetComponentEvent.DSC_EVENT_STOP_EDIT
//				|| dataSetComponentEvent.getEventType() == DataSetComponentEvent.DSC_EVENT_END_EDIT) {
//			// 结束当前编缉
//			this.stopEditing();
//			return;
//		}
//	}

	/**
   *
   */
	public void stopEditing() {
		// Take in the new value
		TableCellEditor editor = getCellEditor();
		if (editor != null) {
			Object value = editor.getCellEditorValue();
			//
			getModel().setValueAt(value, convertRowIndexToModel(editingRow), convertColumnIndexToModel(editingColumn));
			// setValueAt(value, editingRow, editingColumn);
			removeEditor();
		}
	}

	/**
	 * 
	 * @param e
	 *            DataSetEvent
	 */
	public void dataSetChanged(DataSetEvent e) {
		if (e.getEventType() == DataSetComponentEvent.DSC_EVENT_END_EDIT) {
			this.stopEditing();
		}
	}

	/**
	 * 
	 * @param row
	 *            int
	 * @param column
	 *            int
	 * @param e
	 *            EventObject
	 * @return boolean
	 */
	public boolean editCellAt(int row, int column, EventObject e) {
		boolean value = super.editCellAt(row, column, e);
		if (value) {
			/**
			 *
			 */
//			if (cellEditor != null && cellEditor instanceof TableCellEditorManager) {
//				TableCellEditorManager editorManager = (TableCellEditorManager) cellEditor;
//				editorManager.prepareEditor(editorManager, editorComp, row, column);
//			}
		}
		return value;
	}

	public Component prepareEditor(TableCellEditor editor, int row, int column) {
		Object value = getValueAt(row, column);
		boolean isSelected = isCellSelected(row, column);
		Component comp = editor.getTableCellEditorComponent(this, value,
				isSelected, row, column);
		if (comp instanceof JComponent) {
			JComponent jComp = (JComponent) comp;
			if (jComp.getNextFocusableComponent() == null) {
				jComp.setNextFocusableComponent(this);
			}
		}
		return comp;
	}

	public void keyTyped(KeyEvent e) {
	}

	public void keyPressed(KeyEvent e) {
//		if (e.getKeyCode() == KeyEvent.VK_ENTER)
//			e.setKeyCode(KeyEvent.VK_TAB);
//		if (e.getKeyCode() == KeyEvent.VK_ENTER
//				|| e.getKeyCode() == KeyEvent.VK_TAB) {
//			int row = this.getSelectedRow();
//			int col = getSelectedColumn();
//			int coln = this.getColumnCount() - 1;
//			while (coln >= 0
//					&& getColumnModel().getColumn(coln).getWidth() == 0)
//				coln--;
//			boolean cedit = true;
//			if (this.getModel() instanceof DictModel)
//				cedit = ((DictModel) getModel()).isEnableDataEdit();
//			if (cedit && isEditable() && row == this.getRowCount() - 1
//					&& col == coln) {
//				DictModel model = this.getDCTableModel();
//				model.getDataSet().insertRowSet(EFRowSet.getInstance());
//				// if(col==getColumnCount()-1){
//				setColumnSelectionInterval(0, 0);
//				this.setRowSelectionInterval(row + 1, row + 1);
//				editCellAt(row + 1, 0);
//				e.setKeyCode(0);
//				// }
//			}
//		}
	}

	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ENTER || e.getKeyCode() == KeyEvent.VK_TAB || e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			e.setKeyCode(0);
		}

	}

	public void changeSelection(int rowIndex, int columnIndex, boolean toggle, boolean extend) {
		Component editComponent;
		super.changeSelection(rowIndex, columnIndex, toggle, extend);
		if (rowIndex == -1) return;
		 this.setRowSelectionInterval(rowIndex,rowIndex);
		if (this.isEditing()) return;
		int rown = this.getSelectedRow();
		int[] rows = getSelectedRows();
		if (rows != null && rows.length > 1) {
			rown = rows[rows.length - 1];
			((DictModel)this.getModel()).getDataSet().goToRow(rown);
		}			
		int coln = this.getSelectedColumn();
		if (coln >= 0) {
			int count = getColumnCount();
			while (coln < count && getColumnModel().getColumn(coln).getWidth() == 0)
				coln++;
			if (coln >= count && rown + 1 < getRowCount()) {
				changeSelection(rown + 1, 0, toggle, extend);
				return;
			}

			if (rown >= 0 && coln >= 0 && coln < count) {
				if (coln != getSelectedColumn())
					this.setColumnSelectionInterval(coln, coln);
				this.setEditingRow(rown);
				this.setEditingColumn(coln);
				this.editCellAt(rown, coln);
//				this.scrollCellToVisible(rown, coln);
				this.setRequestFocusEnabled(true);
				editComponent = this.getEditorComponent();
				if (editComponent != null) {
					editComponent.requestFocus();
				}
			}
		}
	}

	/**
	 * 可排序否
	 */
	private boolean columnSortable;

	/**
	 * 
	 * @param sortable  boolean
	 */
	public void setColumnSortable(boolean sortable) {
		columnSortable = sortable;
	}

	/**
   *
   */
	public boolean isColumnSortable() {
		return columnSortable;
	}

	/**
   *
   */
	public void mouseClicked(MouseEvent e) {
		if (e.getClickCount() == 2 && e.getButton() == e.BUTTON1) {
			// 左键双击列排序
			if (e.getSource() == getTableHeader() && isColumnSortable()) {
				int columnIndex = columnAtPoint(e.getPoint());
				sortColumn = getColModel().getColumnByModelIndex(columnIndex);
				WaitingManager.getDefault().waitInvoke(this, "提示",
						"正在按列[" + sortColumn.getHeaderValue() + "]进行排序.......",
						null, this);

			}
		}
	}

	Column sortColumn = null;

	protected void sort(Column column) {
		// EFDataSet
//		EFDataSet ds = getDCTableModel().getDataSet();
//		DataSetListener dsl[] = ds.getTableModelListeners();
//		try {
//			for (int i = 0; dsl != null && i < dsl.length; i++)
//				// 禁止前面刷新，要不排一条刷一条，难看
//				ds.removeDataSetListener(dsl[i]);
//			// 设置rowSet在排序前的索引
//			if (column.getSortOrder() == column.UNSORTED)
//				DataSetUtils.setRowSetIndex(ds, column.getDataSetColID());
//			// 设置列的排序方式
//			column.convertSortOrder();
//			// 设置列的渲染器
//			column.convertCellRenderer();
//			// 记录排序列
//			getColModel().setSortColumn(column);
//			// 冒泡排序
//			sortEFDataSet(ds, getColModel().getSortColumnList());
//			// 如果是取消排序，恢复rowSet排序前的索引
//			if (column.getSortOrder() == column.UNSORTED)
//				DataSetUtils.recoverRowSetIndex(ds, column.getDataSetColID());
//		} finally {
//			for (int i = 0; dsl != null && i < dsl.length; i++)
//				ds.addDataSetListener(dsl[i]);
//			updateUI();
//		}
	}

	/**
	 * 
	 * @param ds EFDataSet
	 * @param sortColumn List
	 */
	protected void sortEFDataSet(EFDataSet ds, java.util.List<Column> sortColumn) {
//		String[] columns = new String[sortColumn.size()];
//		Object[] compar = new Object[sortColumn.size()];
//		boolean[] ascend = new boolean[sortColumn.size()];
//		for (int i = 0; i < sortColumn.size(); i++) {
//			Column col = sortColumn.get(i);
//			String name = col.getDataSetColID();
//			// 使用内部数据排序
//			if (col.isUserInternalDataSetID())
//				name = col.getInternalDataSetID() + "." + name;
//			columns[i] = name;
//			ascend[i] = col.ASCENDING == col.getSortOrder();
//			if (col.getClientProperty("Comparator") != null)
//				compar[i] = col.getClientProperty("Comparator");
//		}
//		DataSetUtils.sortEFDataSet(ds, columns, ascend, compar);
	}

	/**
   *
   */
	public void mousePressed(MouseEvent e) {
	}

	/**
   *
   */
	public void mouseReleased(MouseEvent e) {
	}

	/**
   *
   */
	public void mouseEntered(MouseEvent e) {
	}

	/**
   *
   */
	public void mouseExited(MouseEvent e) {
	}

	public static String SAP_STD_LOOKANDFEEL = "com.sap.plaf.frog.FrogLookAndFeel";

//	/**
//     * Returns the background color for selected cells.
//     *
//     * @return the <code>Color</code> used for the background of selected list items
//     * @see #setSelectionBackground
//     * @see #setSelectionForeground
//     */
//    public Color getSelectionBackground() {
//        return Color.RED;
//    }
    
	/**
   *
   */
	// public void updateUI() {
	// LookAndFeel lf = UIManager.getLookAndFeel();
	// try {
	// UIManager.setLookAndFeel(SAP_STD_LOOKANDFEEL);
	// super.updateUI();
	// } catch ( Exception ex ) {
	// ex.printStackTrace();
	// } finally {
	// try {
	// UIManager.setLookAndFeel(lf);
	// }
	// catch (UnsupportedLookAndFeelException ex1) {
	// }
	// }
	// invalidate();
	// }

	/**
	 * add by lchong at 2011-4-15 单元格内容提示
	 * 
	 * @param e
	 *            MouseEvent
	 * @return String
	 */
//	public String getToolTipText(MouseEvent e) {
//		String tiptextString = null;
//		try {
//			int rowIndex = this.rowAtPoint(e.getPoint());
//			int colIndex = this.columnAtPoint(e.getPoint());
//			if (rowIndex < 0 || colIndex < 0)
//				return "";
//
//			// 从自定义脚本中获取单元格提示内容
//			tiptextString = getCusToolTipText(rowIndex, colIndex);
//			if (tiptextString == null)
//				tiptextString = this.getStringAt(rowIndex, colIndex);
//
//		} catch (Exception ex) {
//			ex.printStackTrace();
//		}
//		return tiptextString;
//	}

//	public String getCusToolTipText(int row, int col) throws Exception {
////		return (String) runScript(this, "getToolTipText", new String[] {
////				"tableObj", "row", "col" }, new Object[] { this, row, col });
//
//	}

	public int read(CharBuffer cb) throws IOException {
		// TODO Auto-generated method stub
		return 0;
	}

	public void run() {
		// TODO Auto-generated method stub
		sort(this.sortColumn);
	}
}