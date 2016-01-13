package com.core.net;

import java.io.BufferedInputStream;
import java.net.URL;
import java.util.Map;

public abstract interface HttpResponse
{
  public abstract URL getRequest();

  public abstract int getStatusCode();

  public abstract int getContentLength();

  public abstract long getLastModified();

  public abstract String getContentType();

  public abstract String getResponseHeader(String paramString);

  public abstract BufferedInputStream getInputStream();

  public abstract void disconnect();

  public abstract String getContentEncoding();

  public abstract Map getHeader();
}