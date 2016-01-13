package jdatareport.dof.classes.report.ext.condition.util;

import java.io.*;
import java.util.*;

import org.jdom.*;
import jdatareport.dof.classes.report.ext.condition.ui.*;
import jtoolkit.xml.classes.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: Pansoft</p>
 * @author Stephen Zhao
 * @version 1.0
 */

public class JCStoreManager {
    /**
     * 常量
     */
    private final static String FILE_NAME = "LocalRptCobj.xml";
    private final static String FILE_PATH =
        System.getProperty("user.home") +
        File.separator +
        "pansoft" +
        File.separator +
        FILE_NAME;

    private JCStoreTableModel mModel = new JCStoreTableModel();
    /**
     *
     */
    public JCStoreManager() {
        load();
    }

    /**
     *
     */
    public JCStoreTableModel getModel() {
        return mModel;
    }

    /**
     *
     * @param model
     */
    public void setModel(JCStoreTableModel model) {
        if (model != null) {
            mModel = model;
        }

    }

    /**
     *
     * @param name
     * @param exp
     */
    public void addExpression(String name, JCExpression exp) {
        if (name != null && exp != null) {
            mModel.addExpression(name, exp);
        }
    }

    /**
     *
     * @param id
     * @return
     */
    public JCExpression getExpression(String id) {
        if (id != null) {
            return mModel.getExpression(id);
        }
        return null;
    }

    /**
     *
     * @param name
     */
    public void removeExpression(String id) {
        if (id != null) {
            mModel.removeExpressioin(id);
        }
    }

    /**
     *
     */
    public void save() {
        try {
            if (mModel != null) {
                JXMLBaseObject cobjXmlObj = mModel.toXml();
                String dataStr = cobjXmlObj.GetRootXMLString();
                FileOutputStream FOS = new FileOutputStream(FILE_PATH);
                FOS.write(dataStr.getBytes());
                FOS.flush();
                FOS.close();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     *
     */
    public boolean load() {
        try {
            if (new File(FILE_PATH).exists()) {
                FileInputStream FIS = new FileInputStream(FILE_PATH);
                JXMLBaseObject cobjXmlObj = new JXMLBaseObject();
                cobjXmlObj.InitXMLStream(FIS);
                FIS.close();

                Vector exps = new Vector();
                Element rootElmt = cobjXmlObj.GetElementByName(JCStoreTableModel.ROOT_NAME);
                for (Iterator i = rootElmt.getChildren().iterator(); i.hasNext(); ) {
                    Element crtElmt = (Element) i.next();
                    JCExpression exp = new JCExpression();
                    exp.mId = crtElmt.getAttributeValue("id", "");
                    exp.mName = crtElmt.getAttributeValue("name", "");
                    exp.mShowValue = crtElmt.getAttributeValue("show", "");
                    exp.mStoreValue = crtElmt.getAttributeValue("store", "");
                    exps.add(exp);
                }
                if (mModel != null) {
                    mModel.setExpression(exps);
                }

            }
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;

    }
}