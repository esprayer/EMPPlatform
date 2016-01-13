package com.efounder.service.tree.model;

import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import com.efounder.service.tree.*;
import com.efounder.service.tree.node.ModelTreeNode;
import javax.swing.tree.TreePath;

public class TreeExModel extends DefaultTreeModel {
  protected TreeManager treeManager = null;
  /**
   *
   * @param root TreeNode
   * @param asksAllowsChildren boolean
   */
  protected TreeExModel(TreeManager treeManager,TreeNode root, boolean asksAllowsChildren) {
    super(root,asksAllowsChildren);
    this.treeManager = treeManager;
    treeManager.setTreeModel(this);
  }
  /**
   *
   * @param treeManager TreeManager
   * @param root TreeNode
   * @param asksAllowsChildren boolean
   * @return TreeExModel
   */
  public static TreeExModel getInstance(TreeManager treeManager,TreeNode root, boolean asksAllowsChildren) {
    TreeExModel treeModel = new TreeExModel(treeManager,root,asksAllowsChildren);
    return treeModel;
  }
  /**
   *
   * @param treeNode ModelTreeNode
   * @param childNodes ModelTreeNode[]
   */
  public void insertChildNode(ModelTreeNode treeNode,ModelTreeNode[] childNodes) {
    int index = treeNode.getChildCount();int[] indexs = new int[childNodes.length];
    for(int i=0;i<childNodes.length;i++) {
      treeNode.insert(childNodes[i],i+index);
      indexs[i] = i+index;
    }
    TreeNode[] treeNodes = this.getPathToRoot(treeNode);
    TreePath treePath = new TreePath(treeNodes);
    fireTreeNodesInserted(this, treePath.getPath(), indexs, childNodes);
  }
}
