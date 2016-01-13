package jreport.swing.classes.ReportBook;

import jreport.swing.classes.JReportModel;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */
//--------------------------------------------------------------------------------------------------
//描述:
//设计: Skyline(2001.04.22)
//实现: Skyline
//修改:
//--------------------------------------------------------------------------------------------------
public class JAddUnitInfo extends JUnitInfo{
//  public static JRowInfo     HeadRowInfo = null;
//  public static JColInfo     HeadColInfo = null;

//  public String DYZD_ORDE="";
//  public String DYZD_SJ="";
//  public double DYZD_DATA=0.00;
//  public int ChangeLog=0;     // 修改标志(0:没有修改过，1:新建，2:修改过)
//  public JAddUnitInfo PrioUnit=null;
//  public JAddUnitInfo NextUnit=null;
//  public JAddUnitInfo RowPrioUnit=null;  // 同一行的上一个单元
//  public JAddUnitInfo RowNextUnit=null;  // 同一行的下一个单元
//  public JAddUnitInfo ColPrioUnit=null;  // 同一列的上一个单元
//  public JAddUnitInfo ColNextUnit=null;  // 同一列的下一个单元
//  public JRowInfo RowInfo=null;      // 行的指针
//  public JColInfo ColInfo=null;      // 列的指针
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  public JAddUnitInfo() {
  }
/*
    long                  ID;
    AnsiString            cData;     // 字符型的单元数据
    double                nData;     // 数值型的单元数据
    unsigned char         ChangeLog; // 修改标志(0:没有修改过，1:新建，2:修改过)
    long                  lResver;   // 保留
    struct strAddUnitInfo *PrioUnit;
    struct strAddUnitInfo *NextUnit;
    struct strAddUnitInfo *RowPrioUnit;  // 同一行的上一个单元
    struct strAddUnitInfo *RowNextUnit;  // 同一行的下一个单元
    struct strAddUnitInfo *ColPrioUnit;  // 同一列的上一个单元
    struct strAddUnitInfo *ColNextUnit;  // 同一列的下一个单元
    void                  *RowInfo;   // 行的指针
    void                  *ColInfo;   // 列的指针
*/
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  public void setModified() {
    if ( ChangeLog != 1 ) ChangeLog = 2;
  }
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  public static JUnitInfo GetUnitInfoByHL(int HZB,int LZB,JReportModel RM) {
    return GetUnitInfoByHL(HZB,LZB,0,RM);
  }
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  public static JUnitInfo GetUnitInfoByHL(int HZB,int LZB,int Op,JReportModel RM) {
    JUnitInfo UnitInfo=null;JRowInfo RowInfo;JColInfo ColInfo;
    RowInfo = JRowInfo.GetRowInfoByZB(HZB,Op,RM);
    if ( RowInfo == null ) return null;
    ColInfo = JColInfo.GetColInfoByZB(LZB,Op,RM);
    if ( ColInfo == null ) return null;
    UnitInfo = RowInfo.AddUnitRowHead;
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
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  static JAddUnitInfo CreateUI(JRowInfo RowInfo,JColInfo ColInfo) {
    JAddUnitInfo UnitInfo = new JAddUnitInfo();
    UnitInfo.ChangeLog = 1; // 新建
    UnitInfo.RowInfo = RowInfo;
    UnitInfo.ColInfo = ColInfo;
    // 处理行列表
    UnitInfo.RowNextUnit = RowInfo.AddUnitRowHead;
    if ( RowInfo.AddUnitRowHead != null ) RowInfo.AddUnitRowHead.PrioUnit = UnitInfo;
    RowInfo.AddUnitRowHead = UnitInfo;
    // 行理列列表
    UnitInfo.ColNextUnit = ColInfo.AddUnitColHead;
    if ( ColInfo.AddUnitColHead != null ) ColInfo.AddUnitColHead.PrioUnit = UnitInfo;
      ColInfo.AddUnitColHead = UnitInfo;
    return UnitInfo;
  }
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  public static JUnitInfo GetUnitInfoByOrde(String HOrde,String LOrde,int Op,JReportModel RM) {
    JUnitInfo UnitInfo=null;JRowInfo RowInfo;JColInfo ColInfo;
    RowInfo = JRowInfo.GetRowInfoByOrde(HOrde,RM);
    if ( RowInfo == null ) return null;
    ColInfo = JColInfo.GetColInfoByOrde(LOrde,RM);
    if ( ColInfo == null ) return null;
    UnitInfo = RowInfo.AddUnitRowHead;
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
