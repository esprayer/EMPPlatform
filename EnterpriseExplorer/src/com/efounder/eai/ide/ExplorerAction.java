package com.efounder.eai.ide;

import java.awt.event.*;
import javax.swing.Icon;
import com.efounder.action.*;
import org.openide.*;

/**
 * <p>Title: Enterprise Explorer</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: EFounder</p>
 * @author Skyline
 * @version 1.0
 */

public abstract class ExplorerAction extends UpdateAction {

	public abstract void actionPerformed(EnterpriseExplorer browser);

	public final void doPerformed(ActionEvent actionevent) {
		EnterpriseExplorer explorer = EnterpriseExplorer.findExplorer(actionevent.getSource());
//    try {
//      WaitingManager.getDefault().beginWait(explorer);
		if (explorer != null)
			actionPerformed(explorer);
//    } catch ( Exception e ) {
//      e.printStackTrace();
//    } finally {
//      WaitingManager.getDefault().endWait(explorer);
//    }
	}

	public void doUpdate(EnterpriseExplorer explorer) {
		super.doUpdate(explorer);
	}

	public final void doUpdate(Object obj) {
		EnterpriseExplorer explorer = EnterpriseExplorer.findExplorer(obj);
		if(explorer != null)
			doUpdate(explorer);
	}

	public ExplorerAction(String s, char c, String s1, Icon icon, Icon icon1) {
		super(s, c, s1, icon, icon1);
	}

	public ExplorerAction(String s, char c, String s1, Icon icon, String s2) {
		super(s, c, s1, icon, s2);
	}

	public ExplorerAction(String s, char c, String s1, Icon icon) {
		super(s, c, s1, icon);
	}

	public ExplorerAction(String s, char c, String s1) {
		super(s, c, s1);
	}

	public ExplorerAction(String s, char c) {
		super(s, c);
	}

	public ExplorerAction(String s) {
		super(s);
	}

	public ExplorerAction() {
	}
}
