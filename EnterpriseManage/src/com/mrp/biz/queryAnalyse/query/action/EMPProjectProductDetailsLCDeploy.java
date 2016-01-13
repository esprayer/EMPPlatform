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

@Repository
public class EMPProjectProductDetailsLCDeploy {

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
	 * 取出原始数据
	 * @throws Exception
	 */
	public void getOriginData(Statement stmt, JParamObject po) throws Exception {
		String            strSql ="";
		String            F_XMBH = po.GetValueByParamName("F_XMBH", "");
		String            F_CLBH = po.GetValueByParamName("F_CLBH", "");
		String            F_CPBH = po.GetValueByParamName("F_CPBH", "");
		String      strBeginDate = po.GetValueByParamName("beginDate", "");
		String        strEndDate = po.GetValueByParamName("endDate", "");
		
		try {

			//插入入库数量
			strSql = " insert into " + msTableName + " (F_KJQJ, F_DJBH, F_FLBH, F_CRFX, F_CKBH, F_XMBH, F_CPBH, F_CLBH, F_DWBH, F_CSBH, F_CLDJ, F_CLSL)"
			       + " select F_KJQJ, F_DJBH, F_FLBH, F_CRFX, F_CKBH, F_XMBH, F_CPBH, F_CLBH, F_DWBH, F_CSBH, F_CLDJ,"
			       + " round(sum(F_CLSL), 2)"
			       + " from HYXMCK"
			       + " where HYXMCK.F_CLBH like '%" + F_CLBH + "%' and HYXMCK.F_CPBH like '%" + F_CPBH + "%'"
			       + " and HYXMCK.F_XMBH = '" + F_XMBH + "' and HYXMCK.F_CRFX = 'R'";
			if(po.GetValueByParamName("CX", "").equals("CP")) {
				strSql += " and exists(select 1 from HYXMZD where HYXMZD.F_XMBH = HYXMCK.F_XMBH and "
						+ " (HYXMZD.F_SQSJ >= '" + strBeginDate + "' AND HYXMZD.F_SQSJ <= '" + strEndDate + "'))"; 
			}
			strSql += " group by F_KJQJ, F_DJBH, F_CKBH, F_XMBH, F_CPBH, F_CLBH, F_DWBH, F_CSBH, F_CLDJ";
			stmt.execute(strSql);
			
			//插入退货和调拨数量
			strSql = " insert into " + msTableName + " (F_KJQJ, F_DJBH, F_FLBH, F_CRFX, F_CKBH, F_XMBH, F_CPBH, F_YYCKBH, F_YYXMBH, F_YYCPBH, F_CLBH, F_DWBH, F_CSBH, F_CLDJ, F_CLSL)"
			       + " select F_KJQJ, F_DJBH, F_FLBH, F_CRFX, F_CKBH, F_XMBH, F_CPBH, F_YYCKBH, F_YYXMBH, F_YYCPBH, F_CLBH, F_DWBH, F_CSBH, F_CLDJ,"
			       + " round(sum(F_CLSL), 2)"
			       + " from HYXMCK"
			       + " where HYXMCK.F_CLBH like '%" + F_CLBH + "%' and HYXMCK.F_YYCPBH like '%" + F_CPBH + "%'"
			       + " and HYXMCK.F_YYXMBH = '" + F_XMBH + "' and (HYXMCK.F_CRFX = 'D' or HYXMCK.F_CRFX = 'T')";
			if(po.GetValueByParamName("CX", "").equals("CP")) {
				strSql += " and exists(select 1 from HYXMZD where HYXMZD.F_XMBH = HYXMCK.F_YYXMBH and "
						+ " (HYXMZD.F_SQSJ >= '" + strBeginDate + "' AND HYXMZD.F_SQSJ <= '" + strEndDate + "'))"; 
			}
			strSql += " group by F_KJQJ, F_DJBH, F_CRFX, F_CKBH, F_XMBH, F_CPBH, F_YYCKBH, F_YYXMBH, F_YYCPBH, F_CLBH, F_DWBH, F_CSBH, F_CLDJ";
			stmt.execute(strSql);
			
			//插入正常出库数量
			strSql = " insert into " + msTableName + " (F_KJQJ, F_DJBH, F_FLBH, F_CRFX, F_CKBH, F_XMBH, F_CPBH, F_YYCKBH, F_YYXMBH, F_YYCPBH, F_CLBH, F_DWBH, F_CSBH, F_CLDJ, F_CLSL, F_DJLX)"
			       + " select F_KJQJ, F_DJBH, F_FLBH, F_CRFX, F_CKBH, F_XMBH, F_CPBH, F_YYCKBH, F_YYXMBH, F_YYCPBH, F_CLBH, F_DWBH, F_CSBH, F_CLDJ,"
			       + " round(sum(F_CLSL), 2), '0'"
			       + " from HYXMCK"
			       + " where HYXMCK.F_CLBH like '%" + F_CLBH + "%' and HYXMCK.F_CPBH like '%" + F_CPBH + "%'"
			       + " and HYXMCK.F_XMBH = '" + F_XMBH + "' and HYXMCK.F_CRFX = 'C' and HYXMCK.F_FLLX = '0'";
			if(po.GetValueByParamName("CX", "").equals("CP")) {
				strSql += " and exists(select 1 from HYXMZD where HYXMZD.F_XMBH = HYXMCK.F_YYXMBH and "
						+ " (HYXMZD.F_SQSJ >= '" + strBeginDate + "' AND HYXMZD.F_SQSJ <= '" + strEndDate + "'))"; 
			}
			strSql += " group by F_KJQJ, F_DJBH, F_CRFX, F_CKBH, F_XMBH, F_CPBH, F_YYCKBH, F_YYXMBH, F_YYCPBH, F_CLBH, F_DWBH, F_CSBH, F_CLDJ";
			stmt.execute(strSql);
			
			//插入借调出库数量
			strSql = " insert into " + msTableName + " (F_KJQJ, F_DJBH, F_FLBH, F_CRFX, F_CKBH, F_XMBH, F_CPBH, F_YYCKBH, F_YYXMBH, F_YYCPBH, F_CLBH, F_DWBH, F_CSBH, F_CLDJ, F_CLSL, F_DJLX)"
			       + " select F_KJQJ, F_DJBH, F_FLBH, F_CRFX, F_CKBH, F_XMBH, F_CPBH, F_YYCKBH, F_YYXMBH, F_YYCPBH, F_CLBH, F_DWBH, F_CSBH, F_CLDJ,"
			       + " round(sum(F_CLSL), 2), '1'"
			       + " from HYXMCK"
			       + " where HYXMCK.F_CLBH like '%" + F_CLBH + "%' and HYXMCK.F_YYCPBH like '%" + F_CPBH + "%'"
			       + " and HYXMCK.F_YYXMBH = '" + F_XMBH + "' and HYXMCK.F_CRFX = 'C' and HYXMCK.F_FLLX = '1'";
			if(po.GetValueByParamName("CX", "").equals("CP")) {
				strSql += " and exists(select 1 from HYXMZD where HYXMZD.F_XMBH = HYXMCK.F_YYXMBH and "
						+ " (HYXMZD.F_SQSJ >= '" + strBeginDate + "' AND HYXMZD.F_SQSJ <= '" + strEndDate + "'))"; 
			}
			strSql += " group by F_KJQJ, F_DJBH, F_CRFX, F_CKBH, F_XMBH, F_CPBH, F_YYCKBH, F_YYXMBH, F_YYCPBH, F_CLBH, F_DWBH, F_CSBH, F_CLDJ";
			stmt.execute(strSql);
			
			//插入被借调出库数量
			strSql = " insert into " + msTableName + " (F_KJQJ, F_DJBH, F_FLBH, F_CRFX, F_CKBH, F_XMBH, F_CPBH, F_YYCKBH, F_YYXMBH, F_YYCPBH, F_CLBH, F_DWBH, F_CSBH, F_CLDJ, F_CLSL, F_DJLX)"
			       + " select F_KJQJ, F_DJBH, F_FLBH, F_CRFX, F_CKBH, F_XMBH, F_CPBH, F_YYCKBH, F_YYXMBH, F_YYCPBH, F_CLBH, F_DWBH, F_CSBH, F_CLDJ,"
			       + " round(sum(F_CLSL), 2), '2'"
			       + " from HYXMCK"
			       + " where HYXMCK.F_CLBH like '%" + F_CLBH + "%' and HYXMCK.F_CPBH like '%" + F_CPBH + "%'"
			       + " and HYXMCK.F_XMBH = '" + F_XMBH + "' and HYXMCK.F_CRFX = 'C' and HYXMCK.F_FLLX = '1'";
			if(po.GetValueByParamName("CX", "").equals("CP")) {
				strSql += " and exists(select 1 from HYXMZD where HYXMZD.F_XMBH = HYXMCK.F_YYXMBH and "
						+ " (HYXMZD.F_SQSJ >= '" + strBeginDate + "' AND HYXMZD.F_SQSJ <= '" + strEndDate + "'))"; 
			}
			strSql += " group by F_KJQJ, F_DJBH, F_CRFX, F_CKBH, F_XMBH, F_CPBH, F_YYCKBH, F_YYXMBH, F_YYCPBH, F_CLBH, F_DWBH, F_CSBH, F_CLDJ";
			stmt.execute(strSql);
		} catch(Exception ce) {
			ce.printStackTrace();
		}
	}
	
