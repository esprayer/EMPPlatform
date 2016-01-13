package com.efounder.eai.ui;

import com.efounder.eai.*;
import com.efounder.eai.ide.*;
import com.efounder.action.*;

/**
 * <p>Title: Enterprise Explorer</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: EFounder</p>
 * @author Skyline
 * @version 1.0
 */

public class ExplorerMenu {
	public static void InitOpenEnterpriseExplorer(EnterpriseFounder enterprise,Object args,Object o3,Object o4) {
		initMenubar();
   	 	initToolbar();
	}
	private static void initMenubar() {
		EnterpriseExplorer.addMenuGroup(FileMenu.GROUP_File);// 系统
//		EnterpriseExplorer.addMenuGroup(EditMenu.GROUP_Edit);// 编缉
//		EnterpriseExplorer.addMenuGroup(ViewMenu.GROUP_View);// 视图
//		EnterpriseExplorer.addMenuGroup(ToolsMenu.GROUP_Tools);// 工具
//		EnterpriseExplorer.addMenuGroup(WindowMenu.GROUP_Window);// 窗口
		EnterpriseExplorer.addMenuGroup(HelpMenu.GROUP_Help);// 帮助
	}
	private static void initToolbar() {
		EnterpriseExplorer.addToolBarGroup(FileMenu.GROUP_FileBar,0);
	    EnterpriseExplorer.addToolBarGroup(EditMenu.GROUP_EditBar,0);
	    EnterpriseExplorer.addToolBarGroup(ToolsMenu.GROUP_ToolsBar,0);
	    EnterpriseExplorer.addToolBarGroup(ExplorerToolBar.Group_WindowBar,0);
	}

	protected ExplorerMenu() {

	}
}
