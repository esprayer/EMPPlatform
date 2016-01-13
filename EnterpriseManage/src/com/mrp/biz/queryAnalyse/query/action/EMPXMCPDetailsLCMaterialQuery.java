package com.mrp.biz.queryAnalyse.query.action;

import java.sql.ResultSet;

import java.sql.Statement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.jdbc.support.JdbcUtils;
import org.springframework.stereotype.Repository;

import com.etsoft.pub.enums.EMPQueryParamEnum;
import com.etsoft.pub.util.EMPLimitConvertUtil;
import com.mrp.persistence.queryAnalyse.pageQuery.bean.EMPPageQuery;

import esyt.framework.com.bridge.JResponseObject;
import esyt.framework.com.builder.base.data.DataSetUtils;
import esyt.framework.com.builder.base.data.EFDataSet;
import esyt.framework.com.builder.base.data.EFRowSet;
import esyt.framework.com.core.foundation.sql.JConnection;
import esyt.framework.com.core.foundation.sql.JStatement;
import esyt.framework.com.eai.data.JParamObject;
import esyt.framework.com.pub.util.StringFunction;

/**
 * <p>Title: 项目产品联查材料</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2010</p>
 *
 * <p>Company: </p>
 *
 * @author ES
 * @version 1.0
 */

@Repository
public class EMPXMCPDetailsLCMaterialQuery {

	@Autowired
	private JdbcTemplate                  jdbcTemplate;

	protected     String                   msTableName = "";
	
