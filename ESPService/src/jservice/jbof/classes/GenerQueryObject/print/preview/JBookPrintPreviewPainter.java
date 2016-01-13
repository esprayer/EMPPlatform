package jservice.jbof.classes.GenerQueryObject.print.preview;

import java.awt.*;

import com.f1j.swing.*;
import com.pub.comp.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class JBookPrintPreviewPainter {
  /**
   *
   */
  private JBookPrintPreviewModel mModel = null;

  /**
   *
   */
  public JBookPrintPreviewPainter(JBook book, JCustomPanel canvas) {
    if (book != null && canvas != null) {
      mModel = new JBookPrintPreviewModel(book, canvas);
    }
  }

  /**
   *
   */
  public void draw(Graphics g, int pageIndex) {
    if (mModel != null) {

      mModel.initPageInfos();

      drawCanvas(g);
      drawPaper(g);
      drawBook(g, pageIndex);
    }
  }

  /**
   *
   */
  private void drawCanvas(Graphics g) {
    int x = mModel.getCanvasX();
    int y = mModel.getCanvasY();
    int canvasWidth = (int) mModel.getCanvasWidth();
    int canvasHeight = (int) mModel.getCanvasHeight();

    g.setColor(Color.GRAY);
    g.fillRect(x, y, canvasWidth, canvasHeight);
  }

  /**
   *
   */
  private void drawPaper(Graphics g) {
    int x = mModel.getX();
    int y = mModel.getY();
    double paperFitScale = mModel.getPaperFitScale();
    int paperWidth = (int) (JBookPrintPreviewUtils.twipsToDots(mModel.getBook(),
        (mModel.getPaperWidth())) * paperFitScale);
    int paperHeight = (int) (JBookPrintPreviewUtils.twipsToDots(mModel.getBook(),
        (mModel.getPaperHeight())) * paperFitScale);

    //纸张
    g.setColor(Color.WHITE);
    g.fillRect(x, y, paperWidth, paperHeight);
    //黑边
    g.setColor(Color.BLACK);
    g.drawRect(x - 1, y - 1, paperWidth + 1, paperHeight + 1);
  }

  /**
   *
   */
  private void drawBook(Graphics g, int pageIndex) {
    try {
      JBookPrintPageInfo info = mModel.getPageInfo(pageIndex);
      if (info == null) {
        return;
      }
      JBook book = mModel.getBook();

      double paperFitScale = mModel.getPaperFitScale();
      int x = mModel.getX() +
          (int) (JBookPrintPreviewUtils.twipsToDots(mModel.getBook(),
          mModel.getLeftMargin()) * paperFitScale);
      int y = mModel.getY() +
          (int) (JBookPrintPreviewUtils.twipsToDots(mModel.getBook(),
          mModel.getTopMargin()) * paperFitScale);

      double widthMargin = mModel.getLeftMargin() + mModel.getRightMargin();
      double heightMargin = mModel.getTopMargin() + mModel.getBottomMargin();

      int width = (int) (JBookPrintPreviewUtils.twipsToDots(mModel.getBook(),
          ( (mModel.getPaperWidth() - widthMargin))) * paperFitScale);
      int height = (int) (JBookPrintPreviewUtils.twipsToDots(mModel.getBook(),
          ( (mModel.getPaperHeight() - heightMargin))) * paperFitScale);

      int startRow = info.mStartRow;
      int rowCount = info.mEndRow - info.mStartRow + 1;

      int[] rows = new int[] {
          rowCount};
      int startCol = info.mStartCol;
      int colCount = info.mEndCol - info.mStartCol + 1;
      int[] cols = {
          colCount};

//      int fixedRowCount = mModel.getFixedRowCount();
//      int fixedColCount = mModel.getFixedColCount();
      int fixedRowCount = mModel.getPrintFixRowCount();
      int fixedColCount = mModel.getPrintFixColCount();
      int fixedRow = mModel.getPrintFixRow();
      int fixedCol = mModel.getPrintFixCol();

      short saveunits = book.getColWidthUnits();
      book.setColWidthUnits(book.eColWidthUnitsTwips);
      book.draw(g,
                x, //starting x coordinate of graphics image in offscreen coordinates.
                y, //starting y coordinate of graphics image in offscreen coordinates.
                width, //width of the image in offscreen coordinates.
                height, //height of the image if offscreen coordinates.
                startRow, //beginning row of the workbook to be drawn - 0 based.
                startCol, //beginning column of the workbook to be drawn - 0 based.
                rows, //number of rows to be drawn.
                cols, //number of columns to be drawn.
                fixedRow, //beginning fixed row of the drawn worksheet - 0 based.
                fixedRowCount, //number of rows to be fixed in the drawn worksheet.
                fixedCol, //beginning fixed column of the drawn worksheet - 0 based.
                fixedColCount); //number of columns to be fixed in the drawn worksheet.
      book.setColWidthUnits(saveunits);
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   *
   * @return
   */
  public int getPageCount() {
    return mModel.getPageCount();
  }

}