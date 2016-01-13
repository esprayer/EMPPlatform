package com.efounder.eai.service.security;

import java.util.*;

import javax.swing.*;

import com.core.xml.*;
import com.efounder.buffer.DataBufferManager;
import com.efounder.db.DBTools;
import com.efounder.eai.*;
import com.efounder.eai.data.*;
import com.efounder.eai.ide.*;
import com.efounder.eai.service.*;
import com.efounder.service.security.SecurityContext;
import com.efounder.service.security.ServiceSecurityManager;


/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

abstract public class EAISecurityService extends ServiceSecurityManager {
  /**
   *
   */
  public EAISecurityService() {
  }
  /**
   *
   * @param p1 Object
   * @param p2 Object
   * @param p3 Object
   * @param p4 Object
   * @return Object
   */
  public Object DoLogoff(Object p1, Object p2, Object p3, Object p4) throws Exception {
    return null;
  }
  /**
   *
   * @param p1 Object
   * @param p2 Object
   * @param p3 Object
   * @param p4 Object
   * @return Object
   */
  public Object Logoff(Object p1, Object p2, Object p3, Object p4) throws Exception {
    JParamObject paramObject = JParamObject.Create();
    java.util.List list = this.getAllLogList();
    return EAI.DAL.IOM("SecurityObject","LogoffProduct", paramObject);
  }

  /**
   *
   * @param paramObject JParamObject
   * @return Object
   * @throws Exception
   */
  public JResponseObject loginProduct(JParamObject paramObject,Object callObject) throws Exception {
    // 在客户端，设置记忆DataBufferManager
    DataBufferManager.setRememberDBM(true);
    String Product = paramObject.GetValueByEnvName("Product");
    setLoginMessage(ExplorerIcons.ICON_ADD_CUSTOM_VIEW,"开始个性化登录！",callObject);
    // 开始登录过程
//    EAI.DOF.IOM(Product,"startLoginProduct",paramObject);
    JResponseObject response = null;
    // 登录过程，必须设置WebLogin=no的参数
    Map requestHeader = new java.util.HashMap();
    // 设置requestHeader
    requestHeader.put(com.efounder.eai.WebInvoke._NO_LOGIN_,"no");
    // 调用后台的登录过程
    setLoginMessage(ExplorerIcons.ICON_ADD_CUSTOM_VIEW,"执行通用登录过程！",callObject);
    //    
    if(paramObject.GetValueByEnvName("webInvoke", "0").equals("1")) {
    	response = (JResponseObject) EAI.DAL.webInvoke("SecurityObject", "loginProduct", paramObject, requestHeader);
    } else {
        response = (JResponseObject) EAI.DAL.IOM("SecurityObject", "loginProduct", paramObject, requestHeader);
    }
    if(response.getErrorCode()!=1){
      initAppEnv(paramObject,response);//可能也得初始
      return response;
    }
    // 初始化应用环境
    setLoginMessage(ExplorerIcons.ICON_ADD_CUSTOM_VIEW,"初始化应用环境！",callObject);
    initAppEnv(paramObject,response);
    // 当前登当产品loginProduct过程
    setLoginMessage(ExplorerIcons.ICON_ADD_CUSTOM_VIEW,"当前产品"+Product+"登录过程！",callObject);
//    EAI.DOF.IOM(Product,"loginProduct",paramObject,response);
    // JParamObject中必有的项
    setLoginMessage(ExplorerIcons.ICON_ADD_CUSTOM_VIEW,"初始化系统参数！",callObject);
    initParamObject(paramObject);
    // 设置使用单位个性化serverID
    setUnitServer();
    // 装入业务模型
    loadBIZModel(callObject);
    setLoginMessage(ExplorerIcons.ICON_ADD_CUSTOM_VIEW,"登录成功！",callObject);
    return response;
  }

