package com.efounder.builder.meta.bizmodel;

import java.util.*;

import com.efounder.builder.base.data.*;
import com.efounder.builder.meta.*;
import com.efounder.builder.meta.domodel.*;
import com.efounder.builder.meta.fctmodel.*;
import com.efounder.builder.meta.dctmodel.*;
import com.efounder.builder.base.util.MapUtil;

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
public class BIZMetaData extends MetaData implements IDOMetaData {

  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
/**
   *
   */
  public BIZMetaData() {
  }
  /**
   *
   * @param doID String
   * @return BIZMetaData
   */
  public static BIZMetaData getInstance(String doID) {
    BIZMetaData dctMetaData = new BIZMetaData();
    dctMetaData.objID = doID;
    return dctMetaData;
  }
  /**
   *
   */
  public static final String _SYS_MODEL_    = "SYS_MODEL";
  /**
   *
   */
  public static final String _SYS_MDL_CTN_ = "SYS_MDL_CTN";
  /**
   *
   */
  public static final String _SYS_MDL_VAL_ = "SYS_MDL_VAL";
  /**
   *
   */
  public static final int _FCT_COUNT_ = 16;
  /**
   * 鑾峰彇妯″瀷鍐呭鐨勬暟鎹泦
   * @return EFDataSet
   */
  public EFDataSet getSYS_MDL_CTN() {
    return this.getDataSet(_SYS_MDL_CTN_);
  }
  /**
   *
   */
  protected java.util.Map SYS_MDL_VAL = null;
  /**
  *
  */
  public void setSYS_MDL_VAL(java.util.Map SYS_MDL_VAL){
	  this.SYS_MDL_VAL = SYS_MDL_VAL;
  }
  /**
   *
   * @param key String
   * @param value String
   */
  public void addSYS_MDL_VAL(String key,String value) {
    if ( SYS_MDL_VAL == null ) SYS_MDL_VAL = new java.util.HashMap();
    SYS_MDL_VAL.put(key,value);
  }
  /**
   * 妯″瀷灞炴�璁剧疆鏁版嵁闆�
   * @return EFDataSet
   */
  public java.util.Map getSYS_MDL_VAL() {
    return this.SYS_MDL_VAL;
  }
  /**
   *
   * @param MDL_KEY String
   * @param def String
   * @return String
   */
  public String getSYS_MDL_VAL(String MDL_KEY,String def) {
//    if ( SYS_MDL_VAL == null ) return def;
//    return MapUtil.getString(SYS_MDL_VAL,MDL_KEY+"-",def);
    return this.getExtPropertyValue(MDL_KEY,def);
  }
  /**
   *
   * @param UNIT_ID String
   * @param MDL_KEY String
   * @param def String
   * @return String
   */
  public String getSYS_MDL_VAL(String UNIT_ID,String MDL_KEY,String def) {
    return this.getExtPropertyValue(UNIT_ID,MDL_KEY,def);
//    if ( SYS_MDL_VAL == null ) return def;
//    // 鍏堣幏鍙栦娇鐢ㄥ崟浣嶇殑璁剧疆
//    UNIT_ID=UNIT_ID==null?"":UNIT_ID;
//    String SMDL_KEY = MDL_KEY+"-"+UNIT_ID;
//    String v = MapUtil.getString(SYS_MDL_VAL,SMDL_KEY,null);
//    // 濡傛灉浣跨敤鍗曚綅娌℃湁瀹氫箟锛屽垯杩斿洖鏃犲崟浣嶇殑璁剧疆
//    if ( v == null ) v = getSYS_MDL_VAL(MDL_KEY,def);
//    return v;
  }
  /**
   *
   * @return Map
   */
  public java.util.Map getFCTMap() {
    return FCTMap;
  }
  /**
  *
  */
  public void setFCTMap(java.util.Map FCTMap){
	  this.FCTMap = FCTMap;
  }
  /**
   *
   */
  protected java.util.Map FCTMap = new HashMap();
  /**
   *
   */
  protected java.util.Map CTNMap = new HashMap();
  /**
   *
   * @return Map
   */
  public java.util.Map getCTNMap() {
    return CTNMap;
  }
  /**
  *
  */
  public void setCTNMap(java.util.Map CTNMap){
	  this.CTNMap = CTNMap;
  }
  /**
  *
  * @return Map
  */
 public java.util.Map getCTN_TypeMap() {
   return CTN_TypeMap;
 }
 /**
 *
 */
 public void setCTN_TypeMap(java.util.Map CTN_TypeMap){
	  this.CTN_TypeMap = CTN_TypeMap;
 }
  /**
   *
   * @return String[]
   */
  public String[] getCTNIDs(){
      Object[] objctn = getCTNMap().keySet().toArray();
      if (objctn == null) return null;
      String[] strctn = new String[objctn.length];
      System.arraycopy(objctn, 0, strctn, 0, strctn.length);
      return strctn;
  }
  /**
   *
   * @param CTN_ID String
   * @param fctList List
   */
  public void addFCT_MetaData2CTN(String CTN_ID,java.util.List fctList) {
    CTNMap.put(CTN_ID,fctList);
  }
  /**
   *
   * @param fctMetaData FCTMetaData
   */
  public void addFCT_METAData(FCTMetaData fctMetaData) {
    FCTMap.put(fctMetaData.getObjID(),fctMetaData);
  }

