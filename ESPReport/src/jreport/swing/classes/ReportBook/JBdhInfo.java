package jreport.swing.classes.ReportBook;

import java.util.*;

import java.awt.*;

import org.jdom.*;
import com.f1j.ss.*;
import jreport.foundation.classes.*;
import jreport.swing.classes.*;
import java.math.BigDecimal;

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
public class JBdhInfo {

    static ResourceBundle res = ResourceBundle.getBundle("jreport.swing.classes.ReportBook.Language");
    public static java.awt.Color LIGHT_GREEN = new java.awt.Color(128, 255, 128);
    public String BDH_ORDE = "";
    public String BDH_MC = "";
    public String BDH_KSORDE = "";
    public String BDH_DYL = "";
    public int HZD_ZB = 0;
    public int BDH_NUM = 0;
    public int BDH_ISNEW = 0; // 是否是新变动区 1:是，0:不是
    public String BZBM_BM = "";
    public JColInfo DyColInfo = null;
    public JRowInfo RowInfo = null;
    public int ChangeLog = 0;
    public int BDH_MAX = 0; // 旧版本中的有数据的最大行数
    public JBdhInfo Next = null;
    public JBdhInfo Prio = null;
    public JReportModel ReportModel;
    private Vector DelBDList = null;
    public String getBZBM_BM() {
        String Res = "";
        if (BZBM_BM != null) Res = BZBM_BM;
        return Res;
    }

    //------------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.12.29)
    //实现: Skyline
    //修改:
    //------------------------------------------------------------------------------------------------
    public JBdhInfo(JReportModel RM) {
        ReportModel = RM;
    }

    public void incBdhNum(int inc) {
        BDH_NUM += inc;
        setModified();
    }

    public void decBdhNum(int dec) {
        BDH_NUM -= dec;
        setModified();
    }

    public void incNewBdh(int index, int count) {
        if (RowInfo.getBdhDataList() == null) {
            RowInfo.setNewBDH();
        }
        JBDHDataInfo BDData = null;
        for (int i = 0; i < count; i++) {
            BDData = JBDHDataInfo.createBDDataInfo(ReportModel, this);
            RowInfo.getBdhDataList().add(index, BDData);
        }
    }

    public void decNewBdh(int index, int count) {
        if (count <= 0)return;
        if (RowInfo.getBdhDataList() == null) {
            RowInfo.setNewBDH();
        }
        JBDHDataInfo BDData = null;
        createDelBDList();
        for (int i = 0; i < count; i++) {
            BDData = (JBDHDataInfo) RowInfo.getBdhDataList().remove(index);
            if (BDData.ChangeLog != 1) // 如果不是新建的，就需要记录
                DelBDList.add(BDData);
        }
    }

    private Vector createDelBDList() {
        if (DelBDList == null) DelBDList = new Vector();
        return DelBDList;
    }

    public Vector getDelBDList() {
        return DelBDList;
    }

    public void setModified() {
        if (ChangeLog != 1) ChangeLog = 2;
    }

    public String toString() {
        return BDH_MC;
    }

