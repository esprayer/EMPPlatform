package com.efounder.eai.ui.action.toolsaction;

import com.efounder.eai.ide.*;
import javax.swing.*;
import com.efounder.eai.*;
import java.util.ResourceBundle;

/**
 * <p>Title: Enterprise Explorer</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: EFounder</p>
 * @author Skyline
 * @version 1.0
 */

public class HomeExplorerAction extends ExplorerAction {
static ResourceBundle res = ResourceBundle.getBundle("com.efounder.eai.ui.action.toolsaction.Res");
  public static HomeExplorerAction homeAction = new HomeExplorerAction(res.getString("KEY2"),'0',res.getString("KEY3"),ExplorerIcons.getExplorerIcon("beeload/home.gif"));
  public HomeExplorerAction(String s, char c, String s1, javax.swing.Icon icon)
  {
    super(s, c, s1, icon);
  }
  /**
   *
   * @param browser EnterpriseExplorer
   */
  public void actionPerformed(EnterpriseExplorer browser) {
    /**@todo Implement this com.efounder.eai.ide.ExplorerAction abstract method*/
    try {
      EAI.DOF.IOM(EAI.Product, "openHomeWindow", browser);
      EAI.DOF.IOM(EAI.Product, "openStartWindow", browser);
    } catch ( Exception e ) {
      e.printStackTrace();
    }
//    HtmlWindow hw = HtmlWindow.getHtmlWindow();
//    browser.ContentView.openChildWindow(hw,"系统主页","系统主页",(Icon)getValue("SmallIcon"));
  }

}
