package com.print.account;

import com.print.service.*;
import jservice.jbof.classes.GenerQueryObject.*;
import com.f1j.swing.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class JAccountBookPrinter {
  JBook Book;
  public JAccountBookPrinter() {
  }
  /**
   * 此处为回调函数，在确定此次打印一共有多少行时调用，一般对于余额表类的打印直接返回0，对于账页方式的打印可能需要返回相应的
   * @return Integer
   */
  public Integer getCustomRows(JAccountBookStub abs) {
    return new Integer(0);
  }
  // 实现真正的打印
  /**
   *
   * @param abs JAccountBookStub
   * @param SheetIndex int
   * @param context JQueryStubObject
   * @return Object
   */
  public Object printOjbect(JAccountBookStub abs,int SheetIndex,JQueryStubObject context) {
    // 获取JBook
    Book = abs.getViewBook();
    // 设置打印Sheet
    setSheet(abs,SheetIndex);
    // 形成打印格式(打印格式的形成是通用方式)
    AccountPrint.printAccountBookFormat(abs);
    // 打印数据对刚刚绘制好的格式(打印格式形成是通用的方法，其中可以回调用户自定义方法)
    AccountPrint.printAccouontBookData(abs);
    return null;
  }
  /**
   *
   * @param abs JAccountBookStub
   * @param SheetIndex int
   */
  private void setSheet(JAccountBookStub abs,int SheetIndex) {
    try {
      Book.setColWidthUnits(Book.eColWidthUnitsNormal);
      int SheetCount = Book.getBook().getSheetCount();
      if (SheetIndex >= SheetCount) { // 如果不存在这个Sheet
        Book.insertSheets(SheetCount, 1);
      } else {
        Book.setSheet(SheetIndex);
      }
      //

      // 设置不打印Grid
      Book.setPrintGridLines(false);
      Book.setShowGridLines(false);
      abs.getF1PageSets().saveToBook(Book);
      int Lastrow = Book.getLastRow();int Lastcol = Book.getLastCol();
      if ( Lastrow != -1 && Lastcol != -1 )
        Book.clearRange(0,0,Lastrow,Lastcol,Book.eClearAll);
    } catch ( Exception e ) {
      e.printStackTrace();
    }
  }
}
