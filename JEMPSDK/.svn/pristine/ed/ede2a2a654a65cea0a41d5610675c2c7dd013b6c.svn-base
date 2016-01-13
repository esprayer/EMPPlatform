package com.efounder.eai;

import java.util.*;

import com.efounder.eai.application.IManagerApplication;
import com.efounder.eai.application.classes.JBOFApplication;
import com.efounder.eai.framework.IActiveFramework;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class EAI {

  public static IActiveFramework BOF     = null;// ҵ����
  public static IActiveFramework DOF     = null;// ��ݿ��
  public static IActiveFramework DAL     = null;// ������
  public static IManagerApplication  MA  = null;// ��Ʒ����
  public static String Codebase          = null;//����������λ��
  public static String LocalUserHome     = null;
  public static String Company           = null;// ��˾���
  public static String Tier              = null;// ���
  public static String Product           = null;// ��Ʒ
  public static String Protocol          = null;// Э��
  public static String Server            = null;// ��������ƻ��ַ
  public static String Port              = null;// �˿ں�
  public static String Path              = null;// ·��
  public static String SSLServer         = null;// SSL��������ƻ��ַ
  public static String SSLPort           = null;// SSL����˿�
  public static String ProxyServer       = null;// ����
  public static String ProxyPort         = null;// ����˿�
  public static String Application       = null;// Ӧ�����
  private static boolean Offline         = false;// �Ƿ�����
  private static boolean Security        = false;// �Ƿ�ȫ
  private static String Language          = null;// ��ǰ��¼������
  private static Locale EAILocale         = null;
  public static Hashtable PropertyList          = null;//
  protected static JBOFApplication  desktopPane;
  
  // ESP模式
  public static final String DAL_ESP_STYLE = "ESP";
  // 兼容模式
  public static final String DAL_FWK_STYLE = "FWK";
  //
  protected static String dalInvokeStyle = DAL_ESP_STYLE;
  //
  public static void setDalInvokeStyle(String style) {
    dalInvokeStyle = style;
  }
  public static String getDalInvokeStyle() {
    return dalInvokeStyle;
  }

  protected static String localHost = null;
  protected static String localPort = null;
  public static String getLocalHost() {
    return localHost;
  }
  public static void setLocalHost(String host) {
    localHost = host;
  }
  public static String getLocalPort() {
    return localPort;
  }
  public static void setLocalPort(String port) {
    localPort = port;
  }
  static {
    setEAILocale(Locale.getDefault());
  }
  public EAI() {
  }
  public static void setLanguage(String l,String c) {
    if ( l == null || "".equals(l.trim()) ) l = Locale.getDefault().getLanguage();
    if ( c == null || "".equals(c.trim()) ) c = Locale.getDefault().getCountry();
    Language = l+"_"+c;
    EAILocale = new Locale(l,c);
  }
  public static Locale getEAILocale() {
    return EAILocale;
  }
  public static void setEAILocale(Locale l) {
    setLanguage(l.getLanguage(),l.getCountry());
  }
  public static String getLanguage() {
    return Language;
  }
  public static boolean getOffline() {
    return Offline;
  }
  public static void setOffline(boolean value) {
    Offline = value;
  }
  public static boolean getSecurity() {
    return Security;
  }
  public static void setSecurity(boolean value) {
    Security = value;
  }
  /**
   *
   */
  protected static java.util.Map envMap = null;
  /**
   *
   * @param key Object
   * @param value Object
   */
  public static void putEnv(Object key,Object value) {
   if ( envMap == null ) envMap = new HashMap();
   if(value==null)envMap.remove(key);
   else
     envMap.put(key,value);
 }

  /**
   *
   * @param key Object
   * @param def Object
   * @return Object
   */
  public static Object getEnv(Object key,Object def) {
    Object value = null;
    if ( envMap == null ) return def;
    value = envMap.get(key);
    if ( value == null ) value = def;
    return value;
  }
  /**
   *
   * @param env Map
   */
  public static void assginEnvMap(java.util.Map env) {
    if ( envMap == null ) envMap = new HashMap();
    envMap.putAll(env);
  }


  public static Map getEnvMap() {
    return envMap;
  }
  
  public static JBOFApplication getMainWindow() {
	  return desktopPane;
  }

  public static void createMainWindow() {
	  desktopPane = new JBOFApplication();
  }
}
