package com.etsoft.server;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.jdbc.support.JdbcUtils;
import org.springframework.stereotype.Repository;

import com.etsoft.pub.util.EMPReflectUtil;
import com.mrp.persistence.dailyBusiness.backgoods.bean.HYTHD;
import com.mrp.persistence.sysConfigure.project.bean.HYXMZD;
import com.mrp.persistence.sysConfigure.project.mapper.HYXMZDMapper;

import esyt.framework.com.builder.base.data.DataSetUtils;
import esyt.framework.com.builder.base.data.EFDataSet;
import esyt.framework.com.builder.base.data.EFRowSet;
import esyt.framework.com.core.foundation.sql.JConnection;
import esyt.framework.com.core.foundation.sql.JStatement;
import esyt.framework.pub.util.Debug;

@Repository
public class EMPFormUtil {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private HYXMZDMapper hyxmzdMapper;
	
	//--------------------------------------------------------------------------------------------------
	//描述:获取项目材料帮助
	//设计: ES
	//实现: ES
	//修改:
	//--------------------------------------------------------------------------------------------------
	public EFDataSet checkMaterial(String F_CLBH) {
		JConnection       conn = null;
		JStatement        stmt = null;
		ResultSet             rs = null;
		EFDataSet             ds = null;
		String[]           array = null;
		String            strSql = "select HYCLZD.F_CLBH, HYCLZD.F_CLMC from HYCLZD where HYCLZD.F_SYZT = '1' and (";
		array = F_CLBH.split("@");
		
		for(int i = 0; i < array.length; i++) {
			strSql += " HYCLZD.F_CLBH = '" + array[i] + "' or ";
		}
		strSql = strSql.substring(0, strSql.lastIndexOf("or")) + ")";
		
		Debug.PrintlnMessageToSystem("sql|||" + strSql);
//		System.out.println("sql|||" + strSql);
		try {
			conn = JConnection.getInstance(jdbcTemplate.getDataSource().getConnection());
			stmt = conn.createStatement();
			rs = stmt.executeQuery(strSql);
			ds = DataSetUtils.resultSet2DataSet(rs, ds);
		} catch (Exception e) {
			Debug.PrintlnMessageToSystem(e.getMessage());
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
	//描述:获取材料详细信息
	//设计: ES
	//实现: ES
	//修改:
	//--------------------------------------------------------------------------------------------------
	public EFDataSet materialInfo(String F_CLBH) {
		JConnection    conn = null;
		JStatement     stmt = null;
		ResultSet        rs = null;
		EFDataSet      ds = null;
		String[]    array = null;
		String     strSql = "select HYCLZD.F_CLBH, HYCLZD.F_CLMC, HYCSZD.F_CSBH, HYCSZD.F_CSMC, HYDWZD.F_DWBH, "
		                  + " HYDWZD.F_DWMC, HYCLZD.F_JLDW, HYCLZD.F_GGXH from "
		                  + " HYCLZD, HYCSZD, HYDWZD "
		                  + " where HYCLZD.F_CSBH = HYCSZD.F_CSBH and HYCSZD.F_SYZT = '1' and "
		                  + " HYCLZD.F_DWBH = HYDWZD.F_DWBH and HYDWZD.F_SYZT = '1' and "
		                  + " HYCLZD.F_SYZT = '1' and (";
		array = F_CLBH.split("@");
		for(int i = 0; i < array.length; i++) {
			strSql += " HYCLZD.F_CLBH = '" + array[i] + "' or ";
		}
		strSql = strSql.substring(0, strSql.lastIndexOf("or")) + ")";
		
		Debug.PrintlnMessageToSystem("sql|||" + strSql);
		try {
			conn = JConnection.getInstance(jdbcTemplate.getDataSource().getConnection());
			stmt = conn.createStatement();
			rs = stmt.executeQuery(strSql);
			ds = DataSetUtils.resultSet2DataSet(rs, ds);
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
	//描述:获取材料详细信息
	//设计: ES
	//实现: ES
	//修改:
	//--------------------------------------------------------------------------------------------------
	public EFDataSet deportMaterialInfo(String F_CLBH, String F_CKBH) {
		JConnection    conn = null;
		JStatement     stmt = null;
		ResultSet        rs = null;
		EFDataSet      ds = null;
		String[]    array = null;
		String     strSql = "select HYCK.F_CLBH, HYCLZD.F_CLMC, HYCK.F_CSBH, HYCSZD.F_CSMC, HYCK.F_DWBH, "
		                  + " HYDWZD.F_DWMC, HYCK.F_CLDJ, HYCK.F_CLSL from "
		                  + " HYCK, HYCLZD, HYCSZD, HYDWZD "
		                  + " where HYCK.F_CSBH = HYCSZD.F_CSBH and HYCSZD.F_SYZT = '1' and "
		                  + " HYCK.F_DWBH = HYDWZD.F_DWBH and HYDWZD.F_SYZT = '1' and "
		                  + " HYCK.F_CKBH = '" + F_CKBH + "' and HYCK.F_CLBH = HYCLZD.F_CLBH and "
		                  
		                  + " HYCLZD.F_SYZT = '1' and (";
		array = F_CLBH.split("@");
		for(int i = 0; i < array.length; i++) {
			strSql += " HYCLZD.F_CLBH = '" + array[i] + "' or ";
		}
		strSql = strSql.substring(0, strSql.lastIndexOf("or")) + ")";
		
		Debug.PrintlnMessageToSystem("sql|||" + strSql);
		try {
			conn = JConnection.getInstance(jdbcTemplate.getDataSource().getConnection());
			stmt = conn.createStatement();
			rs = stmt.executeQuery(strSql);
			ds = DataSetUtils.resultSet2DataSet(rs, ds);
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
	//描述:获取库存数量
	//设计: ES
	//实现: ES
	//修改:
	//--------------------------------------------------------------------------------------------------
	public double getClKcSl(String F_XMBH, String F_CLBH) {
		JConnection    conn = null;
		JStatement     stmt = null;
		ResultSet        rs = null;
		double           sl = 0;

		String      sql = " select round(sum(HYXMCK.F_CLSL)) as F_RKSL,"
			            + " round(sum(TH.F_CLSL), 2) as F_THSL, round(sum(CK.F_CLSL), 2) as F_CKSL from HYXMCK"
			            + " LEFT JOIN HYXMCK TH ON HYXMCK.F_XMBH = TH.F_YYXMBH and "
						+ " HYXMCK.F_CLBH = TH.F_CLBH and HYXMCK.F_CKBH = TH.F_YYCKBH and HYXMCK.F_CPBH = TH.F_YYCPBH"
						+ " and HYXMCK.F_CLDJ = TH.F_CLDJ and TH.F_CRFX = 'T' and exists(" 
						+ " select 1 from HYTHD where HYTHD.F_KJQJ = TH.F_KJQJ and HYTHD.F_DJBH = TH.F_DJBH"
						+ " and HYTHD.F_THLX = '0')"	
						+ " LEFT JOIN HYXMCK CK ON HYXMCK.F_XMBH = CK.F_YYXMBH and "
						+ " HYXMCK.F_CLBH = CK.F_CLBH and HYXMCK.F_CKBH = CK.F_YYCKBH and HYXMCK.F_CPBH = CK.F_YYCPBH"
						+ " and HYXMCK.F_CLDJ = CK.F_CLDJ and (CK.F_CRFX = 'C' or CK.F_CRFX = 'D')"		
		       	        + " where HYXMCK.F_XMBH='" + F_XMBH + "' and HYXMCK.F_CLBH = '" + F_CLBH + "'"
		       	        + " and HYXMCK.F_CRFX = 'R' ";
		try {
			conn = JConnection.getInstance(jdbcTemplate.getDataSource().getConnection());
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if(rs != null && rs.next()) {
				sl = rs.getDouble(1) + rs.getDouble(2) - rs.getDouble(3);
			}
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
		return sl;
	}
	
	//--------------------------------------------------------------------------------------------------
	//描述:获取库存数量
	//设计: ES
	//实现: ES
	//修改:
	//--------------------------------------------------------------------------------------------------
	public double getClKcSl(String F_XMBH, String F_CPBH, String F_CLBH, String F_CKBH, double F_CLDJ) {
		JConnection    conn = null;
		JStatement     stmt = null;
		ResultSet        rs = null;
		double           sl = 0;

		String      sql = " select round(sum(HYXMCK.F_CLSL)) as F_RKSL,"
			            + " round(sum(TH.F_CLSL), 2) as F_THSL, round(sum(CK.F_CLSL), 2) as F_CKSL from HYXMCK"
			            + " LEFT JOIN HYXMCK TH ON HYXMCK.F_XMBH = TH.F_YYXMBH and "
						+ " HYXMCK.F_CLBH = TH.F_CLBH and HYXMCK.F_CKBH = TH.F_YYCKBH and HYXMCK.F_CPBH = TH.F_YYCPBH"
						+ " and HYXMCK.F_CLDJ = TH.F_CLDJ and TH.F_CRFX = 'T' and exists(" 
						+ " select 1 from HYTHD where HYTHD.F_KJQJ = TH.F_KJQJ and HYTHD.F_DJBH = TH.F_DJBH"
						+ " and HYTHD.F_THLX = '0')"	
						+ " LEFT JOIN HYXMCK CK ON HYXMCK.F_XMBH = CK.F_YYXMBH and "
						+ " HYXMCK.F_CLBH = CK.F_CLBH and HYXMCK.F_CKBH = CK.F_YYCKBH and HYXMCK.F_CPBH = CK.F_YYCPBH"
						+ " and HYXMCK.F_CLDJ = CK.F_CLDJ and (CK.F_CRFX = 'C' or CK.F_CRFX = 'D')"		
		       	        + " where HYXMCK.F_XMBH='" + F_XMBH + "' and HYXMCK.F_CPBH = '" + F_CPBH + "' and HYXMCK.F_CLBH = '" + F_CLBH + "'"
		       	        + " and HYXMCK.F_CRFX = 'R' "
		       	        + " and HYXMCK.F_CKBH = '" + F_CKBH + "' and HYXMCK.F_CLDJ = " + F_CLDJ;
		try {
			conn = JConnection.getInstance(jdbcTemplate.getDataSource().getConnection());
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if(rs != null && rs.next()) {
				sl = rs.getDouble(1) + rs.getDouble(2) - rs.getDouble(3);
			}
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
		return sl;
	}
	
	//--------------------------------------------------------------------------------------------------
	//描述:获取仓库库存数量
	//设计: ES
	//实现: ES
	//修改:
	//--------------------------------------------------------------------------------------------------
	public double getDeportKcSl(String F_CKBH, String F_CLBH, String F_DWBH, String F_CSBH, double F_CLDJ) {
		JConnection    conn = null;
		JStatement     stmt = null;
		ResultSet        rs = null;
		double       sl = 0;
		String      sql = " select F_CLSL from HYCK"
		       	        + " where F_CKBH='" + F_CKBH + "' and F_CLBH = '" + F_CLBH + "' and F_DWBH = '" + F_DWBH + "'"
		       	        + " and F_CSBH = '" + F_CSBH + "' and F_CLDJ = " + F_CLDJ;
		try {
			conn = JConnection.getInstance(jdbcTemplate.getDataSource().getConnection());
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if(rs != null && rs.next()) {
				sl = rs.getDouble(1);
			}
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
		return sl;
	}
	
	//--------------------------------------------------------------------------------------------------
	//描述:获取领用数量
	//设计: ES
	//实现: ES
	//修改:
	//--------------------------------------------------------------------------------------------------
	public EFDataSet getClLySl(String F_XMBH, String F_CPBH, String F_CLBH, String F_CKBH, double F_CLDJ) {
		//项目申请单已领用数量
		String           sql = "";
		JConnection     conn = null;
		JStatement      stmt = null;
		ResultSet         rs = null;
		EFDataSet         ds = null;
		double          lysl = 0.0;
		double          thsl = 0.0;
		
		sql = " select HYXMCK.F_YYXMBH, HYXMCK.F_CLBH, HYXMCK.F_YYCPBH, MAX(HYCPZD.F_CPMC), "
		    + " round(sum(HYXMCK.F_CLSL)) as F_LYSL, F_SL as F_SQSL, "
		    + " round(sum(TH.F_CLSL), 2) as F_THSL"
		    + " from HYXMCK "
		    + " LEFT JOIN HYCPZD ON HYXMCK.F_YYCPBH = HYCPZD.F_CPBH"
		    + " LEFT JOIN HYXMMX ON HYXMMX.F_XMBH = HYXMCK.F_YYXMBH AND HYXMMX.F_CLBH = HYXMCK.F_CLBH "
		    + " LEFT JOIN HYXMCK TH ON HYXMCK.F_YYXMBH = TH.F_YYXMBH and "
			+ " HYXMCK.F_CLBH = TH.F_CLBH and HYXMCK.F_YYCKBH = TH.F_YYCKBH and HYXMCK.F_YYCPBH = TH.F_YYCPBH"
			+ " and HYXMCK.F_CLDJ = TH.F_CLDJ and TH.F_CRFX = 'T' and exists(" 
			+ " select 1 from HYTHD where HYTHD.F_KJQJ = TH.F_KJQJ and HYTHD.F_DJBH = TH.F_DJBH"
			+ " and HYTHD.F_THLX = '0')"	
			+ " where HYXMCK.F_YYXMBH = '" + F_XMBH + "' and HYXMCK.F_CLBH = '" + F_CLBH 
			+ "' and HYXMCK.F_YYCPBH = '" + F_CPBH + "' and HYXMCK.F_YYCKBH = '" + F_CKBH + "' and (HYXMCK.F_CRFX = 'C' or HYXMCK.F_CRFX = 'D') and"
			+ " exists(select 1 from HYXMMX where HYXMMX.F_XMBH = HYXMCK.F_YYXMBH and HYXMMX.F_CLBH = HYXMCK.F_CLBH)" 
			+ " and HYXMCK.F_CLDJ = " + F_CLDJ
		    + " GROUP BY HYXMCK.F_XMBH, HYXMCK.F_CLBH,HYXMCK.F_CPBH";
		
		try {
			conn = JConnection.getInstance(jdbcTemplate.getDataSource().getConnection());
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			ds = DataSetUtils.resultSet2DataSet(rs, ds);
			for(int i = 0; i < ds.getRowCount(); i++) {
				lysl = ds.getRowSet(i).getNumber("F_LYSL", 0.0).doubleValue();
				thsl = ds.getRowSet(i).getNumber("F_THSL", 0.0).doubleValue();

				ds.getRowSet(i).putNumber("F_LYSL",  lysl - thsl);
			}
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
//		jdbcTemplate.query(sql, new Object[]{F_XMBH, F_CLBH, F_CPBH, F_CKBH}, new RowCallbackHandler(){
//			public void processRow(ResultSet rs) throws SQLException{
//				int[] dataArray = new int[2];				
//				dataArray[0] = rs.getInt(5);  //申请数量
//				dataArray[1] = rs.getInt(6);  //领用数量
//				dataList.add(dataArray);
//			}
//		});
		return ds;
	}
	
	//--------------------------------------------------------------------------------------------------
	//描述:获取申请数量
	//设计: ES
	//实现: ES
	//修改:
	//--------------------------------------------------------------------------------------------------
	public double getClSqSl(String F_XMBH, String F_CLBH) {
		JConnection     conn = null;
		JStatement      stmt = null;
		ResultSet         rs = null;
		double            sl = 0;
		
		String sql = " select F_SL from HYXMMX"
		       	   + " where F_XMBH='" + F_XMBH + "' and F_CLBH = '" + F_CLBH + "'";
		try {
			conn = JConnection.getInstance(jdbcTemplate.getDataSource().getConnection());
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if(rs != null && rs.next()) {
				sl = rs.getDouble(1);
			}			
		} catch (Exception e) {
			e.printStackTrace();
			sl = 0;
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
		return sl;
	}
	
	public int onCheck(String strSql) {
		JConnection    conn = null;
		JStatement     stmt = null;
		ResultSet        rs = null;
		int           count = 0;
		try {
			conn = JConnection.getInstance(jdbcTemplate.getDataSource().getConnection());
			stmt = conn.createStatement();
			rs = stmt.executeQuery(strSql);
			if(rs != null && rs.next()) {
				count = rs.getInt(1);
			}			
		} catch (Exception e) {
			e.printStackTrace();
			count = 0;
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
		return count;
	}
	
	
	//delivery voucher简称DV、warehouse  voucher简称WV
	public String checkDVItemMaterialSave(String F_KJQJ, String F_DJBH) {
		String              errMsg = "";
		EFDataSet        HYDBDMXDS = null;
		EFRowSet         itemRwSet = EFRowSet.getInstance(); 
		try {
			HYDBDMXDS = collectDVFormItem(F_KJQJ, F_DJBH);	
			
			for(int i = 0; i < HYDBDMXDS.getRowCount(); i++) {
				itemRwSet = HYDBDMXDS.getRowSet(i);

				errMsg = checkXMWG(itemRwSet.getString("F_YYXMBH", ""), itemRwSet.getString("F_SSXMMC", ""));
				if(!errMsg.trim().equals("")) return errMsg;
				
				errMsg = checkStockNumber(itemRwSet);
				if(!errMsg.trim().equals("")) return errMsg;
				
				errMsg = checkApplyForNumber(itemRwSet);
				if(!errMsg.trim().equals("")) return errMsg;
			}
		} catch(Exception ce) {
			ce.printStackTrace();
		}
		return errMsg;
	}
	
	//汇总出库单明细
	public EFDataSet collectDVFormItem(String F_KJQJ, String F_DJBH) {
		String      strSql = "";
		JConnection   conn = null;
		JStatement    stmt = null;
		ResultSet       rs = null;
		EFDataSet   itemDS = EFDataSet.getInstance();
		
		try {
			strSql = " select F_SSXMBH, F_SSCPBH, F_YYXMBH, F_YYCPBH, HYCKDMX.F_DWBH, HYCKDMX.F_CSBH, F_SSCKBH, F_YYCKBH, HYCKDMX.F_CLBH, HYCKDMX.F_CLDJ, "
				   + " MAX(SSXM.F_XMMC) as F_SSXMMC, MAX(SSCP.F_CPMC) as F_SSCPMC, MAX(SSCK.F_CKMC) as F_SSCKMC,"
				   + " MAX(YYXM.F_XMMC) as F_YYXMMC, MAX(YYCP.F_CPMC) as F_YYCPMC, MAX(YYCK.F_CKMC) as F_YYCKMC,"
				   + " MAX(HYDWZD.F_DWMC) as F_DWMC, MAX(HYCSZD.F_CSMC) as F_CSMC, MAX(HYCLZD.F_CLMC) as F_CLMC,"
				   + " F_CLDJ, round(sum(HYCKDMX.F_CKSL),2) as F_CKSL"
				   + " from HYCKDMX "
			       + " LEFT JOIN HYCLZD ON HYCKDMX.F_CLBH = HYCLZD.F_CLBH"
			       + " LEFT JOIN HYDWZD ON HYCKDMX.F_DWBH = HYDWZD.F_DWBH"
			       + " LEFT JOIN HYCSZD ON HYCKDMX.F_CSBH = HYCSZD.F_CSBH"
			       + " LEFT JOIN HYCPZD SSCP ON HYCKDMX.F_SSCPBH = SSCP.F_CPBH"
			       + " LEFT JOIN HYCPZD YYCP ON HYCKDMX.F_YYCPBH = YYCP.F_CPBH"
			       + " LEFT JOIN HYXMZD SSXM ON HYCKDMX.F_SSXMBH = SSXM.F_XMBH"
			       + " LEFT JOIN HYXMZD YYXM ON HYCKDMX.F_YYXMBH = YYXM.F_XMBH"
			       + " LEFT JOIN HYCKZD SSCK ON HYCKDMX.F_SSCKBH = SSCK.F_CKBH"
			       + " LEFT JOIN HYCKZD YYCK ON HYCKDMX.F_YYCKBH = YYCK.F_CKBH"
			       + " where F_KJQJ = '" + F_KJQJ + "' and F_DJBH = '" + F_DJBH + "'"
			       + " group by F_SSXMBH, F_SSCPBH, F_YYXMBH, F_YYCPBH, F_DWBH, F_CSBH, F_SSCKBH, F_YYCKBH, F_CLBH, F_CLDJ";
			try {
				conn = JConnection.getInstance(jdbcTemplate.getDataSource().getConnection());
				stmt = conn.createStatement();
				rs = stmt.executeQuery(strSql);
				itemDS = DataSetUtils.resultSet2DataSet(rs, itemDS);
			} catch (Exception e) {
				e.printStackTrace();
				itemDS = EFDataSet.getInstance();
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
		} catch(Exception ce) {
			ce.printStackTrace();
		}
		return itemDS;
	}
	
	//--------------------------------------------------------------------------------------------------
	//描述:检查出库数量是否大于库存数量
	//设计: ES
	//实现: ES
	//修改:
	//--------------------------------------------------------------------------------------------------
	public String checkStockNumber(EFRowSet rowset) {
		double                kcsl = 0;
		double                cksl = rowset.getNumber("F_CKSL", 0.0).doubleValue();	
		String              errMsg = "";
		String              F_XMBH = rowset.getString("F_SSXMBH", "");
		String              F_CPBH = rowset.getString("F_SSCPBH", "");
		String              F_CKBH = rowset.getString("F_SSCKBH", "");
		String              F_CLBH = rowset.getString("F_CLBH", "");		
		double              F_CLDJ = rowset.getNumber("F_CLDJ", 0.0).doubleValue();
		
		kcsl = getClKcSl(F_XMBH, F_CPBH, F_CLBH, F_CKBH, F_CLDJ);
		if(cksl > kcsl) {
			errMsg = "项目【" + rowset.getString("F_SSXMMC", "") + "】-产品【"
		           + rowset.getString("F_SSXMMC", "") + "】-材料【" + rowset.getString("F_CLMC", "") 
		           + "】出库数量大于库存数量，请重新维护出库数量并保存！库存数量为：" + kcsl + "；出库数量为：" + cksl + "<br>";
		}
		return errMsg;
	}
	
	//--------------------------------------------------------------------------------------------------
	//描述:批量检查入库数量是否大于申请数量
	//设计: ES
	//实现: ES
	//修改:
	//--------------------------------------------------------------------------------------------------
	public String batchCheckMaterialStockApplyNumber(EFDataSet dataset) {
		String              errMsg = "";		
		String              tempMsg = "";
		EFRowSet           rowset = null;
		
		for(int i = 0; i < dataset.getRowCount(); i++) {
			rowset = dataset.getRowSet(i);
			tempMsg = checkMaterialStockApplyNumber(rowset);
			if(!tempMsg.trim().equals("")) {
				errMsg = errMsg + "<br>第" + (i + 1) + "行：" + tempMsg;
			}
		}
		return errMsg;
	}
	
	//--------------------------------------------------------------------------------------------------
	//描述:检查入库数量是否大于申请数量
	//设计: ES
	//实现: ES
	//修改:
	//--------------------------------------------------------------------------------------------------
	public String checkMaterialStockApplyNumber(EFDataSet dataset) {
		double                sqsl = 0;
		double                rksl = 0.0;	
		double               yrksl = 0.0;	
		String              errMsg = "";
		String              F_XMBH = "";
		String              F_CLBH = "";		
		EFRowSet            rowset = null;
		String              F_XMMC = "";
		String              F_CLMC = "";
		String              strSql = "";
		ResultSet               rs = null;
		JConnection           conn = null;
		JStatement            stmt = null;
		
		for(int i = 0; i < dataset.getRowCount(); i++) {
			rowset = dataset.getRowSet(i);
			F_XMBH = rowset.getString("F_XMBH", "");
			F_CLBH = rowset.getString("F_CLBH", "");
			rksl = rowset.getNumber("F_RKSL", 0.0).doubleValue();	
			yrksl = getClKcSl(F_XMBH, F_CLBH);
			sqsl = getClSqSl(F_XMBH, F_CLBH);
			if(rksl > sqsl - yrksl) {
				try {
					conn = JConnection.getInstance(jdbcTemplate.getDataSource().getConnection());
					stmt = conn.createStatement();
					if(F_XMMC.equals("")) {
						strSql = "select * from HYXMZD where F_XMBH = '" + F_XMBH + "'";
						rs = stmt.executeQuery(strSql);
						if(rs != null && rs.next()) F_XMMC = rs.getString("F_XMMC");
					}
					
					if(F_CLMC.equals("")) {
						strSql = "select * from HYCLZD where F_CLBH = '" + F_CLBH + "'";
						rs = stmt.executeQuery(strSql);
						if(rs != null && rs.next()) F_CLMC = rs.getString("F_CLMC");
					}
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
				errMsg += "第" + (i + 1) + "行数据保存失败：项目【" + F_XMMC + "】-材料【" + F_CLMC
			           + "】入库数量大于申请数量，请重新维护入库数量并保存！申请数量为：" + sqsl + "；已入库数量为：" + yrksl + "；本次入库数量为：" + rksl + "<br>";
			}
		}
		
		return errMsg;
	}
	
	//--------------------------------------------------------------------------------------------------
	//描述:检查入库数量是否大于申请数量
	//设计: ES
	//实现: ES
	//修改:
	//--------------------------------------------------------------------------------------------------
	public String checkMaterialStockApplyNumber(EFRowSet rowset) {
		double                sqsl = 0;
		double               yrksl = 0.0;	
		double                rksl = rowset.getNumber("F_RKSL", 0.0).doubleValue();	
		String              errMsg = "";
		String              F_XMBH = rowset.getString("F_XMBH", "");
		String              F_CLBH = rowset.getString("F_CLBH", "");		
		
		sqsl = getClSqSl(F_XMBH, F_CLBH);
		yrksl = getClKcSl(F_XMBH, F_CLBH);
		if(rksl > sqsl - yrksl) {
			errMsg = "项目【" + rowset.getString("F_XMMC", "") + "】-材料【" + rowset.getString("F_CLMC", "") 
		           + "】入库数量大于申请数量，请重新维护入库数量并保存！申请数量为：" + sqsl + "；已入库数量为：" + yrksl + "；本次入库数量为：" + rksl + "<br>";
		}
		return errMsg;
	}
	
	//--------------------------------------------------------------------------------------------------
	//描述:检查出库数量是否大于项目申请单材料数量
	//设计: ES
	//实现: ES
	//修改:
	//--------------------------------------------------------------------------------------------------
	public String checkApplyForNumber(EFRowSet rowset) {
		double                sqsl = 0;
		double                lysl = 0;
		EFDataSet           lyslDS = null;
		EFRowSet            lyslRS = null;
		String              errMsg = "";
		String              F_XMBH = rowset.getString("F_YYXMBH", "");
		String              F_CPBH = rowset.getString("F_YYCPBH", "");
		String              F_CKBH = rowset.getString("F_YYCKBH", "");
		String              F_CLBH = rowset.getString("F_CLBH", "");		
		double              F_CLDJ = rowset.getNumber("F_CLDJ", 0.0).doubleValue();
		
		lyslDS = getClLySl(F_XMBH, F_CPBH, F_CLBH, F_CKBH, F_CLDJ);
		
		if(lyslDS.getRowCount() > 0) {
			lyslRS = lyslDS.getRowSet(0);
			sqsl = lyslRS.getNumber("F_SQSL", 0).doubleValue();
			lysl = lyslRS.getNumber("F_LYSL", 0).doubleValue();
			if(lysl > sqsl) {
				errMsg = "项目【" + rowset.getString("F_SSXMMC", "") + "】-产品【"
			  	       + rowset.getString("F_SSXMMC", "") + "】-材料【" + rowset.getString("F_CLMC", "") 
			           + "】出库数量大于材料申请单申请数量，请重新维护出库数量并保存！申请数量为：" 
			           + lyslRS.getNumber("F_LYSL", 0) + "；已领用数量为：" + lyslRS.getNumber("F_SQSL", 0) + "<br>";
			}
		}	
		return errMsg;
	}
	
	//汇总出库单明细
	public EFDataSet loadTransfersFormItem(String F_XMBH, String F_CLBH, String F_CPBH, String F_CKBH) {
		//项目申请单已领用数量
		String          sql = "";
		JConnection    conn = null;
		JStatement     stmt = null;
		ResultSet        rs = null;
		EFDataSet        ds = null;
		double         lysl = 0.0;
		double         thsl = 0.0;
		double         rksl = 0.0;
		
		sql = " select HYXMCK.F_YYXMBH, HYXMCK.F_CLBH, HYXMCK.F_YYCPBH, MAX(HYCPZD.F_CPMC), "
		    + " round(sum(HYXMCK.F_CLSL)) * -1 as F_LYSL, F_SL as F_SQSL,"
		    + " round(sum(TH.F_CLSL), 2) as F_THSL,"
		    + " round(sum(RK.F_CLSL), 2) as F_RKSL"
		    + " from HYXMCK "
		    + " LEFT JOIN HYCPZD ON HYXMCK.F_YYCPBH = HYCPZD.F_CPBH"
		    + " LEFT JOIN HYXMMX ON HYXMMX.F_XMBH = HYXMCK.F_YYXMBH AND HYXMMX.F_CLBH = HYXMCK.F_CLBH "
		    + " LEFT JOIN HYXMCK TH ON HYXMCK.F_YYXMBH = TH.F_YYXMBH and "
			+ " HYXMCK.F_CLBH = TH.F_CLBH and HYXMCK.F_YYCKBH = TH.F_YYCKBH and HYXMCK.F_YYCPBH = TH.F_YYCPBH"
			+ " and HYXMCK.F_CLDJ = TH.F_CLDJ and TH.F_CRFX = 'T' and exists(" 
			+ " select 1 from HYTHD where HYTHD.F_KJQJ = TH.F_KJQJ and HYTHD.F_DJBH = TH.F_DJBH"
			+ " and HYTHD.F_THLX = '0')"	
			+ " LEFT JOIN HYXMCK RK ON HYXMCK.F_YYXMBH = RK.F_XMBH and "
			+ " HYXMCK.F_CLBH = RK.F_CLBH and HYXMCK.F_YYCKBH = RK.F_CKBH and HYXMCK.F_YYCPBH = RK.F_CPBH"
			+ " and HYXMCK.F_CLDJ = RK.F_CLDJ and RK.F_CRFX = 'R'"	
			+ " where HYXMCK.F_YYXMBH = '" + F_XMBH + "' and HYXMCK.F_CLBH = '" + F_CLBH 
			+ "' and HYXMCK.F_YYCPBH = '" + F_CPBH + "' and HYXMCK.F_YYCKBH = '" + F_CKBH + "' and (F_CRFX = 'C' or F_CRFX = 'D') and"
			+ " exists(select 1 from HYXMMX where HYXMMX.F_XMBH = HYXMCK.F_YYXMBH and HYXMMX.F_CLBH = HYXMCK.F_CLBH)" 
		    + " GROUP BY HYXMCK.F_XMBH, HYXMCK.F_CLBH,HYXMCK.F_CPBH";
		
		try {
			conn = JConnection.getInstance(jdbcTemplate.getDataSource().getConnection());
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			ds = DataSetUtils.resultSet2DataSet(rs, ds);
			for(int i = 0; i < ds.getRowCount(); i++) {
				lysl = ds.getRowSet(i).getNumber("F_LYSL", 0.0).doubleValue();
				thsl = ds.getRowSet(i).getNumber("F_THSL", 0.0).doubleValue();
				rksl = ds.getRowSet(i).getNumber("F_RKSL", 0.0).doubleValue();
				ds.getRowSet(i).putNumber("F_LYSL", lysl + thsl + rksl);
			}
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
	
	//delivery voucher简称DV、warehouse  voucher简称WV
	public String checkTransfersItemMaterialSave(String F_KJQJ, String F_DJBH) {
		String              errMsg = "";
		EFDataSet        HYDBDMXDS = null;
		EFRowSet         itemRwSet = EFRowSet.getInstance(); 
		try {
			HYDBDMXDS = collectTransfersFormItem(F_KJQJ, F_DJBH);	
			
			for(int i = 0; i < HYDBDMXDS.getRowCount(); i++) {
				itemRwSet = HYDBDMXDS.getRowSet(i);

				errMsg = checkXMWG(itemRwSet.getString("F_XMBH", ""), itemRwSet.getString("F_XMMC", ""));
				if(!errMsg.trim().equals("")) return errMsg;
				
				errMsg = checkTransfersStockNumber(itemRwSet);
				if(!errMsg.trim().equals("")) return errMsg;
				
				errMsg = checkTransfersApplyForNumber(itemRwSet);
				if(!errMsg.trim().equals("")) return errMsg;
			}
		} catch(Exception ce) {
			ce.printStackTrace();
		}
		return errMsg;
	}
	
	//汇总调拨单明细
	public EFDataSet collectTransfersFormItem(String F_KJQJ, String F_DJBH) {
		String       strSql = "";
		JConnection    conn = null;
		JStatement     stmt = null;
		ResultSet        rs = null;
		EFDataSet    itemDS = EFDataSet.getInstance();
		
		try {
			strSql = " select HYDBDMX.F_XMBH, HYDBDMX.F_CPBH, HYDBDMX.F_DWBH, HYDBDMX.F_CSBH, HYDBDMX.F_CKBH, HYDBDMX.F_CLBH, HYDBDMX.F_CLDJ, "
				   + " MAX(XM.F_XMMC) as F_XMMC, MAX(CP.F_CPMC) as F_CPMC, MAX(CK.F_CKMC) as F_CKMC,"
				   + " MAX(HYDWZD.F_DWMC) as F_DWMC, MAX(HYCSZD.F_CSMC) as F_CSMC, MAX(HYCLZD.F_CLMC) as F_CLMC,"
				   + " round(sum(HYDBDMX.F_CLSL),2) as F_DBSL"
				   + " from HYDBDMX "
			       + " LEFT JOIN HYCLZD ON HYDBDMX.F_CLBH = HYCLZD.F_CLBH"
			       + " LEFT JOIN HYDWZD ON HYDBDMX.F_DWBH = HYDWZD.F_DWBH"
			       + " LEFT JOIN HYCSZD ON HYDBDMX.F_CSBH = HYCSZD.F_CSBH"
			       + " LEFT JOIN HYCPZD CP ON HYDBDMX.F_CPBH = CP.F_CPBH"
			       + " LEFT JOIN HYXMZD XM ON HYDBDMX.F_XMBH = XM.F_XMBH"
			       + " LEFT JOIN HYCKZD CK ON HYDBDMX.F_CKBH = CK.F_CKBH"
			       + " where F_KJQJ = '" + F_KJQJ + "' and F_DJBH = '" + F_DJBH + "'"
			       + " group by HYDBDMX.F_XMBH, HYDBDMX.F_CPBH,HYDBDMX.F_DWBH, HYDBDMX.F_CSBH, HYDBDMX.F_CKBH, HYDBDMX.F_CLBH, HYDBDMX.F_CLDJ";
			try {
				conn = JConnection.getInstance(jdbcTemplate.getDataSource().getConnection());
				stmt = conn.createStatement();
				rs = stmt.executeQuery(strSql);
				itemDS = DataSetUtils.resultSet2DataSet(rs, itemDS);
			} catch (Exception e) {
				e.printStackTrace();
				itemDS = EFDataSet.getInstance();
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
		} catch(Exception ce) {
			ce.printStackTrace();
		}
		return itemDS;
	}
	
	//--------------------------------------------------------------------------------------------------
	//描述:检查出库数量是否大于库存数量
	//设计: ES
	//实现: ES
	//修改:
	//--------------------------------------------------------------------------------------------------
	public String checkTransfersStockNumber(EFRowSet rowset) {
		double                kcsl = 0;
		double                dbsl = rowset.getNumber("F_DBSL", 0.0).doubleValue();	
		String              errMsg = "";
		String              F_DWBH = rowset.getString("F_DWBH", "");
		String              F_CSBH = rowset.getString("F_CSBH", "");
		String              F_CKBH = rowset.getString("F_CKBH", "");
		String              F_CLBH = rowset.getString("F_CLBH", "");		
		double              F_CLDJ = rowset.getNumber("F_CLDJ", 0.0).doubleValue();
		
		kcsl = getDeportKcSl(F_CKBH, F_CLBH, F_DWBH, F_CSBH, F_CLDJ);
		if(dbsl > kcsl) {
			errMsg = "材料【" + rowset.getString("F_CLMC", "") + "】-供应商【"
		           + rowset.getString("F_DWMC", "") + "】-厂商【" + rowset.getString("F_CSMC", "") 
		           + "】-材料单价【" + rowset.getObject("F_CLDJ", "") + "】调拨数量大于库存数量，请重新维护出库数量并保存！库存数量为：" 
		           + kcsl + "；调拨数量为：" + dbsl + "<br>";
		}
		return errMsg;
	}
	
	//--------------------------------------------------------------------------------------------------
	//描述:检查出库数量是否大于项目申请单材料数量
	//设计: ES
	//实现: ES
	//修改:
	//--------------------------------------------------------------------------------------------------
	public String checkTransfersApplyForNumber(EFRowSet rowset) {
		double                sqsl = 0;
		double                lysl = 0;
		EFDataSet           lyslDS = null;
		EFRowSet            lyslRS = null;
		String              errMsg = "";
		String              F_XMBH = rowset.getString("F_XMBH", "");
		String              F_CPBH = rowset.getString("F_CPBH", "");
		String              F_CKBH = rowset.getString("F_CKBH", "");
		String              F_CLBH = rowset.getString("F_CLBH", "");		
		double              F_CLDJ = rowset.getNumber("F_CLDJ", 0.0).doubleValue();
		
		lyslDS = getClLySl(F_XMBH, F_CPBH, F_CLBH, F_CKBH, F_CLDJ);
		
		if(lyslDS.getRowCount() > 0) {
			lyslRS = lyslDS.getRowSet(0);
			sqsl = lyslRS.getNumber("F_SQSL", 0).doubleValue();
			lysl = lyslRS.getNumber("F_LYSL", 0).doubleValue();
			if(lysl > sqsl) {
				errMsg = "项目【" + rowset.getString("F_XMMC", "") + "】-产品【"
			  	       + rowset.getString("F_XMMC", "") + "】-材料【" + rowset.getString("F_CLMC", "") 
			           + "】出库数量大于材料申请单申请数量，请重新维护出库数量并保存！申请数量为：" 
			           + lyslRS.getNumber("F_LYSL", 0) + "；已领用数量为：" + lyslRS.getNumber("F_SQSL", 0) + "<br>";
			}
		}	
		return errMsg;
	}
	
	//--------------------------------------------------------------------------------------------------
	//描述:检查项目是否完工
	//设计: ES
	//实现: ES
	//修改:
	//--------------------------------------------------------------------------------------------------
	public String checkXMWG(String F_XMBH, String F_XMMC) {
		HYXMZD     xmzd = hyxmzdMapper.load(F_XMBH);
		String   errMsg = "";
		if(xmzd.getF_XMZT().equals("1")) {
			errMsg = "项目【" + F_XMMC + "】已完工，不允许再做业务！<br>";
		}
		return errMsg;
	}
	
	
	
	//--------------------------------------------------------------------------------------------------
	//描述:检查调拨数量是否大于仓库库存数量
	//设计: ES
	//实现: ES
	//修改:
	//--------------------------------------------------------------------------------------------------
	public String checkTransfersNumber(EFRowSet rowset) {
		double                kcsl = 0;
		double                dbsl = rowset.getNumber("F_DBSL", 0.0).doubleValue();	
		String              errMsg = "";
		String              F_CLBH = rowset.getString("F_CLBH", "");
		String              F_DWBH = rowset.getString("F_DWBH", "");
		String              F_CSBH = rowset.getString("F_CSBH", "");
		String              F_CKBH = rowset.getString("F_CKBH", "");		
		double              F_CLDJ = rowset.getNumber("F_CLDJ", 0.0).doubleValue();
		
		kcsl = getDeportKcSl(F_CKBH, F_CLBH, F_DWBH, F_CSBH, F_CLDJ);
		if(dbsl > kcsl) {
			errMsg = "材料【" + rowset.getString("F_CLMC", "") + "】-供应商【"
		           + rowset.getString("F_DWMC", "") + "】-厂商【" + rowset.getString("F_CSMC", "") 
		           + "】-材料单价【" + rowset.getObject("F_CLDJ", "") + "】调拨数量大于库存数量，请重新维护出库数量并保存！库存数量为：" + kcsl + "；调拨数量为：" + dbsl + "<br>";
		}
		return errMsg;
	}
	
	public HYTHD createProductReturnFormByDVHead(String F_KJQJ, String F_GUID, String EXT_BIZ_KJQJ, String EXT_BIZ_DJBH) {
		String      strSql = "";
		Connection    conn = null;
		Statement     stmt = null;
		ResultSet       rs = null;
		EFDataSet  dataset = EFDataSet.getInstance();
		EFRowSet    rowset = null;
		HYTHD           po = null;
		
		strSql = " select F_KJQJ as EXT_BIZ_KJQJ,F_DATE,F_DJBH as EXT_BIZ_DJBH,F_BZ,"
			   + " HYCKD.F_XMBH as F_XMBH,HYXMZD.F_XMMC as F_XMMC,"
			   + " HYCKD.F_CKBH as F_CKBH,HYCKZD.F_CKMC as F_CKMC,"
			   + " HYCKD.F_LLRID as F_THRID,LL.F_ZGMC as F_THRMC,"
			   + " HYCKD.F_BZRID as F_BZRID,ZD.USER_NAME as F_BZRMC,"
			   + " HYCKD.F_CRDATE,HYCKD.F_CHDATE,'0' as F_DJZT, "
			   + " '" + F_KJQJ + "' as F_KJQJ, '" + F_GUID + "' as F_GUID, '' as F_DJBH "
			   + " from HYCKD "
			   + " LEFT JOIN HYXMZD ON HYCKD.F_XMBH = HYXMZD.F_XMBH "
			   + " LEFT JOIN HYCKZD ON HYCKD.F_CKBH = HYCKZD.F_CKBH "
			   + " LEFT JOIN BSUSER ZD ON HYCKD.F_BZRID = ZD.USER_ID "
			   + " LEFT JOIN HYZGZD LL ON HYCKD.F_LLRID = LL.F_ZGBH "
			   + " where F_KJQJ = '" + EXT_BIZ_KJQJ + "' and F_DJBH = '" + EXT_BIZ_DJBH + "'";
		
		Debug.PrintlnMessageToSystem("searchHYTHD|||||" + strSql);
		
		try {
			conn = (Connection) jdbcTemplate.getDataSource().getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(strSql);
			dataset = DataSetUtils.resultSet2DataSet(rs, dataset);
			if(dataset.getRowCount() > 0) {
				rowset = dataset.getRowSet(0);
				po = (HYTHD)EMPReflectUtil.rowtsetReflectClass(HYTHD.class.getName(), rowset);
			} else {
				rowset = EFRowSet.getInstance();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			dataset = EFDataSet.getInstance();
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
		
		return po;
	}
	
	//--------------------------------------------------------------------------------------------------
	//描述:根据出库单据生成退货单据
	//设计: ES
	//实现: ES
	//修改:
	//--------------------------------------------------------------------------------------------------
	public EFDataSet createProductReturnFormByDVItem(String F_KJQJ, String F_GUID, String F_FLGUID, String EXT_BIZ_KJQJ, String EXT_BIZ_DJBH) {
		String         strSql = "";
		Connection       conn = null;
		Statement        stmt = null;
		ResultSet          rs = null;
		EFDataSet     dataset = EFDataSet.getInstance();
		
		strSql = " select HYXMCK.F_KJQJ as EXT_BIZ_KJQJ,HYXMCK.F_DJBH as EXT_BIZ_DJBH,HYXMCK.F_FLBH as EXT_BIZ_FLBH, "
			   + " HYXMCK.F_CLBH as F_CLBH, '" + F_KJQJ + "' as F_KJQJ, "
			   + " '" + F_GUID + "' as F_GUID, '" + F_FLGUID + "' as F_FLGUID, HYXMCK.F_FLBH as F_FLBH, "
			   + " HYCLZD.F_CLMC as F_CLMC,HYXMCK.F_XMBH as F_SSXMBH,HYXMZD.F_XMMC as F_SSXMMC,HYXMCK.F_DWBH as F_DWBH,HYDWZD.F_DWMC as F_DWMC, "
			   + " HYXMCK.F_CPBH as F_SSCPBH,HYCPZD.F_CPMC as F_SSCPMC,HYXMCK.F_YYXMBH as F_YYXMBH,YYXM.F_XMMC as F_YYXMMC,"
			   + " HYXMCK.F_YYCPBH as F_YYCPBH,YYCP.F_CPMC as F_YYCPMC,HYXMCK.F_CLDJ as F_CLDJ,"
			   + " HYXMCK.F_CSBH as F_CSBH,HYCSZD.F_CSMC as F_CSMC,HYXMCK.F_CLDJ as F_CLDJ,"
			   + " HYXMCK.F_CLSL as F_CKSL, IFNULL(round(sum(TH.F_CLSL), 2), 0.0) as F_THSL, 0 as F_BCSL, HYXMCK.F_CHDATE"
			   + " from HYXMCK "
			   + " LEFT JOIN HYCLZD ON HYXMCK.F_CLBH = HYCLZD.F_CLBH "
			   + " LEFT JOIN HYXMZD ON HYXMCK.F_XMBH = HYXMZD.F_XMBH "
			   + " LEFT JOIN HYCPZD ON HYXMCK.F_CPBH = HYCPZD.F_CPBH "
			   + " LEFT JOIN HYDWZD ON HYXMCK.F_DWBH = HYDWZD.F_DWBH "
			   + " LEFT JOIN HYCSZD ON HYXMCK.F_CSBH = HYCSZD.F_CSBH "
			   + " LEFT JOIN HYXMZD YYXM ON HYXMCK.F_XMBH = YYXM.F_XMBH "
			   + " LEFT JOIN HYCPZD YYCP ON HYXMCK.F_CPBH = YYCP.F_CPBH "
			   + " LEFT JOIN HYXMCK TH ON HYXMCK.F_KJQJ = TH.EXT_BIZ_KJQJ and HYXMCK.F_DJBH = TH.EXT_BIZ_DJBH and "
			   + " HYXMCK.F_FLBH = TH.EXT_BIZ_FLBH and HYXMCK.F_CLDJ = TH.F_CLDJ and TH.F_CRFX = 'T'"			   
			   + " where HYXMCK.F_KJQJ = '" + EXT_BIZ_KJQJ + "' and HYXMCK.F_DJBH = '" + EXT_BIZ_DJBH + "'"
			   + " and HYXMCK.F_CRFX = 'C'"
			   + " group by HYXMCK.F_KJQJ,HYXMCK.F_DJBH,HYXMCK.F_FLBH,"
			   + " HYXMCK.F_CLBH,HYXMCK.F_XMBH,HYXMCK.F_DWBH,HYXMCK.F_CPBH,"
			   + " HYXMCK.F_YYXMBH, HYXMCK.F_YYCPBH,HYXMCK.F_CLDJ"
			   + " order by HYXMCK.F_FLBH";
		
		Debug.PrintlnMessageToSystem("createProductReturnFormByDV SQL|||||" + strSql);
		
		try {
			conn = (Connection) jdbcTemplate.getDataSource().getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(strSql);
			dataset = DataSetUtils.resultSet2DataSet(rs, dataset);
		} catch (Exception e) {
			e.printStackTrace();
			dataset = EFDataSet.getInstance();
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
		return dataset;
	}
	
	//--------------------------------------------------------------------------------------------------
	//描述:根据出库单据生成退货单据
	//设计: ES
	//实现: ES
	//修改:
	//--------------------------------------------------------------------------------------------------
	public EFDataSet loadDVItem(String F_KJQJ, String F_GUID, String F_FLGUID, String EXT_BIZ_KJQJ, String EXT_BIZ_DJBH) {
		String         strSql = "";
		Connection       conn = null;
		Statement        stmt = null;
		ResultSet          rs = null;
		EFDataSet     dataset = EFDataSet.getInstance();
		EFRowSet       rowset = null;
		java.util.Date   date = null;
		long       thDateLong;
		
		strSql = " select HYXMCK.F_KJQJ as EXT_BIZ_KJQJ,HYXMCK.F_DJBH as EXT_BIZ_DJBH,HYXMCK.F_FLBH as EXT_BIZ_FLBH, "
			   + " HYXMCK.F_CLBH as F_CLBH, '" + F_KJQJ + "' as F_KJQJ, "
			   + " '" + F_GUID + "' as F_GUID, '" + F_FLGUID + "' as F_FLGUID, HYXMCK.F_FLBH as F_FLBH, "
			   + " HYCLZD.F_CLMC as F_CLMC,HYXMCK.F_XMBH as F_SSXMBH,HYXMZD.F_XMMC as F_SSXMMC,HYXMCK.F_DWBH as F_DWBH,HYDWZD.F_DWMC as F_DWMC, "
			   + " HYXMCK.F_CPBH as F_SSCPBH,HYCPZD.F_CPMC as F_SSCPMC,HYXMCK.F_YYXMBH as F_YYXMBH,YYXM.F_XMMC as F_YYXMMC,"
			   + " HYXMCK.F_YYCPBH as F_YYCPBH,YYCP.F_CPMC as F_YYCPMC,HYXMCK.F_CLDJ as F_CLDJ,"
			   + " HYXMCK.F_CSBH as F_CSBH,HYCSZD.F_CSMC as F_CSMC,HYXMCK.F_CLDJ as F_CLDJ,"
			   + " HYXMCK.F_CLSL as F_CKSL, IFNULL(round(sum(TH.F_CLSL), 2), 0.0) as F_THSL, 0 as F_BCSL, HYXMCK.F_CHDATE"
			   + " from HYDBDMX "			  		   
			   + " where F_KJQJ = '" + EXT_BIZ_KJQJ + "' and F_DJBH = '" + EXT_BIZ_DJBH + "'"
			   + " order by F_FLBH";
		
		Debug.PrintlnMessageToSystem("createProductReturnFormByDV SQL|||||" + strSql);
		
		try {
			conn = (Connection) jdbcTemplate.getDataSource().getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(strSql);
			dataset = DataSetUtils.resultSet2DataSet(rs, dataset);
			for(int i = 0; i < dataset.getRowCount(); i++) {
				rowset = dataset.getRowSet(i);
				thDateLong = rowset.getNumber("F_CHDATE", (new java.util.Date()).getTime()).longValue();
				date = new java.util.Date(thDateLong);
				rowset.putObject("F_CHDATE", date);
			}
		} catch (Exception e) {
			e.printStackTrace();
			dataset = EFDataSet.getInstance();
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
		return dataset;
	}
	
	public void doCompleteProject(String F_XMBH, String F_WCR) {
		boolean                 bCommit = false;
		boolean                 bExists = false;
		Connection                 conn = null;
		Statement                  stmt = null;
		EFDataSet      materialMaterial = null;
		EFRowSet                 rowset = null;
		String                   strSql = "";
		String                   F_CLBH = "";
		String                   F_DWBH = "";
		String                   F_CSBH = "";
		String                   F_CKBH = "";
		double                   F_CLDJ = 0.0;
		double                   F_CLSL = 0.0;
		List<String>            sqlList = new ArrayList<String>();
		SimpleDateFormat            sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); // yyyyMMdd
		java.util.Date               dd = new java.util.Date();
		
		try {
			conn = jdbcTemplate.getDataSource().getConnection();
			bCommit = conn.getAutoCommit();
			conn.setAutoCommit(false);
			stmt = conn.createStatement();
			materialMaterial = projectSurplusMaterial(stmt, F_XMBH);
			for(int i = 0; i < materialMaterial.getRowCount(); i++) {
				rowset = materialMaterial.getRowSet(i);
				F_CLBH = rowset.getString("F_CLBH", "");
				F_DWBH = rowset.getString("F_DWBH", "");
				F_CSBH = rowset.getString("F_CSBH", "");
				F_CKBH = rowset.getString("F_CKBH", "");
				F_CLDJ = rowset.getNumber("F_CLDJ", 0.0).doubleValue();
				F_CLSL = rowset.getNumber("F_LYSL", 0.0).doubleValue();

				if(F_CLSL <= 0) continue;
				
				bExists = checkMaterialExist(stmt, F_CLBH, F_DWBH, F_CSBH, F_CKBH, F_CLDJ);
				if(bExists) {
					strSql = "update HYCK set F_CLSL = F_CLSL + " + F_CLSL + " where "
					       + " F_CLBH = '" + F_CLBH + "' and F_DWBH = '" + F_DWBH + "' and "
					       + " and F_CSBH = '" + F_CSBH + "' and F_CKBH = '" + F_CKBH + "' and "
					       + " and F_CLDJ = " + F_CLDJ;
					sqlList.add(strSql);
				} else {
					strSql = "insert into HYCK(F_CLBH,F_DWBH,F_CSBH,F_CKBH,F_CLDJ,F_CLSL) values('"
						   + F_CLBH + "','" + F_DWBH + "','" + F_CSBH + "','" + F_CKBH + "',"
						   + F_CLDJ + "," + F_CLSL + ")";
					sqlList.add(strSql);
				}
			}
			strSql = "update HYXMZD set F_WCRBH = '" + F_WCR + "', F_XMZT = '1', F_WGSJ = '" + sdf.format(dd) + "', F_WCRMC = '"
		           + getUserName(stmt, F_WCR) + "' where F_XMBH = '" + F_XMBH + "'";
			sqlList.add(strSql);
			
			if(sqlList.size() > 0) {
				stmt.clearBatch();
				for(int i = 0; i < sqlList.size(); i++) {
					Debug.PrintlnMessageToSystem("xmwg batch sql|||" + sqlList.get(i).toString());
					stmt.addBatch(sqlList.get(i).toString());
				}
				stmt.executeBatch();
			}
			conn.commit();
		} catch(Exception ce) {
			try {
				conn.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			ce.printStackTrace();
		} finally {
			
			try {
				conn.setAutoCommit(bCommit);
				JdbcUtils.closeStatement(stmt);
				stmt = null;
				DataSourceUtils.releaseConnection(conn, jdbcTemplate.getDataSource());
			} catch(Exception ce) {
				ce.printStackTrace();
			}
		}
	}
	
	//--------------------------------------------------------------------------------------------------
	//描述:项目完工时获取需要结转的材料
	//设计: ES
	//实现: ES
	//修改:
	//--------------------------------------------------------------------------------------------------
	private EFDataSet projectSurplusMaterial(Statement stmt, String F_XMBH) {		
		ResultSet        rs = null;
		EFDataSet        ds = EFDataSet.getInstance();
		EFRowSet     rowset = null;
		String          sql = "";
		double         rksl = 0.0;
		double         thsl = 0.0;
		double         cksl = 0.0;

		//库存剩余材料
		sql = " select HYXMCK.F_CLBH, HYXMCK.F_CSBH, HYXMCK.F_CKBH, HYXMCK.F_DWBH, HYXMCK.F_CLDJ,"
		    + " round(sum(HYXMCK.F_CLSL)) as F_RKSL,"
		    + " round(sum(TH.F_CLSL), 2) as F_THSL,"
		    + " round(sum(CK.F_CLSL), 2) as F_CKSL"
		    + " from HYXMCK "
		    + " LEFT JOIN HYXMCK TH ON HYXMCK.F_XMBH = TH.F_YYXMBH and "
			+ " HYXMCK.F_CLBH = TH.F_CLBH and HYXMCK.F_CKBH = TH.F_YYCKBH and HYXMCK.F_CPBH = TH.F_YYCPBH"
			+ " and HYXMCK.F_CLDJ = TH.F_CLDJ and TH.F_CRFX = 'T' and exists(" 
			+ " select 1 from HYTHD where HYTHD.F_KJQJ = TH.F_KJQJ and HYTHD.F_DJBH = TH.F_DJBH"
			+ " and HYTHD.F_THLX = '0')"	
			+ " LEFT JOIN HYXMCK CK ON HYXMCK.F_XMBH = CK.F_YYXMBH and "
			+ " HYXMCK.F_CLBH = CK.F_CLBH and HYXMCK.F_CKBH = CK.F_YYCKBH and HYXMCK.F_CPBH = CK.F_YYCPBH"
			+ " and HYXMCK.F_CLDJ = CK.F_CLDJ and (CK.F_CRFX = 'C' or CK.F_CRFX = 'D')"	
			+ " where HYXMCK.F_XMBH = '" + F_XMBH + "' and HYXMCK.F_CRFX = 'R'" 
		    + " GROUP BY HYXMCK.F_CLBH, HYXMCK.F_CSBH, HYXMCK.F_CKBH, HYXMCK.F_DWBH, HYXMCK.F_CLDJ";
		Debug.PrintlnMessageToSystem("sql|||" + sql);
		try {
			rs = stmt.executeQuery(sql);
			ds = DataSetUtils.resultSet2DataSet(rs, ds);
			for(int i = 0; i < ds.getRowCount(); i++) {
				rowset = ds.getRowSet(i);
				rksl = rowset.getNumber("F_RKSL", 0.0).doubleValue();
				thsl = rowset.getNumber("F_THSL", 0.0).doubleValue();
				cksl = rowset.getNumber("F_CKSL", 0.0).doubleValue();
				rowset.putNumber("F_LYSL", rksl + thsl - cksl);
			}
		} catch (Exception e) {
			e.printStackTrace();
			ds = EFDataSet.getInstance();
		}
		return ds;
	}
	
	//--------------------------------------------------------------------------------------------------
	//描述:项目完工时材料是否存在于仓库中
	//设计: ES
	//实现: ES
	//修改:
	//--------------------------------------------------------------------------------------------------
	private boolean checkMaterialExist(Statement stmt, String F_CLBH, String F_DWBH, String F_CSBH, String F_CKBH, double F_CLDJ) {		
		ResultSet        rs = null;
		String          sql = "";

		//库存剩余材料
		sql = " select 1 from HYCK "
			+ " where F_CLBH = '" + F_CLBH + "' and F_DWBH = '" + F_DWBH + "' and F_CSBH = '" + F_CSBH + "' and " 
		    + " F_CKBH = '" + F_CKBH + "' and F_CLDJ = " + F_CLDJ;
		Debug.PrintlnMessageToSystem("sql|||" + sql);
		try {
			rs = stmt.executeQuery(sql);
			if(rs != null && rs.next()) return true;
			else return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	//--------------------------------------------------------------------------------------------------
	//描述:项目完工获取完工人员名称
	//设计: ES
	//实现: ES
	//修改:
	//--------------------------------------------------------------------------------------------------
	private String getUserName(Statement stmt, String USER_ID) {		
		ResultSet        rs = null;
		String          sql = "";
		String    USER_NAME = "";
		//库存剩余材料
		sql = " select USER_NAME from BSUSER where USER_ID = '" + USER_ID + "'";
		Debug.PrintlnMessageToSystem("sql|||" + sql);
		try {
			rs = stmt.executeQuery(sql);
			if(rs != null && rs.next()) {
				USER_NAME = rs.getString("USER_NAME");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return USER_NAME;
	}
}
