package com.efounder.eai.ui.action.moveaction;

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

public class PriorExplorerAction extends ExplorerAction {
static ResourceBundle res = ResourceBundle.getBundle("com.efounder.eai.ui.action.moveaction.Res");
  public static PriorExplorerAction priorAction = new PriorExplorerAction(res.getString("KEY6"),'O',res.getString("KEY7"),ExplorerIcons.getExplorerIcon("prior"));
  public PriorExplorerAction() {
  }
  /**
   *
   * @param explorer EnterpriseExplorer
   */
  public void actionPerformed(EnterpriseExplorer explorer) {
    IWindow win = explorer.ContentView.getActiveWindow();
    if ( win instanceof MoveActionInterface ) {
      ((MoveActionInterface)win).movePrior();
    }
  }
  /**
   *
   * @param explorer EnterpriseExplorer
   */
  public void doUpdate(EnterpriseExplorer explorer) {
    IWindow win = explorer.ContentView.getActiveWindow();boolean enable = false;
    if ( win instanceof MoveActionInterface ) {
      enable = ((MoveActionInterface)win).canMovePrior();
    }
    setEnabled(enable);
  }

  public PriorExplorerAction(String s, char c, String s1, javax.swing.Icon icon)
  {
      super(s, c, s1, icon);
  }

}
