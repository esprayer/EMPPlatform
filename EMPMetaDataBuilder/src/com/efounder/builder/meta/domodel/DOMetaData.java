package com.efounder.builder.meta.domodel;

import java.util.*;

import com.efounder.builder.base.data.*;
import com.efounder.builder.base.util.*;
import com.efounder.builder.meta.*;
import com.efounder.builder.meta.dctmodel.*;
import com.efounder.builder.meta.fctmodel.FCTMetaData;
import com.efounder.builder.meta.iomodel.IOMetaData;

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
public class DOMetaData extends MetaData implements IDOMetaData {
  /**
   *
   */
  public DOMetaData() {
	  super();
  }
  protected EFDataSet SYS_OBJ_VAL_DS = null;
/**
 *
 * @return EFDataSet
 */
public EFDataSet getSYS_OBJ_VAL_DS() {
  return SYS_OBJ_VAL_DS;
}
/**
 *
 * @param ACJGSZ EFDataSet
 */
public void setSYS_OBJ_VAL_DS(EFDataSet SYS_OBJ_VAL) {
  this.SYS_OBJ_VAL_DS = SYS_OBJ_VAL;
}

  /**
   *
   */
  protected java.util.Map SYS_OBJ_VAL = null;
  /**
   *
   * @param key String
   * @param value String
   */
  public void addSYS_OBJ_VAL(String key,String value) {
    if ( SYS_OBJ_VAL == null ) SYS_OBJ_VAL = new java.util.HashMap();
    SYS_OBJ_VAL.put(key,value);
  }
  /**
   * 妯″瀷灞炴�璁剧疆鏁版嵁闆�
   * @return EFDataSet
   */
  public java.util.Map getSYS_OBJ_VAL() {
    return this.SYS_OBJ_VAL;
  }
  /**
   * 
   * 
   */
  public void setSYS_OBJ_VAL(java.util.Map SYS_OBJ_VAL){
	  this.SYS_OBJ_VAL = SYS_OBJ_VAL;
  }
  /**
   *
   * @param MDL_KEY String
   * @param def String
   * @return String
   */
  public String getSYS_OBJ_VAL(String MDL_KEY,String def) {
    if ( SYS_OBJ_VAL == null ) return def;
    return MapUtil.getString(SYS_OBJ_VAL,MDL_KEY,def);
  }
  /**
   *
   * @return DOMetaData
   */
  public static DOMetaData getInstance(String doID) {
    DOMetaData doMetaData = new DOMetaData();
    doMetaData.setObjID(doID);
    return doMetaData;
  }
  /**
   *
   */
  protected static final String _REF_OBJECT_ = "refObject";
  /**
   *
   * @return DOMetaData
   */
  public DOMetaData getRefObject() {
    return (DOMetaData)this.getObject(_REF_OBJECT_,null);
  }
  /**
   *
   * @param doMetaData DOMetaData
   */
  protected void setRefObject(DOMetaData doMetaData) {
    this.putObject(_REF_OBJECT_,doMetaData);
  }
  /**
   *
   * @return String[]
   */
  public String[] getFKeyColIDs() {
    Map map = getFKeyMetaDataMap();
    if ( map == null ) return null;
    String[] colIDS = new String[map.size()];
    colIDS = (String[])map.keySet().toArray(colIDS);
    return colIDS;
  }
  public String getColIDByFKEYObj(String fkobj){
  String cols[] = this.getFKeyColIDs();
  for (int i = 0;cols!=null&&i < cols.length; i++) {
    if(getFKeyMetaData(cols[i])==null)continue;
    if(getFKeyMetaData(cols[i]).getObjID().equals(fkobj))return cols[i];
  }
  return "";
}

