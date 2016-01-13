package com.efounder.eai.ui;

import com.efounder.action.ActionGroup;
import com.efounder.eai.ide.ExplorerAction;
import com.efounder.eai.ide.ExplorerActionGroup;
import com.efounder.eai.ide.ExplorerIcons;
import com.efounder.eai.ui.action.BufferManagerAction;
import com.efounder.eai.ui.action.ServiceManagerAction;
import com.efounder.eai.ui.action.SystemViewAction;
import com.efounder.eai.ui.action.UpdateCenterAction;
import com.efounder.eai.ui.action.calcaction.SysCalcExplorerAction;
import com.efounder.eai.ui.action.toolsaction.HomeExplorerAction;
import com.efounder.eai.ui.action.toolsaction.WizardExplorerAction;

public class ToolsMenu
{
  public static final ActionGroup GROUP_Tools;
  public static final ActionGroup GROUP_Tools1 = new ExplorerActionGroup();
  public static final ActionGroup GROUP_ToolsBar = new ExplorerActionGroup();
  public static ExplorerAction updateCenterAction = new UpdateCenterAction("下载升级", '@', "软件下载升级中心", ExplorerIcons.getExplorerIcon("oicons/update2.png"));
  public static ExplorerAction bufferManagerAction = new BufferManagerAction("缓存管理", '@', "缓存服务管理中心", ExplorerIcons.getExplorerIcon("oicons/tablebinding.png"));
  public static ExplorerAction serviceManagerAction = new ServiceManagerAction("服务管理", '@', "服务管理中心", ExplorerIcons.getExplorerIcon("oicons/actionbinding.png"));
  public static ExplorerAction sysViewAction = new SystemViewAction("系统视图工具", '@', "系统视图工具", ExplorerIcons.ICON_WATCH);

  static { GROUP_Tools = new ExplorerActionGroup("工具[T]", 'T', true);
    GROUP_Tools1.add(updateCenterAction);

    ActionGroup ag = new ActionGroup();
    ag.add(bufferManagerAction);
    ag.add(serviceManagerAction);
    GROUP_Tools1.add(ag);

    GROUP_Tools1.add(sysViewAction);

    GROUP_Tools.add(GROUP_Tools1);
    GROUP_ToolsBar.add(WizardExplorerAction.wizardAction);
    GROUP_ToolsBar.add(SysCalcExplorerAction.calcAction);
    GROUP_ToolsBar.add(HomeExplorerAction.homeAction);
  }
}