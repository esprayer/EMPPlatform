package com.efounder.eai.service;

import javax.servlet.*;
import javax.servlet.http.*;

import com.core.servlet.*;
import com.efounder.eai.data.*;
import com.efounder.eai.service.util.*;
import com.core.xml.*;
import java.io.*;
import java.util.*;
import java.text.SimpleDateFormat;
import java.math.*;
import com.efounder.eai.EAI;

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
public class ESPServiceManager extends ServletServiceAdapter {
  /**
   *
   */
  public ESPServiceManager() {
  }

  /**
   * invokeFilter
   *
   * @param p0 ServletRequest
   * @param p1 ServletResponse
   * @param filterChain FilterChain
   * @return boolean
   * @todo Implement this com.core.servlet.EAIServiceManager method
   */
  public boolean invokeFilter(ServletRequest p0, ServletResponse p1,FilterChain filterChain) {
    return false;
  }
  /**
   *
   * @param request HttpServletRequest
   * @param httpServlet HttpServlet
   * @return Object
   * @throws Exception
   */
  public Object getRequestObject(HttpServletRequest request,HttpServlet httpServlet) throws Exception {
    return ESPServiceUtil.getRequestObject(request,httpServlet);
  }
  /**
   *
   * @param p0 HttpServletRequest
   * @param p1 HttpServletResponse
   * @throws Exception
   * @return boolean
   * @todo Implement this com.core.servlet.EAIServiceManager method
   */
  public boolean invokeService(HttpServlet httpServlet,HttpServletRequest request, HttpServletResponse response,boolean isLogin) throws Exception {
    Object requestObject = null;Object responseObject=null;
    if ( request.getContentLength() <= 0 ) {
      System.out.println("error request.getContentLength()");
      return false;
    }
    // 获取requestObject
    requestObject = getRequestObject(request,httpServlet);
    // 如果requestObject为空，则进行页面模式的处理
    if ( requestObject == null )
     {
         System.out.println("error requestObject == null");
       return false;
     }
    if ( !isLogin ) { // 如果是在没有登录状态下调用，需要检查此对象的方法是否在允许列表中
      // 如果不在允许列表中，则直接返回
      if ( !checkObjectMethod(ESPServiceUtil.getObjectName(requestObject),
                              ESPServiceUtil.getMethodName(requestObject)) ) return false;
    }
    String obj=ESPServiceUtil.getObjectName(requestObject);
    String med=ESPServiceUtil.getMethodName(requestObject);

    if("1".equals(ParameterManager.getDefault().getSystemParam("CALL_MONITOR"))){
         SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss SSS");
      String pTime = sdf.format(new Date());
      int Count=request.getContentLength();
      JVMStub jvm = (JVMStub) EAI.getEnv("JVMStub", null);
      String mess="";
      if(jvm!=null)
        mess = "JVM:"+ jvm.getHOST()+" "+jvm.getString("PID", "")+"@"+jvm.getString("SVR", "");
      if(obj.equals("BZServiceComponentManager")&&med.equals("syncRunService")){
         com.efounder.eai.data.JRequestObject req= (com.efounder.eai.data.JRequestObject)requestObject;
         if(req!=null){
             com.efounder.eai.data.JParamObject pp = ((com.efounder.eai.data.
                     JParamObject) req.ParamObject);
             if(pp!=null)med+=" "+pp.GetValueByEnvName("ServiceKey","");//把服务或表单的号打出来
         }
      }
      if(obj.equals("FormDesigner")&&med.equals("loadCombFormFormat")){
          com.efounder.eai.data.JRequestObject req= (com.efounder.eai.data.JRequestObject)requestObject;
          if(req!=null){
              com.efounder.eai.data.JParamObject pp = ((com.efounder.eai.data.
                      JParamObject) req.ParamObject);
              if(pp!=null)med+=" "+pp.GetValueByParamName("formIDs","");//把服务或表单的号打出来
          }
       }
      System.out.println(mess+" "+pTime+" "+obj + " " + med+" "+ Count + " BIT "+(Count/1024)+" KB" );
    }
    // 如果不为空，则进行ESP模式的处理
    responseObject = ESPServiceUtil.invokeObjectMethod(httpServlet,request,response,requestObject);
    // 将返回结果发送到客户羰
    ESPServiceUtil.putResponseObject(response,responseObject);
    return true;
  }
  /**
   *
   */
  protected final static String UnauthorizedWebInvoke = "UnauthorizedWebInvoke";
  /**
   *
   * @param objName String
   * @param mtdName String
   * @return boolean
   */
  protected boolean checkObjectMethod(String objName,String mtdName) {
    java.util.List uwiList = PackageStub.getContentVector(UnauthorizedWebInvoke);
    if ( uwiList == null || uwiList.size() == 0 ) return false;
    StubObject stub = null;
    for(int i=0;i<uwiList.size();i++) {
      stub = (StubObject)uwiList.get(i);
      if ( objName.equals(stub.getString("object",null)) &&
           mtdName.equals(stub.getString("method",null)) ) {
        return true;
      }
    }
    return false;
  }
  /**
   *
   * @param httpServlet HttpServlet
   * @param p0 HttpServletRequest
   * @param p1 HttpServletResponse
   * @param isLogin boolean
   * @throws Exception
   */
  public void StartMonitorService(HttpServlet httpServlet,HttpServletRequest p0, HttpServletResponse p1,boolean isLogin) throws Exception {
//    SessionUtil.incrMemCachedInvoke();
  }
  /**
   *
   * @param httpServlet HttpServlet
   * @param p0 HttpServletRequest
   * @param p1 HttpServletResponse
   * @return boolean
   * @throws Exception
   */
//  public LoginStub getLoginStub(HttpServlet httpServlet,HttpServletRequest p0, HttpServletResponse p1) throws Exception {
//    HttpSession httpSession = p0.getSession(false);
//    if ( httpSession == null ) return null;
//    LoginStub loginStub = (LoginStub)httpSession.getAttribute(LoginStub._LOING_STUB_);
//    return loginStub;
//  }
}
