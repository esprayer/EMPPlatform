package com.security.jdal;

import com.efounder.eai.framework.JActiveObject;
import com.efounder.pub.util.JCommonFunction;
import com.efounder.sql.JConnection;
import com.efounder.eai.data.JParamObject;
import com.efounder.eai.EAI;
import com.efounder.eai.data.JResponseObject;
import java.sql.*;
import com.efounder.db.*;
import com.security.jdal.dbo.DBOSecurityObject;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */
//--------------------------------------------------------------------------------------------------
//描述:
//设计: Skyline(2001.04.22)//实现: Skyline
//修改:
//--------------------------------------------------------------------------------------------------
public class JDALSecurityObject extends JActiveObject {
  private static final String GUID = "00000000-0003-0001-0000000000000001";
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  public JDALSecurityObject() {
    setObjectGUID(GUID);
  }

  public Object GetUserID(JConnection conn) {
    return DBOSecurityObject.GetUserID(conn);
  }

  public Object GetCurrentCaYear(JConnection conn) {
    return DBOSecurityObject.GetCurrentCaYear(conn);
  }
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: 本方法已经不再使用。
  //实现:
  //修改:
  //------------------------------------------------------------------------------------------------
  public Object ChangePassword(Object Param,Object Data,Object CustomObject,Object AdditiveObject) {
    JParamObject PO   = (JParamObject)Param;
    JConnection conn  = null;
    try{
      conn = (JConnection) EAI.DAL.IOM("DBManagerObject", "GetDBConnection", PO);
    }
    catch(Exception E){
      E.printStackTrace();
    }
    String user    = PO.GetValueByEnvName("UserName");
    String oldPass = PO.GetValueByEnvName("UserPass");
    String newPass = PO.GetValueByParamName("NewPass");
    try {
        String op1 = JCommonFunction.makeCNPCDarkPass(user,oldPass);
        String op2 = JCommonFunction.makeCNPCDarkPass(user,newPass);
        String SQL = "";
        if (op1 == "") {
            SQL = "sp_password null,'" + op2 + "'";
        }
        else {
            SQL = "sp_password '" + op1 + "','" + op2 + "'";
        }
        //execute
        conn.GetStatement(conn).execute(SQL);
    }catch(Exception e){
        e.printStackTrace();
        return new Integer(-1);
    }
    return null;
  }
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: LSL
  //实现: 本方法已经不再使用。使用业务系统层面的登录。
  //修改:
  //------------------------------------------------------------------------------------------------
  public Object Login(Object Param,Object Data,Object CustomObject,Object AdditiveObject) {
    JConnection conn=null;JResponseObject RO;
    JParamObject PO;String LoginDate,UserID,CurYear,Year,Res;
    PO = (JParamObject)Param;
    try{
      conn = (JConnection) EAI.DAL.IOM("DBManagerObject", "GetDBConnection", PO);
    }
    catch(Exception E){
      E.printStackTrace();
    }
    if ( conn != null ) {// 如果获取的连接不为空,说明登录成功,下面进行各种其他环境的设置
      LoginDate = PO.GetValueByEnvName("LoginDate");
      UserID  = DBOSecurityObject.GetUserID(conn);
      PO.SetValueByEnvName("UserID",UserID);
      CurYear    = DBOSecurityObject.GetCurrentCaYear(conn);
      if ( CurYear != null ) {
          // 如果登录年度与当前会计年度相等,表后缀为空
          if ( CurYear.substring(0,4).compareTo(LoginDate.substring(0,4)) <= 0 )
              Year = "";
          else
              Year = LoginDate.substring(0,4);
          // 设置当前系统中各种数据表的年度后缀
          PO.SetValueByEnvName("Suffixyear",Year);
          // 设置当前登录的会计年度
          PO.SetValueByEnvName("LoginFaYear",LoginDate.substring(0,4));
          // 设置当前会计年度
          PO.SetValueByEnvName("CurFaYear",CurYear.substring(0,4));
          // 设置当前会计日期
          PO.SetValueByEnvName("CurFaDate",CurYear);

      }
      conn.close();
      // 新建一个返回值对象
      // 归还数据库联接
      RO = new JResponseObject(PO,0,null);
      return RO;
    } else
      RO = new JResponseObject("登录失败!",-1);
      return RO;
  }
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: 检查功能权限。
  //实现: 根据PO查询它的功能权限列表。
  //修改:
  //------------------------------------------------------------------------------------------------
  public Object CheckFunctionLimit(Object Param,Object Data,Object CustomObject,Object AdditiveObject) {
    JConnection conn = null;JParamObject PO = (JParamObject)Param;JResponseObject RO;
    try{
      conn = (JConnection) EAI.DAL.IOM("DBManagerObject", "GetDBConnection", PO);
      String strSchemaName = DBTools.getDBOSchemaUser(PO);
      //切换模工。
      DBTools.ChangeSchema(conn,strSchemaName);
    }
    catch(Exception E){
      E.printStackTrace();
    }
    if ( DBOSecurityObject.CheckFunctionLimit(conn,PO) != null ) {
      RO = new JResponseObject(null,0,null);
    } else {
      RO = new JResponseObject("无权使用此功能!",-1);
    }
    return RO;
  }

  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: 注册一个功能权限。
  //实现: 给指定用户注册一个功能权限。
  //修改:
  //------------------------------------------------------------------------------------------------
  public Object RegFunctionLimit(Object Param,Object Data,Object CustomObject,Object AdditiveObject) {
    JConnection conn = null;JParamObject PO = (JParamObject)Param;JResponseObject RO;
    try{
      conn = (JConnection) EAI.DAL.IOM("DBManagerObject", "GetDBConnection", PO);
    }
    catch(Exception E){
      E.printStackTrace();
    }
    if ( DBOSecurityObject.CheckFunctionLimit(conn,PO) != null ) {
      RO = new JResponseObject(null,0,null);
    } else {
      RO = new JResponseObject("无权使用此功能!",-1);
    }
    return RO;
  }

