package com.mrp.biz.server;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.jdbc.support.JdbcUtils;

import com.mrp.persistence.sysConfigure.project.bean.HYXMZD;

import esyt.framework.com.builder.base.data.DataSetUtils;
import esyt.framework.com.builder.base.data.EFDataSet;
import esyt.framework.com.builder.base.data.EFRowSet;
import esyt.framework.com.core.foundation.sql.JConnection;
import esyt.framework.com.core.foundation.sql.JStatement;

public class EMPCheckFormBusinessUtil {

	public static String checkMaterialStockApplyNumber(JStatement stmt, EFRowSet rowset) throws Exception {
		double                sqsl = 0;
		double               yrksl = 0.0;	
		double                rksl = rowset.getNumber("F_RKSL", 0.0).doubleValue();	
		String              errMsg = "";
		String              F_XMBH = rowset.getString("F_XMBH", "");
		String              F_XMMC = rowset.getString("F_XMMC", "");
		String              F_CLBH = rowset.getString("F_CLBH", "");
		String              F_CLMC = rowset.getString("F_CLMC", "");
		String              strSql = "";
		ResultSet               rs = null;
		
		sqsl = getClSqSl(stmt, F_XMBH, F_CLBH);
		yrksl = getClKcSl(stmt, F_XMBH, F_CLBH);
		
		if(rksl > sqsl - yrksl) {
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
			
			errMsg = "项目【" + F_XMMC + "】-材料【" + F_CLMC
		           + "】入库数量大于申请数量，请重新维护入库数量并保存！申请数量为：" + sqsl + "；已入库数量为：" + yrksl + "；本次入库数量为：" + rksl + "<br>";
		}
		return errMsg;
	}
	
	//--------------------------------------------------------------------------------------------------
	//描述:获取申请数量
	//设计: ES
	//实现: ES
	//修改:
	//--------------------------------------------------------------------------------------------------
	public static double getClSqSl(JStatement stmt, String F_XMBH, String F_CLBH) throws SQLException {
		ResultSet         rs = null;
		double            sl = 0;
		
		String sql = " select F_SL from HYXMMX"
		       	   + " where F_XMBH='" + F_XMBH + "' and F_CLBH = '" + F_CLBH + "'";
		rs = stmt.executeQuery(sql);
		if(rs != null && rs.next()) {
			sl = rs.getDouble(1);
		}		
		return sl;
	}
	
	//--------------------------------------------------------------------------------------------------
	//描述:获取库存数量
	//设计: ES
	//实现: ES
	//修改:
	//--------------------------------------------------------------------------------------------------
	public static double getClKcSl(JStatement stmt, String F_XMBH, String F_CLBH) throws SQLException {
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
		rs = stmt.executeQuery(sql);
		if(rs != null && rs.next()) {
			sl = rs.getDouble(1) + rs.getDouble(2) - rs.getDouble(3);
		}
		return sl;
	}
	
	//--------------------------------------------------------------------------------------------------
	//描述:检查出库数量是否大于库存数量
	//设计: ES
	//实现: ES
	//修改:
	//--------------------------------------------------------------------------------------------------
	public static String checkStockNumber(JStatement stmt, EFRowSet rowset) {
		double                kcsl = 0;
		double                cksl = rowset.getNumber("F_CKSL", 0.0).doubleValue();	
		String              errMsg = "";
		String              F_XMBH = rowset.getString("F_SSXMBH", "");
		String              F_CPBH = rowset.getString("F_SSCPBH", "");
		String              F_CKBH = rowset.getString("F_SSCKBH", "");
		String              F_CLBH = rowset.getString("F_CLBH", "");		
		double              F_CLDJ = rowset.getNumber("F_CLDJ", 0.0).doubleValue();
		
		kcsl = getClKcSl(stmt, F_XMBH, F_CPBH, F_CLBH, F_CKBH, F_CLDJ);
		if(cksl > kcsl) {
			errMsg = "项目【" + rowset.getString("F_SSXMMC", "") + "】-产品【"
		           + rowset.getString("F_SSXMMC", "") + "】-材料【" + rowset.getString("F_CLMC", "") 
		           + "】出库数量大于库存数量，请重新维护出库数量并保存！库存数量为：" + kcsl + "；出库数量为：" + cksl + "<br>";
		}
		return errMsg;
	}
	
