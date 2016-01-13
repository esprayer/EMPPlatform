package com.mrp.biz.queryAnalyse.query;

import java.sql.ResultSet;
import java.sql.Statement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.jdbc.support.JdbcUtils;
import org.springframework.stereotype.Repository;

import esyt.framework.com.bridge.JResponseObject;
import esyt.framework.com.builder.base.data.DataSetUtils;
import esyt.framework.com.builder.base.data.EFDataSet;
import esyt.framework.com.builder.base.data.EFRowSet;
import esyt.framework.com.core.foundation.sql.JConnection;
import esyt.framework.com.core.foundation.sql.JStatement;
import esyt.framework.com.eai.data.JParamObject;

@Repository
public class EMPSystemHomeQuery {

	@Autowired
	private JdbcTemplate                  jdbcTemplate;
	
	protected     String                   msTableName = "";
	
	public JResponseObject QueryObject(JParamObject PO){
		JConnection         conn = null;
		JStatement          stmt = null;
		EFRowSet          rowset = EFRowSet.getInstance();
		JResponseObject       RO = null;
		try {
			conn = JConnection.getInstance(jdbcTemplate.getDataSource().getConnection());
			conn.setAutoCommit(true);
			stmt = conn.createStatement();
			// 查询数据
			queryDataObject(stmt, PO, rowset);
			// 封装返回数据
		   	RO = new JResponseObject(rowset, 0, null);
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
	 * 查询数据
	 * @throws Exception
	 * @return TreeTableDataManager
	 */
	private EFDataSet queryDataObject(Statement stmt, JParamObject po, EFRowSet rowset) throws Exception{
		EFDataSet   queryDataset = null;
	    //取出原始数据
		if(po.GetValueByParamName("CXFLAG", "").equals("ALL") || po.GetValueByParamName("CXFLAG", "").equals("CLYJ")) {
			queryWarningMaterialDataObject(stmt, po, rowset);
		}
		if(po.GetValueByParamName("CXFLAG", "").equals("ALL") || po.GetValueByParamName("CXFLAG", "").equals("HYRKD")) {
			queryStorageFormDataObject(stmt, po, rowset);
		}
		
		if(po.GetValueByParamName("CXFLAG", "").equals("ALL") || po.GetValueByParamName("CXFLAG", "").equals("HYCKD")) {
			queryOutBoundFormDataObject(stmt, po, rowset);
		}
		
		if(po.GetValueByParamName("CXFLAG", "").equals("ALL") || po.GetValueByParamName("CXFLAG", "").equals("HYTHD")) {
			queryBackGoodsFormDataObject(stmt, po, rowset);
		}
		
		if(po.GetValueByParamName("CXFLAG", "").equals("ALL") || po.GetValueByParamName("CXFLAG", "").equals("HYDBD")) {
			queryTransferFormDataObject(stmt, po, rowset);
		}
		
		if(po.GetValueByParamName("CXFLAG", "").equals("ALL") || po.GetValueByParamName("CXFLAG", "").equals("CLCSTH")) {
			queryReturnMaterialDataObject(stmt, po, rowset);
		}
		return queryDataset;
	}
	
	/**
	 * 查询超出预警设置的材料
	 * @throws Exception
	 */
	public void queryWarningMaterialDataObject(Statement stmt, JParamObject po, EFRowSet rowset) throws Exception {
		ResultSet           rs = null;
		EFDataSet queryDataset = null;
		EFRowSet        rowset1 = null;
		String          strSql = "";
		
		strSql = " select HYCLYJ.F_CLBH, HYCLZD.F_CLMC, HYCLZD.F_GGXH, HYCLZD.F_JLDW, "
		       + " HYCLYJ.F_KCXX AS F_KCXX, HYCK.F_CLSL as F_CLSL"
		       + " from HYCLYJ"
		       + " LEFT JOIN HYCLZD ON HYCLYJ.F_CLBH = HYCLZD.F_CLBH"
		       + " INNER JOIN (select F_CLBH, round(sum(F_CLSL), 2) as F_CLSL from HYCK group by F_CLBH) as HYCK ON "	
		       + " HYCK.F_CLBH = HYCLYJ.F_CLBH "
		       + " order by HYCLYJ.F_CLBH";
		System.out.println("SQL|||" + strSql);
		rs = stmt.executeQuery(strSql);
		queryDataset = DataSetUtils.resultSet2DataSet(rs, queryDataset);
		for(int i = 0; i < queryDataset.getRowCount(); i++) {
			rowset1 = queryDataset.getRowSet(i);
			if(rowset1.getNumber("F_KCXX", 0.0).doubleValue() < rowset1.getNumber("F_CLSL", 0.0).doubleValue()) {
				queryDataset.removeRowSet(i);
			}
		}
		rowset.setDataSet("CLYJ", queryDataset);
	}
	
	/**
	 * 查询没有提交的入库单据
	 * @throws Exception
	 */
	public void queryStorageFormDataObject(Statement stmt, JParamObject po, EFRowSet rowset) throws Exception {
		ResultSet           rs = null;
		EFDataSet queryDataset = null;
		String          strSql = "";
		
		//未提交入库单
		strSql = " select HYRKD.F_KJQJ, count(F_DJBH) as F_COUNT"
		       + " from HYRKD"
		       + " where F_DJZT = '0' and F_BZRID = '" + po.GetValueByEnvName("USER_ID", "") + "'"
		       + " group by F_KJQJ "		       
		       + " order by F_KJQJ";
		rs = stmt.executeQuery(strSql);
		queryDataset = DataSetUtils.resultSet2DataSet(rs, queryDataset);
		rowset.setDataSet("HYRKD", queryDataset);		
	}
	
	/**
	 * 查询没有提交的出库单据
	 * @throws Exception
	 */
	public void queryOutBoundFormDataObject(Statement stmt, JParamObject po, EFRowSet rowset) throws Exception {
		ResultSet           rs = null;
		EFDataSet queryDataset = null;
		String          strSql = "";
		
		//未提交出库单
		strSql = " select F_KJQJ, count(F_DJBH) as F_COUNT"
		       + " from HYCKD"
		       + " where F_DJZT = '0' and F_BZRID = '" + po.GetValueByEnvName("USER_ID", "") + "'"
		       + " group by F_KJQJ "		       
		       + " order by F_KJQJ";
		rs = stmt.executeQuery(strSql);
		queryDataset = DataSetUtils.resultSet2DataSet(rs, queryDataset);
		rowset.setDataSet("HYCKD", queryDataset);		
	}
	
	/**
	 * 查询没有提交的退货单据
	 * @throws Exception
	 */
	public void queryBackGoodsFormDataObject(Statement stmt, JParamObject po, EFRowSet rowset) throws Exception {
		ResultSet           rs = null;
		EFDataSet queryDataset = null;
		String          strSql = "";
		
		//未提交退货单
		strSql = " select F_KJQJ, count(F_DJBH) as F_COUNT"
		       + " from HYTHD"
		       + " where F_DJZT = '0' and F_BZRID = '" + po.GetValueByEnvName("USER_ID", "") + "'"
		       + " group by F_KJQJ "		       
		       + " order by F_KJQJ";
		rs = stmt.executeQuery(strSql);
		queryDataset = DataSetUtils.resultSet2DataSet(rs, queryDataset);
		rowset.setDataSet("HYTHD", queryDataset);		
	}
	
	/**
	 * 查询没有提交的调拨单据
	 * @throws Exception
	 */
	public void queryTransferFormDataObject(Statement stmt, JParamObject po, EFRowSet rowset) throws Exception {
		ResultSet           rs = null;
		EFDataSet queryDataset = null;
		String          strSql = "";
		
		//未提交调拨单
		strSql = " select F_KJQJ, count(F_DJBH) as F_COUNT"
		       + " from HYDBD"
		       + " where F_DJZT = '0' and F_BZRID = '" + po.GetValueByEnvName("USER_ID", "") + "'"
		       + " group by F_KJQJ "		       
		       + " order by F_KJQJ";
		rs = stmt.executeQuery(strSql);
		queryDataset = DataSetUtils.resultSet2DataSet(rs, queryDataset);
		rowset.setDataSet("HYDBD", queryDataset);
	}
	
	/**
	 * 查询退货材料
	 * @throws Exception
	 */
	public void queryReturnMaterialDataObject(Statement stmt, JParamObject po, EFRowSet rowset) throws Exception {
		ResultSet           rs = null;
		EFDataSet    thDataset = null;
		EFDataSet    rkDataset = null;
		EFRowSet      thRowset = null;
		EFRowSet      rkRowset = null;
		String          strSql = "";
		String[]      primeKey = {"F_CLBH", "F_CSBH"};
		String[]      primeVal = new String[2];
		thDataset = EFDataSet.getInstance(primeKey);
		rkDataset = EFDataSet.getInstance(primeKey);
		
		strSql = " select HYXMCK.F_CLBH, HYXMCK.F_CSBH, HYCLZD.F_CLMC, HYCSZD.F_CSMC,"
		       + " round(sum(F_CLSL), 2) as F_THSL, 0.00 as F_RKSL"
		       + " from HYXMCK"
		       + " LEFT JOIN HYCLZD ON HYXMCK.F_CLBH = HYCLZD.F_CLBH"
		       + " LEFT JOIN HYCSZD ON HYXMCK.F_CSBH = HYCSZD.F_CSBH"
		       + " where HYXMCK.F_CRFX = 'T' and HYXMCK.F_FLLX = '1'"
		       + " group by HYXMCK.F_CLBH, HYXMCK.F_CSBH";
		rs = stmt.executeQuery(strSql);
		thDataset = DataSetUtils.resultSet2DataSet(rs, thDataset);
		System.out.println("SQL|||" + strSql);
		System.out.println("RowCount()|||" + thDataset.getRowCount());
		
		strSql = " select HYXMCK.F_CLBH, HYXMCK.F_CSBH, HYCLZD.F_CLMC, HYCSZD.F_CSMC,"
		       + " round(sum(F_CLSL), 2) as F_RKSL, 0.00 as F_THSL"
		       + " from HYXMCK"
		       + " LEFT JOIN HYCLZD ON HYXMCK.F_CLBH = HYCLZD.F_CLBH"
		       + " LEFT JOIN HYCSZD ON HYXMCK.F_CSBH = HYCSZD.F_CSBH"
		       + " where HYXMCK.F_CRFX = 'R' and HYXMCK.F_FLLX = '1'"
		       + " group by HYXMCK.F_CLBH, HYXMCK.F_CSBH";
		rs = stmt.executeQuery(strSql);
		rkDataset = DataSetUtils.resultSet2DataSet(rs, rkDataset);
		
		for(int i = thDataset.getRowCount() - 1; thDataset.getRowCount() > 0 && i < thDataset.getRowCount(); i--) {
			thRowset = thDataset.getRowSet(i);
			primeVal = new String[2];
			primeVal[0] = thRowset.getString("F_CLBH", "");
			primeVal[1] = thRowset.getString("F_CSBH", "");
			if(rkDataset.getRowSet(primeVal) == null) continue;
			rkRowset = (EFRowSet) rkDataset.getRowSet(primeVal);
			if(thRowset.getNumber("F_THSL", 0.0).doubleValue() == rkRowset.getNumber("F_RKSL", 0.0).doubleValue()) {
				thDataset.remove(i);
			} else {
				thRowset.putNumber("F_RKSL", rkRowset.getNumber("F_RKSL", 0.0));
				thRowset.putNumber("F_JHSL", thRowset.getNumber("F_THSL", 0.0).doubleValue() - rkRowset.getNumber("F_RKSL", 0.0).doubleValue());
			}
		}
		rowset.setDataSet("CLCSTH", thDataset);
	}
}
