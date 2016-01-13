package jdatareport.dof.classes.report.ext.sort.ui;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class JQuerySortSelTableModel
    extends JQuerySortTableModel {
    /**
     *
     */
    public static final int COL_COUNT = 2;

    public static final String[] COL_NAMES = new String[] {
        "列名", "顺序"};

    /**
     *
     */
    public JQuerySortSelTableModel() {
    }

    /**
     *
     * @return
     */
    public int getColumnCount() {
        return COL_COUNT;
    }

    /**
     *
     * @param column
     * @return
     */
    public String getColumnName(int column) {
        return COL_NAMES[column];
    }

    /**
     *
     * @param row
     * @param column
     * @return
     */
    public boolean isCellEditable(int row, int column) {
        /**@todo Override this javax.swing.table.DefaultTableModel method*/
        if (column == 1) {
            return true;
        }
        return false;
    }

}