package com.print.service;

import java.util.ResourceBundle;
import java.util.Vector;

import jdatareport.dof.classes.report.QueryDataSet;

import com.core.xml.StubObject;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class PrintDataObject {
  static ResourceBundle res = ResourceBundle.getBundle("com.print.service.Language");
  protected Object printObject = null;
  protected PrintFormatObject printFormatObject = null;
  protected StubObject[] dataObjects    = null;
  protected Object []     parDataObjects = null;
  protected int DataLevel = 0;
  public void setPrintFormatObject(PrintFormatObject printFormatObject) {
    this.printFormatObject = printFormatObject;
  }
  /**
   *
   * @return int
   */
  public int getDataCount() {
    int count = 0;
    count = dataObjects.length;
    return count;
  }
//  public Vector getDataManager(int index) {
//    return (Vector)dataObjects[index].getObject("dataManager",null);
//  }
  public StubObject getStubObject(int index) {
    return dataObjects[index];
  }
//  public Vector getDataObject(int index) {
//    Vector dataObject = null;
//    dataObject = (Vector)dataObjects[index].getObject("dataObject",null);
//    return dataObject;
//  }
//  public int getDataObjectSize(int index) {
//    Vector dataObject = null;
//    dataObject  = (Vector)dataObjects[index].getObject("dataObject" ,null);
//    int dataLength = dataObject.size();//dataManager.getDataSize(dataObject);
//    return dataLength;
//  }

  public PrintFormatObject getPrintFormatObject() {
    return printFormatObject;
  }

  public Object getPrintObject() {
    return printObject;
  }

  public StubObject[] getDataObjects() {
    return dataObjects;
  }

  public int getDataLevel() {
    return DataLevel;
  }

  protected PrintDataObject() {
  }
  public static PrintDataObject getPrintDataObject(PrintFormatObject printFormatObject,StubObject[] dataObjects,Object printObject,int JS) {
    PrintDataObject pd   = new PrintDataObject();
    pd.DataLevel = JS;
    pd.printFormatObject = printFormatObject;
    pd.printObject       = printObject;
    pd.dataObjects       = dataObjects;
    pd.processDataObject();
    return pd;
  }

  private void processDataObject() {
    Object dataObject = null;
    Object[] ColsID = printFormatObject.getTableManager().getColId();// mValueCols.toArray();
    // 在这里形成所有的数据，与查询模式共用一组数据，节省空间和提高速度
    for(int i=0;i<dataObjects.length;i++) {
      dataObject = dataObjects[i].getObject("dataManager",null);
      dataObjects[i].setObject("dataObject",dataObject);// 设置数据
    }
  }
  protected ResultSetStatus resultSetStatus = null;

  protected void initResultSetStatus() {
    resultSetStatus = new ResultSetStatus();
    if ( dataObjects.length == 0 ) {
      resultSetStatus.setStatus(ResultSetStatus.RESULTSETS_EOF); // 结束
    } else {
      initDatas();// 初始化所有的数据集到数据集数组
      int Size = isEmpty();
      if ( Size > 0 ) {// 检查数据是否为空
        resultSetStatus.setStatus(ResultSetStatus.RESULTSETS_BOF); // 开始
        resultSetStatus.setResultSetIndex(0); // 数据集从0开始
        resultSetStatus.setDataIndex(0); // 数据从0开始
        resultSetStatus.Size = Size;
      } else {
        resultSetStatus.setStatus(ResultSetStatus.RESULTSETS_EOF); // 如果所有数据集为空，则处于结束
      }
    }
  }

  /**
   * 计算所有数据集的数据行数之和
   * @return int
   */
  public int isEmpty() {
    Vector dataList = null;int res = 0;
    for(int i=0;i<parDataObjects.length;i++) {
      //dataList = (Vector)parDataObjects[i];
      //res += dataList.size();
      QueryDataSet queryDataSet=(QueryDataSet)parDataObjects[i];
      res += queryDataSet.getTotalRowCount();
    }
    return res;
  }

  /**
   * 初始化打印数据集
   */
  private void initDatas() {
    if ( dataObjects.length == 0 ) return;
    parDataObjects = new Object[dataObjects.length];
    for(int i=0;i<dataObjects.length;i++) {
      parDataObjects[i] = dataObjects[i].getObject("dataObject",null);
    }
  }
  public void initPrintDataObject() {
    resultSetStatus = null;
  }
  /**
   * 返回数据集状态 0:数据集开
   * @return int
   */
  public ResultSetStatus next() {
    // 如果是第一次，则初始化
    if ( resultSetStatus == null ) {
      initResultSetStatus();
    } else {
      gotoNext(); // 否则执行下一个
    }
    return resultSetStatus;
  }

  /**
   * 获取一行数据
   * @return Object
   */
  public Object getObject() {
    Object value = null;
    int dataIndex      = resultSetStatus.getDataIndex();
    int ResultSetIndex = resultSetStatus.getResultSetIndex();
    //Object dataList = parDataObjects[ResultSetIndex];
    //value = dataList.get(dataIndex);
    QueryDataSet queryDataSet=(QueryDataSet)parDataObjects[ResultSetIndex];
    return queryDataSet.next();
  }

  private void gotoNext() {
    // 首先检查当前数据集的数据是否结束了
    int dataIndex      = resultSetStatus.getDataIndex();
    int ResultSetIndex = resultSetStatus.getResultSetIndex();
    // 获取当前数据集
    //Vector dataList = parDataObjects[ResultSetIndex];
    QueryDataSet queryDataSet =(QueryDataSet) parDataObjects[ResultSetIndex];
    // 如果到了‘当前数据集’的最后一行
    if ( (dataIndex+1) == queryDataSet.getTotalRowCount() ) {
      // 1、需要检查是否是最后一个数据集的最后一行，如果是，则结束了
      if ( (ResultSetIndex+1) == parDataObjects.length ) {
        // 设置结束标志
        resultSetStatus.setStatus(ResultSetStatus.RESULTSETS_EOF);
      } else { // 不是最后一个数据集，则数据集加1
        resultSetStatus.setResultSetIndex(ResultSetIndex+1);// 当前数据集加1
        resultSetStatus.setDataIndex(0);//当前数据集的数据索引为0
        resultSetStatus.setStatus(ResultSetStatus.RESULTSET_CHN);// 当前状态为数据集切换状态
      }
    } else {// 如果没有到最后一行，则继续处理
      resultSetStatus.setStatus(ResultSetStatus.RESULTSET_GEN);// 设置为正常处理位置
      resultSetStatus.setDataIndex(dataIndex+1);// 数据指针加1
    }
  }
  class ResultSetStatus {
    public static final short RESULTSETS_BOF  = 0x00; // 数据集组开始位置
    public static final short RESULTSETS_EOF  = 0xFF; // 数据集组结束位置
    public static final short RESULTSET_CHN   = 0x80; // 数据集交换
    public static final short RESULTSET_BOF   = 0x01; // 数据集开始位置
    public static final short RESULTSET_EOF   = 0xFE; // 数据集结束位置
    public static final short RESULTSET_GEN   = 0x88; // 正常处理位置

    protected short Status = RESULTSETS_BOF;
    protected int  ResultSetIndex = 0;
    protected int  DataIndex      = 0;
    protected int  Size = 0;
    public int getSize() {
      return Size;
    }

    /**
     *
     * @param ResultSetIndex long
     */
    public void setResultSetIndex(int ResultSetIndex) {
      this.ResultSetIndex = ResultSetIndex;
    }
    /**
     *
     * @return long
     */
    public int getResultSetIndex() {
      return ResultSetIndex;
    }
    /**
     *
     * @param DataIndex long
     */
    public void setDataIndex(int DataIndex) {
      this.DataIndex = DataIndex;
    }
    /**
     *
     * @return long
     */
    public int getDataIndex() {
      return DataIndex;
    }
    /**
     *
     * @param Status short
     */
    public void setStatus(short Status) {
      this.Status = Status;
    }
    /**
     *
     * @return short
     */
    public short getStatus() {
      return Status;
    }
  }
}
