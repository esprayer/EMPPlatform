package com.core.servlet;

import java.io.IOException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class EMPServlet extends HttpServlet
{
  public static InitBootObject initBootObject = null;

  protected ServletServiceManager eaiServiceManager = null;

  protected String servletName = null;
  protected static boolean monitorService;

  public static synchronized void initBootObject()
  {
    try
    {
      if (initBootObject == null) {
        initBootObject = InitBootObject.getDefault();
        initBootObject.initBootObject();
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void init(ServletConfig config)
    throws ServletException
  {
    InitBootObject.initClassLoader();
    this.servletName = config.getServletName();
    initESPServiceManager(config.getServletName());
    super.init(config);
  }

  public void destroy()
  {
    if (this.eaiServiceManager != null) {
      this.eaiServiceManager.destroy();
    }
    super.destroy();
  }

  protected void initESPServiceManager(String servletName)
  {
    if (this.eaiServiceManager != null) {
      return;
    }
    this.eaiServiceManager = ServletServiceManager.getInstance(servletName);
  }

  protected void printRequest(HttpServletRequest p0)
    throws Exception
  {
  }

  public static boolean isMonitorService()
  {
    return monitorService;
  }

  public static void setMonitorService(boolean b)
  {
    monitorService = b;
  }

  protected void service(HttpServletRequest p0, HttpServletResponse p1)
    throws ServletException, IOException
  {
    InitBootObject.initClassLoader();

    super.service(p0, p1);
  }

  protected void dontInvoke(HttpServletRequest p0, HttpServletResponse p1)
    throws ServletException, IOException
  {
    p1.sendError(401, "no login!");
  }

  protected boolean canInvoke(HttpServletRequest p0, HttpServletResponse p1)
    throws ServletException, IOException
  {
    String paramLogin = getInitParameter("WebLogin");
    return "no".equals(paramLogin);
  }

  protected final LoginStub getLoginStub(HttpServletRequest p0, HttpServletResponse p1)
    throws Exception
  {
    if (this.eaiServiceManager != null) {
      return this.eaiServiceManager.getLoginStub(this, p0, p1);
    }
    return null;
  }

  protected final boolean invokeExtObject(HttpServletRequest p0, HttpServletResponse p1, boolean isLogin)
    throws Exception
  {
    boolean res = true;
    if (this.eaiServiceManager != null)
    {
      if (isMonitorService()) {
        this.eaiServiceManager.StartMonitorService(this, p0, p1, isLogin);
      }
      res = this.eaiServiceManager.invokeService(this, p0, p1, isLogin);
      if (!(res)) {
        this.eaiServiceManager.doPost(this, p0, p1);
      }
    }
    return res;
  }

  public void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
  {
    if (this.eaiServiceManager != null)
      this.eaiServiceManager.doGet(this, request, response);
  }

  public void doPost(HttpServletRequest p0, HttpServletResponse p1)
    throws ServletException, IOException
  {
    try
    {
      printRequest(p0);

      InitBootObject.initClassLoader();

      if ((getLoginStub(p0, p1) != null) || (canInvoke(p0, p1)))
      {
        if (invokeExtObject(p0, p1, true));
      }
      else if (!(invokeExtObject(p0, p1, false)))
      {
        dontInvoke(p0, p1);
      }

    }
    catch (Exception e)
    {
      e.printStackTrace(System.out);
      e.printStackTrace();
    }
  }

  static
  {
    initBootObject();

    monitorService = true;
  }
}