  /**
   *
   * @param fctMetaData FCTMetaData
   */
  public void addDCT_METAData(DCTMetaData dctMetaData) {
    this.refDCTMap.put(dctMetaData.getObjID(), dctMetaData);
  }

  /**
   *
   * @param fctID String
   * @return FCTMetaData
   */
  public FCTMetaData getFCTMetaDataByFCT_ID(String fctID) {
    return (FCTMetaData)FCTMap.get(fctID);
  }
  /**
   *
   * @return String
   */
  public String getKEY_DCT() {
    return this.getString("MDL_KEYDCT",null);
  }
  /**
   *
   * @return String
   */
  public String getUNIT_DCT() {
    return this.getString("MDL_UNITDCT",null);
  }
  /**
   *
   * @return String
   */
  public String getYEAR_DCT() {
      return this.getString("MDL_NDCT", null);
  }
  /**
   *
   * @return String
   */
  public String getMONTH_DCT() {
    return this.getString("MDL_YDCT", null);
  }

  /**
   *
   * @return String
   */
  public String getDAY_DCT() {
    return this.getString("MDL_RDCT", null);
  }
  /**
   *
   * @return String
   */
  public String getLX_DCT() {
    return this.getString("MDL_BHDCT", null);
  }

  /**
   *
   * @return String
   */
  public String getBH_DCT() {
    return this.getString("MDL_BHDCT", null);
  }

  /**
   *
   * @return String
   */
  public String getTY_DCT() {
    return this.getString("MDL_TYDCT", null);
  }
  /**
   * 鍏抽敭鎸囨爣璁剧疆鏄惁鍒嗙粍缁囨満鏋勮缃�
   * @return boolean
   */
  public boolean isSetByUnit(){
    return "1".equals(this.getString("KEY_SET", ""));
  }

  /**
   * 妯″瀷杞崲瀹氫箟鏄惁鍒嗙粍缁囨満鏋勮缃�
   * @return boolean
   */
  public boolean isConvertDefineByUnit() {
    return "1".equals(this.getString("CVT_UNITSET", ""));
  }

