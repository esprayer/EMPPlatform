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

public class GraphExplorerAction extends ExplorerAction {
static ResourceBundle res = ResourceBundle.getBundle("com.efounder.eai.ui.action.toolsaction.Res");
  public static GraphExplorerAction graphAction = new GraphExplorerAction(res.getString("KEY"),'0',res.getString("KEY1"),ExplorerIcons.ICON_HIDE_MESSAGE);
  public GraphExplorerAction(String s, char c, String s1, javax.swing.Icon icon)
  {
    super(s, c, s1, icon);
  }

  public GraphExplorerAction() {
  }
  public void actionPerformed(EnterpriseExplorer browser) {
    /**@todo Implement this com.efounder.eai.ide.ExplorerAction abstract method*/
  }

}
