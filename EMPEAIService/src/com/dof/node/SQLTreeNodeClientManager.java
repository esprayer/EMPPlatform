package com.dof.node;

import com.core.xml.StubObject;
import com.dal.service.ModelTreeNodeManager;
import com.efounder.service.tree.TreeManager;
import com.efounder.eai.data.JParamObject;
import com.efounder.service.tree.node.ModelTreeNode;
import com.efounder.service.tree.TreeNodeStubObject;
import java.util.Map;
import java.util.Iterator;
import com.borland.dx.dataset.DataSetData;
import com.borland.dx.dataset.StorageDataSet;
import com.efounder.service.tree.TreeDataStub;
import com.borland.dx.dataset.DataRow;
import com.efounder.db.DBTools;
import java.util.List;
import java.util.ArrayList;
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
public class SQLTreeNodeClientManager extends ModelTreeNodeManager{
  public SQLTreeNodeClientManager() {
  }

  protected StubObject convertSourceObject(TreeManager treeManager,
                                           JParamObject paramObject,
                                           ModelTreeNode parentModelTreeNode,
                                           TreeNodeStubObject
                                           treeNodeStubObject) {
    StubObject stubObject = new StubObject();//只装入后台需要的参数
    //paramObject.SetValueByParamName("FLOW_BH","DEMO");
    //paramObject.SetValueByParamName("FLOW_DATE","2007");
    StubObject dsStub = treeNodeStubObject.getDsStub();
    String sql = paramObject.GetValueByParamName("SQL","");
    if("".equals(sql.trim()))
        sql = dsStub.getString("SQL","");
    String order = paramObject.GetValueByParamName("ORDER","");
    if("".equals(order.trim()))
        order = dsStub.getString("ORDER","");
    String idField = paramObject.GetValueByParamName("IDFIELD","");
    if("".equals(idField.trim()))
        idField = dsStub.getString("IDFIELD","");
    String nameField = paramObject.GetValueByParamName("NAMEFIELD","");
    if("".equals(nameField.trim()))
       nameField = dsStub.getString("NAMEFIELD","");


    boolean isIncludeWhere = false;
    int whereStart = sql.toUpperCase().indexOf("WHERE");
    if(whereStart>0) isIncludeWhere = true;
    if(sql.equals("")) return null;
    String where = "";
    if(whereStart >0)
        where = sql.substring(whereStart);
    String whereStr = replaceWhereForValue(paramObject,where);
    if(whereStr != null && !"".equals(whereStr.trim()))
      sql = sql.substring(0,whereStart)+whereStr;
    stubObject.setString("SQL",sql);
    stubObject.setString("ORDER",order);
    stubObject.setString("IDFIELD",idField);
     stubObject.setString("NAMEFIELD",nameField);
    stubObject.setBoolean("INCLUDEWHERE",isIncludeWhere);
    return stubObject;
  }

  protected ModelTreeNode[] createNodeFromData(TreeManager treeManager,
                                               Object nodeData,
                                               ModelTreeNode parentModelTreeNode,
                                               TreeNodeStubObject
                                               treeNodeStubObject) throws
      Exception {

    if(nodeData == null) return null;
     Object[] retDatas = (Object[])nodeData;
     StubObject paramStub = (StubObject)retDatas[0];
     List nodeList = new ArrayList();
     DataSetData  dataSetData = (DataSetData)retDatas[1];
     String idField = paramStub.getString("IDFIELD","").trim().toUpperCase();
     String nameField = paramStub.getString("NAMEFIELD","").trim().toUpperCase();
     StorageDataSet sds = new StorageDataSet();
     dataSetData.loadDataSet(sds);
     if (sds.getRowCount() > 0) {
       sds.first();
       do {
         TreeDataStub tdStub = new TreeDataStub();
         DataRow dr = new DataRow(sds);
         sds.getDataRow(dr);
         DBTools.DataRowToStubObject(dr, tdStub);
         if (tdStub != null) {
           tdStub.setStubID(tdStub.getObject(idField,"").toString());
           tdStub.setCaption(tdStub.getObject(idField,"").toString()+" "+tdStub.getObject(nameField,"").toString());
           SQLTreeNode sqlTreeNode = new SQLTreeNode();
           sqlTreeNode.setTreeNodeDataObject(tdStub);
           nodeList.add(sqlTreeNode);
         }

       }
       while (sds.next());
     }
     if(nodeList == null || nodeList.isEmpty()) return null;
    SQLTreeNode[] sqlTreeNodes = new SQLTreeNode[nodeList.size()];
    for(int i = 0;i<nodeList.size();i++)
      sqlTreeNodes[i] = (SQLTreeNode)nodeList.get(i);
    return sqlTreeNodes;
  }
  public boolean isTypeLeaf(TreeManager treeManager, ModelTreeNode treeNode) {
      return true;//都是叶子节点
  }
  protected StubObject convertNodeDataObject(TreeManager treeManager,
                                             JParamObject paramObject,
                                             ModelTreeNode parentModelTreeNode,
                                             TreeNodeStubObject
                                             treeNodeStubObject) {
    return null;
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
}
