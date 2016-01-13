package com.efounder.eai.ui.action.calcaction;

import com.efounder.eai.ide.*;
import com.efounder.pfc.window.*;

import java.io.IOException;
import java.util.ResourceBundle;

/**
 * <p>Title: Enterprise Explorer</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: EFounder</p>
 * @author Skyline
 * @version 1.0
 */

public class SysCalcExplorerAction extends ExplorerAction {
  public static SysCalcExplorerAction calcAction = new SysCalcExplorerAction("计算器",'O',"计算器",ExplorerIcons.getExplorerIcon("calc"));
  public SysCalcExplorerAction() {
  }
  /**
   *
   * @param explorer EnterpriseExplorer
   */
  public void actionPerformed(EnterpriseExplorer explorer) {
	  try {
		Runtime.getRuntime().exec("calc");
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
  }
  /**
   *
   * @param explorer EnterpriseExplorer
   */
  public void doUpdate(EnterpriseExplorer explorer)
  {
    setEnabled(true);
  }

  public SysCalcExplorerAction(String s, char c, String s1, javax.swing.Icon icon)
  {
      super(s, c, s1, icon);
      setWaitInvoke(false);
  }

}
