package jframework.resource.classes;

import java.awt.*;
import java.net.*;
import javax.swing.*;
import jframework.foundation.classes.*;
import jtoolkit.xml.classes.JXMLResourceReadObject;
import org.jdom.*;
/**
 * <p>Title: Java Framework</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author Skyline
 * @version 1.0
 */

//--------------------------------------------------------------------------------------------------
//描述: 在此对象中可以管理多种语言的XML资源文件,还可以设置当前的语言集
//
//设计: Skyline(2001.04.22)
//实现: Skyline
//修改:
//--------------------------------------------------------------------------------------------------
public class JXMLResource {
//  static public String CodeBase;        // 代码根
//  static public String International;   // 当前语言集
  public static String FontName   = "Dialog";
  public static int    FontSize   = 10;
  public static String DateFormat = "MM/DD/YYYY";
  public static String Money      = "$";
  private static java.util.Hashtable ResourceHashtable = new java.util.Hashtable();
  public JXMLResource() {
  }
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  public static void InitResource(String CB,String IT) {
//    CodeBase      = CB;
//    International = IT;
  }
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
//  public static ImageIcon LoadImageIcon(Object obj,String ImageName) {
//    ImageIcon imageicon = null;//String ClassName,PathName;URL url;
//      imageicon = (ImageIcon)JActiveDComDM.DataActiveFramework.InvokeObjectMethod("ResourceObject","LoadImageIcon",obj,ImageName);
//      return imageicon;
//  }
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
//  public static ImageIcon LoadSImageIcon(String path,String ImageName) {
//    ImageIcon imageicon = null;//String ClassName,PathName;URL url;
//      imageicon = (ImageIcon)JActiveDComDM.DataActiveFramework.InvokeObjectMethod("ResourceObject","LoadSImageIcon",path,ImageName);
//      return imageicon;
//  }
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  public static ImageIcon LoadSImageIcon(String path,String ImageName) {
    ImageIcon imageicon = null;String PathName;URL url;
      try {
//        PathName  = JActiveDComDM.CodeBase+path+"/"+JActiveDComDM.International+"/"+ImageName;
//        url = new URL(PathName);
        if ( !path.endsWith("/") && path.trim().length() != 0 ) path = path +"/";
        PathName  = "Resource/"+path+JActiveDComDM.International+"/"+ImageName;
//        System.out.println("Resource:+"+PathName);
        url = JActiveDComDM.FrameworkClassLoader.getResource(PathName);
//        System.out.println("Resource:-"+url.toString());
        imageicon = new ImageIcon(url);
      } catch ( Exception e ) {
        imageicon = null;
        e.printStackTrace();
      }
      return  imageicon;
  }
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  public static URL LoadSXML(String path,String XMLName) {
    String PathName;URL url=null;
      try {
//        PathName  = JActiveDComDM.CodeBase+path+"/"+JActiveDComDM.International+"/"+ImageName;
//        url = new URL(PathName);
        if ( !path.endsWith("/") && path.trim().length() != 0 ) path = path +"/";
        PathName  = "Resource/"+path+JActiveDComDM.International+"/"+XMLName;
//        System.out.println("Resource:+"+PathName);
        url = JActiveDComDM.FrameworkClassLoader.getResource(PathName);
//        System.out.println("Resource:-"+url.toString());
      } catch ( Exception e ) {
        e.printStackTrace();
      }
      return  url;
  }
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  public static URL LoadSXML(String path,String XMLName,String International) {
    String PathName;URL url=null;
      try {
        if ( !path.endsWith("/") && path.trim().length() != 0 ) path = path +"/";
        if ( International != null && International.trim().length() == 0 ) International = null;
        if ( International != null )
          PathName  = "Resource/"+path+International+"/"+XMLName;
        else
          PathName  = "Resource/"+path+XMLName;
        url = JActiveDComDM.FrameworkClassLoader.getResource(PathName);
      } catch ( Exception e ) {
        e.printStackTrace();
      }
      return  url;
  }
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  public static ImageIcon LoadAbsSImageIcon(String path,String ImageName) throws Exception {
    ImageIcon imageicon = null;//String ClassName,PathName;URL url;
      imageicon = (ImageIcon)JActiveDComDM.DataActiveFramework.InvokeObjectMethod("ResourceObject","LoadAbsSImageIcon",path,ImageName);
      return imageicon;
  }
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
//  public static Image LoadImage(Object obj,String ImageName) {
//    Image image = null;//String ClassName,PathName;URL url;
//      image = (Image)JActiveDComDM.DataActiveFramework.InvokeObjectMethod("ResourceObject","LoadImage",obj,ImageName);
//      return image;
//  }
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
//  public static Image LoadSImage(String path,String ImageName) {
//    Image image = null;//String ClassName,PathName;URL url;
//      image = (Image)JActiveDComDM.DataActiveFramework.InvokeObjectMethod("ResourceObject","LoadSImage",path,ImageName);
//      return image;
//  }
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  public static Image LoadAbsSImage(String path,String ImageName) throws Exception {
    Image image = null;//String ClassName,PathName;URL url;
      image = (Image)JActiveDComDM.DataActiveFramework.InvokeObjectMethod("ResourceObject","LoadAbsSImage",path,ImageName);
      return image;
  }
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
//  public static String GetString(Object obj,String SecName,String Default) {
//    return (String)JActiveDComDM.DataActiveFramework.InvokeObjectMethod("ResourceObject","GetString",obj,SecName,Default,null);
//  }
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  public static ImageIcon LoadImageIcon(Object obj,String ImageName) {
    ImageIcon imageicon = null;String ClassName,PathName;URL url;
      try {
        ClassName = obj.getClass().getName();
        ClassName = ClassName.replace('.','/');
//        PathName  = JActiveDComDM.CodeBase+"Codebase/"+ClassName+"/"+JActiveDComDM.International+"/"+ImageName;
//        url = new URL(PathName);
        PathName  = ClassName+"/"+JActiveDComDM.International+"/"+ImageName;
        url = JActiveDComDM.FrameworkClassLoader.getResource(PathName);
//        System.out.println("Resource:-"+url.toString());
        imageicon = new ImageIcon(url);
      } catch ( Exception e ) {
        imageicon = null;
        System.out.println(obj.getClass().toString()+"/"+ImageName+"找不到！");
      }
      return  imageicon;
  }
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  public static URL LoadXML(Object obj,String XMLName) {
    String ClassName,PathName;URL url=null;
      try {
        ClassName = obj.getClass().getName();
        ClassName = ClassName.replace('.','/');
        PathName  = ClassName+"/"+JActiveDComDM.International+"/"+XMLName;
        url = JActiveDComDM.FrameworkClassLoader.getResource(PathName);
      } catch ( Exception e ) {
        e.printStackTrace();
      }
      return  url;
  }
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  public static URL LoadXML(Object obj,String XMLName,String International) {
    String ClassName,PathName;URL url=null;
      try {
        if ( International != null && International.trim().length() == 0 ) International = null;
        ClassName = obj.getClass().getName();
        ClassName = ClassName.replace('.','/');
        if ( International != null )
          PathName  = ClassName+"/"+International+"/"+XMLName;
        else
          PathName  = ClassName+"/"+XMLName;
        url = JActiveDComDM.FrameworkClassLoader.getResource(PathName);
      } catch ( Exception e ) {
        e.printStackTrace();
      }
      return  url;
  }
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  public static Image LoadImage(Object obj,String ImageName) {
    Image image = null;String ClassName,PathName;URL url;
      try {
        ClassName = obj.getClass().getName();
        ClassName = ClassName.replace('.','/');
        PathName  = JActiveDComDM.CodeBase+"Codebase/"+ClassName+"/"+JActiveDComDM.International+"/"+ImageName;
//        PathName  = ClassName+"/"+JActiveDComDM.International+"/"+ImageName;
//        Class.forName("df").getResource(PathName);
        url = new URL(PathName);
        image = Toolkit.getDefaultToolkit().getImage(url);
      } catch ( Exception e ) {
        image = null;
        e.printStackTrace();
      }
      return  image;
  }
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  public static Image LoadSImage(String path,String ImageName) {
    Image image = null;String PathName;URL url;
      try {
        PathName  = JActiveDComDM.CodeBase+path+"/"+JActiveDComDM.International+"/"+ImageName;
        url = new URL(PathName);
        image = Toolkit.getDefaultToolkit().getImage(url);
      } catch ( Exception e ) {
        image = null;
        e.printStackTrace();
      }
      return  image;
  }
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  public static String GetString(String SecName,String Default,String International) {
    return GetString(null,SecName,Default,International);
  }
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  public static String GetString(String SecName,String Default) {
    return GetString(null,SecName,Default,JActiveDComDM.International);
  }
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  public static String GetString(Object obj,String SecName,String Default) {
    return GetString(obj,SecName,Default,JActiveDComDM.International);
  }
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  public static String GetString(Object obj,String SecName,String Default,String International) {
  String ClassName=null,PrivatePathName=null,PublicPathName=null,ResString=null;URL url;
  if ( obj != null ) {
    PrivatePathName = GetPrivatePathName(obj,International);
    url = JActiveDComDM.FrameworkClassLoader.getResource(PrivatePathName);
    PrivatePathName = url.toString();
    ClassName = obj.getClass().getName();
    if ( PrivatePathName != null ) {
      ResString = GetXMLString(PrivatePathName,ClassName,(String)SecName);
    }
  }
    if ( ResString == null ) {
      PublicPathName = "Resource/Resource/"+International+"/"+"SystemResource.xml";
      url = JActiveDComDM.FrameworkClassLoader.getResource(PublicPathName);
      PublicPathName = url.toString();
      ResString = GetXMLString(PublicPathName,ClassName,(String)SecName);
    }
    if ( ResString == null ) ResString = (String)Default;
    return ResString;
  }
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  private static String GetPrivatePathName(Object obj,String International) {
    String ClassName,PrivatePathName=null;
    if ( obj != null ) {
       ClassName = obj.getClass().getName();
       ClassName = ClassName.replace('.','/');
       if ( International != null && International.trim().length() == 0 ) International = null;
       if ( International != null )
         PrivatePathName = ClassName+"/"+International+"/";
       else
         PrivatePathName = ClassName+"/";
       ClassName = ClassName.substring(ClassName.lastIndexOf("/")+1);
       PrivatePathName = PrivatePathName + ClassName+".xml";
      }
    return PrivatePathName;
  }
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  private static String GetXMLString(String FileName,String SecName,String KeyName) {
    JXMLResourceReadObject XMLResObject;Element ResElement;String ResString = null;
    XMLResObject = (JXMLResourceReadObject)ResourceHashtable.get((Object)FileName);
    if ( XMLResObject == null ) {
      XMLResObject = new JXMLResourceReadObject(FileName);
      ResourceHashtable.put(FileName,XMLResObject);
    }
    if ( XMLResObject != null ) {
      ResElement = XMLResObject.GetElementByName(KeyName);
      if ( ResElement != null ) ResString = ResElement.getAttribute("text").getValue();
    }
    return ResString;
  }
}
