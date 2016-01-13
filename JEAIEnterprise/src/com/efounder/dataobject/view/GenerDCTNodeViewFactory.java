package com.efounder.dataobject.view;

import com.efounder.node.view.*;
import com.efounder.node.*;
import com.efounder.dataobject.node.*;


/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class GenerDCTNodeViewFactory extends NodeViewerFactoryAdapter {
  /**
   *
   */
  public GenerDCTNodeViewFactory() {
  }
  /**
   *
   * @param context Context
   * @param p1 Object
   * @param p2 Object
   * @return NodeViewer
   */
  public NodeViewer createNodeViewer(Context context, Object p1, Object p2) {
    GenerDCTNodeView dctNodeView = new GenerDCTNodeView();
    dctNodeView.initDataStruct(context);
    return dctNodeView;
  }
  /**
   *
   * @param node Context
   * @param p1 Object
   * @param p2 Object
   * @return boolean
   */
  public boolean canDisplayNode(Context node, Object p1, Object p2) {
    return ( node.getNode() instanceof DCTNodeDataRow );
  }

}
