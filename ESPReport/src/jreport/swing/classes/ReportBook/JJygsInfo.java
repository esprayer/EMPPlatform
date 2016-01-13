package jreport.swing.classes.ReportBook;

import jreport.swing.classes.*;
import java.util.ResourceBundle;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
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
public class JJygsInfo {
static ResourceBundle res = ResourceBundle.getBundle("jreport.swing.classes.ReportBook.Language");
  public static final String FROM_XTBB = "1"; //系统报表公式
  public static final String FROM_DWBB  = "2"; //单位报表公式
  //
  public String JYGS_ORDE = "";
  public String JYGS_GSX  = "";
  public String JYGS_EXGSX  = "";
  public String JYGS_BJ   = "=";
  public String JYGS_SHOW = "";// 存放审核信息
  public String JYGS_SYFW   = "";
  public int    JYGS_TYPE = 1;// 1逻辑审核、2合理审核、3逻辑＋差额、4合理＋差额
  public int    JYGS_STYLE= 0;//  标识此公式是否为审核公式：2为审核公式
  public int JYGS_HOFFSET = 0;
  public int JYGS_LOFFSET = 0;
  /**
   * 校验公式类别，１系统报表;
   * 用于区分当前校验信息是系统报表的还是单位报表的
   * add by hufeng 2005.11.17
   */
  public String JYGS_FROM   = ""; //
  // 用于变动区的校验公式专用属性区
//  public int BD_OFFSET = 0;
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  public JJygsInfo() {
  }
  public static JJygsInfo CreateJygsInfo(JReportModel RM) {
    JJygsInfo JygsInfo = new JJygsInfo();
    JygsInfo.JYGS_ORDE = String.valueOf(++RM.JygsMaxOrde);
    return JygsInfo;
  }
  public String toString() {
    return JYGS_BJ+JYGS_EXGSX;
  }
}
