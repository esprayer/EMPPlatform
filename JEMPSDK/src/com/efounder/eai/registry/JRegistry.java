package com.efounder.eai.registry;

import org.jdom.*;
import com.efounder.pub.util.*;
import com.core.xml.*;
import java.io.File;


/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class JRegistry extends JXMLBaseObject implements IRegistry {
  static final public int  HKEY_CLASSES_ROOT    = 0;
  static final public int  HEKY_CURRENT_USER    = 1;
  static final public int  HKEY_LOCAL_MACHINE   = 2;
  static final public int  HKEY_USERS           = 3;
  static final public int  HKEY_CURRENT_CONFIG  = 4;

  private Element[] eHKEY = new Element[5];
  private String[]  sHKEY = new String[5];
  //------------------------------------------------------------------------------------------------
  //描述: 打开一个指定的SubKey,各级Key用'\\'分隔
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  public Element RegOpenKey(int hKey,String SubKeys) {
    String[] SubKeyArray = StringFunction.convertFromStringToStringArrayBySymbolNO(SubKeys,"\\");
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
    String[] SubKeyArray = StringFunction.convertFromStringToStringArrayBySymbol(SubKeys,"\\");
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
  public boolean InitRegistry() {

    boolean res = false;
    try {
      GetRegistryHEKYS();
    } catch ( Exception e) {
      e.printStackTrace();
    }
    return res;
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
  }
  public JRegistry() {
    // 确定根键的名称
    sHKEY[HKEY_CLASSES_ROOT]   = "HKEY_CLASSES_ROOT";
    sHKEY[HEKY_CURRENT_USER]   = "HEKY_CURRENT_USER";
    sHKEY[HKEY_LOCAL_MACHINE]  = "HKEY_LOCAL_MACHINE";
    sHKEY[HKEY_USERS]          = "HKEY_USERS";
    sHKEY[HKEY_CURRENT_CONFIG] = "HKEY_CURRENT_CONFIG";
  }
}
