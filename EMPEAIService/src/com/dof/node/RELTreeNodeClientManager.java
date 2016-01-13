package com.dof.node;

import java.math.*;
import java.util.*;

import com.borland.dx.dataset.*;
import com.core.xml.*;
import com.dal.service.ModelTreeNodeManager;
import com.efounder.db.*;
import com.efounder.eai.data.*;
import com.efounder.pub.util.*;
import com.efounder.service.tree.*;
import com.efounder.service.tree.node.*;
import com.tree.util.TreeTools;

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
public class RELTreeNodeClientManager extends ModelTreeNodeManager {
  public RELTreeNodeClientManager() {
  }

  public boolean isTypeLeaf(TreeManager treeManager, ModelTreeNode treeNode) {
    if(treeNode == null) return true;
    RELTreeNode relTreeNode = (RELTreeNode)treeNode;
    String isMxValue = "1";
     try{
        TreeDataStub nodeDataStub = (TreeDataStub)relTreeNode.getTreeNodeDataObject();
       StubObject dctMetaStub = relTreeNode.getDctMetaStub();
       isMxValue = nodeDataStub.getObject(dctMetaStub.getObject(RELTreeNode.MX_FIELD, "").toString().trim(),"1").toString();
     }
     catch(Exception e){
       System.out.println("警告：得到是否明细节点时错误！");
       e.printStackTrace();
     }
     if("1".equals(isMxValue.trim()) || "Y".equals(isMxValue.trim().toUpperCase()))
         return true;
     return false;
  }
  //本程序假设如果是装入父节点，则要一次装入，否则无法区分再次打开时，是要装入父节点，还是子节点
  protected StubObject convertSourceObject(TreeManager treeManager,
                                           JParamObject paramObject,
                                           ModelTreeNode parentModelTreeNode,
                                           TreeNodeStubObject
                                           treeNodeStubObject) {

    StubObject sourceStub = new StubObject();
    StubObject dsStub = TreeTools.keyValueToStub(treeNodeStubObject.getString("NODE_DS",""),";","=");
    String selObjId = dsStub.getString("OBJID","");
    String selParentObjId = dsStub.getString("PNODEOBJID","");
    StubObject attrStub = TreeTools.keyValueToStub(treeNodeStubObject.getString("NODE_ATTR",""),";","=");
    sourceStub.setString("PNODEOBJID",dsStub.getString("PNODEOBJID",""));//上级节点对象ID
    sourceStub.setString("OBJID",dsStub.getString("OBJID",""));//子节点对象ID
    sourceStub.setString("RELTABLE",dsStub.getString("RELTABLE",""));//关联表名称
    String prelFields = dsStub.getString("PRELFIELDS","");
    String subRelFields = dsStub.getString("SUBRELFIELDS","");
    Map subrelValueMap = TreeTools.keyValueToMap(subRelFields,",",":");
    sourceStub.setObject("SUBRELVALUE",subrelValueMap);
    //String subKeyField = dsStub.getString("SUBKEYFIELD","");
    //subKeyField = subKeyField.trim().toUpperCase();
    //sourceStub.setString("SUBKEYFIELD",subKeyField);//子表在关联表中的字段名
    sourceStub.setString("EXPANDALL",attrStub.getString("EXPANDALL","N"));
    sourceStub.setString("SHOWROOT",attrStub.getString("SHOWROOT","N"));
    sourceStub.setString("QXKZ",attrStub.getString("QXKZ","N"));//权限控制
    sourceStub.setString("QXBH",dsStub.getString("QXBH",""));//权限编号
    sourceStub.setString("JS",dsStub.getString("JS","-1"));//起始级数
    sourceStub.setString("MAXJS",dsStub.getString("MAXJS","-1"));//最大级数
    sourceStub.setString("WHERE",dsStub.getString("WHERE",""));//where条件
    sourceStub.setString("ORDER",dsStub.getString("ORDER",""));//order by

    boolean isSameRel = false;//是否打开自己
    TreeDataStub parentUserStub = null;
    StubObject parentTreeNodeStub = null;
    StubObject parentDctMetaStub = null;
    StubObject parentDsStub  = null;
    boolean parentIsFinal = false;
    if(parentModelTreeNode != null){
      parentUserStub = (TreeDataStub) parentModelTreeNode.getTreeNodeDataObject();
      parentTreeNodeStub = (StubObject) parentModelTreeNode.getTreeNodeStubObject();
      parentDsStub = TreeTools.keyValueToStub(parentTreeNodeStub.getString("NODE_DS", ""), ";", "=");
      if(parentModelTreeNode instanceof DCTTreeNode){
          parentIsFinal = ((DCTTreeNode)parentModelTreeNode).isFinal();
      }
      if(parentIsFinal){
        Map keyValueMap = new HashMap();
        String parentKeyValue = parentUserStub.getString("NODE_BH", "");
        if (parentKeyValue != null){
          Map relValueMap = TreeTools.keyValueToMap(prelFields,",",":");
          if(relValueMap != null){
            Iterator it = relValueMap.keySet().iterator();
            if(it.hasNext()){
               String key = it.next().toString();
               String value = relValueMap.get(key).toString();
               keyValueMap.put(value,parentKeyValue);
            }
          }
       }
        sourceStub.setObject("PRELVALUE", keyValueMap);
        sourceStub.setBoolean("ISSAMEREL", isSameRel);
        return sourceStub;
      }

    }
    String parentObjId = "";
    String parentSubObjId = "";
    if(parentDsStub != null){
      if (parentModelTreeNode instanceof DCTTreeNode) {
        parentObjId = parentDsStub.getString("OBJID", "");
      }
      else {
        parentObjId = parentDsStub.getString("PNODEOBJID","");
        parentSubObjId = parentDsStub.getString("OBJID","");
      }
      if(parentModelTreeNode instanceof DCTTreeNode){
        parentDctMetaStub = ((DCTTreeNode)parentModelTreeNode).getDctMetaStub();
      }
      if(parentModelTreeNode instanceof RELTreeNode){
        parentDctMetaStub = ((RELTreeNode)parentModelTreeNode).getDctMetaStub();
      }
    }
    if(parentObjId.equals(selParentObjId) && parentSubObjId.equals(selObjId)){
      isSameRel = true;
      int JS = -1;
      String sJS = parentUserStub.getObject(parentDctMetaStub.getObject(RELTreeNode.JS_FIELD,"").toString().trim(),"-1").toString();
      if(sJS != null && !"".equals(sJS.trim()))
         JS = Integer.parseInt(sJS);
      if(JS != -1) JS = JS+1;
      sourceStub.setString("JS", ""+JS);
      sourceStub.setString("PARENTID",parentUserStub.getObject(parentDctMetaStub.getObject(RELTreeNode.BM_FIELD,"").toString().trim(),"#ROOT").toString());
    }
    if(!isSameRel){
     TreeNodeDataObject tndo = null;
     if(parentModelTreeNode != null)
         tndo = parentModelTreeNode.getTreeNodeDataObject();
       if(!"".equals(prelFields.trim()) && tndo != null){
       Map relValueMap = TreeTools.keyValueToMap(prelFields,",",":");
       Map prelValueMap = new HashMap();
       if(relValueMap != null){
         Iterator it = relValueMap.keySet().iterator();
         String keyField = "";
         String valueField = "";
          while(it.hasNext()){
            keyField = it.next().toString();
            valueField = relValueMap.get(keyField).toString();//此处反了一下，KEY是父节点字段，这时已经换成了值，后面放的是关联表的字段名
            Object value = getValueOfField(parentModelTreeNode,keyField);
            if(value != null)
             prelValueMap.put(valueField,value);
         }
         sourceStub.setObject("PRELVALUE",prelValueMap);
       }
     }
    }
    sourceStub.setBoolean("ISSAMEREL",isSameRel);

    return sourceStub;
  }

