package com.efounder.view.actions;

import com.efounder.view.*;
import com.efounder.node.*;
import javax.swing.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class ExplorerViewCommandAction extends ExplorerViewAction {
  protected String  Command = null;
  public ExplorerViewCommandAction(JExplorerView ExplorerView,String command,String s, char c, String s1, Icon icon)
  {
    super(ExplorerView,s, c, s1, icon);
    Command = command;
  }

  public void actionPerformed(JExplorerView parm1, JNodeStub[] parm2) {
    /**@todo Implement this com.efounder.view.actions.ExplorerViewAction abstract method*/
  }

}
