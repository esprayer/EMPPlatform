package com.efounder.sql;

import java.sql.*;
import java.util.Map;
import java.util.Properties;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2003</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class DelegateConnection extends JConnection {
	/**
     *
     * @param con Connection
     */
	public DelegateConnection(Connection con) {
		this.connection = con;
	}
	
	/**
     *
     * @param PO Object
     * @return JConnection
     * @throws Exception
     */
	protected JConnection createInstance(Object PO) throws Exception {
		return null;
	}

	public Array createArrayOf(String typeName, Object[] elements) throws SQLException {
		return null;
	}

	public Blob createBlob() throws SQLException {
		return null;
	}

	public Clob createClob() throws SQLException {
		return null;
	}

	public NClob createNClob() throws SQLException {
		return null;
	}

	public SQLXML createSQLXML() throws SQLException {
		return null;
	}

	public Struct createStruct(String typeName, Object[] attributes)
			throws SQLException {
		return null;
	}

	public Properties getClientInfo() throws SQLException {
		return null;
	}

	public String getClientInfo(String name) throws SQLException {
		return null;
	}

	public boolean isValid(int timeout) throws SQLException {
		return false;
	}

	public void setClientInfo(Properties properties)
			throws SQLClientInfoException {

	}

	public void setClientInfo(String name, String value)
			throws SQLClientInfoException {

	}

	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		return false;
	}

	public <T> T unwrap(Class<T> iface) throws SQLException {
		return null;
	}
}
