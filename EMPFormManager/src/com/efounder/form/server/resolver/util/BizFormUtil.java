package com.efounder.form.server.resolver.util;

import java.sql.ResultSet;

import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.efounder.builder.base.data.DataSetUtils;
import com.efounder.builder.base.data.EFDataSet;
import com.efounder.builder.base.data.EFRowSet;
import com.efounder.builder.meta.domodel.SYS_OBJCOLS;
import com.efounder.dctbuilder.data.ColumnMetaData;
import com.efounder.eai.data.JParamObject;
import com.efounder.form.EFFormDataModel;
import com.efounder.sql.JStatement;
import com.metadata.bizmodel.SYS_MODEL;
import com.pub.util.StringFunction;


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
	
	public static void saveRowSetData(JStatement stmt, String tableName, EFRowSet rowSet) throws Exception {
		EFRowSet   bizMetaData = null;

		bizMetaData = BizFormUtil.getBIZMetaData(stmt, tableName);
		
		BizFormUtil.saveRowSetData(stmt, tableName, rowSet, bizMetaData.getDataSet(SYS_OBJCOLS._SYS_OBJCOLS_));

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
					if (colers.getString("COL_TYPE", "C").equals("D") && o instanceof String) {
						valuesql += "'" + o + "',";
					} else if(colers.getString("COL_TYPE", "C").equals("T") && o instanceof String) {
						valuesql += "to_timestamp('" + o + "','yyyymmddhh24missff6'),";
					} else if (colers.getString("COL_TYPE", "C").equals("C") && o instanceof java.util.Date) {
						java.text.SimpleDateFormat fpsdf = new java.text.SimpleDateFormat("yyyyMMdd");
						valuesql += "'" + fpsdf.format(o) + "',";
					} else if (colers.getString("COL_TYPE", "C").equals("C") && o instanceof Long) {
						valuesql += "'" + o + "',";
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
		stmt.executeUpdate(insertsql + valuesql);
	}

	public static void saveRowSetData(JStatement stmt, String tableName, EFRowSet rowSet, List colList) throws Exception {
		SimpleDateFormat          sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); // yyyyMMdd
		String                  colID = "";
		String              insertsql = "insert into " + tableName + "(";
		String               valuesql = "values(";
		ColumnMetaData    colMetaData = null;
		
		for (int i = 0; i < colList.size(); i++) {
			colMetaData = (ColumnMetaData) colList.get(i);
			colID = colMetaData.getColid();
			Object o = FormBillFieldUtil.getValue(colID, rowSet, null);

			if (o instanceof String && ((String) o).trim().length() == 0) continue;
			if (o != null) {
				if (o != null) {
					insertsql += colID + ",";
					if (colMetaData.getColType().equals("N")) {
						int decn = getNumberDecn(colMetaData);
						if (o instanceof Double && ((Double) o).isNaN())
							o = "0";
						if (o instanceof Double && ((Double) o).isInfinite())
							o = "0";
						if (o instanceof Number && colMetaData.getColEditType() == 5) {
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
					if (colMetaData.getColType().equals("D") && o instanceof String) {
						valuesql += "'" + o + "',";
					} else if(colMetaData.getColType().equals("T") && o instanceof String) {
						valuesql += "to_timestamp('" + o + "','yyyymmddhh24missff6'),";
					} else if (colMetaData.getColType().equals("C") && o instanceof java.util.Date) {
						java.text.SimpleDateFormat fpsdf = new java.text.SimpleDateFormat("yyyyMMdd");
						valuesql += "'" + fpsdf.format(o) + "',";
					} else if (colMetaData.getColType().equals("C") && o instanceof Long) {
						valuesql += "'" + o + "',";
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
		stmt.executeUpdate(insertsql + valuesql);
	}
	
	public static void updateRowSetData(JStatement stmt, String tableName, EFRowSet rowSet, EFDataSet bizMetaData) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); // yyyyMMdd
		String           key = "";
		String         isKey = "";
		EFRowSet      colers = null;
		String      whereSql = "";
		String     updateSql = " update " + tableName + " set ";
		String      colValue = "";
		String      valuesql = " ";

		for (int i = 0; i < bizMetaData.getRowCount(); i++) {
			colers = bizMetaData.getRowSet(i);
			key = colers.getString("COL_ID", "");
			isKey = colers.getString("COL_ISKEY", "0");
			Object o = FormBillFieldUtil.getValue(colers.getString("COL_ID", ""), rowSet, null);

			if (o instanceof String && ((String) o).trim().length() == 0) continue;
			if (o != null) {
				if (o != null) {
					if (colers.getString("COL_TYPE", "C").equals("N")) {
						int decn = getNumberDecn(colers);
						if (o instanceof Double && ((Double) o).isNaN())
							o = "0";
						if (o instanceof Double && ((Double) o).isInfinite())
							o = "0";
						if (o instanceof Number && colers.getString("COL_EDIT", "1").equals("5")) {
							colValue = "" + o;
							//如果是主键，则只能做为where条件
							if(isKey.equals("1")) {
								if(!whereSql.trim().equals("")) {
									whereSql += " and ";
								}
								whereSql += key + " = " + colValue;
							}
							//否则作为更新一部分
							else {
								if(!valuesql.trim().equals("")) {
									valuesql += " , ";
								}
								valuesql += key + " = " + colValue;
							}
							continue;
						} else if (o instanceof Number) {
							colValue = "round(" + o + "," + decn + ")";
							//如果是主键，则只能做为where条件
							if(isKey.equals("1")) {
								if(!whereSql.trim().equals("")) {
									whereSql += " and ";
								}
								whereSql += key + " = " + colValue;
							}
							//否则作为更新一部分
							else {
								if(!valuesql.trim().equals("")) {
									valuesql += " , ";
								}
								valuesql += key + " = " + colValue;
							}
							continue;
						}
						if (o instanceof String) {
							colValue += "round(" + o + "," + decn + ")";
							//如果是主键，则只能做为where条件
							if(isKey.equals("1")) {
								if(!whereSql.trim().equals("")) {
									whereSql += " and ";
								}
								whereSql += key + " = " + colValue;
							}
							//否则作为更新一部分
							else {
								if(!valuesql.trim().equals("")) {
									valuesql += " , ";
								}
								valuesql += key + " = " + colValue;
							}
							continue;
						}
					}
					if (colers.getString("COL_TYPE", "C").equals("D") && o instanceof String) {
						colValue = "'" + o + "'";
					} else if(colers.getString("COL_TYPE", "C").equals("T") && o instanceof String) {
						
					} else if (colers.getString("COL_TYPE", "C").equals("C") && o instanceof java.util.Date) {
						java.text.SimpleDateFormat fpsdf = new java.text.SimpleDateFormat("yyyyMMdd");
						colValue = "'" + fpsdf.format(o) + "'";
					} else if (o instanceof String) {
						// 转义字符
						String s = (String) o;
						if (s.indexOf("'") >= 0) o = StringFunction.replaceString(s, "'", "''");
						colValue = "'" + o + "'";
					} else if (o instanceof java.util.Date) {
						java.util.Date dd = (java.util.Date) o;
						colValue = "'" + sdf.format(dd) + "'";
					} else if (o instanceof Integer) {
						colValue = ((Integer) o).intValue() + "";					
					} else{
						colValue = "" + o;
					}
					//如果是主键，则只能做为where条件
					if(isKey.equals("1")) {
						if(!whereSql.trim().equals("")) {
							whereSql += " and ";
						}
						whereSql += key + " = " + colValue;
					}
					//否则作为更新一部分
					else {
						if(!valuesql.trim().equals("")) {
							valuesql += " , ";
						}
						valuesql += key + " = " + colValue;
					}
				}
			}
		}
		stmt.executeUpdate(updateSql + valuesql + " where " + whereSql);
	}
	
	public static void updateRowSetData(JStatement stmt, String tableName, EFRowSet rowSet, List colList) throws Exception {
		SimpleDateFormat          sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); // yyyyMMdd
		String                  colID = "";
		String               whereSql = "";
		String              updateSql = " update " + tableName + " set ";
		String               colValue = "";
		String               valuesql = " ";
		ColumnMetaData    colMetaData = null;
		
		for (int i = 0; i < colList.size(); i++) {
			colMetaData = (ColumnMetaData) colList.get(i);
			colID = colMetaData.getColid();
			Object o = FormBillFieldUtil.getValue(colID, rowSet, null);

			if (o instanceof String && ((String) o).trim().length() == 0) continue;
			if (o != null) {
				if (o != null) {
					if (colMetaData.getColType().equals("N")) {
						int decn = getNumberDecn(colMetaData);
						if (o instanceof Double && ((Double) o).isNaN())
							o = "0";
						if (o instanceof Double && ((Double) o).isInfinite())
							o = "0";
						if (o instanceof Number && colMetaData.getColEditType() == 5) {
							colValue = "" + o;
							//如果是主键，则只能做为where条件
							if(colMetaData.isKey()) {
								if(!whereSql.trim().equals("")) {
									whereSql += " and ";
								}
								whereSql += colID + " = " + colValue;
							}
							//否则作为更新一部分
							else {
								if(!valuesql.trim().equals("")) {
									valuesql += " , ";
								}
								valuesql += colID + " = " + colValue;
							}
							continue;
						} else if (o instanceof Number) {
							colValue = "round(" + o + "," + decn + ")";
							//如果是主键，则只能做为where条件
							if(colMetaData.isKey()) {
								if(!whereSql.trim().equals("")) {
									whereSql += " and ";
								}
								whereSql += colID + " = " + colValue;
							}
							//否则作为更新一部分
							else {
								if(!valuesql.trim().equals("")) {
									valuesql += " , ";
								}
								valuesql += colID + " = " + colValue;
							}
							continue;
						}
						if (o instanceof String) {
							colValue += "round(" + o + "," + decn + ")";
							//如果是主键，则只能做为where条件
							if(colMetaData.isKey()) {
								if(!whereSql.trim().equals("")) {
									whereSql += " and ";
								}
								whereSql += colID + " = " + colValue;
							}
							//否则作为更新一部分
							else {
								if(!valuesql.trim().equals("")) {
									valuesql += " , ";
								}
								valuesql += colID + " = " + colValue;
							}
							continue;
						}
					}
					if (colMetaData.getColType().equals("D") && o instanceof String) {
						colValue = "'" + o + "'";
					} else if(colMetaData.getColType().equals("T") && o instanceof String) {
						
					} else if (colMetaData.getColType().equals("C") && o instanceof java.util.Date) {
						java.text.SimpleDateFormat fpsdf = new java.text.SimpleDateFormat("yyyyMMdd");
						colValue = "'" + fpsdf.format(o) + "'";
					} else if (o instanceof String) {
						// 转义字符
						String s = (String) o;
						if (s.indexOf("'") >= 0) o = StringFunction.replaceString(s, "'", "''");
						colValue = "'" + o + "'";
					} else if (o instanceof java.util.Date) {
						java.util.Date dd = (java.util.Date) o;
						colValue = "'" + sdf.format(dd) + "'";
					} else if (o instanceof Integer) {
						colValue = ((Integer) o).intValue() + "";					
					} else{
						colValue = "" + o;
					}
					//如果是主键，则只能做为where条件
					if(colMetaData.isKey()) {
						if(!whereSql.trim().equals("")) {
							whereSql += " and ";
						}
						whereSql += colID + " = " + colValue;
					}
					//否则作为更新一部分
					else {
						if(!valuesql.trim().equals("")) {
							valuesql += " , ";
						}
						valuesql += colID + " = " + colValue;
					}
				}
			}
		}
		stmt.executeUpdate(updateSql + valuesql + " where " + whereSql);
	}
	
	public static void deleteRowSetData(JStatement stmt, String tableName, String keyVal, EFDataSet bizMetaData) throws Exception {
		String           key = "";
		String         isKey = "";
		EFRowSet      colers = null;
		String     deleteSql = " delete from " + tableName + " where ";

		for (int i = 0; i < bizMetaData.getRowCount(); i++) {
			colers = bizMetaData.getRowSet(i);
			key = colers.getString("COL_ID", "");
			isKey = colers.getString("COL_ISKEY", "0");
			
			if(!isKey.equals("1")) continue;
			
			deleteSql += key + " = '" + keyVal + "'";
			break;
		}
		stmt.executeUpdate(deleteSql);
	}
	
	public static int deleteRowSetData(JStatement stmt, String tableName, EFRowSet rowSet, List colList) throws Exception {
		String              deleteSql = " delete from " + tableName + " where ";
		ColumnMetaData    colMetaData = null;
		Object                      o = null;
		
		for (int i = 0; i < colList.size(); i++) {
			colMetaData = (ColumnMetaData) colList.get(i);

			if(!colMetaData.isKey()) continue;
			
			o = FormBillFieldUtil.getValue(colMetaData.getColid(), rowSet, null);
			
			deleteSql += colMetaData.getColid() + " = '" + o.toString() + "' and ";
//			break;
		}
		if(deleteSql.trim().endsWith("and")) {
			deleteSql += " 1 = 1";
		}
		return stmt.executeUpdate(deleteSql);
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

	public static int getNumberDecn(ColumnMetaData colMeataData) {

		String colType = colMeataData.getColType();
		if ("N".equals(colType)) {
			if(colMeataData.getColEditType() == 5) return 0;
			
			int decn = 2;
			Object oo = colMeataData.getColScale();
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
	
	public static void formDataDeleter(JStatement stmt, EFFormDataModel model, JParamObject PO) throws Exception {
		String                            where = "";
		String                              sql = "";
		java.util.List<String>   dataSetKeyList = new java.util.ArrayList<String>();
		EFDataSet                           eds = model.getBillDataSet();
		String                            table = eds.getTableName();
		String                        tableName = "";
		int                           itemcount = eds.getRowCount();
		
		for (int i = 0; i < itemcount; i++) {
			EFRowSet ers = eds.getRowSet(i);
			where = getWhere(PO, ers);
			sql = "delete from " + table + " where " + where;
			stmt.execute(sql);
			dataSetKeyList = ers.getDataSetKeyList();
			for (int j = 0; dataSetKeyList != null && j < dataSetKeyList.size(); j++) {
				tableName = dataSetKeyList.get(j).toString();
				if(tableName.lastIndexOf("_ITEM") > 0) {
					tableName = tableName.substring(0, tableName.indexOf("_ITEM"));
				}
				eds = ers.getDataSet(dataSetKeyList.get(j));
				if(eds != null && eds.getRowCount() > 0) {
					for(int k = 0; eds != null && k < eds.getRowCount(); k++) {
						sql = "delete from " + tableName + " where " 
					    	+ PO.GetValueByParamName(SYS_MODEL._BLFL_KJQJ_COL_, SYS_MDL_VAL.BILL_KJQJ) + " = '" + ers.getString(PO.GetValueByParamName(SYS_MODEL._BLFL_KJQJ_COL_, SYS_MDL_VAL.BILL_KJQJ), null)
					    	+ "' and F_GUID = '" + ers.getString("F_GUID", null) + "' ";
						if(model.getFormSaveType() != EFFormDataModel._FORM_SAVE_ITEMS_ALL_ && eds.getRowCount() > 0) {
							if(eds.getRowSet(j).getString("F_FLGUID", null) != null) {
								sql += " and F_FLGUID = '" + eds.getRowSet(k).getString("F_FLGUID", null) + "'";
							}
							if(model.getFormSaveType() == EFFormDataModel._FORM_SAVE_ITEM_) {
								sql += " and F_FLBH = '" + eds.getRowSet(k).getString(PO.GetValueByParamName(SYS_MODEL._BILL_FLBH_COL_, SYS_MDL_VAL.BLFL_BH), "") + "' ";
							}
						}
						stmt.execute(sql);
					}		
				} else {
					sql = "delete from " + tableName + " where " 
			    		+ PO.GetValueByParamName(SYS_MODEL._BLFL_KJQJ_COL_, SYS_MDL_VAL.BILL_KJQJ) + " = '" + ers.getString(PO.GetValueByParamName(SYS_MODEL._BLFL_KJQJ_COL_, SYS_MDL_VAL.BILL_KJQJ), null)
			    		+ "' and F_GUID = '" + ers.getString("F_GUID", null) + "' ";
					stmt.execute(sql);
				}
						
			}
		}
	}
	
	public static String getWhere(JParamObject PO, EFRowSet rowSet) throws Exception {
		String where = PO.GetValueByParamName(SYS_MODEL._BLFL_KJQJ_COL_, SYS_MDL_VAL.BILL_KJQJ) 
		             + " = '" + rowSet.getString(PO.GetValueByParamName(SYS_MODEL._BLFL_KJQJ_COL_, SYS_MDL_VAL.BILL_KJQJ) , "") + "' and " 
		             + PO.GetValueByParamName(SYS_MODEL._BILL_GUID_COL_, SYS_MDL_VAL.BILL_GUID)  + " = '" + rowSet.getString(PO.GetValueByParamName(SYS_MODEL._BILL_GUID_COL_, SYS_MDL_VAL.BILL_GUID), "") + "'";
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
