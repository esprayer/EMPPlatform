package com.efounder.eai.ui;

import java.awt.event.*;

/**
 * <p>Title: Enterprise Explorer</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2004</p>
 *
 * <p>Company: EFounder</p>
 *
 * @author Skyline
 * @version 1.0
 */
public class DialogViewAdapter extends WindowAdapter {
  /**
   *
   */
  JDialogView dialogView = null;
  /**
   *
   * @param dialogView JDialogView
   */
  public DialogViewAdapter(JDialogView dialogView) {
    this.dialogView = dialogView;
  }
  public void windowClosing(WindowEvent e)
  {
    closeDialogView(dialogView);
  }
  public static void closeDialogView(JDialogView dialogView) {
    dialogView.setDefaultCloseOperation(JDialogView.DO_NOTHING_ON_CLOSE);
    if ( dialogView.closeAllWindow() ) {
      dialogView.setDefaultCloseOperation(JDialogView.DISPOSE_ON_CLOSE);
      dialogView.hideView();
    }
  }
}
