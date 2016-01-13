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

public class ViewMenu {
  static ResourceBundle res = ResourceBundle.getBundle("com.efounder.eai.ui.Res");
  public static final ActionGroup GROUP_View;
  public static final ActionGroup GROUP_ViewContext = new ExplorerActionGroup();
  public static final ActionGroup GROUP_ViewComponents = new ExplorerActionGroup();
  public static final ActionGroup GROUP_ViewIde = new ExplorerActionGroup();
  static{
    GROUP_View    = new ExplorerActionGroup(res.getString("KEY47")+"[V]", 'V', true);
//    GROUP_View.add(GROUP_ViewContext);
//    GROUP_View.add(GROUP_ViewComponents);
//    GROUP_View.add(GROUP_ViewIde);
  }
  public ViewMenu() {
  }

}
