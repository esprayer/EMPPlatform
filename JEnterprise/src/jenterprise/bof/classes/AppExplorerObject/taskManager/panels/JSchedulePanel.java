package jenterprise.bof.classes.AppExplorerObject.taskManager.panels;

import java.util.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

import com.borland.jbcl.layout.*;
import jenterprise.bof.classes.AppExplorerObject.taskManager.*;
import jenterprise.bof.classes.AppExplorerObject.taskManager.panels.JTimePanel.*;
import jenterprise.bof.classes.AppExplorerObject.taskManager.wizard.*;
import jenterprise.bof.classes.AppExplorerObject.taskManager.JScheduleTaskManageWindow.JTaskTableModel;
import jenterprise.bof.classes.AppExplorerObject.taskManager.panels.JSchedulePanel.ScheduleObject;

/**
 * 定制任务模型.计划面板
 *
 * @version 1.0
 */
public class JSchedulePanel
    extends JPanel implements ActionListener, ItemListener, IStepable {

    private String[] modeText = {"从不运行", "时间方式运行", "事件方式运行", "复杂时间方式运行"};
    private DefaultComboBoxModel cModel = new DefaultComboBoxModel(modeText);
    JTimePanel  timePanel  = new JTimePanel();
    JEventPanel eventPanel = new JEventPanel();
    JCTimePanel ctimePanel = new JCTimePanel();
    private String errMessage;

    public              int ScheduleType      = 0;//任务执行方式
    public static final int ScheduleTypeNone  = 1;//从不运行
    public static final int ScheduleTypeTime  = 2;//时间方式运行
    public static final int ScheduleTypeEvent = 3;//事件方式运行
    public static final int ScheduleTypeCTime = 4;//复杂时间方式运行

    private JTable taskTable;

    public JSchedulePanel() {
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 任务列表.
     */
    public void setTaskTable(JTable tTable) {
        taskTable = tTable;
    }

    /**
     * 动作事件.
     *
     * @param e ActionEvent
     * @todo Implement this java.awt.event.ActionListener method
     */
    public void actionPerformed(ActionEvent e) {
        try {
            setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            if (e.getSource() == neverButton) {
                processButtonEvent1();
            } else if (e.getSource() == timeButton) {
                processButtonEvent2();
            } else if (e.getSource() == eventButton) {
                processButtonEvent3();
            } else if (e.getSource() == ctimeButton) {
                processButtonEvent4();
            }
            processTaskTable();
        } finally {
            setCursor(Cursor.getDefaultCursor());
        }
    }

    /**
     * 改变ScheduleObject对象.
     */
    private void processTaskTable() {
        ScheduleObject sObj = (ScheduleObject) getSelectedInfo();
        int index = taskTable.getSelectedRow();
        if (index > -1) {
            JTaskTableModel model = (JTaskTableModel) taskTable.getModel();
            JTaskObject tObj = (JTaskObject) model.taskList.get(index);
            tObj.schedule = sObj;
            model.setValueAt(tObj, index, 0);
        }
    }

    /**
     * 从不运行。
     */
    private void processButtonEvent1() {
        ScheduleType = ScheduleTypeNone;
        this.removeAll();
        this.addBasicPanel();
        this.cModel.setSelectedItem(modeText[0]);
        refreshPanel();
    }

    /**
     * 时间方式运行。
     */
    private void processButtonEvent2() {
        ScheduleType = ScheduleTypeTime;
        this.removeAll();
        this.addBasicPanel();
        this.add(timePanel);
        this.cModel.setSelectedItem(modeText[1]);
        refreshPanel();
    }

    /**
     * 事件方式运行。
     */
    private void processButtonEvent3() {
        ScheduleType = ScheduleTypeEvent;
        this.removeAll();
        this.addBasicPanel();
        this.add(eventPanel);
        this.cModel.setSelectedItem(modeText[2]);
        refreshPanel();
    }

    /**
     * 复杂时间方式运行。
     */
    private void processButtonEvent4() {
        ScheduleType = ScheduleTypeCTime;
        this.removeAll();
        this.addBasicPanel();
        this.add(ctimePanel);
        this.cModel.setSelectedItem(modeText[3]);
        refreshPanel();
    }

    /**
     * 添加基本的组件。
     */
    private void addBasicPanel() {
        this.add(namePanel);
        this.add(modePanel);
        this.add(jPanel1);
        refreshPanel();
        this.timePanel.setTaskTable(taskTable);
    }

    /**
     * 刷新。
     */
    private void refreshPanel() {
        this.setVisible(false);
        this.setVisible(true);
    }

    /**
     * Invoked when an item has been selected or deselected by the user.
     *
     * @param e ItemEvent
     * @todo Implement this java.awt.event.ItemListener method
     */
    public void itemStateChanged(ItemEvent e) {
        if (e.getSource() == modeComboBox) {
            processComboBoxEvent();
            processTaskTable();
        }
    }

    private void processComboBoxEvent() {
        int index = modeComboBox.getSelectedIndex();
        if (index > -1) {
            switch (index) {
                case 0:
                    processButtonEvent1();
                    break;
                case 1:
                    processButtonEvent2();
                    break;
                case 2:
                    processButtonEvent3();
                    break;
                case 3:
                    processButtonEvent4();
                    break;
            }
        }
    }

    private void jbInit() throws Exception {
        this.setLayout(verticalFlowLayout1);
        verticalFlowLayout1.setVgap(5);
        namePanel.setLayout(flowLayout1);
        modePanel.setLayout(flowLayout2);
        flowLayout1.setAlignment(FlowLayout.LEFT);
        flowLayout2.setAlignment(FlowLayout.LEFT);
        nameLabel.setText("任务名称:");
        nameTextField.setPreferredSize(new Dimension(300, 22));
        nameTextField.setText("");
        ctimeButton.setPreferredSize(new Dimension(22, 22));
        ctimeButton.setIcon(JTaskUtil.getIcon("complexTime.gif"));
        ctimeButton.setToolTipText("复杂时间方式运行");
        ctimeButton.addActionListener(this);
        eventButton.setPreferredSize(new Dimension(22, 22));
        eventButton.setIcon(JTaskUtil.getIcon("event.gif"));
        eventButton.setToolTipText("事件方式运行");
        eventButton.addActionListener(this);
        timeButton.setPreferredSize(new Dimension(22, 22));
        timeButton.setIcon(JTaskUtil.getIcon("schedule.gif"));
        timeButton.setToolTipText("时间方式运行");
        timeButton.addActionListener(this);
        neverButton.setPreferredSize(new Dimension(22, 22));
        neverButton.setIcon(JTaskUtil.getIcon("never.gif"));
        neverButton.setToolTipText("从不运行");
        neverButton.addActionListener(this);
        jLabel.setText("运行方式:");
        modeComboBox.setPreferredSize(new Dimension(192, 22));
        flowLayout3.setAlignment(FlowLayout.LEFT);
        flowLayout4.setAlignment(FlowLayout.LEFT);
        flowLayout5.setAlignment(FlowLayout.LEFT);
        jPanel1.setBorder(border1);
        jPanel1.setPreferredSize(new Dimension(391, 2));
        namePanel.add(nameLabel);
        namePanel.add(nameTextField);
        modePanel.add(jLabel);
        modePanel.add(neverButton);
        modePanel.add(timeButton);
        modePanel.add(eventButton);
        modePanel.add(ctimeButton);
        modePanel.add(modeComboBox);
        modeComboBox.addItemListener(this);
        modeComboBox.setModel(cModel);
        addBasicPanel();
    }

    VerticalFlowLayout verticalFlowLayout1 = new VerticalFlowLayout();
    JPanel modePanel = new JPanel();
    JPanel namePanel = new JPanel();
    FlowLayout flowLayout1 = new FlowLayout();
    FlowLayout flowLayout2 = new FlowLayout();
    JLabel nameLabel = new JLabel();
    JTextField nameTextField = new JTextField();
    JComboBox modeComboBox = new JComboBox();
    JButton ctimeButton = new JButton();
    JButton eventButton = new JButton();
    JButton timeButton = new JButton();
    JButton neverButton = new JButton();
    JLabel jLabel = new JLabel();
    FlowLayout flowLayout3 = new FlowLayout();
    FlowLayout flowLayout4 = new FlowLayout();
    FlowLayout flowLayout5 = new FlowLayout();
    JPanel jPanel1 = new JPanel();
    Border border1 = BorderFactory.createEtchedBorder(Color.white, new Color(165, 163, 151));
    ButtonGroup group = new ButtonGroup();

    /**
     * 验证该步骤的合法性．
     *
     * @return boolean
     * @todo Implement this
     *   jenterprise.bof.classes.AppExplorerObject.taskManager.wizard.IStepCheckable
     *   method
     */
    public boolean isValid() {
        //1.没有指定任务名称，不合法
        String taskName = nameTextField.getText().trim();
        if (taskName.length() == 0) {
            errMessage = "任务名称不能为空！";
            return false;
        }
        //2.指定了执行计划
        //复杂时间计划：如果没有定义任何计划，返回错误提示
        if (ScheduleType == ScheduleTypeCTime) {
            Vector ctime = ctimePanel.getCTimeList();
            if (ctime == null || ctime.size() == 0) {
                errMessage = "设置了复杂时间方式执行任务，但是却没有指定任何时间方案！";
                return false;
            }
        }
        return true;
    }

    /**
     * 返回错误信息．
     *
     * @return String
     * @todo Implement this
     *   jenterprise.bof.classes.AppExplorerObject.taskManager.wizard.IStepCheckable
     *   method
     */
    public String getInvalidMessage() {
        return errMessage;
    }

    /**
     * 返回设置的数据。
     *
     * @return Object
     * @todo Implement this
     *   jenterprise.bof.classes.AppExplorerObject.taskManager.wizard.IStepable
     *   method
     */
    public Object getSelectedInfo() {
        ScheduleObject sObj = new ScheduleObject();
        sObj.taskName = nameTextField.getText();
        switch (ScheduleType) {
            case ScheduleTypeNone:
                sObj.modeID    = ScheduleTypeNone;
                sObj.modeName  = modeText[0];
                break;
            case ScheduleTypeTime:
                sObj.modeID = ScheduleTypeTime;
                sObj.modeName = modeText[1];
                sObj.modeContent = timePanel.getSelectedObject();
                break;
            case ScheduleTypeEvent:
                sObj.modeID = ScheduleTypeEvent;
                sObj.modeName = modeText[2];
                sObj.modeContent = eventPanel.getSelectedObject();
                break;
            case ScheduleTypeCTime:
                sObj.modeID = ScheduleTypeCTime;
                sObj.modeName = modeText[3];
                sObj.modeContent = ctimePanel.getCTimeList();
                break;
        }

        return sObj;
    }

    /**
     * 设置计划对象.
     */
    public void setScheduleObject(ScheduleObject sObj) {
        if (sObj != null) {
            nameTextField.setText(sObj.taskName);
            switch (sObj.modeID) {
                case ScheduleTypeNone:
                    setNoneObject(sObj);
                    break;
                case ScheduleTypeTime:
                    setTimeObject(sObj);
                    break;
                case ScheduleTypeEvent:
                    setEventObject(sObj);
                    break;
                case ScheduleTypeCTime:
                    setCTimeObject(sObj);
                    break;
            }
        }
    }

    /**
     * 从不运行.
     */
    private void setNoneObject(ScheduleObject sObj) {
        processButtonEvent1();
    }

    /**
     * 设置时间对象.
     */
    private void setTimeObject(ScheduleObject sObj) {
        timePanel.setTimeObject( (TimeObject) sObj.modeContent);
        processButtonEvent2();
    }

    /**
     * 设置事件对象.
     */
    private void setEventObject(ScheduleObject sObj) {
        processButtonEvent3();
    }

    /**
     * 设置复杂时间对象.
     */
    private void setCTimeObject(ScheduleObject sObj) {
        ctimePanel.setCTimeList( (Vector) sObj.modeContent);
        processButtonEvent4();
    }

    /**
     * 计划对象.
     */
    public static class ScheduleObject {

        public String taskName;
        public int    modeID;
        public String modeName;
        public Object modeContent;

        public ScheduleObject() {
        }

        public ScheduleObject(String tName, int mID, String mName, Object mContent) {
            this.taskName    = tName;
            this.modeID      = mID;
            this.modeName    = mName;
            this.modeContent = mContent;
        }

    }
}
