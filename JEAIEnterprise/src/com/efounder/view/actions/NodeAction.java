package com.efounder.view.actions;

import com.efounder.action.UpdateAction;
import java.awt.event.*;
import com.efounder.view.*;
import com.efounder.node.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class NodeAction extends UpdateAction {
  JExplorerView ExplorerView = null;
  JNodeStub     Node         = null;
  public void actionPerformed(JExplorerView ExplorerView,JNodeStub node) {
    Context context = new Context(ExplorerView,node);
    Node.openObject(null,context);
  }

  public final void doPerformed(ActionEvent actionevent)
  {
    if(ExplorerView != null && Node != null )
      actionPerformed(ExplorerView,Node);
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

  public NodeAction(JExplorerView ExplorerView,JNodeStub node)
  {
//    super(s, c, s1, icon, icon1);
      super(node.toString(), 'N', node.toString(), node.getNodeIcon(), node.getNodeIcon());
      this.ExplorerView = ExplorerView;
      this.Node         = node;
  }



}
