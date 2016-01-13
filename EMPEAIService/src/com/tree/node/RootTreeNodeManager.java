package com.tree.node;

import com.core.xml.*;
import com.dal.service.ModelTreeNodeManager;
import com.efounder.eai.data.*;
import com.efounder.service.tree.*;
import com.efounder.service.tree.node.*;
import java.util.Map;

public class RootTreeNodeManager extends ModelTreeNodeManager {
  /**
   *
   */
  public RootTreeNodeManager() {
  }
  /**
   *
   * @param treeManager TreeManager
   * @param treeNode ModelTreeNode
   * @return boolean
   */
  public boolean isTypeLeaf(TreeManager treeManager, ModelTreeNode treeNode) {
    return true;
  }
  /**
   *
   * @param treeManager TreeManager
   * @param paramObject JParamObject
   * @param parentModelTreeNode ModelTreeNode
   * @param treeNodeStubObject TreeNodeStubObject
   * @return StubObject
   */
  protected StubObject convertSourceObject(TreeManager treeManager,
                                           JParamObject paramObject,
                                           ModelTreeNode parentModelTreeNode,
                                           TreeNodeStubObject
                                           treeNodeStubObject) {
    return null;
  }
  /**
   *
   * @param treeManager TreeManager
   * @param nodeData Object
   * @param parentModelTreeNode ModelTreeNode
   * @param treeNodeStubObject TreeNodeStubObject
   * @return ModelTreeNode[]
   * @throws Exception
   */
  protected ModelTreeNode[] createNodeFromData(TreeManager treeManager,
                                               Object nodeData,
                                               ModelTreeNode parentModelTreeNode,
                                               TreeNodeStubObject
                                               treeNodeStubObject) throws
      Exception {
    ModelTreeNode[] treeNodes = new ModelTreeNode[1];
    RootTreeNode rootTreeNode = new RootTreeNode();
    rootTreeNode.setTreeManager(treeManager);
    rootTreeNode.setTreeNodeStubObject(treeNodeStubObject);


    treeNodes[0] = rootTreeNode;
    return treeNodes;
  }
  /**
   *
   * @param paramObject JParamObject
   * @param sourceObject StubObject
   * @param nodeGUID String
   * @param userObject Object
   * @return Object
   */
  public Object loadChildNodeData(TreeManager treeManager,JParamObject paramObject,StubObject sourceObject,String nodeGUID,Object userObject) throws Exception {
    return null;
  }

  protected StubObject convertNodeDataObject(TreeManager treeManager,
                                             JParamObject paramObject,
                                             ModelTreeNode parentModelTreeNode,
                                             TreeNodeStubObject
                                             treeNodeStubObject) {
    return null;
  }

  public Object loadNodeData(TreeManager treeManager, 
		                     JParamObject paramObject,
		                     StubObject sourceObject, String nodeGUID, Object userObject) throws Exception {

	return null;
  }

}
