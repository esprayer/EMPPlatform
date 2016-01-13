package com.core.xml;

import java.util.*;
import java.net.*;
import org.jdom.*;
import org.openide.util.Lookup;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class PackageStub extends StubObject {
  public static int           PACKAGE_NUMBER = 100;
  public static final String      META_INF[] = new String[]{"package"};
  public static final String    WEB_META_INF = "webComponent";
  static Hashtable               contentList = new Hashtable();
  
  public static Hashtable getContenetList(){
    return contentList;
  }
  public PackageStub() {
  }
  public static StubObject getStubObject(List stubList,String stubID) {
    StubObject SO = null;
    for(int i=0;i<stubList.size();i++) {
      SO = (StubObject)stubList.get(i);
      if ( stubID.equals(SO.getStubID()) ) return SO;
    }
    return null;
  }
  protected static Vector creatContentVector(String name) {
    Vector v = getContentVector(name);
    if ( v == null ) v = new Vector();
    contentList.put(name,v);
    return v;
  }
  public static Vector getContentVector(String name) {
    Vector v = (Vector)contentList.get(name.toLowerCase());
    if ( v == null ) {
      v = (Vector)contentList.get(name);
    }
    return v;
  }
  public static void initPackage(String pckCnt) {
    JXMLBaseObject XML = new JXMLBaseObject(pckCnt);
    initPackage(XML,null);
  }
  public static void initPackage(URL url) {
    try {
      JXMLBaseObject XML = new JXMLBaseObject(url.openStream());
      initPackage(XML,url);
    } catch ( Exception e ) {
      e.printStackTrace();
    }
  }
  public static void initPackage(JXMLBaseObject XML,URL url) {
    Element e;int Index = 0;String Name;Vector contentVector=null;
    java.util.List nodelist = XML.BeginEnumerate(XML.Root);
    while ( nodelist != null ) {
      e = XML.Enumerate(nodelist,Index++);
      if ( e == null ) break;
      Name = e.getName().toLowerCase();// ȫ��ΪСд
      contentVector = creatContentVector(Name);
      initItem(XML,e,contentVector,url);
    }
    XML.EndEnumerate();
  }
  protected static void initItem(JXMLBaseObject XML,Element e,Vector contentVector,URL url) {
    Element child;int Index = 0;StubObject SO = null;String ID,Caption;
    java.util.List nodelist = XML.BeginEnumerate(e);
    while ( nodelist != null ) {
      child = XML.Enumerate(nodelist,Index++);
      if ( child == null ) break;
      SO = new StubObject();
      StubObject.createXML2Stub(XML,child,SO);
      SO.setString("__xmlfile__",url!=null?url.toString():"public");
//      SO.setObjectFromXMLElemnt(child);
      contentVector.add(SO);
    }
  }

  /*
  * @param loader ClassLoader
  */
  public static void initMETA_INF(ClassLoader loader) {
	initMETA_INF(loader,"Package/public/");
	String tier = System.getProperty("EAT","");
//   if ( "client".equals(tier) ) {
	initMETA_INF(loader,"Package/client/");
//   }
//   if ( "server".equals(tier) ) {
	initMETA_INF(loader,"Package/server/");
//     initMETA_INF(loader,"WebComponent/",WEB_META_INF);
//   }
  }
  
  public static void initMETA_INF(ClassLoader loader,String path) {
	for(int i=0;i<META_INF.length;i++)
		initMETA_INF(loader,path,META_INF[i]);
  }
  
  public static void initMETA_INF(String path) {
	  ClassLoader loader = Thread.currentThread().getContextClassLoader();
	  for(int i=0;i<META_INF.length;i++) {
		  initMETA_INF(loader,path,META_INF[i]);
	  }
  }
  public static void initMETA_INF(ClassLoader loader,String path,String meta_inf) {
    try {
      Enumeration enums = loader.getResources(path+meta_inf+".xml");
      initMETA_INFn(enums);
      String META_INFName = null;
      for(int i=1;i<=PACKAGE_NUMBER;i++) {
        META_INFName = meta_inf + i + ".xml";
        enums = loader.getResources(path+META_INFName);
        initMETA_INFn(enums);
      }
    } catch ( Exception e ) {
      e.printStackTrace();
    }
  }
  private static void initMETA_INFn(Enumeration enums) {
    try {
      while (enums.hasMoreElements()) {
        URL url = (URL)enums.nextElement();
        if ( url == null ) continue;
//        System.out.println(url.toString());
        PackageStub.initPackage(url);
      }
    } catch ( Exception e ) {
      e.printStackTrace();
    }
  }
}