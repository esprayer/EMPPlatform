package jservice.jbof.classes.GenerQueryObject.ext.condition.util;

import java.util.*;

import javax.swing.*;

import jservice.jbof.classes.GenerQueryObject.ext.condition.ui.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: Pansoft</p>
 * @author Stephen Zhao
 * @version 1.0
 */

public class JCUtils
    implements JCConstants {

    /**
     *
     * @return
     */
    public static Vector getCompareItems(Object los) {
        Vector compareItems = new Vector();
//        String[] filterIDs = los.FunctionStub.cobj.split(",");
//        Hashtable allCompareItems = los.HelpObject.WizardObject.ConditionObjectList;

//        for (int i = 0; i < filterIDs.length; i++) {
//            String crtID = filterIDs[i];
//            JConditionObjectStub cos = (JConditionObjectStub) allCompareItems.get(crtID);
//            if (cos != null) {
//                compareItems.add(cos);
//            }

//        }
//        compareItems.insertElementAt(new JConditionObjectStub(), 0); //锟斤拷选锟斤拷
        return compareItems;
    }

    /**
     *
     * @param talle
     * @return
     */
    public static String getStoreExp(JCTable talle) {
        String expStr = "";
        JCTableModel model = (JCTableModel) talle.getModel();
        Vector exps = model.getExps();
        for (Enumeration e = exps.elements(); e.hasMoreElements(); ) {
            JCExpressionItem info = (JCExpressionItem) e.nextElement();
            expStr += info.toStoreString();
        }
        return expStr;
    }

    /**
     *
     * @param talle
     * @return
     */
    public static String getShowExp(JCTable talle) {
        String expStr = "";
        JCTableModel model = (JCTableModel) talle.getModel();
        Vector exps = model.getExps();
        for (Enumeration e = exps.elements(); e.hasMoreElements(); ) {
            JCExpressionItem info = (JCExpressionItem) e.nextElement();
            expStr += info.toShowString();
        }
        return expStr;
    }

    /**
     *
     * @return
     */
    public static boolean checkBeforeOk(JCTable table) {
        table.editingStopped(null);
        int[] checkIndexs = new int[] {
            1, 2, 5};
        String[] msgs = new String[] {
            "锟饺斤拷锟斤拷目", "锟饺较凤拷", "l锟接凤拷"};
        boolean flag = false;
        String msg = "";
        int rowCount = table.getRowCount();
        if (rowCount == 0) {
            return true;
        }
        //只锟斤拷一锟斤拷,锟斤拷校锟斤拷l锟接凤拷
        if (rowCount == 1) {
            checkIndexs = new int[] {
                1, 2};
            for (int j = 0; j < checkIndexs.length; j++) {
                String value = table.getValueAt(0, checkIndexs[j]).toString();
                if (value == null || (value != null && value.length() == 0)) {
                    flag = true;
                    msg = msgs[j];
                    break;
                }
            }
        }
        else {
            for (int i = 0; i < table.getRowCount() - 1; i++) {
                for (int j = 0; j < checkIndexs.length; j++) {
                    String value = table.getValueAt(i, checkIndexs[j]).toString();
                    if (value == null || (value != null && value.length() == 0)) {
                        flag = true;
                        msg = msgs[j];
                        break;
                    }
                }
            }
            //锟斤拷锟叫诧拷校锟斤拷锟斤拷锟揭伙拷械锟絣锟接凤拷
            checkIndexs = new int[] {
                1, 2};
            for (int j = 0; j < checkIndexs.length; j++) {
                String value = table.getValueAt(table.getRowCount() - 1, checkIndexs[j]).toString();
                if (value == null || (value != null && value.length() == 0)) {
                    flag = true;
                    msg = msgs[j];
                    break;
                }
            }

        }

        if (flag) {
            JOptionPane.showMessageDialog(table, "缺锟劫憋拷要锟斤拷" + msg + ",锟斤拷锟睫革拷" + msg, "系统锟斤拷示", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        return checkBracketMatch(table);
    }

    /**
     *
     * @return
     */
    public static boolean checkBeforeInsert(JCTable table) {
        table.editingStopped(null);
        int[] checkIndexs = new int[] {
            1, 2, 5};
        String[] msgs = new String[] {
            "锟饺斤拷锟斤拷目", "锟饺较凤拷", "l锟接凤拷"};
        boolean flag = false;
        String msg = "";
//        for (int i = 0; i < table.getRowCount(); i++) {
        int i = table.getSelectedRow();
        if (i != -1) {
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
            JOptionPane.showMessageDialog(table, "缺锟劫憋拷要锟斤拷" + msg + ",锟斤拷锟睫革拷" + msg, "系统锟斤拷示", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return checkBracketMatch(table);
    }

    /**
     *
     * @return
     */
    public static boolean checkBracketMatch(JCTable table) {
        boolean flag = false;
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
        if (flag) {
            JOptionPane.showMessageDialog(table, "锟斤拷锟斤拷(锟脚诧拷匹锟斤拷,锟斤拷锟睫革拷锟斤拷锟斤拷(锟斤拷", "系统锟斤拷示", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }
}
