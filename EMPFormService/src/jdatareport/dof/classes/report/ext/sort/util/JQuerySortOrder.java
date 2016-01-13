package jdatareport.dof.classes.report.ext.sort.util;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class JQuerySortOrder {
    /**
     *
     */
    public static final int ORDER_ASC = 0; //升序
    public static final int ORDER_DESC = 1; //降序

    private int mColIndex = 0;

    /**
     *
     */
    public JQuerySortOrder() {

    }

    /**
     *
     * @param col
     */
    public JQuerySortOrder(int colIndex) {
        this.mColIndex = colIndex;
    }

    /**
     *
     * @param order
     */
    public void setDirection(int direction) {
        if (direction == ORDER_ASC && mColIndex < 0) {
            mColIndex = -mColIndex;
        }
        else if (direction == ORDER_DESC && mColIndex > 0) {
            mColIndex = -mColIndex;
        }
    }

    /**
     *
     * @return
     */
    public int getDirection() {
        if (mColIndex < 0) {
            return this.ORDER_ASC;
        }
        else if (mColIndex > 0) {
            return this.ORDER_DESC;
        }
        return 0;
    }

    /**
     *
     * @param colIndex
     */
    public void setColumnIndex(int colIndex) {
        this.mColIndex = colIndex;
    }

    /**
     *
     * @return
     */
    public int getColumnIndex() {
        return this.mColIndex;
    }

    /**
     *
     * @return
     */
    public String toString() {
        if (mColIndex > 0) {
            return "升序";
        }
        else if (mColIndex < 0) {
            return "降序";
        }
        return "";
    }
}