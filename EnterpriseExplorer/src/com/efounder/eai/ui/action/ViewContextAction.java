package com.efounder.eai.ui.action;

import com.efounder.eai.ide.*;
import javax.swing.*;
import com.efounder.pfc.window.*;


/**
 * <p>Title: Enterprise Explorer</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: EFounder</p>
 * @author Skyline
 * @version 1.0
 */

public class ViewContextAction extends ExplorerAction {
  protected IView viewContext = null;
  protected String command = null;

  public void doUpdate(EnterpriseExplorer explorer){
    if ( "CloseOther".equals(command) ) {
      int Count = viewContext.getWindowCount();
      this.setEnabled(Count == 1 ? false : true);
    }
  }

  /**
   *
   * @param view IView
   * @param command String
   * @param s String
   * @param c char
   * @param s1 String
   * @param icon Icon
   */
  public ViewContextAction(IView view,String command,String s, char c, String s1, Icon icon) {
    super(s,c,s1,icon);
    viewContext  = view;
    this.command = command;
  }
  /**
   *
   * @param browser EnterpriseExplorer
   */
  public void actionPerformed(EnterpriseExplorer browser) {
    /**@todo Implement this com.efounder.eai.ide.ExplorerAction abstract method*/
  }

}
