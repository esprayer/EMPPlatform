package jreport.swing.classes.wizard.imp.HelpCOBJDlg;

import javax.swing.table.*;

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

public class JCObjModel extends DefaultTableModel {
static ResourceBundle res = ResourceBundle.getBundle("jreport.swing.classes.wizard.imp.HelpCOBJDlg.Language");
  // Names of the columns.
  static protected String[]  cNames = {res.getString("String_1"), res.getString("String_2"), res.getString("String_3"), res.getString("String_4"), res.getString("String_5"), res.getString("String_6")};
  // Types of the columns.
  static protected Class[]  cTypes = {JCOBase.class, JCOBase.class, JCOBase.class, String.class,JCOBase.class, JCOBase.class};
  int RowCount = 1;
  public JCObjModel() {
  }

  public int getRowCount() {
    return RowCount;
  }

  public int getColumnCount() {
    return cNames.length;
  }

  public String getColumnName(int columnIndex) {
    return cNames[columnIndex];
  }


  public Class getColumnClass(int columnIndex) {
    return cTypes[columnIndex];
  }

  public boolean isCellEditable(int rowIndex, int columnIndex) {
    return true;
  }

  public Object getValueAt(int rowIndex, int columnIndex) {
    return null;
  }

  public void setValueAt(Object aValue, int rowIndex, int columnIndex) {

  }

}
//class JCObjValue {
//  public String LeftKH = null;
//  public JConditionObjectStub CObj   = null;
//  public String BJFH   = null;
//  public String Value  = null;
//  public String RightKH = null;
//  public String LJFH   = null;
//  public JCObjValue() {
//
//  }
//}
