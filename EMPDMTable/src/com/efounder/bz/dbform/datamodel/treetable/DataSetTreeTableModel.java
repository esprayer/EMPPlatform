package com.efounder.bz.dbform.datamodel.treetable;

import java.util.*;

import javax.swing.tree.*;

import org.jdesktop.swingx.treetable.*;
import com.efounder.bz.dbform.datamodel.ColumnModel;
import com.efounder.bz.dbform.datamodel.DMComponent;
import com.efounder.builder.base.data.EFDataSet;
import com.efounder.bz.dbform.datamodel.DataSetComponent;
import com.efounder.builder.base.data.DataSetListener;
import com.efounder.builder.base.data.DataSetEvent;
import com.efounder.builder.meta.domodel.DOMetaData;
import com.efounder.builder.meta.dctmodel.DCTMetaData;
import javax.swing.table.TableColumn;
import com.efounder.bz.dbform.datamodel.DataSetComponentEvent;
import com.efounder.builder.base.data.ESPRowSet;
import com.efounder.bz.dbform.datamodel.DMColComponent;
import com.efounder.bz.dbform.datamodel.RowSetValue;
import com.efounder.bz.dbform.component.RowSetValueUtils;
import com.efounder.bz.dbform.datamodel.column.Column;
import com.efounder.builder.base.data.EFRowSet;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.ListSelectionModel;
import java.beans.PropertyChangeListener;
import javax.swing.event.TreeSelectionListener;
import javax.swing.event.TreeSelectionEvent;
import org.jdesktop.swingx.JXTreeTable;

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
public class DataSetTreeTableModel extends DefaultTreeTableModel implements DMComponent,DataSetListener,TreeSelectionListener{

	/**
	 *
	 */
	protected ColumnModel columnModel = null;
	
	/**
	 *
	 * @return ColumnModel
	 */
	public ColumnModel getColModel() {
		return columnModel;
	}

	/**
	 *
	 * @param colModel ColumnModel
	 */
	public void setColModel(ColumnModel colModel) {
//    if (this.columnModel != null) {
//      this.columnModel.setDMComponent(null);
//    }
		this.columnModel = colModel;
//    if (this.columnModel != null) {
//      this.columnModel.setDMComponent(this);
//    }
	}
	
	/**
	 *
	 */
	protected DataSetComponent dataSetComponent = null;
	
	/**
	 *
	 * @return DataSetComponent
	 */
	public DataSetComponent getDataSetComponent() {
		return dataSetComponent;
	}

	/**
	 *
	 * @param dsc DataSetComponent
	 */
	public void setDataSetComponent(DataSetComponent dsc) {
		if (dataSetComponent != dsc) {
			if (dataSetComponent != null) {
				dataSetComponent.removeDMComponent(this);
			}
			dataSetComponent = dsc;
			if (dataSetComponent != null) {
				dataSetComponent.insertDMComponent(this);
			}
		}
	}

