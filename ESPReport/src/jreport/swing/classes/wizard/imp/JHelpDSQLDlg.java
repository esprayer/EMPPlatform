package jreport.swing.classes.wizard.imp;

import jfoundation.gui.window.classes.JFrameDialog;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.event.*;
import java.util.ResourceBundle;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class JHelpDSQLDlg
    extends JFrameDialog {
static ResourceBundle res = ResourceBundle.getBundle("jreport.swing.classes.wizard.imp.Language");
    public static final int OK_OPTION = 0;
    public static final int CANCEL_OPTION = 1;
    public int mOption = 1;
    private String mSqlText = "";

    BorderLayout borderLayout1 = new BorderLayout();
    JPanel jPanel1 = new JPanel();
    JPanel jPanel2 = new JPanel();
    GridLayout gridLayout1 = new GridLayout();
    JPanel jPanel3 = new JPanel();
    JPanel jPanel4 = new JPanel();
    JLabel mStatusLabel = new JLabel();
    JButton cancelBtn = new JButton();
    JButton okBtn = new JButton();
    FlowLayout flowLayout1 = new FlowLayout();
    FlowLayout flowLayout2 = new FlowLayout();
    JLabel jLabel1 = new JLabel();
    Border border1;
    Border border2;
    BorderLayout borderLayout2 = new BorderLayout();
    Border border3;
    JScrollPane jScrollPane1 = new JScrollPane();
    JTextArea mInputTA = new JTextArea();
    JLabel jLabel2 = new JLabel();
    public JHelpDSQLDlg(Frame frame, String title, boolean isModel) {
        super(frame, title, isModel);
        try {
            jbInit();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void jbInit() throws Exception {
        border2 = BorderFactory.createEmptyBorder(10, 10, 10, 10);
        border3 = BorderFactory.createCompoundBorder(BorderFactory.createEtchedBorder(Color.white, new Color(148, 145, 140)), BorderFactory.createEmptyBorder(10, 10, 10, 10));
        this.getContentPane().setLayout(borderLayout1);
        jPanel2.setLayout(gridLayout1);
        cancelBtn.setText(res.getString("String_12"));
        cancelBtn.addActionListener(new JHelpDSQLDlg_cancelBtn_actionAdapter(this));
        okBtn.setText(res.getString("String_13"));
        okBtn.addActionListener(new JHelpDSQLDlg_okBtn_actionAdapter(this));
        jPanel3.setLayout(flowLayout1);
        flowLayout1.setAlignment(FlowLayout.LEFT);
        flowLayout1.setHgap(5);
        jPanel4.setLayout(flowLayout2);
        flowLayout2.setAlignment(FlowLayout.RIGHT);
        jLabel1.setText(res.getString("String_14"));
        mStatusLabel.setText("");
        jPanel1.setBorder(border3);
        jPanel1.setLayout(borderLayout2);
        jPanel4.setBorder(null);
        jPanel2.setBorder(BorderFactory.createEtchedBorder());
        jLabel2.setText(res.getString("String_16"));
        mInputTA.addKeyListener(new JHelpDSQLDlg_mInputTA_keyAdapter(this));
        jPanel3.add(jLabel1, null);
        this.getContentPane().add(jPanel1, BorderLayout.CENTER);
        this.getContentPane().add(jPanel2, BorderLayout.SOUTH);
        jPanel2.add(jPanel3, null);
        jPanel3.add(mStatusLabel, null);
        jPanel2.add(jPanel4, null);
        jPanel4.add(okBtn, null);
        jPanel4.add(cancelBtn, null);
        jPanel1.add(jScrollPane1, BorderLayout.CENTER);
        jScrollPane1.getViewport().add(mInputTA, null);
        jPanel1.add(jLabel2, BorderLayout.NORTH);
        this.pack();
        this.setSize(new Dimension(500, 300));
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    }

    private void onOk() {
        String sql = this.mInputTA.getText().trim();
//        if (sql.length() > 500) {
//            JOptionPane.showMessageDialog(this, res.getString("String_17"), res.getString("String_18"), JOptionPane.ERROR_MESSAGE);
//        }
//        else {
            mSqlText = sql;
            this.setOption(JHelpDSQLDlg.OK_OPTION);
            dispose();
//        }
    }

    private void onCancel() {
        this.setOption(JHelpDSQLDlg.CANCEL_OPTION);
        dispose();
    }

    private void setOption(int option) {
        this.mOption = option;
    }

    public int getOption() {
        return mOption;
    }

    public String getSqlText() {
        return mSqlText;
    }

    private void updateStatus() {
        this.mStatusLabel.setText(Integer.toString(this.mInputTA.getText().length()));
    }

    void okBtn_actionPerformed(ActionEvent e) {
        onOk();
    }

    void cancelBtn_actionPerformed(ActionEvent e) {
        onCancel();
    }

    void mInputTA_keyReleased(KeyEvent e) {
        updateStatus();
    }

}

class JHelpDSQLDlg_okBtn_actionAdapter
    implements java.awt.event.ActionListener {
    JHelpDSQLDlg adaptee;

    JHelpDSQLDlg_okBtn_actionAdapter(JHelpDSQLDlg adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.okBtn_actionPerformed(e);
    }
}

class JHelpDSQLDlg_cancelBtn_actionAdapter
    implements java.awt.event.ActionListener {
    JHelpDSQLDlg adaptee;

    JHelpDSQLDlg_cancelBtn_actionAdapter(JHelpDSQLDlg adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cancelBtn_actionPerformed(e);
    }
}

class JHelpDSQLDlg_mInputTA_keyAdapter
    extends java.awt.event.KeyAdapter {
    JHelpDSQLDlg adaptee;

    JHelpDSQLDlg_mInputTA_keyAdapter(JHelpDSQLDlg adaptee) {
        this.adaptee = adaptee;
    }

    public void keyReleased(KeyEvent e) {
        adaptee.mInputTA_keyReleased(e);
    }
}
