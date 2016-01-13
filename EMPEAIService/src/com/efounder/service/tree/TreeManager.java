package com.efounder.service.tree;

import java.util.*;

import org.openide.util.*;
import com.core.xml.*;
import com.efounder.model.biz.*;
import com.efounder.service.tree.model.*;
import com.efounder.service.tree.node.*;
import com.efounder.eai.EAI;
import com.efounder.eai.data.JParamObject;


public abstract class TreeManager {
  /**
   *
   */
  public TreeManager() {
  }
  protected TreeExModel treeModel = null;
  protected JParamObject paramObject = null;

  /**
   *
   * @param treeModel TreeExModel
   */
  public void setTreeModel(TreeExModel treeModel) {
    this.treeModel = treeModel;
  }
  /**
   *
   * @return TreeExModel
   */
  public TreeExModel getTreeModel() {
    return treeModel;
  }
  /**
   *
   * @return TreeStubObject
   */
  public TreeStubObject getTreeStubObject() {
    return treeStubObject;
  }
  /**
   *
   */
  protected TreeStubObject treeStubObject = null;
  protected BIZContext bizContext = null;
  protected String tier;
  /**
   *
   * @param treeStubObject TreeStubObject
   * @return TreeManager
   */
//  public final static TreeManager getInstance(TreeStubObject treeStubObject) {
//    return getInstance(treeStubObject,null);
//  }
  private  static TreeManager getTreeManger(TreeStubObject treeStubObject,BIZContext bizContext,String tier,JParamObject jparamObject) {
   TreeManager treeManager = null;
   if(tier == null || "".equals(tier.trim()))
    treeManager = (TreeManager)Lookup.getDefault().lookups(TreeManager.class,EAI.Tier);
   else
     treeManager = (TreeManager)Lookup.getDefault().lookups(TreeManager.class,tier);
   if ( treeManager != null ) {
     treeManager.treeStubObject = treeStubObject;
     treeManager.paramObject = jparamObject;
     if ( bizContext != null ) treeManager.bizContext = bizContext;
   }
   treeManager.tier = tier;
   return treeManager;
 }

  /**
   *
   * @param treeStubObject TreeStubObject
   * @return TreeManager
   */
  public final static TreeManager getInstance(TreeStubObject treeStubObject,BIZContext bizContext) {
    return getTreeManger(treeStubObject, bizContext,null,null);

  }
  public final static TreeManager getInstance(TreeStubObject treeStubObject,BIZContext bizContext,String tier) {
     return getTreeManger(treeStubObject, bizContext,tier,null);
  }
  public final static TreeManager getInstance(TreeStubObject treeStubObject,BIZContext bizContext,String tier,JParamObject jparamObject) {
     return getTreeManger(treeStubObject, bizContext,tier,jparamObject);
 }




