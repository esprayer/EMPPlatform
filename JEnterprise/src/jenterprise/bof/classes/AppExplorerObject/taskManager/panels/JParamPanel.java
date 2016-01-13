package jenterprise.bof.classes.AppExplorerObject.taskManager.panels;

import java.io.*;
import java.util.*;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;

import jenterprise.bof.classes.AppExplorerObject.taskManager.*;
import jenterprise.bof.classes.AppExplorerObject.taskManager.panels.JParamPanel.JParamTableModel;

/**
 * 参数面板。
 * 这个参数面板提供参数的添加、编辑和删除功能。
 *
 * @version 1.0 初始版本${gengeng}
 */
public class JParamPanel
    extends JPanel {

    public JParamPanel() {
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 设置参数对象.
     */
    public void setParamObject(ParamObject pObj) {
        if (pObj != null) {
            switch (pObj.paramType) {
                case ParamObject.ParamTypeReport:
                    setReportParam(pObj);
                    break;
            }
            paramTable.updateUI();
        }
    }

    /**
     * 报表参数.
     */
    private void setReportParam(ParamObject pObj) {
        JParamTableModel tModel = (JParamTableModel) paramTable.getModel();
        Vector pVector = tModel.paramList;
        if (pVector != null) {
            pVector.removeAllElements();
            pVector.add(pObj);
        }
    }

    private void jbInit() throws Exception {
        this.setLayout(borderLayout1);
        menuPanel.setLayout(flowLayout1);
        flowLayout1.setAlignment(FlowLayout.LEFT);
        mainPanel.setLayout(borderLayout2);
        delButton.setPreferredSize(new Dimension(22, 22));
        delButton.setIcon(JTaskUtil.getIcon("delete.gif"));
        delButton.setToolTipText("删除");
        editButton.setPreferredSize(new Dimension(22, 22));
        editButton.setIcon(JTaskUtil.getIcon("edit.gif"));
        editButton.setToolTipText("编辑");
        addButton.setPreferredSize(new Dimension(22, 22));
        addButton.setIcon(JTaskUtil.getIcon("add.gif"));
        addButton.setToolTipText("添加");
        mainScrollPane.getViewport().add(paramTable);
        menuPanel.add(toolBar);
        toolBar.add(addButton);
        toolBar.add(editButton);
        toolBar.add(delButton);
        mainPanel.add(mainScrollPane, java.awt.BorderLayout.CENTER);
        this.add(menuPanel, java.awt.BorderLayout.NORTH);
        this.add(mainPanel, java.awt.BorderLayout.CENTER);
        paramTable.setModel(new JParamTableModel(null));
        setParamTable();
    }

    private void setParamTable() {
        paramTable.setModel(new JParamTableModel(null));
        paramTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        TableColumn column;
        column = paramTable.getColumn(paramTable.getColumnName(0));
        column.setPreferredWidth(180);
        column = paramTable.getColumn(paramTable.getColumnName(1));
        column.setPreferredWidth(540);
    }

    BorderLayout borderLayout1 = new BorderLayout();
    JPanel menuPanel = new JPanel();
    JPanel mainPanel = new JPanel();
    FlowLayout flowLayout1 = new FlowLayout();
    BorderLayout borderLayout2 = new BorderLayout();
    JToolBar toolBar = new JToolBar();
    JScrollPane mainScrollPane = new JScrollPane();
    JTable paramTable = new JTable();
    JButton delButton = new JButton();
    JButton editButton = new JButton();
    JButton addButton = new JButton();

    /**
     * 参数对象．
     */
    public static class ParamObject
        implements Serializable {

        public static final int ParamTypeReport = 1;

        public final String rptUnitListName   = "单位编号：";
        public final String rptReportListName = "报表编号：";
        public final String rptReportTypeName = "报表类型：";
        public final String rptReportDateName = "报表日期：";

        public Vector rptUnitList;   //单位编号
        public Vector rptReportList; //报表编号
        public String rptReportType; //报表类型
        public String rptReportDate; //报表日期

        public int paramType;

        public ParamObject(int paramType) {
            this.paramType = paramType;
        }
    }

    class JParamTableModel
        extends AbstractTableModel {

        public Vector paramList;

        public JParamTableModel(Vector ROSList) {
            paramList = ROSList;
            if (paramList == null)
                paramList = new Vector();
        }

        public int getRowCount() {
            if (paramList != null)
                return paramList.size();
            return 0;
        }

        public String getColumnName(int columnIndex) {
            if (columnIndex == 0)
                return "参数名称";
            if (columnIndex == 1)
                return "参数值";
            return "";
        }

        public int getColumnCount() {
            return 2;
        }

        public Object getValueAt(int rowIndex, int columnIndex) {
            ParamObject pObj;
            if (paramList != null) {
                if (rowIndex <= paramList.size()) {
                    pObj = (ParamObject) paramList.get(rowIndex);
                    int index = pObj.paramType;
                    switch (index) {
                        case 1:
                            return getReportParam(rowIndex, columnIndex,pObj);
                    }
                }
            }
            return null;
        }

        /**
         * 报表参数
         */
        private Object getReportParam(int rowIndex, int columnIndex, ParamObject pObj) {
            if (columnIndex == 0)
                return "报表参数";
            if (columnIndex == 1)
                return "【" + pObj.rptReportTypeName + pObj.rptReportType + "】，【" +
                    pObj.rptReportDateName + pObj.rptReportDate + "】，【" +
                    pObj.rptUnitListName + arrayToString(pObj.rptUnitList.toArray())+ "】，【" +
                    pObj.rptReportListName + arrayToString(pObj.rptReportList.toArray()) + "】";
            return null;
        }

        private String arrayToString(Object[] array) {
            String rtn = "";
            if (array != null) {
                for (int i = 0, n = array.length; i < n; i++) {
                    rtn = rtn.concat(array[i] + ",");
                }
                if (rtn.indexOf(",") >= 0) {
                    rtn = rtn.substring(0, rtn.length() - 1);
                }
            }
            return rtn;
        }

        public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
            if (paramList != null) {
                if (rowIndex < paramList.size())
                    paramList.setElementAt(aValue, rowIndex);
            }
        }

        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return false;
        }

    }
}
