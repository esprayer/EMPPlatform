package com.efounder.dataobject.action;

import com.borland.dx.dataset.*;
import com.efounder.dataobject.view.*;
import javax.swing.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class DCTViewCommandAction extends DCTViewAction {
  protected String  Command = null;
  public DCTViewCommandAction(OBJView ExplorerView,String command,String s, char c, String s1, Icon icon)
  {
    super(ExplorerView,s, c, s1, icon);
    Command = command;
  }

  /**
   * actionPerformed
   *
   * @param ExplorerView DCTViewAction
   * @param DataRowArray DataRow[]
   */
  public void actionPerformed(OBJView ExplorerView,DataRow[] DataRowArray) {
  }

}
