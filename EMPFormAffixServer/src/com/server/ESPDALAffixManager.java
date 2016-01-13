package com.server;

import com.core.xml.StubObject;
import com.efounder.builder.base.data.DataSetUtils;
import com.efounder.builder.base.data.EFDataSet;
import com.efounder.builder.base.data.EFRowSet;
import com.efounder.db.DBTools;
import com.efounder.eai.data.JResponseObject;
import java.util.Map;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.sql.ResultSetMetaData;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;
import com.efounder.eai.data.JParamObject;
import com.efounder.sql.JConnection;
import com.efounder.sql.JStatement;
import com.efounder.eai.EAI;
import com.efounder.eai.framework.JActiveObject;
import com.server.pub.ESPAffixUtilSvr;

import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2010</p>
 *
 * <p>Company: </p>
 *
 * @author ZhangES
 * @version 1.0
 */
public class ESPDALAffixManager extends JActiveObject {   

    private static final String GUID = "ESPDALBudgetAffixManager";

    JConnection conn = null;

    JResponseObject RO = null;

    JParamObject PO = null;

    public ESPDALAffixManager() {
        setObjectGUID(GUID);
    }

    /**
	 * 上传附件
	 * @param Param
	 * @param Data
	 * @param CustomObject
	 * @param AdditiveObject
	 * @return
	 * @throws Exception 
	 */
	public JResponseObject uploadAffix(Object Param, Object Data, Object CustomObject, Object AdditiveObject) throws Exception {
		JConnection     conn = null;
		JResponseObject   RO = null;
		JParamObject      PO = null;
		PO = (JParamObject) Param;
		
		java.util.List list = (java.util.List) PO.getValue("affixList", null);
		java.util.List messageList = new ArrayList();
		java.util.List successList = new ArrayList();
		java.util.List errorList = new ArrayList();
		try {
			conn  = (JConnection) EAI.DAL.IOM("DBManagerObject", "GetDBConnection", PO,this); // 获取数据库连接
			EFRowSet efRowSet = null;
			String filepath = "";
			for (int i = 0; i < list.size(); i++) {
				try {
					efRowSet = (EFRowSet) list.get(i);
					filepath = efRowSet.getString("FILEPATH", "");
					ESPAffixUtilSvr.uploadAffixTable(PO, efRowSet, conn);
//					ESPAffixUtilSvr.uploadAffixBlobTable(PO, efRowSet, conn);
					successList.add("保存附件[" + filepath + "]成功！");
				} catch (Exception e) {
					e.printStackTrace();
					errorList.add("保存附件[" + filepath + "]失败！\r\n" + e.getMessage());
				}
			}
			messageList.add(successList);
			messageList.add(errorList);
			RO =  new JResponseObject(messageList, 0);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally{
			if (conn != null) {
				conn.close();
			}
		}
		return RO; 
	}	

	/**
	 *  得到最大Order
	 * @param Param
	 * @param Data
	 * @param CustomObject
	 * @param AdditiveObject
	 * @return
	 */
	public JResponseObject getMaxOrder(Object Param, Object Data, Object CustomObject, Object AdditiveObject) {
	    JConnection        conn = null;
		JResponseObject      RO = null;
		JParamObject         PO = null;
		Statement            st = null;
		ResultSet            rs = null;
		EFRowSet       efRowSet = null;
		String           DCT_ID = "";
		String           F_CODE = "";
		String           F_USER = "";
		String          FLOW_ID = "";
		String          NODE_ID = "";
		String            vsSql = "";
	    PO = (JParamObject) Param;
	    
	    try {
	    	conn  = (JConnection) EAI.DAL.IOM("DBManagerObject", "GetDBConnection", PO,this); // 获取数据库连接
			conn.setAutoCommit(false);
			st = conn.createStatement();
			
			efRowSet = (EFRowSet) PO.getValue("affixList", null);
			DCT_ID = efRowSet.getString("DCT_ID", "");
			F_CODE = PO.GetValueByParamName("F_CODE", "");
			F_USER = PO.GetValueByParamName("F_USER", "");
			FLOW_ID = PO.GetValueByParamName("FLOW_ID", "");
			NODE_ID = PO.GetValueByParamName("NODE_ID", "");
			
			vsSql = " select max(F_ORDE), F_CODE, F_USER from " + DCT_ID + "_AFFIX where F_CODE = '" + F_CODE + "' and F_USER = '" + F_USER + "' and " 
			      + " FLOW_ID = '" + FLOW_ID + "' and NODE_ID = '" + NODE_ID + "' group by F_CODE, F_USER";
			
			try {
				rs = st.executeQuery(vsSql);
				if(rs.next()){
					return new JResponseObject(rs.getString(1),JResponseObject.RES_OK); 
				}else{
					return new JResponseObject("0",JResponseObject.RES_OK);
				}	
			} catch (Exception e) {
				e.printStackTrace();
				return new JResponseObject( "-1",JResponseObject.RES_ERROR); 
			}
	    } catch (Exception e) {
	    	conn.rollback();
	    	e.printStackTrace();
	    }finally{
	    	conn.setAutoCommit(true);
	    	try {
	    		try{if(rs != null){rs.close();}}catch(Exception e){}
				try{if(st != null){st.close();}}catch(Exception e){}
				if(conn != null)conn.close();
	    	}
	    	catch (Exception e) {
	    		e.printStackTrace();
			}
	    }
	    return RO;
	}
	
	
	/**
	 * 
	 * @param PO
	 * @param arg0
	 * @param arg1
	 * @param arg2
	 * @return
	 * @throws Exception
	 */
	public JResponseObject deleteAffixBlob(Object Param, Object arg0, Object arg1, Object arg2) throws Exception {		
		Statement               st = null;
		Statement              st1 = null;
		JParamObject            PO = (JParamObject) Param;
		JResponseObject         RO = null;
		JConnection           conn = (JConnection) EAI.DAL.IOM("DBManagerObject", "GetDBConnection", PO,this); // 获取数据库连接
		
		conn.setAutoCommit(false);
		st = conn.createStatement();
		st1 = conn.createStatement();
		String F_CODE = PO.GetValueByParamName("F_CODE", "");
		String F_USER = PO.GetValueByParamName("F_USER", "");
		String F_ORDER = PO.GetValueByParamName("F_ORDER", "");
		String table = PO.GetValueByParamName("tableName", "");
		
		String vsAffixSql = " delete from " + table + " where F_CODE ='" + F_CODE + "' and F_USER = '" + F_USER + "' and F_ORDE = '" + F_ORDER + "'";
		String vsAffixBlobSql = " delete from "+table +"BLOB where F_CODE ='" + F_CODE + "' and F_USER = '" + F_USER + "' and F_ORDE = '" + F_ORDER + "'";
		try {
			st.execute(vsAffixSql);
			st1.execute(vsAffixBlobSql);
			RO = new JResponseObject( "0",JResponseObject.RES_OK); 
		} catch (Exception e) {
			conn.rollback();
			e.printStackTrace();
			return new JResponseObject( "-1",JResponseObject.RES_ERROR); 
		}finally{
			conn.setAutoCommit(true);
			if(conn != null){
				 conn.close();
			}
		}
		return RO;
		
	}
	
	/**
	 * 
	 * @param PO
	 * @param arg0
	 * @param arg1
	 * @param arg2
	 * @return
	 * @throws Exception
	 */
	public JResponseObject loadAffixBlob(JParamObject PO, Object arg0, Object arg1, Object arg2) throws Exception {
		JConnection  conn  = (JConnection) EAI.DAL.IOM("DBManagerObject", "GetDBConnection", PO,this); // 获取数据库连接
		String       table = PO.GetValueByParamName("table", "");
		String        cols = PO.GetValueByParamName("cols", "");
		String        vals = PO.GetValueByParamName("vals", "");
		String     blobcol = PO.GetValueByParamName("blobcol", "");
		String[]       col = cols.split(",");
		String[]       val = vals.split(",");
		byte[]        data = loadAffixBlobFromDB(conn, table, col, val, blobcol);
		if(conn != null){
			 conn.close();
		}
		JResponseObject RO = new JResponseObject();
		RO.setResponseObject("Bytes", data);
		return RO;
	}
	
	/**
	 *
	 * @param efRowSet
	 * @param conn
	 */
	public byte[] loadAffixBlobFromDB(JConnection conn, String table, String[] col, String[] val, String blobcol) throws Exception {
		byte []        data = null;
		JStatement     stmt = null;
		ResultSet        rs = null;
		String       strSql = "";
		
		try {
			String where = "";
			for(int i=0; i < col.length; i++){
				if(i > 0){
					where += " and ";
				}
				where += col[i] + "='"  + val[i] + "'";
			}
			strSql = "select " + blobcol + " from " + table + " where " + where;
			stmt = conn.createStatement();
			rs = stmt.executeQuery(strSql);
			if(rs != null && rs.next()) {
				data = rs.getBytes(blobcol);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return data;
	}
	
	/**
	 * 获取附件数据
	 * @param Param
	 * @param Data
	 * @param CustomObject
	 * @param AdditiveObject
	 * @return
	 * @throws Exception 
	 */
	public JResponseObject getAffixBlob(Object Param, Object Data, Object CustomObject, Object AdditiveObject) throws Exception {
		JConnection      conn = null;
		JResponseObject    RO = new JResponseObject();
		JParamObject       PO = null;
		
		PO = (JParamObject) Param;
		
		try {
			conn  = (JConnection) EAI.DAL.IOM("DBManagerObject", "GetDBConnection", PO,this); // 获取数据库连接
			EFRowSet efRowSet = (EFRowSet) PO.getValue("applyInfo", null);
			ESPAffixUtilSvr.loadAffixBlobFromDB(efRowSet, conn);
			byte[] data = (byte[]) efRowSet.getValue("Bytes", null);
			RO.setResponseObject("Bytes", data);	      
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if (conn != null) {
				conn.close();
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

    public static void clearConnection(JConnection conn){
      if(conn != null){
        try {
          conn.close();
        }
        catch (Exception ce) {
          ce.printStackTrace();
        }
        finally {
          conn = null;
        }
      }
    }

    public static void clearStatement(Statement stmt){
      if(stmt != null){
        try{
          stmt.close();
        }catch(Exception ce){
          ce.printStackTrace();
        }finally{
          stmt = null;
        }
      }
    }

    public static void clearResultSet(ResultSet rs){
      if(rs != null){
        try{
          rs.close();
        }catch(Exception ce){
          ce.printStackTrace();
        }finally{
          rs = null;
        }
      }
    }
}
