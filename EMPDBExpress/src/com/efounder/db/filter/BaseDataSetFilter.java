package com.efounder.db.filter;

import com.borland.dx.dataset.*;
import com.borland.dx.text.*;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2006</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class BaseDataSetFilter implements RowFilterListener {

  /**
   *
   */
  protected Variant Value =  null;
  /**
   *
   */
  protected VariantFormatter valueFormatter = null;
  /**
   *
   */
  protected String fieldName   = null;
  /**
   *
   */
  protected String fieldValue = null;
  /**
   *
   */
  protected DataSet dataSet    = null;
  protected java.util.Map fieldValueList = new java.util.HashMap();
  /**
   *
   * @param dataSet DataSet
   * @param fValueList Map
   */
  public BaseDataSetFilter(DataSet dataSet,java.util.Map fValueList) {
    /**
     *
     */
    this.dataSet     = dataSet;
    fieldValueList.putAll(fValueList);
    /**
     * 列格式器
     */
//    valueFormatter = dataSet.getColumn(fieldName).getFormatter();
  }
  /**
   * 构造函数
   */
  public BaseDataSetFilter(DataSet dataSet,String fieldName,String fieldValue) {
    /**
     *
     */
    this.dataSet     = dataSet;
    /**
     *
     */
    this.fieldName   = fieldName;
    /**
     *
     */
    this.fieldValue = fieldValue;
    //
    fieldValueList.put(fieldName,fieldValue);
    /**
     * 列格式器
     */
//    valueFormatter = dataSet.getColumn(fieldName).getFormatter();
    /**
     *
     */
  }
  /**
   *
   * @param cds ClientDataSet
   * @throws TooManyListenersException
   */
  protected void addFilterEvent() {
    try {
      dataSet.removeRowFilterListener(this);
      dataSet.addRowFilterListener(this);
    } catch ( Exception e ) {
      e.printStackTrace();
    }
  }
  /**
   *
   * @param cds ClientDataSet
   */
  protected void removeFilterEvent() {
    dataSet.removeRowFilterListener(this);
  }
  protected boolean checkFieldValue(ReadRow row) {
    Object[] Fields = this.fieldValueList.keySet().toArray();
    String fieldName,fieldValue;
    for(int i=0;i<Fields.length;i++) {
      fieldName  = (String)Fields[i];
      fieldValue = (String)fieldValueList.get(fieldName);
      Value = new Variant();
      row.getVariant(fieldName,Value);
      valueFormatter = dataSet.getColumn(fieldName).getFormatter();
      String s = valueFormatter.format(Value);
      if (!fieldValue.equals(s))
        return false;
    }
    return true;
  }
  /**
   * 过滤器的主函数
   * @param row ReadRow
   * @param response RowFilterResponse
   */
  public void filterRow(ReadRow row, RowFilterResponse response) {
    try {
      if ( checkFieldValue(row) )
        response.add();
      else
        response.ignore();
    //      // 如果两个其中有一个为空，不能过滤
//      if ( fieldValue == null || fieldName == null )
//        response.add();
//      else {
//        //
//        row.getVariant(fieldName,Value);
//        String s = valueFormatter.format(Value);
//        if (fieldValue.equals(s))
//          response.add();
//        else response.ignore();
//        }
      }
    catch (Exception e) {
      System.err.println("Filter example failed");
    }
  }
  /**
   * 取消过滤
   */
  public void cancelFilter() {
    if ( dataSet == null ) return;
    this.fieldName  = null;
    this.fieldValue = null;
    this.removeFilterEvent();
    dataSet.refilter();
    dataSet.refresh();
  }
  /**
   * 重新过滤
   */
  public void reFilter() {
    this.addFilterEvent();
    dataSet.refilter();
  }
  /**
   * 重新过滤
   */
  public void reFilter(String fieldValue) {
    this.fieldValue = fieldValue;
    this.addFilterEvent();
    dataSet.refilter();
  }
  /**
   *
   * @param fieldName String
   * @param fieldValue String
   */
  public void reFilter(String fieldName,String fieldValue) {
    this.fieldName  = fieldName;
    this.fieldValue = fieldValue;
    reFilter();
  }
  /**
   *
   * @return DataSetView
   */
  public DataSetView getDataSetView() {
    DataSetView dataSetView = dataSet.cloneDataSetView();
    // 返回DataSetView
    return dataSetView;
  }
}