	/**
	 *
	 */
	protected String dataSetID = null;
	/**
	 *
	 * @param dataSetID String
	 */
	public void setDataSetID(String dataSetID) {
		this.dataSetID = dataSetID;
		if (dataSetComponent != null) {
			setDataSet(dataSetComponent.getDataSet(dataSetID));
		}
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
	 * @param dataSet EFDataSet
	 */
	public void setDataSet(EFDataSet ds) {
		if (dataSet != ds) {
			// 清除掉事件监听器
			if (dataSet != null) {
				dataSet.removeDataSetListener(this);
			}
			dataSet = ds;
			// 增加事件监听器
			if (dataSet != null) {
				dataSet.addDataSetListener(this);
				DCTMetaData dctMetaData = getDCTMetaData();
				if ( dctMetaData != null )
					TreeTableUtils.createTreeTable(this,dctMetaData,dataSet,this.getColModel());
			}
		}
	}
	
	/**
	 *
	 * @return DCTMetaData
	 */
	protected DCTMetaData getDCTMetaData() {
		DataSetComponent dsc = this.getDataSetComponent();
		if ( dsc == null ) return null;
		DOMetaData[] doMetaDatas  = dsc.getDOMetaData(dataSetID);
		if ( doMetaDatas != null && doMetaDatas.length > 0 ) {
			return doMetaDatas[0].getDCTMetaData();
		}
		return null;
	}
	
	/**
	 *
	 * @param e DataSetEvent
	 */
	public void dataSetChanged(DataSetEvent e) {
		if (e.getEventType() == DataSetEvent.CURSOR) {
			cursorMoved(e);
			return;
		}
		if (e.getEventType() == DataSetEvent.INSERT) {
//        fireTableRowsInserted(e.getFirstRow(), e.getFirstRow());
			return;
//      fireTableDataSetInsertChanged();return;
		}
	}
	
	/**
	 *
 	 * @param dataSetComponentEvent DataSetComponentEvent
 	 */
	public void dataSetComponentListener(DataSetComponentEvent dataSetComponentEvent) {
	}


	protected TreeSelectionModel treeSelectionModel = null;
    /**
     *
     * @param lsm ListSelectionModel
     */
    JXTreeTable treetable = null;
    
    public void setTreeSelectionModel(JXTreeTable jxt,TreeSelectionModel lsm) {
    	treetable=jxt;
    	if (treeSelectionModel != null) {
    		treeSelectionModel.removeTreeSelectionListener(this);
    	}
    	treeSelectionModel = lsm;
    	if (treeSelectionModel != null) {
    		treeSelectionModel.addTreeSelectionListener(this);
    	}
    }

    protected boolean canSelection = true;
    
    /**
     * JTable中的SelectionModel中产生的事件
     * SelectionModel中的选择器，选定了指定的行索引
     * 与
     */
    public void valueChanged(TreeSelectionEvent e) {
    	if (!canSelection) {
    		return;
    	}
    	if (treeSelectionModel == null) {
    		return;
    	}
    	int rowIndex[] = treeSelectionModel.getSelectionRows(); //se.getLastIndex();
    	if (this.dataSet != null&&rowIndex!=null) {
    		try {
    			canCrusorMove = false;
    			dataSet.goToRow(rowIndex[0]);
    		} finally {
    			canCrusorMove = true;
    		}
    	}
    }

    protected boolean canCrusorMove = true;
    
    /**
     * 当前DCTableModel中的DataSet的游标移动到了相应的位置
     * 在此方法中接收到此事件
     * @param dse DataSetEvent
     */
    protected void cursorMoved(DataSetEvent dse) {
      if (!canCrusorMove) {
    	  return;
      }
      if (this.treeSelectionModel == null) {
    	  return;
      }
      int rowIndex = dse.getNewCursor();
      try {
    	  canSelection = false;
    	  TreePath tp=treetable.getPathForRow(rowIndex);
    	  if(tp!=null)treeSelectionModel.setSelectionPath(tp);
      }
      finally {
    	  canSelection = true;
      }
    }























    /**
     *
     */
    public DataSetTreeTableModel() {
    	this(null);
    }

    /**
     * Creates a new {@code DefaultTreeTableModel} with the specified
     * {@code root}.
     *
     * @param root  the root node of the tree
     */
    public DataSetTreeTableModel(TreeTableNode root) {
    	this(root, null);
    }

    /**
     * Creates a new {@code DefaultTreeTableModel} with the specified {@code
     * root} and column names.
     *
     * @param root  the root node of the tree
     * @param columnNames
     *            the names of the columns used by this model
     * @see #setColumnIdentifiers(List)
     */
	 public DataSetTreeTableModel(TreeTableNode root, List<?> columnNames) {
		 super(root);
		 setColumnIdentifiers(columnNames);
	 }
	 
	 /**
	  * {@inheritDoc}
	  */
	 public int getColumnCount() {
		 if ( columnModel == null ) return super.getColumnCount();
		 return columnModel.getColumnCount();
	 }

	 /**
	  * {@inheritDoc}
	  */
	 // Can we make getColumnClass final and avoid the complex DTM copy? -- kgs
	 public String getColumnName(int column) {
		 if ( columnModel == null ) super.getColumnName(column);
		 TableColumn col = getTableColumnByColumnIndex(column);
		 return (String)col.getHeaderValue();
	 }
	 
	 /**
	  *
	  * @param columnIndex int
	  * @return TableColumn
	  */
	 protected TableColumn getTableColumnByColumnIndex(int columnIndex) {
		 if (columnModel == null) {
     	 return null;
		 }
		 if (columnIndex >= columnModel.getColumnCount()) {
			 return null;
		 }
		 return columnModel.getColumn(columnIndex);
	 }
	 
	 public ESPRowSet getMainRowSet() {
		 return null;
	 }
	 
	 public Object getValueAt(Object oo, int column) {

		 // 先获取表列
//		 if(column==0)return "";
		 DataSetTreeTableNode node = (DataSetTreeTableNode)oo;
		 Column        tableColumn = (Column)this.getTableColumnByColumnIndex(column);
		 
		 // 如果为空，则直接返回为空
		 if (tableColumn == null && ! (tableColumn instanceof DMColComponent)) {
			 return null;
		 }
		 EFRowSet mainRowSet = (EFRowSet) node.getRowSet();
		 if(mainRowSet==null)return "";
		 // 获取cellValue
		 Object cellValue = RowSetValueUtils.getObject(mainRowSet, (DMColComponent) tableColumn);
		 RowSetValue rowSetValue = RowSetValue.getInstance(mainRowSet, cellValue, (Column) tableColumn);
		 EFRowSet colRowSet = getColRowSet(tableColumn.getInternalDataSetID(), tableColumn.getDataSetColID());
		 rowSetValue.setColMetaData(colRowSet);
		 // 设置TreeTableNode
		 rowSetValue.setTreeTableNode(node);
		 return rowSetValue;
   	 }
	 
	 protected EFRowSet getColRowSet(String FCT_ID, String COL_ID) {
		 if(getDataSetComponent()==null)return null;
		 DOMetaData[] doMetaDatas = this.getDataSetComponent().getDOMetaData(FCT_ID);
		 if (doMetaDatas == null || doMetaDatas.length == 0)
			 return null;
		 DOMetaData doMetaData = doMetaDatas[0];
		 return (EFRowSet) doMetaData.getColumnDefineRow(COL_ID);
    }
}