	/**
	 * 创建临时表
	 * @throws Exception
	 */
	protected void createTempTable(Statement stmt) throws Exception{
	    msTableName = "XMCPCL"+StringFunction.getTempStr(6);
	    String        strSql = "";
	    
	    String tempTableBody = " F_KJQJ        varchar(90)          default ''   not null,"
	         				 + " F_DJBH        varchar(255)         default ''   not null,"
	         				 + " F_FLBH        varchar(255)         default ''   not null,"
	         				 + " F_CKBH        varchar(255)         default ''   null,"
	         				 + " F_CKMC        varchar(255)         default ''   null,"
	         				 + " F_XMBH        varchar(30) 			default ''   null,"
	         				 + " F_XMMC        varchar(255) 		default ''   null,"
	         				 + " F_CPBH        varchar(255)        	default ''   null,"
	         				 + " F_CPMC        varchar(255)     	default ''   null,"
	         				 + " F_YYXMBH      varchar(255) 		default ''   null,"
	         				 + " F_YYXMMC      varchar(255) 		default ''   null,"
	         				 + " F_YYCKBH      varchar(255)        	default ''   null,"
	         				 + " F_YYCKMC      varchar(255)         default ''   null,"
	         				 + " F_YYCPBH      varchar(255)         default ''   null,"
	         				 + " F_YYCPMC      varchar(255)         default ''   null,"
	         				 + " F_CLBH        varchar(255)         default ''   not null,"
	         				 + " F_CLMC        varchar(255)         default ''   not null,"
	         				 + " F_DWBH        varchar(255)         default ''   not null,"
	         				 + " F_DWMC        varchar(255)         default ''   not null,"
	         				 + " F_CSBH        varchar(255)         default ''   not null,"
	         				 + " F_CSMC        varchar(255)         default ''   not null,"
	         				 + " F_GGXH        varchar(255)         default ''   not null,"
	         				 + " F_JLDW        varchar(255)         default ''   not null,"
	         				 + " F_CRFX        varchar(255)         default ''   not null,"
	         				 + " F_DJLX        varchar(255)         default ''   not null,"
	         				 + " F_CLDJ        decimal(30,2)	    default 0    null,"
	         				 + " F_CLSL        decimal(30,2)   	    default 0    null,"
	         				 + " F_CLZJ        decimal(30,2)   	    default 0    null,"
	         				 + " F_SBDATE      decimal(30,0)       	default 0    null";
	    strSql = "create table " + msTableName + "(" + tempTableBody + ")";
	    stmt.execute(strSql);
	}
	  
