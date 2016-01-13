package com.efounder.eai.service;

import com.core.servlet.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.danga.MemCached.*;
import com.efounder.eai.service.util.*;

public class ServletServiceAdapter extends ServletServiceManager {
  /**
   *
   */
  public ServletServiceAdapter() {
  }

  /**
   *
   * @param httpServlet HttpServlet
   * @param p0 HttpServletRequest
   * @param p1 HttpServletResponse
   * @return LoginStub
   * @throws Exception
   */
  public LoginStub getLoginStub(HttpServlet httpServlet,HttpServletRequest p0, HttpServletResponse p1) throws Exception {
    LoginStub loginStub = null;
    HttpSession httpSession = p0.getSession(false);
    // 输出当前获取的Session
//    System.err.println("HttpSession:"+httpSession);
//    if ( httpSession != null )
//      System.err.println("HttpSessionID:"+httpSession.getId());
    // 先从Session中获取，如果有，则直接返回
    if ( httpSession != null ) {
      loginStub = (LoginStub) httpSession.getAttribute(LoginStub._LOING_STUB_);
      if ( loginStub != null ) return loginStub;
    }
    // 再从MemCached中获取，如果可以得到，则直接返回
    try {
//      loginStub = SessionUtil.getMemLoginStub(p0);
    } catch ( Exception ex ) {
      ex.printStackTrace();
    }
    return loginStub;
  }

}
