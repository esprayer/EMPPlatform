package com.efounder.eai.service.dal;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.borland.dx.sql.dataset.Database;
import com.efounder.db.DBTools;
import com.efounder.eai.EAI;
import com.efounder.eai.data.JParamObject;
import com.efounder.eai.data.JResponseObject;
import com.efounder.eai.framework.JActiveObject;
import com.efounder.sql.JConnection;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class DBService extends JActiveObject {
  /**
   *
   */
  private static final String GUID = "00000000-0002-0001-0000000000000001";
  /**
   *
   */
  public DBService() {
    setObjectGUID(GUID);
  }
  /**
   * 对象信息,对象列信息,数据字典信息
   * @param DCT_ID String
   * @param isML Boolean
   * @param isQX boolean
   * @param o4 Object
   * @throws Excepton
   * @return Object
   */
  public Object getDCTObject(JParamObject PO,Object o2,Object o3,Object o4) throws Exception {
    String[] DCT_IDS = (String[])PO.getValue("DCT_IDS",null);
    String ISML   = PO.GetValueByParamName("ISML","1"); // 需要多语言
    String ISQX   = PO.GetValueByParamName("ISQX","0"); // 需要数据权限
    boolean isML  = "1".equals(ISML)?true:false;
    boolean isQX  = "1".equals(ISQX)?true:false;
    JConnection conn = null;Database dataBase = null;
    JResponseObject RO = null;Object[] dataSetDatas = new Object[DCT_IDS.length];
    try {
      // 获取数据库连接
      conn = DBUtils.getConnection(PO);
      // 形成Database组件
      dataBase = DBUtils.getDatabase(conn);
      // 获取每个数据字典的结构与数据
      for(int i=0;i<DCT_IDS.length;i++) {
        // 放到指定的数组中
        dataSetDatas[i] = DBUtils.getDCT_DATA(conn,dataBase, DCT_IDS[i], PO, isML, isQX);
      }
      // 形成JResponseObject返回对象
      RO = new JResponseObject(dataSetDatas,0);
    } catch ( Exception e ) {
      e.printStackTrace();throw e;
    } finally {
      conn.close();
    }
    return RO;
  }
  /**
   * 对象信息，对象列信息
   * @param TAB_ID String
   * @param o2 Object
   * @param o3 Object
   * @param o4 Object
   * @throws Exception
   * @return Object
   */
  public Object getTABObject(JParamObject PO,Object o2,Object o3,Object o4) throws Exception {
    String[] TAB_IDS = (String[])PO.getObject("TAB_IDS",null);
    JConnection conn = null;Database dataBase = null;
    JResponseObject RO = null;Object[] dataSetDatas = new Object[TAB_IDS.length];
    try {
      // 获取数据库连接
      conn = DBUtils.getConnection(PO);
      // 形成Database组件
      dataBase = DBUtils.getDatabase(conn);
      // 获取每个数据字典的结构与数据
      for(int i=0;i<TAB_IDS.length;i++) {
        // 放到指定的数组中
        dataSetDatas[i] = DBUtils.getTAB_DATA(dataBase, TAB_IDS[i], PO);
      }
      // 形成JResponseObject返回对象
      RO = new JResponseObject(dataSetDatas,0);
    } catch ( Exception e ) {
      e.printStackTrace();throw e;
    } finally {
      conn.close();
    }
    return RO;
  }
  /**
   *
   * @param DBO_ID String
   * @param o2 Object
   * @param o3 Object
   * @param o4 Object
   * @throws Exception
   * @return Object
   */
  public Object getDBOObject(JParamObject PO,Object o2,Object o3,Object o4) throws Exception {
    return null;
  }
  public JResponseObject SimpleExecute(JParamObject PO,
                                         Object data,
                                         Object CustomObject,
                                         Object AdditiveObject
   ) throws Exception {
   JConnection conn = null;
   Statement stmt = null;
   String sql="";
   try {
     conn = (JConnection) EAI.DAL.IOM("DBManagerObject", "GetDBConnection",
                                      PO, this);
//     DBTools.ChangeSchema(conn,DBTools.getDBOSchemaUser(PO));
     conn.setAutoCommit(false);
     stmt = conn.createStatement();
     if(data instanceof String){
       stmt.executeUpdate((String)data);
     }else if(data instanceof List){
       for (int i = 0; i < ((List)data).size(); i++) {
         sql = (String) ((List)data).get(i);
         stmt.executeUpdate(sql);
       }
     }
     conn.commit();
   }
   catch (Exception e) {
     conn.rollback();
     throw new Exception(sql,e);
   }
   finally {
     conn.BackStatement(stmt, null);
     conn.close();
   }
   return new JResponseObject();
}

  public JResponseObject SimpleUpdate(JParamObject PO,
                                          List list,
                                          Object CustomObject,
                                          Object AdditiveObject
    ) throws Exception {
    JConnection conn = null;
    Statement stmt = null;
    String sql="";
    try {
      conn = (JConnection) EAI.DAL.IOM("DBManagerObject", "GetDBConnection",
                                       PO, this);
//      DBTools.ChangeSchema(conn,DBTools.getDBOSchemaUser(PO));
      stmt = conn.createStatement();
      for(int i=0;i<list.size();i++){
        Map map=(Map)list.get(i);
        String table=(String) map.get("TABLE");
        String[] fields =(String[]) map.get("FIELD");
        String[] values = (String[])map.get("VALUE");
        String where =(String)map.get("WHERE");
         sql = DBTools.updateSql(table, fields, values, where);
        stmt.executeUpdate(sql);
      }
    }
    catch (Exception e) {
      throw new Exception(sql,e);
    }
    finally {
      conn.BackStatement(stmt, null);
      conn.close();
    }
    return new JResponseObject();
}
  public JResponseObject getBlobData(JParamObject PO,
                                          String table,
                                          String blobColumn,
                                          String where
     ) throws Exception {
    JConnection conn=null;
    Statement stmt=null;
    try {
      conn = (JConnection) EAI.DAL.IOM("DBManagerObject", "GetDBConnection",
                                       PO, this);
      stmt = conn.createStatement();
      ResultSet rs = stmt.executeQuery("SELECT " + blobColumn + " FROM " + table +
                                       " WHERE " + where);
      if (rs.next()) {
        Object o = conn.getSQLToolkit().getBlogData(rs, blobColumn);
        return new JResponseObject(o, 0);
      }
    }
    finally {
      conn.BackStatement(stmt,null);
      conn.close();
    }
      return new JResponseObject(null,0);
  }

  public JResponseObject SimpleQuery(JParamObject PO,
                                            List list,
                                            Object CustomObject,
                                            Object AdditiveObject
      ) throws Exception {
      JConnection conn = null;
      Statement stmt = null;
      String sql="";
      List dataList=new ArrayList();
      try {
        conn = (JConnection) EAI.DAL.IOM("DBManagerObject", "GetDBConnection",
                                         PO, this);
//        DBTools.ChangeSchema(conn,DBTools.getDBOSchemaUser(PO));
        stmt = conn.createStatement();
        dataList=DBUtils.SimpleQuery(conn,PO,list,stmt);
        return new JResponseObject(dataList);
      }
      finally {
        conn.BackStatement(stmt, null);
        conn.close();
      }
}

    public JResponseObject QueryEFDataSet(JParamObject PO,
                                       List list,
                                       Object CustomObject,
                                       Object AdditiveObject
        ) throws Exception {
      JConnection conn = null;
      Statement stmt = null;
      String sql = "";
      try {
        conn =JConnection.getInstance(PO);
//        DBTools.ChangeSchema(conn, DBTools.getDBOSchemaUser(PO));
        stmt = conn.createStatement();
        return new JResponseObject(DBUtils.QueryEFDataSet(PO,stmt));
      }
      finally {
        conn.BackStatement(stmt, null);
        conn.close();
      }
    }
}
