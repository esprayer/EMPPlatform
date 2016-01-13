package com.efounder.bz.dbform.datamodel.treetable;

import java.util.List;

import com.client.test.treetable.MyTreeNode;
import com.efounder.builder.base.data.*;
import com.efounder.bz.dbform.datamodel.*;
import com.efounder.dbc.swing.table.ICellEditable;
import com.efounder.dctbuilder.mdl.DictPluginManager;

import org.jdesktop.swingx.table.TableColumnExt;
import org.jdesktop.swingx.treetable.TreeTableNode;

import javax.swing.event.ListSelectionEvent;

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
public class MultiDataSetTreeTableModel extends DCTreeTableModel {
	private String                          dsids = "";
	protected              String   userPluginKey = "";
	protected   DictPluginManager             dpm = new DictPluginManager();
	
	public MultiDataSetTreeTableModel() {
	}
	public Object getValueAt(Object node, int column) {
		if (column < 0 || column >= getColumnCount()) {
			return "";
		}
		return super.getValueAt(node,column);
	}
	public boolean isCellEditable(Object node, int column) {
		TableColumnExt tc=getColModel().getColumnExt(column);
		return tc.isEditable();
	}
	public void setDataSet(EFDataSet ds) {
		if (dataSet != ds) {
			// 清除掉事件监听器
			if (dataSet != null) {
				dataSet.removeDataSetListener(this);
			}
			dataSet = ds;
			// 增加事件监听器
			String[]strs=dsids.split(",");
			if (dataSet != null&&dsids!=null&&strs.length>0) {
				dataSet.addDataSetListener(this);
				TreeTableUtils.createTreeTable(this,strs,dataSet,getColModel());
			}
		}
	}

	public void setDsids(String s) {
		this.dsids = s;
		String[]strs=s.split(",");
		if (dataSet != null && strs != null && strs.length > 0) {
			TreeTableUtils.createTreeTable(this, strs, dataSet, getColModel());
		}
	 }

	public String getDsids() {
		return dsids;
	}
	protected boolean canCrusorMove = true;
	public void valueChanged(ListSelectionEvent e) {
		if(dsids.indexOf(",")!=-1)return;//只有一级时才OK
		int rowIndex=e.getFirstIndex();
		if (this.dataSet != null) {
			try {
				canCrusorMove = false;
				dataSet.goToRow(rowIndex);
			} finally {
				canCrusorMove = true;
			}
		}
	}

	public Object getChild(Object node, int index) {
		DataSetTreeTableNode treenode = (DataSetTreeTableNode) node;
		return treenode.getChildAt(index);
	}

	public int getChildCount(Object parent) {
		DataSetTreeTableNode treenode = (DataSetTreeTableNode) parent;
		return treenode.getChildCount();
	}

	public int getIndexOfChild(Object parent, Object child) {
		DataSetTreeTableNode treenode = (DataSetTreeTableNode) parent;
		for (int i = 0; i > treenode.getChildCount(); i++) {
			if (treenode.getChildAt(i) == child) {
				return i;
			}
		}

		return 0;
	}

	public boolean isLeaf(Object node) {
		DataSetTreeTableNode treenode = (DataSetTreeTableNode) node;
		if (treenode.getChildCount() > 0) {
			return false;
		}
		return true;
	}
	
	public boolean isCellEditable(int row, int col) {
		List list = getUserPlugin(ICellEditable.class, DictPluginManager.DATASETEVENT);
		for (int i = 0; i < list.size(); i++) {
			ICellEditable ifp = (ICellEditable) list.get(i);
			if (!ifp.isCellEditable(this, row, col))
				return false;
		}
		return true;
	}
	
	/**
	 *
	 * @param cls Class
	 * @param syskey String
	 * @return List
	 */
	public List getUserPlugin(Class cls,String syskey) {
		String key = getUserPluginKey();
		return dpm.getPlugins(cls,key,syskey);
	}
	
	public String getUserPluginKey() {
		return userPluginKey;
	}
}
