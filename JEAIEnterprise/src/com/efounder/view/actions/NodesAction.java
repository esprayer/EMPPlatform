package com.efounder.view.actions;

import com.efounder.action.UpdateAction;
import java.awt.event.*;
import com.efounder.view.*;
import com.efounder.node.*;
import javax.swing.*;
import com.core.xml.*;


/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class NodesAction extends UpdateAction {
  protected JExplorerView ExplorerView = null;
  protected Object[]     Nodes         = null;
  protected String Command = null;
  public void actionPerformed(JExplorerView ExplorerView,Object[] nodes) {
    JNodeStub ns = null;Object[] OArray = {ExplorerView,nodes};
    if ( ExplorerView != null && nodes != null && Command != null ) {
      for(int i=0;i<nodes.length;i++) {
        ns = (JNodeStub)nodes[i];
        JBOFClass.CallObjectMethod(ns,Command,OArray);
      }
    }
  }
  public void setNodes(Object[] nodes) {
    Nodes = nodes;
  }
  public Object[] getNodes() {
    return Nodes;
  }
  public final void doPerformed(ActionEvent actionevent)
  {
    if(ExplorerView != null && Nodes != null )
      actionPerformed(ExplorerView,Nodes);
//      setNodes(null);
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
  public NodesAction(JExplorerView ExplorerView,String command,String s, char c, String s1, Icon icon)
  {
    super(s, c, s1, icon);
    this.ExplorerView = ExplorerView;
    this.Command = command;
  }

}
