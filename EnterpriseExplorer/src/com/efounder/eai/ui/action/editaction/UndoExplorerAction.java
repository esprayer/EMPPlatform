package com.efounder.eai.ui.action.editaction;

import com.efounder.eai.ide.*;
import javax.swing.*;
import com.efounder.pfc.window.*;
import java.util.ResourceBundle;

/**
 * <p>Title: Enterprise Explorer</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: EFounder</p>
 * @author Skyline
 * @version 1.0
 */

public class UndoExplorerAction extends ExplorerAction {
static ResourceBundle res = ResourceBundle.getBundle("com.efounder.eai.ui.action.editaction.Res");
  public static UndoExplorerAction undoEditAction = new UndoExplorerAction(res.getString("_Z_"),'Z',res.getString("KEY3"),ExplorerIcons.getExplorerIcon("oicons/undo.png"));;
  /**
   *
   * @param s String
   * @param c char
   * @param s1 String
   * @param icon Icon
   */
  public UndoExplorerAction(String s, char c, String s1, Icon icon)
  {
      super(s, c, s1, icon);
  }
  /**
   *
   * @param explorer EnterpriseExplorer
   */
  public void actionPerformed(EnterpriseExplorer explorer) {
    IWindow win = explorer.ContentView.getActiveWindow();
    if ( win instanceof EditActionInterface ) {
      ((EditActionInterface)win).editUndo();
    }
  }
  /**
   *
   * @param explorer EnterpriseExplorer
   */
  public void doUpdate(EnterpriseExplorer explorer)
  {
    IWindow win = explorer.ContentView.getActiveWindow();boolean enable = false;
    if ( win instanceof EditActionInterface ) {
      enable = ((EditActionInterface)win).canUndo();
    }
    setEnabled(enable);
  }


}