  /**
   * 撤消一个功能权限。
   * @param Param Object
   * @param Data Object
   * @param CustomObject Object
   * @param AdditiveObject Object
   * @return Object
   */
  public Object UnregFunctionLimit(Object Param,Object Data,Object CustomObject,Object AdditiveObject) {
    JConnection conn = null;JParamObject PO = (JParamObject)Param;JResponseObject RO;
    try{
      conn = (JConnection) EAI.DAL.IOM("DBManagerObject", "GetDBConnection", PO);
    }
    catch(Exception E){
      E.printStackTrace();
    }
    if ( DBOSecurityObject.CheckFunctionLimit(conn,PO) != null ) {
      RO = new JResponseObject(null,0,null);
    } else {
      RO = new JResponseObject("无权使用此功能!",-1);
    }
    return RO;
  }

  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: 为指定用户指定的数据权限类型注册一个数据权限数据。
  //实现:
  //修改: 若具备，RCode == 0
  //------------------------------------------------------------------------------------------------
  public Object RegDataLimitItem(Object Param,Object Data,Object CustomObject,Object AdditiveObject) {
    JConnection conn = null;JParamObject PO = (JParamObject)Param;JResponseObject RO;
    try{
       conn = (JConnection) EAI.DAL.IOM("DBManagerObject", "GetDBConnection", PO);
       String strSchemaName = DBTools.getDBOSchemaUser(PO);
       //切换模工。
       DBTools.ChangeSchema(conn,strSchemaName);

     }
     catch(Exception E){
       E.printStackTrace();
     }
    if ( DBOSecurityObject.RegDataLimit(conn,PO) == true ) {
      RO = new JResponseObject(null,0,null);
    } else {
      RO = new JResponseObject("没有使用此数据的权限!",-1);
    }
    return RO;
  }

  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: 为指定用户指定的数据权限类型注册一个数据权限数据。
  //实现:
  //修改: 若具备，RCode == 0
  //------------------------------------------------------------------------------------------------
  public Object RegDataLimitItems(Object Param,Object Data,Object CustomObject,Object AdditiveObject) {
    JConnection conn = null;JParamObject PO = (JParamObject)Param;JResponseObject RO;
    try{
       conn = (JConnection) EAI.DAL.IOM("DBManagerObject", "GetDBConnection", PO);
       String strSchemaName = DBTools.getDBOSchemaUser(PO);
       //切换模工。
       DBTools.ChangeSchema(conn,strSchemaName);

     }
     catch(Exception E){
       E.printStackTrace();
     }
    if ( DBOSecurityObject.RegDataLimits(conn,PO) == true ){
      RO = new JResponseObject(null,0,null);
    } else {
      RO = new JResponseObject("没有使用此数据的权限!",-1);
    }
    return RO;
  }

  /**
   * 撤消一个数据权限。
   */
  public Object UnRegDataLimitItem(Object Param,Object Data,Object CustomObject,Object AdditiveObject) {
    JConnection conn = null;JParamObject PO = (JParamObject)Param;JResponseObject RO;
    try{
       conn = (JConnection) EAI.DAL.IOM("DBManagerObject", "GetDBConnection", PO);
       String strSchemaName = DBTools.getDBOSchemaUser(PO);
       //切换模工。
       DBTools.ChangeSchema(conn,strSchemaName);
     }
     catch(Exception E){
       E.printStackTrace();
     }
    if ( DBOSecurityObject.UnRegDataLimit(conn,PO) == true ) {
      RO = new JResponseObject(null,0,null);
    } else {
      RO = new JResponseObject("没有使用此数据的权限!",-1);
    }
    return RO;
  }

