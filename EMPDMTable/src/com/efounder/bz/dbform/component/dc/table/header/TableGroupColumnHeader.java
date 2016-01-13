package com.efounder.bz.dbform.component.dc.table.header;

import java.util.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;
import com.efounder.bz.dbform.datamodel.column.*;
import com.efounder.bz.dbform.datamodel.ColumnHeaderGroupModel;

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
public class TableGroupColumnHeader extends JTableHeader {
  /**
   *
   */
  private static final String uiClassID = "TableGroupColumnHeaderUI";
  /**
   *
   */
  public TableGroupColumnHeader() {
    super(null);
  }
  /**
   *
   */
  protected ColumnHeaderGroupModel groupModel = null;
  /**
   *
   * @param groupModel ColumnHeaderGroupModel
   */
  public void setGroupModel(ColumnHeaderGroupModel groupModel) {
    this.groupModel = groupModel;
  }
  /**
   *
   * @param model TableColumnModel
   */
  public TableGroupColumnHeader(TableColumnModel model) {
    super(model);
    setReorderingAllowed(false);
  }
  /**
   *
   */
  public void updateUI(){
    setUI(new TableGroupColumnHeaderUI());
      TableCellRenderer renderer = getDefaultRenderer();
      if (renderer instanceof Component) {
          SwingUtilities.updateComponentTreeUI((Component)renderer);
      }
    }
  /**
   *
   */
//  protected Vector columnGroups = null;
  /**
   *
   * @param b boolean
   */
  public void setReorderingAllowed(boolean b) {
    reorderingAllowed = b;
  }
  /**
   *
   * @param g ColumnGroup
   */
  public void addColumnGroup(ColumnGroup g) {
//    if (columnGroups == null) {
//      columnGroups = new Vector();
//    }
//    columnGroups.addElement(g);
//    groupModel.insertDataComponent(g);
  }
  /**
   *
   * @param col TableColumn
   * @return Enumeration
   */
  public Enumeration getColumnGroups(TableColumn col) {
//    if (groupModel == null || groupModel.getChildList()== null || groupModel.getChildList().size() == 0 ) return null;
//    Vector columnGroups = (Vector)groupModel.getChildList();
//    Enumeration enume = columnGroups.elements();
//    while (enume.hasMoreElements()) {
//      ColumnGroup cGroup = (ColumnGroup) enume.nextElement();
//      Vector v_ret = (Vector) cGroup.getColumnGroups(col, new Vector());
//      if (v_ret != null) {
//        return v_ret.elements();
//      }
//    }
    return null;
  }
  /**
   *
   */
  public void setColumnMargin() {
//    if (groupModel == null || groupModel.getChildList()== null || groupModel.getChildList().size() == 0 ) return;
//    int columnMargin = getColumnModel().getColumnMargin();
//    Vector columnGroups = (Vector)groupModel.getChildList();
//    Enumeration enume = columnGroups.elements();
//    while (enume.hasMoreElements()) {
//      ColumnGroup cGroup = (ColumnGroup) enume.nextElement();
//      cGroup.setColumnMargin(columnMargin);
//    }
  }
}
