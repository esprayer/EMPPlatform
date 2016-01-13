package jdatareport.dof.classes.report.ext.condition.ui;

import java.util.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;

import com.borland.jbcl.layout.*;
import jdatareport.dof.classes.report.ext.condition.util.*;
import jfoundation.gui.window.classes.*;
import jdatareport.dof.classes.report.ext.condition.ui.JCTable;
/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class JCDialog
    extends JFrameDialog
    implements ActionListener, ListSelectionListener, FocusListener {
    /**
     *
     */
    private JCExpression mExp = null;
    private JCStoreManager mStoreMgr = new JCStoreManager();
    private Vector mCompareItems = null;
    /**
     * GUI Components
     */
    private JCTable mTable = null;

    private JCSubstringDialog mSubstringDlg = new JCSubstringDialog(this, "设置取子串参数", true);
    private JCNameDialog mNameDialog = new JCNameDialog(this, "请选择取数条件的名称", true);
    private JButton mSelectBtn = new JButton();
    private JButton mInsertBtn = new JButton();
    private JButton mRemoveBtn = new JButton();
    private JButton mOkBtn = new JButton();
    private JButton mCancelBtn = new JButton();

    private BorderLayout borderLayout1 = new BorderLayout();
    private JPanel jPanel1 = new JPanel();
    private JPanel jPanel2 = new JPanel();
    private FlowLayout flowLayout1 = new FlowLayout();

    private BorderLayout borderLayout2 = new BorderLayout();
    private JPanel jPanel3 = new JPanel();
    private JPanel jPanel4 = new JPanel();
    private VerticalFlowLayout verticalFlowLayout1 = new VerticalFlowLayout();
    private BorderLayout borderLayout3 = new BorderLayout();
    private Border border1;
    private JScrollPane jScrollPane1 = new JScrollPane();

    private JButton mSaveBtn = new JButton();
    private JButton mSubBtn = new JButton();
    private JButton mAddBtn = new JButton();

    private Border border2;
    private JPanel jPanel5 = new JPanel();
    private BorderLayout borderLayout4 = new BorderLayout();
    /**
     *
     * @param frame
     * @param title
     * @param isModal
     * @param PO
     */
    public JCDialog(Frame parent, String title, boolean isModal) {
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
        mTable = new JCTable(this);
        this.mTable.getSelectionModel().addListSelectionListener(this);
        mTable.getColumnModel().getSelectionModel().addListSelectionListener(this);
        mTable.setColumnSelectionAllowed(true);

        //
        border1 = BorderFactory.createEmptyBorder(5, 5, 5, 5);
        border2 = BorderFactory.createEmptyBorder(5, 5, 5, 5);
        this.getContentPane().setLayout(borderLayout1);
        jPanel2.setLayout(flowLayout1);
        mCancelBtn.setMnemonic('C');
        mCancelBtn.setText("取消(C)");
        mCancelBtn.addActionListener(this);
        flowLayout1.setAlignment(FlowLayout.RIGHT);
        mOkBtn.setMnemonic('O');
        mOkBtn.setText("确定(O)");
        mOkBtn.addActionListener(this);
        jPanel1.setBorder(BorderFactory.createRaisedBevelBorder());
        jPanel1.setLayout(borderLayout2);
        jPanel4.setLayout(verticalFlowLayout1);
        jPanel3.setLayout(borderLayout3);
        jPanel4.setBorder(border1);
        verticalFlowLayout1.setAlignment(VerticalFlowLayout.TOP);
        verticalFlowLayout1.setHgap(5);
        verticalFlowLayout1.setVgap(5);
        verticalFlowLayout1.setHorizontalFill(true);
        mSelectBtn.setText("选择");
        mSelectBtn.addActionListener(this);
        mInsertBtn.setText("插入");
        mInsertBtn.addActionListener(this);
        mRemoveBtn.setOpaque(true);
        mRemoveBtn.setText("删除");
        mRemoveBtn.addActionListener(this);
        mSaveBtn.setText("保存");
        mSaveBtn.addActionListener(this);
        mSubBtn.setEnabled(false);
        mSubBtn.setMinimumSize(new Dimension(73, 29));
        mSubBtn.setPreferredSize(new Dimension(97, 29));
        mSubBtn.setText("取子串");
        mSubBtn.addActionListener(this);
        mAddBtn.setText("追加");
        mAddBtn.addActionListener(this);
        jPanel3.setBorder(border2);
        jPanel5.setLayout(borderLayout4);
        this.getContentPane().add(jPanel1, BorderLayout.CENTER);
        jPanel1.add(jPanel3, BorderLayout.CENTER);
        jPanel3.add(jScrollPane1, BorderLayout.CENTER);
        jPanel3.add(jPanel5, BorderLayout.SOUTH);
        jScrollPane1.getViewport().add(mTable, null);
        jPanel1.add(jPanel4, BorderLayout.EAST);
//        jPanel4.add(mSelectBtn, null);
//        jPanel4.add(mSaveBtn, null);
        jPanel4.add(mAddBtn, null);
        jPanel4.add(mInsertBtn, null);
        jPanel4.add(mRemoveBtn, null);
        this.getContentPane().add(jPanel2, BorderLayout.SOUTH);
        jPanel2.add(mOkBtn, null);
        jPanel2.add(mCancelBtn, null);
        jPanel4.add(mSubBtn, null);
        //final
        this.pack();
        this.setSize(new Dimension(550, 250));
//        this.setMinimumSize(550, 250);
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    }

    /**
     *
     * @param e
     */
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.mSelectBtn) {
            onSelect();
        }
        else if (e.getSource() == this.mSaveBtn) {
            onSave();
        }
        else if (e.getSource() == this.mAddBtn) {
            onAdd();
        }
        else if (e.getSource() == this.mInsertBtn) {
            onInsert();
        }
        else if (e.getSource() == this.mRemoveBtn) {
            onRemove();
        }
        else if (e.getSource() == this.mSubBtn) {
            onSubstring();
        }
        else if (e.getSource() == this.mOkBtn) {
            onOk();
        }
        else if (e.getSource() == this.mCancelBtn) {
            onCancel();
        }
    }

    public void setCompareItems(Vector compareItems) {
        if (compareItems != null) {
            this.mCompareItems = compareItems;
            this.mTable.setCompareItems(compareItems);
        }
    }

    /**
     * 选择
     */
    public void onSelect() {
        JCChooser chooser = new JCChooser(this, "选择条件", true, mStoreMgr);
        chooser.setVisible(true);
        int option = chooser.Result;
        if (option == chooser.RESULT_OK) {
            //加载选择的条件表达式
            this.setExpression(chooser.getExpression());
            super.OnOk();
        }
    }

    /**
     * 插入
     */
    public void onInsert() {
        if (JCUtils.checkBeforeInsert(mTable)) {

            int row = mTable.getSelectedRow();
            if (row != -1) {
                if (row > 0) {
                    row--;
                }
                this.mTable.insertRow(row);
            }
        }
    }

    /**
     * 追加
     */
    public void onAdd() {
        if (JCUtils.checkBeforeInsert(mTable)) {
            this.mTable.addRow();
        }
    }

    /**
     * 删除
     */
    public void onRemove() {
        int row = mTable.getSelectedRow();
        if (row != -1) {
            this.mTable.removeRow(row);
        }
        if (mTable.getRowCount() == 0) {
            this.onAdd();
        }
    }

    /**
     * 保存
     */
    public void onSave() {
        if (JCUtils.checkBeforeOk(mTable)) {
            mNameDialog.setExpression(this.mTable.getExpression());
            mNameDialog.setVisible(true);
            int option = mNameDialog.Result;
            if (option == mNameDialog.RESULT_OK) {
                String name = mNameDialog.getName();
                this.mStoreMgr.addExpression(name, this.mTable.getExpression());
                this.mStoreMgr.save();
            }
        }
    }

    /**
     * 取子串
     */
    public void onSubstring() {
        String text = mSubBtn.getText();
        if (text.equals("取子串")) {
            mSubstringDlg.setVisible(true);
            int option = mSubstringDlg.Result;
            if (option == mSubstringDlg.RESULT_OK) {
                int start = mSubstringDlg.getStart();
                int length = mSubstringDlg.getLength();
                int row = mTable.getSelectedRow();
                if (row != -1) {
                    mTable.substring(true, row, start, length);
                    this.setSubstringStatus();
                }

            }
        }
        else {
            int row = mTable.getSelectedRow();
            mTable.substring(false, row, 0, 0);
            this.setSubstringStatus();
        }
    }

    /**
     * 确定
     */
    public void onOk() {
        if(mTable.isEditing()){
            this.mTable.getCellEditor().stopCellEditing();
        }
        if (JCUtils.checkBeforeOk(mTable)) {
            this.setExpression(this.mTable.getExpression());
            super.OnOk();
        }
    }

    /**
     *
     */
    public void onCancel() {
        super.OnCancel();
    }

    /**
     *
     * @param expStr
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
    public void setSubstringStatus() {
        int row = mTable.getSelectedRow();
        if (row != -1) {
            JCTableModel model = (JCTableModel) mTable.getModel();
            JCExpressionItem expItem = (JCExpressionItem) model.getExps().get(row);

            if (expItem != null && expItem.canSubstring()) {
                this.mSubBtn.setEnabled(true);
                if (expItem.isSubstring()) {
                    mSubBtn.setText("撤销取子串");
                }
                else {
                    mSubBtn.setText("取子串");
                }
            }
            else {
                this.mSubBtn.setEnabled(false);
                expItem.setSubstring(false, 0, 0);
            }

        }

    }

    /**
     *
     * @param e
     */
    public void valueChanged(ListSelectionEvent e) {
        if (e.getSource() == mTable.getSelectionModel() && mTable.getRowSelectionAllowed()) {
            // Column selection changed
            setSubstringStatus();
        }
        else if (e.getSource() == mTable.getColumnModel().getSelectionModel() && mTable.getColumnSelectionAllowed()) {
            // Row selection changed
            int row = mTable.getSelectedRow();
            int col = mTable.getSelectedColumn();
            if (row != -1 && col != -1 && col == 3) {
                if (mTable.isCellEditable(row, col)) {
                    mTable.editCellAt(row, col);
                }
            }

        }

    }

    /**
     *
     * @param focusEvent
     */
    public void focusGained(FocusEvent fe) {
    }

    /**
     *
     * @param focusEvent
     */
    public void focusLost(FocusEvent fe) {
        mTable.editingStopped(null);
        JComboBox editor = ( (JComboBox) fe.getSource());
        if (editor != null) {
            String name = editor.getName();
            if (name.equals("CompareItem")) {
                setSubstringStatus();
            }
            else if (name.equals("ConnOperator")) {
//                int row = mTable.getSelectedRow();
//                if (row != -1) {
//                    String value = (String) mTable.getValueAt(row, 5);
//                    if (value != null) {
//                        if (value.length() > 0) {
//                            onAdd();
//                        }
//                    }
//                }

            }
        }
    }
}
