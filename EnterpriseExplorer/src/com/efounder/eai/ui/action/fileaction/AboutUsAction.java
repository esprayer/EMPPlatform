package com.efounder.eai.ui.action.fileaction;

import javax.swing.*;

import com.efounder.eai.ide.*;
import com.efounder.eai.ui.*;
import com.efounder.eai.EAI;
import com.efounder.eai.data.*;
import java.util.ResourceBundle;

/**
 * <p>Title: Enterprise Explorer</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: EFounder</p>
 * @author Skyline
 * @version 1.0
 */
public class AboutUsAction extends ExplorerAction {
	static ResourceBundle res = ResourceBundle.getBundle("com.efounder.eai.ui.action.fileaction.Res");
	public static AboutUsAction aboutAction = new AboutUsAction(res.getString("KEY26"),'0',res.getString("KEY25"),null);
	/**
	 *
	 */
	public AboutUsAction(String s, char c, String s1, Icon icon) {
		super(s, c, s1, icon);
		this.waitInvoke = false;
	}
	
	/**
	 * actionPerformed
	 *
	 * @param browser EnterpriseExplorer
	 * @todo Implement this com.efounder.eai.ide.ExplorerAction method
	 */
	public void actionPerformed(EnterpriseExplorer browser) {
		try {
			EAI.DOF.IOM("SecurityObject", "aboutus");
		} catch ( Exception e ) {
			e.printStackTrace();
		}
	}
}
