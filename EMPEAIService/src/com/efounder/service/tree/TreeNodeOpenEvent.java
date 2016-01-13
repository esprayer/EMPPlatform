package com.efounder.service.tree;

import com.efounder.service.tree.node.*;
import javax.swing.JTree;

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
public interface TreeNodeOpenEvent {
  public Object openTreeNode(JTree tree,ModelTreeNode treeNode);
}
