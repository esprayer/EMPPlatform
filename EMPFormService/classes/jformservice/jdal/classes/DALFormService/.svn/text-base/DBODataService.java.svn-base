package jformservice.jdal.classes.DALFormService;
import java.sql.*;
import java.util.*;

import com.eai.form.dal.*;
import com.eai.form.dal.interfaces.*;
import com.eai.frame.fcl.interfaces.dal.database.*;
import com.eai.frame.fcl.interfaces.message.*;
import com.eai.tools.lang.*;
import com.pansoft.pub.util.*;
import jfoundation.sql.classes.*;
import com.eai.toolkit.plugins.interfaces.IExtendPoint;
import com.eai.toolkit.plugins.TExtendPoints;
import com.eai.toolkit.compiler.TStringCompiler;
/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
public class DBODataService {
  public static int _RowsPerpage = 50000;
  protected static String[] mTableNames = null;
  private static int mPageTranslate = 0;
  private static int mPageCount = 0;

  private static String STR_SYBASE_CLS_NAME = "jformservice.jdal.classes.exchange.JExchangeSybase";
  private static String STR_ORACLE_CLS_NAME = "jformservice.jdal.classes.exchange.JExchangeOracle";

  public DBODataService() {
  }

  /**
   * 建立一个错误的消息
   * @param IR
   * @param Val
   */
  private static void CreateErrorMessage(IMessage IR, String Val) {
    IR.CreateItem("RESULT", "ERROR");
    IR.CreateItem("MSG", Val);
  }

  private static void CreateOKMessage(IMessage IR, String Val) {
    IR.CreateItem("RESULT", "OK");
    IR.CreateItem("MSG", Val);
  }
  /**
   * 取得类型名
   * @param TotleClsName
   * @return
   */
  public static String TypeName(String TotleClsName) {
     String name = TotleClsName;
     int pos = name.lastIndexOf('.');
     if (pos != -1) {
       name = name.substring(pos + 1);
     }
     return name;
     //return t.getName();
   }
  /**
   * 开始一个查询
   * @param SqlStr
   * @return
   */
  public static int SQLQuery( JConnection Conn, String SqlStr,IMessage _MsgResult) {

    Statement Stm  = null;
    try {
      String strNewSql = null;
      IDataExchangePlugin pPlugin = null;
      if ( JConvertSql.isOracle(Conn)){
        strNewSql = JConvertSql.convertSql(Conn,SqlStr);
        //得到Oracle的插件。
        pPlugin   = (IDataExchangePlugin)(TRCI.MakeObjectInstance(STR_ORACLE_CLS_NAME));
      }
      else{
        strNewSql = JConvertSql.convertSql(Conn,SqlStr);
        //得到Sybase的插件。
        pPlugin   = (IDataExchangePlugin)(TRCI.MakeObjectInstance(STR_SYBASE_CLS_NAME));
      }


      strNewSql = pPlugin.onTranslateQuerySQL(Conn,strNewSql);

      /**
       * 打印输出SQL。
       */
      Debug.PrintlnMessageToSystem(strNewSql);

      int LeftRows = 0;
      int PageCount = 0;

      /**
       * 使用BDE引擎来保存数据。
       */
      IDataExchange DEObj  = CreateMyExchangeInstance();
      LeftRows = DEObj.CreateFromResultSet(pPlugin,Conn,strNewSql);

      //记录下页数
      mPageCount = PageCount;
      mPageTranslate = 0;


      //写回返回结果中
      _MsgResult.CreateItem("SQLRESULT", DEObj);   //使用XML串为结果。
      _MsgResult.CreateItem("DSDEFINE",  DEObj.getDataSetDefineString());

      CreateOKMessage(_MsgResult,"");

      /**
       * 清理。
       */
      DEObj      = null;


    }
    catch (Exception E) {
      CreateErrorMessage(_MsgResult,
                         "Error,when execute SQL:" + SqlStr + ",JDBCMsg:" +
                         E.getMessage());
      E.printStackTrace();
      return -1;
    }
    finally{
      Conn.BackStatement(Stm,null);
    }

    return 0;
  }