  /**
   *
   * @return DOMetaData[]
   */
  public DOMetaData[] getFKeyMetaDatas() {
    Map map = getFKeyMetaDataMap();
    if ( map == null ) return null;
    DOMetaData[] dmds = new DOMetaData[map.size()];
    dmds = (DOMetaData[])map.values().toArray(dmds);
    return dmds;
  }
  /**
   *
   * @param fkey String
   * @return DOMetaData
   */
  public DOMetaData getFKeyMetaData(String fkey) {
    Map map = getFKeyMetaDataMap();
    if ( map == null ) return null;
    return (DOMetaData)map.get(fkey);
  }
  /**
   *
   * @param fkey String
   * @param doMetaData DOMetaData
   */
  protected void addFKEYMetaData(String fkey,DOMetaData doMetaData) {
    java.util.Map fkeyMetaDataMap = createFKeyMataDataMap();
    fkeyMetaDataMap.put(fkey,doMetaData);
  }
  /**
   *
   */
  protected static final String _FKEY_META_DATA_MAP_ = "fkeyMetaDataMap";
  /**
   *
   * @return Map
   */
  protected java.util.Map createFKeyMataDataMap() {
    java.util.Map fkeyMetaDataMap = (java.util.Map)this.getObject(_FKEY_META_DATA_MAP_,null);
    if ( fkeyMetaDataMap == null ) {
      fkeyMetaDataMap = new HashMap();
      this.putObject(_FKEY_META_DATA_MAP_,fkeyMetaDataMap);
    }
    return fkeyMetaDataMap;
  }
  /**
   *
   * @return Map
   */
  protected Map getFKeyMetaDataMap(){
    java.util.Map fkeyMetaDataMap = (java.util.Map)this.getObject(_FKEY_META_DATA_MAP_,null);
    return fkeyMetaDataMap;
  }
  /**
   * 杩斿洖鏁版嵁瀵硅薄鐨勬墍鏈夊垪瀹氫箟
   * @return EFDataSet
   */
  public EFDataSet getDOColumns() {
    return this.getDataSet(SYS_OBJCOLS._SYS_OBJCOLS_);
  }
  /**
   *
   * @param colid String
   * @return ESPRowSet
   */
  public ESPRowSet getColumnDefineRow(String colid){
    EFDataSet eds=getDOColumns();
    return eds.getRowSet(new String[]{colid});
  }
  /**
   *
   * @return String[]
   */
  public String[] getColumns(){
    EFDataSet efs=getDOColumns();
    if(efs==null||efs.getRowCount()==0)return null;
    int count=efs.getRowCount();
    String use,col;
    List list=new ArrayList();
    for(int i=0;i<count;i++){
      use=efs.getRowSet(i).getString(SYS_OBJCOLS._COL_USE_,"1");
      if(use.equals("0"))continue;
      col=efs.getRowSet(i).getString(SYS_OBJCOLS._COL_ID_,"");
      list.add(col);
    }
    String[]cols=new String[list.size()];
    for(int i=0;i<list.size();i++)
      cols[i]=(String) list.get(i);
    return cols;
  }
  /**
   *
   * @return String[]
   */
  public String[] getKEYColumnIDs(){
      String[] allCol = getColumns();
      java.util.List keyCol = new ArrayList();
      for (int i = 0; allCol != null && i < allCol.length; i++) {
          ESPRowSet row = getColumnDefineRow(allCol[i]);
          if (row.getString("COL_ISKEY", "").equals("1")) {
              keyCol.add(row.getString("COL_ID", ""));
          }
      }
      String[] keyColArr = new String[keyCol.size()];
      System.arraycopy(keyCol.toArray(), 0, keyColArr, 0, keyColArr.length);
      return keyColArr;
  }
  /**
   *
   * @param doID String
   * @return DOMetaData
   */
  public DOMetaData[] getDOMetaData(String doID) {
    if ( doID.equals(this.objID) ) return new DOMetaData[]{this};
    DOMetaData doMetaData = this.getFKeyMetaData(doID);
    if ( doMetaData != null ) return new DOMetaData[]{doMetaData};
    doMetaData = this.getRefObject();
    if ( doMetaData != null && doMetaData.getObjID().equals(doID) ) return new DOMetaData[]{doMetaData};
    return null;
  }
  public String getREFOBJ_ID(String col){
    ESPRowSet ers= getColumnDefineRow(col);
    String ref=ers.getString(SYS_OBJCOLS._COL_REF_,"");
    int index=ref.indexOf(".");
    if(ref==null||index==-1)return "";
    return ref.substring(0,index);
  }
  public String getREFCOL_ID(String col){
    ESPRowSet ers= getColumnDefineRow(col);
    String ref=ers.getString(SYS_OBJCOLS._COL_REF_,"");
    int index=ref.indexOf(".");
    if(ref==null||index==-1)return "";
    return ref.substring(index+1);
  }
  /**
   *
   */
  protected DCTMetaData dctMetaData = null;
  /**
   *
   * @return DCTMetaData
   */
  public DCTMetaData getDctMetaData() {
    return dctMetaData;
  }
  /**
   *
   * @param dctMetaData DCTMetaData
   */
  public void setDctMetaData(DCTMetaData dctMetaData) {
    this.dctMetaData = dctMetaData;
  }
  /**
  *
  * @return DCTMetaData
  */
 public DCTMetaData getDCTMetaData() {
   return dctMetaData;
 }
 /**
  *
  * @param dctMetaData DCTMetaData
  */
 public void setDCTMetaData(DCTMetaData dctMetaData) {
   this.dctMetaData = dctMetaData;
 }
  /**
   *
   */
  protected FCTMetaData fctMetaData = null;
  /**
   *
   * @return FCTMetaData
   */
  public FCTMetaData getFctMetaData() {
    return fctMetaData;
  }
  /**
   *
   * @param fctMetaData FCTMetaData
   */
  public void setFctMetaData(FCTMetaData fctMetaData) {
    this.fctMetaData = fctMetaData;
  }
  /**
  *
  * @return FCTMetaData
  */
 public FCTMetaData getFCTMetaData() {
   return fctMetaData;
 }
  /**
  *
  * @param fctMetaData FCTMetaData
  */
 public void setFCTMetaData(FCTMetaData fctMetaData) {
   this.fctMetaData = fctMetaData;
 }

