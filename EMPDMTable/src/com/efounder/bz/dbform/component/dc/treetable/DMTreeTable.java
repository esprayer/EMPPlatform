package com.efounder.bz.dbform.component.dc.treetable;


import java.awt.event.MouseEvent;

import javax.swing.*;
import javax.swing.table.*;

import org.jdesktop.swingx.*;
import org.jdesktop.swingx.decorator.*;
import org.jdesktop.swingx.renderer.*;
import org.jdesktop.swingx.treetable.*;

import com.efounder.builder.base.data.*;
import com.efounder.builder.meta.dctmodel.*;
import com.efounder.bz.dbform.component.*;
import com.efounder.bz.dbform.component.dc.*;
import com.efounder.bz.dbform.component.dc.table.header.*;
import com.efounder.bz.dbform.component.dc.table.render.IconTreeCellRender;
import com.efounder.bz.dbform.component.dc.table.render.TreeCellRenderManager;
import com.efounder.bz.dbform.datamodel.*;
import com.efounder.bz.dbform.datamodel.treetable.DataSetTreeTableModel;
import com.efounder.bz.dbform.datamodel.treetable.MultiDataSetTreeTableModel;

import javax.swing.tree.*;

import com.efounder.dbc.swing.tree.TreeUtils;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2009</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class DMTreeTable extends JXTreeTable implements FormComponent,DCTDataSetContainer {
	/**
	 *
	 */
	public DMTreeTable() {
		autoCreateColumnsFromModel = false;
		setHighlighters(HighlighterFactory.createSimpleStriping());
		setAutoCreateRowSorter(false);
		setRowSorter(null);
		this.setRowHeight(28);
		this.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		iconTreeCellRender = new IconTreeCellRender(this);
		setTreeCellRenderer(iconTreeCellRender);
	}
	
	IconTreeCellRender iconTreeCellRender = null;
	/**
	 *
	 * @return FormContainer
  		*/
	public FormContainer getFormContainer() {
		return null;
	}
	
	/**
	 *
	 * @return JComponent
	 */
	public JComponent getJComponent() {
		return this;
	}











	public int getColumnCount() {
		MultiDataSetTreeTableModel treeTableModel = (MultiDataSetTreeTableModel) this.getTreeTableModel();
		return treeTableModel.getColumnCount();
	}

	public String getColumnName(int column) {
		MultiDataSetTreeTableModel treeTableModel = (MultiDataSetTreeTableModel) this.getTreeTableModel();
		return treeTableModel.getColumnName(column);
	}

	public boolean isCellEditable(int row, int column) {
		return super.isCellEditable(row, column);
	}



	public void setTreeTableModel(TreeTableModel treeModel) {
		TreeTableModel oldTM = this.getTreeTableModel();
		if ( oldTM != null && oldTM instanceof DataSetTreeTableModel ) {
			((DataSetTreeTableModel)oldTM).setTreeSelectionModel(this,null);
		}
		if ( treeModel instanceof DataSetTreeTableModel ) {
			((DataSetTreeTableModel)treeModel).setTreeSelectionModel(this,getTreeSelectionModel());
			super.setTreeTableModel(treeModel);
//      super.setColumnModel(((DataSetTreeTableModel)treeModel).getColModel());
		} else {
			super.setTreeTableModel(treeModel);
		}
	}






//  /**
//   *
//   * @return ColumnModel
//   */
//  public ColumnModel getColModel() {
//    TableColumnModel columnModel = this.getColumnModel();
//    if ( columnModel != null && columnModel instanceof ColumnModel )
//      return (ColumnModel)columnModel;
//    else
//      return null;
//  }
//  /**
//   * modify
//   * @param columnModel TableColumnModel
//   */
//  public void setColumnModel(TableColumnModel columnModel) {
//    if ( columnModel == null ) {
//      super.setColumnModel(new ColumnModel());
//    } else {
//      super.setColumnModel(columnModel);
//    }
//  }
	
	/**
	 *
	 */
	protected ColumnHeaderGroupModel groupModel = null;
	
	/**
	 *
	 * @return ColumnHeaderGroupModel
	 */
	public ColumnHeaderGroupModel getGroupModel() {
		return groupModel;
	}
	/**
	 *
	 * @param groupModel ColumnHeaderGroupModel
	 */
	public void setGroupModel(ColumnHeaderGroupModel groupModel) {
		ColumnHeaderGroupModel old = this.groupModel;
		if (groupModel != old) {
			if (old != null) {
				old.removeColumnModelListener(this);
			}
			this.groupModel = groupModel;
			if ( groupModel != null )
				groupModel.addColumnModelListener(this);

			// Set the column model of the header as well.
			if (tableHeader != null && tableHeader instanceof TableGroupColumnHeader ) {
				((TableGroupColumnHeader)tableHeader).setGroupModel(groupModel);
			}
			firePropertyChange("columnModel", this.getColumnModel(), this.getColumnModel());
			resizeAndRepaint();
		}
	}
	/**
	 *
	 * @return TreeCellRenderManager
	 */
	public TreeCellRenderManager getTreeRender() {
		return treeRender;
	}
	/**
	 *
	 * @param treeRender TreeCellRenderManager
	 */
	public void setTreeRender(TreeCellRenderManager treeRender) {
		this.treeRender = treeRender;
		if( iconTreeCellRender != null ) {
			iconTreeCellRender.setScriptable(treeRender);
			iconTreeCellRender.setTitleIcon(treeRender);
		}
	}

	/**
	 *
	 * @return JTableHeader
	 */
	protected JTableHeader createDefaultTableHeader() {
		return new TableGroupColumnHeader(columnModel);
	}



	/**
	 *
	 * @return EFDataSet
	 */
	public EFDataSet getDataSet() {
		return null;
	}
	/**
	 *
	 * @return ESPRowSet
	 */
	public ESPRowSet getSelectRowSet() {
		return null;
	}
	/**
	 *
	 */
	protected DCTMetaData dctMetaData = null;
	private TreeCellRenderManager treeRender;
	/**
	 *
	 * @return DCTMetaData
	 */
	public DCTMetaData getDCTMetaData() {
		return dctMetaData;
	}
	/**
	 *
	 * @param dctMetaData DCTMetaData
	 */
	public void setDCTMetaData(DCTMetaData dctMetaData) {
		this.dctMetaData = dctMetaData;
	}
	/**
	 *
	 * @param row int
	 * @return Object
	 */
	public Object getTreeNode(int row) {
		TreePath path = super.getPathForRow(row);
		return path != null ? path.getLastPathComponent() : null;
	}
  
	public String getToolTipText(MouseEvent e) {
		String tiptextString=null;
		try{
			int rowIndex= this.rowAtPoint(e.getPoint());
			int colIndex= this.columnAtPoint(e.getPoint());
			if(rowIndex < 0 || colIndex < 0) return "";

			tiptextString = this.getStringAt(rowIndex, colIndex);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return tiptextString;
	}
}
