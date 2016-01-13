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

public class CreateNodeAction extends UpdateAction {
  protected JExplorerView ExplorerView = null;
  protected JNodeStub     Node         = null;
  protected StubObject    childNodeDataStub = null;
  protected String Key = null;

  public void actionPerformed(JExplorerView ExplorerView,JNodeStub node) {
    Context context = new Context(ExplorerView,node);
    if ( ExplorerView != null && node != null && Key != null ) {
      node.createObject(Key,context);
    }
  }

  public final void doPerformed(ActionEvent e) {
      if(ExplorerView != null && Node != null )
        actionPerformed(ExplorerView,Node);
  }
  public void update(JExplorerView ExplorerView)
  {
      if ( childNodeDataStub == null || childNodeDataStub.getObject("nodeDataClass",null) == null ) {
        this.setEnabled(false);
      }
  }

  public final void doUpdate(Object obj)
  {
    if(ExplorerView != null)
      update(ExplorerView);
  }
  public CreateNodeAction(JExplorerView ExplorerView,JNodeStub ns,String key,
                          String s, char c, String s1, Icon icon,StubObject nds)
  {
    super(s, c, s1, icon);
    this.ExplorerView = ExplorerView;
    this.Key = key;Node = ns;
    childNodeDataStub = nds;
  }

}
