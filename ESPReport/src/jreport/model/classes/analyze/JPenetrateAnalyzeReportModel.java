package jreport.model.classes.analyze;

import java.awt.*;

import org.jdom.*;
import com.f1j.ss.*;
import java.util.ResourceBundle;

/**
 * <p>Title: JPenetrateQueryReportModel</p>
 * <p>Description: 透视分析的Model,透视数据展示组织类</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
public class JPenetrateAnalyzeReportModel
    extends AbstractReportDataModel {
static ResourceBundle res = ResourceBundle.getBundle("jreport.model.classes.analyze.Language");

    private String mTitle = null;
    private String mTAIZ_BH = null;
    private int mRowCount = 0;
    private int mColCount = 0;

    private Element mParamElmt = null; //参数
    private Element mDataElmt = null; //数据
    private Element mTitleElmt = null; //标题
    private Element mHeaderElmt = null; //表头
    private Element mBodyElmt = null; //表体
    private Element mFooterElmt = null; //表尾

    /**
     *
     */
    public void init() {
        String xmlStr = this.mXMLObject.GetRootXMLString();

        this.mParamElmt = this.mXMLObject.GetElementByName("Param");
        this.mTitle = this.mParamElmt.getAttributeValue("Title");
        this.mTAIZ_BH = this.mParamElmt.getAttributeValue("TAIZ_BH");
        this.mRowCount = Integer.parseInt(mParamElmt.getAttributeValue("RowCount"));
        this.mColCount = Integer.parseInt(mParamElmt.getAttributeValue("ColCount"));

        this.mTitleElmt = this.mXMLObject.GetElementByName("Title");
        this.mHeaderElmt = this.mXMLObject.GetElementByName("Header");
        this.mBodyElmt = this.mXMLObject.GetElementByName("Body");
        this.mFooterElmt = this.mXMLObject.GetElementByName("Footer");

        SetCaption(res.getString("String_16") + "-[" + mTAIZ_BH + "]" + mTitle);
    }

    /**
     *
     */
    public void drawReportTitle() {
        try {
            ReportView.setText(0, 0, mTitle);
            ReportView.setSelection(0, 0, 0, mColCount - 1);
            CellFormat cellFormat = ReportView.getCellFormat();
            cellFormat.setFontSize(20 * 20);
            cellFormat.setFontBold(true);
            cellFormat.setHorizontalAlignment(CellFormat.eHorizontalAlignmentCenterAcrossCells);
            cellFormat.setVerticalAlignment(CellFormat.eVerticalAlignmentCenter);
            ReportView.setCellFormat(cellFormat);
            ReportView.setRowHeight(0, TWIPSE_SCALE * 50);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     *
     */
    public void drawReportHead() {
        try {
            int itemCount = Integer.parseInt(mHeaderElmt.getAttributeValue("ItemCount"));
            String BBZD_DATE = mHeaderElmt.getAttributeValue("BBZD_DATE");
            int month = Integer.parseInt(BBZD_DATE.substring(4, 6));

            //项目列头
            String text = mHeaderElmt.getAttributeValue("ItemName" + 0);
            ReportView.setText(1, 0, text);
            ReportView.setSelection(1, 0, 2, 0);
            CellFormat CF = ReportView.getCellFormat();
            CF.setHorizontalAlignment(CellFormat.eHorizontalAlignmentCenter);
            CF.setVerticalAlignment(CellFormat.eVerticalAlignmentCenter);
            CF.setTopBorder(CF.eBorderMedium);
            CF.setBottomBorder(CF.eBorderThin);
            CF.setLeftBorder(CF.eBorderThin);
            CF.setRightBorder(CF.eBorderMedium);
            CF.setTopBorderColor(new Color(0X87, 0XB1, 0XE1).getRGB());
            CF.setBottomBorderColor(new Color(0X87, 0XB1, 0XE1).getRGB());
            CF.setLeftBorderColor(new Color(0X87, 0XB1, 0XE1).getRGB());
            CF.setRightBorderColor(new Color(0X87, 0XB1, 0XE1).getRGB());
            CF.setPattern(CellFormat.ePatternSolid);
            CF.setPatternFG(0xCEDDF0);
            CF.setMergeCells(true);
            ReportView.setCellFormat(CF);

            //数据列头
            int startRow = 1;
            int startCol = 1;
            int fetchCount = itemCount - 1;
            for (int i = 1; i <= month; i++) {
                String monthText = i + res.getString("String_22");
                ReportView.setText(startRow, startCol + (i - 1) * fetchCount, monthText);
            }
            //合并单元格
            for (int i = 1; i <= month; i++) {
                if (fetchCount > 0) {
                    ReportView.setSelection(startRow, startCol + i + (fetchCount - 1) * (i - 1) - 1, startRow, startCol + i + (fetchCount - 1) * (i) - 1);
                } else {
                    ReportView.setSelection(startRow, startCol + i + (fetchCount) * (i - 1) - 1, startRow, startCol + i + (fetchCount) * (i) - 1);
                }
                CF = ReportView.getCellFormat();
                CF.setHorizontalAlignment(CellFormat.eHorizontalAlignmentCenter);
                CF.setVerticalAlignment(CellFormat.eVerticalAlignmentCenter);
                CF.setTopBorder(CF.eBorderMedium);
                CF.setBottomBorder(CF.eBorderThin);
                CF.setLeftBorder(CF.eBorderThin);
                CF.setRightBorder(CF.eBorderMedium);
                CF.setTopBorderColor(new Color(0X87, 0XB1, 0XE1).getRGB());
                CF.setBottomBorderColor(new Color(0X87, 0XB1, 0XE1).getRGB());
                CF.setLeftBorderColor(new Color(0X87, 0XB1, 0XE1).getRGB());
                CF.setRightBorderColor(new Color(0X87, 0XB1, 0XE1).getRGB());
                CF.setPattern(CellFormat.ePatternSolid);
                CF.setPatternFG(0xCEDDF0);
                CF.setMergeCells(true);
                ReportView.setCellFormat(CF);
            }

            startRow = 2;
            startCol = 1;
            for (int i = 1; i < mColCount; i++) {
                int index = (i - 1) % fetchCount + 1;
                String itemText = mHeaderElmt.getAttributeValue("ItemName" + index);
                ReportView.setText(startRow, i, itemText);
                ReportView.setSelection(startRow, i, startRow, i);
                CF = ReportView.getCellFormat();
                CF.setHorizontalAlignment(CellFormat.eHorizontalAlignmentCenter);
                CF.setVerticalAlignment(CellFormat.eVerticalAlignmentCenter);
                CF.setTopBorder(CF.eBorderMedium);
                CF.setBottomBorder(CF.eBorderThin);
                CF.setLeftBorder(CF.eBorderThin);
                CF.setRightBorder(CF.eBorderMedium);
                CF.setTopBorderColor(new Color(0X87, 0XB1, 0XE1).getRGB());
                CF.setBottomBorderColor(new Color(0X87, 0XB1, 0XE1).getRGB());
                CF.setLeftBorderColor(new Color(0X87, 0XB1, 0XE1).getRGB());
                CF.setRightBorderColor(new Color(0X87, 0XB1, 0XE1).getRGB());
                CF.setPattern(CellFormat.ePatternSolid);
                CF.setPatternFG(0xCEDDF0);
                ReportView.setCellFormat(CF);
            }

            ReportView.setSelection(1, 0, 2, mColCount - 1);
            CF = ReportView.getCellFormat();
            CF.setTopBorder(CF.eBorderMedium);
            CF.setBottomBorder(CF.eBorderThin);
            CF.setLeftBorder(CF.eBorderThin);
            CF.setRightBorder(CF.eBorderMedium);
            ReportView.setCellFormat(CF);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     *
     */
    public void drawReportBody() {
        try {
            int startRow = 3;
            int startCol = 0;
            for (int i = 0; i < mRowCount; i++) {
                Element rowElmt = mXMLObject.GetElementByName("Row" + i);
                for (int j = 0; j < mColCount; j++) {
                    String value = rowElmt.getAttributeValue("Col" + j);
                    if (AnalyzeReportUtils.isDouble(value)) {
                        double number = Double.parseDouble(value);
                        ReportView.setNumber(startRow + i, startCol + j, number);
                    } else {
                        ReportView.setText(startRow + i, startCol + j, value);
                    }
                    ReportView.setSelection(startRow + i, startCol + j, startRow + i, startCol + j);
                    CellFormat CF = ReportView.getCellFormat();
                    CF.setValueFormat(DEFAULT_NUMBER_FORMAT);
                    CF.setTopBorder(CF.eBorderThin);
                    CF.setBottomBorder(CF.eBorderThin);
                    CF.setLeftBorder(CF.eBorderThin);
                    CF.setRightBorder(CF.eBorderThin);
                    //最右侧列加粗
                    if (j == mColCount - 1) {
                        CF.setRightBorder(CF.eBorderMedium);
                    }
                    CF.setTopBorderColor(new Color(0X87, 0XB1, 0XE1).getRGB());
                    CF.setBottomBorderColor(new Color(0X87, 0XB1, 0XE1).getRGB());
                    CF.setLeftBorderColor(new Color(0X87, 0XB1, 0XE1).getRGB());
                    CF.setRightBorderColor(new Color(0X87, 0XB1, 0XE1).getRGB());
                    ReportView.setCellFormat(CF);
                }
                //最后一行
                if (i == mRowCount - 1) {
                }
            }
            //行高，列宽
            AnalyzeReportUtils.setDefaultRowHeight(ReportView, 1, mRowCount + 2);
            ReportView.setColWidth(0, 0, TWIPSE_SCALE * 600, false);
            if (mColCount > 1) {
                ReportView.setColWidth(1, mColCount - 1, TWIPSE_SCALE * 200, false);
            } else {
                ReportView.setColWidth(1, mColCount, TWIPSE_SCALE * 200, false);
            }

            //表体边框
            int rowCount = mRowCount;
            int colCount = mColCount;
            if (rowCount > 0) {
                rowCount--;
            }
            if (colCount > 0) {
                colCount--;
            }
            ReportView.setSelection(startRow, startCol, startRow + rowCount, startCol + colCount);
            CellFormat CF = ReportView.getCellFormat();
            CF.setVerticalAlignment(CellFormat.eVerticalAlignmentCenter);
            CF.setTopBorder(CF.eBorderThin);
            CF.setBottomBorder(CF.eBorderThin);
            CF.setLeftBorder(CF.eBorderThin);
            CF.setRightBorder(CF.eBorderMedium);
            CF.setTopBorderColor(new Color(0X87, 0XB1, 0XE1).getRGB());
            CF.setBottomBorderColor(new Color(0X87, 0XB1, 0XE1).getRGB());
            CF.setLeftBorderColor(new Color(0X87, 0XB1, 0XE1).getRGB());
            CF.setRightBorderColor(new Color(0X87, 0XB1, 0XE1).getRGB());
            ReportView.setCellFormat(CF);
            ReportView.setSelection(startRow, startCol, startRow + rowCount, startCol);
            CF = ReportView.getCellFormat();
            CF.setHorizontalAlignment(CellFormat.eHorizontalAlignmentLeft);
            ReportView.setCellFormat(CF);
            if (colCount > 0) {
                ReportView.setSelection(startRow, startCol + 1, startRow + rowCount, startCol + colCount);
                CF = ReportView.getCellFormat();
                CF.setHorizontalAlignment(CellFormat.eHorizontalAlignmentRight);
                ReportView.setCellFormat(CF);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     *
     */
    public void drawReportTail() {
    }

}
