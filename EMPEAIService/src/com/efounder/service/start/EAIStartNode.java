package com.efounder.service.start;

import com.efounder.node.*;
import com.efounder.eai.ide.*;

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
public class EAIStartNode extends JNodeStub {
  /**
   *
   */
  protected EAIStartNode() {
    caption = "系统主页";
    nodeIcon = ExplorerIcons.getExplorerIcon("sunone9/domain.gif");
  }
  protected static EAIStartNode startNode = null;
  /**
   *
   * @return EAIStartNode
   */
  public static EAIStartNode getInstance() {
    if ( startNode == null )
      startNode = new EAIStartNode();
    return startNode;
  }
  /**
   *
   * @return boolean
   */
  public boolean allowOpenDocument() {return false;}
  /**
   *
   * @return boolean
   */
  public boolean allowOpenHelp() {return false;}
}
