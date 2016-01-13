package jdatareport.dof.classes.report.paint;

import java.util.*;

import com.eai.form.formdefine.interfaces.*;
import com.f1j.ss.*;
import com.pansoft.report.table.jxml.*;
import jdatareport.dof.classes.report.*;
import jdatareport.dof.classes.report.figure.*;
import jdatareport.dof.classes.report.util.*;
import jformservice.jdof.classes.*;

/**
 * <p>Title: JDRPaintUtils</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: Pansoft</p>
 * @author Stephen Zhao
 * @version 1.0
 */

public final class JDRPaintUtils {
    /**
     * 构造方法
     */
    public JDRPaintUtils() {
    }

    /**
     * 获得Text的对齐方式
     * @param align
     * @return
     */
    public static short getAlignment(String align) {
        if (align.equals("left")) {
            return CellFormat.eHorizontalAlignmentLeft;
        }
        else if (align.equals("center")) {
            return CellFormat.eHorizontalAlignmentCenter;
        }
        else if (align.equals("right")) {
            return CellFormat.eHorizontalAlignmentRight;
        }
        return CellFormat.eHorizontalAlignment;
    }

    /**
     * 获得对齐方式
     * @param INode
     * @return
     */
    public static short getAlignment(IFormNodeDefine INode) {
        if (INode.getAlignment() == IFormNodeDefine.ALIGN_LEFT) {
            return CellFormat.eHorizontalAlignmentLeft;
        }
        else if (INode.getAlignment() == IFormNodeDefine.ALIGN_MIDDLE) {
            return CellFormat.eHorizontalAlignmentCenter;
        }
        else if (INode.getAlignment() == IFormNodeDefine.ALIGN_RIGHT) {
            return CellFormat.eHorizontalAlignmentRight;
        }
        return CellFormat.eHorizontalAlignment;
    }

    /**
     * 查找行参考
     * @param name
     * @return
     */
    public static JDRCellFigure lookupRowRef(JDRModel model, JDRCellFigure cellFigure) {
        String name = cellFigure.getRowRef();
        JDRRptFigure rptFigure = model.getRptFigure();

        for (Enumeration en = rptFigure.getCellFigures().elements(); en.hasMoreElements(); ) {
            JDRCellFigure crtCellFigure = (JDRCellFigure) en.nextElement();
            String crtName = crtCellFigure.getName();
            if (crtName.equals(name)) {
                return crtCellFigure;
            }
        }
        return null;
    }

    /**
     * 查找列参考
     * @param name
     * @return
     */
    public static JDRCellFigure lookupColRef(JDRModel model, JDRCellFigure cellFigure) {
        String name = cellFigure.getColRef();
        JDRRptFigure rptFigure = model.getRptFigure();

        for (Enumeration en = rptFigure.getCellFigures().elements(); en.hasMoreElements(); ) {
            JDRCellFigure crtCellFigure = (JDRCellFigure) en.nextElement();
            String crtName = crtCellFigure.getName();
            if (crtName.equals(name)) {
                return crtCellFigure;
            }
        }
        return null;
    }

    /**
     * 计算参考行,2006-11-23修改
     * @param cellFigure
     * @return
     */
    public static int calcRefRow(JDRModel model, JDRCellFigure cellFigure) {
        int refRow = 0;
        JDRCellFigure refFigure = lookupRowRef(model, cellFigure);
        if (refFigure == null) {
            return refRow;
        }
        int cellStartRow = refFigure.getStartRow();
        int cellEndRow = refFigure.getEndRow();

        if (refFigure.getType().equals(JDRConstants.TYPE_TEXT)) {
            refRow += cellEndRow - cellStartRow + 1;
        }
        else if (refFigure.getType().equals(JDRConstants.TYPE_FORM)) {
            int formRowCount = 0;
            JDataWindow form = getForm(model, refFigure);
            if (form != null) {
                formRowCount = getFormRowCount(form);
            }
            refRow += formRowCount;
        }
        else if (refFigure.getType().equals(JDRConstants.TYPE_QUERY)) {
            String queryName = refFigure.getName();
            int queryRowCount = getQueryRowCount(model, queryName);
            refRow += queryRowCount;
        }
        else {

        }

        if (refFigure.getRowRef() != null && refFigure.getRowRef().length() > 0) {
            refRow += cellStartRow;
            refRow += calcRefRow(model, refFigure);
        }
        else {
            refRow += cellStartRow;
        }
        return refRow;
    }

