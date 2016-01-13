package com.report.table;

import javax.swing.*;
import java.awt.print.*;
import com.report.table.jxml.*;
import java.util.ResourceBundle;

public class PrintThread extends Thread {
  static ResourceBundle res = ResourceBundle.getBundle("com.report.table.Language");
  private JPanel topPanel;
  private JTable table;
  private PageFormat pageFormat;
  private TableManager tableManager;

  PrintThread() {
  }
  public void run() {
                TablePrinter tp = new TablePrinter(topPanel, table, pageFormat, tableManager);
                PrintMonitor pm = new PrintMonitor(tp);
                try {
                    pm.performPrint(true);
                } catch (PrinterException pe) {
                    pe.printStackTrace();
                    JOptionPane.showMessageDialog(
                        table,
                        "Printing error:" + pe.getMessage());
                }
  }
  public void setTopPanel (JPanel topPanel){
    this.topPanel = topPanel;
  }
  public void setTable (JTable table){
    this.table = table;
  }
  public void setPageFormat(PageFormat pageFormat){
    this.pageFormat = pageFormat;
  }
  public void setTableManager(TableManager tableManager){
    this.tableManager = tableManager;
  }
}
