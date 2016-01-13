package com.efounder.eai.service.dal;

import com.efounder.sql.*;
import com.borland.dx.sql.dataset.*;
import com.efounder.eai.data.*;
import com.efounder.eai.*;
import com.efounder.db.*;
import com.borland.dx.dataset.*;
import com.efounder.service.security.*;
import java.util.*;
import java.sql.*;
import com.efounder.dbc.data.*;
import com.core.xml.StubObject;
import com.efounder.builder.base.data.*;
import com.efounder.eai.service.ParameterManager;
import com.core.xml.PackageStub;
import com.efounder.mdm.server.provider.util.DICTProviderUtils;
import com.efounder.builder.meta.dctmodel.DCTMetaDataManager;
import com.efounder.builder.meta.dctmodel.DCTMetaData;
import com.efounder.mdm.server.MDMContext;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class DBUtils {
  public DBUtils() {
  }
  /**
   *
   * @param PO JParamObject
   * @throws Exception
   * @return JConnection
   */
  public static JConnection getConnection(JParamObject PO) throws Exception {
    JConnection jconn = (JConnection)EAI.DAL.IOM("DBManagerObject","GetDBConnection", PO);
    return jconn;
  }
  /**
   *
   * @param conn JConnection
   * @throws Exception
   * @return Database
   */
  public static Database getDatabase(JConnection conn) throws Exception {
    Database dataBase = new Database();
    dataBase.setJdbcConnection(conn.getConnection());
    return dataBase;
  }
  /**
   *
   * @param dataBase Database
   * @param DCT_ID String
   * @param PO JParamObject
   * @return DataSetData[]
   */
  public static DataSetData[] getDCT_DATA(JConnection conn,Database dataBase,String DCT_ID,JParamObject PO,boolean isML,boolean isQX) {
    String SQL;DataSetData[] dataSetDatas= new DataSetData[4];DataSet dataSet = null;
    //1.获取对象信息
    QueryDataSet QDS = new QueryDataSet();
    SQL = getSYS_OBJSQL(DCT_ID,PO);
    dataSetDatas[0] = getDataSetData(QDS,dataBase,SQL);
    //2.获取对象列信息
    SQL = getSYS_COLSQL(DCT_ID,PO);
    dataSetDatas[1] = getDataSetData(QDS,dataBase,SQL);
    //3.获取数据字典信息
    SQL = getSYS_DCTSQL(DCT_ID,PO);
    dataSetDatas[2] = getDataSetData(QDS,dataBase,SQL);
    //形成DataSet，因为需要取数
    dataSet = getTableDataSet(dataSetDatas[2]);
    //4.获取数据字典的数据
    //add by luody
    if(dataSet != null)
    PO.SetValueByParamName("pscol",DCT_ID+"."+dataSet.getString("DCT_BMCOLID"));//add by luody
    //add end
    SQL = getDCTDataSQL(conn,DCT_ID,dataSet,PO,isML,isQX);
    dataSetDatas[3] = getDataSetData(QDS,dataBase,SQL);
    return dataSetDatas;
  }
  /**
   *
   * @param dataBase Database
   * @param TAB_ID String
   * @param PO JParamObject
   * @return DataSetData[]
   */
  public static DataSetData[] getTAB_DATA(Database dataBase,String TAB_ID,JParamObject PO) {
    String SQL;DataSetData[] dataSetDatas= new DataSetData[3];
    QueryDataSet QDS = new QueryDataSet();
    //1.获取对象信息
    SQL = getSYS_OBJSQL(TAB_ID,PO);
    dataSetDatas[0] = getDataSetData(QDS,dataBase,SQL);
    //2.获取对象列信息
    SQL = getSYS_COLSQL(TAB_ID,PO);
    dataSetDatas[1] = getDataSetData(QDS,dataBase,SQL);
    //3.获取数据字典的数据
    SQL = getTABDataSQL(TAB_ID,PO);
    dataSetDatas[2] = getDataSetData(QDS,dataBase,SQL);
    return dataSetDatas;

  }
  /**
   *
   * @param TAB_ID String
   * @param PO JParamObject
   * @return String
   */
  protected static String getTABDataSQL(String TAB_ID,JParamObject PO) {
    String DataSQL = null;
//    String Schema = DBTools.getDefault().getDBASchema(PO);//getMLangSchema();
//    String Postfix= DBTools.getDefault().getPostfix(PO);
//    DataSQL = "select * from "+Schema+"."+TAB_ID+Postfix;
    DataSQL = "select * from "+TAB_ID;
    return DataSQL;
  }
  /**
   * 字典数据肯定是多语言的
   * @param DCT_ID String
   * @return String
   */
  protected static String getDCTDataSQL(JConnection conn,String DCT_ID,DataSet dataSet,JParamObject PO,boolean isML,boolean isQX) {
    String DataSQL = null;
//    String Schema = isML?DBTools.getDefault().getMLangSchema():DBTools.getDefault().getDBASchema();
//    String Schema = isML?DBTools.getDefault().getDBASchema(PO):DBTools.getDefault().getDBASchema(PO);
//    String Postfix= DBTools.getDefault().getPostfix();
    String OrderBy= getOrderBy(dataSet);

//    DataSQL = "select * from "+Schema+"."+DCT_ID+Postfix + " " + DCT_ID;// 在这里可能需要处理数据权限,先不处理+" where DCT_ID='"+DCT_ID+"'";
    DataSQL = "select * from "+DCT_ID + " " + DCT_ID;// 在这里可能需要处理数据权限,先不处理+" where DCT_ID='"+DCT_ID+"'";
    String Where ="";
//    try {
//      Where = AccessController.getDefault().getAccessController2(conn, null, PO,DCT_ID, 1);
//    } catch ( Exception e ) {e.printStackTrace();}
    if ( Where != null && !"".equals(Where.trim()) ) {
      DataSQL += " where "+Where;
    }
    DataSQL += OrderBy;
    return DataSQL;
  }
  /**
   *
   * @param dataSet DataSet
   * @return String
   */
  protected static String getOrderBy(DataSet dataSet) {
    if ( dataSet == null ) return "";
    String BHField = dataSet.getString("DCT_BMCOLID").trim();
    String JSField = dataSet.getString("DCT_JSCOLID").trim();
    String OrderBy = " order by ";String OrderByPost = null;
    if ( BHField != null && !"".equals(BHField.trim()) )
      OrderByPost = BHField;
    if ( JSField != null && !"".equals(JSField.trim()) )
      OrderByPost += ","+JSField+" ";
    if ( OrderByPost != null )
      OrderBy += OrderByPost;
    else OrderBy = " ";
    return OrderBy;
  }

  /**
   * 获取对象信息
   * @param OBJ_ID String
   * @param PO JParamObject
   * @return String
   */
  public static String getSYS_OBJSQL(String OBJ_ID,JParamObject PO) {
    // 获取对象信息的SQL
//    String Schema = DBTools.getDefault().getDBASchema(PO);//getMLangSchema();
//    String Postfix= DBTools.getDefault().getPostfix(PO);
    // 确定获取对象的SQL
//    String OBJSql = "select * from "+Schema+".SYS_OBJECTS"+Postfix+" where OBJ_ID='"+OBJ_ID+"'";
	  String OBJSql = "select * from "+"SYS_OBJECTS"+" where OBJ_ID='"+OBJ_ID+"'";
    return OBJSql;
  }
  /**
   * 获取对象列信息
   * @param OBJ_ID String
   * @param PO JParamObject
   * @return String
   */
  public static String getSYS_COLSQL(String OBJ_ID,JParamObject PO) {
//    // 获取对象信息的SQL
//    String Schema = DBTools.getDefault().getDBASchema(PO);//getMLangSchema();
//    String Postfix= DBTools.getDefault().getPostfix(PO);
//    String COLSql = "select * from "+Schema+".SYS_OBJCOLS"+Postfix+" where OBJ_ID='"+OBJ_ID+"' order by COL_DISP";
	  String COLSql = "select * from "+".YS_OBJCOLS"+" where OBJ_ID='"+OBJ_ID+"' order by COL_DISP";
    return COLSql;
  }
  /**
   * 获取数据字典信息
   * @param DCT_ID String
   * @param PO JParamObject
   * @return String
   */
  public static String getSYS_DCTSQL(String DCT_ID,JParamObject PO) {
    // 获取对象信息的SQL
//    String Schema = DBTools.getDefault().getDBASchema(PO);//getMLangSchema();
//    String Postfix= DBTools.getDefault().getPostfix(PO);
//    String DCTSql = "select * from "+Schema+".SYS_DICTS"+Postfix+" where DCT_ID='"+DCT_ID+"'";
	  String DCTSql = "select * from "+".SYS_DICTS"+" where DCT_ID='"+DCT_ID+"'";
    return DCTSql;
  }
  /**
   *
   * @param dataBase Database
   * @param SQL String
   * @return DataSetData
   */
  public static DataSetData getDataSetData(QueryDataSet QDS,Database dataBase,String SQL) {
    // 获取QueryDataSet
//    QueryDataSet QDS = getQueryDataSet(dataBase,SQL);
    getQueryDataSet(QDS,dataBase,SQL);
    DataSetData DSD=null;
    // 从QueryDataSet中提取DataSetData
    DSD = DataSetData.extractDataSet(QDS);
    // 关闭QueryDataSet
    QDS.close();
    return DSD;
  }
  /**
   *
   * @param dataBase Database
   * @param SQL String
   * @return QueryDataSet
   */
  public static QueryDataSet getQueryDataSet(QueryDataSet QDS,Database dataBase,String SQL) {
//    QueryDataSet QDS = new QueryDataSet();
    if ( QDS == null ) QDS = new QueryDataSet();
    QueryDescriptor qd = null;DataSetData DSD=null;
    qd = new QueryDescriptor(dataBase, SQL, null, true, Load.ALL);

    String value=ParameterManager.getDefault().getSystemParam("SQL_MONITOR");
       if(value==null || value.trim().length() == 0 )value="0";
       int iv=Integer.parseInt(value);
    if ((iv&1)!=0){
        System.out.println(SQL);
    }
    QDS.setQuery(qd);    QDS.open();
    return QDS;
  }
  /**
   *
   * @param dataSetData DataSetData
   * @return DataSet
   */
  public static DataSet getTableDataSet(DataSetData dataSetData) {
    TableDataSet tds = new TableDataSet();
    dataSetData.loadDataSet(tds);
    return tds;
  }
  public static boolean executeSQLList(Statement stmt,List sqlList,List resultList,boolean exceptoin) throws
      Exception {
    int j=0;
    DataItem di=null;
    boolean b=true;
    String sql="",des="";
    for (j = 0; j < sqlList.size(); j++) {
        Object o=sqlList.get(j);
        if(o instanceof DataItem){
            di = (DataItem) sqlList.get(j);
            sql=di.getBh();
            des=di.getName();
        }else{
            sql = (String) o;
            des="";
        }
        try {
            stmt.executeUpdate(sql);
            if(resultList!=null)
                resultList.add(des+ " 成功！");
        } catch (Exception e) {
            String err = "SQL=" + sql + " ERROR=" + e.getLocalizedMessage() ;
            System.out.println(err);
            if(resultList!=null)
                resultList.add(err);
            if (exceptoin)
                throw e;
            b = false;
        }
      }

    return b;
  }
  public static List SimpleQuery(JConnection conn,JParamObject PO,List list,Statement stmt) throws Exception {
    String sql;
    List dataList=new ArrayList();
    for(int i=0;i<list.size();i++){
         Map map=(Map)list.get(i);
         String table=(String) map.get("TABLE");

         //表名增加用户前缀
         if (table.indexOf(".") < 0) {
//             table = DBTools.getDBAObject(PO, table);
         }
         String where =(String)map.get("WHERE");
         if(where==null)where="1=1";
         String fields =(String)map.get("FIELD");
         String group =(String)map.get("GROUP");
         if(map.get("QXBH")!=null){//可权限过滤
           PO.SetValueByParamName("pszgbh",PO.GetValueByEnvName("UserName"));
           PO.SetValueByParamName("psqxbh",(String)map.get("QXBH"));
           PO.SetValueByParamName("pscol",(String)map.get("QXCOL"));
           PO.SetValueByParamName("pibzw",(String)map.get("QXW"));
//           String qxwhere=DBOSecurityObject.CheckDataLimit2(conn,PO);
//           where+=" and "+qxwhere;
         }
         if(group!=null)
           where+=" GROUP BY "+group;
         if(map.get("ORDER")!=null)
           where+=" ORDER BY "+map.get("ORDER");
         if(fields==null)
          sql = DBTools.selectAllSql(table, where);
        else
          sql=DBTools.selectSql(table,fields,where);
        List tmpList=new ArrayList();
        try{
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                StubObject so = new StubObject();
                DBTools.ResultSetToStubObject(rs, so);
                tmpList.add(so);
            }
            rs.close();
        }catch(Exception e){
            throw new Exception(sql,e);
        }
         dataList.add(tmpList);
       }
       //如果只有一个查询，就不返回多重的list了
       if(dataList.size()==1)dataList=(List)dataList.get(0);
       return dataList;
  }

  /**
   *
   * @param dctid String
   * @param type int
   * @return boolean
   */
  public static boolean isMDMProviderType(String dctid) {
    java.util.Vector v = PackageStub.getContentVector("MDMProviderType");
    StubObject s = null; String providerType = null;String[] providerTypes = null;
    for (int i = 0; v != null && i < v.size(); i++) {
      s = (StubObject) v.get(i);
      // 如果配置了，则按配置进行检查
      if (s == null || !s.getString("dctid", "").toUpperCase().equals(dctid.toUpperCase()))
        continue;
      providerType = s.getString("providerType", null);
      if (providerType != null && providerType.trim().length() > 0)
        return true;
    }
    return false;
  }

  public static EFDataSet QueryEFDataSet(JParamObject PO,Statement stmt,String table,String Column,String where,String GROUP,String ORDER,String[]keys) throws Exception {
//    String sql="SELECT "+Column+" from "+DBTools.getDBAObject(PO,table);
	  String sql="SELECT "+Column+" from "+table;
    if(where!=null)
      sql+=" WHERE "+where;
    if(GROUP!=null)sql+=" GROUP BY "+GROUP;
    if(ORDER!=null)sql+=" ORDER BY "+ORDER;

    // 控制主数据的加载（配置则生效） add by gengeng 2011-12-27
    if (isMDMProviderType(table)) {
      com.efounder.sql.JConnection conn = null;
      // ESP
      conn = (JConnection) stmt.getConnection();
//      if (stmt instanceof com.efounder.sql.DelegateStatement) {
//        conn = ((com.efounder.sql.DelegateStatement)stmt).getJConnection();
//      } else if (stmt instanceof jfoundation.sql.classes.JStatement) {
//        jfoundation.sql.classes.JConnection oldConn = (jfoundation.sql.classes.JConnection)stmt.getConnection();
//        conn = oldConn.getESPConnection();
//      }
      MDMContext espContext = MDMContext.getInstance(PO, conn);
      espContext.setStatement(stmt);
      PO.SetValueByParamName(table + "_MDMSelfWhere", where);
      DCTMetaData dctMeta = (DCTMetaData) DCTMetaDataManager.getDCTMetaDataManager().getMetaData(espContext, table);
      EFDataSet dataSet = DICTProviderUtils.getDICTData(dctMeta, espContext);
      if (keys != null) {
        dataSet.setPrimeKey(keys);
        dataSet.buildPrimeKeyIndex();
      }
      return dataSet;
    }

    EFDataSet  dataSet = EFDataSet.getInstance(table);
    if(keys!=null)
      dataSet.setPrimeKey(keys);
    ResultSet rs=stmt.executeQuery(sql);
    DataSetUtils.resultSet2DataSet(rs, dataSet);
    rs.close();
    // 监控数据量
    try {
      String str = ParameterManager.getDefault().getSystemParam("MDMonitorCount");
      int count = 10000;
      if (str != null && str.trim().length() > 0)
        count = Integer.parseInt(str);
      DataSetUtils.monitorLargeData(dataSet, table, sql, count);
    } catch (Exception ex) {
    	ex.printStackTrace();
    }

    return dataSet;
  }

  public static EFDataSet QueryEFDataSet(JParamObject PO,Statement stmt) throws Exception {
    String table = PO.GetValueByParamName("TABLE");
    String where = PO.GetValueByParamName("WHERE","1=1");
    String Column = PO.GetValueByParamName("COLUMN", "*");
    String GROUP = PO.GetValueByParamName("GROUP", null);
    String ORDER = PO.GetValueByParamName("ORDER", null);
    String[]keys=(String[])PO.getValue("KEYS",null);
    return QueryEFDataSet(PO,stmt,table,Column,where,GROUP,ORDER,keys);
  }

}
