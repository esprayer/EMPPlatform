package jformservice.jdal.classes.DALFormService;

import java.sql.*;
import java.util.*;

import com.eai.form.dal.*;
import com.eai.form.dal.interfaces.*;
import com.eai.form.dal.update.*;
import com.eai.form.eaiservices.interfaces.form.*;
import com.eai.form.form.forms.gridform.define.*;
import com.eai.frame.fcl.interfaces.dal.database.*;
import com.eai.frame.fcl.interfaces.message.*;
import com.eai.tools.lang.*;
import com.eai.tools.translate.*;
import com.pansoft.pub.util.*;
import jformservice.jdal.classes.*;
import jfoundation.dataobject.classes.*;
import jfoundation.sql.classes.*;
import com.eai.toolkit.variable.interfaces.IEAIVariableManager;
import com.eai.toolkit.variable.interfaces.IEAIVariable;
/**
 * <p>DBOFormService</p>
 * <p>Form服务的接口实现对象</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
public class DBOFormService  extends DBODataService{
  private static    int MAX_LINES           = 200;
  private static String STR_SYBASE_CLS_NAME = "jformservice.jdal.classes.exchange.JExchangeSybase";
  private static String STR_ORACLE_CLS_NAME = "jformservice.jdal.classes.exchange.JExchangeOracle";

  public  static String FORM_TYPE_INSTANCE = "instance";
  public  static String FORM_TYPE_VIRTUAL = "virtual";
  private static String REQDS = "ReqDataSet";
  private static String SQL_NAME = "SqlText";
  private static String SQL_VALS   = "SqlValues";
  private static String SQL_Table  = "TableName";
  private static String SQL_Keys   = "Keys";
  //TXmlStg用来管理表单列表,它决定在哪里存这些窗口
  protected static DBOFormStorage mStg = new DBOFormStorage();

  private static IDataExchangePlugin pSybasePlugin = null;
  private static IDataExchangePlugin pOraclePlugin = null;

  protected static void   setResult(IMessage mResult,String FormDefine,String DSDefine){
    if (!DSDefine.equals("")) {
      mResult.CreateItem("DSDEFINE", DSDefine);
    }

    if (!FormDefine.equals("")) {
      mResult.CreateItem("FORMDEFINE", FormDefine);
    }

  }
  /**
   * 以下是给子类使用的方法,没有公布到接口中
   * @param FNames
   */

  /**
   * 设置本次SQL相关的表名
   * 这样可以不用自动确定相关表
   * @param FNames
   */
  public static void setTableNames(String FNames[]) {
    mTableNames = FNames;
  }

  /**
   * 设置本次SQL相关的表名
   * 这样可以不用自动确定相关表
   * @param FNames
   */
  public static String getFormDefine(String FName, String Type,JParamObject ENV,String strAppName) {
    return mStg.LoadFormDefine(FName, Type,ENV,strAppName);
  }

  /**
   * 设置本次SQL相关的表名
   * 这样可以不用自动确定相关表
   * @param FNames
   */
  public static String getDSDefine(String FName, String Type,JParamObject ENV,String strAppName) {
    return mStg.LoadDSDefine(FName + "_DS", Type,ENV,strAppName);
  }

  /**
   * 以下是已经公布客户端的方法
   * @return
   */

  /**
   * 获得该FORM的风格,以串
   * @return
   */
  public static String getFormStyle() {
    return IFormService.STYLE_GRID;
  }

  /**
   * 找开一个服务FORM,这是第一步.
   * @param SqlStr
   * @param ParameterXmlString
   * @return
   * 返回以下几个数据值
   * SQLRESULT : 首个数据集IDataExchange
   * DSDEFINE  : 数据集的定义格式XML文本
   * FORMDEFINE: 视的定义格式文本
   *
   * 本方法用来给子类-需要复杂计算的FORM重载的.
   */
  public static int OpenInstance(String SqlStr, String ParameterXmlString,IMessage _MsgResult) {
    return 0;
  }

  /**
   * 根据输入参数建立一个非实例型的FORM
   * @param SqlStr
   * @param ParameterXmlString
   * @return
   * 返回以下几个数据值
   * SQLRESULT : 首个数据集IDataExchange
   * DSDEFINE  : 数据集的定义格式XML文本
   * FORMDEFINE: 视的定义格式文本
   *
   * 本方法不需要子类重载.
   */
  public static int CreateVirtual(
                           JConnection Con,
                           JParamObject ENV,
                           String FormName,
                           String SqlStr,
                           String Tables,
                           IMessage _MsgResult
                           )
  {
    //取出表名,应该是个数组
    String[] TS = Tables.split(",");

    setTableNames(TS);
    SQLQuery(Con,SqlStr,_MsgResult);

    //从父类取得DS定义,如果不为空,更新一下
    String SavedInfo = getDSDefine(FormName, FORM_TYPE_VIRTUAL,ENV,null);
    String FormInfo  = getFormDefine(FormName, FORM_TYPE_VIRTUAL,ENV,null);

    setResult(_MsgResult,FormInfo,SavedInfo);

    return 0;
  }

  /**
   * 根据数据库类型获得插件。
   * @param pConn JConnection
   * @return IDataExchangePlugin
   */
  private static IDataExchangePlugin getPluginInstnace(JConnection pConn){
    if ( JConvertSql.isOracle(pConn) ){
      if ( pOraclePlugin == null){
        //得到Oracle的插件。
        pOraclePlugin = (IDataExchangePlugin) (TRCI.MakeObjectInstance(STR_ORACLE_CLS_NAME));
      }
      return pOraclePlugin;
     }
     else{
       if ( pSybasePlugin == null ){
         //得到Sybase的插件。
         pSybasePlugin = (IDataExchangePlugin) (TRCI.MakeObjectInstance(STR_SYBASE_CLS_NAME));
       }
       return pSybasePlugin;
     }
  }

  /**
   * 带有回调用初始化。
   * @param Con JConnection
   * @param ENV JParamObject
   * @param FName String
   * @param _MsgInput IMessage
   * @param _MsgResult IMessage
   * @param strAppName String
   * @param pCall IDALFormCallback
   * @return int
   */
  public static int OpenVirtual(JConnection Con, JParamObject ENV, String FName, IMessage _MsgInput, IMessage _MsgResult, String strAppName, IDALFormCallback pCall){
    /**
     * 本方法需要根据名称建立一个Formdefine,从中找出SQL,及定义信息,打开DS
     */
    String strFormDefine = pCall.onLoadFormDefine(FName);
    String strDSDefine   = pCall.onLoadDataSetDefine(FName);

    onLoadFormInfos(Con, _MsgInput, _MsgResult, strFormDefine, strDSDefine);

    return 1;
  }

  /**
   * 打开一个服务FORM,该FORM非实例型
   * @param SqlStr
   * @param ParameterXmlString
   * @return
   * 返回以下几个数据值
   * SQLRESULT : 首个数据集IDataExchange
   * DSDEFINE  : 数据集的定义格式XML文本
   * FORMDEFINE: 视的定义格式文本
   * 本方法不需要子类重载.
   *
   * 这个方法是专门给客户端打开纯静态的用的。
   */
  public static int OpenVirtual( JConnection Con,JParamObject ENV,String FName ,IMessage _MsgInput,IMessage _MsgResult,String strAppName) {
    /**
     * 本方法需要根据名称建立一个Formdefine,从中找出SQL,及定义信息,打开DS
     */
    String strFormDefine = mStg.LoadFormDefine(FName, FORM_TYPE_VIRTUAL,ENV,strAppName);
    String strDSDefine   = mStg.LoadDSDefine(FName + "_DS", FORM_TYPE_VIRTUAL,ENV,strAppName);

    onLoadFormInfos(Con, _MsgInput, _MsgResult, strFormDefine, strDSDefine);
    return 1;
  }


  private static void onLoadFormInfos(JConnection Con, IMessage _MsgInput,
                                      IMessage _MsgResult, String strFormDefine,
                                      String strDSDefine) {
    //如果需要生成数据
    if ( _MsgInput.asBoolValue( REQDS , true )){
      /**
       * 得到定义对象
       */
      TDataSetDefine DSDefine = new TDataSetDefine();
      DSDefine.InputFromString( strDSDefine );

      //如果客户给定了SQL,就用客户的，否则使用XML内存的。
      String UserSql = _MsgInput.asStringValue(SQL_NAME,"");

      String SQL = null;
      if ( UserSql.equals("")){
        SQL = DSDefine.getSQLString();
      }
      else
        SQL = UserSql;

      String[] TT = DSDefine.getTableNames();

      //如果客户指定的变量表，使用变量表替换内容。
      Object   sqlVals = _MsgInput.asObjectValue( SQL_VALS );
      IMessage IM      = null;
      if ( sqlVals != null ){
        try{
          IM = (IMessage) sqlVals;
        }
        catch (Exception E){
          Debug.PrintlnMessageToSystem("想得到SQL变量定义时异常，给定的SqlValues值非IMessage!");
          IM = null;
        }

        if ( IM != null ){
          SQL = TSqlCompiler.CompilerSql(SQL,IM);
        }
      }

      /**
       * 下面运行前置SQL。
       */
      //-----------------------------------------------------------------------------------------------
      /**
       * 以此，引入前置SQL的概念。在打开数据窗口的SQL执行前，先执行这些前置的SQL。
       * 用来准备一些必要的数据。这些SQL里也可以含有变量，并且使用与打开SQL时使用的变量映射表进行编译。
       * 前置SQL为一组，N个SQL。按先后顺序依次执行。
       * LISL.
       * 目前为止：SqlVal和strSqlParams里放的都是变量映射表。可以使用这两个东东编译前置SQL。
       */
      Vector strPreSqls = CreatePreSql( DSDefine.getPreSqlManager(),IM,"");
      for ( int i = 0; i < strPreSqls.size(); i ++){
        String strSqlTemp = (String)strPreSqls.get(i);
        SQLExcute( Con,strSqlTemp,_MsgResult);
      }

      /**
       * 打开表
       */
      setTableNames(TT);

      /**
       * SQL开始。
       * 之前转换成ISO.
       */
      SQLQuery(Con, SQL, _MsgResult);

      /**
       * 清空对象。
       */
      DSDefine = null;
    }

    //从父类取得DS定义,如果不为空,更新一下
    //String SavedInfo = getDSDefine(FName, FORM_TYPE_VIRTUAL,ENV,null);
    //String FormInfo  = getFormDefine(FName, FORM_TYPE_VIRTUAL,ENV,null);

    setResult(_MsgResult,strFormDefine,strDSDefine );
  }

  public static boolean UpdateEdit(JConnection Con,IMessage _MsgInput ,IMessage _MsgResult,boolean isAutoCommit ) {

    //得到XMLString并生成对象,
    String XmlUpdate = _MsgInput.asStringValue("UPDATE","");

    TUpdateInfo    UInfo = new TUpdateInfo(XmlUpdate);

    /**
     * 获得插件。
     */
    IDataExchangePlugin pPlugin = getPluginInstnace(Con);
    //设置插件。
    UInfo.setDataExchangePlugin(pPlugin);


    /**
     * 其它操作。
     */
    DBOUpdateRecall IRecall = new DBOUpdateRecall(Con);
    //得到SQL
    Vector  Sqls   = UInfo.BuildSqls( IRecall );
    if (Sqls.size() == 0) {
      _MsgResult.CreateItem("SQLRESULT", "OK");
      return true;
    }
    //------------------------------------------------------------------------------------
    //下面执行
    //------------------------------------------------------------------------------------
    boolean  AUTO = false;
    boolean  isChangedAuto = false;
    Statement Std = Con.createStatement();
    if ( isAutoCommit ){
      AUTO = Con.getAutoCommit(); //状态
    }

    if (Std != null) {
      try {
        Std.clearBatch();

        if ( isAutoCommit  && AUTO ){
          Con.setAutoCommit(false);
          isChangedAuto   = true;
        }

        int iSize = 0;
        int iSqlSize = Sqls.size();
        //加入删除SQL
        for (int i = 0; i < iSqlSize; i++) {
          String Sql = (String) (Sqls.get(i));

          Sql = pPlugin.onTranslateUpdateSQL(Con, Sql);

          Std.addBatch(JConvertSql.convertSql(Std, Sql ));
          iSize ++;

          if ( iSize == MAX_LINES ){
            /**
             * 批处理执行
             */
            Std.executeBatch();
            Std.clearBatch();
            iSize = 0;
          }
        }

        if ( iSize > 0 ){
          Std.executeBatch();
        }
        /**
         * 提交
         */
        if (isAutoCommit) {
          Con.commit();
        }
        //------------------------------------------------------------------------------------
        _MsgResult.CreateItem("SQLRESULT", "OK");

        return true;

      }
      catch (Exception E) {
        if (isAutoCommit) {
          Con.rollback();
        }
        _MsgResult.CreateItem("SQLRESULT", "");
        _MsgResult.CreateItem("MSG", E.getMessage());
        E.printStackTrace();
      }
      finally {
        Con.BackStatement(Std, null);
        if (isChangedAuto) {
          Con.setAutoCommit(AUTO);
        }
      }

    }
    return false;


  }

  /**
   * 客户端修改的数据提交
   * @param EditXmlInfo
   * @return
   * 这个方法是专门给客户端UPDATE纯静态用的。
   */
  public static boolean UpdateEdit(JConnection Con,IMessage _MsgInput ,IMessage _MsgResult ) {
    return UpdateEdit(Con,_MsgInput ,_MsgResult,true);
  }


  public static boolean UpdateEdit(JConnection Con,IUpdateInfo IInfo ,IMessage _MsgResult,boolean isAutoCommit){
    /**
     * 获得插件。
     */
    IDataExchangePlugin pPlugin = getPluginInstnace(Con);
    //设置插件。
    IInfo.setDataExchangePlugin(pPlugin);

    /**
     * 其它操作。
     */
    DBOUpdateRecall IRecall = new DBOUpdateRecall(Con);
    Vector  Sqls   = IInfo.BuildSqls(IRecall);
    if (Sqls.size() == 0) {
      _MsgResult.CreateItem("SQLRESULT", "OK");
      return true;
    }
    //------------------------------------------------------------------------------------
    //下面执行
    //------------------------------------------------------------------------------------

    Statement Std = Con.createStatement();
    boolean  AUTO = Con.getAutoCommit();          //状态
    boolean  isChangedAuto = false;

    if (Std != null) {
      try {
        Std.clearBatch();


        if ( isAutoCommit && AUTO ){
          Con.setAutoCommit(false);
          isChangedAuto  = true;
        }

        /**
         * 按批量提交SQL。
         */
        int iSize = 0;
        int iSqlSize = Sqls.size();
        //加入删除SQL
        for (int i = 0; i < iSqlSize; i++) {
          String Sql = (String) (Sqls.get(i));

          Sql = pPlugin.onTranslateUpdateSQL(Con, Sql);

          Std.addBatch(JConvertSql.convertSql(Std, Sql ));
          iSize ++;

          if ( iSize == MAX_LINES ){
            /**
             * 批处理执行
             */
            Std.executeBatch();
            Std.clearBatch();
            iSize = 0;
          }
        }

        if ( iSize > 0 ){
          Std.executeBatch();
        }

        /**
         * 提交
         */
        if ( isAutoCommit ) {
          Con.commit();
        }
        //------------------------------------------------------------------------------------
        _MsgResult.CreateItem("SQLRESULT", "OK");

        return true;

      }
      catch (Exception E) {
        if ( isAutoCommit ){
          Con.rollback();
        }
        _MsgResult.CreateItem("SQLRESULT", "");
        _MsgResult.CreateItem("MSG", E.getMessage());
        E.printStackTrace();
      }
      finally{
        Con.BackStatement(Std,null);
        if ( isChangedAuto ){
          Con.setAutoCommit(AUTO);
        }
      }

    }
    return false;

  }

  //另一种更新操作
  public static boolean UpdateEdit(JConnection Con,IUpdateInfo IInfo ,IMessage _MsgResult ) {
    return UpdateEdit(Con,IInfo , _MsgResult,true );
  }

  /**
    * 利用给定的参数编译出要可以直接运行的SQL数组。
    * @param IVars
    * @param IParamItems
    * @param strParam
    * @return
    */
   private static Vector CreatePreSql(IEAIVariableManager IVars,IMessage IParamItems,String strParam){
     Vector Sqls = new Vector(5);
     IEAIVariable[] vars = IVars.getVars();
     for ( int i =0; i < vars.length; i ++){
       String strTmp = vars[i].AsString();
       if ( strTmp != null && strTmp.length() > 0 ){
         //使用第一组参数编译。
         if ( IParamItems != null && IParamItems.getItemsCount() > 0 ){
           strTmp = TSqlCompiler.CompilerSql(strTmp,IParamItems );
         }
         //使用第二个参数编译。
         if ( strParam != null && strParam.length() > 0 ){
           strTmp = TSqlCompiler.CompilerSql(strTmp,strParam );
         }
         //加入返回结果中。
         Sqls.add( strTmp );
       }
     }
     return Sqls;
   }


   /**
    * 统一两个数据集定义
    * @param ISour
    * @param IDest
    */
   private static void ConsistentTo(IDataSetDefine ISour,IDataSetDefine IDest){
     if ( ISour == null || IDest == null ) return;

     int iSrcCount  = ISour.getFieldsDefine().FieldsCount();
     int iDestCount = IDest.getFieldsDefine().FieldsCount();

     for ( int iSrc = 0; iSrc < iSrcCount; iSrc++){
       //取出源定义中的第N个定义。
       IFieldDefine IFDSrc = ISour.getFieldsDefine().Field(iSrc);
       //按名称，在目的定义中查找定义。
       IFieldDefine IFDDest= IDest.getFieldsDefine().Field(IFDSrc.getFieldName());

       //如果目的定义中没有。
       if ( IFDDest == null ){
         //在目的区中加入。
         IDest.getFieldsDefine().FieldAdd( IFDSrc );
       }

     }

   }

   /**
    * 打开一个服务FORM,该FORM非实例型
    * @param SqlStr
    * @param ParameterXmlString
    * @return
    * 返回以下几个数据值
    * SQLRESULT : 首个数据集IDataExchange
    * DSDEFINE  : 数据集的定义格式XML文本
    * FORMDEFINE: 视的定义格式文本
    * 本方法不需要子类重载.
    *
    * 这个方法是专门给客户端打开纯静态的用的。
    */
   public static int OpenVirtualEx( JConnection Con,JParamObject ENV,String FName ,IMessage _MsgInput,IMessage _MsgResult,String strAppName) {
     /**
      * 本方法需要根据名称建立一个Formdefine,从中找出SQL,及定义信息,打开DS
      */
     String SQL           = "";

     //如果客户给定了SQL,就用客户的，否则使用XML内存的。
     String UserSql   = _MsgInput.asStringValue(SQL_NAME, "");
     String UserTable = _MsgInput.asStringValue(SQL_Table, "");
     String UserKeys  = _MsgInput.asStringValue(SQL_Keys,  "");

     SQL = UserSql;

     /**
      * 得到定义对象
      */
     TDataSetDefine   DSDefine = new TDataSetDefine();
     DSDefine.Prepare();
     DSDefine.setTableNames(new String[]{UserTable});

     TGridFormDefine  FMDefine = new TGridFormDefine();



     /**
      * 正式打开操作。
      */
     /**
      * DO SQL。在取得数据的同时，并取得全部的数据集定义接口。
      */
     int iCode = SQLQuery(Con,SQL,_MsgInput);


     /**
      * 获得格式定义串。
      */
     String strFormDefine = FMDefine.OutputToString();
     String strDSDefine   = DSDefine.OutputToString();

     /**
      * 设置返回的格式。
      * @param <any>
      */
     setResult(_MsgResult,strFormDefine,strDSDefine );

     return 1;

   }

}
