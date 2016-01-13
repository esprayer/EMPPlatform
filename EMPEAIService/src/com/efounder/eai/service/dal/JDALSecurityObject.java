package com.efounder.eai.service.dal;

import com.core.xml.PackageStub;
import com.core.xml.StubObject;
import com.efounder.eai.framework.JActiveObject;
import com.efounder.eai.service.dal.DALDBManagerObject.JDataSourceStub;
import com.efounder.pub.util.MD5Tool;
import com.efounder.service.config.DBConfigManager;
import com.efounder.service.security.SecurityContext;
import com.efounder.sql.JConnection;
import com.efounder.eai.EAI;
import com.efounder.date.DateFunction;
import com.efounder.db.DBTools;
import com.efounder.dbservice.data.AccountStub;
import com.efounder.dbservice.data.DataStorageStub;
import com.efounder.eai.data.JResponseObject;

import java.sql.*;

import com.efounder.eai.data.JParamObject;
import com.efounder.builder.base.data.EFDataSet;
import com.efounder.builder.base.data.DataSetUtils;

import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

import com.efounder.builder.base.data.EFRowSet;
import com.pub.util.MD5Util;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
public class JDALSecurityObject extends JActiveObject {
	
	public JDALSecurityObject() {
	}	
	
	/**
	 * 连接测试
	 * @param Param
	 * @param Data
	 * @param CustomObject
	 * @param AdditiveObject
	 * @return
	 */
//    public JResponseObject loginProduct(Object Param, Object Data, Object CustomObject, Object AdditiveObject) throws Exception {
	public JResponseObject loginProduct(JParamObject paramObject,Object o2,Object o3,Object o4) throws Exception {
//		JParamObject           paramObject = null;
//		String                       error = "";
//		JConnection                   conn = null;
//		JResponseObject                 RO = new JResponseObject();
//		
//		try {
//			if (Param == null) {
//				paramObject = JParamObject.Create();
//			} else {
//				paramObject = (JParamObject) Param;
//			}
//			conn = this.getConnection(paramObject);
//			String User    = paramObject.GetValueByEnvName("UserName");
//		    String Pass    = paramObject.GetValueByEnvName("UserPass");
//		    try {
//		    	if (!checkUserPass(conn, paramObject, User, Pass)) throw new Exception("用户或密码错误.");
//		    	// 用户信息初始化，需要根据用户确定公司代码
//		        StubObject map = getUserMap(conn,paramObject);
//		        map.setString("dbOwner",paramObject.GetValueByEnvName("dbOwner"));
//		        RO.setResponseObject("UserInfo", map);
//		        
//		        // 将JParamObject中的登录信息放到当前用户的Session中
////		        EAI.DAL.IOM("SessionManager","initMemCachedManager",paramObject,conn);
//		        
//		        RO.setErrorCode(1);
//		    } catch(Exception ce) {
//		    	if(ce.getMessage() != null) {
//					error += ce.getMessage();
//				}
//				RO = new JResponseObject(error, -1);
//		    	ce.printStackTrace();
//		    }
//		} catch (Exception ce) {
//			if(ce.getMessage() != null) {
//				error += ce.getMessage();
//			}
//			RO = new JResponseObject(error, -1);
//			ce.printStackTrace();
//		}
//		return RO;
    	// 获取当前登录产品
        String product = paramObject.GetValueByEnvName("Product");
        // 首先开始初始化当前登产品
        EAI.DAL.IOM(product,"startLoginProduct",paramObject);
        // 公用初始化
        JResponseObject response = new JResponseObject();
        // 获取数据库连接
        JConnection conn = JConnection.getInstance(paramObject);
        String User    = paramObject.GetValueByEnvName("UserName");
        String Pass    = paramObject.GetValueByEnvName("UserPass");
        String SecurityServer = getUserSecrityServer2(paramObject);
        try {
        	// 从指定的Server上检查口令
        	if (SecurityServer != null && SecurityServer.trim().length() > 0) {
        		if (!checkUserPassInServer(conn, paramObject, SecurityServer)) throw new Exception("远程验证用户失败:用户或密码错误.");
        	} else {
        		if (!checkUserPass(conn, paramObject, User, Pass)) throw new Exception("用户或密码错误.");
        	}

        	// 用户信息初始化，需要根据用户确定公司代码
        	StubObject map = ProductDALUtils.getUserMap(conn,paramObject);
        	if( map != null ) {
            response.setResponseObject("UserInfo", map);
        	}
        	// xxCONF初始化
        	map = ProductDALUtils.getBSCONF(paramObject,conn);
        	initCustomConf(map,paramObject);
        	if ( map != null ) {
            response.setResponseObject("BSCONF", map);
            // 设置到数据库环境变量中
            if ( DBConfigManager.getValueMap(paramObject) == null )
            	DBConfigManager.setValueMap(paramObject,map.getStubTable());
        	}
        	// 产品信息初始化
        	map = ProductDALUtils.getSYS_PRODUCT_ENV(paramObject,conn);
        	if( map != null ) response.setResponseObject("ProductEnv",map);
        	// 单位信息初始化
        	SecurityContext sc = ProductDALUtils.getSecurityContext(conn,paramObject);
        	// 功能权限初始化
        	response.setResponseObject("SecurityContext",sc);
        	sc.setObject("JParamObject", paramObject);
        	// 产品个性化登录
               
        	// 将JParamObject中的登录信息放到当前用户的Session中
        	EAI.DAL.IOM("SessionManager","loginSession",paramObject,conn);
        	EAI.DAL.IOM(product, "loginProduct", paramObject, response, conn);
        	// 记录登录日志
        	writeLoginLog(paramObject,conn);
        	Long timeBeginServer = (Long)paramObject.getValue("timeBeginServer",null);
        	response.setResponseObject("timeBeginServer",timeBeginServer);
//          
//        	//其它产品的初始
//        	String id="";
//        	try{
//        		java.util.Vector servers = PackageStub.getContentVector("LoginInit");
//        		for (int i = 0; servers != null && i < servers.size(); i++) {
//        			StubObject so = (StubObject) servers.get(i);
//        			id=so.getString("id", "");
//        			String server=so.getString("server", "");
//        			String clazz=so.getString("clazz", "");
//        			String method=so.getString("method", "");
//    	         
//        			so=EAIServer.getEAIServer(server);
//        			String bkid=paramObject.GetValueByEnvName("DataBaseName", "");
//        			String bkzt=paramObject.GetValueByEnvName("DBNO", "");
//        			String bkpt=paramObject.GetValueByEnvName("Product", "");
//        			try{
//        				String dbid  =so.getString(EAIServer.SERVER_DBID, "");
//        				String ztid = so.getString(EAIServer.SERVER_ZTID, "");
//        				paramObject.SetValueByEnvName("DataBaseName", dbid);
//        				paramObject.SetValueByEnvName("DBNO", ztid);
//        				paramObject.SetValueByEnvName("Product", id);
//        				paramObject.SetValueByParamName("getLIMIT","0");//不带重复装载权限
//        				Object cls=Class.forName(clazz).newInstance();
//        				JBOFClass.CallObjectMethod(cls, method,paramObject);
//        			}finally{
//        				paramObject.SetValueByEnvName("DataBaseName", bkid);
//        				paramObject.SetValueByEnvName("DBNO", bkzt);
//        				paramObject.SetValueByEnvName("Product", bkpt);
//        			}
//        		}
//        	} catch(Exception e){
//        		System.out.println(id+" Login init failed!");
//        		e.printStackTrace();
//        	}
//          
        	if(response.ErrorCode!=0){
        		return response;
        	}
        	return response;
        } catch ( Exception ex ) {
        	ex.printStackTrace();
        	response = new JResponseObject(ex.getMessage(),-1,ex);
        } finally {
        	conn.close();
        }
        return response;
	}
    
