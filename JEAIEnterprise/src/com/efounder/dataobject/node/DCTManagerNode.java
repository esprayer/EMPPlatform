package com.efounder.dataobject.node;

import com.efounder.node.JNodeStub;
import com.borland.dx.dataset.*;
import com.efounder.dataobject.*;
import com.core.xml.*;
import com.efounder.node.Context;
import com.efounder.dataobject.view.DCTView;
import javax.swing.Icon;
import com.efounder.pub.util.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class DCTManagerNode extends JNodeStub implements com.efounder.dataobject.node.DCTNodeDataRow {
  /**
   * 获取节点数据管理器
   * @return DCTNodeDataManager
   */
  public DCTNodeDataManager getDctNodeDataManager() {
    return (DCTNodeDataManager)this.getNodeDataManager();
  }

  /**
   *
   * @param dctObject DCTObject
   * @return DataSet
   */
  public DataSet getFilterDataSet(DCTObject dctObject) {
    return dctObject.getTabObject().getTAB_DATA();
  }
  /**
   *
   * @return String
   */
  public String getDCT_ID() {
    return getNodeStubObject().getString("DCT_ID",null);
  }
  public Class getDCTNodeClass() {
    String className = getNodeStubObject().getString("DCTNode",null);
    if ( className == null ) return null;
    try {
      Class nodeClass = Class.forName(className);
      return nodeClass;
    } catch ( Exception e ) {
      e.printStackTrace();
    }
    return null;
  }
  public DCTNode getDCTNodeObject(Class nodeClass) {
    try {
      return (DCTNode)nodeClass.newInstance();
    } catch ( Exception e ) {
      e.printStackTrace();
    }
    return null;
  }
  /**
   *
   */
  public DCTManagerNode() {
  }
  /**
   *
   * @param parentNode JNodeStub
   * @param childSO StubObject
   * @return JNodeStub
   */
  public JNodeStub createChildNode(JNodeStub parentNode,StubObject childSO) {
    if ( childSO != null ) return super.createChildNode(parentNode,childSO);
    Class nodeClass = this.getDCTNodeClass();DCTNode dctNode = null;
    if ( nodeClass != null ) {
      dctNode = this.getDCTNodeObject(nodeClass);
      dctNode.setDctManagerNode(this);
    }
    return dctNode;
  }
  protected DCTObject dctObject = null;
  /**
   * getDCTObjectDataRow
   *
   * @param DCT_TYPE String
   * @return DCTObject
   */
  public DCTObject getDCTObjectDataRow(String DCT_TYPE) {
    try {
    	String server=getNodeStubObject().getString("server","");
      dctObject = DCTObject.getDCTObject(getDCT_ID(),server);
    } catch ( Exception e ) {e.printStackTrace();}
    return dctObject;
  }

  /**
   * getDataRowContext
   *
   * @param DCT_TYPE String
   * @param dataSet DataSet
   * @param dataRow DataRow[]
   * @param dctView DCTView
   * @return Context
   */
  public Context getDataRowContext(String DCT_TYPE, DataSet dataSet,
                                   DataRow[] dataRows, int[] Rows,DCTView dctView) {
    String NodeStubName = dctObject.getDCT_ID()+"_JNodeStub";Context context = null;
    JNodeStub[] nodeStubArray = new JNodeStub[dataRows.length];
    for(int i=0;i<dataRows.length;i++) {
      nodeStubArray[i] = (JNodeStub)dataRows[i].getObject(NodeStubName);
    }
    context = new Context(this.getExplorerView(),nodeStubArray);
    return context;
  }

  /**
   * getDataSet
   *
   * @param DCT_TYPE String
   * @return DataSet
   */
  public DataSet getDataSet(String DCT_TYPE) {
    DataSet dataSet = null;
    try {
      dataSet = getDCTObjectDataRow(null).getTabObject().getTAB_DATA();
    } catch ( Exception e ) {e.printStackTrace();}
    return dataSet;
  }

  /**
   * getIcon
   *
   * @param DCT_TYPE String
   * @return Icon
   */
  public Icon getIcon(String DCT_TYPE) {
    return null;
  }

  /**
   * getIcon
   *
   * @param DCT_TYPE String
   * @param value Object
   * @param dataSet DataSet
   * @param row int
   * @param column int
   * @return Icon
   */
  public Icon getIcon(String DCT_TYPE, Object value, DataSet dataSet, int row,
                      int column) {
    return null;
  }

  /**
   * getLevel
   *
   * @param DCT_TYPE String
   * @param value Object
   * @param dataSet DataSet
   * @param row int
   * @param column int
   * @return int
   */
  public int getLevel(String DCT_TYPE, Object value, DataSet dataSet, int row,
                      int column) {
    if ( dctObject != null ) {
      String BMStruct = dctObject.getDCTField("DCT_BMSTRU");
      return StringFunction.getJsByCodingStruct(value.toString(),BMStruct);
    }
    return 1;
  }

  /**
   * getText
   *
   * @param DCT_TYPE String
   * @return String
   */
  public String getText(String DCT_TYPE) {
    return "";
  }

}
