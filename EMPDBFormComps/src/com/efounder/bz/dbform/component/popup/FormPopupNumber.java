package com.efounder.bz.dbform.component.popup;

import java.text.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.plaf.basic.*;

import com.efounder.comp.*;

/**
 *
 * @version 1.0
 */
public class FormPopupNumber extends JPanel implements PopupComponent,
    ActionListener, KeyListener {

    /**
     *
     * @param comboBox JComboBox
     */
    public FormPopupNumber(JComboBox comboBox) {
        setJComboBox(comboBox);
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     *
     * @return JComponent
     */
    public JComponent getJComponent() {
        return this;
    }

    /**
     *
     * @return ListCellRenderer
     */
    public ListCellRenderer getListCellRenderer() {
        return null;
    }

    /**
     *
     */
    protected JComboBox comboBox = null;
    /**
     *
     * @param box JComboBox
     */
    public void setJComboBox(JComboBox box) {
        comboBox = box;
    }

    protected ComboPopup comboPopup = null;
    protected NumberFormat df = new DecimalFormat("###,###.##");
    String command, copy, arg, chg, txt;
    double result;
    String number = "123456789.0";
    String operator = "/*-+=";
    JTextField entrytext;
    JButton numbut[]; //Number buttons
    JButton combut[]; //Command buttons
    JPanel companel, numpanel;

    /**
     *
     * @param comboPopup ComboPopup
     */
    public void setComboPopup(ComboPopup comboPopup) {
        this.comboPopup = comboPopup;
    }

    /**
     *
     * @throws Exception
     */
    private void jbInit() throws Exception {
        GridBagLayout gridbag = new GridBagLayout();
        GridBagConstraints constraints = new GridBagConstraints();
        setLayout(gridbag);
        constraints.weighty = 1;
        constraints.weightx = 1;
        entrytext = new JTextField(18);
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        gridbag.setConstraints(entrytext, constraints);
        add(entrytext);
        entrytext.setForeground(Color.black);
        entrytext.setBackground(Color.white);
        entrytext.addKeyListener(this);
        entrytext.requestFocus();

        constraints.weighty = 1;
        constraints.weightx = 1;
        companel = new JPanel();

        constraints.gridwidth = GridBagConstraints.REMAINDER;
        gridbag.setConstraints(companel, constraints);
        /*
         Command GridLayout
         ---------------------
         | Back |  CE  |  C |
         ---------------------
         */
//        companel.setLayout(new GridLayout(1, 4, 3, 3));
        companel.setLayout(new FlowLayout(0, 3, 0));

        // Create the buttons
        String[] coms = {"确定", "退格", "清空"};
        combut = new JButton[coms.length];
        for (int i = 0; i < combut.length; i++) {
            combut[i] = new JButton(coms[i]);
            companel.add(combut[i]);
            combut[i].addActionListener(this);
            combut[i].addKeyListener(this);
        }
        add(companel);
        companel.addKeyListener(this);
        constraints.weighty = 4;
        constraints.weightx = 1;
        numpanel = new JPanel();
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        gridbag.setConstraints(numpanel, constraints);

        /*
         Number GridLayout
         ---------------------
         | 7 | 8 | 9 | / |sqr|
         ---------------------
         | 4 | 5 | 6 | * | % |
         ---------------------
         | 1 | 2 | 3 | - |1/x|
         ---------------------
         | 0 |+/-| . | + | = |
         ---------------------
         */
        numpanel.setLayout(new GridLayout(4, 5, 3, 3));
        // Create the buttons
        String[] nums = {"7", "8", "9", "/", "sqrt",
            "4", "5", "6", "*", "%",
            "1", "2", "3", "-", "1/x",
            "0", "+/-", ".", "+", "="
        };
        numbut = new JButton[20];
        for (int i = 0; i <= 19; i++) {
            numbut[i] = new JButton(nums[i]);
            numpanel.add(numbut[i]);
            numbut[i].addActionListener(this);
            numbut[i].addKeyListener(this);
            if (operator.indexOf(nums[i]) > -1) {
                numbut[i].setForeground(Color.red);
            } else {
                numbut[i].setForeground(Color.blue);
            }
        }
        add(numpanel);
        numpanel.addKeyListener(this);

        //initialize global variables.
        command = "+";
        copy = "";
        chg = "N";
        txt = "";
        arg = "";
        result = 0;
    }

    public void actionPerformed(ActionEvent e) {
      if (e.getActionCommand() == "退格")
           back_space();
       else if (e.getActionCommand() == "清空")
           entrytext.setText("");
       else if (e.getActionCommand() == "清空") {
           result = 0;
           command = "+";
           entrytext.setText("");
       } else if (e.getActionCommand() == "确定") {

            onok();
        } else {
            arg = e.getActionCommand();
            txt = entrytext.getText();
            if (number.indexOf(arg) > -1) {
                if (chg == "Y")
                    txt = "";
                txt = txt + arg;
                entrytext.setText(txt);
                chg = "N";
            } else
                check_entry();
        }
    }

    public void keyPressed(KeyEvent k) {}

    public void keyReleased(KeyEvent k) {
        int ikey = k.getKeyCode();
        if (ikey == 127)
            entrytext.setText("");
    }

    public void keyTyped(KeyEvent k) {
        int ikey = k.getKeyChar();
        if (ikey == 8)
            back_space();
        else if (ikey == 10) {
            arg = "=";
            txt = entrytext.getText();
            display_ans();
        } else {
            txt = entrytext.getText();
            char ckey = (char) ikey;
            arg = String.valueOf(ckey);
            if (number.indexOf(arg) > -1) {
                if (chg == "Y")
                    txt = "";
                txt = txt + arg;
                entrytext.setText(txt);
                chg = "N";
            } else
                check_entry();
        }
    }

    /**
     *
     */
    public void back_space() {
        txt = entrytext.getText();
        int l = txt.length();
        if (l > 0) {
            txt = txt.substring(0, l - 1);
            entrytext.setText(txt);
        }
    }

    /**
     *
     */
    public void display_ans() {
        entrytext.setText(compute_tot(txt, command));
        chg = "Y";
        command = "+";
        result = 0;
    }

    /**
     *
     */
    public void check_entry() {
        if (arg.equals("="))
            display_ans();
        else if (arg.equals("%")) {
            entrytext.setText(compute_tot(txt, arg));
            chg = "Y";
        } else if (operator.indexOf(arg) > -1) {
            entrytext.setText(compute_tot(txt, command));
            command = arg;
            chg = "Y";
        } else if (arg == "sqrt") {
            entrytext.setText(compute_tot(txt, arg));
            chg = "Y";
            command = "";
        } else if (arg == "+/-") {
            Double tnum = Double.valueOf(txt);
            double num = tnum.doubleValue();
            num = num * -1;
            entrytext.setText(String.valueOf(num));
            chg = "Y";
        } else if (arg == "1/x") {
            entrytext.setText(compute_tot(txt, arg));
            chg = "Y";
        }
    }

    /**
     *
     * @param t String
     * @param c String
     * @return String
     */
    String compute_tot(String t, String c) {
        Double tnum = Double.valueOf(t);
        double num = tnum.doubleValue();

        if (c.equals("+"))
            result = result + num;
        else if (c.equals("-"))
            result = result - num;
        else if (c.equals("*"))
            result = result * num;
        else if (c.equals("/"))
            result = result / num;
        else if (c.equals("%"))
            result = num / 100;
        else if (c.equals("+/-"))
            result = num * -1;
        else if (c.equals("1/x"))
            result = 1.000 / num;
        else if (c.equals("sqrt"))
            result = Math.sqrt(num);
        return String.valueOf(result);
    }

    /**
     *
     * @return String
     */
    private String getValue() {
        return entrytext.getText();
    }

    /**
     *
     * @param value String
     */
    private void appendValue(String value) {
        String text = getValue();
        if (text.indexOf(".") >= 0 && value.equals(".")) {
            return;
        }
        text += value;
        setSelectedItem(text);
    }

    /**
     *
     */
    private void onok() {
        if (comboBox != null) {
            comboBox.setSelectedItem(getValue());
        }
        if (comboPopup != null) {
            comboPopup.hide();
        }
        System.out.print(comboBox.getSelectedItem());
    }

    /**
     *
     * @param item Object
     */
    public void setSelectedItem(Object item) {
        if (item == null) return;
        entrytext.setText(item.toString());
    }

    /**
     *
     * @return Object
     */
    public Object getSelectedItem() {
        return this.getValue();
    }

    /**
     *
     * @return boolean
     */
    public boolean isNotUseScrollPane() {
        return false;
    }

}
