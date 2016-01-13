package com.efounder.bz.dbform.component.dc.table.render;

import java.awt.Component;
import java.util.*;

import org.jdesktop.swingx.renderer.*;

import com.efounder.builder.base.data.EFRowSet;
import com.efounder.builder.base.data.ESPRowSet;
import com.efounder.bz.dbform.datamodel.*;
import com.efounder.bz.dbform.datamodel.column.Column;
import com.efounder.dbc.ClientDataSet;
import com.efounder.dctbuilder.data.ColumnMetaData;

import javax.swing.Icon;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
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
public class TableCellRendererManager extends DefaultTableCellRenderer{
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		DictModel           dictModel = (DictModel) table.getModel();
		ClientDataSet   clientDataSet = dictModel.getDataSet();
		EFRowSet               rowset = clientDataSet.getRowSet(row);
		ColumnModel       columnModel = (ColumnModel) table.getColumnModel();
		Column               tableCol = (Column) columnModel.getColumn(column);		
		ColumnMetaData columnMetaData = tableCol.getColumnMeataData();//获取列的元数据
		String                  colId = columnMetaData.getString("COL_ID", "");
		String              cellValue = rowset.getString(colId, "");
	    this.setValue(cellValue);
	    return this;
	  }
}
