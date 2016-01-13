package com.efounder.mdm.server.provider.util;

import java.sql.*;
import java.util.*;

import com.core.xml.*;
import com.efounder.builder.base.data.*;
import com.efounder.builder.meta.dctmodel.*;
import com.efounder.db.*;
import com.efounder.eai.data.*;
import com.efounder.eai.service.*;
import com.efounder.mdm.server.*;
import com.efounder.builder.meta.domodel.SYS_OBJECTS;
import com.pub.util.StringFunction;

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
public class DICTProviderUtils {
  /**
   *
   */
  protected static final int QS_NULL = 0x0000;
  /**
   *
   */
  protected static final int QS_SJQX = 0x0001;
  /**
   *
   */
  protected static final int QS_ZZJG = 0x0002;
  protected static final int QS_ZZJG_FKEY = 0x0012;
  /**
   *
   */
  protected static final int QS_SYDW = 0x0004;
  /**
   *
   */
  protected DICTProviderUtils() {
  }

  protected static String toString(java.util.Map map) {
    if (map == null) return null;
    StringBuffer sb = new StringBuffer();
    Object nextobj = null;
    for (java.util.Iterator it = map.keySet().iterator(); it.hasNext(); ) {
      nextobj = it.next();
      sb.append(nextobj).append("=").append(map.get(nextobj)).append(";");
    }
    return sb.toString();
  }
  /**
   *
   * @param dctid String
   * @param type int
   * @return boolean
   */
  public static boolean isMDMProviderTypeMatch(String dctid, int type) {
    java.util.Vector v = PackageStub.getContentVector("MDMProviderType");
    StubObject s = null; String providerType = null;String[] providerTypes = null;
    for (int i = 0; v != null && i < v.size(); i++) {
      s = (StubObject) v.get(i);
      // 如果配置了，则按配置进行检查
      if (s == null || !s.getString("dctid", "").toUpperCase().equals(dctid.toUpperCase()))
        continue;
      providerType = s.getString("providerType", null);
      if (providerType == null || providerType.trim().length() == 0)
        continue;
      providerTypes = providerType.split(",");
      if (!java.util.Arrays.asList(providerTypes).contains(String.valueOf(type)))
        return false;
    }
    return true;
  }

  /**
   *
   * @param dctMetaData DCTMetaData
   * @param mdmContext MDMContext
   * @return EFDataSet
   * @throws Exception
   */
  public static EFDataSet getDICTData(DCTMetaData dctMetaData,MDMContext mdmContext) throws Exception {
	  EFDataSet hierarchyDataSet = null;
	  EFDataSet dctDataSet = null;String SQL = null;
	  int QS = getQueryStype(dctMetaData,mdmContext);

	  // 控制主数据的加载（配置则生效） add by gengeng 2011-12-27
	  if (!isMDMProviderTypeMatch(dctMetaData.getDCT_ID(), QS)) {
		  System.out.println("ESP MDM LOAD WARNING!" + dctMetaData.getDCT_ID()+
				  "{ENV:"+toString(mdmContext.getParamObject().getEnvRoot())+"}"+
				  "{PAM:"+toString(mdmContext.getParamObject().getParamRoot())+"}");
		  return EFDataSet.getInstance("");
	  }

	  SQL = getDICTSQL(dctMetaData,mdmContext,QS);
	  if ( SQL == null ) return null;

	  //加载外键数据 add by wujf at 20120225
	  //是否加载外键数据 只加载一级
	  String isFkeyLoad = mdmContext.getParamObject().GetValueByParamName("FKEY_DATASET_LOAD", "0");
	  java.util.Map<String,EFDataSet> dataSetMap = new java.util.HashMap<String,EFDataSet>();
	  if("1".equals(isFkeyLoad)){
		  SQL = SQL.toUpperCase();
		  String objId,colum,where = "exists(select 1 ";
		  DCTMetaData FKeyDCTMetaData;
		  String colums[] = dctMetaData.getDoMetaData().getFKeyColIDs();
		  if(colums != null){
			  for(int i=0;i<colums.length;i++){
				  colum = colums[i];
				  objId = dctMetaData.getDoMetaData().getFKeyMetaData(colum).getObjID();//FORM_TYPE
				  FKeyDCTMetaData = dctMetaData.getFKeyDCTMetaData(objId);//"SYS_FLEXFORM_TYPE"

				  where +=SQL.substring(SQL.indexOf("FROM"), SQL.indexOf("ORDER"))
				  +" AND "+dctMetaData.getObjID()+"."+colum+"=" +objId+"."
				  +FKeyDCTMetaData.getDCT_BMCOLID()+")";

				  mdmContext.getParamObject().GetValueByParamName(objId+"_MDMSelfWhere", where);
				  dataSetMap.put(objId,getFKeyDICTData(FKeyDCTMetaData,mdmContext));
			  }
		  }
	  }

	  //执行加载主数据SQL
	  ResultSet resultSet = getDICTResultSet(SQL,mdmContext);
	  if ( resultSet == null ) return null;
	  dctDataSet = EFDataSet.getInstance(dctMetaData.getObjID());
	  // 设置主键，任何字典都只有一个主键
	  dctDataSet.setPrimeKey(new String[] {dctMetaData.getDCT_BMCOLID()});
	  //设置外键主数据 add by wujf at 20120225
	  dctDataSet.setDataSetMap(dataSetMap);
	  String USER = null;

	  //判断是否分级，order by 增加按级数排序 add by wujf at 20111216
	  String js=dctMetaData.getDCT_JSCOLID();
	  if(js!=null&&js.trim().length()>0){
		  hierarchyDataSet = EFDataSet.getInstance(dctMetaData.getObjID()+"_Hierarchy");
		  // 设置主键，任何字典都只有一个主键
		  hierarchyDataSet.setPrimeKey(new String[] {dctMetaData.getDCT_BMCOLID()});
	  }
	  dctDataSet.setHierarchyDataSet(hierarchyDataSet);


	  if("1".equals(isFkeyLoad)){
		  convertRS2DS2(dctMetaData,USER,resultSet,dctDataSet,QS);
	  }else{
		  //不加载外键
		  convertRS2DS(dctMetaData,USER,resultSet,dctDataSet,QS);
	  }

	  closeResultSet(resultSet);
	  return dctDataSet;
  }

