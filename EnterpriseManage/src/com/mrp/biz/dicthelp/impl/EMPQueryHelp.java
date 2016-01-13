package com.mrp.biz.dicthelp.impl;

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
public class EMPQueryHelp{
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public EFDataSet xmclHelp(String F_XMBH, String F_CPBH, String F_CLBH, String beginDate, String endDate, int startIndex, int coun) {
		JConnection    conn = null;
		JStatement     stmt = null;
		ResultSet        rs = null;
		EFDataSet   dataSet = EFDataSet.getInstance("HYXMZD");
		String       strSql = "select HYXMZD.F_XMBH,HYXMZD.F_XMMC,HYCPZD.F_CPBH,HYCPZD.F_CPMC"
			                + " from HYXMZD, HYCPZD, HYXMCP"
			                + " where HYXMZD.F_XMZT = '0' and (HYXMZD.F_SQSJ >= '" + beginDate + "' AND HYXMZD.F_SQSJ <= '" + endDate + "')"
			                + " and HYXMZD.F_XMBH like '%" + F_XMBH + "%' and HYXMZD.F_XMBH = HYXMCP.F_XMBH"
			                + " and HYCPZD.F_CPBH like '" + F_CPBH + "%' and HYXMCP.F_SYZT = '1' and HYXMCP.F_CPBH = HYCPZD.F_CPBH " 
			                + " and exists(select 1 from HYXMMX where "
			                + " HYXMZD.F_XMBH = HYXMMX.F_XMBH and HYXMMX.F_CLBH = '" + F_CLBH + "')"
			                + " order by HYXMZD.F_XMBH, HYCPZD.F_CPBH";
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
	
	//--------------------------------------------------------------------------------------------------
	//描述:获取项目材料帮助
	//设计: ES
	//实现: ES
	//修改:
	//--------------------------------------------------------------------------------------------------
	public EFDataSet xmclHelp(String F_XMBH, String F_CPBH, String F_CLBH, String F_CKBH, int startIndex, int coun) throws Exception {
		JConnection    conn = null;
		JStatement     stmt = null;
		ResultSet        rs = null;
		EFDataSet   dataSet = EFDataSet.getInstance("HYXMCL");
		ESPRowSet  rkRowSet = null;
		ESPRowSet    rowSet = null;
		String[]   primeKey = {"F_XMBH", "F_CPBH", "F_CLBH", "F_CSBH", "F_DWBH", "F_CLDJ"};
		double         kcsl = 0.0;//库存数量
		double         rksl = 0.0;//入库数量
		double         dbsl = 0.0;//调拨数量
		double       clthsl = 0.0;//材料退货数量
		double       csthsl = 0.0;//厂商退货数量
		double       zccksl = 0.0;//出库数量
		double      bjdcksl = 0.0;//出库数量
		double       jdcksl = 0.0;//出库数量
		
		dataSet.setPrimeKey(primeKey);

		//查询项目对应的所有材料
		String sql = "";
		//材料入库
		sql = " select HYXMCK.F_XMBH, HYXMCK.F_CPBH, MAX(HYXMZD.F_XMMC) as F_XMMC, "
		    + " HYXMCK.F_CLBH, MAX(HYCLZD.F_CLMC) as F_CLMC,HYXMCK.F_DWBH, MAX(HYDWZD.F_DWMC) as F_DWMC,"
		    + " HYXMCK.F_CSBH, MAX(HYCSZD.F_CSMC) as F_CSMC, MAX(HYCPZD.F_CPMC) as F_CPMC," 
			+ " MAX(HYCLZD.F_GGXH) as F_GGXH, MAX(HYCLZD.F_JLDW) as F_JLDW,HYXMCK.F_CLDJ,"			       
			+ " F_SL as F_SQSL,"//出库数量,申请数量
			+ " round(sum(HYXMCK.F_CLSL), 2) as F_RKSL"//入库数量
			+ " from HYXMCK"
			+ " LEFT JOIN HYCLZD ON HYXMCK.F_CLBH = HYCLZD.F_CLBH"
			+ " LEFT JOIN HYXMZD ON HYXMCK.F_XMBH = HYXMZD.F_XMBH"
			+ " LEFT JOIN HYDWZD ON HYXMCK.F_DWBH = HYDWZD.F_DWBH"
			+ " LEFT JOIN HYCSZD ON HYXMCK.F_CSBH = HYCSZD.F_CSBH"
			+ " LEFT JOIN HYCPZD ON HYXMCK.F_CPBH = HYCPZD.F_CPBH"
			+ " LEFT JOIN HYXMMX ON HYXMMX.F_XMBH = HYXMCK.F_XMBH AND HYXMMX.F_CLBH = HYXMCK.F_CLBH"
			+ " where HYXMCK.F_XMBH like '%" + F_XMBH + "%' and HYXMCK.F_CPBH like '%" + F_CPBH + "%'"
			+ " and HYXMCK.F_CLBH like '%" + F_CLBH + "%' and HYXMCK.F_CRFX='R'"
			+ " and HYXMCK.F_CKBH = '" + F_CKBH + "' and exists(select 1 from HYXMMX where HYXMMX.F_XMBH = HYXMCK.F_XMBH" 
			+ " and HYXMMX.F_CLBH = HYXMCK.F_CLBH)"
			+ " GROUP BY HYXMCK.F_XMBH, HYXMCK.F_CLBH,HYXMCK.F_DWBH,HYXMCK.F_CSBH,F_CLDJ,HYXMCK.F_CPBH";
		
		try {
			conn = JConnection.getInstance(jdbcTemplate.getDataSource().getConnection());
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);

			while(rs != null && rs.next()) {
				if(dataSet == null) dataSet = EFDataSet.getInstance();
				// 生成ESPRowSet
				rowSet = EFRowSet.getInstance();
				// 将ResultSet中的一行生成到RowSet
				rowSet = DataSetUtils.resultSet2RowSet(rs, rowSet, true);
				// 插入DataSet中
				dataSet.insertRowSet(rowSet);
				// 形成主键索引
				dataSet.buildPrimeKeyIndex(rowSet);
			}

			//材料调用材料出库（被借调方）,比如A借调到B，查询A
			sql = " select HYXMCK.F_XMBH, HYXMCK.F_CPBH, MAX(HYXMZD.F_XMMC) as F_XMMC, "
			    + " HYXMCK.F_CLBH, MAX(HYCLZD.F_CLMC) as F_CLMC,HYXMCK.F_DWBH, MAX(HYDWZD.F_DWMC) as F_DWMC,"
			    + " HYXMCK.F_CSBH, MAX(HYCSZD.F_CSMC) as F_CSMC, MAX(HYCPZD.F_CPMC) as F_CPMC," 
			    + " MAX(HYCLZD.F_GGXH) as F_GGXH, MAX(HYCLZD.F_JLDW) as F_JLDW,HYXMCK.F_CLDJ,"
			    + " F_SL as F_SQSL, IFNULL(round(sum(HYXMCK.F_CLSL), 2), 0.0) as F_CKSL"//出库数量,申请数量
				+ " from HYXMCK"	
				+ " LEFT JOIN HYCLZD ON HYXMCK.F_CLBH = HYCLZD.F_CLBH"
			    + " LEFT JOIN HYXMZD ON HYXMCK.F_XMBH = HYXMZD.F_XMBH"
			    + " LEFT JOIN HYDWZD ON HYXMCK.F_DWBH = HYDWZD.F_DWBH"
			    + " LEFT JOIN HYCSZD ON HYXMCK.F_CSBH = HYCSZD.F_CSBH"
			    + " LEFT JOIN HYCPZD ON HYXMCK.F_CPBH = HYCPZD.F_CPBH"
			    + " LEFT JOIN HYXMMX ON HYXMMX.F_XMBH = HYXMCK.F_XMBH AND HYXMMX.F_CLBH = HYXMCK.F_CLBH"
				+ " where HYXMCK.F_XMBH like '%" + F_XMBH + "%' and HYXMCK.F_CPBH like '%" + F_CPBH + "%'"
				+ " and HYXMCK.F_CLBH like '%" + F_CLBH + "%' and F_CRFX = 'C'"
				+ " and HYXMCK.F_CKBH = '" + F_CKBH + "' and exists(select 1 from HYXMMX where HYXMMX.F_XMBH = HYXMCK.F_XMBH" 
				+ " and HYXMMX.F_CLBH = HYXMCK.F_CLBH) and F_FLLX = '1'"
				+ " GROUP BY HYXMCK.F_XMBH, HYXMCK.F_CLBH,HYXMCK.F_DWBH,HYXMCK.F_CSBH,F_CLDJ,HYXMCK.F_CPBH";
			rs = stmt.executeQuery(sql);
			while(rs != null && rs.next()) {
				// 生成ESPRowSet
				rowSet = EFRowSet.getInstance();
				// 将ResultSet中的一行生成到RowSet
				rowSet = DataSetUtils.resultSet2RowSet(rs, rowSet, true);
				// 
				rkRowSet = dataSet.getRowSet(dataSet.getPrimeKeyValue(rowSet));
				// 赋值出库单
				if(rkRowSet != null){
					rkRowSet.putNumber("F_BJDCKSL", rowSet.getNumber("F_CKSL", 0.0));
				} else {
					rowSet.putNumber("F_BJDCKSL", rowSet.getNumber("F_CKSL", 0.0));
					dataSet.addRowSet(rowSet);
				}
			}
			
			//材料调用材料出库（借调方）,比如A借调到B，查询B
			sql = " select HYXMCK.F_YYXMBH, HYXMCK.F_YYCPBH, MAX(HYXMZD.F_XMMC) as F_XMMC, "
			    + " HYXMCK.F_CLBH, MAX(HYCLZD.F_CLMC) as F_CLMC,HYXMCK.F_DWBH, MAX(HYDWZD.F_DWMC) as F_DWMC,"
			    + " HYXMCK.F_CSBH, MAX(HYCSZD.F_CSMC) as F_CSMC, MAX(HYCPZD.F_CPMC) as F_CPMC," 
			    + " MAX(HYCLZD.F_GGXH) as F_GGXH, MAX(HYCLZD.F_JLDW) as F_JLDW,HYXMCK.F_CLDJ,"			       
			    + " F_SL as F_SQSL, IFNULL(round(sum(HYXMCK.F_CLSL), 2), 0.0) as F_CKSL"//出库数量,申请数量
				+ " from HYXMCK"	
				+ " LEFT JOIN HYCLZD ON HYXMCK.F_CLBH = HYCLZD.F_CLBH"
			    + " LEFT JOIN HYXMZD ON HYXMCK.F_YYXMBH = HYXMZD.F_XMBH"
			    + " LEFT JOIN HYDWZD ON HYXMCK.F_DWBH = HYDWZD.F_DWBH"
			    + " LEFT JOIN HYCSZD ON HYXMCK.F_CSBH = HYCSZD.F_CSBH"
			    + " LEFT JOIN HYCPZD ON HYXMCK.F_YYCPBH = HYCPZD.F_CPBH"
			    + " LEFT JOIN HYXMMX ON HYXMMX.F_XMBH = HYXMCK.F_YYXMBH AND HYXMMX.F_CLBH = HYXMCK.F_CLBH"
				+ " where HYXMCK.F_YYXMBH like '%" + F_XMBH + "%' and HYXMCK.F_YYCPBH like '%" + F_CPBH + "%'"
				+ " and HYXMCK.F_CLBH like '%" + F_CLBH + "%' and F_CRFX = 'C'"
				+ " and HYXMCK.F_CKBH = '" + F_CKBH + "' and exists(select 1 from HYXMMX where HYXMMX.F_XMBH = HYXMCK.F_YYXMBH" 
				+ " and HYXMMX.F_CLBH = HYXMCK.F_CLBH) and F_FLLX = '1'"
				+ " GROUP BY HYXMCK.F_YYXMBH, HYXMCK.F_CLBH,HYXMCK.F_DWBH,HYXMCK.F_CSBH,F_CLDJ,HYXMCK.F_YYCPBH";
			rs = stmt.executeQuery(sql);
			while(rs != null && rs.next()) {
				// 生成ESPRowSet
				rowSet = EFRowSet.getInstance();
				// 将ResultSet中的一行生成到RowSet
				rowSet = DataSetUtils.resultSet2RowSet(rs, rowSet, true);
				rowSet.putString("F_XMBH", rowSet.getString("F_YYXMBH", ""));
				rowSet.putString("F_CPBH", rowSet.getString("F_YYCPBH", ""));
				// 
				rkRowSet = dataSet.getRowSet(dataSet.getPrimeKeyValue(rowSet));
				// 赋值出库单
				if(rkRowSet != null){
					rkRowSet.putNumber("F_JDCKSL", rowSet.getNumber("F_CKSL", 0.0));
				} else {
					rowSet.putNumber("F_JDCKSL", rowSet.getNumber("F_CKSL", 0.0));
					dataSet.addRowSet(rowSet);
				}
			}
			
			//正常材料出库
			sql = " select HYXMCK.F_XMBH, HYXMCK.F_CPBH, MAX(HYXMZD.F_XMMC) as F_XMMC, "
			    + " HYXMCK.F_CLBH, MAX(HYCLZD.F_CLMC) as F_CLMC,HYXMCK.F_DWBH, MAX(HYDWZD.F_DWMC) as F_DWMC,"
			    + " HYXMCK.F_CSBH, MAX(HYCSZD.F_CSMC) as F_CSMC, MAX(HYCPZD.F_CPMC) as F_CPMC," 
			    + " MAX(HYCLZD.F_GGXH) as F_GGXH, MAX(HYCLZD.F_JLDW) as F_JLDW,HYXMCK.F_CLDJ,"			       
			    + " F_SL as F_SQSL, IFNULL(round(sum(HYXMCK.F_CLSL), 2), 0.0) as F_CKSL"//出库数量,申请数量
				+ " from HYXMCK"	
				+ " LEFT JOIN HYCLZD ON HYXMCK.F_CLBH = HYCLZD.F_CLBH"
			    + " LEFT JOIN HYXMZD ON HYXMCK.F_XMBH = HYXMZD.F_XMBH"
			    + " LEFT JOIN HYDWZD ON HYXMCK.F_DWBH = HYDWZD.F_DWBH"
			    + " LEFT JOIN HYCSZD ON HYXMCK.F_CSBH = HYCSZD.F_CSBH"
			    + " LEFT JOIN HYCPZD ON HYXMCK.F_CPBH = HYCPZD.F_CPBH"
			    + " LEFT JOIN HYXMMX ON HYXMMX.F_XMBH = HYXMCK.F_XMBH AND HYXMMX.F_CLBH = HYXMCK.F_CLBH"
				+ " where HYXMCK.F_XMBH like '%" + F_XMBH + "%' and HYXMCK.F_CPBH like '%" + F_CPBH + "%'"
				+ " and HYXMCK.F_CLBH like '%" + F_CLBH + "%' and F_CRFX = 'C'"
				+ " and HYXMCK.F_YYCKBH = '" + F_CKBH + "' and exists(select 1 from HYXMMX where HYXMMX.F_XMBH = HYXMCK.F_XMBH" 
				+ " and HYXMMX.F_CLBH = HYXMCK.F_CLBH) and F_FLLX = '0'"
				+ " GROUP BY HYXMCK.F_XMBH, HYXMCK.F_CLBH,HYXMCK.F_DWBH,HYXMCK.F_CSBH,F_CLDJ,HYXMCK.F_CPBH";
			rs = stmt.executeQuery(sql);
			while(rs != null && rs.next()) {
				// 生成ESPRowSet
				rowSet = EFRowSet.getInstance();
				// 将ResultSet中的一行生成到RowSet
				rowSet = DataSetUtils.resultSet2RowSet(rs, rowSet, true);
				// 
				rkRowSet = dataSet.getRowSet(dataSet.getPrimeKeyValue(rowSet));
				// 赋值出库单
				if(rkRowSet != null){
					rkRowSet.putNumber("F_ZCCKSL", rowSet.getNumber("F_CKSL", 0.0));
				} else {
					rowSet.putNumber("F_ZCCKSL", rowSet.getNumber("F_CKSL", 0.0));
					dataSet.addRowSet(rowSet);
				}
			}
			
			//仓库调拨出库
			sql = " select HYXMCK.F_XMBH, HYXMCK.F_CPBH, MAX(HYXMZD.F_XMMC) as F_XMMC, "
			    + " HYXMCK.F_CLBH, MAX(HYCLZD.F_CLMC) as F_CLMC,HYXMCK.F_DWBH, MAX(HYDWZD.F_DWMC) as F_DWMC,"
			    + " HYXMCK.F_CSBH, MAX(HYCSZD.F_CSMC) as F_CSMC, MAX(HYCPZD.F_CPMC) as F_CPMC," 
			    + " MAX(HYCLZD.F_GGXH) as F_GGXH, MAX(HYCLZD.F_JLDW) as F_JLDW,HYXMCK.F_CLDJ,"			       
			    + " F_SL as F_SQSL, IFNULL(round(sum(HYXMCK.F_CLSL), 2), 0.0) as F_CKSL"//出库数量,申请数量
				+ " from HYXMCK"	
				+ " LEFT JOIN HYCLZD ON HYXMCK.F_CLBH = HYCLZD.F_CLBH"
			    + " LEFT JOIN HYXMZD ON HYXMCK.F_XMBH = HYXMZD.F_XMBH"
			    + " LEFT JOIN HYDWZD ON HYXMCK.F_DWBH = HYDWZD.F_DWBH"
			    + " LEFT JOIN HYCSZD ON HYXMCK.F_CSBH = HYCSZD.F_CSBH"
			    + " LEFT JOIN HYCPZD ON HYXMCK.F_CPBH = HYCPZD.F_CPBH"
			    + " LEFT JOIN HYXMMX ON HYXMMX.F_XMBH = HYXMCK.F_XMBH AND HYXMMX.F_CLBH = HYXMCK.F_CLBH"
				+ " where HYXMCK.F_YYXMBH like '%" + F_XMBH + "%' and HYXMCK.F_YYCPBH like '%" + F_CPBH + "%'"
				+ " and HYXMCK.F_CLBH like '%" + F_CLBH + "%' and F_CRFX = 'D'"
				+ " and HYXMCK.F_YYCKBH = '" + F_CKBH + "' and exists(select 1 from HYXMMX where HYXMMX.F_XMBH = HYXMCK.F_YYXMBH" 
				+ " and HYXMMX.F_CLBH = HYXMCK.F_CLBH) and F_FLLX = '1'"
				+ " GROUP BY HYXMCK.F_YYXMBH, HYXMCK.F_CLBH,HYXMCK.F_DWBH,HYXMCK.F_CSBH,F_CLDJ,HYXMCK.F_YYCPBH";
			rs = stmt.executeQuery(sql);
			while(rs != null && rs.next()) {
				// 生成ESPRowSet
				rowSet = EFRowSet.getInstance();
				// 将ResultSet中的一行生成到RowSet
				rowSet = DataSetUtils.resultSet2RowSet(rs, rowSet, true);
				// 
				rkRowSet = dataSet.getRowSet(dataSet.getPrimeKeyValue(rowSet));
				// 赋值出库单
				if(rkRowSet != null){
					rkRowSet.putNumber("F_DBCKSL", rowSet.getNumber("F_CKSL", 0.0));
				} else {
					rowSet.putNumber("F_DBCKSL", rowSet.getNumber("F_CKSL", 0.0));
					dataSet.addRowSet(rowSet);
				}
			}
			
			//材料退货材料，影响出库数量
			sql = " select HYXMCK.F_YYXMBH as F_XMBH, HYXMCK.F_YYCPBH as F_CPBH, MAX(HYXMZD.F_XMMC) as F_XMMC, "
			    + " HYXMCK.F_CLBH, MAX(HYCLZD.F_CLMC) as F_CLMC,HYXMCK.F_DWBH, MAX(HYDWZD.F_DWMC) as F_DWMC,"
			    + " HYXMCK.F_CSBH, MAX(HYCSZD.F_CSMC) as F_CSMC, MAX(HYCPZD.F_CPMC) as F_CPMC," 
			    + " MAX(HYCLZD.F_GGXH) as F_GGXH, MAX(HYCLZD.F_JLDW) as F_JLDW,HYXMCK.F_CLDJ,"			       
			    + " F_SL as F_SQSL, IFNULL(round(sum(HYXMCK.F_CLSL), 2), 0.0) as F_THSL"//出库数量,申请数量
				+ " from HYXMCK"
				+ " LEFT JOIN HYCLZD ON HYXMCK.F_CLBH = HYCLZD.F_CLBH"
			    + " LEFT JOIN HYXMZD ON HYXMCK.F_YYXMBH = HYXMZD.F_XMBH"
			    + " LEFT JOIN HYDWZD ON HYXMCK.F_DWBH = HYDWZD.F_DWBH"
			    + " LEFT JOIN HYCSZD ON HYXMCK.F_CSBH = HYCSZD.F_CSBH"
			    + " LEFT JOIN HYCPZD ON HYXMCK.F_YYCPBH = HYCPZD.F_CPBH"
			    + " LEFT JOIN HYXMMX ON HYXMMX.F_XMBH = HYXMCK.F_YYXMBH AND HYXMMX.F_CLBH = HYXMCK.F_CLBH"
				+ " where HYXMCK.F_YYXMBH like '%" + F_XMBH + "%' and HYXMCK.F_YYCPBH like '%" + F_CPBH + "%'"
				+ " and HYXMCK.F_CLBH like '%" + F_CLBH + "%' and HYXMCK.F_CRFX='T'"
				+ " and HYXMCK.F_YYCKBH = '" + F_CKBH + "' and exists(select 1 from HYXMMX where HYXMMX.F_XMBH = HYXMCK.F_YYXMBH" 
				+ " and HYXMMX.F_CLBH = HYXMCK.F_CLBH) and F_FLLX = '0'"
			    + " GROUP BY HYXMCK.F_YYXMBH, HYXMCK.F_CLBH,HYXMCK.F_DWBH,HYXMCK.F_CSBH,F_CLDJ,HYXMCK.F_YYCPBH";
			rs = stmt.executeQuery(sql);
			while(rs != null && rs.next()) {			
				// 生成ESPRowSet
				rowSet = EFRowSet.getInstance();
				// 将ResultSet中的一行生成到RowSet
				rowSet = DataSetUtils.resultSet2RowSet(rs, rowSet, true);
				// 
				rkRowSet = dataSet.getRowSet(dataSet.getPrimeKeyValue(rowSet));
				// 赋值出库单
				if(rkRowSet != null){
					rkRowSet.putNumber("F_CLTHSL", rowSet.getNumber("F_THSL", 0.0));
				} else {
					rowSet.putNumber("F_CLTHSL", rowSet.getNumber("F_THSL", 0.0));
					dataSet.addRowSet(rowSet);
				}
			}
			
			//厂商退货材料
			sql = " select HYXMCK.F_YYXMBH as F_XMBH, HYXMCK.F_YYCPBH as F_CPBH, MAX(HYXMZD.F_XMMC) as F_XMMC, "
			    + " HYXMCK.F_CLBH, MAX(HYCLZD.F_CLMC) as F_CLMC,HYXMCK.F_DWBH, MAX(HYDWZD.F_DWMC) as F_DWMC,"
			    + " HYXMCK.F_CSBH, MAX(HYCSZD.F_CSMC) as F_CSMC, MAX(HYCPZD.F_CPMC) as F_CPMC," 
			    + " MAX(HYCLZD.F_GGXH) as F_GGXH, MAX(HYCLZD.F_JLDW) as F_JLDW,HYXMCK.F_CLDJ,"			       
			    + " F_SL as F_SQSL, IFNULL(round(sum(HYXMCK.F_CLSL), 2), 0.0) as F_THSL"//出库数量,申请数量
				+ " from HYXMCK"
				+ " LEFT JOIN HYCLZD ON HYXMCK.F_CLBH = HYCLZD.F_CLBH"
			    + " LEFT JOIN HYXMZD ON HYXMCK.F_YYXMBH = HYXMZD.F_XMBH"
			    + " LEFT JOIN HYDWZD ON HYXMCK.F_DWBH = HYDWZD.F_DWBH"
			    + " LEFT JOIN HYCSZD ON HYXMCK.F_CSBH = HYCSZD.F_CSBH"
			    + " LEFT JOIN HYCPZD ON HYXMCK.F_YYCPBH = HYCPZD.F_CPBH"
			    + " LEFT JOIN HYXMMX ON HYXMMX.F_XMBH = HYXMCK.F_YYXMBH AND HYXMMX.F_CLBH = HYXMCK.F_CLBH"
				+ " where HYXMCK.F_YYXMBH like '%" + F_XMBH + "%' and HYXMCK.F_YYCPBH like '%" + F_CPBH + "%'"
				+ " and HYXMCK.F_CLBH like '%" + F_CLBH + "%' and HYXMCK.F_CRFX='T'"
				+ " and HYXMCK.F_YYCKBH = '" + F_CKBH + "' and exists(select 1 from HYXMMX where HYXMMX.F_XMBH = HYXMCK.F_YYXMBH" 
				+ " and HYXMMX.F_CLBH = HYXMCK.F_CLBH)"
				+ " and exists(" 
			    + " select 1 from HYTHD where HYTHD.F_KJQJ = HYXMCK.F_KJQJ and HYTHD.F_DJBH = HYXMCK.F_DJBH"
			    + " and HYTHD.F_THLX = '1')"
			    + " GROUP BY HYXMCK.F_YYXMBH, HYXMCK.F_CLBH,HYXMCK.F_DWBH,HYXMCK.F_CSBH,F_CLDJ,HYXMCK.F_YYCPBH";
			rs = stmt.executeQuery(sql);
			while(rs != null && rs.next()) {			
				// 生成ESPRowSet
				rowSet = EFRowSet.getInstance();
				// 将ResultSet中的一行生成到RowSet
				rowSet = DataSetUtils.resultSet2RowSet(rs, rowSet, true);
				// 
				rkRowSet = dataSet.getRowSet(dataSet.getPrimeKeyValue(rowSet));
				// 赋值出库单
				if(rkRowSet != null){
					rkRowSet.putNumber("F_CSTHSL", rowSet.getNumber("F_THSL", 0.0));
				} else {
					rowSet.putNumber("F_CSTHSL", rowSet.getNumber("F_THSL", 0.0));
					dataSet.addRowSet(rowSet);
				}
			}
			
			for(int i = 0; i < dataSet.getRowCount(); i++) {
				rowSet = dataSet.getRowSet(i);
				rksl = rowSet.getNumber("F_RKSL", 0.0).doubleValue();//入库数量
				zccksl = rowSet.getNumber("F_ZCCKSL", 0.0).doubleValue();//正常出库数量
				jdcksl = rowSet.getNumber("F_JDCKSL", 0.0).doubleValue();//调拨出库数量
				bjdcksl = rowSet.getNumber("F_BJDCKSL", 0.0).doubleValue();//调拨出库数量
				clthsl = rowSet.getNumber("F_CLTHSL", 0.0).doubleValue();//材料退货数量
				csthsl = rowSet.getNumber("F_CSTHSL", 0.0).doubleValue();//厂商退货数量
				dbsl =  rowSet.getNumber("F_DBCKSL", 0.0).doubleValue();//厂商退货数量
				
				//比如A借调到B，A为被借调方，B为借调方
				//所以库存数量=入库数量-正常出库数量-被借调出库数量+材料退货数量
				//领用数量=正常出库数量+借调出库数量+仓库调拨数量-材料退货数量-厂商退货数量
				kcsl = rksl - zccksl - bjdcksl + clthsl;
				rowSet.putObject("F_SYSL", kcsl);
				rowSet.putObject("F_LYSL", Math.abs(zccksl + jdcksl - clthsl - csthsl + dbsl));
			}
		
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
	
	
	//--------------------------------------------------------------------------------------------------
	//描述:获取项目产品帮助
	//设计: ES
	//实现: ES
	//修改:
	//--------------------------------------------------------------------------------------------------
	public List<String[]> xmcpHelp(String F_XMBH, String F_CLBH, String F_CPBH, String F_XMZT, int startIndex, int coun) {
		final List<String[]>   dataList = new ArrayList<String[]>();
		Object[]                 object = null;
		String                      sql = "";
		
		if(F_CPBH == null) {
			object = new Object[2];
			object[0] = F_XMBH;
			object[1] = F_CLBH;
		} else {
			object = new Object[3];
			object[0] = F_XMBH;
			object[1] = F_CLBH;
			object[2] = F_CPBH;
		}
		
		//项目申请单已领用数量
		sql = " select HYXMCK.F_YYXMBH, HYXMCK.F_CLBH, HYXMCK.F_YYCPBH, MAX(HYCPZD.F_CPMC), "
		    + " sum(case when F_CRFX = 'T' then F_CLSL * -1 else F_CLSL END), F_SL "
		    + " from HYXMCK "
		    + " LEFT JOIN HYCPZD ON HYXMCK.F_YYCPBH = HYCPZD.F_CPBH"
		    + " LEFT JOIN HYXMMX ON HYXMMX.F_XMBH = HYXMCK.F_YYXMBH AND HYXMMX.F_CLBH = HYXMCK.F_CLBH ";
		if(F_CPBH == null) {			
			sql += " where HYXMCK.F_YYXMBH=? and HYXMCK.F_CLBH = ? and (F_CRFX = 'C' or F_CRFX = 'T') and";
		} else {
			sql += " where HYXMCK.F_YYXMBH=? and HYXMCK.F_CLBH = ? and HYXMCK.F_CPBH = ? and (F_CRFX = 'C' or F_CRFX = 'T') and";
		}
		sql  += " exists(select 1 from HYXMMX where HYXMMX.F_XMBH = HYXMCK.F_YYXMBH and HYXMMX.F_CLBH = HYXMCK.F_CLBH)" 
		      + " GROUP BY HYXMCK.F_XMBH, HYXMCK.F_CLBH,HYXMCK.F_CPBH";
		jdbcTemplate.query(sql, object, new RowCallbackHandler(){
			public void processRow(ResultSet rs) throws SQLException{
				String[] dataArray = new String[5];
				dataArray[0] = rs.getString(3);    //材料编号
				dataArray[1] = rs.getString(4);    //材料名称
				dataArray[2] = String.valueOf(rs.getInt(5));   //申请数量
				dataArray[3] = String.valueOf(rs.getInt(6));   //领用数量
				double sqsl = 0.0;//申请数量
				double lysl = 0.0;//领用数量
				//计算可领用数量
				try {
					lysl = rs.getDouble(5);
				} catch(Exception ce) {
					lysl = 0.0;
				}
				try {
					sqsl = rs.getDouble(6);
				} catch(Exception ce) {
					sqsl = 0.0;
				}
				//计算可领用数量
				dataArray[4] = String.valueOf(sqsl - lysl);
				dataList.add(dataArray);
			}
		});
		return dataList;
	}
	
	//--------------------------------------------------------------------------------------------------
	//描述:获取项目产品帮助
	//设计: ES
	//实现: ES
	//修改:
	//--------------------------------------------------------------------------------------------------
	public List<String[]> xmHelp(String F_XMBH, String F_CPBH, String F_CLBH, String F_CKBH, String F_XMZT, int startIndex, int coun) {
		final List<String[]> dataList = new ArrayList<String[]>();

		String sql = "";
		
		F_XMBH += "%";
		F_CLBH += "%";
		F_CPBH += "%";

		//库存剩余材料
		sql = " select HYXMCK.F_XMBH, MAX(HYXMZD.F_XMMC), HYXMCK.F_CPBH, MAX(HYCPZD.F_CPMC), "
		    + " HYXMCK.F_CLBH, MAX(HYCLZD.F_CLMC),HYXMCK.F_DWBH, MAX(HYDWZD.F_DWMC),"
		    + " MAX(HYCLZD.F_GGXH), MAX(HYCLZD.F_JLDW),HYXMCK.F_CLDJ,"
		    + " F_SL,sum(case when SY.F_CRFX = 'C' then SY.F_CLSL * -1 else SY.F_CLSL END)"
		    + " from HYXMCK"
		    + " LEFT JOIN HYCLZD ON HYXMCK.F_CLBH = HYCLZD.F_CLBH"
		    + " LEFT JOIN HYDWZD ON HYXMCK.F_DWBH = HYDWZD.F_DWBH"
		    + " LEFT JOIN HYXMZD ON HYXMCK.F_XMBH = HYXMZD.F_XMBH and HYXMZD.F_XMZT = '" + F_XMZT  + "'"
		    + " LEFT JOIN HYCPZD ON HYXMCK.F_CPBH = HYCPZD.F_CPBH and HYXMZD.F_XMBH = HYCPZD.F_XMBH"
		    + " LEFT JOIN HYXMMX ON HYXMMX.F_XMBH = HYXMCK.F_XMBH AND HYXMMX.F_CLBH = HYXMCK.F_CLBH"
		    + " LEFT JOIN HYXMCK SY ON HYXMCK.F_XMBH = SY.F_XMBH AND HYXMCK.F_CPBH = SY.F_CPBH AND "
		    + " HYXMCK.F_CKBH = SY.F_CKBH and HYXMCK.F_CLBH=SY.F_CLBH"
		    + " where HYXMCK.F_XMBH like ? and HYXMCK.F_CPBH like ? and HYXMCK.F_CLBH like '%" + F_CLBH + "%' and HYXMCK.F_CRFX='R'"
		    + " and HYXMCK.F_CKBH = '" + F_CKBH + "' and exists(select 1 from HYXMMX where HYXMMX.F_XMBH = HYXMCK.F_XMBH" 
		    + " and HYXMMX.F_CLBH = HYXMCK.F_CLBH) and exists(select 1 from HYXMCP where " 
		    + " HYXMCK.F_CPBH = HYXMCP.F_CPBH and HYXMZD.F_XMBH = HYXMCP.F_XMBH and HYXMCP.F_SYZT = '1')"
		    + " GROUP BY HYXMCK.F_YYXMBH, HYXMCK.F_CLBH,HYXMCK.F_DWBH,F_CLDJ,HYXMCK.F_YYCPBH";
		jdbcTemplate.query(sql, new Object[]{F_XMBH, F_CPBH}, new RowCallbackHandler(){
			public void processRow(ResultSet rs) throws SQLException{
//				double sqsl = 0.0;//申请数量
				double lysl = 0.0;//领用数量
				String[] dataArray = new String[14];
				dataArray[0] = rs.getString(1);                  //项目编号
				dataArray[1] = rs.getString(2);                  //项目名称
				dataArray[2] = rs.getString(3);                  //产品编号
				dataArray[3] = rs.getString(4);                  //产品名称
				dataArray[4] = rs.getString(5);                  //材料编号
				dataArray[5] = rs.getString(6);                  //材料名称
				dataArray[6] = rs.getString(7);                  //供应商编号
				dataArray[7] = rs.getString(8);                  //供应商名称
				dataArray[8] = rs.getString(9);                  //规格型号
				dataArray[9] = rs.getString(10);                 //计量单位
				dataArray[10] = rs.getString(11);                //材料单价
//				sqsl = rs.getInt(12);                            //申请数量
				lysl = rs.getInt(13);                            //领用数量
				dataArray[11] = String.valueOf(lysl);     //库存数量
				dataList.add(dataArray);
			}
		});
		return dataList;
	}
	
	//--------------------------------------------------------------------------------------------------
	//描述:根据扫描枪扫描编号，显示材料
	//设计: ES
	//实现: ES
	//修改:
	//--------------------------------------------------------------------------------------------------
	public EFDataSet scanningHYCLZD(String F_XMBH, String F_CLBH, String F_DWBH, String F_CPBH, String F_CSBH, String WHERE) {
		EFDataSet        ds = EFDataSet.getInstance();
		String          sql = "";
		JConnection    conn = null;
		JStatement     stmt = null;
		ResultSet        rs = null;
		
		//库存剩余材料
		sql = " select HYXMZD.F_XMBH, HYXMZD.F_XMMC, HYXMZD.F_CRDATE, HYXMCP.F_CPBH, HYCPZD.F_CPMC,"
			+ " HYCLZD.F_CLBH, HYCLZD.F_CLMC, HYCLZD.F_GGXH, HYCLZD.F_JLDW, "
			+ " HYCSZD.F_CSBH, HYCSZD.F_CSMC, HYDWZD.F_DWBH, HYDWZD.F_DWMC"
		    + " from HYXMZD, HYXMCP, HYCPZD, HYXMMX, HYCLZD, HYCSZD, HYDWZD"		   
		    + " where HYXMZD.F_XMBH = '" + F_XMBH + "' and HYXMZD.F_XMZT = '0' and "
		    + " HYXMCP.F_XMBH = HYXMZD.F_XMBH and HYXMCP.F_SYZT = '1' and "
		    + " HYCPZD.F_SYZT = '1' and HYXMCP.F_CPBH = HYCPZD.F_CPBH and "
		    + " HYCLZD.F_CLBH like '" + F_CLBH + "%' and HYCLZD.F_CLBH = HYXMMX.F_CLBH and "
		    + " HYXMMX.F_XMBH = HYXMZD.F_XMBH and HYCLZD.F_SYZT = '1' and "
		    + " HYCLZD.F_CSBH = HYCSZD.F_CSBH and HYCSZD.F_SYZT = '1' and "
		    + " HYCLZD.F_DWBH = HYDWZD.F_DWBH and HYDWZD.F_SYZT = '1' and ";
//		if(!F_DWBH.equals("")) {
//			sql += " HYDWZD.F_DWBH = '" + F_DWBH + "' and ";
//		}
		if(!F_CPBH.equals("")) {
			sql += " HYCPZD.F_CPBH = '" + F_CPBH + "' and ";
		}
//		if(!F_CSBH.equals("")) {
//			sql += " HYCSZD.F_CSBH = '" + F_CSBH + "' and ";
//		}
		if(!WHERE.equals("")) {
			sql += WHERE + " and ";
		}
		sql += " 1 = 1 order by HYCLZD.F_CLBH, HYXMCP.F_CPBH";
		try {
			conn = JConnection.getInstance(jdbcTemplate.getDataSource().getConnection());
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			ds = DataSetUtils.resultSet2DataSet(rs, ds);
			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
			ds = EFDataSet.getInstance();
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
		return ds;
	}
	
	//--------------------------------------------------------------------------------------------------
	//描述:仓库材料帮助
	//设计: ES
	//实现: ES
	//修改:
	//--------------------------------------------------------------------------------------------------
	public EFDataSet deportMaterialHelp(String F_CKBH, String F_XMBH, String F_CLBH, String F_DWBH, String F_CSBH, String WHERE) {
		EFDataSet        ds = EFDataSet.getInstance();
		String          sql = "";
		JConnection    conn = null;
		JStatement     stmt = null;
		ResultSet        rs = null;
		
		//库存剩余材料
		sql = " select HYCK.F_CLBH, HYCLZD.F_CLMC, HYCLZD.F_GGXH, HYCLZD.F_JLDW, HYCK.F_DWBH, HYDWZD.F_DWMC, "
			+ " HYCK.F_CSBH, HYCSZD.F_CSMC, F_CLDJ, F_CLSL"
		    + " from HYCK, HYCLZD, HYCSZD, HYDWZD"		   
		    + " where HYCK.F_CLBH like '" + F_CLBH + "%' and HYCK.F_CLBH = HYCLZD.F_CLBH and "
		    + " HYCLZD.F_SYZT = '1' and HYCK.F_CKBH = '" + F_CKBH + "' and "
		    + " HYCLZD.F_CSBH = HYCSZD.F_CSBH and HYCSZD.F_SYZT = '1' and "
		    + " HYCLZD.F_DWBH = HYDWZD.F_DWBH and HYDWZD.F_SYZT = '1' and "
			+ " exists(select 1 from HYXMMX where HYXMMX.F_XMBH = '" + F_XMBH + "' and "
			+ " HYXMMX.F_CLBH = HYCK.F_CLBH) and ";
		if(!F_DWBH.equals("")) {
			sql += " HYDWZD.F_DWBH = '" + F_DWBH + "' and ";
		}
		if(!F_CSBH.equals("")) {
			sql += " HYCSZD.F_CSBH = '" + F_CSBH + "' and ";
		}
		if(!WHERE.equals("")) {
			sql += WHERE + " and ";
		}
		sql += " 1 = 1 order by HYCLZD.F_CLBH";
		try {
			conn = JConnection.getInstance(jdbcTemplate.getDataSource().getConnection());
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			ds = DataSetUtils.resultSet2DataSet(rs, ds);
			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
			ds = EFDataSet.getInstance();
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
		return ds;
	}
	
	public List<String[]> xmclHelp(String F_XMBH, String F_CLBH) {
		String strSql = " SELECT HYXMZD.F_XMBH,HYXMZD.F_XMMC,HYXMMX.F_CLBH,HYCLZD.F_CLMC,HYCLZD.F_GGXH,HYCLZD.F_JLDW,HYCLZD.F_DWBH,"
			          + " HYDWZD.F_DWMC,HYCLZD.F_CSBH,HYCSZD.F_CSMC"
			          + " from HYXMMX,HYXMZD,HYCLZD" 
			          + " LEFT JOIN HYDWZD ON HYCLZD.F_DWBH = HYDWZD.F_DWBH"
			          + " LEFT JOIN HYCSZD ON HYCLZD.F_CSBH = HYCSZD.F_CSBH"
			          + " where HYCLZD.F_CLBH=HYXMMX.F_CLBH and HYXMZD.F_XMBH = HYXMMX.F_XMBH and HYXMZD.F_XMZT='0'";
		if(F_XMBH.trim().equals("")) {
			strSql += "  and HYXMMX.F_XMBH like '%" + F_XMBH + "%' ";
		}
		strSql += " and HYCLZD.F_CLBH like '%" + F_CLBH + "%'";
		final List<String[]> dataList = new ArrayList<String[]>();
		jdbcTemplate.query(strSql, new Object[]{}, new RowCallbackHandler(){
			public void processRow(ResultSet rs) throws SQLException{
				String[] dataArray = new String[10];
				dataArray[0] = rs.getString(1);                  //项目编号
				dataArray[1] = rs.getString(2);                  //项目名称
				dataArray[2] = rs.getString(3);                  //材料编号
				dataArray[3] = rs.getString(4);                  //材料名称
				dataArray[4] = rs.getString(5);                  //规格型号
				dataArray[5] = rs.getString(6);                  //计量单位
				dataArray[6] = rs.getString(7);                  //供应商编号
				dataArray[7] = rs.getString(8);                  //供应商名称
				dataArray[8] = rs.getString(9);                  //厂商编号
				dataArray[9] = rs.getString(10);                 //厂商名称
				dataList.add(dataArray);
			}
		});
		return dataList;
	}
	
	//--------------------------------------------------------------------------------------------------
	//描述:根据项目申请日期范围，产品帮助
	//设计: ES
	//实现: ES
	//修改:
	//--------------------------------------------------------------------------------------------------
	public EFDataSet xmcpHelp(String beginDate, String endDate, String F_CPBH) {
		EFDataSet        ds = EFDataSet.getInstance();
		String          sql = "";
		JConnection    conn = null;
		JStatement     stmt = null;
		ResultSet        rs = null;
		
		//库存剩余材料
		sql = " select HYXMCP.F_CPBH, max(HYCPZD.F_CPMC) as F_CPMC"
		    + " from HYXMCP, HYCPZD"
		    + " where HYXMCP.F_CPBH like '%" + F_CPBH + "%' and "
		    + " HYXMCP.F_CPBH = HYCPZD.F_CPBH and HYCPZD.F_SYZT = '1' and "
		    + " HYXMCP.F_SYZT = '1' and exists(select 1 from HYXMZD where HYXMZD.F_XMBH = HYXMCP.F_XMBH and "
		    + " (HYXMZD.F_SQSJ >= '" + beginDate + "' AND HYXMZD.F_SQSJ <= '" + endDate + "')) "
		    + " group by HYXMCP.F_CPBH"
		    + " order by HYXMCP.F_CPBH";

		try {
			conn = JConnection.getInstance(jdbcTemplate.getDataSource().getConnection());
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			ds = DataSetUtils.resultSet2DataSet(rs, ds);
			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
			ds = EFDataSet.getInstance();
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
		return ds;
	}
	
	//--------------------------------------------------------------------------------------------------
	//描述:根据仓库帮助材料
	//设计: ES
	//实现: ES
	//修改:
	//--------------------------------------------------------------------------------------------------
	public EFDataSet searchHYCLZD(String beginDate, String endDate, String F_CKBH, String F_CLBH) {
		EFDataSet                 ds = EFDataSet.getInstance();
		String                   sql = "";
		JConnection             conn = null;
		JStatement              stmt = null;
		ResultSet                 rs = null;
		String          strBeginDate = beginDate + " 00:00:00";
		String            strEndDate = endDate;
		DateFormat            format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
		DateFormat           format1 = new SimpleDateFormat("yyyy-MM-dd");
		Date                   bDate = null;
		Date                   eDate = null; 
		Calendar            calendar = Calendar.getInstance();   

		try {
			bDate = format.parse(strBeginDate);
			eDate = format1.parse(endDate); 
			
			calendar.setTime(eDate); 
			System.out.println(calendar.get(Calendar.DAY_OF_MONTH));//截止日期 
			calendar.set(Calendar.DAY_OF_MONTH,calendar.get(Calendar.DAY_OF_MONTH)+1);//让日期加1  
			System.out.println(calendar.get(Calendar.DATE));//加1之后的日期Top 
			
			eDate = calendar.getTime();
			strEndDate = format1.format(eDate) + " 00:00:00";
			eDate = format.parse(strEndDate);
			
			sql = " select HYCLZD.F_CLBH, HYCLZD.F_CLMC, F_GGXH, F_JLDW"
			    + " from HYCLZD"		    
			    + " where HYCLZD.F_CLBH like '" + F_CLBH + "%' and "
			    + " exists(select 1 from HYXMCK where (HYXMCK.F_CKBH like '" + F_CKBH + "%' or HYXMCK.F_YYCKBH like '" 
			    + F_CKBH + "%') and HYXMCK.F_CLBH = HYCLZD.F_CLBH and "
			    + " HYXMCK.F_CHDATE < " + eDate.getTime() + " and HYXMCK.F_CHDATE >= " + bDate.getTime() + ")"
			    + " order by HYCLZD.F_CLBH";

			conn = JConnection.getInstance(jdbcTemplate.getDataSource().getConnection());
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			ds = DataSetUtils.resultSet2DataSet(rs, ds);
			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
			ds = EFDataSet.getInstance();
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
		return ds;
	}
	
	//--------------------------------------------------------------------------------------------------
	//描述:根据项目申请日期帮助材料
	//设计: ES
	//实现: ES
	//修改:
	//--------------------------------------------------------------------------------------------------
	public EFDataSet searchHYCLZD(String beginDate, String endDate, String F_CLBH) {
		EFDataSet        ds = EFDataSet.getInstance();
		String          sql = "";
		JConnection    conn = null;
		JStatement     stmt = null;
		ResultSet        rs = null;

		sql = " select HYCLZD.F_CLBH, HYCLZD.F_CLMC"
		    + " from HYCLZD"		    
		    + " where HYCLZD.F_CLBH like '" + F_CLBH + "%' and "
		    + " exists(select 1 from HYXMMX, HYXMZD where HYXMZD.F_XMBH = HYXMMX.F_XMBH and "
		    + " (HYXMZD.F_SQSJ >= '" + beginDate + "' AND HYXMZD.F_SQSJ <= '" + endDate + "') and HYXMMX.F_CLBH = HYCLZD.F_CLBH)"
		    + " order by HYCLZD.F_CLBH";

		try {
			conn = JConnection.getInstance(jdbcTemplate.getDataSource().getConnection());
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			ds = DataSetUtils.resultSet2DataSet(rs, ds);
			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
			ds = EFDataSet.getInstance();
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
		return ds;
	}
	
	//--------------------------------------------------------------------------------------------------
	//描述:根据项目申请日期和项目帮助帮助材料
	//设计: ES
	//实现: ES
	//修改:
	//--------------------------------------------------------------------------------------------------
	public EFDataSet searchHYCLZDByHYXM(String beginDate, String endDate, String F_XMBH, String F_CLBH) {
		EFDataSet        ds = EFDataSet.getInstance();
		String          sql = "";
		JConnection    conn = null;
		JStatement     stmt = null;
		ResultSet        rs = null;

		sql = " select HYCLZD.F_CLBH, HYCLZD.F_CLMC"
		    + " from HYCLZD"		    
		    + " where HYCLZD.F_CLBH like '" + F_CLBH + "%' and "
		    + " exists(select 1 from HYXMMX, HYXMZD where HYXMZD.F_XMBH = HYXMMX.F_XMBH and "
		    + " (HYXMZD.F_SQSJ >= '" + beginDate + "' AND HYXMZD.F_SQSJ <= '" + endDate 
		    + "') and HYXMMX.F_CLBH = HYCLZD.F_CLBH and HYXMZD.F_XMBH like '" + F_XMBH + "%')"
		    + " order by HYCLZD.F_CLBH";

		try {
			conn = JConnection.getInstance(jdbcTemplate.getDataSource().getConnection());
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			ds = DataSetUtils.resultSet2DataSet(rs, ds);
			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
			ds = EFDataSet.getInstance();
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
		return ds;
	}
}
