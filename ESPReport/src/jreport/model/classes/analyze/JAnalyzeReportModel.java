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

        if (paramsElement.getAttributeValue("FXB_LHJ").equals("1")) { //��ǰ
            xOffset += itemCount;
            xBorderOffset += itemCount;
        } else if (paramsElement.getAttributeValue("FXB_LHJ").equals("2")) { //�ں�
            xBorderOffset += itemCount;
        } else { //��

        }
        if (paramsElement.getAttributeValue("FXB_HHJ").equals("1")) { //��ǰ
            yOffset++;
            yBorderOffset++;
        } else if (paramsElement.getAttributeValue("FXB_HHJ").equals("2")) { //�ں�
            yBorderOffset++;
        } else { //��

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

            //�кϼƴ���
            if (paramsElement.getAttributeValue("FXB_LHJ").equals("1")) { //��ǰ
                monthCount++;
            } else if (paramsElement.getAttributeValue("FXB_LHJ").equals("2")) { //�ں�
                monthCount++;
            } else { //��

            }

            //��λ��ű�ͷ
            ReportView.setSelection(mTitleRowCount, 0, mTitleRowCount + 1, 0);
            F1Util.setStdHeadCellFormat(ReportView);
            CellFormat cellFormat = ReportView.getCellFormat();
            cellFormat.setMergeCells(true);
            ReportView.setCellFormat(cellFormat);
            ReportView.setText(mTitleRowCount, 0, res.getString("String_182"));
            //��λ���Ʊ�ͷ
            ReportView.setSelection(mTitleRowCount, 1, mTitleRowCount + 1, 1);
            F1Util.setStdHeadCellFormat(ReportView);
            cellFormat = ReportView.getCellFormat();
            cellFormat.setMergeCells(true);
            ReportView.setCellFormat(cellFormat);
            ReportView.setText(mTitleRowCount, 1, res.getString("String_183"));
            //����(����һЩ�����ڸ÷�����ã������ڴ˴�����)
            ReportView.setSelection(0, 0, 0, monthCount * itemCount - 1 + 2);
            cellFormat = ReportView.getCellFormat();
            cellFormat.setHorizontalAlignment(CellFormat.eHorizontalAlignmentCenterAcrossCells);
            ReportView.setCellFormat(cellFormat);
            //��ͷ

            for (int i = 1; i <= monthCount; i++) {
                //�·�
                //�кϼƴ���
                if (paramsElement.getAttributeValue("FXB_LHJ").equals("1") && i == 1) { //��ǰ
                    ReportView.setText(mTitleRowCount, (i - 1) * itemCount + 2, res.getString("String_185"));
                } else if (paramsElement.getAttributeValue("FXB_LHJ").equals("2") && i == monthCount) { //�ں�
                    ReportView.setText(mTitleRowCount, (i - 1) * itemCount + 2, res.getString("String_187"));
                } else { //��
                    String crtMonth = "";
                    if (paramsElement.getAttributeValue("FXB_LHJ").equals("1")) { //��ǰ
                        crtMonth = StringUtil.leftPad( (Integer.toString(startMonth + i - 1 - 1)), '0', 2);
                    } else if (paramsElement.getAttributeValue("FXB_LHJ").equals("2")) { //�ں�
                        crtMonth = StringUtil.leftPad( (Integer.toString(startMonth + i - 1)), '0', 2);
                    } else { //��
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
                //��ǰ�·ݰ�������Ŀ
                for (int j = 0; j < itemCount; j++) {
                    String crtItem = itemElement.getAttributeValue("ITEM" + j);
                    ReportView.setText(mTitleRowCount + 1, j + (i - 1) * itemCount + 2, crtItem);
                }
                //��Ŀ�ĸ�ʽ
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
            //�Ǻϼ�����
            for (int i = 1; i <= mBodyRowCount; i++) {
                Element crtRowElement = mXMLObject.GetElementByName("ROW" + (i - 1));
                String crtBH = "";
                String crtMC = "";
                //��ţ�����
                crtBH = crtRowElement.getAttributeValue("BH");
                crtMC = crtRowElement.getAttributeValue("MC");
                ReportView.setText(mTitleRowCount + mHeadRowCount + i - 1 + yOffset, 0, crtBH);
                ReportView.setText(mTitleRowCount + mHeadRowCount + i - 1 + yOffset, 1, crtMC);
                //����
                for (int j = 0; j < mColCount; j++) {
                    String crtCellValue = crtRowElement.getAttributeValue("COL" + j);
                    ReportView.setText(mTitleRowCount + mHeadRowCount + i - 1 + yOffset, j + xOffset + 2, crtCellValue);
                }
            }
            //
            if (paramsElement.getAttributeValue("FXB_HHJ").equals("1")) { //��ǰ
                ReportView.setText(mTitleRowCount + mHeadRowCount, 1, res.getString("String_202"));
            } else if (paramsElement.getAttributeValue("FXB_HHJ").equals("2")) { //�ں�
                ReportView.setText(mTitleRowCount + mHeadRowCount + mBodyRowCount, 1, res.getString("String_204"));
            } else { //��
            }

            //�кϼ�����
            sumByCol();
            //�кϼ�����
            sumByRow();
            //Format

            //�����ʽ
            setNumberFormat(2);
            ReportView.setSelection(mTitleRowCount + mHeadRowCount, 0, mTitleRowCount + mHeadRowCount + mBodyRowCount - 1 + yBorderOffset, mColCount - 1 + xBorderOffset);
            F1Util.setStdBodyCellFormat(ReportView);
            ReportView.setColWidthAuto(0, 0, mRowCount - 1 + itemCount, 1, true);
            //Format(�������)
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
     * �кϼ�
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
                if (paramsElement.getAttributeValue("FXB_HHJ").equals("1")) { //��ǰ
                    ReportView.setNumber(mTitleRowCount + mHeadRowCount, i + 2, sumValue);
                    Debug.PrintlnMessageToSystem("target:(" + (mTitleRowCount + mHeadRowCount) + "," + (i + 2) + ")" + sumValue);
                } else if (paramsElement.getAttributeValue("FXB_HHJ").equals("2")) { //�ں�
                    ReportView.setNumber(mTitleRowCount + mHeadRowCount + mBodyRowCount, i + 2, sumValue);
                } else { //��
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * �кϼ�
     */
    private void sumByCol()
    {
        try {
            //
            if (paramsElement.getAttributeValue("FXB_LHJ").equals("1")) { //��ǰ
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

            } else if (paramsElement.getAttributeValue("FXB_LHJ").equals("2")) { //�ں�
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
            } else { //��
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
