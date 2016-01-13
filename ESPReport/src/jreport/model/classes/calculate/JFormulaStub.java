package jreport.model.classes.calculate;

import java.io.*;
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
public class JFormulaStub
    implements Serializable {
static ResourceBundle res = ResourceBundle.getBundle("jreport.model.classes.calculate.Language");

    public static final int JO_DDY  = 0;
    public static final int JO_BDY  = 1;
    public static final int JO_DY   = 2;
    public static final int JO_DYDY = 3;
    public static final int JO_XY   = 4;
    public static final int JO_XYDY = 5;

    public String BBZD_BH      = null;
    public String BBZD_DATE    = null;
    public String ADD_BH       = null;
    public int    ADD_TYPE     = 0;
    public String BDH_ZZORDE   = null;
    public String BDH_ORDE     = null;
    public String HZD_ORDE     = null;
    public String LZD_ORDE     = null;
    public String JYGS_BJ      = null; // 用于校验公式
    public String JYGS_ORDE    = null; // 用于校验公式
    public String JYGS_SYFW    = null; // 用于校验公式
    public int    JYGS_VAL     = 0;    //用于存放审核公式的比较值
    public int    HZD_ZB       = 0;
    public int    LZD_ZB       = 0;
    public int    DYZD_HOFFSET = 0;
    public int    DYZD_LOFFSET = 0;
    public String DYZD_GSX     = null;
    public int    CAFlag       = 0;    // 0:是还没有计算过,1:正在嵌套检查2:正在计算3:计算完毕 4:处理批量从ECC取数
    public int    NAFlag       = 0;    // 0:是还没有嵌套检查,1:正在嵌套检查

	public String GROUP_BY     = "";   //公共分组对象
	public String ORDER_BY     = "";   //公共排序对象
	public int    TOP_N        = 0;    //取多少行，例如在建工程取前10大 0表示取全部
	public String RANGE_CON    = "";   //公共取数范围条件
	public String ZB_DIMID       = "";
	public String ZB_YEAR       = "";
	public String ZB_LBBH       = "";
	public String ZB_BH       = "";
	public String ZB_GROUP      = "";
	public String ZB_ZRZX      = "";
	public String ZB_TYPE      = "";
	public String ZB_SJZD      = "";
	public String ZB_SRCCONT   = "";
	public String DW_KJ   = "1";
	public int    iLastRes =0;
	public boolean bLL = false;



    //为变动区计算增加的 2004.5.20 fsz
    public static final int CHAR_TYPE   = 1;
    public static final int NUMBER_TYPE = 0;
	public static final int MACRO_TYPE = 2;

    public static final int NO_BDQ      = 0;
    public static final int H_BDQ       = 1;
    public static final int L_BDQ       = 2;
    public static final int H_BDQ_NEW   = 3; // 新版本行变动
    public int              BDQ_TYPE    = 0;
    public int              ResCount    = 0;
    public int              BD_OFFSET   = 0; // add by xSkyline 2004.12.02 存放新版变动区的OFFSET
    public int              ResultType  = NUMBER_TYPE;
	public boolean          innerMacro  = false;
	public boolean          bPoint      = true;
	public boolean          bCtCal      = false;

    /**
     *
     * @return boolean
     */
    public boolean isPoint() {
        /**
         * 如果在公式是定义在变动行的话，则默认为块公式,在计算时会用临时表的方式处理
         * modified by hufeng 2005.11.9
         */
        if (BDH_ORDE != null && BDH_ORDE.length() > 0) {
            return false;
        }
        return DYZD_HOFFSET == 0 && DYZD_LOFFSET == 0;
    }

    //end
    //------------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.12.29)
    //实现: Skyline
    //修改:
    //------------------------------------------------------------------------------------------------
    public int GetJygsBj() {
        if (JYGS_BJ == null)return JO_DDY;
        if (JYGS_BJ.equals("="))return JO_DDY;
        if (JYGS_BJ.equals("<>"))return JO_BDY;
        if (JYGS_BJ.equals(">"))return JO_DY;
        if (JYGS_BJ.equals(">="))return JO_DYDY;
        if (JYGS_BJ.equals("<"))return JO_XY;
        if (JYGS_BJ.equals("<="))return JO_XYDY;
        return JO_DDY;
    }

    //------------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.12.29)
    //实现: Skyline
    //修改:
    //------------------------------------------------------------------------------------------------
    public JFormulaStub() {

    }

    public JFormulaStub cloneFS() {
        JFormulaStub fs = new JFormulaStub();
        fs.ADD_BH = ADD_BH;
        fs.ADD_TYPE = ADD_TYPE;
        fs.BBZD_BH = BBZD_BH;
        fs.BBZD_DATE = BBZD_DATE;
        fs.BD_OFFSET = BD_OFFSET;
        fs.BDQ_TYPE = BDQ_TYPE;
        fs.CAFlag = CAFlag;
        fs.DYZD_GSX = DYZD_GSX;
        fs.DYZD_HOFFSET = DYZD_HOFFSET;
        fs.DYZD_LOFFSET = DYZD_LOFFSET;
        fs.HZD_ORDE = HZD_ORDE;
        fs.HZD_ZB = HZD_ZB;
        fs.LZD_ORDE = LZD_ORDE;
        fs.LZD_ZB = LZD_ZB;
        fs.JYGS_BJ = JYGS_BJ;
        fs.JYGS_ORDE = JYGS_ORDE;
        fs.JYGS_SYFW = JYGS_SYFW;
        fs.ResCount = ResCount;
        return fs;
    }
}
