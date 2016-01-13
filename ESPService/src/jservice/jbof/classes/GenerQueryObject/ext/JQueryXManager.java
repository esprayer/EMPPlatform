package jservice.jbof.classes.GenerQueryObject.ext;

import java.util.*;

import com.f1j.swing.*;
import com.report.table.jxml.*;
import jdatareport.dof.classes.*;
import jdatareport.dof.classes.report.*;
import jdatareport.dof.classes.report.util.*;
import jservice.jbof.classes.GenerQueryObject.*;
import jdatareport.dof.classes.report.ext.condition.util.*;
import jservice.jbof.classes.GenerQueryObject.ext.sort.*;
import jservice.jbof.classes.GenerQueryObject.ext.filter.*;
import jdatareport.dof.classes.report.filter.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class JQueryXManager {
    public JQueryXManager() {
    }

    /**
     * 2006-11-23修改
     * @param QueryWindow
     * @return
     */
//    public static Vector getQueryData(JGenerQueryWindow QueryWindow) {
//        if (QueryWindow == null) {
//            return null;
//        }
//        String[] QueryObject = QueryWindow.QueryObject;
//        String[] datas = null;
//        if (QueryWindow.QueryObject.length == 4) {
//            datas = new String[] {
//                QueryObject[0], QueryObject[1]};
//        }
//        else if (QueryObject.length == 2) {
//            datas = new String[] {
//                QueryObject[0], QueryObject[1]};
//        }
//        if (datas != null && datas.length == 2) {
//            TableManager mFmtMgr = new TableManager(datas[0]);
//            TreeTableDataManager mDataMgr = new TreeTableDataManager(datas[1]);
//            Vector queryData = new Vector();
//            queryData.add(mFmtMgr);
//            queryData.add(mDataMgr);
//            return queryData;
//        }
//        return null;
//    }

    /**
     * 2006-11-23修改
     * @param queryWindow
     * @return
     */
//    public static Vector getQueryColumns(JGenerQueryWindow queryWindow) {
//        return JQueryFilterManager.getQueryColumns(queryWindow);
//    }

    /**
     * 2006-11-23修改
     * @param queryWindow
     * @return
     */
//    public static Vector getSortColumns(JGenerQueryWindow queryWindow) {
//        return JQuerySortManager.getSortColumns(queryWindow);
//    }

    /**
     *
     * @param book
     * @param sortKeys
     */
    public static void sort(JGenerQueryWindow queryWindow, Vector sortCols) {
        if (queryWindow == null || sortCols == null) {
            return;
        }
        JDRModel model = queryWindow.vwQueryView.getDataReport().getModel();
        JBook book = queryWindow.vwQueryView.getDataReport().getBook();

        int bodyStartRow = model.getTitleRowCount() + model.getHeadRowCount();
        int bodyEndRow = model.getMaxRow();
        int bodyRowCount = model.getBodyRowCount();
        int bodyEndCol = model.getMaxCol();

        int startRow = bodyStartRow;
        int startCol = 0;
        int endRow = bodyEndRow;
        int endCol = bodyEndCol;
        if (endCol > 0) {
            endCol--;
        }
        //排序
        JQuerySortManager.sort(book, startRow, startCol, endRow, endCol, sortCols);

        //重新绘制格式
        try {
            JDataReport dataRpt = queryWindow.vwQueryView.getDataReport();
            dataRpt.load(JDataReport.FILTER_FORMAT_ONLY);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     *
     * @param queryWindow
     * @param expItems
     */
    public static void filter(JGenerQueryWindow queryWindow, JCExpression filterExp) {
        if (queryWindow == null || filterExp == null) {
            return;
        }
        JDRModel model = queryWindow.vwQueryView.getDataReport().getModel();
        JBook book = queryWindow.vwQueryView.getDataReport().getBook();

        int bodyStartRow = model.getTitleRowCount() + model.getHeadRowCount();
        int bodyEndRow = model.getMaxRow()+1;
        int bodyRowCount = model.getBodyRowCount();
        int bodyEndCol = model.getMaxCol();

        int startRow = bodyStartRow;
        int startCol = 0;
        int endRow = bodyEndRow;
        int endCol = bodyEndCol;
        if (endCol > 0) {
            endCol--;
        }

        try {
            //删除格式和数据
            book.setSelection(startRow,startCol,endRow,endCol);
            book.editClear(JBook.eClearAll);
            book.setRowOutlineLevel(startRow,endRow,0,false);
            //构造过滤器
            JDRRowFilter rowFilter = new JDRRowFilter();
            rowFilter.setFilterExpressions(filterExp);
            //绘制报表
            JDataReport dataRpt = queryWindow.vwQueryView.getDataReport();
            dataRpt.load(rowFilter);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
