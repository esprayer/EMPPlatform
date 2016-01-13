package jreport.swing.classes.wizard.imp.HelpCOBJDlg.util;

import java.util.*;

import javax.swing.*;

import jreport.swing.classes.wizard.*;
import jreport.swing.classes.wizard.imp.HelpCOBJDlg.ui.*;
import jreport.jdal.classes.sap.JSAPFuncObjStub;
import java.util.ResourceBundle;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: Pansoft</p>
 * @author Stephen Zhao
 * @version 1.0
 */
public class JCOBJUtils
    implements JCOBJConstants {
static ResourceBundle res = ResourceBundle.getBundle("jreport.swing.classes.wizard.imp.HelpCOBJDlg.util.Language");

    /**
     *
     * @return
     */
    public static Vector getCompareItems(JLimitObjectStub los) {
        Vector compareItems = new Vector();
        //SAP函数单独取条件对象
        //add by gengeng 2007-3-28
        if (los.FunctionStub.id.indexOf("SAPBB") >= 0) {
            JSAPFuncObjStub objStub;
            JConditionObjectStub conStub;
            String func = los.FunctionStub.GetParamValueCtrl("SAPFUNCFL").getText();
            if(func == null || func.trim().length() == 0)
                return null;
            Object objs = JSAPFuncUtil.getSAPFuncObjInfo(func);
            java.util.ArrayList objList;
            objList = (ArrayList) objs;
            for (int i = 0, n = objList.size(); i < n; i++) {
                conStub = new JConditionObjectStub();
                objStub = (JSAPFuncObjStub) objList.get(i);
                //过滤出条件对象
                if ("TJDX".equals(objStub.F_OTYP)) {
                    conStub.id = objStub.F_OBJN;
                    conStub.text = objStub.F_NAME;
                    conStub.type = objStub.F_TYPE;
                    compareItems.add(conStub);
                    //将该函数的条件对象保存起来
                    JSAPFuncUtil.addSAPFuncCObj(func, objStub);
                }
                conStub = null;
            }
        } else {
            String[] filterIDs = los.FunctionStub.cobj.split(",");
            Hashtable allCompareItems = los.HelpObject.WizardObject.ConditionObjectList;

            for (int i = 0; i < filterIDs.length; i++) {
                String crtID = filterIDs[i];
                JConditionObjectStub cos = (JConditionObjectStub) allCompareItems.get(crtID);
                if (cos != null) {
                    compareItems.add(cos);
                }
            }
        }
        compareItems.insertElementAt(new JConditionObjectStub(), 0); //空选项
        return compareItems;
    }

    /**
     *
     * @param talle
     * @return
     */
    public static String getStoreExp(JCOBJTable talle) {
        String expStr = "";
        JCOBJTableModel model = (JCOBJTableModel) talle.getModel();
        Vector exps = model.getExps();
        for (Enumeration e = exps.elements(); e.hasMoreElements(); ) {
            JCOBJExpressionItem info = (JCOBJExpressionItem) e.nextElement();
            expStr += info.toStoreString();
        }
        return expStr;
    }

    /**
     *
     * @param talle
     * @return
     */
    public static String getShowExp(JCOBJTable talle) {
        String expStr = "";
        JCOBJTableModel model = (JCOBJTableModel) talle.getModel();
        Vector exps = model.getExps();
        for (Enumeration e = exps.elements(); e.hasMoreElements(); ) {
            JCOBJExpressionItem info = (JCOBJExpressionItem) e.nextElement();
            expStr += info.toShowString();
        }
        return expStr;
    }

    /**
     *
     * @return
     */
    public static boolean checkBeforeOk(JCOBJTable table) {
        table.editingStopped(null);
        int[] checkIndexs = new int[] {1, 2, 5};
        String[] msgs = new String[] {res.getString("String_5"), res.getString("String_6"), res.getString("String_7")};
        boolean flag = false;
        String msg = "", value, colName;
        int rowCount = table.getRowCount();
        if (rowCount == 0) {
            return true;
        }
        //只有一行,不校验连接符
        if (rowCount == 1) {
            checkIndexs = new int[] {1, 2};
            for (int j = 0; j < checkIndexs.length; j++) {
                value = table.getValueAt(0, checkIndexs[j]).toString();
                if (value == null || (value != null && value.length() == 0)) {
                    /*如果是BOOLEAN型的字符，不用比较符*/
                    if (j == 1) {
                        JConditionObjectStub itemInfo = (JConditionObjectStub) table.getValueAt(0, j);
                        if (itemInfo != null && itemInfo.text.length() > 0) {
                            if (itemInfo.type.equals("B")) {
                                continue;
                            }
                        }
                    }
                    flag = true;
                    msg = msgs[j];
                    break;
                }
            }
        } else {
            for (int i = 0; i < table.getRowCount() - 1; i++) {
                for (int j = 0; j < checkIndexs.length; j++) {
                    value = table.getValueAt(i, checkIndexs[j]).toString();
                    if (value == null || (value != null && value.length() == 0)) {
                        /*如果是BOOLEAN型的字符，不用比较符*/
                        if (j == 1) {
                            JConditionObjectStub itemInfo = (JConditionObjectStub) table.getValueAt(i, j);
                            if (itemInfo != null && itemInfo.text.length() > 0) {
                                if (itemInfo.type.equals("B")) {
                                    continue;
                                }
                            }
                        }
                        flag = true;
                        msg = msgs[j];
                        break;
                    }
                }
            }
            //多行不校验最后一行的连接符
            checkIndexs = new int[] {1, 2};
            for (int j = 0; j < checkIndexs.length; j++) {
                value = table.getValueAt(table.getRowCount() - 1, checkIndexs[j]).toString();
                if (value == null || (value != null && value.length() == 0)) {
                    /*如果是BOOLEAN型的字符，不用比较符*/
                    if (j == 1) {
                        JConditionObjectStub itemInfo = (JConditionObjectStub) table.getValueAt(table.getRowCount() - 1, j);
                        if (itemInfo != null && itemInfo.text.length() > 0) {
                            if (itemInfo.type.equals("B")) {
                                continue;
                            }
                        }
                    }
                    flag = true;
                    msg = msgs[j];
                    break;
                }
            }

        }

        if (flag) {
            JOptionPane.showMessageDialog(table, res.getString("String_12") + msg + res.getString("String_13") + msg, res.getString("String_14"), JOptionPane.ERROR_MESSAGE);
            return false;
        }

        return checkBracketMatch(table);
    }

    /**
     * 修改为不进行行校验
     * @return
     */
    public static boolean checkBeforeInsert(JCOBJTable table) {
        /*
                 table.editingStopped(null);
                 int[] checkIndexs = new int[] {1, 2, 5};
                 String[] msgs = new String[] {"比较项目", "比较符", "连接符"};
                 boolean flag = false;
                 String msg = "";
//        for (int i = 0; i < table.getRowCount(); i++) {
                 int i = table.getSelectedRow();
                 if(i != -1){
            for (int j = 0; j < checkIndexs.length; j++) {
                String value = table.getValueAt(i, checkIndexs[j]).toString();
                if (value == null || (value != null && value.length() == 0)) {
                    flag = true;
                    msg = msgs[j];
                    break;
                }
            }
                 }
//        }
                 if (flag) {
            JOptionPane.showMessageDialog(table, "缺少必要的" + msg + ",请修改" + msg, "系统提示", JOptionPane.ERROR_MESSAGE);
            return false;
                 }
                 return checkBracketMatch(table);
         */
        return true;
    }

    /**
     *
     * @return
     */
    public static boolean checkBracketMatch(JCOBJTable table) {
        boolean match = true;
        //单行的校验
        /*
                 for (int i = 0; i < table.getRowCount(); i++) {
            String leftBracket = table.getValueAt(i, 0).toString();
            String rightBracket = table.getValueAt(i, 4).toString();
            if (leftBracket.equals("(") && (!rightBracket.equals(")"))) {
                flag = true;
                break;
            }
            if (rightBracket.equals(")") && (!leftBracket.equals("("))) {
                flag = true;
                break;
            }
                 }
         */
        //多行的校验
        String bracketStr = "";
        for (int i = 0; i < table.getRowCount(); i++) {
            String leftBracket = table.getValueAt(i, 0).toString();
            String rightBracket = table.getValueAt(i, 4).toString();
            if (leftBracket != null) {
                bracketStr += leftBracket;
            }
            if (rightBracket != null) {
                bracketStr += rightBracket;
            }
        }
        if (bracketStr != null && bracketStr.length() > 0) {
            if ( (bracketStr.length() % 2) != 0 || bracketStr.charAt(0) != '(') {
                match = false;
            }
        }
        if (!match) {
            JOptionPane.showMessageDialog(table, res.getString("String_17"), res.getString("String_18"), JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }
}
