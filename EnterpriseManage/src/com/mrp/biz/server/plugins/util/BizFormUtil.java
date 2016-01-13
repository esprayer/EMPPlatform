package com.mrp.biz.server.plugins.util;

import java.sql.ResultSet;

import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.Map;

import com.etsoft.pub.util.StringFunction;

import esyt.framework.com.builder.base.data.DataSetUtils;
import esyt.framework.com.builder.base.data.EFDataSet;
import esyt.framework.com.builder.base.data.EFRowSet;
import esyt.framework.com.core.foundation.sql.JStatement;
import esyt.framework.com.dbform.bizmodel.EFFormDataModel;
import esyt.framework.com.dbform.meta.domodel.SYS_OBJCOLS;

public class BizFormUtil {
	
	public BizFormUtil() {
	}

	public static EFRowSet getBIZMetaData(JStatement stmt, String tableName) {
		EFRowSet rowSet = getSYS_OBJECTS(stmt, tableName);
		EFDataSet dataset = getSYS_OBJCOLS(stmt, tableName);;
		rowSet.setDataSet(SYS_OBJCOLS._SYS_OBJCOLS_, dataset);
		
		return rowSet;
	}
	
	public static EFRowSet getSYS_OBJECTS(JStatement stmt, String tableName) {
		ResultSet rs;
		String strSql = "select * from SYS_OBJECTS where OBJ_ID = '" + tableName + "'";
		EFDataSet ds = EFDataSet.getInstance(tableName);
		try {
			rs = stmt.executeQuery(strSql);
			ds = DataSetUtils.resultSet2DataSet(rs, ds);
		} catch (Exception e) {
			e.printStackTrace();
			ds = EFDataSet.getInstance();
		}
		if(ds.getRowCount() > 0) return ds.getRowSet(0);
		else return EFRowSet.getInstance();
	}
	
	public static EFDataSet getSYS_OBJCOLS(JStatement stmt, String tableName) {
		ResultSet rs;
		String strSql = "select * from SYS_OBJCOLS where OBJ_ID = '" + tableName + "'";
		EFDataSet ds = EFDataSet.getInstance(tableName);
		try {
			rs = stmt.executeQuery(strSql);
			ds = DataSetUtils.resultSet2DataSet(rs, ds);
		} catch (Exception e) {
			e.printStackTrace();
			ds = EFDataSet.getInstance();
		}
		return ds;
	}
	
	public static EFRowSet getSYS_MDL_VAL(JStatement stmt, String mdl_key) {
		ResultSet rs;
		String strSql = "select * from SYS_MDL_VAL where 2 > 1 ";
		if(mdl_key != null && !mdl_key.trim().equals("")) {
			strSql += " and MDL_KEY = '" + mdl_key + "'";
		}
		EFDataSet ds = EFDataSet.getInstance("SYS_MDL_VAL");
		try {
			rs = stmt.executeQuery(strSql);
			ds = DataSetUtils.resultSet2DataSet(rs, ds);
		} catch (Exception e) {
			e.printStackTrace();
			ds = EFDataSet.getInstance();
		}
		if(ds.getRowCount() > 0) return ds.getRowSet(0);
		else return EFRowSet.getInstance();
	}
	
	public static EFRowSet getSYS_MDL_BH(JStatement stmt, String F_BHLX, String F_DATE) {
		ResultSet rs;
		String strSql = "select * from SYS_MDL_BH where F_DATE = '" + F_DATE + "' ";
		if(F_BHLX != null && !F_BHLX.trim().equals("")) {
			strSql += " and F_BHLX = '" + F_BHLX + "'";
		}
		EFDataSet ds = EFDataSet.getInstance("SYS_MDL_BH");
		try {
			rs = stmt.executeQuery(strSql);
			ds = DataSetUtils.resultSet2DataSet(rs, ds);
		} catch (Exception e) {
			e.printStackTrace();
			ds = EFDataSet.getInstance();
		}
		if(ds.getRowCount() > 0) return ds.getRowSet(0);
		else return EFRowSet.getInstance();
	}
	
