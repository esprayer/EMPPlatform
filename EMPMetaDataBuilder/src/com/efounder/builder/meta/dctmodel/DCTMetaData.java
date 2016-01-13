package com.efounder.builder.meta.dctmodel;

import java.util.*;

import com.efounder.builder.base.util.*;
import com.efounder.builder.meta.*;
import com.efounder.builder.meta.domodel.*;
import com.efounder.builder.base.data.ESPRowSet;
import com.efounder.builder.base.data.EFDataSet;
import com.efounder.builder.base.data.EFRowSet;


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
public class DCTMetaData extends MetaData implements IDOMetaData {
  /**
   *
   */
  public DCTMetaData() {
	  super();
  }
  /**
   *
   */
  protected java.util.Map SYS_DCT_CST = null;
  /**
   *
   * @param key String
   * @param value String
   */
  public void addSYS_DCT_CST(String key,String value) {
    if ( SYS_DCT_CST == null ) SYS_DCT_CST = new java.util.HashMap();
    SYS_DCT_CST.put(key,value);
  }
  /**
   * 妯″瀷灞炴�璁剧疆鏁版嵁闆�
   * @return EFDataSet
   */
  public java.util.Map getSYS_DCT_CST() {
      if (SYS_DCT_CST == null) return getExtendProperty();
      return SYS_DCT_CST;
  }
  /**
  *
  * 
  */
  public void setSYS_DCT_CST(java.util.Map SYS_DCT_CST){
	  this.SYS_DCT_CST = SYS_DCT_CST;
  }
  /**
   *
   * @return String
   */
  public String getDCT_ID(){
      return this.getString(SYS_DICTS._DCT_ID_,"");
  }

  /**
   *
   * @return String
   */
  public String getDCT_MC(){
      return this.getString(SYS_DICTS._DCT_MC_,"");
  }

  /**
   *
   * @return String
   */
  public String  getDCT_BMCOLID(){
      return this.getString(SYS_DICTS._DCT_BMCOLID_,"");
  }
  /**
   *
   * @return String
   */
  public String getDCT_MCCOLID() {
      return this.getString(SYS_DICTS._DCT_MCCOLID_, "");
  }
  /**
   *
   * @return String
   */
  public String getDCT_JSCOLID() {
        return this.getString(SYS_DICTS._DCT_JSCOLID_, "");
  }
  /**
   *
   * @return String
   */
  public String getDCT_MXCOLID() {
      return this.getString(SYS_DICTS._DCT_MXCOLID_, "");
  }
  /**
   *
   * @return String
   */
  public String getDCT_QXCOLID() {
        return this.getString(SYS_DICTS._DCT_QXCOLID_, "");
  }

  /**
   *
   * @return String
   */
  public String getDCT_TYPE() {
    return this.getString(SYS_DICTS._DCT_TYPE_, "");
  }

  /**
   *
   * @return String
   */
  public String getDCT_BMSTRU() {
    return this.getString(SYS_DICTS._DCT_BMSTRU_, "");
  }

  /**
   *
   * @return boolean
   */
  public boolean isGradDCT() {
    return getDCT_BMSTRU().length() > 1;
  }