    /**
     * 根据读取上来的XML数据形成JBdhInfo数组
     * @param RM JReportModel
     * @param RDO JReportDataObject
     */
    public static void GetReportBdhAttrib(JReportModel RM, JReportDataObject RDO) {
        Element BdhsElement;
        java.util.List nodelist;
        Element node;
        int i = 0;
        JBdhInfo BdhInfo = null;
        Element Bdh;
        int MaxID = 0, ID = 0;
        if (RM.BdhInfoList != null)
            RM.BdhInfoList.removeAllElements();
        if (RM.DelBdhInfoList != null)
            RM.DelBdhInfoList.removeAllElements();

        BdhsElement = RDO.getBdhsElement();
        if (BdhsElement == null) {
            return;
        }
        nodelist = RDO.BeginEnumerate(BdhsElement);
        String Name, BMLB;
        int Row, DyCol;
        while (nodelist != null) {
            node = RDO.Enumerate(nodelist, i++);
            if (node == null) {
                break;
            }
            BdhInfo = new JBdhInfo(RM);
            Bdh = (Element) node;
            Name = Bdh.getAttribute(BdhsElement.getAttribute("BDH_MC").getValue()).
                getValue();
            // 变动行名称
            BdhInfo.BDH_MC = Name;
            BMLB = Bdh.getAttribute(BdhsElement.getAttribute("BZBM_BM").getValue()).
                getValue();
            // 获取开始行序号
            BdhInfo.BDH_KSORDE = Bdh.getAttribute(BdhsElement.getAttribute("BDH_KSORDE").getValue()).getValue().trim();
            // 标准编码类别
            BdhInfo.BZBM_BM = BMLB;
            Row = JRowInfo.GetRowInfoByOrde(BdhInfo.BDH_KSORDE, RM).HZD_ZB;

            DyCol = JColInfo.GetColInfoByOrde(Bdh.getAttribute(BdhsElement.
                getAttribute("BDH_DYL").getValue()).getValue(), RM).LZD_ZB;
            // 项目对应列
            BdhInfo.DyColInfo = JColInfo.GetColInfoByZB(DyCol, 1, RM);
            // 开始行
            BdhInfo.RowInfo = JRowInfo.GetRowInfoByZB(Row, 1, RM);
            // 设置
            BdhInfo.RowInfo.BdhInfo = BdhInfo;
            try {
                // 变动行数
                int num = Bdh.getAttribute(BdhsElement.getAttribute("BDH_NUM").getValue()).getIntValue();
                BdhInfo.BDH_NUM = num;
                // 变动区的版本 默认为旧版本
                String text = BdhsElement.getAttribute("BDH_ISNEW").getValue();
                String text1 = Bdh.getAttribute(text).getValue();
                if (text1 == null || "".equals(text1.trim())) text1 = "0";
                BdhInfo.BDH_ISNEW = Integer.valueOf(text1).intValue(); //Bdh.getAttribute(text).getIntValue();
                // 当前变动区有数据的最大行数，不允许大于变动行数
                text = BdhsElement.getAttribute("BDH_MAX").getValue();
                text1 = Bdh.getAttribute(text).getValue();
                if (text1 == null || "".equals(text1.trim())) text1 = "0";
                int hzb = Integer.valueOf(text1).intValue(); //Bdh.getAttribute(BdhsElement.getAttribute("BDH_MAX").getValue()).getIntValue();
                BdhInfo.BDH_MAX = hzb;
                BdhInfo.BDH_ORDE = Bdh.getAttribute(BdhsElement.getAttribute("BDH_ORDE").getValue()).getValue();
                // 如果是新的变动方式
                if (BdhInfo.BDH_ISNEW == 1) {
                    // 处理变动区数据，计算公式，校验公式
                    processBDQData(BdhInfo, RM, RDO, Bdh);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            BdhInfo.ChangeLog = 0; // 修改标志为0

            ID = Integer.valueOf(BdhInfo.BDH_ORDE).intValue();
            if (ID > MaxID) {
                MaxID = ID;
            }
            RM.BdhInfoList.add(BdhInfo);
        }
        RM.BdhMaxOrde = ++MaxID;
        RDO.EndEnumerate();
    }

    /**
     * 根据读取上来的XML数据形成JBdhInfo数组
     * @param RM JReportModel
     * @param RDO JReportDataObject
     */
    public static void GetReportBdhTzAttrib(JReportModel RM, JReportDataObject RDO) {
        Element BdhsElement;
        java.util.List nodelist;
        Element node;
        int i = 0;
        JBdhInfo BdhInfo = null;
        Element Bdh;
        int MaxID = 0, ID = 0;
        if (RM.BdhInfoList != null)
            RM.BdhInfoList.removeAllElements();
        if (RM.DelBdhInfoList != null)
            RM.DelBdhInfoList.removeAllElements();

        BdhsElement = RDO.getBdhsElement();
        if (BdhsElement == null) {
            return;
        }
        nodelist = RDO.BeginEnumerate(BdhsElement);
        String Name, BMLB;
        int Row, DyCol;
        while (nodelist != null) {
            node = RDO.Enumerate(nodelist, i++);
            if (node == null) {
                break;
            }
            BdhInfo = new JBdhInfo(RM);
            Bdh = (Element) node;
            Name = Bdh.getAttribute(BdhsElement.getAttribute("BDH_MC").getValue()).
                getValue();
            // 变动行名称
            BdhInfo.BDH_MC = Name;
            BMLB = Bdh.getAttribute(BdhsElement.getAttribute("BZBM_BM").getValue()).
                getValue();
            // 获取开始行序号
            BdhInfo.BDH_KSORDE = Bdh.getAttribute(BdhsElement.getAttribute("BDH_KSORDE").getValue()).getValue().trim();
            // 标准编码类别
            BdhInfo.BZBM_BM = BMLB;
            Row = JRowInfo.GetRowInfoByOrde(BdhInfo.BDH_KSORDE, RM).HZD_ZB;

            DyCol = JColInfo.GetColInfoByOrde(Bdh.getAttribute(BdhsElement.
                getAttribute("BDH_DYL").getValue()).getValue(), RM).LZD_ZB;
            // 项目对应列
            BdhInfo.DyColInfo = JColInfo.GetColInfoByZB(DyCol, 1, RM);
            // 开始行
            BdhInfo.RowInfo = JRowInfo.GetRowInfoByZB(Row, 1, RM);
            // 设置
            BdhInfo.RowInfo.BdhInfo = BdhInfo;
            try {
                // 变动行数
                int num = Bdh.getAttribute(BdhsElement.getAttribute("BDH_NUM").getValue()).getIntValue();
                BdhInfo.BDH_NUM = num;
                // 变动区的版本 默认为旧版本
                String text = BdhsElement.getAttribute("BDH_ISNEW").getValue();
                String text1 = Bdh.getAttribute(text).getValue();
                if (text1 == null || "".equals(text1.trim())) text1 = "0";
                BdhInfo.BDH_ISNEW = Integer.valueOf(text1).intValue(); //Bdh.getAttribute(text).getIntValue();
                // 当前变动区有数据的最大行数，不允许大于变动行数
                text = BdhsElement.getAttribute("BDH_MAX").getValue();
                text1 = Bdh.getAttribute(text).getValue();
                if (text1 == null || "".equals(text1.trim())) text1 = "0";
                int hzb = Integer.valueOf(text1).intValue(); //Bdh.getAttribute(BdhsElement.getAttribute("BDH_MAX").getValue()).getIntValue();
                BdhInfo.BDH_MAX = hzb;
                BdhInfo.BDH_ORDE = Bdh.getAttribute(BdhsElement.getAttribute("BDH_ORDE").getValue()).getValue();
                // 如果是新的变动方式
                if (BdhInfo.BDH_ISNEW == 1) {
                    // 处理变动区数据，计算公式，校验公式
                    processBDQData(BdhInfo, RM, RDO, Bdh);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            BdhInfo.ChangeLog = 0; // 修改标志为0

            ID = Integer.valueOf(BdhInfo.BDH_ORDE).intValue();
            if (ID > MaxID) {
                MaxID = ID;
            }
            RM.BdhInfoList.add(BdhInfo);
        }
        RM.BdhMaxOrde = ++MaxID;
        RDO.EndEnumerate();
    }

    static void processBDQData(JBdhInfo BdhInfo, JReportModel RM, JReportDataObject RDO, Element Bdh) {
        if (BdhInfo.RowInfo == null)return;
        // 初始化存储变动区的数组
        BdhInfo.RowInfo.setNewBDH();
        // 数组的最大值存放在BDH中的BDH_MAX变量中
        // １．处理变动单元数据
        processBdqDyzd(BdhInfo, RM, RDO, Bdh);
        // ２．处理变动计算公式
        processBdqJsgs(BdhInfo, RM, RDO, Bdh);
        // ３．处理变动校验公式
        processBdqJygs(BdhInfo, RM, RDO, Bdh);
        // ３．处理变动校验公式
        processBdqZsgs(BdhInfo, RM, RDO, Bdh);
        // ４．处理变动附加单元数据
        processBdqAddDyzd(BdhInfo, RM, RDO, Bdh);
        // ５．处理变动附加计算公式
        processBdqAddJsgs(BdhInfo, RM, RDO, Bdh);
        // ６．处理变动附加校验公式
        processBdqAddJygs(BdhInfo, RM, RDO, Bdh);
        //7. 处理变动调整附加单元数据
        processBdqTzAddDyzd(BdhInfo, RM, RDO, Bdh);
        //8. 处理变动附加折算公式
        processBdqAddZsgs(BdhInfo, RM, RDO, Bdh);
    }

    /**
     * 统一的处理数据的过程
     * @param BdhInfo JBdhInfo
     * @param RDO JReportDataObject
     * @param Bdh Element
     * @param EName String
     */
    static void processData(JReportModel RM, JBdhInfo BdhInfo, JReportDataObject RDO, Element Bdh, String EName) {
        // 获取存放变动单元数据的Element
        Element BDDyzd = RDO.GetElementByName(Bdh, EName);
        Element BData = null;
        if (BDDyzd == null)return; // 如果为空，则直接返回，没有这样的数据
        java.util.List nodelist = null;
        int Index = 0;
        JBDHDataInfo BDHDataInfo = null;
        nodelist = RDO.BeginEnumerate(BDDyzd);
        java.util.List AList = BDDyzd.getAttributes();
        Attribute attr = null;
        String AName, AValue, Value;
        while (nodelist != null && AList != null) {
            // 将某一行变动区数据放入变动列表中
            // 获取数据所在的 Element
            BData = RDO.Enumerate(nodelist, Index);
            if (BData == null)break;

            BDHDataInfo = null;
            if (Index < BdhInfo.RowInfo.getBdhDataList().size())
                BDHDataInfo = (JBDHDataInfo) BdhInfo.RowInfo.getBdhDataList().get(Index);
            if (BDHDataInfo == null) {
                BDHDataInfo = JBDHDataInfo.createBDDataInfo(RM, BdhInfo);
                BDHDataInfo.ChangeLog = 0;
                BdhInfo.RowInfo.getBdhDataList().add(Index, BDHDataInfo);
            }
            String TextColName;
            String NumberColName;
            String Tmp;
            String CText = null;
            String NText = null;

            TextColName = RDO.GetElementValue(BDDyzd, "BD_OFFSET");
            NText = RDO.GetElementValue(BData, TextColName, "0");
            BDHDataInfo.BD_OFFSET = Integer.parseInt(NText);
            TextColName = RDO.GetElementValue(BDDyzd, "BDSJ_HBM");
            CText = RDO.GetElementValue(BData, TextColName, "0");
            BDHDataInfo.BDSJ_HBM = CText;
            JUnitInfo UI = null;
            for (int Col = 1; Col <= RM.BBZD_LS; Col++) {
                // 确定属性的位置
                if (Col < 10) Tmp = "0" + String.valueOf(Col);
                else Tmp = String.valueOf(Col);
                // 确定文本属性名称
                TextColName = "BDSJ_TEXT" + Tmp;
                TextColName = RDO.GetElementValue(BDDyzd, TextColName);
                CText = RDO.GetElementValue(BData, TextColName, null);
//                // 确定数值属性名称
//                NumberColName = "BDSJ_NUM" + Tmp;
//                NumberColName = RDO.GetElementValue(BDDyzd, NumberColName);
//                NText = RDO.GetElementValue(BData, NumberColName, null);
                /**
                 * 数据值列改为从字符列转换取得
                 * modified by hufeng 2008.5.6
                 */
                try{
                    Double.parseDouble(CText);
                    NText = CText;
                }catch(Exception ee){
                    NText = "0";
                }
                if("BDTZAddDyzd".equals(EName)){
                  JUnitInfo SUI = null;
                  int hzb,lzb;
                  hzb = BdhInfo.RowInfo.HZD_ZB;
                  lzb = Col;
                  SUI = JUnitInfo.GetUnitInfoByHL(hzb,lzb,1,RM);
                  if(SUI != null){
                    if(SUI.DYZD_TZGS !=null && SUI.DYZD_TZGS.trim().length()>0){
                      UI = BDHDataInfo.setAdjustDataObject(Col - 1, CText, NText);
                      UI.ChangeLog = 0;
                    }
                  }

                }else{
                  UI = BDHDataInfo.setDataObject(Col - 1, CText, NText);
                  UI.ChangeLog = 0;
                }

            }
            Index++;
        }
        RDO.EndEnumerate();
    }

    // １．处理变动单元数据
    static void processBdqDyzd(JBdhInfo BdhInfo, JReportModel RM, JReportDataObject RDO, Element Bdh) {
        processData(RM, BdhInfo, RDO, Bdh, "BDDyzd");
    }

    /**
     *
     * @param BdhInfo JBdhInfo
     * @param RM JReportModel
     * @param RDO JReportDataObject
     * @param Bdh Element
     * @param EName String
     */
    static void processFormat(JBdhInfo BdhInfo, JReportModel RM, JReportDataObject RDO, Element Bdh, String EName, String Ext) {
        // 获取存放变动格式的Element
        Element BDDyzd = RDO.GetElementByName(Bdh, EName);
        Element BData = null;
        String LZD_ORDE = null;
        int col = 0;
        String BD_OFFSET = null;
        if (BDDyzd == null)return; // 如果为空，则直接返回，没有这样的数据
        java.util.List nodelist = null;
        int Index = 0, Kndex = 0;
        JBDHDataInfo BDHDataInfo = null;
        nodelist = RDO.BeginEnumerate(BDDyzd);
        java.util.List AList = BDDyzd.getAttributes();
        Attribute attr = null;
        String AName, AValue, Value;
        String aLZD = RDO.GetElementValue(BDDyzd, "LZD_ORDE", null);
        String aOFF = RDO.GetElementValue(BDDyzd, "BD_OFFSET", "0");
        if (aLZD == null || aOFF == null)return;
        while (nodelist != null && AList != null) {
            //　获取元素
            BData = RDO.Enumerate(nodelist, Index++);
            if (BData == null)break;
            BDHDataInfo = null;
            // 获取偏移
            BD_OFFSET = RDO.GetElementValue(BData, aOFF, "1");
            // 获取索引
            Kndex = Integer.valueOf(BD_OFFSET).intValue() - 1;
            if (Kndex < BdhInfo.RowInfo.getBdhDataList().size())
                BDHDataInfo = (JBDHDataInfo) BdhInfo.RowInfo.getBdhDataList().get(Kndex);
            if (BDHDataInfo == null) {
                BDHDataInfo = JBDHDataInfo.createBDDataInfo(RM, BdhInfo);
                BDHDataInfo.ChangeLog = 0;
                /**
                 * 有可能在单位报表中存在在一些垃级数据
                 * 比如当前变动行中，最大为１号，但有个单位公式的位置在５行
                 * 那么执行下面语句时，就要报错，所以要强制检查
                 * modified by hufeng 2006.1.4
                 */
                try {
                    BdhInfo.RowInfo.getBdhDataList().add(Kndex, BDHDataInfo);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            // 将某一行变动区数据放入变动列表中
            // 获取数据所在的 Element

            // 需要确定在哪一列上，根据LZD_ORDE进行
            LZD_ORDE = RDO.GetElementValue(BData, aLZD, null);
            JColInfo ColInfo = JColInfo.GetColInfoByOrde(LZD_ORDE, RM);
            if (ColInfo == null)break;
            col = ColInfo.LZD_ZB;
            JBDUnitInfo BDUnit = BDHDataInfo.getBDUnitInfo(col - 1);

            if ("JS".equals(Ext)) {
                setJSGSBDUnitInfo(BDUnit, RDO, BDDyzd, BData, false);
            }
            if ("JY".equals(Ext)) {
                setJYGSBDUnitInfo(RM, BDUnit, RDO, BDDyzd, BData, false);
            }
            if ("ZS".equals(Ext)) {
                setZSGSBDUnitInfo(BDUnit, RDO, BDDyzd, BData, false);
            }
            
            if ("AddJS".equals(Ext)) {
                setJSGSBDUnitInfo(BDUnit, RDO, BDDyzd, BData, true);
            }
            if ("AddJY".equals(Ext)) {
                setJYGSBDUnitInfo(RM, BDUnit, RDO, BDDyzd, BData, true);
            }
            if ("AddZS".equals(Ext)) {
                setZSGSBDUnitInfo(BDUnit, RDO, BDDyzd, BData, false);
            }

            BDUnit.ChangeLog = 0;
            BDUnit.ChangeLogJs = 0;
            BDUnit.ChangeLogJy = 0; // 清空修改标志
            BDUnit.ChangeLogZs = 0; // 清空修改标志
        }
        RDO.EndEnumerate();
    }

    static void setJYGSBDUnitInfo(JReportModel RM, JBDUnitInfo BDUnit, JReportDataObject RDO, Element EBdhs, Element EBdh, boolean isAdd) {
        //JYGS_ORDE="c1" LZD_ORDE="c2" BD_OFFSET="c3" DYZD_HOFFSET="c4" DYZD_LOFFSET="c5" JYGS_EXP="c6" JYGS_BJ="c7"
        //JYGS_TYPE="c8" JYGS_STYLE="c9" JYGS_MESS="c10" JYGS_SHJG="c11" JYGS_SHOW="c12"
        String ColName, ColAias, Value, gsx = "";
//    ColName = "JYGS_EXP";
//    ColAias = RDO.GetElementValue(EBdhs,ColName);
//    Value   = RDO.GetElementValue(EBdh,ColAias,null);
//    if ( Value != null && Value.trim().length() == 0 ) Value = null;
//    // 如果公式为空，则直接退出
//    if ( Value == null ) return;
        /**
         * 变动行中的公式项长度大于255时，就会出错，是因为在后台取数时
         * 用了GSX+GSX1...这样的表达式，而SYBASE最大的字符型字段长度只有255
         * 所以导致长公式不能正常取出，现在分开取，在前台组合成完整的公式串
         * modified by hufeng 2005.10.11
         */
        ColName = "JYGS_EXP";
        ColAias = RDO.GetElementValue(EBdhs, ColName);
        Value = RDO.GetElementValue(EBdh, ColAias, null);
        // 如果公式为空，则直接退出
        if (Value == null || Value.trim().length() == 0) {
            return;
        }
        gsx += Value;
        for (int i = 1; i <= 5; i++) {
            ColName = "JYGS_EXP" + i;
            ColAias = RDO.GetElementValue(EBdhs, ColName);
            Value = RDO.GetElementValue(EBdh, ColAias, null);
            if (Value != null) {
                gsx += Value;
            }
        }
        //创建校验公式列表
        JJygsInfo JygsInfo = new JJygsInfo();
        /**
         * 给校验公式来源赋值
         * modified by hufeng 2005.11.21
         */
        if (isAdd) {
            JygsInfo.JYGS_FROM = JJygsInfo.FROM_DWBB; //单位报表
        } else {
            JygsInfo.JYGS_FROM = JJygsInfo.FROM_XTBB; //系统报表
        }
        JygsInfo.JYGS_GSX = gsx;

        /**
         * 设置较验公式ORDE,并更新最大ORDER
         * add by hufeng 2005.11.24
         */
        ColName = "JYGS_ORDE";
        ColAias = RDO.GetElementValue(EBdhs, ColName);
        Value = RDO.GetElementValue(EBdh, ColAias, null);
        if (Value != null && Value.trim().length() == 0) {
            Value = "0";
        }
        int nMax = Integer.parseInt(Value);
        if (nMax > RM.JygsMaxOrde) {
            RM.JygsMaxOrde = nMax;
        }
        JygsInfo.JYGS_ORDE = Value;

        ColName = "JYGS_BJ";
        ColAias = RDO.GetElementValue(EBdhs, ColName);
        Value = RDO.GetElementValue(EBdh, ColAias, null);
        if (Value != null && Value.trim().length() == 0) Value = null;
        JygsInfo.JYGS_BJ = Value;

        ColName = "DYZD_HOFFSET";
        ColAias = RDO.GetElementValue(EBdhs, ColName);
        Value = RDO.GetElementValue(EBdh, ColAias, "0");
        JygsInfo.JYGS_HOFFSET = Integer.parseInt(Value);

        ColName = "DYZD_LOFFSET";
        ColAias = RDO.GetElementValue(EBdhs, ColName);
        Value = RDO.GetElementValue(EBdh, ColAias, "0");
        JygsInfo.JYGS_LOFFSET = Integer.parseInt(Value);

        BDUnit.createJygsList().add(JygsInfo);

        ColName = "JYGS_STYLE";
        ColAias = RDO.GetElementValue(EBdhs, ColName);
        Value = RDO.GetElementValue(EBdh, ColAias, "0");
        try {
            JygsInfo.JYGS_STYLE = Integer.parseInt(Value);
        } catch (Exception e) {
            JygsInfo.JYGS_STYLE = 0;
        }

        ColName = "JYGS_TYPE";
        ColAias = RDO.GetElementValue(EBdhs, ColName);
        Value = RDO.GetElementValue(EBdh, ColAias, "0");
        try {
            JygsInfo.JYGS_TYPE = Integer.parseInt(Value);
        } catch (Exception e) {
            JygsInfo.JYGS_TYPE = 0;
        }

//    ColName = "JYGS_SHOW";
//    ColAias = RDO.GetElementValue(EBdhs,ColName);
//    Value   = RDO.GetElementValue(EBdh,ColAias,null);
//    if ( Value != null && Value.trim().length() == 0 ) Value = null;
//    JygsInfo.JYGS_SHOW=Value;
        //
        gsx = "";
        ColName = "JYGS_SHOW";
        ColAias = RDO.GetElementValue(EBdhs, ColName);
        Value = RDO.GetElementValue(EBdh, ColAias, null);
        if (Value == null || Value.trim().length() == 0) {
            Value = "";
        }
        gsx += Value;
        for (int i = 1; i <= 3; i++) {
            ColName = "JYGS_SHOW" + i;
            ColAias = RDO.GetElementValue(EBdhs, ColName);
            Value = RDO.GetElementValue(EBdh, ColAias, null);
            if (Value != null) {
                gsx += Value;
            }
        }
        JygsInfo.JYGS_SHOW = gsx;
    }

    static void setJSGSBDUnitInfo(JBDUnitInfo BDUnit, JReportDataObject RDO, Element EBdhs, Element EBdh, boolean isAdd) {
        // LZD_ORDE="c1" BD_OFFSET="c2" DYZD_HOFFSET="c3" DYZD_LOFFSET="c4" DYZD_GSX="c5" DYZD_GSBZ="c6" DYZD_GSJB="c7
        String ColName, ColAias, Value, gsx = "";

        /**
         * 变动行中的公式项长度大于255时，就会出错，是因为在后台取数时
         * 用了GSX+GSX1...这样的表达式，而SYBASE最大的字符型字段长度只有255
         * 所以导致长公式不能正常取出，现在分开取，在前台组合成完整的公式串
         * modified by hufeng 2005.10.11
         */
        ColName = "DYZD_GSX";
        ColAias = RDO.GetElementValue(EBdhs, ColName);
        Value = RDO.GetElementValue(EBdh, ColAias, null);
        /**
         * 如果是报表公式，为空时退出
         * 如果是单位公式，为空时表示删除了原报表格式中的公式
         * modified by hufeng 2005.12.14
         */
        if (Value == null) {
            Value = "";
        }
        if (!isAdd && Value.trim().length() == 0) {
            return;
        }
        gsx += Value;
        for (int i = 1; i <= 5; i++) {
            ColName = "DYZD_GSX" + i;
            ColAias = RDO.GetElementValue(EBdhs, ColName);
            Value = RDO.GetElementValue(EBdh, ColAias, null);
            if (Value != null) {
                gsx += Value;
            }
        }
        BDUnit.DYZD_GSX = gsx;
        //
        ColName = "DYZD_HOFFSET";
        ColAias = RDO.GetElementValue(EBdhs, ColName);
        Value = RDO.GetElementValue(EBdh, ColAias, "0");
        BDUnit.DYZD_HOFFSET = Integer.parseInt(Value);

        ColName = "DYZD_LOFFSET";
        ColAias = RDO.GetElementValue(EBdhs, ColName);
        Value = RDO.GetElementValue(EBdh, ColAias, "0");
        BDUnit.DYZD_LOFFSET = Integer.parseInt(Value);

//    ColName = "DYZD_GSBZ";
//    ColAias = RDO.GetElementValue(EBdhs,ColName);
//    Value   = RDO.GetElementValue(EBdh,ColAias,null);
//    if ( Value != null && Value.trim().length() == 0 ) Value = null;
//    BDUnit.DYZD_GSBZ=Value;

//    ColName = "DYZD_GSJB";
//    ColAias = RDO.GetElementValue(EBdhs,ColName);
//    Value   = RDO.GetElementValue(EBdh,ColAias,"0");
//    BDUnit.DYZD_LOFFSET =Integer.parseInt(Value);
//    BDUnit.DYZD_GSJB=Integer.parseInt(Value);
    }
    static void setZSGSBDUnitInfo(JBDUnitInfo BDUnit, JReportDataObject RDO, Element EBdhs, Element EBdh, boolean isAdd) {
        // LZD_ORDE="c1" BD_OFFSET="c2" DYZD_HOFFSET="c3" DYZD_LOFFSET="c4" DYZD_GSX="c5" DYZD_GSBZ="c6" DYZD_GSJB="c7
        String ColName, ColAias, Value, gsx = "";

        ColName = "DYZD_GSX";
        ColAias = RDO.GetElementValue(EBdhs, ColName);
        Value = RDO.GetElementValue(EBdh, ColAias, null);
        if (Value == null) {
            Value = "";
        }
        if (!isAdd && Value.trim().length() == 0) {
            return;
        }
        gsx += Value;
        for (int i = 1; i <= 5; i++) {
            ColName = "DYZD_GSX" + i;
            ColAias = RDO.GetElementValue(EBdhs, ColName);
            Value = RDO.GetElementValue(EBdh, ColAias, null);
            if (Value != null) {
                gsx += Value;
            }
        }
        BDUnit.DYZD_ZSGS = gsx;
        //
        ColName = "DYZD_HOFFSET";
        ColAias = RDO.GetElementValue(EBdhs, ColName);
        Value = RDO.GetElementValue(EBdh, ColAias, "0");
        BDUnit.DYZD_ZSHOFFSET = Integer.parseInt(Value);

        ColName = "DYZD_LOFFSET";
        ColAias = RDO.GetElementValue(EBdhs, ColName);
        Value = RDO.GetElementValue(EBdh, ColAias, "0");
        BDUnit.DYZD_ZSLOFFSET = Integer.parseInt(Value);
    }
    // ２．处理变动计算公式
    static void processBdqJsgs(JBdhInfo BdhInfo, JReportModel RM, JReportDataObject RDO, Element Bdh) {
        processFormat(BdhInfo, RM, RDO, Bdh, "BDJsgs", "JS");
    }

    // ３．处理变动校验公式
    static void processBdqJygs(JBdhInfo BdhInfo, JReportModel RM, JReportDataObject RDO, Element Bdh) {
        processFormat(BdhInfo, RM, RDO, Bdh, "BDJygs", "JY");
    }

    // ４．处理变动附加单元数据
    static void processBdqAddDyzd(JBdhInfo BdhInfo, JReportModel RM, JReportDataObject RDO, Element Bdh) {
        processData(RM, BdhInfo, RDO, Bdh, "BDAddDyzd");
    }

    // ５．处理变动附加计算公式
    static void processBdqAddJsgs(JBdhInfo BdhInfo, JReportModel RM, JReportDataObject RDO, Element Bdh) {
        processFormat(BdhInfo, RM, RDO, Bdh, "BDAddJsgs", "AddJS");
    }

    // ６．处理变动附加校验公式
    static void processBdqAddJygs(JBdhInfo BdhInfo, JReportModel RM, JReportDataObject RDO, Element Bdh) {
        processFormat(BdhInfo, RM, RDO, Bdh, "BDAddJygs", "AddJY");
    }

    // 7．处理变动附加单元调整数据
    static void processBdqTzAddDyzd(JBdhInfo BdhInfo, JReportModel RM, JReportDataObject RDO, Element Bdh) {
      processData(RM, BdhInfo, RDO, Bdh, "BDTZAddDyzd");
    }
    // 8．处理变动折算公式
    static void processBdqZsgs(JBdhInfo BdhInfo, JReportModel RM, JReportDataObject RDO, Element Bdh) {
        processFormat(BdhInfo, RM, RDO, Bdh, "BDZsgs", "ZS");
    }
    static void processBdqAddZsgs(JBdhInfo BdhInfo, JReportModel RM, JReportDataObject RDO, Element Bdh) {
        processFormat(BdhInfo, RM, RDO, Bdh, "BDAddZsgs", "AddZS");
    }


    public static boolean hasNewBdh(JReportModel RM) {
        if (RM.BdhInfoList == null || RM.BdhInfoList.size() == 0)return false;
        JBdhInfo BdhInfo = null;
        int Count = RM.BdhInfoList.size();
        int Index = 0;
        for (Index = 0; Index < Count; Index++) {
            BdhInfo = (JBdhInfo) RM.BdhInfoList.get(Index);
            if (BdhInfo.BDH_ISNEW == 1)return true;
        }
        return false;
    }

    public static void deleBdhs(JReportModel RM) {
        if (RM.BdhInfoList == null || RM.BdhInfoList.size() == 0)return;
        JBdhInfo BdhInfo = null;
        int Count = RM.BdhInfoList.size();
        int Index = 0;
        boolean hasnew = false;
        for (Index = 0; Index < Count; Index++) {
            BdhInfo = (JBdhInfo) RM.BdhInfoList.get(Index);
            if (deleBdh(RM, BdhInfo)) {
                hasnew = true;
                // 备份变动区
                BdhInfo.RowInfo.backBDH();
            }
        }
        if (hasnew) {
            setRowHeadTitle(RM);
        }
        for (Index = 0; Index < Count; Index++) {
            BdhInfo = (JBdhInfo) RM.BdhInfoList.get(Index);
            if (BdhInfo.BDH_ISNEW == 1) {
                // 恢复变动区
                BdhInfo.RowInfo.restoreBDH();
            }
        }
    }

    public static boolean deleBdh(JReportModel RM, JBdhInfo BdhInfo) {
        boolean hasnew = false;
        if (BdhInfo.BDH_ISNEW == 0) {
            // 旧版本的变动行方式
            deleOldBdh(RM, BdhInfo);
        }
        if (BdhInfo.BDH_ISNEW == 1) {
            // 新版本的变动行方式
            deleNewBdh(RM, BdhInfo);
            hasnew = true;
        }
        return hasnew;
    }

    public static void deleOldBdh(JReportModel RM, JBdhInfo BdhInfo) {
        try {
            // 设置显示级别
            int SRow = BdhInfo.RowInfo.HZD_ZB - 1;
            int ERow = SRow + BdhInfo.BDH_NUM;
            RM.ReportView.setRowOutlineLevel(SRow, ERow, 0, false);
            int SCol = 0;
            int ECol = RM.BBZD_LS - 1;
            setBDQColor(RM.ReportView, SRow, SCol, ERow, ECol, Color.WHITE, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void deleNewBdh(JReportModel RM, JBdhInfo BdhInfo) {
        // 如果没有变动数据，直接返回
        if (BdhInfo == null || BdhInfo.RowInfo.getBdhDataList() == null ||
            BdhInfo.RowInfo.getBdhDataList().size() == 0)return;
        // 获取变动行数
        int BDCount = BdhInfo.RowInfo.getBdhDataList().size();
//		boolean bProtected = RM.ReportView.isEnableProtection();
		boolean bProtected = !RM.ReportView.isAllowInCellEditing();
        try {
            int SRow = BdhInfo.RowInfo.HZD_ZB;
            // 获取物理行
            SRow = JBdhUtils.getPhyRow(SRow - 1, RM);
            int ERow = SRow;
            // 需要将所有的大于当前变动行主行的行坐标值加上当前的变动行数
            // 需要加上此行上部的变动行数
            ERow = SRow + BDCount - 1;
            // 设置树型分级结构
            RM.ReportView.setPhyRowOutlineLevel(SRow + 1, ERow + 1, 0, false);

//      RM.ReportView.setActiveCell(SRow+1,0);
            RM.ReportView.setPhySelection(SRow + 1, 0, SRow + 1 + BDCount - 1, 0);
            // 删除所有的变动行数
//      for(int i=0;i<BDCount;i++) {
			if ( bProtected ) {
				RM.ReportView.setAllowInCellEditing(true);
//				RM.ReportView.setEnableProtection(false);
			}
            RM.ReportView.editDelete(RM.ReportView.eShiftRows);
//      }
            // 设置新插入的变动行的行标题
//      for(int i=0;i<BDCount;i++) {
//        RM.ReportView.setRowText(SRow+i,"-"+String.valueOf(i+1));
//      }
            // 设置变动区的Color
//      int SCol = 0;int ECol = RM.BBZD_LS-1;
//      setBDQColor(RM.ReportView,SRow,SCol,ERow,ECol,Color.LIGHT_GRAY,true);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
			if ( bProtected ) { //如果原来是保护的，恢复现场
				//RM.ReportView.setEnableProtection(true);
				RM.ReportView.setAllowInCellEditing(false);
			}
		}

    }

    public static void viewBdhs(JReportModel RM) {
        viewBdhs(RM, true);
        /**
         * 设置最大行和最大列
         */
        try {
            RM.ReportView.setMaxRow(RM.getReportMaxRow() - 1);
            RM.ReportView.setMaxCol(RM.BBZD_LS - 1);
        } catch (Exception e) {}
    }

    public static void viewBdhs(JReportModel RM, boolean viewdata) {
        // 如果一张表没有定义任何的变动区域，直接返回
        boolean hasnewbdh = false;
        if (RM.BdhInfoList == null || RM.BdhInfoList.size() == 0)return;
        JBdhInfo BdhInfo = null;
        int Count = RM.BdhInfoList.size();
        int Index = 0;
        for (Index = 0; Index < Count; Index++) {
            BdhInfo = (JBdhInfo) RM.BdhInfoList.get(Index);
            if (viewBdh(RM, BdhInfo, viewdata)) // 如果此表定义了
                hasnewbdh = true;
        }
        // 如果此表定义新版本的变动行，需要对行的标题进行修改
        if (hasnewbdh) {
            setRowHeadTitle(RM);
        }
    }

    public static void setRowHeadTitle(JReportModel RM) {
        setRowHeadTitle(RM, false);
    }

    public static void setRowHeadTitle(JReportModel RM, boolean show) {
        if (!hasNewBdh(RM) && !show)return;
        int SRow = 1;
        int ERow = RM.BBZD_HS;
        JRowInfo RowInfo = null;
        int UP_BDHS = 0;
        String Text;
        int Row;
        int phyRow = 0;
        try {
            for (Row = SRow; Row <= ERow; Row++) {
                phyRow = JBdhUtils.getPhyRow(Row - 1, RM);
                Text = String.valueOf(Row);
                RM.ReportView.setRowText(phyRow, Text);
                RowInfo = JRowInfo.GetRowInfoByZB(Row, RM);
                // 如果该行定义了新的变动行
                if (RowInfo != null && RowInfo.getBdhDataList() != null) {
                    Text = "+" + String.valueOf(Row);
                    RM.ReportView.setRowText(phyRow, Text);
                }
                /**
                 * 输出坐标
                 * modified by hufeng 2006.1.10
                 */
//        System.out.println(phyRow + ":" + Text);
//        System.out.println("HZD_ZB==="+Text);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//  public static boolean viewBdh(JReportModel RM,JBdhInfo BdhInfo) {
//    return viewBdh(RM,BdhInfo,true);
//  }
    public static boolean viewBdh(JReportModel RM, JBdhInfo BdhInfo, boolean viewdata) {
        boolean hasnewbdh = false;
        if (BdhInfo.BDH_ISNEW == 0) {
            // 旧版本的变动行方式
            viewOldBdh(RM, BdhInfo);
        }
        if (BdhInfo.BDH_ISNEW == 1) {
            // 新版本的变动行方式
            viewNewBdh(RM, BdhInfo, viewdata);
            hasnewbdh = true;
        }
        return hasnewbdh;
    }

    /**
     * 旧版本的变动行方式
     * @param RM JReportModel
     * @param BdhInfo JBdhInfo
     */
    public static void viewOldBdh(JReportModel RM, JBdhInfo BdhInfo) {
        try {
            // 设置显示级别
            int SRow = BdhInfo.RowInfo.HZD_ZB - 1;
            int ERow = SRow + BdhInfo.BDH_NUM;
            RM.ReportView.setRowOutlineLevel(SRow, ERow, 0, false);
            RM.ReportView.setRowOutlineLevel(SRow, ERow, 1, false);
            int SCol = 0;
            int ECol = RM.BBZD_LS - 1;
            setBDQColor(RM.ReportView, SRow, SCol, ERow, ECol, Color.LIGHT_GRAY, false);
            /**
             * 处理老变动行的展示问题
             * 把主变动行的边框给完整显示出来
             * modified by hufeng 2006.3.13
             */

            RM.ReportView.setPhySelection(SRow - 1, 0, SRow - 1, RM.BBZD_LS - 1);
            CellFormat CF = RM.ReportView.getCellFormat();
            CF.setTopBorder(CF.eBorderThin);
            CF.setBottomBorder(CF.eBorderThin);
            CF.setPatternFG(CF.getPatternFG());

            RM.ReportView.setCellFormat(CF);
            RM.ReportView.setPhySelection(0, 0, 0, 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 新版本的变动行方式
     * @param RM JReportModel
     * @param BdhInfo JBdhInfo
     */
    public static void viewNewBdh(JReportModel RM, JBdhInfo BdhInfo) {
        viewNewBdh(RM, BdhInfo, true);
    }

    public static void viewNewBdh(JReportModel RM, JBdhInfo BdhInfo, boolean viewdata) {
        if (BdhInfo == null)return;
        try {
            // 1.插入新变动区的行数
            insertNewBdh(RM, BdhInfo);
            // 在刚刚插入的变动区中，填制数据
            if (viewdata)
                viewNewData(RM, BdhInfo);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param RM JReportModel
     * @param BdhInfo JBdhInfo
     */
    public static void viewNewData(JReportModel RM, JBdhInfo BdhInfo) {
        // 如果没有变动数据，直接返回
        if (BdhInfo == null || BdhInfo.RowInfo.getBdhDataList() == null ||
            BdhInfo.RowInfo.getBdhDataList().size() == 0)return;

        int Index = 0;
        int Count = BdhInfo.RowInfo.getBdhDataList().size();
        JBDHDataInfo BDHDataInfo = null;
        int Row = 0, Col = 0;
        int ColCount = RM.BBZD_LS;
        // 将所有的变动数据显示
        String Text;
        double Number;
        // 确定变动行的开始位置
        Row = JBdhUtils.getPhyRow(BdhInfo.RowInfo.HZD_ZB - 1, RM); // BdhInfo.RowInfo.HZD_ZB + BdhInfo.RowInfo.UP_BDHS;
        JUnitInfo UI = null;
        for (Index = 0; Index < Count; Index++) {
            // 取变动数据定义
            BDHDataInfo = (JBDHDataInfo) BdhInfo.RowInfo.getBdhDataList().get(Index);
            // 对每一列进行设置
            for (Col = 0; Col < ColCount; Col++) {
                // 取出文本属性值
                UI = BDHDataInfo.getUnitInfo(Col);
                if(RM.TZZD_BH != null && RM.TZZD_BH.trim().length()>0){
                  JUnitInfo SUI = null;
                  int hzb,lzb;
                  hzb = BdhInfo.RowInfo.HZD_ZB;
                  lzb = Col;
                  SUI = JUnitInfo.GetUnitInfoByHL(hzb,lzb+1,1,RM);
                  if(SUI != null){
                    if(SUI.DYZD_TZGS !=null && SUI.DYZD_TZGS.trim().length()>0){
                      if (UI != null) {
                        if(UI.TZZD_SJ !=null&&UI.TZZD_DATA!=0.00){
                          Text = UI.TZZD_SJ;
                          Number = UI.TZZD_DATA;
                        }else{
                          Text = UI.DYZD_SJ;
                          Number = UI.DYZD_DATA;
                        }
                      } else {
                        Text = "";
                        Number = 0.00;
                      }
                    }else{
                      if (UI != null) {
                        Text = UI.DYZD_SJ;
                        Number = UI.DYZD_DATA;
                      } else {
                        Text = "";
                        Number = 0.00;
                      }

                    }
                  }else{
                    if (UI != null) {
                      Text = UI.DYZD_SJ;
                      Number = UI.DYZD_DATA;
                    } else {
                      Text = "";
                      Number = 0.00;
                    }
                  }

                }else{
                  if (UI != null) {
                    Text = UI.DYZD_SJ;
                    Number = UI.DYZD_DATA;
                  } else {
                    Text = "";
                    Number = 0.00;
                  }

                }

                // 设置具体的值
                setCellContent(RM, Row + Index + 1, Col, Text, Number);
            }
        }
    }

    static public void getLogCellContent(JReportModel RM, int row, int col, JUnitInfo UI) {
        double d = 0.00;
        try {
            UI.DYZD_SJ = RM.ReportView.getText(row, col);
            try {
                d = Double.parseDouble(UI.DYZD_SJ);
                UI.DYZD_DATA = RM.ReportView.getNumber(row, col);
            } catch (Exception e) {
                UI.DYZD_DATA = 0;
            }
        } catch (Exception e) {
        }
    }

    static public void getLogTzCellContent(JReportModel RM, int row, int col, JUnitInfo UI) {
    double d = 0.00;
    try {
        UI.TZZD_SJ = RM.ReportView.getText(row, col);
        try {
            d = Double.parseDouble(UI.DYZD_SJ);
            UI.TZZD_DATA = RM.ReportView.getNumber(row, col);
        } catch (Exception e) {
            UI.TZZD_DATA = 0;
        }
    } catch (Exception e) {
    }
}


    static public void getPhyCellContent(JReportModel RM, int row, int col, JUnitInfo UI) {
        double d = 0.00;
        try {
            UI.DYZD_SJ = RM.ReportView.getPhyText(row, col);
            try {
                d = Double.parseDouble(UI.DYZD_SJ);
                UI.DYZD_DATA = RM.ReportView.getPhyNumber(row, col);
            } catch (Exception e) {
                UI.DYZD_DATA = 0;
            }
        } catch (Exception e) {
        }
    }

    static public void getPhyTzCellContent(JReportModel RM, int row, int col, JUnitInfo UI) {
    double d = 0.00;
    try {
        UI.TZZD_SJ = RM.ReportView.getPhyText(row, col);
        try {
            d = Double.parseDouble(UI.TZZD_SJ);
            UI.TZZD_DATA = RM.ReportView.getPhyNumber(row, col);
        } catch (Exception e) {
            UI.TZZD_DATA = 0;
        }
    } catch (Exception e) {
    }
}


    /**
     * 设置单元格内容，不考虑变动行
     * @param RM JReportModel
     * @param row int
     * @param col int
     * @param Text String
     * @param CNumber String
     */
    static public void setCellContent(JReportModel RM, int row, int col, String Text, double number) {
        try {
            double dd = 0.00;
            // 首先将字符转换成数值，如果成功，说明是数据型的
            if (Text != null && !"".equals(Text.trim()))
                dd = Double.parseDouble(Text);
            // 设置为数值型
            if (number > -0.000001 && number < 0.000001) number = dd;
            /**
             * 加入对单元格格式的判断，如果当前单元格是字符型的，则设置字符列
             * 如003001，数值为3001，如果不判断的话，显示的将为3001
             * modified by hufeng 2005.11.23
             */
            String format = RM.ReportView.getCellFormat(row, col, row, col).getValueFormat();
            if (format.equals("@")) {
                RM.ReportView.setPhyText(row, col, Text);
            } else {
                RM.ReportView.setPhyNumber(row, col, number);
            }
//      RM.ReportView.setPhyNumber(row,col,number);
        } catch (Exception e) {
            try {
                /**
                 * 如果文本为（<计算>，<校验>,<同左>,<同上>）则不显示
                 * add by hufeng 2005.11.8
                 */
                if (Text == null || Text.equals(res.getString("String_179")) || Text.equals(res.getString("String_180")) || Text.equals(res.getString("String_181")) ||
                    Text.equals(res.getString("String_182"))) {
                    Text = "";
                }
                // 设置文本型
                RM.ReportView.setPhyText(row, col, Text);
            } catch (Exception ee) {

            }
        }
    }

    /**
     * 设置单元格内容，考虑新变动行
     * @param RM JReportModel
     * @param row int
     * @param col int
     * @param Text String
     * @param number double
     */
    static public void setLogCellContent(JReportModel RM, int row, int col, String Text, double number) {
        try {
            double dd = 0.00;
            // 首先将字符转换成数值，如果成功，说明是数据型的
            if (Text != null && !"".equals(Text.trim()))
                dd = Double.parseDouble(Text);
            // 设置为数值型
            if (number > -0.000001 && number < 0.000001) number = dd;
            /**
             * 加入对单元格格式的判断，如果当前单元格是字符型的，则设置字符列
             * 如003001，数值为3001，如果不判断的话，显示的将为3001
             * modified by hufeng 2005.11.23
             */
            String format = RM.ReportView.getCellFormat(row, col, row, col).getValueFormat();
            if (format.equals("@")) {
                RM.ReportView.setText(row, col, Text);
            } else {
                RM.ReportView.setNumber(row, col, number);
            }
        } catch (Exception e) {
            try {
                /**
                 * 如果文本为（<计算>，<校验>,<同左>,<同上>）则不显示
                 * add by hufeng 2005.11.8
                 */
                if (Text == null || Text.equals(res.getString("String_185")) || Text.equals(res.getString("String_186")) || Text.equals(res.getString("String_187")) ||
                    Text.equals(res.getString("String_188"))) {
                    Text = "";
                }
                // 设置文本型
                RM.ReportView.setText(row, col, Text);
            } catch (Exception ee) {

            }
        }
    }



  /**
* 设置单元格内容，考虑新变动行
* @param RM JReportModel
* @param row int
* @param col int
* @param Text String
* @param number double
*/
static public void setAdjustCellContent(JReportModel RM, int row, int col, String Text, double number) {
  try {
      double dd = 0.00;
      // 首先将字符转换成数值，如果成功，说明是数据型的
      if (Text != null && !"".equals(Text.trim()))
          dd = Double.parseDouble(Text);
      // 设置为数值型
      if (number > -0.000001 && number < 0.000001) number = dd;
      /**
       * 加入对单元格格式的判断，如果当前单元格是字符型的，则设置字符列
       * 如003001，数值为3001，如果不判断的话，显示的将为3001
       * modified by hufeng 2005.11.23
       */
      String format = RM.ReportView.getCellFormat(row, col, row, col).getValueFormat();
      if (format.equals("@")) {
          RM.ReportView.setEntry(row, col, Text);
      } else {
          RM.ReportView.setNumber(row, col, number);
      }
  } catch (Exception e) {
      try {
          /**
           * 如果文本为（<计算>，<校验>,<同左>,<同上>）则不显示
           * add by hufeng 2005.11.8
           */
          if (Text == null || Text.equals(res.getString("String_185")) || Text.equals(res.getString("String_186")) || Text.equals(res.getString("String_187")) ||
              Text.equals(res.getString("String_188"))) {
              Text = "";
          }
          // 设置文本型
          RM.ReportView.setEntry(row, col, Text);
      } catch (Exception ee) {

      }
  }
}

/**
* 设置单元格内容，考虑新变动行
* @param RM JReportModel
* @param row int
* @param col int
* @param Text String
* @param number double
*/
static public void setAdjustCell(JReportModel RM, int row, int col, String Text, double number,JUnitInfo UI) {
 try {
     double dd = 0.00;
     BigDecimal b1 = new BigDecimal(Double.toString(UI.DYZD_DATA));
     BigDecimal b2 = new BigDecimal(RM.TZZD_XS);
     if(RM.TZZD_FH.equals("*")){
       dd = b1.multiply(b2).doubleValue();
       }else if(RM.TZZD_FH.equals("/")){
         dd = b1.divide(b2,Integer.valueOf(RM.TZZD_JD),BigDecimal.ROUND_HALF_EVEN).doubleValue();
      }
      Text = Double.toString(dd);
     // 首先将字符转换成数值，如果成功，说明是数据型的
//     if (Text != null && !"".equals(Text.trim()))
//         dd = Double.parseDouble(Text);
     // 设置为数值型
     if (number > -0.000001 && number < 0.000001) number = dd;
     /**
      * 加入对单元格格式的判断，如果当前单元格是字符型的，则设置字符列
      * 如003001，数值为3001，如果不判断的话，显示的将为3001
      * modified by hufeng 2005.11.23
      */
     String format = RM.ReportView.getCellFormat(row, col, row, col).getValueFormat();
     if (format.equals("@")) {
         RM.ReportView.setEntry(row, col, Text);
         RM.ReportView.setText(row, col, Text);
     } else {
         RM.ReportView.setNumber(row, col, dd);
     }
//     UI.DYZD_SJ = Text;
//     UI.DYZD_DATA = dd;
     UI.TZZD_SJ = Text;
     UI.TZZD_DATA = dd;
 } catch (Exception e) {
     try {
         /**
          * 如果文本为（<计算>，<校验>,<同左>,<同上>）则不显示
          * add by hufeng 2005.11.8
          */
         if (Text == null || Text.equals(res.getString("String_185")) || Text.equals(res.getString("String_186")) || Text.equals(res.getString("String_187")) ||
             Text.equals(res.getString("String_188"))) {
             Text = "";
         }
         // 设置文本型
         RM.ReportView.setEntry(row, col, Text);
          RM.ReportView.setText(row, col, Text);
     } catch (Exception ee) {

     }
 }
}
    public static void viewNewJsgs(JReportModel RM, JBdhInfo BdhInfo) {

    }

    public static void viewNewJygs(JReportModel RM, JBdhInfo BdhInfo) {

    }

    /**
     *
     * @param RM JReportModel
     * @param BdhInfo JBdhInfo
     */
    public static void setNewBdhTitle(JReportModel RM, JBdhInfo BdhInfo) {
        // 如果没有变动数据，直接返回
        if (BdhInfo == null || BdhInfo.RowInfo.getBdhDataList() == null ||
            BdhInfo.RowInfo.getBdhDataList().size() == 0)return;
        // 获取变动行数
        int BDCount = BdhInfo.RowInfo.getBdhDataList().size();
        try {
            int SRow = BdhInfo.RowInfo.HZD_ZB;
            SRow = JBdhUtils.getPhyRow(SRow - 1, RM); //BdhInfo.RowInfo.HZD_ZB + BdhInfo.RowInfo.UP_BDHS;
            int ERow = SRow + BDCount - 1;
            // 设置新插入的变动行的行标题
            for (int i = 0; i < BDCount; i++) {
                RM.ReportView.setRowText(SRow + i + 1, "-" + String.valueOf(i + 1));
            }
            // 设置树型分级结构
            RM.ReportView.setPhyRowOutlineLevel(SRow + 1, ERow + 1, 0, false);
            RM.ReportView.setPhyRowOutlineLevel(SRow + 1, ERow + 1, 1, false);
            // 设置变动区的Color
            int SCol = 0;
            int ECol = RM.BBZD_LS - 1;
            setBDQColor(RM.ReportView, SRow + 1, SCol, ERow + 1, ECol, LIGHT_GREEN, true);
            /**
             * 设置主行的上下表格线，使之在变动行隐藏后可以完整显示
             * add by hufeng 2005.9.20
             */
            RM.ReportView.setPhySelection(SRow, 0, SRow, RM.BBZD_LS - 1);
            CellFormat CF = RM.ReportView.getCellFormat();
            CF.setTopBorder(CF.eBorderThin);
            CF.setBottomBorder(CF.eBorderThin);
            RM.ReportView.setCellFormat(CF);
            RM.ReportView.setPhySelection(0, 0, 0, 0);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void insertNewBdh(JReportModel RM, JBdhInfo BdhInfo) {
        // 如果没有变动数据，直接返回
        if (BdhInfo == null || BdhInfo.RowInfo.getBdhDataList() == null ||
            BdhInfo.RowInfo.getBdhDataList().size() == 0)return;
        // 获取变动行数
        int BDCount = BdhInfo.RowInfo.getBdhDataList().size();
//		boolean bProtected = RM.ReportView.isEnableProtection();
		boolean bProtected = !RM.ReportView.isAllowInCellEditing();
        try {
            int SRow = BdhInfo.RowInfo.HZD_ZB;
            int ERow = SRow;
            // 需要将所有的大于当前变动行主行的行坐标值加上当前的变动行数
//      addRow(SRow,BDCount,RM);
            // 需要加上此行上部的变动行数
            SRow = JBdhUtils.getPhyRow(SRow - 1, RM); //BdhInfo.RowInfo.HZD_ZB + BdhInfo.RowInfo.UP_BDHS;
            ERow = SRow + BDCount - 1;
            /**
             * 输出结果
             * modified by hufeng 2006.1.10
             */
//      System.out.println("insert row on:" + (SRow + 1));
            RM.ReportView.setPhySelection(SRow + 1, 0, SRow + 1 + BDCount - 1, 0);
//      RM.ReportView.setActiveCell(SRow+1,0);
            // 插入所有的变动行数
//      for(int i=0;i<BDCount;i++) {
            if ( bProtected ) {
             //   RM.ReportView.setEnableProtection(false);
				RM.ReportView.setAllowInCellEditing(true);
            }
            RM.ReportView.editInsert(RM.ReportView.eShiftRows);
//      }
            // 设置新插入的变动行的行标题
//      for(int i=0;i<BDCount;i++) {
//        RM.ReportView.setRowText(SRow+i+1,"-"+String.valueOf(i+1));
//      }
            // 设置树型分级结构
//      RM.ReportView.setPhyRowOutlineLevel(SRow+1,ERow+1,1,false);
            // 设置变动区的Color
//      int SCol = 0;int ECol = RM.BBZD_LS-1;
//      setBDQColor(RM.ReportView,SRow+1,SCol,ERow+1,ECol,Color.LIGHT_GRAY,true);
            setNewBdhTitle(RM, BdhInfo);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if ( bProtected ) { //如果原来是保护的，恢复现场
           //     RM.ReportView.setEnableProtection(true);
				RM.ReportView.setAllowInCellEditing(false);
            }
        }
    }

    private static void setBDQColor(JReportView Book, int SRow, int SCol, int ERow, int ECol, Color c, boolean phy) {
        try {
            CellFormat CF = null;
            if (phy) {
                Book.setPhySelection(SRow, SCol, ERow, ECol);
            } else {
                Book.setSelection(SRow, SCol, ERow, ECol);
            }
            if (CF == null)
                CF = Book.getCellFormat();
            CF.setTopBorder(CF.eBorderThin);
            CF.setTopBorderColor(Color.BLACK.getRGB());
            CF.setPattern(CellFormat.ePatternSolid);
            CF.setPatternFG(c.getRGB());
            CF.setHorizontalInsideBorder(CF.eBorderThin);
            CF.setHorizontalInsideBorderColor(Color.BLACK.getRGB());
            CF.setVerticalInsideBorder(CF.eBorderThin);
            CF.setVerticalInsideBorderColor(Color.BLACK.getRGB());
            CF.setBottomBorder(CF.eBorderThin);
            CF.setBottomBorderColor(Color.BLACK.getRGB());
            CF.setLeftBorder(CF.eBorderThin);
            CF.setLeftBorderColor(Color.BLACK.getRGB());
            CF.setRightBorder(CF.eBorderThin);
            CF.setRightBorderColor(Color.BLACK.getRGB());
            Book.setCellFormat(CF);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void addRow(int Row, int Count, JReportModel RM) {
        JRowInfo RowInfo = RM.HeadRowInfo.NextRow;
        while (RowInfo != null) {
            if (RowInfo.HZD_ZB > Row) {
                RowInfo.UP_BDHS = Count;
            }
            RowInfo = RowInfo.NextRow;
        }
    }

    public static JUnitInfo getBDUnitInfo(JReportModel RM, int logSRow, int logSCol, int phySRow, int phySCol) {
        JUnitInfo UI = null;
        int proRow = JBdhUtils.getPhyNewBdhPrioRow(RM, phySRow);
        int logRow = JBdhUtils.getLogRow(RM, proRow);
        JBdhInfo BdhInfo = RM.getBDH(logRow);
        if (BdhInfo != null && BdhInfo.RowInfo.getBdhDataList() != null) {
            JBDHDataInfo BDHDataInfo = (JBDHDataInfo) BdhInfo.RowInfo.getBdhDataList().get(logSRow);
            UI = BDHDataInfo.getBDUnitInfo(phySCol);
        }
        return UI;
    }

    public static JUnitInfo getUnitInfo(JReportModel RM, int logSRow, int logSCol, int phySRow, int phySCol) {
        JUnitInfo UI = null;
        int proRow = JBdhUtils.getPhyNewBdhPrioRow(RM, phySRow);
        int logRow = JBdhUtils.getLogRow(RM, proRow);
        JBdhInfo BdhInfo = RM.getBDH(logRow);
        if (BdhInfo != null && BdhInfo.RowInfo.getBdhDataList() != null) {
            JBDHDataInfo BDHDataInfo = (JBDHDataInfo) BdhInfo.RowInfo.getBdhDataList().get(logSRow);
            UI = BDHDataInfo.getUnitInfo(phySCol);
        }
        return UI;
    }

}
