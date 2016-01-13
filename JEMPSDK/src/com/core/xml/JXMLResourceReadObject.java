package com.core.xml;

import org.jdom.Element;

/**
 * <p>Title: Java Framework</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */
//--------------------------------------------------------------------------------------------------
//描述:
//设计: Skyline(2001.12.29)
//实现: Skyline
//修改:
//--------------------------------------------------------------------------------------------------
public class JXMLResourceReadObject extends JXMLBaseObject {
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  public JXMLResourceReadObject(String uri) {
    InitXMLURI(uri);
  }
  public JXMLResourceReadObject() {
    super();
  }
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  public String GetString(String KeyName,String Default) {
    Element ResElement;String ResString = Default;
    ResElement = GetElementByName(KeyName);
    if ( ResElement != null )
      ResString = ResElement.getAttribute("text").getValue();
    return ResString;
  }
}
