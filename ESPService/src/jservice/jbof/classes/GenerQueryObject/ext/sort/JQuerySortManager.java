package jservice.jbof.classes.GenerQueryObject.ext.sort;

import java.util.*;

import com.f1j.swing.*;
import com.report.table.jxml.*;
import jservice.jbof.classes.GenerQueryObject.*;
import jservice.jbof.classes.GenerQueryObject.ext.*;
import jservice.jbof.classes.GenerQueryObject.ext.sort.util.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class JQuerySortManager {
    /**
     *
     */
    public JQuerySortManager() {
    }

    /**
     *
     * @param sortBys
     * @param queryWindow
     */
    public static void sort(JBook book, int startRow, int startCol, int endRow, int endCol, Vector sortCols) {
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

//    /**
//     * 2006-11-23修改
//     * @param queryWindow
//     * @return
//     */
//    public static Vector getSortColumns(JGenerQueryWindow queryWindow) {
//        Vector sortKeys = new Vector();
//        if (queryWindow != null) {
//            Vector queryData = JQueryXManager.getQueryData(queryWindow);
//            if (queryData != null && queryData.size() == 2) {
//                TableManager mFmtMgr = (TableManager) queryData.get(0);
//                String xmlStr = mFmtMgr.GetRootXMLString();
//                Vector tableView = mFmtMgr.getTableView();
//                for (Enumeration e = tableView.elements(); e.hasMoreElements(); ) {
//                    XmlViewCol crtViewCol = (XmlViewCol) e.nextElement();
//                    getSortColumns(crtViewCol, mFmtMgr, sortKeys);
//                }
//            }
//        }
//        return sortKeys;
//    }

    /**
     *
     * @param id
     * @param fmtMgr
     */
    private static void getSortColumns(XmlViewCol viewCol, TableManager fmtMgr, Vector sortCols) {
        if (viewCol.getType().equalsIgnoreCase("group")) {
            String groupId = viewCol.getId();
            XmlGroup xmlGroup = (XmlGroup) fmtMgr.getGroupById(groupId);
            getSortColumns(xmlGroup, fmtMgr, sortCols);
        }
        else {
            String colId = viewCol.getId();
            XmlColumn col = fmtMgr.getColumnById(colId);
            String name = col.getCaption();
            int colIndex = Integer.parseInt(col.getNo()); //不知道是否合理
            JQuerySortColumn sortCol = new JQuerySortColumn(name, colIndex);
            sortCols.add(sortCol);
        }
    }

    /**
     *
     * @param group
     * @param fmtMgr
     * @param sortKeys
     */
    private static void getSortColumns(XmlGroup group, TableManager fmtMgr, Vector sortCols) {
        Vector groupItems = group.getItems();
        for (Enumeration e = groupItems.elements(); e.hasMoreElements(); ) {
            XmlItem crtItem = (XmlItem) e.nextElement();
            if (crtItem.getType().equalsIgnoreCase("group")) {
                String groupId = crtItem.getId();
                XmlGroup xmlGroup = (XmlGroup) fmtMgr.getGroupById(groupId);
                getSortColumns(xmlGroup, fmtMgr, sortCols);
            }
            else {
                String colId = crtItem.getId();
                XmlColumn col = fmtMgr.getColumnById(colId);
                String name = col.getCaption();
                int colIndex = Integer.parseInt(col.getNo()); //不知道是否合理
                JQuerySortColumn sortCol = new JQuerySortColumn(name, colIndex);
                sortCols.add(sortCol);

            }
        }

    }
}
