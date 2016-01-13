package com.mrp.repository.dailyBusiness.storage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.jdbc.support.JdbcUtils;
import org.springframework.stereotype.Repository;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import esyt.framework.com.builder.base.data.DataSetUtils;
import esyt.framework.com.builder.base.data.EFDataSet;
import esyt.framework.com.builder.base.data.EFRowSet;
import esyt.framework.com.builder.base.data.ESPRowSet;
import esyt.framework.com.core.foundation.sql.JConnection;
import esyt.framework.com.core.foundation.sql.JStatement;

@Repository
public class EMPStorageQueryHelp{
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	//--------------------------------------------------------------------------------------------------
	//描述:项目帮助
	//设计: ES
	//实现: ES
	//修改:
	//--------------------------------------------------------------------------------------------------
	public EFDataSet projectHelp(String beginDate, String endDate, String F_XMBH, String F_CLBH, int startIndex, int count) {
		JConnection    conn = null;
		JStatement     stmt = null;
		ResultSet        rs = null;
		EFDataSet   dataSet = EFDataSet.getInstance("HYXMZD");
		String       strSql = " select F_XMBH, F_XMMC, F_SQSJ, F_SQDW, F_SQR, F_SQRMC, F_GYZX, F_DWLD, F_FGLD, "
			                + " F_ZGLD, F_GYZXMC, F_DWLDMC, F_FGLDMC, F_ZGLDMC, F_CLXQSJ, F_WGSJ, F_WCRBH, F_WCRMC, F_XMZT, F_CRDATE, F_CHDATE"
			                + " from HYXMZD"
			                + " where HYXMZD.F_XMZT = '0' and (HYXMZD.F_SQSJ >= '" + beginDate + "' AND HYXMZD.F_SQSJ <= '" + endDate + "')"
			                + " and HYXMZD.F_XMBH like '%" + F_XMBH + "%' and exists(select 1 from HYXMMX, HYCLZD where HYXMZD.F_XMBH = HYXMMX.F_XMBH"
			                + " and HYXMMX.F_CLBH = '" + F_CLBH + "' and HYXMMX.F_CLBH = HYCLZD.F_CLBH and HYCLZD.F_SYZT = '1')"
			                + " order by HYXMZD.F_XMBH";
		try {
			conn = JConnection.getInstance(jdbcTemplate.getDataSource().getConnection());
			stmt = conn.createStatement();
			rs = stmt.executeQuery(strSql);
			dataSet = DataSetUtils.resultSet2DataSet(rs, dataSet);
			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
			dataSet = EFDataSet.getInstance();
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
		return dataSet;
	}
}
