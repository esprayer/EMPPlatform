package com.efounder.service.tree.node;

import com.efounder.node.*;
import com.efounder.service.tree.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.Icon;
import com.efounder.eai.ide.ExplorerIcons;
import com.core.xml.StubObject;
import com.efounder.model.biz.*;
import java.util.List;
import com.efounder.eai.data.JParamObject;
import com.efounder.dbc.swing.tree.DataSetTreeNode;
import com.pub.util.StringUtil;

import java.util.*;

public abstract class ModelTreeNode extends DataSetTreeNode  implements TreeNodeEx,BIZContext {
  /**
   *
   */
  //add by luody
  private Icon leafIcon = null;//明细项目图标
  private Icon openIcon = null;//目录打开图标
  private Icon closeIcon = null;//目录关闭图标

  private Icon defaultLeaf = null;
  private Icon defaultOpen = null;
  private Icon defaultClose = null;
  protected Map dataMap = new HashMap();
  protected String tier;
  //end add
  public ModelTreeNode() {
  }
  //add by luody
  public Icon getDefaultLeaf(){
    return defaultLeaf;
  }
  protected void setDefaultLeaf(Icon icon){
    this.defaultLeaf = icon;
  }
  public Icon getDefaultOpen(){
    return this.defaultOpen;
  }
  protected void setDefaultOpen(Icon icon){
    this.defaultOpen = icon;
  }
  public Icon getDefaultClose(){
    return this.defaultClose;
  }
  protected void setDefaultClose(Icon icon){
    this.defaultClose = icon;
  }
  public Icon getNodeIcon() {
    leafIcon = getAttrIcon("LEAFICON");
    if(leafIcon == null) leafIcon = defaultLeaf;
   return leafIcon;
 }


 public Icon getOpenIcon() {
   openIcon = closeIcon = getAttrIcon("OPENICON");
   if(openIcon == null) openIcon = defaultOpen;
   return openIcon;
 }



 public Icon getClosedIcon() {
   closeIcon = getAttrIcon("CLOSEICON");
   if(closeIcon == null) closeIcon = defaultClose;
   return closeIcon;
 }

 public Icon getAttrIcon(String attrName){
   Icon icon = null;
   if(treeNodeStubObject != null){
       icon = ExplorerIcons.getExplorerIcon(treeNodeStubObject.getAttrStub().getString(attrName,""));
   }
   if(icon == null){
     icon = ExplorerIcons.getExplorerIcon(treeNodeStubObject.getPrivateAttrStub().getString(attrName,""));
   }
   if(icon == null){
     icon = ExplorerIcons.getExplorerIcon(treeNodeStubObject.getString("VTREE_ICON",""));
   }
   return icon;
 }
 public void setObject(Object objectkey,Object object){
       dataMap.put(objectkey,object);
 }
 public Object getObject(Object objKey,Object object){
   Object obj = dataMap.get(objKey);
   if(obj != null) return obj;
   return object;
}

  //end add
  /**
   *
   */
  protected TreeNodeStubObject treeNodeStubObject = null;
  /**
   *
   * @return TreeNodeStubObject
   */
  public TreeNodeStubObject getTreeNodeStubObject() {
    return treeNodeStubObject;
  }
  /**
   *
   * @param treeNodeStubObject TreeNodeStubObject
   */
  public void setTreeNodeStubObject(TreeNodeStubObject treeNodeStubObject) {
    this.treeNodeStubObject = treeNodeStubObject;
  }
  protected TreeNodeDataObject treeNodeDataObject = null;
  /**
   *
   * @return TreeNodeDataObject
   */
  public TreeNodeDataObject getTreeNodeDataObject() {
    return treeNodeDataObject;
  }
  /**
   *
   * @param treeNodeDataObject TreeNodeDataObject
   */
  public void setTreeNodeDataObject(TreeNodeDataObject treeNodeDataObject) {
    this.treeNodeDataObject = treeNodeDataObject;
  }
  /**
   *
   */
  protected TreeStubObject treeStubObject = null;
  /**
   *
   * @return TreeStubObject
   */
  public TreeStubObject getTreeStubObject() {
    return treeStubObject;
  }

