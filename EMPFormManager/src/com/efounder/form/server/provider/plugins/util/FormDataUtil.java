package com.efounder.form.server.provider.plugins.util;

import com.common.util.EMPSQLUtil;
import com.efounder.builder.base.data.*;
import com.efounder.eai.data.JParamObject;
import com.efounder.form.EFFormDataModel;
import com.efounder.form.server.resolver.util.FormBillFieldUtil;

import java.sql.*;

import com.efounder.sql.JStatement;
import com.metadata.bizmodel.SYS_MDL_CTN;
import com.metadata.bizmodel.SYS_MODEL;

public class FormDataUtil {

	protected FormDataUtil() {
	}
	
	public static EFDataSet getFormHeadDataSet(JStatement stmt, JParamObject PO, EFFormDataModel formModel, EFDataSet ctnHead, String BIZ_CTN_TYPE) {
		EFRowSet rowset = ctnHead.getRowSet(0);
		EFDataSet billDataSet = getFormDataSet(stmt, PO, rowset, BIZ_CTN_TYPE);
		return billDataSet;
	}
	
	public static EFDataSet[] getFormItemDataSets(JStatement stmt, JParamObject PO, EFFormDataModel formModel, EFDataSet ctnItem, String BIZ_CTN_TYPE) {
		EFDataSet[] dataSets = new EFDataSet[ctnItem.getRowCount()];
		EFRowSet      rowset = null;
	    for(int i = 0; i < ctnItem.getRowCount(); i++) {
	    	rowset = ctnItem.getRowSet(i);
	    	dataSets[i] = getFormDataSet(stmt, PO, rowset, BIZ_CTN_TYPE);
	    }
	    return dataSets;
	}

