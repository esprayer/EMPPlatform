package com.dof.node;

import com.efounder.service.tree.node.*;
import com.core.xml.StubObject;
import com.efounder.eai.ide.ExplorerIcons;
import com.efounder.service.tree.TreeDataStub;
import java.util.Map;
import com.efounder.model.biz.BIZContext;

public class DCTTreeNode extends ModelTreeNode {
  public final static String OBJ_ID = "OBJ_ID";//字典ID
  public final static String BM_FIELD = "DCT_BMCOLID";//编码字段
  public final static String MC_FIELD = "DCT_MCCOLID";//显示名称字段
  public final static String MX_FIELD = "DCT_MXCOLID";//明细字段
  public final static String JS_FIELD = "DCT_JSCOLID";//级数字段
  public final static String PARENT_FIELD = "DCT_PTCOLID";//父节点字字段
  public final static String BM_STRU = "DCT_BMSTRU";//编码结构字段

  public final static String ROOTID = "#ROOT";//根节点标识
  private StubObject dctMetaStub = null;//存放字典表结构信息
  private String nodeId = "";
  private String parentId = "";
  private boolean isFinal = false;//是否为常量节点
  public DCTTreeNode() {
    //setDefaultLeaf(ExplorerIcons.getExplorerIcon("idea/nodes/beaSmall.png"));
    //setDefaultOpen(ExplorerIcons.getExplorerIcon("idea/nodes/appFolderOpen.png"));
    //setDefaultClose(ExplorerIcons.getExplorerIcon("idea/nodes/appFolderClosed.png"));
  }
  public String getNodeId(){
    return nodeId;
  }
  public void setNodeId(String nodeId){
    this.nodeId = nodeId;
  }
  public String getParentId(){
    return this.parentId;
  }
  public void setParentId(String parentId){
    this.parentId = parentId;
  }

  public StubObject getDctMetaStub(){
    return this.dctMetaStub;
  }
  public void setDctMetaStub(StubObject metaStub){
    this.dctMetaStub = metaStub;
  }
  public boolean isFinal(){
    return this.isFinal;
  }
  public void setIsFinal(boolean isFinal){
    this.isFinal = isFinal;
  }
//  public String toString(){
//    if(isFinal) return treeNodeStubObject.getString("NODE_MC","");
//    return ((TreeDataStub)getTreeNodeDataObject()).getCaption();
// }
 public String getTreeNodeID(){
    return ((TreeDataStub)getTreeNodeDataObject()).getStubID();
 }

 public String getPrepareAttString() {
   return null;
  }

  public void setCallBackValue(Object key, Object value) {
  }
  /*public int getChildCount(){
    if(this.children == null) return 0;
    return this.children.size();
  }*/
  public Map getCallBackMap() {
     return null;
  }

  public void addBIZContext(BIZContext bizContext) {
  }


}