  /**
   * 加载外键数据  add by wujf at 20120225
   * @param dctMetaData
   * @param mdmContext
   * @return
   * @throws Exception
   */
  public static EFDataSet getFKeyDICTData(DCTMetaData dctMetaData,MDMContext mdmContext) throws Exception {
	  EFDataSet hierarchyDataSet = null;
	  EFDataSet dctDataSet = null;String SQL = null;
	  int QS = getQueryStype(dctMetaData,mdmContext);

	  // 控制主数据的加载（配置则生效） add by gengeng 2011-12-27
	  if (!isMDMProviderTypeMatch(dctMetaData.getDCT_ID(), QS)) {
		  System.out.println("ESP MDM LOAD WARNING!" + dctMetaData.getDCT_ID()+
				  "{ENV:"+toString(mdmContext.getParamObject().getEnvRoot())+"}"+
				  "{PAM:"+toString(mdmContext.getParamObject().getParamRoot())+"}");
		  return EFDataSet.getInstance("");
	  }

	  SQL = getDICTSQL(dctMetaData,mdmContext,QS);
	  if ( SQL == null ) return null;
	  ResultSet resultSet = getDICTResultSet(SQL,mdmContext);
	  if ( resultSet == null ) return null;
	  dctDataSet = EFDataSet.getInstance(dctMetaData.getObjID());
	  // 设置主键，任何字典都只有一个主键
	  dctDataSet.setPrimeKey(new String[] {dctMetaData.getDCT_BMCOLID()});
	  String USER = null;

	  //判断是否分级，order by 增加按级数排序 add by wujf at 20111216
	  String js=dctMetaData.getDCT_JSCOLID();
	  if(js!=null&&js.trim().length()>0){
		  hierarchyDataSet = EFDataSet.getInstance(dctMetaData.getObjID()+"_Hierarchy");
		  // 设置主键，任何字典都只有一个主键
		  hierarchyDataSet.setPrimeKey(new String[] {dctMetaData.getDCT_BMCOLID()});
	  }
	  dctDataSet.setHierarchyDataSet(hierarchyDataSet);

	  convertRS2DS(dctMetaData,USER,resultSet,dctDataSet,QS);
	  closeResultSet(resultSet);
	  return dctDataSet;
  }

  /**
   *
   * @param resultSt ResultSet
   * @param dataSet EFDataSet
   * @param queryStyle int
   * @throws Exception
   */
  protected static void convertRS2DS(DCTMetaData dctMetaData,String USER,ResultSet resultSet,EFDataSet dataSet,int queryStyle) throws Exception {
    if ( queryStyle == QS_SJQX ) {
//      DataSetUtils.resultSet2DataSet(resultSet,dataSet); // del by wujf at 20111221
//      convertRS2DS_SJQX(USER,resultSet,dataSet);
      resultSet2DataSet(dctMetaData,resultSet,dataSet,true);
    } else {
//      DataSetUtils.resultSet2DataSet(resultSet,dataSet);// del by wujf at 20111221
      resultSet2DataSet(dctMetaData,resultSet,dataSet,true);
    }
  }

  /**
  * add by wujf at 20120225
  * @param resultSt ResultSet
  * @param dataSet EFDataSet
  * @param queryStyle int
  * @throws Exception
  */
  protected static void convertRS2DS2(DCTMetaData dctMetaData,String USER,ResultSet resultSet,EFDataSet dataSet,int queryStyle) throws Exception {

	  if ( queryStyle == QS_SJQX ) {
		  //	      DataSetUtils.resultSet2DataSet(resultSet,dataSet); // del by wujf at 20111221
		  //	      convertRS2DS_SJQX(USER,resultSet,dataSet);
		  resultSet2DataSet2(dctMetaData,resultSet,dataSet,true);
	  } else {
		  //	      DataSetUtils.resultSet2DataSet(resultSet,dataSet);// del by wujf at 20111221
		  resultSet2DataSet2(dctMetaData,resultSet,dataSet,true);
	  }

  }

  /**
  *
  * @param resultSet ResultSet
  * @param dataSet EFDataSet
  * @param btrim boolean
  * @return EFDataSet
  * @throws Exception
  */
 public static EFDataSet resultSet2DataSet(DCTMetaData dctMetaData,ResultSet resultSet,EFDataSet dataSet, boolean btrim) throws Exception {
	  ESPRowSet rowSet = null;
	  while ( resultSet.next() ) {
		  // 生成ESPRowSet
		  rowSet = EFRowSet.getInstance();
		  // 将ResultSet中的一行生成到RowSet
		  rowSet = DataSetUtils.resultSet2RowSet(resultSet,rowSet,btrim);
		  // 插入DataSet中
		  dataSet.insertRowSet(rowSet);
		  // 形成主键索引
		  //     dataSet.buildPrimeKeyIndex(rowSet);

		  //组织分级数据 add by wujf at 20111216
		  resultSet2HierarchyRowSet(dctMetaData,resultSet,dataSet,rowSet);
	  }

	  return dataSet;
 }

