package com.smartweb.cache;

import java.io.PrintStream;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SmartWebCache
{
  public static String JSESSIONID = "JSESSIONID";
  public static int timeout = 1;
  public static String LOGHEADER = "|SmartWebCache|";

  public static void removeCache(HttpServletRequest request, HttpServletResponse response) { String id = ServletTools.readCookie(request, JSESSIONID);
    if (id != null) {
      SmartWebCacheFilter.clearCache(id);
//      response.setDateHeader("Last-Modified", new Date().getTime());
    } else
      System.err.println(LOGHEADER + JSESSIONID + " in cache is null!");
  }

  public static void setTypes(String types) {
    SmartWebCacheFilter.setFilterType(types);
  }
}