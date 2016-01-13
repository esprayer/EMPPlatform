package com.efounder.eai.service.dal;

import java.sql.*;
import java.util.*;

import com.efounder.eai.data.*;
import com.efounder.eai.framework.*;
import com.efounder.eai.service.dal.DALDBManagerObject.JDataSourceStub;
import com.efounder.sql.*;
import com.core.xml.*;
import javax.sql.DataSource;

import com.efounder.service.config.ConfigManager;
import com.efounder.dbservice.data.AccountStub;
import com.efounder.dbservice.data.DataStorageStub;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */
public class JDALDBManagerObject extends JActiveObject {
  private static java.util.Vector DBList = new java.util.Vector();
  private static java.util.Hashtable CustomDBList = new Hashtable();
  /**
   *
   */
  public JDALDBManagerObject() {
  }
  /**
   *
   * @param paramObject JParamObject
   * @return JConnection
   * @throws Exception
   */
  public JConnection getConnection(JParamObject paramObject, Object Data, Object CustomObject, Object AdditiveObject) throws Exception {
    JConnection conn = null;
    AccountStub accountStub = getAccountStub(paramObject);
    if ( accountStub == null ) return null;
    // 设置dbOwner
    paramObject.SetValueByEnvName("dbOwner",accountStub.getUserId());
    DataStorageStub dataStorageStub = getDataStorageStub(accountStub);
    if ( dataStorageStub == null ) return null;
    JDataSourceStub dataSourceStub = (JDataSourceStub)getDataSourceStub(dataStorageStub);
    conn  = createConnection(dataSourceStub);
    return initConnection(conn,paramObject,dataSourceStub,accountStub,dataStorageStub);
  }
  
  /**
   *
   * @param conn JConnection
   * @param PO JParamObject
   * @param DSS JDataSourceStub
   * @param custObj Object
   * @return JConnection
   * @throws Exception
   */
  protected static JConnection initConnection(JConnection conn,JParamObject PO,
                                              JDataSourceStub dataSourceStub,
                                              AccountStub accountStub,
                                              DataStorageStub dataStorageStub) throws Exception{
    if ( conn.InitConnection(PO, dataSourceStub, accountStub, dataStorageStub ) == null) return null;
//    if (PO.isAutoConnection()) {
//      PO.addConn(conn);
//    }
    // 写入日志
    writeLoginfo(conn,PO);
    //add by fsz
    List sqlList=(List) PO.getValue("$$SQL_VIEWLIST",null);
    if(sqlList!=null){
//      conn.setSqlLogList(sqlList);
    }
    return conn;
  }
  /**
   *
   * @param conn JConnection
   * @param PO JParamObject
   * @throws Exception
   */
  protected static void writeLoginfo(JConnection conn,JParamObject paramObject) throws Exception {
//    String tableName = DBTools.getDBAObject(paramObject,"SYS_OPLOG");
//    String LOG_ID = (String)paramObject.getValue("_sessionID_",null);
//    String Fields = "LOG_ID,F_GNBH,F_GNMC,F_STIME,F_ETIME,F_SJ";
//    Statement stmt = conn.GetStatement(conn);
//    SimpleDateFormat formator = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//    try {
//      for (int i = 0; i < list.size(); i++) {
//        OperateLoginfo ql = (OperateLoginfo) list.get(i);
//        String F_GNBH = ql.getString("F_GNBH", null);
//        String F_GNMC = ql.getString("F_GNMC", null);
//        long time = ql.getLong("F_STIME", 0);
//        java.util.Date date = new java.util.Date(time);
////        String F_STIME = date.toLocaleString();
//        String F_STIME = formator.format(date);
//        time = ql.getLong("F_ETIME", 0);
//        date = new java.util.Date(time);
////        String F_ETIME = date.toLocaleString();
//        String F_ETIME = formator.format(date);
//        String Values = "'"+LOG_ID+"','" + F_GNBH + "','" + F_GNMC + "','" + F_STIME + "','" + F_ETIME + "',null";
//        String SQL = DBTools.insertSql(tableName, Fields, Values);
//        stmt.execute(SQL);
//      }
//    } catch ( Exception ex ) {
//      ex.printStackTrace();
//    } finally {
//      stmt.close();
//    }
  }
  /**
     LOG_ID      VARCHAR2(255)  not null,
     F_GNBH      VARCHAR2(30)  not null,
     F_GNMC      VARCHAR2(255) null,
     F_SJ        VARCHAR2(255) null,
     F_STIME     VARCHAR2(30)  null,
     F_ETIME     VARCHAR2(30)  null
   */
  /**
   *
   * @param dataSourceStub JDataSourceStub
   * @return JConnection
   * @throws Exception
   */
  protected static JConnection createConnection(JDataSourceStub dataSourceStub) throws Exception {
    JConnection jconn = null;
    if (dataSourceStub.DBClass == null) {
      dataSourceStub.DBClass = Class.forName(dataSourceStub.classname);
    }
    if (dataSourceStub.NAClass == null) {
      dataSourceStub.NAClass = Class.forName(dataSourceStub.dbclass);
    }
    if (dataSourceStub.NAClass != null) {
      jconn = (JConnection) dataSourceStub.NAClass.newInstance();
    }
    if ( jconn != null ) jconn.setDataBaseType(dataSourceStub.DataBaseType);
    return jconn;
  }
  /**
   *
   * @param loginStub LoginStub
   * @return AccountStub
   * @throws Exception
   */
  protected static AccountStub getAccountStub(JParamObject loginStub) throws Exception {
    java.util.List accountSetList = (List)ConfigManager.getDefault().getCfgData("AccountSet",null);
    if ( accountSetList == null || accountSetList.size() == 0 ) return null;
    AccountStub accountStub = null;
    for(int i=0;i<accountSetList.size();i++) {
      accountStub = (AccountStub)accountSetList.get(i);
      if ( accountStub.getDataStorageId().equals(loginStub.GetValueByEnvName("DataBaseName",null)) &&
           accountStub.getAccountId().equals(loginStub.GetValueByEnvName("DBNO",null)) ) {
        return accountStub;
      }
    }
    return null;
  }
  /**
   *
   * @param accountStub AccountStub
   * @return DataStorageStub
   * @throws Exception
   */
  protected static DataStorageStub getDataStorageStub(AccountStub accountStub) throws Exception {
    java.util.List dataStorageList = (List)ConfigManager.getDefault().getCfgData("StorageService",null);
    if ( dataStorageList == null ) return null;DataStorageStub dss = null;
    String dataStorageID = accountStub.getDataStorageId();
    for(int i=0;i<dataStorageList.size();i++) {
      dss = (DataStorageStub)dataStorageList.get(i);
      if ( dataStorageID.equals(dss.getDataStorageId()) ) return dss;
    }
    return null;
  }
  /**
   *
   * @param accountStub AccountStub
   * @return JDataSourceStub
   * @throws Exception
   */
  protected static JDataSourceStub getDataSourceStub(DataStorageStub dataStorageStub) throws Exception {
    String dbType = dataStorageStub.getDBType();JDataSourceStub DSS;
    for (int i = 0; i < DBList.size(); i++) {
      DSS = (JDataSourceStub) DBList.get(i);
      if (DSS.DataBaseType.toLowerCase().equals(dbType.toLowerCase())) return DSS;
    }
    return null;
  }
  /**
   *
   * @param ParamObject Object
   * @param DataObject Object
   * @param CustomObject Object
   * @param AdditiveObject Object
   * @return Object
   * @throws Exception
   */
  public Object GetDBConnection(Object ParamObject, Object DataObject,
                                Object CustomObject, Object AdditiveObject) throws Exception {
    return getConnection((JParamObject)ParamObject, null, null, null);
  }
  
