package com.efounder.eai.ui.action.fileaction;

import javax.swing.*;

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

public class OpenExplorerActioin extends ExplorerAction {
static ResourceBundle res = ResourceBundle.getBundle("com.efounder.eai.ui.action.fileaction.Res");
  public static OpenExplorerActioin openFileAction = new OpenExplorerActioin(res.getString("_O_"),'O',res.getString("KEY18"),ExplorerIcons.ICON_OPEN_FILE);
  public OpenExplorerActioin(String s, char c, String s1, Icon icon)
  {
      super(s, c, s1, icon);
  }
  public void actionPerformed(EnterpriseExplorer browser) {
    /**@todo Implement this com.efounder.eai.ide.ExplorerAction abstract method*/
  }
}
