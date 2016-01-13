package jenterprise.bof.classes.AppExplorerObject.taskManager.panels;

import java.util.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;

import jenterprise.bof.classes.AppExplorerObject.taskManager.*;
import jenterprise.bof.classes.AppExplorerObject.taskManager.subdlg.*;
import jframework.foundation.classes.*;

/**
 * 动作界面。
 * 这个界面有别于向导中的动作选择界面，在那个界面中，只能并且必须选择一个已知的动作。
 * 在这里，可以定义多个动作，这些动作同属于一个任务；还可以编辑、删除任务。
 *
 * @version 1.0 初始版本${gengeng}
 */
public class JActionPanel
    extends JPanel implements ActionListener {

    private Vector actionList = new Vector();

    public JActionPanel() {
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 返回动作对象信息。
     * 在向导中只允许添加一个动作，在这里可以添加多个动作，返回的是动作列表。
     */
    public Object getActionObject() {
        JActionTableModel model = (JActionTableModel) actionTable.getModel();
        if (model != null)
            return model.actionList;
        return null;
    }

    /**
     * 设置动作列表。
     */
    public void setActionObject(Vector actionList) {
        if (actionList != null) {
            JActionTableModel model = (JActionTableModel) actionTable.getModel();
            if (model != null)
                model.actionList = actionList;
        }
    }

    /**
     * 设置单个动作。
     */
    public void setActionObject(ActionObject aObj) {
        JActionTableModel model = (JActionTableModel) actionTable.getModel();
        if (model != null)
            model.actionList.removeAllElements();
        if (aObj != null)
            model.setValueAt(aObj, 0, 0);
        actionTable.updateUI();
    }

    private void jbInit() throws Exception {
        this.setLayout(borderLayout1);
        menuPanel.setLayout(flowLayout1);
        flowLayout1.setAlignment(FlowLayout.LEFT);
        mainPanel.setLayout(borderLayout2);
        delButton.setPreferredSize(new Dimension(22, 22));
        delButton.setIcon(JTaskUtil.getIcon("delete.gif"));
        delButton.setToolTipText("删除");
        delButton.addActionListener(this);
        editButton.setPreferredSize(new Dimension(22, 22));
        editButton.setIcon(JTaskUtil.getIcon("edit.gif"));
        editButton.setToolTipText("编辑");
        editButton.addActionListener(this);
        addButton.setPreferredSize(new Dimension(22, 22));
        addButton.setIcon(JTaskUtil.getIcon("add.gif"));
        addButton.setToolTipText("添加");
        addButton.addActionListener(this);
        mainScrollPane.getViewport().add(actionTable);
        menuPanel.add(toolBar);
        toolBar.add(addButton);
        toolBar.add(editButton);
        toolBar.add(delButton);
        mainPanel.add(mainScrollPane, java.awt.BorderLayout.CENTER);
        this.add(menuPanel, java.awt.BorderLayout.NORTH);
        this.add(mainPanel, java.awt.BorderLayout.CENTER);
        setActionTable();
    }

    private void setActionTable() {
        actionTable.setModel(new JActionTableModel(actionList));
        actionTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        TableColumn column;
        column = actionTable.getColumn(actionTable.getColumnName(0));
        column.setPreferredWidth(180);
        column = actionTable.getColumn(actionTable.getColumnName(1));
        column.setPreferredWidth(540);
    }

    BorderLayout borderLayout1 = new BorderLayout();
    JPanel menuPanel = new JPanel();
    JPanel mainPanel = new JPanel();
    FlowLayout flowLayout1 = new FlowLayout();
    BorderLayout borderLayout2 = new BorderLayout();
    JToolBar toolBar = new JToolBar();
    JScrollPane mainScrollPane = new JScrollPane();
    JTable actionTable = new JTable();
    JButton delButton = new JButton();
    JButton editButton = new JButton();
    JButton addButton = new JButton();

    class JActionTableModel
        extends AbstractTableModel {

        public Vector actionList;

        public JActionTableModel(Vector ROSList) {
            actionList = ROSList;
        }

        public int getRowCount() {
            if (actionList != null)
                return actionList.size();
            return 0;
        }

        public String getColumnName(int columnIndex) {
            if (columnIndex == 0)
                return "动作名称";
            if (columnIndex == 1)
                return "动作参数";
            return "";
        }

        public int getColumnCount() {
            return 2;
        }

        public Object getValueAt(int rowIndex, int columnIndex) {
            ActionObject obj;
            if (actionList != null) {
                if (rowIndex <= actionList.size()) {
                    obj = (ActionObject) actionList.get(rowIndex);
                    if (columnIndex == 0)
                        return obj.actionMC;
                    else
                        return "【object:" + obj.objectID + "】，【method:" + obj.actionID + "】";
                }
            }
            return null;
        }

        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return false;
        }

        public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
            if (actionList != null) {
                if (rowIndex < actionList.size())
                    actionList.removeElementAt(rowIndex);
                actionList.insertElementAt(aValue, rowIndex);
            }
        }

    }

    /**
     * 动作对象。
     */
    public static class ActionObject {

        public String actionID;   //动作ID，也就是具体模块中的方法名
        public String actionMC;   //动作的中文说明
        public String objectID;   //动作的模块ID，比如ReportObject
        public String paramClass; //该动作对应的参数设置类
        public String auxiCaption;//附加的动作描述

        public ActionObject(String aID, String aMC, String oID) {
            this.actionID = aID;
            this.actionMC = aMC;
            this.objectID = oID;
        }

        public void setParamClassName(String clsName) {
            this.paramClass = clsName;
        }

        public void setAuxiCaption(String auxi) {
            this.auxiCaption = auxi;
        }

    }

    /**
     * Invoked when an action occurs.
     *
     * @param e ActionEvent
     * @todo Implement this java.awt.event.ActionListener method
     */
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addButton) {
            processAddEvent();
        } else if (e.getSource() == editButton) {
            processEditEvent();
        } else if (e.getSource() == delButton) {
            processDeleteEvent();
        }
    }

    /**
     * 增加
     */
    private void processAddEvent() {
        Frame frame = JActiveDComDM.MainApplication.MainWindow;
        JManageActionDialog actionDlg;
        actionDlg = new JManageActionDialog(frame, "添加动作", true);
        actionDlg.setSize(340, 180);
        actionDlg.CenterWindow();
        actionDlg.setVisible(true);
        if (actionDlg.OPTION == actionDlg.OPTION_OK) {
            int index = actionTable.getRowCount();
            actionTable.getModel().setValueAt(actionDlg.getActionObject(), index, 0);
            actionTable.updateUI();
        }
    }

    /**
     * 编辑
     */
    private void processEditEvent() {
        int count = actionTable.getModel().getRowCount();
        if (count == 0)
            return;
        int index = actionTable.getSelectedRow();
        if (index < 0)
            return;
        JActionTableModel model = (JActionTableModel) actionTable.getModel();
        ActionObject obj = (ActionObject) model.actionList.get(index);

        Frame frame = JActiveDComDM.MainApplication.MainWindow;
        JManageActionDialog actionDlg;
        actionDlg = new JManageActionDialog(frame, "编辑动作", true);
        actionDlg.setSize(340, 180);
        actionDlg.CenterWindow();
        actionDlg.setActionObject(obj);
        actionDlg.setVisible(true);
        if (actionDlg.OPTION == actionDlg.OPTION_OK) {
            actionTable.getModel().setValueAt(actionDlg.getActionObject(), index, 0);
            actionTable.updateUI();
        }
    }

    /**
     * 删除
     */
    private void processDeleteEvent() {
        int index = actionTable.getSelectedRow();
        if (index == -1)
            return;
        JActionTableModel model = (JActionTableModel) actionTable.getModel();
        if (index < actionList.size())
            model.actionList.removeElementAt(index);
        actionTable.updateUI();
    }

}
