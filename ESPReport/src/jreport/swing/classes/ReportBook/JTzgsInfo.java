package jreport.swing.classes.ReportBook;

import java.util.ResourceBundle;
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
public class JTzgsInfo {
  static ResourceBundle res = ResourceBundle.getBundle("jreport.swing.classes.ReportBook.Language");
  public static final String FROM_XTBB = "1"; //系统报表公式
  //
  public String TZGS_BBBH = "";
  public String TZGS_GSX  = "";
  public String TZGS_EXGSX  = "";
  public String TZGS_DATE  = ""; //报表期间
  public String TZGS_SHOW = "";// 存放审核信息
  public int TZGS_HZD = 0;//行坐标
  public int TZGS_LZD = 0;//列坐标
  /**
   * 校验公式类别，１系统报表;
   * 用于区分当前校验信息是系统报表的还是单位报表的
   * add by hufeng 2005.11.17
   */
  public String TZGS_FROM   = ""; //
  // 用于变动区的校验公式专用属性区
//  public int BD_OFFSET = 0;
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: mengfei(2009.11.16)
  //实现: mengfei
  //修改:
  //------------------------------------------------------------------------------------------------

  public JTzgsInfo() {
  }
  public static JTzgsInfo CreateTzgsInfo(JReportModel RM) {
   JTzgsInfo TzgsInfo = new JTzgsInfo();
   TzgsInfo.TZGS_BBBH = String.valueOf(++RM.TzgsMaxOrde);
   return TzgsInfo;
 }
 public String toString() {
   return TZGS_GSX+TZGS_EXGSX;
 }

}
