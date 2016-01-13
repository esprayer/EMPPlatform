package com.efounder.comp.treetable;

import com.core.xml.*;

//import com.pansoft.pub.comp.StubObject;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class JTreeTableModel extends AbstractTreeTableModel
                             implements TreeTableModel {
  // Names of the columns.
  protected String[]  cNames = null;//{"月份", "完成", "封表", "封式"};

  protected String[]  cColumns = null;//{"月份", "完成", "封表", "封式"};

  // Types of the columns.
  protected Class[]  cTypes = null;//{TreeTableModel.class, Boolean.class, Boolean.class, Boolean.class};

  public JTreeTableModel(StubObject Panent) {
    super(Panent);
  }
  //
  // Some convenience methods.
  //
  public void setNames(String[] ns) {
    cNames = ns;
  }
  public void setTypes(Class[] ts) {
    cTypes = ts;
  }
  public void setColumns(String[] cs) {
    cColumns = cs;
  }
  protected Object[] getChildren(Object node) {
    StubObject IOS = (StubObject)node;
    return IOS.getChilds();
  }

  //
  // The TreeModel interface
  //

  public int getChildCount(Object node) {
      Object[] children = getChildren(node);
      return (children == null) ? 0 : children.length;
  }

  public Object getChild(Object node, int i) {
    return getChildren(node)[i];
  }

  // The superclass's implementation would work, but this is more efficient.
//  public boolean isLeaf(Object node) {
//    StubObject IOS = (StubObject)node;
//    // 如果为Year，则为非叶结点
//    if ( IOS.getChilds() != null ) return false;
//    return true;
//  }

  //
  //  The TreeTableNode interface.
  //

  public int getColumnCount() {
      return getType().length;
  }

  public String getColumnName(int column) {
      return getName()[column];
  }

  public Class getColumnClass(int column) {
      return getType()[column];
  }
  protected String[] getName() {
    return cNames;
  }
  protected Class[] getType() {
    return cTypes;
  }
  public Object getValueAt(Object node, int column) {
    StubObject IOS = (StubObject)node;
      try {
        if  ( column == 0 ) {
            return IOS;
        }
        return IOS.getObject(cColumns[column],null);
      }
      catch  (Exception se) { se.printStackTrace(); }
      return null;
  }
  public void setValueAt(Object aValue, Object node, int column) {
    StubObject IOS = (StubObject)node;
    String colName = cColumns[column];
  }
}
