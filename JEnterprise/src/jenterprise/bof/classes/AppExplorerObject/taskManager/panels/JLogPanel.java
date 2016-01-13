package jenterprise.bof.classes.AppExplorerObject.taskManager.panels;

import java.util.*;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;

import jenterprise.bof.classes.AppExplorerObject.taskManager.*;

/**
 *
 * @version 1.0 初始版本${gengeng}
 */
public class JLogPanel
    extends JPanel {

    public JLogPanel() {
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
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
        mainScrollPane.getViewport().add(logTable);
        menuPanel.add(toolBar);
        toolBar.add(delButton);
        mainPanel.add(mainScrollPane, java.awt.BorderLayout.CENTER);
        this.add(menuPanel, java.awt.BorderLayout.NORTH);
        this.add(mainPanel, java.awt.BorderLayout.CENTER);
        logTable.setModel(new JlogTableModel(null));
        setLogTable();
    }

    private void setLogTable() {
        logTable.setModel(new JlogTableModel(null));
        logTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        TableColumn column;
        column = logTable.getColumn(logTable.getColumnName(0));
        column.setPreferredWidth(100);
        column = logTable.getColumn(logTable.getColumnName(1));
        column.setPreferredWidth(80);
        column = logTable.getColumn(logTable.getColumnName(2));
        column.setPreferredWidth(80);
        column = logTable.getColumn(logTable.getColumnName(3));
        column.setPreferredWidth(470);
    }

    BorderLayout borderLayout1 = new BorderLayout();
    JPanel menuPanel = new JPanel();
    JPanel mainPanel = new JPanel();
    FlowLayout flowLayout1 = new FlowLayout();
    BorderLayout borderLayout2 = new BorderLayout();
    JToolBar toolBar = new JToolBar();
    JScrollPane mainScrollPane = new JScrollPane();
    JTable logTable = new JTable();
    JButton delButton = new JButton();

    class JlogTableModel
        extends AbstractTableModel {

        private Vector actionList;

        public JlogTableModel(Vector ROSList) {
            actionList = ROSList;
        }

        public int getRowCount() {
            if (actionList != null)
                return actionList.size();
            return 0;
        }

        public String getColumnName(int columnIndex) {
            switch (columnIndex) {
                case 0:
                    return "状态";
                case 1:
                    return "日期";
                case 2:
                    return "时间";
                case 3:
                    return "日志详情";
                default:
                    return "";
            }
        }

        public int getColumnCount() {
            return 4;
        }

        public Object getValueAt(int rowIndex, int columnIndex) {
            return null;
        }

        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return false;
        }

    }
}
