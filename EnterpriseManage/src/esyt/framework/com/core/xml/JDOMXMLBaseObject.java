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
public class JDOMXMLBaseObject implements IDOMXMLBaseObject{
  public String[] _ResString1_ =  {"<",   ">",   "&",    "\"",   "'"};
  public String[] _ResString2_ =  {"&lt;","&gt;","&amp;","&quot;","&apos;"};
  public String XMLString;
  public SAXBuilder     SAXParser   = null;
  public Document       Doc         = null;
  public Element        Root        = null;
  public String         ROOT_NAME   = "XML_ROOT";
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  public JDOMXMLBaseObject() {
    CreateXmlEmpty();
  }
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
    public void CreateXmlEmpty(){
      CreateXmlEmpty(ROOT_NAME);
    }
  public void CreateXmlEmpty(String rname){
    try {
      SAXParser = new SAXBuilder();
      Root = new Element(rname);
      Doc = new Document();
      Doc.setRootElement(Root);
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  public JDOMXMLBaseObject(String DataString) {
    try {
        SAXParser = new SAXBuilder();
        //给的是一个XML串
        if(DataString.indexOf("<?xml") != -1 ){
          InitXMLString(DataString);
        }
   } catch (Exception e) {
     CreateXmlEmpty();
     e.printStackTrace();
    }
  }
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  public boolean InitXMLString(String DataString) {
    boolean Res = false;
    try {
      SAXParser = new SAXBuilder();
      //给的是一个XML串
      StringReader sr     = new StringReader(DataString);
      InputSource iSrc    = new InputSource(sr);
      Doc = SAXParser.build(iSrc);
      Root = Doc.getRootElement();
      Res = true;
   } catch (Exception e) {
      e.printStackTrace();
    }
    return Res;
  }
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  public boolean InitXMLFile(File XmlFile) {
    boolean Res = false;
    try {
      SAXParser = new SAXBuilder();
      Doc = SAXParser.build(XmlFile);
      Root = Doc.getRootElement();
      Res = true;
    } catch (Exception e) {
      e.printStackTrace();
    }
    return Res;
  }
  public JDOMXMLBaseObject(File XmlFile) {
    try {
      SAXParser = new SAXBuilder();
      Doc = SAXParser.build(XmlFile);
      Root = Doc.getRootElement();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  public JDOMXMLBaseObject(URL url) {
    try {
      SAXParser = new SAXBuilder();
      Doc = SAXParser.build(url);
      Root = Doc.getRootElement();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  public JDOMXMLBaseObject(InputStream is) {
    try {
      SAXParser = new SAXBuilder();
      Doc = SAXParser.build(is);
      Root = Doc.getRootElement();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  public boolean InitXMLURI(String uri) {
    boolean Res = false;
    SAXParser = new SAXBuilder();
    try {
      URL url = new URL(uri);
      Res = InitXMLURL(url);
    } catch ( Exception e) {
      e.printStackTrace();
    }
    return Res;
  }
  public boolean InitXMLURL(URL url) {
    boolean Res = false;
    SAXParser = new SAXBuilder();
    try {
      Doc = SAXParser.build(url);
      Root     = Doc.getRootElement();
      Res = true;
    } catch ( Exception e) {
      e.printStackTrace();
    }
    return Res;
  }
  public boolean InitXMLURL(InputStream is) {
    boolean Res = false;
    SAXParser = new SAXBuilder();
    try {
      Doc = SAXParser.build(is);
      Root     = Doc.getRootElement();
      Res = true;
    } catch ( Exception e) {
      e.printStackTrace();
    }
    return Res;
  }
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  public boolean InitXMLStream(InputStream is) {
    boolean Res = false;
    SAXParser = new SAXBuilder();
    try {
      Doc = SAXParser.build(is);
      Root     = Doc.getRootElement();
      Res = true;
    } catch ( Exception e) {
      e.printStackTrace();
    }
    return Res;
  }
  //----------------------------------------------------------------------------------------------
  //描述: 创建一个元素
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //----------------------------------------------------------------------------------------------
  public Element CreateElement(String ElementName) {
    Element node=null;
    node = new Element(ElementName);
    return node;
  }
  //----------------------------------------------------------------------------------------------
  //描述: 开始枚举一个节点的下级节点
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //----------------------------------------------------------------------------------------------
  public List BeginEnumerate(Element ParentNode) {
    List list;Element Parent=null;
      Parent = ParentNode;
      if ( Parent == null ) Parent = this.Root;
      list = Parent.getChildren();
      return list;
  }
//  public List BeginEnumerate(String  NodeName) {
//    List list;Element Parent=null;
//    Element ParentNode = this.GetElementByName(NodeName);
//    return BeginEnumerate(ParentNode);
//  }
  public List getElementsByTagName(String  NodeName) {
    List list;Element Parent=null;
    Element ParentNode = this.GetElementByName(NodeName);
    return BeginEnumerate(ParentNode);
  }
  //----------------------------------------------------------------------------------------------
  //描述: 枚举一个节点的下级节点
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //----------------------------------------------------------------------------------------------
  public Element Enumerate(List nodelist,int Index) {
      Element node = null;
      if ( Index < nodelist.size() ) {
          node = (Element)nodelist.get(Index);
      }
      return node;
  }
  //----------------------------------------------------------------------------------------------
  //描述: 结束一个节点的枚举
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //----------------------------------------------------------------------------------------------
  public void EndEnumerate() {
      return;
  }
  //----------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //----------------------------------------------------------------------------------------------
  public Element GetElementByName(String ElementName) {
    ElementFilter ef = new ElementFilter(ElementName);
    return getElementByFilter(Root,ef);
  }
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  Element getElementByFilter(Element e,ElementFilter ef) {
    if ( e == null ) return null;
    Element ResE = null,node;
    List nodelist;
    nodelist = e.getContent(ef);
    if ( nodelist.size() > 0 ) {ResE = (Element)nodelist.get(0);}
    if ( ResE == null ) {
//      if ( e.hasChildren() ) {
      if ( hasChildren(e) ) {    //modi by fsz
        nodelist = e.getChildren();
        for(int i=0;i<nodelist.size();i++) {
          node = (Element)nodelist.get(i);
          ResE = getElementByFilter(node,ef);
          if ( ResE != null ) break;
        }
      }
    }
    return ResE;
  }
  //----------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //----------------------------------------------------------------------------------------------
  public Element GetElementByName(Element e,String Name) {
    ElementFilter ef = new ElementFilter(Name);
    return getElementByFilter(e,ef);
//    Element node;List nodelist;int i=0;
//    nodelist = BeginEnumerate(root);
//    while ( nodelist != null ) {
//      node = Enumerate(nodelist,i++);
//      return node;
//    }
//    return null;
  }
  //----------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //----------------------------------------------------------------------------------------------
  public Element AddChildElement(Element ParentNode,String ElementName) {
    Element E=null;
    E = CreateElement(ElementName);
    return AddChildElement(ParentNode,E);
  }
  public Element AddChildElement(Element ParentNode,Element E) {
    if ( ParentNode == null ) ParentNode = Root;
    ParentNode.addContent(E);
    return E;
  }
  //----------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //----------------------------------------------------------------------------------------------
  public Element InsertChildElementBefore(Element ParentNode,Element BeforeNode,String ElementName) {
    Element E=null;
      E = CreateElement(ElementName);
      return InsertChildElementBefore(ParentNode,BeforeNode,E);
  }
  public Element InsertChildElementBefore(Element ParentNode,Element BeforeNode,Element E) {
    if ( ParentNode == null ) ParentNode = Root;
    List list = ParentNode.getChildren();
    Vector Children = new Vector();
    for ( int i = 0; i < list.size(); i ++){
      Children.add(list.get(i));
    }
  //  if ( ParentNode.removeChildren() && list != null ) {
  if (   list!=null&&list.size()>0 ) {  //modi by fsz
    list.clear();
      int Index = Children.indexOf(BeforeNode);
      if ( Index == -1 ) Index = 0;
        Children.add(Index,E);
       // ParentNode.setChildren(Children);
       ParentNode.setContent(Children);
    } else {
      ParentNode.addContent(E);
    }
    return E;
  }
  //----------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //----------------------------------------------------------------------------------------------
  public boolean RemoveElement(Element ParentNode,int Index) {
    if ( ParentNode == null ) ParentNode = Root;
    List list = ParentNode.getChildren();
    //  if ( ParentNode.removeChildren() && list != null ) {
  if (   list!=null&&list.size()>0 ) {  //modi by fsz
      try {
        Element E = (Element) list.get(Index);
        if ( RemoveElement(ParentNode,E) ) {
//          ParentNode.setChildren(list);
         ParentNode.setContent(list);//modi by fsz
          return true;
        } else return false;
      } catch ( Exception e ) {
        e.printStackTrace();
      }
    }
    return false;
  }
  public boolean RemoveElement(Element ParentNode,Element E) {
    if ( ParentNode == null ) ParentNode = Root;
    return ParentNode.removeContent(E);
  }
  //----------------------------------------------------------------------------------------------
  //描述: 设置节点信息
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //----------------------------------------------------------------------------------------------
  public void SetElementValue(Element node,String Name,String Value) {
    if ( Value == null ) Value = "";
      if ( node != null ) {
        Value = RepleaceValue1(Value);
        node.setAttribute(Name,Value);
      }
  }
  //----------------------------------------------------------------------------------------------
  //描述: 获取节点信息
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //----------------------------------------------------------------------------------------------
  public String GetElementValue(Element node,String Name) {
    String Value = "";
    try {
      Value = node.getAttribute(Name).getValue();
    } catch ( Exception e ) {
//      e.printStackTrace();
    }
    return Value;
  }
  //----------------------------------------------------------------------------------------------
  //描述: 获取节点信息
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //----------------------------------------------------------------------------------------------
  public String GetElementValue(Element node,String Name,String Default) {
    String Value = Default;
    try {
      Value = node.getAttribute(Name).getValue();
    } catch ( Exception e ) {
//      e.printStackTrace();
    }
    return Value;
  }
  //----------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //----------------------------------------------------------------------------------------------
  public String RepleaceValue1(String Value) {
    return Value;
//      if ( Value == null ) return Value;
//      for(int i=0;i<_ResString1_.length;i++) {
//        Value = com.efounder.pub.util.StringFunction.replaceString(Value,_ResString1_[i],_ResString2_[i]);
//      }
//      return Value;
  }
  //----------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //----------------------------------------------------------------------------------------------
  public String RepleaceValue2(String Value) {
    return Value;
//      if ( Value == null ) return Value;
//      for(int i=0;i<_ResString1_.length;i++) {
//        Value = com.efounder.pub.util.StringFunction.replaceString(Value,_ResString2_[i],_ResString1_[i]);
//      }
//      return Value;
  }
  //----------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //----------------------------------------------------------------------------------------------
  public String GetRootXMLString() {
    return GetXMLString(Doc);
  }
  //----------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //----------------------------------------------------------------------------------------------
  public String GetXMLString(Document doc) {
    try {
       XMLOutputter Out=getEncodingOutputter();
      String Res = Out.outputString(doc);
      return Res;
    } catch ( Exception e ) {
      e.printStackTrace();
    }
    return null;
  }

  protected String encoding = "gb2312";

  public String getEncoding() {
    return encoding;
  }
  public void setEncoding(String encoding) {
    this.encoding = encoding;
  }

  public  XMLOutputter getEncodingOutputter(){
    XMLOutputter Out = new XMLOutputter();
    Out.setEncoding(encoding);
    //     Format format=Format.getPrettyFormat();//add by fsz
   //      //设置编码格式为”GB2312”
     //      format.setEncoding("gb2312");
   //      Out.setFormat(format);
    return Out;
  }
  public String GetXMLString(Element doc) {
    try {
     XMLOutputter Out=getEncodingOutputter();
      String Res = Out.outputString(doc);
      return Res;
    } catch ( Exception e ) {
      e.printStackTrace();
    }
    return null;
  }

  //----------------------------------------------------------------------------------------------
  //描述: 获取节点信息
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //----------------------------------------------------------------------------------------------
  public Element CopyElementValue(Element srcnode,Element desnode) {
    int i,Count;Element node;Attribute attr;
    String Name,Value;
      if ( srcnode != null && desnode != null ) {
        List attrs = srcnode.getAttributes();
        Count = attrs.size();
        for(i=0;i<Count;i++) {
          attr = (Attribute)attrs.get(i);
          if ( attr != null ) {
            Name  = attr.getName();
            Value = attr.getValue();
            desnode.setAttribute(Name,Value);
          }
        }
      }
    return desnode;
  }
  public Element getPreviousSibling(Element e) {
    if ( e == null ) return null;
    Element ParentNode = e.getParent();
//        Element ParentNode = e.getParentElement();//modi by fsz
    if ( ParentNode == null ) return null;
    List list = ParentNode.getChildren();
    int Index = list.indexOf(e);
    if ( (Index - 1 ) >= 0 ) {
      return (Element)list.get(Index-1);
    }
    return null;
  }
  public Element getNextSibling(Element e) {
    if ( e == null ) return null;
    Element ParentNode = e.getParent();
//    Element ParentNode = e.getParentElement();//mofi by fsz
    if ( ParentNode == null ) return null;
    List list = ParentNode.getChildren();
    int Index = list.indexOf(e);
    if ( (Index + 1 ) < list.size() ) {
      return (Element)list.get(Index+1);
    }
    return null;

  }

  //----------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //----------------------------------------------------------------------------------------------
  public boolean SaveToFile(String PathName) {
    boolean Res = false;
    String DataObject = GetRootXMLString();
    try {
      FileOutputStream FOS = new FileOutputStream(PathName);
      FOS.write(DataObject.getBytes("utf8"));
      FOS.flush();
      FOS.close();
      Res = true;
    } catch ( Exception e ) {
      e.printStackTrace();
    }
    return Res;
  }
  public boolean hasChildren(Element e){
    List list=e.getChildren();
    if(list==null||list.size()==0)return false;
    return true;
  }
}