  /**
   *
   * @param treeStubObject TreeStubObject
   */
  public void setTreeStubObject(TreeStubObject treeStubObject) {
    this.treeStubObject = treeStubObject;
  }
  /**
   *
   */
  protected TreeManager treeManager = null;
  /**
   *
   * @param treeManger TreeManager
   */
  public void setTreeManager(TreeManager treeManger) {
    this.treeManager = treeManager;
  }
  /**
   *
   * @return TreeManager
   */
  public TreeManager getTreeManager() {
    return treeManager;
  }
  /**
   *
   * @return boolean
   */
  public boolean isTypeLeaf() {
    if ( treeNodeStubObject == null ) return true;
    TreeNodeManager treeNodeManager = TreeNodeManager.getInstance(treeNodeStubObject.getNodeGUID(),null,this.tier,null);
    return treeNodeManager.isTypeLeaf(treeManager,this);
  }
  /**
   *
   * @return String
   */
  public String getBIZUnit() {
    String bizUnit = getTreeNodeStubObject().getAttrStub().getString("BIZUnit",null);
    if ( bizUnit == null ) bizUnit = getTreeNodeStubObject().getPrivateAttrStub().getString("BIZUnit",null);
    if ( bizUnit == null ) {
      String BIZUnitCol = getTreeNodeStubObject().getAttrStub().getString("BIZUnitCol",null);
      if ( BIZUnitCol == null ) BIZUnitCol = getTreeNodeStubObject().getPrivateAttrStub().getString("BIZUnitCol",null);
      if ( BIZUnitCol != null ) {
        bizUnit = (String)this.getTreeNodeDataObject().getValue(BIZUnitCol,null);
      }
    }
    if(bizUnit == null)
      bizUnit = getF_AXIS();
    return bizUnit;
  }
  private Map fmap = null;
  private String getF_AXIS(){
    if(fmap == null)
     fmap = getAF_AXISMap(this,new HashMap());
    if(fmap == null) return null;
    if(fmap.get("DWZD_BH") != null) return (String)fmap.get("DWZD_BH");
    else if(fmap.get("LBZD_BH") != null) return (String)fmap.get("LBZD_BH");
    else return (String)fmap.get("BIZUnit");
  }
  public Map getF_AXISMap(){
     Map map = new HashMap();
     if(fmap == null) fmap = getAF_AXISMap(this,map);
     if(fmap == null) return null;
     //删除单位编号和类别编号
     Map nounitMap = new HashMap();
     nounitMap.putAll(fmap);
     nounitMap.remove("DWZD_BH");
     nounitMap.remove("LBZD_BH");
     return nounitMap;
  }

