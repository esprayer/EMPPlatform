package com.server;

import com.core.xml.StubObject;
import com.efounder.eai.framework.JActiveObject;
import com.efounder.service.meta.db.DictMetadata;
import com.efounder.sql.JConnection;
import com.efounder.sql.JStatement;
import com.efounder.eai.EAI;
import com.efounder.db.DBTools;
import com.efounder.dctbuilder.data.ColumnMetaData;
import com.efounder.dctbuilder.data.DctContext;
import com.efounder.dctbuilder.data.DictMetaDataEx;
import com.efounder.dctbuilder.rule.EMPCodeRuler;
import com.efounder.dctbuilder.util.MetaDataUtil;
import com.efounder.eai.data.JResponseObject;

import com.efounder.eai.data.JParamObject;
import com.efounder.form.server.resolver.util.BizFormUtil;
import com.efounder.form.server.resolver.util.FormBillFieldUtil;

import com.efounder.builder.base.data.DataSetUtils;
import com.efounder.builder.base.data.EFDataSet;
import com.efounder.builder.base.data.EFRowSet;
import com.efounder.builder.base.data.ESPRowSet;
import com.efounder.builder.meta.dctmodel.DCTMetaData;
import com.efounder.builder.meta.domodel.DOMetaData;
import com.metadata.persistence.SYS_DCT_CST;
import com.pub.util.StringFunction;
import com.pub.util.StringUtil;

import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2011</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
@Repository
public class EMPDALPermissionManager extends JActiveObject{
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public EMPDALPermissionManager() {
	}

    /**
	 * 查询维表信息
	 * @param Param
	 * @param Data
	 * @param CustomObject
	 * @param AdditiveObject
	 * @return
	 */
    public EFDataSet queryBSXTZD(){
    	JConnection                    conn = null;
		JStatement                     stmt = null;
		ResultSet                 resultSet = null;
		String                  strQuerySql = "";
		EFDataSet                 xtDataSet = EFDataSet.getInstance();

		try {
			conn = JConnection.getInstance(jdbcTemplate.getDataSource().getConnection());
			
			conn.setAutoCommit(false);
			
			stmt = conn.createStatement();
			
			
            strQuerySql = " select F_XTBH, F_XTMC from BSXTZD where F_SYZT = '1' order by F_XTBH";
            
            resultSet = stmt.executeQuery(strQuerySql);
            resultSet = stmt.executeQuery(strQuerySql);
            xtDataSet = DataSetUtils.resultSet2DataSet(resultSet, xtDataSet);
			return xtDataSet;
		} catch (Exception ce) {
			ce.printStackTrace();
		} finally {
			try {
				closeAllResources(resultSet, stmt, conn);
			} catch(Exception ce) {
				ce.printStackTrace();
			}
		}
		return null;
	}
    
    /**
	 * 查询维表信息
	 * @param Param
	 * @param Data
	 * @param CustomObject
	 * @param AdditiveObject
	 * @return
	 */
    public List<Map> queryBSGNZD(){
    	JConnection                    conn = null;
		JStatement                     stmt = null;
		ResultSet                 resultSet = null;
		String                  strQuerySql = "";
		List<Map>                    xtList = new ArrayList<Map>();
		Map<String, String>          rowMap = null;

		try {
			conn = JConnection.getInstance(jdbcTemplate.getDataSource().getConnection());
			
			conn.setAutoCommit(false);
			
			stmt = conn.createStatement();
			
			
            strQuerySql = " select F_XTBH, F_XTMC from BSXTZD where F_SYZT = '1' order by F_XTBH";
            
            resultSet = stmt.executeQuery(strQuerySql);
            while (resultSet.next()) {
            	rowMap = new HashMap<String, String>();
            	rowMap.put("F_XTBH", resultSet.getString("F_XTBH"));
            	rowMap.put("F_XTMC", resultSet.getString("F_XTMC"));
            	xtList.add(rowMap);
            }
			return xtList;
		} catch (Exception ce) {
			ce.printStackTrace();
		} finally {
			try {
				closeAllResources(resultSet, stmt, conn);
			} catch(Exception ce) {
				ce.printStackTrace();
			}
		}
		return null;
	}
	
