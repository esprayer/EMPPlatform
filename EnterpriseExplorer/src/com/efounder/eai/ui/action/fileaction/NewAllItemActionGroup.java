package com.efounder.eai.ui.action.fileaction;

import java.util.ResourceBundle;

import javax.swing.Icon;

import com.efounder.eai.ide.EnterpriseExplorer;
import com.efounder.eai.ide.ExplorerAction;
import com.efounder.eai.ide.ExplorerIcons;
import com.efounder.pfc.window.IWindow;

public class NewAllItemActionGroup extends ExplorerAction {
	static ResourceBundle res = ResourceBundle.getBundle("com.efounder.eai.ui.Res");
	public static NewAllItemActionGroup newAllItemAction = new NewAllItemActionGroup(res.getString("KEY"),'0',res.getString("KEY1"),ExplorerIcons.getExplorerIcon("oicons/new.png"));
	/**
	 *
	 */
	public NewAllItemActionGroup() {

	}
	/**
	 *
	 * @param explorer EnterpriseExplorer
	 */
	public void actionPerformed(EnterpriseExplorer explorer) {
//		int count = explorer.ContentView.getWindowCount();
		IWindow win = null;
//		for(int i=0;i<count;i++) {
			win = explorer.ContentView.getActiveWindow();//getWindow(i);
			if ( win != null && win instanceof FileActionInterface ) {
					try {((FileActionInterface)win).newNode();} catch ( Exception e ) {}
	
			}
//		}
	}
	/**
	 *
	 * @param s String
	 * @param c char
	 * @param s1 String
	 * @param icon Icon
	 */
	public NewAllItemActionGroup(String s, char c, String s1, Icon icon) {
		super(s, c, s1, icon);
		setWaitInvoke(false);
	}

}
