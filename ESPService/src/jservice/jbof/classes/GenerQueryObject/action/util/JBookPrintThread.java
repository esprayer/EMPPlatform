package jservice.jbof.classes.GenerQueryObject.action.util;

import com.f1j.swing.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: Pansoft</p>
 * @author Stephen Zhao
 * @version 1.0
 */

public class JBookPrintThread
    extends Thread {
  private JBook mBook = null;
  public JBookPrintThread(JBook book) {
    this.mBook = book;
  }

  public void run() {
    try {
      com.f1j.swing.ss.PageSetupDlg PageDlg = new com.f1j.swing.ss.PageSetupDlg(mBook);
      PageDlg.show();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

}