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

public class NextExplorerAction extends ExplorerAction {
static ResourceBundle res = ResourceBundle.getBundle("com.efounder.eai.ui.action.moveaction.Res");
  public static NextExplorerAction nextAction = new NextExplorerAction(res.getString("KEY"),'O',res.getString("KEY1"),ExplorerIcons.getExplorerIcon("next"));
  public NextExplorerAction() {
  }
  /**
   *
   * @param explorer EnterpriseExplorer
   */
  public void actionPerformed(EnterpriseExplorer explorer) {
    IWindow win = explorer.ContentView.getActiveWindow();
    if ( win instanceof MoveActionInterface ) {
      ((MoveActionInterface)win).moveNext();
    }
  }
  /**
   *
   * @param explorer EnterpriseExplorer
   */
  public void doUpdate(EnterpriseExplorer explorer) {
    IWindow win = explorer.ContentView.getActiveWindow();boolean enable = false;
    if ( win instanceof MoveActionInterface ) {
      enable = ((MoveActionInterface)win).canMoveNext();
    }
    setEnabled(enable);
  }

  public NextExplorerAction(String s, char c, String s1, javax.swing.Icon icon)
  {
      super(s, c, s1, icon);
  }

}
