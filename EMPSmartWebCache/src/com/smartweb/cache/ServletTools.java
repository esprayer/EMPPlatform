package com.smartweb.cache;

import javax.servlet.ServletRequest;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

public class ServletTools {
  public static String readCookie(ServletRequest request, String key) {
    HttpServletRequest hsr = (HttpServletRequest)request;
    Cookie[] cookies = hsr.getCookies();
    if ((cookies != null) && (cookies.length > 0)) {
      for (Cookie c : cookies) {
        if (key.equals(c.getName())) {
          return c.getValue();
        }
      }
    }
    return null;
  }
}