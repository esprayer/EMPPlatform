package com.tree.node;

import com.efounder.service.tree.node.*;
import com.efounder.service.tree.TreeDataStub;
import java.util.Map;
import com.efounder.model.biz.BIZContext;
import com.efounder.service.tree.TreeStubObject;

public class RootTreeNode extends ModelTreeNode {
  public RootTreeNode() {
  }
  public String toString(){
    TreeDataStub treeDataStub = (TreeDataStub)getTreeNodeDataObject();
    if(treeDataStub==null) return "";
    return treeDataStub.getCaption();
  }

  public String getTreeNodeID() {
    return "#ROOT#";
  }

  public String getTreeNodeType() {
    return "rootGUID";
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
