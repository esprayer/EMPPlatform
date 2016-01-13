package com.common;

/**
 * <p>Title: 取得网卡的MAC地址．</p>
 *
 * <p>Description: 取得网卡的物理地址．本类针对目前不同的操作系统做了不同的处理．
 * 源码来源于网络．</p>
 *
 * <p>Copyright: Copyright (c) 2006</p>
 *
 * <p>Company: Pansoft</p>
 *
 * @author no author
 * @version 1.0
 */
import java.net.InetAddress;
import java.io.InputStream;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.text.ParseException;
import java.util.StringTokenizer;

public final class NetworkInfo {
  public final static String getMacAddress() throws IOException {
    String os = System.getProperty("os.name");

    try {
      if (os.startsWith("Windows")) {
        return windowsParseMacAddress(windowsRunIpConfigCommand());
      }
      else if (os.startsWith("Linux")) {
        return linuxParseMacAddress(linuxRunIfConfigCommand());
      }
      else if (os.startsWith("Mac OS X")) {
        return osxParseMacAddress(osxRunIfConfigCommand());
      }

      else {
        throw new IOException("unknown operating system: " + os);
      }
    }
    catch (ParseException ex) {
      ex.printStackTrace();
      throw new IOException(ex.getMessage());
    }
  }

  /*
   * Linux stuff
   */
  private final static String linuxParseMacAddress(String ipConfigResponse) throws
      ParseException {
    String localHost = null;
    try {
      localHost = InetAddress.getLocalHost().getHostAddress();
    }
    catch (java.net.UnknownHostException ex) {
      ex.printStackTrace();
      throw new ParseException(ex.getMessage(), 0);
    }

    StringTokenizer tokenizer = new StringTokenizer(ipConfigResponse, "\n");
    String lastMacAddress = null;

    while (tokenizer.hasMoreTokens()) {
      String line = tokenizer.nextToken().trim();
      boolean containsLocalHost = line.indexOf(localHost) >= 0;

      // see if line contains IP address
      if (containsLocalHost && lastMacAddress != null) {
        return lastMacAddress;
      }

      // see if line contains MAC address
      int macAddressPosition = line.indexOf("HWaddr");
      if (macAddressPosition <= 0) {
        continue;
      }

      String macAddressCandidate = line.substring(macAddressPosition + 6).trim();
      if (linuxIsMacAddress(macAddressCandidate)) {
        lastMacAddress = macAddressCandidate;
        continue;
      }
    }

    ParseException ex = new ParseException
        ("cannot read MAC address for " + localHost + " from [" +
         ipConfigResponse + "]", 0);
    ex.printStackTrace();
    throw ex;
  }

  private final static boolean linuxIsMacAddress(String macAddressCandidate) {
    // TODO: use a smart regular expression
    if (macAddressCandidate.length() != 17) {
      return false;
    }
    return true;
  }

  private final static String linuxRunIfConfigCommand() throws IOException {
    Process p = Runtime.getRuntime().exec("ifconfig");
    InputStream stdoutStream = new BufferedInputStream(p.getInputStream());

    StringBuffer buffer = new StringBuffer();
    for (; ; ) {
      int c = stdoutStream.read();
      if (c == -1) {
        break;
      }
      buffer.append( (char) c);
    }
    String outputText = buffer.toString();

    stdoutStream.close();

    return outputText;
  }

