package jreport.swing.classes.ReportBook;

import jreport.swing.classes.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class JBDUnitInfo extends JUnitInfo {

  public JBDUnitInfo() {
  }
  public static JBDUnitInfo createBDUnitInfo(JReportModel RM,JBdhInfo BdhInfo,JBDHDataInfo BDHDataInfo,int col) {
    JBDUnitInfo UnitInfo = new JBDUnitInfo();
    JRowInfo RI = BdhInfo.RowInfo;
    JColInfo CI = JColInfo.GetColInfoByZB(col,1,RM);
    UnitInfo.ChangeLog = 1; // ÐÂ½¨
    UnitInfo.RowInfo = RI;
    UnitInfo.ColInfo = CI;
    UnitInfo.BDHDataInfo = BDHDataInfo;
    return UnitInfo;
  }
  public int getPhyHZD_ZB() {
    return BDHDataInfo.getPhyPrioRow()+BDHDataInfo.getTrueOffset();
//    return JBdhUtils.getPhyRow(RowInfo.HZD_ZB-1,ReportModel);
  }

  public int getHZD_ZB() {
    int Index = BDHDataInfo.BdhInfo.RowInfo.getBdhDataList().indexOf(BDHDataInfo);
    return Index+1;
  }
  public int getLZD_ZB() {
    return ColInfo.LZD_ZB;
  }

}
