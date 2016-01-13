package com.efounder.bz.dbform.datamodel.tree;

import com.efounder.builder.meta.dctmodel.DCTMetaData;
import com.efounder.builder.base.data.EFDataSet;
import java.util.HashMap;
import com.efounder.builder.base.data.ESPRowSet;
import com.pub.util.StringFunction;

import java.util.List;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2009</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class TreeUtils {
  /**
   *
   */
  protected TreeUtils() {
  }
  /**
   *
   * @param treeModel DataSetTreeModel
   * @param dctMetaData DCTMetaData
   * @param dataSet EFDataSet
   * @throws Exception
   */
  public static void createTree(DataSetTreeModel treeModel,
                                     DCTMetaData dctMetaData,EFDataSet dataSet) {
    DataSetTreeNode rootNode = createTreeNode(dctMetaData,dataSet);
    treeModel.setRoot(rootNode);
  }
  /**
   *
   * @param dctMetaData DCTMetaData
   * @param dataSet EFDataSet
   * @return DataSetTreeNode
   * @throws Exception
   */
  public static DataSetTreeNode createTreeNode(DCTMetaData dctMetaData,EFDataSet dataSet) {
    DataSetTreeNode rootNode = DataSetTreeNode.getInstance(dctMetaData.getDoMetaData());
    if ( dataSet == null ) return rootNode;
    List<ESPRowSet> rowSetList = dataSet.getRowSetList();
    if ( rowSetList == null || rowSetList.size() == 0 ) return rootNode;
    java.util.Map context = new HashMap();
    for(int i=0;i<rowSetList.size();i++) {
      createTreeNode(dctMetaData,rowSetList.get(i),rootNode,context);
    }
    return rootNode;
  }
  /**
   *
   * @param dctMetaData DCTMetaData
   * @param dataSet EFDataSet
   * @param rootNode DataSetTreeNode
   * @param context Map
   * @return DataSetTreeNode
   * @throws Exception
   */
  protected static DataSetTreeNode createTreeNode(DCTMetaData dctMetaData,ESPRowSet rowSet,
      DataSetTreeNode rootNode,
      java.util.Map context) {
    StringBuffer DCT_BMCOLID = (StringBuffer)context.get("DCT_BMCOLID");
    StringBuffer DCT_MCCOLID = null,DCT_JSCOLID = null;
    if ( DCT_BMCOLID == null ) {
      DCT_BMCOLID = new StringBuffer(dctMetaData.getDCT_BMCOLID());
      DCT_MCCOLID = new StringBuffer(dctMetaData.getDCT_MCCOLID());
      DCT_JSCOLID = new StringBuffer(dctMetaData.getDCT_JSCOLID());
    } else {
      DCT_MCCOLID = (StringBuffer)context.get("DCT_MCCOLID");
      DCT_JSCOLID = (StringBuffer)context.get("DCT_JSCOLID");
    }
    // 生成Node
    DataSetTreeNode rowSetNode = DataSetTreeNode.getInstance(rowSet,DCT_BMCOLID,DCT_MCCOLID,DCT_JSCOLID);
    // 缓存Node
    putRowSetNode(dctMetaData,rowSet,rowSetNode,context);
    // 得到父Node
    DataSetTreeNode parentNode = getParentNode(dctMetaData,rowSet,context,rootNode);
    // 做为子节点增加
    parentNode.add(rowSetNode);
    // 返回
    return rowSetNode;
  }
  /**
   *
   * @param dctMetaData DCTMetaData
   * @param rowSet ESPRowSet
   * @param rowSetNode DataSetTreeNode
   * @param context Map
   */
  protected static void putRowSetNode(DCTMetaData dctMetaData,ESPRowSet rowSet,
                                   DataSetTreeNode rowSetNode,java.util.Map context) {
    String nodeCode = getNodeCode(dctMetaData,rowSet);
    context.put(nodeCode,rowSetNode);
  }
  /**
   *
   * @param dctMetaData DCTMetaData
   * @param rowSet ESPRowSet
   * @return String
   */
  protected static String getNodeCode(DCTMetaData dctMetaData,ESPRowSet rowSet) {
    String DCT_BMCOLID = dctMetaData.getDCT_BMCOLID();
    return rowSet.getString(DCT_BMCOLID,null);
  }
  /**
   *
   * @param parentCode String
   * @param context Map
   * @param rootNode DataSetTreeNode
   * @return DataSetTreeNode
   */
  protected static DataSetTreeNode getParentNode(DCTMetaData dctMetaData,ESPRowSet rowSet,
      java.util.Map context,
      DataSetTreeNode rootNode) {
    String parentCode = getParentCode(dctMetaData,rowSet);
    DataSetTreeNode parentNode = (DataSetTreeNode)context.get(parentCode);
    if ( parentNode == null ) parentNode = rootNode;
    return parentNode;
  }
  /**
   *
   * @param dctMetaData DCTMetaData
   * @param rowSet ESPRowSet
   * @return String
   */
  protected static String getParentCode(DCTMetaData dctMetaData,ESPRowSet rowSet) {
    String DCT_BMCOLID = dctMetaData.getDCT_BMCOLID();
    String DCT_JSCOLID = dctMetaData.getDCT_JSCOLID();
    String BMStruct = dctMetaData.getString("DCT_BMSTRU",null);
    String nodeCode = rowSet.getString(DCT_BMCOLID,null);
    int JS = rowSet.getNumber(DCT_JSCOLID,1).intValue();
    if ( JS == 1 || JS == 0 ) return null;
    int Length = StringFunction.GetStructLength(BMStruct, JS - 1);
    String parentCode = nodeCode.substring(0, Length);
    return parentCode;
  }
}
