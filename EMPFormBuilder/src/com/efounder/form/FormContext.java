package com.efounder.form;

import com.efounder.builder.base.util.*;
import com.efounder.eai.data.*;
import com.efounder.sql.*;
import com.efounder.builder.meta.bizmodel.*;
import com.efounder.builder.base.data.EFDataSet;
import java.util.*;
import com.efounder.builder.base.data.*;
/**
 *
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
public class FormContext extends ESPServerContext {
  /**
   *
   */
  protected FormContext() {
  }
  /**
   *
   * @param paramObject
   * @param conn
   * @return
   */
  public static FormContext getInstance(JParamObject paramObject,
                                        JConnection connection) {
    FormContext ctx = new FormContext();
    ctx.paramObject = paramObject;
    ctx.connection = connection;
    return ctx;
  }
  /**
   * 业务模型的元数据
   */
  protected BIZMetaData bizMetaData = null;
  /**
   * 业务模型元数据的设置方法
   * @param bizMetaData BIZMetaData
   */
  public void setBIZMetaData(BIZMetaData bizMetaData) {
    this.bizMetaData = bizMetaData;
  }
  /**
   * 业务模型元数据的获取
   * @return BIZMetaData
   */
  public BIZMetaData getBIZMetaData() {
    return bizMetaData;
  }
  /**
   *
   */
  protected String formProviderKey = null;
  /**
   *
   * @param fpk String
   */
  public void setFormProviderKey(String fpk) {
    this.formProviderKey = fpk;
  }
  /**
   *
   * @return String
   */
  public String getFormProviderKey() {
    return formProviderKey;
  }
  /**
   *
   */
  protected String formResolverKey = null;
  /**
   *
   * @param frk String
   */
  public void setFormResolverKey(String frk) {
    this.formResolverKey = frk;
  }
  /**
   *
   * @return String
   */
  public String getFormResolverKey() {
    return formResolverKey;
  }
  /**
   *
   */
  protected EFDataSet[] itemDataSets = null;
  /**
   *
   * @return EFDataSet[]
   */
  public EFDataSet[] getItemDataSets() {
    return itemDataSets;
  }
  /**
   *
   * @param itemDataSets EFDataSet[]
   */
  public void setItemDataSets(EFDataSet[] itemDataSets) {
    this.itemDataSets = itemDataSets;
  }
  /**
   *
   */
  protected EFDataSet[] partDataSets = null;
  /**
   *
   * @return EFDataSet[]
   */
  public EFDataSet[] getPartDataSets() {
    return partDataSets;
  }
  /**
   *
   * @param itemDataSets EFDataSet[]
   */
  public void setPartDataSets(EFDataSet[] partDataSets) {
    this.partDataSets = partDataSets;
  }
  /**
   *
   */
  protected EFDataSet billDataSet = null;
  /**
   *
   * @return EFDataSet
   */
  public EFDataSet getBillDataSet() {
    return billDataSet;
  }
  /**
   *
   * @param billDataSet EFDataSet
   */
  public void setBillDataSet(EFDataSet billDataSet) {
    this.billDataSet = billDataSet;
  }
  /**
   *
   */
  protected java.util.Map<String,java.util.List> itemDataSetMap = new java.util.HashMap();
  /**
   *
   * @return String[]
   */
  public String[] getItemDataSetMapKeys() {
    String[] keys = new String[itemDataSetMap.size()];
    keys = (String[])itemDataSetMap.keySet().toArray(keys);
    return keys;
  }
  /**
   *
   * @param CTN_ID String
   * @param dataSet EFDataSet
   */
  public void addItemDataSet2List(String CTN_ID,EFDataSet dataSet) {
    java.util.List dataSetList= (java.util.List)itemDataSetMap.get(CTN_ID);
    if ( dataSetList == null ) {
      dataSetList = new java.util.ArrayList();
      itemDataSetMap.put(CTN_ID,dataSetList);
    }
    dataSetList.add(dataSet);
  }
  /**
   *
   * @param CTN_ID String
   * @return List
   */
  public java.util.List getItemDataSetList(String CTN_ID) {
    return (java.util.List)itemDataSetMap.get(CTN_ID);
  }
  /**
   *
   */
  protected java.util.Map<String,java.util.List> partDataSetMap = new java.util.HashMap();
  /**
   *
   * @return String[]
   */
  public String[] getPartDataSetMapKeys() {
    String[] keys = new String[partDataSetMap.size()];
    keys = (String[])partDataSetMap.keySet().toArray(keys);
    return keys;
  }
  /**
   *
   * @param CTN_ID String
   * @param dataSet EFDataSet
   */
  public void addPartDataSet2List(String CTN_ID,EFDataSet dataSet) {
    java.util.List dataSetList= (java.util.List)partDataSetMap.get(CTN_ID);
    if ( dataSetList == null ) {
      dataSetList = new java.util.ArrayList();
      partDataSetMap.put(CTN_ID,dataSetList);
    }
    dataSetList.add(dataSet);
  }
  /**
   *
   * @param CTN_ID String
   * @return List
   */
  public java.util.List getPartDataSetList(String CTN_ID) {
    return (java.util.List)partDataSetMap.get(CTN_ID);
  }
  /**
   *
   */
  protected java.util.Map billRowSetMap = new HashMap();
  /**
   *
   * @param dataSet EFDataSet
   * @param billRowSet ESPRowSet
   */
  public void addBillRowSet(EFDataSet dataSet,ESPRowSet billRowSet) {
    billRowSetMap.put(dataSet,billRowSet);
  }
  /**
   *
   * @param dataSet EFDataSet
   * @return ESPRowSet
   */
  public ESPRowSet getBillRowSet(EFDataSet dataSet) {
    return (ESPRowSet)billRowSetMap.get(dataSet);
  }
  /**
   *
   */
  protected java.util.Map itemRowSetMap = new HashMap();
  /**
   *
   * @param dataSet EFDataSet
   * @param itemRowSet ESPRowSet
   */
  public void addItemRowSet(EFDataSet dataSet,ESPRowSet itemRowSet) {
    itemRowSetMap.put(dataSet,itemRowSet);
  }
  /**
   *
   * @param dataSet EFDataSet
   * @return ESPRowSet
   */
  public ESPRowSet getItemRowSet(EFDataSet dataSet) {
    return (ESPRowSet)itemRowSetMap.get(dataSet);
  }
}
