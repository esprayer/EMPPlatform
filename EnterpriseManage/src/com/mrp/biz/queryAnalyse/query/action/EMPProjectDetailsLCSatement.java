package com.mrp.biz.queryAnalyse.query.action;

import java.sql.ResultSet;
import java.sql.Statement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.jdbc.support.JdbcUtils;
import org.springframework.stereotype.Repository;

import com.etsoft.pub.util.StringUtil;

import esyt.framework.com.bridge.JResponseObject;
import esyt.framework.com.builder.base.data.DataSetUtils;
import esyt.framework.com.builder.base.data.EFDataSet;
import esyt.framework.com.builder.base.data.EFRowSet;
import esyt.framework.com.core.foundation.sql.JConnection;
import esyt.framework.com.core.foundation.sql.JStatement;
import esyt.framework.com.eai.data.JParamObject;

/**
 * <p>Title: 项目联查申请单</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2010</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */

@Repository
public class EMPProjectDetailsLCSatement {

	@Autowired
	private JdbcTemplate                  jdbcTemplate;

	public JResponseObject QueryObject(JParamObject PO){
		JConnection         conn = null;
		JStatement          stmt = null;
		EFRowSet       applyForm = null;
		JResponseObject       RO = null;
		try {
			conn = JConnection.getInstance(jdbcTemplate.getDataSource().getConnection());
			conn.setAutoCommit(true);
			stmt = conn.createStatement();
			// 获取查询数据
			applyForm = loadApplyFormHead(stmt, PO);
			loadApplyFormItems(stmt, PO, applyForm);
			// 封装返回数据
		   	RO = new JResponseObject(applyForm, 0, null);
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
	 * 组织项目申请单头信息
	 * @throws Exception
	 * @return TreeTableDataManager
	 */
	private EFRowSet loadApplyFormHead(Statement stmt, JParamObject PO) throws Exception{
		EFRowSet   headRowSet = EFRowSet.getInstance();
		ResultSet          rs = null;
		String         strSql = "select * from HYXMZD where F_XMBH = '" + PO.GetValueByParamName("F_XMBH", "") + "'";
		rs = stmt.executeQuery(strSql);
		if(rs != null && rs.next()) {
			headRowSet = (EFRowSet) DataSetUtils.resultSet2RowSet(rs, headRowSet, true);
			headRowSet.putString("F_SQSJ", StringUtil.formatDate(headRowSet.getString("F_SQSJ", "")));
		} else {
			headRowSet = EFRowSet.getInstance();
		}
		
		return headRowSet;
	}
	
	/**
	 * 组织项目申请单明细信息
	 * @throws Exception
	 * @return TreeTableDataManager
	 */
	private void loadApplyFormItems(Statement stmt, JParamObject PO, EFRowSet applyForm) throws Exception{
		EFDataSet   itemsDataset = null;
		ResultSet             rs = null;
		EFRowSet          rowset = null;
		EFRowSet        hjRowSet = EFRowSet.getInstance();
		double            F_LYSL = 0.0;
		double            F_CLZJ = 0.0;
		double            F_KCSL = 0.0;
		
		String            strSql = " select HYXMMX.F_XMBH, HYXMMX.F_CLBH, HYCLZD.F_CLMC, round(HYXMMX.F_SL, 2) as F_SQSL, "
			                     + " HYCLZD.F_GGXH, HYCLZD.F_JLDW, HYXMMX.F_BZ, HYXMMX.F_KCQK, HYXMMX.F_CRDATE, HYXMMX.F_CHDATE, "
			                     + " IFNULL(round(RK.F_CLSL, 2), 0.0) as F_RKSL, IFNULL(CK.F_CLSL, 0.0) as F_ZCSL, "
			                     + " IFNULL(CLJDCK.F_CLSL, 0.0) as F_JDSL, IFNULL(CLBJDCK.F_CLSL, 0.0) as F_BJDSL, "
			                     + " IFNULL(CLTH.F_CLSL, 0.0) as F_CLTHSL, IFNULL(CSTH.F_CLSL, 0.0) as F_CSTHSL, "
			                     + " IFNULL(CKDB.F_CLSL, 0.0) as F_DBSL, "
			                     + " IFNULL(round(RK.F_CLZJ, 2), 0.0) as F_RKZJ, IFNULL(CK.F_CLZJ, 0.0) as F_ZCZJ, "
			                     + " IFNULL(CLJDCK.F_CLZJ, 0.0) as F_JDZJ, IFNULL(CLBJDCK.F_CLZJ, 0.0) as F_BJDZJ, "
			                     + " IFNULL(CLTH.F_CLZJ, 0.0) as F_CLTHZJ, IFNULL(CSTH.F_CLZJ, 0.0) as F_CSTHZJ, "
			                     + " IFNULL(CKDB.F_CLZJ, 0.0) as F_DBZJ "
		                         + " FROM HYXMMX "
		                         + " LEFT JOIN HYCLZD ON HYXMMX.F_CLBH = HYCLZD.F_CLBH "	
		                         //入库数量
		                         + " LEFT JOIN (select F_CLBH, round(sum(F_CLSL), 2) as F_CLSL, "
		                         + " round(sum(F_CLDJ * F_CLSL), 2) as F_CLZJ from HYXMCK"
		                         + " where HYXMCK.F_XMBH = '" + PO.GetValueByParamName("F_XMBH", "") 
		                         + "' and HYXMCK.F_CRFX = 'R' group by F_CLBH) as RK ON HYXMMX.F_CLBH = RK.F_CLBH"	
		                         //如果借方和被借方项目编号相同，则认为是正常出库
		                         + " LEFT JOIN (select F_CLBH, round(sum(F_CLSL), 2) as F_CLSL, "
		                         + " round(sum(F_CLDJ * F_CLSL), 2) as F_CLZJ from HYXMCK"
		                         + " where HYXMCK.F_XMBH = '" + PO.GetValueByParamName("F_XMBH", "") 
		                         + "' and ((HYXMCK.F_CRFX = 'C' and F_FLLX='0') or (HYXMCK.F_CRFX = 'C' "
		                         + " and F_FLLX='1' and HYXMCK.F_XMBH = HYXMCK.F_YYXMBH))group by F_CLBH) as CK ON HYXMMX.F_CLBH = CK.F_CLBH"
		                         //不同项目之间的调货，被别的项目借调
		                         + " LEFT JOIN (select F_CLBH, round(sum(F_CLSL), 2) as F_CLSL,"
		                         + " round(sum(F_CLDJ * F_CLSL), 2) as F_CLZJ from HYXMCK"
		                         + " where HYXMCK.F_XMBH = '" + PO.GetValueByParamName("F_XMBH", "") 
		                         + "' and HYXMCK.F_CRFX = 'C' and F_FLLX='1' and HYXMCK.F_XMBH <> HYXMCK.F_YYXMBH group by F_CLBH) as CLJDCK ON HYXMMX.F_CLBH = CLJDCK.F_CLBH"
		                         //不同项目之间的调货，从别的项目借调
		                         + " LEFT JOIN (select F_CLBH, round(sum(F_CLSL), 2) as F_CLSL,"
		                         + " round(sum(F_CLDJ * F_CLSL), 2) as F_CLZJ from HYXMCK"
		                         + " where HYXMCK.F_YYXMBH = '" + PO.GetValueByParamName("F_XMBH", "") 
		                         + "' and HYXMCK.F_CRFX = 'C' and F_FLLX='1' and HYXMCK.F_XMBH <> HYXMCK.F_YYXMBH group by F_CLBH) as CLBJDCK ON HYXMMX.F_CLBH = CLBJDCK.F_CLBH"	
		                         //材料退货
		                         + " LEFT JOIN (select F_CLBH, round(sum(F_CLSL), 2) as F_CLSL,"
		                         + " round(sum(F_CLDJ * F_CLSL), 2) as F_CLZJ from HYXMCK"
		                         + " where HYXMCK.F_YYXMBH = '" + PO.GetValueByParamName("F_XMBH", "") 
		                         + "' and HYXMCK.F_CRFX = 'T' and F_FLLX = '0') as CLTH ON HYXMMX.F_CLBH = CLTH.F_CLBH"	
		                         //厂商退货
		                         + " LEFT JOIN (select F_CLBH, round(sum(F_CLSL), 2) as F_CLSL,"
		                         + " round(sum(F_CLDJ * F_CLSL), 2) as F_CLZJ from HYXMCK"
		                         + " where HYXMCK.F_YYXMBH = '" + PO.GetValueByParamName("F_XMBH", "") 
		                         + "' and HYXMCK.F_CRFX = 'T' and F_FLLX = '1') as CSTH ON HYXMMX.F_CLBH = CSTH.F_CLBH"	
		                         //仓库调拨
		                         + " LEFT JOIN (select F_CLBH, round(sum(F_CLSL), 2) as F_CLSL,"
		                         + " round(sum(F_CLDJ * F_CLSL), 2) as F_CLZJ from HYXMCK"
		                         + " where HYXMCK.F_YYXMBH = '" + PO.GetValueByParamName("F_XMBH", "") 
		                         + "' and HYXMCK.F_CRFX = 'D') as CKDB ON HYXMMX.F_CLBH = CKDB.F_CLBH"	
		                         + " WHERE HYXMMX.F_XMBH = '" + PO.GetValueByParamName("F_XMBH", "") + "' ORDER BY HYXMMX.F_CLBH";
		rs = stmt.executeQuery(strSql);
		itemsDataset = DataSetUtils.resultSet2DataSet(rs, itemsDataset);
		
		hjRowSet.putString("F_CLBH", "合计");
		
		for(int i = 0; i < itemsDataset.getRowCount(); i++) {
			rowset = itemsDataset.getRowSet(i);
			//项目成本(领用成本)=正常出库成本+从别的项目借调成本+仓库调拨成本-材料退货成本-厂商退货成本
			F_LYSL = rowset.getNumber("F_ZCSL", 0.0).doubleValue() + rowset.getNumber("F_JDSL", 0.0).doubleValue()
			       + rowset.getNumber("F_DBSL", 0.0).doubleValue() - rowset.getNumber("F_CLTHSL", 0.0).doubleValue()
			       - rowset.getNumber("F_CSTHSL", 0.0).doubleValue();
			rowset.putNumber("F_LYSL", F_LYSL);
			F_CLZJ = rowset.getNumber("F_ZCZJ", 0.0).doubleValue() + rowset.getNumber("F_JDSL", 0.0).doubleValue()
		           + rowset.getNumber("F_DBSL", 0.0).doubleValue() - rowset.getNumber("F_CLTHZJ", 0.0).doubleValue()
		           - rowset.getNumber("F_CSTHZJ", 0.0).doubleValue();
			rowset.putNumber("F_CLZJ", F_CLZJ);
			//库存成本=入库成本-正常出库成本-借给其他项目成本+材料退货成本
			F_KCSL = rowset.getNumber("F_RKSL", 0.0).doubleValue() - rowset.getNumber("F_ZCSL", 0.0).doubleValue()
		           - rowset.getNumber("F_BJDSL", 0.0).doubleValue() + rowset.getNumber("F_CLTHSL", 0.0).doubleValue();
			rowset.putNumber("F_KCSL", F_KCSL);
			
			hjRowSet.putNumber("F_SQSL", hjRowSet.getNumber("F_SQSL", 0.0).doubleValue() + rowset.getNumber("F_SQSL", 0.0).doubleValue());
			hjRowSet.putNumber("F_RKSL", hjRowSet.getNumber("F_RKSL", 0.0).doubleValue() + rowset.getNumber("F_RKSL", 0.0).doubleValue());
			hjRowSet.putNumber("F_ZCSL", hjRowSet.getNumber("F_ZCSL", 0.0).doubleValue() + rowset.getNumber("F_ZCSL", 0.0).doubleValue());
			hjRowSet.putNumber("F_JDSL", hjRowSet.getNumber("F_JDSL", 0.0).doubleValue() + rowset.getNumber("F_JDSL", 0.0).doubleValue());
			hjRowSet.putNumber("F_BJDSL", hjRowSet.getNumber("F_BJDSL", 0.0).doubleValue() + rowset.getNumber("F_BJDSL", 0.0).doubleValue());
			hjRowSet.putNumber("F_CLTHSL", hjRowSet.getNumber("F_CLTHSL", 0.0).doubleValue() + rowset.getNumber("F_CLTHSL", 0.0).doubleValue());
			hjRowSet.putNumber("F_CSTHSL", hjRowSet.getNumber("F_CSTHSL", 0.0).doubleValue() + rowset.getNumber("F_CSTHSL", 0.0).doubleValue());
			hjRowSet.putNumber("F_DBSL", hjRowSet.getNumber("F_DBSL", 0.0).doubleValue() + rowset.getNumber("F_DBSL", 0.0).doubleValue());
			hjRowSet.putNumber("F_KCSL", hjRowSet.getNumber("F_KCSL", 0.0).doubleValue() + rowset.getNumber("F_KCSL", 0.0).doubleValue());
			hjRowSet.putNumber("F_LYSL", hjRowSet.getNumber("F_LYSL", 0.0).doubleValue() + rowset.getNumber("F_LYSL", 0.0).doubleValue());
			hjRowSet.putNumber("F_CLZJ", hjRowSet.getNumber("F_CLZJ", 0.0).doubleValue() + rowset.getNumber("F_CLZJ", 0.0).doubleValue());			
		}
		itemsDataset.insertRowSet(hjRowSet);
		applyForm.setDataSet("HYXMMX", itemsDataset);
	}
}
