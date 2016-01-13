package com.efounder.eai.service;

import java.net.*;
import java.util.*;

import com.core.xml.*;
import com.efounder.eai.*;
import com.efounder.eai.data.*;

public class CommUtil {
  protected CommUtil() {
  }

  /**
   * ServerID的确定顺序：
   * 1.由ParamObject指定服务ID
   * 2.服务ID.对象ID
   * 3.在Package中定义的服务ID
   * @param paramObject Object
   * @param objectName String
   * @return String
   */
  public static String getObjectOnServer(Object paramObject, String objectName) {
    String serverID = null;
    // 新PO中设置的EAIServer优先级最高
//    if (paramObject instanceof com.efounder.eai.data.JParamObject) {
//      serverID = ( (JParamObject) paramObject).getEAIServer(null);
//      if (serverID != null && serverID.trim().length() > 0 ) {
//        return serverID;
//      }
//    }
//    // 旧PO中设置的Server优先级最高
//    if ( paramObject instanceof JParamObject ) {
//      serverID = ((JParamObject) paramObject).GetValueByParamName("Server",null);
//      if (serverID != null && serverID.trim().length() > 0 ) {
//        return serverID;
//      }
//      // 旧的新PO中的值
//      serverID = ( (JParamObject) paramObject).getEAIParamObject().getEAIServer(null);
//      //
//      if (serverID != null && serverID.trim().length() > 0 ) {
//        return serverID;
//      }
//    }
    // 对象名中设置的EAIServer次之
    if (objectName.indexOf(".") != -1) {
      serverID = objectName.substring(0, objectName.indexOf("."));
    }
    else {
      // Package XML中定义的最低
      serverID = EAIServer.getDALObjectServer(objectName);
      if ( serverID == null ) {
        serverID = getServerIDFromStack(objectName);
      }
    }
    if (serverID != null && serverID.trim().length() > 0 ) {
      return serverID;
    }
    return null;
  }
  /**
   *
   * @param objectName String
   * @return String
   */
  protected static String getServerIDFromStack(String objectName) {
    StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
    if ( stackTraceElements == null || stackTraceElements.length == 0 ) return null;
    String className = null;String serverID = null;
    for (int i = 0; i < stackTraceElements.length; i++) {
      className = stackTraceElements[i].getClassName();
      serverID = getServerByClassInfo(className);
      if ( serverID != null && serverID.trim().length() > 0 ) return serverID;
    }
    return null;
  }
  /**
   *
   * @param className String
   * @return String
   */
  protected static String getServerByClassInfo(String className) {
    java.util.Map stackObjectMap = EAIServer.getStackObjectMap();
    if ( stackObjectMap == null || stackObjectMap.size() == 0 ) return null;
    Object[] packIDs = stackObjectMap.keySet().toArray();
    for(int i=0;i<packIDs.length;i++) {
      if (className.indexOf(packIDs[i].toString()) != -1) {
        return (String)stackObjectMap.get(packIDs[i]);
      }
    }
    return null;
   }
  /**
   *
   * @param paramObject Object
   * @param objectName String
   * @param defUrl URL
   * @return URL
   * @throws Exception
   */
  private static URL getURL(Object paramObject, String objectName, URL defUrl,String serverName) throws Exception {
    URL url = null;
//    String serverName = CommUtil.getObjectOnServer(paramObject, objectName);

    com.efounder.eai.data.JParamObject PO = null;
    String uri = null;
//    if ( paramObject instanceof jfoundation.dataobject.classes.JParamObject ) {
//      PO = ((jfoundation.dataobject.classes.JParamObject)paramObject).getEAIParamObject();
//    }
//    if (paramObject instanceof com.efounder.eai.data.JParamObject) {
//      PO = (com.efounder.eai.data.JParamObject) paramObject;
//    }
//    //add by lchong
//    if(PO == null && paramObject instanceof String){
//        PO = (new jfoundation.dataobject.classes.JParamObject((String)paramObject)).getEAIParamObject();
//    }
    PO = (com.efounder.eai.data.JParamObject) paramObject;
    //add end
    // 先获取PO中的ServiceURL，如果设置了它，就用，优先级最高
    uri = PO.getServiceURL();
    if ( uri != null && uri.trim().length() != 0 ) {
      url = new URL(uri);
      return url;
    }
    // 如果ServerName为空，则使用默认的连接，在后台，ServerName肯定不为空
    if (serverName == null || serverName.trim().length() == 0) {
      return defUrl;
    }
    else {
      StubObject serverStub = EAIServer.getEAIServer(serverName);
      if (serverStub == null) {
        return defUrl;
      }
      // 返回指定Server的URI
      uri = EAIServer.getEAIServerURI(serverStub);
      // 如果是老的PO，则用它的EAIParamObject属性
//      if ( paramObject instanceof jfoundation.dataobject.classes.JParamObject ) {
//        paramObject = ((jfoundation.dataobject.classes.JParamObject)paramObject).getEAIParamObject();
//      }
      if (paramObject instanceof com.efounder.eai.data.JParamObject) {
        com.efounder.eai.data.JParamObject po = (com.efounder.eai.data.JParamObject) paramObject;
        // 设置相应的数据库设置
        String dbset = serverStub.getString(EAIServer.SERVER_DBID, null);
        if (dbset != null && dbset.trim().length() > 0) {
          po.SetValueByEnvName("DataBaseName", dbset);
        }
        dbset = serverStub.getString(EAIServer.SERVER_ZTID, null);
        if (dbset != null && dbset.trim().length() > 0) {
          po.SetValueByEnvName("DBNO", dbset);
        }
      }
      url = new URL(uri);
      return url;
    }
  }
  /**
   *
   * @param Param Object
   * @param objectName String
   * @return String
   */
  private static String getObjectName(Object Param, String objectName) {
    if (objectName.indexOf(".") != -1) {
      return objectName.substring(objectName.indexOf(".") + 1);
    }
    else {
      return objectName;
    }
  }
  /**
   *
   * @param sender Object
   * @param ObjectName String
   * @param MethodName String
   * @param Param Object
   * @param Data Object
   * @param c Object
   * @param a Object
   * @param defUrl URL
   * @return Object
   * @throws Exception
   */
  public static Object frameworkCall(Object sender, String ObjectName,
                                     String MethodName,
                                     Object Param, Object Data,
                                     Object c, Object a, URL defUrl) throws
      Exception {
    // 获取后台ServiceID
    String serverName = CommUtil.getObjectOnServer(Param, ObjectName);
    // 获取Param,有可能会被Clone
    Param = getParamObject(serverName,Param,Data);
    // 获取URL
    URL url = CommUtil.getURL(Param, ObjectName, defUrl,serverName);
    // 获取对象名称
    ObjectName = getObjectName(Param, ObjectName);
    // 调用方法
    return RemoteMethodInvoke.remoteFWKInvoke(sender, url, ObjectName,
                                              MethodName, Param, Data, c, a,serverName);
  }
  /**
   *
   * @param sender Object
   * @param ObjectName String
   * @param MethodName String
   * @param Param Object
   * @param Data Object
   * @param c Object
   * @param a Object
   * @param defUrl URL
   * @return Object
   * @throws Exception
   */
  public static Object OnlineCall(Object sender, String ObjectName,
                                  String MethodName, Object Param,
                                  Object Data, Object c, Object a, URL defUrl) throws Exception {
    String serverName = CommUtil.getObjectOnServer(Param, ObjectName);
    // 获取Param,有可能会被Clone
    Param = getParamObject(serverName,Param,Data);
    // 如果是跨服务器调用，并且Param是JParamObject，则需要Clone JParamObject
    URL url = CommUtil.getURL(Param, ObjectName, defUrl,serverName);
    // 获取对象名称
    ObjectName = getObjectName(Param, ObjectName);
    // 调用方法
    return RemoteMethodInvoke.remoteESPInvoke(sender, url, ObjectName,
                                              MethodName, Param, Data, c, a, null,serverName);
  }
  protected static Object getParamObject(String serverName,Object param,Object data) {
    if ( serverName == null ) return param;
    // 如果新的JParamObject
    param = cloneParamObject(param);
//    if ( param instanceof com.efounder.eai.data.JParamObject ) {
//      if ( data instanceof RequestDataSetData ) {
//        ((RequestDataSetData)data).setExtentObject(param);
//      }
//    }
    return param;
  }
  /**
   *
   * @param param JParamObject
   * @return JParamObject
   */
  private static Object cloneParamObject(Object param) {
//    if ( param instanceof com.efounder.eai.data.JParamObject ) {
//      Object po = ((com.efounder.eai.data.JParamObject)param).clone();
//      return po;
//    }
//    if ( param instanceof jfoundation.dataobject.classes.JParamObject ) {
//      Object po = ((jfoundation.dataobject.classes.JParamObject)param).clone();
//      return po;
//    }
    //modified by lchong
    return param;
//    return null;
    //modified end
  }
  /**
   *
   * @param sender Object
   * @param ObjectName String
   * @param MethodName String
   * @param Param Object
   * @param Data Object
   * @param c Object
   * @param a Object
   * @param requestHeader Map
   * @param defUrl URL
   * @return Object
   * @throws Exception
   */
  public static Object webCall(Object sender, String ObjectName,
                               String MethodName, Object Param,
                               Object Data, Object c, Object a,
                               Map requestHeader, URL defUrl) throws Exception {

    String serverName = CommUtil.getObjectOnServer(Param, ObjectName);
    // 获取Param,有可能会被Clone
    Param = getParamObject(serverName,Param,Data);
    // 如果是跨服务器调用，并且Param是JParamObject，则需要Clone JParamObject
    URL url = CommUtil.getURL(Param, ObjectName, defUrl,serverName);
    // 获取对象名称
    ObjectName = getObjectName(Param, ObjectName);

//    URL url = CommUtil.getURL(Param, ObjectName, defUrl,null);
    return RemoteMethodInvoke.remoteESPInvoke(sender, url, ObjectName,
                                              MethodName, Param, Data, c, a,
                                              requestHeader,serverName);
  }
}
