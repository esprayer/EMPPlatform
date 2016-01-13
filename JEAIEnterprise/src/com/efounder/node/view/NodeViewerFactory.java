package com.efounder.node.view;

import com.efounder.node.*;
import com.core.xml.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public interface NodeViewerFactory {
  // Methods
  public NodeViewerFactory[] getNodeViewerFactorys(Context context,Object p1,Object p2);
  public NodeViewer createNodeViewer(Context context);
  public NodeViewer createNodeViewer(Context context,Object p1);
  public NodeViewer createNodeViewer(Context context,Object p1,Object p2);
  public NodeViewer[] createNodeViewers(Context context,Object p1,Object p2);
  public boolean canDisplayNode(Context node,Object p1,Object p2);
  public Object prepareNodeViewer(Context node,Object p1,Object p2);
  public int getDisplayNodeView(Context node,Object p1,Object p2);
  public StubObject getStubObject();
  public void setStubObject(StubObject SO);
  public void   setSecurityKey(String key);
  public String getSecurityKey();
  public String getID();
  public String getName();
  public String getIcon();
}
