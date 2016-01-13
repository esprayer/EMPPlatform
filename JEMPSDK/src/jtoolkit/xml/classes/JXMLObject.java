package jtoolkit.xml.classes;
import java.io.*;

import org.jdom.*;
import org.jdom.input.*;
import org.xml.sax.*;
import java.net.URL;

/**
 * <p>Title: Java Framework</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Pansoft Ltd.</p>
 * @author Skyline
 * @version 1.0
 */
 /*
<?xml version="1.0" encoding="gb2312"?>
<!-- edited with XML Spy v4.2 U (http://www.xmlspy.com) by xSkyline (Pansoft) -->
<paramstack>
  <environments>
                <Username value="abc"/>
                <Userpass value="abc"/>
                <Date value="abc"/>
                <Center value="abc"/>
  </environments>
  <params>
                <Param1 value="123"/>
                <Param2 value="123"/>
  </params>
</paramstack>
 */
//--------------------------------------------------------------------------------------------------
//描述:
//设计: Skyline(2001.12.29)
//实现: Skyline
//修改:
//--------------------------------------------------------------------------------------------------
public class JXMLObject  extends JXMLBaseObject {
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  public JXMLObject() {
    super();
  }
  //----------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //----------------------------------------------------------------------------------------------
  public JXMLObject(String DataString) {
    super(DataString);
  }
  //----------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //----------------------------------------------------------------------------------------------
  public JXMLObject(URL url) {
    super(url);
  }
  //----------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //----------------------------------------------------------------------------------------------
  public void InitXMLObject(String DataString) {
    InitXMLString(DataString);
  }
  //----------------------------------------------------------------------------------------------
  //描述: 创建一个元素
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //----------------------------------------------------------------------------------------------
  public Element CreateElement(String ElementName) {
    return super.CreateElement("XML_"+ElementName);
  }
    //----------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.12.29)
    //实现: Skyline
    //修改:
    //----------------------------------------------------------------------------------------------
    public Element GetElementByName(String ElementName) {
      return super.GetElementByName("XML_"+ElementName);
    }
    //----------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.12.29)
    //实现: Skyline
    //修改:
    //----------------------------------------------------------------------------------------------
    public Element GetElementByName(Element e,String Name) {
      return super.GetElementByName(e,"XML_"+Name);
    }
}
