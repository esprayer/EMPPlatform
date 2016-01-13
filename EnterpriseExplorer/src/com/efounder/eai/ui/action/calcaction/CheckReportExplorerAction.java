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

public class CheckReportExplorerAction extends ExplorerAction {
static ResourceBundle res = ResourceBundle.getBundle("com.efounder.eai.ui.action.calcaction.Res");
  public static CheckReportExplorerAction checkReportAction = new CheckReportExplorerAction(res.getString("KEY2"),'O',res.getString("KEY3"),ExplorerIcons.getExplorerIcon("checkset"));
  public CheckReportExplorerAction() {
  }
  /**
   *
   * @param explorer EnterpriseExplorer
   */
  public void actionPerformed(EnterpriseExplorer explorer) {
    IWindow win = explorer.ContentView.getActiveWindow();
    if ( win instanceof CalcActionInterface ) {
      ((CalcActionInterface)win).checkReportNode();
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
      enable = ((CalcActionInterface)win).canCheckReport();
    }
    setEnabled(enable);
  }

  public CheckReportExplorerAction(String s, char c, String s1, javax.swing.Icon icon)
  {
      super(s, c, s1, icon);
      setWaitInvoke(false);
  }

}