    /**
     * 计算参考列
     * @param cellFigure
     * @return
     */
    public static int calcRefCol(JDRModel model, JDRCellFigure cellFigure) {
        int refCol = 0;
        JDRCellFigure refFigure = lookupColRef(model, cellFigure);
        if (refFigure == null) {
            return refCol;
        }
        int cellStartCol = refFigure.getStartCol();
        int cellEndCol = refFigure.getEndCol();

        if (refFigure.getType().equals(JDRConstants.TYPE_TEXT)) {
            refCol += cellEndCol - cellStartCol + 1;
        }
        else if (refFigure.getType().equals(JDRConstants.TYPE_FORM)) {
            int formRowCount = 0;
            JDataWindow form = getForm(model, refFigure);
            if (form != null) {
                formRowCount = getFormColCount(form);
            }
            refCol += formRowCount;
        }
        else if (refFigure.getType().equals(JDRConstants.TYPE_QUERY)) {
            String queryName = refFigure.getName();
            int queryColCount = getQueryColCount(model, queryName);
            refCol += queryColCount;
        }
        else {

        }

        if (refFigure.getColRef() != null && refFigure.getColRef().length() > 0) {
            refCol += cellStartCol;
            refCol += calcRefCol(model, refFigure);
        }
        else {
            refCol += cellStartCol;
        }
        return refCol;
    }

    /**
     * 获得数据窗口,如果前台缓存没有,则后台获取
     * @param cellFigure
     * @return
     */
    public static JDataWindow getForm(JDRModel model, JDRCellFigure cellFigure) {
        JDataWindow form = null;
        String name = cellFigure.getName();
        String ds = cellFigure.getAttribute("ds");
        String params = cellFigure.getAttribute("params");
        form = model.getForm(name, ds, JDRUtils.parseParams(params));
        return form;
    }

    /**
     * 获得数据窗口行数
     * @param form
     * @return
     */
    public static int getFormRowCount(JDataWindow form) {
        int formRowCount = 0;
        if (form != null) {
            String strFormType = form.getDefine().getFormStyle();
            if (strFormType.equals("GRID")) { //表格
                formRowCount = form.getDataSetInterface().getActivePool().getRecordCount();
            }
            else if (strFormType.equals("FREE")) { //卡片
                formRowCount = form.getDefine().getRowCount();
            }
        }
        return formRowCount;
    }

    /**
     * 获得数据窗口列数
     * @param form
     * @return
     */
    public static int getFormColCount(JDataWindow form) {
        int formColCount = 0;
        if (form != null) {
            String strFormType = form.getDefine().getFormStyle();
            if (strFormType.equals("GRID")) { //表格
                formColCount = form.getDataSetInterface().getDiaplayMap().length;
            }
            else if (strFormType.equals("FREE")) { //卡片
                formColCount = form.getDefine().getColumnCount();
            }
        }
        return formColCount;
    }

