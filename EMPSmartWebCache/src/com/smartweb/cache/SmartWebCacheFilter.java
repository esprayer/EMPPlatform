package com.smartweb.cache;

import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SmartWebCacheFilter
  implements Filter
{
  private static Map<String, List<String>> idList = new HashMap();
  private static List<String> urltypeList = new ArrayList();
  private static Map<String, Long> timeMap = new HashMap();

  public void destroy()
  {
  }

  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
    throws IOException, ServletException
  {
    try
    {
      HttpServletRequest oldRequest = (HttpServletRequest)request;
      SmartWebRequest swRequest = new SmartWebRequest(oldRequest);      
      String id = ServletTools.readCookie(request, SmartWebCache.JSESSIONID);
      if (id != null) {
        String path = oldRequest.getServletPath();
        List servletUrls = (List)idList.get(id);
        if ((typeContain(path)) && (servletUrls != null) && (!(servletUrls.contains(path)))) {
          swRequest.removeHeader("If-Modified-Since");
          servletUrls.add(path);
        }
      }
      chain.doFilter(swRequest, response);
    } catch (Exception e) {
      chain.doFilter(request, response);
    }
  }

  public void init(FilterConfig fConfig)
    throws ServletException
  {
    System.out.println(SmartWebCache.LOGHEADER + "SmartWebCache is working !"); }

  public boolean typeContain(String url) {
    for (String type : urltypeList) {
      if (url.endsWith(type)) {
        return true;
      }
    }
    return false; }

  public static void clearCache(String jsessionid) {
    if (idList.containsKey(jsessionid)) {
      idList.remove(jsessionid);
    }
    removeTimeOutSessionID(jsessionid);
    idList.put(jsessionid, new ArrayList());
    if (timeMap.containsKey(jsessionid)) {
      timeMap.remove(jsessionid);
    }
    timeMap.put(jsessionid, Long.valueOf(new Date().getTime())); }

  public static void setFilterType(String typestr) {
    urltypeList = new ArrayList();
    String[] types = typestr.split(",");
    for (String type : types)
      urltypeList.add("." + type);
  }

  private static void removeTimeOutSessionID(String sessionID) {
    Iterator it = timeMap.entrySet().iterator();
    while (it.hasNext()) {
      Map.Entry entry = (Map.Entry)it.next();
      String id = String.valueOf(entry.getKey());
      long time = Long.parseLong(entry.getValue().toString());
      if (new Date().getTime() - time > SmartWebCache.timeout * 1000 * 60) {
        if (idList.containsKey(id)) {
          idList.remove(id);
        }
        if (timeMap.containsKey(id))
          timeMap.remove(id);
      }
    }
  }
}