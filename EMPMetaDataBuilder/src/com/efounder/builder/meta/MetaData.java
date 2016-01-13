package com.efounder.builder.meta;

import com.efounder.builder.base.data.*;
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
public abstract class MetaData extends EFRowSet {
  /**
   *
   */
//  protected transient MetaDataManager metaDataManager = null;
  /**
   *
   */
  private String eaiServer;
  /**
   *
   */
  protected MetaData() {

  }
  /**
   *
   */
  protected java.util.Map referenceMetaDataMap = null;
  public void setReferenceMetaDataMap(java.util.Map referenceMetaDataMap) {
	  this.referenceMetaDataMap = referenceMetaDataMap;
  }
/**
   *
   * @param metaData MetaData
   */
  public void addReferenceMetaData(MetaData metaData) {
    if ( referenceMetaDataMap == null ) referenceMetaDataMap = new java.util.HashMap();
    referenceMetaDataMap.put(metaData,metaData);
  }
  /**
   *
   * @return List
   */
  public java.util.Map getReferenceMetaDataMap() {
    return referenceMetaDataMap;
  }
  /**
   *
   */
  protected String objID = null;
  /**
   *
   * @return String
   */
  public String getObjID() {
    return objID;
  }
  /**
   *
   * @return String
   */
  public String getDataBaseName() {
    return dataBaseName;
  }

  public String getEAIServer() {
    return eaiServer;
  }

  /**
   *
   * @return String
   */
  public String getDBNO() {
    return DBNO;
  }
  /**
   *
   */
  private String DBNO;
  /**
   *
   */
  private String dataBaseName;
  /**
   *
   * @param objID String
   */
  public void setObjID(String objID) {
    this.objID = objID;
  }
  /**
   *
   * @param dataBaseName String
   */
  public void setDataBaseName(String dataBaseName) {
    this.dataBaseName = dataBaseName;
  }

  public void setEAIServer(String eaiServer) {
    this.eaiServer = eaiServer;
  }

  /**
   *
   * @param DBNO String
   */
  public void setDBNO(String DBNO) {
    this.DBNO = DBNO;
  }
//  /**
//   *
//   */
//  protected java.util.Map extPropertyMap = null;
//  /**
//   *
//   * @return Map
//   */
//  public java.util.Map getExtProperty() {
//    return extPropertyMap;
//  }
//  /**
//   *
//   * @param map Map
//   */
//  public void setExtProperty(java.util.Map map) {
//    this.extPropertyMap = map;
//  }
//  /**
//   *
//   * @param key String
//   * @param dev String
//   * @return String
//   */
//  public String getExpProperty(String key,String dev) {
//    if ( extPropertyMap == null ) return dev;
//    return MapUtil.getString(extPropertyMap,key,dev);
//  }

  public static final String _INNR_UNIT_ = "_INNR_UNIT_";

  /**
   * 一定要不可序列化 transient
   */
  protected  java.util.Map extendProperty = null;
  /**
   *
   * @param UNIT_ID String
   * @return Map
   */
  public java.util.Map getExtendProperty(String UNIT_ID) {
    if ( extendProperty == null ) return null;

    if ( UNIT_ID == null || UNIT_ID.trim().length() == 0 )
        UNIT_ID = _INNR_UNIT_;
    return (java.util.Map)extendProperty.get(UNIT_ID);
  }
  /**
   *
   * @return Map
   */
  public java.util.Map getExtendProperty() {
    return getExtendProperty(null);
  }
  /**
   *
   * @param UNIT_ID String
   * @return Map
   */
  public java.util.Map createExtendProperty(String UNIT_ID) {
    if ( extendProperty == null ) extendProperty = new java.util.HashMap();
    if ( UNIT_ID == null || UNIT_ID.trim().length() == 0 ) UNIT_ID = _INNR_UNIT_;

    java.util.Map map = (java.util.Map)extendProperty.get(UNIT_ID);
    if ( map == null ) {
      map = new java.util.HashMap();
      extendProperty.put(UNIT_ID, map);
    }
    return map;
  }
  /**
   *
   * @return Map
   */
  public java.util.Map createExtendProperty() {
    return createExtendProperty(null);
  }
  /**
   *
   * @param UNIT_ID String
   * @param ecp Map
   */
  public void setExtendProperty(String UNIT_ID,java.util.Map ecp) {
    if ( ecp == null || ecp.size() == 0 ) return;
    if ( extendProperty == null ) extendProperty = new java.util.HashMap();
    if ( UNIT_ID == null || UNIT_ID.trim().length() == 0 ) UNIT_ID = _INNR_UNIT_;
    extendProperty.put(UNIT_ID,ecp);
  }
  /**
   *
   * @param ecp Map
   */
  public void setExtendProperty(java.util.Map ecp) {
    setExtendProperty(null,ecp);
  }
  /**
   *
   * @param key Object
   * @param value Object
   */
  public void setExtPropertyValue(String UNIT_ID,String key,String value) {
    java.util.Map map = createExtendProperty(UNIT_ID);
    map.put(key,value);
  }
  /**
   *
   * @param key String
   * @param value String
   */
  public void setExtPropertyValue(String key,String value) {
    setExtPropertyValue(null,key,value);
  }
  /**
   *
   * @param key Object
   * @param defValue Object
   * @return Object
   */
  public String getExtPropertyValue(String UNIT_ID,String key,String defValue) {
    java.util.Map map = getExtendProperty(UNIT_ID);
    if ( map == null ){
        map=getExtendProperty(null);
        if(map==null)
            return defValue;
        else{
            String value = (String)map.get(key);
            if ( value == null )
               return defValue;
            else
                return value;
        }
    }else{
       String value = (String) map.get(key);
       if(value!=null)return value;
        map = getExtendProperty(null);
        if (map == null)
            return defValue;
        else {
             value = (String) map.get(key);
            if (value == null)
             return defValue;
            else
                return value;
        }
    }
  }
  /**
   *
   * @param key String
   * @param defValue String
   * @return Object
   */
  public String getExtPropertyValue(String key,String defValue) {
    return getExtPropertyValue(null,key,defValue);
  }
  /**
   *
   * @param dataSet EFDataSet
   * @param UNIT_COL String
   * @param OBJ_COL String
   * @param KEY_COL String
   * @param VAL_COL String
   */
  public void processExtendProperty(EFDataSet dataSet,String UNIT_COL,String KEY_COL,String VAL_COL) {
    if ( dataSet == null || dataSet.getRowCount() == 0 ) return;
    List rowSetList = dataSet.getRowSetList();
    for(int i=0;i<rowSetList.size();i++) {
      EFRowSet rowSet = (EFRowSet)rowSetList.get(i);
      String UNIT_ID = rowSet.getString(UNIT_COL,null);
      String key     = rowSet.getString(KEY_COL,null);
      String value   = rowSet.getString(VAL_COL,null);
      this.setExtPropertyValue(UNIT_ID,key,value);
    }
  }
}
