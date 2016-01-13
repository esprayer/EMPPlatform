package com.efounder.sql;

import java.sql.*;
import java.util.*;
import javax.naming.InitialContext;
import javax.naming.Context;

import org.openide.util.Lookup;

/**
 * <p>Title:��װ Statement 创建自定义Connection</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2013</p>
 *
 * <p>Company: </p>
 *
 * @author ES
 * @version 1.0
 */
public abstract class JConnection implements Connection {
	
	public Connection connection = null;

	/**
	 *
	 */
	protected String dataBaseType = null;
	
	/**
     *
     */
	public JConnection() {
	}

	/**
	 *
	 * @return String
	 */
	public String getDataBaseType() {
		return dataBaseType;
	}
	
	/**
	 *
	 * @param dbt String
	 */
	public void setDataBaseType(String dbt) {
		this.dataBaseType = dbt;
	}
	
	/**
	 * 
	 * @param conn Connection
	 */
	public void setConnection(Connection conn) {
		connection = conn;
	}

	/**
	 * 
	 * @return Connection
	 */
	public Connection getConnection() {
		return connection;
	}

	/**
	 * 
	 * @param SQL String
	 * @return String
	 */
	public String getDialectSql(String SQL) {
		return null;
	}

	/**
	 * 
	 * @param PO Object
	 * @return int
	 */
	public int CheckDatabase(Object PO) {
		return 0;
	}

