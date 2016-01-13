package com.efounder.node.action;

import com.efounder.actions.ContextActionProvider;
import javax.swing.Action;
import com.efounder.node.*;
import com.efounder.action.*;
import com.efounder.eai.ide.*;
import com.core.xml.*;
import com.efounder.view.*;
import com.efounder.view.actions.*;
import javax.swing.*;
import com.efounder.node.view.*;
import com.efounder.eai.*;
import java.awt.event.*;
import com.efounder.help.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class GenerNodeActionProvider implements ContextActionProvider {
//  public static ActionGroup GROUP_Create   = new ExplorerActionGroup("新建",'C',"新建子节点对象",ExplorerIcons.ICON_NEW_FILE,true);;
//  public static ActionGroup GROUP_Open     = new ExplorerActionGroup("打开",'C',"打开节点视图",ExplorerIcons.ICON_OPEN,true);;;
//  public static ActionGroup GROUP_Gener    = new ExplorerActionGroup();
  public GenerNodeActionProvider() {
  }
  public Action getContextAction(Object frame, Object[] nodeArray) {
    if ( nodeArray == null ) return null;
    ActionGroup GROUP_Gener = new ExplorerActionGroup();JNodeStub ns = null;
    // 如果选择多个Node,则只有一个找开
    if ( nodeArray.length > 1 ) {
      Action ag = getOpenActionGroup((JExplorerView) frame, nodeArray);
      ActionGroup group1   = new ExplorerActionGroup();
      group1.add(ag);
      GROUP_Gener.add(group1);
      ag = getCloseAction((JExplorerView) frame, nodeArray);
      if ( ag != null ) {
        group1   = new ExplorerActionGroup();
        group1.add(ag);
        GROUP_Gener.add(group1);
      }
    } else {
      ns = (JNodeStub) nodeArray[0];
      // 如果存在数据对象管理器,需要增加"新建"Action
      Action ag = getCreateActionGroup( (JExplorerView) frame, ns);
      if (ag != null) {
        ActionGroup group1   = new ExplorerActionGroup();
        group1.add(ag);
        GROUP_Gener.add(group1);
      }
      // 处理打开Action
      ag = getOpenActionGroup( (JExplorerView) frame, ns);
      if (ag != null) {
        ActionGroup group1   = new ExplorerActionGroup();
        group1.add(ag);
        GROUP_Gener.add(group1);
      }
      // 处理关闭Action
      ag = getCloseAction((JExplorerView) frame, nodeArray);
      if ( ag != null ) {
        ActionGroup group1   = new ExplorerActionGroup();
        group1.add(ag);
        GROUP_Gener.add(group1);
      }
    }
    // 处理删除Action
    JNodeStub parentNode = null;StubObject SO = null;int i;
    for(i=0;i<nodeArray.length;i++) {
      ns = (JNodeStub)nodeArray[i];
      SO = ns.getNodeStubObject();
      parentNode = (JNodeStub)ns.getParent();
      if ( parentNode == null ) continue;
      if ( ns.getNodeDataStub() != null && parentNode.getNodeDataManager() != null && ns.getNodeStubObject() != null ) {
        break;
      }
    }
    if ( i < nodeArray.length ) { // 需要处理Delete Action
      ns = (JNodeStub)nodeArray[0];
      // 如果不为空，并且能删除
      if ( ns != null && ns.canDelete() ) {
        ActionGroup group1 = new ExplorerActionGroup();
        Action deleteAction = new DeleteNodeAction( (JExplorerView) frame,
            nodeArray, null, "删除", '0', "删除选择节点", ExplorerIcons.ICON_DELETE);
        group1.add(deleteAction);
        GROUP_Gener.add(group1);
      }
    }
    return GROUP_Gener;
  }
  private Action getCloseAction(JExplorerView ev,Object[] nodes) {
    Action closeAction = null;int i = 0;
    for(i=0;i<nodes.length;i++) {
      if ( ((JNodeStub)nodes[i]).getNodeWindow() != null ) break;
    }
    if ( i != nodes.length )
      closeAction = new CloseNodeAction(ev,nodes,null,"关闭",'0',"关闭节点所有视图",ExplorerIcons.ICON_CLOSE);
    return closeAction;
  }
  private Action getOpenActionGroup(JExplorerView ev,JNodeStub node) {
    ActionGroup GROUP_Open   = new ExplorerActionGroup("打开",'C',"打开",ExplorerIcons.ICON_OPEN,true);;
    Object[] NArray = {node};
    Action openAction = new OpenNodeAction(ev,NArray,null,"全部",'0',"打开节点所有视图",ExplorerIcons.ICON_CASCADE);
    GROUP_Open.add(openAction);
    // 获取View工厂
    Context context = new Context(ev,node);
    NodeViewerFactory[] nvfs = NodeViewerManager.getNodeViewerFactorys(context,node,ev);
    StubObject SO = null;
    if ( nvfs.length > 0 ) {
      ActionGroup GROUP_Open1   = new ExplorerActionGroup();
      for (int i = 0; i < nvfs.length; i++) {
        SO = nvfs[i].getStubObject();
        String ID    = nvfs[i].getID();
        String Name  = nvfs[i].getName();
        String sicon = nvfs[i].getIcon();
        Icon icon = null;
        if ( sicon != null )
          icon = ExplorerIcons.getImageIcon(SO, "/" + EAI.Product + "/Resource",sicon, null);
        openAction = new OpenNodeAction(ev,NArray,ID,Name,'0',Name,icon);
        GROUP_Open1.add(openAction);
      }
      GROUP_Open.add(GROUP_Open1);
    }
    Action setupNode = getNodeSetupAction(ev,node);
    if ( setupNode != null )
      GROUP_Open.add(setupNode);
    return GROUP_Open;
  }

  private Action getOpenActionGroup(JExplorerView ev,Object[] nodes) {
    Action openAction = new OpenNodeAction(ev,nodes,null,"打开",'C',"打开节点视图",ExplorerIcons.ICON_OPEN);
    return openAction;
  }
  private Action getCreateActionGroup(JExplorerView ev,JNodeStub ns) {
    if ( ns.getNodeDataManager() == null ) return null;
    java.util.List list = ns.getChildNodeStubList();
    if ( list.size() == 0 ) return null;
    ActionGroup GROUP_Create   = new ExplorerActionGroup("新建",'C',"新建子节点对象",ExplorerIcons.ICON_NEW_FILE,true);
    StubObject SO = null;Action createAction = null;
    for(int i=0;i<list.size();i++) {
      SO = (StubObject)list.get(i);String ID = SO.getString("id",null);String Name = SO.toString();
      createAction = new CreateNodeAction(ev,ns,ID,Name,'0',Name,(Icon)SO.getObject("icon",null),SO);
      if ( SO.getObject("nodeDataClass",null) == null )
        createAction.setEnabled(false);
      GROUP_Create.add(createAction);
    }
    return GROUP_Create;
  }
  private Action getNodeSetupAction(JExplorerView ev,JNodeStub node) {
    ActionGroup setupActions = new ActionGroup();
    Action setupAction = new ActiveObjectAction(this,node,"setupNode","节点设置",'0',"节点设置",ExplorerIcons.ICON_CUSTOMTOOLS);
    setupActions.add(setupAction);
    return setupActions;
  }
  public void setupNode(Object actionObject,Object dataObject,Action action,ActionEvent actionevent) {
    JNodeStub nodeStub = (JNodeStub)dataObject;
    HelpUtil.setupNode(nodeStub);
  }
}