  /*
   * Windows stuff
   */
  private final static String windowsParseMacAddress(String ipConfigResponse) throws
      ParseException {
    String localHost = null;
    try {
      localHost = InetAddress.getLocalHost().getHostAddress();
    }
    catch (java.net.UnknownHostException ex) {
      ex.printStackTrace();
      throw new ParseException(ex.getMessage(), 0);
    }

    StringTokenizer tokenizer = new StringTokenizer(ipConfigResponse, "\n");
    String lastMacAddress = null;

    while (tokenizer.hasMoreTokens()) {
      String line = tokenizer.nextToken().trim();

      //add xft 2009.03.15 vista系统有时在IP地址后加上(首选)，从而造成无法取得相关信息
      if(line.indexOf("(")>=0){
    	  line = line.substring(0, line.indexOf("("));
      }
      // see if line contains IP address
      if (line.endsWith(localHost) && lastMacAddress != null) {
        return lastMacAddress;
      }

      // see if line contains MAC address
      int macAddressPosition = line.indexOf(":");
      if (macAddressPosition <= 0) {
        continue;
      }

      String macAddressCandidate = line.substring(macAddressPosition + 1).trim();
      if (windowsIsMacAddress(macAddressCandidate)) {
        lastMacAddress = macAddressCandidate;
        continue;
      }
    }

    ParseException ex = new ParseException("cannot read MAC address from [" +
                                           ipConfigResponse + "]", 0);
    ex.printStackTrace();
    throw ex;
  }

  private final static boolean windowsIsMacAddress(String macAddressCandidate) {
    // TODO: use a smart regular expression
    if (macAddressCandidate.length() != 17) {
      return false;
    }

    return true;
  }

  private final static String windowsRunIpConfigCommand() throws IOException {
    Process p = Runtime.getRuntime().exec("ipconfig /all");
    InputStream stdoutStream = new BufferedInputStream(p.getInputStream());

    StringBuffer buffer = new StringBuffer();
    for (; ; ) {
      int c = stdoutStream.read();
      if (c == -1) {
        break;
      }
      buffer.append( (char) c);
    }
    String outputText = buffer.toString();

    stdoutStream.close();

    return outputText;
  }

  /*
   * OS X stuff
   */
  private final static String osxParseMacAddress(String ipConfigResponse) throws
      ParseException {
    String localHost = null;
    try {
      localHost = InetAddress.getLocalHost().getHostAddress();
    }
    catch (java.net.UnknownHostException ex) {
      ex.printStackTrace();
      throw new ParseException(ex.getMessage(), 0);
    }
    StringTokenizer tokenizer = new StringTokenizer(ipConfigResponse, "\n");
    while (tokenizer.hasMoreTokens()) {
      String line = tokenizer.nextToken().trim();
      boolean containsLocalHost = line.indexOf(localHost) >= 0;
// see if line contains MAC address
      int macAddressPosition = line.indexOf("ether");
      if (macAddressPosition != 0) {
        continue;
      }
      String macAddressCandidate = line.substring(macAddressPosition + 6).trim();
      if (osxIsMacAddress(macAddressCandidate)) {
        return macAddressCandidate;
      }
    }
    ParseException ex = new ParseException
        ("cannot read MAC address for " + localHost + " from [" +
         ipConfigResponse + "]", 0);
    ex.printStackTrace();
    throw ex;
  }

  private final static boolean osxIsMacAddress(String macAddressCandidate) {
// TODO: use a smart regular expression
    if (macAddressCandidate.length() != 17) {
      return false;
    }
    return true;
  }

  private final static String osxRunIfConfigCommand() throws IOException {
    Process p = Runtime.getRuntime().exec("ifconfig");
    InputStream stdoutStream = new BufferedInputStream(p.getInputStream());
    StringBuffer buffer = new StringBuffer();
    for (; ; ) {
      int c = stdoutStream.read();
      if (c == -1) {
        break;
      }
      buffer.append( (char) c);
    }
    String outputText = buffer.toString();
    stdoutStream.close();
    return outputText;
  }
  /*
   * Main
   */
  public final static void main(String[] args) {
    try {
      System.out.println("Network infos");

      System.out.println("  Operating System: " + System.getProperty("os.name"));
      System.out.println("  IP/Localhost: " +
                         InetAddress.getLocalHost().getHostAddress());
      System.out.println("  MAC Address: " + getMacAddress());
    }
    catch (Throwable t) {
      t.printStackTrace();
    }
  }
}
