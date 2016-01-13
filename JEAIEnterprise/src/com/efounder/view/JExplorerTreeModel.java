package com.efounder.view;

import javax.swing.tree.*;

import com.efounder.node.*;
import com.efounder.service.security.*;
import com.core.xml.StubObject;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class JExplorerTreeModel extends DefaultTreeModel {
  public JExplorerTreeModel(TreeNode root) {
    this(root, false);
  }
  /**
   *
   * @param root TreeNode
   * @param asksAllowsChildren boolean
   */
  public JExplorerTreeModel(TreeNode root, boolean asksAllowsChildren) {
    super(root,asksAllowsChildren);
  }
  /**
   *
   * @param treepath TreePath
   * @param nodeStubArray JNodeStub[]
   * @return Object[]
   */
  public Object[] registryChildNode(TreePath treepath,JNodeStub[] nodeStubArray) {
    DefaultMutableTreeNode parentNode = (DefaultMutableTreeNode)treepath.getLastPathComponent();
    JNodeStub myParentNode = (JNodeStub)parentNode;
    StubObject nodeStubObject = myParentNode.getNodeStubObject();
    // 如果NodeStubObject不为空，处理hasChild
    if ( nodeStubObject != null ) {
      String haschild = nodeStubObject.getString("haschild", null);
      if ("0".equals(haschild))
        return null;
    }
    JNodeStub childNode  = null;
    int Count = nodeStubArray.length;
    int[] indexs = new int[Count];String GNBH = null;
    int begin = parentNode.getChildCount();
    Object[] nodes = new Object[Count];int index = 0;java.util.List nodeAddList = new java.util.ArrayList();
    for(int i=0;i<Count;i++) {
      childNode = nodeStubArray[i];
      if ( childNode == null ) continue;
      // 获取该节点的功能编号
      GNBH = childNode.getSecurityKey();
      if ( GNBH == null || "".equals(GNBH.trim()) || ServiceSecurityManager.getDefault().checkPermission(GNBH) ) {
        parentNode.add(childNode);
        indexs[i] = begin + (index++);
        nodeAddList.add(childNode);
//        nodes[i] = childNode;
      }
    }
    if ( nodeAddList.size() > 0 ) {
      fireTreeNodesInserted(this, treepath.getPath(), indexs, nodeAddList.toArray());
    }
    return nodes;
  }
  public Object[] changeChildNode(TreePath treepath,JNodeStub nodeStubArray) {
    DefaultMutableTreeNode parentNode = (DefaultMutableTreeNode)treepath.getLastPathComponent();
    DefaultMutableTreeNode childNode  = null;
    int Count = 1;//nodeStubArray.length;
    int[] indexs = new int[Count];
    Object[] nodes = new Object[Count];
    for(int i=0;i<parentNode.getChildCount();i++) {
      if ( nodeStubArray.equals(parentNode.getChildAt(i)) ) {
        indexs[0] = i;
        nodes[0] = nodeStubArray;
        break;
      }
    }
    fireTreeNodesChanged(this,treepath.getPath(),indexs,nodes);
    return nodes;
  }
  public Object[] removeChildNode(TreePath treepath,JNodeStub nodeStubArray) {
    DefaultMutableTreeNode parentNode = (DefaultMutableTreeNode)treepath.getLastPathComponent();
    DefaultMutableTreeNode childNode  = null;
    int Count = 1;//nodeStubArray.length;
    int[] indexs = new int[Count];
    Object[] nodes = new Object[Count];
    for(int i=0;i<parentNode.getChildCount();i++) {
      if ( nodeStubArray.equals(parentNode.getChildAt(i)) ) {
        indexs[0] = i;
        nodes[0] = nodeStubArray;
        parentNode.remove(i);
//        nodeStubArray.delete();
        break;
      }
    }
    fireTreeNodesRemoved(this,treepath.getPath(),indexs,nodes);
    return nodes;
  }

  /**
   *
   * @param treepath TreePath
   */
  public void dyinsertNode(TreePath treepath) {
//    DefaultMutableTreeNode node = (DefaultMutableTreeNode)treepath.getLastPathComponent();
//    JNodeStub ns = (JNodeStub)node.getUserObject();JNodeStub nss;
//    if ( ns.isExpanded() ) return;
//    ArrayList al = ns.getChilds();
//    int Count = al.size();
//    if ( Count <= 0 ) return;
//    int[] indexs = new int[Count];
//    int begin = node.getChildCount();
//    Object[] nodes = new Object[Count];
//    for(int i=0;i<Count;i++) {
//      nss = (JNodeStub) al.get(i);
//      DefaultMutableTreeNode n = new DefaultMutableTreeNode(nss);
//      node.add(n);
//      indexs[i] = begin+i;
//      nodes[i] = n;
//    }
//    ns.setExpanded(true);
//    this.fireTreeNodesInserted(this,treepath.getPath(),indexs,nodes);
  }

}