  /**
   * add by wujf at 20120225
   * @param resultSet ResultSet
   * @param dataSet EFDataSet
   * @param btrim boolean
   * @return EFDataSet
   * @throws Exception
   */
  public static EFDataSet resultSet2DataSet2(DCTMetaData dctMetaData,ResultSet resultSet,EFDataSet dataSet, boolean btrim) throws Exception {
	  ESPRowSet rowSet = null;
	  while ( resultSet.next() ) {
		  // 生成ESPRowSet
		  rowSet = EFRowSet.getInstance();
		  // 将ResultSet中的一行生成到RowSet
		  rowSet = DataSetUtils.resultSet2RowSet(resultSet,rowSet,btrim);
		  // 插入DataSet中
		  dataSet.insertRowSet(rowSet);
		  // 形成主键索引
		  //     dataSet.buildPrimeKeyIndex(rowSet);

		  //外键数据设置对应 ESPRowSet add by wujf at 2012\02\25
		  setFKey2RowSet(dctMetaData,resultSet,dataSet,rowSet);

		  //组织分级数据 add by wujf at 20111216
		  resultSet2HierarchyRowSet(dctMetaData,resultSet,dataSet,rowSet);
	  }

	  return dataSet;
  }

  /**
   * 外键数据设置对应 ESPRowSet add by wujf at 20120225
   * @param dctMetaData
   * @param resultSet
   * @param dataSet
   * @param rowSet
   * @throws SQLException
   */
  private static void setFKey2RowSet(DCTMetaData dctMetaData,
		  ResultSet resultSet, EFDataSet dataSet, ESPRowSet rowSet) throws SQLException {
	  // TODO Auto-generated method stub
	  String objId,colum,columValue;
	  String MCCOLID,MCCOLValue;
	  DCTMetaData FKeyDCTMetaData;
	  ESPRowSet fkeyRowSet;
	  String colums[] = dctMetaData.getDoMetaData().getFKeyColIDs();
	  if(colums != null){
		  for(int i=0;i<colums.length;i++){
			  colum = colums[i];
			  columValue = resultSet.getString(colum);
			  objId = dctMetaData.getDoMetaData().getFKeyMetaData(colum).getObjID();
			  FKeyDCTMetaData = dctMetaData.getFKeyDCTMetaData(objId);
			  MCCOLID = FKeyDCTMetaData.getDCT_MCCOLID();

			  fkeyRowSet = dataSet.getDataSet(objId).getRowSet(new String[]{columValue});
			  ((EFRowSet) rowSet).setID2RowSet(objId, colum, fkeyRowSet);

			  if(fkeyRowSet == null) continue;
			  MCCOLValue = fkeyRowSet.getString(MCCOLID, "");
			  rowSet.setID2Name(objId, colum, MCCOLValue);
		  }
	  }
  }

private static void resultSet2HierarchyRowSet(DCTMetaData dctMetaData,ResultSet resultSet,EFDataSet dataSet,ESPRowSet rowSet) throws Exception {
	  // TODO Auto-generated method stub
	 EFDataSet hierarchyDataSet = dataSet.getHierarchyDataSet();
	  if(hierarchyDataSet == null) return;

	  String bmStru = dctMetaData.getDCT_BMSTRU(); //编码结构
	  String bmField = dctMetaData.getDCT_BMCOLID();
	  String bmValue = resultSet.getString(bmField);//编码
	  int jsValue = resultSet.getInt(dctMetaData.getDCT_JSCOLID());//级数
	  if(jsValue>0){
		  //获取父节点DataSet
		  EFDataSet parentDataSet = getParentDataSet(dataSet,bmValue,bmStru,jsValue-1);
		  // 插入DataSet中
		  parentDataSet.insertRowSet(rowSet);
		  // 形成主键索引
//		  parentDataSet.buildPrimeKeyIndex(rowSet);
	  }
 }

 //获取父节点DataSet
 private static EFDataSet getParentDataSet(EFDataSet dataSet, String bmValue,
		 String bmStru,int js) throws SQLException {
	 // TODO Auto-generated method stub
	 if(js == 0) return dataSet.getHierarchyDataSet();
	 EFDataSet childDataSet = null;

	 ESPRowSet rowSet = null;

	 String bm = StringFunction.GetSubBMfromBM(bmValue, bmStru, js);
	 rowSet = dataSet.getRowSet(bm);
	 if(rowSet == null) return dataSet.getHierarchyDataSet();//取不到上级，则为根节点 modi by wujf at 20120109
	 childDataSet = rowSet.getDataSet("children");

	 if(childDataSet == null){
		 childDataSet = EFDataSet.getInstance(dataSet.getHierarchyDataSet().getTableName());
		 //			 childDataSet.setDataSetType(EFDataSet._DATA_SET_CONT_ );
		 // 设置主键，任何字典都只有一个主键
		 childDataSet.setPrimeKey(dataSet.getHierarchyDataSet().getPrimeKey());
		 //			 rowSet.insertDataSetManager(childDataSet);
		 rowSet.setDataSet("children", childDataSet);
	 }
	 return childDataSet;
 }

