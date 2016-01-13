package jdatareport.dof.classes.report.ext.condition.ui;

import java.util.*;

import javax.swing.table.*;

import jdatareport.dof.classes.report.ext.condition.util.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: Pansoft</p>
 * @author Stephen Zhao
 * @version 1.0
 */

public class JCTableModel
    extends DefaultTableModel
    implements JCConstants {

    private Vector mExps = new Vector();
    public JCTableModel() {
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
        JCExpressionItem exp = (JCExpressionItem)this.mExps.get(rowIndex);
        return exp.getValue(columnIndex);
    }

    /**
     *
     * @param aValue
     * @param rowIndex
     * @param columnIndex
     */
    public void setValueAt(Object value, int rowIndex, int columnIndex) {
        JCExpressionItem exp = (JCExpressionItem) mExps.get(rowIndex);
        if (exp != null) {
            exp.setValue(value, columnIndex);
        }
    }

    /**
     *
     * @param row
     */
    public void insertRow(int row) {
        JCExpressionItem exp = new JCExpressionItem();
        this.mExps.insertElementAt(exp, row);
        super.insertRow(row, exp.toVector());
    }

    /**
     *
     */
    public void addRow() {
        JCExpressionItem exp = new JCExpressionItem();
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
            JCExpressionItem exp = (JCExpressionItem)this.mExps.get(row);
            if (exp != null) {
                exp.setSubstring(isSubstring, start, length);
            }
        }
    }

}