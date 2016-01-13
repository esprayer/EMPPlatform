package jreport.model.classes.analyze;

import com.efounder.pub.util.Debug;
import com.f1j.ss.CellFormat;
import org.jdom.Element;
import jreportwh.jdof.classes.common.util.F1Util;
import jreportwh.jdof.classes.common.util.StringUtil;
import com.f1j.util.F1Exception;
import java.util.ResourceBundle;

/**
 * <p>Title: JAnalyzeReportModel</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class JAnalyzeReportModel extends AbstractReportDataModel
{
static ResourceBundle res = ResourceBundle.getBundle("jreport.model.classes.analyze.Language");
    Element paramsElement = null;
    Element monthElement = null;
    Element itemElement = null;
    int startMonth;
    int endMonth;
    int monthCount;
    int itemCount;
    int xOffset = 0;
    int yOffset = 0;
    int xBorderOffset = 0;
    int yBorderOffset = 0;

    public JAnalyzeReportModel()
    {
    }

    public void init()
    {
        /**@todo Override this jreport.model.classes.analyze.AbstractReportDataModel method*/
        paramsElement = mXMLObject.GetElementByName("PARAMS");
        mTitleRowCount = Integer.parseInt(paramsElement.getAttributeValue("TITLE_ROW_COUNT"));
        mHeadRowCount = Integer.parseInt(paramsElement.getAttributeValue("HEAD_ROW_COUNT"));
        mBodyRowCount = Integer.parseInt(paramsElement.getAttributeValue("BODY_ROW_COUNT"));
        mTailRowCount = Integer.parseInt(paramsElement.getAttributeValue("TAIL_ROW_COUNT"));
        mColCount = Integer.parseInt(paramsElement.getAttributeValue("COL_COUNT"));
        mReportNumber = paramsElement.getAttributeValue("BBZD_BH");
        mReportName = paramsElement.getAttributeValue("BBZD_MC");
        mReprotDate = paramsElement.getAttributeValue("BBZD_DATE");
        mTitle = paramsElement.getAttributeValue("TITLE");
        mRowCount = mTitleRowCount + mHeadRowCount + mBodyRowCount + mTailRowCount;
        this.SetCaption(res.getString("String_162")+paramsElement.getAttributeValue("FXB_BH")+"]"+paramsElement.getAttributeValue("FXB_MC")+">");
        //
        monthElement = mXMLObject.GetElementByName("MONTH");
        itemElement = mXMLObject.GetElementByName("ITEM");
        startMonth = Integer.parseInt(monthElement.getAttributeValue("START"));
        endMonth = Integer.parseInt(monthElement.getAttributeValue("END"));
        monthCount = endMonth - startMonth + 1;
        itemCount = itemElement.getAttributes().size();

        if (paramsElement.getAttributeValue("FXB_LHJ").equals("1")) { //在前
            xOffset += itemCount;
            xBorderOffset += itemCount;
        } else if (paramsElement.getAttributeValue("FXB_LHJ").equals("2")) { //在后
            xBorderOffset += itemCount;
        } else { //无

        }
        if (paramsElement.getAttributeValue("FXB_HHJ").equals("1")) { //在前
            yOffset++;
            yBorderOffset++;
        } else if (paramsElement.getAttributeValue("FXB_HHJ").equals("2")) { //在后
            yBorderOffset++;
        } else { //无

        }

    }

    public void drawReportTitle()
    {
        /**@todo Override this jreport.model.classes.analyze.AbstractReportDataModel method*/
        try {
            //Data
            ReportView.setText(0, 0, mTitle);
            //Format
            this.ReportView.setSelection(0, 0, 0, 0);
            CellFormat cellFormat = ReportView.getCellFormat();
            cellFormat.setFontSize(24 * 20);
            cellFormat.setFontBold(true);
            ReportView.setColWidthAuto(0, 0, 0, getColCount() - 1 + 2, true);
            cellFormat.setHorizontalAlignment(CellFormat.eHorizontalAlignmentCenterAcrossCells);
            cellFormat.setVerticalAlignment(CellFormat.eVerticalAlignmentCenter);
            ReportView.setCellFormat(cellFormat);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void drawReportHead()
    {
        /**@todo Override this jreport.model.classes.analyze.AbstractReportDataModel method*/
        try {
            //Data
            Element paramsElement = mXMLObject.GetElementByName("PARAMS");
            Element monthElement = mXMLObject.GetElementByName("MONTH");
            Element itemElement = mXMLObject.GetElementByName("ITEM");
            int startMonth = Integer.parseInt(monthElement.getAttributeValue("START"));
            int endMonth = Integer.parseInt(monthElement.getAttributeValue("END"));
            int monthCount = endMonth - startMonth + 1;
            int itemCount = itemElement.getAttributes().size();

            //列合计处理
            if (paramsElement.getAttributeValue("FXB_LHJ").equals("1")) { //在前
                monthCount++;
            } else if (paramsElement.getAttributeValue("FXB_LHJ").equals("2")) { //在后
                monthCount++;
            } else { //无

            }

            //单位编号表头
            ReportView.setSelection(mTitleRowCount, 0, mTitleRowCount + 1, 0);
            F1Util.setStdHeadCellFormat(ReportView);
            CellFormat cellFormat = ReportView.getCellFormat();
            cellFormat.setMergeCells(true);
            ReportView.setCellFormat(cellFormat);
            ReportView.setText(mTitleRowCount, 0, res.getString("String_182"));
            //单位名称表头
            ReportView.setSelection(mTitleRowCount, 1, mTitleRowCount + 1, 1);
            F1Util.setStdHeadCellFormat(ReportView);
            cellFormat = ReportView.getCellFormat();
            cellFormat.setMergeCells(true);
            ReportView.setCellFormat(cellFormat);
            ReportView.setText(mTitleRowCount, 1, res.getString("String_183"));
            //标题(由于一些参数在该方法获得，所以在此处设置)
            ReportView.setSelection(0, 0, 0, monthCount * itemCount - 1 + 2);
            cellFormat = ReportView.getCellFormat();
            cellFormat.setHorizontalAlignment(CellFormat.eHorizontalAlignmentCenterAcrossCells);
            ReportView.setCellFormat(cellFormat);
            //表头

            for (int i = 1; i <= monthCount; i++) {
                //月份
                //列合计处理
                if (paramsElement.getAttributeValue("FXB_LHJ").equals("1") && i == 1) { //在前
                    ReportView.setText(mTitleRowCount, (i - 1) * itemCount + 2, res.getString("String_185"));
                } else if (paramsElement.getAttributeValue("FXB_LHJ").equals("2") && i == monthCount) { //在后
                    ReportView.setText(mTitleRowCount, (i - 1) * itemCount + 2, res.getString("String_187"));
                } else { //无
                    String crtMonth = "";
                    if (paramsElement.getAttributeValue("FXB_LHJ").equals("1")) { //在前
                        crtMonth = StringUtil.leftPad( (Integer.toString(startMonth + i - 1 - 1)), '0', 2);
                    } else if (paramsElement.getAttributeValue("FXB_LHJ").equals("2")) { //在后
                        crtMonth = StringUtil.leftPad( (Integer.toString(startMonth + i - 1)), '0', 2);
                    } else { //无
                        crtMonth = StringUtil.leftPad( (Integer.toString(startMonth + i - 1)), '0', 2);
                    }
                    ReportView.setText(mTitleRowCount, (i - 1) * itemCount + 2, crtMonth);
                }
                //Format
                ReportView.setSelection(mTitleRowCount, (i - 1) * itemCount + 2, mTitleRowCount, i * itemCount - 1 + 2);
                F1Util.setStdHeadCellFormat(ReportView);
                if (itemCount > 1) {
                    CellFormat CF = ReportView.getCellFormat();
                    CF.setHorizontalAlignment(CellFormat.eHorizontalAlignmentCenterAcrossCells);
                    ReportView.setCellFormat(CF);
                }
                //当前月份包含的项目
                for (int j = 0; j < itemCount; j++) {
                    String crtItem = itemElement.getAttributeValue("ITEM" + j);
                    ReportView.setText(mTitleRowCount + 1, j + (i - 1) * itemCount + 2, crtItem);
                }
                //项目的格式
                for(int k=0;k<mColCount+2;k++){
                    ReportView.setSelection(mTitleRowCount+1,k+2,mTitleRowCount+1,k+2);
                    ReportView.setColWidthAuto(mTitleRowCount+1,k+2,mTitleRowCount+1,k+2,true);
                    F1Util.setStdHeadCellFormat(ReportView);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void drawReportBody()
    {
        /**@todo Override this jreport.model.classes.analyze.AbstractReportDataModel method*/
        try {
            //非合计数据
            for (int i = 1; i <= mBodyRowCount; i++) {
                Element crtRowElement = mXMLObject.GetElementByName("ROW" + (i - 1));
                String crtBH = "";
                String crtMC = "";
                //编号，名称
                crtBH = crtRowElement.getAttributeValue("BH");
                crtMC = crtRowElement.getAttributeValue("MC");
                ReportView.setText(mTitleRowCount + mHeadRowCount + i - 1 + yOffset, 0, crtBH);
                ReportView.setText(mTitleRowCount + mHeadRowCount + i - 1 + yOffset, 1, crtMC);
                //表体
                for (int j = 0; j < mColCount; j++) {
                    String crtCellValue = crtRowElement.getAttributeValue("COL" + j);
                    ReportView.setText(mTitleRowCount + mHeadRowCount + i - 1 + yOffset, j + xOffset + 2, crtCellValue);
                }
            }
            //
            if (paramsElement.getAttributeValue("FXB_HHJ").equals("1")) { //在前
                ReportView.setText(mTitleRowCount + mHeadRowCount, 1, res.getString("String_202"));
            } else if (paramsElement.getAttributeValue("FXB_HHJ").equals("2")) { //在后
                ReportView.setText(mTitleRowCount + mHeadRowCount + mBodyRowCount, 1, res.getString("String_204"));
            } else { //无
            }

            //列合计数据
            sumByCol();
            //行合计数据
            sumByRow();
            //Format

            //表体格式
            setNumberFormat(2);
            ReportView.setSelection(mTitleRowCount + mHeadRowCount, 0, mTitleRowCount + mHeadRowCount + mBodyRowCount - 1 + yBorderOffset, mColCount - 1 + xBorderOffset);
            F1Util.setStdBodyCellFormat(ReportView);
            ReportView.setColWidthAuto(0, 0, mRowCount - 1 + itemCount, 1, true);
            //Format(编号名称)
//            this.ReportView.setSelection(mTitleRowCount + mHeadRowCount, 0, mTitleRowCount + mHeadRowCount + mBodyRowCount - 1 + yBorderOffset, 0);
//            F1Util.setBorder(ReportView, CellFormat.eBorderDouble);
//            this.ReportView.setSelection(mTitleRowCount + mHeadRowCount, 1, mTitleRowCount + mHeadRowCount + mBodyRowCount - 1 + yBorderOffset, 1);
//            F1Util.setBorder(ReportView, CellFormat.eBorderDouble);
            ReportView.setRowHeightAuto(mTitleRowCount + mHeadRowCount, 0, mTitleRowCount + mHeadRowCount + mBodyRowCount-1+ yBorderOffset, mColCount - 1+ xBorderOffset, true);
            ReportView.setColWidthAuto(mTitleRowCount + mHeadRowCount, 0, mTitleRowCount + mHeadRowCount + mBodyRowCount-1+ yBorderOffset, mColCount - 1+ xBorderOffset, true);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void setNumberFormat(int gCount) throws F1Exception {
        String numberFormat="#,##0.00";
        ReportView.setSelection(mTitleRowCount + mHeadRowCount, gCount, mTitleRowCount + mHeadRowCount + mBodyRowCount-1+ yBorderOffset, mColCount-1+ xBorderOffset);

        CellFormat cellFormat=ReportView.getCellFormat();
        cellFormat.setValueFormat(numberFormat);
        cellFormat.setHorizontalAlignment(CellFormat.eHorizontalAlignmentRight);
        ReportView.setCellFormat(cellFormat);
        ReportView.setShowZeroValues(false);
    }

    /**
     * 行合计
     */
    private void sumByRow()
    {
        try {
            //
            for (int i = 0; i < mColCount + itemCount - 2; i++) {
                double sumValue = 0;
                for (int j = 0; j < mBodyRowCount; j++) {
                    double crtValue = ReportView.getNumber(mTitleRowCount + mHeadRowCount + j + 1, i + 2);
                    sumValue += crtValue;
                    Debug.PrintlnMessageToSystem("source:(" + (mTitleRowCount + mHeadRowCount + j + 1) + "," + (i + 2 + 1) + ")" + crtValue);
                }
                if (paramsElement.getAttributeValue("FXB_HHJ").equals("1")) { //在前
                    ReportView.setNumber(mTitleRowCount + mHeadRowCount, i + 2, sumValue);
                    Debug.PrintlnMessageToSystem("target:(" + (mTitleRowCount + mHeadRowCount) + "," + (i + 2) + ")" + sumValue);
                } else if (paramsElement.getAttributeValue("FXB_HHJ").equals("2")) { //在后
                    ReportView.setNumber(mTitleRowCount + mHeadRowCount + mBodyRowCount, i + 2, sumValue);
                } else { //无
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 列合计
     */
    private void sumByCol()
    {
        try {
            //
            if (paramsElement.getAttributeValue("FXB_LHJ").equals("1")) { //在前
                for (int i = 0; i < mBodyRowCount; i++) {
                    for (int k = 0; k < itemCount; k++) {
                        double sumValue = 0;
                        for (int j = 0; j < monthCount; j++) {
                            int x = mTitleRowCount + mHeadRowCount + i + yOffset;
                            int y = k + j * itemCount + 2 + xOffset;
                            double crtValue = ReportView.getNumber(x, y);
//                            System.out.println("source:("+x+","+y+")"+crtValue);
                            sumValue += crtValue;
                        }
                        ReportView.setNumber(mTitleRowCount + mHeadRowCount + i + yOffset, k + 2, sumValue);
//                        System.out.println("target:("+(mTitleRowCount + mHeadRowCount + i+yOffset)+","+(k+2)+")"+sumValue);
                    }
                }

            } else if (paramsElement.getAttributeValue("FXB_LHJ").equals("2")) { //在后
                for (int i = 0; i < mBodyRowCount; i++) {
                    for (int k = 0; k < itemCount; k++) {
                        double sumValue = 0;
                        for (int j = 0; j < monthCount; j++) {
                            int x = mTitleRowCount + mHeadRowCount + i + yOffset;
                            int y = k + j * itemCount + 2 + xOffset;
                            double crtValue = ReportView.getNumber(x, y);
                            Debug.PrintlnMessageToSystem("source:(" + x + "," + y + ")" + crtValue);
                            sumValue += crtValue;
                        }
                        ReportView.setNumber(mTitleRowCount + mHeadRowCount + i + yOffset, k + mColCount, sumValue);
                        Debug.PrintlnMessageToSystem("target:(" + (mTitleRowCount + mHeadRowCount + i + yOffset) + "," + (k + 2) + ")" + sumValue);
                    }
                }
            } else { //无
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void drawReportTail()
    {
    /**@todo Override this jreport.model.classes.analyze.AbstractReportDataModel method*/
    }
}
