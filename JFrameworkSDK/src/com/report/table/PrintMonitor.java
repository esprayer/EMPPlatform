package com.report.table;

import java.awt.print.*;
import javax.swing.*;
import java.util.ResourceBundle;

/**
 * Creating an instance of this class and printing it allows it to
 * add a status dialog during printing. The print requests are
 * simply delegated to the Pageable that actually contains the
 * data to be printed, but by intercepting those calls, we can update
 * the page number displayed in our dialog so that it indicates
 * which page is currently being displayed.
 */
public class PrintMonitor implements Pageable {

  static ResourceBundle res = ResourceBundle.getBundle("com.report.table.Language");
  protected PrinterJob printerJob;
  protected Pageable pageable;
  protected JOptionPane optionPane;
  protected JDialog statusDialog;

  public PrintMonitor(Pageable p) {
    pageable = p;
    printerJob = PrinterJob.getPrinterJob();
    String[] options = {res.getString("String_42")};
    optionPane = new JOptionPane("",
        JOptionPane.INFORMATION_MESSAGE,
        JOptionPane.CANCEL_OPTION,
        null, options);
    statusDialog = optionPane.createDialog(null,
        "打印机状态");
  }
  public int getNumberOfPages() {
    return pageable.getNumberOfPages();
  }
  public PageFormat getPageFormat(int index) {
    return pageable.getPageFormat(index);
  }
  /*
   * Update our dialog message and delegate the getPrintable() call
   */
  public Printable getPrintable(int index) {
    optionPane.setMessage("正在打印： " + (index + 1));
    return pageable.getPrintable(index);
  }
  /**
   * Create a new thread and have it call the print() method.
   * This ensures that the AWT event thread will be able to handle
   * the Cancel button if it is pressed, and can cancel the print job.
   */
  public void performPrint(boolean showDialog)
      throws PrinterException {
    printerJob.setPageable(this);
    if (showDialog) {
      boolean isOk = printerJob.printDialog();
      if (!isOk) return;
    }
    optionPane.setMessage(res.getString("String_46"));
    PrintMonitorThread t = new PrintMonitorThread();
    t.setPrinterJob(printerJob);
    t.setOptionPane(optionPane);
    t.setStatusDialog(statusDialog);
//    Thread t = new Thread(new Runnable() {
//      public void run() {
//        statusDialog.setVisible(true);
//        if (optionPane.getValue() !=
//            JOptionPane.UNINITIALIZED_VALUE) {
//          printerJob.cancel();
//        }
//      }
//    });
    t.start();
    printerJob.print();
    statusDialog.setVisible(false);
  }
}
