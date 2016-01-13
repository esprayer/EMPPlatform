package jdatareport.dof.classes.report.style;

import javax.swing.*;
import java.awt.*;
import com.f1j.swing.*;
import jdatareport.dof.classes.report.util.*;
import com.f1j.ss.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class JDRStyleViewPanel
    extends JPanel {
    /**
     *
     */
    private static final int TITLE_ROW_COUNT = 1;
    private static final int HEADER_ROW_COUNT = 1;
    private static final int BODY_ROW_COUNT = 8;
    private static final int ROW_COUNT = 10;
    private static final int COL_COUNT = 5;

    private String[][] bodyData = {
        {
        "北京", "3", "7", "4", "1"}
        , {
        "上海", "14", "55", "66", "4"}
        , {
        "天津", "33", "25", "63", "3"}
        , {
        "乌鲁木齐", "32", "35", "13", "2"}
        , {
        "济南", "32", "35", "13", "2"}
        , {
        "哈密", "32", "35", "13", "2"}
        , {
        "吐鲁番", "32", "35", "13", "2"}
        , {
        "广州", "32", "35", "13", "2"}
    };
    /**
     *
     */
    private JPanel jPanel1 = new JPanel();
    private BorderLayout borderLayout1 = new BorderLayout();
    private BorderLayout borderLayout2 = new BorderLayout();
    private JBook book = new JBook();
    /**
     *
     */
    public JDRStyleViewPanel() {
        try {
            jbInit();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @throws java.lang.Exception
     */
    private void jbInit() throws Exception {
        this.setLayout(borderLayout1);
        jPanel1.setLayout(borderLayout2);
        this.add(jPanel1, BorderLayout.CENTER);
        jPanel1.add(book, BorderLayout.CENTER);

        //Disable all the actions and unnecessay components
        book.setAllowInCellEditing(false);
        book.setAllowDesigner(false);
        book.setAllowEditHeaders(false);
        book.setAllowMoveRange(false);
        book.setAllowArrows(false);
        book.setAllowShortcutMenus(false);
        book.setAllowTabs(false);
        book.setShowZeroValues(false);
        book.setShowSelections(JBook.eShowOff);
        book.setShowColHeading(false);
        book.setShowRowHeading(false);
        book.setShowEditBar(false);
    }

    /**
     *
     * @throws java.lang.Exception
     */
    public void initGUI(JDRQueryFormatInfo fmtInfo) throws Exception {
        initTitle(fmtInfo);
        initHeader(fmtInfo);
        initBody(fmtInfo);

//        book.setMaxRow(ROW_COUNT);
//        book.setMaxCol(COL_COUNT);
    }

    /**
     *
     * @throws java.lang.Exception
     */
    public void initTitle(JDRQueryFormatInfo fmtInfo) throws Exception {
        int startRow = 0;
        int startCol = 0;
        //数据
        book.setText(startRow, startCol, "标题");
        //格式
        this.book.setSelection(startRow, startCol, startRow, COL_COUNT - 1);
        CellFormat cf = book.getCellFormat();
        cf.setFontName("宋体");
        cf.setTopBorder(fmtInfo.mTitleInnerBorder);
        cf.setBottomBorder(fmtInfo.mTitleInnerBorder);
        cf.setLeftBorder(fmtInfo.mTitleInnerBorder);
        cf.setRightBorder(fmtInfo.mTitleInnerBorder);
        book.setColWidthAuto(startRow, startCol, startRow, COL_COUNT - 1, true);
        cf.setHorizontalAlignment(CellFormat.eHorizontalAlignmentCenterAcrossCells);
        cf.setVerticalAlignment(CellFormat.eVerticalAlignmentCenter);
        book.setCellFormat(cf);
        //
        this.book.setSelection(startRow, startCol, startRow, COL_COUNT - 1);
        cf = book.getCellFormat();
        cf.setFontName("宋体");
        cf.setTopBorder(fmtInfo.mTitleOuterBorder);
        cf.setBottomBorder(fmtInfo.mTitleOuterBorder);
        cf.setLeftBorder(fmtInfo.mTitleOuterBorder);
        cf.setRightBorder(fmtInfo.mTitleOuterBorder);
        cf.setPattern(CellFormat.ePatternSolid);
        cf.setPatternFG(fmtInfo.mTitleColor);
        book.setCellFormat(cf);

    }

    /**
     *
     * @throws java.lang.Exception
     */
    public void initHeader(JDRQueryFormatInfo fmtInfo) throws Exception {
        int startRow = TITLE_ROW_COUNT;
        int startCol = 0;
        //数据
        for (int i = 1; i < COL_COUNT; i++) {
            String strText = (i ) + " 月";
            book.setText(startRow, startCol + i, strText);
            book.setSelection(startRow, startCol + i, startRow, startCol + i);
            CellFormat cf = book.getCellFormat();
            cf.setFontName("宋体");
            cf.setTopBorder(fmtInfo.mHeadInnerBorder);
            cf.setBottomBorder(fmtInfo.mHeadInnerBorder);
            cf.setLeftBorder(fmtInfo.mHeadInnerBorder);
            cf.setRightBorder(fmtInfo.mHeadInnerBorder);
            book.setCellFormat(cf);
        }
        //格式
        book.setSelection(startRow, startCol, startRow, COL_COUNT - 1);
        CellFormat cf = book.getCellFormat();
        cf.setTopBorder(fmtInfo.mHeadOuterBorder);
        cf.setBottomBorder(fmtInfo.mHeadOuterBorder);
        cf.setLeftBorder(fmtInfo.mHeadOuterBorder);
        cf.setRightBorder(fmtInfo.mHeadOuterBorder);
        cf.setPattern(CellFormat.ePatternSolid);
        cf.setPatternFG(fmtInfo.mHeadColor);
        book.setCellFormat(cf);

    }

    /**
     *
     * @throws java.lang.Exception
     */
    public void initBody(JDRQueryFormatInfo fmtInfo) throws Exception {
        int startRow = TITLE_ROW_COUNT + HEADER_ROW_COUNT;
        int startCol = 0;
        //数据
        for (int i = 0; i < BODY_ROW_COUNT; i++) {
            for (int j = 0; j < COL_COUNT; j++) {
                String strText = bodyData[i][j];
                book.setText(startRow + i, startCol + j, strText);
                book.setSelection(startRow + i, startCol + j, startRow + i, startCol + j);
                CellFormat cf = book.getCellFormat();
                cf.setFontName("宋体");
                cf.setBottomBorder(fmtInfo.mBodyInnerBorder);
                cf.setLeftBorder(fmtInfo.mBodyInnerBorder);
                cf.setRightBorder(fmtInfo.mBodyInnerBorder);
                cf.setVerticalAlignment(CellFormat.eVerticalAlignmentCenter);
                book.setCellFormat(cf);

            }
            //对比色
            int crtRow = startRow + i;
            book.setSelection(crtRow, 0, crtRow, COL_COUNT - 1);
            CellFormat cf = book.getCellFormat();
            cf.setPattern(CellFormat.ePatternSolid);
            if ( ((crtRow+1) % 2) != 0) {
                cf.setPatternFG(fmtInfo.mBodyBgColor0);
            }
            else {
                cf.setPatternFG(fmtInfo.mBodyBgColor1);
            }
            book.setCellFormat(cf);

        }
        //格式
        book.setColWidthAuto(startRow, startCol, ROW_COUNT-1, startCol,true);
        this.book.setSelection(startRow, startCol, ROW_COUNT-1, COL_COUNT - 1);
        CellFormat cf = book.getCellFormat();
        cf.setBottomBorder(fmtInfo.mBodyOuterBorder);
        cf.setLeftBorder(fmtInfo.mBodyOuterBorder);
        cf.setRightBorder(fmtInfo.mBodyOuterBorder);
        book.setCellFormat(cf);
    }

}