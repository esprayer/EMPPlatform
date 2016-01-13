package com.efounder.view.actions;

import com.efounder.action.UpdateAction;
import java.awt.event.*;
import com.efounder.view.*;
import com.efounder.node.*;
import javax.swing.*;
import com.efounder.eai.ide.*;
import com.efounder.pfc.window.*;


/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class CloseNodeAction extends UpdateAction {
  protected JExplorerView ExplorerView = null;
  protected Object[]   Nodes         = null;
  protected String Key = null;

  public void actionPerformed(JExplorerView ExplorerView,Object[] nodes) {
    if ( ExplorerView != null && nodes != null ) {
      for(int i=0;i<nodes.length;i++) {
        Context context = new Context(ExplorerView,(JNodeStub)nodes[i]);
        IWindow nodeWindow = context.getNode().getNodeWindow();
        if ( nodeWindow != null )
          EnterpriseExplorer.ContentView.closeChildWindow(nodeWindow);
      }
    }
  }

  public final void doPerformed(ActionEvent e) {
      if(ExplorerView != null && Nodes != null )
        actionPerformed(ExplorerView,Nodes);
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
  public CloseNodeAction(JExplorerView ExplorerView,Object[] nss,String key,
                          String s, char c, String s1, Icon icon)
  {
    super(s, c, s1, icon);
    this.ExplorerView = ExplorerView;
    this.Key = key;Nodes = nss;
  }

}