  /**
   *
   * @param resultSet ResultSet
   * @param dataSet EFDataSet
   * @throws Exception
   */
  protected static void convertRS2DS_SJQX(String USER,ResultSet resultSet,EFDataSet dataSet) throws Exception {

  }

  /**
   *
   * @param table String
   * @return boolean
   */
  protected static boolean isUseDatalimit(String table) {
    java.util.List dicts = PackageStub.getContentVector("UseDatalimitTable");
    for (int i = 0; dicts != null && i < dicts.size(); i++){
      StubObject so = (StubObject) dicts.get(i);
      if (so.getString("DCT_ID", "").equals(table)) return true;
    }
    return false;
  }

  /**
   * 判断是否启用数据权限
   * @param dctMetaData DCTMetaData
   * @param UNIT_ID String
   * @return boolean
   */
  protected static boolean isSJQX(DCTMetaData dctMetaData, String UNIT_ID) {
    String QXZT = dctMetaData.getString("DCT_QXSTAT", "0"); // 0/1/2:没启用/全启用/使用单位启用
    String QXID = dctMetaData.getDCT_QXCOLID();
    String QXDW = dctMetaData.getSYS_DCT_CST("SJQXDW", ""); // 启用了数据权限的单位
    if (QXID == null || QXID.trim().length() == 0) return false;
    // 整个字典都启用数据权限
    if ("1".equals(QXZT)) return true;
    // 判断使用单位是否启用
    if ("2".equals(QXZT) && QXDW != null && QXDW.trim().length() > 0) {
      String[] dws = StringFunction.split(QXDW, ",");
      if (dws != null && java.util.Arrays.asList(dws).contains(UNIT_ID))
        return true;
    }
    return false;
  }

  /**
   *
   * @param dctMetaData DCTMetaData
   * @return int
   */
  protected static int getQueryStype(DCTMetaData dctMetaData,MDMContext mdmContext) {
    // 登录使用单位
    String UNIT_ID = mdmContext.getParamObject().GetValueByEnvName("UNIT_ID", "");
    // 检查是否数据权限模式，优先级最高
    // 如果不是在具体的业务模型环境下，不使用数据权限，但允许个别表例外
//    String QXZT = dctMetaData.getString("DCT_QXSTAT", "0");// 0/1/2:没启用/全启用/使用单位启用
//    String QXID = dctMetaData.getDCT_QXCOLID();
//    // 使用单位是否单独启用了数据权限
//    if (UNIT_ID != null && UNIT_ID.trim().length() > 0
//        && (QXID == null || QXID.trim().length() == 0))
//      QXID = dctMetaData.getSYS_DCT_CST(UNIT_ID, "SJQXBH", null);
//    if ( QXID != null && QXID.trim().length() != 0
//         && (mdmContext.getBIZMetaData() != null||isUseDatalimit(dctMetaData.getDCT_ID())))
    if ( isSJQX(dctMetaData, UNIT_ID)
         && (mdmContext.getBIZMetaData() != null||isUseDatalimit(dctMetaData.getDCT_ID())))
      return QS_SJQX;
    // 检查是否组织机构选用模式 优先级次之
    EFDataSet dataSet = dctMetaData.getACJGSZ();
    if ( dataSet != null && dataSet.getRowCount() != 0 && mdmContext.getBIZMetaData() != null ) {
      ESPRowSet rowSet = dataSet.getRowSet(new String[]{UNIT_ID, mdmContext.getBIZMetaData().getUNIT_DCT(),dctMetaData.getObjID()});
      if ( rowSet != null ) return QS_ZZJG;
    }
    // 外键第三
    if ( mdmContext.getBIZMetaData() != null ) {
      String UNIT_DCT = mdmContext.getBIZMetaData().getUNIT_DCT();
      DCTMetaData fkeyDctMetaData = dctMetaData.getFKeyDCTMetaData(UNIT_DCT);
      if (fkeyDctMetaData != null) return QS_ZZJG_FKEY;
    }
    // 检醒是否使用单位选用模式 使用单位第四
    if ( "1".equals(dctMetaData.getString("DCT_SELECT","0")) ) {
      return QS_SYDW;
    }
    // 第五，啥也没有
    return QS_NULL;
  }
  /**
   *
   * @param dctMetaData DCTMetaData
   * @param mdmContext MDMContext
   * @return String
   * @throws Exception
   */
  protected static String getDICTSQL(DCTMetaData dctMetaData,MDMContext mdmContext,int queryStyle) throws Exception {
    String SQL = null;
//    switch ( queryStyle ) {
//    case QS_SJQX:
//      if ("1".equals(mdmContext.getParamObject().GetValueByParamName("SJQX_STYLE", "1")))
//        SQL = getDICTSJQX2(dctMetaData,mdmContext);
//      else
//        SQL = getDICTSJQX(dctMetaData,mdmContext);
//      break;
//    case QS_ZZJG:
//      SQL = getDICTZZJG(dctMetaData,mdmContext);
//      break;
//    case QS_ZZJG_FKEY:
//      SQL = getDICTZZJG_FKEY(dctMetaData,mdmContext);
//      break;
//    case QS_SYDW:
//      SQL = getDICTSYDW(dctMetaData,mdmContext);
//      break;
//    case QS_NULL:
      SQL = getDICTNULL(dctMetaData,mdmContext);
//      break;
//    }
    return SQL;
  }
  /**
   *
   * @param SQL String
   * @param mdmContext MDMContext
   * @return ResultSet
   * @throws Exception
   */
  protected static ResultSet getDICTResultSet(String SQL,MDMContext mdmContext) throws Exception {
    try {
      ResultSet resultSet = mdmContext.getStatement().executeQuery(SQL);
      return resultSet;
    } catch (SQLException ex) {
      System.out.println("error sql:" + SQL);
      throw ex;
    }
  }
  /**
   *
   * @param resultSet ResultSet
   * @throws Exception
   */
  protected static void closeResultSet(ResultSet resultSet) throws Exception {
    if ( resultSet != null ) resultSet.close();
  }

