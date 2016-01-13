package com.efounder.view.actions;

import com.efounder.action.UpdateAction;
import java.awt.event.*;
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

public abstract class ExplorerViewAction extends UpdateAction {
  JExplorerView ExplorerView = null;

  public abstract void actionPerformed(JExplorerView ExplorerView,JNodeStub[] NodeArray);

  public final void doPerformed(ActionEvent actionevent)
  {
    JNodeStub[] NodeArray = null;
    NodeArray = ExplorerView.getSelectNodes();
    if(ExplorerView != null && NodeArray != null )
      actionPerformed(ExplorerView,NodeArray);
  }

  public void doUpdate(JExplorerView ExplorerView)
  {
      super.doUpdate(ExplorerView);
  }

  public final void doUpdate(Object obj)
  {
    if(ExplorerView != null)
      doUpdate(ExplorerView);
  }

  public ExplorerViewAction(JExplorerView ExplorerView,String s, char c, String s1, Icon icon, Icon icon1)
  {
      super(s, c, s1, icon, icon1);
      this.ExplorerView = ExplorerView;
  }

  public ExplorerViewAction(JExplorerView ExplorerView,String s, char c, String s1, Icon icon, String s2)
  {
    super(s, c, s1, icon, s2);
      this.ExplorerView = ExplorerView;
  }

  public ExplorerViewAction(JExplorerView ExplorerView,String s, char c, String s1, Icon icon)
  {
    super(s, c, s1, icon);
      this.ExplorerView = ExplorerView;
  }

  public ExplorerViewAction(JExplorerView ExplorerView,String s, char c, String s1)
  {
    super(s, c, s1);
      this.ExplorerView = ExplorerView;
  }

  public ExplorerViewAction(JExplorerView ExplorerView,String s, char c)
  {
    super(s, c);
      this.ExplorerView = ExplorerView;
  }

  public ExplorerViewAction(JExplorerView ExplorerView,String s)
  {
    super(s);
      this.ExplorerView = ExplorerView;
  }

  public ExplorerViewAction(JExplorerView ExplorerView)
  {
      this.ExplorerView = ExplorerView;
  }

}
