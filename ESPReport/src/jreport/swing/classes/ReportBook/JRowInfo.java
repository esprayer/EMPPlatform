package jreport.swing.classes.ReportBook;

import jreport.swing.classes.*;
import java.util.ArrayList;
import java.util.ResourceBundle;

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
public class JRowInfo {
static ResourceBundle res = ResourceBundle.getBundle("jreport.swing.classes.ReportBook.Language");
  public String HZD_ORDE="";
  public int    HZD_ZB=0;
  public int    UP_BDHS=0;
  public String BZBM_BM="";
  public int    HZD_PRO = 0;// 是否是变动区的主行
  public int    ChangeLog=0;// 修改标志(0:没有修改过，1:新建，2:修改过)
  public JRowInfo      PrioRow=null;
  public JRowInfo      NextRow=null;
  public JUnitInfo     UnitRowHead=null;
  public JAddUnitInfo  AddUnitRowHead=null;
  public JTzUnitInfo  TzUnitRowHead=null;
  // 用数组保存变动行数据
  private java.util.ArrayList BdhDataList = null;// 如果此行定义了变动行，则保存变动行数据
  private ArrayList BackBdhDataList = null;
  // 用来保存变动行定义
  public JBdhInfo      BdhInfo = null; // 变动行信息的定义

  public ArrayList getBdhDataList() {
    return BdhDataList;
  }
  public void setNewBDH() {
    HZD_PRO = 1;
    BdhDataList = new ArrayList();
    setModified();
  }
  public void backBDH() {
    BackBdhDataList = BdhDataList;
    BdhDataList = null;
  }
  public void restoreBDH() {
    BdhDataList = BackBdhDataList;
    BackBdhDataList = null;
  }
  public void delNewBDH() {
    HZD_PRO = 0;
    BackBdhDataList = null;
    BdhDataList = null;
    setModified();
  }
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  public JRowInfo() {
  }
  public void setModified() {
    if ( ChangeLog != 1 ) ChangeLog = 2;
  }
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  public static JRowInfo GetRowInfoByZB(int ZB,JReportModel RM) {
    return GetRowInfoByZB(ZB,0,RM);
  }
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  public static JRowInfo GetRowInfoByZB(int ZB,int Op,JReportModel RM) {
    JRowInfo RowInfo=null;
    RowInfo = RM.HeadRowInfo.NextRow;
    while ( RowInfo != null ) {
      if ( RowInfo.HZD_ZB == ZB ) return RowInfo;
      RowInfo = RowInfo.NextRow;
    }
    RowInfo = null;
    if ( Op == 1 ) { // 新建
      RowInfo = new JRowInfo();
      RowInfo.ChangeLog = 1; // 新建
      RowInfo.NextRow = RM.HeadRowInfo.NextRow;
      if ( RM.HeadRowInfo.NextRow != null ) RM.HeadRowInfo.NextRow.PrioRow = RowInfo;
      RM.HeadRowInfo.NextRow = RowInfo;
      RowInfo.PrioRow = RM.HeadRowInfo;
      RowInfo.HZD_ZB = ZB;
      RowInfo.HZD_ORDE = String.valueOf(++RM.RowMaxOrde);
      RM.setModified();
    }
    return RowInfo;
  }
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  public static JRowInfo GetRowInfoByOrde(String Orde,JReportModel RM) {
    JRowInfo RowInfo=null;
    RowInfo = RM.HeadRowInfo.NextRow;
    while ( RowInfo != null ) {
      if ( RowInfo.HZD_ORDE.equals(Orde) == true ) return RowInfo;
      RowInfo = RowInfo.NextRow;
    }
    return null;
  }
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  public static void InsertRow(int SRow,int ERow,JReportModel RM) {
    JRowInfo RowInfo = null;int R=ERow-SRow+1;
    RowInfo = RM.HeadRowInfo.NextRow;
    while ( RowInfo != null ) {
      if ( RowInfo.HZD_ZB >= SRow ) {
        ChangeRowUnit(RowInfo);
        RowInfo.HZD_ZB += R;
        RowInfo.setModified();
      }
      RowInfo = RowInfo.NextRow;
    }
    for(int i=SRow;i<=ERow;i++)
      GetRowInfoByZB(i,1,RM);
    return;
  }
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  static void ChangeRowUnit(JRowInfo RowInfo) {
    JUnitInfo UI;
    UI = RowInfo.UnitRowHead;
    while ( UI != null ) {
      UI.DYZD_EXGSX = null;
      UI.setModified();
      UI = UI.RowNextUnit;
    }
  }
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  public static void DeleteRow(int SRow,int ERow,JReportModel RM) {
    JRowInfo RowInfo = null,DelRowInfo = null;int R=ERow-SRow+1;
    RowInfo = RM.HeadRowInfo.NextRow;
    while ( RowInfo != null ) {
      if ( RowInfo.HZD_ZB >= SRow && RowInfo.HZD_ZB <= ERow ) {
        DelRowInfo = RowInfo;
        RowInfo = RowInfo.NextRow;
        if ( DelRowInfo.PrioRow != null ) {
          DelRowInfo.PrioRow.NextRow = DelRowInfo.NextRow;
        }
        if ( DelRowInfo.NextRow != null ) {
          DelRowInfo.NextRow.PrioRow = DelRowInfo.PrioRow;
        }
        DelRowInfo.NextRow = RM.DelHeadRowInfo.NextRow;
        DelRowInfo.PrioRow = RM.DelHeadRowInfo;
        RM.DelHeadRowInfo.NextRow = DelRowInfo;
      } else {
        RowInfo = RowInfo.NextRow;
      }
    }
    RowInfo = RM.HeadRowInfo.NextRow;
    while ( RowInfo != null ) {
      if ( RowInfo.HZD_ZB > ERow ) {
        ChangeRowUnit(RowInfo);
        RowInfo.HZD_ZB -= R;
        RowInfo.setModified();
      }
      RowInfo = RowInfo.NextRow;
    }
    return;
  }
}
