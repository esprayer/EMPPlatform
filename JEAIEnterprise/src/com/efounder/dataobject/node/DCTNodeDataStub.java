package com.efounder.dataobject.node;

import com.efounder.node.NodeDataStub;
import com.borland.dx.dataset.*;
import com.efounder.dataobject.*;
import com.efounder.pub.util.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class DCTNodeDataStub extends NodeDataStub {
  public static DCTNodeDataStub createDctNodeDataStub(Class dataClass,DCTObject dctObject,DataSet dataSet,DataRow dataRow) {
    DCTNodeDataStub dataStub = null;
    try {
      dataStub = (DCTNodeDataStub)dataClass.newInstance();
      dataStub.setDctObject(dctObject);
      dataStub.setDataSet(dataSet);
      dataStub.setDataRow(dataRow);
    } catch ( Exception e ) {
      e.printStackTrace();
    }
    return dataStub;
  }
  // 数据行
  private DataRow dataRow = null;
  // 数据集
  private DataSet dataSet = null;
  // 数据字典
  private DCTObject dctObject = null;
  /**
   *
   * @param dctObject DCTObject
   */
  public void setDctObject(DCTObject dctObject) {
    this.dctObject = dctObject;
  }
  /**
   *
   * @return String
   */
  public String getDCTMC() {
    String DCT_MCCOLID = dctObject.getDCTField("DCT_MCCOLID");
    return dataRow.getString(DCT_MCCOLID);
  }
  /**
   *
   * @return String
   */
  public String getDCTBH() {
    String DCT_BMCOLID = dctObject.getDCTField("DCT_BMCOLID");
    return dataRow.getString(DCT_BMCOLID);
  }
  /**
   *
   * @return int
   */
  public int getDCTJS() {
    String JSCOL = dctObject.getSYS_DICTS().getString("DCT_JSCOLID", "");
    if ( "".equals(JSCOL.trim()) ) return -1;
    int JS = dataRow.getBigDecimal(JSCOL).intValue();
    return JS;
  }
  /**
   *
   * @return String
   */
  public String getDCTMX() {
    String MXCOL = dctObject.getSYS_DICTS().getString("DCT_MXCOLID");
    return dataRow.getString(MXCOL);
  }
  public String getCurrentBH() {
    String BH = getDCTBH();
    String BMStruct = dctObject.getSYS_DICTS().getString("DCT_BMSTRU").trim();
    if ( "".equals(BMStruct) ) return BH;
    String JSCOL = dctObject.getSYS_DICTS().getString("DCT_JSCOLID");
    if ( "".equals(JSCOL.trim()) ) return BH;
    int JS = dataRow.getBigDecimal(JSCOL).intValue();
    if ( JS == 1 )
      return BH;
    String tBH = StringFunction.GetSubBMfromBM(BH, BMStruct, JS - 1);
    tBH = BH.substring(tBH.length(),BH.length());
    return tBH;
  }
  /**
   *
   * @param dataRow DataRow
   */
  public void setDataRow(DataRow dataRow) {
    this.dataRow = dataRow;
  }
  /**
   *
   * @param dataSet DataSet
   */
  public void setDataSet(DataSet dataSet) {
    this.dataSet = dataSet;
  }

  /**
   * 获取数据行
   */
  public DataRow getDataRow() {
    return dataRow;
  }
  /**
   * 获取数据集
   * @return DataSet
   */
  public DataSet getDataSet() {
    return dataSet;
  }

  public DCTObject getDctObject() {
    return dctObject;
  }

  /**
   *
   */
  public DCTNodeDataStub() {
  }

}