  /**
   * 开始一个查询
   * @param SqlStr
   * @return
   */
  public static IDataSetDefine SQLQueryEx( JConnection Conn, String SqlStr,String strTName,String strKeys,IMessage _MsgResult) {
    IDataSetDefine  ITD = null;
    Statement      Stm  = null;
    try {

      String strNewSql = null;

      Stm = Conn.createStatement();

      IDataExchangePlugin pPlugin = null;

      if ( JConvertSql.isOracle(Conn)){
        strNewSql = JConvertSql.convertSql(Stm,SqlStr);
        //得到Oracle的插件。
        pPlugin   = (IDataExchangePlugin)(TRCI.MakeObjectInstance(STR_ORACLE_CLS_NAME));
      }
      else{
        strNewSql = SqlStr;
        //得到Sybase的插件。
        pPlugin   = (IDataExchangePlugin)(TRCI.MakeObjectInstance(STR_SYBASE_CLS_NAME));
      }

      strNewSql = pPlugin.onTranslateQuerySQL(Conn,strNewSql);

      int LeftRows = 0;
      int PageCount = 0;

      //创建数据交换对象
      IDataExchange DEObj = CreateMyExchangeInstance();
      LeftRows = DEObj.CreateFromResultSet(pPlugin,Conn,strNewSql);

      //记录下页数
      mPageCount = PageCount;
      mPageTranslate = 0;

      //下面重新设置已经生成的数据链的各项参数. 这些参数用来处理分页机制
      //即使没有数据,也会有一个IDE存在
      IDataExchange DE = DEObj;
      DE.setPageCount(1);
      DE.setPageIndex(0);
      DE.setDataKEY(0);
      DE.setRowsPerpage( DEObj.getRowCount() );
      DE.setRowCount( DEObj.getRowCount() );
      DE.setRowsInPage( DEObj.getRowCount() );

      //形成数据据定义
      //并输出至字符串,这需要操作XML
      Debug.PrintlnMessageToSystem("Enter create DSDefine...\n");

      //String DSDefineStr = getDSetDefine(SqlStr,strTName,strKeys, RS);
      //写回返回结果中
      _MsgResult.CreateItem("SQLRESULT", DE);
      _MsgResult.CreateItem("DSDEFINE", DEObj.getDataSetDefineString());

      CreateOKMessage(_MsgResult,"");

      //如果页数小于等于1，关闭SQL
      if (mPageCount <= 1) {
        SQLClose(0,_MsgResult);
      }

      Debug.PrintlnMessageToSystem("OK,Translate object...\n");

    }
    catch (Exception E) {
      CreateErrorMessage(_MsgResult,
                         "Error,when execute SQL:" + SqlStr + ",JDBCMsg:" +
                         E.getMessage());
      E.printStackTrace();
      return null;
    }
    finally{
      Conn.BackStatement(Stm,null);
    }

    return ITD;
  }

  public static boolean SQLClose(int SQLQueryID,IMessage _MsgResult) {

    return true;
  }

  /**
   * 执行一个独立的SQL语句
   * @param SqlStr:SQL
   * @return
   */
  public static boolean SQLExcute(JConnection Conn,String SqlStr,IMessage _MsgResult) {
    Statement St = Conn.createStatement();

    if (St == null) {
      return false;
    }

    //Excute...
    try {
      St.execute(JConvertSql.convertSql(St,SqlStr));
      return true;
    }
    catch (Exception E) {
      CreateErrorMessage(_MsgResult,
                         "ExecuteSQLExcept,SQL:" + SqlStr + ".\nExcept:" +
                         E.getMessage());

      E.printStackTrace();
      return false;
    }
    finally{
      Conn.BackStatement(St,null);
    }

  }

