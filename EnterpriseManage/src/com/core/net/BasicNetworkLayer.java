package com.core.net;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.GZIPInputStream;

public class BasicNetworkLayer
  implements HttpRequest
{
  URL _request = null;
  private static final String _$11874 = "UA-Java-Version";
  private static final String _$11875 = "User-Agent";

  public URL getURL()
  {
    return this._request;
  }

  public HttpResponse doGetRequest(URL url)
    throws IOException
  {
    return _$11876(url, false, null, null, true);
  }

  public HttpResponse doGetRequest(URL url, boolean flag)
    throws IOException
  {
    return _$11876(url, false, null, null, flag);
  }

  public HttpResponse doHeadRequest(URL url)
    throws IOException
  {
    return _$11876(url, true, null, null, true);
  }

  public HttpResponse doHeadRequest(URL url, boolean flag)
    throws IOException
  {
    return _$11876(url, true, null, null, flag);
  }

  public HttpResponse doGetRequest(URL url, String[] as, String[] as1)
    throws IOException
  {
    return _$11876(url, false, as, as1, true);
  }

  public HttpResponse doGetRequest(URL url, String[] as, String[] as1, boolean flag)
    throws IOException
  {
    return _$11876(url, false, as, as1, flag);
  }

  public HttpResponse doHeadRequest(URL url, String[] as, String[] as1)
    throws IOException
  {
    return _$11876(url, true, as, as1, true);
  }

  public HttpResponse doHeadRequest(URL url, String[] as, String[] as1, boolean flag)
    throws IOException
  {
    return _$11876(url, true, as, as1, flag);
  }

  private HttpResponse _$11876(URL url, boolean flag, String[] as, String[] as1, boolean flag1)
    throws IOException
  {
    this._request = url;
    long lastFileModified = 0L;
    String Mime = null;
    if (("file".equals(url.getProtocol())) && (url.getFile() != null))
      try
      {
        String s1 = URLUtil.getPathFromURL(url);
        File file = new File(s1);
        lastFileModified = file.lastModified();

        if (s1.endsWith(".jnlp")) {
          Mime = "application/x-java-jnlp-file";
        }
        else if (s1.endsWith(".jardiff"))
          Mime = "application/x-java-archive-diff";
      } catch (Exception exception) {
      }
    URLConnection urlconnection = _$11883(url, flag, as, as1, flag1);
    HttpURLConnection httpurlconnection = null;
    if (urlconnection instanceof HttpURLConnection)
      httpurlconnection = (HttpURLConnection)urlconnection;
    URLUtil.setHostHeader(urlconnection);
    urlconnection.connect();
    int ResponseCode = 200;
    if (httpurlconnection != null)
      ResponseCode = httpurlconnection.getResponseCode();
    int j = urlconnection.getContentLength();
    long lastModified = (lastFileModified == 0L) ? urlconnection.getLastModified() : lastFileModified;
    String s2 = (Mime == null) ? urlconnection.getContentType() : Mime;
    if ((s2 != null) && (s2.indexOf(59) != -1))
      s2 = s2.substring(0, s2.indexOf(59)).trim();
    HashMap hashmap = new HashMap();
    int k = 1;
    for (String s3 = urlconnection.getHeaderFieldKey(k); s3 != null; s3 = urlconnection.getHeaderFieldKey(k))
    {
      hashmap.put(s3.toLowerCase(), urlconnection.getHeaderField(k));
      ++k;
    }

    String s4 = (String)hashmap.get("content-encoding");
    if (s4 != null) {
      s4 = s4.toLowerCase();
    }
    BufferedInputStream bufferedinputstream = null;
    if (flag)
    {
      bufferedinputstream = null;
    }
    else {
      BufferedInputStream bufferedinputstream1 = null;
      bufferedinputstream1 = new BufferedInputStream(urlconnection.getInputStream());
      if ((s4 != null) && (((s4.compareTo("pack200-gzip") == 0) || (s4.compareTo("gzip") == 0))))
        bufferedinputstream = new BufferedInputStream(new GZIPInputStream(bufferedinputstream1));
      else
        bufferedinputstream = new BufferedInputStream(bufferedinputstream1);
    }
    return new BasicHttpResponse(url, ResponseCode, j, lastModified, s2, hashmap, bufferedinputstream, httpurlconnection, s4);
  }

  private URLConnection _$11883(URL url, boolean flag, String[] as, String[] as1, boolean flag1)
    throws MalformedURLException, IOException
  {
    URLConnection urlconnection = url.openConnection();
    _$11891(urlconnection, "pragma", "no-cache");
    if ((flag1) && (url.getPath().endsWith(".jar")))
    {
      String s = "gzip";
      _$11891(urlconnection, "accept-encoding", s);
      _$11891(urlconnection, "content-type", "application/x-java-archive");
    }

    urlconnection.setRequestProperty("WebLogin", "no");
    if (System.getProperty("http.agent") == null)
    {
      urlconnection.setRequestProperty("User-Agent", "User-Agent");
      urlconnection.setRequestProperty("UA-Java-Version", "UA-Java-Version");
    }
    if ((as != null) && (as1 != null))
    {
      for (int i = 0; i < as.length; ++i) {
        urlconnection.setRequestProperty(as[i], as1[i]);
      }
    }
    if (urlconnection instanceof HttpURLConnection)
      ((HttpURLConnection)urlconnection).setRequestMethod((flag) ? "HEAD" : "GET");
    return urlconnection;
  }

  private void _$11891(URLConnection urlconnection, String s, String s1)
  {
    String s2 = urlconnection.getRequestProperty(s);
    if ((s2 == null) || (s2.trim().length() == 0))
      s2 = s1;
    else
      s2 = s2 + "," + s1;
    urlconnection.setRequestProperty(s, s2);
  }

  static class BasicHttpResponse
    implements HttpResponse
  {
    private URL _$11861;
    private int _$11863;
    private int _$11864;
    private long _$11865;
    private String _$11866;
    private Map _$11867;
    private BufferedInputStream _$11868;
    private HttpURLConnection _$11869;
    private String _$11870;

    public Map getHeader()
    {
      return this._$11867;
    }

    public void disconnect() {
      if (this._$11869 == null)
        return;
      this._$11869.disconnect();
    }

    public URL getRequest()
    {
      return this._$11861;
    }

    public int getStatusCode()
    {
      return this._$11863;
    }

    public int getContentLength()
    {
      return this._$11864;
    }

    public long getLastModified()
    {
      return this._$11865;
    }

    public String getContentType()
    {
      return this._$11866;
    }

    public String getContentEncoding()
    {
      return this._$11870;
    }

    public String getResponseHeader(String s)
    {
      return ((String)this._$11867.get(s.toLowerCase()));
    }

    public BufferedInputStream getInputStream()
    {
      return this._$11868;
    }

    BasicHttpResponse(URL url, int i, int j, long l, String s, Map map, BufferedInputStream bufferedinputstream, HttpURLConnection httpurlconnection, String s1)
    {
      this._$11861 = url;
      this._$11863 = i;
      this._$11864 = j;
      this._$11865 = l;
      this._$11866 = s;
      this._$11867 = map;
      this._$11868 = bufferedinputstream;
      this._$11869 = httpurlconnection;
      this._$11870 = s1;
    }
  }
}