package jenterprise.bof.classes.AppExplorerObject.taskManager.panels;

import java.util.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;

import com.borland.jbcl.layout.*;
import jenterprise.bof.classes.AppExplorerObject.taskManager.subdlg.*;
import jenterprise.bof.classes.AppExplorerObject.taskManager.subdlg.JManageCTimeDialog.*;
import jframework.foundation.classes.*;
import jenterprise.bof.classes.AppExplorerObject.taskManager.JScheduleTaskManageWindow.JTaskTableModel;
import jenterprise.bof.classes.AppExplorerObject.taskManager.JTaskObject;

/**
 *
 * @version 1.0
 */
public class JCTimePanel
    extends JPanel implements ActionListener {

    private JTable taskTable;

    public JCTimePanel() {
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     *
     */
    public void setTaskTable(JTable tTable) {
        this.taskTable = tTable;
    }

    /**
     * 改变ScheduleObject对象.
     */
    private void processTaskTable() {
        Vector v = getCTimeList();
        if (taskTable == null)
            return;
        int index = taskTable.getSelectedRow();
        if (index > -1) {
            JTaskTableModel model = (JTaskTableModel) taskTable.getModel();
            JTaskObject task = (JTaskObject) model.taskList.get(index);
            task.schedule.modeContent = v;
            model.setValueAt(task, index, 0);
        }
    }


    /**
     * 返回复杂时间对象方案。
     */
    public Vector getCTimeList() {
        JTimeTableModel model = (JTimeTableModel) timeTable.getModel();
        return model.timeList;
    }

    /**
     * 设置复杂时间对象方案。
     */
    public void setCTimeList(Vector timeList) {
        timeTable.setModel(new JTimeTableModel(timeList));
    }

    class JTimeTableModel
        extends AbstractTableModel {

        public Vector timeList;

        public JTimeTableModel(Vector list) {
            timeList = list;
            if (timeList == null) {
                timeList = new Vector();
            }
        }

        public int getRowCount() {
            if (timeList != null)
                return timeList.size();
            return 0;
        }

        public String getColumnName(int columnIndex) {
            if (columnIndex == 0)
                return "时间方案";
            return "";
        }

        public int getColumnCount() {
            return 1;
        }

        public Object getValueAt(int rowIndex, int columnIndex) {
            if (timeList != null) {
                if (rowIndex < timeList.size()) {
                    JCTimeObject ctObj = (JCTimeObject) timeList.get(rowIndex);
                    if (columnIndex == 0) {
                        return ctObj.toString();
                    }
                }
            }
            return null;
        }

        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return false;
        }

        public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
            if (timeList != null) {
                if (rowIndex < timeList.size())
                    timeList.setElementAt(aValue, rowIndex);
                else
                    timeList.add(aValue);
            }
        }

    }

    private void jbInit() throws Exception {
        editButton.setPreferredSize(new Dimension(80, 22));
        editButton.setMnemonic('E');
        editButton.setText("编辑(E)");
        editButton.addActionListener(this);
        addButton.setPreferredSize(new Dimension(80, 22));
        addButton.setMnemonic('A');
        addButton.setText("添加(A)");
        addButton.addActionListener(this);
        delButton.setPreferredSize(new Dimension(80, 22));
        delButton.setMnemonic('D');
        delButton.setText("删除(D)");
        delButton.addActionListener(this);
        this.setLayout(borderLayout1);
        jPanel2.setLayout(verticalFlowLayout1);
        timeTable.setRowHeight(18);
        this.setPreferredSize(new Dimension(540, 204));
        timeScrollPane.setMinimumSize(new Dimension(19, 50));
        centerPanel.setLayout(borderLayout2);
        this.add(jPanel2, java.awt.BorderLayout.EAST);
        jPanel2.add(addButton);
        jPanel2.add(editButton);
        jPanel2.add(delButton);
        this.add(centerPanel, java.awt.BorderLayout.CENTER);
        centerPanel.add(timeScrollPane, java.awt.BorderLayout.CENTER);
        timeScrollPane.getViewport().add(timeTable);
        timeScrollPane.setAutoscrolls(true);
        timeTable.setModel(new JTimeTableModel(null));
    }

    JButton editButton = new JButton();
    JButton addButton = new JButton();
    JPanel jPanel2 = new JPanel();
    JButton delButton = new JButton();
    BorderLayout borderLayout1 = new BorderLayout();
    VerticalFlowLayout verticalFlowLayout1 = new VerticalFlowLayout();
    JScrollPane timeScrollPane = new JScrollPane();
    JTable timeTable = new JTable();
    JPanel centerPanel = new JPanel();
    BorderLayout borderLayout2 = new BorderLayout();

    /**
     * Invoked when an action occurs.
     *
     * @param e ActionEvent
     * @todo Implement this java.awt.event.ActionListener method
     */
    public void actionPerformed(ActionEvent e) {
        try {
            setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            if (e.getSource() == addButton) {
                processAddEvent();
            } else if (e.getSource() == editButton) {
                processEditEvent();
            } else if (e.getSource() == delButton) {
                processDeleteEvent();
            }
            processTaskTable();
        } finally {
            setCursor(Cursor.getDefaultCursor());
        }
    }

    /**
     * 增加
     */
    private void processAddEvent() {
        Frame frame = JActiveDComDM.MainApplication.MainWindow;
        JManageCTimeDialog ctimeDlg;
        ctimeDlg = new JManageCTimeDialog(frame, "添加时间方案", true);
        ctimeDlg.setSize(480, 310);
        ctimeDlg.setMinimumSize(480, 310);
        ctimeDlg.CenterWindow();
        ctimeDlg.setVisible(true);
        if (ctimeDlg.OPTION == ctimeDlg.OPTION_OK) {
            int index = timeTable.getRowCount();
            timeTable.getModel().setValueAt(ctimeDlg.getCTimeObject(), index, 0);
            timeTable.updateUI();
        }
    }

    /**
     * 编辑
     */
    private void processEditEvent() {
        int count = timeTable.getModel().getRowCount();
        if (count == 0)
            return;
        int index = timeTable.getSelectedRow();
        if (index < 0)
            return;
        JTimeTableModel model = (JTimeTableModel) timeTable.getModel();
        JCTimeObject obj = (JCTimeObject) model.timeList.get(index);

        Frame frame = JActiveDComDM.MainApplication.MainWindow;
        JManageCTimeDialog actionDlg;
        actionDlg = new JManageCTimeDialog(frame, "编辑时间方案", true);
        actionDlg.setSize(480, 310);
        actionDlg.CenterWindow();
        actionDlg.setCTimeObject(obj);
        actionDlg.setVisible(true);
        if (actionDlg.OPTION == actionDlg.OPTION_OK) {
            timeTable.getModel().setValueAt(actionDlg.getCTimeObject(), index, 0);
            timeTable.updateUI();
        }
    }

    /**
     * 删除
     */
    private void processDeleteEvent() {
        int index = timeTable.getSelectedRow();
        if (index == -1)
            return;
        JTimeTableModel model = (JTimeTableModel) timeTable.getModel();
        if (index < model.timeList.size())
            model.timeList.removeElementAt(index);
        timeTable.updateUI();
    }

}
