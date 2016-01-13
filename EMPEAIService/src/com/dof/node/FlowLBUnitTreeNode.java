package com.dof.node;

import com.efounder.service.tree.node.ModelTreeNode;
import com.efounder.service.tree.TreeDataStub;
import com.core.xml.StubObject;
import java.util.List;
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
public class FlowLBUnitTreeNode extends ModelTreeNode{
  //private StubObject  flowStub = null;
  //private List flowUnits = null;//存放该节点流程下的流程有部门，存放sys_flow_unit_date信息
  public FlowLBUnitTreeNode() {
  }
  //返回单位的上级单位编码
 public String getParentId(){
   Object obj = ((TreeDataStub)getTreeNodeDataObject()).getValue("F_PARENT","");
   if(obj == null) obj = "";
   return obj.toString();
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
