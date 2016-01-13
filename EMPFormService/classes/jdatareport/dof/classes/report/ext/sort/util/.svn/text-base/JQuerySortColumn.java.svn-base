package jdatareport.dof.classes.report.ext.sort.util;

import jdatareport.dof.classes.report.ext.util.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class JQuerySortColumn
    extends DefaultQueryColumn {
    /**
     *
     */
    private JQuerySortOrder mOrder = new JQuerySortOrder(0);
    public JQuerySortColumn() {

    }

    /**
     *
     * @param name
     * @param order
     */
    public JQuerySortColumn(String name, JQuerySortOrder order) {
        if (name != null && order != null) {
            this.mName = name;
            this.mColIndex = order.getColumnIndex();
            this.mOrder = order;
        }
    }

    /**
     *
     * @param name
     * @param colIndex
     */
    public JQuerySortColumn(String name, int colIndex) {
        this(name, new JQuerySortOrder(colIndex));
    }

    /**
     *
     * @param col
     * @return
     */
    public Object getValueAt(int col) {
        if (col == 0) {
            return mName;
        }
        else if (col == 1) {
            return mOrder;
        }
        return "";
    }

    /**
     *
     * @param value
     * @param col
     */
    public void setValueAt(int col, Object value) {
        if (col < 0 && col > 1 && value == null) {
            return;
        }
        if (col == 0) {
            this.mName = (String) value;
        }
        else if (col == 1) {
            if (value.equals("升序")) {
                this.mOrder.setDirection(JQuerySortOrder.ORDER_ASC);
            }
            else if (value.equals("降序")) {
                this.mOrder.setDirection(JQuerySortOrder.ORDER_DESC);
            }

        }
    }

    /**
     *
     * @param order
     */
    public void setOrder(JQuerySortOrder order) {
        if (order != null) {
            this.mOrder = order;
        }
    }

    /**
     *
     * @return
     */
    public JQuerySortOrder getOrder() {
        return this.mOrder;
    }
}