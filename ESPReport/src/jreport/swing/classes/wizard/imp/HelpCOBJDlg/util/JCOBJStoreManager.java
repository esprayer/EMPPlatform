package jreport.swing.classes.wizard.imp.HelpCOBJDlg.util;

import java.io.*;
import java.util.*;

import org.jdom.*;
import jreport.swing.classes.wizard.imp.HelpCOBJDlg.ui.*;
import jtoolkit.xml.classes.*;
import java.util.ResourceBundle;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: Pansoft</p>
 * @author Stephen Zhao
 * @version 1.0
 */

public class JCOBJStoreManager {
static ResourceBundle res = ResourceBundle.getBundle("jreport.swing.classes.wizard.imp.HelpCOBJDlg.util.Language");
    /**
     * ³£Á¿
     */
    private final static String FILE_NAME = "LocalRptCobj.xml";
    private final static String FILE_PATH =
        System.getProperty("user.home") +
        File.separator +
        "pansoft" +
        File.separator +
        FILE_NAME;

    private JCOBJStoreTableModel mModel = new JCOBJStoreTableModel();
    /**
     *
     */
    public JCOBJStoreManager() {
        load();
    }
    /**
     *
     */
    public JCOBJStoreTableModel getModel(){
        return mModel;
    }
    /**
     *
     * @param model
     */
    public void setModel(JCOBJStoreTableModel model) {
        if (model != null) {
            mModel = model;
        }

    }

    /**
     *
     * @param name
     * @param exp
     */
    public void addExpression(String name, JCOBJExpression exp) {
        if (name != null && exp != null) {
         mModel.addExpression(name,exp);
        }
    }
    /**
     *
     * @param id
     * @return
     */
    public JCOBJExpression getExpression(String id){
      if(id != null){
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
            if(mModel != null){
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
                Element rootElmt = cobjXmlObj.GetElementByName(JCOBJStoreTableModel.ROOT_NAME);
                for (Iterator i = rootElmt.getChildren().iterator(); i.hasNext(); ) {
                    Element crtElmt = (Element) i.next();
                    JCOBJExpression exp = new JCOBJExpression();
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