  /**
   *
   * @param includeUnit boolean 是否包含单位及类别
   * @return Map
   */
  private Map getAF_AXISMap(ModelTreeNode modelTreeNode,Map map){
    TreeNodeDataObject dataStub = modelTreeNode.getTreeNodeDataObject();
    if(dataStub == null) return null;
    Map tmap = getModelTreeMap(modelTreeNode);
    incorporateMap(map,tmap);
    if ( modelTreeNode.getParent() != null && modelTreeNode.getParent() instanceof ModelTreeNode) {
      return getAF_AXISMap((ModelTreeNode)modelTreeNode.getParent(),map);
    } else
    return map;
  }
  /**
   * 得到一个节点的变量信息
   * @param modelTreeNode ModelTreeNode
   * @return Map
   */
  private Map getModelTreeMap(ModelTreeNode modelTreeNode){
    Map map = new HashMap();
    TreeNodeDataObject dataStub = modelTreeNode.getTreeNodeDataObject();
    if(dataStub == null) return null;
    TreeNodeStubObject treeNodeStub = modelTreeNode.getTreeNodeStubObject();
    String objid = treeNodeStub.getDsStub().getString("OBJID", null);
    String zdBH = modelTreeNode.getTreeNodeID();
    map.put(objid, zdBH);
    Object obj = dataStub.getValue("F_AXIS",null);
    if(obj == null) return map;
    String f_axis = obj.toString();
    if (f_axis == null || "".equals(f_axis.trim()))
        return map;
    Map nmap = StringUtil.keyValueToMap(f_axis, ",", "=");
    if(nmap != null) map.putAll(nmap);
    return map;
  }
  /**
   * 整合两个map
   * @param sourceMap Map 如果source中已经存在,不把targetMap的键值放入,否则放入sourceMap
   * @param targetMap Map
   */
  private void incorporateMap(Map sourceMap,Map targetMap){
    if(targetMap != null){
      Iterator it = targetMap.keySet().iterator();
      while (it.hasNext()){
        String key = (String) it.next();
        if(key == null) continue;
        Object value = targetMap.get(key);
        if (value == null)
          continue;
        if (sourceMap.get(key) != null)
          continue;
        sourceMap.put(key, value);
      }
    }

   }
  /**
   *
   * @return String
   */
  public String getBIZType() {
    String bizType = this.getTreeNodeStubObject().getAttrStub().getString("BIZType",null);
    if ( bizType == null ) bizType = this.getTreeNodeStubObject().getPrivateAttrStub().getString("BIZType",null);
    if(fmap == null) fmap = getAF_AXISMap(this,new HashMap());
    if(fmap != null){
      if(fmap.get("DWZD_BH") != null) return "1";
      else if(fmap.get("LBZD_BH") != null) return "2";
    }
    return bizType;
  }
  /**
   *
   * @return String
   */
  public String getDateType() {
    String DateType = this.getTreeNodeStubObject().getAttrStub().getString("DateType",null);
    if ( DateType == null ) DateType = this.getTreeNodeStubObject().getPrivateAttrStub().getString("DateType",null);
    return DateType;
  }
  /**
   *
   * @return String
   */
  public String getBIZSDate() {return null;}
  /**
   *
   * @return String
   */
  public String getBIZEDate(){return null;}
  /**
   *
   * @param key Object
   * @param def Object
   * @return Object
   */
  public Object getBIZValue(Object key, Object def) {
    Object DataObjt = this.getTreeNodeStubObject().getAttrStub().getObject(key,def);
    if ( DataObjt == null ) DataObjt = this.getTreeNodeStubObject().getPrivateAttrStub().getObject(key,def);
    if ( DataObjt == null ) DataObjt = this.getTreeNodeDataObject().getValue(key,def);
    return DataObjt;
  }
  /**
   *
   * @param key Object
   * @param value Object
   */
  public void setBIZValue(Object key, Object value) {
  }
  /**
   *
   * @param callObject Object
   * @param context Object
   */
  public void callBack(Object callObject, Object context) {
  }
  /**
   *
   * @param keyList List
   */
  public void enumBIZKey(List keyList) {
  }
  /**
   *
   * @param sourceObject Object
   * @param contextObject Object
   * @param addinObject Object
   */
  public void initBIZContext(Object sourceObject, Object contextObject,
                             Object addinObject) {
  }
  /**
   *
   * @param eventType int
   * @param sourceObject Object
   * @param contextObject Object
   * @param addinObject Object
   */
  public void changeEvent(int eventType, Object sourceObject,
                          Object contextObject, Object addinObject) {
  }
  public JParamObject convertParamObject(Object userObject,JParamObject po) {
    return po;
  }
  public abstract String getTreeNodeID();
  public String getTreeNodeType() {
    return null;
  }
  public void setTier(String tier){
    this.tier = tier;
  }
  public String getTier(){
    return this.tier;
  }

}
