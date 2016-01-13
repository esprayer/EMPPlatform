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

public class RedoExplorerAction extends ExplorerAction {
static ResourceBundle res = ResourceBundle.getBundle("com.efounder.eai.ui.action.editaction.Res");
  public static RedoExplorerAction redoEditAction = new RedoExplorerAction(res.getString("_R_"),'R',res.getString("KEY1"),ExplorerIcons.getExplorerIcon("oicons/redo.png"));;
  public RedoExplorerAction(String s, char c, String s1, Icon icon)
  {
      super(s, c, s1, icon);
  }

  public void actionPerformed(EnterpriseExplorer explorer) {
    IWindow win = explorer.ContentView.getActiveWindow();
    if ( win instanceof EditActionInterface ) {
      ((EditActionInterface)win).editRedo();
    }
  }
  public void doUpdate(EnterpriseExplorer explorer)
  {
    IWindow win = explorer.ContentView.getActiveWindow();boolean enable = false;
    if ( win instanceof EditActionInterface ) {
      enable = ((EditActionInterface)win).canUndo();
    }
    setEnabled(enable);
  }


}
