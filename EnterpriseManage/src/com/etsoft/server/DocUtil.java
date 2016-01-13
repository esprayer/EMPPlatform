package com.etsoft.server;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Repository;

@Repository
public class DocUtil {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	//--------------------------------------------------------------------------------------------------
	//描述:获取项目材料帮助
	//设计: ES
	//实现: ES
	//修改:
	//--------------------------------------------------------------------------------------------------
	public String loadMaxDocName(String F_NAME, String F_PATH) {
		String suffix = F_NAME.substring(F_NAME.lastIndexOf("."));
		String fileName = F_NAME.substring(0, F_NAME.lastIndexOf("."));
		String strSql = "select F_NAME from dms_doc where f_name REGEXP BINARY '" + fileName + "[(0-9)]{1,}" + suffix + "' order by F_NAME desc";
		final List<String> dataList = new ArrayList<String>();
		jdbcTemplate.query(strSql, new Object[]{}, new RowCallbackHandler(){
			public void processRow(ResultSet rs) throws SQLException{
				dataList.add(rs.getString(1));
			}
		});
		if(dataList.size() > 0) return dataList.get(0).toLowerCase();
		else return "";
	}
}
