package com.efounder.eai.ui;

import com.efounder.action.*;
import com.efounder.eai.ide.*;
import com.efounder.eai.ui.action.fileaction.AboutUsAction;
import com.efounder.eai.ui.action.fileaction.ChangepasswordAction;

import java.util.ResourceBundle;

/**
 * <p>Title: Enterprise Explorer</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: EFounder</p>
 * @author Skyline
 * @version 1.0
 */

public class HelpMenu {
	static ResourceBundle res = ResourceBundle.getBundle("com.efounder.eai.ui.Res");
	public static final ActionGroup GROUP_Help;
	public static final ActionGroup GROUP_Help6 = new ExplorerActionGroup();
	public static final ActionGroup GROUP_Help5 = new ExplorerActionGroup();
	public static final ActionGroup GROUP_Help4 = new ExplorerActionGroup();
	public static final ActionGroup GROUP_Help3 = new ExplorerActionGroup();
	public static final ActionGroup GROUP_Help2 = new ExplorerActionGroup();
	public static final ActionGroup GROUP_Help1 = new ExplorerActionGroup();
	public static final ActionGroup GROUP_Tools1 = new ExplorerActionGroup();
  
	static {
		GROUP_Help    = new ExplorerActionGroup(res.getString("KEY48")+"[H]", 'H', true);
		GROUP_Help.add(AboutUsAction.aboutAction);
	}
	
	public HelpMenu() {
	}

}
