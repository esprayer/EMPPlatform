package com.efounder.bz.task.bof.render;

import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import com.efounder.builder.base.data.EFRowSet;
import com.efounder.bz.dbform.component.dc.table.DictTable;
import com.efounder.bz.dbform.datamodel.DictModel;
import com.efounder.bz.dbform.datamodel.column.Column;
import com.efounder.dbc.ClientDataSet;


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
public class DataFormEnumCellRenderer extends DefaultTableCellRenderer {
	
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		
		DictModel         dictModel = (DictModel)table.getModel();
		ClientDataSet       dataset = dictModel.getDataSet();
		Column          tableColumn = dictModel.getColModel().getColumnByModelIndex(column);
		String            cellValue = "";
		String         displayValue = "";

		EFRowSet rowset = dataset.getRowSet(row);
		
		cellValue = rowset.getString(tableColumn.getColumnMeataData().getColid(), "");

		if(tableColumn.getColumnMeataData().getColid().equals("RESR_IN_CAUSE")) {
			displayValue = "[" + rowset.getString("OP_USER_NAME", "") + "]";
		}
		
		if(cellValue.equals("rollback")) {
			displayValue += "拒绝并回退";
		} else if(cellValue.equals("submit")) {
			displayValue += "同意并提交";
		} else if(cellValue.equals("create")) {
			displayValue += "新建";
		} else if(cellValue.equals("retake")) {
			displayValue += "主动取回";
		} else if(cellValue.equals("invalid")) {
			displayValue += "作废";
		} else if(cellValue.equals("waiting")) {
			displayValue += "等待";
		} else if(cellValue.equals("ended")) {
			displayValue += "结束";
		}
		
		return super.getTableCellRendererComponent(table, displayValue, isSelected, hasFocus, row, column); 
	}

}