	/**
	  *
	  * @param PO JParamObject
	  * @return String
	  */
	 public String getUserSecrityServer2(JParamObject PO ){
	     String DataBaseName = PO.GetValueByEnvName("DataBaseName", "");
	     String DBNO = PO.GetValueByEnvName("DBNO", "");
	     String IDKEY = DataBaseName + "_" + DBNO;
	     java.util.Vector servers = PackageStub.getContentVector("UserSecurityServer");
	     String securityServer = "";
	     for (int i = 0; servers != null && i < servers.size(); i++) {
	         StubObject so = (StubObject) servers.get(i);
	         if (IDKEY.equals(so.getID())) securityServer = so.getString("securityServer", "");
	     }
	     return securityServer;
	 }
	 
    /**
     *
     * @param conn JConnection
     * @param PO JParamObject
     * @param User String
     * @param Pass String
     * @return boolean
     * @throws Exception
     */
    protected boolean checkUserPass(JConnection conn,JParamObject PO,String User,String Pass) throws Exception {
    	boolean res = true;
    	//3种密码加密方式：MD5(BH+PASS);MD5(PASS)
    	String encryptpass = MD5Tool.getDefault().getMD5ofStr(User + Pass);
    	String encryptpass1 = MD5Tool.getDefault().getMD5ofStr(Pass);
    	String encryptpass2 = MD5Util.MD5(Pass);
    	
    	//验证用户是否被冻结
    	String SQL = "select USER_ID, USER_NAME,USER_PASS from BSUSER where USER_ID = ? and ( USER_PASS = ? or USER_PASS = ? or USER_PASS = ?)";
    	Statement st = null;ResultSet rs = null;
    	PreparedStatement pst = conn.prepareStatement(SQL);
    	try {
    		// 验证密码
    		pst.setString(1, User);
    		pst.setString(2, encryptpass);
    		pst.setString(3, encryptpass1);
    		pst.setString(4, encryptpass2);
    		rs = pst.executeQuery();
    		res = rs.next();
    		System.out.println("user=" + User + ";Pass=" + " "+ encryptpass + " "+ encryptpass1);
    	} catch ( Exception e ) {
    		e.printStackTrace();
    		throw e;
    	} finally {
    		conn.BackStatement(st,rs);
    		conn.BackPreparedStatement(pst,null);
    	}
    	return res;
    }
   