  //end add
  protected ModelTreeNode rootNode = null;
  /**
   *
   * @return TreeNodeEx
   */
  public final ModelTreeNode getRootNode() {
    return rootNode;
  }
  /**
   *
   * @return TreeModel
   */
  public TreeExModel buildTreeModel() {
    TreeExModel treeExModel = null;
    treeExModel = TreeExModel.getInstance (this,getRootNode(),true);
    return treeExModel;
  }
  /**
   *
   * @return TreeNodeEx
   */
  public void buildRootNode() throws Exception {
    ModelTreeNode[] treeNodes = expandTreeNode();
    if ( treeNodes.length > 0 ) rootNode = treeNodes[0];
  }
  /**
   *
   * @return ModelTreeNode[]
   * @throws Exception
   */
  public final ModelTreeNode[] expandTreeNode() throws Exception {
    return expandTreeNode(null,false);
  }
  /**
   *
   * @param treeNode TreeNodeEx
   */
  public final ModelTreeNode[] expandTreeNode(ModelTreeNode treeNode) throws Exception {
    return expandTreeNode(treeNode,false);
  }
  /**
   *
   * @param treeNode ModelTreeNode
   * @param isRefresh boolean
   * @param isAll boolean
   */
  public final ModelTreeNode[] expandTreeNode(ModelTreeNode treeNode,boolean isRefresh) throws Exception {
    // 如果是刷新，则需要删除现有的所有子节点
    if ( isRefresh ) this.removeChilds(treeNode);
    else if ( treeNode != null && treeNode.getChildCount() > 0 ) return null; // 如果不是刷新，且有子节点，则直接返回
    // 如果当前节点是此类节点的叶节点，则需要处理其下级不同类型节点
    if(treeNode != null)
       treeNode.setTier(this.tier);//add by luody
    if ( treeNode != null && treeNode.isTypeLeaf() ) {
      return expandDiffTreeNode(treeNode);// 展开不同类型的节点
    } else {
      return expandSameTreeNode(treeNode);// 展开相同类型的节点
    }
  }
  /**
   *
   * @param treeNode ModelTreeNode
   * @param isAll boolean
   */
  protected final ModelTreeNode[] expandDiffTreeNode(ModelTreeNode treeNode) throws Exception {
    // 获取当前节点的的StubObject对象
    TreeNodeStubObject parentTreeNodeStubObject = treeNode.getTreeNodeStubObject();
    if ( parentTreeNodeStubObject == null ) return null;
    TreeNodeStubObject childTreeNodeStubObject = null;
    // 循环处理每一个下级不同类型的节点
    StubObject[] stubObjects = parentTreeNodeStubObject.getChilds();
    if ( stubObjects == null || stubObjects.length == 0 ) return null;
    ModelTreeNode[] modelTreeNodes = null,treeNodes = null;
    java.util.List treeNodeList = new java.util.ArrayList();
    // 循环处理第一种类型的子节点
    for(int i=0;i<stubObjects.length;i++) {
      // 获取某一个
      childTreeNodeStubObject = (TreeNodeStubObject)stubObjects[i];
      // 展开此节点
      treeNodes = expandTreeNode(treeNode,childTreeNodeStubObject,true);
      // 放入列表中
      insertNode2List(treeNodeList,treeNodes);
    }
    // 转换成数组
    modelTreeNodes = getArrayFromList(treeNodeList);
    return modelTreeNodes;
  }
  /**
   *
   * @param treeNodeList List
   * @return ModelTreeNode[]
   */
  protected final ModelTreeNode[] getArrayFromList(java.util.List treeNodeList) {
    ModelTreeNode[] treeNodes = new ModelTreeNode[treeNodeList.size()];
    ModelTreeNode treeNode = null;
    for(int i=0;i<treeNodeList.size();i++) {
      treeNode = (ModelTreeNode)treeNodeList.get(i);
      treeNodes[i] = treeNode;
    }
    return treeNodes;
  }
  /**
   *
   * @param treeNodeList List
   * @param treeNodes ModelTreeNode[]
   */
  protected final void insertNode2List(java.util.List treeNodeList,
                                       ModelTreeNode[] treeNodes) {
    if ( treeNodes == null ) return;int index = treeNodeList.size();
    for(int i=0;i<treeNodes.length;i++) {
      treeNodeList.add(i+index,treeNodes[i]);
    }
  }
  /**
   *
   * @param treeNode ModelTreeNode
   * @param isAll boolean
   */
  protected final ModelTreeNode[] expandSameTreeNode(ModelTreeNode treeNode) throws Exception {
    TreeNodeStubObject childTreeNodeStubObject = null;
    // 如果不为空，取出自己的StubObject
    if ( treeNode != null ) childTreeNodeStubObject = treeNode.getTreeNodeStubObject();
    // 如果为空，说明是根节点，使用根节点的StubObject
    else childTreeNodeStubObject = this.treeStubObject.getRootNodeStubObject();
    return expandTreeNode(treeNode,childTreeNodeStubObject,false);
  }
  /**
   *
   * @param treeNode ModelTreeNode
   * @param childTreeNodeStubObject TreeNodeStubObject
   */
  protected final ModelTreeNode[] expandTreeNode(ModelTreeNode treeNode,TreeNodeStubObject childTreeNodeStubObject,boolean isDiff) throws Exception {
    String nodeGUID = childTreeNodeStubObject.getNodeGUID();
    TreeNodeManager treeNodeManager = TreeNodeManager.getInstance(nodeGUID,bizContext,this.tier,this.paramObject);
    if ( treeNodeManager == null ) return null;
    // 装入所有的子节点
    ModelTreeNode[] modelTreeNodes = treeNodeManager.loadChildNode(this,treeNode,childTreeNodeStubObject,isDiff);
    // 插进去
    if(modelTreeNodes != null){ //add by luody
      insertChildNode(treeNode, modelTreeNodes);
    }
    return modelTreeNodes;
  }

  //add by luody
  /**
   *
   * @param treeNode ModelTreeNode
   * @param dataIndex int  要取的数所序列
   * @return Object
   * @throws Exception
   */
  public final Object expandNodeData(ModelTreeNode treeNode,int dataIndex) throws Exception {
    // 如果是刷新，则需要删除现有的所有子节点
    TreeNodeStubObject treeNodeStubObject = null;
    // 如果不为空，取出自己的StubObject
    if(treeNode == null ) return null;
    treeNodeStubObject = treeNode.getTreeNodeStubObject();
    List dsList = treeNodeStubObject.getDsList();
    if(dsList == null || dsList.isEmpty()) return null;
    if(dataIndex > dsList.size()) dataIndex = dsList.size()-1;
    if(dataIndex<0 ) dataIndex = 0;

    TreeNodeStubObject nodeStubObject = (TreeNodeStubObject)dsList.get(dataIndex);//只存放了SYS_TREENODE表信息
    //如果是常量节点，则直接退出空值
    String nodeGUID = nodeStubObject.getNodeGUID();
    TreeNodeManager treeNodeManager = TreeNodeManager.getInstance(nodeGUID,bizContext,this.tier,this.paramObject);
    if ( treeNodeManager == null ) return null;
    // 装入节点数据
    Object nodeDataObject = treeNodeManager.loadNodeData(this,treeNode,nodeStubObject,dataIndex);
    return nodeDataObject;
  }

//end add


  /**
   *
   * @param treeNode ModelTreeNode
   * @param childNodes ModelTreeNode[]
   */
  protected final void insertChildNode(ModelTreeNode treeNode,ModelTreeNode[] childNodes) {
    if ( treeNode == null ) return;
    this.treeModel.insertChildNode(treeNode,childNodes);
//    int index = treeNode.getChildCount();
//    for(int i=0;i<childNodes.length;i++) {
//      treeNode.insert(childNodes[i],i+index);
//    }
  }
  /**
   *
   * @param treeNode ModelTreeNode
   */
  public void collapseTreeNode(ModelTreeNode treeNode) {}
  /**
   *
   * @param treeNode TreeNodeEx
   */
  public void removeChilds(ModelTreeNode treeNode) {}
  public String getTier(){
    return this.tier;
  }
}