  /**
   *
   * @param ParamObject Object
   * @param DataObject Object
   * @param CustomObject Object
   * @param AdditiveObject Object
   * @return Object
   * @throws Exception
   */
//  public Object getStorageConnection(Object ParamObject, Object DataObject, Object CustomObject, Object AdditiveObject) throws Exception {
//	  JConnection conn = null;
//	  DataStorageStub dataStorageStub= (DataStorageStub) DataObject;
//	  if ( dataStorageStub == null ) return null;
//	  JDataSourceStub dataSourceStub = (JDataSourceStub)getDataSourceStub(dataStorageStub);
//	  conn  = createConnection(dataSourceStub);
//	  return initConnection(conn,paramObject,dataSourceStub,accountStub,dataStorageStub);     
//  }

  /**
   *
   * @param ParamObject Object
   * @param DataObject Object
   * @param CustomObject Object
   * @param AdditiveObject Object
   * @return Object
   * @throws Exception
   */
  public Object CheckDBDataSource(Object ParamObject, Object DataObject,
                                  Object CustomObject, Object AdditiveObject) throws
      Exception {
    JParamObject PO;
    String DBType;
    JDataSourceStub DSS;
    JConnection jconn = null;
    boolean hasDataSource = false;
    PO = (JParamObject) ParamObject;
    DBType = PO.GetValueByEnvName("DataBaseType");
    for (int i = 0; i < DBList.size(); i++) {
      DSS = (JDataSourceStub) DBList.get(i);
      if (DSS.DataBaseType.toLowerCase().equals(DBType.toLowerCase()) == true) {
        if (DSS.DBClass == null) {
          DSS.DBClass = Class.forName(DSS.classname);
        }
        if (DSS.NAClass == null) {
          DSS.NAClass = Class.forName(DSS.dbclass);
        }
        if (DSS.NAClass != null) {
          jconn = (JConnection) DSS.NAClass.newInstance();
          try{
            hasDataSource = lookDataSource(jconn, PO);
          }
          catch(Exception e){
            e.printStackTrace();
          }
          if(hasDataSource) break;
        }
      }
    }
    if (!hasDataSource) {
      throw new Exception("ϵͳû���ṩ" + DBType + "������ݿ�������!");
    }
    if(hasDataSource) return jconn;
    return null;
  }

