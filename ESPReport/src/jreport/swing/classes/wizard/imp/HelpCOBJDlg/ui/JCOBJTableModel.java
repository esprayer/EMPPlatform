package jreport.swing.classes.wizard.imp.HelpCOBJDlg.ui;

import java.util.*;

import javax.swing.table.*;

import jreport.swing.classes.wizard.imp.HelpCOBJDlg.util.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: Pansoft</p>
 * @author Stephen Zhao
 * @version 1.0
 */

public class JCOBJTableModel
    extends DefaultTableModel
    implements JCOBJConstants {

    private Vector mExps = new Vector();
    public JCOBJTableModel() {
        super();
        init();
    }

    private void init() {
        this.addRow();
    }

    /**
     *
     * @return
     */
    public int getColumnCount() {
        return COL_NAMES.length;
    }

    /**
     *
     * @param columnIndex
     * @return
     */
    public String getColumnName(int columnIndex) {
        return COL_NAMES[columnIndex];
    }

    /**
     *
     * @param rowIndex
     * @param columnIndex
     * @return
     */
    public Object getValueAt(int rowIndex, int columnIndex) {
        JCOBJExpressionItem exp = (JCOBJExpressionItem)this.mExps.get(rowIndex);
        return exp.getValue(columnIndex);
    }

    /**
     *
     * @param aValue
     * @param rowIndex
     * @param columnIndex
     */
    public void setValueAt(Object value, int rowIndex, int columnIndex) {
        JCOBJExpressionItem exp = (JCOBJExpressionItem) mExps.get(rowIndex);
        if (exp != null) {
            exp.setValue(value, columnIndex);
        }
    }

    /**
     *
     * @param row
     */
    public void insertRow(int row) {
        JCOBJExpressionItem exp = new JCOBJExpressionItem();
        this.mExps.insertElementAt(exp, row);
        super.insertRow(row, exp.toVector());
    }

    /**
     *
     */
    public void addRow() {
        JCOBJExpressionItem exp = new JCOBJExpressionItem();
        this.mExps.add(exp);
        super.addRow(exp.toVector());
    }

    /**
     *
     * @param row
     */
    public void removeRow(int row) {
        this.mExps.removeElementAt(row);
        super.removeRow(row);
    }

    /**
     *
     * @return
     */
    public Vector getExps() {
        return this.mExps;
    }

    /**
     *
     * @param exps
     */
    public void setExps(Vector exps) {
        if (exps != null) {
            this.mExps = exps;
        }
    }

    public void substring(boolean isSubstring, int row, int start, int length) {
        if (row != -1) {
            JCOBJExpressionItem exp = (JCOBJExpressionItem)this.mExps.get(row);
            if (exp != null) {
                exp.setSubstring(isSubstring, start, length);
            }
        }
    }

}