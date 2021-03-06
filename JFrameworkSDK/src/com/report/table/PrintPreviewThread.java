package com.report.table;

import javax.swing.*;
import java.awt.print.*;
import com.report.table.jxml.*;
import java.awt.*;
import java.util.ResourceBundle;

public class PrintPreviewThread extends Thread {

  static ResourceBundle res = ResourceBundle.getBundle("com.report.table.Language");
  private JPanel topPanel;
  private JTable table;
  private PageFormat pageFormat;
  private TableManager tableManager;

  PrintPreviewThread() {
  }
  public void run() {
                TablePrinter tp = new TablePrinter(topPanel, table, pageFormat, tableManager);
                PrintPreviewer pp = new PrintPreviewer(tp, 0);
                JDialog dlg = new JDialog(new JFrame(res.getString("String_47")), res.getString("String_48"),true);
                dlg.getContentPane().add(pp);
		        Dimension ScnSize = Toolkit.getDefaultToolkit().getScreenSize();
                int width = (int)ScnSize.getWidth();
                int height = (int)ScnSize.getHeight()-20;
                dlg.setSize(width,height);
                dlg.setVisible(true);
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
