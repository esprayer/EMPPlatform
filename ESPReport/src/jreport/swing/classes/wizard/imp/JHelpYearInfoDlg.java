package jreport.swing.classes.wizard.imp;

import jfoundation.gui.window.classes.JFrameDialog;
import java.awt.event.*;
import jreport.swing.classes.wizard.*;
import javax.swing.*;
import java.awt.*;
import com.borland.jbcl.layout.*;
import javax.swing.border.*;
import java.util.ResourceBundle;

/**
 * <p>Title: JHelpYearInfoDlg</p>
 * <p>Description: 会计年度</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class JHelpYearInfoDlg
    extends JFrameDialog
    implements ActionListener {
static ResourceBundle res = ResourceBundle.getBundle("jreport.swing.classes.wizard.imp.Language");
    public JFunctionWizardObject mWizardObject = null;
    private JButton mOkBtn = new JButton(res.getString("String_60")),
        mCancelBtn = new JButton(res.getString("String_61"));
    private int mCurrentYear = 0;
    private int mMinYear = 0;
    private int mYear;
    public static final int OK_OPTION = 0;
    public static final int CANCEL_OPTION = 1;
    public static final int RELATIVE_STYLE = 3;
    public static final int ABSOLUTE_STYLE = 4;
    public int mStyle = 3;
    public int mOption = 1;
    public JHelpYearInfoDlg(Frame frame, String title, boolean isModel, int currentYear, int minYear) {
        super(frame, title, isModel);
        mCurrentYear = currentYear;
        mMinYear = minYear;
        init();
        mOkBtn.addActionListener(this);
        mCancelBtn.addActionListener(this);
        try {
            jbInit();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void init() {
    //initialization of member variables
    }

    private void jbInit() throws Exception {
        titledBorder1 = new TitledBorder(BorderFactory.createEtchedBorder(Color.white, new Color(148, 145, 140)), res.getString("String_62"));
        border1 = BorderFactory.createEmptyBorder();
        titledBorder2 = new TitledBorder(BorderFactory.createEtchedBorder(Color.white, new Color(148, 145, 140)), res.getString("String_63"));
        border2 = BorderFactory.createCompoundBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED, Color.white, Color.white, new Color(103, 101, 98), new Color(148, 145, 140)),
            BorderFactory.createEmptyBorder(5, 5, 5, 5));
        this.getContentPane().setLayout(borderLayout1);
        centerPanel.setBorder(border2);
        centerPanel.setLayout(verticalFlowLayout1);
        jPanel1.setLayout(flowLayout1);
        flowLayout1.setAlignment(FlowLayout.RIGHT);
        presentPanel.setLayout(verticalFlowLayout2);
        relativeRb.setText(res.getString("String_64"));
        relativeRb.addItemListener(new JHelpYearInfoDlg_relativeRb_itemAdapter(this));
        absoluteRb.setText(res.getString("String_65"));
        absoluteRb.addItemListener(new JHelpYearInfoDlg_absoluteRb_itemAdapter(this));
        presentPanel.setBorder(titledBorder1);
        definePanel.setBorder(titledBorder2);
        definePanel.setLayout(flowLayout2);
        jLabel1.setText(res.getString("String_66"));
        flowLayout2.setAlignment(FlowLayout.LEFT);
        spinner.setPreferredSize(new Dimension(60, 26));
        this.getContentPane().add(centerPanel, BorderLayout.CENTER);
        this.getContentPane().add(jPanel1, BorderLayout.SOUTH);
        mOkBtn.setMnemonic('O');
        mOkBtn.setPreferredSize(new Dimension(80,25));
        mCancelBtn.setMnemonic('C');
        mCancelBtn.setPreferredSize(new Dimension(80,25));
        jPanel1.add(mOkBtn, null);
        jPanel1.add(mCancelBtn, null);
        centerPanel.add(presentPanel, null);
        btnGroup.add(relativeRb);
        btnGroup.add(absoluteRb);
        relativeRb.setSelected(true);
        presentPanel.add(relativeRb, null);
        presentPanel.add(absoluteRb, null);
        centerPanel.add(definePanel, null);
        definePanel.add(jLabel1, null);
        definePanel.add(spinner, null);
        this.setSize(new Dimension(300, 265));
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == mOkBtn) {
            onOk();
        }
        else {
            onCancel();
        }
    }

    private void onOk() {
        int value = ( (SpinnerNumberModel)this.spinner.getModel()).getNumber().intValue();
        if (new Integer(value) == null) {
            JOptionPane.showMessageDialog(this, res.getString("String_69"), res.getString("String_70"), JOptionPane.ERROR_MESSAGE);
        }
        else if (!validateValue(value)) {
            JOptionPane.showMessageDialog(this, res.getString("String_71"), res.getString("String_72"), JOptionPane.ERROR_MESSAGE);
        }
        else {
            setYear(value);
            this.setOption(JHelpYearInfoDlg.OK_OPTION);
            dispose();
        }
    }

    private void onCancel() {
        this.setOption(JHelpYearInfoDlg.CANCEL_OPTION);
        dispose();
    }

    private void setOption(int option) {
        this.mOption = option;
    }

    public int getOption() {
        return mOption;
    }

    private void setYear(int year) {
        this.mYear = year;
    }

    public int getYear() {
        return mYear;
    }

    public int getStyle() {
        return mStyle;
    }

    public void setStyle(int style) {
        mStyle = style;
    }

    void relativeRb_itemStateChanged(ItemEvent e) {
        Integer value = new Integer(0);
        Integer min = new Integer(mMinYear - mCurrentYear);
        Integer max = new Integer(0);
        Integer step = new Integer(1);
        SpinnerNumberModel model = new SpinnerNumberModel(value, min, max, step);
        spinner.setModel(model);
        setStyle(JHelpYearInfoDlg.RELATIVE_STYLE);
    }

    void absoluteRb_itemStateChanged(ItemEvent e) {
        Integer value = new Integer(mCurrentYear);
        Integer min = new Integer(mMinYear);
        Integer max = new Integer(mCurrentYear);
        Integer step = new Integer(1);
        SpinnerNumberModel model = new SpinnerNumberModel(value, min, max, step);
        spinner.setModel(model);
        setStyle(JHelpYearInfoDlg.ABSOLUTE_STYLE);
    }

    private boolean validateValue(int value) {
        if (this.relativeRb.isSelected()) { //相对
            if ( -value < this.mCurrentYear - this.mMinYear) {
                return true;
            }
        }
        else if (this.absoluteRb.isSelected()) { //绝对
            if (value >= this.mMinYear && value <= this.mCurrentYear) {
                return true;
            }
        }

        return false;
    }

    JPanel centerPanel = new JPanel();
    BorderLayout borderLayout1 = new BorderLayout();
    JPanel jPanel1 = new JPanel();
    FlowLayout flowLayout1 = new FlowLayout();
    JButton jButton1 = new JButton();
    JButton jButton2 = new JButton();
    VerticalFlowLayout verticalFlowLayout1 = new VerticalFlowLayout();
    JPanel definePanel = new JPanel();
    JPanel presentPanel = new JPanel();
    VerticalFlowLayout verticalFlowLayout2 = new VerticalFlowLayout();
    ButtonGroup btnGroup = new ButtonGroup();
    JRadioButton relativeRb = new JRadioButton();
    JRadioButton absoluteRb = new JRadioButton();
    TitledBorder titledBorder1;
    Border border1;
    TitledBorder titledBorder2;
    JLabel jLabel1 = new JLabel();
    JSpinner spinner = new JSpinner();
    FlowLayout flowLayout2 = new FlowLayout();
    Border border2;

}

class JHelpYearInfoDlg_relativeRb_itemAdapter
    implements java.awt.event.ItemListener {
    JHelpYearInfoDlg adaptee;

    JHelpYearInfoDlg_relativeRb_itemAdapter(JHelpYearInfoDlg adaptee) {
        this.adaptee = adaptee;
    }

    public void itemStateChanged(ItemEvent e) {
        adaptee.relativeRb_itemStateChanged(e);
    }
}

class JHelpYearInfoDlg_absoluteRb_itemAdapter
    implements java.awt.event.ItemListener {
    JHelpYearInfoDlg adaptee;

    JHelpYearInfoDlg_absoluteRb_itemAdapter(JHelpYearInfoDlg adaptee) {
        this.adaptee = adaptee;
    }

    public void itemStateChanged(ItemEvent e) {
        adaptee.absoluteRb_itemStateChanged(e);
    }
}