	/**
	 * 逐级汇总数据
	 * @throws Exception
	 */
	public EFDataSet convertDataSet(Statement stmt, JParamObject po) throws Exception {
		ResultSet             rs = null;
		EFDataSet   queryDataset = null;
		EFDataSet     newDataset = EFDataSet.getInstance();
		EFRowSet       hjRrowset = EFRowSet.getInstance();
		EFRowSet          rowset = null;
		String            strSql = "";
		EMPPageQuery   pageQuery = (EMPPageQuery) po.getValue(EMPQueryParamEnum.PAGEQUERY, "");
		
		//更新入库单据的单据类型和提交日期
		strSql = " update " + msTableName + " TEMP INNER JOIN HYRKD ON "
		       + " TEMP.F_KJQJ = HYRKD.F_KJQJ and TEMP.F_DJBH = HYRKD.F_DJBH and TEMP.F_CRFX = 'R' "
		       + " set TEMP.F_DJLX = HYRKD.F_RKLX, TEMP.F_SBDATE = HYRKD.F_SBDATE";
		stmt.execute(strSql);
		
		//更新出库单据的提交日期
		strSql = " update " + msTableName + " TEMP INNER JOIN HYCKD ON "
			   + " TEMP.F_KJQJ = HYCKD.F_KJQJ and TEMP.F_DJBH = HYCKD.F_DJBH and TEMP.F_CRFX = 'C' "
		       + " set TEMP.F_SBDATE = HYCKD.F_SBDATE";
		stmt.execute(strSql);
		
		//更新调拨单据提交日期
		strSql = " update " + msTableName + " TEMP "
			   + " INNER JOIN HYDBD ON TEMP.F_KJQJ = HYDBD.F_KJQJ and TEMP.F_DJBH = HYDBD.F_DJBH and TEMP.F_CRFX = 'D' "
			   + " set TEMP.F_SBDATE = HYDBD.F_SBDATE";
		stmt.execute(strSql);
		
		//更新退货单据的单据类型和提交日期
		strSql = " update " + msTableName + " TEMP "
		       + " INNER JOIN HYTHD ON TEMP.F_KJQJ = HYTHD.F_KJQJ and TEMP.F_DJBH = HYTHD.F_DJBH and TEMP.F_CRFX = 'T' "
		       + " set TEMP.F_DJLX = HYTHD.F_THLX, TEMP.F_SBDATE = HYTHD.F_SBDATE";
		stmt.execute(strSql);
		
		//更新所属项目名称
		strSql = " update " + msTableName + " TEMP"
	           + " INNER JOIN HYXMZD ON TEMP.F_XMBH = HYXMZD.F_XMBH set TEMP.F_XMMC = HYXMZD.F_XMMC" ;
		stmt.execute(strSql);
		
		//更新应用项目名称
		strSql = " update " + msTableName + " TEMP"
               + " INNER JOIN HYXMZD ON TEMP.F_YYXMBH = HYXMZD.F_XMBH set TEMP.F_YYXMMC = HYXMZD.F_XMMC" ;
		stmt.execute(strSql);
		
		//更新所属仓库名称
		strSql = " update " + msTableName + " TEMP"
	           + " INNER JOIN HYCKZD ON TEMP.F_CKBH = HYCKZD.F_CKBH set TEMP.F_CKMC = HYCKZD.F_CKMC" ;
		stmt.execute(strSql);
		
		//更新应用仓库名称
		strSql = " update " + msTableName + " TEMP"
	           + " INNER JOIN HYCKZD ON TEMP.F_YYCKBH = HYCKZD.F_CKBH set TEMP.F_YYCKMC = HYCKZD.F_CKMC" ;
		stmt.execute(strSql);
		
		//更新所属产品名称
		strSql = " update " + msTableName + " TEMP"
	           + " INNER JOIN HYCPZD ON TEMP.F_CPBH = HYCPZD.F_CPBH set TEMP.F_CPMC = HYCPZD.F_CPMC" ;
		stmt.execute(strSql);
		
		//更新应用产品名称
		strSql = " update " + msTableName + " TEMP"
	           + " INNER JOIN HYCPZD ON TEMP.F_YYCPBH = HYCPZD.F_CPBH set TEMP.F_YYCPMC = HYCPZD.F_CPMC" ;
		stmt.execute(strSql);
		
		//更新单位名称
		strSql = " update " + msTableName + " TEMP"
	           + " INNER JOIN HYDWZD ON TEMP.F_DWBH = HYDWZD.F_DWBH set TEMP.F_DWMC = HYDWZD.F_DWMC" ;
		stmt.execute(strSql);
		
		//更新厂商名称
		strSql = " update " + msTableName + " TEMP"
	           + " INNER JOIN HYCSZD ON TEMP.F_CSBH = HYCSZD.F_CSBH set TEMP.F_CSMC = HYCSZD.F_CSMC" ;
		stmt.execute(strSql);
		
		//更新材料名称、规格型号和计量单位
		strSql = " update " + msTableName + " TEMP "
		       + " INNER JOIN HYCLZD ON TEMP.F_CLBH = HYCLZD.F_CLBH "
		       + " set TEMP.F_CLMC = HYCLZD.F_CLMC, TEMP.F_GGXH = HYCLZD.F_GGXH, TEMP.F_JLDW = HYCLZD.F_JLDW";
		stmt.execute(strSql);

		strSql = " select F_KJQJ, F_DJBH, F_FLBH, F_CKBH, F_CKMC, F_XMBH, F_XMMC, F_CPBH, F_CPMC, "
			   + " F_YYCKBH, F_YYCKMC, F_YYXMBH, F_YYXMMC, F_YYCPBH, F_YYCPMC, "
			   + " F_CLBH, F_CLMC, F_GGXH, F_JLDW, F_DWBH, F_DWMC, F_CSBH, F_CSMC, "
			   + " F_CRFX, concat(F_CRFX,F_DJLX) as F_DJLX, F_CLDJ, F_CLSL, round(F_CLSL*F_CLDJ, 2) as F_CLZJ from " + msTableName + " order by F_SBDATE, F_KJQJ, F_DJBH, F_FLBH";
		rs = stmt.executeQuery(strSql);
		queryDataset = DataSetUtils.resultSet2DataSet(rs, queryDataset);

		if(pageQuery == null) return queryDataset;
		
		///
		pageQuery.setTotalCount(queryDataset.getRowCount());
		newDataset = EMPLimitConvertUtil.getDataSetLimit(queryDataset, pageQuery.getPageNum(), pageQuery.getNumPerPage());
		
//		hjRrowset.putString("F_KJQJ", "合计");
//		for(int i = 0; i < queryDataset.getRowCount(); i++) {
//			rowset = queryDataset.getRowSet(i);
//			hjRrowset.putNumber("F_CLSL", hjRrowset.getNumber("F_CLSL", 0.00).doubleValue() + rowset.getNumber("F_CLSL", 0.00).doubleValue());
//		}
//		if(newDataset.getRowCount() > 0) newDataset.insertRowSet(hjRrowset);
//		
		return newDataset;
	}
}
