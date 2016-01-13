package com.efounder.eai.service;

import com.efounder.sql.*;
import com.efounder.eai.EAI;
import java.sql.Clob;
import java.sql.NClob;
import java.sql.SQLClientInfoException;
import java.sql.SQLException;
import java.sql.Blob;
import java.sql.SQLXML;
//import java.sql.NClob;
//import java.sql.SQLXML;
//import java.sql.SQLClientInfoException;
import java.util.Map;
import java.util.Properties;
import java.sql.Array;
import java.sql.Struct;
import java.util.List;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2004</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class EAIConnection  extends JConnection {
  public EAIConnection() {
  }
  /**
   *
   * @param PO Object
   * @return JConnection
   * @throws Exception
   */
  protected JConnection createInstance(Object PO) throws Exception {
    JConnection conn = null;
    conn = (JConnection) EAI.DAL.IOM("DBManagerObject", "GetDBConnection", PO);
    return conn;
  }

  public Clob createClob() throws SQLException {
    return null;
  }

  public Blob createBlob() throws SQLException {
    return null;
  }

//  public NClob createNClob() throws SQLException {
//    return null;
//  }
//
//  public SQLXML createSQLXML() throws SQLException {
//    return null;
//  }

  public boolean isValid(int timeout) throws SQLException {
    return false;
  }

//  public void setClientInfo(String name, String value) throws
//      SQLClientInfoException {
//  }
//
//  public void setClientInfo(Properties properties) throws
//      SQLClientInfoException {
//  }

  public String getClientInfo(String name) throws SQLException {
    return "";
  }

  public Properties getClientInfo() throws SQLException {
    return null;
  }

  public Array createArrayOf(String typeName, Object[] elements) throws SQLException {
    return null;
  }

  public Struct createStruct(String typeName, Object[] attributes) throws SQLException {
    return null;
  }

  public NClob createNClob() throws SQLException {
	return null;
  }

  public SQLXML createSQLXML() throws SQLException {
	return null;
  }

  public void setClientInfo(String name, String value) throws SQLClientInfoException {
	
  }

  public void setClientInfo(Properties properties) throws SQLClientInfoException {

  }

  public <T> T unwrap(Class<T> iface) throws SQLException {
	return null;
  }

  public boolean isWrapperFor(Class<?> iface) throws SQLException {
	return false;
  }
}
