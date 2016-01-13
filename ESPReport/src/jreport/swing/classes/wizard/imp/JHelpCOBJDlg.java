package jreport.swing.classes.wizard.imp;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;
import javax.swing.table.*;

import com.borland.jbcl.layout.*;
import jreport.swing.classes.wizard.*;
import jreport.swing.classes.wizard.imp.HelpCOBJDlg.ui.*;
import jreport.swing.classes.wizard.imp.HelpCOBJDlg.util.*;
import jreportwh.jdof.classes.common.swing.*;
import java.util.ResourceBundle;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class JHelpCOBJDlg
        extends JDefaultDialog
	implements ActionListener, ListSelectionListener, FocusListener {
static ResourceBundle res = ResourceBundle.getBundle("jreport.swing.classes.wizard.imp.Language");
    /**
     *
     */
    private JCOBJExpression mExp = null;
    private JCOBJStoreManager mStoreMgr = new JCOBJStoreManager();

    /**
     * GUI Components
     */
    private JCOBJSubstringDialog mSubstringDlg = new JCOBJSubstringDialog(this, res.getString("String_28"), true);
    private JCOBJNameDialog mNameDialog = new JCOBJNameDialog(this, res.getString("String_29"), true);
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
    private JLimitObjectStub mLos = null;
    private JCOBJTable mTable = null;
    private Border border2;

    /**
     *
     * @param frame
     * @param title
     * @param isModal
     * @param PO
     */
    public JHelpCOBJDlg(Frame parent, String title, boolean isModal, JLimitObjectStub los) {
        super(parent, title, isModal);
        this.mLos = los;

        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @throws java.lang.Exception
     */
    private void jbInit()
            throws Exception {
        mTable = new JCOBJTable(this, mLos);
        this.mTable.getSelectionModel().addListSelectionListener(this);
        mTable.getColumnModel().getSelectionModel().addListSelectionListener(this);
        mTable.setColumnSelectionAllowed(true);
        /**
         * 给连接符列表加入
         */
        //
        border1 = BorderFactory.createEmptyBorder(5, 5, 5, 5);
        border2 = BorderFactory.createEmptyBorder(5, 5, 5, 5);
        this.getContentPane().setLayout(borderLayout1);
        jPanel2.setLayout(flowLayout1);
        mCancelBtn.setPreferredSize(new Dimension(80, 25));
        mCancelBtn.setMnemonic('C');
        mCancelBtn.setText(res.getString("String_31"));
        mCancelBtn.addActionListener(this);
        flowLayout1.setAlignment(FlowLayout.RIGHT);
        mOkBtn.setPreferredSize(new Dimension(80, 25));
        mOkBtn.setMnemonic('O');
        mOkBtn.setText(res.getString("String_33"));
        mOkBtn.addActionListener(this);
        jPanel1.setBorder(BorderFactory.createRaisedBevelBorder());
        jPanel1.setLayout(borderLayout2);
        jPanel4.setLayout(verticalFlowLayout1);
        jPanel3.setLayout(borderLayout3);
        verticalFlowLayout1.setAlignment(VerticalFlowLayout.TOP);
        verticalFlowLayout1.setHgap(5);
        verticalFlowLayout1.setVgap(5);
        verticalFlowLayout1.setHorizontalFill(true);
        mSelectBtn.setPreferredSize(new Dimension(80, 25));
        mSelectBtn.setText(res.getString("String_34"));
        mSelectBtn.addActionListener(this);
        mInsertBtn.setPreferredSize(new Dimension(80, 25));
        mInsertBtn.setText(res.getString("String_35"));
        mInsertBtn.addActionListener(this);
        mRemoveBtn.setOpaque(true);
        mRemoveBtn.setPreferredSize(new Dimension(80, 25));
        mRemoveBtn.setText(res.getString("String_36"));
        mRemoveBtn.addActionListener(this);
        mSaveBtn.setPreferredSize(new Dimension(80, 25));
        mSaveBtn.setText(res.getString("String_37"));
        mSaveBtn.addActionListener(this);
        mSubBtn.setEnabled(false);
        mSubBtn.setPreferredSize(new Dimension(80, 25));
        mSubBtn.setMargin(new Insets(2, 5, 2, 5));
        mSubBtn.setText(res.getString("String_38"));
        mSubBtn.addActionListener(this);
        mAddBtn.setPreferredSize(new Dimension(80, 25));
        mAddBtn.setText(res.getString("String_39"));
        mAddBtn.addActionListener(this);
        jPanel3.setBorder(border2);
        jPanel4.setBorder(border1);
        this.getContentPane().add(jPanel1, BorderLayout.CENTER);
        jPanel1.add(jPanel3, BorderLayout.CENTER);
        jPanel3.add(jScrollPane1, BorderLayout.CENTER);
        jScrollPane1.getViewport().add(mTable, null);
        jPanel1.add(jPanel4, BorderLayout.EAST);
        jPanel4.add(mSelectBtn, null);
        jPanel4.add(mSaveBtn, null);
        jPanel4.add(mAddBtn, null);
        jPanel4.add(mInsertBtn, null);
        jPanel4.add(mRemoveBtn, null);
        this.getContentPane().add(jPanel2, BorderLayout.SOUTH);
        jPanel2.add(mOkBtn, null);
        jPanel2.add(mCancelBtn, null);
        jPanel4.add(mSubBtn, null);
        //final
        this.pack();
        this.setSize(new Dimension(550, 300));
        this.setMinimumSize(550, 300);
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    }

    /**
     *
     * @param e
     */
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.mSelectBtn) {
            onSelect();
        } else if (e.getSource() == this.mSaveBtn) {
            onSave();
        } else if (e.getSource() == this.mAddBtn) {
            onAdd();
        } else if (e.getSource() == this.mInsertBtn) {
            onInsert();
        } else if (e.getSource() == this.mRemoveBtn) {
            onRemove();
        } else if (e.getSource() == this.mSubBtn) {
            onSubstring();
        } else if (e.getSource() == this.mOkBtn) {
            onOk();
        } else if (e.getSource() == this.mCancelBtn) {
            onCancel();
        }
    }

    /**
     * 选择
     */
    public void onSelect() {
        JCOBJChooser chooser = new JCOBJChooser(this, res.getString("String_40"), true, mStoreMgr);
        chooser.setVisible(true);
        int option = chooser.getOption();
        if (option == chooser.OPTION_OK) {
            //加载选择的条件表达式
            this.setExpression(chooser.getExpression());
            setOption(this.OPTION_OK);
            dispose();
        }

    }

    /**
     * 插入
     */
    public void onInsert() {
        if (JCOBJUtils.checkBeforeInsert(mTable)) {

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
        if (JCOBJUtils.checkBeforeInsert(mTable)) {
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
        TableCellEditor editor = mTable.getCellEditor();
        if (editor != null) {
            editor.stopCellEditing();
        }
        mTable.editingStopped(null);
        if (JCOBJUtils.checkBeforeOk(mTable)) {
            mNameDialog.setExpression(this.mTable.getExpression());
            mNameDialog.setVisible(true);
            int option = mNameDialog.getOption();
            if (option == mNameDialog.OPTION_OK) {
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
        if (text.equals(res.getString("String_41"))) {
            mSubstringDlg.setVisible(true);
            int option = mSubstringDlg.getOption();
            if (option == mSubstringDlg.OPTION_OK) {
                int start = mSubstringDlg.getStart();
                int length = mSubstringDlg.getLength();
                int row = mTable.getSelectedRow();
                if (row != -1) {
                    mTable.substring(true, row, start, length);
                    this.setSubstringStatus();
                }

            }
        } else {
            int row = mTable.getSelectedRow();
            mTable.substring(false, row, 0, 0);
            this.setSubstringStatus();
        }
    }

    /**
     * 确定
     */
    public void onOk() {
        mTable.editingStopped(null);
        if (JCOBJUtils.checkBeforeOk(mTable)) {
            this.setExpression(this.mTable.getExpression());
            setOption(this.OPTION_OK);
            dispose();
        }
    }

    /**
     *
     */
    public void onCancel() {
        setOption(this.OPTION_CANCEL);
        dispose();
    }

    /**
     *
     * @param expStr
     */
    public void setExpression(JCOBJExpression exp) {
        if (exp != null) {
            this.mExp = exp;
        }
    }

    /**
     * 取字符串表达式
     * 将 LIKE 后的字符串自动加％号
     * @return
     */
    public JCOBJExpression getExpression() {
        return this.mExp;
    }

    /**
     *
     */
    public void setSubstringStatus() {
        int row = mTable.getSelectedRow();
        if (row != -1) {
            JCOBJTableModel model = (JCOBJTableModel) mTable.getModel();
            JCOBJExpressionItem expItem = (JCOBJExpressionItem) model.getExps().get(row);

            if (expItem != null && expItem.canSubstring()) {
                this.mSubBtn.setEnabled(true);
                if (expItem.isSubstring()) {
                    mSubBtn.setText(res.getString("String_42"));
                } else {
                    mSubBtn.setText(res.getString("String_43"));
                }
            } else {
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
        } else if (e.getSource() == mTable.getColumnModel().getSelectionModel() && mTable.getColumnSelectionAllowed()) {
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
            } else if (name.equals("ConnOperator")) {
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
