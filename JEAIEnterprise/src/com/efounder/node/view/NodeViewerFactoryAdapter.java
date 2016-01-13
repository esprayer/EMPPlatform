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

public abstract class NodeViewerFactoryAdapter implements NodeViewerFactory{
  /**
   *
   */
  protected StubObject viewerFactoryStubObject = null;
  /**
   *
   * @return NodeViewerFactory[]
   */
  public NodeViewerFactory[] getNodeViewerFactorys(Context context,Object p1,Object p2) {
    NodeViewerFactory[] nvs = {this};
    return nvs;
  }
  /**
   *
   * @param context Context
   * @return NodeViewer
   */
  public NodeViewer createNodeViewer(Context context) {
    return createNodeViewer(context,null,null);
  }
  /**
   *
   * @param context Context
   * @param p1 Object
   * @return NodeViewer
   */
  public NodeViewer createNodeViewer(Context context,Object p1) {
    return createNodeViewer(context,p1,null);
  }
  /**
   *
   * @param context Context
   * @param p1 Object
   * @param p2 Object
   * @return NodeViewer
   */
  public abstract NodeViewer createNodeViewer(Context context,Object p1,Object p2);
  /**
   *
   * @param node Context
   * @param p1 Object
   * @param p2 Object
   * @return int
   */
  public int getDisplayNodeView(Context node,Object p1,Object p2) {
    return canDisplayNode(node,p1,p2)?1:0;
//    return res?1:0;
  }
  /**
   *
   * @param context Context
   * @param p1 Object
   * @param p2 Object
   * @return boolean
   */
  public abstract boolean canDisplayNode(Context context,Object p1,Object p2);
  /**
   * 可以创建一个视图组
   * @param context Context
   * @param p1 Object
   * @param p2 Object
   * @return NodeViewer[]
   */
  public NodeViewer[] createNodeViewers(Context context,Object p1,Object p2) {
    NodeViewer nv = createNodeViewer(context,p1,p2);
    NodeViewer[] nvs = {nv};
    return nvs;
  }
  public Object prepareNodeViewer(Context node,Object p1,Object p2) {
    return null;
  }
  /**
   *
   */
  public NodeViewerFactoryAdapter() {
  }
  /**
   *
   * @return StubObject
   */
  public StubObject getStubObject() {
    return viewerFactoryStubObject;
  }
  /**
   *
   * @return String
   */
  public String getID() {
    return viewerFactoryStubObject.getString("id",null);
  }
  /**
   *
   * @return String
   */
  public String getName() {
    return viewerFactoryStubObject.toString();
  }
  /**
   *
   * @return String
   */
  public String getIcon() {
    return viewerFactoryStubObject.getString("icon",null);
  }
  /**
   *
   * @param SO StubObject
   */
  public void setStubObject(StubObject SO) {
    viewerFactoryStubObject = SO;
  }
  /**
   *
   * @param key String
   */
  public void   setSecurityKey(String key) {
    viewerFactoryStubObject.setObject("GNBH",key);
  }
  /**
   *
   * @return String
   */
  public String getSecurityKey() {
    return viewerFactoryStubObject.getString("GNBH",null);
  }
}