  public Object UnRegDataLimitItems(Object Param,Object Data,Object CustomObject,Object AdditiveObject) {
    JConnection conn = null;JParamObject PO = (JParamObject)Param;JResponseObject RO;
    try{
       conn = (JConnection) EAI.DAL.IOM("DBManagerObject", "GetDBConnection", PO);
       String strSchemaName = DBTools.getDBOSchemaUser(PO);
       //切换模工。
       DBTools.ChangeSchema(conn,strSchemaName);
     }
     catch(Exception E){
       E.printStackTrace();
     }
    if ( DBOSecurityObject.UnRegDataLimits(conn,PO) == true ) {
      RO = new JResponseObject(null,0,null);
    } else {
      RO = new JResponseObject("没有使用此数据的权限!",-1);
    }
    return RO;
  }

  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: 查询是否具备指定的数据权限。
  //实现:
  //修改: 若具备，RCode == 0
  //------------------------------------------------------------------------------------------------
  public Object CheckDataLimit1(Object Param,Object Data,Object CustomObject,Object AdditiveObject) {
    JConnection conn = null;JParamObject PO = (JParamObject)Param;JResponseObject RO;
    try{
       conn = (JConnection) EAI.DAL.IOM("DBManagerObject", "GetDBConnection", PO);
       String strSchemaName = DBTools.getDBOSchemaUser(PO);
       //切换模工。
       DBTools.ChangeSchema(conn,strSchemaName);
    }
     catch(Exception E){
       E.printStackTrace();
     }
    if ( DBOSecurityObject.CheckDataLimit1(conn,PO) == true ) {
      RO = new JResponseObject(null,0,null);
    } else {
      RO = new JResponseObject("没有使用此数据的权限!",-1);
    }
    return RO;
  }
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: 查询指定PO所具备的指定类型的数据权限列表。
  //实现:
  //修改:
  //------------------------------------------------------------------------------------------------
  public Object CheckDataLimit2(Object Param,Object Data,Object CustomObject,Object AdditiveObject) {
    JConnection conn = null;JParamObject PO = (JParamObject)Param;JResponseObject RO;
    try{
        conn = (JConnection) EAI.DAL.IOM("DBManagerObject", "GetDBConnection", PO);
        String strSchemaName = DBTools.getDBOSchemaUser(PO);
        //切换模工。
        DBTools.ChangeSchema(conn,strSchemaName);
        String Res = DBOSecurityObject.CheckDataLimit2(conn,PO);
        RO = new JResponseObject(Res,0,null);
    } catch(Exception E){
        E.printStackTrace();
        RO = new JResponseObject(null, -1, E);
    } finally {
        if (conn != null)
            conn.close();
    }
    return RO;
  }
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: 查询带有分级的数据权限列表。
  //实现:
  //修改:
  //------------------------------------------------------------------------------------------------
  public Object CheckDataLimit3(Object Param,Object Data,Object CustomObject,Object AdditiveObject) {
    JConnection conn = null;JParamObject PO = (JParamObject)Param;JResponseObject RO;
    try{
       conn = (JConnection) EAI.DAL.IOM("DBManagerObject", "GetDBConnection", PO);

       String strSchemaName = DBTools.getDBOSchemaUser(PO);
       //切换模工。
       DBTools.ChangeSchema(conn,strSchemaName);

     }
     catch(Exception E){
       E.printStackTrace();
     }
    String Res = DBOSecurityObject.CheckDataLimit3(conn,PO);
    RO = new JResponseObject(Res,0,null);
    return RO;
  }

  // 获取系统标准时间，数据库时间后者应用服务器时间
  public Object getSystemTimestamp(Object Param,Object Data,Object CustomObject,Object AdditiveObject) {
      JParamObject  PO = (JParamObject) Param;
      // 选项，数据库还是应用服务器DBServer/AppServer
      String    option = PO.GetValueByParamName("DateTimeOption", "DBServer");
      if (!"DBServer".equals(option)) return new JResponseObject(String.valueOf(System.currentTimeMillis()), 0, null);
      // 数据库方式
      JConnection conn = null;
      try {
          conn = (JConnection) EAI.DAL.IOM("DBManagerObject", "GetDBConnection", PO);
          //
          java.sql.Timestamp time = conn.getSQLToolkit().getSystemTimestamp(conn);
          return new JResponseObject(String.valueOf(time.getTime()), 0, null);
      } catch (Exception ex) {
          ex.printStackTrace();
      } finally {
          if (conn != null) conn.close();
      }
      return new JResponseObject(null, 1, null);
  }

}
