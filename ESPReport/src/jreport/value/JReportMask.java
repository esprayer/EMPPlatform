package jreport.value;

import java.util.*;

public class JReportMask {

    static ResourceBundle res = ResourceBundle.getBundle("jreport.value.Language");

    int mask;

    public static final int           OUT_BBGS = 0x01;   //格式
    public static final int           OUT_JSGS = 0x02;   //计算公式
    public static final int           OUT_BZBM = 0x04;   //标准编码
    public static final int           OUT_BBSJ = 0x08;   //报表数据
    public static final int           OUT_JYGS = 0x10;   //校验公式
    public static final int           OUT_XFBB = 0x20;   //下发报表
    public static final int           OUT_SRKZ = 0x40;   //输入控制
    public static final int           OUT_SHGS = 0x80;   //审核公式
    public static final int         OUT_FMSHGS = 0x100;  //封面审核公式
    public static final int           OUT_MJZD = 0x200;  //枚举字典
    public static final int         OUT_JSGSTZ = 0x400; //计算公式调整
    public static final int         OUT_JYGSTZ = 0x800; //校验公式调整

    public static final int            IN_BBGS = 0x01;   //报表格式
    public static final int            IN_BBYS = 0x02;   //报表样式
    public static final int            IN_JSGS = 0x04;   //计算公式
    public static final int            IN_JYGS = 0x08;   //校验公式
    public static final int            IN_SHGS = 0x10;   //审核公式
    public static final int          IN_FMSHGS = 0x20;   //封面审核公式
    public static final int            IN_TZGS = 0x30;   //调整公式
    public static final int            IN_BZBM = 0x1000; //标准编码
    public static final int            IN_MJZD = 0x2000; //枚举字典

    public static final int           DEL_BBGS = 0x01;   //删除格式
    public static final int           DEL_BBYS = 0x02;   //删除样式
    public static final int           DEL_JSGS = 0x04;   //删除计算公式
    public static final int           DEL_JYGS = 0x08;   //删除校验公式
    public static final int           DEL_DWSJ = 0x10;   //删除单位数据
    public static final int           DEL_HZSJ = 0x20;   //删除汇总数据
    public static final int           DEL_BMSJ = 0x30;   //删除部门数据
    public static final int           DEL_CBSJ = 0x40;   //删除成本数据
    public static final int           DEL_SHGS = 0x50;   //删除审核公式
    public static final int         DEL_FMSHGS = 0x80;   //删除封面审核公式
    public static final int           DEL_DWJS = 0x100;  //删除单位公式
    public static final int           DEL_DWJY = 0x200;  //删除单位校验公式
    public static final int           DEL_LBJS = 0x400;  //删除类别公式
    public static final int           DEL_LBJY = 0x800;  //删除类别校验公式
    public static final int           DEL_BMJS = 0x300;  //删除部门公式
    public static final int           DEL_BMJY = 0x600;  //删除部门校验公式
    public static final int           DEL_CBJS = 0x2000; //删除成本公式
    public static final int           DEL_CBJY = 0x4000; //删除成本校验公式
    public static final int           DEL_MJZD = 0x10000; //删除枚举字典(解决在删除报表时不删除枚举字典的问题)

    public static final int       DATAOUT_DATA = 0x01; //数据
    public static final int        DATAOUT_DOC = 0x02; //附加文档
    public static final int       DATAOUT_FMDM = 0x04; //封面代码

    public static final int         DATAIN_DOC = 0x02; //封面代码
    public static final int        DATAIN_FMDM = 0x04; //封面代码
    public static final int        ENCRPT_FILE = 0x10; //加密文件
    public static final int    NOT_ENCRPT_FILE = 0x20; //非加密文件

    public static final int         GSOUT_JSGS = 0x01; //计算公式
    public static final int         GSOUT_JYGS = 0x02; //校验公式   

    public static final int          GSIN_JSGS = 0x01; //计算公式
    public static final int          GSIN_JYGS = 0x02; //校验公式

    public static final int           DATA_OUT = 1; //数据转出
    public static final int             GS_OUT = 2; //公式转出

    public static final int          CTCX_LBJS = 0x01; //穿透类别计算公式
    public static final int          CTCX_DWJS = 0x02; //穿透单位计算公式
    public static final int          CTCX_BMJS = 0x04; //穿透部门计算公式
    public static final int          CTCX_CBJS = 0x08; //穿透成本计算公式
    public static final int          CTCX_LBJY = 0x10; //穿透类别校验公式
    public static final int          CTCX_DWJY = 0x20; //穿透单位校验公式
    public static final int          CTCX_BMJY = 0x40; //穿透部门校验公式
    public static final int          CTCX_CBJY = 0x80; //穿透成本校验公式

    public static final String      CHAR_QUOTA = "\'\'";   //字典串的单引号，要改为char(39),以便支持多数据库

    public static final String    GNQX_RPT_NEW = "RT1020"; // 新建报表格式
    public static final String GNQX_RPT_IMPROT = "RT1110"; // 报表格式转入

    public static final String    JY_TYPE_INNER  = "1";    // 表内校验
    public static final String    JY_TYPE_OUTER  = "2";    // 表间校验
    public static final String    JY_TYPE_ALL    = "3";    // 全部校验

    public static final String REPORT_CALC_ALL     = "1";    // 计算所有函数
    public static final String REPORT_CALC_COUNT   = "2";    // 计算汇总函数(宏函数+报表函数)
    public static final String REPORT_CALC_REPORT  = "3";    // 计算报表函数
    public static final String REPORT_CALC_MACRO   = "4";    // 计算宏函数
    public static final String REPORT_CALC_INNER   = "5";    // 计算报表类表内取数公式
	public static final String REPORT_CALC_ACCOUNT = "6";    // 计算账务函数

    public static final String RPT_CALC_SAP         = "RPT_CALC_SAP";     // 是否计算SAP函数
    public static final String RPT_CALC_TIME        = "RPT_CALC_TIME";    // 是否后台输出计算时间日志

    public static final String CALC_UNIT_LOG        = "CALC_UNIT_LOG";    // 是否需要记录关账记算日志
    public static final String RPT_CHECK_LOG        = "RPT_CHECK_LOG";    // 上报计算校验日志

    public static final String RPT_REMOTE_CALL       = "RPT_REMOTE_CALL";  // 远程调用

    public JReportMask() {
        mask = 0;
    }

    public JReportMask(int m) {
        mask = m;
    }

    public int getMask() {
        return mask;
    }

    public void restMask() {
    	mask = 0;
    }
    
    public boolean haveMask(int m) {
        return ( (mask & m) > 0 ? true : false);
    }

    public void setMask(int m) {
        mask |= m;
    }
}