  /**
   *
   * @param ctnID String
   * @return DOMetaData
   */
  public FCTMetaData[] getFCTMetaDatasByCTN_ID(String ctnID) {
    java.util.List fctList = (java.util.List)this.CTNMap.get(ctnID);
    if ( fctList == null || fctList.size() == 0 ) return null;
    FCTMetaData[] fds = new FCTMetaData[fctList.size()];
    fds = (FCTMetaData[])fctList.toArray(fds);
    return fds;
  }
  /**
   *
   * @param doID String
   * @return DOMetaData
   */
  public DOMetaData[] getDOMetaData(String doID) {
	  // 鍏堝彇浜嬪疄琛�
	  //    FCTMetaData[] fcMetaData = this.getFCTMetaDatasByCTN_ID(doID);//this.getFCTMetaDataByFCT_ID(doID);
	  //    // 涓嶄负绌鸿繑鍥�
	  //    if ( fcMetaData != null ) {//return new DOMetaData[]{fcMetaData.getDoMetaData()};
	  //    	DOMetaData[] dos = new DOMetaData[fcMetaData.length];
	  //    	for(int i=0; i<dos.length; i++){
	  //    		dos[i] = fcMetaData[i].getDoMetaData();
	  //    	}
	  //    	return dos;
	  //    }
	  // 鍐嶅彇鍐呭琛�
	  FCTMetaData[] fctMetaData = getFCTMetaDatasByCTN_ID(doID);
	  if ( fctMetaData != null && fctMetaData.length > 0 ) {
		  DOMetaData[] dds = new DOMetaData[fctMetaData.length];
		  for (int i = 0; i < fctMetaData.length; i++) {
			  dds[i] = fctMetaData[i].getDoMetaData();
		  }
		  return dds;
	  } else {
		  FCTMetaData fcMetaData = this.getFCTMetaDataByFCT_ID(doID);
		  if ( fcMetaData != null ) {
			  return new DOMetaData[]{fcMetaData.getDoMetaData()};
		  }else{
			  DCTMetaData dctMetaData = this.getDCTMetaData(doID);
			  if ( dctMetaData == null ) return null;
			  DOMetaData[] dds = new DOMetaData[1];
			  dds[0] = dctMetaData.getDoMetaData();
			  return dds;
		  }
	  }
  }
  /**
   *
   */
  protected java.util.Map refFCTMap = null;
  /**
   *
   */
  protected java.util.Map refDCTMap = null;
  /**
  *
  */
  public void setRefDCTMap(java.util.Map refDCTMap){
	  this.refDCTMap = refDCTMap;
  }
  /**
  *
  * @return Map
  */
 public java.util.Map getRefDCTMap() {
   return refDCTMap;
 }
 /**
 *
 */
 public void setRefFCTMap(java.util.Map refFCTMap){
	  this.refFCTMap = refFCTMap;
 }
 /**
 *
 * @return Map
 */
 public java.util.Map getRefFCTMap() {
  return refFCTMap;
}
  /**
   *
   * @param DCT_ID String
   * @return DCTMetaData
   */
  public DCTMetaData getRefDCTMetaData(String DCT_ID) {
    if ( refDCTMap == null ) return null;
    return (DCTMetaData)refDCTMap.get(DCT_ID);
  }
  /**
   *
   * @return String[]
   */
  public String[] getRefDCTIDs (){
    if ( refDCTMap == null ) return null;
    Object[] refobj = refDCTMap.keySet().toArray();
    String[] refstr = new String[refDCTMap.keySet().size()];
    System.arraycopy(refobj, 0, refstr, 0, refobj.length);
    return refstr;
  }
  /**
   *
   * @param DCT_ID String
   * @return DCTMetaData
   */
  public DCTMetaData getDCTMetaData(String DCT_ID) {
    return getRefDCTMetaData(DCT_ID);
  }
  /**
   *
   * @param FCT_ID String
   * @return FCTMetaData
   */
  public FCTMetaData getRefFCTMetaData(String FCT_ID) {
    if ( refFCTMap == null ) return null;
    return (FCTMetaData)refFCTMap.get(FCT_ID);
  }
  /**
   *
   * @return String[]
   */
  public String[] getRefFCTIDs() {
    if (refFCTMap == null) return null;
    Object[] refobj = refFCTMap.keySet().toArray();
    String[] refstr = new String[refFCTMap.keySet().size()];
    System.arraycopy(refobj, 0, refstr, 0, refobj.length);
    return refstr;
  }
  /**
   *
   * @param FCT_ID String
   * @return FCTMetaData
   */
  public FCTMetaData getFCTMetaData(String FCT_ID) {
    return getRefFCTMetaData(FCT_ID);
  }





























  /**
   *
   */
  protected java.util.Map CTN_TypeMap = null;
  /**
   *
   * @param CTN_Type String
   * @param CTN_ID String
   */
  public void addCTN_ID2CTN_TypeMap(String CTN_Type,String CTN_ID) {
    if ( CTN_TypeMap == null ) CTN_TypeMap = new HashMap();
    java.util.List ctnList = (java.util.List)CTN_TypeMap.get(CTN_Type);
    if ( ctnList == null ) {
      ctnList = new ArrayList();
      CTN_TypeMap.put(CTN_Type,ctnList);
    }
    if ( ctnList.indexOf(CTN_ID) == -1 ) ctnList.add(CTN_ID);
  }
  /**
   *
   * @param FCT_ID String
   * @return String
   */
  public String getCTN_TypeByFCT_ID(String FCT_ID) {
    if ( CTN_TypeMap == null ) return null;
    Object[] keys = CTN_TypeMap.keySet().toArray();
    if ( keys == null || keys.length == 0 ) return null;
    for(int i=0;i<keys.length;i++) {
      String[] ctn_ids = getCTN_IDsByCTN_Type((String)keys[i]);
      if ( ctn_ids == null || ctn_ids.length == 0 ) continue;
      for(int j=0;j<ctn_ids.length;j++) {
        String[] fct_ids = getFCTsByCTN_ID(ctn_ids[j]);
        for(int k=0;k<fct_ids.length;k++) {
          if ( FCT_ID.equals(fct_ids[k]) ) {
            return (String) keys[i];
          }
        }
      }
    }
    return null;
  }
  public String getCTN_TypeByCTN_ID(String CTN_ID) {
    if ( CTN_TypeMap == null ) return null;
    Object[] keys = CTN_TypeMap.keySet().toArray();
    if ( keys == null || keys.length == 0 ) return null;
    for(int i=0;i<keys.length;i++) {
      String[] ctn_ids = getCTN_IDsByCTN_Type((String)keys[i]);
      if ( ctn_ids == null || ctn_ids.length == 0 ) continue;
      for(int j=0;j<ctn_ids.length;j++) {
          if ( CTN_ID.equals(ctn_ids[j]) ) {
            return (String) keys[i];
          }
      }
    }
    return null;
  }

