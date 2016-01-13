package com.report.table;

import java.util.*;
import javax.swing.table.*;

/**
  * GroupableTableHeader
  *
  * @version 1.0 2002/01/25
  * @author Lubo
  */

public class GroupableTableHeader extends JTableHeader {
  static ResourceBundle res = ResourceBundle.getBundle("com.report.table.Language");
  private static final String uiClassID = "GroupableTableHeaderUI";
  protected Vector columnGroups = null;

  public GroupableTableHeader(TableColumnModel model) {
	super(model);

	setUI(new GroupableTableHeaderUI());
	setReorderingAllowed(false);

  }
  public void addColumnGroup(ColumnGroup g) {
	if (columnGroups == null) {
	  columnGroups = new Vector();
	}
  	columnGroups.addElement(g);
  }
  public Enumeration getColumnGroups(TableColumn col) {
	if (columnGroups == null) return null;
	Enumeration enum1 = columnGroups.elements();
	while (enum1.hasMoreElements()) {
	  ColumnGroup cGroup = (ColumnGroup)enum1.nextElement();
	  Vector v_ret = (Vector)cGroup.getColumnGroups(col,new Vector());
	  if (v_ret != null) {
	    return v_ret.elements();
	  }
	}
	return null;
  }
  public void setColumnMargin() {
	if (columnGroups == null) return;
	int columnMargin = getColumnModel().getColumnMargin();
	Enumeration enum1 = columnGroups.elements();
	while (enum1.hasMoreElements()) {
	  ColumnGroup cGroup = (ColumnGroup)enum1.nextElement();
	  cGroup.setColumnMargin(columnMargin);
	}
  }
  public void setReorderingAllowed(boolean b) {
	reorderingAllowed = false;
  }
}