    /**
     * 获得查询行数,2006-11-23修改
     * @param model
     * @param queryName
     * @return
     */
    public static int getQueryRowCount(JDRModel model, String queryName) {
        int queryRowCount = 0;
        Object[] queryResult = (String[])model.getQuery(queryName);
        if (queryResult != null && queryResult.length > 0) {
            //表头行数
            TableManager titleMgr = (TableManager)queryResult[0];
            queryRowCount += titleMgr.getTitles().size();
            Vector cols = titleMgr.getColumns();
            Vector titles = titleMgr.getTitles();
            Vector views = titleMgr.getTableView();
            Vector groups = titleMgr.getGroups();
            Vector colIds = new Vector();
            for (int i = 0; i < views.size(); i++) {
                XmlViewCol xmlViewCol = (XmlViewCol) views.elementAt(i);
                if (xmlViewCol.getType().equals("col")) {

                    XmlColumn xmlColumn = (XmlColumn) TableManager.getElementById(cols, xmlViewCol.getId());
                    colIds.addElement(xmlColumn.getId());

                    queryRowCount++;
                }
                else if (xmlViewCol.getType().equals("group")) {
                    XmlGroup xmlGroup = (XmlGroup) TableManager.getElementById(groups, xmlViewCol.getId());
                    Vector leafItems = getGroupChildren(groups, xmlGroup);
                    for (int k = 0; k < leafItems.size(); k++) {
                        XmlItem leafItem = (XmlItem) leafItems.elementAt(k);
                        XmlColumn xmlColumn = (XmlColumn) TableManager.getElementById(cols, leafItem.getId());
                        colIds.addElement(xmlColumn.getId());
                    }
                }
            }
            //表体行数
            //TableDataManager dataMgr = new TableDataManager(queryResult[1]);
            //Object[][] data = dataMgr.getTableDatas(colIds.toArray());
            QueryDataSet queryDataSet=(QueryDataSet)queryResult[1];
            queryRowCount += queryDataSet.getDataVector().size();
        }
        return queryRowCount;
    }

    /**
     * 获得查询列数
     * @param model
     * @param queryName
     * @return
     */
    public static int getQueryColCount(JDRModel model, String queryName) {
        int queryColCount = 0;
        String[] queryResult = (String[])model.getQuery(queryName);
        if (queryResult != null && queryResult.length > 0) {
            //列数
            TableManager titleMgr = new TableManager(queryResult[0]);

            Vector cols = titleMgr.getColumns();
            Vector titles = titleMgr.getTitles();
            Vector views = titleMgr.getTableView();
            Vector groups = titleMgr.getGroups();

            for (int i = 0; i < views.size(); i++) {
                XmlViewCol xmlViewCol = (XmlViewCol) views.elementAt(i);
                if (xmlViewCol.getType().equals("col")) {
                    queryColCount++;
                }
                else if (xmlViewCol.getType().equals("group")) {
                    XmlGroup xmlGroup = (XmlGroup) TableManager.getElementById(groups, xmlViewCol.getId());
                    queryColCount += getGroupColCount(groups, xmlGroup);
                }
            }

        }
        return queryColCount;
    }

    /**
     *
     * @param groups
     * @param xGroup
     * @return
     */
    private static Vector getGroupChildren(Vector groups, XmlGroup xGroup) {
        Vector items = xGroup.getItems();
        Vector leafItems = new Vector();
        for (int i = 0; i < items.size(); i++) {
            XmlItem item = (XmlItem) items.elementAt(i);
            if (item.getType().equals("col")) {
                leafItems.addElement(item);
            }
            else if (item.getType().equals("group")) {
                XmlGroup ggg = (XmlGroup) TableManager.getElementById(groups, item.getId());
                Vector vvv = getGroupChildren(groups, ggg);
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
     * @param groups
     * @param group
     * @return
     */
    private static int getGroupColCount(Vector groups, XmlGroup group) {
        int n, nCol = 0;
        Vector xmlItems = group.getItems();
        for (int i = 0; i < xmlItems.size(); i++) {
            XmlItem xmlItem = (XmlItem) xmlItems.elementAt(i);
            if (xmlItem.getType().equals("col")) {
                nCol++;
            }
            if (xmlItem.getType().equals("group")) {
                XmlGroup xGroup = (XmlGroup) TableManager.getElementById(groups, xmlItem.getId());
                n = getGroupColCount(groups, xGroup);
                nCol += n;
            }
        }

        return nCol;
    }

    /**
     * 变量替换
     * @param source
     * @param params
     * @return
     */
    public static String replace(String source, Hashtable params) {
        if (source != null && params != null) {
            for (Enumeration e = params.elements(); e.hasMoreElements(); ) {
                String key = (String) e.nextElement();
                String val = (String) params.get(key);
                source = source.replaceAll("@" + key + "@", val);
            }
        }
        return source;
    }
}
