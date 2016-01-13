package com.efounder.service.tree;

import org.openide.util.*;
import com.efounder.eai.data.*;
import com.efounder.eai.*;

public abstract class TreeLoader {
  /**
   *
   */
  public TreeLoader() {
  }
  /**
   *
   * @return TreeManager
   */
  public static final TreeLoader getDefault() {
    return getDefault(null,null);
  }
  public static final TreeLoader getDefault(String tier) {
    return getDefault(null,null,tier);
  }
  public static final TreeLoader getDefault(JParamObject paramObject,Object userObject) {
    return getDefault(paramObject,userObject,EAI.Tier);
  }
  /**
   *
   * @return TreeManager
   */
  public static final TreeLoader getDefault(JParamObject paramObject,Object userObject,String tier) {
    TreeLoader treeLoader = null;

    treeLoader = (TreeLoader)Lookup.getDefault().lookup(TreeLoader.class,tier);
    return treeLoader;
  }
  /**
   *
   * @param treeid String
   * @return TreeStubObject
   * @throws Exception
   */
  public final TreeStubObject loadTree(String treeid) throws Exception {
    return loadTree(treeid,JParamObject.Create(),null);
  }
  public final TreeStubObject loadTree(String treeid,String server) throws Exception {
	  JParamObject PO=JParamObject.Create();
	  if(server!=null)
		  PO.setEAIServer(server);
	  return loadTree(treeid,PO,null);
	  }
  /**
   *
   * @param treeid String
   * @return TreeStubObject
   * @throws Exception
   */
  public final TreeStubObject loadTree(String treeid,JParamObject paramObject) throws Exception {
    return loadTree(treeid,paramObject,null);
  }
  /**
   *
   * @param treeid String
   * @param userObject Object
   * @return TreeStubObject
   * @throws Exception
   */
  public final TreeStubObject loadTree(String treeid,JParamObject paramObject,Object userObject) throws Exception {
    TreeStubObject treeStubObject = null;
    // 装入树
    treeStubObject = loadTreeStubObject(treeid,paramObject,userObject);
    // 根节点
    TreeNodeStubObject rootNodeStubObject = null;
    // 装入根节点
    rootNodeStubObject = loadTreeNodeStubObject(treeStubObject,paramObject,userObject);
    // 如果不等空，则设置树的根节点
    if ( rootNodeStubObject != null ) {
      // 设置根节点
      treeStubObject.setRootNodeStubObject(rootNodeStubObject);
    }
    // 返回树
    return treeStubObject;
  }
  /**
   *
   * @param treeid String
   * @param userObject Object
   * @return TreeStubObject
   * @throws Exception
   */
  protected abstract TreeStubObject loadTreeStubObject(String treeid,JParamObject paramObject,Object userObject) throws Exception;
  /**
   *
   * @param treeStubObject TreeStubObject
   * @param userObject Object
   * @throws Exception
   */
  protected abstract TreeNodeStubObject loadTreeNodeStubObject(TreeStubObject treeStubObject,JParamObject paramObject,Object userObject) throws Exception;
}
