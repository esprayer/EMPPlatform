package com.efounder.service.tree.node;

import javax.swing.tree.*;

import com.efounder.service.tree.*;

public interface TreeNodeEx extends TreeNode {
  /**
   *
   * @return TreeNodeStubObject
   */
  public TreeNodeStubObject getTreeNodeStubObject();
  /**
   *
   * @param treeNodeStubObject TreeNodeStubObject
   */
  public void setTreeNodeStubObject(TreeNodeStubObject treeNodeStubObject);
  /**
   *
   * @return TreeNodeDataObject
   */
  public TreeNodeDataObject getTreeNodeDataObject();
  /**
   *
   * @param treeNodeDataObject TreeNodeDataObject
   */
  public void setTreeNodeDataObject(TreeNodeDataObject treeNodeDataObject);
  /**
   *
   * @return TreeStubObject
   */
  public TreeStubObject  getTreeStubObject();
  /**
   *
   * @param treeStubObject TreeStubObject
   */
  public void setTreeStubObject(TreeStubObject treeStubObject);
  /**
   *
   * @param treeManger TreeManager
   */
  public void setTreeManager(TreeManager treeManger);
  /**
   *
   * @return TreeManager
   */
  public TreeManager getTreeManager();
  /**
   * 此节点是否是此类节点的叶节点
   * @return boolean
   */
  public boolean isTypeLeaf();
}
