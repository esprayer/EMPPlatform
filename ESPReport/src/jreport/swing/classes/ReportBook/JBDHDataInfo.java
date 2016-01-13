package jreport.swing.classes.ReportBook;

import jreport.swing.classes.*;
import java.util.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class JBDHDataInfo {
  public static final int MAX_BDH_COLUMNS = 80;
  public JBdhInfo     BdhInfo         = null;// 变动行的定义信息
  // Report Model
  public JReportModel ReportModel     = null;

  //
  // 列信息是以数据下标的形式存放的
  private JBDUnitInfo[] Units             = new JBDUnitInfo[MAX_BDH_COLUMNS];
  public int ChangeLog=0;

  public int        BD_OFFSET = 0;
  public String     BDSJ_HBM  = null;

  public int getTrueOffset() {
    return BdhInfo.RowInfo.getBdhDataList().indexOf(this) + 1;
  }
  public int getPrioRow() {
    return BdhInfo.RowInfo.HZD_ZB;
  }
  public int getPhyPrioRow() {
    return JBdhUtils.getPhyRow(BdhInfo.RowInfo.HZD_ZB-1,ReportModel);
  }
  public static JBDHDataInfo createBDDataInfo(JReportModel RM,JBdhInfo bdh) {
    JBDHDataInfo bd = new JBDHDataInfo();
    bd.ChangeLog = 1;
    bd.ReportModel = RM;
    bd.BdhInfo = bdh;
//    bd.createBDUnit();// 生成所有的80个单元，一行最多80列
    return bd;
  }
  private void createBDUnit() {
    JBDUnitInfo BDUnit = null;
    for(int i=0;i<MAX_BDH_COLUMNS;i++) {
      BDUnit = JBDUnitInfo.createBDUnitInfo(ReportModel,BdhInfo,this,i+1);
      Units[i] = BDUnit;
    }
  }
  public JBDUnitInfo getBDUnitInfo(int col) {
    if ( Units[col] == null )
      Units[col] = JBDUnitInfo.createBDUnitInfo(ReportModel,BdhInfo,this,col+1);
    return Units[col];
  }
  public JBDUnitInfo getUnitInfo(int col) {
    return Units[col];
  }

  public void setModified() {
    if ( ChangeLog != 1 ) ChangeLog = 2;
  }

  /**
   *
   * @param Key Object
   * @param Value Object
   */
  public void setDataObject(int col,String Text,double number) {
    Units[col].DYZD_SJ   = Text;
    Units[col].DYZD_DATA = number;
//    if ( Key == null || Value == null ) return;
//    DataInfos.put(Key,Value);
  }
  public JUnitInfo setDataObject(int col,String CText,String NText) {
    double number = 0.00;
    if ( Units[col] == null ) {
      Units[col] = JBDUnitInfo.createBDUnitInfo(ReportModel,BdhInfo,this,col+1);
    }
    Units[col].DYZD_SJ   = CText;
    try {
      number = Double.parseDouble(NText);
    } catch ( Exception e ) {
      number = 0.00;
    }
    Units[col].DYZD_DATA = number;
    return Units[col];
  }

  public JUnitInfo setAdjustDataObject(int col,String CText,String NText) {
  double number = 0.00;
  if ( Units[col] == null ) {
    Units[col] = JBDUnitInfo.createBDUnitInfo(ReportModel,BdhInfo,this,col+1);
  }
  Units[col].TZZD_SJ   = CText;
  try {
    number = Double.parseDouble(NText);
  } catch ( Exception e ) {
    number = 0.00;
  }
  Units[col].TZZD_DATA = number;
  return Units[col];
}


  public JBDHDataInfo() {

  }
}
