package jdatareport.dof.classes.report.ext.condition.util;

import java.util.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: Pansoft</p>
 * @author Stephen Zhao
 * @version 1.0
 */

public class JCExpression implements JCConstants{
    public String mId = "";
    public String mName = "";
    public String mShowValue = "";
    public String mStoreValue = "";
    public Vector mExpItems = new Vector();
    public JCExpression() {
    }

    public String getValueAt(int index) {
        if (index == 0) {
            return mId;
        }
        else if (index == 1) {
            return mName;
        }
        else if (index == 2) {
            return mShowValue;
        }
        else if (index == 3) {
            return mStoreValue;
        }
        return "";
    }

    /**
     *
     * @param value
     * @param index
     */
    public void setValueAt(Object value, int index) {
        if (value == null) {
            return;
        }
        if (index == 0) {
            mId = (String) value;
        }
        else if (index == 1) {
            mName = (String) value;
        }
        else if (index == 2) {
            mShowValue = (String) value;
        }
        else if (index == 3) {
            mStoreValue = (String) value;
        }
    }
}