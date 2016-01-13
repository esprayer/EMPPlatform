package com.mrp.biz.queryAnalyse.query;

import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

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
public class EMPProjectMaterialQuery {

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
		String            strBeginDate = po.GetValueByParamName("beginDate", "") + " 00:00:00";
		String            strEndDate = po.GetValueByParamName("endDate", "");
		String            F_CKBH = po.GetValueByParamName("F_CKBH", "");
		String            F_XMBH = po.GetValueByParamName("F_XMBH", "");
		String            F_CLBH = po.GetValueByParamName("F_CLBH", "");
		DateFormat        format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
		DateFormat       format1 = new SimpleDateFormat("yyyy-MM-dd");
		Date           beginDate = format.parse(strBeginDate);
		Date             endDate = format1.parse(po.GetValueByParamName("endDate", "")); 
		Calendar        calendar = Calendar.getInstance();   
		calendar.setTime(endDate); 
		System.out.println(calendar.get(Calendar.DAY_OF_MONTH));//截止日期 
		calendar.set(Calendar.DAY_OF_MONTH,calendar.get(Calendar.DAY_OF_MONTH)+1);//让日期加1  
		System.out.println(calendar.get(Calendar.DATE));//加1之后的日期Top 
		
		endDate = calendar.getTime();
		strEndDate = format1.format(endDate) + " 00:00:00";
		endDate = format.parse(strEndDate);
		try {
			//插入入库数量
			strSql = " insert into " + msTableName + " (F_ID, F_CRFX, F_CKBH, F_XMBH, F_CLBH, F_DWBH, F_CSBH, F_CLDJ, F_CLSL)"
			       + " select '0', F_CRFX, F_CKBH, F_XMBH, F_CLBH, F_DWBH, F_CSBH, F_CLDJ,"
			       + " round(sum(F_CLSL), 2)"
			       + " from HYXMCK"
			       + " where HYXMCK.F_CLBH like '" + F_CLBH + "%'"		       
			       + " and HYXMCK.F_CRFX = 'R'"
			       + " and HYXMCK.F_CKBH like '" + F_CKBH + "%'"
			       + " and HYXMCK.F_XMBH like '" + F_XMBH + "%'"
			       + " and HYXMCK.F_CHDATE < " + endDate.getTime() + " and HYXMCK.F_CHDATE >= " + beginDate.getTime()
			       + " group by F_CKBH, F_CLBH, F_DWBH, F_XMBH, F_CSBH, F_CLDJ";
			stmt.execute(strSql);
			
			//插入正常出库和调拨数量
			strSql = " insert into " + msTableName + " (F_ID, F_CRFX, F_CKBH, F_XMBH, F_CLBH, F_DWBH, F_CSBH, F_FLLX, F_CLDJ, F_CLSL)"
			       + " select '0', F_CRFX, F_YYCKBH, F_YYXMBH, F_CLBH, F_DWBH, F_CSBH, F_FLLX, F_CLDJ,"
			       + " round(sum(F_CLSL), 2)"
			       + " from HYXMCK"
			       + " where HYXMCK.F_CLBH like '" + F_CLBH + "%'"		       
			       + " and HYXMCK.F_CRFX = 'C' and F_FLLX = '0'"
			       + " and HYXMCK.F_YYCKBH like '" + F_CKBH + "%'"
			       + " and HYXMCK.F_YYXMBH like '" + F_XMBH + "%'"
			       + " and HYXMCK.F_CHDATE < " + endDate.getTime() + " and HYXMCK.F_CHDATE >= " + beginDate.getTime()
			       + " group by F_CRFX, F_YYCKBH, F_CLBH, F_YYXMBH, F_DWBH, F_CSBH, F_FLLX, F_CLDJ";
			stmt.execute(strSql);
			
			//插入借调出库和调拨数量
			strSql = " insert into " + msTableName + " (F_ID, F_CRFX, F_CKBH, F_XMBH, F_CLBH, F_DWBH, F_CSBH, F_FLLX, F_CLDJ, F_CLSL)"
			       + " select '0', F_CRFX, F_CKBH, F_XMBH, F_CLBH, F_DWBH, F_CSBH, F_FLLX, F_CLDJ,"
			       + " round(sum(F_CLSL), 2)"
			       + " from HYXMCK"
			       + " where HYXMCK.F_CLBH like '" + F_CLBH + "%'"		       
			       + " and HYXMCK.F_CRFX = 'C' and F_FLLX = '1'"
			       + " and HYXMCK.F_CKBH like '" + F_CKBH + "%'"
			       + " and HYXMCK.F_XMBH like '" + F_XMBH + "%'"
			       + " and HYXMCK.F_CHDATE < " + endDate.getTime() + " and HYXMCK.F_CHDATE >= " + beginDate.getTime()
			       + " group by F_CRFX, F_CKBH, F_CLBH, F_XMBH, F_DWBH, F_CSBH, F_FLLX, F_CLDJ";
			stmt.execute(strSql);
			
			//插入正常出库和调拨数量
			strSql = " insert into " + msTableName + " (F_ID, F_CRFX, F_CKBH, F_XMBH, F_CLBH, F_DWBH, F_CSBH, F_FLLX, F_CLDJ, F_CLSL)"
			       + " select '0', F_CRFX, F_YYCKBH, F_YYXMBH, F_CLBH, F_DWBH, F_CSBH, F_FLLX, F_CLDJ,"
			       + " round(sum(F_CLSL), 2)"
			       + " from HYXMCK"
			       + " where HYXMCK.F_CLBH like '" + F_CLBH + "%'"		       
			       + " and (HYXMCK.F_CRFX = 'D' or HYXMCK.F_CRFX = 'T')"
			       + " and HYXMCK.F_YYCKBH like '" + F_CKBH + "%'"
			       + " and HYXMCK.F_YYXMBH like '" + F_XMBH + "%'"
			       + " and HYXMCK.F_CHDATE < " + endDate.getTime() + " and HYXMCK.F_CHDATE >= " + beginDate.getTime()
			       + " group by F_CRFX, F_YYCKBH, F_CLBH, F_YYXMBH, F_DWBH, F_CSBH, F_FLLX, F_CLDJ";
			stmt.execute(strSql);
		} catch(Exception ce) {
			ce.printStackTrace();
		}
	}
	  
	/**
	   * 创建临时表
	   * @throws Exception
	   */
	  private void createTempTable(Statement stmt) throws Exception{
	    msTableName = "CLMXCX"+StringFunction.getTempStr(6);
	    String        strSql = "";
	    
	    String tempTableBody = " F_ID          varchar(1)           default ''   null,"
	    		             + " F_CKBH        varchar(255)         default ''   null,"
	         				 + " F_CKMC        varchar(255)         default ''   null,"
	         				 + " F_CLBH        varchar(255)         default ''   null,"
	         				 + " F_XMMC        varchar(255)         default ''   null,"
	         				 + " F_XMBH        varchar(255)         default ''   null,"
	         				 + " F_CLMC        varchar(255)         default ''   null,"
	         				 + " F_DWBH        varchar(255)         default ''   null,"
	         				 + " F_DWMC        varchar(255)         default ''   null,"
	         				 + " F_CSBH        varchar(255)         default ''   null,"
	         				 + " F_CSMC        varchar(255)         default ''   null,"
	         				 + " F_GGXH        varchar(255)         default ''   null,"
	         				 + " F_JLDW        varchar(255)         default ''   null,"
	         				 + " F_CRFX        varchar(255)         default ''   null,"
	         				 + " F_DJLX        varchar(255)         default ''   null,"
	         				 + " F_FLLX        varchar(255)         default ''   null,"
	         				 + " F_CLDJ        decimal(30,2)	    default 0    null,"
	         				 + " F_CLSL        decimal(30,2)   	    default 0    null";
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
		String            strSql = "";
		String[]        primeKey = {"F_BH"};
		EFDataSet        dataset = EFDataSet.getInstance();
		EFDataSet     newDataset = EFDataSet.getInstance();
		EFRowSet          rowset = null;
		EFRowSet      tempRowset = null;
		EFRowSet       hjRrowset = EFRowSet.getInstance();
		EMPPageQuery   pageQuery = (EMPPageQuery) po.getValue(EMPQueryParamEnum.PAGEQUERY, "");
		dataset.setPrimeKey(primeKey);
		
		//汇总数据
		strSql = " insert into " + msTableName + " (F_ID, F_CRFX, F_CKBH, F_XMBH, F_CLBH, F_DWBH, F_CSBH, F_FLLX, F_CLDJ, F_CLSL)"
		       + " select '1', F_CRFX, F_CKBH, F_XMBH, F_CLBH, F_DWBH, F_CSBH, F_FLLX, F_CLDJ,"
		       + " round(sum(F_CLSL), 2)"
		       + " from " + msTableName
		       + " where F_ID = '0' "		       
		       + " group by F_CRFX, F_CKBH, F_XMBH, F_CLBH, F_DWBH, F_CSBH, F_FLLX, F_CLDJ";
		stmt.execute(strSql);
		
		//删除汇总前数据
		strSql = " delete from " + msTableName + " where F_ID = '0'";
		stmt.execute(strSql);
		
		//更新所属仓库名称
		strSql = " update " + msTableName + " TEMP"
	           + " INNER JOIN HYCKZD ON TEMP.F_CKBH = HYCKZD.F_CKBH set TEMP.F_CKMC = HYCKZD.F_CKMC" ;
		stmt.execute(strSql);
		
		//更新所属项目名称
		strSql = " update " + msTableName + " TEMP"
	           + " INNER JOIN HYXMZD ON TEMP.F_XMBH = HYXMZD.F_XMBH set TEMP.F_XMMC = HYXMZD.F_XMMC" ;
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

		strSql = " select concat(F_CKBH, '-', F_CLBH, '-', F_XMBH, '-', F_DWBH, '-', F_CSBH, '-', F_CLDJ) as F_BH, F_CKBH, F_CKMC, F_XMBH, F_XMMC, F_CLBH, F_CLMC, F_GGXH, F_JLDW, F_DWBH, F_DWMC, F_CSBH, F_CSMC, "
			   + " F_CRFX, F_FLLX, round(F_CLDJ, 2) as F_CLDJ, round(F_CLSL, 2) as F_CLSL, round(F_CLDJ * F_CLSL, 2) as F_CLZJ from " + msTableName + " order by F_CKBH, F_CLBH";
		rs = stmt.executeQuery(strSql);
		queryDataset = DataSetUtils.resultSet2DataSet(rs, queryDataset);
		
		
		
		hjRrowset.putString("F_CKBH", "合计");
		for(int i = 0; i < queryDataset.getRowCount(); i++) {
			rowset = queryDataset.getRowSet(i);
			tempRowset = (EFRowSet) dataset.getRowSet(rowset.getString("F_BH", ""));
			if(rowset.getString("F_CRFX", "").equals("R")) {
				hjRrowset.putNumber("F_RKSL", hjRrowset.getNumber("F_RKSL", 0.0).doubleValue() + rowset.getNumber("F_CLSL", 0.0).doubleValue());
			} else if(rowset.getString("F_CRFX", "").equals("C")) {
				hjRrowset.putNumber("F_CKSL", hjRrowset.getNumber("F_CKSL", 0.0).doubleValue() + rowset.getNumber("F_CLSL", 0.0).doubleValue());
			} else if(rowset.getString("F_CRFX", "").equals("D")) {
				hjRrowset.putNumber("F_DBSL", hjRrowset.getNumber("F_DBSL", 0.0).doubleValue() + rowset.getNumber("F_CLSL", 0.0).doubleValue());
			} else if(rowset.getString("F_CRFX", "").equals("T")) {
				if(rowset.getString("F_FLLX", "").equals("0")) {
					hjRrowset.putNumber("F_CLTHSL", hjRrowset.getNumber("F_CLTHSL", 0.0).doubleValue() + rowset.getNumber("F_CLSL", 0.0).doubleValue());
				} else if(rowset.getString("F_FLLX", "").equals("1")) {
					hjRrowset.putNumber("F_CSTHSL", hjRrowset.getNumber("F_CSTHSL", 0.0).doubleValue() + rowset.getNumber("F_CLSL", 0.0).doubleValue());
				}					
			}
			hjRrowset.putNumber("F_CLZJ", hjRrowset.getNumber("F_CLZJ", 0.0).doubleValue() + rowset.getNumber("F_CLZJ", 0.0).doubleValue());
			if(tempRowset == null) {
				if(rowset.getString("F_CRFX", "").equals("R")) {
					rowset.putNumber("F_RKSL", rowset.getNumber("F_CLSL", 0.0));					
				} else if(rowset.getString("F_CRFX", "").equals("C")) {
					//正常出库
					if(rowset.getString("F_FLLX", "0").equals("0")) {
						rowset.putNumber("F_ZCCKSL", rowset.getNumber("F_CLSL", 0.0));	
					}
					//借调出库
					if(rowset.getString("F_FLLX", "0").equals("1")) {
						rowset.putNumber("F_JDCKSL", rowset.getNumber("F_CLSL", 0.0));	
					}					
				} else if(rowset.getString("F_CRFX", "").equals("D")) {
					rowset.putNumber("F_DBSL", rowset.getNumber("F_CLSL", 0.0));					
				} else if(rowset.getString("F_CRFX", "").equals("T")) {
					if(rowset.getString("F_FLLX", "").equals("0")) {
						rowset.putNumber("F_CLTHSL", rowset.getNumber("F_CLSL", 0.0));
					} else if(rowset.getString("F_FLLX", "").equals("1")) {
						rowset.putNumber("F_CSTHSL", rowset.getNumber("F_CLSL", 0.0));
					}					
				}
				dataset.insertRowSet(rowset);		
				// 形成主键索引
				dataset.buildPrimeKeyIndex(rowset);
			} else {
				if(rowset.getString("F_CRFX", "").equals("R")) {
					tempRowset.putNumber("F_RKSL", rowset.getNumber("F_CLSL", 0.0));					
				} else if(rowset.getString("F_CRFX", "").equals("C")) {
					//正常出库
					if(rowset.getString("F_FLLX", "0").equals("0")) {
						tempRowset.putNumber("F_ZCCKSL", rowset.getNumber("F_CLSL", 0.0));	
					}
					//借调出库
					if(rowset.getString("F_FLLX", "0").equals("1")) {
						tempRowset.putNumber("F_JDCKSL", rowset.getNumber("F_CLSL", 0.0));	
					}					
				} else if(rowset.getString("F_CRFX", "").equals("D")) {
					tempRowset.putNumber("F_DBSL", rowset.getNumber("F_CLSL", 0.0));					
				} else if(rowset.getString("F_CRFX", "").equals("T")) {
					if(rowset.getString("F_FLLX", "").equals("0")) {
						tempRowset.putNumber("F_CLTHSL", rowset.getNumber("F_CLSL", 0.0));
					} else if(rowset.getString("F_FLLX", "").equals("1")) {
						tempRowset.putNumber("F_CSTHSL", rowset.getNumber("F_CLSL", 0.0));
					}
				}
			}
		}
		
		if(pageQuery == null) return dataset;
		pageQuery.setTotalCount(dataset.getRowCount());
		newDataset = EMPLimitConvertUtil.getDataSetLimit(dataset, pageQuery.getPageNum(), pageQuery.getNumPerPage());
		
//		if(newDataset.getRowCount() > 0) newDataset.insertRowSet(hjRrowset);
		
		return newDataset;
	}
}
