package com.efounder.dctbuilder.view;

import com.efounder.bz.dbform.datamodel.footer.GroupTableModel;


public class DictFooterModel extends GroupTableModel{

	//合计列不能编辑
	public boolean isCellEditable(int row, int column) {
		
		return false;
	}
}
