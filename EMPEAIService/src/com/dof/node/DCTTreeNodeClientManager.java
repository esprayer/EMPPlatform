package com.dof.node;

import java.util.*;

import javax.swing.tree.*;

import com.borland.dx.dataset.*;
import com.core.xml.*;
import com.dal.service.ModelTreeNodeManager;
import com.efounder.db.*;
import com.efounder.dbc.swing.tree.*;
import com.efounder.eai.data.*;
import com.efounder.service.tree.*;
import com.efounder.service.tree.node.*;
import com.tree.node.RootTreeNode;
import com.tree.util.TreeTools;

public class DCTTreeNodeClientManager
    extends ModelTreeNodeManager {
  protected int strulen[] = new int[] {
      4, 8, 12, 16, 20, 24, 28};
  Hashtable hash = null;
  public DCTTreeNodeClientManager() {
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
    String isDW = "";
    int isFinal  = Integer.parseInt(treeNodeStubObject.getObject("NODE_ISFINAL","0").toString());
    boolean bFinal = false;
    if (isFinal != 0)
      bFinal = true; //是常量节�\uFFFD
    if(bFinal){//如果常量节点，直接返�\uFFFD
      stubObject.setBoolean("ISFINAL",bFinal);
      stubObject.setString("NODE_BH",treeNodeStubObject.getString("NODE_BH","0"));
      stubObject.setString("NODE_MC",treeNodeStubObject.getString("NODE_MC",""));
      return stubObject;
    }
    stubObject.setBoolean("ISFINAL",false);
    TreeNodeStubObject parentTreeNodeStub = null;
    TreeDataStub pnodeDataStub = null;
    StubObject pdctMetaStub = null;
    if(parentModelTreeNode != null && parentModelTreeNode instanceof DCTTreeNode){
      parentTreeNodeStub = parentModelTreeNode.getTreeNodeStubObject();
      pnodeDataStub = (TreeDataStub)(parentModelTreeNode.getTreeNodeDataObject());
      pdctMetaStub = (StubObject)((DCTTreeNode)parentModelTreeNode).getDctMetaStub();
    }
    boolean isSameDCT = false;//如果是同�\uFFFD个字典，说明是点击上级节点，装入子节�\uFFFD
    if(parentTreeNodeStub != null && parentModelTreeNode instanceof DCTTreeNode){
        if(parentTreeNodeStub.getString("NODE_BH","").equals(treeNodeStubObject.getString("NODE_BH","")))
          isSameDCT = true;
    }

    StubObject ds = TreeTools.keyValueToStub(treeNodeStubObject.getString("NODE_DS",""),";","=");
    StubObject attr = TreeTools.keyValueToStub(treeNodeStubObject.getString("NODE_ATTR",""),";","=");
    String expAll = attr.getString("EXPANDALL","N");
    if(isSameDCT && "Y".equals(expAll)) return null;
    String objId = ds.getString("OBJID","");
    String dctWhere = ds.getString("DCTWHERE","");

    if(objId == null || "".equals(objId.trim())){
      objId = paramObject.getBIZValue("GENER_DCT_ID", "").toString();
      //modified by lchong at 2011-4-19
//      if(!"".equals(dctWhere.trim()))
//        if(paramObject.getBIZValue("DCTWHERE","") != null)
//          dctWhere = paramObject.getBIZValue("DCTWHERE","").toString();

       if("".equals(dctWhere.trim())){
           if(paramObject.getBIZValue("DCTWHERE","") != null)
               dctWhere = paramObject.getBIZValue("DCTWHERE","").toString();
       }


       //modified end

       //如果是从PO中取的objID,则需要把objID设置回treenodestub
       Map map = TreeTools.keyValueToMap(treeNodeStubObject.getString("NODE_DS",""),";","=");
       map.put("OBJID",objId);
       TreeTools.mapToNodeValue(map,treeNodeStubObject,"NODE_DS",";","=");
    }
    //add by luody 20100827
    if(paramObject != null && paramObject.getAttriMap() != null){
      isDW = (String)paramObject.getAttriMap().get("ISDW");
      String sdctWhere = (String)paramObject.getAttriMap().get("DCTWHERE");
      if(sdctWhere != null && !"".equals(sdctWhere.trim())){
        if(dctWhere == null || "".equals(dctWhere.trim()))
           dctWhere = sdctWhere;
         else
           dctWhere += " and "+sdctWhere;
      }
    }
    //end add
    String SJS = ds.getString("DCTJS","-1");
    if("-1".equals(SJS)){
      SJS = paramObject.GetValueByParamName("DCTJS","-1");
    }
    String BH = ds.getString("DCTBH","");
    if("".equals(BH)){
      BH = paramObject.GetValueByParamName("DCTBH","");
    }
//    String UNIT_BH = paramObject.getBIZUnit();
    //add by luody 2010-06-09
//    if(dctWhere.indexOf("@DWZD_BH@") != -1 && !"".equals(UNIT_BH)){
//      dctWhere = dctWhere.replaceAll("@DWZD_BH@", UNIT_BH);
//    }
    if (isDW == null || "".equals(isDW))
     isDW = ds.getString("ISDW","1");//是否分单位，默认为分单位
    stubObject.setString("OBJID",objId);
    stubObject.setString("EXPANDALL",attr.getString("EXPANDALL","N"));
    stubObject.setString("SHOWROOT",attr.getString("SHOWROOT","N"));
    stubObject.setString("QXKZ",attr.getString("QXKZ","N"));//权限控制
    stubObject.setString("QXBH",ds.getString("QXBH",""));//权限编号
    stubObject.setString("JS",SJS);//起始级数
    stubObject.setString("BH",BH);//起始级数
    stubObject.setString("MAXJS",ds.getString("MAXJS","-1"));//�\uFFFD大级�\uFFFD
    if(dctWhere.equals(""))
      stubObject.setString("WHERE",ds.getString("WHERE",""));//where条件
    else
      stubObject.setString("WHERE",dctWhere);//where条件
    stubObject.setString("ORDER",ds.getString("ORDER",""));//order by
//    stubObject.setString("UNIT_BH",UNIT_BH);
    stubObject.setString("ISDW",isDW);
    stubObject.setBoolean("ISSAMEDCT",isSameDCT);


    if(isSameDCT && "N".equals(attr.getString("EXPANDALL","N"))){
      if(pnodeDataStub != null && pdctMetaStub != null){
        int JS = -1;
        String sJS = pnodeDataStub.getObject(pdctMetaStub.getObject(DCTTreeNode.JS_FIELD,"").toString().trim(),"-1").toString();
        if(sJS != null && !"".equals(sJS.trim()))
          JS = Integer.parseInt(sJS);
        if(JS != -1) JS = JS+1;
        stubObject.setString("JS", ""+JS);
        stubObject.setString("PARENTID",pnodeDataStub.getObject(pdctMetaStub.getObject(DCTTreeNode.BM_FIELD,"").toString().trim(),"#ROOT").toString());
      }
    }
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
    hash = new Hashtable();
    StubObject dctMetaStub = null;
    DataSetData  dataSetData = null;
    StubObject attr = TreeTools.keyValueToStub(treeNodeStubObject.getString("NODE_ATTR",""),";","=");
    String expandAll = attr.getString("EXPANDALL","N");//是否�\uFFFD次展�\uFFFD�\uFFFD有节�\uFFFD
    expandAll = expandAll.trim().toUpperCase();
    int isFinal  = Integer.parseInt(treeNodeStubObject.getObject("NODE_ISFINAL","0").toString());
    boolean bFinal = false;
    if (isFinal != 0)
      bFinal = true; //是常量节�\uFFFD
    if(bFinal){//如果常量节点，直接返�\uFFFD
      DCTTreeNode dctTreeNode = new DCTTreeNode();
      TreeDataStub dstub = (TreeDataStub)nodeData;
      dctTreeNode.setTreeNodeDataObject(dstub);
      dctTreeNode.setTreeNodeStubObject(treeNodeStubObject);
      dctTreeNode.setIsFinal(true);
      dctTreeNode.setDctMc(treeNodeStubObject.getString("VTREE_MC",""));
      ModelTreeNode[] modelNode = new ModelTreeNode[1];
      modelNode[0] = dctTreeNode;
      return  modelNode;
    }

    if(nodeData == null) throw new Exception("没有得到字典树信息！");
    Object[] datas = (Object[])nodeData;
    if(nodeData != null){
      if(datas[0] == null) throw new Exception("没有得到字典定义信息�\uFFFD");
      if(datas[1] == null) throw new Exception("没有得到字典树数据！");
      dctMetaStub = (StubObject)datas[0];
      dataSetData = (DataSetData)datas[1];
    }
    setStru(dctMetaStub.getObject(DCTTreeNode.BM_STRU,
                    "").toString().trim());
    if("1".equals(expandAll)) expandAll = "Y";

    boolean isTree = false;
    if (!"".equals(dctMetaStub.getObject(DCTTreeNode.JS_FIELD, "").toString().trim())) { //如果没有指定级次字段和父节点字段，则认为是非树形字典
      if (!"".equals(dctMetaStub.getObject(DCTTreeNode.MC_FIELD, "").toString().trim()))
        isTree = true;
    }
    StorageDataSet sds = new StorageDataSet();
    dataSetData.loadDataSet(sds);
    List midNodes = new ArrayList();
    Map nodeMap = new HashMap();
    List rootNodes = new ArrayList();
    //add by luody 2008-4-12
    ModelTreeNode root = null;
    root = new DCTTreeNode();


    fillRootBh(root,dctMetaStub,sds,treeNodeStubObject,isTree);
    if(parentModelTreeNode instanceof RootTreeNode) {
       TreeDataStub userStub = null;
       userStub = (TreeDataStub)parentModelTreeNode.getTreeNodeDataObject();
       userStub.setCaption(root.getDctMc());
       parentModelTreeNode.setTreeNodeDataObject(userStub);
    }
    if(sds.getRowCount()>0){
      initTree(root,sds,dctMetaStub,treeNodeStubObject,isTree);
    }
    //ModelTreeNode[] dctTreeNodes = new DCTTreeNode[1];
    ModelTreeNode[] dctTreeNodes = new ModelTreeNode[root.getChildCount()];
    Enumeration enumer = root.children();
    int i = 0;
    while(enumer.hasMoreElements()){
      ModelTreeNode modelTreeNode = (ModelTreeNode)enumer.nextElement();
      dctTreeNodes[i] = modelTreeNode;
      i = i+1;
    }
    //dctTreeNodes[] = root;
    return dctTreeNodes;
  }
  protected void initTree(TreeNode root,DataSet dataSet,StubObject dctMetaStub,TreeNodeStubObject
                                               treeNodeStubObject,boolean isTree) {
      try {
        hash.clear();
        hash.put("B_#ROOT", root);
        hash.put("O_#ROOT", root);
        //add by luody
        if(root instanceof DCTTreeNode)
        hash.put("B_"+((ModelTreeNode)root).getDctBH(),root);
       //end add

       if (dataSet.getRowCount() > 0) {
          dataSet.first();
          while (true) {
            TreeDataStub dstub = new TreeDataStub();
             DataRow dr = new DataRow(dataSet);
             dataSet.getDataRow(dr);
            try {
              DBTools.DataRowToStubObject(dr, dstub);
            }
            catch (Exception ex) {
              continue;
            }
            setCurrentTreeNode(root,dataSet,dstub,dctMetaStub,treeNodeStubObject,isTree);
            if (!dataSet.next())
              break;
          }
        }
      }
      finally {
      }
      dataSet.first();
  }
  protected void setCurrentTreeNode(TreeNode root,DataSet dataSet,TreeDataStub dstub,StubObject dctStub,TreeNodeStubObject
                                               treeNodeStubObject,boolean isTree){
      String bh, mc, js, mx;
      ModelTreeNode DOS, PDOS;
      String jscol=dctStub.getObject(DCTTreeNode.JS_FIELD,"F_JS").toString();
      String bmcol =dctStub.getObject(DCTTreeNode.BM_FIELD,"ZD_BH").toString();
      String mccol = dctStub.getObject(DCTTreeNode.MC_FIELD,"ZD_MC").toString();
      String mxcol = dctStub.getObject(DCTTreeNode.MX_FIELD,"ZD_MX").toString();
      String parentcol = dctStub.getObject(DCTTreeNode.PARENT_FIELD,"F_PARENT").toString();
      bh = dataSet.getString(bmcol);
      mc = dataSet.getString(mccol);
      DOS = new DCTTreeNode();
      DOS.setDctBH(bh);
      DOS.setDctMc(mc);
      dstub.setStubID(bh);
      DOS.setTreeNodeDataObject(dstub);
      DOS.setTreeNodeStubObject(treeNodeStubObject);
      if(DOS instanceof DCTTreeNode){
        ((DCTTreeNode)DOS).setDctMetaStub(dctStub);
        ((DCTTreeNode)DOS).setNodeId(dstub.getStubID());
      }

      if(isTree){
          if (dataSet.getColumn(jscol).getDataType() ==
              com.borland.dx.dataset.Variant.INT)
              js = String.valueOf(dataSet.getInt(jscol));
          else if (dataSet.getColumn(jscol).getDataType() ==
                   com.borland.dx.dataset.Variant.LONG)
              js = String.valueOf(dataSet.getInt(jscol));
          else if (dataSet.getColumn(jscol).getDataType() ==
                   com.borland.dx.dataset.Variant.BIGDECIMAL)
              js = dataSet.getBigDecimal(jscol).toString();
          else
              js = dataSet.getString(jscol);
          if (!js.equals("0")) {
              mx = dataSet.getString(mxcol);
              DOS.setDctJs(js);
              DOS.setDctMx(mx);
              DOS.setInternalRow(dataSet.getInternalRow());

              StubObject so=new StubObject();
              DataRow dr=new DataRow(dataSet);
              dataSet.getDataRow(dr);
            try {
                DBTools.DataRowToStubObject(dr, so);
            } catch (Exception ex2) {
                ex2.printStackTrace();
            }
              DOS.setUserObject(so);
              parseBH(DOS);
              putTreeNode(DOS.getDctBH(), DOS);
              //modify by luody
              //PDOS = (DataSetTreeNode) hash.get("B_" + DOS.getParentBh());
              PDOS = findParentNode(DOS.getParentBH(),DOS.getIntJs()-1);//add by luody

              if (PDOS != null) {
                  DOS.setParentBH(PDOS.getDctBH());//add by luody
                  //dataSet.setString(parentcol,PDOS.getBH());//add by luody
                  PDOS.add(DOS);
              }
              else {//add by luody
                  DOS.setParentBH(((DataSetTreeNode)root).getDctBH());
                  DOS.setCurBH(DOS.getDctBH());
                  //dataSet.setString(parentcol,((DataSetTreeNode)root).getBH());//add by luody
                  ((DataSetTreeNode)root).add(DOS);
                 }
          }
      }else
          ((DCTTreeNode)root).add(DOS);
  }
  //add by luody
     protected ModelTreeNode findParentNode(String BH,int curJS){
         ModelTreeNode DST = null;
         DST = (ModelTreeNode)hash.get("B_"+BH);
         if(DST != null) return DST;
         for(int i=curJS;i>0;i--){
            String pBH = BH.substring(0, strulen[i - 1]);
            DST = (ModelTreeNode)hash.get("B_"+pBH);
            if(DST != null) return DST;
         }
         return null;
     }
   protected void parseBH(ModelTreeNode node) {
     int js = node.getIntJs();
     String BH = node.getDctBH();
     if (js <= 1) {
       node.setParentBH("#ROOT");
       node.setCurBH(BH);
     }
     else {
       node.setCurBH(BH.substring(strulen[js - 2], strulen[js - 1]));
       node.setParentBH(BH.substring(0, strulen[js - 2]));
     }

  }
  protected ModelTreeNode getTreeNode(String bh){
      return (ModelTreeNode)hash.get("B_" + bh);
  }

  protected  void putTreeNode(String bh,ModelTreeNode node){
      if(node==null)
          hash.remove("B_"+bh);
      else
          hash.put("B_"+bh,node);
  }
  protected void fillRootBh(ModelTreeNode rootNode,StubObject dctStub,DataSet dataSet,TreeNodeStubObject
                                               treeNodeStubObject,boolean isTree){
       dataSet.first();
       DataRow dr=new DataRow(dataSet);
       dataSet.getDataRow(dr);
       int js = 0;
       if(isTree){
         Object obj = dr.getBigDecimal(dctStub.getObject(DCTTreeNode.JS_FIELD,"F_JS").toString());
         if(obj == null) obj = "0";
         js = Integer.parseInt(obj.toString());
       }
       rootNode.setDctBH("#ROOT");
       rootNode.setDctJs("0");
       rootNode.setDctMc(dctStub.getObject("DCT_MC","").toString());


  }
  private void setStru(String stru) {
     if (stru == null || stru.length() <= 0) {
       return;
     }
     int Max = stru.trim().length();
     strulen = new int[Max];
     for (int i = 0; i < Max; i++) {
       String temp = stru.substring(i, i + 1);
       if (i != 0) {
         strulen[i] = strulen[i - 1] + Integer.parseInt(temp);
       }
       else {
         strulen[i] = Integer.parseInt(temp);
       }
     }

 }

  /**
   *
   * @param treeManager TreeManager
   * @param treeNode ModelTreeNode
   * @return boolean
   * @todo Implement this com.efounder.service.tree.TreeNodeManager method
   */
  public boolean isTypeLeaf(TreeManager treeManager, ModelTreeNode treeNode) {
     if(treeNode == null) return true;
     String isMxValue = "1";
     try{
       DCTTreeNode dctTreeNode = (DCTTreeNode)treeNode;
       if(dctTreeNode.isFinal()) return true;//如果是常节点，肯定是明细节点
       TreeDataStub nodeDataStub = (TreeDataStub)dctTreeNode.getTreeNodeDataObject();
       StubObject dctMetaStub = dctTreeNode.getDctMetaStub();
       isMxValue = nodeDataStub.getObject(dctMetaStub.getObject(DCTTreeNode.MX_FIELD, "").toString().trim(),"1").toString();
     }
     catch(Exception e){
       System.out.println("警告：得到是否明细节点时错误�\uFFFD");
       e.printStackTrace();
     }
     if("1".equals(isMxValue.trim()) || "Y".equals(isMxValue.trim().toUpperCase()))
         return true;
     return false;
  }




   protected StubObject convertNodeDataObject(TreeManager treeManager,
                                             JParamObject paramObject,
                                             ModelTreeNode parentModelTreeNode,
                                             TreeNodeStubObject
                                             treeNodeStubObject) {
     //treeNodeStubObject 只存放了SYS_TREENODE表信�\uFFFD
    StubObject sourceStub = new StubObject();
    sourceStub.setString("OBJID",treeNodeStubObject.getDsStub().getString("OBJID",""));
    sourceStub.setString("FIELDS",treeNodeStubObject.getDsStub().getString("FIELDS","*"));
    sourceStub.setString("EXPANDALL",treeNodeStubObject.getAttrStub().getString("EXPANDALL","N"));
    sourceStub.setString("SHOWROOT",treeNodeStubObject.getAttrStub().getString("SHOWROOT","N"));
    sourceStub.setString("QXKZ",treeNodeStubObject.getAttrStub().getString("QXKZ","N"));//权限控制
    sourceStub.setString("QXBH",treeNodeStubObject.getDsStub().getString("QXBH",""));//权限编号
    sourceStub.setString("JS",treeNodeStubObject.getDsStub().getString("JS","-1"));//起始级数
    sourceStub.setString("MAXJS",treeNodeStubObject.getDsStub().getString("MAXJS","-1"));//�\uFFFD大级�\uFFFD
    sourceStub.setString("WHERE",treeNodeStubObject.getDsStub().getString("WHERE",""));//where条件
    sourceStub.setString("ORDER",treeNodeStubObject.getDsStub().getString("ORDER",""));//order by
    String relFields = treeNodeStubObject.getDsStub().getString("RELFIELDS","");//关联字段值对,如A:B,C:D,表示objId表的字段A要等于当前节点B字段的�\uFFFD\uFFFD
    TreeNodeDataObject tndo = null;
    if(parentModelTreeNode != null)
        tndo = parentModelTreeNode.getTreeNodeDataObject();
    if(!"".equals(relFields.trim()) && tndo != null){
      Map keyValueMap = TreeTools.keyValueToMap(relFields,",",":");
      if(keyValueMap != null){
        Iterator it = keyValueMap.keySet().iterator();
        String keyField = "";
        String valueField = "";
         while(it.hasNext()){
           keyField = it.next().toString();
           valueField = keyValueMap.get(keyField).toString();
           Object value = getValueOfField(parentModelTreeNode,valueField);
           if(value != null)
            keyValueMap.put(keyField,value);
        }
        sourceStub.setObject("KEYVALUE",keyValueMap);
      }
    }
    return sourceStub;
  }



}
