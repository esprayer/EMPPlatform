package com.mrp.biz.server;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.jdbc.support.JdbcUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import esyt.framework.com.builder.base.data.EFDataSet;
import esyt.framework.com.core.foundation.sql.JConnection;
import esyt.framework.com.core.foundation.sql.JStatement;
import esyt.framework.com.dbform.bizmodel.EFFormDataModel;

@Transactional(rollbackFor = Exception.class)
@Repository
public class EMPCheckFormUtil {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public boolean checkForm(EFFormDataModel model) throws Exception {
		JConnection         conn = null;
		JStatement          stmt = null;
		ResultSet             rs = null;
		boolean           bError = false;
		try {
			conn = JConnection.getInstance(jdbcTemplate.getDataSource().getConnection());
			stmt = conn.createStatement();
			bError = checkForm(stmt, model);
			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				JdbcUtils.closeResultSet(rs);
				JdbcUtils.closeStatement(stmt);
				stmt = null;
				DataSourceUtils.releaseConnection(conn, jdbcTemplate.getDataSource());
			} catch(Exception ce) {
				ce.printStackTrace();
			}
		}
		return bError;
	}
	
	public static boolean checkForm(JStatement stmt, EFFormDataModel model) throws Exception {
		EFDataSet    billDataSet = model.getBillDataSet();
		int             rowCount = billDataSet.getRowCount();
		String            F_KJQJ = billDataSet.getRowSet(0).getString("F_KJQJ", "");
		String            F_GUID = billDataSet.getRowSet(0).getString("F_GUID", "");
		String          F_CHDATE = billDataSet.getRowSet(0).getString("F_CHDATE", "");
		
		if(F_CHDATE.trim().length() == 0) {
			F_CHDATE = String.valueOf(billDataSet.getRowSet(0).getNumber("F_CHDATE", 0));
		}

		boolean bExists = checkFormExiss(stmt, F_KJQJ, F_GUID, billDataSet.getTableName());
		int formEditStatus = model.getFormEditStatus();
		
		//如果之前FormEditStatus已经设置了，就不需要再重新设置了
		if(formEditStatus != EFFormDataModel._FORM_EDIT_STATUS_) {
			bExists = true;			
		} else {
			if(!bExists) model.setFormEditStatus(EFFormDataModel._FORM_EDIT_CREATE_);
			else model.setFormEditStatus(EFFormDataModel._FORM_EDIT_CHANGE_);
		}
		
		
		if(bExists && rowCount > 0) return true;
		else return false;
	}
	
	public boolean checkFormExiss(String F_KJQJ, String F_GUID, String tableName) throws Exception {
		JConnection    conn = null;
		JStatement     stmt = null;
		ResultSet        rs = null;
		boolean      bError = false;
		try {
			conn = JConnection.getInstance(jdbcTemplate.getDataSource().getConnection());
			stmt = conn.createStatement();
			bError = checkFormExiss(stmt, F_KJQJ, F_GUID, tableName);
			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				JdbcUtils.closeResultSet(rs);
				JdbcUtils.closeStatement(stmt);
				stmt = null;
				DataSourceUtils.releaseConnection(conn, jdbcTemplate.getDataSource());
			} catch(Exception ce) {
				ce.printStackTrace();
			}
		}
		return bError;
	}
	
	//--------------------------------------------------------------------------------------------------
	//描述:获取单据是否提交
	//设计: ES
	//实现: ES
	//修改:
	//--------------------------------------------------------------------------------------------------
	public static boolean checkFormExiss(JStatement stmt, String F_KJQJ, String F_GUID, String tableName) {
		ResultSet    rs;
		String   strSql = getCheckSql(F_KJQJ, F_GUID, tableName);
		try {
			rs = stmt.executeQuery(strSql);
			if(rs != null && rs.next()) return true;
			else return false;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public String checkFormSubmit(String F_KJQJ, String F_GUID, String tableName, String F_CHDATE) throws Exception {
		JConnection         conn = null;
		JStatement          stmt = null;
		String          errorMsg = "";
		try {
			conn = JConnection.getInstance(jdbcTemplate.getDataSource().getConnection());
			stmt = conn.createStatement();
			errorMsg = checkFormSubmit(stmt, F_KJQJ, F_GUID, tableName, F_CHDATE);
			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				JdbcUtils.closeStatement(stmt);
				stmt = null;
				DataSourceUtils.releaseConnection(conn, jdbcTemplate.getDataSource());
			} catch(Exception ce) {
				ce.printStackTrace();
			}
		}
		return errorMsg;
	}
	
	//--------------------------------------------------------------------------------------------------
	//描述:获取单据是否提交
	//设计: ES
	//实现: ES
	//修改:
	//--------------------------------------------------------------------------------------------------
	public static String checkFormSubmit(JStatement stmt, String F_KJQJ, String F_GUID, String tableName, String F_CHDATE) {
		SimpleDateFormat  sdf = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒"); //yyyyMMdd
		ResultSet          rs;
		String         strSql = getCheckSql(F_KJQJ, F_GUID, tableName);
		String         F_DJZT = "";
		Date             date;
		String       errorMsg = "";
		try {
			rs = stmt.executeQuery(strSql);
			if(rs != null && rs.next()) {
				F_DJZT = rs.getString("F_DJZT");
				if(F_DJZT.equals("1")) {
					date = new Date(rs.getLong("F_SBDATE"));
					errorMsg = "单据编号【" + rs.getString("F_DJBH") + "】已被用户"
					         + "在" + sdf.format(date) + "提交，不允许修改保存！";
					return errorMsg;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();		
			return e.getMessage();
		}
		return "";
	}
	
	
	public String checkFormEdit(String F_KJQJ, String F_GUID, String tableName, String F_CHDATE) throws Exception {
		JConnection         conn = null;
		JStatement          stmt = null;
		String          errorMsg = "";
		try {
			conn = JConnection.getInstance(jdbcTemplate.getDataSource().getConnection());
			stmt = conn.createStatement();
			errorMsg = checkFormEdit(stmt, F_KJQJ, F_GUID, tableName, F_CHDATE);
			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				JdbcUtils.closeStatement(stmt);
				stmt = null;
				DataSourceUtils.releaseConnection(conn, jdbcTemplate.getDataSource());
			} catch(Exception ce) {
				ce.printStackTrace();
			}
		}
		return errorMsg;
	}
	
	//--------------------------------------------------------------------------------------------------
	//描述:判断单据是否在保存前已被修改
	//设计: ES
	//实现: ES
	//修改:
	//--------------------------------------------------------------------------------------------------
	public static String checkFormEdit(JStatement stmt, String F_KJQJ, String F_GUID, String tableName, String F_CHDATE) {
		SimpleDateFormat   sdf = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒"); //yyyyMMdd
		ResultSet           rs;
		String          strSql = getCheckSql(F_KJQJ, F_GUID, tableName);
		long        COL_CHDATE;
		long        OBJ_CHDATE;
		Date              date;
		String        errorMsg = "";
		BigDecimal           b = null;
		try {
			rs = stmt.executeQuery(strSql);
			if(rs != null && rs.next()) {
				COL_CHDATE = rs.getLong("F_CHDATE");
				b = new BigDecimal(F_CHDATE);
				OBJ_CHDATE = b.longValue();
				if(COL_CHDATE > OBJ_CHDATE) {
					date = new Date(rs.getLong("F_CHDATE"));
					errorMsg = "单据编号【" + rs.getString("F_DJBH") + "】已被用户"
					         + "在" + sdf.format(date) + "修改，请重新打开单据！<br>";
					return errorMsg;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();	
			return e.getMessage();
		}
		return "";
	}
	
	public static String getCheckSql(String F_KJQJ, String F_GUID, String tableName) {
		String strSql = "select * from " + tableName + " where F_KJQJ = '" + F_KJQJ + "' and F_GUID = '" + F_GUID + "'";
		return strSql;
	}
}
