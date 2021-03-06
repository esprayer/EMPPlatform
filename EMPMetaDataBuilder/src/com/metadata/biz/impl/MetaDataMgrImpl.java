package com.metadata.biz.impl;

import java.sql.ResultSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.metadata.biz.MetaDataServiceMgr;

import com.efounder.builder.base.data.DataSetUtils;
import com.efounder.builder.base.data.EFDataSet;
import com.efounder.builder.base.data.EFRowSet;
import com.efounder.sql.JConnection;
import com.efounder.sql.JStatement;
import com.framework.sys.business.AbstractBusinessObjectServiceMgr;

@Transactional(rollbackFor = Exception.class)
@Service("MetaDataServiceMgr")
public class MetaDataMgrImpl extends AbstractBusinessObjectServiceMgr implements MetaDataServiceMgr {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	/**
	 * 获取字典元数据
	 * @param conn
	 * @param tableName
	 * @return
	 */
	public EFRowSet getDictRow(String tableName) {
		JConnection    conn = null;
		JStatement     stmt = null;
		ResultSet        rs = null;
		EFDataSet    dictDS = EFDataSet.getInstance("SYS_DICTS");
		EFRowSet     dictRS = null;
		String       strSql = " select * from SYS_DICTS where DCT_ID = '" + tableName + "'";
		
		try {
			conn = JConnection.getInstance(jdbcTemplate.getDataSource().getConnection());
			stmt = conn.createStatement();
			rs = stmt.executeQuery(strSql);
			dictDS = DataSetUtils.resultSet2DataSet(rs, dictDS);
			if(dictDS != null && dictDS.getRowCount() > 0) {
				dictRS = dictDS.getRowSet(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				closeAllResources(rs, stmt, conn);				
			} catch(Exception ce) {
				ce.printStackTrace();
			}
		}
		return dictRS;
	}
	
	/**
	 * 获取字典扩展属性
	 * @param conn
	 * @param tableName
	 * @return
	 */
	public EFRowSet getExtendProperties(String tableName) {
		JConnection    conn = null;
		JStatement     stmt = null;
		ResultSet        rs = null;
		EFDataSet dictCstDS = EFDataSet.getInstance("SYS_DCT_CST");
		EFRowSet  dictCstRS = null;
		EFRowSet     rowset = EFRowSet.getInstance();
		String       strSql = " select * from SYS_DCT_CST where DCT_ID = '" + tableName + "'";
		
		try {
			conn = JConnection.getInstance(jdbcTemplate.getDataSource().getConnection());
			stmt = conn.createStatement();
			rs = stmt.executeQuery(strSql);
			dictCstDS = DataSetUtils.resultSet2DataSet(rs, dictCstDS);
			for(int i = 0; i < dictCstDS.getRowCount(); i++) {
				dictCstRS = dictCstDS.getRowSet(i);
				rowset.putString(dictCstRS.getString("DCT_KEY", ""), dictCstRS.getString("DCT_VAL", ""));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				closeAllResources(rs, stmt, conn);		
			} catch(Exception ce) {
				ce.printStackTrace();
			}
		}
		return rowset;
	}
	
	/**
	 * 获取字典元数据
	 * @param conn
	 * @param tableName
	 * @return
	 */
	public EFRowSet getSYS_OBJECTS(String tableName) {
		JConnection    conn = null;
		JStatement     stmt = null;
		ResultSet        rs = null;
		EFDataSet    dictDS = EFDataSet.getInstance("SYS_DCT_CST");
		EFRowSet     dictRS = null;
		String       strSql = " select * from SYS_DCT_CST where DCT_ID = '" + tableName + "' order by F_CRDATE";
		
		try {
			conn = JConnection.getInstance(jdbcTemplate.getDataSource().getConnection());
			stmt = conn.createStatement();
			rs = stmt.executeQuery(strSql);
			dictDS = DataSetUtils.resultSet2DataSet(rs, dictDS);
			if(dictDS != null && dictDS.getRowCount() > 0) {
				dictRS = dictDS.getRowSet(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				closeAllResources(rs, stmt, conn);				
			} catch(Exception ce) {
				ce.printStackTrace();
			}
		}
		return dictRS;
	}
	
	/**
	 * 获取字典列元数据
	 * @param conn
	 * @param tableName
	 * @return
	 */
	public EFDataSet getSYS_OBJCOLS(String tableName) {
		JConnection    conn = null;
		JStatement     stmt = null;
		ResultSet        rs = null;
		EFDataSet    dictDS = EFDataSet.getInstance("SYS_OBJCOLS");
		String       strSql = " select * from SYS_OBJCOLS where OBJ_ID = '" + tableName + "' and F_STAU = '1'";
		
		try {
			conn = JConnection.getInstance(jdbcTemplate.getDataSource().getConnection());
			stmt = conn.createStatement();
			rs = stmt.executeQuery(strSql);
			dictDS = DataSetUtils.resultSet2DataSet(rs, dictDS);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				closeAllResources(rs, stmt, conn);					
			} catch(Exception ce) {
				ce.printStackTrace();
			}
		}
		return dictDS;
	}
	
	/**
	 * 获取模型元数据
	 * @param conn
	 * @param tableName
	 * @return
	 */
	public EFDataSet getSYS_MDL_CTN(String MDL_ID, String CTN_TYPE) {
		JConnection    conn = null;
		JStatement     stmt = null;
		ResultSet        rs = null;
		EFDataSet  mdlCtnDS = EFDataSet.getInstance("SYS_MDL_CTN");
		String       strSql = " select * from SYS_MDL_CTN where MDL_ID = '" + MDL_ID + "'";
		
		if(CTN_TYPE != null && CTN_TYPE.trim().length() > 0) {
			strSql += " and CTN_TYPE = '" + CTN_TYPE + "'";
		}
		try {
			conn = JConnection.getInstance(jdbcTemplate.getDataSource().getConnection());
			stmt = conn.createStatement();
			rs = stmt.executeQuery(strSql);
			mdlCtnDS = DataSetUtils.resultSet2DataSet(rs, mdlCtnDS);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				closeAllResources(rs, stmt, conn);					
			} catch(Exception ce) {
				ce.printStackTrace();
			}
		}
		return mdlCtnDS;
	}
	
	/**
	 * 获取模型元数据
	 * @param conn
	 * @param tableName
	 * @return
	 */
	public EFDataSet getSYS_MDL_VAL(String MDL_ID) {
		JConnection    conn = null;
		JStatement     stmt = null;
		ResultSet        rs = null;
		EFDataSet  mdlValDS = EFDataSet.getInstance("SYS_MDL_VAL");
		String       strSql = " select * from SYS_MDL_VAL where MDL_ID = '" + MDL_ID + "'";

		try {
			conn = JConnection.getInstance(jdbcTemplate.getDataSource().getConnection());
			stmt = conn.createStatement();
			rs = stmt.executeQuery(strSql);
			mdlValDS.setPrimeKey(new String[]{"MDL_KEY"});
			mdlValDS = DataSetUtils.resultSet2DataSet(rs, mdlValDS);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				closeAllResources(rs, stmt, conn);					
			} catch(Exception ce) {
				ce.printStackTrace();
			}
		}
		return mdlValDS;
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