	//--------------------------------------------------------------------------------------------------
	//描述:检查出库数量是否大于项目申请单材料数量
	//设计: ES
	//实现: ES
	//修改:
	//--------------------------------------------------------------------------------------------------
	public static String checkApplyForNumber(JStatement stmt, EFRowSet rowset) {
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
		
		lyslDS = getClLySl(stmt, F_XMBH, F_CPBH, F_CLBH, F_CKBH, F_CLDJ);
		
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
	
	//--------------------------------------------------------------------------------------------------
	//描述:获取领用数量
	//设计: ES
	//实现: ES
	//修改:
	//--------------------------------------------------------------------------------------------------
	public static EFDataSet getClLySl(JStatement stmt, String F_XMBH, String F_CPBH, String F_CLBH, String F_CKBH, double F_CLDJ) {
		//项目申请单已领用数量
		String           sql = "";
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
		}
		return ds;
	}
	
	//--------------------------------------------------------------------------------------------------
	//描述:获取库存数量
	//设计: ES
	//实现: ES
	//修改:
	//--------------------------------------------------------------------------------------------------
	public static double getClKcSl(JStatement stmt, String F_XMBH, String F_CPBH, String F_CLBH, String F_CKBH, double F_CLDJ) {
		ResultSet        rs = null;
		double         sysl = 0.0;//剩余数量
		double         rksl = 0.0;//入库数量
		double         dbsl = 0.0;//调拨数量
		double       clthsl = 0.0;//材料退货数量
		double       zccksl = 0.0;//出库数量
		double       jdcksl = 0.0;//出库数量
		double      bjdcksl = 0.0;//出库数量

		String sql = " select F_SL as F_SQSL,"//申请数量
		       	   + " round(sum(HYXMCK.F_CLSL), 2) as F_RKSL"//入库数量
		       	   + " from HYXMCK"
		       	   + " LEFT JOIN HYXMMX ON HYXMMX.F_XMBH = HYXMCK.F_XMBH AND HYXMMX.F_CLBH = HYXMCK.F_CLBH"
		       	   + " where HYXMCK.F_XMBH = '" + F_XMBH + "' and HYXMCK.F_CPBH = '" + F_CPBH + "'"
		       	   + " and HYXMCK.F_CLBH = '" + F_CLBH + "' and HYXMCK.F_CRFX='R'"
		       	   + " and HYXMCK.F_CKBH = '" + F_CKBH + "' and HYXMCK.F_CLDJ = " + F_CLDJ 
		       	   + " and exists(select 1 from HYXMMX where HYXMMX.F_XMBH = HYXMCK.F_XMBH" 
		       	   + " and HYXMMX.F_CLBH = HYXMCK.F_CLBH)"
		       	   + " GROUP BY HYXMCK.F_XMBH, HYXMCK.F_CLBH,HYXMCK.F_DWBH,HYXMCK.F_CSBH,F_CLDJ,HYXMCK.F_CPBH";

		try {
			rs = stmt.executeQuery(sql);
			if(rs != null && rs.next()) {
				rksl = rs.getDouble("F_RKSL");
			}
			
			//正常材料出库
			sql = " select IFNULL(round(sum(HYXMCK.F_CLSL), 2), 0.0) as F_CKSL"//出库数量
				+ " from HYXMCK"	
				+ " where HYXMCK.F_YYXMBH = '" + F_XMBH + "' and HYXMCK.F_YYCPBH = '" + F_CPBH + "'"
				+ " and HYXMCK.F_CLBH = '" + F_CLBH + "' and F_CRFX = 'C'"
				+ " and HYXMCK.F_YYCKBH = '" + F_CKBH + "' and HYXMCK.F_CLDJ = " + F_CLDJ 
				+ " and F_FLLX = '0'"
				+ " GROUP BY HYXMCK.F_YYXMBH, HYXMCK.F_CLBH,HYXMCK.F_DWBH,HYXMCK.F_CSBH,F_CLDJ,HYXMCK.F_YYCPBH";
			rs = stmt.executeQuery(sql);
			if(rs != null && rs.next()) {
				zccksl = rs.getDouble("F_CKSL");
			}
			
			//材料调用材料出库（被借调方）,比如A借调到B，查询A
			sql = " select IFNULL(round(sum(HYXMCK.F_CLSL), 2), 0.0) as F_CKSL"//出库数量,申请数量
				+ " from HYXMCK"	
				+ " where HYXMCK.F_XMBH = '" + F_XMBH + "' and HYXMCK.F_CPBH = '" + F_CPBH + "'"
				+ " and HYXMCK.F_CLBH = '" + F_CLBH + "' and F_CRFX = 'C'"
				+ " and HYXMCK.F_CKBH = '" + F_CKBH + "' and F_FLLX = '1' and HYXMCK.F_CLDJ = " + F_CLDJ 
				+ " GROUP BY HYXMCK.F_XMBH, HYXMCK.F_CLBH,HYXMCK.F_DWBH,HYXMCK.F_CSBH,F_CLDJ,HYXMCK.F_CPBH";
			rs = stmt.executeQuery(sql);
			if(rs != null && rs.next()) {
				bjdcksl = rs.getDouble("F_CKSL");
			}
			
			//材料调用材料出库（借调方）,比如A借调到B，查询B
			sql = " select IFNULL(round(sum(HYXMCK.F_CLSL), 2), 0.0) as F_CKSL"//出库数量,申请数量
				+ " from HYXMCK"	
				+ " where HYXMCK.F_YYXMBH = '" + F_XMBH + "' and HYXMCK.F_YYCPBH = '" + F_CPBH + "'"
				+ " and HYXMCK.F_CLBH = '" + F_CLBH + "' and F_CRFX = 'C'"
				+ " and HYXMCK.F_CKBH = '" + F_CKBH + "' and F_FLLX = '1' and HYXMCK.F_CLDJ = " + F_CLDJ 
				+ " GROUP BY HYXMCK.F_YYXMBH, HYXMCK.F_CLBH,HYXMCK.F_DWBH,HYXMCK.F_CSBH,F_CLDJ,HYXMCK.F_YYCPBH";
			rs = stmt.executeQuery(sql);
			if(rs != null && rs.next()) {
				jdcksl = rs.getDouble("F_CKSL");
			}
			
			//仓库调拨出库
			sql = " select IFNULL(round(sum(HYXMCK.F_CLSL), 2), 0.0) as F_CKSL"//出库数量,申请数量
				+ " from HYXMCK"	
				+ " where HYXMCK.F_YYXMBH = '" + F_XMBH + "' and HYXMCK.F_YYCPBH = '" + F_CPBH + "'"
				+ " and HYXMCK.F_CLBH = '" + F_CLBH + "' and F_CRFX = 'D'"
				+ " and HYXMCK.F_YYCKBH = '" + F_CKBH + "' and F_FLLX = '1' and HYXMCK.F_CLDJ = " + F_CLDJ 
				+ " GROUP BY HYXMCK.F_YYXMBH, HYXMCK.F_CLBH,HYXMCK.F_DWBH,HYXMCK.F_CSBH,F_CLDJ,HYXMCK.F_YYCPBH";
			rs = stmt.executeQuery(sql);
			if(rs != null && rs.next()) {
				dbsl = rs.getDouble("F_CKSL");
			}
			
			//材料退货材料，影响出库数量
			sql = " select IFNULL(round(sum(HYXMCK.F_CLSL), 2), 0.0) as F_THSL"//出库数量,申请数量
				+ " from HYXMCK"
				+ " where HYXMCK.F_YYXMBH = '" + F_XMBH + "' and HYXMCK.F_YYCPBH = '" + F_CPBH + "'"
				+ " and HYXMCK.F_CLBH = '" + F_CLBH + "' and HYXMCK.F_CRFX='T'"
				+ " and HYXMCK.F_YYCKBH = '" + F_CKBH + "' and F_FLLX = '0' and HYXMCK.F_CLDJ = " + F_CLDJ 
			    + " GROUP BY HYXMCK.F_YYXMBH, HYXMCK.F_CLBH,HYXMCK.F_DWBH,HYXMCK.F_CSBH,F_CLDJ,HYXMCK.F_YYCPBH";
			rs = stmt.executeQuery(sql);
			if(rs != null && rs.next()) {
				clthsl = rs.getDouble("F_THSL");
			}
			
			//正常出库是入库数量建上，借调出库是影响被借调入库数量
			//材料退货，出库数量减少，剩余数量增加
			//厂商退货，入库数量减少
			//借调出库不影响入库数量，退货的时候影响库存数量，所以借调出库不考虑
			//厂商退货直接退给厂家，所以还是相当于出库
			sysl = rksl - zccksl - bjdcksl + clthsl;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sysl;
	}
	
	//delivery voucher简称DV、warehouse  voucher简称WV
	public static String checkDVItemMaterialSave(JStatement stmt, String F_KJQJ, String F_DJBH) {
		String              errMsg = "";
		EFDataSet        HYDBDMXDS = null;
		EFRowSet         itemRwSet = EFRowSet.getInstance(); 
		try {
			HYDBDMXDS = collectDVFormItem(stmt, F_KJQJ, F_DJBH);	
			
			for(int i = 0; i < HYDBDMXDS.getRowCount(); i++) {
				itemRwSet = HYDBDMXDS.getRowSet(i);

				errMsg = checkXMWG(stmt, itemRwSet.getString("F_YYXMBH", ""), itemRwSet.getString("F_SSXMMC", ""));
				if(!errMsg.trim().equals("")) return errMsg;
				
				errMsg = checkStockNumber(stmt, itemRwSet);
				if(!errMsg.trim().equals("")) return errMsg;
				
				errMsg = checkApplyForNumber(stmt, itemRwSet);
				if(!errMsg.trim().equals("")) return errMsg;
			}
		} catch(Exception ce) {
			ce.printStackTrace();
		}
		return errMsg;
	}
	
	//汇总出库单明细
	public static EFDataSet collectDVFormItem(JStatement stmt, String F_KJQJ, String F_DJBH) {
		String      strSql = "";
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
				rs = stmt.executeQuery(strSql);
				itemDS = DataSetUtils.resultSet2DataSet(rs, itemDS);
			} catch (Exception e) {
				e.printStackTrace();
				itemDS = EFDataSet.getInstance();
			}
		} catch(Exception ce) {
			ce.printStackTrace();
		}
		return itemDS;
	}
	
	//--------------------------------------------------------------------------------------------------
	//描述:检查项目是否完工
	//设计: ES
	//实现: ES
	//修改:
	//--------------------------------------------------------------------------------------------------
	public static String checkXMWG(JStatement stmt, String F_XMBH, String F_XMMC) {
		String      F_XMZT = "";
		String      errMsg = "";
		String      strSql = "";
		ResultSet       rs = null;
		
		try {
			strSql = "select * from HYXMZD where F_XMBH = '" + F_XMBH + "'";
			rs = stmt.executeQuery(strSql);
			
			if(rs != null && rs.next()) {
				F_XMZT = rs.getString("F_XMZT");
				if(F_XMZT.equals("")) {
					errMsg = "项目【" + F_XMMC + "】不存在，请重新检查项目是否做过修改！<br>";
				}
				if(F_XMZT.equals("1")) {
					errMsg = "项目【" + F_XMMC + "】已完工，不允许再做业务！<br>";
				}
			} else {
				errMsg = "项目【" + F_XMMC + "】不存在，请重新检查项目是否做过修改！<br>";
			}
		} catch(Exception ce) {
			ce.printStackTrace();
			return ce.getCause().getMessage();
		}
		
		return errMsg;
	}
	
	//--------------------------------------------------------------------------------------------------
	//描述:检查调拨数量是否大于仓库库存数量
	//设计: ES
	//实现: ES
	//修改:
	//--------------------------------------------------------------------------------------------------
	public static String checkTransfersNumber(JStatement stmt, EFRowSet rowset) {
		double                kcsl = 0;
		double                dbsl = rowset.getNumber("F_CLSL", 0.0).doubleValue();	
		String              errMsg = "";
		
		kcsl = getDeportKcSl(stmt, rowset);
		if(dbsl > kcsl) {
			errMsg = "材料【" + rowset.getString("F_CLMC", "") + "】-供应商【"
		           + rowset.getString("F_DWMC", "") + "】-厂商【" + rowset.getString("F_CSMC", "") 
		           + "】-材料单价【" + rowset.getObject("F_CLDJ", "") + "】调拨数量大于库存数量，请重新维护出库数量并保存！库存数量为：" + kcsl + "；调拨数量为：" + dbsl + "<br>";
		}
		return errMsg;
	}
	
	public static double getDeportKcSl(JStatement stmt, EFRowSet rowset) {
		ResultSet              rs = null;
		String              F_CLBH = rowset.getString("F_CLBH", "");
		String              F_DWBH = rowset.getString("F_DWBH", "");
		String              F_CSBH = rowset.getString("F_CSBH", "");
		String              F_CKBH = rowset.getString("F_CKBH", "");		
		double              F_CLDJ = Double.parseDouble(rowset.getString("F_CLDJ", "0.0"));
		double                  sl = 0;
		
		if(F_CLDJ == 0) {
			F_CLDJ = rowset.getNumber("F_CLDJ", 0.0).doubleValue();
		}
		String                 sql = " select F_CLSL from HYCK"
		       	                   + " where F_CKBH='" + F_CKBH + "' and F_CLBH = '" + F_CLBH + "' and F_DWBH = '" + F_DWBH + "'"
		       	                   + " and F_CSBH = '" + F_CSBH + "' and F_CLDJ = " + F_CLDJ;
		try {
			rs = stmt.executeQuery(sql);
			if(rs != null && rs.next()) {
				sl = rs.getDouble(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sl;
	}
	
	//--------------------------------------------------------------------------------------------------
	//描述:检查出库数量是否大于库存数量
	//设计: ES
	//实现: ES
	//修改:
	//--------------------------------------------------------------------------------------------------
	public static String checkTransfersStockNumber(JStatement stmt, EFRowSet rowset) {
		double                kcsl = 0;
		double                dbsl = rowset.getNumber("F_DBSL", 0.0).doubleValue();	
		String              errMsg = "";
		String              F_DWBH = rowset.getString("F_DWBH", "");
		String              F_CSBH = rowset.getString("F_CSBH", "");
		String              F_CKBH = rowset.getString("F_CKBH", "");
		String              F_CLBH = rowset.getString("F_CLBH", "");		
		double              F_CLDJ = rowset.getNumber("F_CLDJ", 0.0).doubleValue();
		
		kcsl = getDeportKcSl(stmt, rowset);
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
	public static String checkTransfersApplyForNumber(JStatement stmt, EFRowSet rowset) {
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
		
		lyslDS = getClLySl(stmt, F_XMBH, F_CPBH, F_CLBH, F_CKBH, F_CLDJ);
		
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
}
