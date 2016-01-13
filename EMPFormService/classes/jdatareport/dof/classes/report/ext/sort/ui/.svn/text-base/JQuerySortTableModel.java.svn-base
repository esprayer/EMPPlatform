package jdatareport.dof.classes.report.ext.sort.ui;

import java.util.*;

import javax.swing.table.*;

import jdatareport.dof.classes.report.ext.sort.util.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class JQuerySortTableModel
    extends DefaultTableModel {

    /**
     *
     */
    protected Vector mSortCols = new Vector();

    /**
     *
     */
    public JQuerySortTableModel() {
    }

    /**
     *
     * @return
     */
    public int getRowCount() {
        if (this.mSortCols != null) {
            return this.mSortCols.size();
        }
        return 0;
    }

    /**
     *
     * @return
     */
    public int getColumnCount() {
        return 0;
    }

    /**
     *
     * @param row
     * @param col
     * @return
     */
    public Object getValueAt(int row, int col) {
        if (this.mSortCols != null) {
            JQuerySortColumn sortCol = (JQuerySortColumn)this.mSortCols.get(row);
            if (sortCol != null) {
                return sortCol.getValueAt(col);
            }
        }
        return null;
    }

    /**
     *
     * @param row
     * @param col
     * @param vallue
     */
    public void setValueAt(Object value, int row, int col) {
        if (row < this.getRowCount() && col < this.getColumnCount() && value != null) {
            JQuerySortColumn sortCol = (JQuerySortColumn)this.mSortCols.get(row);
            if (sortCol != null) {
                sortCol.setValueAt(col, value);
            }
        }
    }

    /**
     *
     * @param column
     * @return
     */
    public String getColumnName(int column) {
        return "";
    }

    /**
     *
     * @return
     */
    public Vector getSortColumns() {
        return mSortCols;
    }

    /**
     *
     * @param colIndex
     * @return
     */
    public JQuerySortColumn getSortColumn(int colIndex) {
        if (colIndex >= 0 && colIndex < this.getSortColumnCount()) {
            return (JQuerySortColumn)this.mSortCols.get(colIndex);
        }
        return null;
    }

    /**
     *
     * @return
     */
    public int getSortColumnCount() {
        if (this.mSortCols != null) {
            return this.mSortCols.size();
        }
        return 0;
    }

    /**
     *
     * @param sortCols
     */
    public void setSortColumns(Vector sortCols) {
        if (sortCols != null) {
            this.mSortCols = sortCols;
        }
    }

    /**
     *
     * @param sortCol
     */
    public void addSortColumn(JQuerySortColumn sortCol) {
        if (sortCol != null) {
            this.mSortCols.add(sortCol);
        }
    }

    /**
     *
     * @param sortCol
     */
    public void removeSortColumn(JQuerySortColumn sortCol) {
        if (sortCol != null) {
            this.mSortCols.remove(sortCol);
        }
    }

    /**
     *
     * @param colIndex
     */
    public void removeSortColumn(int colIndex) {
        if (colIndex >= 0 && colIndex <= getSortColumnCount()) {
            this.mSortCols.remove(colIndex);
        }
    }

    /**
     *
     */
    public void removeAllSortColumn() {
        if (mSortCols != null) {
            this.mSortCols.removeAllElements();
        }
    }
}
