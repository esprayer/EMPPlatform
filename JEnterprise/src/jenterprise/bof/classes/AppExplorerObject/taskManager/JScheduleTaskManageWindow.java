package jenterprise.bof.classes.AppExplorerObject.taskManager;

import java.util.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;

import jbof.gui.window.classes.style.mdi.*;
import jenterprise.bof.classes.AppExplorerObject.taskManager.panels.*;
import jenterprise.bof.classes.AppExplorerObject.taskManager.wizard.*;
import jframework.foundation.classes.*;
import jenterprise.bof.classes.AppExplorerObject.taskManager.JScheduleTaskManageWindow.JTaskTableModel;

/**
 * 定制任务管理界面．
 *
 * @version 1.0 初始版本${gengeng}
 */
public class JScheduleTaskManageWindow
    extends JBOFMDIChildWindow implements ActionListener, MouseListener {

    public JScheduleTaskManageWindow() {
        try {
            jbInit();
            prepare();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 初始化。
     */
    private void prepare() {
        Vector v = JTaskUtil.getScheduledTaskList();
        taskTable.setModel(new JTaskTableModel(v));
        setTaskTable();
        taskTable.updateUI();
    }

    private void jbInit() throws Exception {
        this.setLayout(borderLayout2);
        cmdPanel.setLayout(flowLayout2);
        flowLayout2.setAlignment(FlowLayout.RIGHT);
        mainSplitPane.setPreferredSize(new Dimension(390, 550));
        taskPanel.setLayout(borderLayout1);
        topPanel.setLayout(flowLayout1);
        flowLayout1.setAlignment(FlowLayout.LEFT);
        runButton.setPreferredSize(new Dimension(22, 22));
        runButton.setIcon(JTaskUtil.getIcon("run.gif"));
        runButton.setToolTipText("运行");
        runButton.addActionListener(this);
        resetButton.setPreferredSize(new Dimension(22, 22));
        resetButton.setIcon(JTaskUtil.getIcon("reset.gif"));
        resetButton.setToolTipText("重新启动");
        resetButton.addActionListener(this);
        delButton.setPreferredSize(new Dimension(22, 22));
        delButton.setIcon(JTaskUtil.getIcon("delete.gif"));
        delButton.setToolTipText("删除所选任务");
        delButton.addActionListener(this);
        addButton.setPreferredSize(new Dimension(22, 22));
        addButton.setIcon(JTaskUtil.getIcon("add.gif"));
        addButton.setToolTipText("添加一个任务");
        addButton.addActionListener(this);
        taskTable.setRowHeight(20);
        applyButton.setPreferredSize(new Dimension(80, 25));
        applyButton.setMnemonic('A');
        applyButton.setText("应用(A)");
        applyButton.addActionListener(this);
        okButton.setPreferredSize(new Dimension(80, 25));
        okButton.setMnemonic('O');
        okButton.setText("确定(O)");
        okButton.addActionListener(this);
        cancelButton.setPreferredSize(new Dimension(80, 25));
        cancelButton.setMnemonic('C');
        cancelButton.setText("取消(C)");
        cancelButton.addActionListener(this);
        centerTabbedPane.add(schedulePanel, "计划");
        centerTabbedPane.add(actionPanel, "动作");
        centerTabbedPane.add(paramPanel, "参数");
        centerTabbedPane.add(logPanel, "日志");
        cmdPanel.add(applyButton);
        cmdPanel.add(okButton);
        cmdPanel.add(cancelButton);
        mainSplitPane.add(taskPanel, JSplitPane.LEFT);
        mainSplitPane.add(centerTabbedPane, JSplitPane.RIGHT);
        taskPanel.add(topPanel, java.awt.BorderLayout.NORTH);
        topPanel.add(addButton);
        topPanel.add(delButton);
        topPanel.add(runButton);
//        topPanel.add(resetButton);
        taskPanel.add(taskScrollPane, java.awt.BorderLayout.CENTER);
        taskScrollPane.getViewport().add(taskTable);
        this.add(mainSplitPane, java.awt.BorderLayout.CENTER);
        this.add(cmdPanel, java.awt.BorderLayout.SOUTH);
        centerTabbedPane.setSelectedComponent(schedulePanel);
        taskTable.addMouseListener(this);
        mainSplitPane.setDividerLocation(240);
    }

    /**
     * 调整一下任务表.
     */
    private void setTaskTable() {
        taskTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        taskTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        TableColumn column;
        column = taskTable.getColumn(taskTable.getColumnName(0));
        column.setPreferredWidth(50);
        column = taskTable.getColumn(taskTable.getColumnName(1));
        column.setPreferredWidth(168);
        schedulePanel.setTaskTable(taskTable);
    }

    JPanel cmdPanel = new JPanel();
    JTabbedPane centerTabbedPane = new JTabbedPane();
    FlowLayout flowLayout2 = new FlowLayout();
    JSchedulePanel schedulePanel = new JSchedulePanel();
    JActionPanel actionPanel = new JActionPanel();
    JParamPanel paramPanel = new JParamPanel();
    JLogPanel logPanel = new JLogPanel();
    JSplitPane mainSplitPane = new JSplitPane();
    JPanel taskPanel = new JPanel();
    JPanel topPanel = new JPanel();
    BorderLayout borderLayout1 = new BorderLayout();
    FlowLayout flowLayout1 = new FlowLayout();
    JButton runButton = new JButton();
    JButton resetButton = new JButton();
    JButton delButton = new JButton();
    JButton addButton = new JButton();
    JTable taskTable = new JTable();
    JScrollPane taskScrollPane = new JScrollPane();
    JButton applyButton = new JButton();
    JButton okButton = new JButton();
    JButton cancelButton = new JButton();
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
            if (e.getSource() == this.addButton) {
                processAddEvent();
            } else if (e.getSource() == delButton) {
                processDeleteEvent();
            } else if (e.getSource() == runButton) {
                processRunEvent();
            } else if (e.getSource() == resetButton) {
                processResetEvent();
            } else if (e.getSource() == applyButton) {
                processApplyEvent();
            } else if (e.getSource() == okButton) {
                processOKEvent();
            } else if (e.getSource() == cancelButton) {
                processCancelEvent();
            }
        } finally {
            setCursor(Cursor.getDefaultCursor());
        }
    }

    /**
     * 增加一个任务
     */
    private void processAddEvent() {
        Frame frame = JActiveDComDM.MainApplication.MainWindow;
        JAddTaskWizardDialog taskWizard;
        taskWizard = new JAddTaskWizardDialog(frame, "任务添加向导", true);
        taskWizard.setSize(594, 400);
        taskWizard.CenterWindow();
        taskWizard.setVisible(true);
        if (taskWizard.OPTION == taskWizard.OPTION_OK) {
            int index = taskTable.getRowCount();
            Object tObj = taskWizard.getTaskObject();
            if (tObj == null)
                return;
            taskTable.getModel().setValueAt(tObj, index, 0);
            taskTable.setRowSelectionInterval(index, index);
            processTaskTableClickEvent();
            taskTable.updateUI();
        }
    }

    /**
     * 删除一个任务
     */
    private void processDeleteEvent() {
        int index = taskTable.getSelectedRow();
        if (index == -1)
            return;
        int rtn = JOptionPane.showConfirmDialog(this,
                                                "确定要删除该任务吗?",
                                                "提示",
                                                JOptionPane.OK_CANCEL_OPTION);
        if (rtn == JOptionPane.OK_OPTION) {
            JTaskTableModel model = (JTaskTableModel) taskTable.getModel();
            if (index < model.taskList.size())
                model.taskList.removeElementAt(index);
            taskTable.updateUI();
        }
    }

    /**
     * 试运行一次。
     */
    private void processRunEvent() {

    }

    /**
     * 重新运行一个任务
     */
    private void processResetEvent() {

    }

    /**
     * 应用。就是将新加的任务加入到运行的行列中去。
     */
    private void processApplyEvent() {
        //首先保存任务
        JTaskTableModel model = (JTaskTableModel) taskTable.getModel();
        Object         rtnObj = JTaskUtil.saveScheduledTaskList(model.taskList);
        //

    }

    /**
     * 确定。先应用，然后关闭窗口。
     */
    private void processOKEvent() {

    }

    /**
     * 取消
     */
    private void processCancelEvent() {
    }

    /**
     * 任务列表Model.
     */
    public class JTaskTableModel
        extends AbstractTableModel {

        public Vector taskList;

        public JTaskTableModel(Vector taskList) {
            this.taskList = taskList;
        }

        public int getRowCount() {
            if (taskList != null)
                return taskList.size();
            return 0;
        }

        public String getColumnName(int columnIndex) {
            if (columnIndex == 0)
                return "序号";
            if (columnIndex == 1)
                return "任务名称";
            return "";
        }

        public int getColumnCount() {
            return 2;
        }

        public Object getValueAt(int rowIndex, int columnIndex) {
            JTaskObject tObj;
            if (taskList != null && rowIndex < taskList.size()) {
                tObj = (JTaskObject) taskList.get(rowIndex);
                if (tObj != null) {
                    if (columnIndex == 0)
                        return String.valueOf(++rowIndex);
                    if (columnIndex == 1)
                        return tObj.taskMC;
                }
            }
            return null;
        }

        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return false;
        }

        public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
            if (taskList != null) {
                if (rowIndex < taskList.size())
                    taskList.removeElementAt(rowIndex);
                taskList.insertElementAt(aValue, rowIndex);
            }
        }
    }

    /**
     * Invoked when the mouse button has been clicked (pressed and released)
     * on a component.
     *
     * @param e MouseEvent
     * @todo Implement this java.awt.event.MouseListener method
     */
    public void mouseClicked(MouseEvent e) {
        if (SwingUtilities.isRightMouseButton(e)) {
            JPopupMenu menu = new JPopupMenu("PopupMenu"); //右键
            menu.show(taskTable, e.getX(), e.getY());
        } else if (SwingUtilities.isLeftMouseButton(e)) {
            if (e.getSource() == taskTable) {
                processTaskTableClickEvent();
            }
        }
    }

    /**
     * 任务表左键单击事件.
     */
    private void processTaskTableClickEvent() {
        int rowIndex = taskTable.getSelectedRow();
        if (rowIndex >= 0) {
            JTaskTableModel tModel = (JTaskTableModel) taskTable.getModel();
            Vector taskList = tModel.taskList;
            if (taskList != null) {
                JTaskObject tObj = (JTaskObject) taskList.get(rowIndex);
                schedulePanel.setScheduleObject(tObj.schedule);
                actionPanel.setActionObject(tObj.action);
                paramPanel.setParamObject(tObj.param);
                centerTabbedPane.setSelectedIndex(0);
                centerTabbedPane.setVisible(false);
                centerTabbedPane.setVisible(true);
            }
        }
    }

    /**
     * Invoked when a mouse button has been pressed on a component.
     *
     * @param e MouseEvent
     * @todo Implement this java.awt.event.MouseListener method
     */
    public void mousePressed(MouseEvent e) {
    }

    /**
     * Invoked when a mouse button has been released on a component.
     *
     * @param e MouseEvent
     * @todo Implement this java.awt.event.MouseListener method
     */
    public void mouseReleased(MouseEvent e) {
    }

    /**
     * Invoked when the mouse enters a component.
     *
     * @param e MouseEvent
     * @todo Implement this java.awt.event.MouseListener method
     */
    public void mouseEntered(MouseEvent e) {
    }

    /**
     * Invoked when the mouse exits a component.
     *
     * @param e MouseEvent
     * @todo Implement this java.awt.event.MouseListener method
     */
    public void mouseExited(MouseEvent e) {
    }

}