  /**
   * 获得数据集定义
   * @param SqlString
   * @param RS
   * @return
   */
  private static String getDSetDefine(String SqlString, String strTname,String strKeys,ResultSet RS) {
    IDataSetDefine TD = new TDataSetDefine(); //表定义
    TD.Prepare();

    TD.setSQLString(SqlString);

    //建立码表
    Properties KeyPros = TStringCompiler.CreateCodeTable(strKeys);


    try {
      ResultSetMetaData RMD = RS.getMetaData();
      int ColCount = RMD.getColumnCount();

      //如果只有一张表,该数据集各字段的属主表名只有一个, 直接赋值
      String OwnerName = "";
      //表名字
      if (mTableNames != null && mTableNames.length >= 1) {
        OwnerName = mTableNames[0];
      }

      //取出每个字段的信息并放入字段管理器
      for (int i = 1; i <= ColCount; i++) {
        IFieldDefine IF = new TFieldDefine(); //建立新的字段定义器

        String strFieldName = RMD.getColumnName(i);
        IF.setFieldName(strFieldName); //列名
        IF.setOwneredTable(OwnerName); //表名
        IF.setTitle("_" + IF.getFieldName()); //显示名
        IF.setNullable(RMD.isNullable(i) == ResultSetMetaData.columnNullable); //是否为空

        String strTypeName = TypeName(RMD.getColumnClassName(i));
        if ( strTypeName.equals("[B") ){
          strTypeName = "Image";
        }
        IF.setDataType(strTypeName); //类型名
        IF.setLength(RMD.getColumnDisplaySize(i));
        IF.setDotLength(2);
        IF.setOwneredTable( strTname );
        IF.setPhyFieldName(strFieldName);
        IF.setReqUpdate(true);

        //处理关键字否
        String strKey = KeyPros.getProperty(strFieldName);
        if ( strKey != null ){
          IF.setKeyField(true);
        }

        TD.getFieldsDefine().FieldAdd(IF);
      }

      //表名
      if (mTableNames != null) {
        TD.setTableNames(mTableNames);
      }

    }
    catch (Exception e) {
      e.printStackTrace();
    }

    return TD.OutputToString();
  }


  /**
   * 获得数据集定义
   * @param SqlString
   * @param RS
   * @return
   */
  private static String getDSetDefineFromBDE(String SqlString, String strTname,String strKeys,ResultSet RS) {
    IDataSetDefine TD = new TDataSetDefine(); //表定义
    TD.Prepare();

    TD.setSQLString(SqlString);

    //建立码表
    Properties KeyPros = TStringCompiler.CreateCodeTable(strKeys);


    try {
      ResultSetMetaData RMD = RS.getMetaData();
      int ColCount = RMD.getColumnCount();

      //如果只有一张表,该数据集各字段的属主表名只有一个, 直接赋值
      String OwnerName = "";
      //表名字
      if (mTableNames != null && mTableNames.length >= 1) {
        OwnerName = mTableNames[0];
      }

      //取出每个字段的信息并放入字段管理器
      for (int i = 1; i <= ColCount; i++) {
        IFieldDefine IF = new TFieldDefine(); //建立新的字段定义器

        String strFieldName = RMD.getColumnName(i);
        IF.setFieldName(strFieldName); //列名
        IF.setOwneredTable(OwnerName); //表名
        IF.setTitle("_" + IF.getFieldName()); //显示名
        IF.setNullable(RMD.isNullable(i) == ResultSetMetaData.columnNullable); //是否为空

        String strTypeName = TypeName(RMD.getColumnClassName(i));
        if ( strTypeName.equals("[B") ){
          strTypeName = "Image";
        }
        IF.setDataType(strTypeName); //类型名
        IF.setLength(RMD.getColumnDisplaySize(i));
        IF.setDotLength(2);
        IF.setOwneredTable( strTname );
        IF.setPhyFieldName(strFieldName);
        IF.setReqUpdate(true);

        //处理关键字否
        String strKey = KeyPros.getProperty(strFieldName);
        if ( strKey != null ){
          IF.setKeyField(true);
        }

        TD.getFieldsDefine().FieldAdd(IF);
      }

      //表名
      if (mTableNames != null) {
        TD.setTableNames(mTableNames);
      }

    }
    catch (Exception e) {
      e.printStackTrace();
    }

    return TD.OutputToString();
  }
  /**
   * 创建一个合适的对象。
   * @return IDataExchange
   */
  private static IDataExchange CreateMyExchangeInstanceGeneral(){
    return new TDataExchangeByObject();
  }

  private static IDataExchange CreateMyExchangeInstance(){
    IExtendPoint pTT = TExtendPoints.getDefault().getExtendPoint("com.pansoft.formservice.ExchangeObject.Active");
    if ( pTT != null ){
      Properties pKeys = pTT.getItemProperties("Active");
      String     pActive = pKeys.getProperty("Active","Default");

      IExtendPoint pExchange = TExtendPoints.getDefault().getExtendPoint("com.eai.frame.dal.ExchangeObject");
      if( pExchange != null ){
        Properties pExKeys = pExchange.getItemProperties( pActive );
        String pClass = pExKeys.getProperty( "Class", "" );
        Object pNew   = TRCI.MakeObjectInstance( pClass );
        if( pNew != null && pNew instanceof IDataExchange ){
          return( IDataExchange )pNew;
        }
      }
    }

    return null;
  }

}
