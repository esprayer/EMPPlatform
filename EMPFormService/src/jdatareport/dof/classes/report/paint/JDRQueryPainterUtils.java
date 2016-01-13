package jdatareport.dof.classes.report.paint;

import java.util.*;

import com.report.table.jxml.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class JDRQueryPainterUtils {
    public JDRQueryPainterUtils() {
    }

    /**
     *
     * @return
     */
    public static int getHeaderLevels(TableManager titleMgr) {
        int i, nTemp, js = 1;
        Vector viewCols = titleMgr.getTableView();
        for (i = 0; i < viewCols.size(); i++) {
            XmlViewCol xmlViewCol = (XmlViewCol) viewCols.elementAt(i);
            if (xmlViewCol.getType().equals("group")) {
                XmlGroup xmlGroup = (XmlGroup) titleMgr.getElementById(titleMgr.getGroups(), xmlViewCol.getId());
                nTemp = getGroupLevels(titleMgr, xmlGroup, 1);
                if (js < nTemp) {
                    js = nTemp;
                }
            }
        }
        return js;
    }

    /**
     *
     * @param titleManager
     * @param group
     * @param js
     * @return
     */
    public static int getGroupLevels(TableManager titleMgr, XmlGroup group, int js) {
        int nJs, rtnJs = 0;
        js++;
        rtnJs = js;
        Vector xmlItems = group.getItems();
        for (int i = 0; i < xmlItems.size(); i++) {
            XmlItem xmlItem = (XmlItem) xmlItems.elementAt(i);
            if (xmlItem.getType().equals("group")) {
                XmlGroup xmlTemp = (XmlGroup) titleMgr.getGroupById(xmlItem.getId());
                nJs = getGroupLevels(titleMgr, xmlTemp, js);
                if (rtnJs < nJs) {
                    rtnJs = nJs;
                }
            }
        }
        return rtnJs;
    }

    /**
     *
     * @param titleMgr
     * @param xmlGroup
     * @return
     */
    public static Vector getLeafItems(TableManager titleMgr, XmlGroup xmlGroup) {
        Vector items = xmlGroup.getItems();
        Vector leafItems = new Vector();
        for (int i = 0; i < items.size(); i++) {
            XmlItem item = (XmlItem) items.elementAt(i);
            if (item.getType().equals("col")) {
                leafItems.addElement(item);
            }
            if (item.getType().equals("group")) {
                XmlGroup ggg = (XmlGroup) TableManager.getElementById(titleMgr.getGroups(),
                    item.getId());
                Vector vvv = getLeafItems(titleMgr, ggg);
                for (int j = 0; j < vvv.size(); j++) {
                    XmlItem iii = (XmlItem) vvv.elementAt(j);
                    leafItems.addElement(iii);
                }
            }
        }
        return leafItems;
    }

    /**
     *
     * @param group
     * @param groups
     * @return
     */
    public static int getGroupCols(Vector valueGroups, XmlGroup group) {
        int n, nCol = 0;
        Vector xmlItems = group.getItems();
        for (int i = 0; i < xmlItems.size(); i++) {
            XmlItem xmlItem = (XmlItem) xmlItems.elementAt(i);
            if (xmlItem.getType().equals("col")) {
                nCol++;
            }
            if (xmlItem.getType().equals("group")) {
                XmlGroup ggg = (XmlGroup) TableManager.getElementById(valueGroups,
                    xmlItem.getId());
                n = getGroupCols(valueGroups, ggg);
                nCol += n;
            }
        }
        return nCol;
    }

    /**
     *
     * @param views
     * @param groups
     * @return
     */
    public static int getTotalCols(Vector viewCols, Vector valueGroups) {
        int count = 0;
        for (int i = 0; i < viewCols.size(); i++) {
            XmlViewCol xmlViewCol = (XmlViewCol) viewCols.elementAt(i);
            if (xmlViewCol.getType().equals("col")) {
                count++;
            }
            else if (xmlViewCol.getType().equals("group")) {
                XmlGroup xmlGroup = (XmlGroup) TableManager.getElementById(valueGroups,
                    xmlViewCol.getId());
                count += JDRQueryPainterUtils.getGroupCols(valueGroups, xmlGroup);
            }
        }
        return count;
    }

}