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

@Repository
public class EMPProjectProductCarryoverQuery {

	@Autowired
	private JdbcTemplate                  jdbcTemplate;
	
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
		ResultSet             rs = null;
		EFDataSet   queryDataset = null;
		EFDataSet     newDataset = EFDataSet.getInstance();
		EMPPageQuery   pageQuery = (EMPPageQuery) po.getValue(EMPQueryParamEnum.PAGEQUERY, "");
		String            strSql = "";
		Double            F_CLDJ = 0.0;
		try {
			F_CLDJ = Double.parseDouble(po.GetValueByParamName("F_CLDJ", ""));
			
			strSql = " select HYXMCK.F_XMBH, MAX(HYXMZD.F_XMMC) as F_XMMC, HYXMCK.F_CPBH, MAX(HYCPZD.F_CPMC) as F_CPMC, "
			       + " HYXMCK.F_CLDJ,"			       
			       + " IFNULL(round(sum(CK.F_CLSL), 2), 0.0) as F_CKSL,"             //出库数量
			       + " IFNULL(round(sum(CLTH.F_CLSL), 2), 0.0) as F_CLTHSL,"         //材料退货数量
			       + " IFNULL(round(sum(CSTH.F_CLSL), 2), 0.0) as F_CSTHSL,"         //厂商退货数量
			       + " IFNULL(round(sum(DB.F_CLSL), 2), 0.0) as F_DBSL,"         //材料退货数量
			       + " IFNULL(round(sum(HYXMCK.F_CLSL), 2), 0.0) as F_RKSL"          //入库数量
			       + " from HYXMCK"
			       + " LEFT JOIN HYXMZD ON HYXMCK.F_XMBH = HYXMZD.F_XMBH "
			       + " LEFT JOIN HYCPZD ON HYXMCK.F_CPBH = HYCPZD.F_CPBH "
			       + " LEFT JOIN HYXMCK CLTH ON HYXMCK.F_XMBH = CLTH.F_YYXMBH and "
			       + " HYXMCK.F_CLBH = CLTH.F_CLBH and HYXMCK.F_CKBH = CLTH.F_YYCKBH and HYXMCK.F_CPBH = CLTH.F_YYCPBH"
			       + " and HYXMCK.F_CLDJ = CLTH.F_CLDJ and CLTH.F_CRFX = 'T' and exists(" 
			       + " select 1 from HYTHD where HYTHD.F_KJQJ = CLTH.F_KJQJ and HYTHD.F_DJBH = CLTH.F_DJBH"
			       + " and HYTHD.F_THLX = '0')"	
			       + " LEFT JOIN HYXMCK CSTH ON HYXMCK.F_XMBH = CSTH.F_YYXMBH and "
			       + " HYXMCK.F_CLBH = CSTH.F_CLBH and HYXMCK.F_CKBH = CSTH.F_YYCKBH and HYXMCK.F_CPBH = CSTH.F_YYCPBH"
			       + " and HYXMCK.F_CLDJ = CSTH.F_CLDJ and CSTH.F_CRFX = 'T' and exists(" 
			       + " select 1 from HYTHD where HYTHD.F_KJQJ = CSTH.F_KJQJ and HYTHD.F_DJBH = CSTH.F_DJBH"
			       + " and HYTHD.F_THLX = '1')"	
			       + " LEFT JOIN HYXMCK CK ON HYXMCK.F_XMBH = CK.F_YYXMBH AND HYXMCK.F_CPBH = CK.F_YYCPBH AND "
			       + " HYXMCK.F_CKBH = CK.F_YYCKBH and HYXMCK.F_CLBH=CK.F_CLBH AND HYXMCK.F_CSBH = CK.F_CSBH AND "
			       + " CK.F_CRFX = 'C' AND HYXMCK.F_CLDJ = CK.F_CLDJ "
			       + " LEFT JOIN HYXMCK DB ON HYXMCK.F_XMBH = DB.F_YYXMBH AND HYXMCK.F_CPBH = DB.F_YYCPBH AND "
			       + " HYXMCK.F_CKBH = DB.F_YYCKBH and HYXMCK.F_CLBH=DB.F_CLBH AND HYXMCK.F_CSBH = DB.F_CSBH AND "
			       + " DB.F_CRFX = 'D' AND HYXMCK.F_CLDJ = DB.F_CLDJ "
			       + " where HYXMCK.F_CLBH = '" + po.GetValueByParamName("F_CLBH", "") + "'"
			       + " and HYXMCK.F_CRFX='R' "
			       + " and HYXMCK.F_XMBH like '%" + po.GetValueByParamName("F_XMBH", "") + "%'"
			       + " and HYXMCK.F_CPBH like '%" + po.GetValueByParamName("F_CPBH", "") + "%'"
			       + " and HYXMCK.F_DWBH = '" + po.GetValueByParamName("F_DWBH", "") + "'"
			       + " and HYXMCK.F_CSBH = '" + po.GetValueByParamName("F_CSBH", "") + "'"
			       + " and HYXMCK.F_CKBH = '" + po.GetValueByParamName("F_CKBH", "") + "'"
			       + " and HYXMCK.F_CPBH like '%" + po.GetValueByParamName("F_CPBH", "") + "%'"
			       + " and HYXMCK.F_CLDJ = " + F_CLDJ 
			       + " and exists(select 1 from HYXMZD where HYXMCK.F_XMBH = HYXMZD.F_XMBH and HYXMZD.F_XMZT = '1')"
			       + " GROUP BY HYXMCK.F_XMBH, HYXMCK.F_CPBH";
			System.out.println("projectMaterialCarryover Sql|||" + strSql);
			rs = stmt.executeQuery(strSql);
			queryDataset = DataSetUtils.resultSet2DataSet(rs, queryDataset);
			
			if(pageQuery == null) {
				convertDataSet(queryDataset);
				return queryDataset;
			}
			pageQuery.setTotalCount(queryDataset.getRowCount());
			newDataset = EMPLimitConvertUtil.getDataSetLimit(queryDataset, pageQuery.getPageNum(), pageQuery.getNumPerPage());
			
			convertDataSet(newDataset);
		} catch(Exception ce) {
			ce.printStackTrace();
		}
		return newDataset;
	}
	
	public void convertDataSet(EFDataSet queryDataSet) {
		EFRowSet convertRowSet = EFRowSet.getInstance();
		EFRowSet    itemRowSet = null;
		double          F_RKSL = 0.0;
		double        F_CLTHSL = 0.0;
		double        F_CSTHSL = 0.0;
		double          F_DBSL = 0.0;
		double          F_CKSL = 0.0;
		double          F_JZSL = 0.0;
		double            rksl = 0.0;
		double            thsl = 0.0;
		double            cksl = 0.0;
		double            dbsl = 0.0;
		
		if(queryDataSet.getRowCount() <= 0) return;
			
		convertRowSet.putString("F_CPBH", "合计");

		for(int i = 0; i < queryDataSet.getRowCount(); i++) {
			itemRowSet = queryDataSet.getRowSet(i);
			rksl = itemRowSet.getNumber("F_RKSL", 0.0).doubleValue();
			thsl = itemRowSet.getNumber("F_CLTHSL", 0.0).doubleValue();
			cksl = itemRowSet.getNumber("F_CKSL", 0.0).doubleValue();
			dbsl = itemRowSet.getNumber("F_DBSL", 0.0).doubleValue();
			
			itemRowSet.putNumber("F_JZSL", rksl + thsl - cksl - dbsl);
			
			F_RKSL = convertRowSet.getNumber("F_RKSL", 0.0).doubleValue() + itemRowSet.getNumber("F_RKSL", 0.0).doubleValue();
			F_CLTHSL = convertRowSet.getNumber("F_CLTHSL", 0.0).doubleValue() + itemRowSet.getNumber("F_CLTHSL", 0.0).doubleValue();
			F_CSTHSL = convertRowSet.getNumber("F_CSTHSL", 0.0).doubleValue() + itemRowSet.getNumber("F_CSTHSL", 0.0).doubleValue();
			F_CKSL = convertRowSet.getNumber("F_CKSL", 0.0).doubleValue() + itemRowSet.getNumber("F_CKSL", 0.0).doubleValue();
			F_JZSL = convertRowSet.getNumber("F_JZSL", 0.0).doubleValue() + itemRowSet.getNumber("F_JZSL", 0.0).doubleValue();
			F_DBSL = convertRowSet.getNumber("F_DBSL", 0.0).doubleValue() + itemRowSet.getNumber("F_DBSL", 0.0).doubleValue();
			
			convertRowSet.putNumber("F_RKSL", F_RKSL);
			convertRowSet.putNumber("F_CLTHSL", F_CLTHSL);
			convertRowSet.putNumber("F_CSTHSL", F_CSTHSL);
			convertRowSet.putNumber("F_CKSL", F_CKSL);
			convertRowSet.putNumber("F_JZSL", F_JZSL);
			convertRowSet.putNumber("F_DBSL", F_DBSL);
			
		}
		queryDataSet.insertRowSet(convertRowSet);
	}
}
