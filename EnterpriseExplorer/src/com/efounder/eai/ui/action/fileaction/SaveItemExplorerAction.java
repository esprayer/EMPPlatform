package com.efounder.eai.ui.action.fileaction;

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

public class SaveItemExplorerAction extends ExplorerAction {
  protected IWindow winSave = null;
  /**
   *
   */
  public SaveItemExplorerAction() {
  }
  /**
   *
   * @param browser EnterpriseExplorer
   */
  public void actionPerformed(EnterpriseExplorer browser) {
    if ( winSave instanceof FileActionInterface ) {
      try {((FileActionInterface)winSave).saveNode();} catch ( Exception e ) {}
    }
  }
  /**
   *
   * @param s String
   * @param c char
   * @param s1 String
   * @param icon Icon
   */
  public SaveItemExplorerAction(String s, char c, String s1, Icon icon,IWindow win) {
      super(s, c, s1, icon);
      winSave = win;
      setWaitInvoke(false);
  }

}
