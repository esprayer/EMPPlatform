package jservice.jbof.classes.GenerQueryObject.ext.sort.ui;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class JQuerySortUnselTableModel
    extends JQuerySortTableModel {
    /**
     *
     */
    public static final int COL_COUNT = 1;

    public static final String[] COL_NAMES = new String[] {
        "列名"};

    /**
     *
     */
    public JQuerySortUnselTableModel() {
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
        return false;
    }

}
