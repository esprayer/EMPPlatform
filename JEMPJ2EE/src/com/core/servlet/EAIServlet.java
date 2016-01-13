package com.core.servlet;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

import com.efounder.service.Request;
import com.efounder.service.RequestProcessor;

import java.util.Enumeration;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class EAIServlet extends HttpServlet {
  /**
   * 初始化对象
   */
  public static InitBootObject initBootObject = null;
  /**
   *
   */
  static {
    initBootObject();
  }
  /**
   * 此方法是同步的，只允许执行一次
   */
  public synchronized static void initBootObject() {
    try {
      if ( initBootObject == null ) {
        initBootObject = InitBootObject.getDefault();
        initBootObject.initBootObject();
      }
    } catch ( Exception e ) {
      e.printStackTrace();
    }
  }
  /**
   *
   */
  protected ServletServiceManager eaiServiceManager = null;
  /**
   *
   * @throws ServletException
   */
  public void init(ServletConfig config) throws ServletException {
    // 设置当前Servlet线程的ClassLoader
//    initBootObject();
//    InitBootObject.initClassLoader();
    servletName = config.getServletName();
    initESPServiceManager(config.getServletName());
    super.init(config);
  }
  protected String servletName = null;
  /**
   *
   */
  public void destroy() {
    if ( eaiServiceManager != null ) {
      eaiServiceManager.destroy();
    }
    super.destroy();
  }
  /**
   * 初始化ESPServiceManager
   */
  protected void initESPServiceManager(String servletName) {
    // 初始化ESPServiceManager
    if ( eaiServiceManager == null ) {
      //System.err.println(servletName+":"+"prepare initESPServiceManager");
      // 根据ServletName获取相应的ESPServiceMnager
      eaiServiceManager = ServletServiceManager.getInstance(servletName);
      //System.err.println(servletName+":"+"prepare initESPServiceManager"+eaiServiceManager.toString());
    }
  }
  /**
   *
   */
  public EAIServlet() {
    super();
  }
  protected void printRequest(HttpServletRequest p0) throws Exception {
//    Enumeration enumer = p0.getAttributeNames();
//    if ( enumer == null ) return;
//    while ( enumer.hasMoreElements() ) {
//      String key = enumer.nextElement().toString();
//      //System.err.println(servletName+":"+"request key="+key+" value="+p0.getHeader(key));
//    }
  }
  /**
   *
   */
  protected static boolean monitorService = true;
  /**
   *
   * @return boolean
   */
  public static boolean isMonitorService() {
    return monitorService;
  }
  /**
   *
   * @param b boolean
   */
  public static void setMonitorService(boolean b) {
    monitorService = b;
  }
  /**
   *
   * @param p0 HttpServletRequest
   * @param p1 HttpServletResponse
   * @throws ServletException
   * @throws IOException
   */
  protected void service(HttpServletRequest p0, HttpServletResponse p1) throws ServletException, IOException {
    // 设置ClassLoader
    InitBootObject.initClassLoader();

    super.service(p0,p1);
  }
  /**
   *
   * @param p1 HttpServletResponse
   * @throws ServletException
   * @throws IOException
   */
  protected void dontInvoke(HttpServletRequest p0,HttpServletResponse p1) throws ServletException, IOException {
    p1.sendError(p1.SC_UNAUTHORIZED,"no login!");
  }
  /**
   *
   * @param p0 HttpServletRequest
   * @param p1 HttpServletResponse
   * @return boolean
   * @throws ServletException
   * @throws IOException
   */
  protected boolean canInvoke(HttpServletRequest p0, HttpServletResponse p1) throws ServletException, IOException {
//    String WebLogin = "no";//p0.getHeader("WebLogin");
    String paramLogin = this.getInitParameter("WebLogin");
    return "no".equals(paramLogin);
  }
  /**
   *
   * @param p0 HttpServletRequest
   * @param p1 HttpServletResponse
   * @return boolean
   * @throws Exception
   */
  protected final LoginStub getLoginStub(HttpServletRequest p0, HttpServletResponse p1) throws Exception {
    // 如果ESPServiceManager为空，则直接返回，不做处理
    if ( eaiServiceManager != null ) {
      return eaiServiceManager.getLoginStub(this,p0,p1);
    }
    return null;
  }
  /**
   *
   * @param p0 HttpServletRequest
   * @param p1 HttpServletResponse
   * @return boolean
   */
  protected final boolean invokeExtObject(HttpServletRequest p0, HttpServletResponse p1,boolean isLogin) throws Exception {
    // 如果ESPServiceManager为空，则直接返回，不做处理
   boolean res=true;
    if ( eaiServiceManager != null ) {
      //
      if ( isMonitorService() ){
        eaiServiceManager.StartMonitorService(this,p0,p1,isLogin);
      }
       res = eaiServiceManager.invokeService(this,p0,p1,isLogin);
      if ( !res ) {
        eaiServiceManager.doPost(this,p0,p1);
      }
    }
    return res;
  }
  /**
   *
   * @param request HttpServletRequest
   * @param response HttpServletResponse
   * @throws ServletException
   * @throws IOException
   */
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    if ( eaiServiceManager != null ) {
      eaiServiceManager.doGet(this,request,response);
    }
  }
  /**
   *
   * @param request HttpServletRequest
   * @param response HttpServletResponse
   * @throws ServletException
   * @throws IOException
   */
  public void doPost(HttpServletRequest p0, HttpServletResponse p1) throws ServletException, IOException {
	  try{
//	   	System.out.println("doPost：");
//	   	System.out.print("BaseDataServlet service received a remote request");
//		ObjectInputStream in = new ObjectInputStream(p0.getInputStream());
//		p1.setContentType("application/octest-stream");
//		ByteArrayOutputStream byteout = new ByteArrayOutputStream();
//		ObjectOutputStream out = new ObjectOutputStream(byteout);
//		Request request;
//		try {
//			request = (Request)in.readObject();//读取远程调用请求的封装对象
//			//System.out.println(":n"+request.toString()); 
//			RequestProcessor processor=new RequestProcessor();//请求解析对象 
//			out.writeObject(processor.processorLocalhost(request));//执行请求并回写结果
//			out.flush();
//		} catch (ClassNotFoundException e) { 
//			e.printStackTrace();
//		} 
//		byte buf[]= byteout.toByteArray();
//		p1.setContentLength(buf.length);
//		ServletOutputStream servletout = p1.getOutputStream();
//		servletout.write(buf);
//		servletout.close(); 
      printRequest(p0);
      // 初始化当前线程的ClassLoader
      InitBootObject.initClassLoader();
      // 如果允许执行(如果已经登录，或是WebLogin设置了no值，则可以执行相应调用)
      if ( getLoginStub(p0,p1) != null || canInvoke(p0,p1) ) {
        // 首先通过ESPServiceManager方式处理请求，如果返回为空，则用默认的方式处理请求
        if (!invokeExtObject(p0, p1, true)) {
          //System.err.println(servletName+":"+"invoke failt invokeExtObject");
        } else {
          //System.err.println(servletName + ":" +"invoke sucess invokeExtObject");
        }
      } else {
        // 如果没有登录，或是此Servlet没有设置WebLogin=no，则需要在调用过程中检查
        if ( !invokeExtObject(p0, p1, false) ) {
          //System.err.println(servletName+":"+"invoke failt UnauthorizedWebInvoke invokeExtObject");
          dontInvoke(p0,p1);
        } else {
          //System.err.println(servletName + ":" +"invoke sucess UnauthorizedWebInvoke invokeExtObject");
        }
      }
    } catch ( Exception e ) {
      e.printStackTrace(System.out);
      e.printStackTrace();
    }
  }
}
