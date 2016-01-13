package com.mrp.biz.queryAnalyse.query;

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
public class EMPWarehouseMaterialQuery {

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
		String            strSql = "";
		EMPPageQuery   pageQuery = (EMPPageQuery) po.getValue(EMPQueryParamEnum.PAGEQUERY, "");
		
		try {
			strSql = "select HYCK.F_CLBH, HYCLZD.F_CLMC as F_CLMC, HYCLZD.F_GGXH, HYCLZD.F_JLDW, "
				   + " HYCK.F_DWBH, HYDWZD.F_DWMC as F_DWMC, HYCK.F_CSBH, HYCSZD.F_CSMC as F_CSMC, HYCK.F_CLSL, "
				   + " HYCK.F_CLDJ, round(HYCK.F_CLSL * HYCK.F_CLDJ, 2) as F_CLZJ"
				   + " from HYCK "
				   + " LEFT JOIN HYCLZD ON HYCK.F_CLBH = HYCLZD.F_CLBH"
				   + " LEFT JOIN HYCSZD ON HYCK.F_CSBH = HYCSZD.F_CSBH"
				   + " LEFT JOIN HYDWZD ON HYCK.F_DWBH = HYDWZD.F_DWBH"
				   + " where HYCK.F_CKBH = '" + po.GetValueByParamName("F_CKBH", "")
				   + "' and HYCK.F_CLBH like '" + po.GetValueByParamName("F_CLBH", "")
				   + "%' order by F_CLBH";
			
			if(pageQuery != null) strSql = EMPLimitConvertUtil.getSqlLimitString(strSql, pageQuery.getPageNum(), pageQuery.getNumPerPage());

			rs = stmt.executeQuery(strSql);
			queryDataset = DataSetUtils.resultSet2DataSet(rs, queryDataset);
			
			if(pageQuery != null) {
				strSql = "select count(*) as F_COUNT"
					   + " from HYCK "				   
					   + " where HYCK.F_CKBH = '" + po.GetValueByParamName("F_CKBH", "")
					   + "' and HYCK.F_CLBH like '" + po.GetValueByParamName("F_CLBH", "") + "%'";
				rs = stmt.executeQuery(strSql);
				if(rs != null && rs.next()) pageQuery.setTotalCount(rs.getInt("F_COUNT"));
			}
			
			convertDataSet(queryDataset);
			
		} catch(Exception ce) {
			ce.printStackTrace();
		}
		return queryDataset;
	}
	
	public void convertDataSet(EFDataSet queryDataSet) {
		EFRowSet convertRowSet = EFRowSet.getInstance();
		EFRowSet    itemRowSet = null;
		double          F_CLSL = 0.0;
		double          F_CLZJ = 0.0;
		
		if(queryDataSet.getRowCount() <= 0) return;
			
		convertRowSet.putString("F_CLBH", "合计");
		convertRowSet.putNumber("F_CLSL", 0.0);
		convertRowSet.putNumber("F_CLZJ", 0.0);

		for(int i = 0; i < queryDataSet.getRowCount(); i++) {
			itemRowSet = queryDataSet.getRowSet(i);
			F_CLSL = convertRowSet.getNumber("F_CLSL", 0.0).doubleValue() + itemRowSet.getNumber("F_CLSL", 0.0).doubleValue();
			F_CLZJ = convertRowSet.getNumber("F_CLZJ", 0.0).doubleValue() + itemRowSet.getNumber("F_CLZJ", 0.0).doubleValue();
			convertRowSet.putNumber("F_CLSL", F_CLSL);
			convertRowSet.putNumber("F_CLZJ", F_CLZJ);
		}
		queryDataSet.insertRowSet(convertRowSet);
	}
}
