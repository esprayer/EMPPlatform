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

public class PreviewExplorerAction extends ExplorerAction {
static ResourceBundle res = ResourceBundle.getBundle("com.efounder.eai.ui.action.fileaction.Res");
  /**
   *
   */
  public static PreviewExplorerAction previewFileAction = new PreviewExplorerAction(res.getString("KEY2"),'O',res.getString("KEY3"),ExplorerIcons.getExplorerIcon("office/(24,35).gif"));
  /**
   *
   * @param s String
   * @param c char
   * @param s1 String
   * @param icon Icon
   */
  public PreviewExplorerAction(String s, char c, String s1, Icon icon)
  {
      super(s, c, s1, icon);
  }
  /**
   *
   * @param explorer EnterpriseExplorer
   */
  public void actionPerformed(EnterpriseExplorer explorer) {
    IWindow win = explorer.ContentView.getActiveWindow();
    if ( win instanceof FileActionInterface ) {
      ((FileActionInterface)win).previewNode();
    }
  }
  /**
   *
   * @param explorer EnterpriseExplorer
   */
  public void doUpdate(EnterpriseExplorer explorer)
  {
    IWindow win = explorer.ContentView.getActiveWindow();boolean enable = false;
    if ( win instanceof FileActionInterface ) {
      enable = ((FileActionInterface)win).canPreview();
    }
    setEnabled(enable);
  }

}
