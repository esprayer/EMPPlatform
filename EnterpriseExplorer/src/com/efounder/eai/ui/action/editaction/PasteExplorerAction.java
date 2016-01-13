package com.efounder.eai.ui.action.editaction;

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

public class PasteExplorerAction extends ExplorerAction {
static ResourceBundle res = ResourceBundle.getBundle("com.efounder.eai.ui.action.editaction.Res");
  public static PasteExplorerAction pasteEditAction = new PasteExplorerAction(res.getString("_P_"),'P',res.getString("KEY2"),ExplorerIcons.getExplorerIcon(res.getString("oicons_paste_png")));;
  public PasteExplorerAction(String s, char c, String s1, javax.swing.Icon icon)
  {
      super(s, c, s1, icon);
  }
  /**
   *
   * @param explorer EnterpriseExplorer
   */
  public void actionPerformed(EnterpriseExplorer explorer) {
    IWindow win = explorer.ContentView.getActiveWindow();
    if ( win instanceof EditActionInterface ) {
      ((EditActionInterface)win).editPaste();
    }
  }
  /**
   *
   * @param explorer EnterpriseExplorer
   */
  public void doUpdate(EnterpriseExplorer explorer) {
    IWindow win = explorer.ContentView.getActiveWindow();boolean enable = false;
    if ( win instanceof EditActionInterface ) {
      enable = ((EditActionInterface)win).canPaste();
    }
    setEnabled(enable);
  }


}
