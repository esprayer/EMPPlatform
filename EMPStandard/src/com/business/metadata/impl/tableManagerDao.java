package com.business.metadata.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import dwz.persistence.beans.SYS_OBJCOL;
import dwz.persistence.beans.SYS_OBJECT;

@Repository
public class tableManagerDao{
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public void createObject(SYS_OBJECT object, List<SYS_OBJCOL> objcols) {
		String               sql = "";
		String         uniqueSql = "";
		String     primaryKeySql = "";
		SYS_OBJCOL           pos = new SYS_OBJCOL();
		List<String>     primary = new ArrayList<String>();
		
		//创建物理表结构
		if(object.getOBJ_TYPE().equals("T")) {
			sql = "create table " + object.getOBJ_ID() + "(";
			for(int i = 0; i < objcols.size(); i++) {
				pos = (SYS_OBJCOL) objcols.get(i);
				if(pos.getCOL_ISKEY().equals("1")) primary.add(pos.getCOL_ID());
				if(pos.getCOL_TYPE().equals("C") || pos.getCOL_TYPE().equals("I")) {
					sql += pos.getCOL_ID() + " " + getColType(pos.getCOL_TYPE()) + "(" + pos.getCOL_LEN()  + ") " + colIsNull(pos.getCOL_ISNULL()) + " COMMENT '" + pos.getCOL_DES() + "',";
				} else if(pos.getCOL_TYPE().equals("N")) {
					sql += pos.getCOL_ID() + " " + getColType(pos.getCOL_TYPE()) + "(" + pos.getCOL_LEN() + "," + pos.getCOL_SCALE() + ") " + colIsNull(pos.getCOL_ISNULL()) + " COMMENT '" + pos.getCOL_DES() + "',";
				} else {
					sql += pos.getCOL_ID() + " " + getColType(pos.getCOL_TYPE()) + " " + colIsNull(pos.getCOL_ISNULL()) + " COMMENT '" + pos.getCOL_DES() + "',";
				}
			}
			if(sql.lastIndexOf(",") > -1) sql = sql.substring(0,sql.lastIndexOf(",")) + ")";
			jdbcTemplate.update(sql);
//			try {
//				jdbcTemplate.getDataSource().getConnection();
//			} catch (SQLException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			DataSource ds = (DataSource)ApplicationContexth.getApplicationContext().getBean("dataSource");
//			Connection c = ds.getConnection();
			if(object.getOBJ_APPTYPE().equals("DIM")) {
				sql = "insert into SYS_DICTS (OBJ_ID, DCT_ID, DCT_MC, DCT_BMCOLID, DCT_MCCOLID, DCT_JSCOLID, DCT_MXCOLID, DCT_BMSTRU)"
					+ "values('" + object.getOBJ_ID() + "', '" + object.getOBJ_ID() + "', '" + object.getOBJ_MC() + "', '', '', '', '', '')";
				jdbcTemplate.update(sql);
			}
		}
		
		//添加索引
		if(primary.size() > 0) {
			uniqueSql = "ALTER TABLE " + object.getOBJ_ID() + " ADD PRIMARY KEY(";
			primaryKeySql = "ALTER TABLE " + object.getOBJ_ID() + " ADD UNIQUE KEY(";
			
			for(int i = 0; i < primary.size(); i++) {
				if(i != primary.size() -1) {
					uniqueSql += primary.get(i).toString() + ",";
					primaryKeySql += primary.get(i).toString() + ",";
				} else {
					uniqueSql += primary.get(i).toString() + ")";
					primaryKeySql += primary.get(i).toString() + ")";
				}
			}
			jdbcTemplate.update(uniqueSql);
			jdbcTemplate.update(primaryKeySql);
		}		
	}
	
	/**
	 * 列类型
	 * @param COL_TYPE
	 * @return
	 */
	private String getColType(String COL_TYPE) {
		if(COL_TYPE.equals("N")) {
			return "decimal";
		} else if(COL_TYPE.equals("I")) {
			return "int";
		} else if(COL_TYPE.equals("D")) {
			return "date";
		} else if(COL_TYPE.equals("T")) {
			return "datetime";
		} else if(COL_TYPE.equals("B")) {
			return "blob";
		} else if(COL_TYPE.equals("L")) {
			return "longtext";
		} else if(COL_TYPE.equals("G")) {
			return "bigint";
		} else {
			return "varchar";
		}
	}
	
	/**
	 * 列是否为空
	 * @param COL_ISNULL
	 * @return
	 */
	private String colIsNull(String COL_ISNULL) {
		if(COL_ISNULL.equals("0")) {
			return " not null ";
		} else {
			return "";
		}
	}

