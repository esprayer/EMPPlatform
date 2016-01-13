package com.efounder.eai.ui.action.editaction;

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

public class CopyExplorerAction extends ExplorerAction {
static ResourceBundle res = ResourceBundle.getBundle("com.efounder.eai.ui.action.editaction.Res");
  public static CopyExplorerAction copyEditAction = new CopyExplorerAction(res.getString("_C_"),'C',res.getString("KEY4"),ExplorerIcons.getExplorerIcon("oicons/copy.png"));;
  public CopyExplorerAction(String s, char c, String s1, javax.swing.Icon icon)
  {
      super(s, c, s1, icon);
      KeyStroke ks = KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_C,java.awt.event.InputEvent.CTRL_MASK,false);
      this.putValue("Accelerator",ks);
  }

  /**
   *
   * @param explorer EnterpriseExplorer
   */
  public void actionPerformed(EnterpriseExplorer explorer) {
    IWindow win = explorer.ContentView.getActiveWindow();
    if ( win instanceof EditActionInterface ) {
      ((EditActionInterface)win).editCopy();
    }
  }
  /**
   *
   * @param explorer EnterpriseExplorer
   */
  public void doUpdate(EnterpriseExplorer explorer)
  {
    IWindow win = explorer.ContentView.getActiveWindow();boolean enable = false;
    if ( win instanceof EditActionInterface ) {
      enable = ((EditActionInterface)win).canCopy();
    }
    setEnabled(enable);
  }


}
