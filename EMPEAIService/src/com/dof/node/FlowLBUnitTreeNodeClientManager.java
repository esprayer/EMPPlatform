package com.dof.node;

import com.core.xml.*;
import com.dal.service.ModelTreeNodeManager;
import com.efounder.eai.data.*;
import com.efounder.service.tree.*;
import com.efounder.service.tree.node.*;
import java.util.List;
import com.borland.dx.dataset.DataSetData;
import com.borland.dx.dataset.StorageDataSet;
import com.borland.dx.dataset.DataRow;
import com.efounder.db.DBTools;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Iterator;
import com.efounder.pub.util.StringFunction;



public class FlowLBUnitTreeNodeClientManager
    extends ModelTreeNodeManager {
  private final static String FLOW_UNIT_TYPE_NOEXP="0";//不自动装入下级节点
  private final static String FLOW_UNIT_TYPE_EXPALL = "4";//装入下级所有节点
  protected final String FLOW_UNIT_FIELDS = "FLOW_BH,UNIT_BH,FLOW_DATE,FLOW_WC,FLOW_WCR,FLOW_FC,FLOW_FCR,FLOW_FLAG0,FLOW_FLAGR0,FLOW_FLAG1,FLOW_FLAGR1,FLOW_FLAG2,FLOW_FLAGR2,FLOW_FLAG3,FLOW_FLAGR3,FLOW_FLAG4,FLOW_FLAGR4,FLOW_FLAG5,FLOW_FLAGR5,FLOW_FLAG6,FLOW_FLAGR6,FLOW_FLAG7,FLOW_FLAGR7,FLOW_FLAG8,FLOW_FLAGR8,START_DATE,END_DATE,F_STAT,FLOW_USESET";
  protected final String FLOW_RESR_FIELDS = "NODE_FLAG0,NODE_FLAGR0,NODE_FLAGDATE0,NODE_FLAGTEXT0,NODE_FLAG1,NODE_FLAGR1,NODE_FLAGDATE1,NODE_FLAGTEXT1,NODE_FLAG2,NODE_FLAGR2,NODE_FLAGDATE2,NODE_FLAGTEXT2,NODE_FLAG3,NODE_FLAGR3,NODE_FLAGDATE3,NODE_FLAGTEXT3,NODE_FLAG4,NODE_FLAGR4,NODE_FLAGDATE4,NODE_FLAGTEXT4,NODE_FLAG5,NODE_FLAGR5,NODE_FLAGDATE5,NODE_FLAGTEXT5,NODE_FLAG6,NODE_FLAGR6,NODE_FLAGDATE6,NODE_FLAGTEXT6,NODE_FLAG7,NODE_FLAGR7,NODE_FLAGDATE7,NODE_FLAGTEXT7,NODE_FLAG8,NODE_FLAGR8,NODE_FLAGDATE8,NODE_FLAGTEXT8";
  public FlowLBUnitTreeNodeClientManager() {
  }

  /**
   *
   * @param treeManager TreeManager
   * @param paramObject JParamObject
   * @param parentModelTreeNode ModelTreeNode
   * @param treeNodeStubObject TreeNodeStubObject
   * @return StubObject
   * @todo Implement this com.efounder.service.tree.TreeNodeManager method
   */
  protected StubObject convertNodeDataObject(TreeManager treeManager,
                                             JParamObject paramObject,
                                             ModelTreeNode parentModelTreeNode,
                                             TreeNodeStubObject
                                             treeNodeStubObject) {
    return null;

  }

  /**
   *
   * @param treeManager TreeManager
   * @param paramObject JParamObject
   * @param parentModelTreeNode ModelTreeNode
   * @param treeNodeStubObject TreeNodeStubObject
   * @return StubObject
   * @todo Implement this com.efounder.service.tree.TreeNodeManager method
   */
  protected StubObject convertSourceObject(TreeManager treeManager,
                                           JParamObject paramObject,
                                           ModelTreeNode parentModelTreeNode,
                                           TreeNodeStubObject
                                           treeNodeStubObject) {
    StubObject stubObject = new StubObject();//只装入后台需要的参数
    if(parentModelTreeNode instanceof FlowLBUnitTreeNode) return null;

    //paramObject.SetValueByParamName("FLOW_BH","DEMO");
    //paramObject.SetValueByParamName("FLOW_DATE","2007");
    //paramObject.SetValueByParamName("NODE_ID","BKYSSH");
    StubObject dsStub = treeNodeStubObject.getDsStub();
    String where = dsStub.getString("WHERE","");
    String flowBh = dsStub.getString("FLOW_BH","");
    String flowDate = dsStub.getString("FLOW_DATE","");
    String lbzd_bh = "";
    if(parentModelTreeNode.getTreeNodeDataObject() != null) lbzd_bh = parentModelTreeNode.getTreeNodeDataObject().getValue("DWZD_BH","").toString();
    if("".equals(flowBh))
      flowBh = paramObject.GetValueByParamName("FLOW_BH","");
    if("".equals(flowDate))
      flowDate = paramObject.GetValueByParamName("FLOW_DATE","");
    //add by luody 2008-8-3
//   String dateType = paramObject.getDateType();
//   if("2".equals(dateType) && flowDate.length()<5){//如果日期类型为月份，并且日期长度小5，则取环境变量日期
//     flowDate = paramObject.getBIZSDate();//系统的当前年月
//   }
   //end add

    String isQx = dsStub.getString("ISQX","Y");//是否权限过滤，默认为需要权限过滤
    if(isQx.trim().equals("0") || isQx.trim().toUpperCase().equals("N")) isQx = "N";
    else if(isQx.trim().equals("1") || isQx.trim().toUpperCase().equals("Y")) isQx = "Y";
    String userName = paramObject.GetValueByEnvName("UserName");
    String whereStr = replaceWhereForValue(paramObject,where);
    String node_id = paramObject.GetValueByParamName("NODE_ID","");//得到节点ID
    String node_flag=paramObject.GetValueByParamName("NODE_FLAG","");//得到节点ID
    stubObject.setString("NODE_FLAG",node_flag);

//    String unitType = paramObject.getBIZType();
    stubObject.setString("WHERE",whereStr);
    stubObject.setString("userName",userName);
    stubObject.setString("FLOW_BH",flowBh);
    stubObject.setString("LBZD_BH",lbzd_bh);
    stubObject.setString("FLOW_DATE",flowDate);
    stubObject.setString("NODE_ID",node_id);
//    stubObject.setString("UNIT_TYPE",unitType);
//    stubObject.setString("DATE_TYPE",dateType);
    stubObject.setString("isQx",isQx);
    return stubObject;

  }

  /**
   *
   * @param treeManager TreeManager
   * @param nodeData Object
   * @param parentModelTreeNode ModelTreeNode
   * @param treeNodeStubObject boolean
   * @return ModelTreeNode[]
   * @throws Exception
   * @todo Implement this com.efounder.service.tree.TreeNodeManager method
   */
  protected ModelTreeNode[] createNodeFromData(TreeManager treeManager,
                                               Object nodeData,
                                               ModelTreeNode
                                               parentModelTreeNode,
                                               TreeNodeStubObject
                                               treeNodeStubObject) throws
      Exception {
    if(nodeData == null) return null;
    List ffieldList = getListFromStr(FLOW_UNIT_FIELDS,",");
    List rfieldList = getListFromStr(FLOW_RESR_FIELDS,",");
    Object[] retDatas = (Object[])nodeData;
    StubObject flowStub = (StubObject)retDatas[0];
    String node_id = flowStub.getString("MY_NODE_ID","");
    String unitType = flowStub.getString("MY_UNIT_TYPE","1");
    StubObject dctMetaStub = (StubObject)flowStub.getObject("MY_DCTMETA",null);
    String bmbmStru = dctMetaStub.getString("DCT_BMSTRU","4444");
    List flowUnits = (List)retDatas[1];
    Map flowUnitMap = (Map)retDatas[2];
    Map flowResMap = (Map)retDatas[3];
    StubObject spaceStub = fillSpaceFlowUnit(ffieldList,flowUnitMap);
    StubObject spaceResStub = fillSpaceFlowRes(rfieldList,flowResMap,node_id,unitType);
    Map dwmap = new HashMap();
    DataSetData  dataSetData = (DataSetData)retDatas[4];
    StubObject  flowStatStub = (StubObject)retDatas[5];
    StorageDataSet sds = new StorageDataSet();
    dataSetData.loadDataSet(sds);
    if (sds.getRowCount() > 0) {
      sds.first();
      do {
        TreeDataStub tdStub = new TreeDataStub();
        DataRow dr = new DataRow(sds);
        sds.getDataRow(dr);
//        DBTools.DataRowToStubObject(dr, tdStub);
        if (tdStub != null) {
          tdStub.setStubID(tdStub.getObject("DWZD_BH","").toString());
          String curId = tdStub.getObject("DWZD_BH","").toString();
          String curJS = tdStub.getObject("DWZD_JS","0").toString();
          int icurJS = Integer.parseInt(curJS);
          if(icurJS>=2){

              int curstart = StringFunction.GetStructLength(bmbmStru,
                  icurJS - 1);
              int curlen = StringFunction.GetStructLength(bmbmStru, icurJS);
              curId = curId.substring(curstart, curlen);

          }
          tdStub.setCaption(curId+" "+tdStub.getObject("DWZD_MC","").toString());
          Object flowObj = flowUnitMap.get(tdStub.getStubID());
          Object flowResObj = flowResMap.get(tdStub.getStubID());
          if(node_id != null && !"".equals(node_id)){
            //flowObj = flowUnitMap.get(tdStub.getObject("LBZD_BH","").toString());
            flowResObj = flowResMap.get(tdStub.getObject("DWZD_BH","").toString());
          }
          StubObject flowUnitStub = spaceStub;
          StubObject flowResStub = spaceResStub;
          if(flowObj != null) flowUnitStub = (StubObject)flowObj;
          if(flowResObj != null) flowResStub = (StubObject)flowResObj;
          //tdStub.setObject("SYS_FLOW_RESR",flowResStub);
         for(int i=0;i<ffieldList.size();i++){
            String field = ffieldList.get(i).toString();
            String value = "";
            value = tdStub.getObject(field, "").toString();
            if(value == null || value.equals("")) tdStub.setObject(field,flowUnitStub.getObject(field,""));
         }

          FlowLBUnitTreeNode flowNode = new FlowLBUnitTreeNode();
          flowNode.setTreeNodeDataObject(tdStub);
          flowNode.setTreeNodeStubObject(treeNodeStubObject);
          if(node_id != null && !"".equals(node_id.trim())){
            flowNode.setObject("SYS_FLOW_RESR", flowResStub);
            flowNode.setObject("SYS_FLOW_STATUS", flowStatStub);
          }
          dwmap.put(flowNode.getTreeNodeID(),flowNode);
        }
      }
      while (sds.next());
    }
    List rootNodes = packNode(dwmap,flowUnits,flowStub.getString("FLOW_UNIT_TYPE","0").trim());
    FlowLBUnitTreeNode[] flowLBUnitTreeNodes = new FlowLBUnitTreeNode[rootNodes.size()];
    for(int i = 0;i<rootNodes.size();i++)
     flowLBUnitTreeNodes[i] = (FlowLBUnitTreeNode)rootNodes.get(i);
   return flowLBUnitTreeNodes;

  }
  //循环获得每个节点的上级节点
   private List packNode(Map nodeMap,List flowUnits,String flowUnitType) throws Exception{
     String parentId = "";
      List nrootNodes = new ArrayList();
     if (flowUnits.size() == 0)
       return nrootNodes;
     String nodeId = "";
     //if(flowUnitType.equals(FLOW_UNIT_TYPE_EXPALL)){
       for(int i=0;i<flowUnits.size();i++){
         StubObject flowUnitStub = (StubObject) flowUnits.get(i);
         nodeId = flowUnitStub.getString("UNIT_BH", "");
         Object flowobj = nodeMap.get(nodeId);
         if (flowobj != null) {
           FlowLBUnitTreeNode flowNode = (FlowLBUnitTreeNode)flowobj;
           parentId = flowNode.getParentId();
           Object parentObj = nodeMap.get(parentId);
           if (parentObj != null) {
             FlowLBUnitTreeNode parentFlowNode = (FlowLBUnitTreeNode) parentObj;
             parentFlowNode.add(flowNode);
             nodeMap.put(parentId, parentFlowNode);
           }
           else{
             //如果是一级节点，则截编码
             TreeDataStub tdStub = (TreeDataStub)flowNode.getTreeNodeDataObject();
             String curId = tdStub.getObject("DWZD_BH","").toString();
             tdStub.setCaption(curId+" "+tdStub.getObject("DWZD_MC","").toString());
             flowNode.setTreeNodeDataObject(tdStub);
             nrootNodes.add(flowNode);
           }
         }
       }
      //}
      /*else{//如果不是展开所有下级，则列示出所有流程单位
        if (flowUnits.size() == 0)
          return nrootNodes;
        for (int i = 0; i < flowUnits.size(); i++) {
          StubObject flowUnitStub = (StubObject) flowUnits.get(i);
          nodeId = flowUnitStub.getString("UNIT_BH", "");
          Object pobj = nodeMap.get(nodeId);
          if (pobj != null) {
            FlowLBUnitTreeNode flowTreeNode = (FlowLBUnitTreeNode) pobj;
            nrootNodes.add(flowTreeNode);
          }
        }
      }*/
     return nrootNodes;
   }
  private StubObject fillSpaceFlowUnit(List fields,Map flowUnitMap){
      StubObject sourceStub = null;
      if(!flowUnitMap.isEmpty()){
        Iterator it = flowUnitMap.keySet().iterator();
        String key = (String)it.next();
        if(key != null)
          sourceStub = (StubObject)flowUnitMap.get(key);

      }
     StubObject stub = new StubObject();
     for(int i=0;i<fields.size();i++){
       String field = fields.get(i).toString();
       String value = "0";
       //if(sourceStub != null) value = sourceStub.getObject(field,"").toString();
       if(field.equals("UNIT_BH")) value = "";
       if(field.startsWith("FLOW_FLAG")) value="0";
       stub.setString(field,value);
     }
     return stub;
  }
  private StubObject fillSpaceFlowRes(List fields,Map flowResMap,String node_id,String unitType){
      StubObject sourceStub = null;
      if(!flowResMap.isEmpty()){
        Iterator it = flowResMap.keySet().iterator();
        String key = (String)it.next();
        if(key != null)
          sourceStub = (StubObject)flowResMap.get(key);

      }
     StubObject stub = new StubObject();
     stub.setString("UNIT_BH","");
     stub.setString("UNIT_TYPE",unitType);
     stub.setString("FLOW_DATE","");
     stub.setString("NODE_ID",node_id);
     stub.setString("NODE_WC","0");
     stub.setString("NODE_WCR","");
     stub.setString("NODE_WCDATE","");
     stub.setString("NODE_WCTEXT","");
     stub.setString("NODE_FC","0");
     stub.setString("NODE_FCDATE","");
     stub.setString("NODE_FCR","");
     stub.setString("NODE_FCTEXT","");
     for(int i=0;i<fields.size();i++){
       String field = fields.get(i).toString();
       String value = "0";
       //if(sourceStub != null) value = sourceStub.getObject(field,"").toString();
       if(field.indexOf("_FLAGDATE")>0 || field.indexOf("_FLAGTEXT")>0) value="";
       stub.setString(field,value);
     }
     return stub;
  }

  private String replaceWhereForValue(JParamObject po,String where){
      String repWhere = where;
      while(repWhere.indexOf("@")>=0){
        int signStart = repWhere.indexOf("@");
        int signEnd = repWhere.indexOf("@",signStart+1);
        String paramName = repWhere.substring(signStart+1,signEnd);
        if(paramName != null && !"".equals(paramName.trim())){
          String paramValue = po.GetValueByParamName(paramName,"");
          if(paramValue != null && !"".equals(paramValue.trim())){
             where = where.replaceAll("@"+paramName+"@",paramValue);
          }
        }
        repWhere = repWhere.substring(signEnd+1);
      }

      return where;
  }
  private  List getListFromStr(String strSjlx,String sign){
          String[] sjlxArray = strSjlx.split(sign);
          List retList = new ArrayList();
          for(int i = 0; i < sjlxArray.length; i++){
              retList.add(sjlxArray[i]);
          }
          return retList;
    }
}
