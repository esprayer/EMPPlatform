package jdatareport.dof.classes.report.ext.condition.ui;

import java.util.*;

import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;

import jdatareport.dof.classes.report.ext.condition.help.*;
import jdatareport.dof.classes.report.ext.condition.util.*;
import jdatareport.dof.classes.report.ext.util.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: Pansoft</p>
 * @author Stephen Zhao
 * @version 1.0
 */

public class JCTable
    extends JTable
    implements JCConstants {

    /**
     *
     */
    private JCDialog mDlg = null;
    private JCHelpObject mHelpObj = new JCHelpObject();
    private Vector mCompareItems = new Vector();

    /**
     *
     */

    /**
     *
     */
    public JCTable(JCDialog dlg) {
        super(new JCTableModel());
        this.mDlg = dlg;
        init();
    }

    /**
     *
     * @param compareItems
     */
    public void setCompareItems(Vector compareItems) {
        if (compareItems != null) {
            this.mCompareItems = compareItems;
        }
    }

    /**
     *
     */
    private void init() {
        this.setRowHeight(DEFAULT_ROW_HEIGHT);
        int width = 150;
        TableColumn col = this.getColumnModel().getColumn(1);
        col.setPreferredWidth(width);
        col = this.getColumnModel().getColumn(3);
        col.setPreferredWidth(width);

        this.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        this.setRowSelectionInterval(0, 0);
    }

    /**
     *
     * @param row
     * @param col
     * @return
     */
    public TableCellEditor getCellEditor(int row, int col) {
        /**@todo Override this javax.swing.JTable method*/
        DefaultCellEditor cellEditor = null;
        if (col == 0) {
            JComboBox mEditor0 = null;
            mEditor0 = new JComboBox(this.OP_LEFT);
            cellEditor = new DefaultCellEditor(mEditor0);
        }
        else if (col == 1) {
            //比较项目
            JComboBox mEditor1 = null;
            mEditor1 = new JComboBox(mCompareItems);
            mEditor1.setName("CompareItem");
            mEditor1.addFocusListener(mDlg);
            cellEditor = new DefaultCellEditor(mEditor1);
        }
        else if (col == 2) {
            JComboBox mEditor2 = null;
            mEditor2 = new JComboBox(this.OP_COMPARE);
            cellEditor = new DefaultCellEditor(mEditor2);
        }
        else if (col == 3) {
            QueryColumn queryCol = (QueryColumn)this.getValueAt(row, 1);
            if (queryCol != null && mHelpObj.isHelpable(queryCol)) {
                JCHelpTextField mEditor3 = null;
                mEditor3 = new JCHelpTextField();
                JButton helpBtn = mEditor3.getButton();
                if (helpBtn != null) {
                    helpBtn.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent ae) {
                            onHelp();
                        }
                    });
                }
                cellEditor = new DefaultCellEditor(mEditor3);
            }
        }
        else if (col == 4) {
            JComboBox mEditor4 = null;
            mEditor4 = new JComboBox(this.OP_RIGHT);
            cellEditor = new DefaultCellEditor(mEditor4);
        }
        else if (col == 5) {
            JComboBox mEditor5 = null;
            mEditor5 = new JComboBox(this.OP_CONN);
            mEditor5.setName("ConnOperator");
            cellEditor = new DefaultCellEditor(mEditor5);
        }

        if (cellEditor != null) {
            return cellEditor;
        }
        return super.getCellEditor(row, col);
    }

    /**
     *
     * @param rowIndex
     * @param columnIndex
     * @return
     */
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        if (columnIndex == 3) {

            //如果比较项目为空则比较值不可编辑
            QueryColumn col = (QueryColumn)this.getValueAt(rowIndex, 1);
            if (col != null && col.getColumnName().length() > 0) {
                return true;
            }
            else {
                return false;
            }
        }
        return true;
    }

    /**
     *
     */
    private void onHelp() {
        int row = this.getSelectedRow();
        QueryColumn col = (QueryColumn)this.getValueAt(row, 1);
        if (col != null && col.getColumnName().length() > 0) {
            Object data = mHelpObj.showHelp(col);
            if (data != null) {
                DefaultCellEditor cellEditor = (DefaultCellEditor)this.getCellEditor();
                JCHelpTextField ctf = (JCHelpTextField) cellEditor.getComponent();
                ctf.setText( (String) data);
                this.setValueAt(data, row, 3);
            }
        }

    }

    /**
     *
     * @param row
     */
    public void addRow() {
        JCTableModel model = (JCTableModel)this.getModel();
        if (model != null) {
            model.addRow();
            int rowCount = this.getRowCount();
        }
    }

    /**
     *
     * @param row
     */
    public void insertRow(int row) {
        JCTableModel model = (JCTableModel)this.getModel();
        if (model != null) {
            model.insertRow(row);
        }

    }

    /**
     *
     * @param row
     */
    public void removeRow(int row) {
        JCTableModel model = (JCTableModel)this.getModel();
        if (model != null) {
            model.removeRow(row);
            if (row > 0) {
                if (row == this.getRowCount()) {
                    this.setRowSelectionInterval(row - 1, row - 1);
                }
                else {
                    this.setRowSelectionInterval(row, row);
                }
            }
        }
    }

    /**
     *
     * @param row
     * @param start
     * @param length
     */
    public void substring(boolean isSubstring, int row, int start, int length) {
        if (row != -1) {
            JCTableModel model = (JCTableModel)this.getModel();
            if (model != null) {
                model.substring(isSubstring, row, start, length);
                this.updateUI();
            }
        }
    }

    /**
     * 获得条件表达式
     */
    public JCExpression getExpression() {
        JCExpression exp = new JCExpression();
        exp.mShowValue = JCUtils.getShowExp(this);
        exp.mStoreValue = JCUtils.getStoreExp(this);

        JCTableModel model = (JCTableModel) this.getModel();
        Vector exps = model.getExps();
        exp.mExpItems = exps;
        return exp;
    }

    /**
     *
     * @param row
     * @param column
     * @return
     */
    public TableCellRenderer getCellRenderer(int row, int column) {
        /**@todo Override this javax.swing.JTable method*/
        if (column == 1) {
            return new MyCellRenderer(row, column);
        }
        return super.getCellRenderer(row, column);
    }

    class MyCellRenderer
        extends DefaultTableCellRenderer {
        int mRow = 0;
        int mCol = 0;
        public MyCellRenderer(int row, int col) {
            mRow = row;
            mCol = col;
        }

        protected void setValue(Object value) {
            if (value == null) {
                setText("");
            }
            if (mRow != -1) {
                QueryColumn col = (QueryColumn) value;
                if (col != null) {
                    JCTableModel model = (JCTableModel) getModel();
                    JCExpressionItem expItem = (JCExpressionItem) model.getExps().get(mRow);
                    if (expItem != null && expItem.isSubstring()) {
                        setText("取子串(" + col.getColumnName() + "," + expItem.mStart + "," + expItem.mLength + ")");
                    }
                    else {
                        setText(col.getColumnName());
                    }
                }
            }

        }

    }
}