  /**
   *
   * @param dctMetaData DCTMetaData
   * @param mdmContext MDMContext
   * @return String
   * @throws Exception
   */
  protected static String getDICTFields(DCTMetaData dctMetaData,MDMContext mdmContext, String function, boolean prefix) throws Exception {
      //如果有BLOB的列，排除掉
      String[] columns = dctMetaData.getDoMetaData().getColumns();
      String[] blobcol = dctMetaData.getDoMetaData().getBlobColumns();
      StringBuffer  sb = new StringBuffer();
      for (int i = 0; columns != null && i < columns.length; i++) {
          //排除BLOB列
          if (Arrays.asList(blobcol).contains(columns[i])) continue;
          if (function != null) sb.append("max(");
          if (prefix) sb.append(dctMetaData.getObjID() + ".");
          sb.append(columns[i]);
          if (function != null) sb.append(")");
          sb.append(" as ");
          sb.append(columns[i]);
          if (i < columns.length - 1) sb.append(",");
      }
      return sb.toString();
  }

//  /**
//   *
//   * @param dctMetaData DCTMetaData
//   * @param mdmContext MDMContext
//   * @return String
//   * @throws Exception
//   */
//  protected static String getDICTSJQX2(DCTMetaData dctMetaData,MDMContext mdmContext) throws Exception {
//    String tableName = dctMetaData.getObjID();
//    String sjqxName = dctMetaData.getDCT_QXCOLID();
//    String BSUSERROLE = DBTools.getDBAObject(mdmContext.getParamObject(),"BSUSERROLE");
//    String F_BH = dctMetaData.getDCT_BMCOLID();
//    String F_ZGBH = mdmContext.getParamObject().GetValueByEnvName("UserName");
//    String UserUNIT_ID = mdmContext.getParamObject().GetValueByEnvName("UserUNIT_ID");//用户所属的使用单位
//    // 权限编号支持自定义，默认是表名
//    String  QXBH = (String)mdmContext.getParamObject().getValue(tableName+"_QXBH","");//支持从前台传数据权限编号 add by wujf at 20120706
//    if(QXBH == null || "".equals(QXBH.trim()))
//    	QXBH = dctMetaData.getSYS_DCT_CST("QXBH", tableName);
//    String field = getDICTFields(dctMetaData, mdmContext, null, true) + ",QX.F_G0 as F_G0,QX.F_G1 as F_G1,QX.F_G2 as F_G2," +
//        "QX.F_G3 as F_G3,QX.F_G4 as F_G4,QX.F_G5 as F_G5,QX.F_G6 as F_G6,QX.F_G7 as F_G7,QX.F_G8 as F_G8,QX.F_SH as F_SH";
//    String table = getDBAObject(mdmContext.getParamObject(),dctMetaData)+" "+tableName+","+sjqxName+" QX";
//    // 使用权限审核
//    boolean useAudit = ParameterManager.getDefault().isUseLimitAudit();
//    String where = "QX.F_QXBH = '" + QXBH + "' and " + tableName + "." + F_BH + " = QX.F_SJBH";
//    if (dctMetaData.isGradDCT())
//      where = "QX.F_QXBH = '" + QXBH + "' and " + tableName + "." + F_BH + " like QX.F_SJBH ||'%'";
//    if (useAudit) where += " and QX.F_SH = '1'";
//    String where1 = where + " and QX.F_ZGBH = '" + F_ZGBH + "'";
//    String where2 = where + " and exists(select 1 from " + BSUSERROLE + " where F_ZGBH = '"+F_ZGBH+"' and QX.F_ZGBH = F_ROLECODE";
//    if (useAudit) where2 += " and F_SH = '1'";
//    where2 += ")";
//    // 使用单位使用数据权限，本单位用户自动有本单位的数据权限
//    if ("SYS_UNITS".equals(dctMetaData.getDCT_ID())) {
//        where1 = "(" + where1 + ") or SYS_UNITS.UNIT_ID = '" + UserUNIT_ID + "'";
//        where2 = "(" + where2 + ") or SYS_UNITS.UNIT_ID = '" + UserUNIT_ID + "'";
//    }
//    // 用户字典使用数据权限，操作用户有自身的数据权限
//    if ("BSUSER".equals(dctMetaData.getDCT_ID())) {
//        where1 = "(" + where1 + ") or BSUSER.F_ZGBH = '" + F_ZGBH + "'";
//        where2 = "(" + where2 + ") or BSUSER.F_ZGBH = '" + F_ZGBH + "'";
//    }
//    // 自定义条件
//    String selfWhere = mdmContext.getParamObject().GetValueByParamName(dctMetaData.getObjID() + "_MDMSelfWhere", "");
//    if (selfWhere.trim().length() > 0) {
//        where1 = "(" + where1 + ") and " + selfWhere;
//        where2 = "(" + where2 + ") and " + selfWhere;
//    }
//    // 用户权限
//    String SQL1 = "select " + field + " from " + table + " where " + where1;
//    // 角色权限
//    String SQL2 = "select " + field + " from " + table + " where " + where2;
//    String SQL = SQL1 + " union all " + SQL2;
//    // 取最大
//    field = getDICTFields(dctMetaData, mdmContext, "max", false) + ",max(F_G0) as F_G0,max(F_G1) as F_G1,max(F_G2) as F_G2," +
//        "max(F_G3) as F_G3,max(F_G4) as F_G4,max(F_G5) as F_G5,max(F_G6) as F_G6,max(F_G7) as F_G7,max(F_G8) as F_G8,max(F_SH) as F_SH";
//    SQL = "select " + field + " from (" + SQL + ") group by " + dctMetaData.getDCT_BMCOLID() + " order by " + dctMetaData.getDCT_BMCOLID();
//    //判断是否分级，order by 增加按级数排序 add by wujf at 20111216
//    String js=dctMetaData.getDCT_JSCOLID();
//    if(js!=null&&js.trim().length()>0)
//    	SQL +=","+dctMetaData.getDCT_JSCOLID();
//
//
//    return SQL;
//  }
//
//  /**
//   *
//   * @param dctMetaData DCTMetaData
//   * @param mdmContext MDMContext
//   * @return String
//   * @throws Exception
//   */
//  protected static String getDICTSJQX(DCTMetaData dctMetaData,MDMContext mdmContext) throws Exception {
//    String tableName = dctMetaData.getObjID();
//    String sjqxName = dctMetaData.getDCT_QXCOLID();
//    String BSUSERROLE = DBTools.getDBAObject(mdmContext.getParamObject(),"BSUSERROLE");
//    String F_BH = dctMetaData.getDCT_BMCOLID();
//    String F_ZGBH = mdmContext.getParamObject().GetValueByEnvName("UserName");
//    String UserUNIT_ID = mdmContext.getParamObject().GetValueByEnvName("UserUNIT_ID");//用户所属的使用单位
//    //权限编号支持自定义，默认是表名
//    String  QXBH = (String)mdmContext.getParamObject().getValue(tableName+"_QXBH","");//支持从前台传数据权限编号 add by wujf at 20120706
//    if(QXBH == null || "".equals(QXBH.trim()))
//    	QXBH = dctMetaData.getSYS_DCT_CST("QXBH", tableName);
//    String field = getDICTFields(dctMetaData, mdmContext,"max",true) + ",max(QX.F_G0) as F_G0,max(QX.F_G1) as F_G1,max(QX.F_G2) as F_G2," +
//        "max(QX.F_G3) as F_G3,max(QX.F_G4) as F_G4,max(QX.F_G5) as F_G5,max(QX.F_G6) as F_G6,max(QX.F_G7) as F_G7,max(QX.F_G8) as F_G8,max(QX.F_SH) as F_SH";
//    String table = getDBAObject(mdmContext.getParamObject(),dctMetaData)+" "+tableName+","+sjqxName+" QX";
//    //使用权限审核
//    boolean useAudit = ParameterManager.getDefault().isUseLimitAudit();
//    String where = "(" + tableName+"."+F_BH+" = QX.F_SJBH and QX.F_QXBH='"+QXBH+"'";
//    if (dctMetaData.isGradDCT())
//      where = "(" + tableName+"."+F_BH+" like QX.F_SJBH ||'%' and QX.F_QXBH='"+QXBH+"'";
//    if (useAudit) where += " and QX.F_SH = '1'";
//    where += " and (QX.F_ZGBH='"+F_ZGBH+"' or QX.F_ZGBH in (select F_ROLECODE from ";
//    where += BSUSERROLE+" where F_ZGBH='"+F_ZGBH+"' and QX.F_ZGBH=F_ROLECODE";
//    if (useAudit) where += " and F_SH = '1'";
//    where += ")))";
//    // 使用单位使用数据权限，本单位用户自动有本单位的数据权限
//    if ("SYS_UNITS".equals(dctMetaData.getDCT_ID())) where+= " or SYS_UNITS.UNIT_ID ='"+UserUNIT_ID+"'";
//    // 用户字典使用数据权限，操作用户有自身的数据权限
//    if ("BSUSER".equals(dctMetaData.getDCT_ID())) where += " or BSUSER.F_ZGBH = '" + F_ZGBH + "'";
//    // 自定义条件
//    String selfWhere = mdmContext.getParamObject().GetValueByParamName(dctMetaData.getObjID() + "_MDMSelfWhere", "");
//    if (selfWhere.trim().length() > 0) where ="(" + where + ") and " + selfWhere;
//
//    String group = tableName + "." + F_BH;
//    String order = tableName + "." + F_BH;
//    String SQL = "select " + field + " from " + table + " where " + where + " group by " + group + " order by " + order;
//
//    //判断是否分级，order by 增加按级数排序 add by wujf at 20111216
//    String js=dctMetaData.getDCT_JSCOLID();
//    if(js!=null&&js.trim().length()>0)
//      SQL+=","+tableName+"."+js;
//
//    return SQL;
//  }
//  /**
//   select LSKMZD.*,QX.F_G0 as F_G0_,QX.F_G1 as F_G1_
//   from LSKMZD,BSUSSJ QX
//   where LSKMZD.F_KMBH=QX.F_SJBH and
//   QX.F_QXBH='LSKMZD' and
//   (QX.F_ZGBH='0001' or QX.F_ZGBH in
//   ( select F_ROLECODE from BSUSERROLE where F_ZGBH='0001' and QX.F_ZGBH=F_ROLECODE ))
//   order by LSKMZD.F_KMBH
//   */
//  /**
//   *
//   * @param dctMetaData DCTMetaData
//   * @param mdmContext MDMContext
//   * @return String
//   * @throws Exception
//   */
//  protected static String getDICTZZJG(DCTMetaData dctMetaData,MDMContext mdmContext) throws Exception {
////    String tableName = DBTools.getDBAObject(mdmContext.getParamObject(), dctMetaData.getObjID());
//    String tableName = getDBAObject(mdmContext.getParamObject(), dctMetaData) + " " + dctMetaData.getObjID();
//    String UNIT_ID = mdmContext.getParamObject().GetValueByEnvName("UNIT_ID", "");
//    EFDataSet dataSet= dctMetaData.getACJGSZ();
//    //选用表使用配置的
//    ESPRowSet rowSet = dataSet.getRowSet(new String[]{UNIT_ID, mdmContext.getBIZMetaData().getUNIT_DCT(),dctMetaData.getObjID()});
//    String xyTable = rowSet.getString("XY_DCT", "");
//    if (xyTable.trim().length() == 0) xyTable = dctMetaData.getObjID()+"_XY_"+UNIT_ID;
//    xyTable = DBTools.getDBAObject(mdmContext.getParamObject(), xyTable);
//    String ZZJG = mdmContext.getBIZMetaData().getUNIT_DCT();
//    // 组织机构编码
//    String ZZJG_BH = mdmContext.getParamObject().GetValueByParamName(ZZJG, null);
//    if (ZZJG_BH == null||ZZJG_BH.trim().length()==0) ZZJG_BH = mdmContext.getParamObject().GetValueByEnvName(ZZJG);
//    String F_BH = dctMetaData.getObjID() + "." + dctMetaData.getDCT_BMCOLID();
//    String where = "exists(select 1 from "+xyTable+" where (ZX_BM like '"+ZZJG_BH+"%' or '" + ZZJG_BH + "' like ZX_BM||'%') and XY_BM="+F_BH + ")";
//    // 根据对象个性化层次区分，个性化到组织机构层次
//    if (dctMetaData.getDoMetaData().isOBJ_MCODE())
//      where += " and (CODE_ID = ' ' or CODE_ID = '" + ZZJG_BH + "' or CODE_ID is null)";
//    // 个性化到使用单位层次
//    else if (dctMetaData.getDoMetaData().isOBJ_MUNIT())
//      where += " and (UNIT_ID = ' ' or UNIT_ID = '" + UNIT_ID + "' or UNIT_ID is null)";
//    // 其他定义了使用单位外键
//    else if (isColumnFKey("UNIT_ID", dctMetaData, mdmContext)) {
//      where += " and (UNIT_ID = ' ' or UNIT_ID = '" + UNIT_ID + "' or UNIT_ID is null)";
//    }
//    //自定义条件
//    String selfWhere = mdmContext.getParamObject().GetValueByParamName(dctMetaData.getObjID() + "_MDMSelfWhere", "");
//    if (selfWhere.trim().length() > 0) where += " and " + selfWhere;
//    String SQL = DBTools.selectAllSql(tableName, where);
//    String bhc = dctMetaData.getDCT_BMCOLID();
//    if (bhc != null && bhc.trim().length() > 0)
//      SQL+=" ORDER BY "+dctMetaData.getDCT_BMCOLID();
//    String js=dctMetaData.getDCT_JSCOLID();
//    if(js!=null&&js.trim().length()>0) SQL += "," + js;
//    return SQL;
//  }
//  /**
//   *
//   * @param dctMetaData DCTMetaData
//   * @param mdmContext MDMContext
//   * @return String
//   * @throws Exception
//   */
//  protected static String getDICTZZJG_FKEY(DCTMetaData dctMetaData,MDMContext mdmContext) throws Exception {
////    String tableName = DBTools.getDBAObject(mdmContext.getParamObject(), dctMetaData.getObjID());
//    String tableName = getDBAObject(mdmContext.getParamObject(), dctMetaData) + " " + dctMetaData.getObjID();
//    String UNIT_ID = mdmContext.getParamObject().GetValueByEnvName("UNIT_ID");
//    String xyTable = DBTools.getDBAObject(mdmContext.getParamObject(), dctMetaData.getObjID()+"_XY_"+UNIT_ID);
//    String UNIT_DCT = mdmContext.getBIZMetaData().getUNIT_DCT();
//    DCTMetaData fkeyDctMetaData = dctMetaData.getFKeyDCTMetaData(UNIT_DCT);
//    String COL_ID = dctMetaData.getDoMetaData().getColIDByFKEYObj(UNIT_DCT);
//    // 先从Param区取
//    String ZZJG_BH = mdmContext.getParamObject().GetValueByParamName(UNIT_DCT, null);
//    if (ZZJG_BH == null||ZZJG_BH.trim().length()==0) ZZJG_BH = mdmContext.getParamObject().GetValueByEnvName(UNIT_DCT);
//    String where = "("+COL_ID+" like '"+ZZJG_BH+"%')";
//    // 自定义条件
//    String selfWhere = mdmContext.getParamObject().GetValueByParamName(dctMetaData.getObjID() + "_MDMSelfWhere", "");
//    if (selfWhere.trim().length() > 0) where += " and " + selfWhere;
//
//    String SQL = DBTools.selectAllSql(tableName,where);
//    String bhc = dctMetaData.getDCT_BMCOLID();
//    if (bhc != null && bhc.trim().length() > 0)
//      SQL+=" ORDER BY "+dctMetaData.getDCT_BMCOLID();
//    String js=dctMetaData.getDCT_JSCOLID();
//    if(js!=null&&js.trim().length()>0)
//      SQL+=","+js;
//    return SQL;
//  }
  /**
   select * from LSKMZD
   where exists(select 1 from LSKMZD_XY_5030 where ZX_BM='0010' and F_KMBH=XY_BM)
   */
  /**
   *
   * @param dctMetaData DCTMetaData
   * @param mdmContext MDMContext
   * @return String
   * @throws Exception
   */
//  protected static String getDICTSYDW(DCTMetaData dctMetaData,MDMContext mdmContext) throws Exception {
//    String tableName = dctMetaData.getObjID();
//    String SYS_UNIT_SELECT = DBTools.getDBAObject(mdmContext.getParamObject(),"SYS_UNIT_SELECT");
//    String F_BH = dctMetaData.getString("DCT_BMCOLID","F_BH");
//    String UNIT_ID = mdmContext.getParamObject().GetValueByEnvName("UNIT_ID"," ");
//    String where = "exists( select 1 from "+SYS_UNIT_SELECT+" where OBJ_ID='"+tableName+"' and ZD_BH="+F_BH+" and UNIT_ID='"+UNIT_ID+"')";
//    if (dctMetaData != null)  {
//      String munit = dctMetaData.getDoMetaData().getString(SYS_OBJECTS._OBJ_MUNIT_, "");
//      //如果是多单位、多组织机构
//      if (munit.equals(SYS_OBJECTS._OBJ_MUNIT_UNIT))
//        where += " and UNIT_ID = '" + UNIT_ID + "'";
//    }
//    //单位选用模式使用UNIT_ID过滤
////    where += " and UNIT_ID = '" + UNIT_ID + "'";
////    tableName = DBTools.getDBAObject(mdmContext.getParamObject(),tableName);
//    //自定义条件
//    String selfWhere = mdmContext.getParamObject().GetValueByParamName(dctMetaData.getObjID() + "_MDMSelfWhere", "");
//    if (selfWhere.trim().length() > 0) where += " and " + selfWhere;
//
//    tableName = getDBAObject(mdmContext.getParamObject(),dctMetaData) + " " + tableName;
//    String SQL = DBTools.selectAllSql(tableName,where);
//    String bhc = dctMetaData.getDCT_BMCOLID();
//    if (bhc != null && bhc.trim().length() > 0)
//      SQL+=" ORDER BY "+dctMetaData.getDCT_BMCOLID();
//    String js=dctMetaData.getDCT_JSCOLID();
//    if(js!=null&&js.trim().length()>0)
//      SQL+=","+js;
//    return SQL;
//  }
  /**
   select * from LSKMZD where
   exists( select 1 from SYS_UNIT_SELECT where OBJ_ID='LSWLDW' and ZD_BH=F_KMBH and UNIT_ID='5030')
   */

