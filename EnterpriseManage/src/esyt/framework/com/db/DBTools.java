package esyt.framework.com.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.poi.hssf.record.formula.functions.Lookup;

import com.borland.dx.dataset.Column;
import com.borland.dx.dataset.DataRow;
import com.borland.dx.dataset.DataSet;
import com.borland.dx.dataset.UpdateMode;
import com.borland.dx.dataset.Variant;
import com.borland.dx.sql.dataset.Database;
import com.borland.dx.sql.dataset.Load;
import com.borland.dx.sql.dataset.QueryDataSet;
import com.borland.dx.sql.dataset.QueryDescriptor;
import com.borland.dx.sql.dataset.QueryResolver;
import com.mysql.jdbc.ResultSetMetaData;

import esyt.framework.com.builder.base.data.EFRowSet;
import esyt.framework.com.core.xml.StubObject;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public abstract class DBTools implements DBSQL {
	protected static DBTools defaultDBTools = null;

	public DBTools() {
	}

	/**
	 * 
	 * @param tableName
	 *            String
	 * @param Condition
	 *            String
	 * @return String
	 */
	public static String deleteSql(String tableName, String Condition) {
		return _DELETE_ + tableName + _WHERE_ + Condition;
	}

	/**
	 * 
	 * @param tableName
	 *            String
	 * @param Fields
	 *            String
	 * @param Values
	 *            String
	 * @return String
	 */
	public static String insertSql(String tableName, String Fields,
			String Values) {
		return _INSERT_ + tableName + _LEFTPT_ + Fields + _RIGHTPT_ + _VALUES_
				+ _LEFTPT_ + Values + _RIGHTPT_;
	}

	/**
	 * 
	 * @param tableName
	 *            String
	 * @param Condition
	 *            String
	 * @return String
	 */
	public static String selectAllSql(String tableName, String Condition) {
		String SQL = _SELECTALL_ + _FROM_ + tableName;
		if (Condition != null && !"".equals(Condition.trim()))
			SQL += _WHERE_ + Condition;
		return SQL;
	}

	/**
	 * 
	 * @param tableName
	 *            String
	 * @param Fields
	 *            String
	 * @param Condition
	 *            String
	 * @return String
	 */
	public static String selectSql(String tableName, String Fields,
			String Condition) {
		String SQL = _SELECT_ + Fields + _FROM_ + tableName;
		if (Condition != null && !"".equals(Condition.trim()))
			SQL += _WHERE_ + Condition;
		return SQL;
	}

	/**
	 * 
	 * @param tableName
	 *            String
	 * @param Field
	 *            String
	 * @param Value
	 *            String
	 * @param Condition
	 *            String
	 * @return String
	 */
	public static String updateSql(String tableName, String Field,
			String Value, String Condition) {
		return _UPDATE_ + tableName + _SET_ + Field + _EQUAL_ + Value + _WHERE_
				+ Condition;
	}

	/**
	 * 
	 * @param tableName
	 *            String
	 * @param Field
	 *            String[]
	 * @param Value
	 *            String[]
	 * @param Condition
	 *            String
	 * @return String
	 */
	public static String updateSql(String tableName, String[] Field,
			String[] Value, String Condition) {
		// modify by luody
		String fieldValue = "";
		for (int i = 0; i < Field.length; i++) {
			fieldValue += Field[i] + _EQUAL_ + Value[i] + ",";
		}
		if (fieldValue.length() > 0)
			fieldValue = fieldValue.substring(0, fieldValue.length() - 1);
		// return _UPDATE_ + tableName + _SET_ + Field + _EQUAL_ + Value +
		// _WHERE_ + Condition;
		return _UPDATE_ + tableName + _SET_ + fieldValue + _WHERE_ + Condition;
	}

	/**
	 * 
	 * @param rs
	 *            ResultSet
	 * @param so
	 *            StubObject
	 * @throws Exception
	 */
	public static void ResultSetToStubObject(ResultSet rs, StubObject so)
			throws Exception {
		ResultSetMetaData rmd = (ResultSetMetaData) rs.getMetaData();
		int colCount = rmd.getColumnCount();
		String colName = null;
		Object value;
		for (int i = 1; i <= colCount; i++) {
			colName = rmd.getColumnName(i);
			value = rs.getObject(colName);
			if (value instanceof java.sql.Timestamp) {
				java.sql.Timestamp tt = rs.getTimestamp(colName);
				if (tt != null)
					so.setString(colName, String.valueOf(tt.getTime()));
			} else
				so.setObject(colName, value);
		}
	}

	/**
	 * 
	 * @param rs
	 *            ResultSet
	 * @param rowSet
	 *            EFRowSet
	 */
	public static void ResultSetToRowSet(ResultSet rs, EFRowSet rowSet) throws Exception {
		StubObject so = new StubObject();
		ResultSetToStubObject(rs, so);
		if (rowSet.getDataMap() == null)
			rowSet.setDataMap(new HashMap());
		rowSet.getDataMap().putAll(so.getStubTable());
	}

	/**
	 * 
	 * @param dataRow
	 *            DataRow
	 * @param so
	 *            StubObject
	 * @throws Exception
	 */
	public static void DataRowToStubObject(DataRow dataRow, StubObject so) throws Exception {
		int colCount = dataRow.getColumnCount();
		String colName = null;
		Object value;
		for (int i = 0; i < colCount; i++) {
			Column col = dataRow.getColumn(i);
			Variant Value = new Variant();
			colName = col.getColumnName();
			dataRow.getVariant(colName, Value);
			so.setObject(colName, Value);
			int type = Value.getType();
			switch (type) {
			case Variant.STRING:
				so.setString(colName, Value.getString());
				break;
			default:
				so.setObject(colName, Value);
			}
		}
	}

	public static void DataRowToStubObject(DataSet ds, StubObject so)
			throws Exception {
		int colCount = ds.getColumnCount();
		String colName = null;
		Object value;
		for (int i = 0; i < colCount; i++) {
			Column col = ds.getColumn(i);
			Variant Value = new Variant();
			colName = col.getColumnName();
			ds.getVariant(colName, Value);
			so.setObject(colName, Value);
			int type = Value.getType();
			switch (type) {
			case Variant.STRING:
				so.setString(colName, Value.getString());
				break;
			case Variant.ASSIGNED_NULL:
			case Variant.UNASSIGNED_NULL:
				so.setString(colName, "");
				break;
			default:
				so.setObject(colName, Value);
			}
		}
	}

	public static List getResultList(Database db, String table, String col,
			String where) {
		String sql = DBTools.selectSql(table, col, where);
		QueryDescriptor qd = new QueryDescriptor(db, sql, null, true, Load.ALL);
		QueryDataSet qds1 = new QueryDataSet();
		qds1.setQuery(qd);
		qds1.open();
		List list = new ArrayList();
		if (qds1.getRowCount() > 0) {
			do {
				StubObject so = new StubObject();
				try {
					DBTools.DataRowToStubObject(qds1, so);
					list.add(so);
				} catch (Exception e) {
				}
			} while (qds1.next());
		}
		qds1.close();
		return list;
	}

	//
	// /**
	// * 增加一个跨数据库取数的方法
	// */
	// public static List getResultList(String table, String col, String where,
	// String servername) {
	// String sql = DBTools.selectSql(table, col, where);
	// QueryDescriptor qd = new QueryDescriptor(null, sql, null, true,
	// Load.ALL);
	// ClientDataSet qds1 = new ClientDataSet();
	// qds1.setQuerySet(qd);
	// qds1.setAgentDataBase(AgentDatabase.getDefault(servername));
	// qds1.setActiveAgent(true);
	// List list = new ArrayList();
	// if (qds1.getRowCount() > 0) {
	// do {
	// StubObject so = new StubObject();
	// try {
	// DBTools.DataRowToStubObject(qds1, so);
	// list.add(so);
	// } catch (Exception e) {}
	// } while (qds1.next());
	// }
	// qds1.close();
	// return list;
	// }
	//
	// public static List getResultList(String table,String col,String where){
	// String sql = DBTools.selectSql(table, col,where);
	// QueryDescriptor qd = new QueryDescriptor(null, sql, null, true,
	// Load.ALL);
	// ClientDataSet qds1 = new ClientDataSet();
	// qds1.setQuerySet(qd);
	// qds1.setAgentDataBase(AgentDatabase.getDefault());
	// qds1.setActiveAgent(true);
	// List list=new ArrayList();
	// if(qds1.getRowCount()>0){
	// do{
	// StubObject so=new StubObject();
	// try{
	// DBTools.DataRowToStubObject(qds1, so);
	// list.add(so);
	// }catch(Exception e){}
	// }while(qds1.next());
	// }
	// qds1.close();
	// return list;
	// }
	public static List getResultList(Statement stmt, String table, String col,
			String where) {
		String sql = DBTools.selectSql(table, col, where);
		List list = new ArrayList();
		try {
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {

				StubObject so = new StubObject();
				DBTools.ResultSetToStubObject(rs, so);
				list.add(so);
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public static Map getResultHash(Database db, String table, String keycol,
			String valuecol, String where) {
		List list = DBTools.getResultList(db, table, keycol + "," + valuecol,
				where);
		StubObject so;
		Map hash = new TreeMap();
		for (int i = 0; i < list.size(); i++) {
			so = (StubObject) list.get(i);
			hash.put(so.getString(keycol, ""), so.getString(valuecol, ""));
		}
		return hash;
	}

	// public static Map getResultHash(String table,String keycol,String
	// valuecol,String where){
	// List list=DBTools.getResultList(table,keycol+","+valuecol,where);
	// StubObject so;
	// Map hash=new TreeMap();
	// for(int i=0;i<list.size();i++){
	// so=(StubObject)list.get(i);
	// hash.put(so.getString(keycol,""),so.getObject(valuecol,"").toString());
	// }
	// return hash;
	// }
	// /**
	// * 增加一个跨数据库取数的方法
	// */
	// public static Map getResultHash(String table, String keycol,
	// String valuecol, String where,
	// String servername) {
	// List list = DBTools.getResultList(table, keycol + "," + valuecol, where,
	// servername);
	// StubObject so;
	// Map hash = new TreeMap();
	// for (int i = 0; i < list.size(); i++) {
	// so = (StubObject) list.get(i);
	// hash.put(so.getString(keycol, ""),
	// so.getObject(valuecol, "").toString());
	// }
	// return hash;
	// }
	//
	// public static Map getResultMap(String table, String keycol, String
	// valuecol,
	// String where) {
	// String column = keycol + "," + valuecol;
	// if (valuecol.equals("*"))
	// column = "*";
	// List list = DBTools.getResultList(table, column, where);
	// StubObject so;
	// Map hash = new HashMap();
	// for (int i = 0; i < list.size(); i++) {
	// so = (StubObject) list.get(i);
	// if (keycol.indexOf(".") != -1)
	// keycol = keycol.substring(keycol.indexOf(".") + 1);
	// hash.put(so.getString(keycol, ""), so);
	// }
	// return hash;
	// }

	public static Map getResultMap(Statement stmt, String table, String keycol,
			String valuecol, String where) {
		List list = DBTools.getResultList(stmt, table, keycol + "," + valuecol,
				where);
		StubObject so;
		Map hash = new HashMap();
		for (int i = 0; i < list.size(); i++) {
			so = (StubObject) list.get(i);
			if (keycol.indexOf(".") != -1)
				keycol = keycol.substring(keycol.indexOf(".") + 1);
			hash.put(so.getString(keycol, ""), so);
		}
		return hash;
	}

	//
	// /**
	// * 增加一个跨数据库取数的方法
	// */
	// public static Map getResultMap(String table, String keycol,
	// String valuecol, String where,
	// String servername) {
	// List list = DBTools.getResultList(table, keycol + "," + valuecol,
	// where, servername);
	// StubObject so;
	// Map hash = new HashMap();
	// for (int i = 0; i < list.size(); i++) {
	// so = (StubObject) list.get(i);
	// if (keycol.indexOf(".") != -1)
	// keycol = keycol.substring(keycol.indexOf(".") + 1);
	// hash.put(so.getString(keycol, ""), so);
	// }
	// return hash;
	// }

	public static String getConfValue(Connection pCon, String strKeyID,
			String strAccount) {
		String strLanName = "OWNER";
		String strConfName = "DB" + strAccount + "_" + strLanName;

		String strSQL = "SELECT F_VAL FROM " + strConfName
				+ ".BSCONF WHERE F_VKEY = '" + strKeyID + "'";
		Statement pST = null;

		try {
			pST = pCon.createStatement();
			ResultSet pRS = pST.executeQuery(strSQL);
			if (pRS.next()) {
				return pRS.getString(1);
			} else {
				return null;
			}

		} catch (Exception E) {
			return null;
		} finally {
			try {
				pST.close();
			} catch (Exception E) {

			}
		}
	}

	public static boolean isExistColumn(Statement stmt, String owner,
			String table, String column) {

		String key = owner + "-" + column;
		String sql = "select 1 FROM ALL_TAB_COLUMNS T1 "
				+ " WHERE UPPER(OWNER)='" + owner.toUpperCase()
				+ "'  and COLUMN_NAME='" + column.toUpperCase()
				+ "' and UPPER(TABLE_NAME)='" + table.toUpperCase() + "'";
		try {
			ResultSet rs = stmt.executeQuery(sql);
			if (rs.next()) {
				rs.close();
				return true;
			}
			rs.close();
		} catch (Exception e) {
			return false;
		}
		return false;
	}

	public static boolean isExistTable(Statement stmt, String owner,
			String table) {
		// 表名和属主考虑大小写
		String sql = " select 1 from ALL_TABLES where UPPER(TABLE_NAME) = '"
				+ table.toUpperCase() + "'" + " and upper(OWNER) = '"
				+ owner.toUpperCase() + "'";
		ResultSet rs = null;
		try {
			rs = stmt.executeQuery(sql);
			boolean b = rs.next();
			rs.close();
			return b;
		} catch (Exception e) {
		}
		return false;
	}

	public static void saveObjectToNew(Connection conn, String table,
			String where, String[] keys, String[] col, Object[] newvalue) {
		Database db = new Database();
		db.setJdbcConnection(conn);
		String strSQL = "SELECT * FROM " + table + " WHERE " + where;
		QueryDataSet qds = new QueryDataSet();
		qds.setQuery(new QueryDescriptor(db, strSQL, true));
		qds.open();
		if (qds.getRowCount() == 0)
			return;
		QueryDataSet newqds = new QueryDataSet();
		strSQL = "SELECT * FROM " + table + " WHERE 1=2";
		newqds.setQuery(new QueryDescriptor(db, strSQL, null, true, Load.ALL));
		QueryResolver qr = new QueryResolver();
		qr.setDatabase(db);
		qr.setUpdateMode(UpdateMode.KEY_COLUMNS);
		newqds.setResolver(qr);
		newqds.open();
		for (int i = 0; i < keys.length; i++)
			newqds.getColumn(keys[i]).setRowId(true);

		qds.first();
		do {
			newqds.insertRow(false);
			for (int i = 0; i < qds.getColumnCount(); i++) {
				Variant v = new Variant();
				qds.getVariant(i, v);
				newqds.setVariant(i, v);
			}
			for (int i = 0; i < col.length; i++) {
				if (newvalue[i] instanceof String)
					newqds.setString(col[i], (String) newvalue[i]);
				if (newvalue[i] instanceof Variant)
					newqds.setVariant(col[i], (Variant) newvalue[i]);
			}
		} while (qds.next());
		newqds.saveChanges();

	}
}
