package com.efounder.eai.ui;

import com.efounder.action.*;
import com.efounder.eai.ide.*;
import com.efounder.eai.ui.action.searchaction.*;

/**
 * <p>Title: Enterprise Explorer</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: EFounder</p>
 * @author Skyline
 * @version 1.0
 */

public class SearchMenu {
//  public static final ActionGroup GROUP_Search;
  public static final ActionGroup GROUP_SearchBar;
  public static final ActionGroup GROUP_SearchFind     = new ExplorerActionGroup();
  public static final ActionGroup GROUP_SearchGoto     = new ExplorerActionGroup();
  public static final ActionGroup GROUP_SearchBookmark = new ExplorerActionGroup();
  static {
    GROUP_SearchBar = new ExplorerActionGroup();
    GROUP_SearchBar.add(SearchExplorerAction.findSearchAction);
//    GROUP_SearchBar.add(SearchAgainExplorerAction.findAgainSearchAction);
//    GROUP_SearchBar.add(ReplaceExplorerAction.replaceSearchAction);
  }
  static {
//    GROUP_Search  = new ExplorerActionGroup("查找[S]", 'S', true);

    GROUP_SearchFind.add(SearchExplorerAction.findSearchAction);
    GROUP_SearchFind.add(SearchAgainExplorerAction.findAgainSearchAction);
    GROUP_SearchFind.add(ReplaceExplorerAction.replaceSearchAction);

//    GROUP_Search.add(GROUP_SearchFind);
//    GROUP_Search.add(GROUP_SearchGoto);
//    GROUP_Search.add(GROUP_SearchBookmark);
  }

  public SearchMenu() {
  }

}
