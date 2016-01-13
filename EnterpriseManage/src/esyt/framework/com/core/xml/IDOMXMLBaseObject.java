package esyt.framework.com.core.xml;
import java.io.*;
import java.net.*;
import java.util.*;

import org.jdom.*;
import org.jdom.filter.*;
import org.jdom.input.*;
import org.jdom.output.*;
import org.xml.sax.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public interface IDOMXMLBaseObject {
  public void CreateXmlEmpty();
  public boolean InitXMLString(String DataString) ;
  public boolean InitXMLFile(File file) ;
  public boolean InitXMLURI(String uri) ;
  public boolean InitXMLStream(InputStream is) ;
  public Element CreateElement(String ElementName) ;
  public List BeginEnumerate(Element ParentNode) ;

  public List getElementsByTagName(String  NodeName) ;
  public Element Enumerate(List nodelist,int Index) ;
  public void EndEnumerate() ;
  public Element GetElementByName(String ElementName) ;
  public Element GetElementByName(Element e,String Name) ;
  public Element AddChildElement(Element ParentNode,String ElementName) ;
  public Element AddChildElement(Element ParentNode,Element E) ;
  public Element InsertChildElementBefore(Element ParentNode,Element BeforeNode,String ElementName) ;
  public Element InsertChildElementBefore(Element ParentNode,Element BeforeNode,Element E) ;
  public boolean RemoveElement(Element ParentNode,int Index) ;
  public boolean RemoveElement(Element ParentNode,Element E) ;
  public void SetElementValue(Element node,String Name,String Value) ;
  public String GetElementValue(Element node,String Name) ;
  public String GetElementValue(Element node,String Name,String Default) ;
  public String RepleaceValue1(String Value) ;
  public String RepleaceValue2(String Value) ;
  public String GetRootXMLString() ;
  public String GetXMLString(Document doc) ;
  public Element CopyElementValue(Element srcnode,Element desnode) ;

  public Element getPreviousSibling(Element e) ;

  public Element getNextSibling(Element e) ;
  public boolean SaveToFile(String PathName) ;
}
