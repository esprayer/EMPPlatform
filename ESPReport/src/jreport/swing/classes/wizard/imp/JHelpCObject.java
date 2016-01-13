package jreport.swing.classes.wizard.imp;

import jframework.foundation.classes.*;
import jreport.swing.classes.wizard.*;
import java.util.ResourceBundle;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class JHelpCObject {
static ResourceBundle res = ResourceBundle.getBundle("jreport.swing.classes.wizard.imp.Language");
//  public Vector
    public JHelpCObject() {
    }

    public static Object Help(JLimitObjectStub LOS, JFunctionStub FS, JCustomTextField CTF, Object CO) {
        JHelpCOBJDlg dlg = new JHelpCOBJDlg(JActiveDComDM.MainApplication.MainWindow, res.getString("String_46"), true, LOS);
        dlg.centerOnScreen();
        dlg.setVisible(true);
        int option = dlg.getOption();
        if(option == dlg.OPTION_OK){
          return getHelpText(dlg.getExpression().mStoreValue);
        }
        return null;
    }

    /**
     * 帮助字符条件对象
     * @param LOS JLimitObjectStub
     * @param FS JFunctionStub
     * @param CTF JCustomTextField
     * @param CO Object
     * @return Object
     */
    public static Object HelpSOBJ(JLimitObjectStub LOS, JFunctionStub FS, JCustomTextField CTF, Object CO) {
        JHelpDSQLDlg dlg = new JHelpDSQLDlg(JActiveDComDM.MainApplication.MainWindow, res.getString("String_46"), true);
        dlg.CenterWindow();
        dlg.setVisible(true);
        if (dlg.getOption() == JHelpDSQLDlg.OK_OPTION) {
            return dlg.getSqlText();
        }
        return null;
    }

    /**
     *  处理LIKE后的条件
     * @param exp String
     * @return String
     */
    public static String getHelpText(String exp){
      String strSql,sValue;
      int pos,pos2,pos3,nLen;

      strSql = "";
      if(exp == null){
        exp = "";
      }
      //exp = exp.toUpperCase();
      pos = exp.indexOf("LIKE");
      while(pos > -1){
        nLen = pos + 4;
        strSql += exp.substring(0,nLen);
        exp = exp.substring(nLen);
        //取LIKE之后的表达式
        pos2 = exp.indexOf("'");
        if(pos2 <= -1){
          break;
        }
        exp = exp.substring(pos2 + 1);
        pos2 = exp.indexOf("'");
        if(pos2 <= -1){
          break;
        }
        sValue = exp.substring(0,pos2);
        strSql += " '" + sValue + "%'";
        exp = exp.substring(pos2 + 1);
        pos = exp.indexOf("LIKE");
      }
      //
      strSql += exp;
      return strSql;
    }

}
