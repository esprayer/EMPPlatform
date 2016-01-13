package jreport.swing.classes.ReportBook;

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
public class JBdlInfo {
static ResourceBundle res = ResourceBundle.getBundle("jreport.swing.classes.ReportBook.Language");
  public String BDL_ORDE="";
  public String BDL_MC="";
  public String BDL_KSORDE="";
  public int LZD_ZB = 0;
  public int BDL_NUM=0;
  public String BZBM_BM="";
  public int ChangeLog=0;
  public JColInfo ColInfo=null;
  public JBdlInfo Next=null;
  public JBdlInfo Prio=null;
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  public JBdlInfo() {
  }
  public String toString() {
    return BDL_MC;
  }

/*
    int             ID;
    AnsiString      MC;
    TColInfo        *ColInfo;
    int             Num;
    AnsiString      Bmlb;
    unsigned char   ChangeLog;
    struct strBDL   *Next;
    struct strBDL   *Prio;
*/
}