	public JResponseObject QueryObject(JParamObject PO){
		JConnection         conn = null;
		JStatement          stmt = null;
		EFDataSet   queryDataset = null;
		EFRowSet     queryRowset = EFRowSet.getInstance();
		JResponseObject       RO = null;
		try {
			conn = JConnection.getInstance(jdbcTemplate.getDataSource().getConnection());
			conn.setAutoCommit(true);
			stmt = conn.createStatement();
			// 获取查询数据
			queryDataset = queryDataObject(stmt, PO);
			queryRowset.setExtProperty(EMPQueryParamEnum.PAGEQUERY, PO.getValue(EMPQueryParamEnum.PAGEQUERY, null));
			queryRowset.setDataSet(EMPQueryParamEnum.QUERYRESULT, queryDataset);
			// 封装返回数据
		   	RO = new JResponseObject(queryRowset, 0, null);
		} catch(Exception ce) {
			ce.printStackTrace();
			RO = new JResponseObject(null, -1, ce.getMessage());
		} finally {
			try {
				stmt.executeUpdate("drop table "+msTableName);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			try {
				JdbcUtils.closeStatement(stmt);
				stmt = null;
				DataSourceUtils.releaseConnection(conn, jdbcTemplate.getDataSource());
			} catch(Exception ce) {
				ce.printStackTrace();
			}
		}
		return RO;
	}
	
	/**
	 * 组织数据的过程
	 * @throws Exception
	 * @return TreeTableDataManager
	 */
	private EFDataSet queryDataObject(Statement stmt, JParamObject po) throws Exception{
		EFDataSet   queryDataset = null;

		//创建临时表
	    createTempTable(stmt);
	    
	    //取出原始数据
	    getOriginData(stmt, po);
	    
	    //创建临时表
	    queryDataset = convertDataSet(stmt, po);
		return queryDataset;
	}
	
	/**
	 * 创建临时表
	 * @throws Exception
	 */
	private void createTempTable(Statement stmt) throws Exception{
	    msTableName = "XMCPCL1"+StringFunction.getTempStr(6);
	    String        strSql = "";
	    
	    String tempTableBody = " F_ID          varchar(1)           default ''   null,"
	         				 + " F_CLBH        varchar(255)         default ''   null,"
	         				 + " F_CPBH        varchar(255)         default ''   null,"
	         				 + " F_CLMC        varchar(255)         default ''   null,"
	         				 + " F_XMBH        varchar(255)         default ''   null,"	         				 	         			
	         				 + " F_CRFX        varchar(255)         default ''   null,"
	         				 + " F_FLLX        varchar(255)         default ''   null,"
	         				 + " F_XMZT        varchar(255)         default ''   null,"
	         				 + " F_CLDJ        decimal(30,2)	    default 0    null,"
	         				 + " F_RKSL        decimal(30,2)   	    default 0    null,"
	         				 + " F_RKCB        decimal(30,2)   	    default 0    null,"
	         				 + " F_ZCSL        decimal(30,2)   	    default 0    null,"
	         				 + " F_ZCCB        decimal(30,2)   	    default 0    null,"
	         				 + " F_JDSL        decimal(30,2)   	    default 0    null,"
	         				 + " F_JDCB        decimal(30,2)   	    default 0    null,"
	         				 + " F_BJDSL       decimal(30,2)   	    default 0    null,"
	         				 + " F_BJDCB       decimal(30,2)   	    default 0    null,"
	         				 + " F_CLTHSL      decimal(30,2)   	    default 0    null,"
	         				 + " F_CLTHCB      decimal(30,2)   	    default 0    null,"
	         				 + " F_CSTHSL      decimal(30,2)   	    default 0    null,"
	         				 + " F_CSTHCB      decimal(30,2)   	    default 0    null,"
	         				 + " F_DBSL        decimal(30,2)   	    default 0    null,"	 
	         				 + " F_DBCB        decimal(30,2)   	    default 0    null,"	 
	         				 + " F_KCSL        decimal(30,2)   	    default 0    null,"
	         				 + " F_KCCB        decimal(30,2)   	    default 0    null,"
	         				 + " F_XMSL        decimal(30,2)   	    default 0    null,"
	         				 + " F_XMCB        decimal(30,2)   	    default 0    null,"
	         				 + " F_ZCB         decimal(30,2)   	    default 0    null";
	    strSql = "create table " + msTableName + "(" + tempTableBody + ")";
	    stmt.execute(strSql);
	}
	
	/**
	 * 逐级汇总数据
	 * @throws Exception
	 */
	public void getOriginData(Statement stmt, JParamObject po) throws Exception {
		String            strSql = "";
		String            F_XMBH = po.GetValueByParamName("F_XMBH", "");
		String            F_CPBH = po.GetValueByParamName("F_CPBH", "");
		String            F_CLBH = po.GetValueByParamName("F_CLBH", ""); 

		//插入入库数量
		strSql = " insert into " + msTableName + " (F_ID, F_XMBH, F_CPBH, F_CLBH, F_CRFX, F_FLLX, F_CLDJ, F_RKSL, F_RKCB)"
		       + " select '0', F_XMBH, F_CPBH, F_CLBH, 'R', '0', F_CLDJ, round(sum(F_CLSL), 2), round(sum(F_CLSL * F_CLDJ), 2)"
		       + " from HYXMCK"
		       + " where HYXMCK.F_CRFX = 'R' and HYXMCK.F_CLBH like '" + F_CLBH + "%'"		       
		       + " and HYXMCK.F_CPBH like '" + F_CPBH + "%'"
		       + " and HYXMCK.F_XMBH like '" + F_XMBH + "%'"
		       + " group by F_XMBH, F_CPBH, F_CLBH, F_CLDJ";
		stmt.execute(strSql);
		
		//插入正常出库数量
		strSql = " insert into " + msTableName + " (F_ID, F_XMBH, F_CPBH, F_CLBH, F_CRFX, F_FLLX, F_CLDJ, F_ZCSL, F_ZCCB)"
		       + " select '0', F_XMBH, F_CPBH, F_CLBH, 'C', '0', F_CLDJ, round(sum(F_CLSL), 2), round(sum(F_CLSL * F_CLDJ), 2)"
		       + " from HYXMCK"
		       + " where HYXMCK.F_CRFX = 'C' and F_FLLX = '0' and HYXMCK.F_CLBH like '" + F_CLBH + "%'"		       
		       + " and HYXMCK.F_CPBH like '" + F_CPBH + "%'"
		       + " and HYXMCK.F_XMBH like '" + F_XMBH + "%'"
		       + " group by F_XMBH, F_CPBH, F_CLBH, F_CLDJ";
		stmt.execute(strSql);
		
		//插入借调出库数量,同一个项目不同产品之间的调货,影响库存成本和领用成本，对于项目产品口径查询
		strSql = " insert into " + msTableName + " (F_ID, F_XMBH, F_CPBH, F_CLBH, F_CRFX, F_FLLX, F_CLDJ, F_JDSL, F_JDCB)"
		       + " select '0', F_YYXMBH, F_YYCPBH, F_CLBH, 'C', '1', F_CLDJ, round(sum(F_CLSL), 2), round(sum(F_CLSL * F_CLDJ), 2)"
		       + " from HYXMCK"
		       + " where HYXMCK.F_CRFX = 'C' and F_FLLX = '1' and HYXMCK.F_CLBH like '" + F_CLBH + "%'"		       
		       + " and HYXMCK.F_YYCPBH like '" + F_CPBH + "%'"
		       + " and HYXMCK.F_YYXMBH like '" + F_XMBH + "%'"
		       + " group by F_YYXMBH, F_YYCPBH, F_CLBH, F_CLDJ";
		stmt.execute(strSql);
		
		//插入被借调出库数量,借给其他项目,不同项目之间的调货，不影响领用成本，只影响库存成本
		strSql = " insert into " + msTableName + " (F_ID, F_XMBH, F_CPBH, F_CLBH, F_CLDJ, F_BJDSL, F_BJDCB)"
		       + " select '0', F_XMBH, F_CPBH, F_CLBH, F_CLDJ, round(sum(F_CLSL), 2), round(sum(F_CLSL * F_CLDJ), 2)"
		       + " from HYXMCK"
		       + " where HYXMCK.F_CRFX = 'C' and F_FLLX = '1' and HYXMCK.F_CLBH like '" + F_CLBH + "%'"		       
		       + " and HYXMCK.F_CPBH like '" + F_CPBH + "%'"
		       + " and HYXMCK.F_XMBH like '" + F_XMBH + "%'"
		       + " group by F_XMBH, F_CPBH, F_CLBH, F_CLDJ";
		stmt.execute(strSql);
		
		//插入材料退货数量
		strSql = " insert into " + msTableName + " (F_ID, F_XMBH, F_CPBH, F_CLBH, F_CRFX, F_FLLX, F_CLDJ, F_CLTHSL, F_CLTHCB)"
		       + " select '0', F_YYXMBH, F_YYCPBH, F_CLBH, 'T', '0', F_CLDJ, round(sum(F_CLSL), 2), round(sum(F_CLSL * F_CLDJ), 2)"
		       + " from HYXMCK"
		       + " where HYXMCK.F_CRFX = 'T' and F_FLLX = '0' and HYXMCK.F_CLBH like '" + F_CLBH + "%'"		       
		       + " and HYXMCK.F_YYCPBH like '" + F_CPBH + "%'"
		       + " and HYXMCK.F_YYXMBH like '" + F_XMBH + "%'"
		       + " group by F_YYXMBH, F_YYCPBH, F_CLBH, F_CLDJ";
		stmt.execute(strSql);
		
		//插入厂商退货数量
		strSql = " insert into " + msTableName + " (F_ID, F_XMBH, F_CPBH, F_CLBH, F_CRFX, F_FLLX, F_CLDJ, F_CSTHSL, F_CSTHCB)"
		       + " select '0', F_YYXMBH, F_YYCPBH, F_CLBH, 'T', '1', F_CLDJ, round(sum(F_CLSL), 2), round(sum(F_CLSL * F_CLDJ), 2)"
		       + " from HYXMCK"
		       + " where HYXMCK.F_CRFX = 'T' and F_FLLX = '1' and HYXMCK.F_CLBH like '" + F_CLBH + "%'"		       
		       + " and HYXMCK.F_YYCPBH like '" + F_CPBH + "%'"
		       + " and HYXMCK.F_YYXMBH like '" + F_XMBH + "%'"
		       + " group by F_YYXMBH, F_YYCPBH, F_CLBH, F_CLDJ";
		stmt.execute(strSql);
		
		//仓库调拨出库
		strSql = " insert into " + msTableName + " (F_ID, F_XMBH, F_CPBH, F_CLBH, F_CRFX, F_FLLX, F_CLDJ, F_DBSL, F_DBCB)"
			   + " select '0', F_YYXMBH, F_YYCPBH, F_CLBH, 'D', '0', F_CLDJ, round(sum(F_CLSL), 2), round(sum(F_CLSL * F_CLDJ), 2)"
			   + " from HYXMCK"
			   + " where HYXMCK.F_CRFX = 'D' and HYXMCK.F_CLBH like '" + F_CLBH + "%'"		          
			   + " and HYXMCK.F_YYCPBH like '" + F_CPBH + "%'"
		       + " and HYXMCK.F_YYXMBH like '" + F_XMBH + "%'"
		       + " group by F_YYXMBH, F_YYCPBH, F_CLBH, F_CLDJ";
		stmt.execute(strSql);
	}
	
	public EFDataSet convertDataSet(Statement stmt, JParamObject po) throws Exception {
		EFDataSet queryDataset = null;
		EFDataSet   newDataset = EFDataSet.getInstance();
		ResultSet           rs = null;
		String          strSql = "";
		String          F_XMBH = po.GetValueByParamName("F_XMBH", "");
		EMPPageQuery pageQuery = (EMPPageQuery) po.getValue(EMPQueryParamEnum.PAGEQUERY, "");
		
		//汇总数据
		strSql = " insert into " + msTableName + " (F_ID, F_CLBH, F_RKCB, F_ZCCB, F_JDCB, F_BJDCB, F_CLTHCB, F_CSTHCB, F_DBCB,"
		       + " F_RKSL, F_ZCSL, F_JDSL, F_BJDSL, F_CLTHSL, F_CSTHSL, F_DBSL)"
		       + " select '1', F_CLBH, round(sum(F_RKCB), 2), round(sum(F_ZCCB), 2), round(sum(F_JDCB), 2), round(sum(F_BJDCB), 2),"
		       + " round(sum(F_CLTHCB), 2), round(sum(F_CSTHCB), 2), round(sum(F_DBCB), 2), round(sum(F_RKSL), 2), round(sum(F_ZCSL), 2), "
		       + " round(sum(F_JDSL), 2), round(sum(F_BJDSL), 2), round(sum(F_CLTHSL), 2), round(sum(F_CSTHSL), 2), round(sum(F_DBSL), 2)"
		       + " from " + msTableName
		       + " where F_ID = '0' "		       
		       + " group by F_CLBH";
		stmt.execute(strSql);
		
		//删除汇总前数据
		strSql = " delete from " + msTableName + " where F_ID = '0'";
		stmt.execute(strSql);
		
		//更新库存成本
		//库存成本=入库成本-正常出库成本-被借调成本+材料退货成本
		strSql = " update " + msTableName + " TEMP"
	           + " set F_KCCB = F_RKCB - F_ZCCB - F_BJDCB + F_CLTHCB, F_KCSL = F_RKSL - F_ZCSL - F_BJDSL + F_CLTHSL" ;
		stmt.execute(strSql);
		
		//更新项目成本
		//项目成本=正常出库成本+仓库调拨成本+借调成本-材料退货成本-厂商退货成本
		strSql = " update " + msTableName + " TEMP"
	           + " set F_XMCB = F_ZCCB + F_JDCB + F_DBCB  - F_CLTHCB - F_CSTHCB, F_XMSL = F_ZCSL + F_JDSL + F_DBSL  - F_CLTHSL - F_CSTHSL" ;
		stmt.execute(strSql);

		//更新总成本
		strSql = " update " + msTableName + " TEMP"
	           + " set F_ZCB = F_XMCB + F_KCCB" ;
		stmt.execute(strSql);
		
		strSql = " insert into " + msTableName + " (F_ID, F_CLBH)"
    	       + " select '1', F_CLBH"
    	       + " from HYXMMX"
    	       + " where F_XMBH like '" + F_XMBH + "%'";	       
		stmt.execute(strSql);
		
		strSql = " insert into " + msTableName + " (F_ID, F_CLBH, F_KCCB, F_XMCB, F_ZCB, F_KCSL, F_XMSL)"
	       	   + " select '2', F_CLBH, round(sum(F_KCCB), 2), round(sum(F_XMCB), 2), round(sum(F_ZCB), 2), "
	       	   + " round(sum(F_KCSL), 2), round(sum(F_XMSL), 2)"
	       	   + " from " + msTableName
	       	   + " where F_ID = '1' "		       
	       	   + " group by F_CLBH";
		stmt.execute(strSql);
	
		//删除汇总前数据
		strSql = " delete from " + msTableName + " where F_ID = '1'";
		stmt.execute(strSql);
		
		//更新所属项目名称
		strSql = " update " + msTableName + " TEMP"
	           + " INNER JOIN HYCLZD ON TEMP.F_CLBH = HYCLZD.F_CLBH set TEMP.F_CLMC = HYCLZD.F_CLMC" ;
		stmt.execute(strSql);
		
		strSql = " select F_CLBH, F_CLMC, round(F_KCCB, 2) as F_KCCB, round(F_XMCB, 2) as F_XMCB, round(F_ZCB, 2) as F_ZCB, "
			   + " round(F_KCSL, 2) as F_KCSL, round(F_XMSL, 2) as F_XMSL from " + msTableName + " order by F_ID, F_CLBH";
		rs = stmt.executeQuery(strSql);
		queryDataset = DataSetUtils.resultSet2DataSet(rs, queryDataset);

		if(pageQuery == null) return queryDataset;
		
		pageQuery.setTotalCount(queryDataset.getRowCount());
		newDataset = EMPLimitConvertUtil.getDataSetLimit(queryDataset, pageQuery.getPageNum(), pageQuery.getNumPerPage());
		return newDataset;
	}
}
