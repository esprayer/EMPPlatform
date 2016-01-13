package jreport.swing.classes;

import java.awt.*;

import com.efounder.eai.data.JParamObject;
import com.f1j.ss.*;
import jreport.foundation.classes.*;
import jtoolkit.xml.classes.*;
import java.util.ResourceBundle;

//--------------------------------------------------------------------------------------------------
//描述:
//设计: Skyline(2001.04.22)
//实现: Skyline
//修改:
//--------------------------------------------------------------------------------------------------
public class JCheckReportModel
    extends JReportDataModel {
static ResourceBundle res = ResourceBundle.getBundle("jreport.swing.classes.Language");

    int ADD_TYPE;
    String BBZD_DATE = null;
    int ColCount = 7;
    String Caption = null;
    String[] ColCaptions = null;
    String[] ColValueName = null;
    String[] AuditColCaptions = new String[] {res.getString("String_680"), res.getString("String_681"), res.getString("String_682"),
        res.getString("String_683"), res.getString("String_684"), "","", res.getString("String_686"), res.getString("String_687"), res.getString("String_688")};
    String[] AuditColValueName = new String[] {"BBZD_BH", "BBZD_MC", "JYGS_LX",
        "JYGS_SHOW", "JYGS_MESS", "JYGS_NULL","JYGS_NULL", "JYGS_EXP", "DWZD_BH", "DWZD_MC"
    };
    static final int BBBH_COL = 0;
    static final int BBMC_COL = 1;
    static final int TYPE_COL = 2; //新增 
    static final int ZB_COL = 3;
    static final int XM_COL = 4;
    static final int VALUE_COL = 5;
    static final int CE_COL = 6;
    static final int GS_COL = 7;
    static final int DWBH_COL = 8;
    static final int DWMC_COL = 9;
    static final int LBBH_COL = 8;
    static final int LBMC_COL = 9;
    static final int TITLE_ROW = 0;
    static final int HEAD_ROW = 2;
    static final int BODY_ROW = 3;
    int TAIL_ROW = 0;
    static final int START_COL = 0;
    int Row;
    //------------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.12.29)
    //实现: Skyline
    //修改:
    //------------------------------------------------------------------------------------------------
    public JCheckReportModel() {
    }

    //------------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.12.29)
    //实现: Skyline
    //修改:
    //------------------------------------------------------------------------------------------------
    public String GetCaption() {
        if (Caption != null)
            return Caption;
        if (ADD_TYPE == JReportModel.STUB_TYPE_REPORT) {
            Caption = BBZD_DATE + res.getString("String_698");
            ColCount = 8;
        } else if (ADD_TYPE == JReportModel.STUB_TYPE_LBZD) {
            Caption = res.getString("String_699");
            ColCount = 10;
        } else if (ADD_TYPE == JReportModel.STUB_TYPE_DWZD) {
            Caption = res.getString("String_700");
            ColCount = 10;
        } else if (ADD_TYPE == JReportModel.STUB_TYPE_BMZD) {
            Caption = res.getString("String_701");
            ColCount = 10;
        } else if (ADD_TYPE == JReportModel.STUB_TYPE_CBZD) {
            Caption = res.getString("String_702");
            ColCount = 10;
        }
        return Caption;
    }

    private void InitModel() {
        ColCount = 8;
        if (ADD_TYPE == JReportModel.STUB_TYPE_LBZD) {
            ColCount = 10;
        } else if (ADD_TYPE == JReportModel.STUB_TYPE_DWZD) {
            ColCount = 10;
        } else if (ADD_TYPE == JReportModel.STUB_TYPE_BMZD) {
            ColCount = 10;
        } else if (ADD_TYPE == JReportModel.STUB_TYPE_CBZD) {
            ColCount = 10;
        }
        ColCaptions = new String[ColCount];
        ColValueName = new String[ColCount];

        ColCaptions[0] = res.getString("String_703");
        ColValueName[0] = "BBZD_BH";

        ColCaptions[1] = res.getString("String_705");
        ColValueName[1] = "BBZD_MC";

        ColCaptions[2] = res.getString("String_682");
        ColValueName[2] = "JYGS_TYPE";

        ColCaptions[3] = res.getString("String_707");
        ColValueName[3] = "JYGS_ZB";

        ColCaptions[4] = res.getString("String_709");
        ColValueName[4] = "JYGS_MESS";

        ColCaptions[5] = res.getString("String_711");
        ColValueName[5] = "JYXX_DATA";

        ColCaptions[6] = res.getString("String_713");
        ColValueName[6] = "JYXX_CE";

        ColCaptions[7] = res.getString("String_715");
        ColValueName[7] = "JYGS_EXP";

        if (ADD_TYPE == JReportModel.STUB_TYPE_LBZD) {
            ColCaptions[8] = res.getString("String_717");
            ColValueName[8] = "LBZD_BH";
            ColCaptions[9] = res.getString("String_719");
            ColValueName[9] = "LBZD_MC";
            //
            AuditColCaptions[8] = res.getString("String_721");
            AuditColValueName[8] = "LBZD_BH";
            AuditColCaptions[9] = res.getString("String_723");
            AuditColValueName[9] = "LBZD_MC";
        } else if (ADD_TYPE == JReportModel.STUB_TYPE_DWZD) {
            ColCaptions[8] = res.getString("String_725");
            ColValueName[8] = "DWZD_BH";
            ColCaptions[9] = res.getString("String_727");
            ColValueName[9] = "DWZD_MC";
            //
            AuditColCaptions[8] = res.getString("String_729");
            AuditColValueName[8] = "DWZD_BH";
            AuditColCaptions[9] = res.getString("String_731");
            AuditColValueName[9] = "DWZD_MC";
        } else if (ADD_TYPE == JReportModel.STUB_TYPE_BMZD) {
            ColCaptions[8] = res.getString("String_733");
            ColValueName[8] = "BMZD_BH";
            ColCaptions[9] = res.getString("String_735");
            ColValueName[9] = "BMZD_MC";
            //
            AuditColCaptions[8] = res.getString("String_737");
            AuditColValueName[8] = "BMZD_BH";
            AuditColCaptions[9] = res.getString("String_739");
            AuditColValueName[9] = "BMZD_MC";
        }
        //成本报表
        else if (ADD_TYPE == JReportModel.STUB_TYPE_CBZD) {
            ColCaptions[8] = res.getString("String_741");
            ColValueName[8] = "CBZD_BH";
            ColCaptions[9] = res.getString("String_743");
            ColValueName[9] = "CBZD_MC";
            //
            AuditColCaptions[8] = res.getString("String_745");
            AuditColValueName[8] = "CBZD_BH";
            AuditColCaptions[9] = "成本中心名称";
            AuditColValueName[9] = "CBZD_MC";
        }
    }

    //------------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.12.29)
    //实现: Skyline
    //修改:
    //------------------------------------------------------------------------------------------------
    public int OpenReportObject(Object roe, JParamObject PO) {
        ADD_TYPE = PO.GetIntByParamName("ADD_TYPE", JReportModel.STUB_TYPE_REPORT);
        if (ADD_TYPE == -1)
            ADD_TYPE = JReportModel.STUB_TYPE_REPORT;
        BBZD_DATE = PO.GetValueByParamName("BBZD_DATE");
        InitModel();
        JOpenXMLResultSet XMLResultSet;
        String xmlString = (String) roe;
        String jyString = xmlString, shString = "";
        if (xmlString.indexOf("##TCZAFLW$$TCZAFLW$$TCZAFLW##") != -1) {
            jyString = xmlString.substring(0, xmlString.indexOf("##TCZAFLW$$TCZAFLW$$TCZAFLW##"));
            shString = xmlString.substring(xmlString.indexOf("##TCZAFLW$$TCZAFLW$$TCZAFLW##") + 29);
        }
        XMLResultSet = new jtoolkit.xml.classes.JOpenXMLResultSet(jyString);
        ReportView.setPublicAttrib();
        if(!"1".equals(PO.GetValueByParamName("ISsh", ""))){
        	DrawCheckReport(XMLResultSet);
        }
        if (shString.length() > 0&&"1".equals(PO.GetValueByParamName("ISsh", ""))) {//modify by lqk
            Row++;
            XMLResultSet = new jtoolkit.xml.classes.JOpenXMLResultSet(shString);
            DrawAuditCheckReport(XMLResultSet);
        }
        /**
         * 设置报表最大行和列
         */
        setBookAttrib();
        return 0;
    }

    /**
     * 设置BOOK属性
     */
    private void setBookAttrib() {
        try {
            this.ReportView.setMaxRow(Row);
            this.ReportView.setMaxCol(AuditColCaptions.length - 1);
            this.ReportView.setShowGridLines(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //------------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.12.29)
    //实现: Skyline
    //修改:
    //------------------------------------------------------------------------------------------------
    void DrawCheckReport(JOpenXMLResultSet XMLResultSet) {
        DrawCheckReportHead(XMLResultSet);
        DrawCheckReportBody(XMLResultSet);
        DrawCheckReportTail(XMLResultSet);
        DrawCheckReportTitle(XMLResultSet);
    }

    void DrawAuditCheckReport(JOpenXMLResultSet XMLResultSet) {
        DrawAuditReportTitle(XMLResultSet);
        DrawAuditReportHead(XMLResultSet);
        DrawAuditReportBody(XMLResultSet);
    }

    //------------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.12.29)
    //实现: Skyline
    //修改:
    //------------------------------------------------------------------------------------------------
    void DrawCheckReportTitle(JOpenXMLResultSet XMLResultSet) {
        CellFormat CF;
        try {
            this.ReportView.setSelection(TITLE_ROW, START_COL, TITLE_ROW, ColCount - 1);
            CF = ReportView.getCellFormat();
            ReportView.setText(TITLE_ROW, START_COL, GetCaption());
            CF.setFontSize(20 * 20);
            CF.setHorizontalAlignment(CF.eHorizontalAlignmentCenterAcrossCells);
            ReportView.setCellFormat(CF);
            ReportView.setRowHeightAuto(TITLE_ROW, 0, TITLE_ROW, START_COL, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void DrawAuditReportTitle(JOpenXMLResultSet XMLResultSet) {
        CellFormat CF;
        try {
            Row ++;
            this.ReportView.setSelection(TITLE_ROW + Row, START_COL, TITLE_ROW + Row, ColCount - 1);
            CF = ReportView.getCellFormat();
            ReportView.setText(TITLE_ROW + Row, START_COL, res.getString("String_755"));
            CF.setFontSize(20 * 20);
            CF.setHorizontalAlignment(CF.eHorizontalAlignmentCenterAcrossCells);
            ReportView.setCellFormat(CF);
            ReportView.setRowHeightAuto(TITLE_ROW + Row, 0, TITLE_ROW + Row, START_COL, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //------------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.12.29)
    //实现: Skyline
    //修改:
    //------------------------------------------------------------------------------------------------
    void DrawCheckReportHead(JOpenXMLResultSet XMLResultSet) {
        CellFormat CF;
        try {
            for (int Col = 0; Col < ColCaptions.length; Col++) {
                ReportView.setText(HEAD_ROW, START_COL + Col, ColCaptions[Col]);
            }
            this.ReportView.setSelection(HEAD_ROW, START_COL, HEAD_ROW, ColCount - 1);
            CF = ReportView.getCellFormat();
            CF.setFontSize(11 * 20);
            CF.setHorizontalAlignment(CF.eHorizontalAlignmentCenter);
            CF.setTopBorderColor(new Color(0X87, 0XB1, 0XE1).getRGB());
            CF.setTopBorder(CellFormat.eBorderMedium);
            CF.setBottomBorderColor(new Color(0X87, 0XB1, 0XE1).getRGB());
            CF.setBottomBorder(CellFormat.eBorderMedium);
            CF.setLeftBorderColor(new Color(0X87, 0XB1, 0XE1).getRGB());
            CF.setLeftBorder(CellFormat.eBorderThin);
            CF.setRightBorderColor(new Color(0X87, 0XB1, 0XE1).getRGB());
            CF.setRightBorder(CellFormat.eBorderMedium);
            CF.setHorizontalInsideBorder(CF.eBorderThin);
            CF.setHorizontalInsideBorderColor(new Color(0X87, 0XB1, 0XE1).getRGB());
            CF.setVerticalInsideBorder(CF.eBorderThin);
            CF.setVerticalInsideBorderColor(new Color(0X87, 0XB1, 0XE1).getRGB());
            CF.setLocked(true);
            CF.setFontBold(true);
            CF.setVerticalAlignment(CF.eVerticalAlignmentCenter);
            CF.setPattern(CellFormat.ePatternSolid);
            CF.setPatternFG(0XCEDDF0);
            ReportView.setCellFormat(CF);
            ReportView.setRowHeight(HEAD_ROW, 20 * 20);
            ReportView.setExtraColor(new Color(0XF5, 0XF6, 0XF8));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //画审核校验信息
    void DrawAuditReportHead(JOpenXMLResultSet XMLResultSet) {
        CellFormat CF;
        try {
            for (int Col = 0; Col < AuditColCaptions.length; Col++) {
                ReportView.setText(HEAD_ROW + Row, START_COL + Col, AuditColCaptions[Col]);
            }
            this.ReportView.setSelection(HEAD_ROW + Row, START_COL, HEAD_ROW + Row, ColCount - 1);
            CF = ReportView.getCellFormat();
            CF.setFontSize(11 * 20);
            CF.setHorizontalAlignment(CF.eHorizontalAlignmentCenter);
            CF.setTopBorderColor(new Color(0X87, 0XB1, 0XE1).getRGB());
            CF.setTopBorder(CellFormat.eBorderMedium);
            CF.setBottomBorderColor(new Color(0X87, 0XB1, 0XE1).getRGB());
            CF.setBottomBorder(CellFormat.eBorderThin);
            CF.setLeftBorderColor(new Color(0X87, 0XB1, 0XE1).getRGB());
            CF.setLeftBorder(CellFormat.eBorderThin);
            CF.setRightBorderColor(new Color(0X87, 0XB1, 0XE1).getRGB());
            CF.setRightBorder(CellFormat.eBorderMedium);
            CF.setHorizontalInsideBorder(CF.eBorderThin);
            CF.setHorizontalInsideBorderColor(new Color(0X87, 0XB1, 0XE1).getRGB());
            CF.setVerticalInsideBorder(CF.eBorderThin);
            CF.setVerticalInsideBorderColor(new Color(0X87, 0XB1, 0XE1).getRGB());
            CF.setLocked(true);
            CF.setFontBold(true);
            CF.setVerticalAlignment(CF.eVerticalAlignmentCenter);
            CF.setPattern(CellFormat.ePatternSolid);
            CF.setPatternFG(0XCEDDF0);
            ReportView.setCellFormat(CF);
            ReportView.setRowHeight(HEAD_ROW + Row, 20 * 20);
            ReportView.setExtraColor(new Color(0XF5, 0XF6, 0XF8));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //------------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.12.29)
    //实现: Skyline
    //修改:
    //------------------------------------------------------------------------------------------------
    void DrawCheckReportBody(JOpenXMLResultSet XMLResultSet) {
        Row = this.BODY_ROW;
        int ID;
        CellFormat CF;
        double JyCe = 0.0;
        double Data = 0.0;
        double Value = 0.0;
        String Text;
        boolean Bof = true;
        try {
            while (XMLResultSet.next()) {
                ID = XMLResultSet.getInt("JYXX_ORDE");
                // 处理以0开始的行
                if (ID == 0) {
                    if (!Bof) {
                        ReportView.setSelection(Row, START_COL, Row, ColCount - 1);
                        CF = ReportView.getCellFormat();
                        CF.setPattern(CellFormat.ePatternSolid);
                        CF.setPatternFG(0xCCCCCC);
                        ReportView.setCellFormat(CF);
                        ReportView.setText(Row, START_COL + 4, res.getString("String_757"));
                        ReportView.setNumber(Row, START_COL + 5, Data);
                        ReportView.setNumber(Row, START_COL + 6, JyCe);
                        ReportView.setRowHeight(Row, 16 * 20);
                        Row++;
                    }
                    JyCe = XMLResultSet.getDouble("JYXX_CE");
                    Data = XMLResultSet.getDouble("JYGS_DATA");
                    for (int Index = 0; Index < ColValueName.length; Index++) {
                        if (Index == 5 || Index == 6) {
                            Value = XMLResultSet.getDouble(ColValueName[Index]);
                            ReportView.setNumber(Row, START_COL + Index, Value);
                        } else {
                            Text = XMLResultSet.getString(ColValueName[Index]);
                            ReportView.setText(Row, START_COL + Index, Text);
                        }
                    }
                } else {
                    for (int Index = 3; Index <= 5; Index++) {
                        if (Index == 5 || Index == 6) {
                            Value = XMLResultSet.getDouble(ColValueName[Index]);
                            ReportView.setNumber(Row, START_COL + Index, Value);
                        } else {
                            Text = XMLResultSet.getString(ColValueName[Index]);
                            ReportView.setText(Row, START_COL + Index, Text);
                        }
                    }
                }
                ReportView.setRowHeight(Row, 18 * 20);
                Row++;
                Bof = false;
            }

            ReportView.setSelection(Row, START_COL, Row, ColCount - 1);
            CF = ReportView.getCellFormat();
            CF.setPattern(CellFormat.ePatternSolid);
            CF.setPatternFG(0xCCCCCC);
            ReportView.setCellFormat(CF);
            ReportView.setText(Row, START_COL + 4, res.getString("String_760"));
            ReportView.setNumber(Row, START_COL + 5, Data);
            ReportView.setNumber(Row, START_COL + 6, JyCe);
            ReportView.setSelection(BODY_ROW, START_COL, Row, ColCount - 1);

            CF = ReportView.getCellFormat();
            CF.setFontSize(10 * 20);
            CF.setHorizontalAlignment(CF.eHorizontalAlignmentLeft);
            CF.setTopBorderColor(new Color(0X87, 0XB1, 0XE1).getRGB());
            CF.setTopBorder(CellFormat.eBorderThin);
            CF.setBottomBorderColor(new Color(0X87, 0XB1, 0XE1).getRGB());
            CF.setBottomBorder(CellFormat.eBorderThin);
            CF.setLeftBorderColor(new Color(0X87, 0XB1, 0XE1).getRGB());
            CF.setLeftBorder(CellFormat.eBorderThin);
            CF.setRightBorderColor(new Color(0X87, 0XB1, 0XE1).getRGB());
            CF.setRightBorder(CellFormat.eBorderMedium);
            CF.setHorizontalInsideBorder(CF.eBorderThin);
            CF.setHorizontalInsideBorderColor(new Color(0X87, 0XB1, 0XE1).getRGB());
            CF.setVerticalInsideBorder(CF.eBorderThin);
            CF.setVerticalInsideBorderColor(new Color(0X87, 0XB1, 0XE1).getRGB());
            CF.setVerticalAlignment(CellFormat.eVerticalAlignmentCenter);
            ReportView.setCellFormat(CF);
            ReportView.setRowHeight(Row, 18 * 20);
            ReportView.setColWidthAuto(0, START_COL, Row, ColCount - 1, true);

            ReportView.setSelection(BODY_ROW, START_COL + 4, Row, START_COL + 5);
            CF = ReportView.getCellFormat();
            CF.setValueFormat("#,##0.00");
            CF.setHorizontalAlignment(CellFormat.eHorizontalAlignmentRight);
            ReportView.setCellFormat(CF);
            ReportView.setRowHeight(Row, 18 * 20);
            TAIL_ROW = Row;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //画审核效验信息 fsz 2004.12.16
    void DrawAuditReportBody(JOpenXMLResultSet XMLResultSet) {
        int ID, Type, startRow;
        CellFormat CF;
        String Text;
        boolean Bof = true;
        Row += 3;
        startRow = Row;
        try {
            while (XMLResultSet.next()) {
                ID = XMLResultSet.getInt("JYXX_ORDE");
                Type = XMLResultSet.getInt("JYGS_TYPE");
                // 处理以0开始的行
                if (ID == 0) {
                    // 如果是第一行
                    for (int Index = 0; Index < AuditColValueName.length; Index++) {
                        Text = XMLResultSet.getString(AuditColValueName[Index]);
                        ReportView.setText(Row, START_COL + Index, Text);
                    }
                    ReportView.setSelection(Row, START_COL, Row, ColCount - 1);
                    CF = ReportView.getCellFormat();
                    CF.setPattern(CellFormat.ePatternSolid);
                    if (Type == 1 || Type == 3)
                        CF.setPatternFG(java.awt.Color.red.getRGB());
                    else
                        CF.setPatternFG(java.awt.Color.yellow.getRGB());
                    CF.setHorizontalAlignment(CF.eHorizontalAlignmentCenter);
                    CF.setTopBorderColor(new Color(0X87, 0XB1, 0XE1).getRGB());
                    CF.setTopBorder(CellFormat.eBorderThin);
                    CF.setBottomBorderColor(new Color(0X87, 0XB1, 0XE1).getRGB());
                    CF.setBottomBorder(CellFormat.eBorderThin);
                    CF.setLeftBorderColor(new Color(0X87, 0XB1, 0XE1).getRGB());
                    CF.setLeftBorder(CellFormat.eBorderThin);
                    CF.setRightBorderColor(new Color(0X87, 0XB1, 0XE1).getRGB());
                    CF.setRightBorder(CellFormat.eBorderThin);
                    CF.setHorizontalInsideBorder(CF.eBorderThin);
                    CF.setHorizontalInsideBorderColor(new Color(0X87, 0XB1, 0XE1).getRGB());
                    CF.setVerticalInsideBorder(CF.eBorderThin);
                    CF.setVerticalInsideBorderColor(new Color(0X87, 0XB1, 0XE1).getRGB());

                    ReportView.setRowHeight(Row, 18 * 20);
                    ReportView.setCellFormat(CF);
                }
                Row++;
            }
            if (Row > startRow) {
                ReportView.setSelection(startRow, START_COL, Row - 1, ColCount - 1);
                CF = ReportView.getCellFormat();
                CF.setFontSize(10 * 20);
                CF.setLeftBorder(CF.eBorderThin);
                CF.setTopBorder(CF.eBorderThin);
                CF.setRightBorder(CF.eBorderThin);
                CF.setBottomBorder(CF.eBorderThin);
                CF.setHorizontalInsideBorder(CF.eBorderThin);
                CF.setVerticalInsideBorder(CF.eBorderThin);
                ReportView.setRowHeight(Row, 18 * 20);
                ReportView.setCellFormat(CF);
            }
            ReportView.setColWidthAuto(0, 1, Row + 1, ColCount - 1, true);
            ReportView.setSelection(0, 0, 0, 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //------------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.12.29)
    //实现: Skyline
    //修改:
    //------------------------------------------------------------------------------------------------
    void DrawCheckReportTail(JOpenXMLResultSet XMLResultSet) {
        CellFormat CF;
        try {
            this.ReportView.setSelection(TAIL_ROW, START_COL, TAIL_ROW, ColCount - 1);
            CF = ReportView.getCellFormat();
            CF.setFontSize(10 * 20);
            CF.setPattern(CellFormat.ePatternSolid);
            CF.setPatternFG(0xCCCCCC);
            CF.setHorizontalAlignment(CF.eHorizontalAlignmentLeft);
            CF.setTopBorderColor(new Color(0X87, 0XB1, 0XE1).getRGB());
            CF.setTopBorder(CellFormat.eBorderThin);
            CF.setBottomBorderColor(new Color(0X87, 0XB1, 0XE1).getRGB());
            CF.setBottomBorder(CellFormat.eBorderMedium);
            CF.setLeftBorderColor(new Color(0X87, 0XB1, 0XE1).getRGB());
            CF.setLeftBorder(CellFormat.eBorderThin);
            CF.setRightBorderColor(new Color(0X87, 0XB1, 0XE1).getRGB());
            CF.setRightBorder(CellFormat.eBorderMedium);
            CF.setHorizontalInsideBorder(CF.eBorderThin);
            CF.setHorizontalInsideBorderColor(new Color(0X87, 0XB1, 0XE1).getRGB());
            CF.setVerticalInsideBorder(CF.eBorderThin);
            CF.setVerticalInsideBorderColor(new Color(0X87, 0XB1, 0XE1).getRGB());
            ReportView.setCellFormat(CF);
            ReportView.setSelection(BODY_ROW, START_COL + 5, Row, START_COL + 6);
            CF = ReportView.getCellFormat();
            CF.setValueFormat("#,##0.00");
            CF.setHorizontalAlignment(CellFormat.eHorizontalAlignmentRight);
            ReportView.setCellFormat(CF);
            ReportView.setRowHeight(Row, 18 * 20);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
