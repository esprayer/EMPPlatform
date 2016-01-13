package com.efounder.dataobject.node;

import com.efounder.node.NodeDataManager;
import com.efounder.dataobject.*;
import com.efounder.dbc.*;
import com.efounder.node.*;
import com.borland.dx.dataset.*;
import com.efounder.eai.*;
import org.openide.*;
import com.efounder.pub.util.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class DCTNodeDataManager extends NodeDataManager {
  /**
   * 数据字典
   */
  protected DCTObject dctObject = null;
  /**
   *
   * @return DCTObject
   */
  public DCTObject getDctObject() {
    return dctObject;
  }
  /**
   * 数据集
   */
  protected DataSet dataSet = null;
  /**
   * 返回数据集
   * @return DataSet
   */
  public DataSet getDataSet() {
    return dataSet;
  }
  /**
   * 获取ServiceKey
   * @param dataSet DataSet
   * @param dataRow DataRow
   * @return String
   */
  protected String getServiceKey(DataSet dataSet,DataRow dataRow) {
    return this.getClass().getName();
  }
  /**
   *
   * @param dataSet DataSet
   * @param dataRow DataRow
   * @return Class
   */
  protected Class getNodeDataStubClass(String ServiceKey) {
    return DCTNodeDataStub.class;
  }
  /**
   *
   * @param nodeDataStubClass Class
   * @return DCTNodeDataStub
   */
  protected DCTNodeDataStub getDctNodeDataStub(Class nodeDataStubClass) {
    try {
      return (DCTNodeDataStub)nodeDataStubClass.newInstance();
    } catch ( Exception e ) {
      e.printStackTrace();
    }
    return null;
  }
  /**
   *
   * @param Key String
   * @param nds NodeDataStub
   * @param actionObject Context
   */
  public Context insertNodeData(String Key,NodeDataStub nds,Context actionObject) {
    // 强转成DCTNodeDataStub
    DCTNodeDataStub dctNodeDataStub = (DCTNodeDataStub)nds;
    // 数据字典对象
    dctNodeDataStub.setDctObject(dctObject);
    // 数据集
    dctNodeDataStub.setDataSet(dataSet);

    /**
     * 调用父类的insertNodeData
     */
    super.insertNodeData(Key,nds,actionObject);
    /**
     * 处理
     */
    return actionObject;
  }
  protected void builderDataTree(int OldJS,JNodeStub parentNode,DataSet dataSet,DCTObject dctObject,String DCT_BM) {
    String BM,MC;int JS=0;DCTNode childNode = null,lastNode = null;
    String BMStruct = dctObject.getSYS_DICTS().getString("DCT_BMSTRU").trim();
    DCTNodeDataStub dctNodeDataStub = null;String ServiceKey = null;
    DataRow dataRow = null;Class dctNodeDataStubClass = null;
    Context context = new Context(parentNode.getExplorerView(),parentNode);
    dataSet.enableDataSetEvents(false);
    try {
      do {
        BM = dataSet.getString(BMCol);
        // 如果DCT_BM不等于null,并且
        if ( DCT_BM != null && !BM.startsWith(DCT_BM) ) continue;
        MC = dataSet.getString(MCCol);
        if (JSCol != null && !"".equals(JSCol.trim())) {
          JS = dataSet.getBigDecimal(JSCol).intValue();
          // 不处理JS为0的数据
          if ( JS == 0 ) continue;
        }
        dataRow = new DataRow(dataSet);
        dataSet.getDataRow(dataRow);
        // 获取ServiceKey
        ServiceKey = this.getServiceKey(dataSet, dataRow);
        // 获取节点数据类
        dctNodeDataStubClass = this.getNodeDataStubClass(ServiceKey);
        // 获取节点数据对象
        dctNodeDataStub = this.getDctNodeDataStub(dctNodeDataStubClass);
        // 设置数据集
        dctNodeDataStub.setDataSet(dataSet);
        // 设置数据行
        dctNodeDataStub.setDataRow(dataRow);
        // 设置数据对象
        dctNodeDataStub.setDctObject(dctObject);

        // 将新建的节点设置到DataSet中
        if (OldJS == 0) OldJS = JS;
        if (OldJS == JS) {
          // 如果级数相等
          context.setParentNodeStub(parentNode);
          context = this.insertNodeData(ServiceKey, dctNodeDataStub, context);
        }
        else if (JS > OldJS) {
          int Length = StringFunction.GetStructLength(BMStruct, JS - 1);
          String Parent_CODE = BM.substring(0, Length);
          JNodeStub pn = findParentNode(lastNode, Parent_CODE);
          context.setParentNodeStub(pn);
          context = this.insertNodeData(ServiceKey, dctNodeDataStub, context);
        } else if ( JS < OldJS ) {
          OldJS = JS;
          // 如果级数相等
          context.setParentNodeStub(parentNode);
          context = this.insertNodeData(ServiceKey, dctNodeDataStub, context);
        }
        lastNode = (DCTNode) context.getRetNode();
        if ( lastNode != null ) {
          lastNode.setDctManagerNode( (DCTManagerNode)this.getNodeStub());
          lastNode.setInternalRow(dataSet.getInternalRow());
          dataSet.setObject(dctObject.getDCT_ID() + "_JNodeStub",context.getRetNode());
        }
//        if ( dataSet.isEditing() )
//          dataSet.cancel();
      } while (dataSet.next());
    } catch ( Exception e ) {
      e.printStackTrace();
    } finally {
      dataSet.enableDataSetEvents(true);
    }
  }

  /**
   *
   * @param Key String
   * @param nds NodeDataStub
   * @param actionObject Context
   */
  public void removeNodeData(String Key,NodeDataStub nds,Context actionObject) {
    super.removeNodeData(Key,nds,actionObject);
  }
  /**
   *
   * @param Key String
   * @param nds NodeDataStub
   * @param actionObject Context
   */
  public void changeNodeData(String Key,NodeDataStub nds,Context actionObject) {
    super.changeNodeData(Key,nds,actionObject);
  }
  /**
   *
   */
  public DCTNodeDataManager() {
  }
  /**
   *
   * @throws Exception
   */
  public void doSave() throws Exception {

  }
  protected String BMCol = null;
  protected String MCCol = null;
  protected String JSCol = null;
  /**
   *
   * @param OldJS int
   * @param parentNode JNodeStub
   * @param dataSet DataSet
   * @param dctObject DCTObject
   */
  protected void builderNodeTree(int OldJS,JNodeStub parentNode,DataSet dataSet,DCTObject dctObject) {
    String BM,MC;int JS=0;DCTNode childNode = null,lastNode = null;
    String BMStruct = dctObject.getSYS_DICTS().getString("DCT_BMSTRU").trim();
    DataRow dataRow = null;
    do {
      BM = dataSet.getString(BMCol);MC = dataSet.getString(MCCol);
      if ( JSCol != null && !"".equals(JSCol.trim()) )
        JS = dataSet.getBigDecimal(JSCol).intValue();
      // 新建一个节点
      childNode = getDCTNode();
      childNode.setDctManagerNode((DCTManagerNode)this.getNodeStub());
      // 设置编码，名称
//      childNode.setDCTBH(BM);childNode.setDCTMC(MC);
      // 设置DCTObject
//      childNode.setDCTObject(dctObject);
      // 设置DataRow
      dataRow = new DataRow(dataSet);
      dataSet.getDataRow(dataRow);
      childNode.setDataRow(dataRow);
      // 将新建的节点设置到DataSet中
      dataSet.setObject(dctObject.getDCT_ID()+"_JNodeStub",childNode);
      if ( OldJS == 0 ) OldJS = JS;
      if ( OldJS == JS ) {
        // 如果级数相等
        childNode.setParentNode(parentNode);
        getNodeStub().getExplorerView().registryChildNode(parentNode,childNode);
        lastNode = childNode;
      } else if ( JS > OldJS ) {
        int Length = StringFunction.GetStructLength(BMStruct, JS - 1);
        String Parent_CODE = BM.substring(0, Length);
        JNodeStub pn = findParentNode(lastNode,Parent_CODE);
        childNode.setParentNode(pn);
        getNodeStub().getExplorerView().registryChildNode(pn,childNode);
      }
    } while ( dataSet.next() );
  }
  /**
   *
   * @param dctNode DCTNode
   * @param BH String
   * @return JNodeStub
   */
  protected JNodeStub findParentNode(DCTNode dctNode,String BH) {
    if ( dctNode.getDCTBH().equals(BH) ) {
//    if ( dctNode.getDCTBH().length() == BH.length() ) {
      return (JNodeStub) dctNode;
    } else {
      if ( dctNode.getParent() instanceof DCTNode ) {
        return findParentNode((DCTNode)(dctNode.getParent()),BH);
      } else {
        return (JNodeStub)dctNode.getParent();
      }
    }
  }
  /**
   *
   * @throws Exception
   */
  public void doLoad() throws Exception {
    String DCT_ID  = this.getNodeStub().getNodeStubObject().getString("DCT_ID",null);
    String DCT_BM  = this.getNodeStub().getNodeStubObject().getString("DCT_BM",null);
    String server = this.getNodeStub().getNodeStubObject().getString("server","");
    if ( DCT_ID == null ) return;
    dctObject = DCTObject.getDCTObject(DCT_ID,false,server);
    BMCol = dctObject.getDCTField("DCT_BMCOLID");
    MCCol = dctObject.getDCTField("DCT_MCCOLID");
    JSCol = dctObject.getDCTField("DCT_JSCOLID");
    JNodeStub nodeStub = this.getNodeStub();
    if ( nodeStub instanceof DCTManagerNode ) {
      dataSet = ((DCTManagerNode)nodeStub).getFilterDataSet(dctObject);
    } else {
      dataSet = dctObject.getTabObject().getTAB_DATA();
    }
    if ( dataSet.getRowCount() <= 0 ) return;
    dataSet.first();
    this.builderDataTree(0,getNodeStub(),dataSet,dctObject,DCT_BM);
    this.setModified(false);
  }
  /**
   *
   * @return String
   */
  public String getDCT_ID() {
    return ((DCTManagerNode)getNodeStub()).getDCT_ID();
  }
  /**
   *
   * @param parentNode JNodeStub
   * @param dctNodeClass Class
   * @param dataSet DataSet
   * @param dctObject DCTObject
   * @param dataRow DataRow
   * @return DCTNode
   */
  public static DCTNode createDCTNode(JNodeStub parentNode,Class dctNodeClass,Class dctDataClass,DCTObject dctObject,DataSet dataSet,DataRow dataRow) {
    DCTNode dctNode = null;DCTNodeDataStub dctData = null;
    try {
      if ( dctNodeClass == null )
        dctNode = new DCTNode();
      else {
        dctNode = (DCTNode)dctNodeClass.newInstance();
      }
      if ( dctDataClass == null )
        dctData = new DCTNodeDataStub();
      else
        dctData = (DCTNodeDataStub)dctDataClass.newInstance();
      dctData.setDctObject(dctObject);
      dctData.setDataSet(dataSet);
      dctData.setDataRow(dataRow);
      dctNode.setNodeDataStub(dctData);
      dctNode.setParentNode(parentNode);
      dctNode.setExplorerView(parentNode.getExplorerView());
    } catch ( Exception e ) {e.printStackTrace();}
    return dctNode;
  }
  /**
   *
   * @return DCTNode
   */
  public DCTNode getDCTNode() {
    String sDCTNode = this.getNodeStub().getNodeStubObject().getString("DCTNode",null);
    if ( sDCTNode == null ) {
      return new DCTNode();
    } else {
      try {
        DCTNode dctNode = (DCTNode)this.getClass().forName(sDCTNode).newInstance();
        return dctNode;
      } catch ( Exception e ) {
        ErrorManager.getDefault().notify(0,e);
      }
    }
    return null;
  }
}

