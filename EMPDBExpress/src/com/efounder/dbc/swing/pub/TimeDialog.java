package com.efounder.dbc.swing.pub;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;
import javax.swing.table.*;

import com.borland.dbswing.*;
import com.efounder.dataobject.view.*;
import com.efounder.dbc.swing.editor.*;
import com.efounder.eai.*;
import com.efounder.pfc.dialog.*;
import com.sunking.swing.JDatePicker;
import com.efounder.dctbuilder.util.DictUtil;
import java.text.SimpleDateFormat;
import java.text.ParseException;

/**
 * <p>Title: KEY-VALUE DIALOG </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2006</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class TimeDialog extends JPDialog implements IWindowCellEditor,
        ActionListener {
    protected JdbTable    cpTable = new JdbTable();
    protected TableScrollPane scrollPane = new DCTTableScrollPane();
    protected DefaultTableModel m = new DefaultTableModel() {};
    protected String separator = ";";
    BorderLayout borderLayout1 = new BorderLayout();
    GridLayout gridLayout1 = new GridLayout();
    JPanel jPanel2 = new JPanel();
    JPanel jPanel3 = new JPanel();
    JPanel jPanel4 = new JPanel();
    JPanel jPanel5 = new JPanel();
    BorderLayout borderLayout2 = new BorderLayout();
    JPanel jPanel7 = new JPanel();
    BorderLayout borderLayout3 = new BorderLayout();
    FlowLayout flowLayout1 = new FlowLayout();
    JButton btncancel = new JButton();
    JButton btnok = new JButton();
    JDatePicker datePicker;
    JLabel hourLabel = new JLabel();
    JSpinner hourSpinner = new JSpinner();
    JLabel minuLabel = new JLabel();
    JSpinner minuteSpinner = new JSpinner();
    JLabel secondLabel = new JLabel();
    JSpinner secondSpinner = new JSpinner();
    String format = null;

    public TimeDialog() {
        this(EAI.EA.getMainWindow(), "日期选择", true,"yyyyMMddhhmmss");
    }

    /**
     *
     * @param frame Frame
     * @param title String
     * @param modal boolean
     */
    public TimeDialog(Frame frame, String title, boolean modal,String format) {
        super(frame, title, modal);
        this.format = format;
        if(format == null || format.length() == 0){
            this.format = "yyyy-MM-dd hh:mm:ss";
        }
        try {
            jbInit();
            pack();
            CenterWindow();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void jbInit() throws Exception {
        datePicker = new JDatePicker(JDatePicker.STYLE_CN_DATE1);
        //
        hourSpinner.setPreferredSize(new Dimension(55, 22));
        SpinnerNumberModel hourmode = (SpinnerNumberModel) hourSpinner.
                                        getModel();
        hourmode.setMinimum(new Integer(0));
        hourmode.setMaximum(new Integer(23));
        hourmode.setValue(new Integer(0));
        hourLabel.setIconTextGap(2);
        hourLabel.setText("时");
        minuteSpinner.setPreferredSize(new Dimension(55, 22));
        SpinnerNumberModel minumode = (SpinnerNumberModel) minuteSpinner.
                                      getModel();
        minumode.setMinimum(new Integer(0));
        minumode.setMaximum(new Integer(59));
        minumode.setValue(new Integer(0));
        minuLabel.setIconTextGap(2);
        minuLabel.setText("分");
        secondSpinner.setPreferredSize(new Dimension(55, 22));
        SpinnerNumberModel secondmode = (SpinnerNumberModel) secondSpinner.
                                      getModel();
        secondmode.setMinimum(new Integer(0));
        secondmode.setMaximum(new Integer(59));
        secondmode.setValue(new Integer(0));
        secondLabel.setIconTextGap(2);
        secondLabel.setText("秒");
        jPanel3.setLayout(new FlowLayout(FlowLayout.LEFT));
        jPanel3.add(hourSpinner);
        jPanel3.add(hourLabel);
        jPanel3.add(minuteSpinner);
        jPanel3.add(minuLabel);
        jPanel3.add(secondSpinner);
        jPanel3.add(secondLabel);

        //
        btncancel.setPreferredSize(new Dimension(75, 23));
        btncancel.setMnemonic('C');
        btncancel.setText("取消(C)");
        btncancel.addActionListener(this);
        btnok.setPreferredSize(new Dimension(75, 23));
        btnok.setMnemonic('O');
        btnok.setText("确定(O)");
        btnok.addActionListener(this);
        jPanel7.setLayout(new FlowLayout(FlowLayout.RIGHT));
        jPanel7.add(btnok);
        jPanel7.add(btncancel);
        //
        this.getContentPane().setLayout(borderLayout1);
        this.getContentPane().add(datePicker, java.awt.BorderLayout.NORTH);
        this.getContentPane().add(jPanel3, java.awt.BorderLayout.CENTER);
        this.getContentPane().add(jPanel7, java.awt.BorderLayout.SOUTH);
        this.getContentPane().add(jPanel4, java.awt.BorderLayout.EAST);
        this.getContentPane().add(jPanel5, java.awt.BorderLayout.EAST);
    }

    /**
     *
     *
     * @param value Object
     * @todo Implement this com.efounder.dbc.swing.editor.IWindowCellEditor method
     */
    public void setValue(Object value) {
        java.util.Date date = null;
        if (value == null || (value instanceof String && ((String)value).length() < 4)) {
            date = new java.util.Date();
        }
        else {
            if (value instanceof String) {
                date = DictUtil.getUtilDate(value.toString());
            }
            else if (value instanceof java.util.Date) {
                date = (java.util.Date)value;
            }
        }
        try {
            datePicker.setSelectedDate(date);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        //
        SimpleDateFormat f = new SimpleDateFormat(format);
        String str = f.format(date);
        str = str.replace("/","");
        str = str.replace("-","");
        str = str.replace(" ","");
        str = str.replace(":","");
        if (str.length() <= 8){
            str = "000000";
        }else{
            str = str.substring(8);
        }
        int length = str.length();
        for (int i = 0; i < (6 - length); i++) {
            str += "0";
        }
        int hour = Integer.valueOf(str.substring(0,2));
        hourSpinner.setValue(hour);
        int minute = Integer.valueOf(str.substring(2,4));
        minuteSpinner.setValue(minute);
        int second = Integer.valueOf(str.substring(4,6));
        secondSpinner.setValue(second);
    }

    /**
     * getValue
     *
     * @return Object
     * @todo Implement this com.efounder.dbc.swing.editor.IWindowCellEditor method
     */
    public Object getValue() {
        try {
            java.util.Date date = datePicker.getSelectedDate();
            SimpleDateFormat formater = new SimpleDateFormat(format);
            String value = formater.format(date);
            value = value.replace("/", "");
            value = value.replace("-", "");
            value = value.replace(" ", "");
            value = value.replace(":", "");
            value = value.substring(0, 4) + "-" + value.substring(4, 6)
                + "-" +  value.substring(6, 8);
            return value + " " + getTime();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return "";
    }

    public String getTime() throws ParseException {
        String time = "";
        Integer tempInt = (Integer) hourSpinner.getValue();
        String tempStr = String.valueOf(tempInt);
        if(tempStr.length() < 2){
            time += "0" + tempStr;
        }else{
            time += tempStr;
        }
        tempInt = (Integer) minuteSpinner.getValue();
        tempStr = String.valueOf(tempInt);
        if(tempStr.length() < 2){
            time += ":0" + tempStr;
        }else{
            time += ":" + tempStr;
        }
        tempInt = (Integer) secondSpinner.getValue();
        tempStr = String.valueOf(tempInt);
        if(tempStr.length() < 2){
            time += ":0" + tempStr;
        }else{
            time += ":" + tempStr;
        }
        return time;
    }


    /**
     *
     * @param valueList List
     */
    public void setValueList(java.util.List valueList) {
        m.getDataVector().removeAllElements();
        if (valueList != null) m.getDataVector().addAll(valueList);
    }

    /**
     *
     * @return List
     */
    public java.util.List getValueList() {
        return m.getDataVector();
    }

    /**
     * Invoked when an action occurs.
     *
     * @param e ActionEvent
     * @todo Implement this java.awt.event.ActionListener method
     */
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnok) {
            super.OnOk();
        } else if (e.getSource() == this.btncancel) {
            super.OnCancel();
        }
    }

    /**
     *
     * @return boolean
     */
    public boolean isResultOK() {
        return Result == RESULT_OK;
    }

    public void setVisible(boolean visible) {
        super.setVisible(visible);
    }
}
