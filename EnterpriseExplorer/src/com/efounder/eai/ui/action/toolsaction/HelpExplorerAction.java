package com.efounder.eai.ui.action.toolsaction;

import com.efounder.eai.ide.*;
import java.util.ResourceBundle;

/**
 * <p>Title: Enterprise Explorer</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: EFounder</p>
 * @author Skyline
 * @version 1.0
 */

public class HelpExplorerAction extends ExplorerAction {
static ResourceBundle res = ResourceBundle.getBundle("com.efounder.eai.ui.action.toolsaction.Res");
  public static HelpExplorerAction helpAction = new HelpExplorerAction(res.getString("KEY5"),'0',res.getString("KEY6"),ExplorerIcons.getExplorerIcon("oicons/help.png"));
  public HelpExplorerAction(String s, char c, String s1, javax.swing.Icon icon)
  {
    super(s, c, s1, icon);
  }

  /**
   *
   * @param browser EnterpriseExplorer
   */
  public void actionPerformed(EnterpriseExplorer browser) {
    /**@todo Implement this com.efounder.eai.ide.ExplorerAction abstract method*/
  }

}
