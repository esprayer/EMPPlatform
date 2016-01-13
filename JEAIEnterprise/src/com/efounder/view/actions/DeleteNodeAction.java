package com.efounder.view.actions;

import com.efounder.action.UpdateAction;
import java.awt.event.*;
import com.efounder.view.*;
import com.efounder.node.*;
import javax.swing.*;
import com.core.xml.*;
import com.efounder.eai.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class DeleteNodeAction extends UpdateAction {
  protected JExplorerView ExplorerView = null;
  protected Object[]   Nodes         = null;
  protected String Key = null;
  /**
   *
   * @param ExplorerView JExplorerView
   * @param nodeArray Object[]
   */
  public void actionPerformed(JExplorerView ExplorerView,Object[] nodeArray) {
    JNodeStub parentNode = null,ns=null;StubObject SO = null;int i;
    for(i=0;i<nodeArray.length;i++) {
      ns = (JNodeStub)nodeArray[i];
      SO = ns.getNodeStubObject();
      parentNode = (JNodeStub)ns.getParent();
      if ( canDelete(ns) ) { //&& ns.canDelete()  ) {
        if (ns.getNodeDataStub() != null && parentNode.getNodeDataManager() != null) {
          parentNode.getNodeDataManager().removeNodeData(ns.getNodeStubObject().
              getString("id", null), ns.getNodeDataStub());
        }
      }
    }
  }
  /**
   *
   * @param e ActionEvent
   */
  public final void doPerformed(ActionEvent e) {
    if(ExplorerView != null && Nodes != null ) {

      actionPerformed(ExplorerView, Nodes);
    }
  }
  /**
   *
   * @param nodeStub JNodeStub
   * @return boolean
   */
  protected boolean canDelete(JNodeStub nodeStub) {
    int Res = JOptionPane.showConfirmDialog(EAI.EA.getMainWindow(),"真的删除节点"+nodeStub.toString()+"吗？", "系统提示",JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
    switch ( Res ) {
      case JOptionPane.YES_OPTION:
        return true;
      case JOptionPane.NO_OPTION:
        return false;
    }
    return false;
  }
  /**
   *
   * @param ExplorerView JExplorerView
   * @param nss Object[]
   * @param key String
   * @param s String
   * @param c char
   * @param s1 String
   * @param icon Icon
   */
  public DeleteNodeAction(JExplorerView ExplorerView,Object[] nss,String key,
                          String s, char c, String s1, Icon icon)
  {
    super(s, c, s1, icon);
    this.ExplorerView = ExplorerView;
    this.Key = key;Nodes = nss;
  }

}
