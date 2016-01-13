package esyt.framework.com.builder.base.data;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.collections.comparators.ComparatorChain;

import com.etsoft.pub.util.EMPReflectUtil;

import esyt.framework.com.builder.base.util.EFRowSetComparator;
import esyt.framework.com.db.DBTools;
import esyt.framework.com.pub.util.StringFunction;

public class DataSetUtils {
	/**
   *
   */
	protected DataSetUtils() {
	}

	/**
	 * 
	 * @param stmt  Statement
	 * @param tableName String
	 * @param fields String
	 * @param where String
	 * @return ResultSet
	 * @throws Exception
	 */
	public static ResultSet getResultSet(Statement stmt, String tableName, String fields, String where) throws Exception {
		ResultSet resultSet = null;
		String          SQL = DBTools.selectSql(tableName, fields, where);
		resultSet = stmt.executeQuery(SQL);
		return resultSet;
	}

	/**
	 * 
	 * @param resultSet ResultSet
	 * @param dataSet EFDataSet
	 * @return EFDataSet
	 * @throws Exception
	 */
	public static EFDataSet resultSet2DataSet(ResultSet resultSet, EFDataSet dataSet) throws Exception {
		return resultSet2DataSet(resultSet, dataSet, true);
	}
	
	/**
	 * 
	 * @param resultSet ResultSet
	 * @param dataSet EFDataSet
	 * @return EFDataSet
	 * @throws Exception
	 */
	public static EFRowSet javaBean2RowSet(Object javaBean) throws Exception {
		// 生成ESPRowSet
		EFRowSet          rowset = EFRowSet.getInstance();
		Class       reflectClass = EMPReflectUtil.getReflectClass(javaBean.getClass().getName());	
		Field[]           fields = reflectClass.getDeclaredFields();
		Field              field;

		
		for(int i = 0; i < fields.length; i++) {
			field = fields[i];
			field.setAccessible(true);
			EMPReflectUtil.setDeclareFieldValueToRowSet(rowset, field, field.get(javaBean));
		}
		return rowset;
	}

	/**
	 * 
	 * @param resultSet ResultSet
	 * @param dataSet EFDataSet
	 * @return EFDataSet
	 * @throws Exception
	 */
	public static EFDataSet javaBean2DataSet(List list) throws Exception {
		EFDataSet dataset = EFDataSet.getInstance();
		for(int i = 0; i < list.size(); i++) {
			dataset.insertRowSet(javaBean2RowSet(list.get(i)));
		}
		return dataset;
	}
	
	/**
	 * 
	 * @param resultSet ResultSet
	 * @param dataSet EFDataSet
	 * @param btrim  boolean
	 * @return EFDataSet
	 * @throws Exception
	 */
	public static EFDataSet resultSet2DataSet(ResultSet resultSet, EFDataSet dataSet, boolean btrim) throws Exception {
		ESPRowSet rowSet = null;
		if(dataSet == null) dataSet = EFDataSet.getInstance();
		while (resultSet.next()) {
			// 生成ESPRowSet
			rowSet = EFRowSet.getInstance();
			// 将ResultSet中的一行生成到RowSet
			rowSet = resultSet2RowSet(resultSet, rowSet, btrim);
			// 插入DataSet中
			dataSet.insertRowSet(rowSet);
			// 形成主键索引
			dataSet.buildPrimeKeyIndex(rowSet);
		}

		return dataSet;
	}

	/**
	 * 
	 * @param resultSet ResultSet
	 * @param dataSet EFDataSet
	 * @param btrim  boolean
	 * @param unique boolean
	 * @return EFDataSet
	 * @throws Exception
	 */
	public static EFDataSet resultSet2DataSet(ResultSet resultSet, EFDataSet dataSet, boolean btrim, boolean unique) throws Exception {
		ESPRowSet rowSet = null;
		while (resultSet.next()) {
			// 生成ESPRowSet
			rowSet = EFRowSet.getInstance();
			// 将ResultSet中的一行生成到RowSet
			rowSet = resultSet2RowSet(resultSet, rowSet, btrim);
			// 插入DataSet中
			dataSet.insertRowSet(rowSet, unique);
		}
		return dataSet;
	}