    /**
     *
     * @param conn JConnection
     * @param PO JParamObject
     * @param serverName String
     * @return boolean
     * @throws Exception
     */
    protected boolean checkUserPassInServer(JConnection conn,JParamObject PO,String serverName) throws Exception {
    	JResponseObject RO = (JResponseObject) EAI.DAL.IOM(serverName + ".SecurityObject", "checkUserPassword", PO);
    	if (RO == null) throw new Exception("远程验证用户失败:没有连上远程服务器！");
    	if (RO.getErrorCode() == RO.RES_ERROR) throw new Exception("远程验证用户失败:" + RO.getResponseObject());
    	Boolean b = (Boolean)RO.getResponseObject();
    	return b.booleanValue();
    }
   
    /**
     *
     * @param conn JConnection
     * @param PO JParamObject
     */
	protected static StubObject getUserMap(JConnection conn,JParamObject PO) {
		String   UserName = PO.GetValueByEnvName("UserName");
		String     BSUSER = "BSUSER";StubObject userMap = null;
		String        SQL = "select * from " + BSUSER + " where USER_ID='" + UserName + "'";
		Statement    stmt = null;ResultSet rs = null;
		try {
			stmt = conn.GetStatement(conn);
			rs = stmt.executeQuery(SQL);
			if ( rs.next() ) {
				StubObject so = new StubObject();
				DBTools.ResultSetToStubObject(rs,so);
				userMap = so;
			}
		} catch ( Exception e ) {

		} finally {
			conn.BackStatement(stmt,rs);
		}
		return userMap;
	}
   
	/**
	 *
	 * @param map StubObject
	 * @param po JParamObject
	 */
	protected void initCustomConf(StubObject map,JParamObject po) {
	    if ( map == null ) return;
	    map.setString("dbOwner",po.GetValueByEnvName("dbOwner"));
	}
	
	/**
	 * 改为公共的静态方法
	 * @param paramObject JParamObject
	 * @param connection JConnection
	 * @throws Exception
	 */
	public static void writeLoginLog(JParamObject paramObject,JConnection connection) throws Exception {
		try {
			Date date = new Date(System.currentTimeMillis());
			long longTime = date.getTime();
			paramObject.setValue("timeBeginServer", new Long(longTime));
			String LOG_ID = (String) paramObject.getValue("_sessionID_", null);
			if (LOG_ID == null || LOG_ID.trim().length() == 0)
				return;
			String F_ZGBH = paramObject.GetValueByEnvName("UserName");
			String F_IP = (String) paramObject.getValue("_sessionIP_", null);
			String F_XTBH = paramObject.GetValueByEnvName("Product");
			String F_STIME = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(System.currentTimeMillis());
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			F_STIME = format.format(date);
			String tableName = DBTools.getDBAObject(paramObject, "SYS_LOGINLOG");
			String Fields = "LOG_ID,F_ZGBH,F_IP,F_XTBH,F_STIME";
			String Values = "'" + LOG_ID + "','" + F_ZGBH + "','" + F_IP + "','" + F_XTBH + "','" + F_STIME + "'";
			String SQL = DBTools.insertSql(tableName, Fields, Values);
			connection.ExecuteSQL(SQL);
		} catch ( Exception ex ) {
			ex.printStackTrace();
	    }
	}
	
	public JResponseObject LogoffProduct(JParamObject paramObject,Object o2,Object o3,Object o4) throws Exception {
	    String LOG_ID = (String)paramObject.getValue("_sessionID_",null);
	    if ( LOG_ID == null || LOG_ID.trim().length() == 0 ) return null;
	    // 获取数据库连接
	    JConnection conn = JConnection.getInstance(paramObject);
	    try {
	    	Date date = new Date();
	    	long longTime = date.getTime();
	    	paramObject.setValue("timeBeginServer",new Long(longTime));
	    	String F_ETIME = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(System.currentTimeMillis());
	    	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    	F_ETIME = format.format(date);
	    	String tableName = DBTools.getDBAObject(paramObject,"SYS_LOGINLOG");
	    	String sql = DBTools.updateSql(tableName,"F_ETIME","'"+F_ETIME+"'","LOG_ID='"+LOG_ID+"'");
	    	conn.ExecuteSQL(sql);
	    } finally {
	    	conn.close();
	    }
	    return null;
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
}