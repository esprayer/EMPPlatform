package com.efounder.util;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2003</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class netutil {
  protected netutil() {
  }
  /**
   *
   * @return String
   */
  public static String getLocalHostname() {
    String hostName = "localhost";
    try {
      java.net.InetAddress LocalAddress = java.net.InetAddress.getLocalHost();
      hostName = LocalAddress.getHostName();
    } catch ( Exception e ) {
      e.printStackTrace();
    }
    return hostName;
  }
  /**
   *
   * @return String
   */
  public static String getLocalAddress() {
    String IP = "127.0.0.1";
    try {
      java.net.InetAddress LocalAddress = java.net.InetAddress.getLocalHost();
      IP = LocalAddress.getHostAddress();
    } catch ( Exception e ) {
      e.printStackTrace();
    }
    return IP;
   }
}
