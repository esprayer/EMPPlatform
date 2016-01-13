package com.efounder.service.start;

import com.efounder.node.*;
import com.efounder.node.view.*;

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
public class EAIStartViewFactory extends NodeViewerFactoryAdapter {
  public EAIStartViewFactory() {
  }

  /**
   * canDisplayNode
   *
   * @param node Context
   * @param p1 Object
   * @param p2 Object
   * @return boolean
   * @todo Implement this com.efounder.node.view.NodeViewerFactory method
   */
  public boolean canDisplayNode(Context context, Object p1, Object p2) {
    return ( context.getNode() instanceof EAIStartNode );
  }

  /**
   * createNodeViewer
   *
   * @param context Context
   * @param p1 Object
   * @param p2 Object
   * @return NodeViewer
   * @todo Implement this com.efounder.node.view.NodeViewerFactory method
   */
  public NodeViewer createNodeViewer(Context context, Object p1, Object p2) {
    NodeViewer nodeView = new EAIStartView();
    return nodeView;
  }
}
