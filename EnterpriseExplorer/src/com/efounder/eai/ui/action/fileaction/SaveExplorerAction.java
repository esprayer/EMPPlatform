package com.efounder.eai.ui.action.fileaction;

import com.efounder.eai.ide.*;
import javax.swing.*;
import com.efounder.node.*;
import com.efounder.pfc.window.*;
import org.openide.ErrorManager;
import java.util.ResourceBundle;

/**
 * <p>Title: Enterprise Explorer</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: EFounder</p>
 * @author Skyline
 * @version 1.0
 */

public class SaveExplorerAction extends ExplorerAction {
static ResourceBundle res = ResourceBundle.getBundle("com.efounder.eai.ui.action.fileaction.Res");
  /**
   *
   */
  public static SaveExplorerAction saveFileAction = new SaveExplorerAction(res.getString("KEY"),'O',res.getString("KEY1"),ExplorerIcons.getExplorerIcon("oicons/filesave.png"));
  /**
   *
   * @param s String
   * @param c char
   * @param s1 String
   * @param icon Icon
   */
  public SaveExplorerAction(String s, char c, String s1, Icon icon) {
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
      try {((FileActionInterface)win).saveNode();} catch ( Exception e ) {
        ErrorManager.getDefault().notify(e);
      }
//      ((FileActionInterface)win).saveNode();

    }
  }
  /**
   *
   * @param explorer EnterpriseExplorer
   */
  public void doUpdate(EnterpriseExplorer explorer) {
    IWindow win = explorer.ContentView.getActiveWindow();boolean enable = false;
    if ( win instanceof FileActionInterface ) {
      enable = ((FileActionInterface)win).canSave() && ((FileActionInterface)win).isModified();
    }
    setEnabled(enable);
  }
}
