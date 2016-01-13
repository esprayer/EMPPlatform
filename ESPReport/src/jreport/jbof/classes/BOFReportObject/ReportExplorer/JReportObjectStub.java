package jreport.jbof.classes.BOFReportObject.ReportExplorer;

import java.util.*;
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
public class JReportObjectStub {
static ResourceBundle res = ResourceBundle.getBundle("jreport.jbof.classes.BOFReportObject.ReportExplorer.Language");
    public static final int STUB_TYPE_YEAR   = 0;
    public static final int STUB_TYPE_MONTH  = 1;
    public static final int STUB_TYPE_REPORT = 2;
    public static final int STUB_TYPE_ROOT   = 3;
    public static final int STUB_TYPE_LBZD   = 4;
    public static final int STUB_TYPE_DWZD   = 5;
    public static final int STUB_TYPE_BMZD   = 6;
    public static final int STUB_TYPE_CBZD   = 7;
    public static final int STUB_TYPE_GFBB   = 8;
    public String  IS_GFBB  = "0";
    public String  TZZD_ORDE  = null;
    public String  TZZD_MC  = null;
    public String  BBZD_BH    = null;
    public String  BBZD_MC    = null;
    public String  ADD_BH     = null;
    public String  ADD_MC     = null;
    public String  ADD_JC     = null;
    public String  BBZD_DATE  = null;
    public String  BBZD_YEAR  = null;
    public String  BBZD_MONTH = null;
    public String  BBZD_SBBH  = null;
    public String  BBZD_FCBZ  = null;  //封存标志
    public String  BBZD_XF    = null;  //下发标志
    public String  F_JS       = null;
    public String  F_MX       = null;
    public boolean F_BBFC     = false; // 报表封存
    public boolean F_GSFC     = false; // 公式封存
    public boolean F_LOCK     = false; // 解锁
    public boolean F_BBWC     = false; // 完成
    public boolean F_BBXF     = false; // 下发
    public String  F_ZGXM     = "";    // 完成人
    public String  F_WCRQ     = "";    // 完成日期
    public String  BBZD_SBXZ  = "";
    public String  F_BBXZ     = "";    // 上报性质
    public String  BBZD_LX    = "";
//  public boolean F_BBYJ     = false; // 月结
    public boolean F_BBSB     = false; //上报
    public boolean F_BBJY     = false; //校验
    public boolean F_NULL     = false; //空数据
    public Object  AddObject  = null;
    public int     STUB_TYPE  = STUB_TYPE_REPORT;
    public Vector  NextROS    = null;
    public JReportObjectStub  Parent;

    public void addNextStub(JReportObjectStub ros) {
        if (NextROS == null)
            NextROS = new Vector();
        NextROS.add(ros);
        ros.Parent = this;
    }

    public void setNextList(Vector v) {
        NextROS = v;
    }

    public Vector getNextList() {
        return NextROS;
    }

    //------------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.12.29)
    //实现: Skyline
    //修改:
    //------------------------------------------------------------------------------------------------
    public JReportObjectStub(int Type) {
        STUB_TYPE = Type;
    }

    public JReportObjectStub() {
    }

    //------------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.12.29)
    //实现: Skyline
    //修改: 财务集中，将12个月改为16个期间2007.1.16
    //------------------------------------------------------------------------------------------------
    public String toString() {
        switch (STUB_TYPE) {
            case STUB_TYPE_YEAR:
                return BBZD_YEAR + res.getString("String_6");
            case STUB_TYPE_MONTH:
                return BBZD_MONTH + res.getString("String_11");
            case STUB_TYPE_ROOT:
                return BBZD_MC;
            case STUB_TYPE_LBZD:
            case STUB_TYPE_DWZD:
            case STUB_TYPE_BMZD:
            case STUB_TYPE_CBZD:
                return ADD_BH + " " + ADD_MC;
            default:
                return BBZD_BH + "-" + BBZD_MC;
        }
    }
}
