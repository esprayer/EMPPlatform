package com.efounder.node.view;

import java.util.*;
import com.efounder.node.*;
import javax.swing.*;
import java.awt.*;
import com.efounder.view.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class NodeViewerManager {
  /**
   *
   */
  protected static java.util.List nodeViewerFactoryList = null;
  /**
   *
   */
  public NodeViewerManager() {
  }
  /**
   *
   * @param p1 Context
   * @param p2 Object
   * @param p3 Object
   * @return NodeViewerFactory[]
   */
  public synchronized static NodeViewerFactory[] getNodeViewerFactorys(Context p1,Object p2,Object p3) {
    if ( nodeViewerFactoryList == null ) return null;
    java.util.List nvfList = new ArrayList();NodeViewerFactory nvf = null;
    NodeViewerFactory[] nvfs = null;
    for(int i=0;i<nodeViewerFactoryList.size();i++) {
      nvf = (NodeViewerFactory)nodeViewerFactoryList.get(i);
      nvfs = nvf.getNodeViewerFactorys(p1,p2,p3);
      for(int k=0;nvfs!=null&&k<nvfs.length;k++) {
        nvf = nvfs[k];
        if (nvf.canDisplayNode(p1, p2, p3)) {
          nvfList.add(nvf);
        }
      }
    }
    NodeViewerFactory[] NVFS = new NodeViewerFactory[nvfList.size()];
    NVFS = (NodeViewerFactory[])nvfList.toArray(NVFS);
    return NVFS;
  }
  /**
   *
   * @param nodeViewerFactory NodeViewerFactory
   */
  public synchronized static void unregisterNodeViewerFactory(NodeViewerFactory nodeViewerFactory) {
    if ( nodeViewerFactory == null ) return;
    if ( nodeViewerFactoryList == null ) return;
    nodeViewerFactoryList.remove(nodeViewerFactory);
  }
  public synchronized static void registerNodeViewerFactory(NodeViewerFactory nodeViewerFactory) {
    registerNodeViewerFactory(-1,nodeViewerFactory);
  }
  /**
   *
   * @param nodeViewerFactory NodeViewerFactory
   */
  public synchronized static void registerNodeViewerFactory(int order,NodeViewerFactory nodeViewerFactory) {
    if ( nodeViewerFactory == null ) return;
    if ( nodeViewerFactoryList == null ) {
      nodeViewerFactoryList = new Vector();
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
  /**
   *
   * @param comp JComponent
   * @return NodeViewer
   */
  public static NodeViewer findNodeViewer(Component obj) {
    if ( obj instanceof NodeViewer )
      return (NodeViewer)obj;
    if ( obj instanceof Component )
      return findNodeViewer(((Component)obj).getParent());
    else
      return null;
  }
  /**
   *
   * @param comp JComponent
   * @return NodeViewer
   */
  public static NodeWindow findNodeWindow(Component obj) {
    NodeViewer nv = findNodeViewer(obj);
    if ( nv != null ) return nv.getNodeWindow();
    return null;
  }
  /**
   *
   * @param obj Component
   * @return JExplorerView
   */
  public static JExplorerView findExporerView(Component obj) {
    NodeWindow ncw = findNodeWindow(obj);
    if ( ncw != null ) return ncw.getNodeStub().getExplorerView();
    return null;
  }
}