  boolean lookDataSource(JConnection jconn, JParamObject PO) throws Exception {
    boolean hasDataSource = false;
    if (jconn != null) {
      String dataSource = PO.GetValueByEnvName("DataSource");
      if (dataSource != null && !"".equals(dataSource)) {
        DataSource ds = null;
//        try {
//          ds = (DataSource) jconn.getDBContext().lookup("jdbc/" +
//              dataSource);
//        }
//        catch (Exception e) {
//          Context context = new InitialContext();
//          Context envContext = (Context) context.lookup(
//              "java:/comp/env");
//          ds = (DataSource) envContext.lookup("jdbc/" + dataSource);
//
//        }
        if (ds != null) {
          jconn.getInstance(ds.getConnection());
          hasDataSource = true;
        }
      }
    }
    return hasDataSource;
  }

  /**
  *
  * @param ParamObject Object
  * @param DataObject Object
  * @param CustomObject Object
  * @param AddeitiveObject Object
  * @return Object
  */
 public Object InitObject(Object ParamObject, Object DataObject,
                          Object CustomObject, Object AddeitiveObject) {
   // ��ȡ��ݿ����б�
   java.util.List dblist = PackageStub.getContentVector("databases");
   if (dblist == null) {
     return null;
   }
   StubObject SO = null;
   for (int i = 0; i < dblist.size(); i++) {
     SO = (StubObject) dblist.get(i);
     if (SO == null) {
       continue;
     }
     JDataSourceStub DSS = new JDataSourceStub();
     DSS.DataBaseType = SO.getString("id", null); // EAI.Registry.GetElementValue(DBElement,"DataBaseType");
     DSS.classname = SO.getString("class", null); //.Registry.GetElementValue(DBElement,"classname");
     DSS.dbclass = SO.getString("dbclass", null); //EAI.Registry.GetElementValue(DBElement,"dbclass");
     DBList.add(DSS);
   }
   return null;
 }
 
  //------------------------------------------------------------------------------------------------
  //����:
  //���: Skyline(2001.12.29)
  //ʵ��: Skyline
  //�޸�:
  //------------------------------------------------------------------------------------------------
  static void deregisterDriver(Driver driver) {

  }

  //------------------------------------------------------------------------------------------------
  //����:
  //���: Skyline(2001.12.29)
  //ʵ��: Skyline
  //�޸�:
  //------------------------------------------------------------------------------------------------
  static Connection getConnection(String url) {
    return null;
  }

  //------------------------------------------------------------------------------------------------
  //����:
  //���: Skyline(2001.12.29)
  //ʵ��: Skyline
  //�޸�:
  //------------------------------------------------------------------------------------------------
  static Connection getConnection(String url, Properties info) {
    return null;
  }

  //------------------------------------------------------------------------------------------------
  //����:
  //���: Skyline(2001.12.29)
  //ʵ��: Skyline
  //�޸�:
  //------------------------------------------------------------------------------------------------
  static Connection getConnection(String url, String user, String password) {
    return null;
  }

  //------------------------------------------------------------------------------------------------
  //����:
  //���: Skyline(2001.12.29)
  //ʵ��: Skyline
  //�޸�:
  //------------------------------------------------------------------------------------------------
  static Driver getDriver(String url) {
    return null;
  }

  //------------------------------------------------------------------------------------------------
  //����:
  //���: Skyline(2001.12.29)
  //ʵ��: Skyline
  //�޸�:
  //------------------------------------------------------------------------------------------------
  static Enumeration getDrivers() {
    return null;
  }

  //------------------------------------------------------------------------------------------------
  //����:
  //���: Skyline(2001.12.29)
  //ʵ��: Skyline
  //�޸�:
  //------------------------------------------------------------------------------------------------
  static int getLoginTimeout() {
    return 0;
  }

  //------------------------------------------------------------------------------------------------
  //����:
  //���: Skyline(2001.12.29)
  //ʵ��: Skyline
  //�޸�:
  //------------------------------------------------------------------------------------------------
  static void registerDriver(Driver driver) {

  }

  //------------------------------------------------------------------------------------------------
  //����:
  //���: Skyline(2001.12.29)
  //ʵ��: Skyline
  //�޸�:
  //------------------------------------------------------------------------------------------------
  static void setLoginTimeout(int seconds) {

  }
}
