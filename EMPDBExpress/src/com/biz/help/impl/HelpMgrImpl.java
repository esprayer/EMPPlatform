package com.biz.help.impl;

import java.sql.ResultSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.biz.help.HelpServiceMgr;
import com.common.util.EMPSQLUtil;
import com.efounder.builder.base.data.DataSetUtils;
import com.efounder.builder.base.data.EFDataSet;
import com.efounder.eai.data.JParamObject;
import com.efounder.sql.JConnection;
import com.efounder.sql.JStatement;
import com.framework.sys.business.AbstractBusinessObjectServiceMgr;


@Transactional(rollbackFor = Exception.class)
@Service("HelpServiceMgr")
public class HelpMgrImpl extends AbstractBusinessObjectServiceMgr implements HelpServiceMgr {
	
	@Autowired
	private JdbcTemplate            jdbcTemplate;

	public EFDataSet helpDict(String tableName, String sqlColumn, String sqlWhere) {
		JParamObject PO = JParamObject.Create();

		PO.SetValueByParamName("tableName", tableName);
		PO.SetValueByParamName("sqlColumn", sqlColumn);
		PO.SetValueByParamName("sqlWhere", sqlWhere);
		return helpDict(PO);
	}
	
	public EFDataSet helpDict(JParamObject PO) {
		JConnection    conn = null;
		JStatement     stmt = null;
		ResultSet        rs = null;
		EFDataSet   dataSet = EFDataSet.getInstance(PO.GetValueByParamName("tableName", ""));
		String       strSql = "";

		try {
			conn = JConnection.getInstance(jdbcTemplate.getDataSource().getConnection());
			stmt = conn.createStatement();
			strSql = "select " + PO.GetValueByParamName("sqlColumn", "") + " from " + PO.GetValueByParamName("tableName", "");
			if(PO.GetValueByParamName("sqlWhere", "") != null && PO.GetValueByParamName("sqlWhere", "").trim().length() != 0) {
				strSql += " where " + PO.GetValueByParamName("sqlWhere", "");
			}
			
			if(PO.GetValueByParamName("sqlOrder", "") != null && PO.GetValueByParamName("sqlOrder", "").trim().length() != 0) {
				strSql += " order by " + PO.GetValueByParamName("sqlOrder", "");
			}
			rs = stmt.executeQuery(strSql);
			dataSet = DataSetUtils.resultSet2DataSet(rs, dataSet);
			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
			dataSet = EFDataSet.getInstance();
		} finally {
			try {
				EMPSQLUtil.closeAllResources(rs, stmt, conn);			
			} catch(Exception ce) {
				ce.printStackTrace();
			}
		}
		return dataSet;
	}
}
