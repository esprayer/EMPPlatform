package com.dal.service;

import java.sql.*;
import java.util.*;

import com.efounder.db.*;
import com.efounder.eai.data.*;
import com.efounder.service.tree.*;
import com.core.xml.StubObject;
import com.efounder.sql.JConnection;

public class ModelTreeLoader extends TreeLoader {
  public ModelTreeLoader() {
  }

  /**
   *
   * @param treeStubObject TreeStubObject
   * @param userObject Object
   * @throws Exception
   * @todo Implement this com.efounder.service.tree.TreeManager method
   */
  //建议不要把每级的都取出来，只取根节点的下一级节点，以提高效率
  protected TreeNodeStubObject loadTreeNodeStubObject(TreeStubObject treeStubObject,
                                        JParamObject paramObject,Object userObject) throws Exception {
    if(treeStubObject == null || userObject == null ) return null;

    TreeNodeStubObject rNodeStub = null;
    String treeTableName = "SYS_VERITY_TREE";
    String nodeTableName = "SYS_TREENODE";
    Map nodeMap = new HashMap();
    List nodeList = new ArrayList();
    Map treeNodeMap = new HashMap();//存放所有的sys_treeNode 表信息//对象内存放StubObject
    try {
      //先获得所有的sys_TreeNode信息，放入hashMap
      treeTableName = DBTools.getDBAObject(paramObject, treeTableName);
      nodeTableName = DBTools.getDBAObject(paramObject, nodeTableName);
      Statement stmt = (Statement) userObject;
      String nodeSql = "select * from "+nodeTableName;
      ResultSet nrs = stmt.executeQuery(nodeSql);
      while(nrs.next()){
        TreeNodeStubObject nstub = TreeNodeStubObject.getInstance();
        DBTools.ResultSetToStubObject(nrs,nstub);
        nstub.setStubID(nrs.getString("NODE_BH"));//此ID中存放node_BH
        treeNodeMap.put(nstub.getStubID(),nstub);
        nstub.copyAttrib();
      }
      nrs.close();

      String sql = " select a.*,b.NODE_MC,b.NODE_NAME,b.NODE_DS,b.NODE_ATTR,b.NODE_SUBJECT,b.NODE_DES,b.NODE_GUID,b.NODE_ISFINAL ";
      sql += " from "+treeTableName +" a,"+nodeTableName+" b where a.NODE_BH=b.NODE_BH  and a.TREE_BH='"+treeStubObject.getString("TREE_BH","")+"'";
       //增加权限过滤
      /*JConnection con = JConnection.getInstance(paramObject);
      StubObject granStub = getBSGRAN(con, paramObject, "SYS_VERITY_TREE");
      if (granStub.getBoolean("isCheck", false) && "BManagerMenu".equals(treeStubObject.getString("TREE_BH",""))) {
        paramObject.SetValueByParamName("pszgbh",
                                        paramObject.GetValueByEnvName(
            "UserName"));
        paramObject.SetValueByParamName("psqxbh",
                                        granStub.getString("F_QXBH", ""));
        paramObject.SetValueByParamName("pibzw", "1");
        String checkSql = DBOSecurityObject.CheckDataLimit3(con, paramObject);
        String bhField = granStub.getString("F_BHZD", "");
        checkSql = checkSql.replaceAll(bhField, "a." + bhField);
        sql += " and " + checkSql;
      }con.close();
     */
      sql += " order by a.VTREE_BH,a.VTREE_JS";

      ResultSet rs = stmt.executeQuery(sql);

      while (rs.next()) {
        TreeNodeStubObject nodeStub = TreeNodeStubObject.getInstance();
        DBTools.ResultSetToStubObject(rs,nodeStub);
        if(nodeStub != null){
           if ("1".equals(nodeStub.getObject("VTREE_ISROOT", "0").toString()) ||
              "Y".equals(nodeStub.getObject("VTREE_ISROOT", "0").toString())) { //得到根节点
            rNodeStub = nodeStub;
            if("".equals(nodeStub.getString("NODE_GUID", "").trim()))
               nodeStub.getString("NODE_GUID", "nodeGUID");//根节点调用固定死的ROOTNODEMANAGER
          }
          String node_ds_bh = nodeStub.getString("NODE_DS_BH","");
          if(!"".equals(node_ds_bh)){
            nodeStub.setDsList(getDsFromStr(node_ds_bh,treeNodeMap,";"));
          }
          nodeMap.put(nodeStub.getString("VTREE_BH", ""), nodeStub);
          nodeList.add(nodeStub.getString("VTREE_BH", ""));
          nodeStub.copyAttrib();
        }
      }
      rs.close();
    }
    catch (SQLException se) {
      System.out.println("得到树字典信息时出错：" + se.getMessage());
      throw new Exception(se);
    }
    if(rNodeStub==null){
    	rNodeStub = TreeNodeStubObject.getInstance();
    	rNodeStub.setString("VTREE_BH", "#ROOT");
    	rNodeStub.setString("NODE_BH", "#nodeMENU");
    	rNodeStub.setString("TREE_BH", treeStubObject.getString("TREE_BH",""));
    	nodeMap.put("#ROOT", rNodeStub);
    	nodeMap.put("", rNodeStub);
    }
    	
     packTreeNode(nodeMap,nodeList,rNodeStub);

     return rNodeStub;
  }

