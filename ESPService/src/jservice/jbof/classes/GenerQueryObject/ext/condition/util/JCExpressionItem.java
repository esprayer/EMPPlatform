package jservice.jbof.classes.GenerQueryObject.ext.condition.util;

import java.util.*;

import jservice.jbof.classes.GenerQueryObject.ext.util.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: Pansoft</p>
 * @author Stephen Zhao
 * @version 1.0
 */

public class JCExpressionItem{
    public String mOpLeft = "";
    public QueryColumn mCompareItem = new DefaultQueryColumn();
    public String mOpCompare = "";
    public String mCompareValue = "";
    public String mOpRight = "";
    public String mOpConn = "";

    public int mStart = 0;
    public int mLength = 0;
    private boolean mIsSubstring = false;

    /**
     *
     */
    public JCExpressionItem() {
    }

    /**
     *
     * @param index
     * @return
     */
    public Object getValue(int index) {
        if (index == 0) {
            return mOpLeft;
        }
        else if (index == 1) {
            return mCompareItem;
        }
        else if (index == 2) {
            return mOpCompare;
        }
        else if (index == 3) {
            return mCompareValue;
        }
        else if (index == 4) {
            return mOpRight;
        }
        else if (index == 5) {
            if (mOpConn.equals("AND")) {
                return "并且";
            }
            else if (mOpConn.equals("OR")) {
                return "或者";
            }
            else {
                return mOpConn;
            }
        }
        return "";
    }

    public void setValue(Object value, int index) {
        if (index == 0) {
            mOpLeft = (String) value;
        }
        else if (index == 1) {
            mCompareItem = (QueryColumn) value;
        }
        else if (index == 2) {
            mOpCompare = (String) value;
        }
        else if (index == 3) {
            mCompareValue = (String) value;
        }
        else if (index == 4) {
            mOpRight = (String) value;
        }
        else if (index == 5) {
            if (value.equals("并且")) {
                mOpConn = "AND";
            }
            else if (value.equals("或者")) {
                mOpConn = "OR";
            }
            else {
                mOpConn = (String) value;
            }
        }

    }

    /**
     *
     * @return
     */
    public Vector toVector() {
        Vector vct = new Vector();
        vct.add(mOpLeft);
        vct.add(mCompareItem);
        vct.add(mOpCompare);
        vct.add(mCompareValue);
        vct.add(mOpRight);
        vct.add(mOpConn);
        return vct;
    }

    /**
     *
     * @return
     */
    public String toShowString() {
        String showStr = "";
        showStr += this.mOpLeft;

        if (isSubstring()) {
            showStr += "取子串(" + this.mCompareItem.getColumnName() + "," + mStart + "," + mLength + ")";
        }
        else {
            showStr += this.mCompareItem.getColumnName();
        }
        showStr += this.mOpCompare;
        if (this.mCompareItem.getColumnType().toUpperCase().equals("C")) {
            showStr += "\'";
        }
        showStr += this.mCompareValue;
        if (this.mCompareItem.getColumnType().toUpperCase().equals("C")) {
            showStr += "\'";
        }
        showStr += this.mOpRight;
        if (mOpConn.equals("AND")) {
            showStr += " 并且 ";
        }
        else if (mOpConn.equals("OR")) {
            showStr += " 或者 ";
        }
        return showStr;
    }

    /**
     *
     * @return
     */
    public String toStoreString() {
        String storeStr = "";
        storeStr += this.mOpLeft;
        if (isSubstring()) {
            storeStr += "SUBSTRING(" + this.mCompareItem.getColumnIndex() + "," + mStart + "," + mLength + ")";
        }
        else {
            storeStr += JCConstants.COLUMN_NAME_PREFIX+this.mCompareItem.getColumnIndex();
        }

        storeStr += this.mOpCompare;
        if (this.mCompareItem.getColumnType().toUpperCase().equals("C")) {
            storeStr += "\'";
        }
        storeStr += this.mCompareValue;
        if (this.mCompareItem.getColumnType().toUpperCase().equals("C")) {
            storeStr += "\'";
        }
        storeStr += this.mOpRight;
        storeStr += " ";
        storeStr += this.mOpConn;
        storeStr += " ";
        return storeStr;
    }

    /**
     *
     * @return
     */
    public String toString() {
        return toShowString();
    }

    /**
     *
     * @return
     */
    public boolean canSubstring() {
        return mCompareItem.getColumnType().toUpperCase().equals("C");
    }

    /**
     *
     * @return
     */
    public boolean isSubstring() {
        return mIsSubstring;
    }

    /**
     *
     * @param start
     * @param length
     */
    public void setSubstring(boolean isSubstring, int start, int length) {
        if (isSubstring) {
            this.mStart = start;
            this.mLength = length;

        }
        this.mIsSubstring = isSubstring;
    }

}