  /**
   *
   * @param MDL_KEY String
   * @param def String
   * @return String
   */
  public String getSYS_DCT_CST(String MDL_KEY,String def) {
    return this.getExtPropertyValue(MDL_KEY,def);
//    if ( SYS_DCT_CST == null ) return def;
//    return MapUtil.getString(SYS_DCT_CST,MDL_KEY+"-",def);
  }
  /**
   *
   * @param UNIT_ID String
   * @param MDL_KEY String
   * @param def String
   * @return String
   */
  public String getSYS_DCT_CST(String UNIT_ID,String MDL_KEY,String def) {
    return this.getExtPropertyValue(UNIT_ID,MDL_KEY,def);
//    String SMDL_KEY = MDL_KEY+"-"+UNIT_ID;
//    // 鍏堣幏鍙栦娇鐢ㄥ崟浣嶇殑璁剧疆
//    String v = MapUtil.getString(SYS_DCT_CST,SMDL_KEY,null);
//    // 濡傛灉浣跨敤鍗曚綅娌℃湁瀹氫箟锛屽垯杩斿洖鏃犲崟浣嶇殑璁剧疆
//    if ( v == null ) v = getSYS_DCT_CST(MDL_KEY,def);
//    return v;
  }
  /**
   *
   * @return DCTMetaData
   */
  public static DCTMetaData getInstance(String dctID) {
    DCTMetaData dctMetaData = new DCTMetaData();
    dctMetaData.objID = dctID;
    return dctMetaData;
  }
  /**
   *
   */
  protected static final String _REF_DCT_OBJECT_ = "refDCTObject";
  /**
   *
   * @return DOMetaData
   */
  public DCTMetaData getRefDCTObject() {
    return (DCTMetaData)this.getObject(_REF_DCT_OBJECT_,null);
  }
  /**
   *
   * @param doMetaData DOMetaData
   */
  protected void setRefDCTObject(DCTMetaData dctMetaData) {
    this.putObject(_REF_DCT_OBJECT_,dctMetaData);
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
   * @param DCT_ID String
   * @return DCTMetaData
   */
  public DCTMetaData getFKeyMetaDataByDCTID(String DCT_ID) {
    Map map = getFKeyDCTMetaDataMap();
    if ( map == null ) return null;
    return (DCTMetaData)map.get(DCT_ID);
  }
  /**
   *
   * @param RLGL_ID String
   * @return ESPRowSet
   */
  public ESPRowSet getRLGLDefine(String RLGL_ID) {
    EFDataSet dataSet = this.getDataSet("SYS_RLGL");
    if ( dataSet == null ) return null;
    ESPRowSet rowSet = dataSet.getRowSet(new String[]{RLGL_ID});
    return rowSet;
  }
  /**
   *
   * @return String[]
   */
  public String[] getRLGL_IDs() {
    if ( RLGLDCTMap == null ) return null;
    String[] sa = new String[RLGLDCTMap.size()];
    sa = (String[])RLGLDCTMap.keySet().toArray(sa);
    return sa;
  }

  /**
   *
   */
  protected java.util.Map RLGLDCTMap = null;
  /**
  *
  */
  public void setRLGLDCTMap(java.util.Map RLGLDCTMap){
	  this.RLGLDCTMap = RLGLDCTMap;
  }
  /**
  *
  */
  public java.util.Map getRLGLDCTMap(){
	  return this.RLGLDCTMap;
  }
  /**
   *
   * @param rlgl_ID String
   * @param dctMetaData DCTMetaData
   */
  public void addRLGLDCT(String rlgl_ID,DCTMetaData dctMetaData) {
    if ( RLGLDCTMap == null ) RLGLDCTMap = new java.util.HashMap();
    RLGLDCTMap.put(rlgl_ID,dctMetaData);
  }
  /**
   *
   * @param RLGL_ID String
   * @return DCTMetaData
   */
  public DCTMetaData getRLGLDCTMetaData(String RLGL_ID) {
    if ( RLGLDCTMap == null ) return null;
    return (DCTMetaData)RLGLDCTMap.get(RLGL_ID);
  }

  /**
   * 鑾峰彇鎵�湁鐨勫叧绯讳富瀛楀吀meta
   * @return DCTMetaData[]
   */
  public DCTMetaData[] getRLGLDCTMetaDatas() {
    String[] rlglids = this.getRLGL_IDs();
    if (rlglids == null) return null;
    DCTMetaData[] metas = new DCTMetaData[rlglids.length];
    for (int i = 0, n = rlglids.length; i < n; i++){
      metas[i] = this.getRLGLDCTMetaData(rlglids[i]);
    }
    return metas;
  }
  /**
   * 鏍规嵁涓诲瓧鍏哥殑瀛楀吀绫诲瀷鑾峰彇meta
   * @param dcttype String
   * @return DCTMetaData[]
   */
  public DCTMetaData[] getRLGLDCTMetaDatasByType(String dcttype) {
    DCTMetaData[] all = getRLGLDCTMetaDatas();
    if (all == null) return null;
    java.util.List dd = new ArrayList();
    for (int i = 0, n = all.length; i < n; i++){
      if (all[i].getDCT_TYPE().equals(dcttype)) dd.add(all[i]);
    }
    DCTMetaData[] mm = new DCTMetaData[dd.size()];
    System.arraycopy(dd.toArray(), 0, mm, 0, mm.length);
    return mm;
  }
  /**
   * 鑾峰彇鎵�湁鐨勫垎缁勫叧绯诲厓鏁版嵁
   * @return DCTMetaData[]
   */
  public DCTMetaData[] getRLGLDCTMetaDatasForGP() {
    return getRLGLDCTMetaDatasByType(SYS_DICTS._DCT_TYPE_GPZD);
  }

  /**
   *
   */
  protected java.util.Map RLGLObjMap = null;
  /**
  *
  */
  public void setRLGLObjMap(java.util.Map RLGLObjMap){
	  this.RLGLObjMap = RLGLObjMap;
  }
  /**
  *
  */
  public java.util.Map getRLGLObjMap(){
	  return this.RLGLObjMap;
  }
  /**
   *
   * @param rlgl_ID String
   * @param doMetaData DOMetaData
   */
  public void addRLGLOBJ(String rlgl_ID,DOMetaData doMetaData) {
    if ( RLGLObjMap == null ) RLGLObjMap = new java.util.HashMap();
    RLGLObjMap.put(rlgl_ID,doMetaData);
  }
  /**
   *
   * @param RLGL_ID String
   * @return DOMetaData
   */
  public DOMetaData getRLGLObjMetaData(String RLGL_ID) {
    if ( RLGLObjMap == null ) return null;
    return (DOMetaData)RLGLObjMap.get(RLGL_ID);
  }
  /**
   *
   * @param fkey String
   * @return DOMetaData
   */
  public DCTMetaData getFKeyDCTMetaData(String fkey) {
    Map map = getFKeyDCTMetaDataMap();
    if ( map == null ) return null;
    return (DCTMetaData)map.get(fkey);
  }
  /**
   *
   * @param fkey String
   * @param doMetaData DOMetaData
   */
  protected void addFKEYDCTMetaData(String fkey,DCTMetaData dctMetaData) {
    java.util.Map fkeyMetaDataMap = createFKeyDCTMataDataMap();
    fkeyMetaDataMap.put(fkey,dctMetaData);
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
    if ( dctMetaData != null ) return dctMetaData.getDOMetaData(doID);
    dctMetaData = this.getRefDCTObject();
    if ( dctMetaData != null && dctMetaData.getObjID().equals(doID) ) return dctMetaData.getDOMetaData(doID);
    return null;
  }
  /**
   *
   * @return String
   */
  public String toString(){
      return getString("DCT_ID", "")+getString("DCT_MC", "");
  }
  /**
   *
   */
  protected EFDataSet ACJGSZ = null;
  /**
   *
   * @return EFDataSet
   */
  public EFDataSet getACJGSZ() {
    return ACJGSZ;
  }
  /**
   *
   * @param ACJGSZ EFDataSet
   */
  public void setACJGSZ(EFDataSet ACJGSZ) {
    this.ACJGSZ = ACJGSZ;
  }
  protected EFDataSet SYS_DCT_CST_DS = null;
 /**
  *
  * @return EFDataSet
  */
 public EFDataSet getSYS_DCT_CST_DS() {
   return SYS_DCT_CST_DS;
 }
 /**
  *
  * @param ACJGSZ EFDataSet
  */
 public void setSYS_DCT_CST_DS(EFDataSet SYS_DCT_CST_DS) {
   this.SYS_DCT_CST_DS = SYS_DCT_CST_DS;
 }

  /**
   *
   * @param UNIT_DCT String
   * @param UNIT_ID String
   * @return EFRowSet
   */
  public EFRowSet getACJGSZRowSet(String UNIT_DCT,String UNIT_ID) {
    if ( ACJGSZ != null && ACJGSZ.getRowCount() != 0 ) {
      EFRowSet rowSet = (EFRowSet)ACJGSZ.getRowSet(new String[]{UNIT_ID, UNIT_DCT,this.objID});
      return rowSet;
    }
    return null;
  }

  /**
   * 缁熶竴浠庢暟鎹璞￠噷鐨勬爣璇嗕笂鍙�
   * @return String
   */
  public String getDCT_MUNIT() {
      return this.getDoMetaData().getString(SYS_OBJECTS._OBJ_MUNIT_, "");
  }

  /**
   * 瀛楀吀鍒嗕娇鐢ㄥ崟浣�
   * @return boolean
   */
  public boolean isDCT_MUNIT() {
      return SYS_OBJECTS._OBJ_MUNIT_UNIT.equals(getDCT_MUNIT());
  }

  /**
   * 瀛楀吀鍒嗙粍缁囨満鏋�
   * @return boolean
   */
  public boolean isDCT_MCODE() {
      return SYS_OBJECTS._OBJ_MUNIT_CODE.equals(getDCT_MUNIT());
  }

}
