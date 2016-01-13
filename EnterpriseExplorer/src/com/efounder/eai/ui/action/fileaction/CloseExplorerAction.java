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

public class CloseExplorerAction extends ExplorerAction {
static ResourceBundle res = ResourceBundle.getBundle("com.efounder.eai.ui.action.fileaction.Res");
  public static CloseExplorerAction closeFileAction = new CloseExplorerAction(res.getString("_C_"),res.getString("C").charAt(0),res.getString("KEY10"),ExplorerIcons.getExplorerIcon("fmisres/close16.gif"));
  public CloseExplorerAction(String s, char c, String s1, Icon icon)
  {
      super(s, c, s1, icon);
  }

  public void actionPerformed(EnterpriseExplorer browser) {
    /**@todo Implement this com.efounder.eai.ide.ExplorerAction abstract method*/
    browser.ContentView.closeChildWindow(browser.ContentView.getActiveWindow());
  }
  public void doUpdate(EnterpriseExplorer explorer)
  {
    boolean enable = false;
    enable = explorer.ContentView.getWindowCount()!=0;
    setEnabled(enable);
  }

}
