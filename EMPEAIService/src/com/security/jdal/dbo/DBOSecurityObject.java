package com.security.jdal.dbo;

import java.util.StringTokenizer;
import com.efounder.sql.JConnection;
import java.sql.*;

import java.sql.ResultSet;
import com.efounder.eai.data.JParamObject;
import com.efounder.pub.util.StringFunction;
import com.efounder.db.*;
import java.util.*;

import jtoolkit.date.classes.DateFunction;

import com.core.xml.*;
import com.efounder.builder.base.data.*;
import com.efounder.eai.service.ParameterManager;
import com.efounder.pub.util.MD5Tool;
import com.efounder.pub.util.RegexUtil;

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
public class DBOSecurityObject {
  //----------------------------------------------------------------------------------------------
  //描述: 获取用户唯一标识
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //----------------------------------------------------------------------------------------------
  public DBOSecurityObject() {
  }

  //----------------------------------------------------------------------------------------------
  //描述: 获取用户唯一标识
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //----------------------------------------------------------------------------------------------
  public static String GetUserID(JConnection conn) {
    Statement st = null;
    String SQL;
    ResultSet RS = null;
    String ID = null;
    int id;
    try {
      st = conn.createStatement();
      // 对此表此行加锁
      SQL = "UPDATE LSCONF SET F_VAL=F_VAL WHERE F_VKEY='CW_USERID'";
      st.executeUpdate(SQL);
      SQL = "SELECT F_VAL FROM LSCONF WHERE F_VKEY = 'CW_USERID'";
      RS = st.executeQuery(SQL);
      RS.next();
      ID = RS.getString(1);
      RS.close();
      ID = ID.trim();
      id = Integer.parseInt(ID);
      if (id > 9999)
        id = 0;
      id++;
      ID = Integer.toString(id);
      SQL = "UPDATE LSCONF SET F_VAL='" + ID + "' WHERE F_VKEY='CW_USERID'";
      st.executeUpdate(SQL);
    }
    catch (Exception e) {
      ID = "1";
      e.printStackTrace();
    }
    finally {
      conn.BackStatement(st, RS);
    }
    return ID;
  }

