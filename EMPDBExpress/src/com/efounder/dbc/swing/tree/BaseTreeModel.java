package com.efounder.dbc.swing.tree;

import java.util.*;

import javax.swing.tree.*;

import com.efounder.dbc.swing.context.*;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2006</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public abstract class BaseTreeModel
    extends DefaultTreeModel {
  protected TreeInfoContext context;
  protected Hashtable hash = new Hashtable();

  protected BaseTreeModel(TreeNode root) {
    super(root, false);
  }

  public void CreateTree(TreeInfoContext ct) {
    context = ct;
    hash.clear();
    CreateTree();
  }

  protected abstract void CreateTree();

  public DataSetTreeNode getTreeNode(String bh) {
    return (DataSetTreeNode) hash.get("B_" + bh);
  }

  public void putTreeNode(String bh, DataSetTreeNode node) {
    if (node == null)
      hash.remove("B_" + bh);
    else
      hash.put("B_" + bh, node);
  }

  protected void addChildNode(DataSetTreeNode DOS) {
    DataSetTreeNode PDOS = null;
    PDOS = getTreeNode(DOS.getParentBH());
    if (PDOS == null)
      PDOS = (DataSetTreeNode) root;
    try{
    PDOS.add(DOS);
    }catch(Exception e){
    	System.out.println(PDOS.getDctBH()+ " this="+DOS.getDctBH());
    	e.printStackTrace();
    }
  }
}
