package jreport.swing.classes.ReportBook;

import java.util.Hashtable;
import java.util.ResourceBundle;

import org.jdom.Element;
import com.f1j.util.F1Exception;
import jreport.foundation.classes.JReportDataModel;
import jreport.foundation.classes.JReportDataObject;
import jreport.swing.classes.JReportModel;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class JBdhUtils {

    static ResourceBundle res = ResourceBundle.getBundle("jreport.swing.classes.ReportBook.Language");

    public static void setSelStartRow(JReportModel RM, int Row) throws F1Exception {
        return;
    }

    public static void setSelEndRow(JReportModel RM, int Row) throws F1Exception {
        return;
    }

    /**
     * 指定逻辑行，返回物理行 传过来的参数是逻辑行-1
     * @param logRow int
     * @return int
     */
    public static int getPhyRow(int logRow, JReportDataModel rm) {
        if (! (rm instanceof JReportModel))return logRow;
        JReportModel RM = (JReportModel) rm;
        int SRow = 1;
        int ERow = logRow + 1;
        JRowInfo RowInfo = null;
        int UP_BDHS = 0;
        String Text;
        int R = SRow, Row;
        try {
            for (Row = SRow; Row <= ERow; Row++) {
                RowInfo = JRowInfo.GetRowInfoByZB(Row, RM);
                Text = String.valueOf(Row);
                if (RowInfo != null)
                    R = RowInfo.HZD_ZB - 1;
                else
                    R = Row - 1;
                R += UP_BDHS;
                // 如果该行定义了新的变动行
                if (RowInfo != null && RowInfo.getBdhDataList() != null) {
                    UP_BDHS += RowInfo.getBdhDataList().size();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        int phyRow = R;
        return phyRow;
    }

    /**
     * 指定物理行，返回逻辑行
     * @param phyRow int
     * @return int
     */
    public static void setText(int mainRow, int offset, int col, String Text, JReportModel RM) {

    }

    public static String getText(int mainRow, int offset, int col, JReportModel RM) {
        return null;
    }

    public static void setNumber(int mainRow, int offset, int col, double number, JReportModel RM) {

    }

    public static double getNumber(int mainRow, int offset, int col, JReportModel RM) {
        return 0.0;
    }

    public static void setEntry(int mainRow, int offset, int col, String Entry, JReportModel RM) {

    }

    public static String getEntry(int mainRow, int offset, int col, JReportModel RM) {
        return null;
    }

    /**
     * 插入变动行
     * @param RM JReportModel
     * @param BdhInfo JBdhInfo
     * @param SRow int
     * @param ERow int
     */
    public static void insertBDH(JReportModel RM, JBdhInfo BdhInfo, int SRow, int ERow) {

    }

    public static int getLogRow(JReportDataModel RM, int phyRow) {
        return getHeadRow(RM, phyRow) + 1;
    }

    public static int getHeadRow(JReportDataModel RM, int phyRow) {
        int NRow = phyRow;
        String Text;
        try {
            Text = RM.ReportView.getRowText(phyRow);
            if (Text != null) {
                if (Text.startsWith("+") || Text.startsWith("-")) {
                    Text = Text.substring(1, Text.length());
                }
                NRow = Integer.parseInt(Text) - 1;
            } else {
                NRow = phyRow;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return NRow;
    }

    /**
     * 检查两行的状态：0:正常的两行且不跨变动区 1:两行在旧版的同一变动区中 2:两行在新版的同一变动区中 3:两行跨越了不同的区域
     * @param RM JReportModel
     * @param SRow int
     * @param ERow int
     * @return int
     */
    // 0:正常行 1:旧版变动行 2:新版变动主行 3:新版变动行
    public static int checkPhyTwoRowStatus(JReportModel RM, int SRow, int ERow) {
        int res = 4;
        int SRowStatus = checkPhyRowStatus(RM, SRow);
        int ERowStatus = checkPhyRowStatus(RM, ERow);
        // 如果两行都是正常行，需要再检查，两行中是否有变动区
        if (SRowStatus == 0 && ERowStatus == 0) {
            SRow = getLogRow(RM, SRow);
            ERow = getLogRow(RM, ERow); // 获取逻辑行
            JBdhInfo BD = null;
            res = 0;
            for (int r = SRow; r <= ERow; r++) {
                BD = RM.getBDH(r);
                if (BD != null) {
                    res = 4;
                    break;
                }
            }
        }
        // 如果两行都在旧版本变动区中，需要检查是否在同一变动区
        if (SRowStatus == 1 && ERowStatus == 1) {
            SRow = getLogRow(RM, SRow);
            ERow = getLogRow(RM, ERow); // 获取逻辑行
            JBdhInfo BD1 = RM.getBDH(SRow);
            JBdhInfo BD2 = RM.getBDH(ERow);
            if (BD1.RowInfo.HZD_ZB == BD2.RowInfo.HZD_ZB) {
                res = 1; // 在同一个旧版本变动区
            }
        }
        // 如果两行都是新版变动区的主行,需要检查是否在同一变动区
        if (SRowStatus == 2 && ERowStatus == 2) {
            SRow = getLogRow(RM, SRow);
            ERow = getLogRow(RM, ERow); // 获取逻辑行
            JBdhInfo BD1 = RM.getBDH(SRow);
            JBdhInfo BD2 = RM.getBDH(ERow);
            if (BD1.RowInfo.HZD_ZB == BD2.RowInfo.HZD_ZB) {
                res = 2; // 在同一个新版本变动区的主行
            }
        }
        // 如查两行都在新版变动区中，需要检查是否在同一变动区
        if (SRowStatus == 3 && ERowStatus == 3) {
            int PriRow1 = getPhyNewBdhPrioRow(RM, SRow);
            int PriRow2 = getPhyNewBdhPrioRow(RM, ERow);
            if (PriRow1 == PriRow2) {
                res = 3;
            }
        }
        return res;
    }

    /**
     * 指定一个新版变动区中的物理行，获取它的主行坐标
     * @param RM JReportModel
     * @param phyRow int
     * @return int
     */
    public static int getPhyNewBdhPrioRow(JReportModel RM, int phyRow) {
        int priRow = 0;
        String Text;
        try {
            for (int R = phyRow; R >= 0; R--) {
                Text = RM.ReportView.getRowText(R);
                if (Text.startsWith("+")) {
                    priRow = R; //getHeadRow(RM,R);
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return priRow;
    }

//  public static int getPhyNewBdhPrioRow(JReportModel RM,int phyRow) {
//  }
    /**
     * 检查指定行的上一行的状态
     * @param RM JReportModel
     * @param phyRow int
     * @return int
     */
    public static int checkPhyUpRowStatus(JReportModel RM, int phyRow) {
        if (phyRow <= 0)return 0;
        return checkPhyRowStatus(RM, phyRow - 1);
    }

    /**
     * 参数是物理行
     * @param RM JReportModel
     * @return int 0:正常行 1:旧版变动行 2:新版变动主行 3:新版变动行
     */
    public static int checkPhyRowStatus(JReportModel RM, int phyRow) {
        int res = 0;
        int logRow;
        try {
            String Text = RM.ReportView.getRowText(phyRow);
            if (Text == null) Text = "";
            // 如果有+，说明是2:新版变动主行
            if (Text.startsWith("+")) {
                res = 2;
            }
            // 如果有-，说明是3:新版变动行
            if (Text.startsWith("-")) {
                res = 3;
            }
            if (res == 0) {
                // 如果是在旧版变动区内
                logRow = getLogRow(RM, phyRow);
                JBdhInfo BdhInfo = RM.getBDH(logRow);
                if (BdhInfo != null) {
                    res = 1;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    /**
     * 处理删除掉的数据
     * @param RM JReportModel
     * @param RDO JReportDataObject
     * @param ReportElement Element
     */
    public static void SaveReportDelBDSJ(JReportModel RM, JReportDataObject RDO, Element ReportElement) {
        if (RM.BdhInfoList == null)return;
    }

    /**
     * 处理新版变动区的数据
     * @param RM JReportModel
     * @param RDO JReportDataObject
     * @param ReportElement Element
     */
    public static void SaveReportBDSJ(JReportModel RM, JReportDataObject RDO, Element RE) {
        saveBDDEL(RM, RDO, RE);
        saveBDSJ(RM, RDO, RE);
        saveBDJS(RM, RDO, RE);
        saveBDJY(RM, RDO, RE);
        saveBDTZ(RM, RDO, RE);
        saveBDZS(RM, RDO, RE);
    }
    public static void SaveReportBDTZSJ(JReportModel RM, JReportDataObject RDO, Element RE) {
    saveBDDEL(RM, RDO, RE);
    saveBDTZSJ(RM, RDO, RE);

}


    private static void saveBDDEL(JReportModel RM, JReportDataObject RDO, Element RE) {
        if (RM.BdhInfoList == null)return;
        JBdhInfo BdhInfo = null;
        // 创建变动数据元素
        Element Rows = RDO.AddChildElement(RE, "BDDEL");
        int index;
        int Count = RM.BdhInfoList.size();
        for (index = 0; index < Count; index++) {
            BdhInfo = (JBdhInfo) RM.BdhInfoList.get(index);
            // 如果是新版变动区，则进行处理
            if (BdhInfo.BDH_ISNEW == 1) {
                saveDelData(RM, RDO, Rows, BdhInfo, "DEL");
            }
        }
    }

    private static void saveBDSJ(JReportModel RM, JReportDataObject RDO, Element RE) {
        if (RM.BdhInfoList == null)return;
        JBdhInfo BdhInfo = null;
        // 创建变动数据元素
        Element Rows = RDO.AddChildElement(RE, "BDSJ");
        int index;
        int Count = RM.BdhInfoList.size();
        for (index = 0; index < Count; index++) {
            BdhInfo = (JBdhInfo) RM.BdhInfoList.get(index);
            // 如果是新版变动区，则进行处理
            if (BdhInfo.BDH_ISNEW == 1) {
                saveData(RM, RDO, Rows, BdhInfo, "SJ");
            }
        }
    }
    private static void saveBDTZSJ(JReportModel RM, JReportDataObject RDO, Element RE) {
        if (RM.BdhInfoList == null)return;
        JBdhInfo BdhInfo = null;
        // 创建变动数据元素
        Element Rows = RDO.AddChildElement(RE, "BDTZSJ");
        int index;
        int Count = RM.BdhInfoList.size();
        for (index = 0; index < Count; index++) {
            BdhInfo = (JBdhInfo) RM.BdhInfoList.get(index);
            // 如果是新版变动区，则进行处理
            if (BdhInfo.BDH_ISNEW == 1) {
                saveData(RM, RDO, Rows, BdhInfo, "TZSJ");
            }
        }
    }
    private static void saveDelData(JReportModel RM, JReportDataObject RDO, Element Rows, JBdhInfo BdhInfo, String Ext) {
        // 如果没有数据则直接返回
        if (BdhInfo.getDelBDList() == null || BdhInfo.getDelBDList().size() == 0)return;
        int index, count = BdhInfo.getDelBDList().size();
        JBDHDataInfo BDInfo = null;
        Element R = null;
        for (index = 0; index < count; index++) {
            BDInfo = (JBDHDataInfo) BdhInfo.getDelBDList().get(index);
            R = RDO.AddChildElement(Rows, "R");
            JBdhSaveUtils.saveBDSJ(RM, RDO, Rows, R, BdhInfo, BDInfo, index, Ext);
        }
    }

    private static void saveData(JReportModel RM, JReportDataObject RDO, Element Rows, JBdhInfo BdhInfo, String Ext) {
        // 如果没有数据则直接返回
        if (BdhInfo.RowInfo.getBdhDataList() == null || BdhInfo.RowInfo.getBdhDataList().size() == 0)return;
        int index, count = BdhInfo.RowInfo.getBdhDataList().size();
        JBDHDataInfo BDInfo = null;
        Element R = null;
        for (index = 0; index < count; index++) {
            BDInfo = (JBDHDataInfo) BdhInfo.RowInfo.getBdhDataList().get(index);
            if (BDInfo.ChangeLog == 0 && BDInfo.BD_OFFSET == (index + 1))continue; // 如果没有修改过数据并且OFFSET没有进行调整,说明这一行没有修改过
            R = RDO.AddChildElement(Rows, "R");
            JBdhSaveUtils.saveBDSJ(RM, RDO, Rows, R, BdhInfo, BDInfo, index, Ext);
        }
    }

    public static void saveBDJS(JReportModel RM, JReportDataObject RDO, Element RE) {
        if (RM.BdhInfoList == null)return;
        JBdhInfo BdhInfo = null;
        // 创建变动数据元素
        Element Rows = RDO.AddChildElement(RE, "BDJS");
        int index;
        int Count = RM.BdhInfoList.size();
        for (index = 0; index < Count; index++) {
            BdhInfo = (JBdhInfo) RM.BdhInfoList.get(index);
            // 如果是新版变动区，则进行处理
            if (BdhInfo.BDH_ISNEW == 1) {
                saveFMT(RM, RDO, Rows, BdhInfo, "JS");
            }
        }
    }
    
    private static void saveBDZS(JReportModel RM, JReportDataObject RDO, Element RE) {
        if (RM.BdhInfoList == null)return;
        JBdhInfo BdhInfo = null;
        // 创建变动数据元素
        Element Rows = RDO.AddChildElement(RE, "BDZS");
        int index;
        int Count = RM.BdhInfoList.size();
        for (index = 0; index < Count; index++) {
            BdhInfo = (JBdhInfo) RM.BdhInfoList.get(index);
            // 如果是新版变动区，则进行处理
            if (BdhInfo.BDH_ISNEW == 1) {
                saveFMT(RM, RDO, Rows, BdhInfo, "ZS");
            }
        }
    }    
    private static void saveBDTZ(JReportModel RM, JReportDataObject RDO, Element RE) {
    if (RM.BdhInfoList == null)return;
    JBdhInfo BdhInfo = null;
    // 创建变动数据元素
    Element Rows = RDO.AddChildElement(RE, "BDTZ");
    int index;
    int Count = RM.BdhInfoList.size();
    for (index = 0; index < Count; index++) {
        BdhInfo = (JBdhInfo) RM.BdhInfoList.get(index);
        // 如果是新版变动区，则进行处理
        if (BdhInfo.BDH_ISNEW == 1) {
            saveFMT(RM, RDO, Rows, BdhInfo, "TZ");
        }
    }
}


    private static void saveBDJY(JReportModel RM, JReportDataObject RDO, Element RE) {
        if (RM.BdhInfoList == null)return;
        JBdhInfo BdhInfo = null;
        // 创建变动数据元素
        Element Rows = RDO.AddChildElement(RE, "BDJY");
        int index;
        int Count = RM.BdhInfoList.size();
        for (index = 0; index < Count; index++) {
            BdhInfo = (JBdhInfo) RM.BdhInfoList.get(index);
            // 如果是新版变动区，则进行处理
            if (BdhInfo.BDH_ISNEW == 1) {
                saveFMT(RM, RDO, Rows, BdhInfo, "JY");
            }
        }
    }

    private static void saveFMT(JReportModel RM, JReportDataObject RDO, Element Rows, JBdhInfo BdhInfo, String Ext) {
        // 如果没有数据则直接返回
        if (BdhInfo.RowInfo.getBdhDataList() == null || BdhInfo.RowInfo.getBdhDataList().size() == 0)return;
        int index, count = BdhInfo.RowInfo.getBdhDataList().size();
        JBDHDataInfo BDInfo = null;
        Element R = null;
        JBDUnitInfo UI = null;
        for (index = 0; index < count; index++) {
            BDInfo = (JBDHDataInfo) BdhInfo.RowInfo.getBdhDataList().get(index);
            // 增加一行XML数据
            for (int Col = 0; Col < RM.BBZD_LS; Col++) {
                UI = BDInfo.getUnitInfo(Col);
                if (UI == null)continue;
                JBdhSaveUtils.saveBDFMT(RM, RDO, Rows, R, BdhInfo, BDInfo, UI, index, Ext);
            }
        }
    }

    private static void bindHastToXML(JReportDataObject RDO, Element Rows, Element R, Hashtable hash, String Ext) {
        Object Key;
        Object Value;
        String col;
        String KeyName;
        Object[] Keys = hash.keySet().toArray();
        int Index;
        for (int i = 0; i < Keys.length; i++) {
            Key = Keys[i];
            Value = hash.get(Key);
            KeyName = Key.toString();

            if (Ext != null) {
                Ext += "_";
                if (KeyName.startsWith(Ext)) {
                    KeyName = KeyName.substring(Ext.length());
                } else {
                    // 如果和指定的Ext不相符，则不需要设轩置
                    Value = null;
                }
            }
            if (Value != null)
                RDO.SetElementValue(R, KeyName, Value.toString());

        }
    }

    public static void ClearChangeLog(JReportModel RM) {
        if (RM.BdhInfoList == null)return;
        JBdhInfo BdhInfo = null;
        // 创建变动数据元素
        int index;
        int Count = RM.BdhInfoList.size();
        for (index = 0; index < Count; index++) {
            BdhInfo = (JBdhInfo) RM.BdhInfoList.get(index);
            // 如果是新版变动区，则进行处理
            if (BdhInfo.BDH_ISNEW == 1) {
                clearBDQChangeLog(RM, BdhInfo);
            }
        }
    }

    private static void clearBDQChangeLog(JReportModel RM, JBdhInfo BdhInfo) {
        if (BdhInfo.BDH_ISNEW == 0)return;
        if (BdhInfo.getDelBDList() != null)
            BdhInfo.getDelBDList().removeAllElements(); // 清除掉所有的删除记录
        if (BdhInfo.RowInfo.getBdhDataList() == null)return;
        int Count = BdhInfo.RowInfo.getBdhDataList().size();
        JBDHDataInfo BDHDataInfo = null;
        for (int i = 0; i < Count; i++) {
            BDHDataInfo = (JBDHDataInfo) BdhInfo.RowInfo.getBdhDataList().get(i);
            if (BDHDataInfo == null)continue;
            BDHDataInfo.BD_OFFSET = i + 1; // 修改成当前的BD_OFFSET;
            BDHDataInfo.ChangeLog = 0; // 修改标志置成0
            clearBDHDataChangeLog(RM, BdhInfo, BDHDataInfo);
        }
    }

    private static void clearBDHDataChangeLog(JReportModel RM, JBdhInfo BdhInfo, JBDHDataInfo BDHDataInfo) {
        JUnitInfo UI = null;
        for (int col = 0; col < RM.BBZD_LS; col++) {
            UI = BDHDataInfo.getUnitInfo(col);
            if (UI == null)continue;
            UI.ChangeLog = 0;
            UI.ChangeLogJs = 0;
            UI.ChangeLogJy = 0;
            UI.ChangeLogZs = 0;
        }
    }

    public static void ShowReportData(JReportModel RM) {
        showNewBdhs(RM, 0);
    }

    public static void ShowReportJsgs(JReportModel RM) {
        showNewBdhs(RM, 1);
    }

    public static void ShowReportJygs(JReportModel RM) {
        showNewBdhs(RM, 2);
    }

    public static void ShowReportComment(JReportModel RM) {
        showNewBdhs(RM, 3);
    }
    public static void ShowReportTzgs(JReportModel RM) {
        showNewBdhs(RM, 4);
    }
    public static void ShowReportZsgs(JReportModel RM) {
        showNewBdhs(RM, 5);
    }

    static void showNewBdhs(JReportModel RM, int viewtype) {
        // 如果一张表没有定义任何的变动区域，直接返回
        if (RM.BdhInfoList == null || RM.BdhInfoList.size() == 0)return;
        JBdhInfo BdhInfo = null;
        int Count = RM.BdhInfoList.size();
        int Index = 0;
        for (Index = 0; Index < Count; Index++) {
            BdhInfo = (JBdhInfo) RM.BdhInfoList.get(Index);
            if (BdhInfo.BDH_ISNEW == 1) {
                showNewBdh(RM, BdhInfo, viewtype);
            }
        }
    }

    static void showNewBdh(JReportModel RM, JBdhInfo BdhInfo, int viewtype) {
        // 如果没有变动数据，直接返回
        if (BdhInfo == null || BdhInfo.RowInfo.getBdhDataList() == null ||
            BdhInfo.RowInfo.getBdhDataList().size() == 0)return;
        int Index = 0;
        int Count = BdhInfo.RowInfo.getBdhDataList().size();
        JBDHDataInfo BDHDataInfo = null;
        int Row = 0, Col = 0;
        int ColCount = RM.BBZD_LS;
        // 将所有的变动数据显示

        // 确定变动行的开始位置
        Row = JBdhUtils.getPhyRow(BdhInfo.RowInfo.HZD_ZB - 1, RM); // BdhInfo.RowInfo.HZD_ZB + BdhInfo.RowInfo.UP_BDHS;
        JUnitInfo UI = null;
        JUnitInfo SUI = null;
        for (Index = 0; Index < Count; Index++) {
            // 取变动数据定义
            BDHDataInfo = (JBDHDataInfo) BdhInfo.RowInfo.getBdhDataList().get(Index);
            // 对每一列进行设置
            for (Col = 0; Col < ColCount; Col++) {
                // 取出文本属性值
                UI = BDHDataInfo.getUnitInfo(Col);
                if (UI != null) {
                    // 设置具体的值
                    if(RM.TZZD_BH!=null && RM.TZZD_BH.trim().length()>0){
                      SUI = JUnitInfo.GetUnitInfoByHL(BdhInfo.RowInfo.HZD_ZB,Col,1,RM);
                      if(SUI!=null){
                        if(SUI.DYZD_TZGS!=null && SUI.DYZD_TZGS.trim().length()>0){
                          setCellTzContent(RM, Row + Index + 1, Col, UI, viewtype);
                        }
                      }else{
                        setCellContent(RM, Row + Index + 1, Col, UI, viewtype);
                      }
                    }else{
                      setCellContent(RM, Row + Index + 1, Col, UI, viewtype);
                    }

                }
            }
        }
    }

    private static void setCellContent(JReportModel RM, int row, int col, JUnitInfo UI, int viewtype) {
        String Text = null;
        double Number;
        try {
            switch (viewtype) {
                case 0: // 数据
                    Text = UI.DYZD_SJ;
                    Number = UI.DYZD_DATA;
                    JBdhInfo.setCellContent(RM, row, col, Text, Number);
                    break;
                case 1: // 计算公式
                    if (UI.DYZD_GSX != null && !"".equals(UI.DYZD_GSX)) {
                        Text = res.getString("String_18");
                        RM.ReportView.setPhyText(row, col, Text);
                        /**
                         * 定义块公式时，加入同左同上的标志
                         * add by hufeng 2005.11.8
                         */
                        int i, j;
                        for (i = 1; i <= UI.DYZD_HOFFSET; i++) {
                            RM.ReportView.setPhyText(row + i, col, res.getString("String_27"));
                        }
                        for (i = 1; i <= UI.DYZD_LOFFSET; i++) {
                            RM.ReportView.setPhyText(row, col + i, res.getString("String_20"));
                        }
                        for (i = 1; i <= UI.DYZD_HOFFSET; i++) {
                            for (j = 1; j <= UI.DYZD_LOFFSET; j++) {
                                RM.ReportView.setPhyText(row + i, col + j, res.getString("String_29"));
                            }
                        }
                    }
                    break;
                case 2: // 校验公式
                    if (UI.JygsList != null && UI.JygsList.size() > 0) {
                        JJygsInfo JygsInfo = (JJygsInfo) UI.JygsList.get(0);
                        /**
                         * 加入是否为空的判断
                         */
                        if (null != JygsInfo.JYGS_GSX && !JygsInfo.JYGS_GSX.equals("")) {
                            Text = res.getString("String_22");
                            RM.ReportView.setPhyText(row, col, Text);
                            /**
                             * 定义块公式时，加入同左同上的标志
                             * add by hufeng 2005.11.8
                             */
                            int i, j;
                            for (i = 1; i <= JygsInfo.JYGS_HOFFSET; i++) {
                                RM.ReportView.setPhyText(row + i, col, res.getString("String_31"));
                            }
                            for (i = 1; i <= JygsInfo.JYGS_LOFFSET; i++) {
                                RM.ReportView.setPhyText(row, col + i, res.getString("String_24"));
                            }
                            for (i = 1; i <= JygsInfo.JYGS_HOFFSET; i++) {
                                for (j = 1; j <= JygsInfo.JYGS_LOFFSET; j++) {
                                    RM.ReportView.setPhyText(row + i, col + j, res.getString("String_33"));
                                }
                            }
                        }
                    }
                    break;
                case 3:
                    if (UI.CommentList != null && UI.CommentList.size() > 0) {
                        JCommentInfo commentInfo = (JCommentInfo) UI.CommentList.get(0);
                        /**
                         * 加入是否为空的判断
                         */
                        if (commentInfo.Comment_Info != null && commentInfo.Comment_Info.trim().length() > 0) {
                            Text = "<批注>";
                            RM.ReportView.setPhyText(row, col, Text);
                            int i, j;
                            for (i = 1; i <= commentInfo.Comment_HOFFSET; i++) {
                                RM.ReportView.setPhyText(row + i, col, res.getString("String_31"));
                            }
                            for (i = 1; i <= commentInfo.Comment_LOFFSET; i++) {
                                RM.ReportView.setPhyText(row, col + i, res.getString("String_24"));
                            }
                            for (i = 1; i <= commentInfo.Comment_HOFFSET; i++) {
                                for (j = 1; j <= commentInfo.Comment_LOFFSET; j++) {
                                    RM.ReportView.setPhyText(row + i, col + j, res.getString("String_33"));
                                }
                            }
                        }
                    }
                    break;
                case 5: // 折算公式
                    if (UI.DYZD_ZSGS != null && !"".equals(UI.DYZD_ZSGS)) {
                        Text = "<折算>";
                        RM.ReportView.setPhyText(row, col, Text);
                        /**
                         * 定义块公式时，加入同左同上的标志
                         * add by hufeng 2005.11.8
                         */
                        int i, j;
                        for (i = 1; i <= UI.DYZD_ZSHOFFSET; i++) {
                            RM.ReportView.setPhyText(row + i, col, res.getString("String_27"));
                        }
                        for (i = 1; i <= UI.DYZD_ZSLOFFSET; i++) {
                            RM.ReportView.setPhyText(row, col + i, res.getString("String_20"));
                        }
                        for (i = 1; i <= UI.DYZD_ZSHOFFSET; i++) {
                            for (j = 1; j <= UI.DYZD_ZSLOFFSET; j++) {
                                RM.ReportView.setPhyText(row + i, col + j, res.getString("String_29"));
                            }
                        }
                    }
                    break;
                    
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private static void setCellTzContent(JReportModel RM, int row, int col, JUnitInfo UI, int viewtype) {
        String Text = null;
        double Number;
        try {
            switch (viewtype) {
                case 0: // 数据
                    Text = UI.TZZD_SJ;
                    Number = UI.TZZD_DATA;
                    JBdhInfo.setCellContent(RM, row, col, Text, Number);
                    break;
                case 1: // 计算公式
                    if (UI.DYZD_GSX != null && !"".equals(UI.DYZD_GSX)) {
                        Text = res.getString("String_18");
                        RM.ReportView.setPhyText(row, col, Text);
                        /**
                         * 定义块公式时，加入同左同上的标志
                         * add by hufeng 2005.11.8
                         */
                        int i, j;
                        for (i = 1; i <= UI.DYZD_HOFFSET; i++) {
                            RM.ReportView.setPhyText(row + i, col, res.getString("String_27"));
                        }
                        for (i = 1; i <= UI.DYZD_LOFFSET; i++) {
                            RM.ReportView.setPhyText(row, col + i, res.getString("String_20"));
                        }
                        for (i = 1; i <= UI.DYZD_HOFFSET; i++) {
                            for (j = 1; j <= UI.DYZD_LOFFSET; j++) {
                                RM.ReportView.setPhyText(row + i, col + j, res.getString("String_29"));
                            }
                        }
                    }
                    break;
                case 2: // 校验公式
                    if (UI.JygsList != null && UI.JygsList.size() > 0) {
                        JJygsInfo JygsInfo = (JJygsInfo) UI.JygsList.get(0);
                        /**
                         * 加入是否为空的判断
                         */
                        if (null != JygsInfo.JYGS_GSX && !JygsInfo.JYGS_GSX.equals("")) {
                            Text = res.getString("String_22");
                            RM.ReportView.setPhyText(row, col, Text);
                            /**
                             * 定义块公式时，加入同左同上的标志
                             * add by hufeng 2005.11.8
                             */
                            int i, j;
                            for (i = 1; i <= JygsInfo.JYGS_HOFFSET; i++) {
                                RM.ReportView.setPhyText(row + i, col, res.getString("String_31"));
                            }
                            for (i = 1; i <= JygsInfo.JYGS_LOFFSET; i++) {
                                RM.ReportView.setPhyText(row, col + i, res.getString("String_24"));
                            }
                            for (i = 1; i <= JygsInfo.JYGS_HOFFSET; i++) {
                                for (j = 1; j <= JygsInfo.JYGS_LOFFSET; j++) {
                                    RM.ReportView.setPhyText(row + i, col + j, res.getString("String_33"));
                                }
                            }
                        }
                    }
                    break;
                case 3:
                    if (UI.CommentList != null && UI.CommentList.size() > 0) {
                        JCommentInfo commentInfo = (JCommentInfo) UI.CommentList.get(0);
                        /**
                         * 加入是否为空的判断
                         */
                        if (commentInfo.Comment_Info != null && commentInfo.Comment_Info.trim().length() > 0) {
                            Text = "<批注>";
                            RM.ReportView.setPhyText(row, col, Text);
                            int i, j;
                            for (i = 1; i <= commentInfo.Comment_HOFFSET; i++) {
                                RM.ReportView.setPhyText(row + i, col, res.getString("String_31"));
                            }
                            for (i = 1; i <= commentInfo.Comment_LOFFSET; i++) {
                                RM.ReportView.setPhyText(row, col + i, res.getString("String_24"));
                            }
                            for (i = 1; i <= commentInfo.Comment_HOFFSET; i++) {
                                for (j = 1; j <= commentInfo.Comment_LOFFSET; j++) {
                                    RM.ReportView.setPhyText(row + i, col + j, res.getString("String_33"));
                                }
                            }
                        }
                    }

                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
