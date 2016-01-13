package com.efounder.node.view;

import com.efounder.node.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class NodeDocumentViewFactory extends NodeViewerFactoryAdapter {
  public NodeDocumentViewFactory() {
  }
  public NodeViewer createNodeViewer(Context context, Object p1, Object p2) {
    return new NodeDocumentView();
  }
  public boolean canDisplayNode(Context node, Object p1, Object p2) {
    return false;//node.getNode().allowOpenDocument(); //false;//
  }

}
