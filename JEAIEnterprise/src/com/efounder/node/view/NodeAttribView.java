package com.efounder.node.view;

import com.efounder.pfc.window.JChildWindow;
import com.efounder.pfc.window.*;
import com.efounder.node.*;
import javax.swing.Icon;
import com.core.xml.*;
import com.efounder.eai.ide.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class NodeAttribView extends JChildWindow {
  protected NodeAttribViewerFactory nodeAttribViewerFactory = null;
  /**
   *
   * @param navf NodeAttribViewerFactory
   */
  public void setNodeAttribViewerFactory(NodeAttribViewerFactory navf) {
    nodeAttribViewerFactory = navf;
  }
  /**
   *
   * @return NodeAttribViewerFactory
   */
  public NodeAttribViewerFactory getNodeAttribViewerFactory() {
    return nodeAttribViewerFactory;
  }
  /**
   *
   */
  public NodeAttribView() {
  }
  /**
   *
   * @param context Context
   * @param p1 Object
   * @param p2 Object
   * @param nodeWindow IWindow
   * @throws Exception
   */
  public void initNodeViewer(Context context,NodeViewer nodeView,Object p1,Object p2) throws Exception {

  }
  public Icon getIcon() {
    if ( nodeAttribViewerFactory == null ) return super.getIcon();
    StubObject so = nodeAttribViewerFactory.getStubObject();
    String ii = so.getString("icon",null);
    Icon icon = ExplorerIcons.getExplorerIcon(ii);
    return icon;
  }
  public String getTitle() {
    if ( nodeAttribViewerFactory == null ) return super.getTitle();
    StubObject so = nodeAttribViewerFactory.getStubObject();
    return so.getCaption();
  }
}
