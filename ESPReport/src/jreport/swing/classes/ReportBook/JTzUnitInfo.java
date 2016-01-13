package jreport.swing.classes.ReportBook;

import jreport.swing.classes.JReportModel;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2009</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class JTzUnitInfo extends JUnitInfo {
  public JTzUnitInfo() {
  }
  //------------------------------------------------------------------------------------------------
 //\uFFFD\uFFFD\uFFFD\uFFFD:
 //\uFFFD\uFFFD\uFFFD: Skyline(2001.12.29)
 //\u02B5\uFFFD\uFFFD: Skyline
 //\uFFFD\u07B8\uFFFD:
 //------------------------------------------------------------------------------------------------
 public void setModified() {
   if ( ChangeLog != 1 ) ChangeLog = 2;
 }
 //------------------------------------------------------------------------------------------------
 //\uFFFD\uFFFD\uFFFD\uFFFD:
 //\uFFFD\uFFFD\uFFFD: Skyline(2001.12.29)
 //\u02B5\uFFFD\uFFFD: Skyline
 //\uFFFD\u07B8\uFFFD:
 //------------------------------------------------------------------------------------------------
 public static JUnitInfo GetUnitInfoByHL(int HZB,int LZB,JReportModel RM) {
   return GetUnitInfoByHL(HZB,LZB,0,RM);
 }
 //------------------------------------------------------------------------------------------------
 //\uFFFD\uFFFD\uFFFD\uFFFD:
 //\uFFFD\uFFFD\uFFFD: Skyline(2001.12.29)
 //\u02B5\uFFFD\uFFFD: Skyline
 //\uFFFD\u07B8\uFFFD:
 //------------------------------------------------------------------------------------------------
 public static JUnitInfo GetUnitInfoByHL(int HZB,int LZB,int Op,JReportModel RM) {
   JUnitInfo UnitInfo=null;JRowInfo RowInfo;JColInfo ColInfo;
   RowInfo = JRowInfo.GetRowInfoByZB(HZB,Op,RM);
   if ( RowInfo == null ) return null;
   ColInfo = JColInfo.GetColInfoByZB(LZB,Op,RM);
   if ( ColInfo == null ) return null;
   UnitInfo = RowInfo.TzUnitRowHead;
   while ( UnitInfo != null ) {
     if ( UnitInfo.ColInfo.LZD_ZB == LZB ) return UnitInfo;
     UnitInfo = UnitInfo.RowNextUnit;
   }
   if ( Op == 1 ) {
     UnitInfo = CreateUI(RowInfo,ColInfo);
     RM.setModified();
   }
   return UnitInfo;
 }
 //------------------------------------------------------------------------------------------------
 //\uFFFD\uFFFD\uFFFD\uFFFD:
 //\uFFFD\uFFFD\uFFFD: Skyline(2001.12.29)
 //\u02B5\uFFFD\uFFFD: Skyline
 //\uFFFD\u07B8\uFFFD:
 //------------------------------------------------------------------------------------------------
 static JTzUnitInfo CreateUI(JRowInfo RowInfo,JColInfo ColInfo) {
   JTzUnitInfo UnitInfo = new JTzUnitInfo();
   UnitInfo.ChangeLog = 1; // \uFFFD\u00BD\uFFFD
   UnitInfo.RowInfo = RowInfo;
   UnitInfo.ColInfo = ColInfo;
   // \uFFFD\uFFFD\uFFFD\uFFFD\uFFFD\uFFFD\uFFFD§Ò\uFFFD
   UnitInfo.RowNextUnit = RowInfo.TzUnitRowHead;
   if ( RowInfo.TzUnitRowHead != null ) RowInfo.TzUnitRowHead.PrioUnit = UnitInfo;
   RowInfo.TzUnitRowHead = UnitInfo;
   // \uFFFD\uFFFD\uFFFD\uFFFD\uFFFD\uFFFD\uFFFD§Ò\uFFFD
   UnitInfo.ColNextUnit = ColInfo.AddUnitColHead;
   if ( ColInfo.AddUnitColHead != null ) ColInfo.AddUnitColHead.PrioUnit = UnitInfo;
     ColInfo.TzUnitColHead = UnitInfo;
   return UnitInfo;
 }
 //------------------------------------------------------------------------------------------------
 //\uFFFD\uFFFD\uFFFD\uFFFD:
 //\uFFFD\uFFFD\uFFFD: Skyline(2001.12.29)
 //\u02B5\uFFFD\uFFFD: Skyline
 //\uFFFD\u07B8\uFFFD:
 //------------------------------------------------------------------------------------------------
 public static JUnitInfo GetUnitInfoByOrde(String HOrde,String LOrde,int Op,JReportModel RM) {
   JUnitInfo UnitInfo=null;JRowInfo RowInfo;JColInfo ColInfo;
   RowInfo = JRowInfo.GetRowInfoByOrde(HOrde,RM);
   if ( RowInfo == null ) return null;
   ColInfo = JColInfo.GetColInfoByOrde(LOrde,RM);
   if ( ColInfo == null ) return null;
   UnitInfo = RowInfo.TzUnitRowHead;
   while ( UnitInfo != null ) {
     if ( UnitInfo.ColInfo.LZD_ORDE.equals(LOrde) ) return UnitInfo;
     UnitInfo = UnitInfo.RowNextUnit;
   }
   if ( Op == 1 ) {
     UnitInfo = CreateUI(RowInfo,ColInfo);
   }
   return UnitInfo;
 }

}
