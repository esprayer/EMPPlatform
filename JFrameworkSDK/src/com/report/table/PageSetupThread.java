package com.report.table;

import java.awt.print.*;

public class PageSetupThread extends Thread {
  private PageFormat pageFormat;
  PageSetupThread() {
  }
  public void run() {
    PrinterJob pj = PrinterJob.getPrinterJob();
    pageFormat = pj.pageDialog(pageFormat);
  }
  public void serPageFormat(PageFormat pageFormat){
    this.pageFormat = pageFormat;
  }
  public PageFormat getPageFormat(){
    return pageFormat;
  }
}
