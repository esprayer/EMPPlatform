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
public class EMPPrivilegeUtil {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	//--------------------------------------------------------------------------------------------------
	//描述:获取用户是否有系统权限
	//设计: ES
	//实现: ES
	//修改:
	//--------------------------------------------------------------------------------------------------
	public boolean checkUserPrivilege(String id) {
		JConnection       conn = null;
		JStatement        stmt = null;
		ResultSet             rs = null;
		String            strSql = "select * FROM BSUSQX WHERE F_ID like '" + id + "%'";

		Debug.PrintlnMessageToSystem("sql|||" + strSql);

		try {
			conn = JConnection.getInstance(jdbcTemplate.getDataSource().getConnection());
			stmt = conn.createStatement();
			rs = stmt.executeQuery(strSql);
			if(rs != null && rs.next()) return true;
			else return false;
		} catch (Exception e) {
			Debug.PrintlnMessageToSystem(e.getMessage());
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
		return false;
	}
}
