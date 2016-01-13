package jservice.jbof.classes.GenerQueryObject.print.preview;

import java.util.*;

import com.f1j.swing.*;
import com.pub.comp.*;
import com.print.CanvasUtils;
import com.print.service.ColumnDefine;
import com.pub.util.StringFunction;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class JBookPrintPreviewModel {
  /**
   *
   */
  private JBook mBook = null;
  private JCustomPanel mCanvas = null;
  private Vector mPageInfos = null;
  private int mHPageCount = 0;
  private int mVPageCount = 0;
  private JBook PreviewBook = null;
  private double nRowScale = 1;
  private double nColScale = 1;
  private String printTitle = ""; //打印标题
  private int FixRow=0,FixCol=0,FixRowCount=0,FixColCount=0; //打印标题开始行，开始列，行数，列数
  /**
   *
   * @param book
   * @param canvas
   */
  public JBookPrintPreviewModel(JBook book, JCustomPanel canvas) {
    if (book != null && canvas != null) {
      mBook = book;
      mCanvas = canvas;
      PreviewBook = new JBook();
    }
  }

  public void initPageInfos() {
    setPrintTitle();
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
        JBookPrintPageInfo rowInfo = (JBookPrintPageInfo) rowInfos.get(row);
        JBookPrintPageInfo colInfo = (JBookPrintPageInfo) colInfos.get(col);
        JBookPrintPageInfo pageInfo = new JBookPrintPageInfo();
        pageInfo.mStartRow = rowInfo.mStartRow;
        pageInfo.mEndRow = rowInfo.mEndRow;
        pageInfo.mStartCol = colInfo.mStartCol;
        pageInfo.mEndCol = colInfo.mEndCol;

        this.mPageInfos.add(pageInfo);
      }
    }

  }

  /**
   * 设置调整为n页高时的，缩小比例.
   * 如果是调整为n页高n页宽，则根据实际情况设置比例，否则设置为1即不缩小
   */
  private void setnRowScale(double availablePageHeight, int firstrow,
                            int lastrow) {
    double rowscale = 1;
    try {
      if (getPrintScaleFitToPage()) {
        /**
         * 处理调整为n页高的情况。
         * 检查n页纸上的有效高度是否可以打印所有行，如果可以则行高不用处理。
         * 否则行高要缩小，缩小的比例是n页纸的有效高度/所有行的行高。
         */
        double nrowheight = availablePageHeight * this.getPrintScaleFitVPages();
        double allrowheight = 0;
        for (int i = firstrow; i < lastrow + 1; i++) {
          allrowheight += mBook.getRowHeight(i);
        }
        rowscale = nrowheight / allrowheight;
        if(rowscale>1){
          rowscale = 1;
        }
      }
      nRowScale = rowscale;
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * 设置调整为n页宽时的，缩小比例.
   * 如果是调整为n页高n页宽，则根据实际情况设置比例，否则设置为1即不缩小
   */
  private void setnColScale(double availablePageWidth, int firstcol,
                            int lastcol) {
    double colscale = 1;
    try {
      if (getPrintScaleFitToPage()) {
        /**
         * 处理调整为n页宽的情况。
         * 检查n页纸上的有效宽度是否可以打印所有列，如果可以则列宽不用处理；
         * 否则列宽要缩小，缩小的比例是n页纸的有效宽度/所有列的列宽。
         */
        double ncolwidth = availablePageWidth * this.getPrintScaleFitHPages();
        double allcolwidth = 0;
        for (int i = firstcol; i < lastcol + 1; i++) {
          allcolwidth += this.getColWidth_ncol(i);
        }
        colscale = ncolwidth / allcolwidth;
        if(colscale>1){
          colscale = 1;
        }
      }
      nColScale = colscale;
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   *
   */
  private Vector getRowInfos() {
    Vector rowInfos = new Vector();
    try {
      double availablePageHeight = 0;
      double heightMargin = 0;
      heightMargin = this.getTopMargin() + this.getBottomMargin();
      availablePageHeight = this.getPaperHeight() - heightMargin;

      double rowsHeight = 0;

      int pageCount = 0;
      int firstRow = this.getFixedRowCount();
      int lastRow = getLastRow();
      //设置调整为n页高时，行高的缩放比例
      setnRowScale(availablePageHeight,firstRow,lastRow);
      for (int i = firstRow; i < lastRow + 1; i++) {
        if (rowsHeight <= availablePageHeight) {
          rowsHeight += getRowHeight(i);
        }
        else {
          rowsHeight = rowsHeight - getRowHeight(i);
          i = i - 1;
          JBookPrintPageInfo info = new JBookPrintPageInfo();
          if (pageCount == 0) {
            info.mStartRow = firstRow;
          }
          else {
            info.mStartRow = ( (JBookPrintPageInfo) rowInfos.get(pageCount -
                1)).mEndRow + 1;
          }
          info.mEndRow = i - 1;
          rowInfos.add(pageCount, info);
          rowsHeight = 0;
          pageCount++;
        }
      }
      if (pageCount == 0) {
        JBookPrintPageInfo info = new JBookPrintPageInfo();
        info.mStartRow = firstRow;
        info.mEndRow = lastRow;
        rowInfos.add(pageCount, info);
      }
      else {
        JBookPrintPageInfo info = new JBookPrintPageInfo();
        info.mStartRow = ( (JBookPrintPageInfo) rowInfos.get(pageCount - 1)).
            mEndRow + 1; ;
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
      double availablePageWidth = 0;
      double widthMargin = 0;
      widthMargin = this.getLeftMargin() + this.getRightMargin();
      availablePageWidth = this.getPaperWidth() - widthMargin;
      //减去固定列的宽度
      double Fixcolwidth =0;
      for(int i=this.getPrintFixCol();i<this.getPrintFixCol()+this.getPrintFixColCount();i++){
           Fixcolwidth += getColWidth(i);
      }
      availablePageWidth = availablePageWidth - Fixcolwidth;

      double colsWidth = 0;
      int pageCount = 0;
      int firstCol = this.getFixedColCount();
      int lastCol = getLastCol();
      setnColScale(availablePageWidth,firstCol,lastCol);

      for (int i = firstCol; i < lastCol + 1; i++) {
        if (colsWidth <= availablePageWidth) {
          double colwidth = 0;
          colwidth = getColWidth(i);
          colsWidth = colsWidth + colwidth;
        }
        else {
          i = i - 1;
          JBookPrintPageInfo info = new JBookPrintPageInfo();
          if (pageCount == 0) {
            info.mStartCol = firstCol;
          }
          else {
            info.mStartCol = ( (JBookPrintPageInfo) colInfos.get(pageCount -
                1)).mEndCol + 1;
          }
          info.mEndCol = i - 1;
          colInfos.add(pageCount, info);
          colsWidth = 0;
          pageCount++;

        }
      }
      if (pageCount == 0) {
        JBookPrintPageInfo info = new JBookPrintPageInfo();
        info.mStartCol = firstCol;
        info.mEndCol = lastCol;
        colInfos.add(pageCount, info);
      }
      else {
        JBookPrintPageInfo info = new JBookPrintPageInfo();
        info.mStartCol = ( (JBookPrintPageInfo) colInfos.get(pageCount - 1)).
            mEndCol + 1; ;
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
    if (mPageInfos == null) {
      this.mPageInfos = new Vector();
    }
    return this.mPageInfos.size();
  }

  /**
   *
   * @param pageIndex
   * @return
   */
  public JBookPrintPageInfo getPageInfo(int pageIndex) {
    if (pageIndex < this.getPageCount()) {
      return (JBookPrintPageInfo)this.mPageInfos.get(pageIndex);
    }
    return null;
  }

  /**
   *
   * @return
   */
  public int getCanvasX() {
    return mCanvas.getX();
  }

  /**
   *
   * @return
   */
  public int getCanvasY() {
    return mCanvas.getY();
  }

  /**
   *
   * @return
   */
  public double getCanvasWidth() {
    return mCanvas.getWidth();
  }

  /**
   *
   * @return
   */
  public double getCanvasHeight() {
    return mCanvas.getHeight();
  }

  /**
   *
   * @return
   */
  public int getX() {
    double paperFitScale = this.getPaperFitScale();
    double canvasWidth = getCanvasWidth();
    double paperWidth = JBookPrintPreviewUtils.twipsToDots(mBook,
        this.getPaperWidth()) * paperFitScale;

    return (int) (canvasWidth - paperWidth) / 2;
  }

  /**
   *
   * @return
   */
  public int getY() {
    return getCanvasY();
  }

  /**
   *
   * @return
   */
  public double getPaperWidth() {
    return mBook.getPrintPaperWidth();
  }

  /**
   *
   * @return
   */
  public double getPaperHeight() {
    return mBook.getPrintPaperHeight();
  }

  /**
   *
   * @return
   */
  public double getTopMargin() {
    return JBookPrintPreviewUtils.inchToTwips(mBook, mBook.getPrintTopMargin());
  }

  /**
   *
   * @return
   */
  public double getBottomMargin() {
    return JBookPrintPreviewUtils.inchToTwips(mBook, mBook.getPrintBottomMargin());
  }

  /**
   *
   * @return
   */
  public double getLeftMargin() {
    return JBookPrintPreviewUtils.inchToTwips(mBook, mBook.getPrintLeftMargin());
  }

  /**
   *
   * @return
   */
  public double getRightMargin() {
    return JBookPrintPreviewUtils.inchToTwips(mBook, mBook.getPrintRightMargin());
  }

  /**
   *
   * @return
   */
  public double getHeaderMargin() {
    return JBookPrintPreviewUtils.inchToTwips(mBook, mBook.getPrintHeaderMargin());
  }

  /**
   *
   * @return
   */
  public double getFooterMargin() {
    return JBookPrintPreviewUtils.inchToTwips(mBook, mBook.getPrintFooterMargin());
  }

  /**
   *
   * @return
   */
  public double getPaperFitScale() {
    double canvasWidth = getCanvasWidth();
    double canvasHeight = getCanvasHeight();
    double paperWidth = JBookPrintPreviewUtils.twipsToDots(mBook,
        this.getPaperWidth());
    double paperHeight = JBookPrintPreviewUtils.twipsToDots(mBook,
        this.getPaperHeight());
    double scale = 1;
    double wScale = 1;
    double hScale = 1;

    if (paperWidth > canvasWidth) {
      wScale = canvasWidth / paperWidth;
    }
    if (paperHeight > canvasHeight) {
      hScale = canvasHeight / paperHeight;
    }
    if (wScale < hScale) {
      scale = wScale;
    }
    else {
      scale = hScale;
    }
    return scale;
  }

  /**
   *
   * @return
   */
  public JCustomPanel getCanvas() {
    return mCanvas;
  }

  /**
   *
   * @return
   */
  public JBook getBook() {
    return mBook;
  }
  public int getPrintFixRow(){
    return FixRow;
  }
  public int getPrintFixCol(){
    return FixCol;
  }
  public int getPrintFixColCount(){
    return FixColCount;
  }
  public int getPrintFixRowCount(){
    return FixRowCount;
  }

  /**
   * 取得打印标题
   * @return
   */
  public void setPrintTitle(){
    try{
      printTitle = mBook.getPrintTitles();
      if(printTitle!=null || !printTitle.equals("")){
        Vector rowcols = StringFunction.convertFromStringToStringVectorByString(printTitle,",");
        for(int i=0;i<rowcols.size();i++){
          String srowcols = "";
          srowcols = (String)rowcols.get(i);
          Vector rowcol = StringFunction.convertFromStringToStringVectorByString(srowcols,"!");
          for(int j=0;j<rowcol.size();j++){
             String srowcol = "";
             srowcol = (String)rowcol.get(j);
             if(srowcol.indexOf("$")>=0){
               Vector hlh =StringFunction.convertFromStringToStringVectorByString(srowcol,":");
               for(int k=0;k<hlh.size();k++){
                 String shlh = "";
                 shlh = (String)hlh.get(k);
                 shlh = shlh.substring(1);
                 if("0123456789".indexOf(shlh)>=0){
                   if(k==0){
                     FixRow = Integer.parseInt(shlh) - 1;
                   }else{
                     FixRowCount = Integer.parseInt(shlh) - FixRow;
                   }
                 }else{
                   long col = 0;
                   for(int l=shlh.length()-1; l>=0; l--){
                     col += java.lang.Math.pow(26, l) * (shlh.charAt(shlh.length()-1-l)-'A'+1);
                   }
                   if(k==0){
                     FixCol = (int)col - 1;
                   }else{
                     FixColCount = (int)col - FixCol;
                   }
                 }
               }
             }
          }
        }

      }
    }catch(Exception e){
      e.printStackTrace();
    }
  }

  /**
   *固定行数
   * @return
   */
  public int getFixedRowCount() {
    return mBook.getFixedRows();
  }

  /**
   *固定列数
   * @return
   */
  public int getFixedColCount() {
    return mBook.getFixedCols();
  }

  /**
   * 是否要调整到n页高n页宽
   * @return
   */
  public boolean getPrintScaleFitToPage() {
    return mBook.isPrintScaleFitToPage();
  }

  /**
   * 调整为几页宽
   * @return
   */
  public int getPrintScaleFitHPages() {
    return mBook.getPrintScaleFitHPages();
  }

  /**
   * 调整为几页高
   * @return
   */
  public int getPrintScaleFitVPages() {
    return mBook.getPrintScaleFitVPages();
  }

  /**
   * 打印比例
   * @return
   */
  public int getPrintScale() {

    return mBook.getPrintScale();
  }

  /**
   *
   * @return
   */
  public int getLastRow() {
    return mBook.getLastRow();
  }

  /**
   *
   * @return
   */
  public int getLastCol() {
    return mBook.getLastCol();
  }

  /**
   *
   * @param row
   * @return
   */
  public double getRowHeight(int row) {
    double height = 0;
    try {
      height = mBook.getRowHeight(row);
      //处理打印比例
      if (!mBook.isPrintScaleFitToPage()) {
        height = height * (this.getPrintScale() / 100.00);
      }
      //处理调整为n页高的情况
      height = height * nRowScale;

    }
    catch (Exception e) {
      e.printStackTrace();
    }
    return height;
  }

  /**
   *
   * @param col
   * @return
   */
  public double getColWidth(int col) {
    double width = 0;
    try {
      short saveUnit = mBook.getColWidthUnits();
      width = mBook.getColWidth(col);
      mBook.setColWidthUnits(JBook.eColWidthUnitsTwips);
      width = mBook.getColWidth(col);
      //必需要处理显示出比例
      width = width / (mBook.getViewScale() / 100.00);
      //处理打印比例
      if (!mBook.isPrintScaleFitToPage()) {
        width = width * (this.getPrintScale() / 100.00);
      }
      //处理调整为n页宽的情况
      width = width * nColScale;
      mBook.setColWidthUnits(saveUnit);
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    return width;
  }

  /**
   *
   * @param col
   * @return
   */
  public double getColWidth_ncol(int col) {
    double width = 0;
    try {
      short saveUnit = mBook.getColWidthUnits();
      width = mBook.getColWidth(col);
      mBook.setColWidthUnits(JBook.eColWidthUnitsTwips);
      width = mBook.getColWidth(col);
      //必需要处理显示出比例
      width = width / (mBook.getViewScale() / 100.00);
      //处理打印比例
      if (!mBook.isPrintScaleFitToPage()) {
        width = width * (this.getPrintScale() / 100.00);
      }
      mBook.setColWidthUnits(saveUnit);
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    return width;
  }

}