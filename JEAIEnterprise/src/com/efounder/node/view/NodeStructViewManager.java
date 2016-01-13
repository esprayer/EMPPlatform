package com.efounder.node.view;

import com.efounder.node.*;
import java.util.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class NodeStructViewManager {
  /**
   *
   */
  protected static java.util.List nodeViewerFactoryList = null;
  public NodeStructViewManager() {
  }
  /**
   * 获取NodeStructView的所有视图工厂
   * @param p1 Context
   * @param nodeView NodeViewer
   * @param p2 Object
   * @param p3 Object
   * @return NodeAttribViewerFactory[]
   */
  public synchronized static NodeAttribViewerFactory[] getNodeViewerFactorys(Context p1,NodeViewer nodeView,Object p2,Object p3) {
    if ( nodeViewerFactoryList == null ) return null;
    java.util.List nvfList = new ArrayList();NodeAttribViewerFactory nvf = null;
    NodeAttribViewerFactory[] nvfs = null;
    for(int i=0;i<nodeViewerFactoryList.size();i++) {
      nvf = (NodeAttribViewerFactory)nodeViewerFactoryList.get(i);
      try {
        if (nvf.canDisplayNode(p1, nodeView, p2, p3)) {
          nvfs = nvf.createNodeAttribViewerFactory(p1,nodeView,p2,p3);
          if ( nvfs != null ) {
            for(int j=0;j<nvfs.length;j++) nvfList.add(nvfs[j]);
          } else {
            nvfList.add(nvf);
          }
        }
      } catch ( Exception ee ) {
        ee.printStackTrace();
      }
    }
    NodeAttribViewerFactory[] NVFS = new NodeAttribViewerFactory[nvfList.size()];
    NVFS = (NodeAttribViewerFactory[])nvfList.toArray(NVFS);
    return NVFS;
  }
  /**
   *
   * @param nodeViewerFactory NodeViewerFactory
   */
  public synchronized static void unregisterNodeViewerFactory(NodeAttribViewerFactory nodeViewerFactory) {
    if ( nodeViewerFactory == null ) return;
    if ( nodeViewerFactoryList == null ) return;
    nodeViewerFactoryList.remove(nodeViewerFactory);
  }
  /**
   *
   * @param nodeViewerFactory NodeAttribViewerFactory
   */
  public synchronized static void registerNodeViewerFactory(NodeAttribViewerFactory nodeViewerFactory) {
    registerNodeViewerFactory(-1,nodeViewerFactory);
  }
  /**
   * 注册一个视图工厂s
   * @param nodeViewerFactory NodeViewerFactory
   */
  public synchronized static void registerNodeViewerFactory(int order,NodeAttribViewerFactory nodeViewerFactory) {
    if ( nodeViewerFactory == null ) return;
    if ( nodeViewerFactoryList == null ) {
      nodeViewerFactoryList = new ArrayList();
    }
    if ( order == -1 || order > nodeViewerFactoryList.size() ) order = nodeViewerFactoryList.size();
    if ( nodeViewerFactoryList.indexOf(nodeViewerFactory) == -1 ) {
      nodeViewerFactoryList.add(order,nodeViewerFactory);
    }
  }
  /**
   *
   * @return Iterator
   */
  public static synchronized java.util.Iterator Enumerate() {
    if ( nodeViewerFactoryList == null ) return null;
    return nodeViewerFactoryList.iterator();
  }

}