	/**
	 * 
	 * @param resultSet  ResultSet
	 * @param rowSet ESPRowSet
	 * @param btrim boolean
	 * @return ESPRowSet
	 * @throws Exception
	 */
	public static ESPRowSet resultSet2RowSet(ResultSet resultSet, ESPRowSet rowSet, boolean btrim) throws Exception {
		// 不动原来的方法
		if (btrim)
			return resultSet2RowSet(resultSet, rowSet);
		// 取出ResultSet的MetaData
		ResultSetMetaData rmd = resultSet.getMetaData();
		// 处理每一列数据
		int colCount = rmd.getColumnCount();
		String colName = null;
		Object value;
		for (int i = 1; i <= colCount; i++) {
			colName = rmd.getColumnName(i);
			value = resultSet.getObject(colName);
			// BLOB CLOB不传递
			if (rmd.getColumnType(i) == java.sql.Types.BLOB
					|| rmd.getColumnType(i) == java.sql.Types.CLOB) {
				value = null;
			}
			// 如果
			if (rmd.getColumnType(i) == java.sql.Types.DATE || 
				rmd.getColumnType(i) == java.sql.Types.TIME || 
				rmd.getColumnType(i) == java.sql.Types.TIMESTAMP) {
				
				value = resultSet.getObject(colName);
				if (value instanceof java.util.Date) {
					value = ((java.util.Date) value);
				}
			} else {// 没法弄了，
				value = resultSet.getObject(colName);
				if (value instanceof java.util.Date) {
					continue;
				}
			}

			if (value != null && value instanceof String)
				rowSet.putString(colName, (String) value, btrim);
			else if (value != null)
				rowSet.putObject(colName, value);
		}
		return rowSet;
	}

	/**
	 * 
	 * @param resultSet ResultSet
	 * @param efDataSet EFDataSet
	 * @return EFDataSet
	 * @throws Exception
	 */
	public static ESPRowSet resultSet2RowSet(ResultSet resultSet, ESPRowSet rowSet) throws Exception {
		// 取出ResultSet的MetaData
		ResultSetMetaData rmd = resultSet.getMetaData();
		// 处理每一列数据
		int colCount = rmd.getColumnCount();
		String colName = null;
		Object value = null;
		for (int i = 1; i <= colCount; i++) {
			colName = rmd.getColumnLabel(i);
			value = resultSet.getObject(colName);
			// 调整一下顺序，很重要
			// //BLOB CLOB不传递
			// if (rmd.getColumnType(i) == java.sql.Types.BLOB
			// || rmd.getColumnType(i) == java.sql.Types.CLOB) {
			// value = null;
			// }
			// 如果
			if (rmd.getColumnType(i) == java.sql.Types.DATE || 
				rmd.getColumnType(i) == java.sql.Types.TIME || 
				rmd.getColumnType(i) == java.sql.Types.TIMESTAMP) {
				
				value = resultSet.getObject(colName);
				if (value instanceof java.util.Date) {
					value = ((java.util.Date) value);
				}
			} else {// 没法弄了，
				value = resultSet.getObject(colName);
				if (value instanceof java.util.Date) {
					continue;
				}
			}
			// BLOB CLOB不传递
			if (rmd.getColumnType(i) == java.sql.Types.BLOB
					|| rmd.getColumnType(i) == java.sql.Types.CLOB) {
				value = null;
			}
			if (value != null)
				rowSet.putObject(colName, value);
		}
		return rowSet;
	}