  /**
   *
   * @param callObject Object
   * @throws Exception
   */
  protected void loadBIZModel(Object callObject) throws Exception {
//      String mdlid=ParameterManager.getDefault().getDefaultModelName();
//      JParamObject po = JParamObject.Create();
//      ESPContext espContext =espContext=ESPClientContext.getInstance(po);
//      try {
//          BIZMetaData bizMetaData = (BIZMetaData) MetaDataManager.
//              getBIZMetaDataManager().getMetaData(espContext,mdlid );
//          EAI.putEnv(JParamObject.DEF_BIZMODEL,bizMetaData);
//      }
//      catch (Exception ex) {
//          ex.printStackTrace();
//      }
//      StubObject stub = null;
//      java.util.List list = PackageStub.getContentVector("BSCONF_Load");
//      for(int i=0;list!=null&&i<list.size();i++) {
//    	  stub = (StubObject)list.get(i);
//    	  String server=stub.getString("eaiServer","");
//    	  po = JParamObject.Create();
//          po.setEAIServer(server);
//          List resList=DBTools.SimpleQuery(po, "BSCONF", "*", "1=1");
//          if(resList==null)continue;
//          Map map=new HashMap();
//          for(int k=0;k<resList.size();k++){
//        	  stub = (StubObject)resList.get(k);
//        	  map.put(stub.getString("F_VKEY", ""), stub.getString("F_VAL", ""));
//          }
//          po.setObject(server+"BSCONF", map);
//      }
//      
//      java.util.List modelList = PackageStub.getContentVector("BIZModelList");
//      if ( modelList == null || modelList.size() == 0 ) return;
//      setLoginMessage(ExplorerIcons.ICON_ADD_CUSTOM_VIEW,"准备装入业务模型！",callObject);
//    
//      for(int i=0;i<modelList.size();i++) {
//          stub = (StubObject)modelList.get(i);
//          setLoginMessage(ExplorerIcons.ICON_ADD_CUSTOM_VIEW,"装入"+stub.getID()+":"+stub.getCaption()+"！",callObject);
//          po = JParamObject.Create();
//          po.setEAIServer(stub.getString("eaiServer",null));
//          espContext = ESPClientContext.getInstance(po);
//          try {
//              BIZMetaData bizMetaData = (BIZMetaData) MetaDataManager.getBIZMetaDataManager().getMetaData(espContext, stub.getID().toString());
//          } catch ( Exception ex ) {
//              ex.printStackTrace();
//          }
//      }
  }

  /**
   *
   * @param icon Icon
   * @param message String
   * @param callObject Object
   */
  protected void setLoginMessage(Icon icon,String message,Object callObject) {
    if ( callObject == null ) return;
    JBOFClass.CallObjectMethod(callObject,"setLoginMessage",icon,message);
  }
  /**
   * JParamObject中必有的项
   * @param po JParamObject
   */
  protected void initParamObject(JParamObject po) {
    JParamObject no = new JParamObject();
    // 登录时间
    no.SetValueByEnvName("dbOwner",po.GetValueByEnvName("dbOwner"));
    no.SetValueByEnvName("LoginDate",po.GetValueByEnvName("LoginDate"));
    // 用户名,存储ID,账套ID属于必填项
    no.SetValueByEnvName("UserName",po.GetValueByEnvName("UserName"));
    //在这放CAPTION
    no.SetValueByEnvName("DataBaseName",po.GetValueByEnvName("DataBaseName"));
    no.SetValueByEnvName("DBNO",po.GetValueByEnvName("DBNO"));
    no.SetValueByEnvName("Language",EAI.getLanguage());
    no.SetValueByEnvName("Product",EAI.Product);
    no.SetValueByEnvName("International",po.GetValueByEnvName("International","zh"));
    no.SetValueByEnvName("Country",po.GetValueByEnvName("Country","CN"));
    JParamObject.assign(no);
  }
  /**
   *
   * @param response JResponseObject
   * @throws Exception
   */
  protected void initAppEnv(JParamObject paramObject,JResponseObject response) throws Exception {
    // 设置BSCONF
    StubObject envMap = (StubObject)response.getResponseObject("BSCONF");
    if ( envMap != null ) {
      // 设置到数据库环境变量中
//      DBConfigManager.setValueMap(paramObject,envMap.getStubTable());
      EAI.assginEnvMap(envMap.getStubTable()); //EAI.putEnv("BSCONF",envMap);
    }
    // 设置UserInfo
    envMap = (StubObject)response.getResponseObject("UserInfo");
    if ( envMap != null ) {
      EAI.putEnv("UserInfo", envMap);
      EAI.assginEnvMap(envMap.getStubTable());
      String UNIT_ID = envMap.getString("UNIT_ID",null);
      JParamObject.setObject("UNIT_ID",UNIT_ID);
      JParamObject.setObject("UserCaption",envMap.getString("USER_NAME",""));
    }
    // 产品信息初始化
    envMap = (StubObject)response.getResponseObject("ProductEnv");
    if ( envMap != null ) EAI.assginEnvMap(envMap.getStubTable());
    // 处理安全上下文环境
    SecurityContext sc = (SecurityContext)response.getResponseObject("SecurityContext");
    if ( sc != null ) initSecurityContext(sc);
    Long longTime = (Long)response.getResponseObject("timeBeginServer");
    if ( longTime != null ) {
      this.timeBeginServer = longTime.longValue();
      this.timeBeingClient = System.currentTimeMillis();
    } else {
      timeBeginServer = System.currentTimeMillis();
      timeBeingClient = timeBeginServer;
    }
    // 帐套信息
    JParamObject no = new JParamObject();
    //modified by lchong
//    no.getEnvRoot().putAll(EAI.getEnvMap());
    //modified end
    no.SetValueByEnvName("DataBaseName",paramObject.GetValueByEnvName("DataBaseName"));
    no.SetValueByEnvName("DBNO",paramObject.GetValueByEnvName("DBNO"));
    JParamObject.assign(no);

  }

