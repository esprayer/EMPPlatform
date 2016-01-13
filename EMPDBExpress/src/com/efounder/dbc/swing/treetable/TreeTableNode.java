package com.efounder.dbc.swing.treetable;

import com.efounder.dbc.swing.tree.*;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2003</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class TreeTableNode
    extends DataSetTreeNode {
       public TreeTableNode() {
  }
  public String toString() {
      return this.getCurBH();
  }
}
