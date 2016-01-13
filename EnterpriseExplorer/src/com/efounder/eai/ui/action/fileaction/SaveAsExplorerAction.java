package com.efounder.eai.ui.action.fileaction;

import com.efounder.eai.ide.*;
import javax.swing.*;
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

public class SaveAsExplorerAction extends ExplorerAction {
static ResourceBundle res = ResourceBundle.getBundle("com.efounder.eai.ui.action.fileaction.Res");
  /**
   *
   */
  public static SaveAsExplorerAction saveAsFileAction = new SaveAsExplorerAction(res.getString("KEY23"),'O',res.getString("KEY24"),null);
  /**
   *
   * @param s String
   * @param c char
   * @param s1 String
   * @param icon Icon
   */
  public SaveAsExplorerAction(String s, char c, String s1, Icon icon) {
      super(s, c, s1, icon);
      setWaitInvoke(false);
  }
  /**
   *
   * @param explorer EnterpriseExplorer
   */
  public void actionPerformed(EnterpriseExplorer explorer) {
    IWindow win = explorer.ContentView.getActiveWindow();
    if ( win instanceof FileActionInterface ) {
      ((FileActionInterface)win).saveAsNode();
    }
  }
  /**
   *
   * @param explorer EnterpriseExplorer
   */
  public void doUpdate(EnterpriseExplorer explorer) {
    IWindow win = explorer.ContentView.getActiveWindow();boolean enable = false;
    if ( win instanceof FileActionInterface ) {
      enable = ((FileActionInterface)win).canSaveAs();
    }
    setEnabled(enable);
  }
}