  public List getColListByAPPTYPE2(String type) {
    EFDataSet coleds = getDOColumns();
    String ref, use;
    List list = new ArrayList();
    for (int c = 0; c < coleds.getRowCount(); c++) {
      EFRowSet ers = coleds.getRowSet(c);
      use = ers.getString(SYS_OBJCOLS._COL_USE_, "1");
      if (use.equals("0"))
        continue;
      ref = ers.getString(SYS_OBJCOLS._COL_REF_, "");
      if (ref == null || ref.trim().length() == 0)
        continue;
      if (ers.getString(SYS_OBJCOLS._COL_YELX_, "").equals(type))
        list.add(ers.getString(SYS_OBJCOLS._COL_ID_, ""));
    }
    return list;
  }

  /**
   *
   * @return boolean
   */
  public boolean isOBJ_MUNIT() {
      return SYS_OBJECTS._OBJ_MUNIT_UNIT.equals(getString(SYS_OBJECTS._OBJ_MUNIT_, ""));
  }
  /**
   *
   * @return boolean
   */
  public boolean isOBJ_MCODE() {
      return SYS_OBJECTS._OBJ_MUNIT_CODE.equals(getString(SYS_OBJECTS._OBJ_MUNIT_, ""));
  }
  /**
   *
   * @return String
   */
  public String getOBJ_MC() {
      return getString(SYS_OBJECTS._OBJ_MC_, "");
  }

  /**
   *
   * @return String[]
   */
  public String[] getColumnsByType(String colType) {
      String[] columns = getColumns();
      java.util.List  blobCols = new ArrayList();
      for (int i = 0; columns != null && i < columns.length; i++) {
          ESPRowSet rowSet = getColumnDefineRow(columns[i]);
          if (rowSet == null) continue;
          if (colType.equals(rowSet.getString("COL_TYPE", ""))) blobCols.add(columns[i]);
      }
      String[] blobColumns = new String[blobCols.size()];
      System.arraycopy(blobCols.toArray(), 0, blobColumns, 0, blobColumns.length);
      return blobColumns;
  }

  /**
   *
   * @return String[]
   */
  public String[] getBlobColumns() {
      return getColumnsByType(SYS_OBJCOLS.BLOB);
  }

  /**
   *
   */
  protected IOMetaData iOMetaData;

  /**
   *
   * @return IOMetaData
   */
  public IOMetaData getIOMetaData() {
    return iOMetaData;
  }
  /**
  *
  * @return IOMetaData
  */
 public IOMetaData getIoMetaData() {
   return iOMetaData;
 }

  /**
   *
   * @param iOMetaData IOMetaData
   */
  public void setIOMetaData(IOMetaData iOMetaData) {
    this.iOMetaData = iOMetaData;
  }
  /**
  *
  * @param iOMetaData IOMetaData
  */
 public void setIoMetaData(IOMetaData iOMetaData) {
   this.iOMetaData = iOMetaData;
 }

}
