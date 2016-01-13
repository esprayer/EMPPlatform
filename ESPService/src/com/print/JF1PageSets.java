package com.print;

import com.f1j.swing.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class JF1PageSets {
  protected JBook PageBook  = null; // 与此相关联的JBook对象
  public boolean PrintLandscape = true; // 横向纵向
  public String PrintArea   = null; // 打印区域
  public String PrintTitles = null; // 打印标题
  public String PrintHeader = null; // 页眉
  public String PrintFooter = null; // 页脚
  public double PrintHeaderMargin = 0.00;// 页眉的上边距 以英寸为单位
  public double PrintFooterMargin = 0.00;// 页脚的下边距 以英寸为单位
  public double PrintTopMargin    = 0.00;// 页顶边距 以英寸为单位
  public double PrintBottomMargin = 0.00;// 页下边距 以英寸为单位
  public double PrintLeftMargin   = 0.00;// 页左边距 以英寸为单位
  public double PrintRightMargin  = 0.00;// 页右连距 以英寸为单位
  public int    PrintNumberOfCopies = 1; // 打印份数
  public double PrintPaperHeight  = 0;   // 当前纸张类型的高度，以twips为单位
  public double PrintPaperWidth   = 0;   // 当前纸张类型的宽度，以twips为单位
  public int    PrintScale        = 0;   // 打印的缩放比例
  public int    PrintScaleFitHPages = 0; // 缩放横向纸张
  public int    PrintScaleFitVPages = 0; // 缩放纵向纸张
  public boolean PrintScaleFitToPage = false;
  public boolean PrintNoColor = true;
  public int    PrintStartPageNumber= 0; // 打印起始页号
  public short  PrintPaperSize    = 0;   // 打印设置的纸张Size，只是一个纸张列表的索引
  /**
   *
   * @param Book JBook
   */
  public JF1PageSets(JBook Book) {
    PageBook = Book;

    initPageSets();
  }
  protected void initPageSets() {
    if ( PageBook == null ) return;
    try {
      PrintLandscape = PageBook.isPrintLandscape();
      PrintArea   = PageBook.getPrintArea(); // 打印区域
      PrintTitles = PageBook.getPrintTitles(); // 打印标题
      PrintHeader = PageBook.getPrintHeader(); // 页眉
      PrintHeaderMargin = PageBook.getPrintHeaderMargin(); // 页眉的上边距
      PrintFooter = PageBook.getPrintFooter(); // 页脚
      PrintFooterMargin = PageBook.getPrintFooterMargin(); // 页脚的边距
      PrintTopMargin = PageBook.getPrintTopMargin(); // 页顶边距
      PrintBottomMargin = PageBook.getPrintBottomMargin(); // 页下边距
      PrintLeftMargin = PageBook.getPrintLeftMargin(); // 页左边距
      PrintRightMargin = PageBook.getPrintRightMargin(); // 页右连距
      PrintNumberOfCopies = PageBook.getPrintNumberOfCopies(); // 打印份数
      PrintPaperHeight = Math.floor(CanvasUtils.convertTwiptoInch(PageBook.getPrintPaperHeight())*100)/100; // 当前纸张类型的高度，以twips为单位 转换到英寸
      PrintPaperWidth  = Math.floor(CanvasUtils.convertTwiptoInch(PageBook.getPrintPaperWidth())*100)/100; // 当前纸张类型的宽度，以twips为单位 转换到英寸
      PrintPaperSize = PageBook.getPrintPaperSize(); // 打印设置的纸张Size，只是一个数据的索引
      PrintScaleFitToPage = PageBook.isPrintScaleFitToPage();
      PrintScale = PageBook.getPrintScale(); // 打印的缩放比例
      PrintScaleFitHPages = PageBook.getPrintScaleFitHPages(); // 缩放横向纸张
      PrintScaleFitVPages = PageBook.getPrintScaleFitVPages(); // 缩放纵向纸张
      PrintStartPageNumber = PageBook.getPrintStartPageNumber(); // 打印起始页号
      PrintNoColor         = PageBook.isPrintNoColor();  // 是否是黑白打印
    } catch ( Exception e ) {
      e.printStackTrace();
    }
  }
  /**
   * 获取与页面参数对象相关联的JBook
   * @return JBook
   */
  public JBook getBook() {
    return PageBook;
  }
  public void saveToBook(JBook PageBook) {
    try {
      PageBook.setPrintLandscape(PrintLandscape);
      PageBook.setPrintArea(PrintArea); // 打印区域
      PageBook.setPrintTitles(PrintTitles); // 打印标题
      PageBook.setPrintHeader(PrintHeader); // 页眉
      PageBook.setPrintHeaderMargin(PrintHeaderMargin); // 页眉的上边距
      PageBook.setPrintFooter(PrintFooter); // 页脚
      PageBook.setPrintFooterMargin(PrintFooterMargin); // 页脚的边距
      PageBook.setPrintTopMargin(PrintTopMargin); // 页顶边距
      PageBook.setPrintBottomMargin(PrintBottomMargin); // 页下边距
      PageBook.setPrintLeftMargin(PrintLeftMargin); // 页左边距
      PageBook.setPrintRightMargin(PrintRightMargin); // 页右连距
      PageBook.setPrintNumberOfCopies(PrintNumberOfCopies); // 打印份数

//      PrintPaperHeight = CanvasUtils.convertTwiptoInch(PageBook.
//          getPrintPaperHeight()); // 当前纸张类型的高度，以twips为单位 转换到英寸
//      PrintPaperWidth = CanvasUtils.convertTwiptoInch(PageBook.
//          getPrintPaperWidth()); // 当前纸张类型的宽度，以twips为单位 转换到英寸
//      PrintPaperSize = PageBook.getPrintPaperSize(); // 打印设置的纸张Size，只是一个数据的索引
      PageBook.setPrintPaperSize(PrintPaperSize);

      PageBook.setPrintScaleFitToPage(PrintScaleFitToPage);
      PageBook.setPrintScale(PrintScale);// 打印的缩放比例
      PageBook.setPrintScaleFitHPages(PrintScaleFitHPages);// 缩放横向纸张
      PageBook.setPrintScaleFitVPages(PrintScaleFitVPages);// 缩放纵向纸张
      PageBook.setPrintStartPageNumber(PrintStartPageNumber);// 打印起始页号
      PageBook.setPrintNoColor(PrintNoColor);
    } catch ( Exception e ) {
      e.printStackTrace();
    }
  }
}