	/**
     *
     */
	public void clearWarnings() {
		try {
			connection.clearWarnings();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
     *
     */
	public void close() {
		try {
			if (!connection.isClosed()) {
				connection.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
     *
     */
	public void commit() {
		try {
			connection.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @return Statement
	 */
	public JStatement createStatement() {
		try {
			return new JStatement(this, connection.createStatement());
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
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
	public Object checkConnection(Object ParamObject,Object DataObject,Object CustomObject,Object AdditiveObject) throws Exception {
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
	public Object InitConnection(Object ParamObject,Object DataObject,Object CustomObject,Object AdditiveObject) throws Exception {
		return null;
	}
	
	/**
	 * 
	 * @param connection JConnection
	 * @return Statement
	 */
	public Statement GetStatement(JConnection connection) {
		try {
			return DelegateStatement.getInstance(connection, connection.getConnection().createStatement());
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 
	 * @param stmt
	 *            Statement
	 * @param rs
	 *            ResultSet
	 */
	static public void BackStatement(Statement stmt, ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (stmt != null) {
			try {
				stmt.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 
	 * @param stmt
	 *            PreparedStatement
	 * @param rs
	 *            ResultSet
	 */
	static public void BackPreparedStatement(PreparedStatement stmt,
			ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (stmt != null) {
			try {
				stmt.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 
	 * @param resultSetType
	 *            int
	 * @param resultSetConcurrency
	 *            int
	 * @return Statement
	 */
	public Statement createStatement(int resultSetType, int resultSetConcurrency) {
		try {
			return DelegateStatement.getInstance(this, connection.createStatement(resultSetType, resultSetConcurrency));
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 
	 * @param resultSetType
	 *            int
	 * @param resultSetConcurrency
	 *            int
	 * @param resultSetHoldability
	 *            int
	 * @return Statement
	 */
	public Statement createStatement(int resultSetType,
			int resultSetConcurrency, int resultSetHoldability)
			throws SQLException {
		return connection.createStatement(resultSetType, resultSetConcurrency,
				resultSetHoldability);
	}

	/**
	 * 
	 * @return boolean
	 */
	public boolean getAutoCommit() {
		try {
			return connection.getAutoCommit();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 
	 * @return String
	 */
	public String getCatalog() {
		try {
			return connection.getCatalog();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 
	 * @return int
	 */
	public int getHoldability() throws SQLException {
		return connection.getHoldability();
	}

	/**
	 * 
	 * @return DatabaseMetaData
	 */
	public DatabaseMetaData getMetaData() throws SQLException {
		return connection.getMetaData();
	}

	/**
	 * 
	 * @return int
	 */
	public int getTransactionIsolation() throws SQLException {
		return connection.getTransactionIsolation();
	}

	/**
	 * 
	 * @return Map
	 */
	public Map getTypeMap() throws SQLException {
		return connection.getTypeMap();
	}

	/**
	 * 
	 * @return SQLWarning
	 */
	public SQLWarning getWarnings() throws SQLException {
		return connection.getWarnings();
	}

	/**
	 * 
	 * @return boolean
	 */
	public boolean isClosed() {
		try {
			return connection.isClosed();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 
	 * @return boolean
	 */
	public boolean isReadOnly() throws SQLException {
		return connection.isReadOnly();
	}

	/**
	 * 
	 * @param sql
	 *            String
	 * @return String
	 */
	public String nativeSQL(String sql) throws SQLException {
		return connection.nativeSQL(sql);
	}

	/**
	 * 
	 * @param sql
	 *            String
	 * @return CallableStatement
	 */
	public CallableStatement prepareCall(String sql) throws SQLException {
		return DelegateCallableStatement.getInstance(this, connection.prepareCall(sql));
	}

	/**
	 * 
	 * @param sql
	 *            String
	 * @param resultSetType
	 *            int
	 * @param resultSetConcurrency
	 *            int
	 * @return CallableStatement
	 */
	public CallableStatement prepareCall(String sql, int resultSetType,
			int resultSetConcurrency) throws SQLException {
		return DelegateCallableStatement.getInstance(this, connection.prepareCall(sql, resultSetType, resultSetConcurrency));
	}

	/**
	 * 
	 * @param sql
	 *            String
	 * @param resultSetType
	 *            int
	 * @param resultSetConcurrency
	 *            int
	 * @param resultSetHoldability
	 *            int
	 * @return CallableStatement
	 */
	public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
		return DelegateCallableStatement.getInstance(this, connection.prepareCall(sql, resultSetType, resultSetConcurrency, resultSetHoldability));
	}

	/**
	 * 
	 * @param sql
	 *            String
	 * @return PreparedStatement
	 */
	public PreparedStatement prepareStatement(String sql) throws SQLException {
		logInfo(sql, 4);
		return DelegatePreparedStatement.getInstance(this,
				connection.prepareStatement(sql));
	}

	public void logInfo(String sql, int mask, boolean bLine) {
		System.out.println(sql);
	}

	public void logInfo(String sql, int mask) {
		logInfo(sql, mask, true);
	}

	/**
	 * 
	 * @param sql
	 *            String
	 * @param autoGeneratedKeys
	 *            int
	 * @return PreparedStatement
	 */
	public PreparedStatement prepareStatement(String sql, int autoGeneratedKeys) throws SQLException {
		return DelegatePreparedStatement.getInstance(this, connection.prepareStatement(sql, autoGeneratedKeys));
	}

	/**
	 * 
	 * @param sql
	 *            String
	 * @param columnIndexes
	 *            int[]
	 * @return PreparedStatement
	 */
	public PreparedStatement prepareStatement(String sql, int[] columnIndexes) throws SQLException {
		return DelegatePreparedStatement.getInstance(this, connection.prepareStatement(sql, columnIndexes));
	}

	/**
	 * 
	 * @param sql
	 *            String
	 * @param resultSetType
	 *            int
	 * @param resultSetConcurrency
	 *            int
	 * @return PreparedStatement
	 */
	public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
		return DelegatePreparedStatement.getInstance(this, connection.prepareStatement(sql, resultSetType, resultSetConcurrency));
	}

	/**
	 * 
	 * @param sql
	 *            String
	 * @param resultSetType
	 *            int
	 * @param resultSetConcurrency
	 *            int
	 * @param resultSetHoldability
	 *            int
	 * @return PreparedStatement
	 */
	public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
		return DelegatePreparedStatement.getInstance(this, connection.prepareStatement(sql, resultSetType, resultSetConcurrency, resultSetHoldability));
	}

	/**
	 * 
	 * @param sql
	 *            String
	 * @param columnNames
	 *            String[]
	 * @return PreparedStatement
	 */
	public PreparedStatement prepareStatement(String sql, String[] columnNames) throws SQLException {
		return DelegatePreparedStatement.getInstance(this, connection.prepareStatement(sql, columnNames));
	}

	/**
	 * 
	 * @param savepoint
	 *            Savepoint
	 */
	public void releaseSavepoint(Savepoint savepoint) throws SQLException {
		connection.releaseSavepoint(savepoint);
	}

	/**
   *
   */
	public void rollback() {
		try {
			System.out.println("rollback=" + this);
			connection.rollback();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @param savepoint
	 *            Savepoint
	 */
	public void rollback(Savepoint savepoint) throws SQLException {
		connection.rollback(savepoint);
	}

	/**
	 * 
	 * @param autoCommit
	 *            boolean
	 */
	public void setAutoCommit(boolean autoCommit) {//
		try {
			connection.setAutoCommit(autoCommit);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @param catalog
	 *            String
	 */
	public void setCatalog(String catalog) throws SQLException {
		connection.setCatalog(catalog);
	}

	/**
	 * 
	 * @param holdability
	 *            int
	 */
	public void setHoldability(int holdability) throws SQLException {
		connection.setHoldability(holdability);
	}

	/**
	 * 
	 * @param readOnly
	 *            boolean
	 */
	public void setReadOnly(boolean readOnly) throws SQLException {
		connection.setReadOnly(readOnly);
	}

	/**
	 * 
	 * @return Savepoint
	 */
	public Savepoint setSavepoint() throws SQLException {
		return connection.setSavepoint();
	}

	/**
	 * 
	 * @param name
	 *            String
	 * @return Savepoint
	 */
	public Savepoint setSavepoint(String name) throws SQLException {
		return connection.setSavepoint(name);
	}

	/**
	 * 
	 * @param level
	 *            int
	 */
	public void setTransactionIsolation(int level) throws SQLException {
		connection.setTransactionIsolation(level);
	}

	/**
	 * 
	 * @param map
	 *            Map
	 */
	public void setTypeMap(Map map) throws SQLException {
		connection.setTypeMap(map);
	}

	/**
	 * 
	 * @param SQL
	 *            String
	 * @return boolean
	 */
	public boolean ExecuteSQL(String SQL) {
		Statement st;
		boolean Res = false;
		long t1 = System.currentTimeMillis();
		st = this.GetStatement(this);
		try {
			Res = st.execute(SQL);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			long t2 = System.currentTimeMillis();
			logInfo(SQL + " execute time:" + (t2 - t1), 2);
			this.BackStatement(st, null);
		}
		return Res;
	}

	/**
	 * 
	 * @param SQL
	 *            String
	 * @return boolean
	 */
	public boolean ExecuteUpdate(String SQL) {
		Statement st;
		boolean Res = true;
		long t1 = System.currentTimeMillis();
		st = this.GetStatement(this);
		try {
			st.executeUpdate(SQL);
		} catch (Exception e) {
			Res = false;
			e.printStackTrace();
		} finally {
			long t2 = System.currentTimeMillis();
			logInfo(SQL + " execute time:" + (t2 - t1), 2);
			this.BackStatement(st, null);
		}
		return Res;
	}

	/**
	 * 
	 * @param PO
	 *            Object
	 * @throws Exception
	 */
	public void changePassword(Object PO) throws Exception {

	}

	/**
	 * 
	 * @param st
	 *            Statement
	 * @param sql
	 *            String
	 * @return ResultSet
	 * @throws SQLException
	 */
	public ResultSet executeQuery(Statement st, String sql) throws SQLException {
		long t1 = System.currentTimeMillis();
		try {
			return st.executeQuery(sql);
		} finally {
			long t2 = System.currentTimeMillis();
			logInfo(sql + " execute time:" + (t2 - t1), 1);
		}
	}

	/**
	 * 
	 * @param st
	 *            Statement
	 * @param sql
	 *            String
	 * @return int
	 * @throws SQLException
	 */
	public int executeUpdate(Statement st, String sql) throws SQLException {
		return st.executeUpdate(sql);
	}

	/**
	 * 
	 * @param st
	 *            Statement
	 * @param sql
	 *            String
	 * @return boolean
	 * @throws SQLException
	 */
	public boolean execute(Statement st, String sql) throws SQLException {
		return st.execute(sql);
	}

	/**
	 * addBatch
	 * 
	 * @param st
	 *            Statement
	 * @param sql
	 *            String
	 * @throws SQLException
	 */
	public void addBatch(Statement st, String sql) throws SQLException {
		st.addBatch(sql);
	}

	/**
	 * 
	 * @param st
	 *            Statement
	 * @param sql
	 *            String
	 * @param autoGeneratedKeys
	 *            int
	 * @return int
	 * @throws SQLException
	 */
	public int executeUpdate(Statement st, String sql, int autoGeneratedKeys) throws SQLException {
		return st.executeUpdate(sql, autoGeneratedKeys);
	}

	/**
	 * 
	 * @param st
	 *            Statement
	 * @param sql
	 *            String
	 * @param columnIndexes
	 *            int[]
	 * @return int
	 * @throws SQLException
	 */
	public int executeUpdate(Statement st, String sql, int columnIndexes[]) throws SQLException {
		return st.executeUpdate(sql, columnIndexes);
	}

	/**
	 * 
	 * @param st
	 *            Statement
	 * @param sql
	 *            String
	 * @param columnNames
	 *            String[]
	 * @return int
	 * @throws SQLException
	 */
	public int executeUpdate(Statement st, String sql, String columnNames[]) throws SQLException {
		return st.executeUpdate(sql, columnNames);
	}

	/**
	 * 
	 * @param st
	 *            Statement
	 * @param sql
	 *            String
	 * @param autoGeneratedKeys
	 *            int
	 * @return boolean
	 * @throws SQLException
	 */
	public boolean execute(Statement st, String sql, int autoGeneratedKeys) throws SQLException {
		return st.execute(sql, autoGeneratedKeys);
	}

	/**
	 * 
	 * @param st
	 *            Statement
	 * @param sql
	 *            String
	 * @param columnIndexes
	 *            int[]
	 * @return boolean
	 * @throws SQLException
	 */
	public boolean execute(Statement st, String sql, int columnIndexes[]) throws SQLException {
		return st.execute(sql, columnIndexes);
	}

	/**
	 * 
	 * @param st
	 *            Statement
	 * @param sql
	 *            String
	 * @param columnNames
	 *            String[]
	 * @return boolean
	 * @throws SQLException
	 */
	public boolean execute(Statement st, String sql, String columnNames[]) throws SQLException {
		return st.execute(sql, columnNames);
	}

	/**
	 * 
	 * @param conn Connection
	 * @return JConnection
	 */
	public static JConnection getInstance(Connection conn) {
		JConnection jconn = new DelegateConnection(conn);
		return jconn;
	}
	
	/**
	 *
	 */
	protected static JConnection defaultConn = null;
	/**
	 *
	 * @param PO Object
	 * @return JConnection
	 * @throws Exception
	 */
	public static JConnection getInstance(Object PO) throws Exception {
	    if ( defaultConn == null ) {
	      defaultConn = (JConnection) Lookup.getDefault().lookup(JConnection.class);
	    }
	    return defaultConn.createInstance(PO);
	}
	
	/**
	 * 
	 * @return JSQLToolkit
	 */
	public JSQLToolkit getSQLToolkit() {
		if (this.connection == null) return null;
		String productName = null;
		try {
			productName = connection.getMetaData().getDatabaseProductName();
		} catch (Exception e) {
			e.printStackTrace();
		}
		JSQLToolkit sqlToolkit = (JSQLToolkit) Lookup.getDefault().lookup(JSQLToolkit.class, productName);
		return sqlToolkit;
	}
	  
	/**
	 *
	 * @param PO Object
	 * @return JConnection
	 * @throws Exception
	 */
	protected abstract JConnection createInstance(Object PO) throws Exception ;
}