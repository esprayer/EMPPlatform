package com.server;

import java.sql.ResultSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.efounder.sql.JConnection;
import com.efounder.sql.JStatement;

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

		try {
			conn = JConnection.getInstance(jdbcTemplate.getDataSource().getConnection());
			stmt = conn.createStatement();
			rs = stmt.executeQuery(strSql);
			if(rs != null && rs.next()) return true;
			else return false;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				closeAllResources(rs, stmt, conn);
			} catch(Exception ce) {
				ce.printStackTrace();
			}
		}
		return false;
	}
	
	public static void closeAllResources(ResultSet rs, JStatement stmt, JConnection conn) {
		try {
			if(rs != null) {
				rs.close();
			}
		} catch(Exception ce) {
			ce.printStackTrace();
		}
		
		try {
			if(stmt != null) stmt.close();
		} catch(Exception ce) {
			ce.printStackTrace();
		}
		
		try {
			if(conn != null) conn.close();
		} catch(Exception ce) {
			ce.printStackTrace();
		}
	}
}
