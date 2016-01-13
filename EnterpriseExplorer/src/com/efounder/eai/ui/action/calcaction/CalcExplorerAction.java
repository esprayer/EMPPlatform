package com.efounder.eai.ui.action.calcaction;

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

public class CalcExplorerAction extends ExplorerAction {
static ResourceBundle res = ResourceBundle.getBundle("com.efounder.eai.ui.action.calcaction.Res");
  public static CalcExplorerAction calcAction = new CalcExplorerAction(res.getString("KEY6"),'O',res.getString("KEY7"),ExplorerIcons.getExplorerIcon("calc"));
  public CalcExplorerAction() {
  }
  /**
   *
   * @param explorer EnterpriseExplorer
   */
  public void actionPerformed(EnterpriseExplorer explorer) {
    IWindow win = explorer.ContentView.getActiveWindow();
    if ( win instanceof CalcActionInterface ) {
      ((CalcActionInterface)win).calcNode();
    }
  }
  /**
   *
   * @param explorer EnterpriseExplorer
   */
  public void doUpdate(EnterpriseExplorer explorer)
  {
    IWindow win = explorer.ContentView.getActiveWindow();boolean enable = false;
    if ( win instanceof CalcActionInterface ) {
      enable = ((CalcActionInterface)win).canCalc();
    }
    setEnabled(enable);
  }

  public CalcExplorerAction(String s, char c, String s1, javax.swing.Icon icon)
  {
      super(s, c, s1, icon);
      setWaitInvoke(false);
  }

}
