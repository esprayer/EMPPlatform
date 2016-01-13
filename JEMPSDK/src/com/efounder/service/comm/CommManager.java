package com.efounder.service.comm;

import java.io.*;
import java.net.*;
import javax.net.ssl.*;

import com.efounder.service.Request;
import com.efounder.service.comm.ssl.*;
import net.sf.jazzlib.*;
import java.util.*;
import org.openide.*;
import com.efounder.eai.data.JResponseObject;
import com.efounder.eai.data.JRequestObject;
import org.apache.commons.codec.binary.Base64;
import com.efounder.eai.*;
import com.efounder.eai.service.ParameterManager;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class CommManager {
  /**
   *
   */
  private static CommManager defaultCommManager = null;
  /**
   *
   * @return CommManager
   */
  public static CommManager getDefault() {
    if ( defaultCommManager == null ) {
      defaultCommManager = new CommManager();
    }
    return defaultCommManager;
  }
  protected Object actionObject = null;
  /**
   *
   * @return CommManager
   */
  public static CommManager getInstance() {
    return getInstance(null);
  }
  /**
   *
   * @return CommManager
   */
  public static CommManager getInstance(Object actionObject) {
    CommManager commManager = new CommManager();
    commManager.actionObject = actionObject;
    return commManager;
  }
  /**
   *
   */
  private NullHostnameVerifier nullVerify = null;
  /**
   *
   */
  private NULLX509TrustManager nullTrust  = null;
  /**
   *
   */
  protected CommManager() {
    nullVerify = new NullHostnameVerifier();
    nullTrust  = new NULLX509TrustManager();
  }
  /**
   *
   */
  transient protected javax.swing.event.EventListenerList listenerList = new javax.swing.event.EventListenerList();
  /**
   * Adds a <code>ChangeListener</code> to the button.
   *
   * @param l the listener to add
   */
  public void addChangeListener(CommListener l) {
      listenerList.add(CommListener.class, l);
  }

  /**
   * Removes a <code>ChangeListener</code> from the button.
   *
   * @param l the listener to remove
   */
  public void removeChangeListener(CommListener l) {
      listenerList.remove(CommListener.class, l);
  }
  /**
   *
   * @param actionObject Object
   * @param url URL
   * @param wo Object
   */
  public void startComm(Object actionObject,URL url,Object wo) {
    // Guaranteed to return a non-null array
    Object[] listeners = listenerList.getListenerList();
    // Process the listeners last to first, notifying
    // those that are interested in this event
    for (int i = listeners.length-2; i>=0; i-=2) {
      if (listeners[i]==CommListener.class) {
        // Lazily create the event:
        ((CommListener)listeners[i+1]).startComm(actionObject,url,wo);
      }
    }
  }
  /**
   *
   * @param actionObject Object
   * @param urlc URLConnection
   * @param ro JResponseObject
   */
  public void stopComm(Object actionObject,URLConnection urlc,Object ro) {
    if ( urlc instanceof HttpURLConnection ) {
      ((HttpURLConnection)urlc).disconnect();
    }
    // Guaranteed to return a non-null array
    Object[] listeners = listenerList.getListenerList();
    // Process the listeners last to first, notifying
    // those that are interested in this event
    for (int i = listeners.length-2; i>=0; i-=2) {
      if (listeners[i]==CommListener.class) {
        // Lazily create the event:
        try {
          ( (CommListener) listeners[i + 1]).stopComm(actionObject, urlc, ro);
        } catch ( Exception ex ) {

        }
      }
    }
  }
  /**
   *
   * @param url URL
   * @param wo Object
   * @throws Exception
   * @return URLConnection
   */
  public URLConnection writeObject(URL url,Object wo,Map requestHeader) throws Exception {
    URLConnection urlc = getURLConnection(url);
    setHttpURLConnection((HttpURLConnection)urlc,requestHeader);
    ZIPWrite(urlc, wo);
    String value=ParameterManager.getDefault().getSystemParam("CALL_DATALOG");
    if("1".equals(value))
      ZIPWriteFile(wo);
    return urlc;
  }
  protected void setHttpURLConnection(HttpURLConnection httpConn,Map requestHeader) throws Exception {
	  httpConn.setUseCaches(false);
	  httpConn.setDoOutput(true);
	  httpConn.setDoInput(true);
	  //不能用application/x-www-form-urlencoded，不知道为什么
	  httpConn.setRequestProperty("Content-type", "application/x-java-serialized-object"); 
	  httpConn.setRequestProperty("Content-length", "" + -1);
	  httpConn.setRequestMethod("POST");  
	  setCookie(httpConn);
	  setRequestHeader(httpConn,requestHeader);
    
//    httpConn.setRequestMethod("POST");
//    httpConn.setDoOutput(true);
//    httpConn.setRequestProperty("content-type"," application/x-www-form-urlencoded;charset=UTF-8");
//    httpConn.setRequestProperty("Accept-Charset", "UTF-8");
//    httpConn.setRequestProperty("User-Agent",  "Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US; rv:1.9.2.3 Gecko/20100401");
//    httpConn.setChunkedStreamingMode(1024*1024);
//    httpConn.setRequestProperty("Accept-Encoding", "identity"); 
  }
  /**
   *
   * @param url URL
   * @param wo Object
   * @return URLConnection
   * @throws Exception
   */
  public URLConnection writeObject(URL url,Object wo) throws Exception {
      return writeObject(url,wo,null);
  }
  /**
   *
   * @param http URLConnection
   * @param requestHeader Map
   */
  private void setRequestHeader(URLConnection http,Map requestHeader) {
    if ( requestHeader == null ) return;
    Object[] keys= requestHeader.keySet().toArray();
    for(int i=0;keys!=null&&i<keys.length;i++) {
      String key = keys[i].toString();String value = requestHeader.get(key).toString();
      if ( key != null && value != null )
        http.setRequestProperty(key,value);
    }
  }
  /**
   *
   * @param os OutputStream
   * @param ro Object
   * @throws Exception
   */
  public void responseObject(java.io.OutputStream os,Object ro) throws Exception {
//    WriteFile(ro);
//    ZIPWriteFile(ro);
    GZIPOutputStream gzipout = new GZIPOutputStream(os);
    ObjectOutputStream oos   = new ObjectOutputStream(gzipout);
    oos.writeObject(ro);
    gzipout.finish();
    oos.close();
  }
  /**
   *
   * @param urlc URLConnection
   * @throws Exception
   * @return Object
   */
  public Object readObject(URLConnection urlc) throws Exception {
    Object ro = null;
    ro = UNZIPRead(urlc);
    return ro;
  }
  /**
   *
   * @param is InputStream
   * @throws Exception
   * @return Object
   */
  public Object requestObject(java.io.InputStream is) throws Exception {
    Object ro = null;
    GZIPInputStream gzipin = new GZIPInputStream(is);
    ObjectInputStream ois  = new ObjectInputStream(gzipin);
    ro = ois.readObject();
    return ro;
  }
//  protected static URLConnection lastUrlc = null;
  /**
   *
   * @param urlc URLConnection
   * @param wo Object
   * @throws Exception
   */
  private void ZIPWrite(URLConnection urlc,Object wo) throws Exception {
    // ѹ��
//    if ( lastUrlc == null ) {
//      urlc.connect();
//      lastUrlc = urlc;
//    }
    OutputStream os = urlc.getOutputStream();
    GZIPOutputStream gzipout = new GZIPOutputStream(os);
    ObjectOutputStream out   = new ObjectOutputStream(gzipout);
    out.writeObject(wo);
    gzipout.finish();
    out.flush();
    os.close();
  }
  /**
   *
   * @param responseCode int
   * @param message String
   */
  protected void procHttpErrorCode(HttpURLConnection urlc,Exception ex) throws Exception {
    ErrorManager.getDefault().notify(0,ex,this,urlc.getResponseCode()+":"+urlc.getResponseMessage());
  }
  /**
   *
   * @param urlc URLConnection
   * @throws Exception
   * @return Object
   */
  private Object UNZIPRead(URLConnection urlc) throws Exception {
    InputStream is=null;Object ResponseObject=null;
    try {
      is = urlc.getInputStream();
    } catch ( Exception ex ) {
      procHttpErrorCode((HttpURLConnection)urlc,ex);
      throw ex;
    }
    GZIPInputStream gzipin = new GZIPInputStream(is);
    ObjectInputStream ois = new ObjectInputStream(gzipin);

    ResponseObject = (Object)ois.readObject();
//    ZIPWriteFile(ResponseObject);
    getCookie(urlc);
    return ResponseObject;
  }
  /**
   *
   */
  private java.util.Map sessionList = new java.util.Hashtable();
  protected static String _SSO_ = "_SSO_";
  /**
   *
   * @param urlc URLConnection
   */
  private void getCookie(URLConnection urlc) {
    HttpURLConnection http = null;
    if (urlc instanceof HttpURLConnection) {
      http = (HttpURLConnection) urlc;
      String session = http.getHeaderField("Set-Cookie");
      if (session != null && !"".equals(session.trim())) {
        URL url = http.getURL();
        String host = url.getHost();
        String port = String.valueOf(url.getPort());
        String path = url.getPath();
        path = path.substring(0,path.lastIndexOf("/"));
        // JSESSIONID=2A7F05B0576A5F1FC4526BD3A84F8D41;Path=/EnterpriseServer
        String SID = getCookies((HttpURLConnection)urlc);//getSessionID(session);
//        sessionList.put(host+":"+port+"/"+path,SID);
        sessionList.put(_SSO_,SID);
      }
    }
  }
  /**
   *
   * @param session String
   * @return String
   */
  private String getSessionID(String session) {
    String[] sArray = com.efounder.pub.util.StringFunction.splitStringByToken(session,";");
    return sArray[0];
  }
  /**
   *
   * @param conn HttpURLConnection
   * @return String
   */
  public static String getCookies(HttpURLConnection connection) {
    String sessionId = "",key,cookieVal;
    for (int i = 1; (key = connection.getHeaderFieldKey(i)) != null; i++ ) {
      if (key.equalsIgnoreCase("set-cookie")) {
        cookieVal = connection.getHeaderField(i);
        if (cookieVal.indexOf(";") >= 0)
          cookieVal = cookieVal.substring(0, cookieVal.indexOf(";"));
        sessionId = sessionId+cookieVal+";";
      }
    }
    return sessionId;
//    StringBuffer cookies = new StringBuffer();
//    String headName;
//    for (int i = 0; (headName = conn.getHeaderField(i)) != null; i++) {
//      StringTokenizer st = new StringTokenizer(headName, "; ");
//      while (st.hasMoreTokens()) {
//        cookies.append(st.nextToken() + "; ");
//      }
//    }
//    return cookies.toString();
  }
  /**
   *
   * @param http URLConnection
   */
  private void setCookie(URLConnection http) {
    URL url = http.getURL();
    String host = url.getHost();
    String port = String.valueOf(url.getPort());
    String path = url.getPath();
    path = path.substring(0,path.lastIndexOf("/"));
//    String SID = (String)sessionList.get(host+":"+port+"/"+path);
    String SID = (String)sessionList.get(_SSO_);
    if ( SID != null ) {
      http.setRequestProperty("Cookie",SID);
    }
  }

  /**
   *
   * @param url URL
   * @throws Exception
   * @return URLConnection
   */
  private URLConnection getURLConnection(URL url) throws Exception {
    URLConnection urlc = null;
    if ( isSecurity(url) )
      urlc = getSSLURLConnection(url); // ����ǰ�ȫt�ӣ�����Ҫ���⴦��
    else
      urlc = url.openConnection();
    return urlc;
  }
  /**
   *
   * @param url URL
   * @return URLConnection
   * @throws Exception
   */
  protected URLConnection getSSLURLConnection(URL url) throws Exception {
    URLConnection urlc = null;
    HttpsURLConnection.setDefaultHostnameVerifier(nullVerify);
    TrustManager[] tm = {nullTrust};
    SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
    sslContext.init(null, tm, new java.security.SecureRandom());
    //������SSLContext�����еõ�SSLSocketFactory����
    SSLSocketFactory ssf = sslContext.getSocketFactory();
    //����HttpsURLConnection���󣬲�������SSLSocketFactory����
    urlc = (HttpsURLConnection) url.openConnection();
    if (urlc instanceof HttpsURLConnection)
      ( (HttpsURLConnection) urlc).setSSLSocketFactory(ssf);
    return urlc;
  }
  /**
   *
   * @param url URL
   * @return boolean
   */
  protected boolean isSecurity(URL url) {
    return "https".equals(url.getProtocol().toLowerCase());
  }
  /**
   *
   * @param urlconnection URLConnection
   * @param s String
   * @param s1 String
   */
  private void addToRequestProperty(URLConnection urlconnection, String s, String s1) {
      String s2 = urlconnection.getRequestProperty(s);
      if(s2 == null || s2.trim().length() == 0)
          s2 = s1;
      else
          s2 = s2 + "," + s1;
      urlconnection.setRequestProperty(s, s2);
  }
  private void WriteFile(Object wo) {
//    try {
//      String fileName = "d://testData//"+wo.getClass().getName()+"_"+ System.currentTimeMillis() + ".dat";
//      OutputStream fos = new FileOutputStream(fileName);
//      ObjectOutputStream out = new ObjectOutputStream(fos);
//      out.writeObject(wo);
//      out.flush();
//      fos.close();
//    } catch ( Exception e ) {e.printStackTrace();}
  }
  private void ZIPWriteFile(Object wo) {
    try {
      String fileName = "";
      String path="";
      if ( wo instanceof JRequestObject) {
        if(wo instanceof JRequestObject ){
          JRequestObject RO=(JRequestObject)wo;
          fileName=RO.ActiveObjectName+"-"+RO.ActiveObjectMethodName+"_" +System.currentTimeMillis() + ".txt";;
        }

//        if ( ((JRequestObject)wo).ParamObject != null )
//          wo = ((JRequestObject)wo).ParamObject;
        path= "c://temp//testData//"+EAI.Tier+"//request//";
      } else if ( wo instanceof JResponseObject ) {
//        Object ro = ((JResponseObject)wo).getResponseObject();
//        if ( ro == null ) ro = wo;
//        path = "c://temp//testData//"+EAI.Tier+"//response//" ;
//        fileName = ro.getClass().getName() + "_" +System.currentTimeMillis() + ".zip";

      } else {
        path = "c://temp//testData//";
        if(wo==null)return;
        fileName =  wo.getClass().getName() + "_" +System.currentTimeMillis() + ".zip";
      }
      ByteArrayOutputStream fos = new ByteArrayOutputStream();
      GZIPOutputStream gzipout = new GZIPOutputStream(fos);
      ObjectOutputStream out = new ObjectOutputStream(gzipout);
      out.writeObject(wo);
      gzipout.finish();
      out.flush();
      byte[] doArray = fos.toByteArray();
      byte[] enDoArray = Base64.encodeBase64(doArray);
      fos.close();
//      byte[] newDoArray = Base64.decodeBase64(enDoArray);
//      FileOutputStream file = new FileOutputStream(fileName);
      File dictory=new File(path);
      if(!dictory.exists())dictory.mkdirs();
      FileOutputStream file = new FileOutputStream(path+"//"+fileName);
      file.write(enDoArray);
      file.close();

    } catch ( Exception e ) {e.printStackTrace();}
  }
}