  /**
   *
   * @param UNIT_ID String
   */
  protected void setUnitServer() {
//    if (EAI.getEnv("UserInfo", null) == null)
//      return;
//    StubObject userInfo = (StubObject)EAI.getEnv("UserInfo", null);
//    String UNIT_ID = userInfo.getString("UNIT_ID",null);
//    if (UNIT_ID == null || UNIT_ID.trim().length() == 0)
//      return;
//    String[] servers = EAIServer.getEAIServers();
//    for (int i = 0; servers != null && i < servers.length; i++) {
//      UnitServer.setUnitEAIServer(servers[i], UNIT_ID);
//    }
  }

  /**
	*
 	*/
  protected SecurityContext securityContext = null;
 
  /**
   *
   * @param p1 Object
   * @param p2 Object
   * @param p3 Object
   * @param p4 Object
   * @return Object
   */
  public Object Login(Object p1, Object p2, Object p3, Object p4)  throws Exception  {
    
    //
    getHostInfo();
    //
    DoLogin(p1,p2,p3,p4);

	// initApplication
    initApplication();
//    initSecurityContext(securityContext);
//    initProductEnv(securityContext);

    return null;
  }
  /**
   *
   */
  protected void initApplication() {
    JParamObject paramObject = JParamObject.Create();
    JResponseObject RO = null;
    paramObject.SetValueByEnvName("Product",EAI.Product);
    try {
      // 开始当前登录产品的初始化
      EAI.DOF.IOM(EAI.Product,"startInitProduct",paramObject);
      Map requestHeader = new java.util.HashMap();
//      requestHeader.put(com.efounder.eai.WebInvoke._NO_LOGIN_,"no");
      // 调用后台初始化过程
      if(paramObject.GetValueByEnvName("webInvoke", "0").equals("1")) {
    	  RO = (JResponseObject) EAI.DAL.webInvoke("SecurityObject", "initApplication", paramObject, requestHeader);
      } else {
    	  RO = (JResponseObject) EAI.DAL.IOM("SecurityObject", "initApplication", paramObject, requestHeader);
      }
      
      if ( RO == null ) return;
      // 公用初始化过程
      procPackage(RO,"PackageClient");
      procPackage(RO,"PackagePublic");
      procDatabase(RO);
      ParameterManager.getDefault().initParameterManager();
      // 正式初始化当前登录产品
      EAI.DOF.IOM(EAI.Product,"initApplication",paramObject,RO);
      EAIServer.initEAIServer();
    } catch ( Exception ex ) {
      ex.printStackTrace();
    }
  }
  /**
   *
   * @param RO JResponseObject
   */
  protected static void procDatabase(JResponseObject RO) {
//    java.util.List packList = (List)RO.getResponseObject("DatabaseList");
//    JDOFDBManagerObject.setDataBaseList(packList);
  }
  /**
   *
   * @param RO JResponseObject
   * @param key String
   */
  protected static void procPackage(JResponseObject RO,String key) {
    java.util.List packList = (List)RO.getResponseObject(key);
    if ( packList == null ) return;
    for(int i=0;i<packList.size();i++) {
      String value = (String)packList.get(i);
      PackageStub.initPackage(value);
    }
  }
  
