package jdatareport.dof.classes.report.ext;

import java.util.*;
import com.report.table.jxml.*;
import jdatareport.dof.classes.report.ext.sort.util.*;
import com.f1j.swing.*;
import jdatareport.dof.classes.report.ext.util.QueryColumn;
import jdatareport.dof.classes.report.ext.util.QueryConstants;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class JDRXManager {
  public JDRXManager() {
  }

  /**
   *
   * @param sortBys
   * @param queryWindow
   */
  public static void sort(JBook book, int startRow, int startCol, int endRow,
                          int endCol, Vector sortCols) {
    try {
      if (book != null && sortCols != null) {
        int[] mapCols = getColumns(sortCols);
        book.sort(startRow, startCol, endRow, endCol, true, mapCols);
      }
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   *
   * @param xmlCols
   * @return
   */
  public static int[] getColumns(Vector sortCols) {
    if (sortCols != null) {
      int[] cols = new int[sortCols.size()];
      for (int i = 0; i < cols.length; i++) {
        JQuerySortOrder order = ( (JQuerySortColumn) sortCols.get(i)).getOrder();
        cols[i] = order.getColumnIndex();
      }
      return cols;
    }
    return new int[0];
  }

  /**
   * 获取排序列 2006-11-22 风波
   * @param queryWindow
   * @return
   */
  public static Vector getSortColumns(TableManager mFmtMgr) {
    Vector sortKeys = new Vector();
    if (mFmtMgr != null) {
      Vector tableView = mFmtMgr.getTableView();
      int num = 1;
      for (Enumeration e = tableView.elements(); e.hasMoreElements(); ) {
        XmlViewCol crtViewCol = (XmlViewCol) e.nextElement();
        num = getSortColumns(crtViewCol, mFmtMgr, sortKeys, num);
      }
    }
    return sortKeys;
  }

  /**
   *
   * @param id
   * @param fmtMgr
   */
  private static int getSortColumns(XmlViewCol viewCol,
                                    TableManager fmtMgr,
                                    Vector sortCols,
                                    int num) {
    if (viewCol.getType().equalsIgnoreCase("group")) {
      String groupId = viewCol.getId();
      XmlGroup xmlGroup = (XmlGroup) fmtMgr.getGroupById(groupId);
      num = getSortColumns(xmlGroup, fmtMgr, sortCols, num);
    }
    else {
      String colId = viewCol.getId();
      XmlColumn col = fmtMgr.getColumnById(colId);
      String name = col.getCaption();
      JQuerySortColumn sortCol = new JQuerySortColumn(name, num++);
      setColumnType(sortCol, col);
      sortCols.add(sortCol);
    }
    return num;
  }

  /**
   *
   * @param group
   * @param fmtMgr
   * @param sortKeys
   */
  private static int getSortColumns(XmlGroup group,
                                    TableManager fmtMgr,
                                    Vector sortCols,
                                    int num) {
    Vector groupItems = group.getItems();
    for (Enumeration e = groupItems.elements(); e.hasMoreElements(); ) {
      XmlItem crtItem = (XmlItem) e.nextElement();
      if (crtItem.getType().equalsIgnoreCase("group")) {
        String groupId = crtItem.getId();
        XmlGroup xmlGroup = (XmlGroup) fmtMgr.getGroupById(groupId);
        num = getSortColumns(xmlGroup, fmtMgr, sortCols, num);
      }
      else {
        String colId = crtItem.getId();
        XmlColumn col = fmtMgr.getColumnById(colId);
        String name = col.getCaption();
        JQuerySortColumn sortCol = new JQuerySortColumn(name, num++);
        setColumnType(sortCol, col);
        sortCols.add(sortCol);
      }
    }
    return num;
  }

  /**
   * 2007-9-26 modiby fengbo
   * @param column QueryColumn
   * @param xColumn XmlColumn
   */
  public static void setColumnType(QueryColumn column, XmlColumn xColumn) {
//         if(xColumn.getDatatype().toUpperCase().equals("N")){
//             column.setColumnType(QueryConstants.COLUMN_TYPE_NUMBER);
//         }else{
//             column.setColumnType(QueryConstants.COLUMN_TYPE_CHAR);
//         }

    if (xColumn.getDatatype().toUpperCase().equals("C") || xColumn.getDatatype().toUpperCase().equals("T")) {
      column.setColumnType(QueryConstants.COLUMN_TYPE_CHAR);
    }
    else {
      column.setColumnType(QueryConstants.COLUMN_TYPE_NUMBER);
    }
  }

  /**
   * 获取查询列 2006-11-22 风波
   * @param queryWindow
   * @return
   */
  public static Vector getQueryColumns(TableManager mFmtMgr) {
    Vector queryCols = new Vector();
    if (mFmtMgr != null) {
      Vector tableView = mFmtMgr.getTableView();
      int num = 1;
      for (Enumeration e = tableView.elements(); e.hasMoreElements(); ) {
        XmlViewCol crtViewCol = (XmlViewCol) e.nextElement();
        num = getSortColumns(crtViewCol, mFmtMgr, queryCols, num);
      }
    }
    return queryCols;
  }
}
