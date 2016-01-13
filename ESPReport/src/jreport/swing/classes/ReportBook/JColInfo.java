package jreport.swing.classes.ReportBook;

import jreport.swing.classes.JReportModel;
import jreport.swing.classes.util.ReportUtil;
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
public class JColInfo {
static ResourceBundle res = ResourceBundle.getBundle("jreport.swing.classes.ReportBook.Language");
  public String LZD_ORDE="";
  public int LZD_ZB=0;
  public String BZBM_BM="";
  public String LZD_MC="";
  public boolean LZD_SZ=true;// 数值列
  public boolean LZD_HZ=true;// 汇总列
  public boolean LZD_TZ=true;// 调整列
  public boolean LZD_XM=false;// 项目列
  public boolean LZD_PX=false;// 排序列
  public int ChangeLog=0; // 修改标志(0:没有修改过，1:新建，2:修改过)
  public JColInfo PrioCol=null;         // 上一列
  public JColInfo NextCol=null;         // 下一列
  public JUnitInfo UnitColHead=null;     // 主表的单元
  public JAddUnitInfo AddUnitColHead=null;  // 可以是单位数据或是汇总数据
  public JTzUnitInfo TzUnitColHead=null;  // 可以是单位数据或是汇总数据的调整
  public JReportModel RM = null;
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  public JColInfo() {
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
  public String GetColName(JReportModel RM) {
    String ColText=null;
    ColText = LZD_MC;
    if ( ColText != null && ColText.trim().length() == 0 ) ColText = null;
    if ( ColText == null ) {
      try {
        ColText = RM.ReportView.getText(RM.BBZD_TITLE,LZD_ZB-1);
        LZD_MC = ColText;
        if ( LZD_MC == null || LZD_MC.trim().length() == 0 ) {
          ColText = ReportUtil.strID(LZD_ZB);
        }
        if ( LZD_MC != null && LZD_MC.trim().length() != 0 ) {
          setModified();
          RM.setModified();
        }
      }catch ( Exception e ) {

      }
    }
    return ColText;
  }
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  public String toString() {
    return GetColName(this.RM);
  }
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  public static JColInfo GetColInfoByZB(int ZB,JReportModel RM) {
    return GetColInfoByZB(ZB,0,RM);
  }
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  public static JColInfo GetColInfoByZB(int ZB,int Op,JReportModel RM) {
    JColInfo ColInfo=null;
    ColInfo = RM.HeadColInfo.NextCol;
    while ( ColInfo != null ) {
      if ( ColInfo.LZD_ZB == ZB ) return ColInfo;
      ColInfo = ColInfo.NextCol;
    }
    ColInfo = null;
    if ( Op == 1 ) { // 新建
      ColInfo = new JColInfo();
      /**
       * 如果是新建，并且是第一列，自动设置为项目列
       * add by hufeng 2006.8.21
       */
      if(ZB == 1){
        ColInfo.LZD_HZ = false;
        ColInfo.LZD_SZ = false;
        ColInfo.LZD_TZ = false;
        ColInfo.LZD_XM = true;
      }
      ColInfo.RM = RM;
      ColInfo.ChangeLog = 1; // 新建
      ColInfo.NextCol = RM.HeadColInfo.NextCol;
      if ( RM.HeadColInfo.NextCol != null ) RM.HeadColInfo.NextCol.PrioCol = ColInfo;
      RM.HeadColInfo.NextCol = ColInfo;
      ColInfo.PrioCol = RM.HeadColInfo;
      ColInfo.LZD_ZB = ZB;
      ColInfo.LZD_ORDE = String.valueOf(++RM.ColMaxOrde);
      RM.setModified();
    }
    return ColInfo;
  }
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  public static JColInfo GetColInfoByOrde(String Orde,JReportModel RM) {
    JColInfo ColInfo=null;
    ColInfo = RM.HeadColInfo.NextCol;
    while ( ColInfo != null ) {
      if ( ColInfo.LZD_ORDE.equals(Orde) == true ) return ColInfo;
      ColInfo = ColInfo.NextCol;
    }
    return null;
  }
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  public static void InsertCol(int SCol,int ECol,JReportModel RM) {
    JColInfo ColInfo = null;int C=Math.abs(SCol-ECol)+1;
    ColInfo = RM.HeadColInfo.NextCol;
    while ( ColInfo != null ) {
      if ( ColInfo.LZD_ZB >= SCol ) {
        ChangeColUnit(ColInfo);
        ColInfo.LZD_ZB += C;
        ColInfo.setModified();
      }
      ColInfo = ColInfo.NextCol;
    }
    for(int i=SCol;i<=ECol;i++)
      GetColInfoByZB(i,1,RM);
    return;
  }
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  static void ChangeColUnit(JColInfo ColInfo) {
    JUnitInfo UI;
    UI = ColInfo.UnitColHead;
    while ( UI != null ) {
      UI.DYZD_EXGSX = null;
      UI.setModified();
      UI = UI.ColNextUnit;
    }
  }
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  public static void DeleteCol(int SCol,int ECol,JReportModel RM) {
    JColInfo ColInfo = null,DelColInfo = null;int C=ECol-SCol+1;
    ColInfo = RM.HeadColInfo.NextCol;
    while ( ColInfo != null ) {
      if ( ColInfo.LZD_ZB >= SCol && ColInfo.LZD_ZB <= ECol ) {
        DelColInfo = ColInfo;
        ColInfo = ColInfo.NextCol;
        if ( DelColInfo.PrioCol != null ) {
          DelColInfo.PrioCol.NextCol = DelColInfo.NextCol;
        }
        if ( DelColInfo.NextCol != null ) {
          DelColInfo.NextCol.PrioCol = DelColInfo.PrioCol;
        }
        DelColInfo.NextCol = RM.DelHeadColInfo.NextCol;
        DelColInfo.PrioCol = RM.DelHeadColInfo;
        RM.DelHeadColInfo.NextCol = DelColInfo;
      } else {
        ColInfo = ColInfo.NextCol;
      }
    }
    ColInfo = RM.HeadColInfo.NextCol;
    while ( ColInfo != null ) {
      if ( ColInfo.LZD_ZB > ECol ) {
        ChangeColUnit(ColInfo);
        ColInfo.LZD_ZB -= C;
        ColInfo.setModified();
      }
      ColInfo = ColInfo.NextCol;
    }
    return;
  }
}
