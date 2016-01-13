package com.db;

import com.efounder.eai.data.*;
import com.efounder.eai.*;
import com.efounder.db.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class EAIDBTools extends DBTools {
  public EAIDBTools() {

  }
  /**
   *
   * @return String
   */
  public String getMLangSchema() {
    return getMLangSchema(JParamObject.Create());
  }
  /**
   * ���PO�е����ԣ��γɵ�ǰ���Ե�Schema
   * @param PO JParamObject
   * @return String DB010_ZH_CN
   */
  public String getMLangSchema(Object P) {
    JParamObject PO = (JParamObject)P;
    String dbNO = PO.GetValueByEnvName("DBNO",null);
    dbNO = "DB"+dbNO+"_"+PO.GetValueByEnvName("Language",EAI.getLanguage());
    return dbNO.toUpperCase();
  }
  public String getDBASchema() {
    return getDBASchema(JParamObject.Create());
  }
  /**
   *
   * @param PO JParamObject
   * @return String
   */
  public String getDBASchema(Object P) {
    JParamObject PO = (JParamObject)P;
    String dbOwner = PO.GetValueByEnvName("dbOwner",null);
    if ( dbOwner != null ) return dbOwner;
    String dbNO = PO.GetValueByEnvName("DBNO",null);
    dbNO = "DB"+dbNO+"_OWNER";// _OWNER
    return dbNO;
  }
  public String getUSRSchema() {
    return getUSRSchema(JParamObject.Create());
  }
  /**
   *
   * @param PO JParamObject
   * @return String
   */
  public String getUSRSchema(Object P) {
    JParamObject PO = (JParamObject)P;
    String dbNO = PO.GetValueByEnvName("DBNO",null);
    String User = PO.GetValueByEnvName("UserName",null);
    dbNO = "DB"+dbNO+"_"+User;
    return dbNO;
  }
  public String getPostfix() {
    return "";
  }
  public String getPostfix(Object P){
    return "";
  }
  /**
   * 根据核算主体和OBJ_ID，返回对像后缀
   * @param P Object
   * @param OBJ_ID String
   * @return String
   */
  public String getPostfix(Object P,String OBJ_ID) {
    return "";
//    JParamObject PO = (JParamObject)P;
//    String postfix = "",key = "_EAIDBTools_LSIVEW_";
//    String property = PO.GetValueByEnvName("ZW_ZRZXSX","1");
//    try{
//      HashMap map =(HashMap)DictDataBuffer.getDefault(PO).getDataItem(DictDataBuffer.
//            DCTDATASET, key);
//      if (map==null){
//        JConnection conn = DBUtils.getConnection(PO);
//        QueryDataSet QDS = new QueryDataSet();
//        Database dataBase = DBUtils.getDatabase(conn);
//        DataSetData dsd = DBUtils.getDataSetData(QDS, dataBase,
//                                                 "SELECT distinct F_TAB from LSVIEW");
//        TableDataSet table = new TableDataSet();
//        map = new HashMap();
//        dsd.loadDataSet(table);
//        while (table.next()) {
//          map.put(table.getString("F_TAB"),table.getString("F_TAB"));
//        }
//        DictDataBuffer.getDefault(PO).addDataItem(DictDataBuffer.DCTDATASET,
//                                                key, map);
//      }
//      if (map.get(OBJ_ID)==null) return postfix;
//
//      //虚拟核算主体、合并组织架构
//      if (property.equals("0") || property.equals("2")) {
//        postfix = "_V";
//      }
//      else if (property.equals("H")) {
//        postfix = "_H";
//      }
//      return postfix;
//    }catch(Exception e){
//      return "";
//    }
  }
//  /**
//   *
//   * @param PO JParamObject
//   * @param DCT_ID String
//   * @return String
//   */
//  public String getMLangObject(Object PO,String DCT_ID) {
//    return getMLangSchema(PO)+"."+DCT_ID+getPostfix();
//  }
//  /**
//   *
//   * @param DCT_ID String
//   * @return String
//   */
//  public String getMLangObject(String DCT_ID) {
//    return getMLangObject(JParamObject.Create(),DCT_ID);
//  }
//  /**
//   *
//   * @param PO Object
//   * @param OBJ_ID String
//   * @return String
//   */
//  public String getDBAObject(Object PO,String OBJ_ID) {
//    return getDBASchema(PO)+"."+OBJ_ID+getPostfix();
//  }
//  /**
//   *
//   * @param OBJ_ID String
//   * @return String
//   */
//  public String getDBAObject(String OBJ_ID) {
//    return getDBAObject(JParamObject.Create(),OBJ_ID);
//  }
  /**
   *
   * @param PO Object
   * @param OBJ_ID String
   * @return String
   */
  public String getUSRObject(Object PO,String OBJ_ID) {
    return getUSRSchema(PO)+"."+OBJ_ID+getPostfix();
  }
  /**
   *
   * @param OBJ_ID String
   * @return String
   */
  public String getUSRObject(String OBJ_ID) {
    return getUSRObject(JParamObject.Create(),OBJ_ID);
  }

}
