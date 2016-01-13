package com.efounder.service.tree;

import org.openide.util.*;
import com.efounder.service.tree.node.*;
import com.efounder.eai.data.*;
import com.core.xml.*;
import com.efounder.eai.*;
import com.efounder.model.biz.*;

public abstract class TreeNodeManager {
  /**
   *
   */
  public TreeNodeManager() {
  }
  protected String nodeGUID   = null;
  protected BIZContext bizContext = null;
  protected String tier = null;
  protected JParamObject PO = null;
  public final static TreeNodeManager getInstance(String nodeGUID) {
    return getInstance(nodeGUID,null);
  }
  public final static TreeNodeManager getInstance(String nodeGUID,BIZContext bizContext){
    return getInstance(nodeGUID,bizContext,null,null);
  }



  /**
   *
   * @param nodeGUID String
   * @return TreeNodeManager
   */
  public final static TreeNodeManager getInstance(String nodeGUID,BIZContext bizContext,String tier,JParamObject jparamObject) {
    TreeNodeManager treeNodeManager = null;
    if(tier == null || "".equals(tier))
        treeNodeManager = (TreeNodeManager)Lookup.getDefault().lookup(TreeNodeManager.class,nodeGUID+"_"+EAI.Tier);
    else
       treeNodeManager = (TreeNodeManager)Lookup.getDefault().lookup(TreeNodeManager.class,nodeGUID+"_"+tier);


    if ( treeNodeManager != null ) {
      treeNodeManager.nodeGUID = nodeGUID;
       treeNodeManager.tier = tier;
       treeNodeManager.PO = jparamObject;
      if ( bizContext != null ) treeNodeManager.bizContext = bizContext;
    }
    return treeNodeManager;
  }
  /**
   *
   * @param parentModelTreeNode ModelTreeNode
   * @param treeNodeStubObject TreeNodeStubObject
   * @param isAll boolean
   * @return Object
   * @throws Exception
   */
  protected Object loadChildNodeData(TreeManager treeManager,ModelTreeNode parentModelTreeNode,
                                           TreeNodeStubObject treeNodeStubObject) throws Exception {
    Object dataObject = null;
    JParamObject paramObject = JParamObject.Create();
    if ( this.bizContext != null ) {
      paramObject = bizContext.convertParamObject(this,paramObject);
//      bizContext.callBack(paramObject, this);
    }
    else if(this.PO != null) paramObject = this.PO;
    // 转换参数
    StubObject   sourceObject = convertSourceObject(treeManager,paramObject,parentModelTreeNode,treeNodeStubObject);
    // 装入数据
    if(sourceObject != null) //add by luody
      dataObject = loadChildNodeData(treeManager,paramObject,sourceObject,nodeGUID,null);
    return dataObject;
  }
  /**
     *
     * @param paramObject JParamObject
     * @param parentModelTreeNode ModelTreeNode
     * @param treeNodeStubObject TreeNodeStubObject
     * @return StubObject
     */
    protected abstract StubObject convertSourceObject(TreeManager treeManager,JParamObject paramObject,ModelTreeNode parentModelTreeNode,TreeNodeStubObject treeNodeStubObject);


   /**
   *
   * @param paramObject JParamObject
   * @param sourceObject StubObject
   * @param nodeGUID String
   * @param userObject Object
   * @return Object
   */
  public abstract Object loadChildNodeData(TreeManager treeManager,JParamObject paramObject,StubObject sourceObject,String nodeGUID,Object userObject) throws Exception;
  /**
   *
   * @param nodeData Object
   * @param parentModelTreeNode ModelTreeNode
   * @param treeNodeStubObject TreeNodeStubObject
   * @param isAll boolean
   * @return ModelTreeNode[]
   * @throws Exception
   */
  protected abstract ModelTreeNode[] createNodeFromData(TreeManager treeManager,Object nodeData,ModelTreeNode parentModelTreeNode,
                                           TreeNodeStubObject treeNodeStubObject) throws Exception;
  /**
   *
   * @param parentModelTreeNode ModelTreeNode
   * @param treeNodeStubObject TreeNodeStubObject
   * @param isAll boolean
   * @return ModelTreeNode[]
   * @throws Exception
   */
  public final ModelTreeNode[] loadChildNode(TreeManager treeManager,ModelTreeNode parentModelTreeNode,
                                             TreeNodeStubObject treeNodeStubObject,boolean isDiff) throws Exception {
    ModelTreeNode[] modelTreeNodes = null;
    Object nodeData = loadChildNodeData(treeManager,parentModelTreeNode,treeNodeStubObject);
    modelTreeNodes = createNodeFromData(treeManager,nodeData,parentModelTreeNode,treeNodeStubObject);
    // 设置
    for(int i=0;modelTreeNodes!=null&&i<modelTreeNodes.length;i++) {
      modelTreeNodes[i].setTreeStubObject(treeManager.getTreeStubObject());
      modelTreeNodes[i].setTreeNodeStubObject(treeNodeStubObject);
    }
    return modelTreeNodes;
  }
  /**
   *
   * @param treeNode ModelTreeNode
   * @return boolean
   */
  public abstract boolean isTypeLeaf(TreeManager treeManager,ModelTreeNode treeNode);




  /**
   *
   * @param treeManager TreeManager
   * @param parentModelTreeNode ModelTreeNode
   * @param treeNodeStubObject TreeNodeStubObject
   * @param dataIndex int  要获取的数据节点序号
   * @return Object
   * @throws Exception
   */
  public Object loadNodeData(TreeManager treeManager,ModelTreeNode parentModelTreeNode,
                                             TreeNodeStubObject treeNodeStubObject,int dataIndex) throws Exception {
      Object dataObject = null;
      JParamObject paramObject = JParamObject.Create();
      if(this.PO != null) paramObject = this.PO;
      paramObject.SetIntByParamName("DATAINDEX",dataIndex);//传入需要获取数据节点序号
      // 转换参数
      StubObject   sourceObject = convertNodeDataObject(treeManager,paramObject,parentModelTreeNode,treeNodeStubObject);
      // 装入数据
      dataObject = loadNodeData(treeManager,paramObject,sourceObject,nodeGUID,null);
      return dataObject;
    }
    /**
   *
   * @param treeManager TreeManager
   * @param paramObject JParamObject
   * @param parentModelTreeNode ModelTreeNode
   * @param treeNodeStubObject TreeNodeStubObject
   * @return StubObject
   */
  protected abstract StubObject convertNodeDataObject(TreeManager treeManager,JParamObject paramObject,ModelTreeNode parentModelTreeNode,TreeNodeStubObject treeNodeStubObject);


    /**
     *
     * @param treeManager TreeManager
     * @param paramObject JParamObject
     * @param sourceObject StubObject
     * @param nodeGUID String
     * @param userObject Object
     * @return Object
     * @throws Exception
     */
    public abstract Object loadNodeData(TreeManager treeManager,JParamObject paramObject,
                                             StubObject sourceObject,String nodeGUID,Object userObject) throws Exception;



}