//package com.efounder.dataobject.node;
//
//import com.efounder.node.NodeDataManager;
//import com.efounder.dataobject.*;
//import com.efounder.dbc.*;
//import com.efounder.node.*;
//import com.borland.dx.dataset.*;
//import com.efounder.eai.*;
//import org.openide.*;
//import com.efounder.pub.util.*;
//
///**
// * <p>Title: </p>
// * <p>Description: </p>
// * <p>Copyright: Copyright (c) 2004</p>
// * <p>Company: </p>
// * @author not attributable
// * @version 1.0
// */
//
//public class DCTNodeDataManager extends NodeDataManager {
//  /**
//   * 数据字典
//   */
//  protected DCTObject dctObject = null;
//  /**
//   *
//   * @return DCTObject
//   */
//  public DCTObject getDctObject() {
//    return dctObject;
//  }
//  /**
//   * 数据集
//   */
//  protected DataSet dataSet = null;
//  /**
//   * 返回数据集
//   * @return DataSet
//   */
//  public DataSet getDataSet() {
//    return dataSet;
//  }
//  /**
//   * 获取ServiceKey
//   * @param dataSet DataSet
//   * @param dataRow DataRow
//   * @return String
//   */
//  protected String getServiceKey(DataSet dataSet,DataRow dataRow) {
//    return this.getClass().getName();
//  }
//  /**
//   *
//   * @param dataSet DataSet
//   * @param dataRow DataRow
//   * @return Class
//   */
//  protected Class getNodeDataStubClass(String ServiceKey) {
//    return DCTNodeDataStub.class;
//  }
//  /**
//   *
//   * @param nodeDataStubClass Class
//   * @return DCTNodeDataStub
//   */
//  protected DCTNodeDataStub getDctNodeDataStub(Class nodeDataStubClass) {
//    try {
//      return (DCTNodeDataStub)nodeDataStubClass.newInstance();
//    } catch ( Exception e ) {
//      e.printStackTrace();
//    }
//    return null;
//  }
//  /**
//   *
//   * @param Key String
//   * @param nds NodeDataStub
//   * @param actionObject Context
//   */
//  public Context insertNodeData(String Key,NodeDataStub nds,Context actionObject) {
//    // 强转成DCTNodeDataStub
//    DCTNodeDataStub dctNodeDataStub = (DCTNodeDataStub)nds;
//    // 数据字典对象
//    dctNodeDataStub.setDctObject(dctObject);
//    // 数据集
//    dctNodeDataStub.setDataSet(dataSet);
//
//    /**
//     * 调用父类的insertNodeData
//     */
//    super.insertNodeData(Key,nds,actionObject);
//    /**
//     * 处理
//     */
//    return actionObject;
//  }
//  protected void builderDataTree(int OldJS,JNodeStub parentNode,DataSet dataSet,DCTObject dctObject) {
//    String BM,MC;int JS=0;DCTNode childNode = null,lastNode = null;
//    String BMStruct = dctObject.getSYS_DICTS().getString("DCT_BMSTRU").trim();
//    DCTNodeDataStub dctNodeDataStub = null;String ServiceKey = null;
//    DataRow dataRow = null;Class dctNodeDataStubClass = null;
//    Context context = new Context(parentNode.getExplorerView(),parentNode);
//    dataSet.enableDataSetEvents(false);
//    try {
//      do {
//        BM = dataSet.getString(BMCol);
//        MC = dataSet.getString(MCCol);
//        if (JSCol != null && !"".equals(JSCol.trim())) JS = dataSet.
//            getBigDecimal(JSCol).intValue();
//        dataRow = new DataRow(dataSet);
//        dataSet.getDataRow(dataRow);
//        // 获取ServiceKey
//        ServiceKey = this.getServiceKey(dataSet, dataRow);
//        // 获取节点数据类
//        dctNodeDataStubClass = this.getNodeDataStubClass(ServiceKey);
//        // 获取节点数据对象
//        dctNodeDataStub = this.getDctNodeDataStub(dctNodeDataStubClass);
//        // 设置数据集
//        dctNodeDataStub.setDataSet(dataSet);
//        // 设置数据行
//        dctNodeDataStub.setDataRow(dataRow);
//        // 设置数据对象
//        dctNodeDataStub.setDctObject(dctObject);
//
//        // 将新建的节点设置到DataSet中
//        if (OldJS == 0) OldJS = JS;
//        if (OldJS == JS) {
//          // 如果级数相等
//          context.setParentNodeStub(parentNode);
//          context = this.insertNodeData(ServiceKey, dctNodeDataStub, context);
//        }
//        else if (JS > OldJS) {
//          int Length = StringFunction.GetStructLength(BMStruct, JS - 1);
//          String Parent_CODE = BM.substring(0, Length);
//          JNodeStub pn = findParentNode(lastNode, Parent_CODE);
//          context.setParentNodeStub(pn);
//          context = this.insertNodeData(ServiceKey, dctNodeDataStub, context);
//        } else if ( JS < OldJS ) {
//          OldJS = JS;
//          // 如果级数相等
//          context.setParentNodeStub(parentNode);
//          context = this.insertNodeData(ServiceKey, dctNodeDataStub, context);
//        }
//        lastNode = (DCTNode) context.getRetNode();
//        if ( lastNode != null ) {
//          lastNode.setDctManagerNode( (DCTManagerNode)this.getNodeStub());
//          lastNode.setInternalRow(dataSet.getInternalRow());
//          dataSet.setObject(dctObject.getDCT_ID() + "_JNodeStub",context.getRetNode());
//        }
//      }
//      while (dataSet.next());
//    } catch ( Exception e ) {
//      e.printStackTrace();
//    } finally {
//      dataSet.enableDataSetEvents(true);
//    }
//  }
//
//  /**
//   *
//   * @param Key String
//   * @param nds NodeDataStub
//   * @param actionObject Context
//   */
//  public void removeNodeData(String Key,NodeDataStub nds,Context actionObject) {
//    super.removeNodeData(Key,nds,actionObject);
//  }
//  /**
//   *
//   * @param Key String
//   * @param nds NodeDataStub
//   * @param actionObject Context
//   */
//  public void changeNodeData(String Key,NodeDataStub nds,Context actionObject) {
//    super.changeNodeData(Key,nds,actionObject);
//  }
//  /**
//   *
//   */
//  public DCTNodeDataManager() {
//  }
//  /**
//   *
//   * @throws Exception
//   */
//  public void doSave() throws Exception {
//
//  }
//  protected String BMCol = null;
//  protected String MCCol = null;
//  protected String JSCol = null;
//  /**
//   *
//   * @param OldJS int
//   * @param parentNode JNodeStub
//   * @param dataSet DataSet
//   * @param dctObject DCTObject
//   */
//  protected void builderNodeTree(int OldJS,JNodeStub parentNode,DataSet dataSet,DCTObject dctObject) {
//    String BM,MC;int JS=0;DCTNode childNode = null,lastNode = null;
//    String BMStruct = dctObject.getSYS_DICTS().getString("DCT_BMSTRU").trim();
//    DataRow dataRow = null;
//    do {
//      BM = dataSet.getString(BMCol);MC = dataSet.getString(MCCol);
//      if ( JSCol != null && !"".equals(JSCol.trim()) )
//        JS = dataSet.getBigDecimal(JSCol).intValue();
//      // 新建一个节点
//      childNode = getDCTNode();
//      childNode.setDctManagerNode((DCTManagerNode)this.getNodeStub());
//      // 设置编码，名称
////      childNode.setDCTBH(BM);childNode.setDCTMC(MC);
//      // 设置DCTObject
////      childNode.setDCTObject(dctObject);
//      // 设置DataRow
//      dataRow = new DataRow(dataSet);
//      dataSet.getDataRow(dataRow);
//      childNode.setDataRow(dataRow);
//      // 将新建的节点设置到DataSet中
//      dataSet.setObject(dctObject.getDCT_ID()+"_JNodeStub",childNode);
//      if ( OldJS == 0 ) OldJS = JS;
//      if ( OldJS == JS ) {
//        // 如果级数相等
//        childNode.setParentNode(parentNode);
//        getNodeStub().getExplorerView().registryChildNode(parentNode,childNode);
//        lastNode = childNode;
//      } else if ( JS > OldJS ) {
//        int Length = StringFunction.GetStructLength(BMStruct, JS - 1);
//        String Parent_CODE = BM.substring(0, Length);
//        JNodeStub pn = findParentNode(lastNode,Parent_CODE);
//        childNode.setParentNode(pn);
//        getNodeStub().getExplorerView().registryChildNode(pn,childNode);
//      }
//    } while ( dataSet.next() );
//  }
//  /**
//   *
//   * @param dctNode DCTNode
//   * @param BH String
//   * @return JNodeStub
//   */
//  protected JNodeStub findParentNode(DCTNode dctNode,String BH) {
//    if ( dctNode.getDCTBH().equals(BH) ) {
////    if ( dctNode.getDCTBH().length() == BH.length() ) {
//      return (JNodeStub) dctNode;
//    } else {
//      if ( dctNode.getParent() instanceof DCTNode ) {
//        return findParentNode((DCTNode)(dctNode.getParent()),BH);
//      } else {
//        return (JNodeStub)dctNode.getParent();
//      }
//    }
//  }
//  /**
//   *
//   * @throws Exception
//   */
//  public void doLoad() throws Exception {
//    String DCT_ID  = this.getNodeStub().getNodeStubObject().getString("DCT_ID",null);
//    String LEAF_ID = this.getNodeStub().getNodeStubObject().getString("LEAF_ID",null);
//    if ( DCT_ID == null ) return;
//    dctObject = DCTObject.getDCTObject(DCT_ID);
//    BMCol = dctObject.getDCTField("DCT_BMCOLID");
//    MCCol = dctObject.getDCTField("DCT_MCCOLID");
//    JSCol = dctObject.getDCTField("DCT_JSCOLID");
//    JNodeStub nodeStub = this.getNodeStub();
//    if ( nodeStub instanceof DCTManagerNode ) {
//      dataSet = ((DCTManagerNode)nodeStub).getFilterDataSet(dctObject);
//    } else {
//      dataSet = dctObject.getTabObject().getTAB_DATA();
//    }
//    if ( dataSet.getRowCount() <= 0 ) return;
//    dataSet.first();
//    this.builderDataTree(0,getNodeStub(),dataSet,dctObject);
//    this.setModified(false);
//  }
//  /**
//   *
//   * @return String
//   */
//  public String getDCT_ID() {
//    return ((DCTManagerNode)getNodeStub()).getDCT_ID();
//  }
//  /**
//   *
//   * @param parentNode JNodeStub
//   * @param dctNodeClass Class
//   * @param dataSet DataSet
//   * @param dctObject DCTObject
//   * @param dataRow DataRow
//   * @return DCTNode
//   */
//  public static DCTNode createDCTNode(JNodeStub parentNode,Class dctNodeClass,Class dctDataClass,DCTObject dctObject,DataSet dataSet,DataRow dataRow) {
//    DCTNode dctNode = null;DCTNodeDataStub dctData = null;
//    try {
//      if ( dctNodeClass == null )
//        dctNode = new DCTNode();
//      else {
//        dctNode = (DCTNode)dctNodeClass.newInstance();
//      }
//      if ( dctDataClass == null )
//        dctData = new DCTNodeDataStub();
//      else
//        dctData = (DCTNodeDataStub)dctDataClass.newInstance();
//      dctData.setDctObject(dctObject);
//      dctData.setDataSet(dataSet);
//      dctData.setDataRow(dataRow);
//      dctNode.setNodeDataStub(dctData);
//      dctNode.setParentNode(parentNode);
//      dctNode.setExplorerView(parentNode.getExplorerView());
//    } catch ( Exception e ) {e.printStackTrace();}
//    return dctNode;
//  }
//  /**
//   *
//   * @return DCTNode
//   */
//  public DCTNode getDCTNode() {
//    String sDCTNode = this.getNodeStub().getNodeStubObject().getString("DCTNode",null);
//    if ( sDCTNode == null ) {
//      return new DCTNode();
//    } else {
//      try {
//        DCTNode dctNode = (DCTNode)this.getClass().forName(sDCTNode).newInstance();
//        return dctNode;
//      } catch ( Exception e ) {
//        ErrorManager.getDefault().notify(0,e);
//      }
//    }
//    return null;
//  }
//}