    /**
	 * 查询维表信息
	 * @param Param
	 * @param Data
	 * @param CustomObject
	 * @param AdditiveObject
	 * @return
	 */
    public EFDataSet queryBSUSGN(JParamObject PO){
    	JConnection                    conn = null;
		JStatement                     stmt = null;
		ResultSet                 resultSet = null;
		String                  strQuerySql = "";
		EFDataSet               usgnDataSet = EFDataSet.getInstance();

		try {
			conn = JConnection.getInstance(jdbcTemplate.getDataSource().getConnection());
			
			conn.setAutoCommit(false);
			
			stmt = conn.createStatement();
			
			
            strQuerySql = " select BSGNZD.F_GNBH, BSGNZD.F_GNMC, ifnull(BSUSGN.F_SH, '0') as F_SH"
            	        + " from BSGNZD "
            	        + " LEFT JOIN BSUSGN on BSUSGN.F_GNBH = BSGNZD.F_GNBH and BSUSGN.F_ZGBH = '" + PO.GetValueByParamName("F_ZGBH", "") + "'"
            	        + " where BSGNZD.F_XTBH = '" + PO.GetValueByParamName("F_XTBH", "") + "'"
            	        + " order by BSGNZD.F_DISP";
            
            resultSet = stmt.executeQuery(strQuerySql);
            usgnDataSet = DataSetUtils.resultSet2DataSet(resultSet, usgnDataSet);
			return usgnDataSet;
		} catch (Exception ce) {
			ce.printStackTrace();
		} finally {
			try {
				closeAllResources(resultSet, stmt, conn);
			} catch(Exception ce) {
				ce.printStackTrace();
			}
		}
		return null;
	}
    
    /**
	 * 增加删除权限信息
	 * @param Param
	 * @param Data
	 * @param CustomObject
	 * @param AdditiveObject
	 * @return
	 */
    public JResponseObject batchSaveUSGN(JParamObject PO) {
    	JResponseObject                  RO = null;
    	JConnection                    conn = null;
		JStatement                     stmt = null;
		ResultSet                 resultSet = null;
		String                  strQuerySql = "";
		String                         F_SH = PO.GetValueByParamName("F_SH", "0");
		String                      F_GNBHS = null;
		String[]                     F_GNBH = null;
    	try {
			conn = JConnection.getInstance(jdbcTemplate.getDataSource().getConnection());
			
			conn.setAutoCommit(false);
			
			stmt = conn.createStatement();
			
			F_GNBHS = PO.GetValueByParamName("F_GNBHS", "");
			F_GNBH = F_GNBHS.split(",");
			
			for(int i = 0; i < F_GNBH.length; i++) {
				strQuerySql = " delete from BSUSGN where F_ZGBH = '" + PO.GetValueByParamName("USER_ID", "") + "' and F_GNBH = '" + F_GNBH[i] + "'";
				stmt.execute(strQuerySql);
				if(F_SH.equals("1")) {
					strQuerySql = " insert into BSUSGN (F_ZGBH, F_GNBH, F_SH, F_G) values ('" + PO.GetValueByParamName("USER_ID", "") 
				                + "', '" + F_GNBH[i] + "', '1', '1')";
					stmt.execute(strQuerySql);
				}
			}
			conn.commit();
			RO = new JResponseObject(null, 1);
			return RO;
		} catch (Exception ce) {
			conn.rollback();
			RO = new JResponseObject(null, -1, ce);
			ce.printStackTrace();
		} finally {
			try {
				closeAllResources(resultSet, stmt, conn);
			} catch(Exception ce) {
				ce.printStackTrace();
			}
		}
    	return RO;
    }
    
    /**
     * 根据PO获取数据库连接
     *
     * @param PO JParamObject
     * @return   JConnection
     * @throws    Exception
     */
    public static JConnection getConnection(JParamObject PO) throws Exception {

        JConnection con = (JConnection) EAI.DAL.IOM("DBManagerObject","GetDBConnection",PO,null);
        return con;
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