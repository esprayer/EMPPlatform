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
//����:
//���: Skyline(2001.12.29)
//ʵ��: Skyline
//�޸�:
//--------------------------------------------------------------------------------------------------
public class JXMLResourceReadObject extends JXMLBaseObject {
  //------------------------------------------------------------------------------------------------
  //����:
  //���: Skyline(2001.12.29)
  //ʵ��: Skyline
  //�޸�:
  //------------------------------------------------------------------------------------------------
  public JXMLResourceReadObject(String uri) {
    InitXMLURI(uri);
  }
  public JXMLResourceReadObject() {
    super();
  }
  //------------------------------------------------------------------------------------------------
  //����:
  //���: Skyline(2001.12.29)
  //ʵ��: Skyline
  //�޸�:
  //------------------------------------------------------------------------------------------------
  public String GetString(String KeyName,String Default) {
    Element ResElement;String ResString = Default;
    ResElement = GetElementByName(KeyName);
    if ( ResElement != null )
      ResString = ResElement.getAttribute("text").getValue();
    return ResString;
  }
}