  /**
   *
   * @param treeid String
   * @param userObject Object
   * @return TreeStubObject
   * @throws Exception
   * @todo Implement this com.efounder.service.tree.TreeManager method
   */
  protected TreeStubObject loadTreeStubObject(String treeid, JParamObject paramObject,Object userObject) throws
      Exception {
    if(userObject == null || treeid == null) throw new Exception();
    TreeStubObject treeStub = null;
    String tableName = "SYS_TREE";
    try{
       tableName= DBTools.getDBAObject(paramObject,tableName);
       Statement stmt = (Statement)userObject;
       ResultSet rs = stmt.executeQuery("select * from "+tableName +" where TREE_BH='"+treeid+"'");
       if(rs.next()){
          treeStub = TreeStubObject.getInstance();
          DBTools.ResultSetToStubObject(rs,treeStub);
          rs.close();
         }
    }
    catch(SQLException se){
      System.out.println("得到树字典信息时出错："+se.getMessage());
      throw new Exception(se);
    }
    return treeStub;
  }
  //把给定的节点ID串，转换成TREENODE对象列表
  private List getDsFromStr(String ds_bh,Map nodesMap,String distance){
    List dsList = new ArrayList();
    if(distance == null || "".equals(distance)) distance = ";";
    StringTokenizer st = new StringTokenizer(ds_bh,distance);
    String nodeBh = "";
    Object obj = null;
    while(st.hasMoreTokens()){
       nodeBh = st.nextToken();
       if(nodeBh != null){
          obj = nodesMap.get(nodeBh);
          if(obj != null){
            TreeNodeStubObject tns = (TreeNodeStubObject)obj;
            dsList.add(tns);
          }
       }
     }
   return dsList;
  }
//循环获得每个节点的上级节点
  private void packTreeNode(Map nodeMap,List nodeLs,TreeNodeStubObject rootNode) throws Exception{
    String rootNodeId = "";
    String nodeId = "";
    String parentId = "";
     for(int i=0;i<nodeLs.size();i++){
        nodeId = (String)nodeLs.get(i);
       TreeNodeStubObject nodeStub = null;
       Object obj = nodeMap.get(nodeId);
       if(obj == null) continue;
       nodeStub = (TreeNodeStubObject)obj;
       if(nodeStub.getObject("VTREE_ISROOT","0").toString().equals("1")) {
         rootNodeId = nodeId;
         continue;
       }
       parentId = nodeStub.getString("VTREE_PARENT","");
       TreeNodeStubObject parentStub =  null;
       obj = nodeMap.get(parentId);
       if(obj == null) continue;
          parentStub = (TreeNodeStubObject)obj;
       parentStub.addChild(nodeStub);
       nodeMap.put(parentId,parentStub);
     }
     Object pobj = nodeMap.get(rootNodeId);
     if(pobj == null) throw new Exception("不能得到根节点");
     rootNode = (TreeNodeStubObject)pobj;

  }
  protected StubObject getBSGRAN(JConnection conn,JParamObject PO,String tableName){
        StubObject stub = new StubObject();
        Statement stmt = conn.createStatement();
        String sql = "select * from "+DBTools.getDBAObject(PO, "BSGRAN")+" where F_TABN='"+tableName+"'";
        try{
          ResultSet rs = stmt.executeQuery(sql);
          if(rs.next()){
            DBTools.ResultSetToStubObject(rs,stub);
            stub.setBoolean("isCheck",true);
            rs.close();
            stmt.close();
            return stub;
          }
        }
        catch(SQLException se){
          se.printStackTrace();
        }
        catch(Exception e){
          e.printStackTrace();
        }
        stub.setBoolean("isCheck",false);
        return stub;
  }
}