  /**
   *
   * @param securityContext SecurityContext
   */
  protected void initProductEnv(SecurityContext securityContext) {
    if ( securityContext == null ) return;
    JParamObject PO = (JParamObject)securityContext.getObject("JParamObject",null);
    securityContext.setObject("JParamObject",null);
    if ( PO == null ) return;
    java.util.Map productEnvMap = (java.util.Map)PO.getValue("ProductEnv",null);
    if ( productEnvMap != null ) {
      EAI.assginEnvMap(productEnvMap);
    }
  }
  
  /**
   *
   */
  protected void getHostInfo() {
    try {
      java.net.InetAddress LocalAddress = java.net.InetAddress.getLocalHost();
      String IP = LocalAddress.getHostAddress();
      String HostName = LocalAddress.getHostName();
      JParamObject.setObject("HostAddress", IP);
      JParamObject.setObject("HostName", HostName);
      JParamObject.setObject("Application",EAI.Application);
      JParamObject.setObject("Product",EAI.Product);
    } catch ( Exception e ) {
      e.printStackTrace();
    }
  }

  /**
   *
   * @param securityContext SecurityContext
   */
  private void initSecurityContext(SecurityContext securityContext) {
	  this.securityContext = securityContext;
  }
 
  /**
   *
   * @return Object
   */
  public Object getContext() {
	  return securityContext;
  }
 
  /**
   *
   * @return Object
   */
  public Object initContext() {
    return null;
  }

  /**
   * 检查菜单权限
   * @param UserName String
   * @param Key String
   * @param Value String
   * @throws Exception
   * @return boolean
   */
  public boolean checkPermission(String UserName, String Key, String Value) {
	java.util.List functionList = null;
    if ( securityContext != null ) {
      functionList = securityContext.getUserFunctionList(UserName);
      if ( functionList != null )
        return functionList.indexOf(Key)==-1?false:true;
    }
    return true;
  }
  /**
   *
   * @param Key String
   * @param Value String
   * @param index int
   * @return boolean
   * @throws Exception
   */
  public boolean getAccessController1(String Key,String Value,int index) throws Exception {
    JParamObject PO = JParamObject.Create();Integer Index = new Integer(index);
    JResponseObject RO = (JResponseObject)EAI.DAL.InvokeObjectMethod("SecurityObject","getAccessController1",PO,Key,Value,Index);
    return ( RO == null || RO.getErrorCode() == 1 );
  }
  /**
   *
   * @param Key String
   * @param index int
   * @return String
   * @throws Exception
   */
  public String getAccessController2(String Key,int index) throws Exception  {
    JParamObject PO = JParamObject.Create();Integer Index = new Integer(index);
    JResponseObject RO = (JResponseObject)EAI.DAL.InvokeObjectMethod("SecurityObject","getAccessController2",PO,Key,null,Index);
    String sql = RO == null?" (1=1) ":RO.getResponseObject().toString();
    return sql;
  }
  public Map getAccessControllerMap(JParamObject PO,String qxbh){
    try {
      JResponseObject RO = (JResponseObject) EAI.DAL.IOM("SecurityObject",
          "getUserDataLimit", PO, qxbh);
      return (Map) RO.getResponseObject();
    }
    catch (Exception ex) {
      ex.printStackTrace();
      return new HashMap();
    }
  }
  /**
   *
   * @param Key String
   * @param index int
   * @return String
   * @throws Exception
   */
  public String getAccessController3(String Key,int index) throws Exception  {
    JParamObject PO = JParamObject.Create();Integer Index = new Integer(index);
    JResponseObject RO = (JResponseObject)EAI.DAL.InvokeObjectMethod("SecurityObject","getAccessController3",PO,Key,null,Index);
    String sql = RO == null?" (1=1) ":RO.getResponseObject().toString();
    return sql;
  }

}
