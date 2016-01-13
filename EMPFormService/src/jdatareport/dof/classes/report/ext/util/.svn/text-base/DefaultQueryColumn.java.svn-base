package jdatareport.dof.classes.report.ext.util;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class DefaultQueryColumn
    implements QueryColumn, QueryConstants {
    /**
     *
     */
    protected String mName = "";
    protected int mColIndex = 0;
    protected String mType = COLUMN_TYPE_CHAR;
    protected String mID = "";
    /**
     *
     */
    public DefaultQueryColumn() {
    }

    /**
     *
     * @param name
     * @param colIndex
     */
    public DefaultQueryColumn(String name, int colIndex) {
        if (name != null) {
            this.mName = name;
            this.mColIndex = colIndex;
        }
    }

    public String getColumnName() {
        return mName;
    }

    public int getColumnIndex() {
        return mColIndex;
    }

    public void setColumnName(String name) {
        if (name != null) {
            this.mName = name;
        }
    }

    public void setColumnIndex(int colIndex) {
        this.mColIndex = colIndex;
    }

    public String toString() {
        return this.mName;
    }

    public String getColumnType() {
        return this.mType;
    }

    public void setColumnType(String type) {
        if (type != null) {
            this.mType = type;
        }
    }

    public String getColumnID() {
        return this.mID;
    }

    public void setColumnID(String ID) {
        if (ID != null) {
            this.mID = ID;
        }
    }
}