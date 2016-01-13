package com.efounder.eai.service.dal.DALDBManagerObject.mysql;

import java.sql.*;

import com.core.xml.*;
import com.efounder.eai.data.*;
import com.efounder.eai.service.*;
import com.efounder.sql.*;
import javax.sql.DataSource;
import com.efounder.db.*;
import javax.naming.*;
import com.efounder.eai.service.dal.DALDBManagerObject.JDataSourceStub;
import com.efounder.dbservice.data.AccountStub;
import com.efounder.dbservice.data.DataStorageStub;
import com.efounder.eai.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */
public class JDALMySqlDBObject extends EAIConnection {
  /**
   *
   */
  protected Object CustomDBManager = null;

  public JDALMySqlDBObject() {
  }
  
  public Object InitConnection(Object ParamObject,Object DataObject,Object CustomObject,Object AdditiveObject) throws Exception {
    JParamObject PO = (JParamObject)ParamObject;String Product = PO.GetValueByEnvName("Product",null);
    JDataSourceStub dataSourceStub = (JDataSourceStub)DataObject;
    AccountStub accountStub = (AccountStub)CustomObject;
    DataStorageStub dataStorageStub = (DataStorageStub)AdditiveObject;
    String user = accountStub.getUserId();
    String pass = accountStub.getUserpass();
    String dataSource = dataStorageStub.getDataSource();//accountStub.getDataSource();
    String dataDBUser = dataStorageStub.getDBUser(); //数据库用户
//    if ( dataSource == null || "".equals(dataSource) ) {
      String Host    = dataStorageStub.getDBHost();//accountStub.getDataStorageHost();
      String Port    = dataStorageStub.getDBPort();//accountStub.getDataStoragePort();
      String DBName  = dataStorageStub.getDataBaseName();
      String jdbcURL = "jdbc:mysql://" + Host + ":" + Port + "/" + DBName;
      connection = DriverManager.getConnection(jdbcURL, user, pass);
////      ((OracleConnection)connection).applyConnectionAttributes(properties);
////      ((oracle.jdbc.driver.OracleConnection)connection);
//    } else {
//      DataSource ds =null;
//      try{
//         ds = (DataSource)this.getDBContext().lookup("jdbc/" + dataSource);
//      }catch(Exception e){
//        Context context = new InitialContext();
//        Context envContext = (Context) context.lookup("java:/comp/env");
//        ds = (DataSource) envContext.lookup("jdbc/" + dataSource);
//      }
//      if ( ds != null ) {
//          if (dataDBUser != null && dataDBUser.trim().length() > 0) connection = ds.getConnection(user, pass);
//          else connection = ds.getConnection(); //weblogic
//      }
//    }
    return connection;
  }
}
