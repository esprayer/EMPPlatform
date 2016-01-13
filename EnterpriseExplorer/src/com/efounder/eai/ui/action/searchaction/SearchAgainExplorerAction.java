package com.efounder.eai.ui.action.searchaction;

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

public class SearchAgainExplorerAction extends ExplorerAction {
static ResourceBundle res = ResourceBundle.getBundle("com.efounder.eai.ui.action.searchaction.Res");
  public static SearchAgainExplorerAction findAgainSearchAction = new SearchAgainExplorerAction(res.getString("KEY4"),'0',res.getString("KEY5"),ExplorerIcons.ICON_FINDAGAIN);;
  public SearchAgainExplorerAction(String s, char c, String s1, javax.swing.Icon icon)
  {
      super(s, c, s1, icon);
  }
  /**
   *
   * @param explorer EnterpriseExplorer
   */
  public void actionPerformed(EnterpriseExplorer explorer) {
    IWindow win = explorer.ContentView.getActiveWindow();
    if ( win instanceof SearchActionInterface ) {
      ((SearchActionInterface)win).searchAgainNode();
    }
  }
  /**
   *
   * @param explorer EnterpriseExplorer
   */
  public void doUpdate(EnterpriseExplorer explorer) {
    IWindow win = explorer.ContentView.getActiveWindow();boolean enable = false;
    if ( win instanceof SearchActionInterface ) {
      enable = ((SearchActionInterface)win).canSearch();
    }
    setEnabled(enable);
  }


}
