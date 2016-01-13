package com.efounder.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.borland.dx.dataset.Column;
import com.borland.dx.dataset.DataRow;
import com.borland.dx.dataset.UpdateMode;
import com.borland.dx.dataset.Variant;
import com.core.core.Lookup;
import com.core.xml.StubObject;
import com.efounder.builder.base.data.EFRowSet;
import com.efounder.builder.meta.dctmodel.DCTMetaData;
import com.efounder.builder.meta.domodel.DOMetaData;
import com.efounder.eai.EAI;
import com.efounder.eai.data.JParamObject;
import com.efounder.eai.data.JResponseObject;
import com.efounder.sql.JConnection;
import com.mysql.jdbc.ResultSetMetaData;

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
	 * Description: ����DBTools��ʵ�ֶ���
	 * @return DBTools
	 */
	public static DBTools getDefault() {
		if ( defaultDBTools == null ) {
	      defaultDBTools = (DBTools) Lookup.getDefault().lookup(com.efounder.db.DBTools.class);
	    }
	    return defaultDBTools;
	}
	  
	/**
	 * ����DBA��Schema
	 * @param PO JParamObject
	 * @return String
	 */
	public abstract String getDBASchema(Object PO) ;
	public abstract String getUSRSchema();
	
	/**
	 * @param PO JParamObject
	 * @return String
	 */
	public abstract String getUSRSchema(Object PO);
	  
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
	public static String selectSql(String tableName, String Fields, String Condition) {
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
	public static void ResultSetToStubObject(ResultSet rs, StubObject so) throws Exception {
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
	
	public static String getDBOSchemaUser(JParamObject pEnv) {
	//  String strLanName = "OWNER";
	//  String strDBID = pEnv.GetValueByEnvName("DBNO", "");
	//  return "DB" + strDBID + "_" + strLanName;
	    String dbOwner = pEnv.GetValueByEnvName("dbOwner",null);
	    String tableName = null;
	    if ( dbOwner == null ) dbOwner = getDefault().getDBASchema(pEnv);
	    return dbOwner;
	}

//	public static void DataRowToStubObject(DataSet ds, StubObject so)
//			throws Exception {
//		int colCount = ds.getColumnCount();
//		String colName = null;
//		Object value;
//		for (int i = 0; i < colCount; i++) {
//			Column col = ds.getColumn(i);
//			Variant Value = new Variant();
//			colName = col.getColumnName();
//			ds.getVariant(colName, Value);
//			so.setObject(colName, Value);
//			int type = Value.getType();
//			switch (type) {
//			case Variant.STRING:
//				so.setString(colName, Value.getString());
//				break;
//			case Variant.ASSIGNED_NULL:
//			case Variant.UNASSIGNED_NULL:
//				so.setString(colName, "");
//				break;
//			default:
//				so.setObject(colName, Value);
//			}
//		}
//	}
//
//	public static List getResultList(Database db, String table, String col,
//			String where) {
//		String sql = DBTools.selectSql(table, col, where);
//		QueryDescriptor qd = new QueryDescriptor(db, sql, null, true, Load.ALL);
//		QueryDataSet qds1 = new QueryDataSet();
//		qds1.setQuery(qd);
//		qds1.open();
//		List list = new ArrayList();
//		if (qds1.getRowCount() > 0) {
//			do {
//				StubObject so = new StubObject();
//				try {
//					DBTools.DataRowToStubObject(qds1, so);
//					list.add(so);
//				} catch (Exception e) {
//				}
//			} while (qds1.next());
//		}
//		qds1.close();
//		return list;
//	}

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

//	public static Map getResultHash(Database db, String table, String keycol,
//			String valuecol, String where) {
//		List list = DBTools.getResultList(db, table, keycol + "," + valuecol,
//				where);
//		StubObject so;
//		Map hash = new TreeMap();
//		for (int i = 0; i < list.size(); i++) {
//			so = (StubObject) list.get(i);
//			hash.put(so.getString(keycol, ""), so.getString(valuecol, ""));
//		}
//		return hash;
//	}

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

//	public static void saveObjectToNew(Connection conn, String table,
//			String where, String[] keys, String[] col, Object[] newvalue) {
//		Database db = new Database();
//		db.setJdbcConnection(conn);
//		String strSQL = "SELECT * FROM " + table + " WHERE " + where;
//		QueryDataSet qds = new QueryDataSet();
//		qds.setQuery(new QueryDescriptor(db, strSQL, true));
//		qds.open();
//		if (qds.getRowCount() == 0)
//			return;
//		QueryDataSet newqds = new QueryDataSet();
//		strSQL = "SELECT * FROM " + table + " WHERE 1=2";
//		newqds.setQuery(new QueryDescriptor(db, strSQL, null, true, Load.ALL));
//		QueryResolver qr = new QueryResolver();
//		qr.setDatabase(db);
//		qr.setUpdateMode(UpdateMode.KEY_COLUMNS);
//		newqds.setResolver(qr);
//		newqds.open();
//		for (int i = 0; i < keys.length; i++)
//			newqds.getColumn(keys[i]).setRowId(true);
//
//		qds.first();
//		do {
//			newqds.insertRow(false);
//			for (int i = 0; i < qds.getColumnCount(); i++) {
//				Variant v = new Variant();
//				qds.getVariant(i, v);
//				newqds.setVariant(i, v);
//			}
//			for (int i = 0; i < col.length; i++) {
//				if (newvalue[i] instanceof String)
//					newqds.setString(col[i], (String) newvalue[i]);
//				if (newvalue[i] instanceof Variant)
//					newqds.setVariant(col[i], (Variant) newvalue[i]);
//			}
//		} while (qds.next());
//		newqds.saveChanges();
//
//	}
	
	/**
	 *
	 * @param   PO JParamObject
	 * @param meta DCTMetaData
	 * @return     String
	 */
	public static String getDBAObject(JParamObject PO, DCTMetaData meta) {
//       String mlang = "0";
//       if (meta != null && meta.getDoMetaData() != null) {
//           mlang = meta.getDoMetaData().getString(SYS_OBJECTS._OBJ_LANG_, "");
//       }
//       if (mlang.equals(SYS_OBJECTS._OBJ_LANG_MLANG)) return getDBAObjectMLang(PO, meta);
//       return getDBAObjectMUnit(PO, meta);
		return getDBAObject(PO, meta.getDoMetaData());
	}
   
	public static boolean ChangeSchema(JConnection pConn, String strName) {
		Statement stmt = null;
		try {
		      String strCmd = "alter session set current_schema =" + strName;
		      stmt = pConn.createStatement();
		      stmt.executeUpdate(strCmd);
		      // pConn.createStatement().executeUpdate(strCmd); modi by fsz
		      return true;
		} catch (Exception E) {
		      E.printStackTrace();
		      return false;
		} finally {
			if (stmt != null)
				pConn.BackStatement(stmt, null);
		}
	}
	
	/**
	   *
	   * @param PO Object
	   * @param OBJ_ID String
	   * @return String
	   */
	  public static String getDBAObject(Object po,String OBJ_ID) {
	    JParamObject PO = (JParamObject)po;
//	    String dbOwner = PO.GetValueByEnvName("dbOwner",null);
//	    //
////	    String tableName = PO.GetValueByEnvName("DBA_"+OBJ_ID,null);
//	    String tableName = null;
//	    //
////	    if ( tableName == null ) {
//	      if ( dbOwner == null ) dbOwner = getDefault().getDBASchema(PO);
//	      tableName = dbOwner+"."+OBJ_ID+getDefault().getPostfix(po,OBJ_ID);
////	      PO.SetValueByEnvName("DBA_"+OBJ_ID,tableName);
////	    }
//	    return tableName;
	    return OBJ_ID;
	  }
	/**
	 *
	 * @param PO JParamObject
	 * @param meta DOMetaData
	 * @return String
	 */
	public static String getDBAObject(JParamObject PO, DOMetaData meta) {
//		String mlang = "0";
//		if (meta != null) mlang = meta.getString(SYS_OBJECTS._OBJ_LANG_, "");
//		if (mlang.equals(SYS_OBJECTS._OBJ_LANG_MLANG)) return getDBAObjectMLang(PO, meta);
		return getDBAObjectMUnit(PO, meta);
	}
	
	/**
	 *
	 * @param   PO JParamObject
	 * @param meta DCTMetaData
	 * @return     String
	 */
	public static String getDBAObjectMUnit(JParamObject PO, DCTMetaData meta) {
		return getDBAObjectMUnit(PO, meta.getDoMetaData());
	}
	
	/**
	 *
	 * @param PO JParamObject
	 * @param meta DOMetaData
	 * @return String
	 */
	public static String getDBAObjectMUnit(JParamObject PO, DOMetaData meta) {
//		String dbOwner = PO.GetValueByEnvName("dbOwner", null);
//		if (dbOwner == null) dbOwner = getDefault().getDBASchema(PO);
//		String munit = "0";
//		if (meta != null) munit = meta.getString(SYS_OBJECTS._OBJ_MUNIT_, "");
		String table = meta.getObjID();
//		//如果是多单位、多组织机构
//		if (munit.equals(SYS_OBJECTS._OBJ_MUNIT_UNIT) || munit.equals(SYS_OBJECTS._OBJ_MUNIT_CODE))
//			table += "_V_ALLUNIT";
//		return dbOwner + "." + table + getDefault().getPostfix();
		return table;
	}
	
	public static List SimpleQuery(JParamObject PO,String table, String col, String where) {
	  return SimpleQuery(PO,table,col,where,null,null,null,null,null);
	}
	
	public static List SimpleQuery(String table, String col, String where) {
	  return SimpleQuery(null,table,col,where,null,null,null,null,null);
	}
	
	public static List SimpleQuery(String table, String col, String where,String group,String order,String qxbh,String qxcol,String bzw){
	  return SimpleQuery(null,table,col,where,group,order,qxbh,qxcol,bzw);
	}
	public static List SimpleQuery(JParamObject PO,String table, String col, String where,String group,String order,String qxbh,String qxcol,String bzw) {
		if (PO == null)
			PO = JParamObject.Create();
		java.util.List list = new ArrayList();
		Map map = new HashMap();
		map.put("TABLE", table);
		map.put("FIELD", col);
		map.put("WHERE", where);
		if (group != null)
			map.put("GROUP", group);
		if (order != null)
			map.put("ORDER", order);
		if (qxbh != null)
			map.put("QXBH", qxbh);
		if (qxcol != null)
			map.put("QXCOL", qxcol);
		if (bzw != null)
			map.put("QXW", bzw);

		list.add(map);
		JResponseObject RO = null;
		try {
			RO = (JResponseObject) EAI.DAL.IOM("DBService", "SimpleQuery", PO,
					list);
			list = (java.util.List) RO.getResponseObject();
		} catch (Exception ex) {
			list = null;
			ex.printStackTrace();
		}

		return list;
	}
}