	public static EFDataSet getFormDataSet(JStatement stmt, JParamObject PO, EFRowSet rowset, String BIZ_CTN_TYPE) {
		ResultSet          rs = null;
		String         strSql = " select ";
		String    strLeftJoin = "";
		String        CTN_FCT = rowset.getString("CTN_FCT1", "");
		String     BILL_WHERE = PO.GetValueByParamName("BILL_WHERE", "").replaceAll("####", CTN_FCT);
		String     BILL_ORDER = PO.GetValueByParamName("BILL_ORDER", "").replaceAll("####", CTN_FCT);
		String     ext_column = PO.GetValueByParamName(BIZ_CTN_TYPE + "_" + CTN_FCT + "_EXT_COLUMN", "");
		EFDataSet dictDataSet = EFDataSet.getInstance(CTN_FCT);
		EFDataSet   objColsDS = getSYS_OBJCOLS(stmt, CTN_FCT);
		EFRowSet     objColRS = null;
		EFRowSet      fkeyObj = null;
		EFDataSet    mdlValDS = getSYS_MDL_VAL(stmt, PO.GetValueByParamName(SYS_MODEL.MODEL_ID, ""));
		String          order  = getOrder(CTN_FCT, rowset.getString("CTN_TYPE", ""), mdlValDS);
		
		try {			
			for(int i = 0; i < objColsDS.getRowCount(); i++) {
				objColRS = objColsDS.getRowSet(i);
				if(objColRS.getString("COL_ISFKEY", "1").equals("1")) {
					fkeyObj = getDictRow(stmt, objColRS.getString("COL_FOBJ", ""));
					if(fkeyObj == null) continue;
					strLeftJoin += " LEFT JOIN " + objColRS.getString("COL_FOBJ", "") + " " + objColRS.getString("COL_ID", "") + " ON " + objColRS.getString("COL_ID", "") 
					             + "." + fkeyObj.getString("DCT_BMCOLID", "")  + " = " + CTN_FCT + "." + objColRS.getString("COL_ID", "");
					strSql += CTN_FCT + "." + objColRS.getString("COL_ID", "") + " as " + objColRS.getString("COL_ID", "") + ","
					        + objColRS.getString("COL_ID", "") + "." + fkeyObj.getString("DCT_MCCOLID", "") 
					        + " as " + objColRS.getString("COL_ID", "") + "MC,";
				} else {
					strSql += CTN_FCT + "." + objColRS.getString("COL_ID", "") + " as " + objColRS.getString("COL_ID", "") + ",";
				}
			}
			strSql = strSql.substring(0, strSql.lastIndexOf(","));
			
			if(!ext_column.equals("")) {
				strSql += "," + ext_column;
			}
			strSql += " from " + CTN_FCT + " " + strLeftJoin + " where " + BILL_WHERE.replaceAll("####", CTN_FCT) + " order by " + order + BILL_ORDER;
			rs = stmt.executeQuery(strSql);
			dictDataSet = DataSetUtils.resultSet2DataSet(rs, dictDataSet);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				EMPSQLUtil.closeAllResources(rs, null, null);
			} catch(Exception ce) {
				ce.printStackTrace();
			}
		}
		return dictDataSet;
	}
  
  
	/**
	 * 获取字典元数据
	 * @param conn
	 * @param tableName
	 * @return
	 */
	public static EFRowSet getDictRow(JStatement stmt, String tableName) {
		ResultSet        rs = null;
		EFDataSet    dictDS = EFDataSet.getInstance("SYS_DICTS");
		EFRowSet     dictRS = null;
		String       strSql = " select * from SYS_DICTS where DCT_ID = '" + tableName + "'";
		
		try {
			rs = stmt.executeQuery(strSql);
			dictDS = DataSetUtils.resultSet2DataSet(rs, dictDS);
			if(dictDS != null && dictDS.getRowCount() > 0) {
				dictRS = dictDS.getRowSet(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dictRS;
	}
	
	/**
	 * 获取字典扩展属性
	 * @param conn
	 * @param tableName
	 * @return
	 */
	public static EFRowSet getExtendProperties(JStatement stmt, String tableName) {
		ResultSet        rs = null;
		EFDataSet dictCstDS = EFDataSet.getInstance("SYS_DCT_CST");
		EFRowSet  dictCstRS = null;
		EFRowSet     rowset = EFRowSet.getInstance();
		String       strSql = " select * from SYS_DCT_CST where DCT_ID = '" + tableName + "'";
		
		try {
			rs = stmt.executeQuery(strSql);
			dictCstDS = DataSetUtils.resultSet2DataSet(rs, dictCstDS);
			for(int i = 0; i < dictCstDS.getRowCount(); i++) {
				dictCstRS = dictCstDS.getRowSet(i);
				rowset.putString(dictCstRS.getString("DCT_KEY", ""), dictCstRS.getString("DCT_VAL", ""));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rowset;
	}
	
	/**
	 * 获取字典元数据
	 * @param conn
	 * @param tableName
	 * @return
	 */
	public static EFRowSet getSYS_OBJECTS(JStatement stmt, String tableName) {
		ResultSet        rs = null;
		EFDataSet    dictDS = EFDataSet.getInstance("SYS_DCT_CST");
		EFRowSet     dictRS = null;
		String       strSql = " select * from SYS_DCT_CST where DCT_ID = '" + tableName + "'";
		
		try {
			rs = stmt.executeQuery(strSql);
			dictDS = DataSetUtils.resultSet2DataSet(rs, dictDS);
			if(dictDS != null && dictDS.getRowCount() > 0) {
				dictRS = dictDS.getRowSet(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dictRS;
	}
	
	/**
	 * 获取字典列元数据
	 * @param conn
	 * @param tableName
	 * @return
	 */
	public static EFDataSet getSYS_OBJCOLS(JStatement stmt, String tableName) {
		ResultSet        rs = null;
		EFDataSet    dictDS = EFDataSet.getInstance("SYS_OBJCOLS");
		String       strSql = " select * from SYS_OBJCOLS where OBJ_ID = '" + tableName + "' and F_STAU = '1'";
		
		try {
			rs = stmt.executeQuery(strSql);
			dictDS = DataSetUtils.resultSet2DataSet(rs, dictDS);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dictDS;
	}
	
	/**
	 * 获取模型元数据
	 * @param conn
	 * @param tableName
	 * @return
	 */
	public static EFDataSet getSYS_MODEL(JStatement stmt, String MDL_ID) {
		ResultSet        rs = null;
		EFDataSet     mdlDS = EFDataSet.getInstance("SYS_MODEL");
		EFRowSet      mdlRS = EFRowSet.getInstance();
		EFDataSet  mdlCtnDS = EFDataSet.getInstance("SYS_MDL_CTN");
		String       strSql = " select * from SYS_MODEL where MDL_ID = '" + MDL_ID + "'";
		
		try {
			rs = stmt.executeQuery(strSql);
			mdlDS = DataSetUtils.resultSet2DataSet(rs, mdlDS);
			if(mdlDS.getRowCount() > 0) {
				mdlRS = mdlDS.getRowSet(0);
				strSql = "select * from SYS_MDL_CTN where MDL_ID = '" + MDL_ID + "' order by CTN_TYPE";
				rs = stmt.executeQuery(strSql);
				mdlCtnDS = DataSetUtils.resultSet2DataSet(rs, mdlCtnDS);
				mdlRS.setDataSet("SYS_MDL_CTN", mdlCtnDS);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mdlDS;
	}
	
	/**
	 * 获取模型元数据
	 * @param conn
	 * @param tableName
	 * @return
	 */
	public static EFDataSet getSYS_MDL_CTN(JStatement stmt, String MDL_ID, String CTN_TYPE) {
		ResultSet        rs = null;
		EFDataSet  mdlCtnDS = EFDataSet.getInstance("SYS_MDL_CTN");
		String       strSql = " select * from SYS_MDL_CTN where MDL_ID = '" + MDL_ID + "'";
		
		if(CTN_TYPE != null && CTN_TYPE.trim().length() > 0) {
			strSql += " and CTN_TYPE = '" + CTN_TYPE + "'";
		}
		try {
			rs = stmt.executeQuery(strSql);
			mdlCtnDS = DataSetUtils.resultSet2DataSet(rs, mdlCtnDS);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mdlCtnDS;
	}
	
	/**
	 * 获取模型元数据
	 * @param conn
	 * @param tableName
	 * @return
	 */
	public static EFDataSet getSYS_MDL_VAL(JStatement stmt, String MDL_ID) {
		ResultSet        rs = null;
		EFDataSet  mdlValDS = EFDataSet.getInstance("SYS_MDL_VAL");
		String       strSql = " select * from SYS_MDL_VAL where MDL_ID = '" + MDL_ID + "'";

		try {
			rs = stmt.executeQuery(strSql);
			mdlValDS.setPrimeKey(new String[]{"MDL_KEY"});
			mdlValDS = DataSetUtils.resultSet2DataSet(rs, mdlValDS);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mdlValDS;
	}
	
	/**
	   *
	   * @param dataType int
	   * @param fctMetaData FCTMetaData
	   * @param formContext FormContext
	   * @param bizMetaData BIZMetaData
	   * @return String
	   * @throws Exception
	   */
	  protected static String getOrder(String table, String dataType, EFDataSet mdlValDS) {
		  
	    String col=FormBillFieldUtil.getBILLBHCol(mdlValDS);
	    String flbh=FormBillFieldUtil.getFLBHBHCol(mdlValDS);
	    if (dataType.equals(SYS_MDL_CTN._BIZ_CTN_TYPE_JBDS_)) {
	      return table + "." + col;
	    }
	    if (dataType.equals(SYS_MDL_CTN._BIZ_CTN_TYPE_JIDS_)) {
	      return col + "," + table + "." + flbh;
	    }
//	    if (dataType.equals(SYS_MDL_CTN._BIZ_CTN_TYPE_JPDS_)) {
//	      return col + "," + flbh + "," + mxbh;
//	    }
	    return "";
	  }
}
