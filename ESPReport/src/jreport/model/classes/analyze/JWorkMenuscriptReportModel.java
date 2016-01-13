package jreport.model.classes.analyze;

import java.io.*;
import java.util.*;

import java.awt.*;

import org.jdom.*;
import com.f1j.ss.*;
import com.f1j.util.*;
import jreportwh.jdof.classes.common.util.*;

/**
 * <p>Title: JWorkMenuscriptReportModel</p>
 * <p>Description: �����׸��Model
 * ���ںϼ�:
 * �ϼ�Ҳ��Ϊһ����Ŀ,û�б�ŵ�����,������Ŀʱ��,��hItemCount��ȫ��ȡ����,����
 * �Ƶ�ȡ����Ҳ��, ��getChildren()�����ķ������ж��ٻ��ƶ���,����ֻ���ƺϼƵ�ȡ
 * ����,��Ϊ�������е�ȡ���ж��ϼ�
 * </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class JWorkMenuscriptReportModel
    extends AbstractReportDataModel {

    static ResourceBundle res = ResourceBundle.getBundle("jreport.model.classes.analyze.Language");
    //����
    private String mDGSM_DGBH = null; //�׸���
    private String mDGSM_DGSM = null; //�׸�˵��
    private String mBBZD_BH = null; //������
    private String mBBZD_DATE = null; //��������
    private String mDGSM_DGLX = null; //�׸�����
    private String mDGSM_HJLY = null; //�ϼ���Դ
    private String mLBZD_BH = null; //�����
    private String mDGSM_HJWZ = null; //�ϼ�λ��
    private int mDGSM_XSDW = 0; //��ʾ��λ
    //��ͷ
    private Element mHeadElement = null; //��ͷ(��ű���)
    private Element hItemElement = null; //��Ŀ����
    private Element hValueElement = null; //��ֵ����
    private Element hSumElement = null; //�ϼƱ���
    //����
    private Element mBodyElement = null; //����(�����ֵ)
    private Element bItemElement = null; //��Ŀ��ֵ
    private Element bUnitElement = null; //��λ��ֵ
    private Element bSumElement = null; //�ϼ���ֵ
    //����
    private int mPageRowCount = 0; //��ҳ����
    private int mPageCount = 0; //ҳ��
    private int mUnitCount = 0; //��λ��(�������ϼ�)
    private int mItemCount = 0; //��Ŀ����
    private int mSumCount = 0; //�ϼ�����
    private int mValueCount = 0; //��ֵ����

    /**
     * ������ʼ��
     */
    public void init() {
        Element paramsElement = mXMLObject.GetElementByName("PARAMS");
        mTitleRowCount = Integer.parseInt(paramsElement.getAttributeValue("TITLE_ROW_COUNT"));
        mHeadRowCount  = Integer.parseInt(paramsElement.getAttributeValue("HEAD_ROW_COUNT"));
        mBodyRowCount  = Integer.parseInt(paramsElement.getAttributeValue("BODY_ROW_COUNT"));
        mTailRowCount  = Integer.parseInt(paramsElement.getAttributeValue("TAIL_ROW_COUNT"));
        mColCount      = Integer.parseInt(paramsElement.getAttributeValue("COL_COUNT"));

        mTitle         = paramsElement.getAttributeValue("TITLE");
        mDGSM_DGBH     = paramsElement.getAttributeValue("DGSM_DGBH");
        mDGSM_DGSM     = paramsElement.getAttributeValue("DGSM_DGSM");
        mBBZD_BH       = paramsElement.getAttributeValue("BBZD_BH");
        mBBZD_DATE     = paramsElement.getAttributeValue("BBZD_DATE");
        mDGSM_DGLX     = paramsElement.getAttributeValue("DGSM_DGLX");
        mDGSM_HJLY     = paramsElement.getAttributeValue("DGSM_HJLY");
        mLBZD_BH       = paramsElement.getAttributeValue("LBZD_BH");
        mDGSM_HJWZ     = paramsElement.getAttributeValue("DGSM_HJWZ");
        mDGSM_XSDW     = Integer.parseInt(paramsElement.getAttributeValue("DGSM_XSDW"));
        mPageRowCount  = Integer.parseInt(paramsElement.getAttributeValue("PAGE_ROW_COUNT"));
        mHeadElement   = mXMLObject.GetElementByName("HEAD");
        mBodyElement   = mXMLObject.GetElementByName("BODY");

        hItemElement   = mXMLObject.GetElementByName("H_ITEM");
        hValueElement  = mXMLObject.GetElementByName("H_VALUE");
        hSumElement    = mXMLObject.GetElementByName("H_SUM");
        mItemCount     = Integer.parseInt(hItemElement.getAttributeValue("COUNT"));
        mSumCount      = Integer.parseInt(hSumElement.getAttributeValue("COUNT"));
        mValueCount    = Integer.parseInt(hValueElement.getAttributeValue("COUNT"));

        bItemElement   = mXMLObject.GetElementByName("B_ITEM");
        bUnitElement   = mXMLObject.GetElementByName("B_UNIT");
        bSumElement    = mXMLObject.GetElementByName("B_SUM");
        mUnitCount     = Integer.parseInt(bUnitElement.getAttributeValue("UNIT_COUNT"));
        mPageCount     = getPageCount();

        //����
        SetCaption(res.getString("String_90") + mDGSM_DGSM);

        //����ȡ�õ�����
        String dataStr = mXMLObject.GetRootXMLString();
        try {
            FileOutputStream FOS = new FileOutputStream("d:\\WorkMenuscript.xml");
            FOS.write(dataStr.getBytes());
            FOS.flush();
            FOS.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * ������ʾ��һЩ�������
     */
    private void setReportView(int rowCount, int colCount) {
        try {
            //�����������
            int maxRow = rowCount - 1;
            int maxCol = colCount - 1;
            if (maxRow >= 0)
                ReportView.setMaxRow(maxRow);
            if (maxCol >= 0)
                ReportView.setMaxCol(maxCol);
        } catch (F1Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * ������
     */
    public void drawReportTitle() {
        try {
            ReportView.setText(0, 0, mTitle);
            if (mDGSM_DGLX.equals("1")) { //��λ����
                ReportView.setSelection(0, 0, 0, getColCount() - 1);
            } else if (mDGSM_DGLX.equals("2")) { //��λ���У�����Ŀ����
                ReportView.setSelection(0, 0, 0, mItemCount + mValueCount);
            } else if (mDGSM_DGLX.equals("3")) { //��λ���У�����λ����
                ReportView.setSelection(0, 0, 0, mItemCount + mValueCount);
            } else if (mDGSM_DGLX.equals("4")) { //���е���
                ReportView.setSelection(0, 0, 0, mPageRowCount - 1);
            }
            CellFormat cellFormat = ReportView.getCellFormat();
            cellFormat.setFontSize(20 * 20);
            cellFormat.setFontBold(true);
            cellFormat.setHorizontalAlignment(CellFormat.eHorizontalAlignmentCenterAcrossCells);
            cellFormat.setVerticalAlignment(CellFormat.eVerticalAlignmentCenter);
            ReportView.setCellFormat(cellFormat);
            ReportView.setRowHeight(0, 20 * 50);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * ������
     */
    public void drawReportBody() {
        try {
            switch (Integer.parseInt(mDGSM_DGLX)) {
                case 1:
                    drawReportBody1(); //��λ����
                    break;
                case 2:
                    drawReportBody2(); //��λ���У�����Ŀ����
                    break;
                case 3:
                    drawReportBody3(); //��λ���У�����λ����
                    break;
                case 4:
                    drawReportBody4(); //���е���
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * ��λ����
     */
    private void drawReportBody1() throws Exception {
        //��ҳ����
        int rowIndex = mTitleRowCount;
        int colIndex = 0;
        for (int i = 0; i < mPageCount; i++) {
            Vector unitList = getUnitListByPage(i);
            drawBodyPage1(rowIndex, colIndex, i, unitList);
            rowIndex += mPageRowCount + 3; //��Ŀ��+2�б�ͷ+1�е�ҳ���
        }
        setReportFormat1();
    }

    /**
     * ��λ����ʱ�ĸ�ʽ��ʾ
     * @throws Exception
     */
    private void setReportFormat1() throws Exception {
        int rowCount = (mTitleRowCount + mHeadRowCount + mPageRowCount) * mPageCount;
        int colCount = mColCount;
        ReportView.setShowZeroValues(false);
        ReportView.setColWidthAuto(mTitleRowCount, 0, rowCount - 1, colCount - 1, true);
        setReportView(rowCount, colCount);
    }

    /**
     * ��λ����.��ҳ
     */
    private void drawBodyPage1(int rowIndex, int colIndex, int pageIndex, Vector unitList) throws Exception {
        //��Ŀ��
        drawItem1(rowIndex, colIndex);
        //��λ������
        drawUnitByPage1(rowIndex, colIndex, pageIndex, unitList);
        //��ʽ
        setReportFormatByPage1(rowIndex, colIndex);
    }

    /**
     * ��λ����.��Ŀ
     */
    private void drawItem1(int rowIndex, int colIndex) throws Exception {
        drawItemHead1(rowIndex, colIndex);
        drawItemValue1(rowIndex, colIndex);
        setItemFormat1(rowIndex, colIndex);
    }

    /**
     * ��λ����.��Ŀ����
     */
    private void drawItemHead1(int rowIndex, int colIndex) throws F1Exception {
        for (int i = 0; i < mItemCount; i++) {
            String itemValue = hItemElement.getAttributeValue("VALUE" + i);
            int x = rowIndex;
            int y = colIndex + i;
            ReportView.setText(x, y, itemValue);
            ReportView.setSelection(x, y, x + 1, y);
            CellFormat CF = ReportView.getCellFormat();
            CF.setMergeCells(true);
            CF.setVerticalAlignment(CellFormat.eVerticalAlignmentCenter);
            CF.setHorizontalAlignment(CellFormat.eHorizontalAlignmentCenter);
            CF.setTopBorder(CF.eBorderMedium);
            CF.setBottomBorder(CF.eBorderMedium);
            CF.setLeftBorder(CF.eBorderThin);
            CF.setRightBorder(CF.eBorderMedium);
            CF.setTopBorderColor(new Color(0X87, 0XB1, 0XE1).getRGB());
            CF.setBottomBorderColor(new Color(0X87, 0XB1, 0XE1).getRGB());
            CF.setLeftBorderColor(new Color(0X87, 0XB1, 0XE1).getRGB());
            CF.setRightBorderColor(new Color(0X87, 0XB1, 0XE1).getRGB());
            CF.setPattern(CellFormat.ePatternSolid);
            CF.setPatternFG(0xCEDDF0);
            ReportView.setRowHeight(x, 17 * 18);
            ReportView.setCellFormat(CF);
        }
    }

    /**
     * ��λ����.��Ŀ����
     */
    private void drawItemValue1(int rowIndex, int colIndex) throws F1Exception {
        int itemCount = bItemElement.getChildren().size();
        for (int i = 0; i < itemCount; i++) {
            Element crtItemElmt = bItemElement.getChild(XMLUtil.PREFIX + "COL" + i);
            for (int k = 1; k <= mPageRowCount; k++) {
                String itemValue = crtItemElmt.getAttributeValue("ROW" + k);
                int x = rowIndex + k + 1;
                int y = colIndex + i;
                ReportView.setText(x, y, itemValue);
                ReportView.setSelection(x, y, x, y);
                setStringFormat(k);
                ReportView.setRowHeight(x, 17 * 18);
            }
        }
    }

    /**
     * ��λ����.��Ŀ��ʽ
     * @param rowIndex
     * @param colIndex
     * @throws F1Exception
     */
    private void setItemFormat1(int rowIndex, int colIndex) throws F1Exception {
        ReportView.setColWidthAuto(rowIndex, colIndex, rowIndex + mPageCount * mPageRowCount, colIndex + mItemCount, true);
    }

    /**
     * ��λ����.��ҳ(��������Ҳ��Ϊһ����λ)
     */
    private void drawUnitByPage1(int rowIndex, int colIndex, int pageIndex, Vector unitList) throws Exception {
        int unitIndex = 0;
        for (int i = 0; i < unitList.size(); i++) {
            Element crtUnitElement = (Element) unitList.get(i);
            if (crtUnitElement != null) {
                int x = rowIndex;
                int y = colIndex;
                if (pageIndex == 0 && unitIndex > 0 && pageIndex == 0 && mDGSM_HJWZ.equals("Q")) { //�ϼ���ǰ
                    y += +mItemCount + (unitIndex - 1) * mValueCount + mSumCount;
                } else {
                    y += +mItemCount + unitIndex * mValueCount;
                }
                drawUnit1(x, y, pageIndex, unitIndex, crtUnitElement);
                colIndex = colIndex + mValueCount; //add
            }
        }
    }

    /**
     * ��λ����.��λ
     */
    private void drawUnit1(int rowIndex, int colIndex, int pageIndex, int unitIndex, Element unitElement) throws Exception {
        int x = rowIndex;
        int y = colIndex;
        int itemCount = unitElement.getChildren().size();
        drawUnitName1(x, y, itemCount, unitElement);
        drawUnitItem1(x, y, pageIndex, unitIndex, unitElement);
    }

    /**
     * ��λ����.��λ����
     * @param rowIndex
     * @param colIndex
     * @param itemCount
     * @param unitElement
     * @throws java.lang.Exception
     */
    private void drawUnitName1(int rowIndex, int colIndex, int itemCount, Element unitElement) throws Exception {
        String name = unitElement.getAttributeValue("MC");
        ReportView.setText(rowIndex, colIndex, name);
        ReportView.setSelection(rowIndex, colIndex, rowIndex, colIndex + itemCount - 1);
        CellFormat CF = ReportView.getCellFormat();
        CF.setHorizontalAlignment(CellFormat.eHorizontalAlignmentCenterAcrossCells);
        CF.setVerticalAlignment(CellFormat.eVerticalAlignmentCenter);
        CF.setTopBorder(CF.eBorderMedium);
        CF.setBottomBorder(CF.eBorderThin);
        CF.setLeftBorder(CF.eBorderThin);
        CF.setRightBorder(CF.eBorderThin);
        CF.setTopBorderColor(new Color(0X87, 0XB1, 0XE1).getRGB());
        CF.setBottomBorderColor(new Color(0X87, 0XB1, 0XE1).getRGB());
        CF.setLeftBorderColor(new Color(0X87, 0XB1, 0XE1).getRGB());
        CF.setRightBorderColor(new Color(0X87, 0XB1, 0XE1).getRGB());
        CF.setPattern(CellFormat.ePatternSolid);
        CF.setPatternFG(0xCEDDF0);
        ReportView.setCellFormat(CF);
        for (int i = 0; i < itemCount; i++) {
            ReportView.setColWidthAuto(rowIndex, colIndex + i, rowIndex, colIndex + i, true);
        }
    }

    /**
     * ��λ����.��λ��Ŀ
     * @param rowIndex
     * @param colIndex
     * @param pageIndex
     * @param unitIndex
     * @param unitElement
     * @throws java.lang.Exception
     */
    private void drawUnitItem1(int rowIndex, int colIndex, int pageIndex, int unitIndex, Element unitElement) throws Exception {
        int itemIndex = 0;
        for (Iterator iterator = unitElement.getChildren().iterator(); iterator.hasNext(); itemIndex++) {
            Element crtItemElement = (Element) iterator.next();
            //��λ����Ŀ�ı���
            drawUnitItemHead1(rowIndex, colIndex, pageIndex, unitIndex, itemIndex);
            //��λ��Ŀ����
            drawUnitItemValue1(rowIndex + 1, colIndex + itemIndex, unitIndex, itemIndex, crtItemElement);
        }
    }

    /**
     * ��λ����.��λ����Ŀ�ı���
     * @param rowIndex
     * @param colIndex
     * @param pageIndex
     * @param unitIndex
     * @param itemIndex
     * @throws Exception
     */
    private void drawUnitItemHead1(int rowIndex, int colIndex, int pageIndex, int unitIndex, int itemIndex) throws Exception {
        String crtItemName = "";
        if (pageIndex == 0 && unitIndex == 0 && mDGSM_HJWZ.equals("Q")) { //�ϼ���ǰ
            crtItemName = hSumElement.getAttributeValue("VALUE" + itemIndex);
        } else if (pageIndex == mPageCount && unitIndex == (mUnitCount - 1) && mDGSM_HJWZ.equals("H")) { //�ϼ��ں�
            crtItemName = hSumElement.getAttributeValue("VALUE" + itemIndex);
        } else {
            crtItemName = hValueElement.getAttributeValue("VALUE" + itemIndex);
        }
        ReportView.setText(rowIndex + 1, colIndex + itemIndex, crtItemName);
        ReportView.setSelection(rowIndex + 1, colIndex + itemIndex, rowIndex + 1, colIndex + itemIndex);
        CellFormat cellFormat = ReportView.getCellFormat();
        cellFormat.setHorizontalAlignment(CellFormat.eHorizontalAlignmentCenter);
        cellFormat.setVerticalAlignment(CellFormat.eVerticalAlignmentCenter);
        cellFormat.setTopBorder(CellFormat.eBorderMedium);
        cellFormat.setBottomBorder(CellFormat.eBorderMedium);
        cellFormat.setLeftBorder(CellFormat.eBorderThin);
        cellFormat.setRightBorder(CellFormat.eBorderThin);
        cellFormat.setTopBorderColor(new Color(0X87, 0XB1, 0XE1).getRGB());
        cellFormat.setBottomBorderColor(new Color(0X87, 0XB1, 0XE1).getRGB());
        cellFormat.setLeftBorderColor(new Color(0X87, 0XB1, 0XE1).getRGB());
        cellFormat.setRightBorderColor(new Color(0X87, 0XB1, 0XE1).getRGB());
        cellFormat.setPattern(CellFormat.ePatternSolid);
        cellFormat.setPatternFG(0xCEDDF0);
        ReportView.setCellFormat(cellFormat);
        ReportView.setRowHeight(rowIndex + 1, 17 * 18);
    }

    /**
     * ��λ����.��λ��Ŀ����ֵ
     */
    private void drawUnitItemValue1(int rowIndex, int colIndex, int unitIndex, int itemIndex, Element itemElement) throws Exception {
        for (int k = 1; k <= mPageRowCount; k++) {
            double itemValue = getUnitItemValue1(k, itemElement);
            int x = rowIndex + k;
            int y = colIndex;
            ReportView.setNumber(x, y, itemValue);
            ReportView.setSelection(x, y, x, y);
            setNumberFormat(k);
            ReportView.setRowHeight(x, 17 * 18);
        }
        ReportView.setColWidthAuto(rowIndex, colIndex, rowIndex + mPageRowCount, colIndex, true);
    }

    /**
     * ��õ�λ����.��λ��Ŀ����ֵ
     * @param rowIndex
     * @param itemElement
     * @return
     */
    private double getUnitItemValue1(int rowIndex, Element itemElement) {
        String value = "";
        if (itemElement.getAttributeValue("ROW" + rowIndex) != null) {
            value = itemElement.getAttributeValue("ROW" + rowIndex);
        }
        if (value == null || value.trim().equals("")) {
            value = "0";
        }
        return Double.parseDouble(value);
    }

    /**
     * ��λ����.�����ʽ
     * @param rowIndex
     * @param colIndex
     */
    private void setReportFormatByPage1(int rowIndex, int colIndex) {
        try {
            ReportView.setSelection(rowIndex, colIndex, rowIndex + mPageRowCount + 1, mColCount - 1);
            CellFormat cellFormat = ReportView.getCellFormat();
            cellFormat.setTopBorder(CellFormat.eBorderMedium);
            cellFormat.setBottomBorder(CellFormat.eBorderMedium);
            cellFormat.setLeftBorder(CellFormat.eBorderMedium);
            cellFormat.setRightBorder(CellFormat.eBorderMedium);
            cellFormat.setPattern(CellFormat.ePatternSolid);
            cellFormat.setTopBorderColor(new Color(0X87, 0XB1, 0XE1).getRGB());
            cellFormat.setBottomBorderColor(new Color(0X87, 0XB1, 0XE1).getRGB());
            cellFormat.setLeftBorderColor(new Color(0X87, 0XB1, 0XE1).getRGB());
            cellFormat.setRightBorderColor(new Color(0X87, 0XB1, 0XE1).getRGB());
            cellFormat.setVerticalAlignment(CellFormat.eVerticalAlignmentCenter);
            ReportView.setCellFormat(cellFormat);
            ReportView.setColWidthAuto(rowIndex, colIndex, rowIndex + mPageRowCount + 1, colIndex + mItemCount + mDGSM_XSDW * mValueCount - 1, true);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * ��λ����.����Ŀ����
     * @throws java.lang.Exception
     */
    private void drawReportBody2() throws Exception {
        drawReportHead2(mTitleRowCount, 0);
        Vector unitList = getAllUnitList();
        for (int i = 1; i <= mPageRowCount; i++) {
            drawItem2(mTitleRowCount + i + mUnitCount * (i - 1), 0, i);
            drawUnit2(mTitleRowCount + i + mUnitCount * (i - 1), mItemCount, i, unitList);
        }
        setReportFormat2();
        
        
        for (int i = 1; i <= mPageRowCount; i++) {
            int itemIndex = 0;
            int totalUnitCount = this.getAllUnitList().size();
            for (itemIndex=0; itemIndex<bItemElement.getChildren().size(); itemIndex++) {
                ReportView.setSelection(mTitleRowCount + i + mUnitCount * (i - 1), itemIndex, mTitleRowCount + i + mUnitCount * (i - 1) + totalUnitCount - 1, itemIndex);
                F1Util.setMergeCells(ReportView, true); //�ϲ���Ԫ��
            }
        }
    }

    /**
     * ��λ����.����Ŀ����.�����ʽ
     * @throws F1Exception
     */
    private void setReportFormat2() throws F1Exception {
        int unitCount = getAllUnitList().size();
        mHeadRowCount = 1;
        mBodyRowCount = unitCount * mPageRowCount + 1;
        int rowCount  = mTitleRowCount + mHeadRowCount + unitCount * mPageRowCount;
        int colCount  = mItemCount + mValueCount + 1;

        ReportView.setShowZeroValues(false);
        ReportView.setSelection(mTitleRowCount, 0, rowCount - 1, colCount - 1);
        CellFormat cellFormat = ReportView.getCellFormat();
        cellFormat.setTopBorder(CellFormat.eBorderMedium);
        cellFormat.setBottomBorder(CellFormat.eBorderMedium);
        cellFormat.setLeftBorder(CellFormat.eBorderMedium);
        cellFormat.setRightBorder(CellFormat.eBorderMedium);
        cellFormat.setPattern(CellFormat.ePatternSolid);
        cellFormat.setTopBorderColor(new Color(0X87, 0XB1, 0XE1).getRGB());
        cellFormat.setBottomBorderColor(new Color(0X87, 0XB1, 0XE1).getRGB());
        cellFormat.setLeftBorderColor(new Color(0X87, 0XB1, 0XE1).getRGB());
        cellFormat.setRightBorderColor(new Color(0X87, 0XB1, 0XE1).getRGB());
        cellFormat.setVerticalAlignment(CellFormat.eVerticalAlignmentCenter);
        ReportView.setCellFormat(cellFormat);
        ReportView.setColWidthAuto(mTitleRowCount, 0, rowCount - 1, colCount - 1, true);
        ReportView.setMaxCol(colCount - 1);
        ReportView.setMaxRow(rowCount - 1);
    }

    /**
     * ��λ����.����Ŀ����.��ͷ
     */
    private void drawReportHead2(int rowIndex, int colIndex) throws Exception {
        drawItemHead3(rowIndex, colIndex);
        ReportView.setText(rowIndex, colIndex + mItemCount, "��λ");
        ReportView.setSelection(rowIndex, colIndex + mItemCount, rowIndex, colIndex + mItemCount);
        CellFormat cellFormat = ReportView.getCellFormat();
        cellFormat.setHorizontalAlignment(CellFormat.eHorizontalAlignmentCenter);
        cellFormat.setVerticalAlignment(CellFormat.eVerticalAlignmentCenter);
        cellFormat.setTopBorder(CellFormat.eBorderMedium);
        cellFormat.setBottomBorder(CellFormat.eBorderMedium);
        cellFormat.setLeftBorder(CellFormat.eBorderThin);
        cellFormat.setRightBorder(CellFormat.eBorderThin);
        cellFormat.setPattern(CellFormat.ePatternSolid);
        cellFormat.setTopBorderColor(new Color(0X87, 0XB1, 0XE1).getRGB());
        cellFormat.setBottomBorderColor(new Color(0X87, 0XB1, 0XE1).getRGB());
        cellFormat.setLeftBorderColor(new Color(0X87, 0XB1, 0XE1).getRGB());
        cellFormat.setRightBorderColor(new Color(0X87, 0XB1, 0XE1).getRGB());
        cellFormat.setPatternFG(0xCEDDF0);
        ReportView.setCellFormat(cellFormat);
        ReportView.setRowHeight(rowIndex, 17 * 18);
        for (int i = 0; i < mValueCount; i++) {
            int x = rowIndex;
            int y = colIndex + i + mItemCount + 1;
            String crtItemName = hValueElement.getAttributeValue("VALUE" + i);
            ReportView.setText(x, y, crtItemName);
            ReportView.setSelection(x, y, x, y);
            cellFormat = ReportView.getCellFormat();
            cellFormat.setHorizontalAlignment(CellFormat.eHorizontalAlignmentCenter);
            cellFormat.setVerticalAlignment(CellFormat.eVerticalAlignmentCenter);
            cellFormat.setTopBorder(CellFormat.eBorderMedium);
            cellFormat.setBottomBorder(CellFormat.eBorderMedium);
            cellFormat.setLeftBorder(CellFormat.eBorderThin);
            cellFormat.setRightBorder(CellFormat.eBorderThin);
            cellFormat.setPattern(CellFormat.ePatternSolid);
            cellFormat.setTopBorderColor(new Color(0X87, 0XB1, 0XE1).getRGB());
            cellFormat.setBottomBorderColor(new Color(0X87, 0XB1, 0XE1).getRGB());
            cellFormat.setLeftBorderColor(new Color(0X87, 0XB1, 0XE1).getRGB());
            cellFormat.setRightBorderColor(new Color(0X87, 0XB1, 0XE1).getRGB());
            cellFormat.setPatternFG(0xCEDDF0);
            ReportView.setCellFormat(cellFormat);
        }
    }

    /**
     * ��λ����.����Ŀ����.��Ŀ
     */
    private void drawItem2(int rowIndex, int colIndex, int rowIndex2) throws Exception {
        int itemIndex = 0;
        int totalUnitCount = this.getAllUnitList().size();
        for (Iterator iterator = bItemElement.getChildren().iterator(); iterator.hasNext(); itemIndex++) {
            Element crtItemElmt = (Element) iterator.next();
            String crtItemValue = crtItemElmt.getAttributeValue("ROW" + rowIndex2, "");
            ReportView.setText(rowIndex, colIndex + itemIndex, crtItemValue);
            ReportView.setSelection(rowIndex, colIndex + itemIndex, rowIndex + totalUnitCount - 1, colIndex + itemIndex);
            ReportView.setColWidthAuto(rowIndex, colIndex + itemIndex, rowIndex + totalUnitCount - 1, colIndex + itemIndex, true);
//            F1Util.setMergeCells(ReportView, true); //�ϲ���Ԫ��
            setStringFormat(itemIndex);
            ReportView.setRowHeight(rowIndex, 17 * 18);
        }
    }

    /**
     * ��λ����.����Ŀ����.��λ
     */
    private void drawUnit2(int rowIndex, int colIndex, int rowIndex2, Vector unitList) throws Exception {
        int unitIndex = 0;
        for (int j = 0, n = unitList.size(); j < n; j++) {
            Element crtUnitElmt = (Element) unitList.get(j);
            String name = crtUnitElmt.getAttributeValue("MC");
            ReportView.setText(rowIndex + unitIndex, colIndex, name);
            ReportView.setSelection(rowIndex + unitIndex, colIndex, rowIndex + unitIndex, colIndex);
            ReportView.setColWidthAuto(rowIndex + unitIndex, colIndex, rowIndex + unitIndex, colIndex, true);
            CellFormat CF = ReportView.getCellFormat();
        //    CF.setMergeCells(true);
            CF.setVerticalAlignment(CellFormat.eVerticalAlignmentCenter);
            CF.setTopBorder(CF.eBorderThin);
            CF.setBottomBorder(CF.eBorderThin);
            CF.setLeftBorder(CF.eBorderThin);
            CF.setRightBorder(CF.eBorderThin);
            CF.setTopBorderColor(new Color(0X87, 0XB1, 0XE1).getRGB());
            CF.setBottomBorderColor(new Color(0X87, 0XB1, 0XE1).getRGB());
            CF.setLeftBorderColor(new Color(0X87, 0XB1, 0XE1).getRGB());
            CF.setRightBorderColor(new Color(0X87, 0XB1, 0XE1).getRGB());
            CF.setPatternFG(java.awt.Color.white.getRGB());

            ReportView.setCellFormat(CF);
            for (int i = 0; i < mValueCount; i++) {
                Element crtItemElmt = crtUnitElmt.getChild(XMLUtil.PREFIX + "COL" + i);
                if (crtItemElmt != null) {
                    String crtItemValue = crtItemElmt.getAttributeValue("ROW" + rowIndex2, "0");
                    ReportView.setNumber(rowIndex + unitIndex, colIndex + i + 1, Double.parseDouble(crtItemValue));
                }
                ReportView.setSelection(rowIndex + unitIndex, colIndex + i + 1, rowIndex + unitIndex, colIndex + i + 1);
                setNumberFormat(i);
                ReportView.setRowHeight(rowIndex + unitIndex, 17 * 18);
            }
            rowIndex = rowIndex + 1; //add
        }
    }

    /**
     * ��λ����.����λ����
     */
    private void drawReportBody3() throws Exception {
        drawReportHead3(mTitleRowCount, 1);
        //�����λ����
        Vector allUnitList = getAllUnitList();
        int unitIndex = 0;
        for (int j = 0; j < allUnitList.size(); j++) {
            Element crtUnitElmt = (Element) allUnitList.get(j);
            int x = mTitleRowCount + j * mPageRowCount;
            int y = 0;
            drawUnit3(x, y, unitIndex, crtUnitElmt);
        }
        setReportFormat3();
        for (int j = 0; j < allUnitList.size(); j++) {
            Element crtUnitElmt = (Element) allUnitList.get(j);
            int x = mTitleRowCount + j * mPageRowCount;
            int y = 0;
            drawUnitHead3(crtUnitElmt, x, y);
        }
        
    }

    private void setReportFormat3() throws F1Exception {
        int unitCount = getAllUnitList().size(); //�������޺ϼ�
        mHeadRowCount = 1;
        mBodyRowCount = unitCount * mPageRowCount + 1;
        int rowCount  = mTitleRowCount + mHeadRowCount + unitCount * mPageRowCount;
        int colCount  = mItemCount + mValueCount + 1;

        ReportView.setSelection(mTitleRowCount, 0, rowCount - 1, colCount - 1);
        CellFormat cellFormat = ReportView.getCellFormat();
        cellFormat.setTopBorder(CellFormat.eBorderMedium);
        cellFormat.setBottomBorder(CellFormat.eBorderMedium);
        cellFormat.setLeftBorder(CellFormat.eBorderMedium);
        cellFormat.setRightBorder(CellFormat.eBorderMedium);
        cellFormat.setPattern(CellFormat.ePatternSolid);
        cellFormat.setTopBorderColor(new Color(0X87, 0XB1, 0XE1).getRGB());
        cellFormat.setBottomBorderColor(new Color(0X87, 0XB1, 0XE1).getRGB());
        cellFormat.setLeftBorderColor(new Color(0X87, 0XB1, 0XE1).getRGB());
        cellFormat.setRightBorderColor(new Color(0X87, 0XB1, 0XE1).getRGB());
        ReportView.setCellFormat(cellFormat);
        ReportView.setShowZeroValues(false);
        ReportView.setColWidthAuto(mTitleRowCount, 0, rowCount - 1, colCount - 1, true);
//        ReportView.setColWidth(0, 200);
//        ReportView.setColWidth(1, 100);
//        ReportView.setColWidth(2, 300);
        
        setReportView(rowCount, colCount);
    }

    /**
     * ��λ����.����λ����.��ͷ
     */
    private void drawReportHead3(int rowIndex, int colIndex) throws Exception {
        ReportView.setText(mTitleRowCount, 0, res.getString("String_125"));
        ReportView.setSelection(mTitleRowCount, 0, mTitleRowCount, 0);
        CellFormat cellFormat = ReportView.getCellFormat();
        cellFormat.setHorizontalAlignment(CellFormat.eHorizontalAlignmentCenter);
        cellFormat.setVerticalAlignment(CellFormat.eVerticalAlignmentCenter);
        cellFormat.setTopBorder(CellFormat.eBorderMedium);
        cellFormat.setBottomBorder(CellFormat.eBorderMedium);
        cellFormat.setLeftBorder(CellFormat.eBorderThin);
        cellFormat.setRightBorder(CellFormat.eBorderThin);
        cellFormat.setPattern(CellFormat.ePatternSolid);
        cellFormat.setTopBorderColor(new Color(0X87, 0XB1, 0XE1).getRGB());
        cellFormat.setBottomBorderColor(new Color(0X87, 0XB1, 0XE1).getRGB());
        cellFormat.setLeftBorderColor(new Color(0X87, 0XB1, 0XE1).getRGB());
        cellFormat.setRightBorderColor(new Color(0X87, 0XB1, 0XE1).getRGB());
        cellFormat.setPatternFG(0xCEDDF0);
        ReportView.setCellFormat(cellFormat);
        ReportView.setRowHeight(mTitleRowCount, 17 * 18);
        drawItemHead3(rowIndex, colIndex);
        for (int i = 0; i < mValueCount; i++) {
            int x = rowIndex;
            int y = colIndex + i + mItemCount;
            String crtItemName = hValueElement.getAttributeValue("VALUE" + i);
            ReportView.setText(x, y, crtItemName);
            ReportView.setSelection(x, y, x, y);
            cellFormat.setBottomBorder(CellFormat.eBorderMedium);
            cellFormat.setLeftBorder(CellFormat.eBorderThin);
            cellFormat.setRightBorder(CellFormat.eBorderThin);
            cellFormat.setPattern(CellFormat.ePatternSolid);
            cellFormat.setTopBorderColor(new Color(0X87, 0XB1, 0XE1).getRGB());
            cellFormat.setBottomBorderColor(new Color(0X87, 0XB1, 0XE1).getRGB());
            cellFormat.setLeftBorderColor(new Color(0X87, 0XB1, 0XE1).getRGB());
            cellFormat.setRightBorderColor(new Color(0X87, 0XB1, 0XE1).getRGB());
            cellFormat.setPatternFG(0xCEDDF0);
            ReportView.setCellFormat(cellFormat);
        }
    }

    /**
     * ��λ����.����λ����.��Ŀ����
     */
    private void drawItemHead3(int rowIndex, int colIndex) throws F1Exception {
        for (int i = 0; i < mItemCount; i++) {
            String itemValue = hItemElement.getAttributeValue("VALUE" + i);
            int x = rowIndex;
            int y = colIndex + i;
            ReportView.setText(x, y, itemValue);
            ReportView.setSelection(x, y, x, y);
            CellFormat cellFormat = ReportView.getCellFormat();
            cellFormat.setHorizontalAlignment(CellFormat.eHorizontalAlignmentCenter);
            cellFormat.setVerticalAlignment(CellFormat.eVerticalAlignmentCenter);
            cellFormat.setTopBorder(CellFormat.eBorderMedium);
            cellFormat.setBottomBorder(CellFormat.eBorderMedium);
            cellFormat.setLeftBorder(CellFormat.eBorderThin);
            cellFormat.setRightBorder(CellFormat.eBorderThin);
            cellFormat.setPattern(CellFormat.ePatternSolid);
            cellFormat.setTopBorderColor(new Color(0X87, 0XB1, 0XE1).getRGB());
            cellFormat.setBottomBorderColor(new Color(0X87, 0XB1, 0XE1).getRGB());
            cellFormat.setLeftBorderColor(new Color(0X87, 0XB1, 0XE1).getRGB());
            cellFormat.setRightBorderColor(new Color(0X87, 0XB1, 0XE1).getRGB());
            cellFormat.setPatternFG(0xCEDDF0);
            ReportView.setCellFormat(cellFormat);
            ReportView.setRowHeight(x, 17 * 18);
        }
    }

    /**
     * ��λ����.����λ����.��λ
     */
    private void drawUnit3(int rowIndex, int colIndex, int unitIndex, Element unitElmt) throws Exception {
        //��λ����,�ϲ�
        int x = rowIndex;
        int y = colIndex;
//        drawUnitHead3(unitElmt, x, y);
        //��Ŀ
        drawItemValue3(x, y + 1);
        //������
        drawUnitItem3(x, y + mItemCount + 1, unitIndex, unitElmt);
    }

    /**
     * ��λ����.����λ����.��λ����
     */
    private void drawUnitHead3(Element unitElmt, int x, int y) throws F1Exception {
        String name = unitElmt.getAttributeValue("MC");
        ReportView.setText(x + 1, y, name);
        ReportView.setSelection(x + 1, y, x + mPageRowCount, y);
        ReportView.setColWidthAuto(x + 1, y, x + mPageRowCount, y, true);
        CellFormat cellFormat = ReportView.getCellFormat();
        cellFormat.setVerticalAlignment(cellFormat.eVerticalAlignmentCenter);
        cellFormat.setLeftBorder(CellFormat.eBorderThin);
        cellFormat.setRightBorder(CellFormat.eBorderThin);
        cellFormat.setTopBorder(CellFormat.eBorderThin);
        cellFormat.setBottomBorder(CellFormat.eBorderThin);
        cellFormat.setTopBorderColor(new Color(0X87, 0XB1, 0XE1).getRGB());
        cellFormat.setBottomBorderColor(new Color(0X87, 0XB1, 0XE1).getRGB());
        cellFormat.setLeftBorderColor(new Color(0X87, 0XB1, 0XE1).getRGB());
        cellFormat.setRightBorderColor(new Color(0X87, 0XB1, 0XE1).getRGB());
        cellFormat.setMergeCells(true);
        cellFormat.setPatternFG(java.awt.Color.white.getRGB());
        
        ReportView.setCellFormat(cellFormat);
    }

    /**
     * ��λ����.����λ����.��Ŀֵ
     */
    private void drawItemValue3(int rowIndex, int colIndex) throws F1Exception {
        int itemCount = bItemElement.getChildren().size();
        for (int i = 0; i < itemCount; i++) {
            Element crtItemElmt = bItemElement.getChild(XMLUtil.PREFIX + "COL" + i);
            for (int k = 1; k <= mPageRowCount; k++) {
                String itemValue = crtItemElmt.getAttributeValue("ROW" + k);
                int x = rowIndex + k;
                int y = colIndex + i;
                ReportView.setText(x, y, itemValue);
                ReportView.setSelection(x, y, x, y);
                setStringFormat(k);
                ReportView.setRowHeight(x, 17 * 18);
            }
        //    ReportView.setColWidthAuto(rowIndex, colIndex, rowIndex + mPageRowCount, colIndex, true);
        }
    }

    /**
     * ��λ����.����λ����.��λ��Ŀ
     */
    private void drawUnitItem3(int rowIndex, int colIndex, int unitIndex, Element unitElement) throws Exception {
        int itemIndex = 0;
        for (Iterator iterator = unitElement.getChildren().iterator(); iterator.hasNext(); itemIndex++) {
            Element crtItemElement = unitElement.getChild(XMLUtil.PREFIX + "COL" + itemIndex);
            if (crtItemElement != null) {
                drawUnitItemValue1(rowIndex, colIndex + itemIndex, unitIndex, itemIndex, crtItemElement);
                iterator.next();
            }
        }
    }

    /**
     * ���е���
     * @throws java.lang.Exception
     */
    private void drawReportBody4() throws Exception {
        int x = mTitleRowCount;
        int y = 0;
        Vector totalUnitList = getAllUnitList();
        for (int j = 0; j < totalUnitList.size(); j++) {
            Element crtUnitElmt = (Element) totalUnitList.get(j);
            drawReportByPage4(x, y, crtUnitElmt);
            x += mItemCount + mValueCount + 2;
        }
        setReportFormat4();
    }

    /**
     * ���е���.��ҳ
     */
    private void drawReportByPage4(int rowIndex, int colIndex, Element unitElmt) throws Exception {
        drawUnitHead4(rowIndex, colIndex, unitElmt);
        drawItem4(rowIndex, colIndex);
        drawUnit4(rowIndex, colIndex, unitElmt);
        setReportFormat4(rowIndex, colIndex);
    }

    /**
     * ���е���.��Ŀ
     */
    private void drawItem4(int rowIndex, int colIndex) throws Exception {
        for (int i = 0; i < mItemCount; i++) {
            int x = rowIndex + 1;
            int y = colIndex;
            String itemValue = hItemElement.getAttributeValue("VALUE" + i);
            ReportView.setText(x + i, y, itemValue);
            ReportView.setSelection(x + i, y, x + i, y);
            CellFormat cellFormat = ReportView.getCellFormat();
            cellFormat.setTopBorder(CellFormat.eBorderThin);
            cellFormat.setLeftBorder(CellFormat.eBorderThin);
            cellFormat.setRightBorder(CellFormat.eBorderThin);
            cellFormat.setBottomBorder(CellFormat.eBorderThin);
            cellFormat.setTopBorderColor(new Color(0X87, 0XB1, 0XE1).getRGB());
            cellFormat.setBottomBorderColor(new Color(0X87, 0XB1, 0XE1).getRGB());
            cellFormat.setLeftBorderColor(new Color(0X87, 0XB1, 0XE1).getRGB());
            cellFormat.setRightBorderColor(new Color(0X87, 0XB1, 0XE1).getRGB());
            cellFormat.setHorizontalAlignment(CellFormat.eHorizontalAlignmentLeft);
            cellFormat.setVerticalAlignment(cellFormat.eVerticalAlignmentCenter);
            cellFormat.setPattern(CellFormat.ePatternSolid);
            cellFormat.setPatternFG(0xCEDDF0);
            ReportView.setCellFormat(cellFormat);
            Element crtItemElmt = bItemElement.getChild(XMLUtil.PREFIX + "COL" + i);
            if (crtItemElmt != null) {
                drawItemValue4(x + i, y + 1, crtItemElmt);
            }
        }
    }

    /**
     * ���е���.��Ԫ
     */
    private void drawUnit4(int rowIndex, int colIndex, Element unitElmt) throws Exception {
        for (int j = 0; j < mValueCount; j++) {
            int x = rowIndex + mItemCount + 1;
            int y = colIndex;
            String crtValue = hValueElement.getAttributeValue("VALUE" + j);
            ReportView.setText(x + j, y, crtValue);
            ReportView.setSelection(x + j, y, x + j, y);
            CellFormat cellFormat = ReportView.getCellFormat();
            cellFormat.setTopBorder(CellFormat.eBorderThin);
            cellFormat.setLeftBorder(CellFormat.eBorderThin);
            cellFormat.setRightBorder(CellFormat.eBorderThin);
            cellFormat.setBottomBorder(CellFormat.eBorderThin);
            cellFormat.setTopBorderColor(new Color(0X87, 0XB1, 0XE1).getRGB());
            cellFormat.setBottomBorderColor(new Color(0X87, 0XB1, 0XE1).getRGB());
            cellFormat.setLeftBorderColor(new Color(0X87, 0XB1, 0XE1).getRGB());
            cellFormat.setRightBorderColor(new Color(0X87, 0XB1, 0XE1).getRGB());
            cellFormat.setHorizontalAlignment(CellFormat.eHorizontalAlignmentLeft);
            cellFormat.setVerticalAlignment(cellFormat.eVerticalAlignmentCenter);
            cellFormat.setPattern(CellFormat.ePatternSolid);
            cellFormat.setPatternFG(0xCEDDF0);
            ReportView.setCellFormat(cellFormat);
            Element crtValueElmt = unitElmt.getChild(XMLUtil.PREFIX + "COL" + j);
            if (crtValueElmt != null) {
                drawUnitItemValue4(x + j, y, crtValueElmt);
            }
        }
    }

    /**
     * ���е���.�����ʽ
     */
    private void setReportFormat4(int rowIndex, int colIndex) throws F1Exception {
//        ReportView.setColWidthAuto(rowIndex, colIndex, rowIndex, colIndex + mItemCount + mValueCount + 1, true);
//        for (int i = 0; i < mPageRowCount + 1; i++) {
//            ReportView.setColWidthAuto(rowIndex + 1, colIndex + 1 + i, rowIndex, colIndex + mItemCount + mValueCount + 1 + i, true);
//        }
        ReportView.setSelection(rowIndex, colIndex, rowIndex + mItemCount + mValueCount, colIndex + mPageRowCount);
        CellFormat cellFormat = ReportView.getCellFormat();
        cellFormat.setTopBorder(CellFormat.eBorderMedium);
        cellFormat.setBottomBorder(CellFormat.eBorderMedium);
        cellFormat.setLeftBorder(CellFormat.eBorderMedium);
        cellFormat.setRightBorder(CellFormat.eBorderMedium);
        cellFormat.setTopBorderColor(new Color(0X87, 0XB1, 0XE1).getRGB());
        cellFormat.setBottomBorderColor(new Color(0X87, 0XB1, 0XE1).getRGB());
        cellFormat.setLeftBorderColor(new Color(0X87, 0XB1, 0XE1).getRGB());
        cellFormat.setRightBorderColor(new Color(0X87, 0XB1, 0XE1).getRGB());
        ReportView.setCellFormat(cellFormat);
    }

    private void setReportFormat4() throws F1Exception {
        int unitCount = getAllUnitList().size();
        mHeadRowCount = 1;
        mBodyRowCount = unitCount * (1 + mItemCount + mValueCount + 1) - 1;
        int rowCount  = mTitleRowCount + mBodyRowCount;
        int colCount  = mPageRowCount + 1;
        mColCount     = mPageRowCount + 1;
        ReportView.setColWidthAuto(mTitleRowCount, 0, mTitleRowCount + mHeadRowCount + mBodyRowCount - 1, mColCount - 1, true);
        setReportView(rowCount, colCount);
    }

    /**
     * ���е���.��λ����
     */
    private void drawUnitHead4(int rowIndex, int colIndex, Element unitElmt) throws F1Exception {
        String name = unitElmt.getAttributeValue("MC");
        ReportView.setText(rowIndex, colIndex, res.getString("String_137"));
        ReportView.setSelection(rowIndex, colIndex, rowIndex, colIndex);
        CellFormat cellFormat = ReportView.getCellFormat();
        cellFormat.setLeftBorder(CellFormat.eBorderThin);
        cellFormat.setRightBorder(CellFormat.eBorderThin);
        cellFormat.setTopBorder(CellFormat.eBorderThin);
        cellFormat.setBottomBorder(CellFormat.eBorderThin);
        cellFormat.setHorizontalAlignment(CellFormat.eHorizontalAlignmentLeft);
        cellFormat.setVerticalAlignment(cellFormat.eVerticalAlignmentCenter);
        cellFormat.setTopBorderColor(new Color(0X87, 0XB1, 0XE1).getRGB());
        cellFormat.setBottomBorderColor(new Color(0X87, 0XB1, 0XE1).getRGB());
        cellFormat.setLeftBorderColor(new Color(0X87, 0XB1, 0XE1).getRGB());
        cellFormat.setRightBorderColor(new Color(0X87, 0XB1, 0XE1).getRGB());
        cellFormat.setPattern(CellFormat.ePatternSolid);
        cellFormat.setPatternFG(0xCEDDF0);
        ReportView.setCellFormat(cellFormat);
        ReportView.setText(rowIndex, colIndex + 1, name);
        ReportView.setSelection(rowIndex, colIndex + 1, rowIndex, colIndex + mPageRowCount);
        cellFormat = ReportView.getCellFormat();
        cellFormat.setHorizontalAlignment(CellFormat.eHorizontalAlignmentLeft);
        cellFormat.setVerticalAlignment(cellFormat.eVerticalAlignmentCenter);
        cellFormat.setLeftBorder(CellFormat.eBorderThin);
        cellFormat.setRightBorder(CellFormat.eBorderThin);
        cellFormat.setTopBorder(CellFormat.eBorderThin);
        cellFormat.setBottomBorder(CellFormat.eBorderThin);
        cellFormat.setTopBorderColor(new Color(0X87, 0XB1, 0XE1).getRGB());
        cellFormat.setBottomBorderColor(new Color(0X87, 0XB1, 0XE1).getRGB());
        cellFormat.setLeftBorderColor(new Color(0X87, 0XB1, 0XE1).getRGB());
        cellFormat.setRightBorderColor(new Color(0X87, 0XB1, 0XE1).getRGB());
        cellFormat.setPattern(CellFormat.ePatternSolid);
        cellFormat.setPatternFG(0xCEDDF0);
        ReportView.setCellFormat(cellFormat);
    }

    /**
     * ���е���.��Ŀֵ
     */
    private void drawItemValue4(int rowIndex, int colIndex, Element itemElmt) throws Exception {
        for (int i = 1; i < mPageRowCount + 1; i++) {
            int x = rowIndex;
            int y = colIndex;
            String crtItemValue = itemElmt.getAttributeValue("ROW" + i);
            ReportView.setText(x, y + i - 1, crtItemValue);
            ReportView.setSelection(x, y + i - 1, x, y + i - 1);
            setStringFormat(i);
        }
    }

    /**
     * ���е���.��λ��Ŀ
     * @param rowIndex
     * @param colIndex
     * @param valueElmt
     * @throws java.lang.Exception
     */
    private void drawUnitItemValue4(int rowIndex, int colIndex, Element valueElmt) throws Exception {
        for (int i = 1; i < mPageRowCount + 1; i++) {
            int x = rowIndex;
            int y = colIndex;
            double crtValue = Double.parseDouble(valueElmt.getAttributeValue("ROW" + i, "0"));
            ReportView.setNumber(x, y + i, crtValue);
            ReportView.setSelection(x, y + i, x, y + i);
            setNumberFormat(i);
            ReportView.setRowHeight(x, 17 * 18);
        }
    }

    /**
     * ҳ��
     * @return
     */
    private int getPageCount() {
        int allUnitCount = 0;
        if (mSumCount > 0)
            allUnitCount = mUnitCount + 1;
        else
            allUnitCount = mUnitCount;

        int pageCount = allUnitCount / mDGSM_XSDW;
        if (! (allUnitCount % mDGSM_XSDW == 0)) {
            pageCount++;
        }
        return pageCount;
    }

    /**
     * ��õ�ҳ��λ��Element�ļ���
     * @param pageIndex
     */
    private Vector getUnitListByPage(int pageIndex) {
        Vector unitList = new Vector();
        if (pageIndex == 0 && mDGSM_HJWZ.equals("Q")) { //�ϼ���ǰ
            //���û�кϼƣ��򲻼Ӻϼ���
            if (mSumCount > 0)
                unitList.addElement(bSumElement);
            for (int i = 0; i < mDGSM_XSDW - 1; i++) {
                String   unitSuffix = Integer.toString(i + pageIndex * mDGSM_XSDW);
                Element crtUnitElmt = mXMLObject.GetElementByName("UNIT" + unitSuffix);
                unitList.addElement(crtUnitElmt);
            }
        } else if (pageIndex == (mPageCount - 1) && mDGSM_HJWZ.equals("H")) { //�ϼ��ں�
            int unitCountOfLastPage = mUnitCount - mDGSM_XSDW * (mPageCount - 1);
            for (int i = 0; i < unitCountOfLastPage; i++) {
                String   unitSuffix = Integer.toString(i + pageIndex * mDGSM_XSDW);
                Element crtUnitElmt = mXMLObject.GetElementByName("UNIT" + unitSuffix);
                unitList.addElement(crtUnitElmt);
            }
            //���û�кϼƣ��򲻼Ӻϼ���
            if (mSumCount > 0)
                unitList.addElement(bSumElement);
        } else {
            int iUnitSuffix = 0;
            for (int i = 0; i < mDGSM_XSDW; i++) {
                iUnitSuffix = i + pageIndex * mDGSM_XSDW;
                /**
                 * ����кϼ��У��Һϼ���ǰ����Ϊ�ϼ���ռ��һ����λ��
                 */
                if (mSumCount > 0 && mDGSM_HJWZ.equals("Q")) {
                    iUnitSuffix--;
                }
                String   unitSuffix = Integer.toString(iUnitSuffix);
                Element crtUnitElmt = mXMLObject.GetElementByName("UNIT" + unitSuffix);
                unitList.addElement(crtUnitElmt);
            }
        }
        return unitList;
    }

    /**
     * ������еĵ�λ��Element�ļ���
     * @return
     */
    private Vector getAllUnitList() {
        Vector unitList = new Vector();
        for (int i = 0; i < mUnitCount; i++) {
            Element crtUnitElmt = mXMLObject.GetElementByName("UNIT" + i);
            unitList.addElement(crtUnitElmt);
        }
        //�ϼ���ǰ
        if (mDGSM_HJWZ.equals("Q")) {
            unitList.insertElementAt(bSumElement, 0);
        } else if (mDGSM_HJWZ.equals("H")) {
            unitList.addElement(bSumElement);
        }
        return unitList;
    }

    private void setNumberFormat(int rowIndex) throws F1Exception {
        String numberFormat = "#,##0.00";
        CellFormat cellFormat = ReportView.getCellFormat();
        cellFormat.setTopBorder(CellFormat.eBorderThin);
        cellFormat.setLeftBorder(CellFormat.eBorderThin);
        cellFormat.setRightBorder(CellFormat.eBorderThin);
        cellFormat.setBottomBorder(CellFormat.eBorderThin);
        cellFormat.setTopBorderColor(new Color(0X87, 0XB1, 0XE1).getRGB());
        cellFormat.setBottomBorderColor(new Color(0X87, 0XB1, 0XE1).getRGB());
        cellFormat.setLeftBorderColor(new Color(0X87, 0XB1, 0XE1).getRGB());
        cellFormat.setRightBorderColor(new Color(0X87, 0XB1, 0XE1).getRGB());
        cellFormat.setValueFormat(numberFormat);
        cellFormat.setHorizontalAlignment(CellFormat.eHorizontalAlignmentRight);
        cellFormat.setVerticalAlignment(CellFormat.eVerticalAlignmentCenter);
        cellFormat.setPatternFG(java.awt.Color.white.getRGB());
        ReportView.setCellFormat(cellFormat);
        ReportView.setShowZeroValues(false);
    }

    private void setStringFormat(int rowIndex) throws F1Exception {
        CellFormat CF = ReportView.getCellFormat();
  //lk      CF.setMergeCells(true);
        CF.setVerticalAlignment(CellFormat.eVerticalAlignmentCenter);
        CF.setTopBorder(CF.eBorderThin);
        CF.setBottomBorder(CF.eBorderThin);
        CF.setLeftBorder(CF.eBorderThin);
        CF.setRightBorder(CF.eBorderThin);
        CF.setTopBorderColor(new Color(0X87, 0XB1, 0XE1).getRGB());
        CF.setBottomBorderColor(new Color(0X87, 0XB1, 0XE1).getRGB());
        CF.setLeftBorderColor(new Color(0X87, 0XB1, 0XE1).getRGB());
        CF.setRightBorderColor(new Color(0X87, 0XB1, 0XE1).getRGB());
        CF.setPatternFG(java.awt.Color.white.getRGB());
        ReportView.setCellFormat(CF);
    }
}