  protected ModelTreeNode[] createNodeFromData(TreeManager treeManager,
                                               Object nodeData,
                                               ModelTreeNode parentModelTreeNode,
                                               TreeNodeStubObject
                                               treeNodeStubObject) throws
      Exception {
    StubObject dctMetaStub = null;
   DataSetData  dataSetData = null;
   StubObject attr = TreeTools.keyValueToStub(treeNodeStubObject.getString("NODE_ATTR",""),";","=");
   String expandAll = attr.getString("EXPANDALL","N");//是否一次展开所有节点
   expandAll = expandAll.trim().toUpperCase();
   if("1".equals(expandAll)) expandAll = "Y";
   if(nodeData == null) throw new Exception("没有得到字典树信息！");
   Object[] datas = (Object[])nodeData;
   if(nodeData != null){
     if(datas[0] == null) throw new Exception("没有得到字典定义信息！");
     if(datas[1] == null) throw new Exception("没有得到字典树数据！");
     dctMetaStub = (StubObject)datas[0];
     dataSetData = (DataSetData)datas[1];
   }



   boolean isTree = false;
   if (!"".equals(dctMetaStub.getObject(RELTreeNode.JS_FIELD, "").toString().trim())) { //如果没有指定级次字段和父节点字段，则认为是非树形字典
     if (!"".equals(dctMetaStub.getObject(RELTreeNode.PARENT_FIELD, "").toString().trim()))
       isTree = true;
   }
   StorageDataSet sds = new StorageDataSet();
   dataSetData.loadDataSet(sds);
   List midNodes = new ArrayList();
   Map nodeMap = new HashMap();
   List rootNodes = new ArrayList();
    if (sds.getRowCount() > 0) {
          sds.first();
          do {
            TreeDataStub dstub = new TreeDataStub();
            DataRow dr = new DataRow(sds);
            sds.getDataRow(dr);
            DBTools.DataRowToStubObject(dr,dstub);
            if (dstub != null) {
             String nodeBM = sds.getString(dctMetaStub.getObject(RELTreeNode.BM_FIELD, "").toString().trim());
              String bm = nodeBM;
              if(isTree){
                String bmStru = dctMetaStub.getObject(RELTreeNode.BM_STRU,
                    "44444").toString().trim();
                BigDecimal nodeJS = sds.getBigDecimal( (dctMetaStub.getObject(
                    RELTreeNode.JS_FIELD, "").toString().trim()));
                if (nodeJS == null)
                  nodeJS = new BigDecimal("" + 1);
                if (nodeJS.intValue() >= 1 && bmStru != null &&
                    bmStru.length() > nodeJS.intValue()) {
                  int length = StringFunction.GetStructLength(bmStru,
                      nodeJS.intValue() - 1);
                  if(nodeJS.intValue() == 1) length = 0;
                  int end = nodeBM.length();
                  if(nodeJS.intValue()== bmStru.length())
                      end = nodeBM.length()-length;
                    else
                      end = Integer.parseInt(bmStru.substring(nodeJS.intValue()-1,nodeJS.intValue()));
                   bm = nodeBM.substring(length, length + end);

                }
              }
              dstub.setStubID(nodeBM);
              dstub.setCaption(bm+" "+sds.getString(dctMetaStub.getObject(RELTreeNode.MC_FIELD,"").toString().trim()));
            }
            RELTreeNode relTreeNode = new RELTreeNode();
            relTreeNode.setTreeNodeDataObject(dstub);
            relTreeNode.setTreeNodeStubObject(treeNodeStubObject);
            relTreeNode.setDctMetaStub(dctMetaStub);
            relTreeNode.setNodeId(dstub.getStubID());
            relTreeNode.setParentId(dstub.getObject(dctMetaStub.getObject(RELTreeNode.PARENT_FIELD, "").toString().trim(), RELTreeNode.ROOTID).toString().trim());
            if ("Y".equals(expandAll) && isTree) {
              midNodes.add(dstub.getStubID());
              nodeMap.put(relTreeNode.getNodeId(), relTreeNode);
            }
            rootNodes.add(relTreeNode);
          } while (sds.next());
           if ("Y".equals(expandAll) && isTree) {
             rootNodes = packNode( nodeMap, midNodes, dctMetaStub,attr,treeNodeStubObject);
           }
   }
   if(rootNodes == null || rootNodes.isEmpty()) return null;
   RELTreeNode[] relTreeNodes = new RELTreeNode[rootNodes.size()];
   for(int i = 0;i<rootNodes.size();i++)
     relTreeNodes[i] = (RELTreeNode)rootNodes.get(i);
   return relTreeNodes;

  }
  //循环获得每个节点的上级节点
   private List packNode(Map nodeMap,List nodeLs,StubObject dctMetaStub,StubObject paramStub,TreeNodeStubObject treeNodeStubObject) throws Exception{
     String rootNodeId = "";
     String nodeId = "";
     String parentId = "";
     String showRoot = paramStub.getString("SHOWROOT","N");//是否显示根节点
     showRoot = showRoot.trim().toUpperCase();
     if("1".equals(showRoot)) showRoot = "Y";
     String JS = paramStub.getString("JS","-1");//起始级数
     List rootIds = new ArrayList();
     for(int i=0;i<nodeLs.size();i++){
         nodeId = (String)nodeLs.get(i);
        RELTreeNode relnode = null;
        Object obj = nodeMap.get(nodeId);
        if(obj == null) continue;
        relnode = (RELTreeNode)obj;
        TreeDataStub dstub = (TreeDataStub)relnode.getTreeNodeDataObject();
        if(!"-1".equals(JS)){
          if(dstub.getString(dctMetaStub.getObject(RELTreeNode.JS_FIELD,""),"").toString().trim().equals(JS)){//如果有起始级，且级数相等，则该节点为根节点
             rootIds.add(dstub.getStubID());
             continue;
           }

        }
        else{
          if ("Y".equals(showRoot)) {
            if (relnode.getParentId().equals("")) { //如果显示根节点，则该节点为根节点
              rootIds.add(relnode.getNodeId());
              continue;
            }
          }
          else {
            if (relnode.getParentId().equals(RELTreeNode.ROOTID)) { //如果显示根节点，则该节点为根节点
              rootIds.add(relnode.getNodeId());
              continue;
            }
          }
        }
        parentId = relnode.getParentId();;
        RELTreeNode parentNode =  null;
        obj = nodeMap.get(parentId);
        if(obj == null) continue;
        parentNode = (RELTreeNode)obj;
        parentNode.add(relnode);
        nodeMap.put(parentId,parentNode);
      }
      List nrootNodes = new ArrayList();
      if(rootIds.size()==0) return nrootNodes;
      for(int i= 0;i<rootIds.size();i++){
        rootNodeId = rootIds.get(i).toString();
        Object pobj = nodeMap.get(rootNodeId);
        if(pobj != null){
          RELTreeNode relTreeNode = (RELTreeNode)pobj;
            nrootNodes.add(relTreeNode);
        }
      }
     return nrootNodes;
   }

