package com.efounder.service.tree;

import javax.swing.*;
import javax.swing.tree.*;

import com.efounder.service.tree.model.*;
import com.efounder.service.tree.view.*;
import com.efounder.service.tree.node.*;
import com.core.xml.StubObject;
import com.efounder.eai.ide.ExplorerIcons;
import com.efounder.model.biz.*;

public class TreeViewBuilder {
  /**
   *
   */
  protected TreeViewBuilder() {
  }
  public static JTreeView buildTreeView(String treeid) throws Exception {
    return buildTreeView(treeid,null);
  }
  /**
   *
   * @param treeid String
   * @return JTreeView
   * @throws Exception
   */
  public static JTreeView buildTreeView(String treeid,BIZContext bizContext) throws Exception {
    JTreeView treeView = null;
    // 获取树装入器
    TreeLoader treeLoader = TreeLoader.getDefault();
    // 装入树
    TreeStubObject treeStubObject = treeLoader.loadTree(treeid);
    // 获取树管理器
    TreeManager treeManager = TreeManager.getInstance(treeStubObject,bizContext);
    // 创建根节点
    treeManager.buildRootNode();
    ModelTreeNode rootTreeNode = treeManager.getRootNode();
    //add by luody
     //TreeDataStub userStub = new TreeDataStub();
     //userStub.setCaption(treeStubObject.getString("TREE_MC",""));
     //rootTreeNode.setTreeNodeDataObject(userStub);

    //end add
    // 创建Model
    TreeExModel treeModel = treeManager.buildTreeModel();
    // 创建TreeView
    treeView = new JTreeView();
    // 获取树控件
    JTree tree = treeView.getTree();
     // 新建CellRenderer
    TreeCellRenderer cellRenderer = new TreeExCellRenderer();
    tree.setCellRenderer(cellRenderer);
    // 设置
    tree.setModel(treeModel);
    treeView.setTreeManager(treeManager);
    treeManager.expandTreeNode(rootTreeNode);
    tree.repaint();
    return treeView;
  }
}
