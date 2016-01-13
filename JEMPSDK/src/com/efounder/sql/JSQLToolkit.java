package com.efounder.sql;

import java.sql.*;

/**
 * <p>Title: Java Framework</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author Skyline
 * @version 1.0
 */
//--------------------------------------------------------------------------------------------------
//����:
//���: Skyline(2001.12.29)
//ʵ��: Skyline
//�޸�:
//--------------------------------------------------------------------------------------------------
public class JSQLToolkit {
  //------------------------------------------------------------------------------------------------
  //����:
  //���: Skyline(2001.12.29)
  //ʵ��: Skyline
  //�޸�:
  //------------------------------------------------------------------------------------------------
  public JSQLToolkit() {
  }
  //------------------------------------------------------------------------------------------------
  //����:
  //���: Skyline(2001.12.29)
  //ʵ��: Skyline
  //�޸�:
  //------------------------------------------------------------------------------------------------
  public int CreateTempTable(JConnection conn,String TableName,String TempTableName) {
    return 0;
  }
  //------------------------------------------------------------------------------------------------
  //����:
  //���: Skyline(2001.12.29)
  //ʵ��: Skyline
  //�޸�:
  //------------------------------------------------------------------------------------------------
  public int DropTable(JConnection conn,String TableName) {
    Statement st = conn.GetStatement(conn);String SQL;
    try {
      SQL = "drop table "+TableName;
      st.execute(SQL);
    } catch ( Exception e ) {
//      e.printStackTrace();
    }
    return 0;
  }
  /**
   *
   * @param conn JConnection
   * @param PO Properties
   * @return String
   */
  public String GetUser(JConnection conn,java.util.Properties PO) {
    return null;
  }
  /**
   *
   * @param conn JConnection
   * @param UserName String
   */
  public void SetUser(JConnection conn,String UserName) {
    return;
  }
  /**
   *
   * @param conn JConnection
   * @param Table String
   * @param BlobField String
   * @param Where String
   * @return Object
   * @throws Exception
   */
  public Object selectBlobData(JConnection conn,String Table,String BlobField,String Where) throws Exception {
    return null;
  }

  /**
   *
   * @param rs ResultSet
   * @param BlobField String
   * @return Object
   * @throws Exception
   */
  public Object getBlogData(ResultSet rs,String BlobField)throws Exception {
      return null;
  }
  /**
   *
   * @param conn JConnection
   * @param Table String
   * @param Fields String
   * @param BlobField String
   * @param Values String
   * @param Where String
   * @param BlobValue byte[]
   * @throws Exception
   */
  public void insertBlobData(JConnection conn,String Table,String Fields,String BlobField,String Values,String Where,byte[] BlobValue) throws Exception {

  }
  /**
   *
   * @param conn JConnection
   * @param conUser String
   * @param conPass String
   * @throws Exception
   */
  public void changePassword(JConnection conn,String conUser,String conPass)throws Exception {

  }
  /**
   *
   * @param conn JConnection
   * @param Table String
   * @param BlobField String
   * @param Where String
   * @param BlobValue byte[]
   * @throws Exception
   */
  public void updateBlobData(JConnection conn,String Table,String BlobField,String Where,byte[] BlobValue) throws Exception {

  }
  /**
   *
   * @param conn JConnection
   * @param Table String
   * @param Fields String
   * @param BlobField String[]
   * @param Values String
   * @param Where String
   * @param BlobValue Object[]
   * @throws Exception
   */
  public void insertBlobData(JConnection conn,String Table,String Fields,String[] BlobField,String Values,String Where,Object[] BlobValue) throws Exception {

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
    return null;
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
    return null;
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
    return null;
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
    return null;
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
  public java.sql.Timestamp getSystemTimestamp(JConnection conn) throws Exception {
      return null;
  }

}
