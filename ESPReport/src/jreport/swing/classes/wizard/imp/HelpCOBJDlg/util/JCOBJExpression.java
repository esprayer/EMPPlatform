package jreport.swing.classes.wizard.imp.HelpCOBJDlg.util;

import java.util.ResourceBundle;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: Pansoft</p>
 * @author Stephen Zhao
 * @version 1.0
 */


public class JCOBJExpression {
static ResourceBundle res = ResourceBundle.getBundle("jreport.swing.classes.wizard.imp.HelpCOBJDlg.util.Language");
    public String mId = "";
    public String mName = "";
    public String mShowValue = "";
    public String mStoreValue = "";
    public JCOBJExpression() {
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