  //----------------------------------------------------------------------------------------------
  //描述: 获取当前会计年度
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //----------------------------------------------------------------------------------------------
  public static String GetCurrentCaYear(JConnection conn) {
    Statement st = null;
    String SQL;
    ResultSet RS;
    String Year = null;
    try {
      st = st = conn.createStatement();
      SQL = "SELECT F_VAL FROM LSCONF WHERE F_VKEY = 'ZW_LSCWRQ'";
      RS = st.executeQuery(SQL);
      RS.next();
      Year = RS.getString(1);
      Year = Year.trim();
      if (Year.length() < 4)
        Year = null;
      RS.close();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    finally {
      conn.BackStatement(st, null); 
    }
    return Year;
  }

  //----------------------------------------------------------------------------------------------
  //描述: 系统功能权限检查
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改: 返回值: 如果不为空,说明此用户有此项权限,如果为空,说明此用户没有此项权限
  //----------------------------------------------------------------------------------------------
  public static String CheckFunctionLimit(JConnection conn, JParamObject PO) {
    String Res = null;
    String SQL, UserName, FunctionNo, Suffixyear;
    ResultSet RS;
    Statement st = null;
    boolean exists = false;
    try {
      if (conn != null) {
        // 取出当前年度后缀
        Suffixyear = PO.GetValueByEnvName("Suffixyear");
        UserName = PO.GetValueByEnvName("UserName");
        FunctionNo = PO.GetValueByParamName("FunctionNo"); // 此项属于参数
        st = conn.createStatement();
        SQL = "select F_ZGBH from BSUSGN" + Suffixyear + " t ";
//            + " where F_ZGBH='" + UserName + "' and ";
//        String tmpTable = DBTools.getDBAObject(PO,"BSUSERROLE"+ Suffixyear);
        //add by lchong
        SQL += " where ( t.F_ZGBH='" + UserName + "'  ";
//            + " or ( exists( select 1 from "+tmpTable+" where f_rolecode = t.F_ZGBH and f_zgbh = '"+UserName+"' ) ) ) and ";

        try {
          String rolwWhere = getRoleSqlWhere(PO, st, UserName, "t");
          if (rolwWhere != null && !"".equals(rolwWhere.trim())) {
            SQL += rolwWhere;
          }
        }
        catch (Exception e) {
          e.printStackTrace();
        }
        SQL += " ) ";

        //add end

        SQL += " and t.F_GNBH='" + FunctionNo + "'";
        if (ParameterManager.getDefault().isUseLimitAudit())
          SQL += " and t.F_SH = '1'";

        RS = st.executeQuery(SQL);
        if (RS.next()) { // 如果不为空,有权限执行此项功能
          exists = true;
        }
        RS.close();
      }
    }
    catch (Exception e) {
      exists = false;
      e.printStackTrace();
    }
    finally {
      conn.BackStatement(st, null);
    }
    if (exists)
      Res = "OK!";
    return Res;
  }

  //----------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改: 返回值: 如果不为空,说明此用户有此项权限,如果为空,说明此用户没有此项权限
  //----------------------------------------------------------------------------------------------
  public static boolean gfchecksjqx1_gz(JConnection conn, String pszgbh,
                                        String psqxbh, String pssjbh, int pibzw,
                                        String pssuffixyear) {
    String vsSql, vsGzTab, vsSjTab = null, vsZdTab, vsBhcol = null;
    String vsGzqs, vsGzjz, vsGzout, vsNote, vsTemp;
    Statement st = null;
    ResultSet RS;
    int vlCount;
    st = conn.createStatement();
    if (st == null)
      return false;
    if (pssuffixyear == null)
      pssuffixyear = "";
    vsGzTab = "BSUSGZ" + pssuffixyear;
    vlCount = 0;
    vsSql = "select 1 from " + vsGzTab + " ";
    try {
      RS = st.executeQuery(vsSql);
      if (RS.next())
        vlCount = RS.getInt(1);
      RS.close();
    }
    catch (Exception e) {

    }
    if (vlCount <= 0) {
      vsGzTab = "BSUSGZ";
      pssuffixyear = "";
    }
    vsSql = "select F_TABN from BSGRAN" + pssuffixyear + " where F_QXBH ='" +
        psqxbh + "'";
    try {
      RS = st.executeQuery(vsSql);
      if (RS.next())
        vsSjTab = RS.getString(1);
      RS.close();
    }
    catch (Exception e) {
      vsSjTab = null;
      e.printStackTrace();
    }
    if (vsSjTab == null) {
      conn.BackStatement(st, null);
      return true;
    }
    vsSjTab = vsSjTab + pssuffixyear;
    vsZdTab = "BSGZZD" + pssuffixyear;

    vsSql = "select F_BHZD from BSGRAN" + pssuffixyear + " where F_QXBH ='" +
        psqxbh + "'";
    try {
      RS = st.executeQuery(vsSql);
      if (RS.next())
        vsBhcol = RS.getString(1);
      RS.close();
    }
    catch (Exception e) {
      vsBhcol = null;
      e.printStackTrace();
    }
    if (vsBhcol == null) {
      conn.BackStatement(st, null);
      return true;
    }

    //是循环逐条执行还是合在一起执行?
    vsSql = "select ZD.F_GZQS, ZD.F_GZJZ, ZD.F_GZOUT, ZD.F_NOTE from " +
        vsZdTab + " ZD, " +
        vsGzTab + " GZ ";

    //modified by lchong
    //    vsSql = vsSql + " where GZ.F_ZGBH ='" + pszgbh + "'"
    vsSql = vsSql + " where ( GZ.F_ZGBH ='" + pszgbh +
        "' or exists (select 1 from BSUSERROLE" + pssuffixyear +
        " where f_rolecode = GZ.f_zgbh and f_zgbh ='" + pszgbh + "' )) ";

    //modified end
    vsSql += " and GZ.F_QXBH ='" + psqxbh + "' and GZ.F_G" +
        String.valueOf(pibzw) + " ='1' ";
    vsSql = vsSql + " and ZD.F_QXBH ='" + psqxbh +
        "' and ZD.F_QXBH ='" + psqxbh + "' and ZD.F_GZBH =GZ.F_SJBH ";

    try {
      RS = st.executeQuery(vsSql);
      String[] SArray;
      int SCount, Index;
      Statement st1 = null;
      ResultSet RS1;
      st1 = conn.createStatement();
      if (st1 == null) {
        conn.BackStatement(st, null);
        return false;
      }
      vlCount = 0;
      while (RS.next()) {
        // 从RS中获取数据
        vsGzqs = RS.getString(1);
        vsGzjz = RS.getString(2);
        vsGzout = RS.getString(3);
        vsNote = RS.getString(4);
        vsSql = " select count(*) from " + vsSjTab + " SJ ";
        vsSql = vsSql + " where " + vsBhcol + " ='" + pssjbh + "'";
        if (vsNote == null || vsNote.trim().length() == 0) {
          vsSql = vsSql + " and " + vsBhcol + " between '" + vsGzqs + "' and '" +
              vsGzjz + "' ";
          if (vsGzout == null)
            vsGzout = "";
          SArray = StringFunction.convertFromStringToStringArrayBySymbol(
              vsGzout, ",");
          SCount = SArray.length;
          for (Index = 0; Index < SCount; Index++) {
            vsTemp = SArray[Index].trim();
            if (vsTemp.length() != 0)
              vsSql = vsSql + " and " + vsBhcol + " not like '" + vsTemp +
                  "%' ";
          }

        }
        else {
          vsSql = vsSql + " and (" + vsNote + ") ";
        }
        vlCount = 0;
        try {
          RS1 = st1.executeQuery(vsSql);
          if (RS1.next())
            vlCount = RS1.getInt(1);
          RS1.close();
        }
        catch (Exception e) {
          e.printStackTrace();
        }
        if (vlCount > 0)
          break;
      }
      RS.close();

      st1.close();
      st.close();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    finally {
      //conn.BackStatement(st,null);
    }
    if (vlCount > 0) {
      return true;
    }
    return false;
  }

  /**
   *
   * @param stmt Statement
   * @param PO JParamObject
   * @param F_ZGBH String
   * @param F_QXBH String
   * @return List
   * @throws Exception
   */
  public static java.util.Map CheckDataLimit(Statement stmt, JParamObject PO,
                                             String F_ZGBH, String F_QXBH) throws
      Exception {
    String tableName = DBTools.getDBAObject(PO, "BSUSSJ");
    String fields =
        "F_SJBH,F_ZGBH,F_QXBH,F_GRAN,F_G0,F_G1,F_G2,F_G3,F_G4,F_G5,F_G6,F_G7,F_G8";
    String where = "F_QXBH='" + F_QXBH + "' and ( ";
    //modified by lchong:添加角色过滤
    where += " F_ZGBH='" + F_ZGBH + "'";
    where += getRoleSqlWhere(PO, stmt, F_ZGBH, "");
    where += " ) ";
    if (ParameterManager.getDefault().isUseLimitAudit())
      where += " and F_SH = '1'";
    //modified end
    String SQL = DBTools.selectSql(tableName, fields, where);
    ResultSet rs = stmt.executeQuery(SQL);
    java.util.Map flowList = new java.util.HashMap();
    String F_SJBH;
    StubObject stub = null;
    while (rs.next()) {
      F_SJBH = rs.getString("F_SJBH");
      stub = new StubObject();
      DBTools.ResultSetToStubObject(rs, stub);
      flowList.put(F_SJBH, stub);
    }
    rs.close();
    return flowList;
  }

  /**
   *
   * @param stmt Statement
   * @param PO JParamObject
   * @param F_ZGBH String
   * @param F_QXBH String
   * @return List
   * @throws Exception
   */
  public static java.util.Map CheckDataLimitNew(Statement stmt, JParamObject PO,
                                                String F_ZGBH, String F_QXBH) throws
      Exception {

    String tableName = DBTools.getDBAObject(PO, "BSUSSJ");
    String SQL = "SELECT F_QXB FROM " + DBTools.getDBAObject(PO, "BSGRAN");
    SQL += " where F_QXBH='" + F_QXBH + "'";
    ResultSet rs = stmt.executeQuery(SQL);
    if (rs.next())
      tableName = DBTools.getDBAObject(PO, rs.getString(1));
    String fields =
        "F_SJBH,F_ZGBH,F_QXBH,F_GRAN,F_G0,F_G1,F_G2,F_G3,F_G4,F_G5,F_G6,F_G7,F_G8";
    String where = "F_QXBH='" + F_QXBH + "' and ( ";
    //modified by lchong:添加角色过滤
    where += " F_ZGBH='" + F_ZGBH + "'";
    where += getRoleSqlWhere(PO, stmt, F_ZGBH, "");
    where += " ) ";
    if (ParameterManager.getDefault().isUseLimitAudit())
      where += " and F_SH = '1'";
    //modified end
    SQL = DBTools.selectSql(tableName, fields, where);
    rs = stmt.executeQuery(SQL);
    java.util.Map flowList = new java.util.HashMap();
    String F_SJBH;
    StubObject stub = null;
    while (rs.next()) {
      F_SJBH = rs.getString("F_SJBH");
      EFRowSet so = (EFRowSet) flowList.get(F_SJBH);
      if (so == null) {
        so = EFRowSet.getInstance();
        flowList.put(F_SJBH, so);
      }
      for (int i = 0; i <= 8; i++) {
        if ("1".equals(rs.getString("F_G" + i)))
          so.putString("QX.F_G" + i, "1");
      }
    }
    rs.close();
    return flowList;
  }

  /**
   *
   * @param stmt Statement
   * @param PO JParamObject
   * @param F_ZGBH String
   * @param F_QXBH String
   * @return List
   * @throws Exception
   */
  public static java.util.List CheckDataLimit0(Statement stmt, JParamObject PO,
                                               String F_ZGBH, String F_QXBH) throws
      Exception {
    String tableName = DBTools.getDBAObject(PO, "BSUSSJ");
    String fields =
        "F_SJBH,F_ZGBH,F_QXBH,F_GRAN,F_G0,F_G1,F_G2,F_G3,F_G4,F_G5,F_G6,F_G7,F_G8";
    String where = "F_QXBH='" + F_QXBH + "' and ( ";
    //modified by lchong:添加角色过滤
    where += "  F_ZGBH='" + F_ZGBH + "'";
    where += getRoleSqlWhere(PO, stmt, F_ZGBH, "");
    where += " ) ";
    if (ParameterManager.getDefault().isUseLimitAudit())
      where += " and F_SH = '1'";
    //modified end
    String SQL = DBTools.selectSql(tableName, fields, where);
    ResultSet rs = stmt.executeQuery(SQL);
    java.util.List flowList = new java.util.ArrayList();
    String F_SJBH;
    while (rs.next()) {
      F_SJBH = rs.getString("F_SJBH");
      flowList.add(F_SJBH);
    }
    rs.close();
    return flowList;
  }

  //----------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改: 返回值: 如果不为空,说明此用户有此项权限,如果为空,说明此用户没有此项权限
  //----------------------------------------------------------------------------------------------
  public static boolean CheckDataLimit1(JConnection conn, JParamObject PO) {
    String pszgbh, psqxbh, pssjbh, pssuffixyear, pstmp, UNIT_ID, unit_id = " ";
    int pibzw = 0;
    String vstemp, vssql, vstbl, vsfzlb, vssavetbl, vsSfsy;
    int vicount;
    //JParamObject PO = new JParamObject(p0);
    Statement st = null;
    ResultSet RS;

    String OFFLINE = PO.GetValueByEnvName("OFFLINE", "0");
    if (OFFLINE.equals("1")) {
      return true;
    }
    // 获取PB函数中相应的函数参数
    pszgbh = PO.GetValueByParamName("pszgbh");
    psqxbh = PO.GetValueByParamName("psqxbh");
    pssjbh = PO.GetValueByParamName("pssjbh");
    pssuffixyear = PO.GetValueByEnvName("Suffixyear");
    pstmp = PO.GetValueByParamName("pibzw");
    pibzw = Integer.parseInt(pstmp);
    UNIT_ID = PO.GetValueByEnvName("UNIT_ID", " ");

    if (pszgbh.equals("9999")) {
      return true;
    }
    // 转换PB的函数体
    if (pssuffixyear == null)
      pssuffixyear = "";
    // 如果在职工编号中存在","
    if (pszgbh.indexOf(",") > 0) {
      // 获取最右边一个字符
      vsfzlb = pszgbh.substring(pszgbh.length() - 1, pszgbh.length() - 1);
      // 获取","以前的字符
      pszgbh = pszgbh.substring(0, pszgbh.indexOf(",") - 1);
      //com.pansoft.pub.util.StringFunction. get_token(pszgbh,',')
      //以下去掉。FNDLVR
      /*
             if (vsfzlb.equals("1")) {
        PO.SetValueByParamName("F_VKEY", "ZW_ZXQXKZ");
        vstemp =
            DBOBusinessEnvObject.GetLsConfig(conn, PO);
        // 为空,取数出错,返回空
        if (vstemp == null) {
          return false;
        }
        // 如果不等1,返回成功
        if (!vstemp.equals("1")) {
          return true;
        }
             }
             if (vsfzlb.equals("2")) {
        PO.SetValueByParamName("F_VKEY", "ZW_DWQXKZ");
        vstemp =
            DBOBusinessEnvObject.GetLsConfig(conn, PO);
        // 为空,取数出错,返回空
        if (vstemp == null) {
          return false;
        }
        // 如果等于0,返回成功
        if (vstemp.equals("0")) {
          return true;
        }
             }
             if (vsfzlb.equals("3")) {
        PO.SetValueByParamName("F_VKEY", "ZW_GRQXKZ");
        vstemp =
            DBOBusinessEnvObject.GetLsConfig(conn, PO);
        // 为空,取数出错,返回空
        if (vstemp == null) {
          return false;
        }
        // 如果等于0,返回成功
        if (!vstemp.equals("1")) {
          return true;
        }
             }
       */
    }
    st = conn.createStatement();
    if (st == null)
      return false;
//    try {
//      //判断是否使用全部科目
//      //以下不再作用。
//      if (pssuffixyear.length() == 0 && psqxbh.equals("ZWKMQX") && pibzw == 1) {
//        vicount = 0;
//        vssql = "select F_SYKM from LSPASS where F_ZGBH='" + pszgbh + "'";
//        RS = st.executeQuery(vssql);
//        if (RS.next())
//          vicount = RS.getInt("F_SYKM");
//        RS.close();
//        //st.close();
//        if (vicount == 1) {
//          conn.BackStatement(st, null);
//          return true;
//        }
//      }
//    }
//    catch (Exception e) {
//      e.printStackTrace();
//    }
      ;
    //取是否使用标志(0为不使用; 1为使用数据权限; 2为使用规则)
    String strGranTable = DBTools.getDBAObject(PO, "BSGRAN" + pssuffixyear);
    vsSfsy = "";
    vssql =
        "select F_SFSY,F_QXDW from " + strGranTable + pssuffixyear +
        " where F_QXBH='" + psqxbh +
        "' and (F_QXDW = ' ' OR F_QXDW like '%" + UNIT_ID + "%')";
    try {
      RS = st.executeQuery(vssql);
      if (RS.next()) {
        vsSfsy = RS.getString("F_SFSY");
        unit_id = RS.getString("F_QXDW");
      }
      else {
        conn.BackStatement(st, RS);
        return true;
      }
      RS.close();
      //st.close();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    if (vsSfsy.length() == 0 || vsSfsy == null) {
      conn.BackStatement(st, null);
      return false;
    }
    if (vsSfsy.equals("0")) {
      conn.BackStatement(st, null);
      return true;
    }
    if (vsSfsy.equals("2")) { //检查规则
      conn.BackStatement(st, null);
      return gfchecksjqx1_gz(conn, pszgbh, psqxbh, pssjbh, pibzw, pssuffixyear);
    }
    //以下为检查数据权限
    vssql =
        "select F_QXB from " + strGranTable + pssuffixyear + " where F_QXBH='" +
        psqxbh +
        "' and F_QXDW = '" + unit_id + "'";
    vssavetbl = DBTools.getDBAObject(PO, "BSUSSJ" + pssuffixyear);
    try {
      RS = st.executeQuery(vssql);
      if (RS.next())
        vssavetbl = DBTools.getDBAObject(PO,
                                         RS.getString("F_QXB") + pssuffixyear);
      RS.close();
      //st.close();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    if (vssavetbl == null || vssavetbl.trim().length() == 0)
      vssavetbl = DBTools.getDBAObject(PO, "BSUSSJ" + pssuffixyear);
    vstbl = vssavetbl + pssuffixyear;

    //判断是否分级
    vstemp = "";
    vssql =
        "select F_BMJG from " + strGranTable + pssuffixyear +
        "  where F_QXBH='" +
        psqxbh + "' and F_QXDW = '" + unit_id + "'";
    try {
      RS = st.executeQuery(vssql);
      if (RS.next()) {
        vstemp = RS.getString("F_BMJG");
        if (vstemp == null) {
          vstemp = "";
        }

      }

      RS.close();
      //st.close();
    }
    catch (Exception e) {
      e.printStackTrace();
    }

    //分级处理。
    if (vstemp != null && vstemp.length() != 0) {
      vssql =
          "select count(*) from "
          + vstbl
          + " BSUSSJ where '"
          //modified by lchong
          + pssjbh + "' LIKE BSUSSJ.F_SJBH || '%' and BSUSSJ.F_G";
//      + pssjbh + "' LIKE BSUSSJ.F_SJBH and BSUSSJ.F_G";
      //modified end
      vssql += String.valueOf(pibzw)
          + "='1' and BSUSSJ.F_QXBH='"
          + psqxbh + "' ";
      //modified by lchong
//          + " and BSUSSJ.F_ZGBH='"
//          + pszgbh
//          + "'";
//      String tmpTable = DBTools.getDBAObject(PO,"BSUSERROLE"+ pssuffixyear);
      vssql += " and ( BSUSSJ.F_ZGBH='" + pszgbh + "' ";
      // or exists ( select 1 from "+tmpTable+" where f_rolecode = BSUSSJ.F_ZGBH and f_zgbh = '"+pszgbh+"' )  ) ";
      try {
        String rolwWhere = getRoleSqlWhere(PO, st, pszgbh, "BSUSSJ");
        if (rolwWhere != null && !"".equals(rolwWhere.trim())) {
          vssql += rolwWhere;
        }
      }
      catch (Exception e) {
        e.printStackTrace();
      }
      vssql += " ) ";

      //modified end
    }
    else {
      //不分级处理。
      vssql =
          "select count(*) from "
          + vstbl
          + "  BSUSSJ where  BSUSSJ.F_SJBH='"
          + pssjbh
          + "'  and BSUSSJ.F_G";
      vssql += String.valueOf(pibzw)
          + "='1' and BSUSSJ.F_QXBH='"
          + psqxbh + "'";
      //modified by lchong
//          + " and BSUSSJ.F_ZGBH='"
//          + pszgbh
//          + "'";
//      String tmpTable = DBTools.getDBAObject(PO,"BSUSERROLE"+ pssuffixyear);
      vssql += " and ( BSUSSJ.F_ZGBH='" + pszgbh + "'";
      // or exists ( select 1 from " + tmpTable + " BSUSERROLE where f_rolecode = BSUSSJ.F_ZGBH and f_zgbh = '"+pszgbh+"' )  ) ";
      try {
        String rolwWhere = getRoleSqlWhere(PO, st, pszgbh, "BSUSSJ");
        if (rolwWhere != null && !"".equals(rolwWhere.trim())) {
          vssql += rolwWhere;
        }
      }
      catch (Exception e) {
        e.printStackTrace();
      }
      vssql += " ) ";

      //modified end
    }
    vicount = 0;
    try {
      RS = st.executeQuery(vssql);
      if (RS.next())
        vicount = RS.getInt(1);
      RS.close();
      //st.close();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    finally {
      conn.BackStatement(st, null);
    }
    if (vicount == 0)
      return false;
    return true;
  }

  //----------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改: 返回值: 如果不为空,说明此用户有此项权限,如果为空,说明此用户没有此项权限
  //----------------------------------------------------------------------------------------------
  public static String gfchecksjqx2_gz(JConnection conn, String pszgbh,
                                       String psqxbh, String pscol, int pibzw,
                                       String pssuffixyear, JParamObject PO) {
    String vsSql, vsGzTab, vsSjTab = null, vsZdTab, vsBhcol = null;
    String vsGzqs, vsGzjz, vsGzout, vsNote, vsTemp;
    Statement st = null;
    ResultSet RS;
    int vlCount;
    String TRUE = "(1=1)", FALSE = "(1=2)";
    st = conn.createStatement();
    if (st == null)
      return FALSE;

    if (pssuffixyear == null)
      pssuffixyear = "";

    vsGzTab = "BSUSGZ" + pssuffixyear;
    String strGZTable = DBTools.getDBAObject(PO, vsGzTab);

    vlCount = 0;
    vsSql = "select 1 from " + strGZTable + " ";
    try {
      RS = st.executeQuery(vsSql);
      if (RS.next())
        vlCount = RS.getInt(1);
      RS.close();
    }
    catch (Exception e) {

    }
    if (vlCount <= 0) {
      vsGzTab = "BSUSGZ";
      pssuffixyear = "";
    }

    String strGranTable = DBTools.getDBAObject(PO, "BSGRAN" + pssuffixyear);
    vsSql = "select F_TABN from " + strGranTable + " where F_QXBH ='" +
        psqxbh + "'";
    try {
      RS = st.executeQuery(vsSql);
      if (RS.next())
        vsSjTab = RS.getString(1);
      RS.close();
      //st.close();
    }
    catch (Exception e) {
      vsSjTab = null;
      e.printStackTrace();
    }
    if (vsSjTab == null) {
      conn.BackStatement(st, null);
      return TRUE;
    }
    vsSjTab = vsSjTab + pssuffixyear;
    vsZdTab = "BSGZZD" + pssuffixyear;

    String strGZZD = DBTools.getDBAObject(PO, vsZdTab);
    String strGranSJ = DBTools.getDBAObject(PO, vsSjTab + pssuffixyear);
    String strGZSJ = DBTools.getDBAObject(PO, vsGzTab + pssuffixyear);

    vsSql = "select F_BHZD from " + strGranTable + " where F_QXBH ='" +
        psqxbh + "'";
    try {
      RS = st.executeQuery(vsSql);
      if (RS.next())
        vsBhcol = RS.getString(1);
      RS.close();
      //st.close();
    }
    catch (Exception e) {
      vsBhcol = null;
      e.printStackTrace();
    }
    if (vsBhcol == null) {
      conn.BackStatement(st, null);
      return TRUE;
    }

    //是循环逐条执行还是合在一起执行?
    vsSql = "select ZD.F_GZQS, ZD.F_GZJZ, ZD.F_GZOUT, ZD.F_NOTE from " +
        strGZZD + " ZD, " + strGZSJ + " GZ ";
    //modified by lchong
//    vsSql = vsSql + " where GZ.F_ZGBH ='" + pszgbh + "'"
//      String tmpTable = DBTools.getDBAObject(PO,"BSUSERROLE"+ pssuffixyear);
    vsSql = vsSql + " where ( GZ.F_ZGBH ='" + pszgbh + "'";
//      or (exists( select 1 from "+tmpTable+" where f_rolecode = GZ.f_zgbh and f_zgbh ='"+pszgbh+"' ) )"

    try {
      String rolwWhere = getRoleSqlWhere(PO, st, pszgbh, "GZ");
      if (rolwWhere != null && !"".equals(rolwWhere.trim())) {
        vsSql += rolwWhere;
      }
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    vsSql += " ) ";

    //modified end
    vsSql += " and GZ.F_QXBH ='"
        + psqxbh + "' and GZ.F_G" + String.valueOf(pibzw) + " ='1' ";
    vsSql = vsSql + " and ZD.F_QXBH ='" + psqxbh +
        "' and ZD.F_GZBH =GZ.F_SJBH ";

    try {
      RS = st.executeQuery(vsSql);
      String[] SArray;
      int SCount, Index;

      vlCount = 0;

      if (pscol.toLowerCase().indexOf("substr") >= 0) {
        vsTemp = pscol.trim();
        if (vsTemp.indexOf(",") >= 0) {
          SArray = StringFunction.convertFromStringToStringArrayBySymbol(vsTemp,
              ",");
          vsTemp = SArray[0].trim();
          vsSql = " exists (select 1 from " + strGranSJ +
              " SJ where (substr(SJ." + vsBhcol + "," + vsTemp + " =" + pscol +
              ") and (";
        }
        else {
          vsSql = " exists (select 1 from " + strGranSJ + " SJ where (SJ." +
              vsBhcol + " =" + pscol + ") and (";
        }
      }
      else {
        vsSql = " exists (select 1 from " + strGranSJ + " SJ where (SJ." +
            vsBhcol + " =" + pscol + ") and (";
      }
      vsSql = " exists (select 1 from " + strGranSJ + " SJ where (SJ." +
          vsBhcol +
          " =" + pscol + ") and (";
      if (RS.next()) {
        while (true) {
          // 从RS中获取数据
          vsGzqs = RS.getString(1);
          vsGzjz = RS.getString(2);
          vsGzout = RS.getString(3);
          vsNote = RS.getString(4);
          if (vsNote == null || vsNote.trim().equals("")) {
            vsSql = vsSql + " (SJ." + vsBhcol + " between '" + vsGzqs +
                "' and '" +
                vsGzjz + "' ";
            if (vsGzout == null)
              vsGzout = "";
            SArray = StringFunction.convertFromStringToStringArrayBySymbol(
                vsGzout, ",");
            SCount = SArray.length;
            for (Index = 0; Index < SCount; Index++) {
              vsTemp = SArray[Index].trim();
              if (vsTemp.length() != 0)
                vsSql = vsSql + " and SJ." + vsBhcol + " not like '" + vsTemp +
                    "%' ";
            }
          }
          else {
            vsSql = vsSql + "(" + vsNote;
          }
          vsSql = vsSql + " ) or ";

          //下一条规则。
          if (!RS.next()) {
            break;
          }

        }
      }
      else {
        //没有任何一条规则定义。
        vsSql = vsSql + " 1=1 or ";
      }

      RS.close();
      //st.close();
    }
    catch (Exception e) {
      e.printStackTrace();
      return FALSE;
    }
    finally {
      conn.BackStatement(st, null);
    }
    if (vsSql.endsWith("or ")) {
      vsSql = vsSql.substring(0, vsSql.length() - 3);
    }
    vsSql = vsSql + " )) ";
    return vsSql;
  }

  //----------------------------------------------------------------------------------------------
  //描述: 改写自c/s 2003.08.22 哪个函数调用这个方法？
  //设计:
  //实现: God
  //修改: 与原来的函数相比，增加了一个SQL参数，用来去掉子查询，优化查询速度
  //----------------------------------------------------------------------------------------------
  public static String gfchecksjqx2_gz_sql(JConnection conn, String pszgbh,
                                           String psqxbh, String pscol,
                                           int pibzw,
                                           String pssuffixyear,
                                           String pssql, JParamObject PO) {
    String vsSql = "", vsFetch = "", vsGztab = "", vsSjtab = "", vsZdtab = "",
        vsBhcol = "", vsNote = "";
    String vsGzqs = "", vsGzjz = "", vsGzout = "", vsTemp = "", returnSQL = "";
    int vlCount = 0, vlWherePos = -1;
    Statement st = null;
    ResultSet RS = null;
    String TRUE = "1=1", FALSE = "1=2";
    st = conn.createStatement();
    if (st == null) {
      return pssql + FALSE;
    }

    if (pssuffixyear == null)
      pssuffixyear = "";
    if (pssql == null || pssql.trim().equals("")) {
      return DBOSecurityObject.gfchecksjqx2_gz(conn, pszgbh, psqxbh, pscol,
                                               pibzw, pssuffixyear, PO);
    }

    vlWherePos = pssql.toLowerCase().indexOf("where");
    if (vlWherePos < 0) {
      pssql = pssql + " where ";
    }
    else {
      pssql = pssql.substring(0, vlWherePos - 1) + " where ( " +
          pssql.substring(vlWherePos + 5) + ") and ";
    }

    vsGztab = "BSUSGZ" + pssuffixyear;
    vsSql = " select count(*) from dbo.sysobjects where name ='" + vsGztab +
        "'";
    try {
      RS = st.executeQuery(vsSql);
      if (RS.next())
        vlCount = RS.getInt(1);
      RS.close();
      //st.close();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    if (vlCount <= 0) {
      vsGztab = "LSUSGZ";
      pssuffixyear = "";
    }
    vsSql = "select F_TABN from BSGRAN" + pssuffixyear + " where F_QXBH ='" +
        psqxbh + "'";
    try {
      RS = st.executeQuery(vsSql);
      if (RS.next())
        vsSjtab = RS.getString(1);
      RS.close();
      //st.close();
    }
    catch (Exception e) {
      vsSjtab = null;
      e.printStackTrace();
    }
    if (vsSjtab == null) {
      conn.BackStatement(st, null);
      return pssql + TRUE;
    }
    vsSjtab = vsSjtab + pssuffixyear;
    vsZdtab = "BSGZZD" + pssuffixyear;
    vsSql = "select F_BHZD from BSGRAN" + pssuffixyear + " where F_QXBH ='" +
        psqxbh + "'";

    try {
      RS = st.executeQuery(vsSql);
      if (RS.next())
        vsBhcol = RS.getString(1);
      RS.close();
      //st.close();
    }
    catch (Exception e) {
      vsBhcol = null;
      e.printStackTrace();
    }
    if (vsBhcol == null) {
      conn.BackStatement(st, null);
      return pssql + TRUE;
    }
    //是循环逐条执行还是合在一起执行?
    vsSql = "select ZD.F_GZQS, ZD.F_GZJZ, ZD.F_GZOUT, ZD.F_NOTE from " +
        vsZdtab + " ZD, " + vsZdtab + " GZ ";
    //modified by lchong
//    vsSql = vsSql + " where GZ.F_ZGBH ='" + pszgbh + "'"
    String tmpTable = DBTools.getDBAObject(PO, "BSUSERROLE" + pssuffixyear);
    vsSql = vsSql + " where (GZ.F_ZGBH ='" + pszgbh + "'";
//   该语句效率很低 or exists (select 1 from "+tmpTable+" where f_rolecode = GZ.f_zgbh and f_zgbh = '" + pszgbh + "') )"
    try {
      String rolwWhere = getRoleSqlWhere(PO, st, pszgbh, "GZ");
      if (rolwWhere != null && !"".equals(rolwWhere.trim())) {
        vsSql += rolwWhere;
      }
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    vsSql += " ) ";

    //modified end

    vsSql += " and GZ.F_QXBH ='"
        + psqxbh + "' and GZ.F_G" + String.valueOf(pibzw) + " ='1' ";
    vsSql = vsSql + " and ZD.F_QXBH ='" + psqxbh +
        "' and ZD.F_GZBH =GZ.F_SJBH ";

    try {
      String[] SArray;
      int SCount, Index;
      if (vlWherePos < 0) {
        pssql = pssql.substring(0, pssql.length() - 6) + ", " + vsSjtab +
            " SJ where ";
      }
      else {
        pssql = pssql.substring(0, vlWherePos - 6) + ", " + vsSjtab +
            " SJ where ";
      }
      if (pscol.toLowerCase().indexOf("substr") >= 0) {
        vsTemp = pscol.trim();
        if (vsTemp.indexOf(",") >= 0) {
          StringTokenizer ster = new StringTokenizer(vsTemp);
          vsTemp = ster.nextToken();
          pssql = pssql + " (substr(SJ." + vsBhcol + "," + vsTemp + " =" +
              pscol + ") and (";
        }
        else {
          pssql = pssql + " (SJ." + vsBhcol + " =" + pscol + ") and (";
        }

      }
      else {
        pssql = pssql + " (SJ." + vsBhcol + " =" + pscol + ") and (";
      }
      RS = st.executeQuery(vsSql);
      while (RS.next()) {
        // 从RS中获取数据
        vsGzqs = RS.getString(1);
        vsGzjz = RS.getString(2);
        vsGzout = RS.getString(3);
        vsNote = RS.getString(4);
        if (vsNote == null || vsNote.trim().equals("")) {
          pssql = pssql + " (SJ." + vsBhcol + " between '" + vsGzqs + "' and '" +
              vsGzjz + "' ";
          if (vsGzout == null)
            vsGzout = "";
          SArray = StringFunction.convertFromStringToStringArrayBySymbol(
              vsGzout, ",");
          SCount = SArray.length;
          for (Index = 0; Index < SCount; Index++) {
            vsTemp = SArray[Index].trim();
            if (vsTemp.length() != 0)
              pssql = pssql + " and SJ." + vsBhcol + " not like '" + vsTemp +
                  "%' ";
          }
        }
        else {
          pssql = pssql + "(" + vsNote;
        }
        pssql = pssql + " ) or ";
      }
      RS.close();

    }
    catch (Exception e) {
      e.printStackTrace();
      return FALSE;
    }
    finally {
      conn.BackStatement(st, null);
    }
    if (pssql.endsWith("or ")) {
      pssql = pssql.substring(0, pssql.length() - 3);
    }
    pssql = pssql + " )) ";
    return pssql;
  }

  //----------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改: 返回值: 如果不为空,说明此用户有此项权限,如果为空,说明此用户没有此项权限
  //----------------------------------------------------------------------------------------------
  public static String CheckDataLimit2(JConnection conn, JParamObject PO) {
    String pszgbh, psqxbh, pscol, pssuffixyear, pstmp, UNIT_ID, unit_id = " ";
    int pibzw = 0;
    String vstemp, vssql, vstbl, vsfzlb, vssavetbl, vsSfsy;
    int vicount;
    Statement st = null;
    ResultSet RS;
    String TRUE = "1=1", FALSE = "1=2";

    String OFFLINE = PO.GetValueByEnvName("OFFLINE", "0");
    if (OFFLINE.equals("1")) {
      return TRUE;
    }

    // 获取PB函数中相应的函数参数
    UNIT_ID = PO.GetValueByEnvName("UNIT_ID", " ");
    pszgbh = PO.GetValueByParamName("pszgbh");
    psqxbh = PO.GetValueByParamName("psqxbh");
    pscol = PO.GetValueByParamName("pscol");
    pssuffixyear = PO.GetValueByEnvName("Suffixyear");
    pstmp = PO.GetValueByParamName("pibzw");
    pibzw = Integer.parseInt(pstmp);

    if (pszgbh.equals("9999") || pszgbh.equals("8888")) { //add by luo
      return TRUE;
    }
    // 转换PB的函数体
    if (pssuffixyear == null)
      pssuffixyear = "";
    // 如果在职工编号中存在","
    if (pszgbh.indexOf(",") > 0) {
      // 获取最右边一个字符
      vsfzlb = pszgbh.substring(pszgbh.length() - 1, pszgbh.length() - 1);
      // 获取","以前的字符
      pszgbh = pszgbh.substring(0, pszgbh.indexOf(",") - 1);

    }
    st = conn.createStatement();
    if (st == null)
      return FALSE;

    //取BSUSER中当前用户用的UNIT_ID，如果为空，则
    //取是否使用标志(0为不使用; 1为使用数据权限; 2为使用规则)
    vsSfsy = "";
    String strGranTable = DBTools.getDBAObject(PO, "BSGRAN") + pssuffixyear;
    vssql = "select F_SFSY,F_BHZD,F_QXDW from " + strGranTable +
        " where F_QXBH='" +
        psqxbh + "' and (F_QXDW = ' ' OR F_QXDW like '%" + UNIT_ID + "%')";
    try {
      RS = st.executeQuery(vssql);
      if (RS.next()) {
        vsSfsy = RS.getString("F_SFSY");
        if (pscol == null || "".equals(pscol)) {
          pscol = RS.getString("F_BHZD");
        }
        unit_id = RS.getString("F_QXDW");
      }
      RS.close();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    if (vsSfsy.length() == 0 || vsSfsy == null) {
      conn.BackStatement(st, null);
      return TRUE;
    }
    if (vsSfsy.equals("0")) {
      conn.BackStatement(st, null);
      return TRUE;
    }
    if (vsSfsy.equals("2")) { //检查规则
      conn.BackStatement(st, null);
      return gfchecksjqx2_gz(conn, pszgbh, psqxbh, pscol, pibzw, pssuffixyear,
                             PO);
    }
    //以下为检查数据权限
    vssql = "select F_QXB from " + strGranTable + " where F_QXBH='" +
        psqxbh + "' and F_QXDW = '" + unit_id + "'";
    vssavetbl = "BSUSSJ";
    try {
      RS = st.executeQuery(vssql);
      if (RS.next())
        vssavetbl = RS.getString("F_QXB").trim();
      RS.close();
    }
    catch (Exception e) {
      e.printStackTrace();
    }

    if (vssavetbl.length() == 0) {
      vssavetbl = "BSUSSJ";
    }

    //分别处理原先有无条件
    vstbl = vssavetbl + pssuffixyear;
    vstbl = DBTools.getDBAObject(PO, vstbl);
//    vstemp = "";
//    vssql = "SELECT name FROM dbo.sysobjects WHERE name='" + vstbl + "'";
//    try {
//      RS = st.executeQuery(vssql);
//      if (RS.next())
//        vstemp = RS.getString("name");
//      RS.close();
//    }
//    catch (Exception e) {
//      e.printStackTrace();
//    }

    //如果为原先的权限
//    if (vstemp.length() == 0) {
//      //判断是否科目权限
//      if (psqxbh.equals("ZWKMQX") && pibzw == 1) {
//        vssql = " exists( select 1 from LSUSKM where LSUSKM.F_KMBH = " + pscol +
//            " and LSUSKM.F_ZGBH='" + pszgbh + "') ";
//        conn.BackStatement(st, null);
//        return vssql;
//      }
//      else {
//        conn.BackStatement(st, null);
//        return "1=1";
//      }
//    }

    //判断是否分级
    vstemp = "";
    vssql = "select F_BMJG from " + strGranTable + "  where F_QXBH='" +
        psqxbh + "' and F_QXDW = '" + unit_id + "'";
    try {
      RS = st.executeQuery(vssql);
      if (RS.next()) {
        vstemp = RS.getString("F_BMJG");
        if (vstemp == null) {
          vstemp = "";
        }
      }
      RS.close();
    }
    catch (Exception e) {
      e.printStackTrace();
    }

    //如果是分级的。
    if (vstemp.length() != 0) {
      vssql = "  exists (select 1 from " + vstbl +
          "   BSUSSJ where BSUSSJ.F_QXBH='" + psqxbh + "' and " + pscol +
          " like (BSUSSJ.F_SJBH || '%')";

      //modified by lchong
//          vssql += " and BSUSSJ.F_ZGBH='" + pszgbh + "'"
//          String tmpTable = DBTools.getDBAObject(PO,"BSUSERROLE"+ pssuffixyear);
      vssql += " and ( BSUSSJ.F_ZGBH='" + pszgbh + "' ";
      try {
        String rolwWhere = getRoleSqlWhere(PO, st, pszgbh, "BSUSSJ");
        if (rolwWhere != null && !"".equals(rolwWhere.trim())) {
          vssql += rolwWhere;
        }
      }
      catch (Exception e) {
        e.printStackTrace();
      }
      vssql += " ) ";
//              or exists ( select 1 from "+tmpTable+" where f_rolecode = BSUSSJ.F_ZGBH and f_zgbh ='" + pszgbh + "')) "
      //modified end

      vssql += " and BSUSSJ.F_G" + String.valueOf(pibzw) + "='1'";
      //使用审核字段
      if (ParameterManager.getDefault().isUseLimitAudit())
        vssql += " and BSUSSJ.F_SH = '1' ";
      vssql += ")";
    }
    else {
      vssql = "  exists (select 1 from " + vstbl +
          "  BSUSSJ where BSUSSJ.F_QXBH='" + psqxbh + "' and " + pscol +
          " =BSUSSJ.F_SJBH";
      //modified by lchong
//      vssql += " and BSUSSJ.F_ZGBH='" + pszgbh + "'"
//      String tmpTable = DBTools.getDBAObject(PO,"BSUSERROLE"+ pssuffixyear);
      vssql += " and ( BSUSSJ.F_ZGBH='" + pszgbh + "' ";
      try {
        String rolwWhere = getRoleSqlWhere(PO, st, pszgbh, "BSUSSJ");
        if (rolwWhere != null && !"".equals(rolwWhere.trim())) {
          vssql += rolwWhere;
        }
      }
      catch (Exception e) {
        e.printStackTrace();
      }
      vssql += " ) ";

//          or exists (select 1 from " + tmpTable + " where f_rolecode = BSUSSJ.f_zgbh and f_zgbh ='" + pszgbh + "')) "
      //modified end
      vssql += " and BSUSSJ.F_G" + String.valueOf(pibzw) + "='1'";
      //使用审核字段
      if (ParameterManager.getDefault().isUseLimitAudit())
        vssql += " and BSUSSJ.F_SH = '1' ";
      vssql += ")";
    }
    conn.BackStatement(st, null);

    return vssql;
  }

  /**
   * 可以选择是否继承上级权限
   * bCrossDown = true ,那么如果有5030的权限，就有50300001的权限
   * @param conn JConnection
   * @param PO JParamObject
   * @param bCrossDown boolean
   * @return String
   */
  public static String CheckDataLimit3New(JConnection conn, JParamObject PO,
                                          boolean bCrossDown) {
    String pszgbh, psqxbh, pscol, pssuffixyear, pstmp, UNIT_ID, unit_id = " ";
    int pibzw = 0;
    String vstemp, vssql, vstbl, vsfzlb, vssavetbl, vsSfsy;
    int vicount;
    Statement st = null;
    ResultSet RS;
    String TRUE = "1=1", FALSE = "1=2";

    String OFFLINE = PO.GetValueByEnvName("OFFLINE", "0");
    if (OFFLINE.equals("1")) {
      return TRUE;
    }
    UNIT_ID = PO.GetValueByEnvName("UNIT_ID", " ");
    pszgbh = PO.GetValueByParamName("pszgbh");
    psqxbh = PO.GetValueByParamName("psqxbh");
    pscol = PO.GetValueByParamName("pscol");
    pssuffixyear = PO.GetValueByEnvName("Suffixyear");
    pstmp = PO.GetValueByParamName("pibzw");
    pibzw = Integer.parseInt(pstmp);

    if (pszgbh.equals("9999")) { //add by luo
      return TRUE;
    }
    // 转换PB的函数体
    if (pssuffixyear == null)
      pssuffixyear = "";
    // 如果在职工编号中存在","
    if (pszgbh.indexOf(",") > 0) {
      // 获取最右边一个字符
      vsfzlb = pszgbh.substring(pszgbh.length() - 1, pszgbh.length() - 1);
      // 获取","以前的字符
      pszgbh = pszgbh.substring(0, pszgbh.indexOf(",") - 1);

    }
    st = conn.createStatement();
    if (st == null)
      return FALSE;

    //取是否使用标志(0为不使用; 1为使用数据权限; 2为使用规则)
    vsSfsy = "";
    String strGranTable = DBTools.getDBAObject(PO, "BSGRAN") + pssuffixyear;
    vssql = "select F_SFSY,F_BHZD,F_QXDW from " + strGranTable +
        " where F_QXBH='" +
        psqxbh + "' and (F_QXDW = ' ' OR F_QXDW like '%" + UNIT_ID + "%')";
    try {
      RS = st.executeQuery(vssql);
      if (RS.next()) {
        vsSfsy = RS.getString("F_SFSY");
        if (pscol == null || "".equals(pscol)) {
          pscol = RS.getString("F_BHZD");
        }
        unit_id = RS.getString("F_QXDW");
      }
      RS.close();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    if (vsSfsy.length() == 0 || vsSfsy == null) {
      conn.BackStatement(st, null);
      return TRUE;
    }
    if (vsSfsy.equals("0")) {
      conn.BackStatement(st, null);
      return TRUE;
    }
    if (vsSfsy.equals("2")) { //检查规则
      conn.BackStatement(st, null);
      return gfchecksjqx2_gz(conn, pszgbh, psqxbh, pscol, pibzw, pssuffixyear,
                             PO);
    }
    //以下为检查数据权限
    vssql = "select F_QXB from " + strGranTable + " where F_QXBH='" +
        psqxbh + "' and F_QXDW = '" + unit_id + "'";
    vssavetbl = "BSUSSJ";
    try {
      RS = st.executeQuery(vssql);
      if (RS.next())
        vssavetbl = RS.getString("F_QXB").trim();
      RS.close();
    }
    catch (Exception e) {
      e.printStackTrace();
    }

    if (vssavetbl.length() == 0) {
      vssavetbl = "BSUSSJ";
    }

    //分别处理原先有无条件
    vstbl = vssavetbl + pssuffixyear;
    vstbl = DBTools.getDBAObject(PO, vstbl);
    //判断是否分级
    vstemp = "";
    vssql = "select F_BMJG from " + strGranTable + "  where F_QXBH='" +
        psqxbh + "'";
    try {
      RS = st.executeQuery(vssql);
      if (RS.next()) {
        vstemp = RS.getString("F_BMJG");
        if (vstemp == null) {
          vstemp = "";
        }
      }
      RS.close();
    }
    catch (Exception e) {
      e.printStackTrace();
    }

    //如果是分级的。
    if (vstemp.length() != 0 && bCrossDown) { //如果是分级的，并且要求继承上级权限
      vssql = "  exists (select 1 from " + vstbl +
          " BSUSSJ where BSUSSJ.F_QXBH='" + psqxbh + "' and " + pscol +
          " like BSUSSJ.F_SJBH ||'%'";
      vssql += " and ( BSUSSJ.F_ZGBH='" + pszgbh + "' ";
      try {
        String rolwWhere = getRoleSqlWhere(PO, st, pszgbh, "BSUSSJ");
        if (rolwWhere != null && !"".equals(rolwWhere.trim())) {
          vssql += rolwWhere;
        }
      }
      catch (Exception e) {
        e.printStackTrace();
      }
      vssql += " ) ";
      vssql += " and BSUSSJ.F_G" + String.valueOf(pibzw) + "='1' ";
      //使用审核字段
      if (ParameterManager.getDefault().isUseLimitAudit())
        vssql += " and BSUSSJ.F_SH = '1' ";
      vssql += ")";
    }
    else {
      vssql = "  exists (select 1 from " + vstbl +
          "  BSUSSJ where BSUSSJ.F_QXBH='" + psqxbh + "' and " + pscol +
          " =BSUSSJ.F_SJBH";
      vssql += " and ( BSUSSJ.F_ZGBH='" + pszgbh + "' ";
      try {
        String rolwWhere = getRoleSqlWhere(PO, st, pszgbh, "BSUSSJ");
        if (rolwWhere != null && !"".equals(rolwWhere.trim())) {
          vssql += rolwWhere;
        }
      }
      catch (Exception e) {
        e.printStackTrace();
      }
      vssql += " ) ";
      vssql += " and BSUSSJ.F_G" + String.valueOf(pibzw) + "='1' ";
      //使用审核字段
      if (ParameterManager.getDefault().isUseLimitAudit())
        vssql += " and BSUSSJ.F_SH = '1' ";
      vssql += ")";
    }
    conn.BackStatement(st, null);

    return vssql;
  }

  public static String CheckDataLimit3New(JConnection conn, JParamObject PO) {
    String pszgbh, psqxbh, pscol, pssuffixyear, pstmp, UNIT_ID, unit_id = " ";
    int pibzw = 0;
    String vstemp, vssql, vstbl, vsfzlb, vssavetbl, vsSfsy;
    int vicount;
    Statement st = null;
    ResultSet RS;
    String TRUE = "1=1", FALSE = "1=2";

    String OFFLINE = PO.GetValueByEnvName("OFFLINE", "0");
    if (OFFLINE.equals("1")) {
      return TRUE;
    }
    UNIT_ID = PO.GetValueByEnvName("UNIT_ID", " ");
    pszgbh = PO.GetValueByParamName("pszgbh");
    psqxbh = PO.GetValueByParamName("psqxbh");
    pscol = PO.GetValueByParamName("pscol");
    pssuffixyear = PO.GetValueByEnvName("Suffixyear");
    pstmp = PO.GetValueByParamName("pibzw");
    pibzw = Integer.parseInt(pstmp);

    if (pszgbh.equals("9999")) { //add by luo
      return TRUE;
    }
    // 转换PB的函数体
    if (pssuffixyear == null)
      pssuffixyear = "";
    // 如果在职工编号中存在","
    if (pszgbh.indexOf(",") > 0) {
      // 获取最右边一个字符
      vsfzlb = pszgbh.substring(pszgbh.length() - 1, pszgbh.length() - 1);
      // 获取","以前的字符
      pszgbh = pszgbh.substring(0, pszgbh.indexOf(",") - 1);

    }
    st = conn.createStatement();
    if (st == null)
      return FALSE;

    //取是否使用标志(0为不使用; 1为使用数据权限; 2为使用规则)
    vsSfsy = "";
    String strGranTable = DBTools.getDBAObject(PO, "BSGRAN") + pssuffixyear;
    vssql = "select F_SFSY,F_BHZD,F_QXDW from " + strGranTable +
        " where F_QXBH='" +
        psqxbh + "' and (F_QXDW = ' ' OR F_QXDW like '%" + UNIT_ID + "%')";
    try {
      RS = st.executeQuery(vssql);
      if (RS.next()) {
        vsSfsy = RS.getString("F_SFSY");
        if (pscol == null || "".equals(pscol)) {
          pscol = RS.getString("F_BHZD");
        }
        unit_id = RS.getString("F_QXDW");
      }
      RS.close();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    if (vsSfsy.length() == 0 || vsSfsy == null) {
      conn.BackStatement(st, null);
      return TRUE;
    }
    if (vsSfsy.equals("0")) {
      conn.BackStatement(st, null);
      return TRUE;
    }
    if (vsSfsy.equals("2")) { //检查规则
      conn.BackStatement(st, null);
      return gfchecksjqx2_gz(conn, pszgbh, psqxbh, pscol, pibzw, pssuffixyear,
                             PO);
    }
    //以下为检查数据权限
    vssql = "select F_QXB from " + strGranTable + " where F_QXBH='" +
        psqxbh + "' and F_QXDW = '" + unit_id + "'";
    vssavetbl = "BSUSSJ";
    try {
      RS = st.executeQuery(vssql);
      if (RS.next())
        vssavetbl = RS.getString("F_QXB").trim();
      RS.close();
    }
    catch (Exception e) {
      e.printStackTrace();
    }

    if (vssavetbl.length() == 0) {
      vssavetbl = "BSUSSJ";
    }

    //分别处理原先有无条件
    vstbl = vssavetbl + pssuffixyear;
    vstbl = DBTools.getDBAObject(PO, vstbl);
    //判断是否分级
    vstemp = "";
    vssql = "select F_BMJG from " + strGranTable + "  where F_QXBH='" +
        psqxbh + "' and F_QXDW ='" + unit_id + "'";
    try {
      RS = st.executeQuery(vssql);
      if (RS.next()) {
        vstemp = RS.getString("F_BMJG");
        if (vstemp == null) {
          vstemp = "";
        }
      }
      RS.close();
    }
    catch (Exception e) {
      e.printStackTrace();
    }

    //如果是分级的。
    if (vstemp.length() != 0) {
      vssql = "  exists (select 1 from " + vstbl +
//         "   BSUSSJ where BSUSSJ.F_QXBH='" + psqxbh + "' and BSUSSJ.F_SJBH " + " like ("+pscol+"|| '%')";
          " BSUSSJ where BSUSSJ.F_QXBH='" + psqxbh + "' and " + pscol +
          " like BSUSSJ.F_SJBH ||'%'";
      vssql += " and ( BSUSSJ.F_ZGBH='" + pszgbh + "' ";
      try {
        String rolwWhere = getRoleSqlWhere(PO, st, pszgbh, "BSUSSJ");
        if (rolwWhere != null && !"".equals(rolwWhere.trim())) {
          vssql += rolwWhere;
        }
      }
      catch (Exception e) {
        e.printStackTrace();
      }
      vssql += " ) ";
//              or exists ( select 1 from "+tmpTable+" where f_rolecode = BSUSSJ.F_ZGBH and f_zgbh ='" + pszgbh + "')) "
      //modified end
      vssql += " and BSUSSJ.F_G" + String.valueOf(pibzw) + "='1' ";
      //使用审核字段
      if (ParameterManager.getDefault().isUseLimitAudit())
        vssql += " and BSUSSJ.F_SH = '1' ";
      vssql += ")";
    }
    else {
      vssql = "  exists (select 1 from " + vstbl +
          "  BSUSSJ where BSUSSJ.F_QXBH='" + psqxbh + "' and " + pscol +
          " =BSUSSJ.F_SJBH";
      //modified by lchong
//      vssql += " and BSUSSJ.F_ZGBH='" + pszgbh + "'"
//      String tmpTable = DBTools.getDBAObject(PO,"BSUSERROLE"+ pssuffixyear);
      vssql += " and ( BSUSSJ.F_ZGBH='" + pszgbh + "' ";
      try {
        String rolwWhere = getRoleSqlWhere(PO, st, pszgbh, "BSUSSJ");
        if (rolwWhere != null && !"".equals(rolwWhere.trim())) {
          vssql += rolwWhere;
        }
      }
      catch (Exception e) {
        e.printStackTrace();
      }
      vssql += " ) ";

//          or exists (select 1 from " + tmpTable + " where f_rolecode = BSUSSJ.f_zgbh and f_zgbh ='" + pszgbh + "')) "
      //modified end
      vssql += " and BSUSSJ.F_G" + String.valueOf(pibzw) + "='1' ";
      //使用审核字段
      if (ParameterManager.getDefault().isUseLimitAudit())
        vssql += " and BSUSSJ.F_SH = '1' ";
      vssql += ")";
    }
    conn.BackStatement(st, null);

    return vssql;
  }

  //----------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改: 返回值: 如果不为空,说明此用户有此项权限,如果为空,说明此用户没有此项权限
  //----------------------------------------------------------------------------------------------

  public static String CheckDataLimit3(JConnection conn, JParamObject PO) {
    int vicount, pibzw = 0;
    String pszgbh, psqxbh, pscol, pssuffixyear, pstmp, UNIT_ID, unit_id = " ";
    String vstemp, vssql, vstbl, vsfzlb, vssavetbl, vsSfsy;
    Statement st = null;
    ResultSet RS = null;
    String TRUE = "(1=1)", FALSE = "(1=2)";

    String OFFLINE = PO.GetValueByEnvName("OFFLINE", "0");
    if (OFFLINE.equals("1")) {
      return TRUE;
    }

    // 获取PB函数中相应的函数参数
    UNIT_ID = PO.GetValueByEnvName("UNIT_ID", " ");
    pszgbh = PO.GetValueByParamName("pszgbh");
    psqxbh = PO.GetValueByParamName("psqxbh");
    pscol = PO.GetValueByParamName("pscol");
    pssuffixyear = PO.GetValueByEnvName("Suffixyear");
    pstmp = PO.GetValueByParamName("pibzw");
    pibzw = Integer.parseInt(pstmp);

    // 转换PB的函数体
    if (pssuffixyear == null)
      pssuffixyear = "";
    // 如果在职工编号中存在","
    if (pszgbh.indexOf(",") > 0) {
      // 获取最右边一个字符
      vsfzlb = pszgbh.substring(pszgbh.length() - 1, pszgbh.length());
      // 获取","以前的字符
      pszgbh = pszgbh.substring(0, pszgbh.indexOf(","));

    }

    try {
      st = conn.createStatement();

      //权限表。
      String strGranTable = DBTools.getDBAObject(PO, "BSGRAN") + pssuffixyear;
      //取是否使用标志(0为不使用; 1为使用数据权限; 2为使用规则)
      vsSfsy = "";
      vssql = "select F_SFSY,F_BHZD,F_QXDW from " + strGranTable +
          " where F_QXBH='" + psqxbh + "' and (F_QXDW = ' ' OR F_QXDW like '%" +
          UNIT_ID + "%')";
      RS = st.executeQuery(vssql);
      if (RS.next()) {
        vsSfsy = RS.getString("F_SFSY");
        if (pscol == null || "".equals(pscol)) {
          pscol = RS.getString("F_BHZD");
        }
        unit_id = RS.getString("F_QXDW");
      }
      RS.close();
      if (vsSfsy == null || vsSfsy.length() == 0) {
        return TRUE;
      }
      if (vsSfsy.equals("0"))
        return TRUE;
      if (vsSfsy.equals("2")) { //检查规则
        return gfchecksjqx2_gz(conn, pszgbh, psqxbh, pscol, pibzw, pssuffixyear,
                               PO);
      }

      //以下为生成数据权限
      vssql = "select F_QXB from " + strGranTable + " where F_QXBH='" + psqxbh +
          "' AND F_QXDW='" + unit_id + "'";
      vssavetbl = "BSUSSJ";
      RS = st.executeQuery(vssql);
      if (RS.next())
        vssavetbl = RS.getString("F_QXB");
      RS.close();
      if (vssavetbl == null || vssavetbl.trim().length() == 0) {
        vssavetbl = "BSUSSJ";
      }
      //分别处理原先有无条件
      vstbl = vssavetbl + pssuffixyear;
      //权限数据表。
      String strGranSJTable = DBTools.getDBAObject(PO, vstbl);

      vssql = "select count(*) from " + strGranTable + " where F_QXBH='" +
          psqxbh +
          "' and F_SFSY='1' and F_QXDW= '" + unit_id + "'";
      vicount = 0;
      RS = st.executeQuery(vssql);
      if (RS.next())
        vicount = RS.getInt(1);
      RS.close();
      if (vicount == 0)
        return FALSE;
      //如果使用该权限
      if (vicount == 1) {
        //判断是否分级
        vstemp = "";
        vssql = "select F_BMJG from " + strGranTable + "  where F_QXBH='" +
            psqxbh + "' and F_QXDW = '" + unit_id + "'";
        RS = st.executeQuery(vssql);
        if (RS.next())
          vstemp = RS.getString(1);
        RS.close();
        if (vstemp == null)
          vstemp = "";
//          return FALSE;
        //--------------------------------------------------------
        // 加入数据权限中，有下级权限，但没上级权限时，能显示上级数据的功能
        // modi by hufeng
        // 2004.05.27
        //--------------------------------------------------------
        if (!vstemp.trim().equals("")) {
          String vsFirstJs, vsStru, vsJsSql, vsJsTemp;
          int viJs, viLen, viPos;
          String vsJsZd = "";
          String vstable = "";
          vssql = "select F_TABN FROM " + strGranTable + " where F_QXBH = '" +
              psqxbh + "'";
          RS = st.executeQuery(vssql);
          if (RS.next()) {
            vstable = RS.getString(1).trim();
          }
          RS.close();
          vsStru = vstemp;
          if (vstemp != null && !vstemp.equals("")) {
            if (vstemp.indexOf("@") >= 0) {
              vstemp = vstemp.substring(1);
              vssql = "SELECT F_VAL FROM LSCONF" + pssuffixyear +
                  " WHERE F_VKEY='" + vstemp + "'";
              RS = st.executeQuery(vssql);
              if (RS.next()) {
                vsStru = RS.getString(1).trim();
              }
              vssql = "select F_JSZD from " + strGranTable + "  where F_QXBH='" +
                  psqxbh + "' and F_QXDW = '" + unit_id + "'";
              RS = st.executeQuery(vssql);
              vsJsZd = "";
              if (RS.next()) {
                vsJsZd = RS.getString(1).trim();
              }
              RS.close();
              //级数字段要加上表名前缀
              if (pscol.indexOf(".") >= 0) {
                int begin = pscol.indexOf("(");
                if (begin == -1)
                  begin = 0;
                vsJsZd = pscol.substring(begin, pscol.indexOf(".") + 1) +
                    vsJsZd;
              }
            }
            else {
              vssql = "select F_JSZD from " + strGranTable + "  where F_QXBH='" +
                  psqxbh + "' and F_QXDW = '" + unit_id + "'";
              RS = st.executeQuery(vssql);
              if (RS.next()) {
                vsJsZd = RS.getString(1).trim();
              }
              RS.close();
              //级数字段要加上表名前缀
              if (pscol.indexOf(".") >= 0) {
                int begin = pscol.indexOf("(");
                if (begin == -1)
                  begin = 0;
                vsJsZd = pscol.substring(0, pscol.indexOf(".") + 1) + vsJsZd;
              }
            }
          }
          viPos = 0;
          vsJsSql = "";
          viLen = vsStru.length();
          String vsjslen;
          for (viJs = 0; viJs < viLen; viJs++) {
            vstemp = vsStru.substring(viJs, viJs + 1);
            if (vstemp.equals("A")) {
              vsjslen = "10";
            }
            else if (vstemp.equals("B")) {
              vsjslen = "11";
            }
            else if (vstemp.equals("C")) {
              vsjslen = "12";
            }
            else if (vstemp.equals("D")) {
              vsjslen = "13";
            }
            else if (vstemp.equals("E")) {
              vsjslen = "14";
            }
            else if (vstemp.equals("F")) {
              vsjslen = "15";
            }
            else {
              vsjslen = vstemp;
            }
            viPos += Integer.parseInt(vsjslen);
            vsJsTemp = " (" + pscol + " like substr(LSUSSJQX.F_SJBH,1," + viPos +
                ") || '%' and " + vsJsZd + "='" + (viJs + 1) + "' ) or ";
            vsJsSql = vsJsSql + vsJsTemp;
          }
          vsJsSql = " and (" + vsJsSql + " 1=2 ) ";
//          String tmpTable = DBTools.getDBAObject(PO,"BSUSERROLE"+ pssuffixyear);
          vssql = "  exists (select 1 from " + strGranSJTable +
              " LSUSSJQX where LSUSSJQX.F_QXBH='" + psqxbh + "' " + vsJsSql +
              "  " +
              //modified by lchong
              " and ( LSUSSJQX.F_ZGBH='" + pszgbh + "' ";
//            //  or exists ( select 1 from "+tmpTable+" where f_rolecode = LSUSSJQX.F_ZGBH and f_zgbh = '"+pszgbh+"')) ";
          String rolwWhere = getRoleSqlWhere(PO, st, pszgbh, "LSUSSJQX");
          if (rolwWhere != null && !"".equals(rolwWhere.trim())) {
            vssql += rolwWhere;
          }
          vssql += " ) ";
          //modified end
          vssql += " and LSUSSJQX.F_G" + pibzw + "='1' ";
          //使用审核字段
          if (ParameterManager.getDefault().isUseLimitAudit())
            vssql += " and LSUSSJQX.F_SH = '1' ";
          vssql += ")";
        } //end 分级显示
        else {
//          String tmpTable = DBTools.getDBAObject(PO,"BSUSERROLE"+ pssuffixyear);
          vssql = "  exists (select 1 from " + strGranSJTable +
              "   LSUSSJQX where LSUSSJQX.F_QXBH='" + psqxbh + "' and " + pscol +
              "=LSUSSJQX.F_SJBH" +
              //modified by lchong
              " and ( LSUSSJQX.F_ZGBH='" + pszgbh + "'";
          // or exists ( select 1 from " + tmpTable + " where f_rolecode = LSUSSJQX.F_ZGBH and f_zgbh = '"+pszgbh+"')) "
          String rolwWhere = getRoleSqlWhere(PO, st, pszgbh, "LSUSSJQX");
          if (rolwWhere != null && !"".equals(rolwWhere.trim())) {
            vssql += rolwWhere;
          }
          vssql += " ) ";
          //modified end
          vssql += " and LSUSSJQX.F_G" + pibzw + "='1'";
          //使用审核字段
          if (ParameterManager.getDefault().isUseLimitAudit())
            vssql += " and LSUSSJQX.F_SH = '1' ";
          vssql += ")";

        }
      }
      //如果不使用该类权限
      else {
        vssql = " 1=1 ";
      }
      st.close();
      return vssql;
    }
    catch (Exception e) {
      e.printStackTrace();
      return FALSE;
    }
  }

  public static String getRoleSqlWhere(JParamObject PO, Statement st,
                                       String f_zgbh, String tabName) throws
      Exception {
    String roleWhere = "";
    List roleList = getUsersOfRole(PO, st, f_zgbh);
    if (roleList != null && roleList.size() > 0) {
      for (int i = 0; i < roleList.size(); i++) {
        String roleBh = (String) roleList.get(i);
        if (tabName == null || "".equals(tabName.trim())) {
          roleWhere += " OR F_ZGBH= '" + roleBh + "'";
        }
        else {
          roleWhere += " OR " + tabName + ".F_ZGBH= '" + roleBh + "'";
        }
      }
    }
    return roleWhere;
  }

  protected static List getUsersOfRole(JParamObject PO, Statement st,
                                       String f_zgbh) throws Exception {
    List roleList = new ArrayList();
    String bsUserRole = DBTools.getDBAObject(PO, "BSUSERROLE");
    String sql = "SELECT F_ROLECODE FROM " + bsUserRole + "  WHERE F_ZGBH = '" +
        f_zgbh + "' ";
    if (ParameterManager.getDefault().isUseLimitAudit())
      sql += " and F_SH = '1'";
    ResultSet rs = st.executeQuery(sql);
    while (rs.next()) {
      roleList.add(rs.getString(1));
    }
    rs.close();
    return roleList;
  }

  //----------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改: 返回值: 如果不为空,说明此用户有此项权限,如果为空,说明此用户没有此项权限
  //----------------------------------------------------------------------------------------------
  /*
    public static String CheckDataLimit3(JConnection conn, JParamObject PO) {
      String pszgbh, psqxbh, pscol, pssuffixyear, pstmp;
      int pibzw = 0;
      String vstemp, vssql, vstbl, vsfzlb, vssavetbl, vsSfsy;
      int vicount;
      Statement st = null;
      ResultSet RS;
      String TRUE = "1=1", FALSE = "1=2";

      String OFFLINE = PO.GetValueByEnvName("OFFLINE","0");
      if ( OFFLINE.equals("1") )
      {
          return TRUE;
      }

      // 获取PB函数中相应的函数参数
      pszgbh = PO.GetValueByParamName("pszgbh");
      psqxbh = PO.GetValueByParamName("psqxbh");
      pscol = PO.GetValueByParamName("pscol");
      pssuffixyear = PO.GetValueByEnvName("Suffixyear");
      pstmp = PO.GetValueByParamName("pibzw");
      pibzw = Integer.parseInt(pstmp);

      // 转换PB的函数体
      if (pssuffixyear == null)
        pssuffixyear = "";
        // 如果在职工编号中存在","
      if (pszgbh.indexOf(",") > 0) {
        // 获取最右边一个字符
        vsfzlb = pszgbh.substring(pszgbh.length() - 1, pszgbh.length() - 1);
        // 获取","以前的字符
        pszgbh = pszgbh.substring(0, pszgbh.indexOf(",") - 1);
        //com.pansoft.pub.util.StringFunction. get_token(pszgbh,',')
        if (vsfzlb.equals("1")) {
          PO.SetValueByParamName("F_VKEY", "ZW_ZXQXKZ");
          vstemp = DBOBusinessEnvObject.GetLsConfig(conn, PO);
          // 为空,取数出错,返回空
          if (vstemp == null) {
            return FALSE;
          }
          // 如果不等1,返回成功
          if (!vstemp.equals("1")) {
            return TRUE;
          }
        }
        if (vsfzlb.equals("2")) {
          PO.SetValueByParamName("F_VKEY", "ZW_DWQXKZ");
          vstemp = DBOBusinessEnvObject.GetLsConfig(conn, PO);
          // 为空,取数出错,返回空
          if (vstemp == null) {
            return FALSE;
          }
          // 如果等于0,返回成功
          if (vstemp.equals("0")) {
            return TRUE;
          }
        }
        if (vsfzlb.equals("3")) {
          PO.SetValueByParamName("F_VKEY", "ZW_GRQXKZ");
          vstemp = DBOBusinessEnvObject.GetLsConfig(conn, PO);
          // 为空,取数出错,返回空
          if (vstemp == null) {
            return FALSE;
          }
          // 如果等于0,返回成功
          if (!vstemp.equals("1")) {
            return TRUE;
          }
        }
      }
      try {
        st = conn.createStatement();
      }
      catch (Exception e) {
        return FALSE;
      }
      try {
        //判断是否使用全部科目
   if (pssuffixyear.length() == 0 && psqxbh.equals("ZWKMQX") && pibzw == 1) {
          vicount = 0;
          vssql = "select F_SYKM from LSPASS where F_ZGBH='" + pszgbh + "'";
          RS = st.executeQuery(vssql);
          if (RS.next())
            vicount = RS.getInt("F_SYKM");
          RS.close();
          if (vicount == 1) {
//                st.close();
            return TRUE;
          }
        }
      }
      catch (Exception e) {
        e.printStackTrace();
      }
      ;
      //取是否使用标志(0为不使用; 1为使用数据权限; 2为使用规则)
      vsSfsy = "";
      vssql = "select F_SFSY from LSGRAN" + pssuffixyear + " where F_QXBH='" +
          psqxbh + "'";
      try {
        RS = st.executeQuery(vssql);
        if (RS.next())
          vsSfsy = RS.getString("F_SFSY");
        RS.close();
        //st.close();
      }
      catch (Exception e) {
        e.printStackTrace();
      }
      if (vsSfsy.length() == 0 || vsSfsy == null) {
        return FALSE;
      }
      if (vsSfsy.equals("0"))
        return TRUE;
      if (vsSfsy.equals("2")) { //检查规则
   return gfchecksjqx2_gz(conn, pszgbh, psqxbh, pscol, pibzw, pssuffixyear);
      }
      //以下为检查数据权限
      vssql = "select F_QXB from LSGRAN" + pssuffixyear + " where F_QXBH='" +
          psqxbh + "'";
      vssavetbl = "LSUSSJ";
      try {
        RS = st.executeQuery(vssql);
        if (RS.next())
          vssavetbl = RS.getString("F_QXB");
        RS.close();
        //st.close();
      }
      catch (Exception e) {
        e.printStackTrace();
      }
      if ( vssavetbl == null || vssavetbl.trim().length() == 0 )
      {
          vssavetbl = "LSUSSJ";
      }
      //分别处理原先有无条件
      vstbl = vssavetbl + pssuffixyear;
      vstemp = "";
      vssql = "SELECT name FROM dbo.sysobjects WHERE name='" + vstbl + "'";
      try {
        RS = st.executeQuery(vssql);
        if (RS.next())
          vstemp = RS.getString("name");
        RS.close();
//          st.close();
      }
      catch (Exception e) {
        e.printStackTrace();
      }

      //如果为原先的权限
      if (vstemp.length() == 0) {
        //判断是否科目权限
        if (psqxbh.equals("ZWKMQX") && pibzw == 1) {
   vssql = " exists( select 1 from LSUSKM where LSUSKM.F_KMBH = " + pscol +
              " and LSUSKM.F_ZGBH='" + pszgbh + "') ";
          return vssql;
        }
        else
          return "1=1";
      }
   vssql = "select count(*) from LSGRAN" + pssuffixyear + " where F_QXBH='" +
          psqxbh + "' and F_SFSY='1'";
      vicount = 0;
      try {
        RS = st.executeQuery(vssql);
        if (RS.next())
          vicount = RS.getInt(1);
        RS.close();
//          st.close();
      }
      catch (Exception e) {
        e.printStackTrace();
      }
      if (vicount == 0)
        return FALSE;
      //如果使用该权限
      if (vicount == 1) {
        //判断是否分级
        vstemp = "";
   vssql = "select F_BMJG from LSGRAN" + pssuffixyear + "  where F_QXBH='" +
            psqxbh + "'";
        try {
          RS = st.executeQuery(vssql);
          if (RS.next())
            vstemp = RS.getString(1);
          RS.close();
//              st.close();
        }
        catch (Exception e) {
          vstemp = null;
          e.printStackTrace();
        }
      if ( vstemp == null || vstemp.trim().length() == 0 )
      {
          vstemp = "";
      }
        if (vstemp == null)
          return FALSE;

        if (vstemp.length() > 0) {
          vssql = "  exists (select 1 from " + vstbl +
              "   LSUSSJ where LSUSSJ.F_QXBH='" + psqxbh + "' and (" + pscol +
              " like (LSUSSJ.F_SJBH+'%')";
          vssql += " or LSUSSJ.F_SJBH like (" + pscol + "+'%'))";
          vssql += " and LSUSSJ.F_ZGBH='" + pszgbh + "' and LSUSSJ.F_G" +
              String.valueOf(pibzw) + "='1') ";
        }
        else {
          vssql = "  exists (select 1 from " + vstbl +
              "   LSUSSJ where LSUSSJ.F_QXBH='" + psqxbh + "' and " + pscol +
              "=LSUSSJ.F_SJBH";
          vssql += " and LSUSSJ.F_ZGBH='" + pszgbh + "' and LSUSSJ.F_G" +
              String.valueOf(pibzw) + "='1') ";
        }
        //如果不使用该类权限
      }
      else {
        vssql = " 1=1 ";
      }
      return vssql;
    }
   */
  //----------------------------------------------------------------------------------------------
  //描述:
  //设计: Xyz(2001.12.29)
  //实现: Xyz
  //修改: 返回值: PO
  //----------------------------------------------------------------------------------------------
  public static JParamObject makeCDL1_PO(String psZgbh, String psQxbh,
                                         String psSjbh, String psSuffixYear,
                                         int piBzw) {
    JParamObject PO = new JParamObject();
    PO.SetValueByParamName("pszgbh", psZgbh);
    PO.SetValueByParamName("psqxbh", psQxbh);
    PO.SetValueByParamName("pssjbh", psSjbh);
    PO.SetValueByParamName("Suffixyear", psSuffixYear);
    PO.SetValueByParamName("pibzw", String.valueOf(piBzw));
    return PO;
  }

  //----------------------------------------------------------------------------------------------
  //描述:
  //设计: Xyz(2001.12.29)
  //实现: Xyz
  //修改: 返回值: PO
  //----------------------------------------------------------------------------------------------
  public static JParamObject makeCDL2_PO(String psZgbh, String psQxbh,
                                         String psCol, String psSuffixYear,
                                         int piBzw) {
    JParamObject PO = new JParamObject();
    PO.SetValueByParamName("pszgbh", psZgbh);
    PO.SetValueByParamName("psqxbh", psQxbh);
    PO.SetValueByParamName("pscol", psCol);
    PO.SetValueByParamName("Suffixyear", psSuffixYear);
    PO.SetValueByParamName("pibzw", String.valueOf(piBzw));
    return PO;
  }

  //----------------------------------------------------------------------------------------------
  //描述:动态生成查询SQL的责任中心权限检查WEHERE条件
  //设计: Xyz(2001.12.29)
  //实现: Xyz
  //修改: 返回值:
  //修改：Lishoulin,0801,for:非明细责任中心帮助不出科目
  //----------------------------------------------------------------------------------------------
  public static String SCheckCenterLimit(String psTable, String centerId,
                                         String psSyzt, String tableNameSuffix) {
    String tmp_Where = " 1 = 1 ";
    if (centerId == null || centerId.equals(""))
      return tmp_Where;
    //
    if (psTable.equals("LSKMXX") || psTable.equals("LSKMZD")) {
      tmp_Where += " and exists (select 1 from LSKMZX" + tableNameSuffix +
          " LSKMZX where " + psTable +
          ".F_KMBH like LSKMZX.F_LIKE and LSKMZX.F_CODE like '" + centerId +
          "%') ";
      if (psSyzt.equals("1"))
        tmp_Where += " and " + psTable + ".F_SYZT='1' ";
    }
    else if (psTable.equals("LSWLDW")) {
      tmp_Where += " and exists (select 1 from LSACDW" + tableNameSuffix +
          " LSACDW where LSWLDW.F_DWBH=LSACDW.F_DWBH and LSACDW.F_CODE like '" +
          centerId + "%' ";
      if (psSyzt.equals("1"))
        tmp_Where += " and LSACDW.F_SYZT='1' ";
      tmp_Where += " ) ";
    }
    else if (psTable.equals("LSHSFL")) {
      tmp_Where += " and exists (select 1 from LSHSZX" + tableNameSuffix +
          " LSHSZX where LSHSFL.F_FLBH=LSHSZX.F_FLBH and LSHSZX.F_CODE like '" +
          centerId + "%' ) ";
    }
    else if (psTable.equals("LSHSZD")) {
      tmp_Where += " and exists (select 1 from LSHSZX" + tableNameSuffix +
          " LSHSZX where LSHSZD.F_HSBH=LSHSZX.F_HSBH and LSHSZX.F_CODE like '" +
          centerId + "%' ) ";
    }
    else if (psTable.equals("LSZGZD")) {
      tmp_Where += " and LSZGZD.F_DWBH  in (select ACBMZD.F_BMBH from ACBMZD" +
          tableNameSuffix + " ACBMZD where (ACBMZD.F_CODE like '" + centerId +
          "%' or '" + centerId + "' like rtrim(ACBMZD.F_CODE)+'%') ) ";
      if (psSyzt.equals("1"))
        tmp_Where += " and LSZGZD.F_SYZT='1' ";
    }
    else if (psTable.equals("ACBMZD")) {
      tmp_Where += " and (ACBMZD.F_CODE like '" + centerId + "%' or '" +
          centerId + "' like rtrim(ACBMZD.F_CODE)+'%') ";
      if (psSyzt.equals("1"))
        tmp_Where += " and ACBMZD.F_SYZT='1' ";
    }
    else if (psTable.equals("ACCBZX")) { //成本 不再对应责任中心
      //yj remarked
      //tmp_Where += " and ACCBZX.F_CODE like '"+centerId+"%' ";
      if (psSyzt.equals("1"))
        tmp_Where += " and ACCBZX.F_SYZT='1' ";
    }
    else if (psTable.equals("LSYSZD")) {
      if (psSyzt.equals("1"))
        tmp_Where += " and LSYSZD.F_SYZT='1' ";
    }
    else if (psTable.equals("LSCPZD")) {
      if (psSyzt.equals("1"))
        tmp_Where += " and LSCPZD.F_SYZT='1' ";
    }
    else if (psTable.equals("ACXJLL")) {
      if (psSyzt.equals("1"))
        tmp_Where += " and ACXJLL.F_SYZT='1' ";
    }
    else if (psTable.equals("YJGCZD")) {
      tmp_Where += " and YJGCZD.F_CODE like '" + centerId + "%' ";
      //if(psSyzt.equals("1"))
      //tmp_Where += " and YJGCZD.F_SYZT='1' ";
    }
    //
    return tmp_Where;
  }

  //----------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改: 返回值: 如果不为空,说明此用户有此项权限,如果为空,说明此用户没有此项权限
  //----------------------------------------------------------------------------------------------
  public static String SCheckDataLimit1(JConnection conn, JParamObject PO) {
    String pszgbh, pstmp;
    boolean res;
    String Res = "0";
    // 获取PB函数中相应的函数参数
    // 设置职工编号(在此以当前登录的职工编号做为参数)

    pstmp = PO.GetValueByEnvName("UserName");

    String psqxbh = PO.GetValueByParamName("psqxbh");
    String pssjbh = PO.GetValueByParamName("pssjbh");
    String pibzw = PO.GetValueByParamName("pibzw");

    PO.SetValueByParamName("pszgbh", pstmp);
    // 设置权限编号，由本函数参数获得
    PO.SetValueByParamName("psqxbh", psqxbh);
    // 设置数据编号，由本函数参数获取
    PO.SetValueByParamName("pssjbh", pssjbh);
    // 设置数据的标志位
    PO.SetValueByParamName("pibzw", pibzw);
    res = CheckDataLimit1(conn, PO);
    if (res)
      Res = "1";
    return Res;
  }

  //----------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改: 返回值:
  //----------------------------------------------------------------------------------------------
  public static String SCheckDataLimit2(JConnection conn, JParamObject PO) {
    String pszgbh, pstmp;
    // 获取PB函数中相应的函数参数
    // 设置职工编号(在此以当前登录的职工编号做为参数)
    pstmp = PO.GetValueByEnvName("UserName");
    String psqxbh = PO.GetValueByParamName("psqxbh");
    String pscol = PO.GetValueByParamName("pscol");
    String pibzw = PO.GetValueByParamName("pibzw");

    PO.SetValueByParamName("pszgbh", pstmp);
    // 设置权限编号，由本函数参数获得
    PO.SetValueByParamName("psqxbh", psqxbh);
    // 设置数据编号，由本函数参数获取
    PO.SetValueByParamName("pscol", pscol);
    // 设置数据的标志位
    PO.SetValueByParamName("pibzw", pibzw);
    return CheckDataLimit2(conn, PO);
  }

  //----------------------------------------------------------------------------------------------
  //描述:
  //设计: Xyz(2001.12.29)
  //实现: Xyz
  //修改: 返回值:
  //----------------------------------------------------------------------------------------------
  public static String SCheckDataLimit2(JConnection conn, JParamObject PO,
                                        JParamObject InputData) {
    String tmp_Where = " ( ";
    JParamObject IN = InputData;

    String psqxbh = IN.GetValueByParamName("psqxbh");
    String pibzw = IN.GetValueByParamName("pibzw");
    String pscol = IN.GetValueByParamName("pscol");
    String tableNameSuffix = IN.GetValueByParamName("tableNameSuffix");
    String psTable = IN.GetValueByParamName("psTable");
    String centerId = IN.GetValueByParamName("centerId");
    String psSyzt = IN.GetValueByParamName("psSyzt");

    if (psqxbh == null || psqxbh.equals("") || pibzw == null || pibzw.equals("")) {
      tmp_Where += " 1=1 ";
    }
    else {
      String pszgbh, pstmp;
      // 获取PB函数中相应的函数参数
      // 设置职工编号(在此以当前登录的职工编号做为参数)
      pstmp = PO.GetValueByEnvName("UserName");
      PO.SetValueByParamName("pszgbh", pstmp);
      // 设置权限编号，由本函数参数获得
      PO.SetValueByParamName("psqxbh", psqxbh);
      // 设置数据编号，由本函数参数获取
      PO.SetValueByParamName("pscol", pscol);
      // 设置数据的标志位
      PO.SetValueByParamName("pibzw", pibzw);
      // 设置表名的年度后缀
      PO.SetValueByEnvName("Suffixyear", tableNameSuffix);
      tmp_Where += CheckDataLimit2(conn, PO);
    }
//    tmp_Where += SCheckCenterLimit(psTable, centerId, psSyzt, tableNameSuffix) +
//        " ) ";
    tmp_Where += ")";
    return tmp_Where;
  }

  //----------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改: 返回值:
  //----------------------------------------------------------------------------------------------
  public static String SCheckDataLimit3(JConnection conn, JParamObject PO) {
    String pszgbh, pstmp;

    String psqxbh = PO.GetValueByParamName("psqxbh");
    String pssjbh = PO.GetValueByParamName("pssjbh");
    String pibzw = PO.GetValueByParamName("pibzw");
    String pscol = PO.GetValueByParamName("pscol");
    // 获取PB函数中相应的函数参数
    // 设置职工编号(在此以当前登录的职工编号做为参数)
    pstmp = PO.GetValueByEnvName("UserName");
    PO.SetValueByParamName("pszgbh", pstmp);
    // 设置权限编号，由本函数参数获得
    PO.SetValueByParamName("psqxbh", psqxbh);
    // 设置数据编号，由本函数参数获取
    PO.SetValueByParamName("pscol", pscol);
    // 设置数据的标志位
    PO.SetValueByParamName("pibzw", pibzw);
    return CheckDataLimit3(conn, PO);
  }

  //----------------------------------------------------------------------------------------------
  //描述:
  //设计: Xyz(2001.12.29)
  //实现: Xyz
  //修改: 返回值:
  //----------------------------------------------------------------------------------------------
  public static String SCheckDataLimit3(JConnection conn, JParamObject PO,
                                        JParamObject InputData) {
    String tmp_Where = " ( ";

    JParamObject IN = InputData;

    String psqxbh = IN.GetValueByParamName("psqxbh");
    String pibzw = IN.GetValueByParamName("pibzw");
    String pscol = IN.GetValueByParamName("pscol");
    String tableNameSuffix = IN.GetValueByParamName("tableNameSuffix");
    String psTable = IN.GetValueByParamName("psTable");
    String centerId = IN.GetValueByParamName("centerId");
    String psSyzt = IN.GetValueByParamName("psSyzt");

    if (psqxbh == null || psqxbh.equals("") || pibzw == null || pibzw.equals("")) {
      tmp_Where += " 1=1 ";
    }
    else {
      String pszgbh, pstmp;
      // 获取PB函数中相应的函数参数
      // 设置职工编号(在此以当前登录的职工编号做为参数)
      pstmp = PO.GetValueByEnvName("UserName");
      PO.SetValueByParamName("pszgbh", pstmp);
      // 设置权限编号，由本函数参数获得
      PO.SetValueByParamName("psqxbh", psqxbh);
      // 设置数据编号，由本函数参数获取
      PO.SetValueByParamName("pscol", pscol);
      // 设置数据的标志位
      PO.SetValueByParamName("pibzw", pibzw);
      // 设置表名的年度后缀
      PO.SetValueByEnvName("Suffixyear", tableNameSuffix);
      tmp_Where += CheckDataLimit3(conn, PO);
    }
//    tmp_Where += SCheckCenterLimit(psTable, centerId, psSyzt, tableNameSuffix) +
//        " ) ";
    tmp_Where += ")";
    return tmp_Where;
  }

  //----------------------------------------------------------------------------------------------
  //描述:动态生成查询SQL的责任中心权限检查WEHERE条件
  //设计: Xyz(2001.12.29)
  //实现: Xyz
  //修改: 返回值:
  //----------------------------------------------------------------------------------------------
  //----------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改: 返回值: 如果不为空,说明此用户有此项权限,如果为空,说明此用户没有此项权限
  //----------------------------------------------------------------------------------------------
  public static String SCheckDataLimit1(JConnection conn, JParamObject PO,
                                        String psqxbh, String pssjbh,
                                        String pibzw) {
    String pszgbh, pstmp;
    boolean res;
    String Res = "0";
    // 获取PB函数中相应的函数参数
    // 设置职工编号(在此以当前登录的职工编号做为参数)
    pstmp = PO.GetValueByEnvName("UserName");
    PO.SetValueByParamName("pszgbh", pstmp);
    // 设置权限编号，由本函数参数获得
    PO.SetValueByParamName("psqxbh", psqxbh);
    // 设置数据编号，由本函数参数获取
    PO.SetValueByParamName("pssjbh", pssjbh);
    // 设置数据的标志位
    PO.SetValueByParamName("pibzw", pibzw);
    res = CheckDataLimit1(conn, PO);
    if (res)
      Res = "1";
    return Res;
  }

  //----------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改: 返回值:
  //----------------------------------------------------------------------------------------------
  public static String SCheckDataLimit2(JConnection conn, JParamObject PO,
                                        String psqxbh, String pscol,
                                        String pibzw) {
    String pszgbh, pstmp;
    // 获取PB函数中相应的函数参数
    // 设置职工编号(在此以当前登录的职工编号做为参数)
    pstmp = PO.GetValueByEnvName("UserName");
    PO.SetValueByParamName("pszgbh", pstmp);
    // 设置权限编号，由本函数参数获得
    PO.SetValueByParamName("psqxbh", psqxbh);
    // 设置数据编号，由本函数参数获取
    PO.SetValueByParamName("pscol", pscol);
    // 设置数据的标志位
    PO.SetValueByParamName("pibzw", pibzw);
    return CheckDataLimit2(conn, PO);
  }

  //----------------------------------------------------------------------------------------------
  //描述:
  //设计: Xyz(2001.12.29)
  //实现: Xyz
  //修改: 返回值:
  //----------------------------------------------------------------------------------------------
  public static String SCheckDataLimit2(JConnection conn, JParamObject PO,
                                        String psqxbh, String pscol,
                                        String pibzw, String psTable,
                                        String centerId, String psSyzt,
                                        String tableNameSuffix) {
    String tmp_Where = " ( ";
    if (psqxbh == null || psqxbh.equals("") || pibzw == null || pibzw.equals("")) {
      tmp_Where += " 1=1 ";
    }
    else {
      String pszgbh, pstmp;
      // 获取PB函数中相应的函数参数
      // 设置职工编号(在此以当前登录的职工编号做为参数)
      pstmp = PO.GetValueByEnvName("UserName");
      PO.SetValueByParamName("pszgbh", pstmp);
      // 设置权限编号，由本函数参数获得
      PO.SetValueByParamName("psqxbh", psqxbh);
      // 设置数据编号，由本函数参数获取
      PO.SetValueByParamName("pscol", pscol);
      // 设置数据的标志位
      PO.SetValueByParamName("pibzw", pibzw);
      // 设置表名的年度后缀
      PO.SetValueByEnvName("Suffixyear", tableNameSuffix);
      tmp_Where += CheckDataLimit2(conn, PO);
    }
//    tmp_Where = tmp_Where + " and ("+SCheckCenterLimit(psTable, centerId, psSyzt, tableNameSuffix) +
//        " )";
    tmp_Where += ")";
    return tmp_Where;
  }

  //----------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改: 返回值:
  //----------------------------------------------------------------------------------------------
  public static String SCheckDataLimit3(JConnection conn, JParamObject PO,
                                        String psqxbh, String pscol,
                                        String pibzw) {
    String pszgbh, pstmp;
    // 获取PB函数中相应的函数参数
    // 设置职工编号(在此以当前登录的职工编号做为参数)
    pstmp = PO.GetValueByEnvName("UserName");
    PO.SetValueByParamName("pszgbh", pstmp);
    // 设置权限编号，由本函数参数获得
    PO.SetValueByParamName("psqxbh", psqxbh);
    // 设置数据编号，由本函数参数获取
    PO.SetValueByParamName("pscol", pscol);
    // 设置数据的标志位
    PO.SetValueByParamName("pibzw", pibzw);
    return CheckDataLimit3(conn, PO);
  }

  //----------------------------------------------------------------------------------------------
  //描述:
  //设计: Xyz(2001.12.29)
  //实现: Xyz
  //修改: 返回值:
  //----------------------------------------------------------------------------------------------
  public static String SCheckDataLimit3(JConnection conn, JParamObject PO,
                                        String psqxbh, String pscol,
                                        String pibzw, String psTable,
                                        String centerId, String psSyzt,
                                        String tableNameSuffix) {
    String tmp_Where = " ( ";
    if (psqxbh == null || psqxbh.equals("") || pibzw == null || pibzw.equals("")) {
      tmp_Where += " 1=1 ";
    }
    else {
      String pszgbh, pstmp;
      // 获取PB函数中相应的函数参数
      // 设置职工编号(在此以当前登录的职工编号做为参数)
      pstmp = PO.GetValueByEnvName("UserName");
      PO.SetValueByParamName("pszgbh", pstmp);
      // 设置权限编号，由本函数参数获得
      PO.SetValueByParamName("psqxbh", psqxbh);
      // 设置数据编号，由本函数参数获取
      PO.SetValueByParamName("pscol", pscol);
      // 设置数据的标志位
      PO.SetValueByParamName("pibzw", pibzw);
      // 设置表名的年度后缀
      PO.SetValueByEnvName("Suffixyear", tableNameSuffix);
      tmp_Where += CheckDataLimit3(conn, PO);
    }
    /**
     * 7.0中责任中心选用那些方法，都不用
     */
    //    String DYGXJC="1";
//    DYGXJC = PO.GetValueByParamName("DYGXJC","1");
//    if ( "1".equals(DYGXJC) ) {
//      tmp_Where += SCheckCenterLimit(psTable, centerId, psSyzt, tableNameSuffix);
//    }
    tmp_Where += " ) ";
    return tmp_Where;
  }

  /**
   * 注册一个权限。
   * @param conn JConnection
   * @param PO JParamObject
   * @return boolean
   */
  public static boolean RegDataLimit(JConnection conn, JParamObject PO) {
    String pszgbh, psqxbh, pssjbh, pssuffixyear, pstmp;
    int pibzw = 0;
    String vstemp, vssql, vstbl, vsfzlb, vssavetbl, vsSfsy;
    int vicount;
    String strGran = "";
    String strType = "1";
    //JParamObject PO = new JParamObject(p0);
    Statement st = null;
    ResultSet RS;

    // 获取PB函数中相应的函数参数
    pszgbh = PO.GetValueByParamName("pszgbh");
    psqxbh = PO.GetValueByParamName("psqxbh");
    pssjbh = PO.GetValueByParamName("pssjbh");
    pstmp = PO.GetValueByParamName("pibzw");

    /**
     * 获得存放数据权限的表与定义的权限内容。
     */
    vssql =
        "select F_SFSY,F_QXB,F_GRAN from " + DBTools.getDBAObject(PO, "BSGRAN") +
        " where F_QXBH='" + psqxbh + "'";
    vssavetbl = "BSUSSJ";
    try {
      st = conn.createStatement();
      RS = st.executeQuery(vssql);
      if (RS.next()) {
        vssavetbl = RS.getString("F_QXB"); //数据表。
        strGran = RS.getString("F_GRAN"); //完整的权限规则。
        strType = RS.getString("F_SFSY");

        //以下情况可以不处理。未启用。使用规则。
        if (strType == null || strType.equals("0") || strType.equals("2")) {
          return false;
        }
      }
      else {
        return false;
      }
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    finally {
      try {
        st.close();
      }
      catch (Exception E) {

      }
    }
    if (vssavetbl == null || vssavetbl.trim().length() == 0)
      vssavetbl = "BSUSSJ";

    vstbl = vssavetbl;
    int iGranCount = strGran.length();

    vssavetbl = DBTools.getDBAObject(PO, vssavetbl);

    //add by lchong
    //先判断一下,该权限表中是否已经存在该字典,如果存在,则不用插入
    boolean isExits = false;
    String isExitsTableSql = "SELECT * FROM " + vssavetbl +
        " t Where t.F_SJBH  = '" + pssjbh + "' "
        + " AND t.F_ZGBH = '" + pszgbh + "' AND t.F_QXBH = '" + psqxbh + "' ";
    try {
      st = conn.createStatement();
      ResultSet existRS = st.executeQuery(isExitsTableSql);
      if (existRS.next()) {
        isExits = true;
      }
      existRS.close();
    }
    catch (Exception E) {
      E.printStackTrace();
      return false;
    }
    finally {
      try {
        st.close();
      }
      catch (Exception E) {
      }
    }
    if (isExits) //存在权限,则不用插入,返回
      return false;
    //add end


    /**
     * 以下生成权限的记录。
     */
    StringBuffer pBuf = new StringBuffer();
    pBuf.append("INSERT INTO " + vssavetbl + "(F_ZGBH,F_QXBH,F_SJBH,F_GRAN,");
    for (int i = 1; i <= iGranCount; i++) {
      pBuf.append("F_G");
      pBuf.append(i);
      pBuf.append(",");
    }

    pBuf.append("F_G0)");

    pBuf.append(" VALUES(");
    pBuf.append("'" + pszgbh + "',");
    pBuf.append("'" + psqxbh + "',");
    pBuf.append("'" + pssjbh + "',");
    pBuf.append("'" + strGran + "',");

    for (int i = 1; i <= iGranCount; i++) {
      pBuf.append("'1',");
    }

    pBuf.append("'1')");

    String strSQL = pBuf.toString();
    try {
      st = conn.createStatement();
      if (st.execute(strSQL)) {
        return true;
      }
      else {
        return false;
      }
    }
    catch (Exception E) {
      E.printStackTrace();
      return false;
    }
    finally {
      try {
        st.close();
      }
      catch (Exception E) {
      }
    }

  }

  /**
   * 注册一个权限。
   * @param conn JConnection
   * @param PO JParamObject
   * @return boolean
   */
  public static boolean RegDataLimits(JConnection conn, JParamObject PO) {
    String pszgbh, psqxbh, pssuffixyear, pstmp;
    int pibzw = 0;
    String vstemp, vssql, vstbl, vsfzlb, vssavetbl, vsSfsy;
    int vicount;
    String strGran = "";
    String strType = "1";
    //JParamObject PO = new JParamObject(p0);
    Statement st = null;
    PreparedStatement pST = null;
    ResultSet RS;
    Vector pssjbh;

    // 获取PB函数中相应的函数参数
    pszgbh = PO.GetValueByParamName("pszgbh");
    psqxbh = PO.GetValueByParamName("psqxbh");
    pssjbh = (Vector) PO.getValue("pssjbh", null);
    pstmp = PO.GetValueByParamName("pibzw");

    /**
     * 获得存放数据权限的表与定义的权限内容。
     */
    vssql =
        "select F_SFSY,F_QXB,F_GRAN from BSGRAN" + " where F_QXBH='" + psqxbh +
        "'";
    vssavetbl = "BSUSSJ";
    try {
      st = conn.createStatement();
      RS = st.executeQuery(vssql);
      if (RS.next()) {
        vssavetbl = RS.getString("F_QXB"); //数据表。
        strGran = RS.getString("F_GRAN"); //完整的权限规则。
        strType = RS.getString("F_SFSY");

        //以下情况可以不处理。未启用。使用规则。
        if (strType == null || strType.equals("0") || strType.equals("2")) {
          return false;
        }
      }
      else {
        return false;
      }
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    finally {
      try {
        st.close();
      }
      catch (Exception E) {

      }
    }
    if (vssavetbl == null || vssavetbl.trim().length() == 0)
      vssavetbl = "BSUSSJ";

    vstbl = vssavetbl;
    int iGranCount = strGran.length();

    /**
     * 以下生成权限的记录。
     */
    StringBuffer pBuf = new StringBuffer();
    pBuf.append("INSERT INTO " + vssavetbl + "(F_ZGBH,F_QXBH,F_SJBH,F_GRAN,");
    for (int i = 1; i <= iGranCount; i++) {
      pBuf.append("F_G");
      pBuf.append(i);
      pBuf.append(",");
    }

    pBuf.append("F_G0)");

    pBuf.append(" VALUES(");
    pBuf.append("'" + pszgbh + "',");
    pBuf.append("'" + psqxbh + "',");
    pBuf.append("?,");
    pBuf.append("'" + strGran + "',");

    for (int i = 1; i <= iGranCount; i++) {
      pBuf.append("'1',");
    }

    pBuf.append("'1')");

    String strSQL = pBuf.toString();
    try {
      pST = conn.prepareStatement(strSQL);

      for (int i = 0; i < pssjbh.size(); i++) {
        try {
          pST.setString(1, pssjbh.get(i).toString());
          pST.execute();
        }
        catch (Exception E) {

        }
      }
      return true;
    }
    catch (Exception E) {
      E.printStackTrace();
      return false;
    }
    finally {
      try {
        pST.close();
      }
      catch (Exception E) {
      }
    }

  }

  /**
   * 移除一个权限。
   * @param conn JConnection
   * @param PO JParamObject
   * @return boolean
   */
  public static boolean UnRegDataLimit(JConnection conn, JParamObject PO) {
    String pszgbh, psqxbh, pssjbh, pssuffixyear, pstmp;
    int pibzw = 0;
    String vstemp, vssql, vstbl, vsfzlb, vssavetbl, vsSfsy;
    int vicount;
    String strGran = "";
    String strType = "1";
    //JParamObject PO = new JParamObject(p0);
    Statement st = null;
    ResultSet RS;

    // 获取PB函数中相应的函数参数
    pszgbh = PO.GetValueByParamName("pszgbh");
    psqxbh = PO.GetValueByParamName("psqxbh");
    pssjbh = PO.GetValueByParamName("pssjbh");
    pstmp = PO.GetValueByParamName("pibzw");
    /**
     * 获得存放数据权限的表与定义的权限内容。
     */
    vssql =
        "select F_SFSY,F_QXB,F_GRAN from " + DBTools.getDBAObject(PO, "BSGRAN") +
        " where F_QXBH='" + psqxbh + "'";
    vssavetbl = "BSUSSJ";
    try {
      st = conn.createStatement();
      RS = st.executeQuery(vssql);
      if (RS.next()) {
        vssavetbl = RS.getString("F_QXB"); //数据表。
        strGran = RS.getString("F_GRAN"); //完整的权限规则。

        strType = RS.getString("F_SFSY");
        //以下情况可以不处理。未启用。使用规则。
        if (strType == null || strType.equals("0") || strType.equals("2")) {
          return false;
        }

      }
      else {
        return false;
      }
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    finally {
      try {
        st.close();
      }
      catch (Exception E) {
      }

    }
    if (vssavetbl == null || vssavetbl.trim().length() == 0)
      vssavetbl = "BSUSSJ";

    vstbl = vssavetbl;
    int iGranCount = strGran.length();

    /**
     * 以下生成权限的记录。
     */
    StringBuffer pBuf = new StringBuffer();
    pBuf.append("DELETE FROM " + DBTools.getDBAObject(PO, vssavetbl) +
                " WHERE ");

    pBuf.append("F_QXBH='");
    pBuf.append(psqxbh);
    pBuf.append("' AND F_SJBH = '");
    pBuf.append(pssjbh);
    pBuf.append("'");

    String strSQL = pBuf.toString();
    try {
      st = conn.createStatement();
      if (st.execute(strSQL)) {
        return true;
      }
      else {
        return false;
      }
    }
    catch (Exception E) {
      E.printStackTrace();
      return false;
    }
    finally {
      try {
        st.close();
      }
      catch (Exception E) {
      }
    }

  }

  /**
   * 移除一个权限。
   * @param conn JConnection
   * @param PO JParamObject
   * @return boolean
   */
  public static boolean UnRegDataLimits(JConnection conn, JParamObject PO) {
    String pszgbh, psqxbh, pssuffixyear, pstmp;
    int pibzw = 0;
    String vstemp, vssql, vstbl, vsfzlb, vssavetbl, vsSfsy;
    int vicount;
    String strGran = "";
    String strType = "1";
    //JParamObject PO = new JParamObject(p0);
    Statement st = null;
    PreparedStatement pST = null;
    ResultSet RS;
    Vector pssjbh;

    // 获取PB函数中相应的函数参数
    pszgbh = PO.GetValueByParamName("pszgbh");
    psqxbh = PO.GetValueByParamName("psqxbh");
    pssjbh = (Vector) PO.getValue("pssjbh", null);
    pstmp = PO.GetValueByParamName("pibzw");
    /**
     * 获得存放数据权限的表与定义的权限内容。
     */
    vssql =
        "select F_SFSY,F_QXB,F_GRAN from " + DBTools.getDBAObject(PO, "BSGRAN") +
        " where F_QXBH='" + psqxbh + "'";
    vssavetbl = "BSUSSJ";
    try {
      st = conn.createStatement();
      RS = st.executeQuery(vssql);
      if (RS.next()) {
        vssavetbl = RS.getString("F_QXB"); //数据表。
        strGran = RS.getString("F_GRAN"); //完整的权限规则。

        strType = RS.getString("F_SFSY");
        //以下情况可以不处理。未启用。使用规则。
        if (strType == null || strType.equals("0") || strType.equals("2")) {
          return false;
        }

      }
      else {
        return false;
      }
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    finally {
      try {
        st.close();
      }
      catch (Exception E) {
      }

    }
    if (vssavetbl == null || vssavetbl.trim().length() == 0)
      vssavetbl = "BSUSSJ";

    vstbl = vssavetbl;
    int iGranCount = strGran.length();

    /**
     * 以下生成权限的记录。
     */
    StringBuffer pBuf = new StringBuffer();
    pBuf.append("DELETE FROM " + DBTools.getDBAObject(PO, vssavetbl) +
                " WHERE ");

    pBuf.append("F_QXBH='");
    pBuf.append(psqxbh);
    pBuf.append("' AND F_SJBH = ?");

    String strSQL = pBuf.toString();
    try {
      pST = conn.prepareStatement(strSQL);

      for (int i = 0; i < pssjbh.size(); i++) {
        pST.setString(1, pssjbh.get(i).toString());
        pST.execute();
      }

      return true;
    }
    catch (Exception E) {
      E.printStackTrace();
      return false;
    }
    finally {
      try {
        st.close();
      }
      catch (Exception E) {
      }
    }
  }

  /**
   *
   * @param stmt Statement
   * @param PO JParamObject
   * @return String[]
   * @throws Exception
   */
  public static String[] checkUserVaild(Statement stmt, JParamObject PO) throws
      Exception {
    String F_ZGBH = PO.GetValueByEnvName("UserName");
    if (F_ZGBH.equals("9999") || F_ZGBH.equals("8888"))
      return null;
    return checkUserLogin(stmt, PO, F_ZGBH);
//
//      int count;
//      ResultSet rs   = null;
//      String strSql,value,zgbh,mess,date,today;
//      zgbh  = PO.GetValueByEnvName("UserName");
//      if(zgbh.equals("9999") || zgbh.equals("8888")){
//        return null;
//      }
//      String passday="",gsdm="",F_IP="",F_IPKZ="";
//      try{
//        strSql="select F_PASSDATE,UNIT_ID,F_IP,F_IPKZ FROM "+DBTools.getDBAObject(PO,"BSUSER")+" where F_ZGBH='"+zgbh+"'";
//        rs = stmt.executeQuery(strSql);
//        if (rs.next()) {
//           passday = rs.getString(1);
//           gsdm=rs.getString(2);
//           F_IP=rs.getString(3);
//           if(F_IP==null)F_IP="";
//           F_IP=F_IP.trim();
//           F_IPKZ=rs.getString(4);
//        }
//        if(F_IPKZ==null)F_IPKZ="";
//        String F_IP1 = (String) PO.getValue("_sessionIP_", null);
//        if("1".equals(F_IPKZ)&&F_IP!=null&&!F_IP.equals("")&&!F_IP.equals("0.0.0.0")&&!F_IP.equals(F_IP1)){
//           return new String[]{"1","用户不允许用IP地址为"+F_IP1+"的机器登陆！"};
//        }
////        if(gsdm==null||"".equals(gsdm.trim())){
////           return new String[]{"1","未取得该用户的公司代码！"};
////        }
//        today = "";
//        if (JConvertSql.isOracle(stmt)) {
//            strSql = "select  to_char(sysdate,'YYYYMMDD') from dual ";
//        }
//        else {
//            strSql = "select convert (char(30),getdate(),102)";
//        }
//        rs = stmt.executeQuery(JConvertSql.convertSql(stmt, strSql));
//        if (rs.next()) {
//            today = rs.getString(1).trim();
//            today = StringFunction.replaceString(today, ".", "");
//        }
//        strSql = "select 1 from "+DBTools.getDBAObject(PO,"BSCONF")+" where F_VKEY = 'CW_RQKZ' and isnull(F_VAL,' ') = '1'";
//        strSql+=" AND (UNIT_ID='"+gsdm+"' or UNIT_ID is null or UNIT_ID = ' ') order by UNIT_ID desc";
//        rs = stmt.executeQuery(strSql);
//        if(rs.next()){
//            date = today;
//            if (date.length() < 8) {
//              return new String[]{"1","取当前日期时出错！"};
//            }
//            strSql = "select 1 from "+DBTools.getDBAObject(PO,"PSLDATE")+" where F_ZGBH = '" + zgbh + "'" +
//                " and F_YEAR = '" + date.substring(0, 6) + "' and F_RQ" + date.substring(6, 8) + " = '0'";
//            rs = stmt.executeQuery(JConvertSql.convertSql(stmt, strSql));
//            if (rs.next()) {
//                mess ="该用户不允许在[" + date + "]登录！";
//                return new String[]{"1",mess};
//            }
//            today = date;
//        }
//
//        strSql = "select 1 from "+DBTools.getDBAObject(PO,"BSCONF")+" where F_VKEY = 'CW_SJKZ' and isnull(F_VAL,' ') = '1'";
//        strSql+=" AND (UNIT_ID='"+gsdm+"' or UNIT_ID is null or UNIT_ID = ' ') order by UNIT_ID desc";
//        rs = stmt.executeQuery(strSql);
//        if(rs.next()){
//            date = "";
//            if (JConvertSql.isOracle(stmt)) {
//                strSql = "select  to_char(sysdate,'HH:MM:SS') from dual ";
//            }
//            else {
//                strSql = "select convert (char(30),getdate(),8)";
//            }
//            rs = stmt.executeQuery(JConvertSql.convertSql(stmt, strSql));
//            if (rs.next()) {
//                date = rs.getString(1).trim();
//            }
//            if (date.length() > 5) {
//                date = date.substring(0, 5);
//            }
//            strSql = "select 1 from "+DBTools.getDBAObject(PO,"PSLTIME")+" where F_ZGBH = '" + zgbh + "'" +
//                " and F_STIME <= '" + date + "' AND F_ETIME>='" + date + "'";
//            rs = stmt.executeQuery(JConvertSql.convertSql(stmt, strSql));
//            if (!rs.next()) {
//                count = 0;
//                strSql =
//                    "select count(1) from "+DBTools.getDBAObject(PO,"PSLTIME")+" where F_STIME = F_ETIME and F_STIME = '00:00' and F_ZGBH = '" + zgbh +
//                    "'";
//                rs = stmt.executeQuery(JConvertSql.convertSql(stmt, strSql));
//                if (rs.next()) {
//                    count = rs.getInt(1);
//                }
//                if (count < 4) {
//                  mess = "+该用户不允许在时间[+" + date + "]登录！";
//                    return new String[]{"1",mess};
//                }
//            }
//        }
//        value = "";
//        strSql = "select F_VAL FROM "+DBTools.getDBAObject(PO,"BSCONF")+" WHERE F_VKEY='CW_PASSDAY'";
//        strSql+=" AND (UNIT_ID='"+gsdm+"' or UNIT_ID is null or UNIT_ID = ' ') order by UNIT_ID desc";
//        rs = stmt.executeQuery(JConvertSql.convertSql(stmt,strSql));
//        if (rs.next()) {
//          value = rs.getString(1).trim();
//        }
//        if (value == null || value.trim().equals("")) {
//          value = "0";
//        }
//        count = Integer.parseInt(value);
//        if(count==0)return null;
//        if (passday == null || "".equals(passday.trim()))
//            return new String[] {"2", "密码已经失效过期！请立即修改密码。"};
//        Calendar td = Calendar.getInstance();
//        td.set(Integer.parseInt(today.substring(0, 4)),
//               Integer.parseInt(today.substring(4, 6)),
//               Integer.parseInt(today.substring(6, 8)));
//        Calendar md = Calendar.getInstance();
//        md.set(Integer.parseInt(passday.substring(0, 4)),
//               Integer.parseInt(passday.substring(4, 6)),
//               Integer.parseInt(passday.substring(6, 8)));
//        md.add(md.DATE, count);
//        if (md.compareTo(td) <= 0)
//            return new String[] {"2", "密码已经失效过期！请立即修改密码。"};
//
//      }catch(Exception e){
//        e.printStackTrace();
//        mess = "检查系统用户时出错！\r\n原因：" + e.getMessage();
//        return   new String[]{"1",mess};
//      }
//      return null;
  }

  /**
   *
   * @param st Statement
   * @param PO JParamObject
   * @param F_ZGBH String
   * @return String[]
   * @throws Exception
   */
  public static String[] checkUserLogin(Statement st, JParamObject PO,
                                        String F_ZGBH) throws Exception {
    EFRowSet user = EFRowSet.getInstance();
    // 用户主数据
    String sql = "select * from " + DBTools.getDBAObject(PO, "BSUSER") +
        " where F_ZGBH='" + F_ZGBH + "'";
    ResultSet rs = st.executeQuery(sql);
    if (rs.next())
      DataSetUtils.resultSet2RowSet(rs, user);
    rs.close();
    // 密码设置数据
    EFDataSet dss = DBOSecurityObject.getPassConfigSet(st, PO,
        user.getString("UNIT_ID", ""));

    String F_IPKZ = user.getString("F_IPKZ", "");
    String F_IP = user.getString("F_IP", "");
    String F_IP1 = (String) PO.getValue("_sessionIP_", null);
    if ("1".equals(F_IPKZ) && F_IP != null && F_IP.trim().length() > 0 &&
        !F_IP.equals("0.0.0.0") && !F_IP.equals(F_IP1))
      return new String[] {
          "1", "该用户不允许用IP地址为" + F_IP1 + "的机器登陆！"};
    // 登录日期控制
    EFRowSet cfg = (EFRowSet) dss.getRowSet("CW_RQKZ");
    if (cfg != null && "1".equals(cfg.getString("F_VAL", ""))) {
      EFRowSet r = getPSLDATE(st, PO, F_ZGBH);
      String date = DateFunction.getCurrentDate();
      if ("0".equals(r.getString("F_RQ" + date.substring(6, 8), "")))
        return new String[] {
            "1", "该用户不允许在" + date + "登陆！"};
    }
    // 登录时间控制
    cfg = (EFRowSet) dss.getRowSet("CW_SJKZ");
    if (cfg != null && "1".equals(cfg.getString("F_VAL", ""))) {

    }
    // 密码失效控制
    cfg = (EFRowSet) dss.getRowSet("CW_PASSDAY");
    String passdate = user.getString("F_PASSDATE", "");
    if (cfg != null && cfg.getString("F_VAL", "").trim().length() > 0
        && passdate.trim().length() > 0) {
      String passday = cfg.getString("F_VAL", "");
      String passnewd = DateFunction.getBeforeDate(passdate,
          Integer.parseInt(passday));
      String currdate = DateFunction.getCurrentDate();
      if (currdate.compareTo(passnewd) > 0)
        return new String[] {
            "2", "密码已经失效过期，请立即修改！（有效期为" + passday + "天）"};
    }

    return null;
  }

  /**
   *
   * @param st Statement
   * @param PO JParamObject
   * @param table String
   * @param F_ZGBH String
   * @return EFDataSet
   */
  public static EFRowSet getPSLDATE(Statement st, JParamObject PO,
                                    String F_ZGBH) throws Exception {
    String date = DateFunction.getCurrentDate();
    String psql = " select 1 from " + DBTools.getDBAObject(PO, "PSLDATE") +
        " where F_ZGBH = '" + F_ZGBH + "'" +
        " and F_YEAR = '" + date.substring(0, 6) + "'";
    ResultSet rs = st.executeQuery(psql);
    EFRowSet rss = EFRowSet.getInstance();
    if (rs.next())
      DataSetUtils.resultSet2RowSet(rs, rss);
    rs.close();
    return rss;
  }

  /**
   *
   * @param conn JConnection
   * @param PO JParamObject
   * @param F_ZGBH String
   * @param F_PASS String
   * @return String
   */
  public static String[] checkUserPassword(JConnection conn, JParamObject PO,
                                           String F_ZGBH, String F_PASS) throws
      Exception {
    Statement st = null;
    try {
      st = conn.createStatement();
      return checkUserPassword(st, PO, F_ZGBH, F_PASS);
    }
    finally {
      conn.BackStatement(st, null);
    }
  }

  /**
   *
   * @param st Statement
   * @param PO JParamObject
   * @param F_ZGBH String
   * @param F_PASS String
   * @return String
   */
  public static String[] checkUserPassword(Statement st, JParamObject PO,
                                           String F_ZGBH, String F_PASS) throws
      Exception {
    String sql = "select * from " + DBTools.getDBAObject(PO, "BSUSER") +
        " where F_ZGBH='" + F_ZGBH + "'";
    ResultSet rs = st.executeQuery(sql);
    // 用户数据
    EFRowSet user = EFRowSet.getInstance();
    if (rs.next())
      DataSetUtils.resultSet2RowSet(rs, user);
    rs.close();
    String UNIT_ID = user.getString("UNIT_ID", "");
    // 配置数据
    sql = "select * from " + DBTools.getDBAObject(PO, "BSCONF") +
        " where UNIT_ID = '" + UNIT_ID + "'";
    EFDataSet ds = EFDataSet.getInstance("");
    ds.setPrimeKey(new String[] {"F_VKEY"});
    rs = st.executeQuery(sql);
    DataSetUtils.resultSet2DataSet(rs, ds);
    rs.close();
    return checkUserPassword(user, ds, F_ZGBH, F_PASS);
  }

  /**
   *
   * @param user EFRowSet
   * @param ds EFDataSet
   * @param F_ZGBH String
   * @param F_PASS String
   * @return String[]
   * @throws Exception
   */
  public static String[] checkUserPassword(EFRowSet user, EFDataSet ds,
                                           String F_ZGBH, String F_PASS) throws
      Exception {
    EFRowSet cfgrs = null;
    /**
     * N天内只能修改一次密码
     */
    cfgrs = (EFRowSet) ds.getRowSet("CW_PASSRATE");
    if (cfgrs != null) {
      String currdate = DateFunction.getCurrentDate();
      String passrate = cfgrs.getString("F_VAL", ""); // n天可以修改一次密码
      String passdate = user.getString("F_PASSDATE", ""); // 上次修改密码日期
      // 在此日期后可以修改密码
      String pnewdate = DateFunction.getBeforeDate(passdate,
          Integer.parseInt(passrate));
      if (currdate.compareTo(pnewdate) < 0)
        return new String[] {
            "A", passrate + "天内只能修改一次密码！"};
    }

    /**
     * 密码的长度
     */
    cfgrs = (EFRowSet) ds.getRowSet("CW_PASSLEN");
    if (cfgrs != null) {
      String passlen = cfgrs.getString("F_VAL", "");
      if (F_PASS.length() < Integer.parseInt(passlen))
        return new String[] {
            "1", "密码应至少为" + passlen + "个字符！"};
      if (F_PASS.length() > 20)
        return new String[] {
            "1", "密码最长为" + passlen + "个字符！"};
    }

    /**
     * 口令的组成，兼容旧方式，最好用正则表达式
     */
    cfgrs = (EFRowSet) ds.getRowSet("CW_PASSCON");
    if (cfgrs != null) {
      String passform = cfgrs.getString("F_VAL", "");
      if ("1".equals(passform) && !isOnlyContainsChar(F_PASS))
        return new String[] {"B1", "密码必须全部是字母！"};
      if ("2".equals(passform) && !isOnlyContainsNumber(F_PASS))
        return new String[] {"B2", "密码必须全部是数字！"};
      if ("3".equals(passform))
        if (isContainsNumber(F_PASS) && isContainsChar(F_PASS))
          ;
        else
          return new String[] {"B3", "密码必须包含字母和数字！"};
      if ("4".equals(passform) && !isOnlyContainsSChar(F_PASS))
        return new String[] {"B4", "密码必须全部是特殊字符！"};
      if ("5".equals(passform))
        if (isContainsSChar(F_PASS) && isContainsChar(F_PASS))
          ;
        else
          return new String[] {"B5", "密码必须包含特殊字符和字母！"};
      if ("6".equals(passform))
        if (isContainsSChar(F_PASS) && isContainsNumber(F_PASS))
          ;
        else
          return new String[] {"B6", "密码必须包含特殊字符和数字！"};
      if ("7".equals(passform))
        if (isContainsSChar(F_PASS) && isContainsNumber(F_PASS) && isContainsChar(F_PASS))
          ;
        else
          return new String[] {"B7", "密码必须包含特殊字符、字母和数字！"};
    }

    /**
     * 最近N次密码不能相同
     */
    cfgrs = (EFRowSet) ds.getRowSet("CW_PASSRECENT");
    if (cfgrs != null) {
      // 0视为不判断
      String passrecent = cfgrs.getString("F_VAL", "");
      String encrptpass = MD5Tool.getDefault().getMD5ofStr(F_ZGBH + F_PASS);
      // 最近修改过的密码，以逗号分隔
      String recentpass = user.getString("F_ZJPASS", "");
      String rpass[] = recentpass.split(",");
      if (recentpass != null && recentpass.trim().length() > 0
          && java.util.Arrays.asList(rpass).contains(encrptpass))
        return new String[] {
            "C", "最近" + passrecent + "次的密码不能一样！"};
    }
    return null;
  }

  public static boolean isContainsNumber(String str) {
    for (char i = '0'; i <= '9'; i++) {
      if (str.indexOf(i) >= 0) {
        return true;
      }
    }
    return false;
  }

  public static boolean isOnlyContainsNumber(String str) {
    for (int i = 0; i < str.length(); i++) {
      if (str.charAt(i) >= '0' && str.charAt(i) <= '9') {
        continue;
      }
      else {
        return false;
      }
    }

    return true;
  }

  public static boolean isContainsChar(String str) {
    return isContainsLChar(str) || isContainsBChar(str);
  }

  public static boolean isContainsLChar(String str) {
    for (char i = 'a'; i <= 'z'; i++) {
      if (str.indexOf(i) >= 0) {
        return true;
      }
    }
    return false;
  }

  public static boolean isContainsBChar(String str) {
    for (char i = 'A'; i <= 'Z'; i++) {
      if (str.indexOf(i) >= 0) {
        return true;
      }
    }
    return false;
  }

  public static boolean isContainsSChar(String str) {
    String pa = "`~!@#$%^&*()_-+={}[],.'\":;?/|\\";
    for (int i = 0; i < str.length(); i++) {
      if (pa.indexOf(str.charAt(i)) >= 0)
        return true;
    }
    return false;
  }

  public static boolean isOnlyContainsSChar(String str) {
    String pa = "`~!@#$%^&*()_-+={}[],.'\":;?/|\\";
    for (int i = 0; i < str.length(); i++) {
      if (pa.indexOf(str.charAt(i)) < 0)
        return false;
    }
    return true;
  }

  public static boolean isOnlyContainsChar(String str) {
    for (int i = 0; i < str.length(); i++) {
      if ((str.charAt(i) >= 'a' && str.charAt(i) <= 'z')
          || (str.charAt(i) >= 'A' && str.charAt(i) <= 'Z')) {
        continue;
      }else{
        return false;
      }
    }
    return true;
  }

  public static boolean isOnlyContainsLChar(String str) {
    for (int i = 0; i < str.length(); i++) {
      if ( (str.charAt(i) >= 'a' && str.charAt(i) <= 'z')) {
        continue;
      }
      else {
        return false;
      }
    }
    return true;
  }


  /**
   *
   * @param st Statement
   * @param PO JParamObject
   * @param UNIT_ID String
   * @return EFDataSet
   */
  public static EFDataSet getPassConfigSet(Statement st, JParamObject PO, String UNIT_ID) throws Exception {
      String sql = "select * from " + DBTools.getDBAObject(PO, "BSCONF") +
          " where UNIT_ID is null or UNIT_ID = ' '";
      EFDataSet ds = EFDataSet.getInstance(""); ds.setPrimeKey(new String[]{"F_VKEY"});
      ResultSet rs = st.executeQuery(sql);
      DataSetUtils.resultSet2DataSet(rs, ds);
      rs.close();
      // 单位设置的优先
      sql = "select * from " + DBTools.getDBAObject(PO, "BSCONF") + " where UNIT_ID = '" + UNIT_ID + "'";
      rs = st.executeQuery(sql);
      EFDataSet ds2 = EFDataSet.getInstance(""); ds2.setPrimeKey(new String[]{"F_VKEY"});
      DataSetUtils.resultSet2DataSet(rs, ds2);
      rs.close();
      EFRowSet rss = null;
      for (int i = 0; ds2 != null && i < ds2.getRowCount(); i++) {
          rss = ds2.getRowSet(i);
          if (ds.getRowSet(rss.getString("F_VKEY", "")) != null) {
              ds.removeRowSet(ds.getRowSet(rss.getString("F_VKEY", "")));
              ds.addRowSet(rss);
          }
      }
      return ds;
  }

  public static void main(String[] args) {
    String regex = "[da-zA-Z]*d+[a-zA-Z]+[da-zA-Z]*";
    String conts = "123";
    System.out.println(RegexUtil.isMatch(regex, conts));
  }

}
