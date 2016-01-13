package com.efounder.tree;

import javax.swing.*;
import javax.swing.tree.*;
import com.efounder.node.*;
import com.efounder.view.*;
import java.util.*;
import com.efounder.actions.*;
import com.efounder.comp.tree.*;
import com.efounder.eai.ide.*;
import javax.swing.plaf.TreeUI;
import com.sap.plaf.frog.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class JExplorerTree extends JImageTree {
  protected JExplorerTreeModel ExplorerTreeModel = null;
  protected TreeCellRenderer treeRenderer = null;
  protected JNodeStub RootNode = null;
  public JExplorerTreeModel getExplorerTreeModel() {
    return ExplorerTreeModel;
  }
  /**
   *
   */
  public JExplorerTree() {
    initRenderer();
    this.setRowHeight(22);
//    Icon icon = ExplorerIcons.getExplorerIcon("greencnpc.jpg");
//    this.setIconImage((ImageIcon)icon);
  }
  /**
   *
   * @param etm JExplorerTreeModel
   */
  public JExplorerTree(JExplorerTreeModel etm) {
    ExplorerTreeModel = etm;
    RootNode = (JNodeStub)etm.getRoot();
    initTree();
    initRenderer();
  }
  /**
   *
   */
  protected void initRenderer() {
    treeRenderer = new MyCellRenderer();//JExplorerTreeCellRenderer();//
    setCellRenderer(treeRenderer);
  }
  public JNodeStub getRootNode() {
    return RootNode;
  }
  public void initExplorerTree(JNodeStub RootNode) {
    this.RootNode = RootNode;
    ExplorerTreeModel = new JExplorerTreeModel(this.RootNode,true);
    initTree();
  }

  /**
   *
   */
  public void initTree() {
    setModel(ExplorerTreeModel);
    setShowsRootHandles(true);
    setRootVisible(true);
  }
  /**
   *
   * @param node JNodeStub
   * @return TreePath
   */
  public TreePath findTreePath(JNodeStub node) {
    ArrayList NodeArray = new ArrayList();
    getChild(node,NodeArray);
    TreePath tp = new TreePath(NodeArray.toArray());
    return tp;
  }
  void getChild(TreeNode NodeObject,ArrayList NodeArray) {
    if ( NodeObject.getParent() == null ) {
      NodeArray.add(NodeObject);
    } else {
      getChild(NodeObject.getParent(),NodeArray);
      NodeArray.add(NodeObject);
    }
  }
  public static String SAP_STD_LOOKANDFEEL =
      "com.sap.plaf.frog.FrogLookAndFeel";
  /**
   *
   */
  public void updateUI() {
//    LookAndFeel lf = UIManager.getLookAndFeel();
    try {
//      UIManager.setLookAndFeel(SAP_STD_LOOKANDFEEL);
//      super.updateUI();
      FrogTreeUI ui = new FrogTreeUI();
      setUI(ui);
    } catch ( Exception ex ) {
      ex.printStackTrace();
    } finally {
//      try {
//        UIManager.setLookAndFeel(lf);
//      }
//      catch (UnsupportedLookAndFeelException ex1) {
//      }
    }
    invalidate();
  }
}
