package com.smartweb.cache;

import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

public class SmartWebRequest extends HttpServletRequestWrapper
  implements Enumeration
{
  private Map headerMap;
  Set mapKeySet;
  Iterator mapKeySetIt;

  public long getDateHeader(String name)
  {
    String pheader = getHeader(name.toLowerCase());
    if (name.toLowerCase().equals("if-modified-since")) {
      return getTime(pheader).longValue();
    }
    return -1L;
  }

  public int getIntHeader(String name)
  {
    return super.getIntHeader(name);
  }

  public SmartWebRequest(HttpServletRequest request)
  {
    super(request);

    Enumeration enumeration = request.getHeaderNames();
    this.headerMap = new HashMap();
    while (enumeration.hasMoreElements()) {
      String name = (String)enumeration.nextElement();
      this.headerMap.put(name, request.getHeader(name));
    }
  }

  public Map getHeaderMap() {
    return this.headerMap;
  }

  public void setHeaderMap(Map headerMap) {
    this.headerMap = headerMap;
  }

  public void addHeader(String name, String value) {
    String pName = name;
    pName = pName.toLowerCase();
    removeHeader(pName);
    this.headerMap.put(pName, value); }

  public void removeHeader(String name) {
    String pName = name;
    pName = pName.toLowerCase();
    if (this.headerMap.containsKey(pName))
      this.headerMap.remove(pName);
  }

  public String getHeader(String name)
  {
    return ((String)this.headerMap.get(name));
  }

  public Enumeration getHeaderNames() {
    return super.getHeaderNames();
  }

  public Enumeration getHeaders(String name)
  {
    return super.getHeaders(name);
  }

  public boolean hasMoreElements()
  {
    if (this.mapKeySet == null) {
      this.mapKeySet = this.headerMap.keySet();
      this.mapKeySetIt = this.mapKeySet.iterator();
    }

    return this.mapKeySetIt.hasNext();
  }

  public String nextElement() {
    return ((String)this.mapKeySetIt.next()); }

  private Long getTime(String timtstr) {
    if (timtstr == null) {
      return Long.valueOf(-1L);
    }
    DateFormatSymbols sym = new DateFormatSymbols(Locale.US);

    SimpleDateFormat sdf = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", sym);

    Date d = null;
    try {
      d = sdf.parse(timtstr);
    }
    catch (ParseException e) {
      e.printStackTrace();
      return Long.valueOf(-1L);
    }
    return Long.valueOf(d.getTime());
  }
}