	public static void saveRowSetData(JStatement stmt, String tableName, EFRowSet rowSet, EFDataSet bizMetaData) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); // yyyyMMdd
		String key = "";
		EFRowSet colers = null;
		String insertsql = "insert into " + tableName + "(";
		String valuesql = "values(";

		for (int i = 0; i < bizMetaData.getRowCount(); i++) {
			colers = bizMetaData.getRowSet(i);
			key = colers.getString("COL_ID", "");
			Object o = FormBillFieldUtil.getValue(colers.getString("COL_ID", ""), rowSet, null);

			if (o instanceof String && ((String) o).trim().length() == 0) continue;
			if (o != null) {
				if (o != null) {
					insertsql += key + ",";
					if (colers.getString("COL_TYPE", "C").equals("N")) {
						int decn = getNumberDecn(colers);
						if (o instanceof Double && ((Double) o).isNaN())
							o = "0";
						if (o instanceof Double && ((Double) o).isInfinite())
							o = "0";
						if (o instanceof Number && colers.getString("COL_EDIT", "1").equals("5")) {
							valuesql += o  + ",";
							continue;
						} else if (o instanceof Number) {
							valuesql += "round(" + o + "," + decn + "),";
							continue;
						}
						if (o instanceof String) {
							valuesql += "round(" + o + "," + decn + "),";
							continue;
						}
					}
					if ((colers.getString("COL_TYPE", "C").equals("D") || colers.getString("COL_TYPE", "C").equals("T")) && o instanceof String) {
						valuesql += "to_timestamp('" + o + "','yyyymmddhh24missff6'),";
					} else if (colers.getString("COL_TYPE", "C").equals("C") && o instanceof java.util.Date) {
						java.text.SimpleDateFormat fpsdf = new java.text.SimpleDateFormat("yyyyMMdd");
						valuesql += "'" + fpsdf.format(o) + "',";
					} else if (o instanceof String) {
						// 转义字符
						String s = (String) o;
						if (s.indexOf("'") >= 0) o = StringFunction.replaceString(s, "'", "''");
						valuesql += "'" + o + "',";
					} else if (o instanceof java.util.Date) {
						java.util.Date dd = (java.util.Date) o;
						valuesql += "'" + sdf.format(dd) + "',";
					} else if (o instanceof Integer) {
						valuesql += ((Integer) o).intValue() + ",";					
					} else{
						valuesql += o + ",";
					}
				}
			}
		}
		insertsql = insertsql.substring(0, insertsql.length() - 1) + ")";
		valuesql = valuesql.substring(0, valuesql.length() - 1) + ")";
		System.out.println("sql|||" + insertsql + valuesql);
		stmt.executeUpdate(insertsql + valuesql);

	}

	public static int getNumberDecn(EFRowSet colRowSet) {

		String colType = colRowSet.getString("COL_TYPE", null);
		if ("N".equals(colType)) {
			if(colRowSet.getString("COL_EDIT", "1").equals("5")) return 0;
			
			int decn = 2;
			Object oo = colRowSet.getObject("COL_SCALE", null);
			if (oo == null)
				decn = 2;
			if (oo instanceof String) {
				try {
					decn = Integer.parseInt((String) oo);
				} catch (Exception e) {
				}
			}
			if (oo instanceof Number) {
				decn = ((Number) oo).intValue();
			}
			if (decn == 0 || decn > 6)
				decn = 2;
			return decn;
		}
		return 2;
	}

	public static void formDataDeleter(JStatement stmt, EFFormDataModel model) throws Exception {
		String                            where = "";
		String                              sql = "";
		java.util.List<String>   dataSetKeyList = new java.util.ArrayList<String>();
		EFDataSet                           eds = model.getBillDataSet();
		String                            table = eds.getTableName();
		int                           itemcount = eds.getRowCount();
		
		for (int i = 0; i < itemcount; i++) {
			EFRowSet ers = eds.getRowSet(i);
			where = getWhere(ers);
			sql = "delete from " + table + " where " + where;
			System.out.println("delete head sql|||" + sql);
			stmt.execute(sql);
			dataSetKeyList = ers.getDataSetKeyList();
			for (int j = 0; dataSetKeyList != null && j < dataSetKeyList.size(); j++) {
				eds = ers.getDataSet(dataSetKeyList.get(j));
				sql = "delete from " + dataSetKeyList.get(j) + " where F_KJQJ = '" + ers.getString("F_KJQJ", null)
			        + "' and F_GUID = '" + ers.getString("F_GUID", null) + "' ";
				if(model.getFormSaveType() != EFFormDataModel._FORM_SAVE_ITEMS_ALL_) {
					sql += " and F_FLGUID = '" + eds.getRowSet(j).getString("F_FLGUID", null) + "'";
					if(model.getFormSaveType() == EFFormDataModel._FORM_SAVE_ITEM_) {
						sql += " and F_FLBH = '" + eds.getRowSet(j).getString("F_FLBH", "") + "' ";
					}
				}
				
				System.out.println("delete item sql|||" + sql);
				stmt.execute(sql);
			}
		}
	}
	
	public static String getWhere(EFRowSet rowSet) throws Exception {
		String where = " F_KJQJ = '" + rowSet.getString("F_KJQJ", "") + "' and F_GUID = '" + rowSet.getString("F_GUID", "") + "'";
		return where;
	}
	
	public static EFDataSet getFormDataSet(JStatement stmt, String OBJ_ID, String where, String orderBy) throws Exception {
		EFDataSet   dataSet = EFDataSet.getInstance(OBJ_ID);
		String       strSql = "select * from " + OBJ_ID + " where " + where + " order by " + orderBy;
		ResultSet        rs = stmt.executeQuery(strSql);
		DataSetUtils.resultSet2DataSet(rs, dataSet);
		return dataSet;
	}
	
	public static void convertRowSet(EFRowSet newRowSet, EFRowSet oldRowSet) {
		Map oldDataMap = oldRowSet.getDataMap();
		Map newDataMap = newRowSet.getDataMap();
		Iterator oldIt = oldDataMap.entrySet().iterator();
		while(oldIt.hasNext()) {
			Map.Entry oldM = (Map.Entry)oldIt.next();
			if(oldM.getKey().equals("F_CRDATE")) continue;
			newDataMap.put(oldM.getKey(), oldM.getValue());
		}
	}
	
	public static void convertDataSet(EFDataSet newDataSet, EFDataSet oldDataSet) {
		for(int i = 0; i < oldDataSet.getRowCount(); i++) {
			convertRowSet(newDataSet.getRowSet(i), oldDataSet.getRowSet(i));
		}
	}
}
