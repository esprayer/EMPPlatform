package com.dof.node;

import com.efounder.service.tree.node.*;
import java.util.Map;
import com.efounder.model.biz.BIZContext;

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
public class CommandTreeNode extends ModelTreeNode {
  /**
   *
   */
  public CommandTreeNode() {
  }
  /**
   *
   * @return String
   */
  public String getTreeNodeID() {
    return "";
  }

  public String getPrepareAttString() {
    return null;
  }

  public void setCallBackValue(Object key, Object value) {
  }

  public Map getCallBackMap() {
    return null;
  }

  public void addBIZContext(BIZContext bizContext) {
  }
}
