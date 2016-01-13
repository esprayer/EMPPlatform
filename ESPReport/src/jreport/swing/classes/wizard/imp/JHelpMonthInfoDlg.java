package jreport.swing.classes.wizard.imp;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

import com.borland.jbcl.layout.*;
import jfoundation.gui.window.classes.*;
import jreport.value.*;
import java.util.ResourceBundle;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
public class JHelpMonthInfoDlg
        extends JFrameDialog implements ActionListener {
static ResourceBundle res = ResourceBundle.getBundle("jreport.swing.classes.wizard.imp.Language");

    private int mMonth = 0;
    private int mCurrentReportMonth = 0;
    public static final int OK_OPTION = 0;
    public static final int CANCEL_OPTION = 1;
    public int mOption = 1;

    public JHelpMonthInfoDlg(Frame frame, String title, boolean isModel, int currentMonth) {
        super(frame, title, isModel);
        mCurrentReportMonth = currentMonth;
        init();
        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void init() {

    }

    private void jbInit() throws Exception {
        border1 = BorderFactory.createBevelBorder(BevelBorder.RAISED, Color.white, Color.white, new Color(103, 101, 98),
                                                  new Color(148, 145, 140));
        titledBorder1 = new TitledBorder(border1, res.getString("String_1"));
        titledBorder2 = new TitledBorder(BorderFactory.createEtchedBorder(Color.white, new Color(148, 145, 140)), res.getString("String_2"));
        border2 = BorderFactory.createBevelBorder(BevelBorder.RAISED, Color.white, Color.white, new Color(103, 101, 98),
                                                  new Color(148, 145, 140));
        this.getContentPane().setLayout(borderLayout1);
        mOkBtn.addActionListener(this);
        mCancelBtn.addActionListener(this);
        mCancelBtn.setText(res.getString("String_3"));
        mOkBtn.setText(res.getString("String_4"));
        mOkBtn.setMnemonic('O');
        mCancelBtn.setMnemonic('C');
        mCancelBtn.setPreferredSize(new Dimension(80, 25));
        mOkBtn.setPreferredSize(new Dimension(80, 25));
        jPanel2.setLayout(flowLayout1);
        flowLayout1.setAlignment(FlowLayout.RIGHT);
        jPanel1.setBorder(titledBorder2);
        jPanel1.setLayout(verticalFlowLayout1);
        jPanel3.setLayout(borderLayout2);
        jPanel3.setBorder(border2);
        jPanel5.setLayout(flowLayout2);
        flowLayout2.setAlignment(FlowLayout.LEFT);
        jLabel1.setText(res.getString("String_7"));
        spinner.setPreferredSize(new Dimension(60, 25));
        jPanel4.setLayout(flowLayout3);
        jLabel2.setText(res.getString("String_8") + JREPORT.getMaxKjqj());
        flowLayout3.setAlignment(FlowLayout.LEFT);
        jPanel2.add(mOkBtn, null);
        jPanel2.add(mCancelBtn, null);
        this.getContentPane().add(jPanel3, BorderLayout.CENTER);
        jPanel3.add(jPanel1, BorderLayout.CENTER);
        jPanel1.add(jPanel5, null);
        jPanel5.add(jLabel1, null);
        Integer value = new Integer(mMonth);
        Integer min = new Integer(-mCurrentReportMonth);
        Integer max = new Integer(JREPORT.getMaxKjqj());
        Integer step = new Integer(1);
        SpinnerNumberModel model = new SpinnerNumberModel(value, min, max, step);
        spinner.setModel(model);

        jPanel5.add(spinner, null);
        jPanel1.add(jPanel4, null);
        jPanel4.add(jLabel2, null);
        this.getContentPane().add(jPanel2, BorderLayout.SOUTH);
        this.setSize(new Dimension(250, 180));
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == mOkBtn) {
            onOk();
        } else {
            onCancel();
        }
    }

    private void onOk() {
        int month = ( (SpinnerNumberModel)this.spinner.getModel()).getNumber().intValue();
        if (validateValue(month)) {
            setMonth(month);
            setOption(JHelpMonthInfoDlg.OK_OPTION);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, res.getString("String_9"), res.getString("String_10"), JOptionPane.ERROR_MESSAGE);
        }
    }

    private void onCancel() {
        setOption(JHelpMonthInfoDlg.CANCEL_OPTION);
        dispose();
    }

    private void setMonth(int month) {
        mMonth = month;
    }

    public int getMonth() {
        return mMonth;
    }

    public void setOption(int option) {
        mOption = option;
    }

    public int getOption() {
        return mOption;
    }

    private boolean validateValue(int value) {
        if (value >= -12 && value <= 12) {
            return true;
        }
        return false;
    }

    BorderLayout borderLayout1 = new BorderLayout();
    JPanel jPanel2 = new JPanel();
    JButton mCancelBtn = new JButton();
    JButton mOkBtn = new JButton();
    FlowLayout flowLayout1 = new FlowLayout();
    Border border1;
    TitledBorder titledBorder1;
    TitledBorder titledBorder2;
    JPanel jPanel3 = new JPanel();
    JPanel jPanel1 = new JPanel();
    BorderLayout borderLayout2 = new BorderLayout();
    Border border2;
    JPanel jPanel4 = new JPanel();
    VerticalFlowLayout verticalFlowLayout1 = new VerticalFlowLayout();
    JPanel jPanel5 = new JPanel();
    FlowLayout flowLayout2 = new FlowLayout();
    JLabel jLabel1 = new JLabel();
    JSpinner spinner = new JSpinner();
    JLabel jLabel2 = new JLabel();
    FlowLayout flowLayout3 = new FlowLayout();

}
