package com.efounder.eai.service.dal;

import java.sql.*;
import java.util.*;

import com.core.xml.*;
import com.efounder.db.*;
import com.efounder.eai.*;
import com.efounder.eai.data.*;
import com.efounder.eai.service.*;
import com.efounder.service.security.*;
import com.efounder.sql.*;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2004</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class ProductDALUtils {
	/**
	 *
	 */
	protected ProductDALUtils() {
	}
	/**
	 * 产品的登录过程，检查用户名和密码
	 * @param o1 Object
	 * @param o2 Object
	 * @param o3 Object
	 * @param o4 Object
	 * @throws Exception
	 * @return Object
	 */
	public static Object loginProduct(JParamObject PO) throws Exception {
		// 根据提供的用户名和密码获取数据库连接
		JConnection jconn = JConnection.getInstance(PO);
		SecurityContext securityContext = new SecurityContext();
		try {
//    JConnection jconn = (JConnection)EAI.DAL.IOM("DBManagerObject","GetDBConnection", PO);
			// 初始化该产品
			initProduct(PO, jconn);
			initProduct2(PO, jconn);
			// 初始化SecurityContext
			if(PO.GetValueByParamName("getLIMIT","1").equals("1"))
				initSecurityContext(jconn, PO, securityContext);
			// 获取用户信息
			getUserInfo(jconn, PO);
			// 获取单位信息
			getUnitInfo(jconn, PO);
			// 装入系统信息
			java.util.Map unitInfoMap = SecurityUtils.getSysInfo(jconn, PO);
			if (unitInfoMap != null)
				PO.setValue("unitInfoMap", unitInfoMap);
		} catch ( Exception e ) {
			e.printStackTrace();
			throw e;
		} finally {
			jconn.close();
		}
//    jconn = JConnection.getInstance(PO);
//    jconn.close();
		// 将SecurityContext返回到前台
		Object[] res = {PO,securityContext};
		JResponseObject RO = new JResponseObject(res,0,null);
		return RO;
	}
	
	protected static void getUnitInfo(JConnection conn,JParamObject PO) {
		String F_DWBH = PO.GetValueByEnvName("USER_DEF_DWZD_BH",null);
		if ( F_DWBH == null || F_DWBH.trim().length() == 0 ) return;
		String STAT_DWZD = DBTools.getDBAObject(PO,"STAT_DWZD");
		String SQL = "select * from "+STAT_DWZD+" where DWZD_BH='"+F_DWBH+"'";
		Statement stmt = null;ResultSet rs = null;
		try {
			stmt = conn.GetStatement(conn);
			rs = stmt.executeQuery(SQL);
			if ( rs.next() ) {
				StubObject so = new StubObject();
				DBTools.ResultSetToStubObject(rs,so);
				PO.setValue("UnitInfo",so);
			}
		} catch ( Exception e ) {
			e.printStackTrace();
		} finally {
			conn.BackStatement(stmt,rs);
		}
	}
	
	/**
	 *
	 * @param conn JConnection
	 * @param PO JParamObject
	 */
	protected static StubObject getUserInfo(JConnection conn,JParamObject PO) {
		String UserName = PO.GetValueByEnvName("UserName");
		String BSUSER = "BSUSER";
		BSUSER = DBTools.getDBAObject(PO,BSUSER);
		String SQL = "select * from "+BSUSER+" where F_ZGBH='"+UserName+"'";
		Statement stmt = null;ResultSet rs = null;StubObject so = null;
		try {
			stmt = conn.GetStatement(conn);
			rs = stmt.executeQuery(SQL);
			if ( rs.next() ) {
				String F_NAME = rs.getString("F_NAME");
				so = new StubObject();
				DBTools.ResultSetToStubObject(rs,so);
				PO.setValue("UserInfo",so);
				PO.SetValueByEnvName("UserCaption",F_NAME);
				String F_DWBH = rs.getString("F_DWBH");
				// 设置用户的默认的单位编号
				if ( F_DWBH != null && F_DWBH.trim().length() > 0 )
					PO.SetValueByEnvName("USER_DEF_DWZD_BH",F_DWBH);
			}
		} catch ( Exception e ) {

		} finally {
			conn.BackStatement(stmt,rs);
		}
		return so;
	}
	
	/**
	 *
	 * @param conn JConnection
	 * @param PO JParamObject
	 * @param securityContext SecurityContext
	 * @throws Exception
	 */
	protected static void initSecurityContext(JConnection conn,JParamObject PO,SecurityContext securityContext) throws Exception {
		java.util.List securityList = SecurityUtils.getSecurityContextMap(conn,PO);
		String UserName = PO.GetValueByEnvName("UserName");
		securityContext.setUserFunctionList(UserName,securityList);
	}

	/**
	 *
	 * @param conn JConnection
	 * @param PO JParamObject
	 * @return SecurityContext
	 * @throws Exception
	 */
	public static SecurityContext getSecurityContext(JConnection conn,JParamObject PO) throws Exception {
		SecurityContext securityContext = new SecurityContext();
		java.util.List securityList = SecurityUtils.getSecurityContextMap(conn,PO);
		String UserName = PO.GetValueByEnvName("UserName");
		// 获取指定的其他服务上的功能权限
		java.util.List otherFunctions = getServerFunctionList(conn, PO);
		for (int i = 0; otherFunctions != null && i < otherFunctions.size(); i++) {
			if( otherFunctions.get(i) instanceof String){
				String function = (String) otherFunctions.get(i);
				if (!securityList.contains(function))
					securityList.add(function);
			}
		}
		securityContext.setUserFunctionList(UserName,securityList);
		return securityContext;
	}

  /**
   *
   * @param conn JConnection
   * @param paramObject JParamObject
   * @return List
   */
  public static java.util.List getServerFunctionList(JConnection conn,JParamObject paramObject){
      java.util.List servers = PackageStub.getContentVector("SecurityFunctionServer");
      java.util.List results = new ArrayList();
      for (int i = 0; servers != null && i < servers.size(); i++) {
          StubObject so = (StubObject) servers.get(i);
          String server = (String) so.getID();
          // 在产品中个性化获取功能权限
          if (so.getString("product", "").trim().length() > 0)
              continue;
          // 功能编号前是否增加ServerID
          boolean serverIDPrefix = so.getString("serverIDPrefix", "").equals("1");
          // 从其他Server中加载的功能权限
          java.util.List tmp = getServerFunctionList(conn, paramObject, server);
          //
          java.util.List fuc = tmp;
          if (serverIDPrefix)
              fuc = packList(tmp, server);
          if (fuc != null)
              results.addAll(fuc);
      }
      return results;
  }

  /**
   *
   * @param list List
   * @param prefix String
   * @return List
   */
  private static java.util.List packList(java.util.List list, String prefix) {
      java.util.List newList = new ArrayList(list.size());
      for (int i = 0; i < list.size(); i++) {
          newList.add(prefix + "." + list.get(i));
      }
      return newList;
  }

  /**
   *
   * @param conn JConnection
   * @param paramObject JParamObject
   * @return List
   */
  public static java.util.List getProductServerFunctionList(JConnection conn,JParamObject paramObject){
      java.util.List servers = PackageStub.getContentVector("SecurityFunctionServer");
      java.util.List results = new ArrayList();
      String product = paramObject.GetValueByEnvName("Product", "");
      for (int i = 0; servers != null && i < servers.size(); i++) {
          StubObject so = (StubObject) servers.get(i);
          String server = (String) so.getID();
          String[] products = so.getString("product", "").split(",");
          if (products != null && Arrays.asList(products).contains(product)) {
              java.util.List tmp = getServerFunctionList(conn, paramObject, server);
              // 功能编号前是否增加ServerID
              boolean serverIDPrefix = so.getString("serverIDPrefix", "").equals("1");
              //
              java.util.List fuc = tmp;
              if (serverIDPrefix)
                  fuc = packList(tmp, server);
              if (fuc != null)
                  results.addAll(fuc);
          }
      }
      return results;
  }


  /**
   *
   * @param conn JConnection
   * @param paramObject JParamObject
   * @param serverName String
   * @return List
   */
  public static java.util.List getServerFunctionList(JConnection conn,JParamObject PO, String serverName) {
      try {
          // 复制一个PO,不影响原始PO
          JParamObject paramObject = (JParamObject) PO.clone();
          // 根据用户使用单位属性获得的使用单位信息
          Object o = paramObject.getValue("SYS_UNITS", null);
//          String UNIT_ID = null;
//          if (o != null && o instanceof StubObject) {
//              // 使用单位编号
//              UNIT_ID = ( (StubObject) o).getString("UNIT_ID", "");
//          } else {
//              // 根据PO中设置的UNIT_ID,可能是核算主体的UNIT_ID
//              UNIT_ID = paramObject.GetValueByEnvName("UNIT_ID", "");
//          }
//          // 根据使用单位设置ServerID
//          UnitServer.setUnitEAIServer(paramObject, serverName, UNIT_ID);
          // 远程调用
          JResponseObject RO = (JResponseObject) EAI.DAL.IOM(serverName + ".SecurityObject", "getUserFunctionLimit", paramObject);
          if (RO != null && RO.getErrorCode() == RO.RES_OK) {
              return (java.util.List) RO.getResponseObject();
          }
      } catch (Exception ex) {
          ex.printStackTrace();
      }
      return null;
  }

  /**
   *
   * @param PO JParamObject
   * @param conn JConnection
   */
  protected static void initProduct(JParamObject PO,JConnection conn) {
    Statement stmt = null;ResultSet rs = null;String SQL,tableName;
    String F_VKEY,F_VAL;
    try {
      stmt = conn.GetStatement(conn);
      tableName = DBTools.getDBAObject(PO,"BSCONF");
      SQL = DBTools.selectAllSql(tableName,null);
      rs  = stmt.executeQuery(SQL);
      java.util.Map valueMap = new HashMap();
      while ( rs.next() ) {
        F_VKEY = rs.getString("F_VKEY");
        F_VAL  = rs.getString("F_VAL");
        if ( F_VKEY != null && F_VAL != null ) {
          valueMap.put(F_VKEY.trim(),F_VAL.trim());
//          PO.SetValueByEnvName(F_VKEY.trim(),F_VAL.trim());
        }
      }
      PO.setValue("BSCONF",valueMap);
    } catch ( Exception e ) {

    } finally {
      conn.BackStatement(stmt,rs);
    }
  }
  /**
   *
   * @param conn JConnection
   * @param PO JParamObject
   */
  protected static StubObject getUserMap(JConnection conn,JParamObject PO) {
    String UserName = PO.GetValueByEnvName("UserName");
    String BSUSER = "BSUSER";StubObject userMap = null;
    BSUSER = DBTools.getDBAObject(PO,BSUSER);
    String SQL = "select * from "+BSUSER+" where USER_ID='"+UserName+"'";
    Statement stmt = null;ResultSet rs = null;
    try {
      stmt = conn.GetStatement(conn);
      rs = stmt.executeQuery(SQL);
      if ( rs.next() ) {
        StubObject so = new StubObject();
        DBTools.ResultSetToStubObject(rs,so);
        userMap = so;
      }
    } catch ( Exception e ) {
    	e.printStackTrace();
    } finally {
      conn.BackStatement(stmt,rs);
    }
    return userMap;
  }
  public static StubObject getSYS_UNIT(JConnection conn,StubObject userStub,JParamObject paramObject) {
    // 初始化EAIServerID，UNIT_ID
    String UNIT_ID = userStub.getString("UNIT_ID",null);
    // 如果UNIT_ID为空，或是长度为0，则返回空
    if ( UNIT_ID == null || UNIT_ID.trim().length() == 0 ) return null;
    // 如果UNIT_ID为空，则返回空，使用当前登录的应用服务器
    if ( UNIT_ID == null ) return null;
    String SQL = "select * from "+DBTools.getDBAObject(paramObject,"SYS_UNITS")+" where UNIT_ID='"+UNIT_ID+"'";
    Statement stmt = null;ResultSet rs = null;StubObject stubObject = null;
    try {
      stmt = conn.GetStatement(conn);
      rs = stmt.executeQuery(SQL);
      if ( rs.next() ) {
        stubObject = new StubObject();
        DBTools.ResultSetToStubObject(rs,stubObject);
      }
    } catch ( Exception e ) {
      e.printStackTrace();
    } finally {
      conn.BackStatement(stmt,rs);
    }
    return stubObject;
  }
  public static StubObject getBSCONF(JParamObject PO,JConnection conn) {
    Statement stmt = null;ResultSet rs = null;String SQL,tableName;
    String F_VKEY,F_VAL;StubObject  valueMap = new StubObject();;
    try {
      stmt = conn.GetStatement(conn);
      tableName = DBTools.getDBAObject(PO,"LSCONF");
      SQL = DBTools.selectAllSql(tableName,null);
      rs  = stmt.executeQuery(SQL);
      while ( rs.next() ) {
        F_VKEY = rs.getString("F_VKEY");
        F_VAL  = rs.getString("F_VAL");
        if ( F_VKEY != null && F_VAL != null ) {
          valueMap.setString(F_VKEY.trim(),F_VAL.trim());
        }
      }
    } catch ( Exception e ) {

    } finally {
      conn.BackStatement(stmt,rs);
    }
    return valueMap;
  }
  public static StubObject getSYS_PRODUCT_ENV(JParamObject PO,JConnection conn) {
    Statement stmt = null;ResultSet rs = null;String SQL,tableName,where;
    String PRODUCT_ID,F_VKEY,F_VAL;StubObject valMap = null;
    try {
      stmt = conn.GetStatement(conn);
      tableName = DBTools.getDBAObject(PO,"SYS_PRODUCT_ENV");
      PRODUCT_ID = PO.GetValueByEnvName("Product");
      where = "PRODUCT_ID='"+PRODUCT_ID+"'";
      SQL = DBTools.selectAllSql(tableName,where);
      rs  = stmt.executeQuery(SQL);
      while ( rs.next() ) {
        F_VKEY = rs.getString("F_VKEY");
        F_VAL  = rs.getString("F_VAL");
        if ( F_VKEY != null && F_VAL != null ) {
          if ( valMap == null ) valMap = new StubObject();
          valMap.setString(F_VKEY.trim(),F_VAL.trim());
        }
      }
    } catch ( Exception e ) {

    } finally {
      conn.BackStatement(stmt,rs);
    }
    return valMap;
  }
  /**
   *
   * @param PO JParamObject
   * @param conn JConnection
   */
  protected static void initProduct2(JParamObject PO,JConnection conn) {
    Statement stmt = null;ResultSet rs = null;String SQL,tableName,where;
    String PRODUCT_ID,F_VKEY,F_VAL;java.util.Map valMap = null;
    try {
      stmt = conn.GetStatement(conn);
      tableName = DBTools.getDBAObject(PO,"SYS_PRODUCT_ENV");
      PRODUCT_ID = PO.GetValueByEnvName("Product");
      where = "PRODUCT_ID='"+PRODUCT_ID+"'";
      SQL = DBTools.selectAllSql(tableName,where);
      rs  = stmt.executeQuery(SQL);
      while ( rs.next() ) {
        F_VKEY = rs.getString("F_VKEY");
        F_VAL  = rs.getString("F_VAL");
        if ( F_VKEY != null && F_VAL != null ) {
          if ( valMap == null ) valMap = new HashMap();
          valMap.put(F_VKEY.trim(),F_VAL.trim());
        }
      }
    } catch ( Exception e ) {

    } finally {
      conn.BackStatement(stmt,rs);
    }
    if ( valMap != null )
      PO.setValue("ProductEnv",valMap);
  }
}
