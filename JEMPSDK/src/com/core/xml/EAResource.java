package com.core.xml;

import java.util.*;
import java.net.*;
import javax.swing.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class EAResource {
  protected static java.util.Map resourceList = new java.util.Hashtable();
  public EAResource() {
  }
  public static String getString(Class clazz,String key) {
    return getString(clazz,key,null,null);
  }
  public static String getString(Class clazz,String key,String def) {
    return getString(clazz,key,def,null);
  }
  protected static String getLang(Locale locale) {
    String Lang = null;
    if ( locale == null )
      locale = (Locale)System.getProperties().get("EALocale");
    if ( locale == null )
      locale = Locale.getDefault();
    Lang = locale.getLanguage() + "_" + locale.getCountry();
    return Lang;
  }
  /**
   *
   * @param clazz Class
   * @param key String
   * @param defalut String
   * @return String
   */
  public static String getString(Class clazz,String key,String def,Locale locale) {
//    if ( locale == null ) locale = Locale.getDefault();
    String Lang = getLang(locale);
    //locale.getLanguage() + "_" + locale.getCountry();
    Map bundleList = (Map)resourceList.get(Lang);
    if ( bundleList == null ) {
      bundleList = new Hashtable();
      resourceList.put(Lang,bundleList);
    }
    XMLResourceBundle rb = (XMLResourceBundle)bundleList.get(clazz);
    if ( rb == null ) {
      java.util.List XMLList = getResourceXML(clazz,Lang);
        if (XMLList != null) {
          rb = new XMLResourceBundle(XMLList);
          bundleList.put(clazz, rb);
        }
    }
    String res = def;
    if ( rb != null ) {
      try {
        res = rb.getString(key);
      } catch ( Exception e ) {}
      if (res == null) res = def;
    }
    return res;
  }
  /**
   *
   * @param clazz Class
   * @param Lang String
   * @return JDOMXMLBaseObject
   */
  protected static java.util.List getResourceXML(Class clazz,String Lang) {
    JDOMXMLBaseObject XML = null;
    String ClassName = clazz.getName();
    int B = ClassName.lastIndexOf(".")+1;
    ClassName = ClassName.substring(B,ClassName.length())+".xml";
    java.util.Enumeration enumer = getResource(clazz,ClassName,Lang);
    URL url = null;java.util.ArrayList XMLList = new java.util.ArrayList();
    while ( enumer.hasMoreElements() ) {
      url = (URL)enumer.nextElement();
      if ( url != null )
        XML = new JDOMXMLBaseObject(url);
        XMLList.add(XML);
    }
//    URL url = getResource(clazz,ClassName,Lang);
//    if ( url != null )
//      XML = new JDOMXMLBaseObject(url);
    return XMLList;
  }
  //------------------------------------------------------------------------------------------------
  //����:
  //���: Skyline(2001.12.29)
  //ʵ��: Skyline
  //�޸�:
  //------------------------------------------------------------------------------------------------
  public static java.util.Enumeration getResource(Class clazz,String filename,String Language) {
    String ClassName,PathName;URL url = null;java.util.Enumeration enumer = null;
    try {
      ClassName = clazz.getName();
      int B = ClassName.lastIndexOf(".")+1;
      ClassName = ClassName.replace('.','/');
//      ClassName = ClassName+"/";//+ClassName.substring(B,ClassName.length());
      if ( Language != null )
        PathName  = ClassName+"/"+Language+"/"+filename;
      else
        PathName  = ClassName+"/"+filename;
      enumer = clazz.getClassLoader().getResources(PathName);
//      url = clazz.getResource(PathName);
    } catch ( Exception e ) {
      e.printStackTrace();
    }
    return enumer;
  }
//  //------------------------------------------------------------------------------------------------
//  //����:
//  //���: Skyline(2001.12.29)
//  //ʵ��: Skyline
//  //�޸�:
//  //------------------------------------------------------------------------------------------------
//  public static URL getResource(Class clazz,String Path,String r,String Language) {
//    String PathName;URL url = null;
//    if ( Path == null ) Path = "/";
//    if ( !Path.endsWith("/") ) Path += "/";
//    try {
//      if ( Language != null )
//        PathName  = Path+Language+"/"+r;
//      else
//        PathName  = Path+r;
//      url = clazz.getResource(PathName);
//    } catch ( Exception e ) {
//      e.printStackTrace();
//    }
//    return url;
//  }
//  public static ImageIcon getImageIcon(Class clazz,String r) {
//    return getImageIcon(clazz,r,null);
//  }
//  public static ImageIcon getImageIcon(Class clazz,String r,String Language) {
//    ImageIcon ii = null;
//    URL url = getResource(clazz,r,Language);
//    if ( url != null ) {
//      ii = new ImageIcon(url);
//    }
//    return ii;
//  }
//  public static ImageIcon getImageIcon(Class clazz,String Path,String r,String Language) {
//    ImageIcon ii = null;
//    URL url = getResource(clazz,Path,r,Language);
//    if ( url != null ) {
//      ii = new ImageIcon(url);
//    }
//    return ii;
//  }

  public static void main(String[] args) {
    String cap = EAResource.getString(EAResource.class,"ServerManager","",null);
    System.out.println(cap);
    cap = EAResource.getString(EAResource.class,"ServerManager","",null);
    System.out.println(cap);
    Locale locale = new Locale("en","US");
    cap = EAResource.getString(EAResource.class,"ServerManager","",locale);
    System.out.println(cap);
  }
}
