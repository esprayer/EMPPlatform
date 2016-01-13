package com.efounder.eai.ui;

import com.efounder.eai.ide.*;
import com.efounder.action.*;
import com.efounder.eai.*;
import com.efounder.eai.ui.action.*;


/**
 * <p>Title: Enterprise Explorer</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: EFounder</p>
 * @author Skyline
 * @version 1.0
 */

public class ExplorerToolBar {
//  public static final ActionGroup GROUP_FileBar;
//  public static final ActionGroup GROUP_FileNewBar = new ExplorerActionGroup("新建",'0',"新建",ExplorerIcons.ICON_GENERIC_NEW,true);
//  public static final ActionGroup GROUP_FileOpenBar = new ExplorerActionGroup();

//  public static final ActionGroup GROUP_EditBar;
//  public static final ActionGroup GROUP_SearchBar;
  public static final ActionGroup GROUP_ViewBar;
  public static final ActionGroup Group_DataBar;
  public static final ActionGroup Group_CalcBar;
  public static final ActionGroup Group_WizardBar;
  public static final ActionGroup Group_ToolsBar;
  public static final ActionGroup Group_WindowBar;
  public static final ActionGroup Group_HelpBar;
  public ExplorerToolBar() {
  }
  static
  {
//    GROUP_FileBar = new ExplorerActionGroup(Res.jf, Res.hf.charAt(0), Res._fldif);
//      GROUP_FileBar = new ExplorerActionGroup("FileC",'C',"File toolbar");
//      GROUP_EditBar = new ExplorerActionGroup();
//      GROUP_SearchBar = new ExplorerActionGroup();
      GROUP_ViewBar = new ExplorerActionGroup();
      Group_DataBar = new ExplorerActionGroup();
      Group_CalcBar = new ExplorerActionGroup();
      Group_WizardBar = new ExplorerActionGroup();
      Group_ToolsBar = new ExplorerActionGroup();
      Group_WindowBar = new ExplorerActionGroup();
      Group_HelpBar = new ExplorerActionGroup();
  }
  public static void InitOpenEnterpriseExplorer(EnterpriseFounder enterprise,Object args,Object o3,Object o4) {
    // 将新建菜单增加到新建Bar
//    GROUP_FileNewBar.add(ExplorerMenu.GROUP_FileNew);
//    GROUP_FileBar.add(GROUP_FileNewBar);
//    // 打开文件放到Bar中
//    GROUP_FileBar.add(GROUP_FileOpenBar);
//    // 将重新打开菜单增加到重打Bar
//    GROUP_FileBar.add(ExplorerMenu.GROUP_FileReOpen);
  }
}
