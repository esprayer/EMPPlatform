package jdatareport.dof.classes.report.ext.condition.ui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

import com.borland.jbcl.layout.*;
import jfoundation.gui.window.classes.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: Pansoft</p>
 * @author Stephen Zhao
 * @version 1.0
 */

public class JCSubstringDialog
    extends JFrameDialog
    implements ActionListener {
    /**
     *
     */
    private JPanel jPanel1 = new JPanel();
    private JPanel jPanel2 = new JPanel();
    private BorderLayout borderLayout1 = new BorderLayout();
    private JPanel jPanel3 = new JPanel();
    private Border border1;
    private JButton mOkBtn = new JButton();
    private JButton mCancelBtn = new JButton();
    private FlowLayout flowLayout1 = new FlowLayout();
    private JPanel jPanel4 = new JPanel();
    private BorderLayout borderLayout2 = new BorderLayout();
    private Border border2;
    private TitledBorder titledBorder1;
    private VerticalFlowLayout verticalFlowLayout1 = new VerticalFlowLayout();
    private JPanel jPanel5 = new JPanel();
    private JPanel jPanel6 = new JPanel();
    private FlowLayout flowLayout2 = new FlowLayout();
    private FlowLayout flowLayout3 = new FlowLayout();
    private JSpinner mStartSp = new JSpinner();
    private JSpinner mLengthSp = new JSpinner();
    private JLabel jLabel1 = new JLabel();
    private JLabel jLabel2 = new JLabel();
    /**
     *
     */
    public JCSubstringDialog(Dialog parent, String title, boolean isModal) {
        super(parent, title, isModal);
        try {
            jbInit();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @throws java.lang.Exception
     */
    private void jbInit() throws Exception {
        SpinnerModel spModel0 = new SpinnerNumberModel(1, 1, 999999999, 1);
        SpinnerModel spModel1 = new SpinnerNumberModel(1, 1, 999999999, 1);
        this.mStartSp.setModel(spModel0);
        this.mLengthSp.setModel(spModel1);
        border1 = BorderFactory.createBevelBorder(BevelBorder.RAISED, Color.white, Color.white, new Color(118, 118, 114), new Color(169, 169, 164));
        border2 = BorderFactory.createEmptyBorder();
        titledBorder1 = new TitledBorder(BorderFactory.createEtchedBorder(Color.white, new Color(169, 169, 164)), "参数");
        jPanel1.setLayout(borderLayout1);
        jPanel2.setBorder(border1);
        jPanel2.setLayout(borderLayout2);
        mCancelBtn.setText("取消");
        mCancelBtn.addActionListener(this);
        jPanel3.setLayout(flowLayout1);
        flowLayout1.setAlignment(FlowLayout.RIGHT);
        jPanel4.setBorder(titledBorder1);
        jPanel4.setLayout(verticalFlowLayout1);
        jPanel6.setLayout(flowLayout2);
        jPanel5.setLayout(flowLayout3);
        flowLayout2.setAlignment(FlowLayout.RIGHT);
        flowLayout3.setAlignment(FlowLayout.RIGHT);
        jLabel1.setText("子串起始位");
        jLabel2.setText("子串长度");
        mStartSp.setPreferredSize(new Dimension(100, 22));
        mLengthSp.setPreferredSize(new Dimension(100, 22));
        mOkBtn.setSelected(false);
        mOkBtn.setText("确定");
        mOkBtn.addActionListener(this);
        this.getContentPane().add(jPanel1, BorderLayout.CENTER);
        jPanel1.add(jPanel2, BorderLayout.CENTER);
        jPanel2.add(jPanel4, BorderLayout.CENTER);
        jPanel4.add(jPanel6, null);
        jPanel6.add(jLabel1, null);
        jPanel6.add(mStartSp, null);
        jPanel4.add(jPanel5, null);
        jPanel5.add(jLabel2, null);
        jPanel5.add(mLengthSp, null);
        jPanel1.add(jPanel3, BorderLayout.SOUTH);
        jPanel3.add(mOkBtn, null);
        jPanel3.add(mCancelBtn, null);
        this.setSize(220, 180);
        this.setMinimumSize(220, 180);
        this.CenterWindow();
    }

    /**
     *
     * @param e
     */
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.mOkBtn) {
            onOk();
        }
        else if (e.getSource() == this.mCancelBtn) {
            onCancel();
        }

    }

    /**
     *
     * @return
     */
    public int getStart() {
        return ( (Integer)this.mStartSp.getValue()).intValue();
    }

    /**
     *
     * @return
     */
    public int getLength() {
        return ( (Integer)this.mLengthSp.getValue()).intValue();
    }

    /**
     *
     */
    private void onOk() {
        super.OnOk();
    }

    /**
     *
     */
    private void onCancel() {
        super.OnCancel();
    }
}