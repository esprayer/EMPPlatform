package com.efounder.builder.meta.fctmodel;

import java.util.*;

import com.efounder.builder.meta.*;
import com.efounder.builder.meta.domodel.*;
import com.efounder.builder.meta.dctmodel.*;
import com.efounder.builder.base.data.*;

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
public class FCTMetaData extends MetaData implements IDOMetaData {

  /**
   *
   */
  public FCTMetaData() {
  }

  /**
   *
   * @return DCTMetaData
   */
  public static FCTMetaData getInstance(String dctID) {
    FCTMetaData fctMetaData = new FCTMetaData();
    fctMetaData.objID = dctID;
    return fctMetaData;
  }
  public String getFCT_TMTYPE(){
    return getString(SYS_FACTS.FCT_TMTYPE,SYS_FACTS._YEAR_);
  }
  /**
   *
   */
  protected static final String _REF_FCT_OBJECT_ = "refFCTObject";
  /**
   *
   * @return DOMetaData
   */
  public FCTMetaData getRefFCTObject() {
    return (FCTMetaData)this.getObject(_REF_FCT_OBJECT_,null);
  }
  /**
   *
   * @param doMetaData DOMetaData
   */
  protected void setRefFCTObject(FCTMetaData fctMetaData) {
    this.putObject(_REF_FCT_OBJECT_,fctMetaData);
  }
  /**
   *
   * @return String[]
   */
  public String[] getFKeyDCTColIDs() {
    Map map = getFKeyDCTMetaDataMap();
    if ( map == null ) return null;
    String[] colIDS = new String[map.size()];
    colIDS = (String[])map.keySet().toArray(colIDS);
    return colIDS;
  }
  /**
   *
   * @return DOMetaData[]
   */
  public DCTMetaData[] getFKeyDCTMetaDatas() {
    Map map = getFKeyDCTMetaDataMap();
    if ( map == null ) return null;
    DCTMetaData[] dmds = new DCTMetaData[map.size()];
    dmds = (DCTMetaData[])map.values().toArray(dmds);
    return dmds;
  }
  /**
   *
   * @param fkey String
   * @return DOMetaData
   */
  public DCTMetaData getFKeyDCTMetaData(String fkey) {
    Map map = getFKeyDCTMetaDataMap();
    return (DCTMetaData)map.get(fkey);
  }
  /**
   *
   * @param fkey String
   * @param doMetaData DOMetaData
   */
  protected void addFKEYDCTMetaData(String fkey,DCTMetaData fctMetaData) {
    java.util.Map fkeyMetaDataMap = createFKeyDCTMataDataMap();
    fkeyMetaDataMap.put(fkey,fctMetaData);
  }
  /**
   *
   */
  protected static final String _FKEY_DCT_META_DATA_MAP_ = "fkeyDCTMetaDataMap";
  /**
   *
   * @return Map
   */
  protected Map getFKeyDCTMetaDataMap(){
    java.util.Map fkeyMetaDataMap = (java.util.Map)this.getObject(_FKEY_DCT_META_DATA_MAP_,null);
    return fkeyMetaDataMap;
  }
  /**
   *
   * @return Map
   */
  protected java.util.Map createFKeyDCTMataDataMap() {
    java.util.Map fkeyMetaDataMap = (java.util.Map)this.getObject(_FKEY_DCT_META_DATA_MAP_,null);
    if ( fkeyMetaDataMap == null ) {
      fkeyMetaDataMap = new HashMap();
      this.putObject(_FKEY_DCT_META_DATA_MAP_,fkeyMetaDataMap);
    }
    return fkeyMetaDataMap;
  }
  /**
   *
   */
  protected static final String _MAIN_DO_META_DATA_ = "mainDoMetaData";
  /**
   *
   * @param doMetaData DOMetaData
   */
  protected void setDoMetaData(DOMetaData doMetaData) {
    this.putObject(_MAIN_DO_META_DATA_,doMetaData);
  }
  /**
   *
   * @return DOMetaData
   */
  public DOMetaData getDoMetaData() {
    return (DOMetaData)this.getObject(_MAIN_DO_META_DATA_,null);
  }
  /**
   *
   * @param doID String
   * @return DOMetaData
   */
  public DOMetaData[] getDOMetaData(String doID) {
    if ( doID.equals(this.objID) ) return new DOMetaData[]{ this.getDoMetaData()};
    DCTMetaData dctMetaData = this.getFKeyDCTMetaData(doID);
    if ( dctMetaData != null ) return new DOMetaData[]{dctMetaData.getDoMetaData()};
    FCTMetaData fctMetaData = this.getRefFCTObject();
    if ( fctMetaData != null && fctMetaData.getObjID().equals(doID) ) return new DOMetaData[]{fctMetaData.getDoMetaData()};
    return null;
  }
  /**
   *
   * @return String
   */
  public String toString() {
      return getString("FCT_ID", "") + getString("FCT_MC", "");
  }
}
