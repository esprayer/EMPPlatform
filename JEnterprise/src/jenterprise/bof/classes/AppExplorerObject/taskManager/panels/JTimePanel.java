package jenterprise.bof.classes.AppExplorerObject.taskManager.panels;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

import com.borland.jbcl.layout.*;
import com.pansoft.pub.comp.*;
import com.pansoft.swing.timespinner.*;
import jenterprise.bof.classes.AppExplorerObject.taskManager.*;
import jenterprise.bof.classes.AppExplorerObject.taskManager.JScheduleTaskManageWindow.*;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;

/**
 *
 * @version 1.0 初始版本${gengeng}
 */
public class JTimePanel
    extends JPanel implements ActionListener, ChangeListener {

    public static final int ModeOnce   = 1;
    public static final int ModePeriod = 2;
    public static final int ModeCycle  = 3;
    public static       int Mode;

    private JTable taskTable;

    public JTimePanel() {
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
     * 返回时间对象.
     */
    public Object getSelectedObject() {
        TimeObject obj = null;
        switch (Mode) {
            case ModeOnce:
                obj = new TimeObject(ModeOnce, onceRButton.getText());
                obj.date = dateComboBox.getSelectedItem().toString();
                obj.time = timeSpinner.getTimeValue();
                break;
            case ModePeriod:
                obj = new TimeObject(ModePeriod, periodRButton.getText());
                obj.hour = hourSpinner.getValue().toString();
                obj.minute = minuteSpinner.getValue().toString();
                obj.second = secondSpinner.getValue().toString();
                break;
            case ModeCycle:
                obj = new TimeObject(ModeCycle, cycleRButton.getText());
                obj.hour = hourSpinner1.getValue().toString();
                obj.minute = minuteSpinner1.getValue().toString();
                obj.second = secondSpinner1.getValue().toString();
                break;
        }
        return obj;
    }

    /**
     * 设置时间对象.
     */
    public void setTimeObject(TimeObject tObj) {
        if (tObj != null) {
            switch (tObj.timeModeID) {
                case ModeOnce:
                    setTimeOnce(tObj);
                    break;
                case ModePeriod:
                    setTimePeriod(tObj);
                    break;
                case ModeCycle:
                    setTimeCycle(tObj);
                    break;
            }
            processRadioButtonEvent();
        }
    }

    /**
     * 只运行一次
     */
    private void setTimeOnce(TimeObject tObj) {
        onceRButton.setSelected(true);
        dateComboBox.setSelectedItem(tObj.date);
        timeSpinner.setTimeValue(tObj.time);
    }

    /**
     * 一段时间后运行
     */
    private void setTimePeriod(TimeObject tObj) {
        periodRButton.setSelected(true);
        hourSpinner.setValue(new Integer(Integer.parseInt(tObj.hour)));
        minuteSpinner.setValue(new Integer(Integer.parseInt(tObj.minute)));
        secondSpinner.setValue(new Integer(Integer.parseInt(tObj.second)));
    }

    /**
     * 周期运行
     */
    private void setTimeCycle(TimeObject tObj) {
        cycleRButton.setSelected(true);
        hourSpinner1.setValue(new Integer(Integer.parseInt(tObj.hour)));
        minuteSpinner1.setValue(new Integer(Integer.parseInt(tObj.minute)));
        secondSpinner1.setValue(new Integer(Integer.parseInt(tObj.second)));
    }

    /**
     * 时间对象.
     */
    public static class TimeObject {

        public int    timeModeID;
        public String timeModeCaption;
        public String date;
        public String time;
        public String hour;
        public String minute;
        public String second;

        public TimeObject() {
        }

        public TimeObject(int tMode,String tModeCaption) {
            this.timeModeID = tMode;
            this.timeModeCaption = tModeCaption;
        }

        public String toString() {
            switch (Mode) {
                case ModeOnce:
                    return timeModeCaption + "【运行日期及时间:" + date + " " + time + "】";
                case ModePeriod:
                    return timeModeCaption + "【时间间隔:" + hour + "小时" + minute + "分钟" + second + "秒】";
                case ModeCycle:
                    return timeModeCaption + "【时间周期:" + hour + "小时" + minute + "分钟" + second + "秒】";
            }
            return "";
        }

    }

    private void jbInit() throws Exception {
        this.setLayout(verticalFlowLayout1);
        verticalFlowLayout1.setVgap(5);
        verticalFlowLayout2.setVgap(5);
        oncePanel.setLayout(flowLayout3);
        periodPanel.setLayout(flowLayout5);
        cyclePanel.setLayout(flowLayout4);
        jLabel1.setText("在");
        flowLayout3.setAlignment(FlowLayout.LEFT);
        jLabel2.setText("每");
        flowLayout4.setAlignment(FlowLayout.LEFT);
        jLabel3.setText("在");
        flowLayout5.setAlignment(FlowLayout.LEFT);
        jLabel4.setText("小时");
        jLabel5.setText("分钟");
        jLabel6.setText("秒后");
        jLabel7.setText("小时");
        jLabel8.setText("分钟");
        jLabel9.setText("秒");
        dateComboBox.setPreferredSize(new Dimension(100, 22));
        dateComboBox.addActionListener(this);
        timeSpinner.setPreferredSize(new Dimension(90, 22));
        timeSpinner.addChangeListener(this);
        hourSpinner.setPreferredSize(new Dimension(40, 22));
        hourSpinner.addChangeListener(this);
        minuteSpinner.setPreferredSize(new Dimension(40, 22));
        minuteSpinner.addChangeListener(this);
        secondSpinner.setPreferredSize(new Dimension(40, 22));
        secondSpinner.addChangeListener(this);
        hourSpinner1.setPreferredSize(new Dimension(40, 22));
        hourSpinner1.addChangeListener(this);
        minuteSpinner1.setPreferredSize(new Dimension(40, 22));
        minuteSpinner1.addChangeListener(this);
        secondSpinner1.setPreferredSize(new Dimension(40, 22));
        secondSpinner1.addChangeListener(this);
        onceRButton.setPreferredSize(new Dimension(120, 25));
        onceRButton.setText("仅运行一次");
        onceRButton.addActionListener(this);
        periodRButton.setPreferredSize(new Dimension(120, 25));
        periodRButton.setText("一段时间后运行");
        periodRButton.addActionListener(this);
        cycleRButton.setPreferredSize(new Dimension(120, 25));
        cycleRButton.setText("周期性运行");
        cycleRButton.addActionListener(this);
        oncePanel.add(onceRButton);
        oncePanel.add(jLabel1);
        oncePanel.add(dateComboBox);
        oncePanel.add(this.timeSpinner);
        this.add(oncePanel);
        this.add(periodPanel);
        this.add(cyclePanel);
        periodPanel.add(periodRButton);
        periodPanel.add(jLabel3);
        periodPanel.add(hourSpinner);
        periodPanel.add(jLabel4);
        periodPanel.add(minuteSpinner);
        periodPanel.add(jLabel5);
        periodPanel.add(secondSpinner);
        periodPanel.add(jLabel6);
        cyclePanel.add(cycleRButton);
        cyclePanel.add(jLabel2);
        cyclePanel.add(hourSpinner1);
        cyclePanel.add(jLabel7);
        cyclePanel.add(minuteSpinner1);
        cyclePanel.add(jLabel8);
        cyclePanel.add(secondSpinner1);
        cyclePanel.add(jLabel9);
        group.add(cycleRButton);
        group.add(onceRButton);
        group.add(periodRButton);
        onceRButton.setSelected(true);
        processRadioButtonEvent();
        setRange();
    }

    /**
     * 设置小时／分钟／秒的最大最小值
     */
    private void setRange() {
        setMaxMinValue(hourSpinner, 23, 0);
        setMaxMinValue(hourSpinner1, 23, 0);
        setMaxMinValue(minuteSpinner, 59, 0);
        setMaxMinValue(minuteSpinner1, 59, 0);
        setMaxMinValue(secondSpinner, 59, 0);
        setMaxMinValue(secondSpinner1, 59, 0);
    }

    private void setMaxMinValue(JSpinner spinner, int max, int min) {
        SpinnerNumberModel model;
        model = (SpinnerNumberModel) spinner.getModel();
        model.setMaximum(new Integer(max));
        model.setMinimum(new Integer(min));
    }

    /**
     * 时间方式:时间方案选择.
     */
    private void processRadioButtonEvent() {
        dateComboBox.setEnabled(onceRButton.isSelected());
        timeSpinner.setEnabled(onceRButton.isSelected());
        hourSpinner.setEnabled(periodRButton.isSelected());
        minuteSpinner.setEnabled(periodRButton.isSelected());
        secondSpinner.setEnabled(periodRButton.isSelected());
        hourSpinner1.setEnabled(cycleRButton.isSelected());
        minuteSpinner1.setEnabled(cycleRButton.isSelected());
        secondSpinner1.setEnabled(cycleRButton.isSelected());
        if (onceRButton.isSelected()) {
            Mode = ModeOnce;
        } else if (periodRButton.isSelected()) {
            Mode = ModePeriod;
        } else if (cycleRButton.isSelected()) {
            Mode = ModeCycle;
        }
    }

    /**
     * 改变ScheduleObject对象.
     */
    private void processTaskTable() {
        TimeObject tObj = (TimeObject) getSelectedObject();
        if (taskTable == null)
            return;
        int index = taskTable.getSelectedRow();
        if (index > -1) {
            JTaskTableModel model = (JTaskTableModel) taskTable.getModel();
            JTaskObject task = (JTaskObject) model.taskList.get(index);
            task.schedule.modeContent = tObj;
            model.setValueAt(task, index, 0);
        }
    }

    VerticalFlowLayout verticalFlowLayout1 = new VerticalFlowLayout();
    VerticalFlowLayout verticalFlowLayout2 = new VerticalFlowLayout();
    JPanel oncePanel = new JPanel();
    JPanel periodPanel = new JPanel();
    JPanel cyclePanel = new JPanel();
    JLabel jLabel1 = new JLabel();
    FlowLayout flowLayout3 = new FlowLayout();
    JDateComboBox dateComboBox = new JDateComboBox();
    JTimeSpinner timeSpinner = new JTimeSpinner();
    JLabel jLabel2 = new JLabel();
    FlowLayout flowLayout4 = new FlowLayout();
    FlowLayout flowLayout5 = new FlowLayout();
    JLabel jLabel3 = new JLabel();
    JSpinner hourSpinner = new JSpinner();
    JLabel jLabel4 = new JLabel();
    JSpinner minuteSpinner = new JSpinner();
    JLabel jLabel5 = new JLabel();
    JSpinner secondSpinner = new JSpinner();
    JLabel jLabel6 = new JLabel();
    JSpinner hourSpinner1 = new JSpinner();
    JLabel jLabel7 = new JLabel();
    JSpinner minuteSpinner1 = new JSpinner();
    JLabel jLabel8 = new JLabel();
    JSpinner secondSpinner1 = new JSpinner();
    JLabel jLabel9 = new JLabel();
    JRadioButton onceRButton = new JRadioButton();
    JRadioButton periodRButton = new JRadioButton();
    JRadioButton cycleRButton = new JRadioButton();
    Border border1 = BorderFactory.createEtchedBorder(Color.white, new Color(165, 163, 151));
    ButtonGroup group = new ButtonGroup();

    /**
     * Invoked when an action occurs.
     *
     * @param e ActionEvent
     * @todo Implement this java.awt.event.ActionListener method
     */
    public void actionPerformed(ActionEvent e) {
        try {
            setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            if (e.getSource() == onceRButton
                || e.getSource() == periodRButton
                || e.getSource() == cycleRButton) {
                processRadioButtonEvent();
                processTaskTable();
            }
        } finally {
            setCursor(Cursor.getDefaultCursor());
        }

    }

    /**
     * Invoked when the target of the listener has changed its state.
     *
     * @param e a ChangeEvent object
     * @todo Implement this javax.swing.event.ChangeListener method
     */
    public void stateChanged(ChangeEvent e) {
        if (e.getSource() == timeSpinner
            || e.getSource() == hourSpinner
            || e.getSource() == minuteSpinner
            || e.getSource() == secondSpinner
            || e.getSource() == hourSpinner1
            || e.getSource() == minuteSpinner1
            || e.getSource() == secondSpinner1) {
            processTaskTable();
        }
    }
}