  /**
   *
   * @param CTN_Type String
   * @return String[]
   */
  public String[] getCTN_IDsByCTN_Type(String CTN_Type) {
    if ( CTN_TypeMap == null ) return null;
    java.util.List ctnList = (java.util.List)CTN_TypeMap.get(CTN_Type);
    if ( ctnList == null ) return null;
    String[] CTN_IDs = new String[ctnList.size()];
    CTN_IDs = (String[])ctnList.toArray(CTN_IDs);
    return CTN_IDs;
  }
  /**
   *
   * @param parentCTN_ID String
   * @return String[]
   */
  public String[] getCTN_IDsByParentCTN_ID(String parentCTN_ID) {
    EFDataSet dataSet = this.getDataSet(BIZMetaData._SYS_MDL_CTN_);
    java.util.List rowSetList = dataSet.getRowSetList();
    if ( rowSetList == null || rowSetList.size() == 0 ) return null;
    ESPRowSet rowSet = null;java.util.List ctn_idList = null;
    for(int i=0;i<rowSetList.size();i++) {
      rowSet = (ESPRowSet)rowSetList.get(i);
      if ( parentCTN_ID.equals(rowSet.getString("PCTN_ID",null)) ) {
        if ( ctn_idList == null ) ctn_idList = new ArrayList();
        ctn_idList.add(rowSet.getString("CTN_ID",""));
      }
    }
    if ( ctn_idList == null || ctn_idList.size() == 0 ) return null;
    String[] ctn_ids = new String[ctn_idList.size()];
    return (String[])ctn_idList.toArray(ctn_ids);
  }
  /**
   *
   * @param CTN_ID String
   * @return String[]
   */
  public String[] getFCTsByCTN_ID(String CTN_ID) {
    EFDataSet dataSet = this.getDataSet(BIZMetaData._SYS_MDL_CTN_);
    if ( dataSet == null ) return null;
    ESPRowSet rowSet = dataSet.getRowSet(new String[]{CTN_ID});
    if ( rowSet == null ) return null;
    java.util.List<String> FCTList = null;String FCT_ID = null;
    for(int i=0;i<SYS_MDL_CTN._BIZ_CTN_TYPE_FCT_COUNT_;i++) {
      FCT_ID = rowSet.getString(SYS_MDL_CTN.CTN_FCT+(i+1),null);
      if ( FCT_ID != null && FCT_ID.trim().length() > 0 ) {
        if( FCTList == null ) FCTList = new java.util.ArrayList();
        FCTList.add(FCT_ID);
      }
    }
    if ( FCTList == null || FCTList.size() == 0 ) return null;
    String[] FCTs = new String[FCTList.size()];
    FCTs = (String[])FCTList.toArray(FCTs);
    return FCTs;
  }
  /**
   *
   * @param CTN_ID String
   * @return String
   */
  public String getParentCTN_IDByCTN_ID(String CTN_ID) {
    EFDataSet dataSet = this.getDataSet(BIZMetaData._SYS_MDL_CTN_);
    if ( dataSet == null ) return null;
    ESPRowSet rowSet = dataSet.getRowSet(new String[]{CTN_ID});
    if ( rowSet == null ) return null;
    return rowSet.getString(SYS_MDL_CTN.PCTN_ID,null);
  }

  /**
   * 杩欓噷榛樿涓�釜鍘熷垯锛氫竴涓崟鎹腑涓�釜浜嬪疄琛ㄥ彧灞炰簬涓�釜CTN_ID
   * @param FCT_ID String
   * @return String
   */
  public String getCTN_IDByFCT_ID(String FCT_ID) {
      String[] CTN_IDs = getCTNIDs();
      for (int i = 0; CTN_IDs != null && i < CTN_IDs.length; i++) {
          String[] FCT_IDs = getFCTsByCTN_ID(CTN_IDs[i]);
          if (FCT_IDs == null) continue;
          if (Arrays.asList(FCT_IDs).contains(FCT_ID)) return CTN_IDs[i];
      }
      return null;
  }

}
