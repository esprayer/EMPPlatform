package com.efounder.eai.ui;

import com.efounder.action.*;
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

public class WindowMenu {
static ResourceBundle res = ResourceBundle.getBundle("com.efounder.eai.ui.Res");
  public static final ActionGroup GROUP_Window;
  public static final ActionGroup GROUP_WindowList;
  public static final ActionGroup GROUP_WindowOpenProjects = new ExplorerActionGroup();
  public static final ActionGroup GROUP_WindowSearchHistory = new ExplorerActionGroup();
  public static final ActionGroup GROUP_WindowBar = new ExplorerActionGroup(res.getString("KEY6"),'0',res.getString("KEY7"),ExplorerIcons.getExplorerIcon("oicons/applications.png"),true);
  static {
    GROUP_Window  = new ExplorerActionGroup(res.getString("KEY44")+"[W]", 'W', true);
    GROUP_WindowList = new ExplorerActionGroup();
    GROUP_Window.add(GROUP_WindowList);
    GROUP_WindowBar.add(GROUP_WindowList);
    ExplorerToolBar.Group_WindowBar.add(GROUP_WindowBar);
  }
  public WindowMenu() {
  }

}
