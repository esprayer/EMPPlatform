package com.efounder.node;

import org.openide.util.*;
import com.efounder.model.biz.*;
import java.awt.*;
import com.efounder.node.view.NodeChildWindow;
import com.efounder.pfc.window.*;
import com.efounder.eai.ide.*;

public abstract class NodeBuilderManager {
  /**
   *
   */
  public NodeBuilderManager() {
  }
  /**
   *
   * @param comp Component
   * @return JNodeStub
   */
  public static JNodeStub getNodeStub(Component comp) {
    JNodeStub nodeStub = null;
    NodeChildWindow nodeWindow = NodeChildWindow.getNodeChildWindow(comp);
    if ( nodeWindow == null ) {
      IWindow window = EnterpriseExplorer.findExplorer(comp).ContentView.getActiveWindow();
      if ( window != null )
        nodeWindow = NodeChildWindow.getNodeChildWindow(window.getWindowComponent());
    }
    if ( nodeWindow != null ) {
      nodeStub = nodeWindow.getNodeStub();
    }
    return nodeStub;
  }
  /**
   *
   * @param builderID String
   * @return NodeBuilderManager
   */
  public static NodeBuilderManager getInstance(String builderID) {
    NodeBuilderManager nodeBuilder = null;
    nodeBuilder = (NodeBuilderManager)Lookup.getDefault().lookup(NodeBuilderManager.class,builderID);
    return nodeBuilder;
  }
  /**
   *
   * @param bizContext BIZContext
   * @param parentNode JNodeStub
   * @param constObject Object
   * @param userObject Object
   * @return JNodeStub
   */
  public abstract JNodeStub[] constructNode(BIZContext bizContext,JNodeStub parentNode,
                                          Object constObject,Object userObject);
  /**
   *
   * @param bizContext BIZContext
   * @param parentNode JNodeStub
   * @param constObject Object
   * @param userObject Object
   * @return JNodeStub
   */
  public final JNodeStub constructOneNode(BIZContext bizContext,JNodeStub parentNode,
                                          Object constObject,Object userObject) {
    JNodeStub[] ns = constructNode(bizContext,parentNode,constObject,userObject);
    if ( ns != null && ns.length > 0 ) {
      return ns[0];
    }
    return null;
  }
}
