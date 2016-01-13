package com.efounder.service.tree;

import javax.swing.*;
import javax.swing.tree.*;

import com.efounder.model.biz.*;
import com.efounder.service.tree.model.*;
import com.efounder.service.tree.node.*;
import com.efounder.service.tree.view.*;
import com.efounder.eai.data.JParamObject;
import java.util.Map;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2004</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class TreeBuilder {
  protected TreeBuilder() {
  }
  /**
   *
   * @param treeid String
   * @return JTreeView
   * @throws Exception
   */
  public static JFlowTreePanel buildFlowTree(String treeid,BIZContext bizContext) throws Exception {
   JFlowTreePanel treeView = null;
   // 获取树装入器
   TreeLoader treeLoader = TreeLoader.getDefault();
   JParamObject paramObject = JParamObject.Create();
   if ( bizContext != null )
     paramObject = bizContext.convertParamObject(treeLoader,paramObject);
   // 装入树
   TreeStubObject treeStubObject = treeLoader.loadTree(treeid,paramObject);
   // 获取树管理器
   TreeManager treeManager = TreeManager.getInstance(treeStubObject,bizContext);
   // 创建根节点
   treeManager.buildRootNode();
   ModelTreeNode rootTreeNode = treeManager.getRootNode();
   //add by luody
    TreeDataStub userStub = new TreeDataStub();
    String rootCatpion = treeStubObject.getString("TREE_MC","");
    Map attrMap = treeStubObject.getAttrMap();
   String ncaption = "";
   if(attrMap != null) ncaption = (String)attrMap.get("rootCaption");
   if(ncaption != null && !"".equals(ncaption.trim())) rootCatpion = ncaption;
   userStub.setCaption(rootCatpion);


    rootTreeNode.setTreeNodeDataObject(userStub);

   //end add
   // 创建Model
   TreeExModel treeModel = treeManager.buildTreeModel();
   // 创建TreeView
   treeView = new JFlowTreePanel();
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

  public static JTreeEx buildTree(String treeid,BIZContext bizContext) throws Exception {
    JTreeEx treeView = null;
    // 获取树装入器
    TreeLoader treeLoader = TreeLoader.getDefault();
    JParamObject paramObject = JParamObject.Create();
    if ( bizContext != null )
      paramObject = bizContext.convertParamObject(treeLoader,paramObject);
    // 装入树
    TreeStubObject treeStubObject = treeLoader.loadTree(treeid,paramObject);
    // 获取树管理器
    TreeManager treeManager = TreeManager.getInstance(treeStubObject,bizContext);
    // 创建根节点
    treeManager.buildRootNode();
    ModelTreeNode rootTreeNode = treeManager.getRootNode();
    //add by luody
     TreeDataStub userStub = new TreeDataStub();
     String rootCatpion = treeStubObject.getString("TREE_MC","");
     Map attrMap = treeStubObject.getAttrMap();
    String ncaption = "";
    if(attrMap != null) ncaption = (String)attrMap.get("rootCaption");
    if(ncaption != null && !"".equals(ncaption.trim())) rootCatpion = ncaption;
    userStub.setCaption(rootCatpion);


     rootTreeNode.setTreeNodeDataObject(userStub);

    //end add
    // 创建Model
    TreeExModel treeModel = treeManager.buildTreeModel();
    // 创建TreeView
    treeView = new JTreeEx();
    // 获取树控件
    JTree tree = treeView;
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
