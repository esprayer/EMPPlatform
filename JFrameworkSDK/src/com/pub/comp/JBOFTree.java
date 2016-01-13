package com.pub.comp;

import javax.swing.*;
import javax.swing.tree.TreePath;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 * <p>Title: 树管理</p>
 * <p>Description: 继续自树</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

public class JBOFTree extends JTree {
  /**
   * 初始化
   */
  public JBOFTree() {
  }
  /**
   * 展开指定树节点的所有下级
   * @param tp 树的节点
   */
  public void ExpandAll(TreePath tp) {
    DefaultMutableTreeNode node,node1;TreePath tp2;
    expandPath(tp);
    node = (DefaultMutableTreeNode)tp.getLastPathComponent();
    for(int i=0;i<node.getChildCount();i++) {
      node1 = (DefaultMutableTreeNode)node.getChildAt(i);
      if ( node1.isLeaf() ) continue;
      tp2 = new TreePath(node1.getPath());
      ExpandAll(tp2);
    }
  }
  /**
   * 收起指定节点的所有下级
   * @param tp 树的节点
   */
  public void CollapseAll(TreePath tp) {
    DefaultMutableTreeNode node,node1;TreePath tp2;
    node = (DefaultMutableTreeNode)tp.getLastPathComponent();
    for(int i=0;i<node.getChildCount();i++) {
      node1 = (DefaultMutableTreeNode)node.getChildAt(i);
      if ( node1.isLeaf() ) continue;
      tp2 = new TreePath(node1.getPath());
      CollapseAll(tp2);
    }
    collapsePath(tp);
  }
}