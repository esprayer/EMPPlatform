package com.efounder.eai.ui.action.editaction;

import com.efounder.eai.ide.*;
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

public class DeleteExplorerAction extends ExplorerAction {
static ResourceBundle res = ResourceBundle.getBundle("com.efounder.eai.ui.action.editaction.Res");
  public static DeleteExplorerAction deleteEditAction = new DeleteExplorerAction(res.getString("_D_"),'D',res.getString("KEY"),ExplorerIcons.getExplorerIcon("oicons/delete.png"));
  public DeleteExplorerAction(String s, char c, String s1, javax.swing.Icon icon)
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
      ((EditActionInterface)win).editDelete();
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
      enable = ((EditActionInterface)win).canDelete();
    }
    setEnabled(enable);
  }
}
