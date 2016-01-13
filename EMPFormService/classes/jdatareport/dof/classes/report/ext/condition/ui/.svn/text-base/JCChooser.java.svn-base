package jdatareport.dof.classes.report.ext.condition.ui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;

import jdatareport.dof.classes.report.ext.condition.util.*;
import jfoundation.gui.window.classes.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: Pansoft</p>
 * @author Stephen Zhao
 * @version 1.0
 */

public class JCChooser
    extends JFrameDialog
    implements ActionListener, JCConstants {
    /**
     *
     */
    private JCStoreManager mStoreMgr = null;

    private JCExpression mExp = null;
    /**
     *
     */
    private JPanel jPanel1 = new JPanel();
    private JPanel jPanel2 = new JPanel();
    private BorderLayout borderLayout1 = new BorderLayout();
    private JPanel jPanel3 = new JPanel();
    private Border border1;
    private BorderLayout borderLayout2 = new BorderLayout();
    private JPanel jPanel4 = new JPanel();
    private BorderLayout borderLayout3 = new BorderLayout();
    private Border border2;
    private JButton mRemoveBtn = new JButton();
    private JButton mOkBtn = new JButton();
    private JButton mCancelBtn = new JButton();
    private FlowLayout flowLayout1 = new FlowLayout();
    private JScrollPane jScrollPane1 = new JScrollPane();
    private JTable mTable = new JTable();
    ;
    /**
     *
     */
    public JCChooser(Dialog parent, String title, boolean isModal, JCStoreManager storeMgr) {
        super(parent, title, isModal);
        this.mStoreMgr = storeMgr;
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
        JCStoreTableModel model = mStoreMgr.getModel();
        mTable.setModel(model);
        mTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        if (mTable.getRowCount() > 0) {
            mTable.setRowSelectionInterval(0, 0);
        }
        //列宽
        mTable.setRowHeight(DEFAULT_ROW_HEIGHT);
        int width = 100;
        TableColumn col = mTable.getColumnModel().getColumn(0);
        col.setPreferredWidth(width);
        width = 100;
        col = mTable.getColumnModel().getColumn(1);
        col.setPreferredWidth(width);
        width = 400;
        col = mTable.getColumnModel().getColumn(2);
        col.setPreferredWidth(width);
        //
        border1 = BorderFactory.createBevelBorder(BevelBorder.RAISED, Color.white, Color.white, new Color(118, 118, 114), new Color(169, 169, 164));
        border2 = BorderFactory.createEmptyBorder(5, 5, 5, 5);
        jPanel1.setLayout(borderLayout1);
        jPanel2.setBorder(border1);
        jPanel2.setLayout(borderLayout2);
        jPanel4.setLayout(borderLayout3);
        jPanel4.setBorder(border2);
        mRemoveBtn.setText("删除");
        mRemoveBtn.addActionListener(this);
        mOkBtn.setText("确定");
        mOkBtn.addActionListener(this);
        mCancelBtn.setText("取消");
        mCancelBtn.addActionListener(this);
        jPanel3.setLayout(flowLayout1);
        flowLayout1.setAlignment(FlowLayout.RIGHT);
        this.getContentPane().add(jPanel1, BorderLayout.CENTER);
        jPanel1.add(jPanel2, BorderLayout.CENTER);
        jPanel2.add(jPanel4, BorderLayout.CENTER);
        jPanel1.add(jPanel3, BorderLayout.SOUTH);
        jPanel3.add(mRemoveBtn, null);
        jPanel3.add(mOkBtn, null);
        jPanel3.add(mCancelBtn, null);
        jPanel4.add(jScrollPane1, BorderLayout.CENTER);
        jScrollPane1.getViewport().add(mTable, null);
        this.setSize(400, 300);
        this.setMinimumSize(400, 300);
        this.CenterWindow();
    }

    /**
     *
     * @param e
     */
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.mRemoveBtn) {
            onRemove();
        }
        else if (e.getSource() == this.mOkBtn) {
            onOk();
        }
        else if (e.getSource() == this.mCancelBtn) {
            onCancel();
        }
    }

    /**
     *
     */
    private void onRemove() {
        int row = mTable.getSelectedRow();
        if (row != -1) {
            JCStoreTableModel model = mStoreMgr.getModel();
            if (model != null) {
                model.removeRow(row);
                mTable.getSelectionModel().clearSelection();
            }
        }
    }

    /**
     *
     */
    private void onOk() {
        int row = mTable.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "请选择条件表达式", "系统提示", JOptionPane.ERROR_MESSAGE);
            return;
        }
        else {
            String id = (String) mTable.getValueAt(row, 0);
            this.setExpression(this.mStoreMgr.getExpression(id));
            this.mStoreMgr.save();
            super.OnOk();
        }

    }

    /**
     *
     * @param exp
     */
    public void setExpression(JCExpression exp) {
        if (exp != null) {
            this.mExp = exp;
        }
    }

    /**
     *
     * @return
     */
    public JCExpression getExpression() {
        return this.mExp;
    }

    /**
     *
     */
    private void onCancel() {
        super.OnCancel();
    }
}