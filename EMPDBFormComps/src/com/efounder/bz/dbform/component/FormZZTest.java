package com.efounder.bz.dbform.component;

import java.awt.*;
import javax.swing.*;

import com.borland.jbcl.layout.*;
import com.swing.history.HistoryTextField;
import com.sunking.swing.*;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.MaskFormatter;

/**
 * Form�������
 */
public class FormZZTest extends JFrame {

    /**
     * FormTextField
     */
    public void testFormTextField() {
      String[] show = {"固定长度", "IP地址"};
              String[] mask = {"AAAAAA","###.###.###.###"};
        String[] patn = {"AAAAAA","([1-9]|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])(\\.(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])){3}"};
        for (int i = 0, n = show.length; i < n; i++) {
            JPanel panel = new JPanel(new BorderLayout());
            JLabel label = new JLabel(show[i]);
            JFormattedTextField ftf = null;
            try {
                ftf = new JFormattedTextField();
                MaskFormatter m = new MaskFormatter(mask[i]);
                ftf.setFormatterFactory(new DefaultFormatterFactory(m));
            } catch (Exception ex) {
            }
            panel.add(label, java.awt.BorderLayout.WEST);
            panel.add(ftf, java.awt.BorderLayout.CENTER);
            jPanel7.add(panel);
        }
    }

    public void testFormNumberField(){
        FormNumberField combo = new FormNumberField();
        jPanel2.add(combo, java.awt.BorderLayout.NORTH);
    }

    public void testJDatePickerEx(){
        FormDatePicker date = new FormDatePicker(JDatePicker.STYLE_CN_DATETIME);
        jPanel3.add(date, java.awt.BorderLayout.NORTH);
    }

    public void testHistoryTextField(){
        HistoryTextField history = new HistoryTextField();
        jPanel5.add(history, java.awt.BorderLayout.NORTH);
    }

    public void testFormComboBox() {
        FormFKeyField combo = new FormFKeyField();
        jPanel4.add(combo, java.awt.BorderLayout.NORTH);
    }

    public FormZZTest() {
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        FormZZTest frame = new FormZZTest();

        frame.testFormTextField();
        frame.testFormNumberField();
        frame.testJDatePickerEx();
        frame.testFormComboBox();
        frame.testHistoryTextField();

        frame.setSize(600, 400);
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation( (d.width  - frame.getSize().width)  / 2,
                           (d.height - frame.getSize().height) / 2);
        frame.setVisible(true);
    }

    private void jbInit() throws Exception {
        this.getContentPane().setLayout(borderLayout1);
        jPanel7.setLayout(verticalFlowLayout1);
        jLabel1.setText("jLabel1");
        jLabel2.setText("jLabel2");
        jTextField1.setText("jTextField1");
        this.getContentPane().add(jTabbedPane1, java.awt.BorderLayout.CENTER);
        jPanel1.setLayout(borderLayout2);
        jPanel2.setLayout(borderLayout3);
        jPanel3.setLayout(borderLayout4);
        jPanel4.setLayout(borderLayout5);
        jPanel5.setLayout(borderLayout6);
        jPanel6.setLayout(borderLayout7);
        jTabbedPane1.add(jPanel1, "FormTextField");
        jPanel1.add(jPanel7, java.awt.BorderLayout.CENTER);
        jTabbedPane1.add(jPanel2, "FormComboBox");
        jTabbedPane1.add(jPanel3, "JDatePicker");
        jTabbedPane1.add(jPanel4, "Form");
        jTabbedPane1.add(jPanel5, "HistoryTextField");
        jTabbedPane1.add(jPanel6, "jPanel6");
    }

    BorderLayout borderLayout1 = new BorderLayout();
    JTabbedPane jTabbedPane1 = new JTabbedPane();
    JPanel jPanel1 = new JPanel();
    JPanel jPanel2 = new JPanel();
    JPanel jPanel3 = new JPanel();
    JPanel jPanel4 = new JPanel();
    JPanel jPanel5 = new JPanel();
    JPanel jPanel6 = new JPanel();
    BorderLayout borderLayout2 = new BorderLayout();
    BorderLayout borderLayout3 = new BorderLayout();
    BorderLayout borderLayout4 = new BorderLayout();
    BorderLayout borderLayout5 = new BorderLayout();
    BorderLayout borderLayout6 = new BorderLayout();
    BorderLayout borderLayout7 = new BorderLayout();
    JPanel jPanel7 = new JPanel();
    VerticalFlowLayout verticalFlowLayout1 = new VerticalFlowLayout();
    BorderLayout borderLayout8 = new BorderLayout();
    JLabel jLabel1 = new JLabel();
    JLabel jLabel2 = new JLabel();
    JTextField jTextField1 = new JTextField();

}
