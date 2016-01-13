package com.efounder.eai.resource;

import java.net.*;

import com.efounder.eai.*;
import com.core.xml.*;


/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class JEnterpriseResource {
  private static java.util.Hashtable ResourceHashtable = new java.util.Hashtable();
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  public JEnterpriseResource() {
  }
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  public static void LoadResource(String KeyName,URL url,String Language) {
    if ( Language == null ) Language = "";
    String Key = KeyName+"_"+Language;
    if ( ResourceHashtable.get(Key) != null ) return;
    String uri = url.toString();
    JXMLResourceReadObject XMLObject = new JXMLResourceReadObject(uri);
    LoadResource(KeyName,XMLObject,Language);
  }
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  public static void LoadResource(String KeyName,JXMLResourceReadObject XMLObject,String Language) {
    if ( Language == null ) Language = "";
    String Key = KeyName+"_"+Language;
    if ( ResourceHashtable.get(Key) == null )
      ResourceHashtable.put(Key,XMLObject);
  }
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  public static String GetString(String KeyName,String SecName,String Default,String Language) {
    if ( Language == null ) Language = "";
    JXMLResourceReadObject XMLResObject;String ResString = Default;
    XMLResObject = (JXMLResourceReadObject)ResourceHashtable.get(KeyName+"_"+Language);
    if ( XMLResObject != null ) {
      ResString = XMLResObject.GetString(SecName,Default);
    }
    return ResString;
  }
  public static String GetString(String KeyName,String SecName,String Default) {
    JXMLResourceReadObject XMLResObject;String ResString = Default;
    XMLResObject = (JXMLResourceReadObject)ResourceHashtable.get(KeyName+"_"+EAI.getLanguage());
    if ( XMLResObject != null ) {
      ResString = XMLResObject.GetString(SecName,Default);
    }
    return ResString;
  }

}
