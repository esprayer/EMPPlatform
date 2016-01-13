package jreport.swing.classes;

import java.util.*;

import javax.swing.*;

import com.f1j.ss.*;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2004</p>
 *
 * <p>Company: pansoft</p>
 *
 * @author not attributable
 * @version 1.0
 */
public class JSplitPage
    extends JComponent {
static ResourceBundle res = ResourceBundle.getBundle("jreport.swing.classes.Language");
    public int TITLE;
    public int HEAD;
    public int SPLIT;
    public int BT;
    public int ROW;
    public int COL;
    public int srcSheet;
    public String Message1,Message2;
    public JReportView Book;

    public JSplitPage() {
    }
    public void SetColWidth() {
        int Width;
        try {
            for (int i = 0; i <= COL; i++) {
                Book.setSheet(srcSheet);
                Width = Book.getColWidth(i);
                Book.setSheet(srcSheet-1);
                Book.setColWidth(i, Width);
                Book.setColWidth(i + COL+1, Width);
            }
            Book.setSheet(srcSheet-1);
        } catch ( Exception e ) {
          e.printStackTrace();
        }
    }

    public void DrawTitle() {
        int nDstSheet, nDstR1, nDstC1, nDstR2,
        nDstC2,  nSrcSheet, nSrcR1, nSrcC1,
        nSrcR2, nSrcC2 ;
//        long hSrcSS=(long)Book.SS();
        nDstSheet = srcSheet-1;
        nDstR1    =  0;
        nDstC1    =  0;
        nDstR2    =  TITLE-1;
        nDstC2    =  COL;
        nSrcSheet = srcSheet;
        nSrcR1    = 0;
        nSrcC1    = 0;
        nSrcR2    = TITLE-1;
        nSrcC2    = COL;
        try {
            Book.setSheet(srcSheet);
            Book.copyRange(nDstSheet, nDstR1 , nDstC1, nDstR2, nDstC2, Book.getBookModel(),  nSrcSheet, nSrcR1, nSrcC1, nSrcR2, nSrcC2);
            Book.setSheet(srcSheet-1);
            Book.setSelection(0, 0, 0, COL + COL+1);
            CellFormat Cell;
            Cell = Book.getCellFormat();
            boolean IsMerge;
            IsMerge = Cell.isMergeCells();
            if (IsMerge) {
                Cell.setMergeCells(false);
            }
            else {
                Cell.setMergeCells(true);
                Cell.setVerticalAlignment(Cell.eVerticalAlignmentCenter);
                Book.setCellFormat(Cell);
                Cell = Book.getCellFormat();
                Cell.setMergeCells(false);
            }
            Book.setCellFormat(Cell);
            Cell = Book.getCellFormat();
//        F1HAlignConstants Align;
            Cell.setHorizontalAlignment(Cell.eHorizontalAlignmentCenterAcrossCells);
            Cell.setVerticalAlignment(Cell.eVerticalAlignmentCenter);
            Book.setCellFormat(Cell);

            String asText;
            for (int Row = 1; Row <= TITLE-1; Row++) {
                for (int Col = COL; Col >= 0; Col--) {
                    Book.setActiveCell(Row, Col);
                    asText = Book.getText();
                    if ("0".equals(asText))
                        asText = "";
                    Cell = Book.getCellFormat();
                    if (Row <= TITLE-1)
                        if (Col == 0)
                            break;
                        else
                            Book.setText("");
                    if (asText.length() > 0) {
                        //如果是年月，则居右
                        if (asText.indexOf(res.getString("String_10")) >= 0 && asText.indexOf(res.getString("String_11")) >= 0) {
                            Cell.setHorizontalAlignment(Cell.eHorizontalAlignmentRight);
                        }
                        if (Row <= TITLE-1 && Col > 0 && Col < COL)
                            Book.setActiveCell(Row, Col + COL / 2);
                        else
                            Book.setActiveCell(Row, Col + COL+1);
                        Book.setText(asText);
                        Book.setCellFormat(Cell);
                    }
                }
            }
        } catch ( Exception e ) {
          e.printStackTrace();
        }
    }

    /**
     * 画表体部分
     */
    public void DrawBT()
    {
        int nDstSheet, nDstR1, nDstC1, nDstR2,
        nDstC2,  nSrcSheet, nSrcR1, nSrcC1,
        nSrcR2, nSrcC2 ,nPosRows;

        try {
            nDstSheet = srcSheet-1;
            nDstR1 = TITLE + HEAD;
            nDstC1 = 0;
            nDstR2 = SPLIT - 1;
            nDstC2 = COL;
            nSrcSheet = srcSheet;
            nSrcR1 = TITLE + HEAD;
            nSrcC1 = 0;
            nSrcR2 = SPLIT - 1;
            nSrcC2 = COL;
            Book.setSheet(srcSheet);
            Book.copyRange(nDstSheet, nDstR1, nDstC1, nDstR2, nDstC2, Book.getBookModel(), nSrcSheet, nSrcR1, nSrcC1, nSrcR2, nSrcC2);

            Book.setSheet(srcSheet-1);
            nPosRows = (BT + HEAD + TITLE) - (SPLIT + 1);
            if (nPosRows > SPLIT)
                nPosRows = SPLIT;
            nDstR1 = TITLE + HEAD;
            nDstC1 = COL+1;
            nDstR2 = nDstR1 + nPosRows;
            nDstC2 = COL + COL+1;
            nSrcSheet = srcSheet;
            nSrcR1 = SPLIT;
            nSrcC1 = 0;
            nSrcR2 = nSrcR1 + nPosRows;
            nSrcC2 = COL;

            Book.setSheet(srcSheet);
            Book.copyRange(nDstSheet, nDstR1, nDstC1, nDstR2, nDstC2, Book.getBookModel(), nSrcSheet, nSrcR1, nSrcC1, nSrcR2, nSrcC2);
            AmendFormat();
            //设置最大行、列，如果最大行列是
            Book.setSheet(srcSheet-1);
            Book.setMaxRow(SPLIT + (ROW - BT - HEAD - TITLE));
            Book.setMaxCol(COL + COL+1);

            Book.setSheet(srcSheet);
            Book.setActiveCell(TITLE-1, 0);
            String asText = Book.getText();
            Book.setSheet(srcSheet-1);
            Book.setActiveCell(TITLE-1, 0);
            Book.setText(asText);

//            Book.setSheet(srcSheet);
//            for (int i = 1; i < COL; i++) {
//                Book.setActiveCell(TITLE-1, i);
//                asText = Book.getText();
//                if (asText.indexOf(Message1) > 0)
//                    break;
//            }
//            Book.setSheet(srcSheet-1);
//            String asTemp = "";
//            for (int i = 1; i <= COL; i++) {
//                Book.setActiveCell(TITLE-1, i);
//                asTemp = Book.getText();
//                if (asTemp.indexOf(Message1) > 0)
//                    Book.setText(asText);
//            }
        } catch ( Exception e ) {
          e.printStackTrace();
        }
    }

    public void AmendFormat()
    {
        try {
            Book.setSheet(srcSheet-1);

            Book.setSelection(TITLE + HEAD + 1, COL - 1, TITLE + HEAD + BT, COL - 1);
            CellFormat Cell;
            Cell = Book.getCellFormat();

            Book.setSelection(TITLE + HEAD + 1, COL, TITLE + HEAD + BT, COL);
            Book.setCellFormat(Cell);

            Book.setSelection(TITLE + HEAD + BT, COL + 1, TITLE + HEAD + BT, COL + COL);
            Cell = Book.getCellFormat();

            Book.setSelection(TITLE + HEAD + BT, 1, TITLE + HEAD + BT, COL);
            Book.setCellFormat(Cell);
        } catch ( Exception e ) {
          e.printStackTrace();
        }
    }

    public void DrawHead()
    {
        int nDstSheet, nDstR1, nDstC1, nDstR2,
        nDstC2,  nSrcSheet, nSrcR1, nSrcC1,
        nSrcR2, nSrcC2 ;

//        long hSrcSS=(long)Book->SS();
        nDstSheet = srcSheet-1;
        nDstR1    =  TITLE;
        nDstC1    =  0;
        nDstR2    =  TITLE+HEAD-1;
        nDstC2    =  COL+COL+1;
        nSrcSheet = srcSheet;
        nSrcR1    = TITLE;
        nSrcC1    = 0;
        nSrcR2    = TITLE+HEAD-1;
        nSrcC2    = COL;
        try {
            Book.setSheet(srcSheet);
            Book.copyRange(nDstSheet, nDstR1, nDstC1, nDstR2, nDstC2, Book.getBookModel(), nSrcSheet, nSrcR1, nSrcC1, nSrcR2, nSrcC2);
            Book.setSheet(srcSheet-1);
        } catch ( Exception e ) {
          e.printStackTrace();
        }
    }

//-----------------------------------------------------------------------
//
//画表尾
//
//-----------------------------------------------------------------------
    public void DrawBottom()
    {
        if ( ROW-BT-HEAD-TITLE+1 > 0 ) {
            int nDstSheet, nDstR1, nDstC1, nDstR2,
            nDstC2,  nSrcSheet, nSrcR1, nSrcC1,
            nSrcR2, nSrcC2 ;

            nDstSheet = srcSheet-1;
            nDstR1    =  SPLIT;
            nDstC1    =  0;
            nDstR2    =  SPLIT+(ROW-BT-HEAD-TITLE);
            nDstC2    =  COL;
            nSrcSheet = srcSheet;
            nSrcR1    = TITLE+HEAD+BT;
            nSrcC1    = 0;
            nSrcR2    = ROW;
            nSrcC2    = COL;
            try {
                Book.setSheet(srcSheet);
                Book.copyRange(nDstSheet, nDstR1, nDstC1, nDstR2, nDstC2, Book.getBookModel(), nSrcSheet, nSrcR1, nSrcC1, nSrcR2, nSrcC2);
                Book.setSheet(srcSheet-1);
                Book.setSelection(0, 0, 0, COL + COL+1);
                CellFormat Cell;
                Cell = Book.getCellFormat();
                boolean IsMerge;
                IsMerge = Cell.isMergeCells();
                if (IsMerge) {
                    Cell.setMergeCells(false);
                }
                else {
                    Cell.setMergeCells(true);
                    Cell.setVerticalAlignment(Cell.eVerticalAlignmentCenter);
                    Book.setCellFormat(Cell);
                    Cell = Book.getCellFormat();
                    Cell.setMergeCells(false);
                }
                Book.setCellFormat(Cell);
                Cell = Book.getCellFormat();
//            F1HAlignConstants Align;
                Cell.setHorizontalAlignment(Cell.eHorizontalAlignmentCenterAcrossCells);
                Cell.setVerticalAlignment(Cell.eVerticalAlignmentCenter);
                Book.setCellFormat(Cell);

                String asText;
                Book.setSheet(srcSheet-1);
                for (int Row = TITLE + HEAD + BT; Row <= ROW; Row++) {
                    for (int Col = COL; Col >= 0; Col--) {
                        Book.setActiveCell(Row, Col);
                        asText = Book.getText();
                        Cell = Book.getCellFormat();
                        if (Row <= TITLE)
                            if (Col == 1)
                                break;
                            else
                                Book.setText("");
                        if (asText.length() > 0) {
                            if (Row <= TITLE-1 && Col > 0 && Col <= COL)
                                Book.setActiveCell(Row, Col + COL / 2);
                            else
                                Book.setActiveCell(Row, Col + COL);
                            Book.setText(asText);
                            Book.setCellFormat(Cell);
                        }
                    }
                }
                AmendFormat();
            } catch ( Exception e ) {
              e.printStackTrace();
            }
        }
    }

    /**
     * 刷新折页数据
     * @param book JReportView
     * @param bMess boolean
     */
    public static void SplitRefresh(JReportView book){
        JReportModel model = (JReportModel) book.getModel();
        JSplitPage SplitPage = new JSplitPage();
        try {
            if ( model.BBZD_SPLIT == 0 &&  book.getNumSheets() < 2) {
                return;
            }
            // 设置主表为活动sheet
            book.setSheet(1);
            SplitPage.TITLE = model.BBZD_TITLE;
            SplitPage.HEAD =  model.BBZD_HEAD;
            SplitPage.SPLIT = model.BBZD_SPLIT;
            SplitPage.BT =    model.BBZD_BT;
            SplitPage.ROW = book.getMaxRow();
            SplitPage.COL = book.getMaxCol();
            SplitPage.srcSheet = book.getSheet();
            SplitPage.Book = book;
            //
            SplitPage.DrawBT();
//            SplitPage.SetColWidth();
            /**
             * 刷新，不刷新标题和表头，只刷新表体部分
             * modified by gengeng 2008-5-6
             */
            SplitPage.DrawTitle();
//            SplitPage.DrawHead();
            SplitPage.DrawBottom();
        } catch ( Exception e ) {
          e.printStackTrace();
    }
    }

//    public static void main(String[] args) {
//        JSplitPage jsplitpage = new JSplitPage();
//    }
}
