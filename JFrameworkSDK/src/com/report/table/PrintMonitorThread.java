package com.report.table;

import java.awt.print.*;
import javax.swing.*;

public class PrintMonitorThread extends Thread {
  protected PrinterJob printerJob;
  protected JOptionPane optionPane;
  protected JDialog statusDialog;

  PrintMonitorThread() {
  }
  public void run() {
        statusDialog.setVisible(true);
        if (optionPane.getValue() !=
            JOptionPane.UNINITIALIZED_VALUE) {
          printerJob.cancel();
        }
  }
  public void setPrinterJob(PrinterJob printerJob){
    this.printerJob = printerJob;
  }
  public void setOptionPane(JOptionPane optionPane){
    this.optionPane = optionPane;
  }
  public void setStatusDialog(JDialog statusDialog){
    this.statusDialog = statusDialog;
  }
}