  protected StubObject convertNodeDataObject(TreeManager treeManager,
                                             JParamObject paramObject,
                                             ModelTreeNode parentModelTreeNode,
                                             TreeNodeStubObject
                                             treeNodeStubObject) {

    //treeNodeStubObject 只存放了SYS_TREENODE表信息
     StubObject sourceStub = new StubObject();
     StubObject dsStub = treeNodeStubObject.getDsStub();
     String selObjId = dsStub.getString("OBJID","");
     String selParentObjId = dsStub.getString("PNODEOBJID","");
     StubObject attrStub = treeNodeStubObject.getAttrStub();
     sourceStub.setString("PNODEOBJID",dsStub.getString("PNODEOBJID",""));//上级节点对象ID
    sourceStub.setString("OBJID",dsStub.getString("OBJID",""));//子节点对象ID
    sourceStub.setString("RELTABLE",dsStub.getString("RELTABLE",""));//关联表名称
    String prelFields = dsStub.getString("PRELFIELDS","");
    String subRelFields = dsStub.getString("SUBRELFIELDS","");
    Map subrelValueMap = TreeTools.keyValueToMap(subRelFields,",",":");
    sourceStub.setObject("SUBRELVALUE",subrelValueMap);
    sourceStub.setString("EXPANDALL",attrStub.getString("EXPANDALL","N"));
    sourceStub.setString("SHOWROOT",attrStub.getString("SHOWROOT","N"));
    sourceStub.setString("QXKZ",attrStub.getString("QXKZ","N"));//权限控制
    sourceStub.setString("QXBH",dsStub.getString("QXBH",""));//权限编号
    sourceStub.setString("JS",dsStub.getString("JS","-1"));//起始级数
    sourceStub.setString("MAXJS",dsStub.getString("MAXJS","-1"));//最大级数
    sourceStub.setString("WHERE",dsStub.getString("WHERE",""));//where条件
    sourceStub.setString("ORDER",dsStub.getString("ORDER",""));//order by

//    String relFields = treeNodeStubObject.getDsStub().getString("RELFIELDS","");//关联字段值对,如A:B,C:D,表示objId表的字段A要等于当前节点B字段的值
     TreeNodeDataObject tndo = null;
     if(parentModelTreeNode != null)
         tndo = parentModelTreeNode.getTreeNodeDataObject();
       if(!"".equals(prelFields.trim()) && tndo != null){
       Map relValueMap = TreeTools.keyValueToMap(prelFields,",",":");
       Map prelValueMap = new HashMap();
       if(relValueMap != null){
         Iterator it = relValueMap.keySet().iterator();
         String keyField = "";
         String valueField = "";
          while(it.hasNext()){
            keyField = it.next().toString();
            valueField = relValueMap.get(keyField).toString();//此处反了一下，KEY是父节点字段，这时已经换成了值，后面放的是关联表的字段名
            Object value = getValueOfField(parentModelTreeNode,keyField);
            if(value != null)
             prelValueMap.put(valueField,value);
         }
         sourceStub.setObject("PRELVALUE",prelValueMap);

       }

     }


     return sourceStub;


  }

}