  /**
   *
   * @param dctMetaData DCTMetaData
   * @param mdmContext MDMContext
   * @return String
   * @throws Exception
   */
  protected static String getDICTNULL(DCTMetaData dctMetaData,MDMContext mdmContext) throws Exception {
    String tableName = dctMetaData.getObjID();
    String   UNIT_ID = mdmContext.getParamObject().GetValueByEnvName("UNIT_ID"," ");
    String     where = " (1=1) ";
    if (isColumnFKey("UNIT_ID", dctMetaData, mdmContext)) {
      where = "(UNIT_ID=' ' or UNIT_ID='" + UNIT_ID + "' or UNIT_ID is null)";
    }
    //自定义条件
    String selfWhere = mdmContext.getParamObject().GetValueByParamName(dctMetaData.getObjID() + "_MDMSelfWhere", "");
    if (selfWhere.trim().length() > 0) where += " and " + selfWhere;

//    tableName = DBTools.getDBAObject(mdmContext.getParamObject(),tableName);
    tableName = getDBAObject(mdmContext.getParamObject(),dctMetaData) + " " + tableName;
    String SQL = DBTools.selectAllSql(tableName,where);
    String bhc = dctMetaData.getDCT_BMCOLID();
    if (bhc != null && bhc.trim().length() > 0)
      SQL+=" ORDER BY "+dctMetaData.getDCT_BMCOLID();
    String js=dctMetaData.getDCT_JSCOLID();
    if(js!=null&&js.trim().length()>0)
      SQL+=","+js;
    return SQL;
  }

  /**
   *
   * @param column String
   * @param mdmContext MDMContext
   * @return boolean
   * @throws Exception
   */
  protected static boolean isColumnFKey(String column, DCTMetaData dctMetaData, MDMContext mdmContext) throws Exception {
    ESPRowSet rowSet = dctMetaData.getDoMetaData().getColumnDefineRow(column);
    if (rowSet == null) return false;
    return rowSet.getStringByTrim("COL_FOBJ", "").length() > 0;
  }

  /**
   *
   * @param PO JParamObject
   * @param meta DCTMetaData
   * @return String
   */
  protected static String getDBAObject(JParamObject PO, DCTMetaData meta) {
    return DBTools.getDBAObject(PO, meta);
  }

  /**
   select * from LSKMZD where UNIT_ID=' ' or UNIT_ID='5030' or UNIT_ID is null
   */


}
