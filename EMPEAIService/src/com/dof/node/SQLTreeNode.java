package com.dof.node;

import com.efounder.service.tree.node.ModelTreeNode;
import com.efounder.service.tree.TreeDataStub;
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
public class SQLTreeNode extends ModelTreeNode{
  public SQLTreeNode() {
  }
  public String toString(){
   return ((TreeDataStub)getTreeNodeDataObject()).getCaption();
  }
  public String getTreeNodeID(){
    return ((TreeDataStub)getTreeNodeDataObject()).getStubID();
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
