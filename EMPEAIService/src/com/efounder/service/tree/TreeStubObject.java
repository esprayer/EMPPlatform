package com.efounder.service.tree;

import com.core.xml.*;
import com.pub.util.StringUtil;

import java.util.Map;

public class TreeStubObject extends StubObject {
  /**
   *
   */
  protected TreeStubObject() {
  }
  /**
   *
   * @return TreeStubObject
   */
  public static TreeStubObject getInstance() {
    return new TreeStubObject();
  }
  /**
   *
   */
  protected TreeNodeStubObject rootNodeStubObject = null;
  /**
   *
   * @return TreeNodeStubObject
   */
  public TreeNodeStubObject getRootNodeStubObject() {
    return rootNodeStubObject;
  }
  /**
   *
   * @param treeNodeStubObject TreeNodeStubObject
   */
  public void setRootNodeStubObject(TreeNodeStubObject treeNodeStubObject) {
    this.rootNodeStubObject = treeNodeStubObject;
  }
  /**
   *
   */
   protected TreeManager treeManager = null;
  /**
   *
   * @param treeManager TreeManager
   */
  protected void setTreeManager(TreeManager treeManager) {
    this.treeManager = treeManager;
  }
  /**
   *
   * @return TreeManager
   */
  public TreeManager getTreeManager() {
    return treeManager;
  }
  public Map getAttrMap(){
    String attrStr = (String)getObject("TREE_ATTR","");
    if("".equals(attrStr)) return null;
    return StringUtil.keyValueToMap(attrStr,";","=");
  }

}
