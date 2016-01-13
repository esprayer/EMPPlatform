package com.efounder.builder.meta.iomodel;

import java.util.*;

import com.efounder.builder.base.data.*;
import com.efounder.builder.meta.*;
import com.efounder.builder.meta.domodel.DOMetaData;

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
public class IOMetaData extends MetaData implements IIOMetaData {
  /**
   *
   */
  public IOMetaData() {
  }

  /**
   *
   * @param objID String
   * @return IOMetaData
   */
  public static IOMetaData getInstance(String objID) {
    IOMetaData ioMetaData = new IOMetaData();
    ioMetaData.setObjID(objID);
    return ioMetaData;
  }

  /**
   * 本对象的所有的接口映射都在此
   */
  protected EFDataSet iODataSet;

  /**
   *
   * @return EFDataSet
   */
  public EFDataSet getIODataSet() {
    return iODataSet;
  }
  /**
  *
  * @return EFDataSet
  */
 public EFDataSet getIoDataSet() {
   return iODataSet;
 }

  /**
   *
   * @param ioID String
   * @return List
   */
  public java.util.List getIORowSetList(String ioID) {
    java.util.List rowSetList = new ArrayList();
    for (int i = 0; iODataSet != null && i < iODataSet.getRowCount(); i++) {
      EFRowSet rowSet = iODataSet.getRowSet(i);
      if (ioID.equals(rowSet.getString(SYS_IOCOLSET.IO_ID, ""))) rowSetList.add(rowSet);
    }
    return rowSetList;
  }

  /**
   *
   * @param ioID String
   * @return String[]
   */
  public String[] getIOColumnIDs(String ioID) {
    java.util.List rowSets = getIORowSetList(ioID);
    java.util.List columns = new ArrayList();
    for (int i = 0; i < rowSets.size(); i++) {
      EFRowSet rowSet = (EFRowSet) rowSets.get(i);
      columns.add(rowSet.getString(SYS_IOCOLSET.COL_ID, ""));
    }
    String[] ioColumnIDs = new String[columns.size()];
    System.arraycopy(columns.toArray(), 0, ioColumnIDs, 0, ioColumnIDs.length);
    return ioColumnIDs;
  }

  /**
   *
   * @param ioID String
   * @param COL_ID String
   * @return EFRowSet
   */
  public EFRowSet getIOColumnRowSet(String ioID, String COL_ID) {
    if (iODataSet != null)
      return (EFRowSet)iODataSet.getRowSet(new String[]{objID, ioID, COL_ID});
    return null;
  }

  /**
   *
   * @param ioID String
   * @param COL_ID String
   * @param key String
   * @param def String
   * @return String
   */
  public String getIOColumnExtValue(String ioID, String COL_ID, String key, String def) {
//    EFRowSet rowSet = getIOColumnRowSet(ioID, COL_ID);
//    if (rowSet == null) return def;
//    //扩展属性的值
//    String IO_EXTCONT = rowSet.getString(SYS_IOCOLSET.IO_EXTCONT, "");
//    //取扩展属性中某个关键字的值
//    return ESPKeyValueUtil.getValueByKey(key, def, IO_EXTCONT);
	  return "";
  }

  /**
   *
   * @param iODataSet EFDataSet
   */
  public void setIODataSet(EFDataSet iODataSet) {
    this.iODataSet = iODataSet;
  }
  /**
  *
  * @param iODataSet EFDataSet
  */
 public void setIoDataSet(EFDataSet iODataSet) {
   this.iODataSet = iODataSet;
 }

  /**
   *
   */
  protected DOMetaData dOMetaData;

  /**
   *
   * @return DOMetaData
   */
  public DOMetaData getDOMetaData(){
    return dOMetaData;
  }

  /**
   *
   * @param dOMetaData DOMetaData
   */
  public void setDOMetaData(DOMetaData dOMetaData) {
    this.dOMetaData = dOMetaData;
  }
  /**
  *
  * @return DOMetaData
  */
 public DOMetaData getDoMetaData(){
   return dOMetaData;
 }
  /**
  *
  * @param dOMetaData DOMetaData
  */
  public void setDoMetaData(DOMetaData dOMetaData){
	  this.dOMetaData = dOMetaData;
  }

}
