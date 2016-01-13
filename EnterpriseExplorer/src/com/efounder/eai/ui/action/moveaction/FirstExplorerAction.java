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

public class FirstExplorerAction extends ExplorerAction {
static ResourceBundle res = ResourceBundle.getBundle("com.efounder.eai.ui.action.moveaction.Res");
  public static FirstExplorerAction firstAction = new FirstExplorerAction(res.getString("KEY4"),'O',res.getString("KEY5"),ExplorerIcons.getExplorerIcon("first"));
  public FirstExplorerAction() {
  }
  /**
   *
   * @param explorer EnterpriseExplorer
   */
  public void actionPerformed(EnterpriseExplorer explorer) {
    IWindow win = explorer.ContentView.getActiveWindow();
    if ( win instanceof MoveActionInterface ) {
      ((MoveActionInterface)win).moveFirst();
    }
  }
  /**
   *
   * @param explorer EnterpriseExplorer
   */
  public void doUpdate(EnterpriseExplorer explorer) {
    IWindow win = explorer.ContentView.getActiveWindow();boolean enable = false;
    if ( win instanceof MoveActionInterface ) {
      enable = ((MoveActionInterface)win).canMoveFirst();
    }
    setEnabled(enable);
  }

  public FirstExplorerAction(String s, char c, String s1, javax.swing.Icon icon)
  {
      super(s, c, s1, icon);
  }

}
