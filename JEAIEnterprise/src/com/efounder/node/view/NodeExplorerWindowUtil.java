package com.efounder.node.view;

import com.efounder.node.*;
import com.efounder.view.JExplorerView;

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
public class NodeExplorerWindowUtil {
  /**
   *
   */
  protected NodeExplorerWindowUtil() {
  }
  /**
   *
   * @param nodeStub JNodeStub
   * @param nodeWindow NodeWindow
   * @return NodeExplorerWindow
   */
  public static NodeExplorerWindow openExpObject(JNodeStub nodeStub,NodeViewer nodeView) throws Exception {
    NodeExplorerWindow  expWin = null;
    Context context = getContext(nodeStub,nodeView);
    NodeViewerFactory[] nvfs = NodeExplorerViewManager.getNodeViewerFactorys(context, nodeStub, nodeView);
    // 如果视为空，则不处理
    if ( nvfs == null || nvfs.length == 0 ) return null;
    expWin = NodeExplorerWindow.getNodeExplorerWindow(nodeStub,nodeView);
    expWin.initNodeChildWindow(context, nvfs,nodeView);
    return expWin;
  }
  /**
   *
   * @param nodeStub JNodeStub
   * @param nodeWindow NodeWindow
   * @return Context
   */
  protected static Context getContext(JNodeStub nodeStub,NodeViewer nodeView) {
    JExplorerView ExplorerView = nodeStub.getExplorerView();
    Context context = new Context(ExplorerView,nodeStub);
    context.setNodeWindow(nodeView.getNodeWindow());
    context.setNodeViewer(nodeView);
    return context;
  }
}