	/**
	 * 对一个EFDataSet排序，可以指定一列或者多列以及每列进行升序或者降序
	 * 
	 * @param ds EFDataSet
	 * @param column String[]
	 * @param type String[]
	 * @param ascending  boolean[]
	 */
	public static void sortEFDataSet(EFDataSet ds, String[] column, boolean ascending) {
		ComparatorChain chain = new ComparatorChain();
		for (int i = 0; i < column.length; i++) {
			boolean ascend = true;
			Comparator cp = null;
			EFRowSetComparator comparator = new EFRowSetComparator(column[i], ascending);
			chain.addComparator(comparator);
		}
		// 一个比较器也没有，直接返回
		if (chain.size() == 0 || ds.getRowCount() == 0) return;
		Object[] rss = ds.getRowSetList().toArray();
		java.util.Arrays.sort(rss, chain);
		// 把排序后的RowSet赋回去
		ds.getRowSetList().clear();
		for (int i = 0; i < rss.length; i++) {
			EFRowSet rs = (EFRowSet) rss[i];
			ds.addRowSet(rs);
		}
	}

	/**
	 * 对一个EFDataSet排序，可以指定一列或者多列以及每列进行升序或者降序
	 * 
	 * @param ds EFDataSet
	 * @param column String[]
	 * @param type String[]
	 * @param ascending boolean[]
	 */
	public static void sortEFDataSet(EFDataSet ds, String[] column, boolean[] ascending) {
		ComparatorChain chain = new ComparatorChain();
		for (int i = 0; i < column.length; i++) {
			boolean ascend = true;
			if (ascending == null)
				ascend = true;
			else
				ascend = ascending[i];
			EFRowSetComparator comparator = new EFRowSetComparator(column[i], ascend);
			chain.addComparator(comparator);
		}
		// 一个比较器也没有，直接返回
		if (chain.size() == 0 || ds.getRowCount() == 0) return;
		Object[] rss = ds.getRowSetList().toArray();
		java.util.Arrays.sort(rss, chain);
		// 把排序后的RowSet赋回去
		ds.getRowSetList().clear();
		for (int i = 0; i < rss.length; i++) {
			EFRowSet rs = (EFRowSet) rss[i];
			ds.addRowSet(rs);
		}
	}

	/**
	 * 恢复每个RowSet在某列排序前的索引
	 * 
	 * @param ds EFDataSet
	 * @param column String
	 */
	public static void recoverRowSetIndex(EFDataSet ds, String column) {
		String[] columns = new String[] { column + "_ROWID" };
		sortEFDataSet(ds, columns, null);
	}

	/**
	 * 设置每个RowSet在某列排序前的索引
	 * 
	 * @param ds EFDataSet
	 * @param column String
	 */
	public static void setRowSetIndex(EFDataSet ds, String column) {
		java.util.List rowSets = ds.getRowSetList();
		if (rowSets == null) return;
		for (int i = 0; i < ds.getRowCount(); i++) {
			EFRowSet rs = ds.getRowSet(i);
			String rowid = StringFunction.FillZeroFromBegin(i, 10); // 10位长度，很给力了
			rs.putString(column + "_ROWID", rowid);
		}
	}

	/**
	 * 并集
	 * 
	 * @param lists List
	 * @return List
	 */
	public static java.util.List unionList(java.util.List<java.util.List> lists) {
		java.util.Set hashSet = new java.util.HashSet();
		java.util.List list = null;
		for (int i = 0; lists != null && i < lists.size(); i++) {
			list = lists.get(i);
			if (list == null) continue;
			hashSet.addAll(list);
		}
		java.util.List result = new java.util.ArrayList();
		result.addAll(hashSet);
		return result;
	}

	/**
	 * 交集
	 * 
	 * @param lists List
	 * @return List
	 */
	public static java.util.List intersectList(java.util.List<java.util.List> lists) {
		java.util.List unionList = unionList(lists);
		java.util.List result = new java.util.ArrayList();
		java.util.List list = null;
		Object value = null;
		for (int i = 0; unionList != null && i < unionList.size(); i++) {
			value = unionList.get(i);
			boolean exists = true;
			for (int j = 0; lists != null && j < lists.size(); j++) {
				list = lists.get(j);
				if (!list.contains(value)) {
					exists = false;
					break;
				}
			}
			if (exists) result.add(value);
		}
		return result;
	}
}
