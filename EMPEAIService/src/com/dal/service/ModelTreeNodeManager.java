package com.dal.service;

import com.core.xml.*;
import com.efounder.eai.data.*;
import com.efounder.service.tree.*;
import com.efounder.service.tree.node.*;
import com.efounder.eai.EAI;
import com.tree.node.RootTreeNode;

public abstract class ModelTreeNodeManager extends TreeNodeManager {
  /**
   *
   */
  public ModelTreeNodeManager() {
  }

  /**
   *
   * @param paramObject JParamObject
   * @param parentModelTreeNode ModelTreeNode
   * @param treeNodeStubObject TreeNodeStubObject
   * @return StubObject
   * @todo Implement this com.efounder.service.tree.TreeNodeManager method
   */
  protected abstract StubObject convertSourceObject(TreeManager treeManager,JParamObject paramObject,
                                           ModelTreeNode parentModelTreeNode,
                                           TreeNodeStubObject
                                           treeNodeStubObject);
  /**
   *
   * @param parentTreeNode ModelTreeNode
   * @param fieldName String
   * @return Object
   */
  protected final Object getValueOfField(ModelTreeNode parentTreeNode,
                                         String fieldName) {
    Object valueObj = null;
    if (parentTreeNode == null)
        return null;

    TreeNodeDataObject tdso = parentTreeNode.getTreeNodeDataObject();
    if (tdso == null)
      return null;

    valueObj = tdso.getValue(fieldName.toUpperCase().trim(), null);
    if (valueObj != null)
      return valueObj;
    ModelTreeNode mtn = (ModelTreeNode) parentTreeNode.getParent();
    while(true){

       if (mtn instanceof RootTreeNode )
         break;
       tdso = mtn.getTreeNodeDataObject();
       valueObj = tdso.getValue(fieldName.toUpperCase().trim(), null);
       if(valueObj != null) break;
       mtn = (ModelTreeNode)mtn.getParent();
    }
     return valueObj;

  }


  /**
   *
   * @param nodeData Object
   * @param parentModelTreeNode ModelTreeNode
   * @param treeNodeStubObject TreeNodeStubObject
   * @return ModelTreeNode[]
   * @throws Exception
   * @todo Implement this com.efounder.service.tree.TreeNodeManager method
   */
  protected abstract ModelTreeNode[] createNodeFromData(TreeManager treeManager,Object nodeData,
                                               ModelTreeNode
                                               parentModelTreeNode,
                                               TreeNodeStubObject
                                               treeNodeStubObject) throws
      Exception ;
  /**
   *
   * @param paramObject JParamObject
   * @param sourceObject StubObject
   * @param nodeGUID String
   * @param userObject Object
   * @return Object
   */
  public Object loadChildNodeData(TreeManager treeManager,JParamObject paramObject,StubObject sourceObject,String nodeGUID,Object userObject) throws Exception {
    JResponseObject responseObject = null;Object dataObject = null;
    responseObject = (JResponseObject)EAI.DAL.IOM("TreeService","loadNodeData",paramObject,sourceObject,nodeGUID,userObject);
    dataObject = responseObject.getResponseObject();
    return dataObject;
  }

  public boolean isTypeLeaf(TreeManager treeManager, ModelTreeNode treeNode) {
    return false;
  }

  protected abstract StubObject convertNodeDataObject(TreeManager treeManager,
                                          JParamObject paramObject,
                                          ModelTreeNode parentModelTreeNode,
                                          TreeNodeStubObject treeNodeStubObject);


  public Object loadNodeData(TreeManager treeManager,
                               JParamObject paramObject,
                               StubObject sourceObject, String nodeGUID,
                               Object userObject) throws Exception {
    JResponseObject responseObject = null;Object dataObject = null;
    responseObject = (JResponseObject)EAI.DAL.IOM("TreeService","loadNodeDataEx",paramObject,sourceObject,nodeGUID,userObject);
    dataObject = responseObject.getResponseObject();
    return dataObject;

  }

}
