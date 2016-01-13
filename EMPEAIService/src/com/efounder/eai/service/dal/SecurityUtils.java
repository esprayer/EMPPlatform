package com.efounder.eai.service.dal;

import com.efounder.sql.*;
import com.efounder.eai.data.*;
import com.borland.dx.sql.dataset.*;
import com.efounder.db.*;
import java.sql.*;
import com.efounder.eai.service.ParameterManager;
import com.security.jdal.dbo.DBOSecurityObject;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class SecurityUtils implements DBSQL {
  /**
   *
   */
  public SecurityUtils() {
  }
  /**
   *
   * @param conn JConnection
   * @param paramObject JParamObject
   * @return Map
   * @throws Exception
   */
  public static java.util.Map getSysInfo(JConnection conn,JParamObject paramObject) throws Exception {
    String table   = DBTools.getDBAObject(paramObject,"SYS_UNIT_INFO");
    String Product = paramObject.GetValueByEnvName("Product","JEnterprise");
    String UNIT_BH = paramObject.GetValueByEnvName("USER_DEF_DWZD_BH","####");
    String SQL = "select INFO_KEY,INFO_VALUE from "+table;
    SQL += " where (UNIT_BH='####' or UNIT_BH='"+UNIT_BH+"') and SYS_ID='"+Product+"'";
    SQL += " order by UNIT_BH";
    Statement stmt =null;
    ResultSet rs = null;java.util.Map infoMap = null;
    try {
      stmt = conn.GetStatement(conn);
      rs = stmt.executeQuery(SQL);
      while (rs.next()) {
        if ( infoMap == null ) infoMap = new java.util.HashMap();
        String infoKey   = rs.getString("INFO_KEY");
        String infoValue = rs.getString("INFO_VALUE");
        infoMap.put(infoKey,infoValue);
      }
    } catch ( Exception e ) {
      throw e;
    } finally {
      conn.BackStatement(stmt,rs);
    }
    return infoMap;
  }
  /**
   *
   * @param conn JConnection
   * @param PO JParamObject
   * @return Map
   */
  public static java.util.List getSecurityContextMap(JConnection conn,JParamObject PO) throws Exception {
    Database dataBase = DBUtils.getDatabase(conn);
    // 获取当前用户编号
    String UserName  = PO.GetValueByEnvName("UserName");
    // 获取表名
//    String tableName = DBTools.getDBAObject(PO,"LSUSGN");
    String tableName = DBTools.getDBAObject(PO,"BSUSGN");
    // 形成条件
    Statement st=null;
    String SQL = "select t.F_GNBH from "+tableName+"  t ";
    SQL += " where ( t.F_ZGBH='" + UserName + "'  ";
    try{
        st=conn.createStatement();
        String rolwWhere = DBOSecurityObject.getRoleSqlWhere(PO, st, UserName, "t");
        if (rolwWhere != null && !"".equals(rolwWhere.trim())) {
            SQL += rolwWhere;
        }
    } catch(Exception e){
        e.printStackTrace();
    }
    SQL+= " ) ";

    // 增加审核字段
    if (ParameterManager.getDefault().isUseLimitAudit()) SQL += " and F_SH = '1'";

    ResultSet rs = null;
    java.util.List securityContext = new java.util.ArrayList();
    try {
        rs = st.executeQuery(SQL);

        // 循环处理每一个用户功能权限，增加到Context中
        while (rs.next()) {
          String GNBH = rs.getString("F_GNBH");
          if (GNBH != null && !securityContext.contains(GNBH)) {
            securityContext.add(GNBH.trim());
          }
        }
        rs.close();
        try {
          // 获取流程权限
          java.util.List flowList = getSysFlowList(conn, PO, UserName,
              "SYS_FLOW");
          for (int i = 0; flowList != null && i < flowList.size(); i++) {
            String GNBH = "_FLOW_" + flowList.get(i).toString().trim();
            securityContext.add(GNBH.trim());
          }
          Object map = DBOSecurityObject.CheckDataLimitNew(st, PO, UserName,
              "SYS_FLOW");
          securityContext.add(map);
        }
        catch (Exception ex) {
          ex.printStackTrace();
        }
      } finally  {
        st.close();
      }

    return securityContext;
  }
  /**
   *
   * @param conn JConnection
   * @param PO JParamObject
   * @param F_ZGBH String
   * @param F_QXBH String
   * @return List
   * @throws Exception
   */
  public static java.util.List getSysFlowList(JConnection conn,JParamObject PO,String F_ZGBH,String F_QXBH) throws Exception {
    Statement stmt = null;java.util.List flowList = null;
    stmt = conn.GetStatement(conn);
    try {
      flowList = DBOSecurityObject.CheckDataLimit0(stmt,PO,F_ZGBH,F_QXBH);
    } catch( Exception e ) {
      e.printStackTrace();
      throw e;
    } finally {
      stmt.close();
    }
    return flowList;
  }
}
