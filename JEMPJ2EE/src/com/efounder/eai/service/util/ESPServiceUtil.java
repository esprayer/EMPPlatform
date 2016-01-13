package com.efounder.eai.service.util;

import javax.servlet.*;
import javax.servlet.http.*;

import com.efounder.eai.*;
import com.efounder.eai.data.*;
import com.efounder.service.comm.*;
import com.efounder.sql.*;
import jframework.foundation.classes.JActiveDComDM;
import java.util.*;

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
public class ESPServiceUtil {
  /**
   *
   */
  protected ESPServiceUtil() {
  }
  /**
   *
   * @param request HttpServletRequest
   * @param response HttpServletResponse
   * @return JRequestObject
   */
  public static Object getRequestObject(HttpServletRequest request,HttpServlet httpServlet) throws Exception {
    Object RO = null;ServletInputStream sis = null;
    try {
      // 获取InputStream
      sis = request.getInputStream();
      // 从httpRequest中获取请求对象
      if (sis != null) {
        RO = CommManager.getDefault().requestObject(sis);
      }
    } catch ( Exception e ) {
        e.printStackTrace();
      RO = null;
    }
    return RO;
  }
  /**
   *
   * @param response HttpServletResponse
   * @param RO JResponseObject
   * @throws Exception
   */
  public static void putResponseObject(HttpServletResponse response,Object RO) throws Exception {
    // 通过response对象返回数据
    ServletOutputStream os = response.getOutputStream();
    // CommManger用来通过HTTP/S进行通讯
    CommManager.getDefault().responseObject(os,RO);
    // 关闭流
    os.close();
  }
  /**
   *
   * @param requestObject Object
   * @return String
   */
  public static String getObjectName(Object requestObject) {
    
    // ESP方式的调用
    if ( requestObject instanceof com.efounder.eai.data.JRequestObject ) {
      return ((com.efounder.eai.data.JRequestObject)requestObject).ActiveObjectName;
    }
    return null;
  }
  /**
   *
   * @param requestObject Object
   * @return String
   */
  public static String getMethodName(Object requestObject) {
    // ESP方式的调用
    if ( requestObject instanceof com.efounder.eai.data.JRequestObject ) {
      return ((com.efounder.eai.data.JRequestObject)requestObject).ActiveObjectMethodName;
    }
    return null;
  }
  /**
   *
   * @param requestObject Object
   * @return JResponseObject
   */
  public static Object invokeObjectMethod(HttpServlet httpServlet,
                                          HttpServletRequest p0,
                                          HttpServletResponse p1,
                                          Object requestObject) {
    // FWK方式的调用

//    if ( requestObject instanceof jfoundation.bridge.classes.JRequestObject ) {
//      return invokeFWKObjectMethod(httpServlet,p0,p1,(jfoundation.bridge.classes.JRequestObject)requestObject);
//    }
    // ESP方式的调用
    if ( requestObject instanceof com.efounder.eai.data.JRequestObject ) {
      Object param = ((com.efounder.eai.data.JRequestObject)requestObject).ParamObject;
      if ( param instanceof com.efounder.eai.data.JParamObject ) {
        return invokeESPObjectMethod(httpServlet, p0, p1,
                                     (com.efounder.eai.data.JRequestObject)
                                     requestObject);
      }
    }
    return null;
  }
  /**
   *
   * @param RequestObject JRequestObject
   * @return Object
   */
  protected static Object invokeFWKObjectMethod(HttpServlet httpServlet,
                                                HttpServletRequest p0,
                                                HttpServletResponse p1,
                                                com.efounder.eai.data.JRequestObject RequestObject) {
//                                                jfoundation.bridge.classes.JRequestObject RequestObject) {
    Object responseObject = null;//jfoundation.dataobject.classes.JParamObject po = null;
    try {
//      if (RequestObject.ParamObject instanceof jfoundation.dataobject.classes.JParamObject) {
//        po = ((jfoundation.dataobject.classes.JParamObject)RequestObject.ParamObject);
//        po.setHttpServlet(httpServlet);//po.getEAIParamObject().setHttpServlet(httpServlet);
//        po.setHttpServletRequest(p0);//po.getEAIParamObject().setHttpServletRequest(p0);
//        po.setHttpServletResponse(p1);//po.getEAIParamObject().setHttpServletResponse(p1);
//        SessionUtil.loginStub2Param(p0,po.getEAIParamObject());
//
//      }
      System.out.println(RequestObject.ActiveObjectName+"-"+RequestObject.ActiveObjectMethodName);
      responseObject = JActiveDComDM.AbstractDataActiveFramework.
          InvokeObjectMethod(RequestObject.ActiveObjectName,
                             RequestObject.ActiveObjectMethodName,
                             RequestObject.ParamObject,
                             RequestObject.DataObject,
                             RequestObject.CustomObject,
                             RequestObject.AdditiveObject);
    } catch ( Exception ex ) {
      if ( responseObject == null )
        responseObject = new JResponseObject(null,JResponseObject.RES_ERROR,new com.efounder.eai.data.EAServerException(ex));
      ex.printStackTrace();
    } finally {

    }
    return responseObject;
  }
  /**
   *
   * @param requestObject JRequestObject
   * @return Object
   */
  protected static Object invokeESPObjectMethod(HttpServlet httpServlet,
                                                HttpServletRequest p0,
                                                HttpServletResponse p1,
                                                com.efounder.eai.data.JRequestObject requestObject) {
    Object responseObject = null;com.efounder.eai.data.JParamObject po = null;
    try {
      // PO中设置上当前的Servlet
      if ( requestObject.ParamObject instanceof com.efounder.eai.data.JParamObject ) {
        po = ((com.efounder.eai.data.JParamObject)requestObject.ParamObject);
        //是不是要在客户端获得SQL,如果是1就创建一个list
        //在生成jconnection时，把这个list放到jconnectoin里
        //执行SQL时加到list里 多线程的功能可能无效，因为后面接着清除
        if(po.GetValueByParamName("$$SQL_VIEW","").equals("1")){
          java.util.List list=new java.util.ArrayList();
          po.setValue("$$SQL_VIEWLIST",list);
        }
        po.setHttpServlet(httpServlet);
        po.setHttpServletRequest(p0);
        po.setHttpServletResponse(p1);
//        SessionUtil.loginStub2Param(p0,po);// 将Session中的值copy到PO中
      }
      responseObject = EAI.DAL.InvokeObjectMethod(
          requestObject.ActiveObjectName,
          requestObject.ActiveObjectMethodName,
          requestObject.ParamObject,
          requestObject.DataObject,
          requestObject.CustomObject,
          requestObject.AdditiveObject);
    } catch ( Exception ex ) {
      if ( responseObject == null )
        responseObject = new JResponseObject(null,JResponseObject.RES_ERROR,new com.efounder.eai.data.EAServerException(ex));
      ex.printStackTrace();
    } finally {
     if(po!=null){
         List sqlList = (List) po.getValue("$$SQL_VIEWLIST", null);
         if (sqlList != null && responseObject instanceof JResponseObject) {
             ((JResponseObject) responseObject).setResponseObject(
                     "$$SQL_VIEWLIST",
                     sqlList);
         }
     }
      if ( requestObject.ParamObject instanceof JParamObject ) {
        closeConnection((JParamObject)requestObject.ParamObject);
      	// 后台功能执完后，清除有关WebInfo的设置，多线程的功能需要注意
      	po.setHttpServlet(null);//po.getEAIParamObject().setHttpServlet(httpServlet);
      	po.setHttpServletRequest(null);//po.getEAIParamObject().setHttpServletRequest(p0);
      	po.setHttpServletResponse(null);//po.getEAIParamObject().setHttpServletResponse(p1);
      }
    }
    return responseObject;
  }
  /**
   *
   * @param PO JParamObject
   */
  static public void closeConnection(JParamObject PO) {
    if ( PO == null ) return;
    // 如果不是自动管理，则返回
//    if ( !PO.isAutoConnection() ) return;
    // 关闭通过此PO创建的所有连接
//    java.util.List connList = PO.getConnList();
//    if ( connList == null ) return;
//    Object o = null;JConnection conn = null;
//    for(int i=0;i<connList.size();i++) {
//      o = connList.get(i);
//      if ( o != null && o instanceof JConnection ) {
//        conn = (JConnection)o;
//        // 如果还没有关闭，则直接关闭
//        if ( !conn.isClosed() ) conn.close();
//      }
//    }
  }
}
