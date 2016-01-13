package jreport.swing.classes.wizard.imp.HelpCOBJDlg.ui;

import java.util.*;
import jreport.swing.classes.wizard.imp.HelpCOBJDlg.util.*;
import jtoolkit.xml.classes.*;
import org.jdom.*;
import javax.swing.table.*;
import java.util.ResourceBundle;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: Pansoft</p>
 * @author Stephen Zhao
 * @version 1.0
 */


public class JCOBJStoreTableModel
        extends DefaultTableModel {
static ResourceBundle res = ResourceBundle.getBundle("jreport.swing.classes.wizard.imp.HelpCOBJDlg.ui.Language");
    /**
     *
     */
    public final static String ROOT_NAME = "rpt-cobj";
    private String[] mColNames = new String[] {res.getString("String_20"), res.getString("String_21"), res.getString("String_22")};
    private Vector mExps = new Vector();
    /**
     *
     */
    public JCOBJStoreTableModel() {
        super();
    }

    /**
     *
     * @return
     */
    public int getColumnCount() {
        return mColNames.length;
    }

    /**
     *
     * @param columnIndex
     * @return
     */
    public String getColumnName(int columnIndex) {
        return mColNames[columnIndex];
    }

    /**
     *
     * @param rowIndex
     * @param columnIndex
     * @return
     */
    public Object getValueAt(int rowIndex, int columnIndex) {
        JCOBJExpression exp = (JCOBJExpression) mExps.get(rowIndex);
        return exp.getValueAt(columnIndex);
    }

    /**
     *
     * @return
     */
    public int getRowCount() {
        if (mExps == null) {
            mExps = new Vector();
        }
        return mExps.size();
    }

    /**
     *
     * @param value
     * @param rowIndex
     * @param columnIndex
     */
    public void setValueAt(Object value, int rowIndex, int columnIndex) {
        JCOBJExpression exp = (JCOBJExpression) mExps.get(rowIndex);
        exp.setValueAt(value, columnIndex);
    }

    /**
     *
     * @param name
     * @param exp
     */
    public void addExpression(String name, JCOBJExpression exp) {
        if (name != null && exp != null) {
            String newId = generateId();
            exp.mId = newId;
            exp.mName = name;
            if (newId != null) {
                mExps.add(exp);
            }
        }
    }

    /**
     *
     * @return
     */
    private String generateId() {
        if (mExps.size() > 0) {
            int maxId = Integer.parseInt( ( (JCOBJExpression) mExps.get(0)).mId);
            for (Enumeration e = mExps.elements(); e.hasMoreElements(); ) {
                JCOBJExpression exp = (JCOBJExpression) e.nextElement();
                int crtId = Integer.parseInt(exp.mId);
                if (crtId > maxId) {
                    maxId = crtId;
                }
            }
            return (maxId + 1) + "";
        }
        return "0";
    }

    /**
     *
     * @param name
     */
    public void removeExpressioin(String id) {
        for (Enumeration e = mExps.elements(); e.hasMoreElements(); ) {
            JCOBJExpression exp = (JCOBJExpression) e.nextElement();
            if (exp.mId.equals(id)) {
                mExps.remove(exp);
            }

        }
    }

    /**
     *
     * @param id
     * @return
     */
    public JCOBJExpression getExpression(String id) {
        for (Enumeration e = mExps.elements(); e.hasMoreElements(); ) {
            JCOBJExpression exp = (JCOBJExpression) e.nextElement();
            if (exp.mId.equals(id)) {
                return exp;
            }

        }
        return null;
    }

    /**
     *
     * @param exps
     * @return
     */
    public JXMLBaseObject toXml() {
        JXMLBaseObject cobjXmlObj = new JXMLBaseObject();
        Element rootElmt = cobjXmlObj.AddChildElement(null, ROOT_NAME);
        for (Enumeration e = mExps.elements(); e.hasMoreElements(); ) {
            JCOBJExpression exp = (JCOBJExpression) e.nextElement();
            Element crtElmt = cobjXmlObj.AddChildElement(rootElmt, "cobj");
            crtElmt.setAttribute("id", exp.mId);
            crtElmt.setAttribute("name", exp.mName);
            crtElmt.setAttribute("show", exp.mShowValue);
            crtElmt.setAttribute("store", exp.mStoreValue);
        }
        return cobjXmlObj;
    }

    /**
     *
     * @param exps
     */
    public void setExpression(Vector exps) {
        if (exps != null) {
            this.mExps = exps;
        }
    }

    /**
     *
     * @return
     */
    public Vector getExpressions() {
        return mExps;
    }

    /**
     *
     * @param row
     */
    public void removeRow(int row) {
        if (row < this.getRowCount()) {
            this.mExps.removeElementAt(row);
        }
    }

    /**
     *
     * @param row
     * @param column
     * @return
     */
    public boolean isCellEditable(int row, int column) {
        return false;
    }

}
