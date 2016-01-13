package jreport.model.classes.analyze;

import org.jdom.Element;
import com.f1j.ss.CellFormat;
import jreportwh.jdof.classes.common.util.F1Util;
import jreportwh.jdof.classes.common.util.StringUtil;
import com.f1j.util.*;
import java.util.ResourceBundle;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class JPenetrateDataReportModel extends AbstractReportDataModel
{
static ResourceBundle res = ResourceBundle.getBundle("jreport.model.classes.analyze.Language");
    public JPenetrateDataReportModel()
    {
    }

    public void init()
    {
        /**@todo Override this jreport.model.classes.analyze.AbstractReportDataModel method*/
        Element paramsElement = mXMLObject.GetElementByName("PARAMS");
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
        this.SetCaption(res.getString("String_36")+paramsElement.getAttributeValue("TSB_BH")+"]"+paramsElement.getAttributeValue("TSB_MC")+">");
    }

    //绘制标题
    public void drawReportTitle()
    {
        /**@todo Override this jreport.model.classes.analyze.AbstractReportDataModel method*/
        try {
            //Data
            ReportView.setText(0, 0, mTitle);
            //Format
            this.ReportView.setSelection(0, 0, 0, getColCount() - 1);
            CellFormat cellFormat = ReportView.getCellFormat();
            cellFormat.setFontSize(24 * 20);
            cellFormat.setFontBold(true);
//            ReportView.setColWidthAuto(0, 0, 0, getColCount() - 1, true);
            cellFormat.setHorizontalAlignment(CellFormat.eHorizontalAlignmentCenterAcrossCells);
            cellFormat.setVerticalAlignment(CellFormat.eVerticalAlignmentCenter);
            ReportView.setCellFormat(cellFormat);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //绘制表头
    public void drawReportHead()
    {
        /**@todo Override this jreport.model.classes.analyze.AbstractReportDataModel method*/
        CellFormat CF = null;
        try {
            //Data
            Element monthElement = mXMLObject.GetElementByName("MONTH");
            Element itemElement = mXMLObject.GetElementByName("ITEM");
            int startMonth = Integer.parseInt(monthElement.getAttributeValue("START"));
            int endMonth = Integer.parseInt(monthElement.getAttributeValue("END"));
            int monthCount = endMonth - startMonth + 1;
            //
//            ReportView.setSelection(0, 0, 0, monthCount * itemCount - 1);
//            CellFormat cellFormat = ReportView.getCellFormat();
//            cellFormat.setMergeCells(true);
//            ReportView.setCellFormat(cellFormat);
            //项目
            int gCount=Integer.parseInt(itemElement.getAttributeValue("G_COUNT"));
            for(int i=0;i<gCount;i++){
                String crtValue=itemElement.getAttributeValue("G_ITEM"+i);
                ReportView.setText(mTitleRowCount, i, crtValue);
                ReportView.setSelection(mTitleRowCount,i,mTitleRowCount+1,i);
                F1Util.setStdHeadCellFormat(ReportView);
                CellFormat cellFormat=ReportView.getCellFormat();
                cellFormat.setMergeCells(true);
                ReportView.setCellFormat(cellFormat);
            }
            //月份和数据
            int tCount=Integer.parseInt(itemElement.getAttributeValue("T_COUNT"));
            for (int i = 1; i <= monthCount; i++) {
                String crtMonth = StringUtil.leftPad( (Integer.toString(startMonth + i - 1)), '0', 2);
                ReportView.setText(mTitleRowCount, (i - 1) * tCount+gCount, crtMonth);
                //
                ReportView.setSelection(mTitleRowCount, (i - 1) * tCount+gCount, mTitleRowCount, i * tCount - 1+gCount);
                F1Util.setStdHeadCellFormat(ReportView);
                CF = ReportView.getCellFormat();
                CF.setHorizontalAlignment(CellFormat.eHorizontalAlignmentCenterAcrossCells);
                ReportView.setCellFormat(CF);
                for (int j = 0; j < tCount; j++) {
                    String crtItem = itemElement.getAttributeValue("T_ITEM" + j);
                    ReportView.setText(mTitleRowCount + 1, j + (i - 1) * tCount+gCount, crtItem);
                    ReportView.setSelection(mTitleRowCount + 1, j + (i - 1) * tCount+gCount, mTitleRowCount + 1, j + (i - 1) * tCount+gCount);
                    F1Util.setStdHeadCellFormat(ReportView);
                }
            }
            //Format
//            for (int i = 0; i < monthCount * itemCount; i++) {
//                ReportView.setSelection(mTitleRowCount, i, mTitleRowCount + 1, i);
//                F1Util.setStdHeadCellFormat(ReportView);
//            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //绘制表体
    public void drawReportBody()
    {
        /**@todo Override this jreport.model.classes.analyze.AbstractReportDataModel method*/
        try {
            Element itemElement = mXMLObject.GetElementByName("ITEM");
            int gCount=Integer.parseInt(itemElement.getAttributeValue("G_COUNT"));
            int tCount=Integer.parseInt(itemElement.getAttributeValue("T_COUNT"));
            for (int i = 1; i <= mBodyRowCount; i++) {
                Element crtRowElement = mXMLObject.GetElementByName("ROW" + i);
                for (int j = 0; j < mColCount; j++) {
                    if(j<gCount){
                        String crtCellValue =crtRowElement.getAttributeValue("COL" + j);
                        ReportView.setText(mTitleRowCount + mHeadRowCount + i - 1, j, crtCellValue);
                    }else{
                        double crtCellValue = 0;
                        String crtValue=crtRowElement.getAttributeValue("COL" + j);
                        if(crtValue!=null&&crtValue.length()!=0){
                            crtCellValue=Double.parseDouble(crtValue);
                        }
                        ReportView.setNumber(mTitleRowCount + mHeadRowCount + i - 1, j, crtCellValue);
                    }
                }
            }
            //Format
            //设置显示格式
            setNumberFormat(gCount);
            ReportView.setSelection(mTitleRowCount + mHeadRowCount, 0, mTitleRowCount + mHeadRowCount + mBodyRowCount-1, mColCount - 1);
            F1Util.setStdBodyCellFormat(ReportView);
            ReportView.setRowHeightAuto(mTitleRowCount + mHeadRowCount, 0, mTitleRowCount + mHeadRowCount + mBodyRowCount-1, mColCount - 1, true);
            ReportView.setColWidthAuto(mTitleRowCount + mHeadRowCount, 0, mTitleRowCount + mHeadRowCount + mBodyRowCount-1, mColCount - 1, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setNumberFormat(int gCount) throws F1Exception {
        String numberFormat="#,##0.00";
        ReportView.setSelection(mTitleRowCount + mHeadRowCount, gCount, mTitleRowCount + mHeadRowCount + mBodyRowCount-1, mColCount-1);
        CellFormat cellFormat=ReportView.getCellFormat();
        cellFormat.setValueFormat(numberFormat);
        cellFormat.setHorizontalAlignment(CellFormat.eHorizontalAlignmentRight);
        ReportView.setCellFormat(cellFormat);
        ReportView.setShowZeroValues(false);
    }

    //绘制表尾
    public void drawReportTail()
    {
    /**@todo Override this jreport.model.classes.analyze.AbstractReportDataModel method*/
    }

    //绘制表格式
    public void setReportFormat()
    {
        /**@todo Override this jreport.model.classes.analyze.AbstractReportDataModel method*/

//        try {
//            ReportView.setColWidthAuto(0, 0, mRowCount, mColCount, true);
//        } catch (F1Exception e) {
//            e.printStackTrace();
//        }
    }
}
