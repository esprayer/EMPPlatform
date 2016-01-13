package com.efounder.eai.service.dal;

import com.borland.dx.sql.dataset.Database;
import com.efounder.dbc.ClientDataSet;
import com.efounder.dbc.ClientDataSetData;
import com.efounder.dbc.QueryDescriptor;
import com.efounder.dctbuilder.data.ColumnMetaData;
import com.efounder.eai.framework.JActiveObject;
import com.efounder.service.meta.db.TableMetadata;
import com.efounder.sql.JConnection;
import com.efounder.sql.JStatement;
import com.efounder.eai.EAI;
import com.efounder.eai.data.JResponseObject;

import com.efounder.eai.data.JParamObject;
import com.efounder.form.server.resolver.util.FormBillFieldUtil;
import com.efounder.builder.base.data.EFDataSet;
import com.efounder.builder.base.data.DataSetUtils;
import com.efounder.builder.base.data.EFRowSet;
import com.efounder.bz.dbform.datamodel.column.Column;
import com.pub.util.StringFunction;

import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2011</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class JDALServiceObject extends JActiveObject {
	
	public JDALServiceObject() {
	}	
	
	/**
	 * 查询数据
	 * @param Param
	 * @param Data
	 * @param CustomObject
	 * @param AdditiveObject
	 * @return
	 */
    public JResponseObject queryDataSet(Object Param, Object Data, Object CustomObject, Object AdditiveObject) throws Exception {
    	QueryDescriptor    queryDescriptor = null;
    	JParamObject           paramObject = null;
		String                       error = "";
		JConnection                   conn = null;
		JStatement                    stmt = null;
		ResultSet                      rs = null;
		JResponseObject                 RO = new JResponseObject();
		String                 strQuerySql = "";
		EFDataSet                  dataset = null;
		
		try {
			if (Param == null) {
				paramObject = JParamObject.Create();
			} else {
				paramObject = (JParamObject) Param;
			}
			queryDescriptor = (QueryDescriptor) Data;
			strQuerySql = queryDescriptor.getQueryString();
			
		    try {
		    	conn = this.getConnection(paramObject);
		    	stmt = conn.createStatement();
		    	rs = stmt.executeQuery(strQuerySql);
		    	dataset = EFDataSet.getInstance("");
		    	dataset = DataSetUtils.resultSet2DataSet(rs, dataset);
		    	
		    	for(int i = 0; i < dataset.getRowCount(); i++) {
		    		dataset.getRowSet(i).setDataStatus(EFRowSet._DATA_STATUS_NORMAL_);
		    	}
		    	RO.setAddinObject(dataset);
		        RO.setErrorCode(1);
		    } catch(Exception ce) {
		    	if(ce.getMessage() != null) {
					error += ce.getMessage();
				}
				RO = new JResponseObject(error, -1);
		    	ce.printStackTrace();
		    }
		} catch (Exception ce) {
			if(ce.getMessage() != null) {
				error += ce.getMessage();
			}
			RO = new JResponseObject(error, -1);
			ce.printStackTrace();
		}
		return RO;
	}
    
    /**
	 * 连接测试
	 * @param Param
	 * @param Data
	 * @param CustomObject
	 * @param AdditiveObject
	 * @return
	 */
    public JResponseObject saveDataSet(Object Param, Object Data, Object CustomObject, Object AdditiveObject) throws Exception {
    	JParamObject             paramObject = null;
		String                         error = "";
		Statement                       stmt = null;
		JResponseObject                   RO = new JResponseObject();
		ClientDataSetData               Adsd = null;
		ClientDataSet                dataset = null;
		Database                    database;
		EFRowSet                      rowset = null;
		TableMetadata          tableMetadata = null;
		List                         colList = null;
		
		try {
			if (Param == null) {
				paramObject = JParamObject.Create();
			} else {
				paramObject = (JParamObject) Param;
			}
			database = (Database) Data;
			Adsd = (ClientDataSetData) CustomObject;
			dataset = Adsd.getDataSetData();
			
		    try {		    	
		    	stmt = database.createStatement();
		    	tableMetadata = ((com.efounder.dctbuilder.data.DctContext)dataset.getUserObject()).getMetaData().getTableMetadata();
		    	colList = tableMetadata.getColList();
		    	
		    	deleteRowSetData(stmt, Adsd, colList);
		    	
		    	for(int i = 0; i < dataset.getRowCount(); i++) {
		    		rowset = dataset.getRowSet(i);
		    		if(rowset.getDataStatus() == EFRowSet._DATA_STATUS_UPDATE_) {
		    			updateRowSetData(stmt, dataset, colList, rowset);
		    		} else if(rowset.getDataStatus() == EFRowSet._DATA_STATUS_INSERT_) {
		    			insertRowSetData(stmt, dataset, colList, rowset);
		    		}
		    	}
		    	RO.setAddinObject(dataset);
		        RO.setErrorCode(1);
		    } catch(Exception ce) {
		    	if(ce.getMessage() != null) {
					error += ce.getMessage();
				}
				RO = new JResponseObject(error, -1);
		    	ce.printStackTrace();
		    }
		} catch (Exception ce) {
			if(ce.getMessage() != null) {
				error += ce.getMessage();
			}
			RO = new JResponseObject(error, -1);
			ce.printStackTrace();
		}
		return RO;
	}
    
    
    public static void deleteRowSetData(Statement stmt, ClientDataSetData Adsd, List colList) throws Exception {
		String                     deleteSql = "";
		String                      columnId = "";
		EFDataSet              deleteDataset = Adsd.getDataSetData().getDeleteDataSet();
		ColumnMetaData   tableColumnMetaData = null;
		
		if(deleteDataset != null && deleteDataset.getRowCount() > 0) {			
    		for(int i = 0; i < deleteDataset.getRowCount(); i++) {
    			deleteSql = " delete from " + Adsd.getTableName() + " where ";
    			for(int j  = 0; j < colList.size(); j++) {
    				tableColumnMetaData = (ColumnMetaData) colList.get(j);
    				columnId = tableColumnMetaData.getColid();
    				
    				if(tableColumnMetaData.isKey()) {
    					deleteSql += columnId + " = '" + deleteDataset.getRowSet(i).getString(columnId, "") + "'";
    				}
    			}
    			System.out.println(deleteSql);
    			stmt.execute(deleteSql);
    		}
    	}
	}
    public static void insertRowSetData(Statement stmt, ClientDataSet dataset, List colList, EFRowSet rowset) throws Exception {
		SimpleDateFormat                 sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); // yyyyMMdd
		String                           key = "";
		String                     insertsql = "insert into " + dataset.getTableName() + "(";
		String                      valuesql = "values(";
		ColumnMetaData   tableColumnMetaData = null;

		for (int i = 0; i < colList.size(); i++) {
			tableColumnMetaData = (ColumnMetaData) colList.get(i);
			key = tableColumnMetaData.getColid();
			Object o = FormBillFieldUtil.getValue(tableColumnMetaData.getColid(), rowset, null);

			if (o instanceof String && ((String) o).trim().length() == 0) continue;
			if (o != null) {
				if (o != null) {
					insertsql += key + ",";
					if (tableColumnMetaData.getColType().equals("N")) {
						int decn = getNumberDecn(tableColumnMetaData);
						if (o instanceof Double && ((Double) o).isNaN())
							o = "0";
						if (o instanceof Double && ((Double) o).isInfinite())
							o = "0";
						if (o instanceof Number && tableColumnMetaData.getColEditType() == 5) {
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
					if (tableColumnMetaData.getColType().equals("D") && o instanceof String) {
						valuesql += "'" + o + "',";
					} else if(tableColumnMetaData.getColType().equals("T") && o instanceof String) {
						valuesql += "to_timestamp('" + o + "','yyyymmddhh24missff6'),";
					} else if (tableColumnMetaData.getColType().equals("C") && o instanceof java.util.Date) {
						java.text.SimpleDateFormat fpsdf = new java.text.SimpleDateFormat("yyyyMMdd");
						valuesql += "'" + fpsdf.format(o) + "',";
					} else if (tableColumnMetaData.getColType().equals("C") && o instanceof Long) {
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
		System.out.println(insertsql + valuesql);
		stmt.executeUpdate(insertsql + valuesql);
	}

	
	public static void updateRowSetData(Statement stmt, ClientDataSet dataset, List colList, EFRowSet rowset) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); // yyyyMMdd
		String                           key = "";
		String                      whereSql = "";
		String                     updateSql = " update " + dataset.getTableName() + " set ";
		String                      colValue = "";
		String                      valuesql = " ";
		ColumnMetaData   tableColumnMetaData = null;
		
		for (int i = 0; i < colList.size(); i++) {
			tableColumnMetaData = (ColumnMetaData) colList.get(i);
			key = tableColumnMetaData.getColid();
			Object o = FormBillFieldUtil.getValue(tableColumnMetaData.getColid(), rowset, null);

			if (o instanceof String && ((String) o).trim().length() == 0) continue;
			if (o != null) {
				if (o != null) {
					if (tableColumnMetaData.getColType().equals("N")) {
						int decn = getNumberDecn(tableColumnMetaData);
						if (o instanceof Double && ((Double) o).isNaN())
							o = "0";
						if (o instanceof Double && ((Double) o).isInfinite())
							o = "0";
						if (o instanceof Number && tableColumnMetaData.getColEditType() == 5) {
							colValue = "" + o;
							//如果是主键，则只能做为where条件
							if(tableColumnMetaData.isKey()) {
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
							if(tableColumnMetaData.isKey()) {
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
							if(tableColumnMetaData.isKey()) {
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
					if (tableColumnMetaData.getColType().equals("D") && o instanceof String) {
						colValue = "'" + o + "'";
					} else if(tableColumnMetaData.getColType().equals("T") && o instanceof String) {
						
					} else if (tableColumnMetaData.getColType().equals("C") && o instanceof java.util.Date) {
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
					if(tableColumnMetaData.isKey()) {
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
		System.out.println(updateSql + valuesql + " where " + whereSql);
		stmt.executeUpdate(updateSql + valuesql + " where " + whereSql);
	}
	
	/**
	 * 获取精度
	 * @param tableColumnMetaData
	 * @return
	 */
	public static int getNumberDecn(ColumnMetaData tableColumnMetaData) {

		String colType = tableColumnMetaData.getColType();
		if ("N".equals(colType)) {
			if(tableColumnMetaData.getColEditType() == 5) return 0;
			
			int decn = 2;
			Object oo = tableColumnMetaData.getColScale();
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
    /**
     * 根据PO获取数据库连接
     *
     * @param PO JParamObject
     * @return   JConnection
     * @throws    Exception
     */
    public static JConnection getConnection(JParamObject PO) throws Exception {

        JConnection con = (JConnection) EAI.DAL.IOM("DBManagerObject","GetDBConnection",PO,null);
        return con;
    }
}