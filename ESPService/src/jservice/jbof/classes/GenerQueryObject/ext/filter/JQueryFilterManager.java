package jservice.jbof.classes.GenerQueryObject.ext.filter;

import java.util.*;

import com.f1j.swing.*;
import com.report.table.jxml.*;
import jservice.jbof.classes.GenerQueryObject.*;
import jservice.jbof.classes.GenerQueryObject.ext.*;
import jservice.jbof.classes.GenerQueryObject.ext.condition.util.*;
import jservice.jbof.classes.GenerQueryObject.ext.util.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class JQueryFilterManager {
    /**
     *
     */
    public JQueryFilterManager() {
    }

//    /**
//     *
//     * @param queryWindow
//     * @return
//     */
//    public static Vector getQueryColumns(JGenerQueryWindow queryWindow) {
//        Vector queryCols = new Vector();
//        if (queryWindow != null) {
//            Vector queryData = JQueryXManager.getQueryData(queryWindow);
//            if (queryData != null && queryData.size() == 2) {
//                TableManager mFmtMgr = (TableManager) queryData.get(0);
//                String xmlStr = mFmtMgr.GetRootXMLString();
////                TreeTableDataManager mDataMgr = (TreeTableDataManager)queryData.get(1);
//
//                Vector tableView = mFmtMgr.getTableView();
//                for (Enumeration e = tableView.elements(); e.hasMoreElements(); ) {
//                    XmlViewCol crtViewCol = (XmlViewCol) e.nextElement();
//                    getSortColumns(crtViewCol, mFmtMgr, queryCols);
//                }
//            }
//        }
//        return queryCols;
//
//    }

    /**
     *
     * @param id
     * @param fmtMgr
     */
    private static void getSortColumns(XmlViewCol viewCol, TableManager fmtMgr, Vector queryCols) {
        if (viewCol.getType().equalsIgnoreCase("group")) {
            String groupId = viewCol.getId();
            XmlGroup xmlGroup = (XmlGroup) fmtMgr.getGroupById(groupId);
            getSortColumns(xmlGroup, fmtMgr, queryCols);
        }
        else {
            String colId = viewCol.getId();
            XmlColumn col = fmtMgr.getColumnById(colId);
            String name = col.getCaption();
            int colIndex = Integer.parseInt(col.getNo()); //不知道是否合理
            String type = col.getDatatype().toUpperCase();
            String ID = col.getName();
            QueryColumn queryCol = new DefaultQueryColumn(name, colIndex);
            queryCol.setColumnType(type);
            queryCol.setColumnID(ID);
            queryCols.add(queryCol);
        }
    }

    /**
     *
     * @param group
     * @param fmtMgr
     * @param sortKeys
     */
    private static void getSortColumns(XmlGroup group, TableManager fmtMgr, Vector queryCols) {
        Vector groupItems = group.getItems();
        for (Enumeration e = groupItems.elements(); e.hasMoreElements(); ) {
            XmlItem crtItem = (XmlItem) e.nextElement();
            if (crtItem.getType().equalsIgnoreCase("group")) {
                String groupId = crtItem.getId();
                XmlGroup xmlGroup = (XmlGroup) fmtMgr.getGroupById(groupId);
                getSortColumns(xmlGroup, fmtMgr, queryCols);
            }
            else {
                String colId = crtItem.getId();
                XmlColumn col = fmtMgr.getColumnById(colId);
                String name = col.getCaption();
                int colIndex = Integer.parseInt(col.getNo()); //不知道是否合理
                String type = col.getDatatype().toUpperCase();
                String ID = col.getName();
                QueryColumn queryCol = new DefaultQueryColumn(name, colIndex);
                queryCol.setColumnType(type);
                queryCol.setColumnID(ID);
                queryCols.add(queryCol);

            }
        }

    }
}
