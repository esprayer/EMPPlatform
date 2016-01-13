package jtoolkit.registry.classes;

import jtoolkit.string.classes.JStringFunction;

import org.jdom.*;

import com.core.xml.JXMLBaseObject;

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
//
//设计: Skyline(2001.04.22)
//实现: Skyline
//修改:
//--------------------------------------------------------------------------------------------------
public class JXMLRegistry extends JXMLBaseObject {
  static final public int  HKEY_CLASSES_ROOT    = 0;
  static final public int  HEKY_CURRENT_USER    = 1;
  static final public int  HKEY_LOCAL_MACHINE   = 2;
  static final public int  HKEY_USERS           = 3;
  static final public int  HKEY_CURRENT_CONFIG  = 4;

  private Element[] eHKEY = new Element[5];
  private String[]  sHKEY = new String[5];

//  public DOMParser  parser              = null;
//  public Document   document            = null;
//  public Element    root                = null;
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  public JXMLRegistry() {

  }
  //------------------------------------------------------------------------------------------------
  //描述: 打开一个指定的SubKey,各级Key用'\\'分隔
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  public Element RegOpenKey(int hKey,String SubKeys) {
    String[] SubKeyArray = JStringFunction.convertFromStringToStringArrayBySymbolNO(SubKeys,"\\");
    int i;String SubKey;Element ParentNode,node=null;
      ParentNode = eHKEY[hKey];
      for(i=0;i<SubKeyArray.length;i++) {
        SubKey = SubKeyArray[i];
        node = IsExistKey(ParentNode,SubKey);
        if ( node == null ) node = AddChildElement(ParentNode,SubKey);
        ParentNode = node;
      }
      return node;
  }
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  public Element RegOpenKey(Element eKey,String SubKeys) {
    String[] SubKeyArray = JStringFunction.convertFromStringToStringArrayBySymbol(SubKeys,"\\");
    int i;String SubKey;Element ParentNode,node=null;
      ParentNode = eKey;
      for(i=0;i<SubKeyArray.length;i++) {
        SubKey = SubKeyArray[i];
        node = IsExistKey(ParentNode,SubKey);
        if ( node == null ) node = AddChildElement(ParentNode,SubKey);
        ParentNode = node;
      }
      return node;
  }
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  public int RegCloseKey(Element SubKey) {
    return 0;
  }
  //------------------------------------------------------------------------------------------------
  //描述: 检查某一键下是否存在名称为SubKey的子键,如果存在返回,如果不存在返回为null
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  public Element IsExistKey(Element ParentKey,String SubKey) {
    Element node;
      node = GetElementByName(ParentKey,SubKey);
      return node;
  }
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  public int InitRegistry(String uri) {
    // 确定根键的名称
    sHKEY[HKEY_CLASSES_ROOT]   = "HKEY_CLASSES_ROOT";
    sHKEY[HEKY_CURRENT_USER]   = "HEKY_CURRENT_USER";
    sHKEY[HKEY_LOCAL_MACHINE]  = "HKEY_LOCAL_MACHINE";
    sHKEY[HKEY_USERS]          = "HKEY_USERS";
    sHKEY[HKEY_CURRENT_CONFIG] = "HKEY_CURRENT_CONFIG";

    try {
      this.InitXMLURI(uri);
      GetRegistryHEKYS();
    } catch ( Exception e) {
      e.printStackTrace();
    }
    return 0;
  }
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  private void GetRegistryHEKYS() {
    eHKEY[HKEY_CLASSES_ROOT]     = GetRegistryHKEY(sHKEY[HKEY_CLASSES_ROOT]);
    eHKEY[HEKY_CURRENT_USER]     = GetRegistryHKEY(sHKEY[HEKY_CURRENT_USER]);
    eHKEY[HKEY_LOCAL_MACHINE]    = GetRegistryHKEY(sHKEY[HKEY_LOCAL_MACHINE]);
    eHKEY[HKEY_USERS]            = GetRegistryHKEY(sHKEY[HKEY_USERS]);
    eHKEY[HKEY_CURRENT_CONFIG]   = GetRegistryHKEY(sHKEY[HKEY_CURRENT_CONFIG]);
  }
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  private Element GetRegistryHKEY(String HKEY) {
    return this.GetElementByName(HKEY);
//    if ( Root == null ) return null;
//    NodeList nodelist;Node node;int i;Element res=null;
//    nodelist = ((Element)(Root)).getElementsByTagName(HKEY);
//    if ( nodelist != null && nodelist.getLength() != 0 ) {
//      for(i=0;i<nodelist.getLength();i++) {
//        node = nodelist.item(i);
//        if ( node.getNodeType() == node.ELEMENT_NODE &&
//             node.getNodeName().equals(HKEY)) {
//          res = (Element)node;
//          break;
//        }
//      }
//    }
//    return res;
  }
}
/*
  有关XML注册表的描述:
  XMLRegistry类似于MS的注册表,只不过数据的存放形式是以XML数据为基础的
  主机的项目有以下几项:
  HKEY_CLASSES_ROOT
  HKEY_CURRENT_USER
  HKEY_LOCAL_MACHINE
  HKEY_USERS
  HKEY_CURRENT_CONFIG
*/
