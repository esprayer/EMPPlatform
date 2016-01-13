package jenterprise.bof.classes.AppExplorerObject.taskManager.subdlg;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

import com.borland.jbcl.layout.*;
import com.pansoft.swing.timespinner.*;
import jfoundation.gui.window.classes.*;
import java.util.Calendar;
import jenterprise.bof.classes.AppExplorerObject.taskManager.subdlg.JManageCTimeDialog.JCTimeObject;

/**
 * 复杂时间操作窗口。
 * 操作对一个复杂时间的添加、删除和编辑。
 *
 * @version 1.0
 */
public class JManageCTimeDialog
    extends JFrameDialog implements ActionListener {

    public final int OPTION_OK     = 0;
    public final int OPTION_CANCEL = 1;
    public       int OPTION;

    public JManageCTimeDialog(Frame frame, String title, boolean modal) {
        super(frame, title, modal);
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void jbInit() throws Exception {
        this.getContentPane().setLayout(borderLayout1);
        dayPanel.setLayout(verticalFlowLayout3);
        timePanel.setLayout(flowLayout2);
        flowLayout2.setAlignment(FlowLayout.LEFT);
        cancelButton.setPreferredSize(new Dimension(80, 22));
        cancelButton.setMnemonic('C');
        cancelButton.setText("取消(C)");
        cancelButton.addActionListener(this);
        okButton.setPreferredSize(new Dimension(80, 22));
        okButton.setMnemonic('O');
        okButton.setText("确定(O)");
        okButton.addActionListener(this);
        monthPanel.setLayout(verticalFlowLayout2);
        timeSpinner.setPreferredSize(new Dimension(80, 22));
        jPanel2.setLayout(flowLayout1);
        jPanel1.setLayout(gridLayout1);
        gridLayout1.setColumns(6);
        gridLayout1.setRows(2);
        cbM1.setText("1");
        cbM2.setText("2");
        cbM3.setText("3");
        cbM4.setText("4");
        cbM5.setText("5");
        cbM6.setText("6");
        cbM7.setText("7");
        cbM8.setText("8");
        cbM9.setText("9");
        cbM10.setText("10");
        cbM11.setText("11");
        cbM12.setText("12");
        jPanel1.setBorder(border7);
        selectMRButton.setToolTipText("");
        selectMRButton.setText("选择固定月份");
        everyMRButton.setText("每月");
        flowLayout1.setAlignment(FlowLayout.LEFT);
        flowLayout1.setHgap(0);
        jPanel4.setLayout(flowLayout3);
        flowLayout3.setAlignment(FlowLayout.LEFT);
        flowLayout3.setHgap(0);
        selectDRButton.setText("选择固定天");
        everyDRButton.setText("每天");
        jPanel3.setBorder(border8);
        jPanel3.setLayout(gridLayout2);
        gridLayout2.setColumns(6);
        gridLayout2.setRows(6);
        cbD1.setText("1");
        cbD2.setText("2");
        cbD3.setText("3");
        cbD4.setText("4");
        cbD5.setText("5");
        cbD6.setText("6");
        cbD7.setText("7");
        cbD8.setText("8");
        cbD9.setText("9");
        cbD10.setText("10");
        cbD11.setText("11");
        cbD12.setText("12");
        cbD13.setText("13");
        cbD14.setText("14");
        cbD15.setText("15");
        cbD16.setText("16");
        cbD17.setText("17");
        cbD18.setText("18");
        cbD19.setText("19");
        cbD20.setText("20");
        cbD21.setText("21");
        cbD22.setText("22");
        cbD23.setText("23");
        cbD24.setText("24");
        cbD25.setText("25");
        cbD26.setText("26");
        cbD27.setText("27");
        cbD28.setText("28");
        cbD29.setText("29");
        cbD30.setText("30");
        cbD31.setText("31");
        monthPanel.add(jPanel2);
        everyMRButton.setSelected(true);
        everyMRButton.addActionListener(this);
        selectMRButton.addActionListener(this);
        jPanel2.add(everyMRButton);
        jPanel2.add(selectMRButton);
        group1.add(everyMRButton);
        group1.add(selectMRButton);
        monthPanel.add(jPanel1);
        jPanel1.add(cbM1);
        jPanel1.add(cbM2);
        jPanel1.add(cbM3);
        jPanel1.add(cbM4);
        jPanel1.add(cbM5);
        jPanel1.add(cbM6);
        jPanel1.add(cbM7);
        jPanel1.add(cbM8);
        jPanel1.add(cbM9);
        jPanel1.add(cbM10);
        jPanel1.add(cbM11);
        jPanel1.add(cbM12);
        dayPanel.add(jPanel4);
        everyDRButton.addActionListener(this);
        selectDRButton.addActionListener(this);
        everyDRButton.setSelected(true);
        jPanel4.add(everyDRButton);
        jPanel4.add(selectDRButton);
        group2.add(everyDRButton);
        group2.add(selectDRButton);
        dayPanel.add(jPanel3);
        jPanel3.add(cbD1);
        jPanel3.add(cbD2);
        jPanel3.add(cbD3);
        jPanel3.add(cbD4);
        jPanel3.add(cbD5);
        jPanel3.add(cbD6);
        jPanel3.add(cbD7);
        jPanel3.add(cbD8);
        jPanel3.add(cbD9);
        jPanel3.add(cbD10);
        jPanel3.add(cbD11);
        jPanel3.add(cbD12);
        jPanel3.add(cbD13);
        jPanel3.add(cbD14);
        jPanel3.add(cbD15);
        jPanel3.add(cbD16);
        jPanel3.add(cbD17);
        jPanel3.add(cbD18);
        jPanel3.add(cbD19);
        jPanel3.add(cbD20);
        jPanel3.add(cbD21);
        jPanel3.add(cbD22);
        jPanel3.add(cbD23);
        jPanel3.add(cbD24);
        jPanel3.add(cbD25);
        jPanel3.add(cbD26);
        jPanel3.add(cbD27);
        jPanel3.add(cbD28);
        jPanel3.add(cbD29);
        jPanel3.add(cbD30);
        jPanel3.add(cbD31);
        timePanel.add(timeSpinner);
        this.getContentPane().add(cmdPanel, java.awt.BorderLayout.SOUTH);
        cmdPanel.add(okButton);
        cmdPanel.add(cancelButton);
        this.getContentPane().add(mainTabbedPane, java.awt.BorderLayout.CENTER);
        mainTabbedPane.add(monthPanel, "月份");
        mainTabbedPane.add(dayPanel, "天");
        mainTabbedPane.add(timePanel, "时间");
        this.processDayEvent();
        this.processMonthEvent();
    }

    BorderLayout borderLayout1 = new BorderLayout();
    JPanel monthPanel = new JPanel();
    JPanel dayPanel = new JPanel();
    JPanel timePanel = new JPanel();
    FlowLayout flowLayout2 = new FlowLayout();
    JPanel cmdPanel = new JPanel();
    JButton cancelButton = new JButton();
    JButton okButton = new JButton();
    JTimeSpinner timeSpinner = new JTimeSpinner();
    VerticalFlowLayout verticalFlowLayout2 = new VerticalFlowLayout();
    VerticalFlowLayout verticalFlowLayout3 = new VerticalFlowLayout();
    JPanel jPanel1 = new JPanel();
    JPanel jPanel2 = new JPanel();
    JPanel jPanel3 = new JPanel();
    JPanel jPanel4 = new JPanel();
    FlowLayout flowLayout1 = new FlowLayout();
    GridLayout gridLayout1 = new GridLayout();
    JCheckBox cbM1 = new JCheckBox();
    JCheckBox cbM2 = new JCheckBox();
    JCheckBox cbM3 = new JCheckBox();
    JCheckBox cbM4 = new JCheckBox();
    JCheckBox cbM5 = new JCheckBox();
    JCheckBox cbM6 = new JCheckBox();
    JCheckBox cbM7 = new JCheckBox();
    JCheckBox cbM8 = new JCheckBox();
    JCheckBox cbM9 = new JCheckBox();
    JCheckBox cbM10 = new JCheckBox();
    JCheckBox cbM11 = new JCheckBox();
    JCheckBox cbM12 = new JCheckBox();
    Border border7 = BorderFactory.createEtchedBorder(Color.white, new Color(165, 163, 151));
    JRadioButton selectMRButton = new JRadioButton();
    JRadioButton everyMRButton = new JRadioButton();
    FlowLayout flowLayout3 = new FlowLayout();
    JRadioButton selectDRButton = new JRadioButton();
    JRadioButton everyDRButton = new JRadioButton();
    Border border8 = BorderFactory.createEtchedBorder(Color.white, new Color(165, 163, 151));
    GridLayout gridLayout2 = new GridLayout();
    JCheckBox cbD1 = new JCheckBox();
    JCheckBox cbD2 = new JCheckBox();
    JCheckBox cbD3 = new JCheckBox();
    JCheckBox cbD4 = new JCheckBox();
    JCheckBox cbD5 = new JCheckBox();
    JCheckBox cbD6 = new JCheckBox();
    JCheckBox cbD7 = new JCheckBox();
    JCheckBox cbD8 = new JCheckBox();
    JCheckBox cbD9 = new JCheckBox();
    JCheckBox cbD10 = new JCheckBox();
    JCheckBox cbD11 = new JCheckBox();
    JCheckBox cbD12 = new JCheckBox();
    JCheckBox cbD13 = new JCheckBox();
    JCheckBox cbD14 = new JCheckBox();
    JCheckBox cbD15 = new JCheckBox();
    JCheckBox cbD16 = new JCheckBox();
    JCheckBox cbD17 = new JCheckBox();
    JCheckBox cbD18 = new JCheckBox();
    JCheckBox cbD19 = new JCheckBox();
    JCheckBox cbD20 = new JCheckBox();
    JCheckBox cbD21 = new JCheckBox();
    JCheckBox cbD22 = new JCheckBox();
    JCheckBox cbD23 = new JCheckBox();
    JCheckBox cbD24 = new JCheckBox();
    JCheckBox cbD25 = new JCheckBox();
    JCheckBox cbD26 = new JCheckBox();
    JCheckBox cbD27 = new JCheckBox();
    JCheckBox cbD28 = new JCheckBox();
    JCheckBox cbD29 = new JCheckBox();
    JCheckBox cbD30 = new JCheckBox();
    JCheckBox cbD31 = new JCheckBox();
    JTabbedPane mainTabbedPane = new JTabbedPane();
    ButtonGroup group1 = new ButtonGroup();
    ButtonGroup group2 = new ButtonGroup();

    /**
     * Invoked when an action occurs.
     *
     * @param e ActionEvent
     * @todo Implement this java.awt.event.ActionListener method
     */
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == okButton) {
            processOk();
        } else if (e.getSource() == cancelButton) {
            processCancel();
        } else if (e.getSource() == everyMRButton
                   || e.getSource() == selectMRButton) {
            processMonthEvent();
        } else if (e.getSource() == everyDRButton
                   || e.getSource() == selectDRButton) {
            processDayEvent();
        }
    }

    /**
     * 月份选择事件
     */
    private void processMonthEvent() {
        boolean selectable = selectMRButton.isSelected();
        JCheckBox[] cbm = {
            cbM1, cbM2, cbM3, cbM4, cbM5, cbM6,
            cbM7, cbM8, cbM9, cbM10, cbM11, cbM12
        };
        for (int i = 0; i < cbm.length; i++) {
            cbm[i].setEnabled(selectable);
        }
    }

    /**
     * 天选择事件
     */
    private void processDayEvent() {
        boolean selectable = selectDRButton.isSelected();
        JCheckBox[] cbd = {
            cbD1, cbD2, cbD3, cbD4, cbD5, cbD6, cbD7, cbD8,
            cbD9, cbD10, cbD11, cbD12, cbD13, cbD14, cbD15, cbD16,
            cbD17, cbD18, cbD19, cbD20, cbD21, cbD22, cbD23, cbD24,
            cbD25, cbD26, cbD27, cbD28, cbD29, cbD30, cbD31};
        for (int i = 0; i < cbd.length; i++) {
            cbd[i].setEnabled(selectable);
        }
    }

    /**
     * 确定
     */
    private void processOk() {
        OPTION = OPTION_OK;
        this.dispose();
    }

    /**
     * 取消
     */
    private void processCancel() {
        this.OPTION = this.OPTION_CANCEL;
        this.dispose();
    }

    private String getMonth() {
        String month = "";
        if (everyMRButton.isSelected()) {
            month = everyMRButton.getText();
        } else {
            JCheckBox[] cbm = {
                cbM1, cbM2, cbM3, cbM4, cbM5, cbM6,
                cbM7, cbM8, cbM9, cbM10, cbM11, cbM12
            };
            for (int i = 0; i < cbm.length; i++) {
                if (cbm[i].isSelected()) {
                    month = month.concat( (i + 1) + ",");
                }
            }
        }
        if (month.indexOf(",") >= 0) {
            month = month.substring(0, month.lastIndexOf(","));
        }
        return month;
    }

    private void setMonth(String month) {
        if (month != null) {
            if (month.equals(everyMRButton.getText())) {
                everyMRButton.setSelected(true);
            } else {
                selectMRButton.setSelected(true);
                String[] sdays = month.split(",");
                String[] adays = {
                    "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"};
                JCheckBox[] cbd = {
                    cbM1, cbM2, cbM3, cbM4, cbM5, cbM6,
                    cbM7, cbM8, cbM9, cbM10, cbM11, cbM12};
                for (int i = 0, n = sdays.length; i < n; i++) {
                    for (int j = 0, m = adays.length; j < m; j++) {
                        if (sdays[i].equals(adays[j])) {
                            cbd[j].setSelected(true);
                        }
                    }
                }
            }
        }
    }

    private String getDay() {
        String day = "";
        if (everyDRButton.isSelected()) {
            day = everyDRButton.getText();
        } else {
            JCheckBox[] cbd = {
                cbD1, cbD2, cbD3, cbD4, cbD5, cbD6, cbD7, cbD8,
                cbD9, cbD10, cbD11, cbD12, cbD13, cbD14, cbD15, cbD16,
                cbD17, cbD18, cbD19, cbD20, cbD21, cbD22, cbD23, cbD24,
                cbD25, cbD26, cbD27, cbD28, cbD29, cbD30, cbD31};
            for (int i = 0; i < cbd.length; i++) {
                if (cbd[i].isSelected()) {
                    day = day.concat( (i + 1) + ",");
                }
            }
        }
        if (day.indexOf(",") >= 0) {
            day = day.substring(0, day.lastIndexOf(","));
        }
        return day;
    }

    private void setDay(String day) {
        if (day != null) {
            if (day.equals(everyDRButton.getText())) {
                everyDRButton.setSelected(true);
            } else {
                selectDRButton.setSelected(true);
                String[] sdays = day.split(",");
                String[] adays = {
                    "1", "2", "3", "4", "5", "6", "7", "8", "9", "10",
                    "11", "12", "13", "14", "15", "16", "17", "18", "19", "20",
                    "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"};
                JCheckBox[] cbd = {
                    cbD1, cbD2, cbD3, cbD4, cbD5, cbD6, cbD7, cbD8,
                    cbD9, cbD10, cbD11, cbD12, cbD13, cbD14, cbD15, cbD16,
                    cbD17, cbD18, cbD19, cbD20, cbD21, cbD22, cbD23, cbD24,
                    cbD25, cbD26, cbD27, cbD28, cbD29, cbD30, cbD31};
                for (int i = 0, n = sdays.length; i < n; i++) {
                    for (int j = 0, m = adays.length; j < m; j++) {
                        if (sdays[i].equals(adays[j])) {
                            cbd[j].setSelected(true);
                        }
                    }
                }
            }
        }
    }

    private String getTime() {
        return timeSpinner.getTimeValue();
    }

    private void setTime(String time) {
        if (time != null) {
            timeSpinner.setTimeValue(time);
        }
    }

    /**
     * 获取复杂时间对象。
     */
    public Object getCTimeObject() {
        JCTimeObject ctObj = new JCTimeObject();
        ctObj.year  = getYear();
        ctObj.month = getMonth();
        ctObj.day   = getDay();
        ctObj.time  = getTime();

        return ctObj;
    }

    /**
     * 返回当前年。
     */
    private String getYear() {
        Calendar calendar = Calendar.getInstance();

        return String.valueOf(calendar.get(calendar.YEAR));
    }

    public void setCTimeObject(Object obj) {
        if (obj != null) {
            JCTimeObject ctObj = (JCTimeObject) obj;
            timeSpinner.setTimeValue(ctObj.time);
            setMonth(ctObj.month);
            setDay(ctObj.day);
        }
        this.processMonthEvent();
        this.processDayEvent();
    }

    /**
     * 一个时间对象。
     */
    public static class JCTimeObject {

        public String year;
        public String month;
        public String day;
        public String time;

        public JCTimeObject() {
        }

        public String toString() {
            return "【年：" + year + "】【月：" + month + "】【日：" + day + "】【时间：" + time + "】";
        }
    }
}
