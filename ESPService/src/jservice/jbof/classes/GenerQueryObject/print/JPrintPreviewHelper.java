package jservice.jbof.classes.GenerQueryObject.print;

import com.f1j.swing.*;
import java.awt.*;
import java.util.*;
import com.pub.comp.*;
import com.f1j.ss.*;
import jservice.jbof.classes.GenerQueryObject.print.util.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class JPrintPreviewHelper
    implements PrintConstants {
    /**
     *
     */

    private JBook mBook = null;
    private JCustomPanel mCanvas = null;

    private Vector mPageInfos = null;
    private int mHPageCount = 0;
    private int mVPageCount = 0;
    /**
     *
     * @param book
     */
    public JPrintPreviewHelper(JBook book, JCustomPanel canvas) {
        if (book != null && canvas != null) {
            this.mBook = book;
            this.mCanvas = canvas;
        }
    }

    /**
     *
     */
    public void init() {
        initPageInfos();
        initPageInfos();
    }

    public void initPageInfos() {
        Vector rowInfos = getRowInfos();
        Vector colInfos = getColInfos();
        this.mHPageCount = colInfos.size();
        this.mVPageCount = rowInfos.size();
        mPageInfos = new Vector();
        if (rowInfos != null && colInfos != null) {
            int pageCount = this.mHPageCount * this.mVPageCount;
            for (int i = 0; i < pageCount; i++) {
//                System.out.println(i);
                int pageIndex = i;
                int row = 0;
                int col = 0;
                if (!mBook.isPrintLeftToRight()) {
                    row = pageIndex % rowInfos.size(); ;
                    col = pageIndex / rowInfos.size(); ;
                }
                else {
                    row = pageIndex / colInfos.size();
                    col = pageIndex % colInfos.size();
                }
                PageInfo rowInfo = (PageInfo) rowInfos.get(row);
                PageInfo colInfo = (PageInfo) colInfos.get(col);
                PageInfo pageInfo = new PageInfo();
                pageInfo.mStartRow = rowInfo.mStartRow;
                pageInfo.mEndRow = rowInfo.mEndRow;
                pageInfo.mStartCol = colInfo.mStartCol;
                pageInfo.mEndCol = colInfo.mEndCol;

                this.mPageInfos.add(pageInfo);
            }
        }

    }

    /**
     *
     */
    private Vector getRowInfos() {
        Vector rowInfos = new Vector();
        try {
            double availablePageHeight = this.getAvailablePaperHeight();
            double rowsHeight = 0;
            int pageCount = 0;
            int firstRow = this.getFixedRowCount();
            int lastRow = getLastPrintRow();
            for (int i = firstRow; i < lastRow + 1; i++) {
                if (rowsHeight <= availablePageHeight) {
                    rowsHeight += getRowHeight(i);
                }
                else {
                    PageInfo info = new PageInfo();
                    if (pageCount == 0) {
                        info.mStartRow = firstRow;
                    }
                    else {
                        info.mStartRow = ( (PageInfo) rowInfos.get(pageCount - 1)).mEndRow + 1;
                    }
                    info.mEndRow = i - 1;
                    rowInfos.add(pageCount, info);
                    rowsHeight = 0;
                    pageCount++;
                }
            }
            if (pageCount == 0) {
                PageInfo info = new PageInfo();
                info.mStartRow = firstRow;
                info.mEndRow = lastRow;
                rowInfos.add(pageCount, info);
            }
            else {
                PageInfo info = new PageInfo();
                info.mStartRow = ( (PageInfo) rowInfos.get(pageCount - 1)).mEndRow + 1; ;
                info.mEndRow = lastRow;
                rowInfos.add(pageCount++, info);
            }

        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return rowInfos;
    }

    /**
     *
     */
    private Vector getColInfos() {
        Vector colInfos = new Vector();
        try {
            double availablePageWidth = this.getAvailablePaperWidth();
            double colsWidth = 0;
            int pageCount = 0;
            int firstCol = this.getFixedColCount();
            int lastCol = getLastPrintCol();
            for (int i = firstCol; i < lastCol + 1; i++) {
                if (colsWidth <= availablePageWidth) {
                    colsWidth += getColWidth(i);
                }
                else {
                    PageInfo info = new PageInfo();
                    if (pageCount == 0) {
                        info.mStartCol = firstCol;
                    }
                    else {
                        info.mStartCol = ( (PageInfo) colInfos.get(pageCount - 1)).mEndCol + 1;
                    }
                    info.mEndCol = i - 1;
                    colInfos.add(pageCount, info);
                    colsWidth = 0;
                    pageCount++;

                }
            }
            if (pageCount == 0) {
                PageInfo info = new PageInfo();
                info.mStartCol = firstCol;
                info.mEndCol = lastCol;
                colInfos.add(pageCount, info);
            }
            else {
                PageInfo info = new PageInfo();
                info.mStartCol = ( (PageInfo) colInfos.get(pageCount - 1)).mEndCol + 1; ;
                info.mEndCol = lastCol;
                colInfos.add(pageCount++, info);
            }

        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return colInfos;

    }

    /**
     *
     * @return
     */
    public int getPageCount() {
        return this.mPageInfos.size();
    }

    /**
     *
     * @param pageIndex
     * @return
     */
    public PageInfo getPageInfo(int pageIndex) {
        if (pageIndex < this.getPageCount()) {
            return (PageInfo)this.mPageInfos.get(pageIndex);
        }
        return null;
    }

    /**
     *
     * @return
     */
    public int getX() {
        double fitScale = getPaperScale();
        int x = JPrintUtils.twipsToDots(mBook,getAvailableCanvasWidth() - getPrintPaperWidth() * fitScale) / 2;
        return x;
    }

    /**
     *
     * @return
     */
    public int getY() {
        int y = (int) OFFSET;
        return y;
    }

    /**
     *
     * @return
     */
    public double getAvailableCanvasWidth() {
        int width = this.mCanvas.getWidth() - OFFSET * 2;
        return JPrintUtils.dotsToTwips(mBook,width);
    }

    /**
     *
     * @return
     */
    public double getAvailableCanvasHeight() {
        int height = this.mCanvas.getHeight() - OFFSET * 2;
        return JPrintUtils.dotsToTwips(mBook,height);
    }

    /**
     *
     * @return
     */
    public double getCanvasWidth() {
        int width = this.mCanvas.getWidth();
        return JPrintUtils.dotsToTwips(mBook,width);
    }

    /**
     *
     * @return
     */
    public double getCanvasHeight() {
        int height = this.mCanvas.getHeight();
        return JPrintUtils.dotsToTwips(mBook,height);
    }

    /**
     *
     * @return
     */
    public double getPrintPaperWidth() {
        double width = mBook.getPrintPaperWidth();
        return width;
    }

    /**
     *
     * @return
     */

    public double getPrintPaperHeight() {
        double height = mBook.getPrintPaperHeight();

        return height;

    }

    /**
     *
     * @return
     */
    public double getAvailablePaperWidth() {
        double availablePaperWidth = getPrintPaperWidth()
            - getPrintLeftMargin()
            - getPrintRightMargin()
            - getFixedColWidth();
        return availablePaperWidth;
    }

    /**
     *
     * @return
     */
    public double getAvailablePaperHeight() {
        double availablePaperHeight = getPrintPaperHeight()
            - getPrintTopMargin()
            - getPrintBottomMargin()
            - getPrintHeaderMargin()
            - getPrintFooterMargin()
            - getFixedRowHeighth();
        return availablePaperHeight;
    }

    /**
     *
     * @return
     */

    public double getPrintTopMargin() {
        return JPrintUtils.inchToTwips(mBook,mBook.getPrintTopMargin());
    }

    /**
     *
     * @return
     */

    public double getPrintBottomMargin() {
        return JPrintUtils.inchToTwips(mBook,mBook.getPrintBottomMargin());
    }

    /**
     *
     * @return
     */

    public double getPrintLeftMargin() {
        return JPrintUtils.inchToTwips(mBook,mBook.getPrintLeftMargin());
    }

    /**
     *
     * @return
     */

    public double getPrintRightMargin() {
        return JPrintUtils.inchToTwips(mBook,mBook.getPrintRightMargin());
    }

    /**
     *
     * @return
     */

    public double getPrintHeaderMargin() {
        return JPrintUtils.inchToTwips(mBook,mBook.getPrintHeaderMargin());
    }

    /**
     *
     * @return
     */
    public double getPrintFooterMargin() {
        return JPrintUtils.inchToTwips(mBook,mBook.getPrintFooterMargin());
    }

    /**
     *
     * @return
     */
    public int getFixedRowCount() {
        return mBook.getFixedRows();
    }

    /**
     *
     * @return
     */
    public int getFixedColCount() {
        return mBook.getFixedCols();
    }

    /**
     *
     * @return
     */
    public double getFixedRowHeighth() {
        int height = 0;
        try {
            for (int i = 0; i < mBook.getFixedRows(); i++) {
                height += mBook.getRowHeight(i);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return height;

    }

    /**
     *
     * @return
     */
    public double getFixedColWidth() {
        int width = 0;
        try {
            short saveUnit = mBook.getColWidthUnits();
            mBook.setColWidthUnits(JBook.eColWidthUnitsTwips);
            for (int i = 0; i < mBook.getFixedCols(); i++) {
                width += mBook.getColWidth(i);
            }
            mBook.setColWidthUnits(saveUnit);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return width;
    }

    /**
     *
     * @return
     */
    public double getPaperScale() {
        double ws = 1;
        double hs = 1;
        double availableCanvasWidth = getAvailableCanvasWidth();
        double availableCanvasHeight = getAvailableCanvasHeight();

        double paperWidth = getPrintPaperWidth();
        double paperHeight = getPrintPaperHeight();

        if (paperWidth > availableCanvasWidth) {
            ws = ((double)availableCanvasWidth )/ ((double)paperWidth);
        }
        if (paperHeight > availableCanvasHeight) {
            hs = ((double)availableCanvasHeight) / ((double)paperHeight);
        }
        if (ws < hs) {
            return ws;
        }
        else {
            return hs;
        }
    }

    /**
     *
     * @return
     */
    public int getHPageCount() {
        return this.mHPageCount;
    }

    /**
     *
     * @return
     */
    public int getVPageCount() {
        return this.mVPageCount;
    }

    /**
     *
     * @return
     */
    public int getLastPrintRow() {
        if (mBook == null) {
            return 0;
        }
        try {
            String formula = mBook.getPrintArea();
            if (formula == null || (formula != null && formula.length() == 0)) {
                return mBook.getLastRow();
            }
            else {
                RangeRef range = mBook.formulaToRangeRef(formula);
                if (range != null) {
                    return range.getRow2();
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return mBook.getLastRow();
    }

    /**
     *
     * @return
     */
    public int getLastPrintCol() {
        if (mBook == null) {
            return 0;
        }
        try {
            String formula = mBook.getPrintArea();
            if (formula == null || (formula != null && formula.length() == 0)) {
                return mBook.getLastCol();
            }
            else {
                RangeRef range = mBook.formulaToRangeRef(formula);
                if (range != null) {
                    return range.getCol2();
                }

            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return mBook.getLastCol();
    }

    /**
     *
     * @return
     */
    public double getColWidth(int col) {
        double width = 0;
        try {
            short saveUnit = mBook.getColWidthUnits();
            mBook.setColWidthUnits(JBook.eColWidthUnitsTwips);
            width += mBook.getColWidth(col);
            mBook.setColWidthUnits(saveUnit);

            double scale = 1;
            if (!mBook.isPrintScaleFitToPage()) {
                scale =((double) mBook.getPrintScale()) / 100;
            }
            else {
                scale = getFitScale();
            }
            width = width * scale;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return width;

    }

    /**
     *
     * @return
     */
    public double getRowHeight(int row) {
        double height = 0;
        try {
            height += mBook.getRowHeight(row);
            double scale = 1;
            if (!mBook.isPrintScaleFitToPage()) {
                scale = ((double)mBook.getPrintScale()) / 100;
            }
            else {
                scale = getFitScale();
            }
            height = height * scale;

        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return height;

    }

    /**
     *
     * @return
     */
    public double getFitScale() {
        double scale = 1;
        if (this.getVPageCount() > 0 && this.getHPageCount() > 0) {
            double vScale = 1;
            double hScale = 1;
            if (mBook.getPrintScaleFitVPages() < this.getVPageCount()) {
                vScale = (double) mBook.getPrintScaleFitVPages() / (double)this.getVPageCount();
            }

            if (mBook.getPrintScaleFitHPages() < this.getHPageCount()) {
                hScale = (double) mBook.getPrintScaleFitHPages() / (double)this.getHPageCount();
            }

            if (vScale != 0 && vScale < scale) {
                scale = vScale;
            }
            if (hScale != 0 && hScale < scale) {
                scale = hScale;
            }
        }
        return scale;
    }
}
