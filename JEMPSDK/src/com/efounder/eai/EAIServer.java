package com.efounder.eai;

import com.core.xml.*;
import java.util.*;
import com.efounder.eai.data.*;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2003</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class EAIServer {
  /**
   * protocol="https" host="" port="" path="" service="" DataBaseName="" DBNO=""
   * ���}��Ӧ�÷��������Ƿ���ͬһ�����\uFFFD
   * ���\uFFFD protocol,host,port,path,dbid������⼸����ȣ�����ͬһ��Ӧ�÷���
   */
  public static final String SERVER_ID       = "id";       // FinanceServer ReportServer BISServer etc.
  public static final String SERVER_CAPTION  = "caption";  // ����Ӧ�÷���,��׼Ӧ�÷���,����Ӧ�÷���
  public static final String SERVER_PROTOCOL = "protocol"; // https
  public static final String SERVER_HOST     = "host";     // 10.21.0.59
  public static final String SERVER_PORT     = "port";     // 9443
  public static final String SERVER_PATH     = "path";     // EnterpriseServer
  public static final String SERVER_SERVICE  = "service";  // ESPService EAIServer UpdateService etc....
  public static final String SERVER_DBID     = "DataBaseName";     // ESPData_001 = StorageID+ZTBH
  public static final String SERVER_ZTID     = "DBNO";     // 001,002,ac
  /**
   *
   */
  protected EAIServer() {
  }
  /**
   *
   * @param stub StubObject
   * @return String
   */
  public static String getEAIServerURI(StubObject stub) {
    String uri = stub.getString(SERVER_PROTOCOL,EAI.Product)+"://" +
        stub.getString(SERVER_HOST,EAI.Server)+":" +
        stub.getString(SERVER_PORT,EAI.Port)+"/" +
        stub.getString(SERVER_PATH,EAI.Path)+"/" +
        stub.getString(SERVER_SERVICE,"ESPService");
    return uri;
  }
  /**
   *
   * @param stub StubObject
   * @return String
   */
  public static String getEAIServerPATH(StubObject stub) {
    String uri = stub.getString(SERVER_PROTOCOL,EAI.Product)+"://" +
        stub.getString(SERVER_HOST,EAI.Server)+":" +
        stub.getString(SERVER_PORT,EAI.Port)+"/" +
        stub.getString(SERVER_PATH,EAI.Path)+"/";
    return uri;
  }
  /**
   *
   * @param stub StubObject
   * @param key String
   * @param def String
   * @return String
   */
  public static String getEAIServerAttrib(StubObject stub,String key,String def) {
    if ( stub == null ) return def;
    return stub.getString(key,def);
  }
  /**
   *
   * @param serverID String
   * @param key String
   * @param def String
   * @return String
   */
  public static String getEAIServerAttrib(String serverID,String key,String def) {
    StubObject stub = getEAIServer(serverID);
    return getEAIServerAttrib(stub,key,def);
  }
  /**
   *
   */
  protected static java.util.Map serverMap = new java.util.HashMap();
  /**
   *
   * @param serverID String
   * @return StubObject
   */
  public static StubObject getEAIServer(String serverID) {
    String DataBaseName = (String)JParamObject.getObject("DataBaseName",null);
    String DBNO = (String)JParamObject.getObject("DBNO",null);
    String serverKey = serverID;
    if ( DataBaseName != null && DBNO != null ) {
      serverKey = DataBaseName+"_"+DBNO+"_"+serverID;
    }
    StubObject so = (StubObject)serverMap.get(serverKey);
    if ( so == null ) so = (StubObject)serverMap.get(serverID);
    return so;
  }
  /**
   *
   * @param serverID String
   * @return StubObject
   */
  public static StubObject getEAIServerByServerID(String serverID) {
    return (StubObject)serverMap.get(serverID);
  }

  /**
   *
   * @return String[]
   */
  public static String[] getEAIServers() {
    Object[] arrays = serverMap.keySet().toArray();
    String[] server = new String[arrays.length];
    System.arraycopy(arrays, 0, server, 0, server.length);
    return server;
  }

  /**
   *
   * @param paramObject JParamObject
   * @param serverID String
   * @return StubObject
   */
  public static StubObject getEAIServer(JParamObject paramObject,String serverID) {
    String DataBaseName = (String)paramObject.getEnvValue("DataBaseName",null);
    String DBNO = (String)paramObject.getEnvValue("DBNO",null);
    String serverKey = serverID;
    if ( DataBaseName != null && DBNO != null ) {
      serverKey = DataBaseName+"_"+DBNO+"_"+serverID;
    }
    StubObject so = (StubObject)serverMap.get(serverKey);
    if ( so == null ) so = (StubObject)serverMap.get(serverID);
    return so;
  }
  /**
   *
   * @param serverID String
   * @param serverStub StubObject
   */
  public static void addServer(String serverID,StubObject serverStub) {
    serverMap.put(serverID,serverStub);
  }
  /**
   *
   * @param serverID String
   * @return StubObject
   */
  public static StubObject removeServer(String serverID) {
    return (StubObject)serverMap.remove(serverID);
  }
  /**
   *
   */
  public static void initEAIServer() {
    List serverList = PackageStub.getContentVector(EAIServer.class.getName());
    if ( serverList == null || serverList.size() == 0 ) return;
    StubObject serverStub = null;
    for(int i=0;i<serverList.size();i++) {
      serverStub = (StubObject)serverList.get(i);
      String serverKey = (String)serverStub.getID();
      // 如果loginDB不为空，则需要按它进行hash
      String loginDB = serverStub.getString("loginDB",null);
      if ( loginDB != null && loginDB.trim().length() > 0 ) {
        serverKey = loginDB+"_"+serverKey;
      }
      serverMap.put(serverKey,serverStub);
    }
    // ��ʼ������ֲ�����\uFFFD
    initDALObject_Server();
  }
  /**
   *
   */
  protected static String CLSID_AbstractDataActiveObjectCategory = "CLSID_AbstractDataActiveObjectCategory";
  /**
   *
   */
  protected static String STACK_AbstractDataActiveObjectCategory = "STACK_AbstractDataActiveObjectCategory";
  /**
   *
   */
  protected static void initStackObject_Server() {
    List stackObjectList = PackageStub.getContentVector(STACK_AbstractDataActiveObjectCategory);
    if ( stackObjectList == null || stackObjectList.size() == 0 ) return;
    StubObject serverStub = null;
    for(int i=0;i<stackObjectList.size();i++) {
      serverStub = (StubObject)stackObjectList.get(i);
      stackObjectMap.put(serverStub.getID(),serverStub.getString("server",""));
    }
  }
  /**
   *
   */
  protected static void initDALObject_Server() {
    // 先初始化堆栈方式的后台对�\uFFFD
    initStackObject_Server();
    List dalObjectList = PackageStub.getContentVector(CLSID_AbstractDataActiveObjectCategory);
    if ( dalObjectList == null || dalObjectList.size() == 0 ) return;
    StubObject serverStub = null;
    for(int i=0;i<dalObjectList.size();i++) {
      serverStub = (StubObject)dalObjectList.get(i);
      dalObjectMap.put(serverStub.getID(),serverStub.getString("server",""));
    }
  }
  /**
   *
   * @return Map
   */
  public static java.util.Map getStackObjectMap() {
    return stackObjectMap;
  }
  /**
   *
   */
  protected static java.util.Map stackObjectMap = new java.util.HashMap();
  /**
   *
   */
  protected static java.util.Map dalObjectMap = new java.util.HashMap();
  /**
   *
   * @param dalObjectID String
   * @return String
   */
  public static String getDALObjectServer(String dalObjectID) {
    return (String)dalObjectMap.get(dalObjectID);
  }
}
