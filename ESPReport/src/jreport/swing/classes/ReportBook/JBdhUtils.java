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
     * ָ���߼��У����������� �������Ĳ������߼���-1
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
                // ������ж������µı䶯��
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
     * ָ�������У������߼���
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
     * ����䶯��
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
     * ������е�״̬��0:�����������Ҳ���䶯�� 1:�����ھɰ��ͬһ�䶯���� 2:�������°��ͬһ�䶯���� 3:���п�Խ�˲�ͬ������
     * @param RM JReportModel
     * @param SRow int
     * @param ERow int
     * @return int
     */
    // 0:������ 1:�ɰ�䶯�� 2:�°�䶯���� 3:�°�䶯��
    public static int checkPhyTwoRowStatus(JReportModel RM, int SRow, int ERow) {
        int res = 4;
        int SRowStatus = checkPhyRowStatus(RM, SRow);
        int ERowStatus = checkPhyRowStatus(RM, ERow);
        // ������ж��������У���Ҫ�ټ�飬�������Ƿ��б䶯��
        if (SRowStatus == 0 && ERowStatus == 0) {
            SRow = getLogRow(RM, SRow);
            ERow = getLogRow(RM, ERow); // ��ȡ�߼���
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
        // ������ж��ھɰ汾�䶯���У���Ҫ����Ƿ���ͬһ�䶯��
        if (SRowStatus == 1 && ERowStatus == 1) {
            SRow = getLogRow(RM, SRow);
            ERow = getLogRow(RM, ERow); // ��ȡ�߼���
            JBdhInfo BD1 = RM.getBDH(SRow);
            JBdhInfo BD2 = RM.getBDH(ERow);
            if (BD1.RowInfo.HZD_ZB == BD2.RowInfo.HZD_ZB) {
                res = 1; // ��ͬһ���ɰ汾�䶯��
            }
        }
        // ������ж����°�䶯��������,��Ҫ����Ƿ���ͬһ�䶯��
        if (SRowStatus == 2 && ERowStatus == 2) {
            SRow = getLogRow(RM, SRow);
            ERow = getLogRow(RM, ERow); // ��ȡ�߼���
            JBdhInfo BD1 = RM.getBDH(SRow);
            JBdhInfo BD2 = RM.getBDH(ERow);
            if (BD1.RowInfo.HZD_ZB == BD2.RowInfo.HZD_ZB) {
                res = 2; // ��ͬһ���°汾�䶯��������
            }
        }
        // ������ж����°�䶯���У���Ҫ����Ƿ���ͬһ�䶯��
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
     * ָ��һ���°�䶯���е������У���ȡ������������
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
     * ���ָ���е���һ�е�״̬
     * @param RM JReportModel
     * @param phyRow int
     * @return int
     */
    public static int checkPhyUpRowStatus(JReportModel RM, int phyRow) {
        if (phyRow <= 0)return 0;
        return checkPhyRowStatus(RM, phyRow - 1);
    }

    /**
     * ������������
     * @param RM JReportModel
     * @return int 0:������ 1:�ɰ�䶯�� 2:�°�䶯���� 3:�°�䶯��
     */
    public static int checkPhyRowStatus(JReportModel RM, int phyRow) {
        int res = 0;
        int logRow;
        try {
            String Text = RM.ReportView.getRowText(phyRow);
            if (Text == null) Text = "";
            // �����+��˵����2:�°�䶯����
            if (Text.startsWith("+")) {
                res = 2;
            }
            // �����-��˵����3:�°�䶯��
            if (Text.startsWith("-")) {
                res = 3;
            }
            if (res == 0) {
                // ������ھɰ�䶯����
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
     * ����ɾ����������
     * @param RM JReportModel
     * @param RDO JReportDataObject
     * @param ReportElement Element
     */
    public static void SaveReportDelBDSJ(JReportModel RM, JReportDataObject RDO, Element ReportElement) {
        if (RM.BdhInfoList == null)return;
    }

    /**
     * �����°�䶯��������
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
        // �����䶯����Ԫ��
        Element Rows = RDO.AddChildElement(RE, "BDDEL");
        int index;
        int Count = RM.BdhInfoList.size();
        for (index = 0; index < Count; index++) {
            BdhInfo = (JBdhInfo) RM.BdhInfoList.get(index);
            // ������°�䶯��������д���
            if (BdhInfo.BDH_ISNEW == 1) {
                saveDelData(RM, RDO, Rows, BdhInfo, "DEL");
            }
        }
    }

    private static void saveBDSJ(JReportModel RM, JReportDataObject RDO, Element RE) {
        if (RM.BdhInfoList == null)return;
        JBdhInfo BdhInfo = null;
        // �����䶯����Ԫ��
        Element Rows = RDO.AddChildElement(RE, "BDSJ");
        int index;
        int Count = RM.BdhInfoList.size();
        for (index = 0; index < Count; index++) {
            BdhInfo = (JBdhInfo) RM.BdhInfoList.get(index);
            // ������°�䶯��������д���
            if (BdhInfo.BDH_ISNEW == 1) {
                saveData(RM, RDO, Rows, BdhInfo, "SJ");
            }
        }
    }
    private static void saveBDTZSJ(JReportModel RM, JReportDataObject RDO, Element RE) {
        if (RM.BdhInfoList == null)return;
        JBdhInfo BdhInfo = null;
        // �����䶯����Ԫ��
        Element Rows = RDO.AddChildElement(RE, "BDTZSJ");
        int index;
        int Count = RM.BdhInfoList.size();
        for (index = 0; index < Count; index++) {
            BdhInfo = (JBdhInfo) RM.BdhInfoList.get(index);
            // ������°�䶯��������д���
            if (BdhInfo.BDH_ISNEW == 1) {
                saveData(RM, RDO, Rows, BdhInfo, "TZSJ");
            }
        }
    }
    private static void saveDelData(JReportModel RM, JReportDataObject RDO, Element Rows, JBdhInfo BdhInfo, String Ext) {
        // ���û��������ֱ�ӷ���
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
        // ���û��������ֱ�ӷ���
        if (BdhInfo.RowInfo.getBdhDataList() == null || BdhInfo.RowInfo.getBdhDataList().size() == 0)return;
        int index, count = BdhInfo.RowInfo.getBdhDataList().size();
        JBDHDataInfo BDInfo = null;
        Element R = null;
        for (index = 0; index < count; index++) {
            BDInfo = (JBDHDataInfo) BdhInfo.RowInfo.getBdhDataList().get(index);
            if (BDInfo.ChangeLog == 0 && BDInfo.BD_OFFSET == (index + 1))continue; // ���û���޸Ĺ����ݲ���OFFSETû�н��е���,˵����һ��û���޸Ĺ�
            R = RDO.AddChildElement(Rows, "R");
            JBdhSaveUtils.saveBDSJ(RM, RDO, Rows, R, BdhInfo, BDInfo, index, Ext);
        }
    }

    public static void saveBDJS(JReportModel RM, JReportDataObject RDO, Element RE) {
        if (RM.BdhInfoList == null)return;
        JBdhInfo BdhInfo = null;
        // �����䶯����Ԫ��
        Element Rows = RDO.AddChildElement(RE, "BDJS");
        int index;
        int Count = RM.BdhInfoList.size();
        for (index = 0; index < Count; index++) {
            BdhInfo = (JBdhInfo) RM.BdhInfoList.get(index);
            // ������°�䶯��������д���
            if (BdhInfo.BDH_ISNEW == 1) {
                saveFMT(RM, RDO, Rows, BdhInfo, "JS");
            }
        }
    }
    
    private static void saveBDZS(JReportModel RM, JReportDataObject RDO, Element RE) {
        if (RM.BdhInfoList == null)return;
        JBdhInfo BdhInfo = null;
        // �����䶯����Ԫ��
        Element Rows = RDO.AddChildElement(RE, "BDZS");
        int index;
        int Count = RM.BdhInfoList.size();
        for (index = 0; index < Count; index++) {
            BdhInfo = (JBdhInfo) RM.BdhInfoList.get(index);
            // ������°�䶯��������д���
            if (BdhInfo.BDH_ISNEW == 1) {
                saveFMT(RM, RDO, Rows, BdhInfo, "ZS");
            }
        }
    }    
    private static void saveBDTZ(JReportModel RM, JReportDataObject RDO, Element RE) {
    if (RM.BdhInfoList == null)return;
    JBdhInfo BdhInfo = null;
    // �����䶯����Ԫ��
    Element Rows = RDO.AddChildElement(RE, "BDTZ");
    int index;
    int Count = RM.BdhInfoList.size();
    for (index = 0; index < Count; index++) {
        BdhInfo = (JBdhInfo) RM.BdhInfoList.get(index);
        // ������°�䶯��������д���
        if (BdhInfo.BDH_ISNEW == 1) {
            saveFMT(RM, RDO, Rows, BdhInfo, "TZ");
        }
    }
}


    private static void saveBDJY(JReportModel RM, JReportDataObject RDO, Element RE) {
        if (RM.BdhInfoList == null)return;
        JBdhInfo BdhInfo = null;
        // �����䶯����Ԫ��
        Element Rows = RDO.AddChildElement(RE, "BDJY");
        int index;
        int Count = RM.BdhInfoList.size();
        for (index = 0; index < Count; index++) {
            BdhInfo = (JBdhInfo) RM.BdhInfoList.get(index);
            // ������°�䶯��������д���
            if (BdhInfo.BDH_ISNEW == 1) {
                saveFMT(RM, RDO, Rows, BdhInfo, "JY");
            }
        }
    }

    private static void saveFMT(JReportModel RM, JReportDataObject RDO, Element Rows, JBdhInfo BdhInfo, String Ext) {
        // ���û��������ֱ�ӷ���
        if (BdhInfo.RowInfo.getBdhDataList() == null || BdhInfo.RowInfo.getBdhDataList().size() == 0)return;
        int index, count = BdhInfo.RowInfo.getBdhDataList().size();
        JBDHDataInfo BDInfo = null;
        Element R = null;
        JBDUnitInfo UI = null;
        for (index = 0; index < count; index++) {
            BDInfo = (JBDHDataInfo) BdhInfo.RowInfo.getBdhDataList().get(index);
            // ����һ��XML����
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
                    // �����ָ����Ext�����������Ҫ������
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
        // �����䶯����Ԫ��
        int index;
        int Count = RM.BdhInfoList.size();
        for (index = 0; index < Count; index++) {
            BdhInfo = (JBdhInfo) RM.BdhInfoList.get(index);
            // ������°�䶯��������д���
            if (BdhInfo.BDH_ISNEW == 1) {
                clearBDQChangeLog(RM, BdhInfo);
            }
        }
    }

    private static void clearBDQChangeLog(JReportModel RM, JBdhInfo BdhInfo) {
        if (BdhInfo.BDH_ISNEW == 0)return;
        if (BdhInfo.getDelBDList() != null)
            BdhInfo.getDelBDList().removeAllElements(); // ��������е�ɾ����¼
        if (BdhInfo.RowInfo.getBdhDataList() == null)return;
        int Count = BdhInfo.RowInfo.getBdhDataList().size();
        JBDHDataInfo BDHDataInfo = null;
        for (int i = 0; i < Count; i++) {
            BDHDataInfo = (JBDHDataInfo) BdhInfo.RowInfo.getBdhDataList().get(i);
            if (BDHDataInfo == null)continue;
            BDHDataInfo.BD_OFFSET = i + 1; // �޸ĳɵ�ǰ��BD_OFFSET;
            BDHDataInfo.ChangeLog = 0; // �޸ı�־�ó�0
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
        // ���һ�ű�û�ж����κεı䶯����ֱ�ӷ���
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
        // ���û�б䶯���ݣ�ֱ�ӷ���
        if (BdhInfo == null || BdhInfo.RowInfo.getBdhDataList() == null ||
            BdhInfo.RowInfo.getBdhDataList().size() == 0)return;
        int Index = 0;
        int Count = BdhInfo.RowInfo.getBdhDataList().size();
        JBDHDataInfo BDHDataInfo = null;
        int Row = 0, Col = 0;
        int ColCount = RM.BBZD_LS;
        // �����еı䶯������ʾ

        // ȷ���䶯�еĿ�ʼλ��
        Row = JBdhUtils.getPhyRow(BdhInfo.RowInfo.HZD_ZB - 1, RM); // BdhInfo.RowInfo.HZD_ZB + BdhInfo.RowInfo.UP_BDHS;
        JUnitInfo UI = null;
        JUnitInfo SUI = null;
        for (Index = 0; Index < Count; Index++) {
            // ȡ�䶯���ݶ���
            BDHDataInfo = (JBDHDataInfo) BdhInfo.RowInfo.getBdhDataList().get(Index);
            // ��ÿһ�н�������
            for (Col = 0; Col < ColCount; Col++) {
                // ȡ���ı�����ֵ
                UI = BDHDataInfo.getUnitInfo(Col);
                if (UI != null) {
                    // ���þ����ֵ
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
                case 0: // ����
                    Text = UI.DYZD_SJ;
                    Number = UI.DYZD_DATA;
                    JBdhInfo.setCellContent(RM, row, col, Text, Number);
                    break;
                case 1: // ���㹫ʽ
                    if (UI.DYZD_GSX != null && !"".equals(UI.DYZD_GSX)) {
                        Text = res.getString("String_18");
                        RM.ReportView.setPhyText(row, col, Text);
                        /**
                         * ����鹫ʽʱ������ͬ��ͬ�ϵı�־
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
                case 2: // У�鹫ʽ
                    if (UI.JygsList != null && UI.JygsList.size() > 0) {
                        JJygsInfo JygsInfo = (JJygsInfo) UI.JygsList.get(0);
                        /**
                         * �����Ƿ�Ϊ�յ��ж�
                         */
                        if (null != JygsInfo.JYGS_GSX && !JygsInfo.JYGS_GSX.equals("")) {
                            Text = res.getString("String_22");
                            RM.ReportView.setPhyText(row, col, Text);
                            /**
                             * ����鹫ʽʱ������ͬ��ͬ�ϵı�־
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
                         * �����Ƿ�Ϊ�յ��ж�
                         */
                        if (commentInfo.Comment_Info != null && commentInfo.Comment_Info.trim().length() > 0) {
                            Text = "<��ע>";
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
                case 5: // ���㹫ʽ
                    if (UI.DYZD_ZSGS != null && !"".equals(UI.DYZD_ZSGS)) {
                        Text = "<����>";
                        RM.ReportView.setPhyText(row, col, Text);
                        /**
                         * ����鹫ʽʱ������ͬ��ͬ�ϵı�־
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
                case 0: // ����
                    Text = UI.TZZD_SJ;
                    Number = UI.TZZD_DATA;
                    JBdhInfo.setCellContent(RM, row, col, Text, Number);
                    break;
                case 1: // ���㹫ʽ
                    if (UI.DYZD_GSX != null && !"".equals(UI.DYZD_GSX)) {
                        Text = res.getString("String_18");
                        RM.ReportView.setPhyText(row, col, Text);
                        /**
                         * ����鹫ʽʱ������ͬ��ͬ�ϵı�־
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
                case 2: // У�鹫ʽ
                    if (UI.JygsList != null && UI.JygsList.size() > 0) {
                        JJygsInfo JygsInfo = (JJygsInfo) UI.JygsList.get(0);
                        /**
                         * �����Ƿ�Ϊ�յ��ж�
                         */
                        if (null != JygsInfo.JYGS_GSX && !JygsInfo.JYGS_GSX.equals("")) {
                            Text = res.getString("String_22");
                            RM.ReportView.setPhyText(row, col, Text);
                            /**
                             * ����鹫ʽʱ������ͬ��ͬ�ϵı�־
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
                         * �����Ƿ�Ϊ�յ��ж�
                         */
                        if (commentInfo.Comment_Info != null && commentInfo.Comment_Info.trim().length() > 0) {
                            Text = "<��ע>";
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
