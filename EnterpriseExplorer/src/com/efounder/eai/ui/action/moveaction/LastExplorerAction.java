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

public class LastExplorerAction extends ExplorerAction {
static ResourceBundle res = ResourceBundle.getBundle("com.efounder.eai.ui.action.moveaction.Res");
  public static LastExplorerAction lastAction = new LastExplorerAction(res.getString("KEY2"),'O',res.getString("KEY3"),ExplorerIcons.getExplorerIcon("last"));
  public LastExplorerAction() {
  }
  /**
   *
   * @param explorer EnterpriseExplorer
   */
  public void actionPerformed(EnterpriseExplorer explorer) {
    IWindow win = explorer.ContentView.getActiveWindow();
    if ( win instanceof MoveActionInterface ) {
      ((MoveActionInterface)win).moveLast();
    }
  }
  /**
   *
   * @param explorer EnterpriseExplorer
   */
  public void doUpdate(EnterpriseExplorer explorer) {
    IWindow win = explorer.ContentView.getActiveWindow();boolean enable = false;
    if ( win instanceof MoveActionInterface ) {
      enable = ((MoveActionInterface)win).canMoveLast();
    }
    setEnabled(enable);
  }

  public LastExplorerAction(String s, char c, String s1, javax.swing.Icon icon)
  {
      super(s, c, s1, icon);
  }

}
