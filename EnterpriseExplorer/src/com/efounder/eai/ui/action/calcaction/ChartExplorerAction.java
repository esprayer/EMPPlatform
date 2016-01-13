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

public class ChartExplorerAction extends ExplorerAction {
static ResourceBundle res = ResourceBundle.getBundle("com.efounder.eai.ui.action.calcaction.Res");
  public static ChartExplorerAction chartAction = new ChartExplorerAction(res.getString("KEY4"),'O',res.getString("KEY5"),ExplorerIcons.getExplorerIcon("chart"));
  public ChartExplorerAction() {
  }
  /**
   *
   * @param explorer EnterpriseExplorer
   */
  public void actionPerformed(EnterpriseExplorer explorer) {
    IWindow win = explorer.ContentView.getActiveWindow();
    if ( win instanceof CalcActionInterface ) {
      ((CalcActionInterface)win).chartNode();
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
      enable = ((CalcActionInterface)win).canChart();
    }
    setEnabled(enable);
  }

  public ChartExplorerAction(String s, char c, String s1, javax.swing.Icon icon)
  {
      super(s, c, s1, icon);
  }

}
