package com.dof.node;

import com.efounder.service.tree.node.ModelTreeNode;
import com.core.xml.StubObject;
import com.efounder.eai.ide.ExplorerIcons;
import com.efounder.service.tree.TreeNodeDataObject;
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
public class RELTreeNode extends ModelTreeNode {

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

  public RELTreeNode() {
    setDefaultLeaf(ExplorerIcons.getExplorerIcon("idea/actions/menu-copy.png"));
    setDefaultOpen(ExplorerIcons.getExplorerIcon("idea/actions/menu-open.png"));
    setDefaultClose(ExplorerIcons.getExplorerIcon("idea/actions/modul.png"));
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
