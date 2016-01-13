package com.efounder.eai.ui.action.fileaction;

import com.efounder.eai.ide.*;
import com.efounder.pfc.window.*;
import javax.swing.*;
import java.util.ResourceBundle;

/**
 * <p>Title: Enterprise Explorer</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: EFounder</p>
 * @author Skyline
 * @version 1.0
 */

public class ExportDataExplorerAction extends ExplorerAction {
static ResourceBundle res = ResourceBundle.getBundle("com.efounder.eai.ui.action.fileaction.Res");
  public static ExportDataExplorerAction exportDataAction = new ExportDataExplorerAction(res.getString("KEY16"),'O',res.getString("KEY17"),ExplorerIcons.getExplorerIcon("export16"));
  public ExportDataExplorerAction(String s, char c, String s1, Icon icon)
  {
      super(s, c, s1, icon);
  }
  public void actionPerformed(EnterpriseExplorer explorer) {
    IWindow win = explorer.ContentView.getActiveWindow();
    if ( win instanceof FileActionInterface ) {
      ((FileActionInterface)win).exportData();
    }
  }
  public void doUpdate(EnterpriseExplorer explorer)
  {
    IWindow win = explorer.ContentView.getActiveWindow();boolean enable = false;
    if ( win instanceof FileActionInterface ) {
      enable = ((FileActionInterface)win).canExport();
    }
    setEnabled(enable);
  }

}
