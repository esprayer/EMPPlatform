package com.efounder.eai.service.dal.DALDBManagerObject.oracle;

import java.io.*;
import java.sql.*;
import java.util.*;

import com.efounder.db.*;
import com.efounder.sql.*;
import com.core.xml.StubObject;
import java.lang.reflect.Method;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class JOracleSQLToolkit extends JSQLToolkit {
  public JOracleSQLToolkit() {
  }
  public void insertBlobData(JConnection conn,String Table,String Fields,String[] BlobField,String Values,String Where,Object[] BlobValue) throws Exception {
    String bfields=BlobField[0];
    String bvalues="EMPTY_BLOB()";
    for(int i=1;i<BlobField.length;i++){
      bfields += "," + BlobField[i];
      bvalues +=",EMPTY_BLOB()";
    }
    String vsSql  = "INSERT INTO "+Table+" ("+Fields+","+bfields+") values (";
          vsSql += Values + ","+bvalues+")";
   Statement stmt = conn.GetStatement(conn);
   conn.executeUpdate(stmt,"delete "+Table+" WHERE "+Where);
   conn.executeUpdate(stmt,vsSql);
   vsSql = "SELECT "+bfields+" FROM "+Table+" WHERE "+Where+" FOR UPDATE";
   ResultSet rs = conn.executeQuery(stmt,vsSql);
//   BLOB voBol=null;
//   if (rs.next()) {
//     for(int i=0;i<BlobField.length;i++){
//         //
//         java.sql.Blob srcBlob = rs.getBlob(BlobField[i]);
//         Class clazz = srcBlob.getClass();
//         Method method = clazz.getMethod("getBinaryOutputStream", new Class[]{});
//         OutputStream out = (OutputStream)method.invoke(srcBlob, new Object[]{});
////       voBol=(BLOB)rs.getBlob(BlobField[i]);
////       OutputStream out = voBol.getBinaryOutputStream();
//         byte[]bt=(byte[])BlobValue[i];
//         out.write(bt, 0, bt.length);
//         out.close();
//     }
//   }
   conn.BackStatement(stmt,rs);

  }
  /**
   *
   * @param conn JConnection
   * @param Table String
   * @param Fields String
   * @param BlobField String
   * @param Values String
   * @param where String
   * @param BlobValue byte[]
   * @throws Exception
   */
  public void updateBlobData(JConnection conn,String Table,String BlobField,String Where,byte[] BlobValue) throws Exception {
    Statement stmt = conn.GetStatement(conn);
    String vsSql = "SELECT " + BlobField + " FROM " + Table + " WHERE " + Where +
        " FOR UPDATE";
    ResultSet rs = conn.executeQuery(stmt, vsSql);
//    BLOB voBol = null;
    java.sql.Blob voBol = null;
    OutputStream out=null;
    if (rs.next()) {
//      voBol = (BLOB) rs.getBlob(1);
      voBol = rs.getBlob(1);
      if(voBol==null){
        String upsql="UPDATE "+Table+" SET "+BlobField+"=EMPTY_BLOB() WHERE "+Where;
        stmt.executeUpdate(upsql);
        rs = conn.executeQuery(stmt, vsSql);
        if(rs.next()){
//          voBol = (BLOB) rs.getBlob(1);
//          out = voBol.getBinaryOutputStream();
          voBol = rs.getBlob(1);
//          Class clazz = srcBlob.getClass();
//          Method method = clazz.getMethod("getBinaryOutputStream", new Class[]{});
//          out = (OutputStream)method.invoke(srcBlob, new Object[]{});
        }
      }
//      else
//         out = voBol.getBinaryOutputStream();
       Class clazz = voBol.getClass();
       Method method = clazz.getMethod("getBinaryOutputStream", new Class[]{});
       out = (OutputStream)method.invoke(voBol, new Object[]{});

      if(out!=null){
        out.write(BlobValue, 0, BlobValue.length);
        out.close();
      }
    }
    conn.BackStatement(stmt, rs);
}

  public void insertBlobData(JConnection conn,String Table,String Fields,String BlobField,String Values,String Where,byte[] BlobValue) throws Exception {
    String vsSql  = "INSERT INTO "+Table+" ("+Fields+","+BlobField+") values (";
           vsSql += Values + ",EMPTY_BLOB())";
    Statement stmt = conn.GetStatement(conn);
    conn.executeUpdate(stmt,vsSql);
    vsSql = "SELECT "+BlobField+" FROM "+Table+" WHERE "+Where+" FOR UPDATE";
    ResultSet rs = conn.executeQuery(stmt,vsSql);
//    BLOB voBol=null;
//    if (rs.next()) {
////      voBol=(BLOB)rs.getBlob(1);
////      OutputStream out = voBol.getBinaryOutputStream();
//        java.sql.Blob srcBlob = rs.getBlob(1);
//        Class clazz = srcBlob.getClass();
//        Method method = clazz.getMethod("getBinaryOutputStream", new Class[]{});
//        OutputStream out = (OutputStream)method.invoke(srcBlob, new Object[]{});
//        out.write(BlobValue,0,BlobValue.length);
//        out.close();
//    }
    conn.BackStatement(stmt,rs);
  }
//  public Object[] selectBlobData(JConnection conn,String Table,String BlobField,String Where) throws Exception {
//    return null;
//  }
  public Object getBlogData(ResultSet rs,String BlobField)throws Exception {
    ByteArrayOutputStream bout = null;
    Blob blob = null;
    blob = rs.getBlob(BlobField);
    if (blob != null) {
      InputStream is = blob.getBinaryStream();
//      int Length = ( (BLOB) blob).getBufferSize();
      int Length = 0;
      Class clazz = blob.getClass();
      Method method = clazz.getMethod("getBufferSize", new Class[]{});
      Object object = method.invoke(blob, new Object[]{});
      if (object != null && object instanceof Integer) {
          Length = ((Integer)object).intValue();
      } else {
          Length = (int)blob.length();
      }
      byte[] data = new byte[Length];
      int readLength = 0;
      while ( (readLength = is.read(data)) > 0) {
        if (bout == null)
          bout = new ByteArrayOutputStream();
        bout.write(data,0,readLength);
      }
      if (bout != null) {
        data = bout.toByteArray();
        bout.close();
      }
      return data;
    }
    return null;
  }
  public Object selectBlobData(JConnection conn,String Table,String BlobField,String Where) throws Exception {
    String vsSql = "SELECT " + BlobField + " FROM " + Table + " WHERE " + Where;
    Statement stmt = conn.GetStatement(conn);
    ResultSet rs = conn.executeQuery(stmt,vsSql);
    ByteArrayOutputStream bout = null;
    if ( rs.next() ) {
      Blob blob=null;
      blob = rs.getBlob(BlobField);
      if ( blob != null ) {
        InputStream is = blob.getBinaryStream();
//        int Length = (int)blob.length();
//        int Length = ( (BLOB) blob).getBufferSize();
        int Length = 0;
        Class clazz = blob.getClass();
        Method method = clazz.getMethod("getBufferSize", new Class[]{});
        Object object = method.invoke(blob, new Object[]{});
        if (object != null && object instanceof Integer) {
            Length = ((Integer)object).intValue();
        }else {
            Length = (int)blob.length();
        }

        byte[] data = new byte[Length];
        int readLength = 0;
        while ( (readLength = is.read(data)) > 0) {
          if (bout == null)
            bout = new ByteArrayOutputStream();
          bout.write(data,0,readLength);
        }
        conn.BackStatement(stmt, rs);
        if (bout != null) {
          data = bout.toByteArray();
          bout.close();
        }
        return data;
      }
    }
    return null;
  }
  /**
   *
   * @param conn JConnection
   * @param conUser String
   * @param conPass String
   * @throws Exception
   */
  public void changePassword(JConnection conn,String conUser,String conPass)throws Exception {
    String vsSql = "alter user "+conUser+" identified by \""+conPass+"\"";
    Statement stmt = conn.GetStatement(conn);
    try {
      stmt.executeUpdate(vsSql);
    } catch ( Exception e ) {
     throw e;
    } finally {
      stmt.close();
    }
  }

  /**
   *
   * @param conn JConnection
   * @param tablename String
   * @param owner String
   * @return List
   */
  public java.util.List getObjectMetaData(JConnection conn, String tablename,
                                          String owner) throws Exception {
      String strsql = " select * from USER_OBJECTS where OBJECT_NAME = '" + tablename + "'";
      Statement  st = null;
      ResultSet  rs = null;
      List presults = new ArrayList();
      try {
          st = conn.createStatement();
          rs = st.executeQuery(strsql);
          while (rs.next()) {
              StubObject so = new StubObject();
              DBTools.ResultSetToStubObject(rs, so);
              presults.add(so);
          }
      } catch (Exception ex) {
          throw ex;
      } finally {
          conn.BackStatement(st, rs);
      }
      return presults;
  }

  /**
   *
   * @param conn JConnection
   * @param tablename String
   * @param owner String
   * @return List
   */
  public java.util.List getTableMetaData(JConnection conn, String tablename,
                                         String owner) throws Exception {
      String strsql = " select * from USER_TABLES where TABLE_NAME = '" + tablename + "'";
      Statement  st = null;
      ResultSet  rs = null;
      List presults = new ArrayList();
      try {
          st = conn.createStatement();
          rs = st.executeQuery(strsql);
          while (rs.next()) {
              StubObject so = new StubObject();
              DBTools.ResultSetToStubObject(rs, so);
              presults.add(so);
          }
      } catch (Exception ex) {
          throw ex;
      } finally {
          conn.BackStatement(st, rs);
      }
      return presults;
  }

  /**
   *
   * @param conn JConnection
   * @param tablename String
   * @param owner String
   * @return List
   */
  public java.util.List getColumnMetaData(JConnection conn, String tablename,
                                          String owner) throws Exception {
      String strsql =
          " select * from USER_TAB_COLUMNS " +
          " where TABLE_NAME = '" + tablename + "' order by COLUMN_ID";
      Statement  st = null;
      ResultSet  rs = null;
      List pcolumns = new ArrayList();
      try {
          st = conn.createStatement();
          rs = st.executeQuery(strsql);
          while (rs.next()) {
              StubObject so = new StubObject();
              DBTools.ResultSetToStubObject(rs, so);
              pcolumns.add(so);
          }
      } catch (Exception ex) {
          throw ex;
      } finally {
          conn.BackStatement(st, rs);
      }
      return pcolumns;
  }

  /**
   *
   * @param conn JConnection
   * @param tablename String
   * @param owner String
   * @return List
   */
  public java.util.List getIndexMetaData(JConnection conn, String tablename,
                                         String owner) throws Exception {
      String strsql =
          " select * from USER_INDEXES " +
          " where upper(TABLE_NAME) = '" + tablename.toUpperCase() + "'" +
          " and UPPER(TABLE_OWNER) = '" + owner.toUpperCase() + "'";
      Statement  st = null;
      ResultSet  rs = null;
      Statement st2 = null;
      ResultSet rs2 = null;
      List  pindexs = new ArrayList();
      try {
          st = conn.createStatement();
          st2= conn.createStatement();
          rs = st.executeQuery(strsql);
          while (rs.next()) {
              StubObject so = new StubObject();
              DBTools.ResultSetToStubObject(rs, so);
              pindexs.add(so);
              String indexname = rs.getString("INDEX_NAME");
              //取索引的列
              strsql =
                  " select * from USER_IND_COLUMNS" +
                  " where upper(TABLE_NAME) = '" + tablename.toUpperCase() + "'" +
                  " and upper(INDEX_NAME) = '" + indexname.toUpperCase() + "'";
              rs2 = st2.executeQuery(strsql);
              java.util.List columns = new ArrayList();
              while (rs2.next()) {
                  columns.add(rs2.getString("COLUMN_NAME"));
              }
              so.setObject("COLUMN", columns);
          }
      } catch (Exception ex) {
          throw ex;
      } finally {
          conn.BackStatement(st, rs);
          conn.BackStatement(st2, rs2);
      }
      return pindexs;
  }

  /**
   *
   * @param conn JConnection
   * @param tablename String
   * @param owner String
   * @return List
   */
  public java.util.List getConstraintMetaData(JConnection conn, String tablename,
                                              String owner) throws Exception {
      return null;
  }

  /**
   *
   * @param conn JConnection
   * @return Timestamp
   */
  public Timestamp getSystemTimestamp(JConnection conn) throws Exception {
      Statement st = null;
      ResultSet rs = null;
      java.sql.Timestamp time = null;
      try {
          st = conn.createStatement();
          rs = st.executeQuery("select sysdate from dual");
          if (rs.next()) {
              time = rs.getTimestamp(1);
          }
      } finally {
          conn.BackStatement(st, rs);
      }
      return time;
  }

}
