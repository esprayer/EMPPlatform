package jreport.swing.classes.wizard.imp.HelpCOBJDlg.util;

import java.util.*;

import jreport.swing.classes.wizard.*;
import java.util.ResourceBundle;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: Pansoft</p>
 * @author Stephen Zhao
 * @version 1.0
 */

public class JCOBJExpressionItem {
static ResourceBundle res = ResourceBundle.getBundle("jreport.swing.classes.wizard.imp.HelpCOBJDlg.util.Language");
    public String mOpLeft = "";
    public JConditionObjectStub mCompareItem = new JConditionObjectStub();
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
    public JCOBJExpressionItem() {
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
                return res.getString("String_69");
            }
            else if (mOpConn.equals("OR")) {
                return res.getString("String_71");
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
            mCompareItem = (JConditionObjectStub) value;
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
            if (value.equals(res.getString("String_73"))) {
                mOpConn = "AND";
            }
            else if (value.equals(res.getString("String_75"))) {
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
            showStr += res.getString("String_78") + this.mCompareItem.text + "," + mStart + "," + mLength + ")";
        }
        else {
            showStr += this.mCompareItem.text;
        }
        showStr += this.mOpCompare;
        if (this.mCompareItem.type.toUpperCase().equals("C")) {
            showStr += "\'";
        }
        showStr += this.mCompareValue;
        if (this.mCompareItem.type.toUpperCase().equals("C")) {
            showStr += "\'";
        }
        showStr += this.mOpRight;
        if (mOpConn.equals("AND")) {
            showStr += res.getString("String_87");
        }
        else if (mOpConn.equals("OR")) {
            showStr += res.getString("String_89");
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
            storeStr += "SUBSTRING(" + this.mCompareItem.id + "," + mStart + "," + mLength + ")";
        }
        else {
            storeStr += this.mCompareItem.id;
        }

        storeStr += this.mOpCompare;
        if (this.mCompareItem.type.toUpperCase().equals("C")) {
            storeStr += "\'";
        }
        storeStr += this.mCompareValue;
        if (this.mCompareItem.type.toUpperCase().equals("C")) {
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
        return mCompareItem.type.toUpperCase().equals("C");
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
