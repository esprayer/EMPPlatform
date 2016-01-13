package jservice.jbof.classes.BOFOfflineObject;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import com.borland.dbswing.*;
import com.borland.jbcl.layout.*;
import jbof.gui.window.classes.style.mdi.*;
import jframework.foundation.classes.*;
import com.borland.dx.sql.dataset.*;
import com.borland.dx.dataset.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class JAgentDataSetDemoWindow extends JBOFMDIChildWindow {
  BorderLayout borderLayout1 = new BorderLayout();
  JPanel jPanel1 = new JPanel();
  JPanel jPanel2 = new JPanel();
  BorderLayout borderLayout2 = new BorderLayout();
  JButton bnRUN = new JButton();
  JPanel jPanel3 = new JPanel();
  JTabbedPane tpnDB = new JTabbedPane();
  BorderLayout borderLayout3 = new BorderLayout();
  JPanel pnSQL = new JPanel();
  JPanel pnFilter = new JPanel();
  JPanel pnLocate = new JPanel();
  TableScrollPane tableScrollPane1 = new TableScrollPane();
  JdbTable dbtResult = new JdbTable();
  JdbNavToolBar dbnvToolbar = new JdbNavToolBar();
  JTextArea edSQL = new JTextArea();
  BorderLayout borderLayout4 = new BorderLayout();
  JLabel jLabel1 = new JLabel();
  JComboBox cbxColumnList1 = new JComboBox();
  JLabel jLabel2 = new JLabel();
  JTextField edFilter = new JTextField();
  BoxLayout2 boxLayout21 = new BoxLayout2();
  JLabel jLabel3 = new JLabel();
  JComboBox cbxColumnList2 = new JComboBox();
  JLabel jLabel4 = new JLabel();
  JTextField edLocate = new JTextField();
  BoxLayout2 boxLayout22 = new BoxLayout2();
  JAgentDataSet  queryDataSet = null;
  JAgentDataBase DataBase     = new JAgentDataBase();
  JPanel jPanel4 = new JPanel();
  BorderLayout borderLayout5 = new BorderLayout();
  FlowLayout flowLayout1 = new FlowLayout();
  JLabel lbErrorText = new JLabel();
  JButton bnSave = new JButton();
  JTabbedPane jTabbedPane1 = new JTabbedPane();
  JPanel pnJSQL = new JPanel();
  BorderLayout borderLayout6 = new BorderLayout();
  JPanel pnColumn = new JPanel();
//  Database database1 = new Database();
  JAgentDataSet LSYSZD = new JAgentDataSet();
  JAgentDataSet LSYSLB = new JAgentDataSet();
  JPanel jPanel5 = new JPanel();
  JPanel jPanel6 = new JPanel();
  BoxLayout2 boxLayout23 = new BoxLayout2();
  TableScrollPane tableScrollPane2 = new TableScrollPane();
  BorderLayout borderLayout7 = new BorderLayout();
  TableScrollPane tableScrollPane3 = new TableScrollPane();
  BorderLayout borderLayout8 = new BorderLayout();
  JdbTable dbtLSYSZD = new JdbTable();
  JdbTable dbtLSYSLB = new JdbTable();
  Column column1 = new Column();
  JTabbedPane jTabbedPane2 = new JTabbedPane();
  JPanel jPanel7 = new JPanel();
  BoxLayout2 boxLayout24 = new BoxLayout2();
  Column column2 = new Column();

  public JAgentDataSetDemoWindow() {
    try {
      jbInit();
    }
    catch(Exception ex) {
      ex.printStackTrace();
    }
  }

  void jbInit() throws Exception {
    this.setLayout(borderLayout1);
    jPanel1.setLayout(borderLayout2);
    bnRUN.setVerifyInputWhenFocusTarget(true);
    bnRUN.setMnemonic('R');
    bnRUN.setText("Run");
    bnRUN.addActionListener(new JAgentDataSetDemoWindow_bnRUN_actionAdapter(this));
    jPanel3.setFont(new java.awt.Font("Dialog", 0, 12));
    jPanel3.setOpaque(true);
    jPanel3.setPreferredSize(new Dimension(80, 85));
    jPanel3.setLayout(borderLayout3);
    jPanel2.setLayout(borderLayout5);
    dbnvToolbar.setLocale(java.util.Locale.getDefault());
    dbnvToolbar.setAutoscrolls(true);
    dbnvToolbar.setMinimumSize(new Dimension(207, 24));
    dbnvToolbar.setOpaque(true);
    dbnvToolbar.setPreferredSize(new Dimension(240, 36));
    dbnvToolbar.setRequestFocusEnabled(true);
    dbnvToolbar.setFloatable(false);
    dbnvToolbar.setOrientation(SwingConstants.HORIZONTAL);
    dbnvToolbar.setAlignment(FlowLayout.LEFT);
    dbnvToolbar.setAutoDetect(true);
    dbnvToolbar.setButtonStateCancel(JdbNavToolBar.AUTO_ENABLED);
    dbnvToolbar.setButtonStateSave(JdbNavToolBar.HIDDEN);
    dbnvToolbar.setButtonStateRefresh(JdbNavToolBar.HIDDEN);
    dbnvToolbar.setShowRollover(true);
//    dbnvToolbar.setDataSet(queryDataSet);
    edSQL.setBorder(BorderFactory.createLoweredBevelBorder());
    edSQL.setText("");
    pnSQL.setLayout(borderLayout4);
    jLabel1.setText("列：");
    pnFilter.setLayout(boxLayout21);
    jLabel2.setText("过滤：");
    jLabel3.setText("列：");
    jLabel4.setText("值：");
    pnLocate.setLayout(boxLayout22);
    edLocate.setText("");
    edFilter.setText("");
//    queryDataSet.setAgentDataBase(DataBase);
//    dbtResult.setDataSet(queryDataSet);
    jPanel4.setLayout(flowLayout1);
    flowLayout1.setAlignment(FlowLayout.LEFT);
    flowLayout1.setHgap(0);
    flowLayout1.setVgap(0);
    lbErrorText.setForeground(Color.red);
    lbErrorText.setBorder(BorderFactory.createLoweredBevelBorder());
    bnSave.setMnemonic('S');
    bnSave.setText("Save");
    bnSave.addActionListener(new JAgentDataSetDemoWindow_bnSave_actionAdapter(this));
    pnJSQL.setLayout(borderLayout6);
//    database1.setConnection(new com.borland.dx.sql.dataset.ConnectionDescriptor("jdbc:sybase:Tds:Skyline:5000/cwbase1", "sa", "", false, "com.sybase.jdbc2.jdbc.SybDriver"));
    pnColumn.setLayout(boxLayout23);

    LSYSZD.setTableName("LSYSZD");
    LSYSZD.setAgentDataBase(this.DataBase);
    LSYSZD.setAgentSQL("SELECT * FROM LSYSZD");


    LSYSLB.setTableName("LSYSLB");
    LSYSLB.setAgentDataBase(this.DataBase);
    LSYSLB.setAgentSQL("select * from LSYSLB");


    boxLayout23.setAxis(BoxLayout.Y_AXIS);
    jPanel6.setLayout(borderLayout7);
    jPanel5.setLayout(borderLayout8);



    column1.setCalcType(com.borland.dx.dataset.CalcType.NO_CALC);
    column1.setColumnName("F_LBBH");
    column1.setDataType(com.borland.dx.dataset.Variant.STRING);
    column1.setExportDisplayMask("");
    column1.setDefault("");
    column1.setPrecision(2);
    column1.setRowId(true);
    column1.setTableName("LSYSZD");
    column1.setVisible(com.borland.jb.util.TriStateProperty.TRUE);
    column1.setServerColumnName("F_LBBH");
    column1.setSqlType(1);
    jPanel7.setLayout(boxLayout24);
    boxLayout24.setAxis(BoxLayout.Y_AXIS);
    column2.setCalcType(com.borland.dx.dataset.CalcType.CALC);
    column2.setCaption("F_LBMC(计算列)");
    column2.setColumnName("F_LBMC");
    column2.setDataType(com.borland.dx.dataset.Variant.STRING);
    column2.setDefault("");
    column2.setPreferredOrdinal(1);
    column2.setTableName("");
    column2.setServerColumnName("NewColumn1");
    column2.setSqlType(0);
    jPanel4.add(dbnvToolbar, null);
    jPanel4.add(bnRUN, null);
    jPanel4.add(bnSave, null);
    jPanel2.add(lbErrorText, BorderLayout.CENTER);
    pnJSQL.add(jPanel3, BorderLayout.NORTH);
    jPanel3.add(tpnDB, BorderLayout.CENTER);
    jPanel2.add(jPanel4, BorderLayout.WEST);
    tpnDB.add(pnSQL,   "SQL");
    pnSQL.add(edSQL, BorderLayout.CENTER);
    tpnDB.add(pnFilter,   "Filter");
    this.add(jTabbedPane1,  BorderLayout.CENTER);
    jTabbedPane1.add(pnJSQL,  "JSQL");
    pnJSQL.add(jPanel1,  BorderLayout.SOUTH);
    jPanel1.add(jPanel2, BorderLayout.NORTH);
    pnFilter.add(jLabel1, null);
    pnFilter.add(cbxColumnList1, null);
    pnFilter.add(jLabel2, null);
    pnFilter.add(edFilter, null);
    tpnDB.add(pnLocate, "Locate");
    pnLocate.add(jLabel3, null);
    pnLocate.add(cbxColumnList2, null);
    pnLocate.add(jLabel4, null);
    pnLocate.add(edLocate, null);
    pnJSQL.add(tableScrollPane1, BorderLayout.CENTER);
    jTabbedPane1.add(pnColumn,  "Column");
    pnColumn.add(jTabbedPane2, null);
    jTabbedPane2.add(jPanel7,  "MasterLink");
    jPanel7.add(jPanel6, null);
    jPanel6.add(tableScrollPane2, BorderLayout.CENTER);
    jPanel7.add(jPanel5, null);
    jPanel5.add(tableScrollPane3, BorderLayout.CENTER);
    tableScrollPane3.getViewport().add(dbtLSYSZD, null);
    tableScrollPane2.getViewport().add(dbtLSYSLB, null);
    tableScrollPane1.getViewport().add(dbtResult, null);
    // 默认进行SQL处理
    tpnDB.setSelectedIndex(0);
    LSYSZD.setMasterLink(new com.borland.dx.dataset.MasterLinkDescriptor(LSYSLB, new String[] {"F_LBBH"}, new String[] {"F_LBBH"}, false, false, false));
    LSYSZD.setColumns(new Column[] {column1, column2});
    LSYSZD.addCalcFieldsListener(new com.borland.dx.dataset.CalcFieldsListener() {
      public void calcFields(ReadRow changedRow, DataRow calcRow, boolean isPosted) throws DataSetException {
        queryDataSet2_calcFields(changedRow, calcRow, isPosted);
      }
    });
    LSYSLB.loadData();
    LSYSZD.loadData();

    dbtLSYSLB.setDataSet(LSYSLB);
    dbtLSYSZD.setDataSet(LSYSZD);
  }
  void queryDataSet2_calcFields(ReadRow changedRow, DataRow calcRow, boolean isPosted) throws DataSetException {
    DataRow lookupRow = new DataRow(LSYSLB, "F_LBBH");
    DataRow resultRow = new DataRow(LSYSLB);
    lookupRow.setString("F_LBBH", changedRow.getString("F_LBBH"));
    if (LSYSLB.lookup(lookupRow, resultRow, Locate.FIRST))
      calcRow.setString("F_LBMC", resultRow.getString("F_LBMC"));
  }

  void bnRUN_actionPerformed(ActionEvent e) {
    int Index = tpnDB.getSelectedIndex();
    if ( Index == 0 ) {
      runSQL();
    }
  }
  void runSQL() {
    lbErrorText.setText("");
    String sql = this.edSQL.getText();
    queryDataSet = new JAgentDataSet();
    queryDataSet.setAgentDataBase(DataBase);
    try {
      String TN = DataBase.getTableNameFromSQL(sql,null);
      queryDataSet.loadData(TN, sql);
      this.dbtResult.setDataSet(queryDataSet);
      this.dbnvToolbar.setDataSet(queryDataSet);
    } catch ( Exception e ) {
      lbErrorText.setText(e.getMessage());
      queryDataSet = null;
//      JOptionPane.showMessageDialog(JActiveDComDM.MainApplication.MainWindow,e.getMessage(), "系统提示",JOptionPane.ERROR_MESSAGE);
    }

  }

  void bnSave_actionPerformed(ActionEvent e) {
    if ( queryDataSet == null ) return;
    try {
      String TN = DataBase.getTableNameFromSQL(queryDataSet.getAgentSQL(),null);
      if ( TN != null ) {
        queryDataSet.setTableName(TN);
        queryDataSet.saveData();
      }
    } catch ( Exception ee ) {
      JOptionPane.showMessageDialog(JActiveDComDM.MainApplication.MainWindow,ee.getMessage(), "系统提示",JOptionPane.ERROR_MESSAGE);
    }
  }
}

class JAgentDataSetDemoWindow_bnRUN_actionAdapter implements java.awt.event.ActionListener {
  JAgentDataSetDemoWindow adaptee;

  JAgentDataSetDemoWindow_bnRUN_actionAdapter(JAgentDataSetDemoWindow adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.bnRUN_actionPerformed(e);
  }
}

class JAgentDataSetDemoWindow_bnSave_actionAdapter implements java.awt.event.ActionListener {
  JAgentDataSetDemoWindow adaptee;

  JAgentDataSetDemoWindow_bnSave_actionAdapter(JAgentDataSetDemoWindow adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.bnSave_actionPerformed(e);
  }
}
