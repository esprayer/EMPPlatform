package com.efounder.service.start;

import com.efounder.node.*;
import com.efounder.node.view.*;
import com.core.xml.StubObject;

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
public class EAIMenuViewFactory
    extends NodeViewerFactoryAdapter {
  public EAIMenuViewFactory() {
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
    JNodeStub node=context.getNode();
        StubObject stub = node.getNodeStubObject();
    String mr="0";
    if(stub!=null)mr= stub.getString("menuRoot", "0");
    return "1".equals(mr);
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
    return new EAIMenuView();
  }
}