	/**
	 * 修改列信息
	 * @param po
	 */
	public void updateColumnPro(SYS_OBJECT object, SYS_OBJCOL objcol, List<SYS_OBJCOL> objcols) {
		updateColumn(objcol);
		dropTablePrimary(object);
		createTablePrimary(object, objcols);
	}
	
	/**
	 * 修改列信息
	 * @param po
	 */
	public void addColumnPro(SYS_OBJECT object, SYS_OBJCOL objcol, List<SYS_OBJCOL> objcols) {
		addColumn(objcol);
		dropTablePrimary(object);
		createTablePrimary(object, objcols);
	}
	
	/**
	 * 添加列
	 * @param po
	 */
	public void addColumn(SYS_OBJCOL objcol) {
		String sql = "";
		if(objcol.getCOL_TYPE().equals("C") || objcol.getCOL_TYPE().equals("I")) {
			sql = "alter table " + objcol.getOBJ_ID() + " add " + objcol.getCOL_ID() + " " + getColType(objcol.getCOL_TYPE()) + "(" + objcol.getCOL_LEN() + ") " + colIsNull(objcol.getCOL_ISNULL()) + " COMMENT '" + objcol.getCOL_DES() + "'";
		} else if(objcol.getCOL_TYPE().equals("N")) {
			sql = "alter table " + objcol.getOBJ_ID() + " add " + objcol.getCOL_ID() + " " + getColType(objcol.getCOL_TYPE()) + "(" + objcol.getCOL_LEN()+ "," + objcol.getCOL_SCALE() + ") " + colIsNull(objcol.getCOL_ISNULL()) + " COMMENT '" + objcol.getCOL_DES() + "'";
		} else {
			sql = "alter table " + objcol.getOBJ_ID() + " add " + objcol.getCOL_ID() + " " + getColType(objcol.getCOL_TYPE()) + " " + colIsNull(objcol.getCOL_ISNULL()) + " COMMENT '" + objcol.getCOL_DES() + "'";
		}		
		jdbcTemplate.update(sql);
	}
	
	/**
	 * 修改列属性
	 * @param po
	 */
	public void updateColumn(SYS_OBJCOL objcol) {
		String sql = "";
		if(objcol.getCOL_TYPE().equals("C") || objcol.getCOL_TYPE().equals("I")) {
			sql = "alter table " + objcol.getOBJ_ID() + " modify " + objcol.getCOL_ID() + " " + getColType(objcol.getCOL_TYPE()) + "(" + objcol.getCOL_LEN() + ") " + colIsNull(objcol.getCOL_ISNULL()) + " COMMENT '" + objcol.getCOL_DES() + "'";
		} else if(objcol.getCOL_TYPE().equals("N")) {
			sql = "alter table " + objcol.getOBJ_ID() + " modify " + objcol.getCOL_ID() + " " + getColType(objcol.getCOL_TYPE()) + "(" + objcol.getCOL_LEN()+ "," + objcol.getCOL_SCALE() + ") " + colIsNull(objcol.getCOL_ISNULL()) + " COMMENT '" + objcol.getCOL_DES() + "'";
		} else {
			sql = "alter table " + objcol.getOBJ_ID() + " modify " + objcol.getCOL_ID() + " " + getColType(objcol.getCOL_TYPE())	 + colIsNull(objcol.getCOL_ISNULL()) + " COMMENT '" + objcol.getCOL_DES() + "'";
		}
		jdbcTemplate.update(sql);
	}
	
	/**
	 * 删除索引
	 */
	public void dropTablePrimary(SYS_OBJECT object) {
		String sql = "alter table " + object.getOBJ_ID() + " drop PRIMARY KEY";
		jdbcTemplate.update(sql);
	}
	
	/**
	 * 创建索引
	 * @param object
	 * @param objcols
	 */
	public void createTablePrimary(SYS_OBJECT object, List<SYS_OBJCOL> objcols) {
		String sql = "";
		SYS_OBJCOL pos = new SYS_OBJCOL();

		//创建物理表结构索引
		if(object.getOBJ_TYPE().equals("T")) {
			sql = "ALTER TABLE " + object.getOBJ_ID() + " ADD PRIMARY KEY(";
			for(int i = 0; i < objcols.size(); i++) {
				pos = (SYS_OBJCOL) objcols.get(i);
				if(pos.getCOL_ISKEY().equals("1")) sql += pos.getCOL_ID() + ",";
			}
			if(sql.lastIndexOf(",") > -1) sql = sql.substring(0,sql.lastIndexOf(",")) + ")";
			jdbcTemplate.update(sql);
		}		
	}
}
