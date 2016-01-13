package com.core.net;

import java.io.File;
import java.net.URL;
import java.net.URLConnection;
import java.util.BitSet;

public class URLUtil
{
  static BitSet encodedInPath = new BitSet(256);

  public static void setHostHeader(URLConnection urlconnection)
  {
    int i = urlconnection.getURL().getPort();
    String s = urlconnection.getURL().getHost();
    if ((i != -1) && (i != 80))
      s = s + ":" + String.valueOf(i);
    urlconnection.setRequestProperty("Host", s);
  }

  public static URL getBase(URL url)
  {
    if (url == null)
      return null;
    String s = url.getFile();
    if (s == null)
      return url;
    int i = s.lastIndexOf(47);
    if (i != -1)
      s = s.substring(0, i + 1);
    try {
      return new URL(url.getProtocol(), url.getHost(), url.getPort(), s);
    } catch (Exception e) {
      e.printStackTrace();
    }

    return url;
  }

  public static URL asPathURL(URL url)
  {
    if (url == null)
      return null;
    String s = url.getFile();
    if ((s == null) || (s.endsWith("/")))
      return url;
    try
    {
      return new URL(url.getProtocol(), url.getHost(), url.getPort(), url.getFile() + "/");
    }
    catch (Exception e) {
      e.printStackTrace();
    }

    return url;
  }

  public static boolean equals(URL url, URL url1)
  {
    if ((url == null) || (url1 == null))
      return (url1 == url);
    if (("http".equals(url.getProtocol())) && ("http".equals(url1.getProtocol())) && (url.getPort() != url1.getPort()))
    {
      url = _$12167(url);
      url1 = _$12167(url1);
    }
    return url.toString().equals(url1.toString());
  }

  public static int compareTo(URL url, URL url1)
  {
    if (("http".equals(url.getProtocol())) && ("http".equals(url1.getProtocol())) && (url.getPort() != url1.getPort()))
    {
      url = _$12167(url);
      url1 = _$12167(url1);
    }
    return url.toString().compareTo(url1.toString());
  }

  private static URL _$12167(URL url)
  {
    if (url.getPort() != -1)
      return url;
    try {
      return new URL(url.getProtocol(), url.getHost(), 80, url.getFile());
    } catch (Exception e) {
      e.printStackTrace();
    }
    return url;
  }

  public static String encodePath(String s)
  {
    StringBuffer stringbuffer = new StringBuffer();
    int i = s.length();
    for (int j = 0; j < i; ++j)
    {
      char c = s.charAt(j);
      if (c == File.separatorChar)
      {
        stringbuffer.append('/');
      }
      else if (c <= '')
      {
        if (encodedInPath.get(c))
          _$12171(stringbuffer, c);
        else {
          stringbuffer.append(c);
        }
      }
      else if (c > 2047)
      {
        _$12171(stringbuffer, (char)(0xE0 | c >> '\f' & 0xF));
        _$12171(stringbuffer, (char)(0x80 | c >> '\6' & 0x3F));
        _$12171(stringbuffer, (char)(0x80 | c >> '\0' & 0x3F));
      }
      else {
        _$12171(stringbuffer, (char)(0xC0 | c >> '\6' & 0x1F));
        _$12171(stringbuffer, (char)(0x80 | c >> '\0' & 0x3F));
      }
    }

    if (!(s.equals(stringbuffer.toString())));
    return stringbuffer.toString();
  }

  private static void _$12171(StringBuffer stringbuffer, char c)
  {
    stringbuffer.append('%');
    stringbuffer.append(Character.forDigit(c >> '\4' & 0xF, 16));
    stringbuffer.append(Character.forDigit(c & 0xF, 16));
  }

  public static String decodePath(String s)
  {
    char c;
    StringBuffer stringbuffer = new StringBuffer();

    for (int i = 0; i < s.length(); stringbuffer.append(c))
    {
      c = s.charAt(i);
      if (c != '%')
      {
        ++i;
      }
      else {
        try
        {
          c = _$12174(s, i);
          i += 3;
          if ((c & 0x80) != 0)
          {
            switch (c >> '\4')
            {
            case 12:
            case 13:
              char c1 = _$12174(s, i);
              i += 3;
              c = (char)((c & 0x1F) << '\6' | c1 & 0x3F);
              break;
            case 14:
              char c2 = _$12174(s, i);
              i += 3;
              char c3 = _$12174(s, i);
              i += 3;
              c = (char)((c & 0xF) << '\f' | (c2 & 0x3F) << '\6' | c3 & 0x3F);
            }

          }

        }
        catch (NumberFormatException numberformatexception)
        {
        }

      }

    }

    if (!(s.equals(stringbuffer.toString())));
    return stringbuffer.toString();
  }

  private static char _$12174(String s, int i)
  {
    return (char)Integer.parseInt(s.substring(i + 1, i + 3), 16);
  }

  public static String getEncodedPath(File file)
  {
    String s = file.getAbsolutePath();
    if ((!(s.endsWith(File.separator))) && (file.isDirectory()))
      s = s + File.separator;
    return encodePath(s);
  }

  public static String getDecodedPath(URL url)
  {
    String s = url.getFile();
    s = s.replace('/', File.separatorChar);
    return decodePath(s);
  }

  public static String getPathFromURL(URL url)
  {
    return getDecodedPath(url);
  }

  static
  {
    encodedInPath.set(61);
    encodedInPath.set(59);
    encodedInPath.set(63);
    encodedInPath.set(47);
    encodedInPath.set(35);
    encodedInPath.set(32);
    encodedInPath.set(60);
    encodedInPath.set(62);
    encodedInPath.set(37);
    encodedInPath.set(34);
    encodedInPath.set(123);
    encodedInPath.set(125);
    encodedInPath.set(124);
    encodedInPath.set(92);
    encodedInPath.set(94);
    encodedInPath.set(91);
    encodedInPath.set(93);
    encodedInPath.set(96);
    for (int i = 0; i < 32; ++i) {
      encodedInPath.set(i);
    }
    encodedInPath.set(127);
  }
}