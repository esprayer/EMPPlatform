package jframework.MtoDB.BofJfc.DBO;

import java.sql.*;
import java.util.*;

import com.efounder.pub.util.Debug;
import com.efounder.pub.util.StringFunction;

/**
 * <p>Title: Java Database ExecSql Pansoft</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Pansoft</p>
 * @author Xyz
 * @version 1.0
 */

public class JDComSqlExec {
  public static final String TROUND = "round(";
  public static final String FROUND = "$(";
  public static int IInt = 1;
  public static int IString = 2;
  public JDComSqlExec() {

  }

  //----------------------------------------------------------------------------------------------
  //描述:执行查询出临时表中的Sql
  //设计: Xyz(2001.12.29)
  //实现: Xyz
  //修改:
  //----------------------------------------------------------------------------------------------
  private static String getAllSql(ResultSet rs) throws Exception {
    StringBuffer SSql = new StringBuffer();
    String SQL = null;
    while (rs.next()) {
      SQL = rs.getString("F_SQL");
      SQL = getRoundSQL(SQL);
      SSql.append(SQL);
    }
    return SSql.toString();
  }

  private static String getRoundSQL(String SQL) {
    SQL = StringFunction.replaceString(SQL, FROUND, TROUND);
    return SQL;
  }

  //----------------------------------------------------------------------------------------------
  //描述:获得形参个数
  //设计: Xyz(2001.12.29)
  //实现: Xyz
  //修改:
  //Param:参数数组、参数类型、存储过程名
  //----------------------------------------------------------------------------------------------
  private static String getParam(Object[] paramObjAry) {
    StringBuffer paramArray = new StringBuffer(" ?,?");
    for (int i = 0; i < paramObjAry.length; i++) {
      paramArray.append(",?");
    }
    return paramArray.toString();
  }

  //----------------------------------------------------------------------------------------------
  //描述:执行查询出临时表中的Sql
  //设计: Xyz(2001.12.29)
  //实现: Xyz
  //修改:chaor增加了批量更新
  //----------------------------------------------------------------------------------------------
  private static void processSql(Statement stat, String SSql) throws Exception {
    String vsTmpSql = null;
    Vector vsSqls = StringFunction.convertFromStringToStringVectorByString(SSql,
        "@_@");
    for (int i = 0; i < vsSqls.size(); i++) {
      vsTmpSql = (String) vsSqls.elementAt(i);
      Debug.PrintlnMessageToSystem(vsTmpSql);
//================================================================
      //vsTmpSql=com.pansoft.pub.util.StringFunction.replaceString(vsTmpSql,"\"","'");
//================================================================


//-------------------------------------------批量更新 by chaor-------------------------------------------------------------------------------
      //stat.executeUpdate(vsTmpSql) ;
      stat.addBatch(vsTmpSql);
//--------------------------------------------------------------------------------------------------------------------------
    }
//-------------------------------------------批量更新 by chaor-------------------------------------------------------------------------------
    stat.executeBatch();
//--------------------------------------------------------------------------------------------------------------------------
    /*String STmpSql = null;
     java.util.StringTokenizer st = new java.util.StringTokenizer(SSql,"@_@") ;
           while (st.hasMoreTokens()){
        STmpSql = (String)st.nextElement();
        Debug.PrintlnMessageToSystem(STmpSql);
        //stat.executeUpdate(STmpSql) ;
           }*/
  }
}
